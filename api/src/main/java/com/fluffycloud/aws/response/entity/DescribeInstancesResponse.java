package com.fluffycloud.aws.response.entity;

import com.google.gson.annotations.SerializedName;

public class DescribeInstancesResponse
{
	@SerializedName("Reservations")
	private Reservations reservations;

	public Reservations getReservations()
	{
		return reservations;
	}

	public void setReservations(Reservations reservations)
	{
		this.reservations = reservations;
	}

}
