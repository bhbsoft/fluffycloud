package com.fluffycloud.aws.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RouteTable
{

	@SerializedName("RouteTableId")
	private String routeTableId;

	@SerializedName("Vpc_id")
	private String vpcId;

	@SerializedName("Routes")
	private List<Route> routes;

	@SerializedName("Associations")
	private List<Association> associations;

	public String getRouteTableId()
	{
		return routeTableId;
	}

	public void setRouteTableId(String routeTableId)
	{
		this.routeTableId = routeTableId;
	}

	public String getVpcId()
	{
		return vpcId;
	}

	public void setVpcId(String vpcId)
	{
		this.vpcId = vpcId;
	}

	public List<Route> getRoutes()
	{
		return routes;
	}

	public void setRoutes(List<Route> routes)
	{
		this.routes = routes;
	}

	public List<Association> getAssociations()
	{
		return associations;
	}

	public void setAssociations(List<Association> associations)
	{
		this.associations = associations;
	}

}
