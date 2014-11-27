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

	@Override
	public String toString()
	{
		StringBuilder command = new StringBuilder(super.toString());

		if (null != this.tags && tags.size() > 0)
		{
			command.append("--tags ");
			for (String tag : tags.keySet())
			{
				command.append("Key=" + tag).append(" Value=" + tags.get(tag));
			}
		}
		return command.toString();
	}
}
