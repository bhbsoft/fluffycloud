package com.fluffycloud.aws.cli.utils;

import java.io.File;
import java.io.FilenameFilter;

public class TemplateFileFilter implements FilenameFilter
{
	final String TEMPLATE = ".template";

	@Override
	public boolean accept(File dir, String name)
	{
		return name.endsWith(TEMPLATE);
	}
}
