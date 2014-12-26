package com.fluffycloud.api.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fluffycloud.api.Iservice.CloudFormationService;
import com.fluffycloud.api.request.entity.DescribeStackEventsRequest;
import com.fluffycloud.aws.cli.utils.CLIExecutor;
import com.fluffycloud.aws.constants.Action;
import com.fluffycloud.aws.constants.AppParams;
import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.aws.response.entity.DescribeStackEventsResponse;
import com.fluffycloud.aws.response.entity.DescribeStacksResponse;
import com.fluffycloud.aws.response.entity.ListStacksResponse;
import com.fluffycloud.exceptions.FluffyCloudException;
import com.google.gson.Gson;

@Component
class CloudFormationServiceImpl implements CloudFormationService
{
	final static Logger logger = LoggerFactory.getLogger(CloudFormationServiceImpl.class);

	@Autowired
	private CLIExecutor cliExecutor;

	@Override
	public String describeStacks(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			logger.info("Getting available stack details.");
			paramsToUdate.clear();
			final String describeStacksJsonResponse = cliExecutor.performAction(Action.DESCRIBESTACKS, paramsToUdate);
			logger.info("Got available stack details.");
			DescribeStacksResponse describeStacksResponse = gson.fromJson(describeStacksJsonResponse,
					DescribeStacksResponse.class);
			return gson.toJson(describeStacksResponse);
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
		Gson gson = new Gson();
		try
		{
			logger.info("Getting available stack details.");
			final String listStacksJsonResponse = cliExecutor.performAction(Action.LISTSTACKS, paramsToUdate);
			logger.info("Got available stack details.");
			ListStacksResponse listStacksResponse = gson.fromJson(listStacksJsonResponse, ListStacksResponse.class);
			return gson.toJson(listStacksResponse);
		}
		catch (Exception exception)
		{
			logger.error("Error while getting stack details");
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	@Override
	public String
			describeStackEvents(CommonRequestParams params, DescribeStackEventsRequest describeStackEventsRequest)
					throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			logger.info("getting stack events.");
			paramsToUdate.put(AppParams.STACKNAME.getValue(), describeStackEventsRequest.getStackName());
			if (null != describeStackEventsRequest.getMaxItems())
			{
				paramsToUdate.put(AppParams.MAXITEMS.getValue(), describeStackEventsRequest.getMaxItems().toString());
			}

			if (null != describeStackEventsRequest.getStartingToken())
			{
				paramsToUdate.put(AppParams.STARTINGTOKEN.getValue(), describeStackEventsRequest.getStartingToken());
			}
			final String describeStackEventsJsonResponse = cliExecutor.performAction(Action.DESCRIBESTACKEVENTS,
					paramsToUdate);
			logger.info("stack events.");
			DescribeStackEventsResponse describeStackEventsResponse = gson.fromJson(describeStackEventsJsonResponse,
					DescribeStackEventsResponse.class);
			return gson.toJson(describeStackEventsResponse);
		}
		catch (Exception exception)
		{
			logger.error("Error while getting stack events");
			throw new FluffyCloudException(exception.getMessage());
		}
	}
}
