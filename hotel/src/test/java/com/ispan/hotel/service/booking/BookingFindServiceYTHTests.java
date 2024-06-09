package com.ispan.hotel.service.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ispan.hotel.model.BookingDetail;

@SpringBootTest
public class BookingFindServiceYTHTests {
	@Autowired
	private BookingDetailServiceYTH bookingDetailServiceYTH;
	
	@Autowired
	private BookingFindServiceYTH bookingFindServiceYTH;
	
	@Test
	public void convertBookingDetailToJsonTest() {
		BookingDetail bd = bookingDetailServiceYTH.findById(12);
		System.out.println(bookingFindServiceYTH.findBookingData(bd));
	}
}
