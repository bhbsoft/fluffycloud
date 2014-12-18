package com.fluffycloud.api.request.entity;

import javax.validation.constraints.NotNull;

public class AddSGRuleRequest
{
	@NotNull
	private String securityGroupId;

	@NotNull
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
