package com.fluffycloud.aws.cloud.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ListStacksResponse
{
	@SerializedName("StackSummaries")
	private List<StackSummary> stackSummaries;

	public List<StackSummary> getStackSummaries()
	{
		return stackSummaries;
	}

	public void setStackSummaries(List<StackSummary> stackSummaries)
	{
		this.stackSummaries = stackSummaries;
	}
}
