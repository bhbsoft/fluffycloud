package com.fluffycloud.api.cloud.request.entity;

import javax.validation.constraints.NotNull;

public class ListStackResourcesRequest
{
	@NotNull
	private String stackName;

	private String startingToken;

	private Integer maxItems;

	public String getStackName()
	{
		return stackName;
	}

	public void setStackName(String stackName)
	{
		this.stackName = stackName;
	}

	public String getStartingToken()
	{
		return startingToken;
	}

	public void setStartingToken(String startingToken)
	{
		this.startingToken = startingToken;
	}

	public Integer getMaxItems()
	{
		return maxItems;
	}

	public void setMaxItems(Integer maxItems)
	{
		this.maxItems = maxItems;
	}

}
