package com.fluffycloud.aws.constants;

public enum AppParams
{

	DBHOST("host"), DBPORT("port"), DBNAME("FluffyCloudDB"), VPCID("vpc-id"), SGNAME("TestSG-"),
	GROUPNAME("group-name"), GROUPID("group-id"), GROUPIDS("group-ids"), INSTANCETYPE("instance-type"), SUBNETID(
			"subnet-id"), SGIDS("security-group-ids"), IGID("internet-gateway-id"), ALLOCID("allocation-id"),
	CIDRBLOCK("cidr-block"), CIDR("cidr"), PROTOCOL("protocol"), ROUTETABLEID("route-table-id"), GATEID("gateway-id"),
	DESTCIDRBLOCK("destination-cidr-block"), INSTANCEID("instance-id"), PORT("port"), RESOURCES("resources"),
	INSTANCEIDS("instance-ids"), TAGS("tags");

	public String typeName;

	private AppParams(String value)
	{
		this.typeName = value;
	}

	public String getValue()
	{
		return typeName;
	}

	public boolean isValidProvider(String value)
	{
		for (AppParams provider : values())
		{
			if (provider.getValue().equalsIgnoreCase(value))
			{
				return true;
			}
		}

		return false;
	}
}
