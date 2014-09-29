package com.fluffycloud.aws.cli.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:db.properties")
public class PropertyReader
{
	@Autowired
	Environment env;

	public Environment getEnv()
	{
		return env;
	}

}
