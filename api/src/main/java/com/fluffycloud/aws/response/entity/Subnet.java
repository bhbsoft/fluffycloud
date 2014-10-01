package com.fluffycloud.aws.response.entity;

import com.google.gson.annotations.SerializedName;

public class Subnet
{
	@SerializedName("VpcId")
	private String vpcId;

	@SerializedName("CidrBlock")
	private String cidrBlock;

	@SerializedName("State")
	private String state;

	@SerializedName("AvailabilityZone")
	private String availabilityZone;

	@SerializedName("SubnetId")
	private String subnetId;

	@SerializedName("AvailabilityAddressCount")
	private String availabilityAddressCount;

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

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getAvailabilityZone()
	{
		return availabilityZone;
	}

	public void setAvailabilityZone(String availabilityZone)
	{
		this.availabilityZone = availabilityZone;
	}

	public String getSubnetId()
	{
		return subnetId;
	}

	public void setSubnetId(String subnetId)
	{
		this.subnetId = subnetId;
	}

	public String getAvailabilityAddressCount()
	{
		return availabilityAddressCount;
	}

	public void setAvailabilityAddressCount(String availabilityAddressCount)
	{
		this.availabilityAddressCount = availabilityAddressCount;
	}

}
