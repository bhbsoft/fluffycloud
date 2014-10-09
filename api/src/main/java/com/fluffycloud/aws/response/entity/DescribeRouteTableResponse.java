package com.fluffycloud.aws.response.entity;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class DescribeRouteTableResponse
{
	@SerializedName("RouteTables")
	private ArrayList<RouteTable> routeTables;

	public ArrayList<RouteTable> getRouteTables()
	{
		return routeTables;
	}

	public void setRouteTables(ArrayList<RouteTable> routeTables)
	{
		this.routeTables = routeTables;
	}
}
