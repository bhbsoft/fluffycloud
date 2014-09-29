package com.fluffycloud.aws.response.entity;

public class VPC
{
	private String CidrBlock;
	private String InstanceTenancy;
	private String VpcId;
	private String DhcpOptionsId;
	private String State;

	public String getCidrBlock()
	{
		return CidrBlock;
	}

	public void setCidrBlock(String cidrBlock)
	{
		CidrBlock = cidrBlock;
	}

	public String getInstanceTenancy()
	{
		return InstanceTenancy;
	}

	public void setInstanceTenancy(String instanceTenancy)
	{
		InstanceTenancy = instanceTenancy;
	}

	public String getVpcId()
	{
		return VpcId;
	}

	public void setVpcId(String vpcId)
	{
		VpcId = vpcId;
	}

	public String getDhcpOptionsId()
	{
		return DhcpOptionsId;
	}

	public void setDhcpOptionsId(String dhcpOptionsId)
	{
		DhcpOptionsId = dhcpOptionsId;
	}

	public String getState()
	{
		return State;
	}

	public void setState(String state)
	{
		State = state;
	}

}
