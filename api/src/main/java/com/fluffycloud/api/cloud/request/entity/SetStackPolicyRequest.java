package com.fluffycloud.api.cloud.request.entity;

import java.io.File;

import javax.validation.constraints.NotNull;

public class SetStackPolicyRequest
{
	@NotNull
	private String stackName;

	@NotNull
	private String stackPolicyName;

	public String getStackName()
	{
		return stackName;
	}

	public void setStackName(String stackName)
	{
		this.stackName = stackName;
	}

	public String getStackPolicyName()
	{
		return stackPolicyName;
	}

	public void setStackPolicyName(String stackPolicyName)
	{
		this.stackPolicyName = stackPolicyName;
	}

	public String getStackPolicyBody()
	{
		return "file:" + File.separator + File.separator + "json" + File.separator + File.separator + "aws"
				+ File.separator + File.separator + "cloudformation" + File.separator + File.separator + "templates"
				+ File.separator + File.separator + "policy" + File.separator + File.separator + stackPolicyName;
	}

}