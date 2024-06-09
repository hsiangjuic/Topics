package com.ispan.hotel.controller.booking;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.Room;
import com.ispan.hotel.service.booking.RoomServiceYTH;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class RoomControllerYTH {
	@Autowired
	private RoomServiceYTH roomServiceYTH;
	
	@PostMapping("/book/emptyroom/find")
	public String findEmptyRoom( @RequestBody String json) {
		JSONObject responseJSON = new JSONObject();
		JSONObject obj = new JSONObject(json);
		
		List<Room> rooms = roomServiceYTH.findEmptyRoom(obj);
		JSONArray array = new JSONArray();
		if(rooms != null) {
			for(Room oer : rooms) {
				JSONObject emptyRoom = new JSONObject().put("roomNumber", oer.getRoomNumber());
				array.put(emptyRoom);
			}
		}
		if(array != null && !array.isEmpty()) {
			responseJSON.put("emptyRoomsForModal", array);
			responseJSON.put("success", true);
		}else {
			responseJSON.put("success", false);
		}
		
		return responseJSON.toString();
		
	}
	
}
