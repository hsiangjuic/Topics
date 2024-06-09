package com.ispan.hotel.service.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ispan.hotel.repository.restaurant.RestaurantRepositoryCYJ;

import net.minidev.json.JSONObject;

@SpringBootTest
public class RestaurantTestsCYJ {

	@Autowired
	private RestaurantServiceCYJ restaurantService;

	@Test
	public void testCreate() {
		JSONObject obj = new JSONObject();
		obj.put("hId", "1");
		obj.put("name", "測試餐廳");
		obj.put("totalSeat", "200");
		obj.put("openTime1", "08:00");
		obj.put("closeTime1", "10:00");
		obj.put("openTime2", "11:00");
		obj.put("closeTime2", "14:00");
		obj.put("openTime3", "17:00");
		obj.put("closeTime3", "20:00");
		obj.put("tel", "29121234");
		obj.put("address", "台北市大安區");
		obj.put("setLastModifiedEmp", "");

		String json = obj.toString();
		System.out.println("reult = " + restaurantService.create(json));
	}

}
