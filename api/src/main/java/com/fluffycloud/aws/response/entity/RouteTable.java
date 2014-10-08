package com.fluffycloud.aws.response.entity;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class RouteTable
{

	@SerializedName("RouteTableId")
	private String routeTableId;

	@SerializedName("Vpc_id")
	private String vpcId;

	@SerializedName("Routes")
	private ArrayList<Route> routes;

	@SerializedName("Associations")
	private ArrayList<Association> associations;

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

	public ArrayList<Route> getRoutes()
	{
		return routes;
	}

	public void setRoutes(ArrayList<Route> routes)
	{
		this.routes = routes;
	}

	public ArrayList<Association> getAssociations()
	{
		return associations;
	}

	public void setAssociations(ArrayList<Association> associations)
	{
		this.associations = associations;
	}

}
