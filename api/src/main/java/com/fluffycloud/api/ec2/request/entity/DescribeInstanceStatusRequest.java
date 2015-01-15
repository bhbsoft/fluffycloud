package com.fluffycloud.api.ec2.request.entity;

import javax.validation.constraints.NotNull;

public class DescribeInstanceStatusRequest
{
	@NotNull
	private String instanceId;

	public String getInstanceId()
	{
		return instanceId;
	}

	public void setInstanceId(String instanceId)
	{
		this.instanceId = instanceId;
	}
}