package com.fluffycloud.aws.response.entity;

import com.google.gson.annotations.SerializedName;

public class InstanceStatuses
{
	@SerializedName("InstanceState")
	private InstanceState instanceState;

	public InstanceState getInstanceState()
	{
		return instanceState;
	}

	public void setInstanceState(InstanceState instanceState)
	{
		this.instanceState = instanceState;
	}

}
