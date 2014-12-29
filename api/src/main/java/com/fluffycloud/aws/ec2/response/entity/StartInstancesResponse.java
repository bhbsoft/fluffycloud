package com.fluffycloud.aws.ec2.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class StartInstancesResponse
{
	@SerializedName("StartingInstances")
	private List<InstancesStatus> startInstances;

	public List<InstancesStatus> getStartInstances()
	{
		return startInstances;
	}

	public void setStartInstances(List<InstancesStatus> startInstances)
	{
		this.startInstances = startInstances;
	}

}
