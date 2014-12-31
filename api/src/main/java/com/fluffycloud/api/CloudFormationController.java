package com.fluffycloud.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fluffycloud.api.Iservice.CloudFormationService;
import com.fluffycloud.api.cloud.request.entity.DescribeStackEventsRequest;
import com.fluffycloud.api.cloud.request.entity.DescribeStackResourceRequest;
import com.fluffycloud.api.cloud.request.entity.DescribeStackResourcesRequest;
import com.fluffycloud.api.cloud.request.entity.ListStackResourcesRequest;
import com.fluffycloud.api.ec2.request.entity.CreateStackRequest;
import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.exceptions.FluffyCloudException;

@RestController
@RequestMapping("/aws/cloudformation/")
public class CloudFormationController
{
	@Autowired
	CloudFormationService cloudFormationService;

	@RequestMapping(value = "describestacks", method = GET)
	public String describeStacks(@Valid CommonRequestParams params) throws FluffyCloudException
	{
		return cloudFormationService.describeStacks(params);
	}

	@RequestMapping(value = "liststacks", method = GET)
	public String listStacks(@Valid CommonRequestParams params) throws FluffyCloudException
	{
		return cloudFormationService.listStacks(params);
	}

	@RequestMapping(value = "describestackevents", method = GET)
	public String describeStackEvents(@Valid CommonRequestParams params,
			@Valid DescribeStackEventsRequest describeStackEventsRequest) throws FluffyCloudException
	{
		return cloudFormationService.describeStackEvents(params, describeStackEventsRequest);
	}

	@RequestMapping(value = "liststackresources", method = GET)
	public String listStackResources(@Valid CommonRequestParams params,
			@Valid ListStackResourcesRequest listStackResourcesRequest) throws FluffyCloudException
	{
		return cloudFormationService.listStackResources(params, listStackResourcesRequest);
	}

	@RequestMapping(value = "describestackresource", method = GET)
	public String describeStackResource(@Valid CommonRequestParams params,
			@Valid DescribeStackResourceRequest describeStackResourceRequest) throws FluffyCloudException
	{
		return cloudFormationService.describeStackResource(params, describeStackResourceRequest);
	}

	@RequestMapping(value = "describestackresources", method = GET)
	public String describeStackResources(@Valid CommonRequestParams params,
			@Valid DescribeStackResourcesRequest describeStackResourcesRequest) throws FluffyCloudException
	{
		return cloudFormationService.describeStackResources(params, describeStackResourcesRequest);
	}

	@RequestMapping(value = "createstack", method = POST)
	public String createStack(@Valid CommonRequestParams params, @RequestBody CreateStackRequest createStackRequest)
			throws FluffyCloudException
	{
		return cloudFormationService.createStack(params, createStackRequest);
	}

	@RequestMapping(value = "deletestack", method = GET)
	public boolean deleteStack(@Valid CommonRequestParams params, @RequestParam(required = true) String stackName)
			throws FluffyCloudException
	{
		return cloudFormationService.deleteStack(params, stackName);
	}

	@RequestMapping(value = "gettemplate", method = GET)
	public String getTemplate(@Valid CommonRequestParams params, @RequestParam(required = true) String stackName)
			throws FluffyCloudException
	{
		return cloudFormationService.getTemplate(params, stackName);
	}
}
