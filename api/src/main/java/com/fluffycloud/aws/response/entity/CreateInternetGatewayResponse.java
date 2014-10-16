package com.fluffycloud.aws.response.entity;

import com.google.gson.annotations.SerializedName;

public class CreateInternetGatewayResponse extends BaseResponse
{
	@SerializedName("InternetGateway")
	private InternetGateway internetGateway;

	public InternetGateway getInternetGateway()
	{
		return internetGateway;
	}

	public void setInternetGateway(InternetGateway internetGateway)
	{
		this.internetGateway = internetGateway;
	}

}
