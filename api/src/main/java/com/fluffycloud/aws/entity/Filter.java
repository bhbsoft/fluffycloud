package com.fluffycloud.aws.entity;

import java.util.List;

public class Filter
{
	private String name;
	private List<String> values;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<String> getValues()
	{
		return values;
	}

	public void setValues(List<String> values)
	{
		this.values = values;
	}

}
