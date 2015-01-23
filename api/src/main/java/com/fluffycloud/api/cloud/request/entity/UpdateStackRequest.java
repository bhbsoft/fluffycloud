package com.fluffycloud.api.cloud.request.entity;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

public class UpdateStackRequest
{
	@NotNull
	private String stackName;

	private String templateName;

	private String templateURL;

	private boolean usePreviousTemplate;

	private String stackPolicyDuringUpdateBody;

	private File stackPolicyDuringUpdateFile;

	private String stackPolicyDuringUpdateURL;

	private Map<String, String> templateParams;

	private List<String> capabilities;

	private String stackPolicyBody;

	private String stackPolicyURL;

	public String getStackName()
	{
		return stackName;
	}

	public void setStackName(String stackName)
	{
		this.stackName = stackName;
	}

	public String getTemplateName()
	{
		return templateName;
	}

	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	public String getTemplateURL()
	{
		return templateURL;
	}

	public void setTemplateURL(String templateURL)
	{
		this.templateURL = templateURL;
	}

	public boolean isUsePreviousTemplate()
	{
		return usePreviousTemplate;
	}

	public void setUsePreviousTemplate(boolean usePreviousTemplate)
	{
		this.usePreviousTemplate = usePreviousTemplate;
	}

	public String getStackPolicyDuringUpdateBody()
	{
		return stackPolicyDuringUpdateBody;
	}

	public void setStackPolicyDuringUpdateBody(String stackPolicyDuringUpdateBody)
	{
		this.stackPolicyDuringUpdateBody = stackPolicyDuringUpdateBody;
	}

	public String getStackPolicyDuringUpdateURL()
	{
		return stackPolicyDuringUpdateURL;
	}

	public void setStackPolicyDuringUpdateURL(String stackPolicyDuringUpdateURL)
	{
		this.stackPolicyDuringUpdateURL = stackPolicyDuringUpdateURL;
	}

	public Map<String, String> getTemplateParams()
	{
		return templateParams;
	}

	public void setTemplateParams(Map<String, String> templateParams)
	{
		this.templateParams = templateParams;
	}

	public List<String> getCapabilities()
	{
		return capabilities;
	}

	public void setCapabilities(List<String> capabilities)
	{
		this.capabilities = capabilities;
	}

	public String getStackPolicyBody()
	{
		return stackPolicyBody;
	}

	public void setStackPolicyBody(String stackPolicyBody)
	{
		this.stackPolicyBody = stackPolicyBody;
	}

	public String getStackPolicyURL()
	{
		return stackPolicyURL;
	}

	public void setStackPolicyURL(String stackPolicyURL)
	{
		this.stackPolicyURL = stackPolicyURL;
	}

	public File getStackPolicyDuringUpdateFile()
	{
		return stackPolicyDuringUpdateFile;
	}

	public void setStackPolicyDuringUpdateFile(File stackPolicyDuringUpdateFile)
	{
		this.stackPolicyDuringUpdateFile = stackPolicyDuringUpdateFile;
	}
}
