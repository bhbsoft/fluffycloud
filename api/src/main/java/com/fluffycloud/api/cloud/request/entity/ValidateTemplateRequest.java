package com.fluffycloud.api.cloud.request.entity;

import java.io.File;

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

	public String getTemplateBody()
	{
		return "file:" + File.separator + File.separator + "json" + File.separator + File.separator + "aws"
				+ File.separator + File.separator + "cloudformation" + File.separator + File.separator + "templates"
				+ File.separator + File.separator + templateName;
	}

}
