package com.fluffycloud.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fluffycloud.api.Iservice.AWSService;
import com.fluffycloud.aws.cli.utils.CLIExecutor;
import com.fluffycloud.aws.constants.Action;
import com.fluffycloud.aws.constants.InstanceTypes;
import com.fluffycloud.aws.entity.Filter;
import com.fluffycloud.aws.response.entity.AllocateAddressReponse;
import com.fluffycloud.aws.response.entity.Association;
import com.fluffycloud.aws.response.entity.CreateInternetGatewayResponse;
import com.fluffycloud.aws.response.entity.CreateRouteTableResponse;
import com.fluffycloud.aws.response.entity.CreateScenario1Response;
import com.fluffycloud.aws.response.entity.CreateSecurityGroupResponse;
import com.fluffycloud.aws.response.entity.CreateSubnetResponse;
import com.fluffycloud.aws.response.entity.CreateVPCResponse;
import com.fluffycloud.aws.response.entity.DescribeRouteTableResponse;
import com.fluffycloud.aws.response.entity.DescribeSecurityGroupResponse;
import com.fluffycloud.aws.response.entity.ResponseFlag;
import com.fluffycloud.aws.response.entity.RouteTable;
import com.fluffycloud.aws.response.entity.RunInstanceResponse;
import com.fluffycloud.exceptions.FluffyCloudException;
import com.google.gson.Gson;

@Component
public class AWSServiceImpl implements AWSService
{
	@Autowired
	CLIExecutor cliExecutor;

	@Override
	public String createScenario1() throws IOException, FluffyCloudException, InterruptedException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		CreateScenario1Response createScenario1Response = new CreateScenario1Response();

		/* 1. Create a VPC instance */
		String createVPCResponseJSON = cliExecutor.performAction(Action.CREATEVPC, paramsToUdate);
		CreateVPCResponse createVPCResponse = gson.fromJson(createVPCResponseJSON, CreateVPCResponse.class);
		createScenario1Response.setCreateVPCResponse(createVPCResponse);

		/* 2. Create a Security Group */
		paramsToUdate.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		String sgName = "TestSG-" + System.currentTimeMillis();
		paramsToUdate.put("group-name", sgName);
		String jsonResponse = cliExecutor.performAction(Action.CREATESECURITYGROUP, paramsToUdate);
		CreateSecurityGroupResponse createSecurityGroupResponse = gson.fromJson(jsonResponse,
				CreateSecurityGroupResponse.class);
		createScenario1Response.setCreateSecurityGroupResponse(createSecurityGroupResponse);

		/* 3. Add Inbound Rules */
		paramsToUdate.clear();
		paramsToUdate.put("group-id", createSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

		/* 4. Add OutBound Rules */
		paramsToUdate.clear();
		paramsToUdate.put("group-id", createSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPEGRESS, paramsToUdate);

		/* 5. Get ingress/egress rules info */
		paramsToUdate.clear();
		paramsToUdate.put("group-ids", createSecurityGroupResponse.getGroupId());
		String describeSGResponseJSON = cliExecutor.performAction(Action.DESCRIBESECURITYGROUPS, paramsToUdate);
		DescribeSecurityGroupResponse describeSGesponse = gson.fromJson(describeSGResponseJSON,
				DescribeSecurityGroupResponse.class);
		createScenario1Response.setDescribeSecurityGroupResponse(describeSGesponse);

		/* 6. Create subnet */
		paramsToUdate.clear();
		paramsToUdate.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		String createSubnetResponseJSON = cliExecutor.performAction(Action.CREATESUBNET, paramsToUdate);
		CreateSubnetResponse createSubnetResponse = gson.fromJson(createSubnetResponseJSON, CreateSubnetResponse.class);
		createScenario1Response.setCreateSubnetResponse(createSubnetResponse);

		/* 7. Run instance with configured or default AMI */
		paramsToUdate.clear();
		paramsToUdate.put("instance-type", InstanceTypes.t1MICRO.getValue());
		paramsToUdate.put("subnet-id", createSubnetResponse.getSubnet().getSubnetId());
		paramsToUdate.put("security-group-ids", createSecurityGroupResponse.getGroupId());
		String runInstanceResponseJSON = cliExecutor.performAction(Action.RUNINSTANCES, paramsToUdate);
		RunInstanceResponse runInstanceResponse = gson.fromJson(runInstanceResponseJSON, RunInstanceResponse.class);
		createScenario1Response.setRunInstanceResponse(runInstanceResponse);

		/* 8. Check instance state before proceeding */
		cliExecutor.checkInstanceState(paramsToUdate, gson, runInstanceResponse);

		/* 9. Create Internet Gateway */
		paramsToUdate.clear();
		String createInternetGatewayResponseJson = cliExecutor.performAction(Action.CREATEINTERNETGATEWAY,
				paramsToUdate);
		CreateInternetGatewayResponse createInternetGatewayResponse = gson.fromJson(createInternetGatewayResponseJson,
				CreateInternetGatewayResponse.class);
		createScenario1Response.setCreateInternetGatewayResponse(createInternetGatewayResponse);

		/* 10. Attach Internet Gateway to VPC */
		paramsToUdate.clear();
		paramsToUdate.put("internet-gateway-id", createInternetGatewayResponse.getInternetGateway()
				.getInternetGatewayId());
		paramsToUdate.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		String attachInternetGatewayResponseJson = cliExecutor.performAction(Action.ATTACHINTERNETGATEWAY,
				paramsToUdate);
		ResponseFlag attachInternetGatewayResponse = gson.fromJson(attachInternetGatewayResponseJson,
				ResponseFlag.class);
		createScenario1Response.setInternetGatewayAttached(attachInternetGatewayResponse);

		/* 11. Create elastic IP address */
		paramsToUdate.clear();
		String allocateAddressResponseJSON = cliExecutor.performAction(Action.ALLOCATEADDRESS, paramsToUdate);
		AllocateAddressReponse allocateAddressReponse = gson.fromJson(allocateAddressResponseJSON,
				AllocateAddressReponse.class);
		createScenario1Response.setAllocateAddressReponse(allocateAddressReponse);

		/* 12. Associate elastic IP address to instance */
		paramsToUdate.clear();
		String instanceId = runInstanceResponse.getInstances().get(0).getInstanceId();
		paramsToUdate.put("instance-id", instanceId);
		paramsToUdate.put("allocation-id", allocateAddressReponse.getAllocationID());
		String associateAddressResponseJSON = cliExecutor.performAction(Action.ASSOCIATEADDRESS, paramsToUdate);
		ResponseFlag associateAddressResponse = gson.fromJson(associateAddressResponseJSON, ResponseFlag.class);
		createScenario1Response.setAssociateAddressResponse(associateAddressResponse);

		return gson.toJson(createScenario1Response);
	}

	@Override
	public String createScenario2() throws IOException, FluffyCloudException, InterruptedException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();

		/* 1. Create a VPC instance */
		paramsToUdate.clear();
		paramsToUdate.put("cidr-block", "10.0.0.0/16");
		String createVPCResponseJSON = cliExecutor.performAction(Action.CREATEVPC, paramsToUdate);
		CreateVPCResponse createVPCResponse = gson.fromJson(createVPCResponseJSON, CreateVPCResponse.class);

		/* 2. Create public subnet */
		paramsToUdate.clear();
		paramsToUdate.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		paramsToUdate.put("cidr-block", "10.0.0.0/24");
		String createPublicSubnetResponseJSON = cliExecutor.performAction(Action.CREATESUBNET, paramsToUdate);
		CreateSubnetResponse createPublicSubnetResponse = gson.fromJson(createPublicSubnetResponseJSON,
				CreateSubnetResponse.class);

		/* Create private subnet */
		paramsToUdate.clear();
		paramsToUdate.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		paramsToUdate.put("cidr-block", "10.0.1.0/24");
		String createPrivateSubnetResponseJSON = cliExecutor.performAction(Action.CREATESUBNET, paramsToUdate);
		CreateSubnetResponse createPrivateSubnetResponse = gson.fromJson(createPrivateSubnetResponseJSON,
				CreateSubnetResponse.class);

		/* 3. Create Security Groups */
				

		/* --Webserver SG-- */
		paramsToUdate.clear();
		paramsToUdate.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		String sgName = "WebserverSG-" + System.currentTimeMillis();
		paramsToUdate.put("group-name", sgName);
		String jsonWebSGResponse = cliExecutor.performAction(Action.CREATESECURITYGROUP, paramsToUdate);
		CreateSecurityGroupResponse createWebSecurityGroupResponse = gson.fromJson(jsonWebSGResponse,
				CreateSecurityGroupResponse.class);

		/* Add Inbound Rules */
		paramsToUdate.clear();
		paramsToUdate.put("cidr", "0.0.0.0/0");
		paramsToUdate.put("port", "80");
		paramsToUdate.put("protocol", "tcp");
		paramsToUdate.put("group-id", createWebSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate); 
		
		paramsToUdate.clear();
		paramsToUdate.put("cidr", "0.0.0.0/0");
		paramsToUdate.put("port", "443");
		paramsToUdate.put("protocol", "tcp");
		paramsToUdate.put("group-id", createWebSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);
		
		paramsToUdate.clear();
		paramsToUdate.put("cidr", "10.0.0.0/24");
		paramsToUdate.put("port", "22");
		paramsToUdate.put("protocol", "tcp");
		paramsToUdate.put("group-id", createWebSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);
		
		paramsToUdate.clear();
		paramsToUdate.put("cidr", "10.0.0.0/24");
		paramsToUdate.put("port", "3389");
		paramsToUdate.put("protocol", "tcp");
		paramsToUdate.put("group-id", createWebSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

		/* Add OutBound Rules */
		paramsToUdate.clear();
		paramsToUdate.put("group-id", createWebSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPEGRESS, paramsToUdate);

		/* Get ingress/egress rules info */
		paramsToUdate.clear();
		paramsToUdate.put("group-ids", createWebSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.DESCRIBESECURITYGROUPS, paramsToUdate);

		/* --NAT server SG-- */
		paramsToUdate.clear();
		paramsToUdate.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		sgName = "NatserverSG-" + System.currentTimeMillis();
		paramsToUdate.put("group-name", sgName);
		String jsonNatSGResponse = cliExecutor.performAction(Action.CREATESECURITYGROUP, paramsToUdate);
		CreateSecurityGroupResponse createNatSecurityGroupResponse = gson.fromJson(jsonNatSGResponse,
				CreateSecurityGroupResponse.class);

		/* Add Inbound Rules */
		paramsToUdate.clear();
		paramsToUdate.put("cidr", "10.0.1.0/24");
		paramsToUdate.put("port", "80");
		paramsToUdate.put("protocol", "tcp");
		paramsToUdate.put("group-id", createNatSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);
		
		paramsToUdate.clear();
		paramsToUdate.put("cidr", "10.0.1.0/24");
		paramsToUdate.put("port", "443");
		paramsToUdate.put("protocol", "tcp");
		paramsToUdate.put("group-id", createNatSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

		paramsToUdate.clear();
		paramsToUdate.put("cidr", "203.0.113.0/24");
		paramsToUdate.put("port", "443");
		paramsToUdate.put("protocol", "tcp");
		paramsToUdate.put("group-id", createNatSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

		
		//TODO check for error
		/* Add OutBound Rules */
		paramsToUdate.clear();
		paramsToUdate.put("cidr", "0.0.0.0/0");
		paramsToUdate.put("port", "443");
		paramsToUdate.put("protocol", "tcp");
		paramsToUdate.put("group-id", createNatSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPEGRESS, paramsToUdate);
		
		paramsToUdate.clear();
		paramsToUdate.put("cidr", "0.0.0.0/0");
		paramsToUdate.put("port", "443");
		paramsToUdate.put("protocol", "tcp");
		paramsToUdate.put("group-id", createNatSecurityGroupResponse.getGroupId());
		//cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPEGRESS, paramsToUdate);

		/* Get ingress/egress rules info */
		paramsToUdate.clear();
		paramsToUdate.put("group-ids", createNatSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.DESCRIBESECURITYGROUPS, paramsToUdate);

		/* --DB server SG-- */
		paramsToUdate.clear();
		paramsToUdate.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		sgName = "DBserverSG-" + System.currentTimeMillis();
		paramsToUdate.put("group-name", sgName);
		String jsonDBSGResponse = cliExecutor.performAction(Action.CREATESECURITYGROUP, paramsToUdate);
		CreateSecurityGroupResponse createDBSecurityGroupResponse = gson.fromJson(jsonDBSGResponse,
				CreateSecurityGroupResponse.class);

		/* Add Inbound Rules */
		paramsToUdate.clear();
		paramsToUdate.put("group-id", createDBSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS, paramsToUdate);

		/* Add OutBound Rules */
		paramsToUdate.clear();
		paramsToUdate.put("group-id", createDBSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPEGRESS, paramsToUdate);

		/* Get ingress/egress rules info */
		paramsToUdate.clear();
		paramsToUdate.put("group-ids", createDBSecurityGroupResponse.getGroupId());
		cliExecutor.performAction(Action.DESCRIBESECURITYGROUPS, paramsToUdate);

		/* 5. Create Internet Gateway */
		paramsToUdate.clear();
		String createInternetGatewayResponseJson = cliExecutor.performAction(Action.CREATEINTERNETGATEWAY,
				paramsToUdate);
		CreateInternetGatewayResponse createInternetGatewayResponse = gson.fromJson(createInternetGatewayResponseJson,
				CreateInternetGatewayResponse.class);

		/* 6. Attach Internet Gateway to VPC */
		paramsToUdate.clear();
		paramsToUdate.put("internet-gateway-id", createInternetGatewayResponse.getInternetGateway()
				.getInternetGatewayId());
		paramsToUdate.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		cliExecutor.performAction(Action.ATTACHINTERNETGATEWAY, paramsToUdate);

		/* 7. Create custom Route Table */
		paramsToUdate.clear();
		paramsToUdate.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		String createRouteTableResponseJSON = cliExecutor.performAction(Action.CREATEROUTETABLE, paramsToUdate);
		CreateRouteTableResponse createRouteTableResponse = gson.fromJson(createRouteTableResponseJSON,
				CreateRouteTableResponse.class);

		/* Add Route */
		paramsToUdate.clear();
		paramsToUdate.put("route-table-id", createRouteTableResponse.getRouteTable().getRouteTableId());
		paramsToUdate.put("gateway-id", createInternetGatewayResponse.getInternetGateway().getInternetGatewayId());
		paramsToUdate.put("destination-cidr-block", "0.0.0.0/0");
		cliExecutor.performAction(Action.CREATEROUTE, paramsToUdate);

		/* 7. Run instance with configured or default AMI */
		/*--Launch web server instance in public subnet--*/
		paramsToUdate.clear();
		paramsToUdate.put("instance-type", InstanceTypes.t1MICRO.getValue());
		paramsToUdate.put("subnet-id", createPublicSubnetResponse.getSubnet().getSubnetId());
		paramsToUdate.put("security-group-ids", createWebSecurityGroupResponse.getGroupId());
		String runWebserverInstanceResponseJSON = cliExecutor.performAction(Action.RUNINSTANCES, paramsToUdate);
		RunInstanceResponse runWebserverInstanceResponse = gson.fromJson(runWebserverInstanceResponseJSON,
				RunInstanceResponse.class);

		/* Check instance state before proceeding */
		cliExecutor.checkInstanceState(paramsToUdate, gson, runWebserverInstanceResponse);

		/*--Launch NAT server instance in public subnet--*/
		paramsToUdate.clear();
		paramsToUdate.put("instance-type", InstanceTypes.t1MICRO.getValue());
		paramsToUdate.put("subnet-id", createPublicSubnetResponse.getSubnet().getSubnetId());
		paramsToUdate.put("security-group-ids", createNatSecurityGroupResponse.getGroupId());
		String runNatserverInstanceResponseJSON = cliExecutor.performAction(Action.RUNINSTANCES, paramsToUdate);
		RunInstanceResponse runNatserverInstanceResponse = gson.fromJson(runNatserverInstanceResponseJSON,
				RunInstanceResponse.class);

		/* Check instance state before proceeding */
		cliExecutor.checkInstanceState(paramsToUdate, gson, runNatserverInstanceResponse);

		/*--Launch DB server instance in private subnet--*/
		paramsToUdate.clear();
		paramsToUdate.put("instance-type", InstanceTypes.t1MICRO.getValue());
		paramsToUdate.put("subnet-id", createPrivateSubnetResponse.getSubnet().getSubnetId());
		paramsToUdate.put("security-group-ids", createDBSecurityGroupResponse.getGroupId());
		String runDBserverInstanceResponseJSON = cliExecutor.performAction(Action.RUNINSTANCES, paramsToUdate);
		RunInstanceResponse runDBserverInstanceResponse = gson.fromJson(runDBserverInstanceResponseJSON,
				RunInstanceResponse.class);

		/* Check instance state before proceeding */
		cliExecutor.checkInstanceState(paramsToUdate, gson, runDBserverInstanceResponse);

		/* 8. Add Rule to Main Route Table */
		/*--Get Main Route Table--*/
		paramsToUdate.clear();
		paramsToUdate.put("route-table-id", createRouteTableResponse.getRouteTable().getRouteTableId());
		List<Filter> filters = new ArrayList<Filter>();
		List<String> values = new ArrayList<String>();
		Filter vpcFilter = new Filter();
		values.add(createVPCResponse.getVpc().getVpcId());
		vpcFilter.setName("vpc-id");
		vpcFilter.setValues(values);
		filters.add(vpcFilter);
		String describeRouteTableResponseJson = cliExecutor.performAction(Action.DESCRIBEROUTETABLES, paramsToUdate,
				filters);
		DescribeRouteTableResponse describeRouteTableResponse = gson.fromJson(describeRouteTableResponseJson,
				DescribeRouteTableResponse.class);

		String mainRouteTableId = getMainRouteTableId(describeRouteTableResponse);
		/*--Add rule for Nat instance--*/
		paramsToUdate.clear();
		paramsToUdate.put("route-table-id", mainRouteTableId);
		paramsToUdate.put("instance-id", runNatserverInstanceResponse.getInstances().get(0).getInstanceId());
		paramsToUdate.put("destination-cidr-block", "0.0.0.0/0");
		cliExecutor.performAction(Action.CREATEROUTE, paramsToUdate);
		getMainRouteTableId(describeRouteTableResponse);

		/* 9. Create Elastic IPs to Public instaces Web and Nat */
		/* Create elastic IP address for Web Instance */
		paramsToUdate.clear();
		String allocateWebInstanceAddressResponseJSON = cliExecutor
				.performAction(Action.ALLOCATEADDRESS, paramsToUdate);
		AllocateAddressReponse allocateWebInstanceAddressReponse = gson.fromJson(
				allocateWebInstanceAddressResponseJSON, AllocateAddressReponse.class);

		/* Associate elastic IP address to Web instance */
		paramsToUdate.clear();
		String webInstanceId = runWebserverInstanceResponse.getInstances().get(0).getInstanceId();
		paramsToUdate.put("instance-id", webInstanceId);
		paramsToUdate.put("allocation-id", allocateWebInstanceAddressReponse.getAllocationID());
		String associateAddressResponseJSON = cliExecutor.performAction(Action.ASSOCIATEADDRESS, paramsToUdate);
		ResponseFlag associateAddressResponse = gson.fromJson(associateAddressResponseJSON, ResponseFlag.class);

		/* Create elastic IP address for Nat Instance */
		paramsToUdate.clear();
		String allocateNatInstanceAddressResponseJSON = cliExecutor
				.performAction(Action.ALLOCATEADDRESS, paramsToUdate);
		AllocateAddressReponse allocateNatInstanceAddressReponse = gson.fromJson(
				allocateNatInstanceAddressResponseJSON, AllocateAddressReponse.class);

		/* Associate elastic IP address to Nat instance */
		paramsToUdate.clear();
		String natInstanceId = runWebserverInstanceResponse.getInstances().get(0).getInstanceId();
		paramsToUdate.put("instance-id", natInstanceId);
		paramsToUdate.put("allocation-id", allocateWebInstanceAddressReponse.getAllocationID());
		String associateNatInstanceAddressResponseJSON = cliExecutor.performAction(Action.ASSOCIATEADDRESS,
				paramsToUdate);
		ResponseFlag associateNatInstanceAddressResponse = gson.fromJson(associateNatInstanceAddressResponseJSON,
				ResponseFlag.class);

		// TODO Generate Scenario 2 JSON response
		return "Done";
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
}
