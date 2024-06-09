package com.ispan.hotel.service.booking;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class RoomPhotoServiceYTHTests {
	
	@Autowired
	private RoomPhotoServiceYTH roomPhotoService;
	
	@Autowired
	private RoomTypeServiceYTH roomTypeService;
	
	@Test
	public void testCreate() {
		
		JSONObject obj = new JSONObject()
				.put("rtId", "7")
				.put("photoname", "側視1")
				.put("photoPath", "/room/{id}/1.png")
				.put("photoDescription", "側視圖1");
		
		String json = obj.toString();

		System.out.println("result = " + roomPhotoService.create(json));
		
		
	}
	
//	@Test
	public void testFindById() {
		System.out.println("result = " + roomPhotoService.findById(1));
	}
	
//	@Test
	public void testFind() {
		System.out.println("result = " + roomPhotoService.find());
	}
	
//	@Test
//	public void testFindByRoomType() {
//		System.out.println("result = " + roomPhotoService.findByRoomType(roomTypeService.findById(1)));
//	}
	
//	@Test
	public void testModify() {
		JSONObject obj = new JSONObject()
				.put("rpId", "4")
				.put("rtId", "1")
				.put("photoname", "正面1")
				.put("photoPath", "/room/{id}/1.png")
				.put("photoDescription", "豪華雙人客房示意圖1");
		
		String json = obj.toString();
		System.out.println("result = " + roomPhotoService.modify(json));
	}
	
//	@Test
	public void testDelete() {
	}


}
