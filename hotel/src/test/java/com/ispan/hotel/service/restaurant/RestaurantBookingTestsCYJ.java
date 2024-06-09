package com.ispan.hotel.service.restaurant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestaurantBookingTestsCYJ {
	
	@Autowired
	private RestaurantBookingServiceCYJ restaurantBookingService;
	
//	@Test
	public void testCreate() {
		try {
			extracted();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	@Test
	public void extracted() throws JSONException {
		JSONObject obj = new JSONObject()
				.put("reId", "1")
				.put("isCustomer", true)
				.put("totalAdult", "2")
				.put("totalChildren", "2")
				.put("totalTable", "1")
				.put("firstName", "玉婷")
				.put("lastName", "李")
				.put("cellphone", "0933ˇ˙942")
				.put("email", "sherry0822@gmail.com")
				.put("remark", "");
		
		String json = obj.toString();
		System.out.println("result = "+ restaurantBookingService.create(json));
	}
	
	@Test
	public void testFindById() {
		System.out.println("result = "+restaurantBookingService.findById(1));
	}
	@Test
	public void testFind() {
		System.out.println("result = "+restaurantBookingService.find());
	}
	@Test
	public void testModify() throws JSONException{
		JSONObject obj = new JSONObject()
				.put("rebId", "1")
				.put("reId", "1")
				.put("isCustomer", true)
				.put("totalAdult", "2")
				.put("totalChildren", "2")
				.put("totalTable", "1")
				.put("firstName", "玉婷")
				.put("lastName", "李")
				.put("cellphone", "0933ˇ˙942")
				.put("email", "sherry0822@gmail.com")
				.put("remark", "");
		String json = obj.toString();
		System.out.println("reult = "+restaurantBookingService.modify(json));
	}
	

}
