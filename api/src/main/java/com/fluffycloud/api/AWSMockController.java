package com.fluffycloud.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fluffycloud.aws.cli.utils.CLIExecutor;
import com.fluffycloud.aws.cli.utils.PropertyReader;
import com.fluffycloud.aws.constants.Action;
import com.fluffycloud.aws.constants.InstanceTypes;
import com.fluffycloud.aws.response.entity.AllocateAddressReponse;
import com.fluffycloud.aws.response.entity.CreateScenario1Response;
import com.fluffycloud.aws.response.entity.CreateSecurityGroupResponse;
import com.fluffycloud.aws.response.entity.CreateSubnetResponse;
import com.fluffycloud.aws.response.entity.CreateVPCResponse;
import com.fluffycloud.aws.response.entity.DescribeInstanceStatusResponse;
import com.fluffycloud.aws.response.entity.ResponseFlag;
import com.fluffycloud.aws.response.entity.RunInstanceResponse;
import com.fluffycloud.exceptions.FluffyCloudException;
import com.google.gson.Gson;

@RestController
public class AWSMockController
{

	@Autowired
	PropertyReader propertyReader;

	@Autowired
	CLIExecutor cliExecutor;

	@RequestMapping("/aws")
	public String mockAWSCloud(HttpServletRequest request)
	{
		Map<String, String[]> params = request.getParameterMap();

		/*
		 * TODO Request have the params present in command For eg: CLI command:
		 * aws ec2 describe-images --image-ids ami-xxxxx --endpoint-url
		 * http://127.0.1.1:8080/aws
		 * 
		 * Params in request are: ImageId.1 : ami-xxxxx Action : DescribeImages
		 * SignatureMethod : HmacSHA256 AWSAccessKeyId : AKIAIDGQBXU3XTSPTPOA
		 * SignatureVersion : 2 Version : 2013-10-15 Signature :
		 * olDIF1AjB06H6E7SlThtJC/6ojhWgBUtOmyUGZD+sqo= Timestamp :
		 * 2014-09-10T10:26:20.985215
		 * 
		 * 
		 * So the command can be formed using the params and can be used with
		 * CLIExecutor to process it on AWS server and get the response.
		 */
		for (String key : params.keySet())
		{
			System.out.println(key + " : " + params.get(key)[0]);
		}
		/*
		 * TODO 1. Need to set content type for the response.
		 * 
		 * 2. Using CLI use debug mode with any command then it will show the
		 * response XML which is returned by AWS APIs.
		 * 
		 * 3. When JSON returned in response with response content type
		 * application/json
		 * 
		 * String mockResponse =
		 * "{\"Images\": [{\"ImageId\": \"ami-xxxxx\",\"RootDeviceName\": \"/dev/sda1\",\"ImageType\": \"machine\",\"Description\": \"Amazon Linux AMI x86_64 PV EBS\",\"ImageOwnerAlias\": \"amazon\",\"Public\": true, \"RootDeviceType\": \"ebs\", \"State\":\"available\"}]}\""
		 * ; reponse.setContentType("application/json");
		 * 
		 * then the CLI client shows: Not well-formed(invalid token): line 1,
		 * column 0
		 * 
		 * 4. Issue is to create the XML response which can be parsed by CLI
		 * client.
		 */

		// Sample response for POC
		String mockResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<DescribeImagesResponse xmlns=\"http://ec2.amazonaws.com/doc/2013-10-15/\">\n    <requestId>fd0fa9ac-a547-4497-8263-407781bbbe57</requestId>\n    <imagesSet>\n        <item>\n            <imageId>ami-xxxxxx</imageId>\n            <imageLocation>amazon/amzn-ami-pv-2014.03.2.x86_64-ebs</imageLocation>\n            <imageState>available</imageState>\n            <imageOwnerId>137112412989</imageOwnerId>\n            <isPublic>true</isPublic>\n            <architecture>x86_64</architecture>\n            <imageType>machine</imageType>\n            <kernelId>aki-xxxxxxxxx</kernelId>\n            <imageOwnerAlias>amazon</imageOwnerAlias>\n            <name>amzn-ami-xxxx-2014.03.2.x86_64-ebs</name>\n            <description>Amazon Linux AMI x86_64 PV EBS</description>\n            <rootDeviceType>ebs</rootDeviceType>\n            <rootDeviceName>/dev/sda1</rootDeviceName>\n            <blockDeviceMapping>\n                <item>\n                    <deviceName>/dev/sda1</deviceName>\n                    <ebs>\n                        <snapshotId>snap-8347b454</snapshotId>\n                        <volumeSize>8</volumeSize>\n                        <deleteOnTermination>true</deleteOnTermination>\n                        <volumeType>standard</volumeType>\n                    </ebs>\n                </item>\n            </blockDeviceMapping>\n            <virtualizationType>paravirtual</virtualizationType>\n            <hypervisor>xen</hypervisor>\n        </item>\n    </imagesSet>\n</DescribeImagesResponse>";

		System.out.println(propertyReader.getEnv().getProperty("host"));

		return mockResponse;
	}

	@RequestMapping("/aws/create/scenario1")
	public String createScenario1(HttpServletRequest request) throws IOException, FluffyCloudException,
			InterruptedException
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
		String authorizeSGIngressResponseJSON = cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPINGRESS,
				paramsToUdate);
		ResponseFlag authorizeSecurityGroupIngressResponse = gson.fromJson(authorizeSGIngressResponseJSON,
				ResponseFlag.class);
		createScenario1Response.setAuthorizeSecurityGroupIngressResponse(authorizeSecurityGroupIngressResponse);

		// TODO add other inbound and outbound rules recommended for scenario

		/* 4. Add OutBound Rules */
		paramsToUdate.clear();
		paramsToUdate.put("group-id", createSecurityGroupResponse.getGroupId());
		String authorizeSGEgressResponseJSON = cliExecutor.performAction(Action.AUTHORIZESECURITYGROUPEGRESS,
				paramsToUdate);
		ResponseFlag authorizeSecurityGroupEgressResponse = gson.fromJson(authorizeSGEgressResponseJSON,
				ResponseFlag.class);
		createScenario1Response.setAuthorizeSecurityGroupEgressResponse(authorizeSecurityGroupEgressResponse);

		/* 5. Create subnet */
		paramsToUdate.clear();
		paramsToUdate.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		String createSubnetResponseJSON = cliExecutor.performAction(Action.CREATESUBNET, paramsToUdate);
		CreateSubnetResponse createSubnetResponse = gson.fromJson(createSubnetResponseJSON, CreateSubnetResponse.class);
		createScenario1Response.setCreateSubnetResponse(createSubnetResponse);

		/* 6. Run instance with configured or default AMI */
		paramsToUdate.clear();
		paramsToUdate.put("instance-type", InstanceTypes.t1MICRO.getValue());
		paramsToUdate.put("subnet-id", createSubnetResponse.getSubnet().getSubnetId());
		paramsToUdate.put("security-group-ids", createSecurityGroupResponse.getGroupId());
		String runInstanceResponseJSON = cliExecutor.performAction(Action.RUNINSTANCES, paramsToUdate);
		RunInstanceResponse runInstanceResponse = gson.fromJson(runInstanceResponseJSON, RunInstanceResponse.class);
		createScenario1Response.setRunInstanceResponse(runInstanceResponse);

		/* Check instance state before proceeding */
		Map<String, String> test = new HashMap<String, String>();
		test.put("instance-id", runInstanceResponse.getInstances().get(0).getInstanceId());
		String command = cliExecutor.performAction(Action.DESCRIBEINSTANCESTATUS, test);
		DescribeInstanceStatusResponse response = gson.fromJson(command, DescribeInstanceStatusResponse.class);

		while (!response.getInstanceStatuses().get(0).getInstanceState().getName().equalsIgnoreCase("running"))
		{
			Thread.sleep(2000);
			command = cliExecutor.performAction(Action.DESCRIBEINSTANCESTATUS, test);
			response = gson.fromJson(command, DescribeInstanceStatusResponse.class);
		}

		/* 7. Create elastic IP address */
		paramsToUdate.clear();
		String allocateAddressResponseJSON = cliExecutor.performAction(Action.ALLOCATEADDRESS, paramsToUdate);
		AllocateAddressReponse allocateAddressReponse = gson.fromJson(allocateAddressResponseJSON,
				AllocateAddressReponse.class);
		createScenario1Response.setAllocateAddressReponse(allocateAddressReponse);

		/* 8. Associate elastic IP address to instance */
		paramsToUdate.clear();
		String instanceId = runInstanceResponse.getInstances().get(0).getInstanceId();
		paramsToUdate.put("instance-id", instanceId);
		paramsToUdate.put("allocation-id", allocateAddressReponse.getAllocationID());
		String associateAddressResponseJSON = cliExecutor.performAction(Action.ASSOCIATEADDRESS, paramsToUdate);
		ResponseFlag associateAddressResponse = gson.fromJson(associateAddressResponseJSON, ResponseFlag.class);
		createScenario1Response.setAssociateAddressResponse(associateAddressResponse);

		return gson.toJson(createScenario1Response);
	}
}