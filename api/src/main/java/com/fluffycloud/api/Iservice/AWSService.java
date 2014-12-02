package com.fluffycloud.api.Iservice;

import com.fluffycloud.api.request.entity.CreateInstanceRequest;
import com.fluffycloud.api.request.entity.CreateSecurityGroupRequest;
import com.fluffycloud.api.request.entity.CreateSubnetRequest;
import com.fluffycloud.api.request.entity.CreateVpcRequest;
import com.fluffycloud.api.request.entity.ResourceTags;
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

	public String startInstances(CommonRequestParams params) throws FluffyCloudException;

	public String stopInstances(CommonRequestParams params) throws FluffyCloudException;

	public String describeRouteTables(CommonRequestParams params) throws FluffyCloudException;

	public String describeSubnets(CommonRequestParams params) throws FluffyCloudException;

	public String describeTags(CommonRequestParams params) throws FluffyCloudException;

	public String createVpc(CommonRequestParams params, CreateVpcRequest createVpcRequest) throws FluffyCloudException;

	public String addTags(CommonRequestParams params, ResourceTags createTagRequest) throws FluffyCloudException;

	public String createInstance(CommonRequestParams params, CreateInstanceRequest createInstanceRequest)
			throws FluffyCloudException;

	public String createSubnet(CommonRequestParams params, CreateSubnetRequest createSubnetRequest)
			throws FluffyCloudException;

	public String
			createSecurityGroup(CommonRequestParams params, CreateSecurityGroupRequest createSecurityGroupRequest)
					throws FluffyCloudException;
}
