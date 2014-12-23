package com.fluffycloud.api.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fluffycloud.api.Iservice.CloudFomationService;
import com.fluffycloud.aws.cli.utils.CLIExecutor;
import com.fluffycloud.aws.constants.Action;
import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.exceptions.FluffyCloudException;

@Component
class CloudFormationServiceImpl implements CloudFomationService
{
	final static Logger logger = LoggerFactory.getLogger(CloudFormationServiceImpl.class);

	@Autowired
	private CLIExecutor cliExecutor;

	@Override
	public String describeStacks(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();

		try
		{
			logger.info("Getting available stack details.");
			paramsToUdate.clear();
			final String describeVPCsJsonResponse = cliExecutor.performAction(Action.DESCRIBESTACKS, paramsToUdate);
			logger.info("Got available stack details.");
			return describeVPCsJsonResponse;
		}
		catch (Exception exception)
		{
			logger.error("Error while getting stack details");
			throw new FluffyCloudException(exception.getMessage());
		}

	}
	
	@Override
	public String listStacks(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();

		try
		{
			logger.info("Getting available stack details.");
			paramsToUdate.clear();
			final String describeVPCsJsonResponse = cliExecutor.performAction(Action.LISTSTACKS, paramsToUdate);
			logger.info("Got available stack details.");
			return describeVPCsJsonResponse;
		}
		catch (Exception exception)
		{
			logger.error("Error while getting stack details");
			throw new FluffyCloudException(exception.getMessage());
		}

	}

}
