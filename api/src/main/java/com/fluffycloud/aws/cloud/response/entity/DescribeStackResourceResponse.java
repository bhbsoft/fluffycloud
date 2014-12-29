package com.fluffycloud.aws.cloud.response.entity;

import com.google.gson.annotations.SerializedName;

public class DescribeStackResourceResponse
{
	@SerializedName("StackResourceDetail")
	private StackResourceDetail stackResourceDetail;

	public StackResourceDetail getStackResourceDetail()
	{
		return stackResourceDetail;
	}

	public void setStackResourceDetail(StackResourceDetail stackResourceDetail)
	{
		this.stackResourceDetail = stackResourceDetail;
	}
}
