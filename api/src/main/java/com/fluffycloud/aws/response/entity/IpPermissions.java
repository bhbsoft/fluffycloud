package com.fluffycloud.aws.response.entity;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class IpPermissions
{
	@SerializedName("InBoundRules")
	private ArrayList<Rule> inboundRules;

	@SerializedName("outBoundRules")
	private ArrayList<Rule> outboundRules;

	public ArrayList<Rule> getInboundRules()
	{
		return inboundRules;
	}

	public void setInboundRules(ArrayList<Rule> inboundRules)
	{
		this.inboundRules = inboundRules;
	}

	public ArrayList<Rule> getOutboundRules()
	{
		return outboundRules;
	}

	public void setOutboundRules(ArrayList<Rule> outboundRules)
	{
		this.outboundRules = outboundRules;
	}

}
