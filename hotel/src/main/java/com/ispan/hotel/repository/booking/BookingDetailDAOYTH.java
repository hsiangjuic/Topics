package com.ispan.hotel.repository.booking;

import java.util.List;

import org.json.JSONObject;

import com.ispan.hotel.model.BookingDetail;

public interface BookingDetailDAOYTH {
	List<BookingDetail> find(JSONObject obj);
}
