package com.fluffycloud.aws.response.entity;

import com.google.gson.annotations.SerializedName;

public class StackSummary
{
	@SerializedName("StackStatusReason")
	private String stackStatusReason;

	@SerializedName("StackName")
	private String stackName;

	@SerializedName("StackStatus")
	private String stackStatus;

	@SerializedName("StackId")
	private String stackId;

	@SerializedName("CreationTime")
	private String creationTime;

	@SerializedName("TemplateDescription")
	private String templateDescription;

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

	public String getTemplateDescription()
	{
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription)
	{
		this.templateDescription = templateDescription;
	}

}
