package com.fluffycloud.aws.response.entity;

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
	private String securityGroups;

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

	public String getSecurityGroups()
	{
		return securityGroups;
	}

	public void setSecurityGroups(String securityGroups)
	{
		this.securityGroups = securityGroups;
	}

	public void setRootDeviceType(String rootDeviceType)
	{
		this.rootDeviceType = rootDeviceType;
	}

}
