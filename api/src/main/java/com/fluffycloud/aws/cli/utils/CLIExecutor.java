package com.fluffycloud.aws.cli.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fluffycloud.aws.constants.Action;
import com.fluffycloud.aws.entity.Command;
import com.fluffycloud.aws.entity.Parameters;
import com.fluffycloud.exceptions.CommandExecutionException;
import com.fluffycloud.exceptions.DefaultJsonNotFoundException;
import com.fluffycloud.exceptions.FluffyCloudException;
import com.google.gson.Gson;

@Component
public class CLIExecutor
{
	@Autowired
	ResponseValidator responseValidator;

	/**
	 * 
	 * @param action
	 * @param paramsToUdate
	 * @return
	 * @throws IOException
	 * @throws FluffyCloudException
	 */
	public String performAction(Action action, Map<String, String> paramsToUdate) throws IOException,
			FluffyCloudException
	{
		Command defaultCommand = getDefaultCommand(action);
		Parameters params = defaultCommand.getParameters();
		if (null != params)
		{
			Map<String, String> parameterMap = params.getParameterMap();
			for (String key : paramsToUdate.keySet())
			{
				parameterMap.put(key, paramsToUdate.get(key));
			}
		}
		return processCommand(defaultCommand);
	}

	/**
	 * 
	 * @param action
	 * @return
	 * @throws DefaultJsonNotFoundException
	 */
	public Command getDefaultCommand(final Action action) throws DefaultJsonNotFoundException
	{
		File file = new File("json" + File.separator + "aws" + File.separator + "ec2" + File.separator + "default"
				+ File.separator + action.getAction() + ".json");
		try (Reader reader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(reader);)
		{
			StringBuilder sbJSONCommand = new StringBuilder();
			String s = null;
			while ((s = bufferedReader.readLine()) != null)
			{
				sbJSONCommand.append(s);
				sbJSONCommand.append("\n");
			}
			Gson gson = new Gson();
			Command defaultCommand = gson.fromJson(sbJSONCommand.toString(), Command.class);
			return defaultCommand;
		}
		catch (IOException e)
		{
			throw new DefaultJsonNotFoundException(action.getAction() + ".json not found.");
		}

	}

	/**
	 * 
	 * @param commandFromJSon
	 * @return
	 * @throws FluffyCloudException
	 */
	public String processCommand(final Command commandFromJSon) throws FluffyCloudException
	{
		String command = commandFromJSon.toString();
		StringBuilder sb = new StringBuilder();
		System.out.println(command);
		String[] cliCommand = new String[]
		{ "/bin/sh", "-c", command };

		Process proc = null;
		try
		{
			proc = new ProcessBuilder(cliCommand).start();
		}
		catch (IOException e)
		{
			throw new CommandExecutionException(e.getMessage());
		}
		try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));)
		{
			String s = null;

			while ((s = stdInput.readLine()) != null)
			{
				sb.append(s);
				sb.append("\n");
			}

			if (sb.length() > 0)
			{
				return sb.toString();
			}
			else
			{
				while ((s = stdError.readLine()) != null)
				{
					sb.append(s);
					sb.append("\n");
				}

				throw new CommandExecutionException("Action: " + commandFromJSon.getAction() + ". " + sb.toString());
			}
		}
		catch (Exception e)
		{
			throw new CommandExecutionException(e.getMessage());
		}

	}
}
