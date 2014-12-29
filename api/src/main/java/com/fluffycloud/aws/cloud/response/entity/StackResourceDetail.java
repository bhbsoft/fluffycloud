package com.fluffycloud.aws.cloud.response.entity;

import com.google.gson.annotations.SerializedName;

public class StackResourceDetail
{
	@SerializedName("PhysicalResourceId")
	private String physicalResourceId;

	@SerializedName("LogicalResourceId")
	private String logicalResourceId;

	@SerializedName("ResourceStatus")
	private String resourceStatus;

	@SerializedName("ResourceType")
	private String resourceType;

	@SerializedName("LastUpdatedTimestamp")
	private String lastUpdatedTimestamp;

	@SerializedName("StackName")
	private String stackName;

	@SerializedName("StackId")
	private String stackId;

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

	public void setLogicalResourceId(String logicalResourceId)
	{
		this.logicalResourceId = logicalResourceId;
	}

	public String getResourceStatus()
	{
		return resourceStatus;
	}

	public void setResourceStatus(String resourceStatus)
	{
		this.resourceStatus = resourceStatus;
	}

	public String getResourceType()
	{
		return resourceType;
	}

	public void setResourceType(String resourceType)
	{
		this.resourceType = resourceType;
	}

	public String getLastUpdatedTimestamp()
	{
		return lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(String lastUpdatedTimestamp)
	{
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}
}
