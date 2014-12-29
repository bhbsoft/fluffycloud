package com.fluffycloud.aws.ec2.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DescribeKeyPairsResponse
{
	@SerializedName("KeyPairs")
	private List<KeyPair> keyPairs;

	public List<KeyPair> getKeyPairs()
	{
		return keyPairs;
	}

	public void setKeyPairs(List<KeyPair> keyPairs)
	{
		this.keyPairs = keyPairs;
	}

}
