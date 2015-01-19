package com.fluffycloud.api.cloud.request.entity;

import static java.lang.System.currentTimeMillis;

import org.springframework.web.multipart.MultipartFile;

import com.fluffycloud.aws.constants.AppParams;

public class AddTemplateRequest
{
	private String templateName;

	private String templateJson;

	private MultipartFile templateFile;

	private boolean validateOnly;

	public String getTemplateName()
	{
		if (null == this.templateName)
		{
			this.templateName = (null == this.templateFile) ? Long.toString(currentTimeMillis()) : templateFile
					.getName() + System.currentTimeMillis();
		}
		return templateName + AppParams.TEMPLATEXTSN.getValue();
	}

	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	public String getTemplateJson()
	{
		return templateJson;
	}

	public void setTemplateJson(String templateJson)
	{
		this.templateJson = templateJson;
	}

	public MultipartFile getTemplateFile()
	{
		return templateFile;
	}

	public void setTemplateFile(MultipartFile templateFile)
	{
		this.templateFile = templateFile;
	}

	public boolean isValidateOnly()
	{
		return validateOnly;
	}

	public void setValidateOnly(boolean validateOnly)
	{
		this.validateOnly = validateOnly;
	}

}
