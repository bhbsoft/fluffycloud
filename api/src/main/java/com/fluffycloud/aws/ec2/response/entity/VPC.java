package com.fluffycloud.aws.ec2.response.entity;

import com.google.gson.annotations.SerializedName;

public class VPC
{
	@SerializedName("CidrBlock")
	private String cidrBlock;

	@SerializedName("InstanceTenancy")
	private String instanceTenancy;

	@SerializedName("VpcId")
	private String vpcId;

	@SerializedName("DhcpOptionsId")
	private String dhcpOptionsId;

	@SerializedName("State")
	private String state;

	public String getCidrBlock()
	{
		return cidrBlock;
	}

	public void setCidrBlock(String cidrBlock)
	{
		this.cidrBlock = cidrBlock;
	}

	public String getInstanceTenancy()
	{
		return instanceTenancy;
	}

	public void setInstanceTenancy(String instanceTenancy)
	{
		this.instanceTenancy = instanceTenancy;
	}

	public String getVpcId()
	{
		return vpcId;
	}

	public void setVpcId(String vpcId)
	{
		this.vpcId = vpcId;
	}

	public String getDhcpOptionsId()
	{
		return dhcpOptionsId;
	}

	public void setDhcpOptionsId(String dhcpOptionsId)
	{
		this.dhcpOptionsId = dhcpOptionsId;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

}
