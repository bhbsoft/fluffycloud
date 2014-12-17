package com.fluffycloud.api.request.entity;

public class AddSGRuleRequest
{

	private String securityGroupId;

	private AddRuleRequest addRuleRequest;

	public String getSecurityGroupId()
	{
		return securityGroupId;
	}

	public void setSecurityGroupId(String securityGroupId)
	{
		this.securityGroupId = securityGroupId;
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
