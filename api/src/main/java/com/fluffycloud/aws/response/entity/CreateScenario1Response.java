package com.fluffycloud.aws.response.entity;

import com.google.gson.annotations.SerializedName;

public class CreateScenario1Response extends BaseResponse
{
	@SerializedName("VPC")
	private CreateVPCResponse createVPCResponse;

	@SerializedName("SecurityGroup")
	private CreateSecurityGroupResponse createSecurityGroupResponse;

	@SerializedName("SecurityGroupPermissions")
	private DescribeSecurityGroupResponse describeSecurityGroupResponse;

	@SerializedName("Subnet")
	private CreateSubnetResponse createSubnetResponse;

	@SerializedName("Instance")
	private RunInstanceResponse runInstanceResponse;

	@SerializedName("InternetGateway")
	private CreateInternetGatewayResponse createInternetGatewayResponse;

	@SerializedName("InternetGatewayAttached")
	private ResponseFlag internetGatewayAttached;

	@SerializedName("AllocatedAddress")
	private AllocateAddressReponse allocateAddressReponse;

	@SerializedName("AddressAssociated")
	private ResponseFlag associateAddressResponse;

	public ResponseFlag getInternetGatewayAttached()
	{
		return internetGatewayAttached;
	}

	public void setInternetGatewayAttached(ResponseFlag internetGatewayAttached)
	{
		this.internetGatewayAttached = internetGatewayAttached;
	}

	public CreateInternetGatewayResponse getCreateInternetGatewayResponse()
	{
		return createInternetGatewayResponse;
	}

	public void setCreateInternetGatewayResponse(CreateInternetGatewayResponse createInternetGatewayResponse)
	{
		this.createInternetGatewayResponse = createInternetGatewayResponse;
	}

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

	public DescribeSecurityGroupResponse getDescribeSecurityGroupResponse()
	{
		return describeSecurityGroupResponse;
	}

	public void setDescribeSecurityGroupResponse(DescribeSecurityGroupResponse describeSecurityGroupResponse)
	{
		this.describeSecurityGroupResponse = describeSecurityGroupResponse;
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
