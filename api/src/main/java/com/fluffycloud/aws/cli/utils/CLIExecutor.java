package com.fluffycloud.aws.cli.utils;

import static com.fluffycloud.aws.constants.Action.ALLOCATEADDRESS;
import static com.fluffycloud.aws.constants.Action.ASSOCIATEADDRESS;
import static com.fluffycloud.aws.constants.Action.CREATESUBNET;
import static com.fluffycloud.aws.constants.Action.RUNINSTANCES;
import static com.fluffycloud.aws.constants.InstanceTypes.t1MICRO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fluffycloud.aws.constants.Action;
import com.fluffycloud.aws.entity.Command;
import com.fluffycloud.aws.entity.Parameters;
import com.fluffycloud.aws.response.entity.AllocateAddressReponse;
import com.fluffycloud.aws.response.entity.CreateSecurityGroupResponse;
import com.fluffycloud.aws.response.entity.CreateSubnetResponse;
import com.fluffycloud.aws.response.entity.CreateVPCResponse;
import com.fluffycloud.aws.response.entity.RunInstanceResponse;
import com.google.gson.Gson;

@Component
public class CLIExecutor
{
	public static void main(String args[]) throws IOException
	{
		Gson gson = new Gson();

		/* 1. Create a VPC instance */
		Command defaultCommand = getDefaultCommand(Action.CREATEVPC);
		String createVPCResponseJSON = processCommand(defaultCommand);
		CreateVPCResponse createVPCResponse = gson.fromJson(createVPCResponseJSON, CreateVPCResponse.class);

		/* 2. Create a Security Group */
		Command defaultCommandSG = getDefaultCommand(Action.CREATESECURITYGROUP);
		Parameters sGParams = defaultCommandSG.getParameters();
		Map<String, String> sGParameterMap = sGParams.getParameterMap();
		sGParameterMap.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		String createSGJSON = processCommand(defaultCommandSG);
		System.out.println(createSGJSON);
		CreateSecurityGroupResponse createSecurityGroupResponse = gson.fromJson(createSGJSON,
				CreateSecurityGroupResponse.class);

		/* 3. Add Inbound Rules */
		Command defaultCommandSGIngress = getDefaultCommand(Action.AUTHORIZESECURITYGROUPINGRESS);
		Parameters ingressParams = defaultCommandSGIngress.getParameters();
		Map<String, String> ingressParameterMap = ingressParams.getParameterMap();
		ingressParameterMap.put("group-id", createSecurityGroupResponse.getGroupId());
		String authorizeSGIngressResponseJSON = processCommand(defaultCommandSGIngress);
		System.out.println(authorizeSGIngressResponseJSON);

		// TODO add other inbound and outbound rules recommended for scenario

		/* 4. Add OutBound Rules */
		Command defaultCommandSGEgress = getDefaultCommand(Action.AUTHORIZESECURITYGROUPEGRESS);
		Parameters egressParams = defaultCommandSGEgress.getParameters();
		Map<String, String> egressParameterMap = egressParams.getParameterMap();
		egressParameterMap.put("group-id", createSecurityGroupResponse.getGroupId());
		String authorizeSGEgressResponseJSON = processCommand(defaultCommandSGEgress);
		System.out.println(authorizeSGEgressResponseJSON);

		/* 5. Create subnet */
		Command defaultCommandCreateSubnet = getDefaultCommand(CREATESUBNET);
		Parameters createSubnetParams = defaultCommandCreateSubnet.getParameters();
		Map<String, String> createSubnetParameterMap = createSubnetParams.getParameterMap();
		createSubnetParameterMap.put("vpc-id", createVPCResponse.getVpc().getVpcId());
		String createSubnetResponseJSON = processCommand(defaultCommandCreateSubnet);
		CreateSubnetResponse createSubnetResponse = gson.fromJson(createSubnetResponseJSON, CreateSubnetResponse.class);

		/* Run instance with preconfigured or default AMI */
		Command defaultCommandRunInstance = getDefaultCommand(RUNINSTANCES);
		Parameters runInstanceParams = defaultCommandRunInstance.getParameters();
		Map<String, String> runInstanceParameterMap = runInstanceParams.getParameterMap();
		runInstanceParameterMap.put("instance-type", t1MICRO.getValue());
		runInstanceParameterMap.put("subnet-id", createSubnetResponse.getSubnet().getSubnetId());
		runInstanceParameterMap.put("security-group-ids", createSecurityGroupResponse.getGroupId());
		String runInstanceResponseJSON = processCommand(defaultCommandRunInstance);
		RunInstanceResponse runInstanceResponse = gson.fromJson(runInstanceResponseJSON, RunInstanceResponse.class);
		System.out.println(runInstanceResponseJSON);

		/* Attach instance with VPC */

		/* Create elastic IP address */
		Command defaultCommandAllocateAddress = getDefaultCommand(ALLOCATEADDRESS);
		String allocateAddressResponseJSON = processCommand(defaultCommandAllocateAddress);
		AllocateAddressReponse allocateAddressReponse = gson.fromJson(allocateAddressResponseJSON,
				AllocateAddressReponse.class);

		/* Associate elastic IP address to instance */
		Command defaultCommandAssociateAddress = getDefaultCommand(ASSOCIATEADDRESS);
		Parameters associateAddressParams = defaultCommandAssociateAddress.getParameters();
		Map<String, String> associateAddressParameterMap = associateAddressParams.getParameterMap();

		String instanceId = runInstanceResponse.getInstances().get(0).getInstanceId();

		associateAddressParameterMap.put("instance-id", instanceId);
		associateAddressParameterMap.put("public-ip", allocateAddressReponse.getPublicIp());
		String associateAddressResponseJSON = processCommand(defaultCommandAssociateAddress);

	}

	public static Command getDefaultCommand(final Action action) throws IOException
	{
		File file = new File("json" + File.separator + "aws" + File.separator + "ec2" + File.separator + "default"
				+ File.separator + action.getAction() + ".json");
		System.out.println(file.exists());
		Reader reader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(reader);
		StringBuilder sbJSONCommand = new StringBuilder();

		String s = null;
		while ((s = bufferedReader.readLine()) != null)
		{
			sbJSONCommand.append(s);
			sbJSONCommand.append("\n");
		}
		bufferedReader.close();
		Gson gson = new Gson();
		Command defaultCommand = gson.fromJson(sbJSONCommand.toString(), Command.class);
		return defaultCommand;
	}

	public static String processCommand(final Command commandFromJSon) throws IOException
	{
		String command = commandFromJSon.toString();
		StringBuilder sb = new StringBuilder();
		System.out.println(command);
		String[] cliCommand = new String[]
		{ "/bin/sh", "-c", command };

		try
		{
			Process proc = new ProcessBuilder(cliCommand).start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			String s = null;
			while ((s = stdInput.readLine()) != null)
			{
				sb.append(s);
				sb.append("\n");
			}
			while ((s = stdError.readLine()) != null)
			{
				sb.append(s);
				sb.append("\n");
			}
			stdInput.close();
			stdError.close();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return sb.toString();
	}
}
