package com.fluffycloud.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fluffycloud.api.Iservice.AWSService;
import com.fluffycloud.aws.cli.utils.CLIExecutor;
import com.fluffycloud.aws.constants.Action;
import com.fluffycloud.aws.entity.Command;
import com.fluffycloud.exceptions.FluffyCloudException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
public class EmbeddedMongoConfiguration
{
	@Value("${spring.data.mongodb.port}")
	private int port;

	@Autowired
	AWSService aWSService;

	@Autowired
	CLIExecutor cliExecutor;

	@Bean
	public String startAndInitializeEmbeddedMongoDB() throws FluffyCloudException
	{
		startEmbeddedMongoDB();
		return aWSService.addCommand();
	}

	public MongodProcess startEmbeddedMongoDB() throws FluffyCloudException
	{
		MongodStarter starter = MongodStarter.getDefaultInstance();
		try
		{
			IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
					.net(new Net(port, Network.localhostIsIPv6())).build();

			MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
			return mongodExecutable.start();
		}
		catch (Exception exception)
		{
			throw new FluffyCloudException(exception.getMessage());
		}

	}

	@Bean
	public Map<String, Command> getDefaultCommand() throws FluffyCloudException
	{
		Map<String, Command> defaultCommands = new HashMap<String, Command>();
		for (Action action : Action.values())
		{
			Command defaultCommand = cliExecutor.getDefaultCommand(action);
			defaultCommands.put(action.getAction(), defaultCommand);
		}

		System.out.println("command : " + defaultCommands.size());
		return defaultCommands;
	}
}
