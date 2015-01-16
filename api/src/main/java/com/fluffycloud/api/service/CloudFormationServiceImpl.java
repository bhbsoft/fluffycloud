package com.fluffycloud.api.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fluffycloud.api.Iservice.CloudFormationService;
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
import com.fluffycloud.aws.cli.utils.CLIExecutor;
import com.fluffycloud.aws.cli.utils.TemplateFileFilter;
import com.fluffycloud.aws.cloud.response.entity.DescribeStackEventsResponse;
import com.fluffycloud.aws.cloud.response.entity.DescribeStackResourceResponse;
import com.fluffycloud.aws.cloud.response.entity.DescribeStackResourcesResponse;
import com.fluffycloud.aws.cloud.response.entity.DescribeStacksResponse;
import com.fluffycloud.aws.cloud.response.entity.ListStackResourcesResponse;
import com.fluffycloud.aws.cloud.response.entity.ListStacksResponse;
import com.fluffycloud.aws.cloud.response.entity.ValidateTemplateResponse;
import com.fluffycloud.aws.constants.Action;
import com.fluffycloud.aws.constants.AppParams;
import com.fluffycloud.aws.entity.CommonRequestParams;
import com.fluffycloud.exceptions.FluffyCloudException;
import com.google.gson.Gson;

@Component
class CloudFormationServiceImpl implements CloudFormationService
{
	final static Logger logger = LoggerFactory.getLogger(CloudFormationServiceImpl.class);

	@Autowired
	private CLIExecutor cliExecutor;

	@Override
	public String describeStacks(CommonRequestParams params, DescribeStacksRequest describeStacksRequest)
			throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			logger.info("Getting available stack details.");
			if (null != describeStacksRequest.getStackName())
			{
				paramsToUdate.put(AppParams.STACKNAME.getValue(), describeStacksRequest.getStackName());
			}

			final String describeStacksJsonResponse = cliExecutor.performAction(Action.DESCRIBESTACKS, paramsToUdate);
			logger.info("Got available stack details.");
			DescribeStacksResponse describeStacksResponse = gson.fromJson(describeStacksJsonResponse,
					DescribeStacksResponse.class);
			return gson.toJson(describeStacksResponse);
		}
		catch (Exception exception)
		{
			logger.error("Error while getting stack details" + exception.getMessage());
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
			logger.error("Error while getting stack details" + exception.getMessage());
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
			logger.error("Error while getting stack events" + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public String listStackResources(CommonRequestParams params, ListStackResourcesRequest listStackResourcesRequest)
			throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			logger.info("getting stack resources.");
			paramsToUdate.put(AppParams.STACKNAME.getValue(), listStackResourcesRequest.getStackName());
			if (null != listStackResourcesRequest.getMaxItems())
			{
				paramsToUdate.put(AppParams.MAXITEMS.getValue(), listStackResourcesRequest.getMaxItems().toString());
			}

			if (null != listStackResourcesRequest.getStartingToken())
			{
				paramsToUdate.put(AppParams.STARTINGTOKEN.getValue(), listStackResourcesRequest.getStartingToken());
			}
			final String listStackResourcesJsonResponse = cliExecutor.performAction(Action.LISTSTACKRESOURCES,
					paramsToUdate);
			logger.info("stack resources.");
			ListStackResourcesResponse listStackResourcesResponse = gson.fromJson(listStackResourcesJsonResponse,
					ListStackResourcesResponse.class);
			return gson.toJson(listStackResourcesResponse);
		}
		catch (Exception exception)
		{
			logger.error("Error while getting stack resources" + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public String describeStackResource(CommonRequestParams params,
			DescribeStackResourceRequest describeStackResourceRequest) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			logger.info("getting stack resource.");
			paramsToUdate.put(AppParams.STACKNAME.getValue(), describeStackResourceRequest.getStackName());
			paramsToUdate.put(AppParams.LOGICALRESOURCEID.getValue(),
					describeStackResourceRequest.getLogicalResourceId());

			final String describeStackResourceJsonResponse = cliExecutor.performAction(Action.DESCRIBESTACKRESOURCE,
					paramsToUdate);
			logger.info("stack resource.");
			DescribeStackResourceResponse describeStackResourceResponse = gson.fromJson(
					describeStackResourceJsonResponse, DescribeStackResourceResponse.class);
			return gson.toJson(describeStackResourceResponse);
		}
		catch (Exception exception)
		{
			logger.error("Error while getting stack resources" + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public String describeStackResources(CommonRequestParams params,
			DescribeStackResourcesRequest describeStackResourcesRequest) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			logger.info("getting stack resources.");
			if (null != describeStackResourcesRequest.getPhysicalResourceId())
			{
				paramsToUdate.put(AppParams.PHYSICALRESOURCEID.getValue(),
						describeStackResourcesRequest.getPhysicalResourceId());
			}
			else if (null != describeStackResourcesRequest.getStackName())
			{
				paramsToUdate.put(AppParams.STACKNAME.getValue(), describeStackResourcesRequest.getStackName());
			}

			if (null != describeStackResourcesRequest.getLogicalResourceId())
			{
				paramsToUdate.put(AppParams.LOGICALRESOURCEID.getValue(),
						describeStackResourcesRequest.getLogicalResourceId());
			}

			final String describeStackResourcesJsonResponse = cliExecutor.performAction(Action.DESCRIBESTACKRESOURCES,
					paramsToUdate);
			logger.info("stack resource.");
			DescribeStackResourcesResponse describeStackResourcesResponse = gson.fromJson(
					describeStackResourcesJsonResponse, DescribeStackResourcesResponse.class);
			return gson.toJson(describeStackResourcesResponse);
		}
		catch (Exception exception)
		{
			logger.error("Error while getting stack resources" + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public String createStack(CommonRequestParams params, CreateStackRequest createStackRequest)
			throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		try
		{
			logger.info("creating stack.");
			paramsToUdate.put(AppParams.STACKNAME.getValue(), createStackRequest.getStackName());
			paramsToUdate.put(AppParams.TEMPLATEBODY.getValue(), createStackRequest.getTemplateBody());
			paramsToUdate.put(AppParams.PARAMETERS.getValue(), createStackRequest.getTemplateParamsAsCommand());

			final String createStackJsonResponse = cliExecutor.performAction(Action.CREATESTACK, paramsToUdate);
			logger.info("stack created.");
			return createStackJsonResponse;
		}
		catch (Exception exception)
		{
			logger.error("Error while creating stack" + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public boolean deleteStack(CommonRequestParams params, final String stackName) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		try
		{
			logger.info("deleting stack.");
			paramsToUdate.put(AppParams.STACKNAME.getValue(), stackName);
			cliExecutor.performAction(Action.DELETESTACK, paramsToUdate);
			logger.info("stack deleted.");
			return true;
		}
		catch (Exception exception)
		{
			logger.error("Error while deleting stack " + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public String getTemplate(CommonRequestParams params, final String stackName) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		try
		{
			logger.info("getting stack template.");
			paramsToUdate.put(AppParams.STACKNAME.getValue(), stackName);
			String getStackTemplateJsonResponse = cliExecutor.performAction(Action.GETTEMPLATE, paramsToUdate);
			logger.info("stack template.");
			return getStackTemplateJsonResponse;
		}
		catch (Exception exception)
		{
			logger.error("Error while getting stack template." + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public boolean setStackPolicy(CommonRequestParams params, SetStackPolicyRequest setStackPolicyRequest)
			throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		try
		{
			logger.info("setting stack policy.");
			paramsToUdate.put(AppParams.STACKNAME.getValue(), setStackPolicyRequest.getStackName());
			paramsToUdate.put(AppParams.STACKPOLICYBODY.getValue(), setStackPolicyRequest.getStackPolicyBody());

			cliExecutor.performAction(Action.SETSTACKPOLICY, paramsToUdate);
			logger.info("policy added.");
			return true;
		}
		catch (Exception exception)
		{
			logger.error("Error while setting stack policy." + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public String getStackPolicy(CommonRequestParams params, final String stackName) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		// Gson gson = new Gson();
		try
		{
			logger.info("getting stack policy.");
			paramsToUdate.put(AppParams.STACKNAME.getValue(), stackName);
			String getStackPolicyJsonResponse = cliExecutor.performAction(Action.GETSTACKPOLICY, paramsToUdate);
			// TODO mapping issue
			// GetStackPolicyResponse getStackPolicyResponse =
			// gson.fromJson(getStackPolicyJsonResponse,
			// GetStackPolicyResponse.class);
			logger.info("stack policy.");
			return getStackPolicyJsonResponse;
		}
		catch (Exception exception)
		{
			logger.error("Error while getting stack policy." + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public String updateStack(CommonRequestParams params, UpdateStackRequest updateStackRequest)
			throws FluffyCloudException
	{
		// Map<String, String> paramsToUdate = new HashMap<String, String>();
		// Gson gson = new Gson();

		try
		{
			logger.info("updating stack policy.");
			// TODO issue with use-previous-template option
			logger.info("stack policy updated.");
			return "IN PROGRESS";
		}
		catch (Exception exception)
		{
			logger.error("Error while updating stack." + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	@Override
	public boolean cancelUpdateStack(CommonRequestParams params, String stackName) throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		try
		{
			logger.info("cancelling stack update.");
			paramsToUdate.put(AppParams.STACKNAME.getValue(), stackName);
			cliExecutor.performAction(Action.CREATESTACK, paramsToUdate);
			logger.info("update cancelled.");
			return true;
		}
		catch (Exception exception)
		{
			logger.error("Error while cancelling update stack" + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public String validateTemplate(CommonRequestParams params, ValidateTemplateRequest validateTemplateRequest)
			throws FluffyCloudException
	{
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		Gson gson = new Gson();
		try
		{
			logger.info("validating template.");
			paramsToUdate.put(AppParams.TEMPLATEBODY.getValue(), validateTemplateRequest.getTemplateBody());

			String validateTemplateJsonResponse = cliExecutor.performAction(Action.VALIDATETEMPLATE, paramsToUdate);
			ValidateTemplateResponse validateTemplateResponse = gson.fromJson(validateTemplateJsonResponse,
					ValidateTemplateResponse.class);
			logger.info("template validated");
			return gson.toJson(validateTemplateResponse);
		}
		catch (Exception exception)
		{
			logger.error("Error while validating template" + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	@Override
	public Map<String, String> getStackTemplates(CommonRequestParams params) throws FluffyCloudException
	{
		Map<String, String> templates = new HashMap<String, String>();
		try
		{
			logger.info("Getting stack templates.");
			File templatesLocation = new File(AppParams.TEMPLATEFOLDER.getValue());
			if (templatesLocation.exists() && templatesLocation.isDirectory())
			{
				for (File file : templatesLocation.listFiles(new TemplateFileFilter()))
				{
					templates.put(file.getName(), cliExecutor.getJsonFromFile(file));
				}
			}
		}
		catch (Exception exception)
		{
			logger.error("Error while getting stack templates." + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
		return templates;
	}

	@Override
	public boolean addTemplate(CommonRequestParams params, AddTemplateRequest addTemplateRequest)
			throws FluffyCloudException
	{
		logger.info("Adding stack template.");
		if (null != addTemplateRequest.getTemplateFile())
		{
			File templateFile = new File(addTemplateRequest.getTemplateFile().getOriginalFilename());
			try
			{
				FileUtils.copyInputStreamToFile(addTemplateRequest.getTemplateFile().getInputStream(), templateFile);
				FileUtils.copyFileToDirectory(templateFile, new File(AppParams.TEMPLATEFOLDER.getValue()));
			}
			catch (IOException e)
			{
				throw new FluffyCloudException("Template already exists. Please choose different name.");
			}

		}
		else
		{
			File templateFile = new File(AppParams.TEMPLATEFOLDER.getValue() + addTemplateRequest.getTemplateName()
					+ AppParams.TEMPLATEXTSN.getValue());

			if (templateFile.exists())
			{
				throw new FluffyCloudException("Template already exists. Please choose different name.");
			}

			try (FileWriter filewriter = new FileWriter(templateFile))
			{
				templateFile.createNewFile();
				filewriter.write(addTemplateRequest.getTemplateJson());
				logger.info("Template added.");
			}
			catch (Exception exception)
			{
				logger.error("Error while adding stack template." + exception.getMessage());
				throw new FluffyCloudException(exception.getMessage());
			}

		}
		logger.info("Validating Template.");
		ValidateTemplateRequest validateTemplateRequest = new ValidateTemplateRequest();
		validateTemplateRequest.setTemplateName(addTemplateRequest.getTemplateName());
		validateTemplate(params, validateTemplateRequest);
		logger.info("validated template.");
		return true;

	}
}
