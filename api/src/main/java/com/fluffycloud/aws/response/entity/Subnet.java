package com.fluffycloud.aws.response.entity;

public class Subnet
{
	private String VpcId;
	private String CidrBlock;
	private String State;
	private String AvailabilityZone;
	private String SubnetId;
	private String AvailabilityAddressCount;

	public String getVpcId()
	{
		return VpcId;
	}

	public void setVpcId(String vpcId)
	{
		VpcId = vpcId;
	}

	public String getCidrBlock()
	{
		return CidrBlock;
	}

	public void setCidrBlock(String cidrBlock)
	{
		CidrBlock = cidrBlock;
	}

	public String getState()
	{
		return State;
	}

	public void setState(String state)
	{
		State = state;
	}

	public String getAvailabilityZone()
	{
		return AvailabilityZone;
	}

	public void setAvailabilityZone(String availabilityZone)
	{
		AvailabilityZone = availabilityZone;
	}

	public String getSubnetId()
	{
		return SubnetId;
	}

	public void setSubnetId(String subnetId)
	{
		SubnetId = subnetId;
	}

	public String getAvailabilityAddressCount()
	{
		return AvailabilityAddressCount;
	}

	public void setAvailabilityAddressCount(String availabilityAddressCount)
	{
		AvailabilityAddressCount = availabilityAddressCount;
	}

}
