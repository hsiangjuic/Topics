package com.ispan.hotel.service.booking;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class HotelServiceYTHTests {
	
	@Autowired
	private HotelServiceYTH hotelService;
	
	@Test
	public void testCreate() {
		
		JSONObject obj = new JSONObject()
				.put("name", "測試飯店")
				.put("address", "台北市大安區")
				.put("tel", "02-12345678")
				.put("fax", "02-22345678")
				.put("checkinTime", "15:00")
				.put("checkoutTime", "11:00")
				.put("contactEmail", "ispan181@ispan.com")
				.put("introduction", "美好的飯店");
		
		String json = obj.toString();

		System.out.println("result = " + hotelService.create(json));
		
		
	}
	
//	@Test
	public void testFindById() {
		System.out.println("result = " + hotelService.findById(1));
	}
	
//	@Test
	public void testFind() {
	}
	
//	@Test
	public void testModify() {
		JSONObject obj = new JSONObject()
				.put("hId", "1")
				.put("name", "測試飯店")
				.put("address", "台北市大安區")
				.put("tel", "02-12345678")
				.put("fax", "02-22345678")
				.put("checkinTime", "15:00")
				.put("checkoutTime", "11:00")
				.put("contactEmail", "ispan181@ispan.com")
				.put("introduction", "美好的飯店");
		
		String json = obj.toString();
		System.out.println("result = " + hotelService.modify(json));
	}
	
//	@Test
	public void testDelete() {
	}


}
