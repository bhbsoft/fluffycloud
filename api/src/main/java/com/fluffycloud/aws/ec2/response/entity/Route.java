package com.fluffycloud.aws.ec2.response.entity;

import com.google.gson.annotations.SerializedName;

public class Route
{
	@SerializedName("State")
	private String state;

	@SerializedName("GatewayId")
	private String gatewayId;

	@SerializedName("DestinationCidrBlock")
	private String destinationCidrBlock;

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getGatewayId()
	{
		return gatewayId;
	}

	public void setGatewayId(String gatewayId)
	{
		this.gatewayId = gatewayId;
	}

	public String getDestinationCidrBlock()
	{
		return destinationCidrBlock;
	}

	public void setDestinationCidrBlock(String destinationCidrBlock)
	{
		this.destinationCidrBlock = destinationCidrBlock;
	}

}
