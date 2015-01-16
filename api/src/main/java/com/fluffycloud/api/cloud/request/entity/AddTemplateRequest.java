package com.fluffycloud.api.cloud.request.entity;

import java.io.File;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

public class AddTemplateRequest
{
	@NotNull
	private String templateName;

	private String templateJson;

	private MultipartFile templateFile;

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

	public MultipartFile getTemplateFile()
	{
		return templateFile;
	}

	public void setTemplateFile(MultipartFile templateFile)
	{
		this.templateFile = templateFile;
	}
}
