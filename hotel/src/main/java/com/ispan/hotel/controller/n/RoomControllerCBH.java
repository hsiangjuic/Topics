package com.ispan.hotel.controller.n;

import java.util.List;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.Room;
import com.ispan.hotel.repository.n.RoomRepositoryCBH;
import com.ispan.hotel.service.n.RoomServiceCBH;
import com.ispan.hotel.util.DatetimeConverter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;







@RestController
@CrossOrigin
public class RoomControllerCBH {
	
	@Autowired
	private RoomServiceCBH roomService;
	
	@Autowired
	private RoomRepositoryCBH roomRepository;
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}
	
	
	// 分頁和動態查詢
	@PostMapping("/room/criteriapageforvue")
	public String criteriaPageForVue(@RequestBody String json) {
		
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
		
		List<Room> roomList = roomService.criteriaPagePraticePage(json);
		if(roomList != null && !roomList.isEmpty()) {
			
			for(Room room : roomList) {
				
				JSONObject item = new JSONObject()
						.put("rId", room.getrId())
						.put("roomType", room.getRoomType().getRoomName())
						.put("floor", room.getFloor())
						.put("roomStatus", room.getRoomStatus())
						.put("roomNumber", room.getRoomNumber())
						.put("lastModifiedDate", DatetimeConverter.toString(room.getLastModifiedDate(), "yyyy-MM-dd HH:mm:ss"))
						.put("lastModifiedEmp", room.getLastModifiedEmp())
						.put("lastModifiedText", room.getLastModifiedText());
				
				array.put(item);
				
			}
			
		}
		responseJson.put("list", array);
		Long count = roomService.criteriaPagePraticePageCount(json); // 依據目前條件總共會抓到幾筆資料
		responseJson.put("count", count);
		
		return responseJson.toString();
	}
	
	
	
	// 找單筆資料
	@GetMapping("/room/onlyfindbyidlazy")
	public String onlyfindbyidLazy(@RequestParam("rId") Integer rId) {
		
		JSONObject responseJson = new JSONObject();
        
        Room room = roomService.findByID(rId);
        if(room!=null) {
        	responseJson.put("rId", room.getrId())
			.put("roomType", room.getRoomType().getRoomName())
			.put("floor", room.getFloor())
			.put("roomStatus", room.getRoomStatus())
			.put("roomNumber", room.getRoomNumber())
			.put("lastModifiedDate", DatetimeConverter.toString(room.getLastModifiedDate(), "yyyy-MM-dd HH:mm:ss"))
			.put("lastModifiedEmp", room.getLastModifiedEmp())
			.put("lastModifiedText", room.getLastModifiedText());
        }
        
        return responseJson.toString();
		
	}
	
	// 接收表單插入資料
	@PostMapping("/room/insertfromjson")
	public String insertFromJson(@RequestBody String json) {
				
		JSONObject responseJson = new JSONObject();
				
		Room room = roomService.insertJsonRoom(json);
		roomRepository.save(room);  
				
		if(room == null) {
					
			responseJson.put("message", "新增失敗");
					
		} else {
				
			responseJson.put("message", "新增成功");
			return responseJson.toString();
				
		}
				
				
			return null;
			
		}
	
	// 接收表單更新資料
	@PutMapping("/room/updatefromjson")
	public String updateFromJson(@RequestBody String json) {
			
		JSONObject responseJson = new JSONObject();
			
		Room room = roomService.updateJsonRoom(json);
		roomRepository.save(room);  
			
		if(room == null) {
				
			responseJson.put("message", "新增失敗");
				
		} else {
			
			responseJson.put("message", "新增成功");
			return responseJson.toString();
			
		}
			
			
			return null;
		
	}
	
	//以 id 刪除
	
	@DeleteMapping("/room/deleteById")
	public void deleteRoom(@RequestParam("rId") Integer rId) {
			
		roomService.deleteRoomById(rId);
			
	}

}
