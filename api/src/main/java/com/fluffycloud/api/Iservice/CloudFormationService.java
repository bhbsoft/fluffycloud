package com.fluffycloud.api.Iservice;

import com.fluffycloud.api.request.entity.DescribeStackEventsRequest;
import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.exceptions.FluffyCloudException;

public interface CloudFormationService
{
	String describeStacks(CommonRequestParams params) throws FluffyCloudException;

	String listStacks(CommonRequestParams params) throws FluffyCloudException;

	String describeStackEvents(CommonRequestParams params, DescribeStackEventsRequest describeStackEventsRequest)
			throws FluffyCloudException;
}
