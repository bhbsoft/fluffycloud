package com.fluffycloud.aws.response.entity;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class InternetGateway
{

	@SerializedName("InternetGatewayId")
	private String internetGatewayId;

	@SerializedName("Attachments")
	private ArrayList<Attachments> attachments;

	@SerializedName("Tags")
	private ArrayList<String> tags;

	public String getInternetGatewayId()
	{
		return internetGatewayId;
	}

	public void setInternetGatewayId(String internetGatewayId)
	{
		this.internetGatewayId = internetGatewayId;
	}

	public ArrayList<Attachments> getAttachments()
	{
		return attachments;
	}

	public void setAttachments(ArrayList<Attachments> attachments)
	{
		this.attachments = attachments;
	}

	public ArrayList<String> getTags()
	{
		return tags;
	}

	public void setTags(ArrayList<String> tags)
	{
		this.tags = tags;
	}

}
