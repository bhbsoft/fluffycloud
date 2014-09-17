package com.fluffycloud.aws.entity;

import java.util.List;
import java.util.Map;

public class Parameters
{

	private List<SecurityGroup> securityGroups;
	private Map<String, String> parameterMap;
	private List<String> flags;

	public List<SecurityGroup> getSecurityGroups()
	{
		return securityGroups;
	}

	public void setSecurityGroups(List<SecurityGroup> securityGroups)
	{
		this.securityGroups = securityGroups;
	}

	public Map<String, String> getParameterMap()
	{
		return parameterMap;
	}

	public void setParameterMap(Map<String, String> parameterMap)
	{
		this.parameterMap = parameterMap;
	}

	public List<String> getFlags()
	{
		return flags;
	}

	public void setFlags(List<String> flags)
	{
		this.flags = flags;
	}

}
