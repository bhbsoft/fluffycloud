package com.fluffycloud.aws.cloud.response.entity;

import com.google.gson.annotations.SerializedName;

public class GetTemplateResponse
{
	@SerializedName("TemplateBody")
	private String templateBody;

	public String getTemplateBody()
	{
		return templateBody;
	}

	public void setTemplateBody(String templateBody)
	{
		this.templateBody = templateBody;
	}

}
