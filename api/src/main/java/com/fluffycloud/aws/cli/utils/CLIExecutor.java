package com.fluffycloud.aws.cli.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CLIExecutor
{

	public static void main(String args[])
	{
		generateSWF();
	}

	public static boolean generateSWF()
	{
		try
		{
			File file = new File("D:\\FluffyCloud\\api\\src\\aws.sh");
			if (!file.exists())
			{
				file.createNewFile();
			}

			String fs = System.getProperty("file.separator");
			String command = System.getenv("cygwin") + fs + "sh aws.sh";
			Process p = Runtime.getRuntime().exec(command);

			Process process = Runtime.getRuntime().exec(command);
			InputStream is = process.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			String lastLine = null;
			while ((line = br.readLine()) != null)
			{
				if (line != null)
				{
					lastLine = line;
				}
			}

			System.out.println(lastLine);

		}
		catch (Exception exception)
		{
			System.out.println("Error executing batch job. " + exception.getMessage());
		}
		return true;
	}

}
