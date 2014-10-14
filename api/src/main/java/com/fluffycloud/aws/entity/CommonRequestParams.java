package com.fluffycloud.aws.entity;

public class CommonRequestParams
{

	private String accessKey;

	private String accessKeyId;

	private String region;

	public String getAccessKey()
	{
		return accessKey;
	}

	public void setAccessKey(String accessKey)
	{
		this.accessKey = accessKey;
	}

	public String getAccessKeyId()
	{
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId)
	{
		this.accessKeyId = accessKeyId;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	@Override
	public String toString()
	{
		return "CommonRequestParams [accessKey=" + accessKey + ", accessKeyId=" + accessKeyId + ", region=" + region
				+ "]";
	}

}
