package com.fluffycloud.aws.response.entity;

import com.google.gson.annotations.SerializedName;

public class Output
{
	@SerializedName("OutputKey")
	private String outputKey;

	@SerializedName("OutputValue")
	private String outputValue;

	@SerializedName("Description")
	private String description;

	public String getOutputKey()
	{
		return outputKey;
	}

	public void setOutputKey(String outputKey)
	{
		this.outputKey = outputKey;
	}

	public String getOutputValue()
	{
		return outputValue;
	}

	public void setOutputValue(String outputValue)
	{
		this.outputValue = outputValue;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}
