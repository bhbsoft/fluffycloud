package com.fluffycloud.command;

import static com.fluffycloud.aws.constants.InstanceTypes.t1MICRO;
import static com.fluffycloud.aws.constants.Provider.AWS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fluffycloud.aws.constants.InstanceTypes;
import com.fluffycloud.aws.constants.Provider;
import com.fluffycloud.aws.entity.Command;
import com.fluffycloud.aws.entity.Parameters;
import com.fluffycloud.aws.entity.SecurityGroup;
import com.google.gson.Gson;

public class GenerateCommandJsonTest
{

	@Test
	public void launchAWSInstanceJSON() throws IOException
	{

		Command command = new Command();
		command.setProvider(AWS);
		command.setCommand("ec2");
		command.setAction("run-instances");

		List<String> options = new ArrayList<String>();
		command.setOptions(options);

		Parameters parameters = new Parameters();
		List<SecurityGroup> sgList = new ArrayList<SecurityGroup>();
		SecurityGroup group1 = new SecurityGroup();
		group1.setGroupId("sg1-xxxxxx");
		SecurityGroup group2 = new SecurityGroup();
		group2.setGroupId("sg2-xxxx");
		sgList.add(group1);
		sgList.add(group2);
		parameters.setSecurityGroups(sgList);

		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("image-id", "ami-xxxxxx");
		parameterMap.put("key-name", "keyPairName");
		parameterMap.put("user-data", "");
		parameterMap.put("placement ", "");
		parameterMap.put("kernel-id", "aki-xxxx");
		parameterMap.put("ramdisk-id", "");
		parameterMap.put("block-device-mappings", "");
		parameterMap.put("monitoring", "basic");
		parameterMap.put("subnet-id", "subnet-xxxxx");
		parameterMap.put("instance-initiated-shutdown-behavior", "");
		parameterMap.put("private-ip-address", "");
		parameterMap.put("client-token", "");
		parameterMap.put("additional-info", "");
		parameterMap.put("network-interfaces", "eth0");
		parameterMap.put("iam-instance-profile", "");
		parameterMap.put("ebs-optimized", "False");
		parameterMap.put("count", "1");
		parameterMap.put("secondary-private-ip-addresses", "");
		parameterMap.put("secondary-private-ip-address-count", "");
		parameterMap.put("instance-type", t1MICRO.getValue());
		parameterMap.put("key-name", "awslinux");
		parameterMap.put("subnet-id", "subnet-aed7c186");

		parameters.setParameterMap(parameterMap);

		List<String> flags = new ArrayList<String>();
		flags.add("dry-run");
		flags.add("disable-api-termination");
		flags.add("associate-public-ip-address");
		parameters.setFlags(flags);

		command.setParameters(parameters);

		Gson gson = new Gson();
		String json = gson.toJson(command);

		File file = new File("json" + File.separator + "aws-runinstances.json");
		file.createNewFile();

		FileWriter writer = new FileWriter(file);
		writer.write(json);
		writer.close();

		Command commandFromJSon = gson.fromJson(json, Command.class);
		System.out.println(commandFromJSon);

	}

}
