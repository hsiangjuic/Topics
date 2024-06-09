//package com.ispan.hotel.service.booking;
//
//import org.json.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.ispan.hotel.model.Customer;
//
//@SpringBootTest
//public class CustomerServiceYTHTests {
//	
//	@Autowired
//	private CustomerServiceYTH customerService;
//	
//	@Test
//	public void testCreate() {
//		
//		JSONObject obj = new JSONObject()
//				.put("firstName", "小明")
//				.put("lastName", "王")
//				.put("identification", "A123456789")
//				.put("passportNumber", "null")
//				.put("tel", "0911222333")
//				.put("address", "台北市")
//				.put("birthday", "2000-1-1")
//				.put("gender", "male")
//				.put("country", "台灣")
//				.put("email", "min@ispan.com")
//				.put("remark", "吃素")
//				.put("titleOfCourtesy", "先生")
//				.put("allowPromotionMail", true);
//		String json = obj.toString();
//
//		System.out.println("result = "+customerService.create(obj));
//		
//		
//	}
//	
////	@Test
//	public void testFindById() {
//		Customer customer = customerService.findById(1);
//		System.out.println("customer = "+customer);
//	}
//	
////	@Test
//	public void testFind() {
//		System.out.println("All Customer = "+ customerService.find());
//	}
//	
////	@Test
//	public void testModify() {
//		JSONObject obj = new JSONObject()
//				.put("cId", 1)
//				.put("firstName", "小明")
//				.put("lastName", "王")
//				.put("identification", "A123456789")
//				.put("passportNumber", "null")
//				.put("tel", "0911222333")
//				.put("address", "台北市")
//				.put("birthday", "2000-1-1")
//				.put("gender", "male")
//				.put("country", "台灣")
//				.put("email", "min@ispan.com")
//				.put("remark", "吃素")
//				.put("titleOfCourtesy", "先生")
//				.put("allowPromotionMail", true)
//				.put("lastModifiedText", "test")
//				;
//		String json = obj.toString();
//
//		System.out.println("result = "+customerService.modify(json));
//	}
//	
////	@Test
//	public void testDelete() {
//		System.out.println("result = "+customerService.delete(5));
//	}
//
//
//}
