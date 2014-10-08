package com.fluffycloud.api.Iservice;

import java.io.IOException;

import com.fluffycloud.exceptions.FluffyCloudException;

public interface AWSService
{
	public String createScenario1() throws IOException, FluffyCloudException, InterruptedException;

	public String createScenario2() throws IOException, FluffyCloudException, InterruptedException;
}
