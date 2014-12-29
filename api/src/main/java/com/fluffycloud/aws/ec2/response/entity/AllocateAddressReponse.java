package com.fluffycloud.aws.ec2.response.entity;

import com.google.gson.annotations.SerializedName;

public class AllocateAddressReponse extends BaseResponse
{
	@SerializedName("PublicIp")
	private String publicIp;

	@SerializedName("Domain")
	private String domain;

	@SerializedName("AllocationId")
	private String allocationID;

	public String getPublicIp()
	{
		return publicIp;
	}

	public String getAllocationID()
	{
		return allocationID;
	}

	public void setAllocationID(String allocationID)
	{
		this.allocationID = allocationID;
	}

	public void setPublicIp(String publicIp)
	{
		this.publicIp = publicIp;
	}

	public String getDomain()
	{
		return domain;
	}

	public void setDomain(String domain)
	{
		this.domain = domain;
	}

}
