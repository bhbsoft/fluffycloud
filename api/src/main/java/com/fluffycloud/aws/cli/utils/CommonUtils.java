package com.fluffycloud.aws.cli.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fluffycloud.api.cloud.request.entity.AddTemplateRequest;
import com.fluffycloud.aws.constants.AppParams;
import com.fluffycloud.exceptions.FluffyCloudException;

@Component
public class CommonUtils
{
	final static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

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

	public boolean createTemplate(AddTemplateRequest addTemplateRequest) throws FluffyCloudException
	{
		File templateFile = new File(AppParams.TEMPLATEFOLDER.getValue() + addTemplateRequest.getTemplateName());

		if (templateFile.exists())
		{
			throw new FluffyCloudException("Template already exists. Please choose different name.");
		}

		try (FileWriter filewriter = new FileWriter(templateFile))
		{
			templateFile.createNewFile();
			filewriter.write(addTemplateRequest.getTemplateJson());
			logger.info("Template added.");
			return true;
		}
		catch (Exception exception)
		{
			logger.error("Error while adding stack template." + exception.getMessage());
			throw new FluffyCloudException(exception.getMessage());
		}
	}

	public boolean saveTemplate(AddTemplateRequest addTemplateRequest) throws FluffyCloudException
	{
		File templateFile = new File(AppParams.TEMPLATEFOLDER.getValue() + addTemplateRequest.getTemplateName());

		if (templateFile.exists())
		{
			throw new FluffyCloudException("Template already exists. Please choose different name.");
		}

		try
		{
			FileUtils.copyInputStreamToFile(addTemplateRequest.getTemplateFile().getInputStream(), templateFile);
			return true;
		}
		catch (IOException e)
		{
			throw new FluffyCloudException("Error while creating template file.");
		}
	}

	public void removeTempFiles(final String fileLocation)
	{
		File tempFile = new File(fileLocation);
		if (tempFile.exists())
		{
			tempFile.delete();
		}
	}

}
