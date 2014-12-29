package com.fluffycloud.api.cloud.request.entity;

import javax.validation.constraints.NotNull;

public class DescribeStackResourceRequest
{
	@NotNull
	private String stackName;

	@NotNull
	private String logicalResourceId;

	public String getStackName()
	{
		return stackName;
	}

	public void setStackName(String stackName)
	{
		this.stackName = stackName;
	}

	public String getLogicalResourceId()
	{
		return logicalResourceId;
	}

	public void setLogicalResourceId(String logicalResourceId)
	{
		this.logicalResourceId = logicalResourceId;
	}
}
