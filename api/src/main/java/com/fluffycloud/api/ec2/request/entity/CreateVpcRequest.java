package com.fluffycloud.api.ec2.request.entity;

import javax.validation.constraints.NotNull;

public class CreateVpcRequest extends ResourceTags
{
	private String vpcId;

	@NotNull
	private String cidrBlock;

	@NotNull
	private String instanceTenancy;

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

	public String getInstanceTenancy()
	{
		return instanceTenancy;
	}

	public void setInstanceTenancy(String instanceTenancy)
	{
		this.instanceTenancy = instanceTenancy;
	}
}
