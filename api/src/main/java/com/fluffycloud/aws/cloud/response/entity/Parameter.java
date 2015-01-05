package com.fluffycloud.aws.cloud.response.entity;

import com.google.gson.annotations.SerializedName;

public class Parameter
{
	@SerializedName("ParameterValue")
	private String parameterValue;

	@SerializedName("ParameterKey")
	private String parameterKey;

	@SerializedName("DefaultValue")
	private String defaultValue;

	@SerializedName("NoEcho")
	private boolean noEcho;

	@SerializedName("Description")
	private String description;

	public String getDefaultValue()
	{
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public boolean isNoEcho()
	{
		return noEcho;
	}

	public void setNoEcho(boolean noEcho)
	{
		this.noEcho = noEcho;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getParameterValue()
	{
		return parameterValue;
	}

	public void setParameterValue(String parameterValue)
	{
		this.parameterValue = parameterValue;
	}

	public String getParameterKey()
	{
		return parameterKey;
	}

	public void setParameterKey(String parameterKey)
	{
		this.parameterKey = parameterKey;
	}

}
