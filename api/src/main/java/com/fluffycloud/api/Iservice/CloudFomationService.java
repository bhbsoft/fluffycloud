package com.fluffycloud.api.Iservice;

import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.exceptions.FluffyCloudException;

public interface CloudFomationService
{
	String describeStacks(CommonRequestParams params) throws FluffyCloudException;

	String listStacks(CommonRequestParams params) throws FluffyCloudException;
}
