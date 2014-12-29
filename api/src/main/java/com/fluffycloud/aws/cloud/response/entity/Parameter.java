package com.fluffycloud.aws.cloud.response.entity;

import com.google.gson.annotations.SerializedName;

public class Parameter
{
	@SerializedName("ParameterValue")
	private String parameterValue;

	@SerializedName("ParameterKey")
	private String parameterKey;

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
