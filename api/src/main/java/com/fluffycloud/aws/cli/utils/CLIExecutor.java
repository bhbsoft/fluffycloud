package com.fluffycloud.aws.cli.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.stereotype.Component;

import com.fluffycloud.aws.entity.Command;
import com.google.gson.Gson;

@Component
public class CLIExecutor
{

	public Command getDefaultCommand(final String action) throws IOException
	{
		File file = new File("json" + File.separator + "aws" + File.separator + "ec2" + File.separator + "default"
				+ File.separator + action + ".json");
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

	public String processCommand(final Command commandFromJSon) throws IOException
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
