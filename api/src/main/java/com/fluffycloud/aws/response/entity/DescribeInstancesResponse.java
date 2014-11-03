package com.fluffycloud.aws.response.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DescribeInstancesResponse
{
	@SerializedName("Reservations")
	private List<Reservations> reservations;

	public List<Reservations> getReservations()
	{
		return reservations;
	}

	public void setReservations(List<Reservations> reservations)
	{
		this.reservations = reservations;
	}

}
