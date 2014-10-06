package com.fluffycloud.aws.response.entity;

import com.google.gson.annotations.SerializedName;

public class InstanceState
{
	@SerializedName("Code")
	private String code;

	@SerializedName("Name")
	private String name;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

}
