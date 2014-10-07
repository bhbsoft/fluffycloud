package com.fluffycloud.aws.response.entity;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Rule
{
	@SerializedName("IpProtocol")
	private String ipProtocol;

	@SerializedName("FromPort")
	private String fromPort;

	@SerializedName("IpRanges")
	private ArrayList<IpRanges> ipRanges;

	@SerializedName("ToPort")
	private String toPort;

	public String getIpProtocol()
	{
		return ipProtocol;
	}

	public void setIpProtocol(String ipProtocol)
	{
		this.ipProtocol = ipProtocol;
	}

	public String getFromPort()
	{
		return fromPort;
	}

	public void setFromPort(String fromPort)
	{
		this.fromPort = fromPort;
	}

	public ArrayList<IpRanges> getIpRanges()
	{
		return ipRanges;
	}

	public void setIpRanges(ArrayList<IpRanges> ipRanges)
	{
		this.ipRanges = ipRanges;
	}

	public String getToPort()
	{
		return toPort;
	}

	public void setToPort(String toPort)
	{
		this.toPort = toPort;
	}

}
