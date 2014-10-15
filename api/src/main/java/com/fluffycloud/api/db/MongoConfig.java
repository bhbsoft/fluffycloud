package com.fluffycloud.api.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;

@Component
@Configuration
@PropertySource(value = "classpath:db.properties")
public class MongoConfig
{
	@Autowired
	Environment env;

	public @Bean MongoClient mongo() throws Exception
	{
		return new MongoClient(env.getProperty("host"), Integer.parseInt(env.getProperty("port")));
	}

	public @Bean MongoTemplate mongoTemplate() throws Exception
	{
		return new MongoTemplate(mongo(), env.getProperty("FluffyCloudDB"));
	}

	public @Bean MongoOperations mongoOperations() throws Exception
	{
		return mongoTemplate();
	}
}