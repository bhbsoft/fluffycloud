package com.fluffycloud.aws.ec2.response.entity;

import com.google.gson.annotations.SerializedName;

public class CreateSecurityGroupResponse extends BaseResponse
{

	@SerializedName("GroupId")
	private String groupId;

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

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

}
