package com.fluffycloud.aws.cloud.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DescribeStacksResponse
{
	@SerializedName("Stacks")
	private List<Stack> stacks;

	public List<Stack> getStacks()
	{
		return stacks;
	}

	public void setStacks(List<Stack> stacks)
	{
		this.stacks = stacks;
	}
}
