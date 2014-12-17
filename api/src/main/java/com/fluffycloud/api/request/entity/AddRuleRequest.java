package com.fluffycloud.api.request.entity;

public class AddRuleRequest
{
	private String cidr;
	private String protocol;
	private String portRange;
	private String source;

	public String getCidr()
	{
		return cidr;
	}

	public void setCidr(String cidr)
	{
		this.cidr = cidr;
	}

	public String getProtocol()
	{
		return protocol;
	}

	public void setProtocol(String protocol)
	{
		this.protocol = protocol;
	}

	public String getPortRange()
	{
		return portRange;
	}

	public void setPortRange(String portRange)
	{
		this.portRange = portRange;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

}
