package com.fluffycloud.api.ec2.request.entity;

public class CreateSubnetRequest extends ResourceTags
{
	private String subnetId;

	private String name;

	private String vpcId;

	private String cidrBlock;

	public String getSubnetId()
	{
		return subnetId;
	}

	public void setSubnetId(String subnetId)
	{
		this.subnetId = subnetId;
	}

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
