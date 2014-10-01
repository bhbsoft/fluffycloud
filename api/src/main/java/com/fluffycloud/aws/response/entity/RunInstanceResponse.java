package com.fluffycloud.aws.response.entity;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class RunInstanceResponse extends BaseResponse
{
	@SerializedName("Instances")
	private ArrayList<Instance> instances;

	public ArrayList<Instance> getInstances()
	{
		return instances;
	}

	public void setInstances(ArrayList<Instance> instances)
	{
		this.instances = instances;
	}

}
