package com.fluffycloud.aws.entity;

import java.util.Map;

public class CreateTagCommand extends Command
{

	private Map<String, String> tags;

	public Map<String, String> getTags()
	{
		return tags;
	}

	public void setTags(Map<String, String> tags)
	{
		this.tags = tags;
	}

}
