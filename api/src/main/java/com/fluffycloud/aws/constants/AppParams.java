package com.fluffycloud.aws.constants;

import java.io.File;

public enum AppParams
{
	DBHOST("host"), 
	DBPORT("port"), 
	DBNAME("FluffyCloudDB"), 
	VPCID("vpc-id"), 
	SGNAME("TestSG-"),
	GROUPNAME("group-name"), 
	GROUPID("group-id"), 
	GROUPIDS("group-ids"), 
	INSTANCETYPE("instance-type"), 
	SUBNETID("subnet-id"), 
	SGIDS("security-group-ids"), 
	IGID("internet-gateway-id"), 
	ALLOCID("allocation-id"),
	CIDRBLOCK("cidr-block"), 
	CIDR("cidr"), 
	PROTOCOL("protocol"), 
	ROUTETABLEID("route-table-id"), 
	GATEID("gateway-id"),
	DESTCIDRBLOCK("destination-cidr-block"), 
	INSTANCEID("instance-id"), 
	PORT("port"), 
	RESOURCES("resources"),
	INSTANCEIDS("instance-ids"), 
	TAGS("tags"), 
	KEYPAIR("key-name"), 
	DESCRIPTION("description"),
	STACKNAME("stack-name"), 
	MAXITEMS("max-items"), 
	STARTINGTOKEN("starting-token"), 
	LOGICALRESOURCEID("logical-resource-id"), 
	PHYSICALRESOURCEID("physical-resource-id"), 
	PARAMETERS("parameters"), 
	TEMPLATEBODY("template-body"), 
	TEMPLATEURL("template-url"), 
	STACKPOLICYBODY("stack-policy-body"), 
	TEMPLATEFOLDER("json"+ File.separator + "aws" + File.separator + "cloudformation" + File.separator + "templates"
			+ File.separator), 
	TEMPLATEXTSN(".template"), 
 TEMPLATEBODYCLILOCATION("file:" + File.separator	+ File.separator + "json" + File.separator + File.separator + "aws" + File.separator + File.separator
			+ "cloudformation" + File.separator + File.separator + "templates" + File.separator + File.separator);

	public String value;

	private AppParams(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

}
