package com.fluffycloud.api.Iservice;

import java.util.ArrayList;

import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.exceptions.FluffyCloudException;

public interface AWSService
{
	public String createScenario1(CommonRequestParams params) throws FluffyCloudException, InterruptedException;

	public String createScenario2(CommonRequestParams params) throws FluffyCloudException, InterruptedException;

	public String addCommand() throws FluffyCloudException;

	public String describeVPCs(CommonRequestParams params) throws FluffyCloudException;

	public String describeInstances(CommonRequestParams params) throws FluffyCloudException;
	
	public String describeSecurityGroup(CommonRequestParams params) throws FluffyCloudException;

	public String startInstances(CommonRequestParams params, ArrayList<String> instanceIds) throws FluffyCloudException;

	public String stopInstances(CommonRequestParams params, ArrayList<String> instanceIds) throws FluffyCloudException;

	public String describeRouteTables(CommonRequestParams params) throws FluffyCloudException;

	public String describeSubnets(CommonRequestParams params) throws FluffyCloudException;

	public String describeTags(CommonRequestParams params) throws FluffyCloudException;
}
