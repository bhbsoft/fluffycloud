package com.fluffycloud.aws.cloud.response.entity;

import com.google.gson.annotations.SerializedName;

public class GetStackPolicyResponse
{
	@SerializedName("StackPolicyBody")
	private StackPolicyBody stackPolicyBody;

	public StackPolicyBody getStackPolicyBody()
	{
		return stackPolicyBody;
	}

	public void setStackPolicyBody(StackPolicyBody stackPolicyBody)
	{
		this.stackPolicyBody = stackPolicyBody;
	}
}
