package com.fluffycloud.aws.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Stack
{
	@SerializedName("Outputs")
	private List<Output> ouputs;

	@SerializedName("StackStatusReason")
	private String stackStatusReason;

	@SerializedName("StackName")
	private String stackName;

	@SerializedName("DisableRollback")
	private boolean disableRollback;

	@SerializedName("StackStatus")
	private String stackStatus;

	@SerializedName("StackId")
	private String stackId;

	@SerializedName("CreationTime")
	private String creationTime;

	@SerializedName("Description")
	private String description;

	@SerializedName("Parameters")
	private List<Parameter> parameters;

	@SerializedName("Tags")
	private List<Tag> tags;

	public List<Output> getOuputs()
	{
		return ouputs;
	}

	public void setOuputs(List<Output> ouputs)
	{
		this.ouputs = ouputs;
	}

	public String getStackStatusReason()
	{
		return stackStatusReason;
	}

	public void setStackStatusReason(String stackStatusReason)
	{
		this.stackStatusReason = stackStatusReason;
	}

	public String getStackName()
	{
		return stackName;
	}

	public void setStackName(String stackName)
	{
		this.stackName = stackName;
	}

	public boolean isDisableRollback()
	{
		return disableRollback;
	}

	public void setDisableRollback(boolean disableRollback)
	{
		this.disableRollback = disableRollback;
	}

	public String getStackStatus()
	{
		return stackStatus;
	}

	public void setStackStatus(String stackStatus)
	{
		this.stackStatus = stackStatus;
	}

	public String getStackId()
	{
		return stackId;
	}

	public void setStackId(String stackId)
	{
		this.stackId = stackId;
	}

	public String getCreationTime()
	{
		return creationTime;
	}

	public void setCreationTime(String creationTime)
	{
		this.creationTime = creationTime;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public List<Parameter> getParameters()
	{
		return parameters;
	}

	public void setParameters(List<Parameter> parameters)
	{
		this.parameters = parameters;
	}

	public List<Tag> getTags()
	{
		return tags;
	}

	public void setTags(List<Tag> tags)
	{
		this.tags = tags;
	}
}
