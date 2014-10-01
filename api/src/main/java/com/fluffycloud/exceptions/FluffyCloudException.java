package com.fluffycloud.exceptions;

public class FluffyCloudException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	// TODO can maintain status and detail status hierarchy to get error
	// details.

	public FluffyCloudException(String message)
	{
		super();
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}
}
