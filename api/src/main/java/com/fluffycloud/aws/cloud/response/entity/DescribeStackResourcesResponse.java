package com.fluffycloud.aws.cloud.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DescribeStackResourcesResponse
{
	@SerializedName("StackResources")
	List<StackResource> stackResources;

	public List<StackResource> getStackResources()
	{
		return stackResources;
	}

	public void setStackResources(List<StackResource> stackResources)
	{
		this.stackResources = stackResources;
	}
}
