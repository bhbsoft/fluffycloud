package com.fluffycloud.command;

import static com.fluffycloud.aws.constants.Provider.AWS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fluffycloud.aws.entity.Command;
import com.fluffycloud.aws.entity.Parameters;
import com.google.gson.Gson;

public class CommandProcessorTest
{

	@Test
	public void createDescribeInstanceJson() throws IOException
	{
		Command command = new Command();
		command.setProvider(AWS);
		command.setCommand("ec2");
		command.setAction("describe-instances");

		Parameters parameters = new Parameters();
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("instance-ids", "i-7c687e93");
		parameters.setParameterMap(parameterMap);

		command.setParameters(parameters);

		Gson gson = new Gson();
		String json = gson.toJson(command);

		File file = new File("json" + File.separator + "aws-describeInstances.json");
		if (!file.exists())
		{
			file.createNewFile();
		}

		FileWriter writer = new FileWriter(file);
		writer.write(json);
		writer.close();

	}

	@Test
	public void processCommand() throws IOException
	{
		File file = new File("json" + File.separator + "aws-describeInstances.json");
		Reader reader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(reader);
		StringBuilder sbJSONCommand = new StringBuilder();

		String s = null;
		while ((s = bufferedReader.readLine()) != null)
		{
			sbJSONCommand.append(s);
			sbJSONCommand.append("\n");
		}

		System.out.println(sbJSONCommand.toString());

		Gson gson = new Gson();
		Command commandFromJSon = gson.fromJson(sbJSONCommand.toString(), Command.class);
		System.out.println(commandFromJSon);

		String command = commandFromJSon.toString();
		StringBuilder sb = new StringBuilder();
		String[] commands = new String[]
		{ "/bin/sh", "-c", command };

		try
		{
			Process proc = new ProcessBuilder(commands).start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

			s = null;
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
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println(sb.toString());
	}
}
