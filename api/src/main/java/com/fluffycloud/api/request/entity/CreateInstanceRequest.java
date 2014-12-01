package com.fluffycloud.api.request.entity;

public class CreateInstanceRequest
{
	private String name;
	private CreateVpcRequest createVpcRequest;
	private CreateSubnetRequest createSubnetRequest;
	private CreateSecurityGroupRequest createSecurityGroupRequest;

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
