package com.fluffycloud.aws.response.entity;

import java.util.ArrayList;

public class RunInstanceResponse
{
	private ArrayList<Instance> Instances;

	public ArrayList<Instance> getInstances()
	{
		return Instances;
	}

	public void setInstances(ArrayList<Instance> instances)
	{
		Instances = instances;
	}

}
