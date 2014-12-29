package com.fluffycloud.api.cloud.request.entity;

public class DescribeStackResourcesRequest
{
	private String stackName;

	private String logicalResourceId;

	private String physicalResourceId;

	public String getPhysicalResourceId()
	{
		return physicalResourceId;
	}

	public void setPhysicalResourceId(String physicalResourceId)
	{
		this.physicalResourceId = physicalResourceId;
	}

	public String getLogicalResourceId()
	{
		return logicalResourceId;
	}

	public String getStackName()
	{
		return stackName;
	}

	public void setStackName(String stackName)
	{
		this.stackName = stackName;
	}

	public void setLogicalResourceId(String logicalResourceId)
	{
		this.logicalResourceId = logicalResourceId;
	}
}
