package com.ispan.hotel.service.booking;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ispan.hotel.model.RoomType;


@SpringBootTest
public class RoomServiceYTHTests {
	
	@Autowired
	private RoomServiceYTH roomService;
	
	@Autowired
	private RoomTypeServiceYTH roomTypeService;
	
//	@Test
	public void testCreate() {
//		測試用自動加入房間
/*
		List<RoomType> roomTypes = roomTypeService.find();
		
		int[] floorRNumber = {0,0,0,0};
		for(RoomType roomType : roomTypes) {
			int amount = roomType.getRoomAmount();
			int roomFlag = 0;
			for(int i = 2; i <= 4; i++) { 			//樓層
				int times = 0;
				for(int j = 1; j <= 3; j++) {		//每層各種類型房間最大數
					if(floorRNumber[i-2]+times < 10) {		//判斷是否超過每層房間總量最大數 
						if(roomFlag < amount) {			//判斷沒多過房間數量 
							String floor = ""+i;
							String roomNumber = ""+(j +floorRNumber[i-2]);
							
							JSONObject obj = new JSONObject()
									.put("rtId", roomType.getRtId())
									.put("floor", floor)
									.put("roomStatus", "availible")
									.put("roomNumber", roomNumber)
									.put("lastModifiedEmp", "Alex")
									.put("lastModifiedEmp", "test");
							String json = obj.toString();
							roomFlag++;
							times++;
							System.out.println("result = " + roomService.create(json));
						}
						
					}
					
				}
				floorRNumber[i-2] += times; 
			}
		}
		*/
		
		JSONObject obj = new JSONObject()
				.put("rtId", "5")
				.put("floor", "4")
				.put("roomStatus", "availible")
				.put("roomNumber", "410")
				.put("lastModifiedEmp", "Alex")
				.put("lastModifiedEmp", "test");
		String json = obj.toString();
		System.out.println("result = " + roomService.create(json));
		
		
		
	}
	
//	@Test
	public void testFindById() {
		System.out.println("result = " + roomService.findById(1));
	}
	
//	@Test
	public void testFind() {
		System.out.println("result = " + roomService.find());
	}
	
//	@Test
	public void testModify() {
		JSONObject obj = new JSONObject()
				.put("rId", "30")
				.put("rtId", "5")
				.put("floor", "4")
				.put("roomStatus", "availible")
				.put("roomNumber", "410")
				.put("lastModifiedEmp", "Alex")
				.put("lastModifiedEmp", "test");
		
		String json = obj.toString();
		System.out.println("result = " + roomService.modify(json));
	}
	
//	@Test
	public void testDelete() {
	}
	
	@Test
	public void testFindEmptyRoom() {
		JSONObject obj = new JSONObject()
				.put("rtId", "1")
				.put("beginDate", "2024-05-01")
				.put("lastDate", "2024-05-02");
		
		String result = "";
		if(roomService.findEmptyRoom(obj)!=null) {
//			result = roomService.findEmptyRoom(obj).getRoomNumber();
		}
		
		System.out.println("result = " + result);
	}


}
