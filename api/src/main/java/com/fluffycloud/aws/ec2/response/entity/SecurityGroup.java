package com.fluffycloud.aws.ec2.response.entity;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class SecurityGroup
{

	@SerializedName("IpPermissions")
	private ArrayList<Rule> inboundRules;

	@SerializedName("IpPermissionsEgress")
	private ArrayList<Rule> outboundRules;

	@SerializedName("Description")
	private String description;

	@SerializedName("OwnerId")
	private String ownerId;

	@SerializedName("GroupName")
	private String groupName;

	@SerializedName("GroupId")
	private String groupId;

	@SerializedName("VpcId")
	private String vpcId;

	public ArrayList<Rule> getInboundRules()
	{
		return inboundRules;
	}

	public void setInboundRules(ArrayList<Rule> inboundRules)
	{
		this.inboundRules = inboundRules;
	}

	public ArrayList<Rule> getOutboundRules()
	{
		return outboundRules;
	}

	public void setOutboundRules(ArrayList<Rule> outboundRules)
	{
		this.outboundRules = outboundRules;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(String ownerId)
	{
		this.ownerId = ownerId;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public String getVpcId()
	{
		return vpcId;
	}

	public void setVpcId(String vpcId)
	{
		this.vpcId = vpcId;
	}

}
