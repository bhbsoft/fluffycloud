package com.fluffycloud.aws.cli.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import com.fluffycloud.aws.constants.Action;
import com.fluffycloud.aws.constants.AppParams;
import com.fluffycloud.aws.constants.InstanceState;
import com.fluffycloud.aws.entity.Command;
import com.fluffycloud.aws.entity.Filter;
import com.fluffycloud.aws.entity.Parameters;
import com.fluffycloud.aws.response.entity.DescribeInstancesResponse;
import com.fluffycloud.exceptions.CommandExecutionException;
import com.fluffycloud.exceptions.CommandNotFoundException;
import com.fluffycloud.exceptions.DefaultJsonNotFoundException;
import com.fluffycloud.exceptions.FluffyCloudException;
import com.google.gson.Gson;

@Component
public class CLIExecutor
{
	final static Logger logger = LoggerFactory.getLogger(CLIExecutor.class);

	@Autowired
	ResponseValidator responseValidator;

	@Autowired
	MongoOperations mongoOperations;

	/**
	 * 
	 * @param action
	 * @param paramsToUdate
	 * @param filters
	 * @return
	 * @throws IOException
	 * @throws FluffyCloudException
	 */
	public String performAction(Action action, Map<String, String> paramsToUdate, List<Filter> filters)
			throws FluffyCloudException
	{
		Command defaultCommand = getDefaultCommand(action);
		defaultCommand.setFilters(filters);
		return updateParameters(paramsToUdate, defaultCommand);
	}

	/**
	 * Method to default command for the action.
	 * 
	 * @param action
	 * @param paramsToUdate
	 * @return
	 * @throws IOException
	 * @throws FluffyCloudException
	 * @throws CommandNotFoundException
	 */
	public String performAction(Action action, Map<String, String> paramsToUdate) throws IOException,
			FluffyCloudException, CommandNotFoundException
	{
		/* Get Default Command using JSON present on remote Database. */
		Command defaultCommand = mongoOperations.findById(action.getAction(), Command.class);

		if (null == defaultCommand)
		{
			logger.debug("Default command not found in DB.");
			throw new CommandNotFoundException("Default Command not found for action: " + action.getAction());
		}
		logger.debug("Default command fetched from DB.");

		/*
		 * ------Get Default Command using JSON files present locally.------
		 * Command defaultCommand = getDefaultCommand(action);
		 */
		return updateParameters(paramsToUdate, defaultCommand);
	}

	/**
	 * 
	 * @param paramsToUdate
	 * @param defaultCommand
	 * @return
	 * @throws FluffyCloudException
	 */
	private String updateParameters(Map<String, String> paramsToUdate, Command defaultCommand)
			throws FluffyCloudException
	{
		Parameters params = defaultCommand.getParameters();
		if (null != params)
		{
			Map<String, String> parameterMap = params.getParameterMap();
			for (String key : paramsToUdate.keySet())
			{
				parameterMap.put(key, paramsToUdate.get(key));
			}
		}
		else
		{
			params = new Parameters();
			Map<String, String> parameterMap = new HashMap<String, String>();
			for (String key : paramsToUdate.keySet())
			{
				parameterMap.put(key, paramsToUdate.get(key));
			}
			params.setParameterMap(parameterMap);
			defaultCommand.setParameters(params);
		}
		logger.debug("Default command updated with request parameters.");
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
		String[] cliCommand = new String[]
		{ "/bin/sh", "-c", command };
		logger.info("Command execution started. " + command.toString());
		logger.debug("Command execution started. " + command.toString());
		Process proc = null;
		try
		{
			proc = new ProcessBuilder(cliCommand).start();
		}
		catch (IOException e)
		{
			logger.debug("Error occured while executing command " + e.getMessage());
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
				logger.info("Command executed.");
				return sb.toString();
			}
			else
			{
				while ((s = stdError.readLine()) != null)
				{
					sb.append(s);
					sb.append("\n");
				}

				logger.debug("Action: " + commandFromJSon.getAction() + ". " + sb.toString());
				throw new CommandExecutionException("Action: " + commandFromJSon.getAction() + ". " + sb.toString());
			}
		}
		catch (Exception e)
		{
			logger.error("Exception occured while executing command: " + e.getMessage());
			throw new CommandExecutionException(e.getMessage());
		}

	}

	public void checkInstanceState(final Map<String, String> paramsToUdate, final Gson gson, final String instanceId)
			throws IOException, FluffyCloudException
	{
		final String state = getInstanceState(paramsToUdate, gson, instanceId);
		if (InstanceState.isValidState(state))
		{
			if (InstanceState.isRunning(state) || InstanceState.isStopped(state))
			{
				logger.info("Valid Instance state: " + state);
				return;
			}
			else
			{
				logger.info("Instance not running. Instance state: " + state);
				checkInstanceState(paramsToUdate, gson, instanceId);
			}
		}

	}

	public String getInstanceState(final Map<String, String> paramsToUdate, final Gson gson, final String instanceId)
			throws FluffyCloudException
	{
		paramsToUdate.clear();
		paramsToUdate.put(AppParams.INSTANCEID.getValue(), instanceId);
		String describeInstanceCommand = performAction(Action.DESCRIBEINSTANCES, paramsToUdate, null);
		DescribeInstancesResponse describeInstanceResponse = gson.fromJson(describeInstanceCommand,
				DescribeInstancesResponse.class);
		return describeInstanceResponse.getReservations().get(0).getInstances().get(0).getState().getName();
	}
}
