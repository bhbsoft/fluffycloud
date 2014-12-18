package com.fluffycloud.api.request.entity;

import javax.validation.constraints.NotNull;

public class AddRuleRequest
{
	@NotNull
	private String cidr;

	@NotNull
	private String protocol;

	@NotNull
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
