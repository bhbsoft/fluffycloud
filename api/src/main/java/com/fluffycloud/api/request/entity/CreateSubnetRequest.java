package com.fluffycloud.api.request.entity;

public class CreateSubnetRequest
{
	private String name;
	private String vpcId;
	private String cidrBlock;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getVpcId()
	{
		return vpcId;
	}

	public void setVpcId(String vpcId)
	{
		this.vpcId = vpcId;
	}

	public String getCidrBlock()
	{
		return cidrBlock;
	}

	public void setCidrBlock(String cidrBlock)
	{
		this.cidrBlock = cidrBlock;
	}

}
