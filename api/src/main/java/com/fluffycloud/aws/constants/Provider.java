package com.fluffycloud.aws.constants;

public enum Provider
{

	AWS("aws");

	public String value;

	private Provider(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	public static boolean isValidProvider(String value)
	{
		for (Provider provider : values())
		{
			if (provider.getValue().equalsIgnoreCase(value))
			{
				return true;
			}
		}

		return false;
	}

	public static Provider getProvider(String value)
	{
		for (Provider provider : values())
		{
			if (provider.getValue().equalsIgnoreCase(value))
			{
				return provider;
			}
		}

		return null;
	}

}
