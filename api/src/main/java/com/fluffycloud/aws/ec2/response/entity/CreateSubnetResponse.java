package com.fluffycloud.aws.ec2.response.entity;

import com.google.gson.annotations.SerializedName;

public class CreateSubnetResponse extends BaseResponse
{
	@SerializedName("Subnet")
	private Subnet subnet;

	public Subnet getSubnet()
	{
		return subnet;
	}

	public void setSubnet(Subnet subnet)
	{
		this.subnet = subnet;
	}

}
