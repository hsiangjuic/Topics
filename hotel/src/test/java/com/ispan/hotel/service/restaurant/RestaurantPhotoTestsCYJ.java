package com.ispan.hotel.service.restaurant;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RestaurantPhotoTestsCYJ {
	
	@Autowired
	private RestaurantPhotoServiceCYJ restaurantPhotoService;
	
	@Autowired
	private RestaurantServiceCYJ restaurantService;
	
	@Test
	public void testCreate() {
		
		JSONObject obj = new JSONObject()
				.put("reId","1")
				.put("photoname", "環境1")
				.put("photoPath","/restaurant/{id}/1.png");
		
		String json = obj.toString();
		System.out.println("result = "+ restaurantPhotoService.create(json));
	}
	
	@Test
	public void testFindById() {
		System.out.println("result = "+restaurantPhotoService.findById(1));
	}
	
	@Test
	public void testFind() {
		System.out.println("result = "+restaurantPhotoService.find());
	}
	
	@Test
	public void testModify() {
		JSONObject obj = new JSONObject()
				.put("repId", "1")
				.put("reId", "1")
				.put("photoname", "環境1")
				.put("photoPath","/restaurant/{id}/1.png");
		
		String json = obj.toString();
		System.out.println("result = "+restaurantPhotoService.modify(json));
		
	}

}
