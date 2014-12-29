package com.fluffycloud.aws.ec2.response.entity;

import com.google.gson.annotations.SerializedName;

public class KeyPair
{
	@SerializedName("KeyName")
	private String keyName;

	@SerializedName("KeyFingerPrint")
	private String keyFingerPrint;

	public String getKeyName()
	{
		return keyName;
	}

	public void setKeyName(String keyName)
	{
		this.keyName = keyName;
	}

	public String getKeyFingerPrint()
	{
		return keyFingerPrint;
	}

	public void setKeyFingerPrint(String keyFingerPrint)
	{
		this.keyFingerPrint = keyFingerPrint;
	}

}
