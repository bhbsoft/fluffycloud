package com.fluffycloud.aws.ec2.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DescribeSubnetsResponse
{
	@SerializedName("Subnets")
	private List<Subnet> subnets;

	public List<Subnet> getSubnets()
	{
		return subnets;
	}

	public void setSubnets(List<Subnet> subnets)
	{
		this.subnets = subnets;
	}
}
