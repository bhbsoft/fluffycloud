package com.fluffycloud.aws.response.entity;

import com.google.gson.annotations.SerializedName;

public class StackEvent
{
	@SerializedName("StackName")
	private String stackName;

	@SerializedName("EventId")
	private String eventId;

	@SerializedName("PhysicalResourceId")
	private String physicalResourceId;

	@SerializedName("LogicalResourceId")
	private String logicalResourceId;

	@SerializedName("ResourceStatus")
	private String resourceStatus;

	@SerializedName("ResourceType")
	private String resourceType;

	@SerializedName("StackId")
	private String stackId;

	@SerializedName("Timestamp")
	private String timestamp;

	public String getStackName()
	{
		return stackName;
	}

	public void setStackName(String stackName)
	{
		this.stackName = stackName;
	}

	public String getEventId()
	{
		return eventId;
	}

	public void setEventId(String eventId)
	{
		this.eventId = eventId;
	}

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

	public String getStackId()
	{
		return stackId;
	}

	public void setStackId(String stackId)
	{
		this.stackId = stackId;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

}
