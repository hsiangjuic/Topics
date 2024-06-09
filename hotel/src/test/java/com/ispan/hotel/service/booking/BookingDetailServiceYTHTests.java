package com.ispan.hotel.service.booking;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class BookingDetailServiceYTHTests {
	
	@Autowired
	private BookingDetailServiceYTH bookingDetailService;
	
//	@Test
	public void testCreate() {
		
		JSONObject obj = new JSONObject()
				.put("cId", "1")
				.put("hId", "1")
				.put("customerRemark", "不要床單")
				.put("childNumber", "0")
				.put("infantUtility", false)
				.put("adultNumber", "4")
				.put("petNumber", "0")
				.put("paymentStatus", "未付款")
				.put("arrivalTime", "18:00")
				.put("totalRoom", "2");
		String json = obj.toString();

//		System.out.println("result = "+ bookingDetailService.create(obj));
		
		
	}
	
//	@Test
	public void testFindById() {
		System.out.println("result = "+ bookingDetailService.findById(1));
	}
	
	@Test
	public void testFind() {
//		查詢條件:
//		Customer firstName,lastName from BookingDetail.getCustomer.getxxx
//		Hotel h_id BookingDetail.getHotel.gethId
//		特定日期有哪些訂單 Booking date
//		特定日期入住訂單 Booking beginDate
//		特定日期退房訂單 Booking lastDate
//		房型 RoomType roomName
//		付款狀態 BookingDetail paymentStatus
//		訂單狀態 Booking  bookingStatus
//		使用特定優惠 DiscountDetail name
//		使用特定類型優惠 DiscountDetail discountType
		JSONObject obj = new JSONObject()
//				.put("firstName", "小華")
//				.put("lastName", "張")
//				.put("hId", "1")
//				.put("date", "2024-05-28")
//				.put("beginDate", "2024-05-27")
//				.put("lastDate", "2024-05-31")
//				.put("roomName", "標準雙人客房")
//				.put("paymentStatus", "未付款")
//				.put("bookingStatus", "待確認")
//				.put("dDName", "PROMO006")
//				.put("discountType", "專案")
				.put("start", 0)
				.put("rows", 10)
				.put("order", "orderNumber")
				.put("dir", false);
		
		System.out.println("result = " + bookingDetailService.find(obj));
		
		
//		System.out.println("result = " + bookingDetailService.find());
	}
	
//	@Test
	public void testModify() {
		JSONObject obj = new JSONObject()
				.put("bdId", "1")
				.put("cId", "1")
				.put("hId", "1")
				.put("customerRemark", "不要床單")
				.put("childNumber", "0")
				.put("infantUtility", false)
				.put("adultNumber", "0")
				.put("petNumber", "0")
				.put("paymentStatus", "未付款")
				.put("arrivalTime", "18:00")
				.put("totalRoom", "2");
		String json = obj.toString();

		System.out.println("result = "+ bookingDetailService.modify(json));
	}
	
//	@Test
	public void testDelete() {
	}
	
	
//	@Test
	public void testGenerateOrderNumber() {
		System.out.println(bookingDetailService.generateOrderNumber());
	}


}
