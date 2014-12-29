package com.fluffycloud.aws.ec2.response.entity;

import java.util.List;

import com.fluffycloud.aws.cloud.response.entity.Reservations;
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
