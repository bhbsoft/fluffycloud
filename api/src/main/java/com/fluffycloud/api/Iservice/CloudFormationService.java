package com.fluffycloud.api.Iservice;

import java.util.Map;

import com.fluffycloud.api.cloud.request.entity.AddTemplateRequest;
import com.fluffycloud.api.cloud.request.entity.CreateStackRequest;
import com.fluffycloud.api.cloud.request.entity.DescribeStackEventsRequest;
import com.fluffycloud.api.cloud.request.entity.DescribeStackResourceRequest;
import com.fluffycloud.api.cloud.request.entity.DescribeStackResourcesRequest;
import com.fluffycloud.api.cloud.request.entity.DescribeStacksRequest;
import com.fluffycloud.api.cloud.request.entity.ListStackResourcesRequest;
import com.fluffycloud.api.cloud.request.entity.SetStackPolicyRequest;
import com.fluffycloud.api.cloud.request.entity.UpdateStackRequest;
import com.fluffycloud.api.cloud.request.entity.ValidateTemplateRequest;
import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.exceptions.FluffyCloudException;

public interface CloudFormationService
{
	String describeStacks(CommonRequestParams params, DescribeStacksRequest describeStacksRequest)
			throws FluffyCloudException;

	String listStacks(CommonRequestParams params) throws FluffyCloudException;

	String describeStackEvents(CommonRequestParams params, DescribeStackEventsRequest describeStackEventsRequest)
			throws FluffyCloudException;

	String listStackResources(CommonRequestParams params, ListStackResourcesRequest listStackResourcesRequest)
			throws FluffyCloudException;

	String describeStackResource(CommonRequestParams params, DescribeStackResourceRequest describeStackResourceRequest)
			throws FluffyCloudException;

	String describeStackResources(CommonRequestParams params,
			DescribeStackResourcesRequest describeStackResourcesRequest) throws FluffyCloudException;

	String createStack(CommonRequestParams params, CreateStackRequest createStackRequest) throws FluffyCloudException;

	boolean deleteStack(CommonRequestParams params, String stackName) throws FluffyCloudException;

	String getTemplate(CommonRequestParams params, String stackName) throws FluffyCloudException;

	boolean setStackPolicy(CommonRequestParams params, SetStackPolicyRequest setStackPolicyRequest)
			throws FluffyCloudException;

	String getStackPolicy(CommonRequestParams params, String stackName) throws FluffyCloudException;

	String updateStack(CommonRequestParams params, UpdateStackRequest updateStackRequest) throws FluffyCloudException;

	boolean cancelUpdateStack(CommonRequestParams params, String stackName) throws FluffyCloudException;

	String validateTemplate(CommonRequestParams params, ValidateTemplateRequest validateTemplateRequest)
			throws FluffyCloudException;

	Map<String, String> getStackTemplates(CommonRequestParams params) throws FluffyCloudException;

	boolean addTemplate(CommonRequestParams params, AddTemplateRequest addTemplateRequest) throws FluffyCloudException;
}