package com.ispan.hotel.repository.booking;

import java.util.List;

import org.json.JSONObject;

import com.ispan.hotel.model.Booking;

public interface BookingDAOYTH {
	List<Booking> find(JSONObject obj);
	long count(JSONObject obj);
}
