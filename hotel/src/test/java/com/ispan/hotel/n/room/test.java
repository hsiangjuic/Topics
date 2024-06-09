package com.ispan.hotel.n.room;



import java.util.List;

import org.hibernate.Session;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.hotel.model.Room;
import com.ispan.hotel.repository.n.RoomPhotoRepositoryCBH;
import com.ispan.hotel.repository.n.RoomTypeRepositoryCBH;
import com.ispan.hotel.service.n.RoomServiceCBH;

import jakarta.persistence.PersistenceContext;

@SpringBootTest
public class test {
	
	@Autowired 
	private RoomTypeRepositoryCBH roomTypeRepository;
	
	@Autowired
	private RoomServiceCBH roomService;
	
	@Autowired 
	private RoomPhotoRepositoryCBH photoRepository;
	
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}
	
	@Test
	@Transactional //記得加上 Transactional 自動開啟關閉 Session
	public void testCreate() {
		
		/*
		String roomNumber = pageJson.isNull("roomNumber") ? null : pageJson.getString("roomNumber");
		String roomFloor = pageJson.isNull("roomFloor") ? null : pageJson.getString("roomFloor");
		String roomStatus = pageJson.isNull("roomStatus") ? null : pageJson.getString("roomStatus");
		String roomType = pageJson.isNull("roomType") ? null : pageJson.getString("roomType");
		String order = pageJson.isNull("order") ? "id" : pageJson.getString("order"); // 使用者如果沒有輸入要根據什麼欄位排序就以 id 欄位排序, 第一次放在這裡比較好對照之後都放在上面就好了
		boolean dir = pageJson.isNull("dir") ? false : pageJson.getBoolean("dir");	  // 使用者如果有指定排序的方式是 true 就是反向排序, 第一次放在這裡比較好對照之後都放在上面就好了
		int start = pageJson.isNull("start") ? 0 : pageJson.getInt("start"); //從第幾筆資料開始顯示, 第一次放在這裡比較好對照之後都放在上面就好了
		int rows = pageJson.isNull("rows") ? 10 : pageJson.getInt("rows"); //每頁顯示幾筆資料, 第一次放在這裡比較好對照之後都放在上面就好了
		*/
		
		
		JSONObject obj = new JSONObject()
				.put("start", 0)
				.put("rows", 10)
				.put("dir", false)
				.put("order", "id");
		
		String objString = obj.toString();
	
		
		List<Room> roomList = roomService.criteriaPagePraticePage(objString);
		Long count = roomService.criteriaPagePraticePageCount(objString);

		if(roomList != null && !roomList.isEmpty()) {
			
			for(Room row : roomList) {
				
				System.out.println("row=" + row);
				
			}
			
			
		}

		
		
	}
	
	

	

}
