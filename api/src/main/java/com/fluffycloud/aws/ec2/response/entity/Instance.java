package com.fluffycloud.aws.ec2.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Instance
{

	@SerializedName("KernelId")
	private String kernelId;
	@SerializedName("ImageId")
	private String imageId;
	@SerializedName("InstanceId")
	private String instanceId;
	@SerializedName("InstanceType")
	private String instanceType;
	@SerializedName("RootDeviceType")
	private String rootDeviceType;
	@SerializedName("VpcId")
	private String vpcId;
	@SerializedName("SecurityGroups")
	private List<SecurityGroup> securityGroups;
	@SerializedName("State")
	private State state;
	@SerializedName("StateReason")
	private State stateReason;

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public State getStateReason()
	{
		return stateReason;
	}

	public void setStateReason(State stateReason)
	{
		this.stateReason = stateReason;
	}

	public String getKernelId()
	{
		return kernelId;
	}

	public void setKernelId(String kernelId)
	{
		this.kernelId = kernelId;
	}

	public String getImageId()
	{
		return imageId;
	}

	public void setImageId(String imageId)
	{
		this.imageId = imageId;
	}

	public String getInstanceId()
	{
		return instanceId;
	}

	public void setInstanceId(String instanceId)
	{
		this.instanceId = instanceId;
	}

	public String getInstanceType()
	{
		return instanceType;
	}

	public void setInstanceType(String instanceType)
	{
		this.instanceType = instanceType;
	}

	public String getVpcId()
	{
		return vpcId;
	}

	public void setVpcId(String vpcId)
	{
		this.vpcId = vpcId;
	}

	public void setRootDeviceType(String rootDeviceType)
	{
		this.rootDeviceType = rootDeviceType;
	}

	public List<SecurityGroup> getSecurityGroups()
	{
		return securityGroups;
	}

	public void setSecurityGroups(List<SecurityGroup> securityGroups)
	{
		this.securityGroups = securityGroups;
	}

	public String getRootDeviceType()
	{
		return rootDeviceType;
	}

}
