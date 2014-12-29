package com.fluffycloud.aws.ec2.response.entity;

import com.google.gson.annotations.SerializedName;

public class ResponseFlag extends BaseResponse
{
	@SerializedName("return")
	private boolean actionPerformed;

	public boolean isActionPerformed()
	{
		return actionPerformed;
	}

	public void setActionPerformed(boolean actionPerformed)
	{
		this.actionPerformed = actionPerformed;
	}
}
