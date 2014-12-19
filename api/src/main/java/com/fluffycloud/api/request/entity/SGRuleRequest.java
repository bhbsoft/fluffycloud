package com.fluffycloud.api.request.entity;

import javax.validation.constraints.NotNull;

public class SGRuleRequest
{
	@NotNull
	private String securityGroupId;

	@NotNull
	private Rule rule;

	public Rule getRule()
	{
		return rule;
	}

	public void setRule(Rule rule)
	{
		this.rule = rule;
	}

	public String getSecurityGroupId()
	{
		return securityGroupId;
	}

	public void setSecurityGroupId(String securityGroupId)
	{
		this.securityGroupId = securityGroupId;
	}

}
