package com.fluffycloud.api;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
@EnableAutoConfiguration
@ComponentScan(value = "com.fluffycloud")
public class FluffyCloudApplication
{
	final static Logger logger = LoggerFactory.getLogger(FluffyCloudApplication.class);

	public static void main(String[] args) throws UnknownHostException, IOException
	{

		ApplicationContext ctx = SpringApplication.run(FluffyCloudApplication.class, args);

		logger.info("Let's inspect the beans provided by Spring Boot:");

		MongodStarter starter = MongodStarter.getDefaultInstance();
		int port = 6080;
		IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.net(new Net(port, Network.localhostIsIPv6())).build();

		 MongodExecutable mongodExecutable = null;
		  
		        mongodExecutable = starter.prepare(mongodConfig);
		        MongodProcess mongod = mongodExecutable.start();

		        MongoClient mongo = new MongoClient("localhost", port);
		        DB db = mongo.getDB("test");
		        DBCollection col = db.createCollection("testCol", new BasicDBObject());
		        col.save(new BasicDBObject("testDoc", new Date()));

		
		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);

		for (String beanName : beanNames)
		{
			logger.info(beanName);
		}
	}
}
