package com.ispan.hotel.service.booking;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class DiscountDetailServiceYTHTests {
	
	@Autowired
	private DiscountDetailServiceYTH discountDetailService;
	
	
//	@Test
	public void testFindById() {
		System.out.println("result = " + discountDetailService.findById(3));
	}
	
//	@Test
	public void testFind() {
		System.out.println("result = " + discountDetailService.find());
	}
	
	@Test
	public void testCheckDiscount() {
		JSONObject obj = new JSONObject()
				.put("beginDate", "2024-05-05")
				.put("lastDate","2024-05-08" )
				.put("rtId", "1");
		
		String json = obj.toString();
		
		System.out.println("result = " 
		+ discountDetailService.checkDiscount("promo004", json));
		
	}
	

}
