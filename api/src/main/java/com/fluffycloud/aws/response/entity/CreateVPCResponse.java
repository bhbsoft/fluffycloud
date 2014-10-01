package com.fluffycloud.aws.response.entity;

import com.google.gson.annotations.SerializedName;

public class CreateVPCResponse extends BaseResponse
{
	@SerializedName("Vpc")
	private VPC Vpc;

	public VPC getVpc()
	{
		return Vpc;
	}

	public void setVpc(VPC vpc)
	{
		Vpc = vpc;
	}

}
