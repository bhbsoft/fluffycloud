package com.fluffycloud.aws.response.entity;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Reservations
{
	@SerializedName("ReservationId")
	private String reservationId;

	@SerializedName("Instances")
	private ArrayList<Instance> instances;

	public String getReservationId()
	{
		return reservationId;
	}

	public void setReservationId(String reservationId)
	{
		this.reservationId = reservationId;
	}

	public ArrayList<Instance> getInstances()
	{
		return instances;
	}

	public void setInstances(ArrayList<Instance> instances)
	{
		this.instances = instances;
	}

}
