package com.fluffycloud.aws.entity;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.List;
import java.util.Map;

import com.fluffycloud.aws.constants.Provider;

public class Command
{

	private Provider provider;
	private String command;
	private String subCommand;
	private String action;
	private List<String> options;
	private Parameters parameters;
	private Filter filters;

	public Parameters getParameters()
	{
		return parameters;
	}

	public void setParameters(Parameters parameters)
	{
		this.parameters = parameters;
	}

	public Filter getFilters()
	{
		return filters;
	}

	public void setFilters(Filter filters)
	{
		this.filters = filters;
	}

	public Provider getProvider()
	{
		return provider;
	}

	public void setProvider(Provider provider)
	{
		this.provider = provider;
	}

	public List<String> getOptions()
	{
		return options;
	}

	public void setOptions(List<String> options)
	{
		this.options = options;
	}

	public String getCommand()
	{
		return command;
	}

	public void setCommand(String command)
	{
		this.command = command;
	}

	public String getSubCommand()
	{
		return subCommand;
	}

	public void setSubCommand(String subCommand)
	{
		this.subCommand = subCommand;
	}

	public String getAction()
	{
		return action;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	@Override
	public String toString()
	{
		StringBuilder command = new StringBuilder(this.provider.getValue() + " " + this.command + " " + this.action
				+ " ");

		for (String option : this.options)
		{
			command.append(option + " ");
		}

		if (this.parameters == null)
		{
			return command.toString();
		}

		Map<String, String> paramMap = this.parameters.getParameterMap();
		if (paramMap != null && paramMap.size() > 0)
		{
			for (String key : paramMap.keySet())
			{
				if (paramMap.get(key) != null && !isEmpty(paramMap.get(key)))
				{
					command.append("--" + key + " " + paramMap.get(key) + " ");
				}
			}
		}

		List<SecurityGroup> sList = this.parameters.getSecurityGroups();
		if (sList != null && sList.size() > 0)
		{
			command.append("--security-groups [");

			for (SecurityGroup sGroup : sList)
			{
				command.append(sGroup.getGroupName() + " ");
			}

			command.append("] ");

			command.append("--security-group-ids [");

			for (SecurityGroup sGroup : sList)
			{
				command.append(sGroup.getGroupId() + " ");
			}

			command.append("] ");
		}

		List<String> flags = this.getParameters().getFlags();
		if (flags != null && flags.size() > 0)
		{
			for (String flag : flags)
			{
				command.append("--" + flag + " ");
			}
		}

		return command.toString();
	}
}
