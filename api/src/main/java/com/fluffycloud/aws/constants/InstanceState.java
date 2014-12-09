package com.fluffycloud.aws.constants;

public enum InstanceState
{
	PENDING("pending"), RUNNING("running"), REBOOTING("rebooting"), STOPPING("stopping"), STOPPED("stopped"),
	SHUTTINGDOWN("shutting-down"), TERMINATED("TERMINATED");

	private String state;

	InstanceState(String state)
	{
		this.state = state;
	}

	public String getState()
	{
		return state;
	}

	public static boolean isRunning(final String value)
	{
		if (RUNNING.getState().equalsIgnoreCase(value))
		{
			return true;
		}

		return false;
	}

	public static boolean isStopped(final String value)
	{
		if (STOPPED.getState().equalsIgnoreCase(value))
		{
			return true;
		}

		return false;
	}

	public static boolean isValidState(final String value)
	{
		for (InstanceState state : values())
		{
			if (state.getState().equalsIgnoreCase(value))
			{
				return true;
			}
		}

		return false;
	}
}
