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

		if (templateFile.exists())
		{
			throw new FluffyCloudException("Template already exists. Please choose different name.");
		}

		try (FileWriter filewriter = new FileWriter(templateFile))
		{
			templateFile.createNewFile();
			filewriter.write(templateJson);
			logger.info("Template added.");
			return true;
		}
		catch (Exception exception)
		{
			logger.error("Error while adding stack template." + exception.getMessage());
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

		if (templateFile.exists())
		{
			throw new FluffyCloudException("Template already exists. Please choose different name.");
		}

		try
		{
			FileUtils.copyInputStreamToFile(inputTemplateFile.getInputStream(), templateFile);
			return true;
		}
		catch (IOException e)
		{
			throw new FluffyCloudException("Error while creating template file.");
		}
	}

	/**
	 * 
	 * @param templateParams
	 * @return
	 */
	public String getTemplateParamsAsCommand(Map<String, String> templateParams)
	{
		StringBuilder params = new StringBuilder();
		for (String paramName : templateParams.keySet())
		{
			params.append(" ParameterKey=" + paramName + "," + "ParameterValue=" + templateParams.get(paramName));
		}
		return params.toString();
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
