package com.fluffycloud.api.service;

import static java.lang.System.currentTimeMillis;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
import com.fluffycloud.aws.cli.utils.CommonUtils;
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
import com.google.gson.JsonParser;

@Component
class CloudFormationServiceImpl implements CloudFormationService
{
	final static Logger logger = LoggerFactory.getLogger(CloudFormationServiceImpl.class);

	@Autowired
	private CLIExecutor cliExecutor;

	@Autowired
	private CommonUtils commonUtils;

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
			paramsToUdate.put(AppParams.TEMPLATEBODY.getValue(),
					commonUtils.getTemplateBody(createStackRequest.getTemplateName()));
			paramsToUdate.put(AppParams.PARAMETERS.getValue(),
					commonUtils.getParamsAsCommand(createStackRequest.getTemplateParams()));

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
		Map<String, String> paramsToUdate = new HashMap<String, String>();
		String templateName = updateStackRequest.getTemplateName();
		final String stackPolicyName = AppParams.STACKPOLICYDURINGUPDATE.getValue() + currentTimeMillis()
				+ AppParams.JSONXTSN.getValue();
		boolean temporaryTemplateCreated = false;
		boolean stackPolicyDuringUpdateFlag = false;
		try
		{
			if (null == templateName)
			{
				logger.info("Getting existing stack template.");
				templateName = updateStackRequest.getStackName() + currentTimeMillis()
						+ AppParams.TEMPLATEXTSN.getValue();
				createTemporaryTemplate(params, updateStackRequest, templateName);
				paramsToUdate.put(AppParams.TEMPLATEBODY.getValue(), commonUtils.getTemplateBody(templateName));
				temporaryTemplateCreated = true;
			}
			else
			{
				paramsToUdate.put(AppParams.TEMPLATEBODY.getValue(),
						commonUtils.getTemplateBody(updateStackRequest.getTemplateName()));
			}

			stackPolicyDuringUpdateFlag = null != updateStackRequest.getStackPolicyDuringUpdateFile() ? commonUtils
					.saveStackPolicyFile(updateStackRequest.getStackPolicyDuringUpdateFile(), stackPolicyName)
					: null != updateStackRequest.getStackPolicyDuringUpdateBody() ? commonUtils.createStackPolicyFile(
							stackPolicyName, updateStackRequest.getStackPolicyDuringUpdateBody()) : false;

			if (stackPolicyDuringUpdateFlag)
			{
				paramsToUdate.put(AppParams.STACKPOLICYDURINGUPDATE.getValue(),
						commonUtils.getStackPolicyBody(stackPolicyName));
			}

			logger.info("updating stack.");
			paramsToUdate.put(AppParams.STACKNAME.getValue(), updateStackRequest.getStackName());
			paramsToUdate.put(AppParams.PARAMETERS.getValue(),
					commonUtils.getTemplateParamsAsCommand(updateStackRequest.getTemplateParams()));

			final String updateStackJsonResponse = cliExecutor.performAction(Action.UPDATESTACK, paramsToUdate);
			logger.info("stack updated.");
			deleteTemporaryFiles(templateName, stackPolicyName, temporaryTemplateCreated, stackPolicyDuringUpdateFlag);
			return updateStackJsonResponse;
		}
		catch (Exception exception)
		{
			deleteTemporaryFiles(templateName, stackPolicyName, temporaryTemplateCreated, stackPolicyDuringUpdateFlag);
			logger.error("Error while updating stack." + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	private void deleteTemporaryFiles(String templateName, final String stackPolicyName,
			boolean temporaryTemplateCreated, boolean stackPolicyDuringUpdateFlag)
	{
		if (temporaryTemplateCreated)
		{
			commonUtils.removeTempFiles(AppParams.TEMPLATEFOLDER.getValue() + templateName);
		}

		if (stackPolicyDuringUpdateFlag)
		{
			commonUtils.removeTempFiles(AppParams.STACKPOLICYFOLDER.getValue() + stackPolicyName);
		}
	}

	private void createTemporaryTemplate(CommonRequestParams params, UpdateStackRequest updateStackRequest,
			String templateName) throws FluffyCloudException
	{
		commonUtils.createTemplate(
				templateName,
				new JsonParser().parse(getTemplate(params, updateStackRequest.getStackName())).getAsJsonObject()
						.get("TemplateBody").toString());
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
			paramsToUdate.put(AppParams.TEMPLATEBODY.getValue(),
					commonUtils.getTemplateBody(validateTemplateRequest.getTemplateName()));
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
	public String addTemplate(CommonRequestParams params, AddTemplateRequest addTemplateRequest)
			throws FluffyCloudException
	{
		try
		{
			logger.info("Adding stack template.");
			boolean flag = null != addTemplateRequest.getTemplateFile() ? commonUtils.saveTemplate(
					addTemplateRequest.getTemplateFile(), addTemplateRequest.getTemplateName()) : commonUtils
					.createTemplate(addTemplateRequest.getTemplateName(), addTemplateRequest.getTemplateJson());

			if (!flag)
			{
				throw new FluffyCloudException("Error while save/create template file.");
			}

			ValidateTemplateRequest validateTemplateRequest = new ValidateTemplateRequest();
			validateTemplateRequest.setTemplateName(addTemplateRequest.getTemplateName());

			return addTemplateRequest.isValidateOnly() ? validateOnlyTemplate(params, validateTemplateRequest)
					: validateTemplate(params, validateTemplateRequest);
		}
		catch (Exception exception)
		{
			logger.error("removing temporary file.");
			commonUtils.removeTempFiles(AppParams.TEMPLATEFOLDER.getValue() + addTemplateRequest.getTemplateName());
			logger.error("Error adding/validating template." + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	public String validateOnlyTemplate(CommonRequestParams params, ValidateTemplateRequest validateTemplateRequest)
			throws FluffyCloudException
	{
		logger.info("Validating Template.");
		String validationResponse = validateTemplate(params, validateTemplateRequest);
		logger.info("validated template.");

		File templateFile = new File(AppParams.TEMPLATEFOLDER.getValue() + validateTemplateRequest.getTemplateName());
		templateFile.delete();
		return validationResponse;
	}

}
