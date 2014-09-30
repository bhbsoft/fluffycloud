package com.fluffycloud.aws.constants;

public enum InstanceTypes
{

	t1MICRO("t1.micro");

	public String typeName;

	private InstanceTypes(String value)
	{
		this.typeName = value;
	}

	public String getValue()
	{
		return typeName;
	}

	public boolean isValidProvider(String value)
	{
		for (InstanceTypes provider : values())
		{
			if (provider.getValue().equalsIgnoreCase(value))
			{
				return true;
			}
		}

		return false;
	}
}
