package com.fluffycloud.api.Iservice;

import java.io.IOException;

import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.exceptions.FluffyCloudException;

public interface AWSService
{
	public String createScenario1(CommonRequestParams params) throws IOException, FluffyCloudException,
			InterruptedException;

	public String createScenario2(CommonRequestParams params) throws IOException, FluffyCloudException,
			InterruptedException;

	public String addCommand() throws IOException, FluffyCloudException, InterruptedException;
}
