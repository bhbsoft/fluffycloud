package com.fluffycloud.api.request.entity;

import javax.validation.constraints.NotNull;

public class CreateVpcRequest extends ResourceTags
{
	@NotNull
	private String cidrBlock;

	@NotNull
	private String instanceTenancy;

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
