package com.fluffycloud.aws.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DescribeStackEventsResponse
{
	@SerializedName("StackEvents")
	List<StackEvent> stackEvents;

	public List<StackEvent> getStackEvents()
	{
		return stackEvents;
	}

	public void setStackEvents(List<StackEvent> stackEvents)
	{
		this.stackEvents = stackEvents;
	}
}
