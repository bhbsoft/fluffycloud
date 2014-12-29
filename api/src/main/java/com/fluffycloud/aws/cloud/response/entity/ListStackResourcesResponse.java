package com.fluffycloud.aws.cloud.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ListStackResourcesResponse
{
	@SerializedName("StackResourceSummaries")
	private List<StackResourceSummary> stackResourceSummaries;
}
