package com.fluffycloud.aws.ec2.response.entity;

import com.google.gson.annotations.SerializedName;

public class CreateRouteTableResponse extends BaseResponse
{
	@SerializedName("RouteTable")
	private RouteTable routeTable;

	public RouteTable getRouteTable()
	{
		return routeTable;
	}

	public void setRouteTable(RouteTable routeTable)
	{
		this.routeTable = routeTable;
	}

}
