package com.ispan.hotel.service.booking;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ispan.hotel.model.Discount;
import com.ispan.hotel.model.RoomType;

@SpringBootTest
public class DiscountServiceYTHTests {

	@Autowired
	private DiscountServiceYTH discountService;
	
	@Autowired
	private RoomTypeServiceYTH roomTypeService;
	
	@Test
	public void testfindInUsedByRoomType() {
		
		JSONObject obj = new JSONObject()
				.put("beginDate","2024-05-10")
				.put("lastDate", "2024-05-12");
		
		RoomType rt = roomTypeService.findById(1);
		
		List<Discount> ds = discountService.findInUsedByRoomType(rt, obj);
		JSONObject result = new JSONObject();
		for(Discount d : ds) {
			result.put("discount", d.getDiscountDetail());
			
		}
		
		System.out.println("result = " + result);
		
	}
}
