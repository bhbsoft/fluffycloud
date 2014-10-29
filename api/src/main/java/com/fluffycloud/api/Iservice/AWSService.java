package com.fluffycloud.api.Iservice;

import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.exceptions.FluffyCloudException;

public interface AWSService
{
	public String createScenario1(CommonRequestParams params) throws FluffyCloudException, InterruptedException;

	public String createScenario2(CommonRequestParams params) throws FluffyCloudException, InterruptedException;

	public String addCommand() throws FluffyCloudException;

	public String describeVPCs(CommonRequestParams params) throws FluffyCloudException;
}
