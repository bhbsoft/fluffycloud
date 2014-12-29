package com.fluffycloud.aws.ec2.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class StopInstancesResponse
{
	@SerializedName("StoppingInstances")
	private List<InstancesStatus> stopInstances;

	public List<InstancesStatus> getStopInstances()
	{
		return stopInstances;
	}

	public void setStopInstances(List<InstancesStatus> stopInstances)
	{
		this.stopInstances = stopInstances;
	}

}
