package com.fluffycloud.aws.response.entity;

import com.google.gson.annotations.SerializedName;

public class CreateScenario1Response extends BaseResponse
{
	@SerializedName("VPC")
	private CreateVPCResponse createVPCResponse;

	@SerializedName("SecurityGroup")
	private CreateSecurityGroupResponse createSecurityGroupResponse;

	@SerializedName("InBoundRuleCreated")
	private ResponseFlag authorizeSecurityGroupIngressResponse;

	@SerializedName("OutBoundRuleCreated")
	private ResponseFlag authorizeSecurityGroupEgressResponse;

	@SerializedName("Subnet")
	private CreateSubnetResponse createSubnetResponse;

	@SerializedName("Instance")
	private RunInstanceResponse runInstanceResponse;

	@SerializedName("AllocatedAddress")
	private AllocateAddressReponse allocateAddressReponse;

	@SerializedName("AddressAssociated")
	private ResponseFlag associateAddressResponse;

	public CreateVPCResponse getCreateVPCResponse()
	{
		return createVPCResponse;
	}

	public void setCreateVPCResponse(CreateVPCResponse createVPCResponse)
	{
		this.createVPCResponse = createVPCResponse;
	}

	public CreateSecurityGroupResponse getCreateSecurityGroupResponse()
	{
		return createSecurityGroupResponse;
	}

	public void setCreateSecurityGroupResponse(CreateSecurityGroupResponse createSecurityGroupResponse)
	{
		this.createSecurityGroupResponse = createSecurityGroupResponse;
	}

	public ResponseFlag getAuthorizeSecurityGroupIngressResponse()
	{
		return authorizeSecurityGroupIngressResponse;
	}

	public void setAuthorizeSecurityGroupIngressResponse(ResponseFlag authorizeSecurityGroupIngressResponse)
	{
		this.authorizeSecurityGroupIngressResponse = authorizeSecurityGroupIngressResponse;
	}

	public ResponseFlag getAuthorizeSecurityGroupEgressResponse()
	{
		return authorizeSecurityGroupEgressResponse;
	}

	public void setAuthorizeSecurityGroupEgressResponse(ResponseFlag authorizeSecurityGroupEgressResponse)
	{
		this.authorizeSecurityGroupEgressResponse = authorizeSecurityGroupEgressResponse;
	}

	public CreateSubnetResponse getCreateSubnetResponse()
	{
		return createSubnetResponse;
	}

	public void setCreateSubnetResponse(CreateSubnetResponse createSubnetResponse)
	{
		this.createSubnetResponse = createSubnetResponse;
	}

	public RunInstanceResponse getRunInstanceResponse()
	{
		return runInstanceResponse;
	}

	public void setRunInstanceResponse(RunInstanceResponse runInstanceResponse)
	{
		this.runInstanceResponse = runInstanceResponse;
	}

	public AllocateAddressReponse getAllocateAddressReponse()
	{
		return allocateAddressReponse;
	}

	public void setAllocateAddressReponse(AllocateAddressReponse allocateAddressReponse)
	{
		this.allocateAddressReponse = allocateAddressReponse;
	}

	public ResponseFlag getAssociateAddressResponse()
	{
		return associateAddressResponse;
	}

	public void setAssociateAddressResponse(ResponseFlag associateAddressResponse)
	{
		this.associateAddressResponse = associateAddressResponse;
	}

}
