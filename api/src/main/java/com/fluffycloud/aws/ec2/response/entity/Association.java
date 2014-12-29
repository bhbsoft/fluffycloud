package com.fluffycloud.aws.ec2.response.entity;

import com.google.gson.annotations.SerializedName;

public class Association
{
	@SerializedName("RouteTableId")
	private String routeTableId;

	@SerializedName("RouteTableAssociationId")
	private String routeTableAssociationId;

	@SerializedName("SubnetId")
	private String subnetId;

	@SerializedName("Main")
	private boolean main;

	public String getRouteTableId()
	{
		return routeTableId;
	}

	public void setRouteTableId(String routeTableId)
	{
		this.routeTableId = routeTableId;
	}

	public String getRouteTableAssociationId()
	{
		return routeTableAssociationId;
	}

	public void setRouteTableAssociationId(String routeTableAssociationId)
	{
		this.routeTableAssociationId = routeTableAssociationId;
	}

	public String getSubnetId()
	{
		return subnetId;
	}

	public void setSubnetId(String subnetId)
	{
		this.subnetId = subnetId;
	}

	public boolean isMain()
	{
		return main;
	}

	public void setMain(boolean main)
	{
		this.main = main;
	}

}
