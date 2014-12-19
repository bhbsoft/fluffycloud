package com.fluffycloud.api.request.entity;

public class CreateSecurityGroupRequest extends ResourceTags
{

	private String securityGroupId;

	private String name;

	private String description;

	private Rule addRuleRequest;

	private String vpcId;

	public String getVpcId()
	{
		return vpcId;
	}

	public void setVpcId(String vpcId)
	{
		this.vpcId = vpcId;
	}

	public String getSecurityGroupId()
	{
		return securityGroupId;
	}

	public void setSecurityGroupId(String securityGroupId)
	{
		this.securityGroupId = securityGroupId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Rule getAddRuleRequest()
	{
		return addRuleRequest;
	}

	public void setAddRuleRequest(Rule addRuleRequest)
	{
		this.addRuleRequest = addRuleRequest;
	}

}
