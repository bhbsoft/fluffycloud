package com.fluffycloud.api;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(value = "com.fluffycloud")
public class FluffyCloudApplication
{
	final static Logger logger = LoggerFactory.getLogger(FluffyCloudApplication.class);

	public static void main(String[] args)
	{

		ApplicationContext ctx = SpringApplication.run(FluffyCloudApplication.class, args);

		logger.info("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);

		for (String beanName : beanNames)
		{
			logger.info(beanName);
		}
	}
}
