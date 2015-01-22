package com.fluffycloud.api.cloud.request.entity;

import javax.validation.constraints.NotNull;

public class ValidateTemplateRequest
{
	@NotNull
	private String templateName;

	private String templateURL;

	public String getTemplateURL()
	{
		return templateURL;
	}

	public String getTemplateName()
	{
		return templateName;
	}

	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	public void setTemplateURL(String templateURL)
	{
		this.templateURL = templateURL;
	}
}
