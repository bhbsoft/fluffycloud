package com.fluffycloud.api.request.entity;

import java.util.Map;

import javax.validation.constraints.NotNull;

public class ResourceTags
{
	private String resourceId;

	@NotNull
	private Map<String, String> tags;

	public String getResourceId()
	{
		return resourceId;
	}

	public void setResourceId(String resourceId)
	{
		this.resourceId = resourceId;
	}

	public Map<String, String> getTags()
	{
		return tags;
	}

	public void setTags(Map<String, String> tags)
	{
		this.tags = tags;
	}

}
