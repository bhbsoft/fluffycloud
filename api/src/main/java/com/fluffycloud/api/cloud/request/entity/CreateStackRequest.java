package com.fluffycloud.api.cloud.request.entity;

import java.util.Map;

import javax.validation.constraints.NotNull;

public class CreateStackRequest
{
	@NotNull
	private String templateName;

	@NotNull
	private String stackName;

	@NotNull
	private Map<String, String> templateParams;

	public String getTemplateName()
	{
		return templateName;
	}

	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	public String getStackName()
	{
		return stackName;
	}

	public void setStackName(String stackName)
	{
		this.stackName = stackName;
	}

	public Map<String, String> getTemplateParams()
	{
		return templateParams;
	}

	public void setTemplateParams(Map<String, String> templateParams)
	{
		this.templateParams = templateParams;
	}

}
