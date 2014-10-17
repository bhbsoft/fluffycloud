package com.fluffycloud.aws.response.entity;

import com.fluffycloud.aws.entity.CommonRequestParams;
import com.google.gson.annotations.SerializedName;

public class CreateScenario2Response extends BaseResponse
{
	// @Id
	// private String action;
	// TODO ID for this

	@SerializedName("UserDetail")
	private CommonRequestParams userDetail;

	@SerializedName("VPC")
	private CreateVPCResponse createVPCResponse;

	@SerializedName("PublicSubnet")
	private CreateSubnetResponse createPublicSubnetResponse;

	@SerializedName("PrivateSubnet")
	private CreateSubnetResponse createPrivateSubnetResponse;

	@SerializedName("WebSecurityGroup")
	private DescribeSecurityGroupResponse describeWebSGResponse;

	@SerializedName("NatSecurityGroup")
	private DescribeSecurityGroupResponse describeNatSGResponse;

	@SerializedName("DBSecurityGroup")
	private DescribeSecurityGroupResponse describeDBSGResponse;

	@SerializedName("InternetGateway")
	private CreateInternetGatewayResponse createInternetGatewayResponse;

	@SerializedName("InternetGatewayAttached")
	private ResponseFlag attachInternetGatewayResponse;

	@SerializedName("RouteTable")
	private CreateRouteTableResponse createRouteTableResponse;

	@SerializedName("RouteCreated")
	private ResponseFlag createRouteResponse;

	@SerializedName("WebserverInstance")
	private RunInstanceResponse runWebserverInstanceResponse;

	@SerializedName("NatserverInstance")
	private RunInstanceResponse runNatserverInstanceResponse;

	@SerializedName("DBserverInstance")
	private RunInstanceResponse runDBserverInstanceResponse;

	@SerializedName("RouteTable")
	private DescribeRouteTableResponse describeRouteTableResponse;

	@SerializedName("AllocatePublicIpForWebInstance")
	private AllocateAddressReponse allocateWebInstanceAddressReponse;

	@SerializedName("PublicIPAssociatedToWebInstance")
	private ResponseFlag associateAddressResponse;

	@SerializedName("AllocatePublicIpForNatInstance")
	private AllocateAddressReponse allocateNatInstanceAddressReponse;

	@SerializedName("PublicIPAssociatedToNatInstance")
	private ResponseFlag associateNatInstanceAddressResponse;

	public CommonRequestParams getUserDetail()
	{
		return userDetail;
	}

	public void setUserDetail(CommonRequestParams userDetail)
	{
		this.userDetail = userDetail;
	}

	public CreateVPCResponse getCreateVPCResponse()
	{
		return createVPCResponse;
	}

	public void setCreateVPCResponse(CreateVPCResponse createVPCResponse)
	{
		this.createVPCResponse = createVPCResponse;
	}

	public CreateSubnetResponse getCreatePublicSubnetResponse()
	{
		return createPublicSubnetResponse;
	}

	public void setCreatePublicSubnetResponse(CreateSubnetResponse createPublicSubnetResponse)
	{
		this.createPublicSubnetResponse = createPublicSubnetResponse;
	}

	public CreateSubnetResponse getCreatePrivateSubnetResponse()
	{
		return createPrivateSubnetResponse;
	}

	public void setCreatePrivateSubnetResponse(CreateSubnetResponse createPrivateSubnetResponse)
	{
		this.createPrivateSubnetResponse = createPrivateSubnetResponse;
	}

	public DescribeSecurityGroupResponse getDescribeWebSGResponse()
	{
		return describeWebSGResponse;
	}

	public void setDescribeWebSGResponse(DescribeSecurityGroupResponse describeWebSGResponse)
	{
		this.describeWebSGResponse = describeWebSGResponse;
	}

	public DescribeSecurityGroupResponse getDescribeNatSGResponse()
	{
		return describeNatSGResponse;
	}

	public void setDescribeNatSGResponse(DescribeSecurityGroupResponse describeNatSGResponse)
	{
		this.describeNatSGResponse = describeNatSGResponse;
	}

	public DescribeSecurityGroupResponse getDescribeDBSGResponse()
	{
		return describeDBSGResponse;
	}

	public void setDescribeDBSGResponse(DescribeSecurityGroupResponse describeDBSGResponse)
	{
		this.describeDBSGResponse = describeDBSGResponse;
	}

	public CreateInternetGatewayResponse getCreateInternetGatewayResponse()
	{
		return createInternetGatewayResponse;
	}

	public void setCreateInternetGatewayResponse(CreateInternetGatewayResponse createInternetGatewayResponse)
	{
		this.createInternetGatewayResponse = createInternetGatewayResponse;
	}

	public ResponseFlag getAttachInternetGatewayResponse()
	{
		return attachInternetGatewayResponse;
	}

	public void setAttachInternetGatewayResponse(ResponseFlag attachInternetGatewayResponse)
	{
		this.attachInternetGatewayResponse = attachInternetGatewayResponse;
	}

	public CreateRouteTableResponse getCreateRouteTableResponse()
	{
		return createRouteTableResponse;
	}

	public void setCreateRouteTableResponse(CreateRouteTableResponse createRouteTableResponse)
	{
		this.createRouteTableResponse = createRouteTableResponse;
	}

	public ResponseFlag getCreateRouteResponse()
	{
		return createRouteResponse;
	}

	public void setCreateRouteResponse(ResponseFlag createRouteResponse)
	{
		this.createRouteResponse = createRouteResponse;
	}

	public RunInstanceResponse getRunWebserverInstanceResponse()
	{
		return runWebserverInstanceResponse;
	}

	public void setRunWebserverInstanceResponse(RunInstanceResponse runWebserverInstanceResponse)
	{
		this.runWebserverInstanceResponse = runWebserverInstanceResponse;
	}

	public RunInstanceResponse getRunNatserverInstanceResponse()
	{
		return runNatserverInstanceResponse;
	}

	public void setRunNatserverInstanceResponse(RunInstanceResponse runNatserverInstanceResponse)
	{
		this.runNatserverInstanceResponse = runNatserverInstanceResponse;
	}

	public RunInstanceResponse getRunDBserverInstanceResponse()
	{
		return runDBserverInstanceResponse;
	}

	public void setRunDBserverInstanceResponse(RunInstanceResponse runDBserverInstanceResponse)
	{
		this.runDBserverInstanceResponse = runDBserverInstanceResponse;
	}

	public DescribeRouteTableResponse getDescribeRouteTableResponse()
	{
		return describeRouteTableResponse;
	}

	public void setDescribeRouteTableResponse(DescribeRouteTableResponse describeRouteTableResponse)
	{
		this.describeRouteTableResponse = describeRouteTableResponse;
	}

	public AllocateAddressReponse getAllocateWebInstanceAddressReponse()
	{
		return allocateWebInstanceAddressReponse;
	}

	public void setAllocateWebInstanceAddressReponse(AllocateAddressReponse allocateWebInstanceAddressReponse)
	{
		this.allocateWebInstanceAddressReponse = allocateWebInstanceAddressReponse;
	}

	public ResponseFlag getAssociateAddressResponse()
	{
		return associateAddressResponse;
	}

	public void setAssociateAddressResponse(ResponseFlag associateAddressResponse)
	{
		this.associateAddressResponse = associateAddressResponse;
	}

	public AllocateAddressReponse getAllocateNatInstanceAddressReponse()
	{
		return allocateNatInstanceAddressReponse;
	}

	public void setAllocateNatInstanceAddressReponse(AllocateAddressReponse allocateNatInstanceAddressReponse)
	{
		this.allocateNatInstanceAddressReponse = allocateNatInstanceAddressReponse;
	}

	public ResponseFlag getAssociateNatInstanceAddressResponse()
	{
		return associateNatInstanceAddressResponse;
	}

	public void setAssociateNatInstanceAddressResponse(ResponseFlag associateNatInstanceAddressResponse)
	{
		this.associateNatInstanceAddressResponse = associateNatInstanceAddressResponse;
	}

}
