package com.fluffycloud.api.cloud.request.entity;

import java.io.File;
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

	public String getTemplateParamsAsCommand()
	{
		StringBuilder params = new StringBuilder();
		for (String paramName : templateParams.keySet())
		{
			params.append(" ParameterKey=" + paramName + "," + "ParameterValue=" + templateParams.get(paramName));
		}
		return params.toString();
	}

	public String getTemplateBody()
	{
		return "file:" + File.separator + File.separator + "json" + File.separator + File.separator + "aws"
				+ File.separator + File.separator + "cloudformation" + File.separator + File.separator + "templates"
				+ File.separator + File.separator + templateName;
	}

	public String getTemplateURL()
	{
		// TODO need to check for template URL
		return "https://s3.amazonaws.com/cloudformation-templates-us-east-1/EC2InstanceWithSecurityGroupSample.template";
	}
}
