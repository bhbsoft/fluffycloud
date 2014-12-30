package com.fluffycloud.aws.cloud.response.entity;

import com.google.gson.annotations.SerializedName;

public class CreateStackResponse
{
	@SerializedName("StackId")
	private String stackId;

	public String getStackId()
	{
		return stackId;
	}

	public void setStackId(String stackId)
	{
		this.stackId = stackId;
	}

}
