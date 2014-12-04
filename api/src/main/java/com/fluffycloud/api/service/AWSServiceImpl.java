package com.fluffycloud.api.service;

import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.fluffycloud.api.Iservice.AWSService;
import com.fluffycloud.api.repository.IAWSCommandRepository;
import com.fluffycloud.api.repository.IAWSResponseRepository;
import com.fluffycloud.api.request.entity.CreateInstanceRequest;
import com.fluffycloud.api.request.entity.CreateSecurityGroupRequest;
import com.fluffycloud.api.request.entity.CreateSubnetRequest;
import com.fluffycloud.api.request.entity.CreateVpcRequest;
import com.fluffycloud.api.request.entity.ResourceTags;
import com.fluffycloud.aws.cli.utils.CLIExecutor;
import com.fluffycloud.aws.constants.Action;
import com.fluffycloud.aws.constants.AppParams;
import com.fluffycloud.aws.constants.InstanceTypes;
import com.fluffycloud.aws.entity.Command;
import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.aws.entity.Filter;
import com.fluffycloud.aws.response.entity.AllocateAddressReponse;
import com.fluffycloud.aws.response.entity.Association;
import com.fluffycloud.aws.response.entity.CreateInternetGatewayResponse;
import com.fluffycloud.aws.response.entity.CreateRouteTableResponse;
import com.fluffycloud.aws.response.entity.CreateScenario1Response;
import com.fluffycloud.aws.response.entity.CreateScenario2Response;
import com.fluffycloud.aws.response.entity.CreateSecurityGroupResponse;
import com.fluffycloud.aws.response.entity.CreateSubnetResponse;
import com.fluffycloud.aws.response.entity.CreateVPCResponse;
import com.fluffycloud.aws.response.entity.DescribeInstancesResponse;
import com.fluffycloud.aws.response.entity.DescribeRouteTableResponse;
import com.fluffycloud.aws.response.entity.DescribeSecurityGroupResponse;
import com.fluffycloud.aws.response.entity.DescribeSubnetsResponse;
import com.fluffycloud.aws.response.entity.DescribeVPCsResponse;
import com.fluffycloud.aws.response.entity.ResponseFlag;
import com.fluffycloud.aws.response.entity.RouteTable;
import com.fluffycloud.aws.response.entity.RunInstanceResponse;
import com.fluffycloud.aws.response.entity.StartInstancesResponse;
import com.fluffycloud.aws.response.entity.StopInstancesResponse;
import com.fluffycloud.exceptions.FluffyCloudException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class AWSServiceImpl implements AWSService
{
	final static Logger logger = LoggerFactory.getLogger(AWSServiceImpl.class);

	@Autowired
	private CLIExecutor cliExecutor;

	@Autowired
	MongoOperations mongoOperations;

	@Autowired
	IAWSResponseRepository iAWSResponseRepository;

	@Autowired
	IAWSCommandRepository iAWSCommandRepository;

	@Override
	public String createScenario1(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		CreateScenario1Response createScenario1Response = new CreateScenario1Response();

		try
		{

			/* 1. Create a VPC instance */
			final String createVPCResponseJSON = cliExecutor.performAction(Action.CREATEVPC, paramsToUdate);
			CreateVPCResponse createVPCResponse = gson.fromJson(createVPCResponseJSON, CreateVPCResponse.class);
			createScenario1Response.setCreateVPCResponse(createVPCResponse);

			/* 2. Create a Security Group */
			paramsToUdate.put(AppParams.VPCID.getValue(), createVPCResponse.getVpc().getVpcId());
			final String sgName = AppParams.SGNAME.getValue() + System.currentTimeMillis();
			paramsToUdate.put(AppParams.GROUPNAME.getValue(), sgName);
			final String jsonResponse = cliExecutor.performAction(Action.CREATESECURITYGROUP, paramsToUdate);
			CreateSecurityGroupResponse createSecurityGroupResponse = gson.fromJson(jsonResponse,
					CreateSecurityGroupResponse.class);
			createScenario1Response.setCreateSecurityGroupResponse(createSecurityGroupResponse);

			/* 3. Add Inbound Rules */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.GROUPID.getValue(), createSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

			/* 4. Add OutBound Rules */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.GROUPID.getValue(), createSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPEGRESS, paramsToUdate);

			/* 5. Get ingress/egress rules info */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.GROUPIDS.getValue(), createSecurityGroupResponse.getGroupId());
			final String describeSGResponseJSON = cliExecutor.performAction(Action.DESCRIBESECURITYGROUPS,
					paramsToUdate);
			DescribeSecurityGroupResponse describeSGesponse = gson.fromJson(describeSGResponseJSON,
					DescribeSecurityGroupResponse.class);
			createScenario1Response.setDescribeSecurityGroupResponse(describeSGesponse);

			/* 6. Create subnet */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.VPCID.getValue(), createVPCResponse.getVpc().getVpcId());
			final String createSubnetResponseJSON = cliExecutor.performAction(Action.CREATESUBNET, paramsToUdate);
			CreateSubnetResponse createSubnetResponse = gson.fromJson(createSubnetResponseJSON,
					CreateSubnetResponse.class);
			createScenario1Response.setCreateSubnetResponse(createSubnetResponse);

			/* 7. Run instance with configured or default AMI */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.INSTANCETYPE.getValue(), InstanceTypes.t1MICRO.getValue());
			paramsToUdate.put(AppParams.SUBNETID.getValue(), createSubnetResponse.getSubnet().getSubnetId());
			paramsToUdate.put(AppParams.SGIDS.getValue(), createSecurityGroupResponse.getGroupId());
			final String runInstanceResponseJSON = cliExecutor.performAction(Action.RUNINSTANCES, paramsToUdate);
			RunInstanceResponse runInstanceResponse = gson.fromJson(runInstanceResponseJSON, RunInstanceResponse.class);
			createScenario1Response.setRunInstanceResponse(runInstanceResponse);

			/* 8. Check instance state before proceeding */
			cliExecutor.checkInstanceState(paramsToUdate, gson, runInstanceResponse);

			/* 9. Create Internet Gateway */
			paramsToUdate.clear();
			final String createInternetGatewayResponseJson = cliExecutor.performAction(Action.CREATEINTERNETGATEWAY,
					paramsToUdate);
			CreateInternetGatewayResponse createInternetGatewayResponse = gson.fromJson(
					createInternetGatewayResponseJson, CreateInternetGatewayResponse.class);
			createScenario1Response.setCreateInternetGatewayResponse(createInternetGatewayResponse);

			/* 10. Attach Internet Gateway to VPC */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.IGID.getValue(), createInternetGatewayResponse.getInternetGateway()
					.getInternetGatewayId());
			paramsToUdate.put(AppParams.VPCID.getValue(), createVPCResponse.getVpc().getVpcId());
			final String attachInternetGatewayResponseJson = cliExecutor.performAction(Action.ATTACHINTERNETGATEWAY,
					paramsToUdate);
			ResponseFlag attachInternetGatewayResponse = gson.fromJson(attachInternetGatewayResponseJson,
					ResponseFlag.class);
			createScenario1Response.setInternetGatewayAttached(attachInternetGatewayResponse);

			/* 11. Create elastic IP address */
			paramsToUdate.clear();
			final String allocateAddressResponseJSON = cliExecutor.performAction(Action.ALLOCATEADDRESS, paramsToUdate);
			AllocateAddressReponse allocateAddressReponse = gson.fromJson(allocateAddressResponseJSON,
					AllocateAddressReponse.class);
			createScenario1Response.setAllocateAddressReponse(allocateAddressReponse);

			/* 12. Associate elastic IP address to instance */
			paramsToUdate.clear();
			final String instanceId = runInstanceResponse.getInstances().get(0).getInstanceId();
			paramsToUdate.put(AppParams.INSTANCEID.getValue(), instanceId);
			paramsToUdate.put(AppParams.ALLOCID.getValue(), allocateAddressReponse.getAllocationID());
			final String associateAddressResponseJSON = cliExecutor.performAction(Action.ASSOCIATEADDRESS,
					paramsToUdate);
			ResponseFlag associateAddressResponse = gson.fromJson(associateAddressResponseJSON, ResponseFlag.class);
			createScenario1Response.setAssociateAddressResponse(associateAddressResponse);

			createScenario1Response = iAWSResponseRepository.save(createScenario1Response);

			return gson.toJson(createScenario1Response);
		}
		catch (Exception exception)
		{
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public String addCommand() throws FluffyCloudException
	{
		try
		{
			for (Action action : Action.values())
			{
				Command command = iAWSCommandRepository.findByAction(action.getAction());

				if (null == command)
				{
					command = cliExecutor.getDefaultCommand(action);
					System.out.println(command.getAction());
					mongoOperations.insert(command);
					System.out.println(command.getAction() + " command Json Saved.");
				}
				else
				{
					System.out.println(command.getAction() + " command json already exists");
				}
			}
			return " Added to DB";
		}
		catch (Exception exception)
		{
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public String createScenario2(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		CreateScenario2Response createScenario2Response = new CreateScenario2Response();
		try
		{
			/* 1. Create a VPC instance */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.CIDRBLOCK.getValue(), "10.0.0.0/16");
			final String createVPCResponseJSON = cliExecutor.performAction(Action.CREATEVPC, paramsToUdate);
			CreateVPCResponse createVPCResponse = gson.fromJson(createVPCResponseJSON, CreateVPCResponse.class);
			createScenario2Response.setCreateVPCResponse(createVPCResponse);

			/* 2. Create public subnet */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.VPCID.getValue(), createVPCResponse.getVpc().getVpcId());
			paramsToUdate.put(AppParams.CIDRBLOCK.getValue(), "10.0.0.0/24");
			final String createPublicSubnetResponseJSON = cliExecutor.performAction(Action.CREATESUBNET, paramsToUdate);
			CreateSubnetResponse createPublicSubnetResponse = gson.fromJson(createPublicSubnetResponseJSON,
					CreateSubnetResponse.class);
			createScenario2Response.setCreatePublicSubnetResponse(createPublicSubnetResponse);

			/* Create private subnet */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.VPCID.getValue(), createVPCResponse.getVpc().getVpcId());
			paramsToUdate.put(AppParams.CIDRBLOCK.getValue(), "10.0.1.0/24");
			final String createPrivateSubnetResponseJSON = cliExecutor
					.performAction(Action.CREATESUBNET, paramsToUdate);
			CreateSubnetResponse createPrivateSubnetResponse = gson.fromJson(createPrivateSubnetResponseJSON,
					CreateSubnetResponse.class);
			createScenario2Response.setCreatePrivateSubnetResponse(createPrivateSubnetResponse);

			/* 3. Create Security Groups */

			/* --Webserver SG-- */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.VPCID.getValue(), createVPCResponse.getVpc().getVpcId());
			String sgName = "WebserverSG-" + System.currentTimeMillis();
			paramsToUdate.put(AppParams.GROUPNAME.getValue(), sgName);
			final String jsonWebSGResponse = cliExecutor.performAction(Action.CREATESECURITYGROUP, paramsToUdate);
			CreateSecurityGroupResponse createWebSecurityGroupResponse = gson.fromJson(jsonWebSGResponse,
					CreateSecurityGroupResponse.class);

			/* Add Inbound Rules */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.CIDR.getValue(), "0.0.0.0/0");
			paramsToUdate.put(AppParams.PORT.getValue(), "80");
			paramsToUdate.put(AppParams.PROTOCOL.getValue(), "tcp");
			paramsToUdate.put(AppParams.GROUPID.getValue(), createWebSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

			paramsToUdate.clear();
			paramsToUdate.put(AppParams.CIDR.getValue(), "0.0.0.0/0");
			paramsToUdate.put(AppParams.PORT.getValue(), "443");
			paramsToUdate.put(AppParams.PROTOCOL.getValue(), "tcp");
			paramsToUdate.put(AppParams.GROUPID.getValue(), createWebSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

			paramsToUdate.clear();
			paramsToUdate.put(AppParams.CIDR.getValue(), "10.0.0.0/24");
			paramsToUdate.put(AppParams.PORT.getValue(), "22");
			paramsToUdate.put(AppParams.PROTOCOL.getValue(), "tcp");
			paramsToUdate.put(AppParams.GROUPID.getValue(), createWebSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

			paramsToUdate.clear();
			paramsToUdate.put(AppParams.CIDR.getValue(), "10.0.0.0/24");
			paramsToUdate.put(AppParams.PORT.getValue(), "3389");
			paramsToUdate.put(AppParams.PROTOCOL.getValue(), "tcp");
			paramsToUdate.put(AppParams.GROUPID.getValue(), createWebSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

			/* Add OutBound Rules */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.GROUPID.getValue(), createWebSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPEGRESS, paramsToUdate);

			/* Get ingress/egress rules info */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.GROUPIDS.getValue(), createWebSecurityGroupResponse.getGroupId());
			final String describeWebSGJsonResponse = cliExecutor.performAction(Action.DESCRIBESECURITYGROUPS,
					paramsToUdate);
			DescribeSecurityGroupResponse describeWebSGResponse = gson.fromJson(describeWebSGJsonResponse,
					DescribeSecurityGroupResponse.class);
			createScenario2Response.setDescribeWebSGResponse(describeWebSGResponse);

			/* --NAT server SG-- */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.VPCID.getValue(), createVPCResponse.getVpc().getVpcId());
			sgName = "NatserverSG-" + System.currentTimeMillis();
			paramsToUdate.put(AppParams.GROUPNAME.getValue(), sgName);
			final String jsonNatSGResponse = cliExecutor.performAction(Action.CREATESECURITYGROUP, paramsToUdate);
			CreateSecurityGroupResponse createNatSecurityGroupResponse = gson.fromJson(jsonNatSGResponse,
					CreateSecurityGroupResponse.class);

			/* Add Inbound Rules */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.CIDR.getValue(), "10.0.1.0/24");
			paramsToUdate.put(AppParams.PORT.getValue(), "80");
			paramsToUdate.put(AppParams.PROTOCOL.getValue(), "tcp");
			paramsToUdate.put(AppParams.GROUPID.getValue(), createNatSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

			paramsToUdate.clear();
			paramsToUdate.put(AppParams.CIDR.getValue(), "10.0.1.0/24");
			paramsToUdate.put(AppParams.PORT.getValue(), "443");
			paramsToUdate.put(AppParams.PROTOCOL.getValue(), "tcp");
			paramsToUdate.put(AppParams.GROUPID.getValue(), createNatSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

			paramsToUdate.clear();
			paramsToUdate.put(AppParams.CIDR.getValue(), "203.0.113.0/24");
			paramsToUdate.put(AppParams.PORT.getValue(), "443");
			paramsToUdate.put(AppParams.PROTOCOL.getValue(), "tcp");
			paramsToUdate.put(AppParams.GROUPID.getValue(), createNatSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

			/* Add OutBound Rules */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.CIDR.getValue(), "0.0.0.0/0");
			paramsToUdate.put(AppParams.PORT.getValue(), "443");
			paramsToUdate.put(AppParams.PROTOCOL.getValue(), "tcp");
			paramsToUdate.put(AppParams.GROUPID.getValue(), createNatSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPEGRESS, paramsToUdate);

			paramsToUdate.clear();
			paramsToUdate.put(AppParams.CIDR.getValue(), "0.0.0.0/0");
			paramsToUdate.put(AppParams.PORT.getValue(), "443");
			paramsToUdate.put(AppParams.PROTOCOL.getValue(), "tcp");
			paramsToUdate.put(AppParams.GROUPID.getValue(), createNatSecurityGroupResponse.getGroupId());

			/* Get ingress/egress rules info */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.GROUPIDS.getValue(), createNatSecurityGroupResponse.getGroupId());
			final String describeNatSGJsonResponse = cliExecutor.performAction(Action.DESCRIBESECURITYGROUPS,
					paramsToUdate);
			DescribeSecurityGroupResponse describeNatSGResponse = gson.fromJson(describeNatSGJsonResponse,
					DescribeSecurityGroupResponse.class);
			createScenario2Response.setDescribeNatSGResponse(describeNatSGResponse);

			/* --DB server SG-- */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.VPCID.getValue(), createVPCResponse.getVpc().getVpcId());
			sgName = "DBserverSG-" + System.currentTimeMillis();
			paramsToUdate.put(AppParams.GROUPNAME.getValue(), sgName);
			final String jsonDBSGResponse = cliExecutor.performAction(Action.CREATESECURITYGROUP, paramsToUdate);
			CreateSecurityGroupResponse createDBSecurityGroupResponse = gson.fromJson(jsonDBSGResponse,
					CreateSecurityGroupResponse.class);

			/* Add Inbound Rules */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.GROUPID.getValue(), createDBSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

			/* Add OutBound Rules */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.GROUPID.getValue(), createDBSecurityGroupResponse.getGroupId());
			cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPEGRESS, paramsToUdate);

			/* Get ingress/egress rules info */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.GROUPIDS.getValue(), createDBSecurityGroupResponse.getGroupId());
			final String describeDBSGJsonResponse = cliExecutor.performAction(Action.DESCRIBESECURITYGROUPS,
					paramsToUdate);
			DescribeSecurityGroupResponse describeDBSGResponse = gson.fromJson(describeDBSGJsonResponse,
					DescribeSecurityGroupResponse.class);
			createScenario2Response.setDescribeDBSGResponse(describeDBSGResponse);

			/* 5. Create Internet Gateway */
			paramsToUdate.clear();
			final String createInternetGatewayResponseJson = cliExecutor.performAction(Action.CREATEINTERNETGATEWAY,
					paramsToUdate);
			CreateInternetGatewayResponse createInternetGatewayResponse = gson.fromJson(
					createInternetGatewayResponseJson, CreateInternetGatewayResponse.class);
			createScenario2Response.setCreateInternetGatewayResponse(createInternetGatewayResponse);

			/* 6. Attach Internet Gateway to VPC */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.IGID.getValue(), createInternetGatewayResponse.getInternetGateway()
					.getInternetGatewayId());
			paramsToUdate.put(AppParams.VPCID.getValue(), createVPCResponse.getVpc().getVpcId());
			final String attachInternetGatewayJsonResponse = cliExecutor.performAction(Action.ATTACHINTERNETGATEWAY,
					paramsToUdate);
			ResponseFlag attachInternetGatewayResponse = gson.fromJson(attachInternetGatewayJsonResponse,
					ResponseFlag.class);
			createScenario2Response.setAttachInternetGatewayResponse(attachInternetGatewayResponse);

			/* 7. Create custom Route Table */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.VPCID.getValue(), createVPCResponse.getVpc().getVpcId());
			final String createRouteTableResponseJSON = cliExecutor.performAction(Action.CREATEROUTETABLE,
					paramsToUdate);
			CreateRouteTableResponse createRouteTableResponse = gson.fromJson(createRouteTableResponseJSON,
					CreateRouteTableResponse.class);
			createScenario2Response.setCreateRouteTableResponse(createRouteTableResponse);

			/* Add Route */
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.ROUTETABLEID.getValue(), createRouteTableResponse.getRouteTable()
					.getRouteTableId());
			paramsToUdate.put(AppParams.GATEID.getValue(), createInternetGatewayResponse.getInternetGateway()
					.getInternetGatewayId());
			paramsToUdate.put(AppParams.DESTCIDRBLOCK.getValue(), "0.0.0.0/0");
			final String createRouteJsonResponse = cliExecutor.performAction(Action.CREATEROUTE, paramsToUdate);
			ResponseFlag createRouteResponse = gson.fromJson(createRouteJsonResponse, ResponseFlag.class);
			createScenario2Response.setCreateRouteResponse(createRouteResponse);

			/* 7. Run instance with configured or default AMI */
			/*--Launch web server instance in public subnet--*/
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.INSTANCETYPE.getValue(), InstanceTypes.t1MICRO.getValue());
			paramsToUdate.put(AppParams.SUBNETID.getValue(), createPublicSubnetResponse.getSubnet().getSubnetId());
			paramsToUdate.put(AppParams.SGIDS.getValue(), createWebSecurityGroupResponse.getGroupId());
			final String runWebserverInstanceResponseJSON = cliExecutor.performAction(Action.RUNINSTANCES,
					paramsToUdate);
			RunInstanceResponse runWebserverInstanceResponse = gson.fromJson(runWebserverInstanceResponseJSON,
					RunInstanceResponse.class);
			createScenario2Response.setRunWebserverInstanceResponse(runWebserverInstanceResponse);

			/* Check instance state before proceeding */
			cliExecutor.checkInstanceState(paramsToUdate, gson, runWebserverInstanceResponse);

			/*--Launch NAT server instance in public subnet--*/
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.INSTANCETYPE.getValue(), InstanceTypes.t1MICRO.getValue());
			paramsToUdate.put(AppParams.SUBNETID.getValue(), createPublicSubnetResponse.getSubnet().getSubnetId());
			paramsToUdate.put(AppParams.SGIDS.getValue(), createNatSecurityGroupResponse.getGroupId());
			final String runNatserverInstanceResponseJSON = cliExecutor.performAction(Action.RUNINSTANCES,
					paramsToUdate);
			RunInstanceResponse runNatserverInstanceResponse = gson.fromJson(runNatserverInstanceResponseJSON,
					RunInstanceResponse.class);
			createScenario2Response.setRunNatserverInstanceResponse(runNatserverInstanceResponse);

			/* Check instance state before proceeding */
			cliExecutor.checkInstanceState(paramsToUdate, gson, runNatserverInstanceResponse);

			/*--Launch DB server instance in private subnet--*/
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.INSTANCETYPE.getValue(), InstanceTypes.t1MICRO.getValue());
			paramsToUdate.put(AppParams.SUBNETID.getValue(), createPrivateSubnetResponse.getSubnet().getSubnetId());
			paramsToUdate.put(AppParams.SGIDS.getValue(), createDBSecurityGroupResponse.getGroupId());
			final String runDBserverInstanceResponseJSON = cliExecutor
					.performAction(Action.RUNINSTANCES, paramsToUdate);
			RunInstanceResponse runDBserverInstanceResponse = gson.fromJson(runDBserverInstanceResponseJSON,
					RunInstanceResponse.class);
			createScenario2Response.setRunDBserverInstanceResponse(runDBserverInstanceResponse);

			/* Check instance state before proceeding */
			cliExecutor.checkInstanceState(paramsToUdate, gson, runDBserverInstanceResponse);

			/* 8. Add Rule to Main Route Table */
			/*--Get Main Route Table--*/
			paramsToUdate.clear();
			List<Filter> filters = new ArrayList<Filter>();
			List<String> values = new ArrayList<String>();
			Filter vpcFilter = new Filter();
			values.add(createVPCResponse.getVpc().getVpcId());
			vpcFilter.setName(AppParams.VPCID.getValue());
			vpcFilter.setValues(values);
			filters.add(vpcFilter);
			final String describeRouteTableResponseJson = cliExecutor.performAction(Action.DESCRIBEROUTETABLES,
					paramsToUdate, filters);
			DescribeRouteTableResponse describeRouteTableResponse = gson.fromJson(describeRouteTableResponseJson,
					DescribeRouteTableResponse.class);
			createScenario2Response.setDescribeRouteTableResponse(describeRouteTableResponse);

			final String mainRouteTableId = getMainRouteTableId(describeRouteTableResponse);
			/*--Add rule for Nat instance--*/
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.ROUTETABLEID.getValue(), mainRouteTableId);
			paramsToUdate.put(AppParams.INSTANCEID.getValue(), runNatserverInstanceResponse.getInstances().get(0)
					.getInstanceId());
			paramsToUdate.put(AppParams.DESTCIDRBLOCK.getValue(), "0.0.0.0/0");
			cliExecutor.performAction(Action.CREATEROUTE, paramsToUdate);
			getMainRouteTableId(describeRouteTableResponse);

			/* 9. Create Elastic IPs to Public instaces Web and Nat */
			/* Create elastic IP address for Web Instance */
			paramsToUdate.clear();
			final String allocateWebInstanceAddressResponseJSON = cliExecutor.performAction(Action.ALLOCATEADDRESS,
					paramsToUdate);
			AllocateAddressReponse allocateWebInstanceAddressReponse = gson.fromJson(
					allocateWebInstanceAddressResponseJSON, AllocateAddressReponse.class);
			createScenario2Response.setAllocateWebInstanceAddressReponse(allocateWebInstanceAddressReponse);

			/* Associate elastic IP address to Web instance */
			paramsToUdate.clear();
			final String webInstanceId = runWebserverInstanceResponse.getInstances().get(0).getInstanceId();
			paramsToUdate.put(AppParams.INSTANCEID.getValue(), webInstanceId);
			paramsToUdate.put(AppParams.ALLOCID.getValue(), allocateWebInstanceAddressReponse.getAllocationID());
			final String associateAddressResponseJSON = cliExecutor.performAction(Action.ASSOCIATEADDRESS,
					paramsToUdate);
			ResponseFlag associateAddressResponse = gson.fromJson(associateAddressResponseJSON, ResponseFlag.class);
			createScenario2Response.setAssociateAddressResponse(associateAddressResponse);

			/* Create elastic IP address for Nat Instance */
			paramsToUdate.clear();
			final String allocateNatInstanceAddressResponseJSON = cliExecutor.performAction(Action.ALLOCATEADDRESS,
					paramsToUdate);
			AllocateAddressReponse allocateNatInstanceAddressReponse = gson.fromJson(
					allocateNatInstanceAddressResponseJSON, AllocateAddressReponse.class);
			createScenario2Response.setAllocateNatInstanceAddressReponse(allocateNatInstanceAddressReponse);

			/* Associate elastic IP address to Nat instance */
			paramsToUdate.clear();
			final String natInstanceId = runNatserverInstanceResponse.getInstances().get(0).getInstanceId();
			paramsToUdate.put(AppParams.INSTANCEID.getValue(), natInstanceId);
			paramsToUdate.put(AppParams.ALLOCID.getValue(), allocateWebInstanceAddressReponse.getAllocationID());
			final String associateNatInstanceAddressResponseJSON = cliExecutor.performAction(Action.ASSOCIATEADDRESS,
					paramsToUdate);
			ResponseFlag associateNatInstanceAddressResponse = gson.fromJson(associateNatInstanceAddressResponseJSON,
					ResponseFlag.class);
			createScenario2Response.setAssociateNatInstanceAddressResponse(associateNatInstanceAddressResponse);

			return gson.toJson(createScenario2Response);
		}
		catch (Exception exception)
		{
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	private String getMainRouteTableId(final DescribeRouteTableResponse describeRouteTableResponse)
	{
		for (RouteTable table : describeRouteTableResponse.getRouteTables())
		{
			for (Association association : table.getAssociations())
			{
				if (association.isMain())
				{
					return table.getRouteTableId();
				}
			}
		}

		return null;
	}

	@Override
	public String describeVPCs(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			paramsToUdate.clear();
			final String describeVPCsJsonResponse = cliExecutor.performAction(Action.DESCRIBEVPCS, paramsToUdate);
			DescribeVPCsResponse describeVPCsResponse = gson.fromJson(describeVPCsJsonResponse,
					DescribeVPCsResponse.class);
			return gson.toJson(describeVPCsResponse);
		}
		catch (Exception exception)
		{
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	@Override
	public String describeInstances(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			paramsToUdate.clear();
			List<Filter> filter = gson.fromJson(params.getFilter(), new TypeToken<List<Filter>>()
			{
			}.getType());
			final String describeVPCsJsonResponse = cliExecutor.performAction(Action.DESCRIBEINSTANCES, paramsToUdate,
					filter);
			DescribeInstancesResponse describeInstancesResponse = gson.fromJson(describeVPCsJsonResponse,
					DescribeInstancesResponse.class);
			return gson.toJson(describeInstancesResponse);
		}
		catch (Exception exception)
		{
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	@Override
	public String describeSecurityGroup(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			paramsToUdate.put(AppParams.GROUPIDS.getValue(), collectionToCommaDelimitedString(params.getIds()));
			List<Filter> filter = gson.fromJson(params.getFilter(), new TypeToken<List<Filter>>()
			{
			}.getType());
			final String describeSGJsonResponse = cliExecutor.performAction(Action.DESCRIBESECURITYGROUPS,
					paramsToUdate, filter);
			DescribeSecurityGroupResponse describeSecurityGroupResponse = gson.fromJson(describeSGJsonResponse,
					DescribeSecurityGroupResponse.class);
			return gson.toJson(describeSecurityGroupResponse);
		}
		catch (Exception exception)
		{
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	@Override
	public String startInstances(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			paramsToUdate.put(AppParams.INSTANCEIDS.getValue(), collectionToCommaDelimitedString(params.getIds()));
			final String startInstancesJsonResponse = cliExecutor.performAction(Action.STARTINSTANCES, paramsToUdate);
			StartInstancesResponse startInstancesResponse = gson.fromJson(startInstancesJsonResponse,
					StartInstancesResponse.class);
			return gson.toJson(startInstancesResponse);
		}
		catch (Exception exception)
		{
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	@Override
	public String stopInstances(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			paramsToUdate.put(AppParams.INSTANCEIDS.getValue(), collectionToCommaDelimitedString(params.getIds()));
			final String stopInstancesJsonResponse = cliExecutor.performAction(Action.STOPINSTANCES, paramsToUdate);
			StopInstancesResponse stopInstancesResponse = gson.fromJson(stopInstancesJsonResponse,
					StopInstancesResponse.class);
			return gson.toJson(stopInstancesResponse);
		}
		catch (Exception exception)
		{
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public String describeRouteTables(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			paramsToUdate.clear();
			List<Filter> filter = gson.fromJson(params.getFilter(), new TypeToken<List<Filter>>()
			{
			}.getType());
			final String describeRTJsonResponse = cliExecutor.performAction(Action.DESCRIBEROUTETABLES, paramsToUdate,
					filter);
			DescribeRouteTableResponse describeRouteTableResponse = gson.fromJson(describeRTJsonResponse,
					DescribeRouteTableResponse.class);
			return gson.toJson(describeRouteTableResponse);
		}
		catch (Exception exception)
		{
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	@Override
	public String describeSubnets(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			paramsToUdate.clear();
			List<Filter> filter = gson.fromJson(params.getFilter(), new TypeToken<List<Filter>>()
			{
			}.getType());
			final String describeSubnetJsonResponse = cliExecutor.performAction(Action.DESCRIBESUBNETS, paramsToUdate,
					filter);
			DescribeSubnetsResponse describeSubnetsResponse = gson.fromJson(describeSubnetJsonResponse,
					DescribeSubnetsResponse.class);
			return gson.toJson(describeSubnetsResponse);
		}
		catch (Exception exception)
		{
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public String describeTags(CommonRequestParams params) throws FluffyCloudException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createVpc(CommonRequestParams params, CreateVpcRequest createVpcRequest) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			logger.info("creating vpc");
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.CIDRBLOCK.getValue(), createVpcRequest.getCidrBlock());
			final String createVpcJsonResponse = cliExecutor.performAction(Action.CREATEVPC, paramsToUdate);
			CreateVPCResponse createVPCResponse = gson.fromJson(createVpcJsonResponse, CreateVPCResponse.class);
			logger.info("created vpc");

			if (null != createVPCResponse.getVpc())
			{
				createVpcRequest.setResourceId(createVPCResponse.getVpc().getVpcId());
			}
			addTags(params, createVpcRequest);

			return gson.toJson(createVPCResponse);
		}
		catch (Exception exception)
		{
			logger.error("Exception occured while creating vpc: " + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	@Override
	public String addTags(CommonRequestParams params, ResourceTags createTagRequest) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			logger.info("creating tags");
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.RESOURCES.getValue(), createTagRequest.getResourceId());

			StringBuilder keyValue = new StringBuilder();
			Map<String, String> tags = createTagRequest.getTags();
			for (String tag : tags.keySet())
			{
				keyValue.append("Key=" + tag).append(",Value=" + tags.get(tag)).append(" ");
			}

			paramsToUdate.put(AppParams.TAGS.getValue(), keyValue.toString());
			final String createTagJsonResponse = cliExecutor.performAction(Action.CREATETAGS, paramsToUdate);
			ResponseFlag createTagResponse = gson.fromJson(createTagJsonResponse, ResponseFlag.class);
			logger.info("created tags");
			return gson.toJson(createTagResponse);
		}
		catch (Exception exception)
		{
			logger.error("Exception occured while creating tags: " + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	@Override
	public String createSubnet(CommonRequestParams params, CreateSubnetRequest createSubnetRequest)
			throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			logger.info("Creating Subnet.");
			paramsToUdate.clear();
			paramsToUdate.put(AppParams.VPCID.getValue(), createSubnetRequest.getVpcId());
			paramsToUdate.put(AppParams.CIDRBLOCK.getValue(), createSubnetRequest.getCidrBlock());
			final String createSubnetResponseJSON = cliExecutor.performAction(Action.CREATESUBNET, paramsToUdate);
			CreateSubnetResponse createPrivateSubnetResponse = gson.fromJson(createSubnetResponseJSON,
					CreateSubnetResponse.class);
			logger.info("Subnet Created.");
			createSubnetRequest.setResourceId(createPrivateSubnetResponse.getSubnet().getSubnetId());
			addTags(params, createSubnetRequest);
			return createSubnetResponseJSON;
		}
		catch (Exception exception)
		{
			logger.error("Exception occured while creating subnet: " + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	@Override
	public String
			createSecurityGroup(CommonRequestParams params, CreateSecurityGroupRequest createSecurityGroupRequest)
					throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		try
		{
			logger.info("Creating security group.");
			paramsToUdate.put(AppParams.VPCID.getValue(), createSecurityGroupRequest.getVpcId());
			paramsToUdate.put(AppParams.GROUPNAME.getValue(), createSecurityGroupRequest.getName());
			final String createSecurityGroupJSON = cliExecutor.performAction(Action.CREATESECURITYGROUP, paramsToUdate);
			logger.info("Security group Created.");
			return createSecurityGroupJSON;
		}
		catch (Exception exception)
		{
			logger.error("Exception occured while creating security group: " + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	@Override
	public String createInstance(CommonRequestParams params, CreateInstanceRequest createInstanceRequest)
			throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();

		try
		{
			logger.info("Creating instance.");
			CreateVpcRequest createVpcRequest = createInstanceRequest.getCreateVpcRequest();
			CreateSubnetRequest createSubnetRequest = createInstanceRequest.getCreateSubnetRequest();
			CreateSecurityGroupRequest createSecurityGroupRequest = createInstanceRequest
					.getCreateSecurityGroupRequest();
			if (null == createVpcRequest.getVpcId())
			{
				CreateVPCResponse createVPCResponse = gson.fromJson(createVpc(params, createVpcRequest),
						CreateVPCResponse.class);
				final String vpcId = createVPCResponse.getVpc().getVpcId();
				createVpcRequest.setVpcId(vpcId);
				createSubnetRequest.setVpcId(vpcId);
				createSecurityGroupRequest.setVpcId(vpcId);
			}

			if (null == createSubnetRequest.getSubnetId())
			{
				CreateSubnetResponse createSubnetResponse = gson.fromJson(
						createSubnet(params, createInstanceRequest.getCreateSubnetRequest()),
						CreateSubnetResponse.class);
				createSubnetRequest.setSubnetId(createSubnetResponse.getSubnet().getSubnetId());
			}

			if (null == createSecurityGroupRequest.getSecurityGroupId())
			{
				CreateSecurityGroupResponse createSecurityGroupResponse = gson.fromJson(
						createSecurityGroup(params, createSecurityGroupRequest), CreateSecurityGroupResponse.class);
				createSecurityGroupRequest.setSecurityGroupId(createSecurityGroupResponse.getGroupId());
			}

			paramsToUdate.put(AppParams.SUBNETID.getValue(), createSubnetRequest.getSubnetId());
			paramsToUdate.put(AppParams.KEYPAIR.getValue(), createInstanceRequest.getKeyPairName());
			paramsToUdate.put(AppParams.VPCID.getValue(), createVpcRequest.getVpcId());
			paramsToUdate.put(AppParams.INSTANCETYPE.getValue(), createInstanceRequest.getInstanceType().getValue());
			paramsToUdate.put(AppParams.SGIDS.getValue(), createSecurityGroupRequest.getSecurityGroupId());
			final String runDBserverInstanceResponseJSON = cliExecutor
					.performAction(Action.RUNINSTANCES, paramsToUdate);
			RunInstanceResponse runInstanceResponse = gson.fromJson(runDBserverInstanceResponseJSON,
					RunInstanceResponse.class);
			logger.info("Instance created.");
			return gson.toJson(runInstanceResponse);
		}
		catch (Exception exception)
		{
			logger.error("Exception occured while creating instance: " + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}
}