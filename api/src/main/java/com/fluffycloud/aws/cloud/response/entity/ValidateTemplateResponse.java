package com.fluffycloud.aws.cloud.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ValidateTemplateResponse
{
	@SerializedName("Parameters")
	private List<Parameter> parameters;

	@SerializedName("Description")
	private String description;

	@SerializedName("Capabilities")
	private List<String> capabilities;

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public List<String> getCapabilities()
	{
		return capabilities;
	}

	public void setCapabilities(List<String> capabilities)
	{
		this.capabilities = capabilities;
	}

	public List<Parameter> getParameters()
	{
		return parameters;
	}

	public void setParameters(List<Parameter> parameters)
	{
		this.parameters = parameters;
	}

}
