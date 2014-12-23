package com.fluffycloud.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fluffycloud.api.Iservice.CloudFomationService;
import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.exceptions.FluffyCloudException;

@RestController
@RequestMapping("/aws/cloudformation/")
public class CloudFormationController
{
	@Autowired
	CloudFomationService cloudFomationService;

	@RequestMapping(value = "describestacks", method = GET)
	public String describeStacks(@Valid CommonRequestParams params) throws FluffyCloudException
	{
		return cloudFomationService.describeStacks(params);
	}

	@RequestMapping(value = "liststacks", method = GET)
	public String listStacks(@Valid CommonRequestParams params) throws FluffyCloudException
	{
		return cloudFomationService.listStacks(params);
	}
}
