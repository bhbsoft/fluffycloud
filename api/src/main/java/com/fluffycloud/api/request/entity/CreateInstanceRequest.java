package com.fluffycloud.api.request.entity;

import javax.validation.constraints.NotNull;

import com.fluffycloud.aws.constants.InstanceTypes;

public class CreateInstanceRequest extends ResourceTags
{
	@NotNull
	private String name;

	@NotNull
	private CreateVpcRequest createVpcRequest;

	@NotNull
	private CreateSubnetRequest createSubnetRequest;

	@NotNull
	private InstanceTypes instanceType;

	@NotNull
	private CreateSecurityGroupRequest createSecurityGroupRequest;

	public InstanceTypes getInstanceType()
	{
		return instanceType;
	}

	public void setInstanceType(InstanceTypes instanceType)
	{
		this.instanceType = instanceType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public CreateVpcRequest getCreateVpcRequest()
	{
		return createVpcRequest;
	}

	public void setCreateVpcRequest(CreateVpcRequest createVpcRequest)
	{
		this.createVpcRequest = createVpcRequest;
	}

	public CreateSubnetRequest getCreateSubnetRequest()
	{
		return createSubnetRequest;
	}

	public void setCreateSubnetRequest(CreateSubnetRequest createSubnetRequest)
	{
		this.createSubnetRequest = createSubnetRequest;
	}

	public CreateSecurityGroupRequest getCreateSecurityGroupRequest()
	{
		return createSecurityGroupRequest;
	}

	public void setCreateSecurityGroupRequest(CreateSecurityGroupRequest createSecurityGroupRequest)
	{
		this.createSecurityGroupRequest = createSecurityGroupRequest;
	}

}
