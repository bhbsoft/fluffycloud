package com.fluffycloud.api.request.entity;

import javax.validation.constraints.NotNull;

import com.fluffycloud.aws.constants.InstanceTypes;

public class CreateInstanceRequest extends ResourceTags
{
	@NotNull
	private CreateVpcRequest createVpcRequest;

	@NotNull
	private CreateSubnetRequest createSubnetRequest;

	@NotNull
	private InstanceTypes instanceType;

	@NotNull
	private String keyPairName;

	@NotNull
	private CreateSecurityGroupRequest createSecurityGroupRequest;

	public String getKeyPairName()
	{
		return keyPairName;
	}

	public void setKeyPairName(String keyPairName)
	{
		this.keyPairName = keyPairName;
	}

	public InstanceTypes getInstanceType()
	{
		return instanceType;
	}

	public void setInstanceType(InstanceTypes instanceType)
	{
		this.instanceType = instanceType;
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

	// public static void main(String args[])
	// {
	// CreateInstanceRequest createInstanceRequest = new
	// CreateInstanceRequest();
	// CreateVpcRequest createVpcRequest = new CreateVpcRequest();
	// createVpcRequest.setCidrBlock("10.0.0.0/16");
	// createVpcRequest.setInstanceTenancy("Default");
	// createVpcRequest.setResourceId("resourceId");
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("TestRKey", "TestRValue");
	// createVpcRequest.setTags(map);
	// createVpcRequest.setVpcId("vpc-dadkj87");
	//
	// CreateSubnetRequest createSubnetRequest = new CreateSubnetRequest();
	// createSubnetRequest.setCidrBlock("10.0.0.0/16");
	// createSubnetRequest.setName("SubnetName");
	// createSubnetRequest.setSubnetId("sub-sdad34");
	// createSubnetRequest.setResourceId("resourceId");
	// createSubnetRequest.setTags(map);
	// createSubnetRequest.setVpcId("vpc-dadkj87");
	//
	// CreateSecurityGroupRequest createSecurityGroupRequest = new
	// CreateSecurityGroupRequest();
	// createSecurityGroupRequest.setDescription("description");
	// createSecurityGroupRequest.setName("SGNAME");
	// createSecurityGroupRequest.setResourceId("resourceId");
	// createSecurityGroupRequest.setTags(map);
	// createSecurityGroupRequest.setVpcId("vpc-dadkj87");
	// createSecurityGroupRequest.setSecurityGroupId("SG-Id");
	//
	// createInstanceRequest.setCreateSecurityGroupRequest(createSecurityGroupRequest);
	// createInstanceRequest.setCreateSubnetRequest(createSubnetRequest);
	// createInstanceRequest.setCreateVpcRequest(createVpcRequest);
	// createInstanceRequest.setInstanceType(InstanceTypes.t1MICRO);
	// createInstanceRequest.setKeyPairName("Key pair");
	// createInstanceRequest.setName("Name");
	// createInstanceRequest.setResourceId("resourceId");
	//
	// createInstanceRequest.setTags(map);
	//
	// Gson gson = new Gson();
	// System.out.println(gson.toJson(createInstanceRequest));
	//
	// }

}
