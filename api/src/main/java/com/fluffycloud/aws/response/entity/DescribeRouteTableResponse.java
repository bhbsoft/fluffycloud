package com.fluffycloud.aws.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DescribeRouteTableResponse extends BaseResponse
{
	@SerializedName("RouteTables")
	private List<RouteTable> routeTables;

	public List<RouteTable> getRouteTables()
	{
		return routeTables;
	}

	public void setRouteTables(List<RouteTable> routeTables)
	{
		this.routeTables = routeTables;
	}
}
