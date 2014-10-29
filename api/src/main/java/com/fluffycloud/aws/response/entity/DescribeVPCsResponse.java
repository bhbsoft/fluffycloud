package com.fluffycloud.aws.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DescribeVPCsResponse
{
	@SerializedName("Vpcs")
	private List<VPC> vpcs;

	public List<VPC> getVpcs()
	{
		return vpcs;
	}

	public void setVpcs(List<VPC> vpcs)
	{
		this.vpcs = vpcs;
	}

}
