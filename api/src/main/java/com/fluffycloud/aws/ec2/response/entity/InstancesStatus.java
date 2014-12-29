package com.fluffycloud.aws.ec2.response.entity;

import com.google.gson.annotations.SerializedName;

public class InstancesStatus
{
	@SerializedName("InstanceId")
	private String instanceId;

	@SerializedName("PreviousState")
	private State previousState;

	@SerializedName("CurrentState")
	private State currentState;

	public String getInstanceId()
	{
		return instanceId;
	}

	public void setInstanceId(String instanceId)
	{
		this.instanceId = instanceId;
	}

	public State getPreviousState()
	{
		return previousState;
	}

	public void setPreviousState(State previousState)
	{
		this.previousState = previousState;
	}

	public State getCurrentState()
	{
		return currentState;
	}

	public void setCurrentState(State currentState)
	{
		this.currentState = currentState;
	}

}
