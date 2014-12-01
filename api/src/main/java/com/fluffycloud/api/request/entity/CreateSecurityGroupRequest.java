package com.fluffycloud.api.request.entity;

public class CreateSecurityGroupRequest
{

	private String securityGroupId;
	private String name;
	private String description;
	private AddRuleRequest addRuleRequest;

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

	public AddRuleRequest getAddRuleRequest()
	{
		return addRuleRequest;
	}

	public void setAddRuleRequest(AddRuleRequest addRuleRequest)
	{
		this.addRuleRequest = addRuleRequest;
	}

}
