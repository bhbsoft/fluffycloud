package com.fluffycloud.aws.response.entity;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class DescribeInstanceStatusResponse extends BaseResponse
{

	@SerializedName("InstanceStatuses")
	private ArrayList<InstanceStatuses> instanceStatuses;

	public ArrayList<InstanceStatuses> getInstanceStatuses()
	{
		return instanceStatuses;
	}

	public void setInstanceStatuses(ArrayList<InstanceStatuses> instanceStatuses)
	{
		this.instanceStatuses = instanceStatuses;
	}
}
