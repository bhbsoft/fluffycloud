package com.fluffycloud.aws.ec2.response.entity;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class DescribeSecurityGroupResponse extends BaseResponse
{
	@SerializedName("SecurityGroups")
	private ArrayList<SecurityGroup> securityGroups;

	public ArrayList<SecurityGroup> getSecurityGroups()
	{
		return securityGroups;
	}

	public void setSecurityGroups(ArrayList<SecurityGroup> securityGroups)
	{
		this.securityGroups = securityGroups;
	}
	
}
