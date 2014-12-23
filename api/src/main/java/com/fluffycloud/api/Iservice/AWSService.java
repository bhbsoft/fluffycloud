package com.fluffycloud.api.Iservice;

import com.fluffycloud.api.request.entity.CreateInstanceRequest;
import com.fluffycloud.api.request.entity.CreateSecurityGroupRequest;
import com.fluffycloud.api.request.entity.CreateSubnetRequest;
import com.fluffycloud.api.request.entity.CreateVpcRequest;
import com.fluffycloud.api.request.entity.DescribeInstanceStatusRequest;
import com.fluffycloud.api.request.entity.ResourceTags;
import com.fluffycloud.api.request.entity.SGRuleRequest;
import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.exceptions.FluffyCloudException;

public interface AWSService
{
	String createScenario1(CommonRequestParams params) throws FluffyCloudException, InterruptedException;

	String createScenario2(CommonRequestParams params) throws FluffyCloudException, InterruptedException;

	String addCommand() throws FluffyCloudException;

	String describeVPCs(CommonRequestParams params) throws FluffyCloudException;

	String describeInstances(CommonRequestParams params) throws FluffyCloudException;

	String describeSecurityGroup(CommonRequestParams params) throws FluffyCloudException;

	String startInstances(CommonRequestParams params) throws FluffyCloudException;

	String stopInstances(CommonRequestParams params) throws FluffyCloudException;

	String describeRouteTables(CommonRequestParams params) throws FluffyCloudException;

	String describeSubnets(CommonRequestParams params) throws FluffyCloudException;

	String describeTags(CommonRequestParams params) throws FluffyCloudException;

	String createVpc(CommonRequestParams params, CreateVpcRequest createVpcRequest) throws FluffyCloudException;

	String addTags(CommonRequestParams params, ResourceTags createTagRequest) throws FluffyCloudException;

	String createInstance(CommonRequestParams params, CreateInstanceRequest createInstanceRequest)
			throws FluffyCloudException;

	String createSubnet(CommonRequestParams params, CreateSubnetRequest createSubnetRequest)
			throws FluffyCloudException;

	String createSecurityGroup(CommonRequestParams params, CreateSecurityGroupRequest createSecurityGroupRequest)
			throws FluffyCloudException;

	String describeInstanceStatus(CommonRequestParams params,
			DescribeInstanceStatusRequest describeInstanceStatusRequest) throws FluffyCloudException;

	String describeKeyPairs(CommonRequestParams params) throws FluffyCloudException;

	String addIngressRule(CommonRequestParams params, SGRuleRequest addIngressRuleRequest) throws FluffyCloudException;

	String addEgressRule(CommonRequestParams params, SGRuleRequest addIngressRuleRequest) throws FluffyCloudException;

	String revokeEgressRule(CommonRequestParams params, SGRuleRequest revokeEgressRuleRequest)
			throws FluffyCloudException;

	String revokeIngressRule(CommonRequestParams params, SGRuleRequest revokeEgressRuleRequest)
			throws FluffyCloudException;

}
