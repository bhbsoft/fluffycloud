package com.fluffycloud.aws.response.entity;

public class AllocateAddressReponse
{
	private String PublicIp;
	private String Domain;

	public String getPublicIp()
	{
		return PublicIp;
	}

	public void setPublicIp(String publicIp)
	{
		PublicIp = publicIp;
	}

	public String getDomain()
	{
		return Domain;
	}

	public void setDomain(String domain)
	{
		Domain = domain;
	}
}
