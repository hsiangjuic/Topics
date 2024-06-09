package com.ispan.hotel.service.booking;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RoomTypeServiceYTHTests {
	
	@Autowired
	private RoomTypeServiceYTH roomTypeService;
	
	@Test
	public void testCreate() {
		
		JSONObject obj = new JSONObject()
				.put("hId", "1")
				.put("roomName", "豪華雙床客房")
				.put("roomAmount", "2")
				.put("bedNumber", "2")
				.put("bedType", "加大單人床")
				.put("flexiblePrice", "2600")
				.put("memberPrice", "2100")
				.put("pet", "否")
				.put("allowAddBed", "否")
				.put("accessibleRoom", "否")
				.put("peopleMaxAmount", "1");
		
		String json = obj.toString();

		System.out.println("result = " + roomTypeService.create(json));
		
		
	}
	
//	@Test
	public void testFindById() {
		System.out.println("result = " + roomTypeService.findById(1));
	}
	
//	@Test
	public void testFind() {
		
		JSONObject json = new JSONObject()
				.put("start", "0")
				.put("rows", "3")
				.put("order", "roomName")
				.put("dir", "false")
				.put("name","標準");
		
		System.out.println("result = " + roomTypeService.find(json));
	}
	
//	@Test
	public void testModify() {
		JSONObject obj = new JSONObject()
				.put("rtId", "6")
				.put("hId", "1")
				.put("roomName", "標準雙床客房")
				.put("roomAmount", "3")
				.put("bedNumber", "2")
				.put("bedType", "標準單人床")
				.put("flexiblePrice", "2600")
				.put("memberPrice", "2100")
				.put("pet", "否")
				.put("allowAddBed", "否")
				.put("accessibleRoom", "否")
				.put("peopleMaxAmount", "2")
				.put("lastModifiedEmp", "Alex")
				.put("lastModifiedText", "test");
		
		String json = obj.toString();
		System.out.println("result = " + roomTypeService.modify(json));
	}
	
//	@Test
	public void testDelete() {
	}


}
