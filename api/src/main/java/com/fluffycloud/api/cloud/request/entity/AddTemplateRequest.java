package com.fluffycloud.api.cloud.request.entity;

import java.io.File;

import javax.validation.constraints.NotNull;

public class AddTemplateRequest
{
	@NotNull
	private String templateName;

	private String templateJson;

	private File templateFile;

	public String getTemplateName()
	{
		return templateName;
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

	public File getTemplateFile()
	{
		return templateFile;
	}

	public void setTemplateFile(File templateFile)
	{
		this.templateFile = templateFile;
	}

}
