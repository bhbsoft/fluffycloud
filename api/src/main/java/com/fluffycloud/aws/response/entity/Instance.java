package com.fluffycloud.aws.response.entity;

public class Instance
{
	private String KernelId;
	private String ImageId;
	private String InstanceId;
	private String InstanceType;
	private String RootDeviceType;

	public String getKernelId()
	{
		return KernelId;
	}

	public void setKernelId(String kernelId)
	{
		KernelId = kernelId;
	}

	public String getImageId()
	{
		return ImageId;
	}

	public void setImageId(String imageId)
	{
		ImageId = imageId;
	}

	public String getInstanceId()
	{
		return InstanceId;
	}

	public void setInstanceId(String instanceId)
	{
		InstanceId = instanceId;
	}

	public String getInstanceType()
	{
		return InstanceType;
	}

	public void setInstanceType(String instanceType)
	{
		InstanceType = instanceType;
	}

	public String getRootDeviceType()
	{
		return RootDeviceType;
	}

	public void setRootDeviceType(String rootDeviceType)
	{
		RootDeviceType = rootDeviceType;
	}
}
