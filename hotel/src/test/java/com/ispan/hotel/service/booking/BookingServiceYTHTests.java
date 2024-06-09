package com.ispan.hotel.service.booking;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class BookingServiceYTHTests {
	
	@Autowired
	private BookingServiceYTH bookingService;
	
	@Test
	public void testCreate() {
		
		JSONObject obj = new JSONObject()
				.put("bdId", "1")
				.put("rId", "1")
				.put("beginDate", "2024-5-13")
				.put("lastDate", "2024-5-17")
				.put("bookingStatus", "Pending Confirmation")
				.put("additionalBed", "0")
				.put("breakfast", true);
		
		String json = obj.toString();

//		System.out.println("result = " + bookingService.create(json));
		
		
	}
	
//	@Test
//	public void testCount() {
//		JSONObject obj = new JSONObject()
//				.put("rId", "1")
//				.put("beginDate","2024-5-10")
//				.put("lastDate","2024-5-16");
//		
//		System.out.println("result = " + bookingService.count(obj.toString()));
//		
//	}
	
//	@Test
	public void testFindById() {
		System.out.println("result = " + bookingService.findById(1));
	}
	
//	@Test
	public void testFind() {
		System.out.println("result = " + bookingService.find());
	}
	
//	@Test
	public void testModify() {
		JSONObject obj = new JSONObject()
				.put("bId", "1")
				.put("bdId", "1")
				.put("rId", "1")
				.put("beginDate", "2024-5-12")
				.put("lastDate", "2024-5-13")
				.put("bookingStatus", "Pending Confirmation")
				.put("additionalBed", "0")
				.put("breakfast", true)
				.put("lastModifiedEmp", "Alex")
				.put("lastModifiedText", "test");
		
		String json = obj.toString();
		System.out.println("result = " + bookingService.modify(json));
	}
	
//	@Test
	public void testDelete() {
	}


}
