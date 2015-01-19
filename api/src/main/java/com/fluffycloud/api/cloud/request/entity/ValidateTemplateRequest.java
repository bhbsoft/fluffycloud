package com.fluffycloud.api.cloud.request.entity;

import javax.validation.constraints.NotNull;

import com.fluffycloud.aws.constants.AppParams;

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
		return AppParams.TEMPLATEBODYCLILOCATION.getValue() + templateName;
	}

}
