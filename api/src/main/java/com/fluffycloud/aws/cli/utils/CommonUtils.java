package com.fluffycloud.aws.cli.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fluffycloud.aws.constants.AppParams;
import com.fluffycloud.exceptions.FluffyCloudException;

@Component
public class CommonUtils
{
	final static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * 
	 * @param contentType
	 * @param action
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String getResponse(final String contentType, final String action) throws FileNotFoundException, IOException
	{
		File file = null;

		if (contentType.equalsIgnoreCase("application/x-www-form-urlencoded"))
		{
			file = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "aws"
					+ File.separator + "XMLResponse" + File.separator + action + ".xml");

		}
		else if (contentType.equalsIgnoreCase("application/json"))
		{

			file = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "aws"
					+ File.separator + "JSONResponse" + File.separator + action + ".json");
		}

		Reader reader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(reader);
		StringBuilder sbJSONCommand = new StringBuilder();

		String s = null;
		while ((s = bufferedReader.readLine()) != null)
		{
			sbJSONCommand.append(s);
			sbJSONCommand.append("\n");
		}
		bufferedReader.close();
		reader.close();
		return sbJSONCommand.toString();
	}

	/**
	 * 
	 * @param templateName
	 * @param templateJson
	 * @return
	 * @throws FluffyCloudException
	 */
	public boolean createTemplate(final String templateName, final String templateJson) throws FluffyCloudException
	{
		File templateFile = new File(AppParams.TEMPLATEFOLDER.getValue() + templateName);

		logger.info("Adding template file.");
		return createNewFile(templateJson, templateFile);
	}

	/**
	 * 
	 * @param stackPolicyName
	 * @param stackPolicyJson
	 * @return
	 * @throws FluffyCloudException
	 */
	public boolean createStackPolicyFile(final String stackPolicyName, final String stackPolicyJson)
			throws FluffyCloudException
	{
		File stackPolicyFile = new File(AppParams.STACKPOLICYFOLDER.getValue() + stackPolicyName);
		logger.info("Adding stack policy file.");
		return createNewFile(stackPolicyJson, stackPolicyFile);
	}

	/**
	 * 
	 * @param jsonContent
	 * @param file
	 * @return
	 * @throws FluffyCloudException
	 */
	private boolean createNewFile(final String jsonContent, File file) throws FluffyCloudException
	{
		if (file.exists())
		{
			throw new FluffyCloudException("File already exists. Please choose different name.");
		}

		try (FileWriter filewriter = new FileWriter(file))
		{
			file.createNewFile();
			filewriter.write(jsonContent);
			logger.info("File added.");
			return true;
		}
		catch (Exception exception)
		{
			logger.error("Error while adding file." + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	/**
	 * 
	 * @param inputTemplateFile
	 * @param templateName
	 * @return
	 * @throws FluffyCloudException
	 */
	public boolean saveTemplate(MultipartFile inputTemplateFile, final String templateName) throws FluffyCloudException
	{
		File templateFile = new File(AppParams.TEMPLATEFOLDER.getValue() + templateName);
		logger.info("Saving template file.");
		return saveFile(inputTemplateFile, templateFile);
	}

	/**
	 * 
	 * @param inputStackFile
	 * @param stackName
	 * @return
	 * @throws FluffyCloudException
	 */
	public boolean saveStackPolicyFile(MultipartFile inputStackFile, final String stackName)
			throws FluffyCloudException
	{
		File stackFile = new File(AppParams.STACKPOLICYFOLDER.getValue() + stackName);
		logger.info("Saving stack policy during update file.");
		return saveFile(inputStackFile, stackFile);
	}

	/**
	 * 
	 * @param inputFile
	 * @param destinationFile
	 * @return
	 * @throws FluffyCloudException
	 */
	private boolean saveFile(MultipartFile inputFile, File destinationFile) throws FluffyCloudException
	{
		if (destinationFile.exists())
		{
			throw new FluffyCloudException("File already exists. Please choose different name.");
		}

		try
		{
			FileUtils.copyInputStreamToFile(inputFile.getInputStream(), destinationFile);
			return true;
		}
		catch (IOException e)
		{
			throw new FluffyCloudException("Error while creating file.");
		}
	}

	/**
	 * 
	 * @param templateParams
	 * @return
	 */
	public String getTemplateParamsAsCommand(String templateParams)
	{
		JacksonJsonParser js = new JacksonJsonParser();
		Map<String, Object> params = js.parseMap(templateParams);
		StringBuilder paramString = new StringBuilder();
		for (String paramName : params.keySet())
		{
			paramString.append(" ParameterKey=" + paramName + "," + "ParameterValue=" + params.get(paramName));
		}
		return paramString.toString();
	}

	/**
	 * 
	 * @param params
	 * @return
	 */
	public String getParamsAsCommand(Map<String, String> params)
	{
		StringBuilder paramString = new StringBuilder();
		for (String paramName : params.keySet())
		{
			paramString.append(" ParameterKey=" + paramName + "," + "ParameterValue=" + params.get(paramName));
		}
		return paramString.toString();
	}

	/**
	 * 
	 * @param templateName
	 * @return
	 */
	public String getTemplateBody(final String templateName)
	{
		return AppParams.TEMPLATEBODYCLILOCATION.getValue() + templateName;
	}

	public String getStackPolicyBody(final String stackPolicyName)
	{
		return AppParams.STACKPOLICYFOLDER.getValue() + stackPolicyName;
	}

	/**
	 * 
	 * @param fileLocation
	 */
	public void removeTempFiles(final String fileLocation)
	{
		logger.info("removing temporary files.");
		File tempFile = new File(fileLocation);
		if (tempFile.exists())
		{
			tempFile.delete();
		}
	}

}
