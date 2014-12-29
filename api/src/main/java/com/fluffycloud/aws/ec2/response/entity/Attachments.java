package com.fluffycloud.aws.ec2.response.entity;

import com.google.gson.annotations.SerializedName;

public class Attachments
{
	@SerializedName("VpcId")
	private String vpcId;

	@SerializedName("State")
	private String state;

	public String getVpcId()
	{
		return vpcId;
	}

	public void setVpcId(String vpcId)
	{
		this.vpcId = vpcId;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

}
