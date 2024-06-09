package com.ispan.hotel.service.booking;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class BookingPaymentServiceYTHTests {
	
	@Autowired
	private BookingPaymentServiceYTH bookingPaymentService;
	
	@Test
	public void testCreate() {
		
		JSONObject obj = new JSONObject()
				.put("bId", "1")
				.put("typeName", "全額住宿費")
				.put("amountPayable", "3000")
				.put("amountPaid", "0")
				.put("expireDate", "2024-5-10")
				.put("paymentDate", "2024-5-9")
				.put("paymentMethod", "信用卡")
				.put("remark", "無")
				.put("points", "1")
				.put("invoiceNumber", "16620962");
		String json = obj.toString();

//		System.out.println("result = "+ bookingPaymentService.create());
		
		
	}
	
//	@Test
	public void testFindById() {
		System.out.println("result = "+ bookingPaymentService.findById(1));
	}
	
//	@Test
	public void testFind() {
		System.out.println("result = " + bookingPaymentService.find());
	}
	
//	@Test
	public void testModify() {
		JSONObject obj = new JSONObject()
				.put("bpId", "1")
				.put("bId", "1")
				.put("typeName", "全額住宿費")
				.put("amountPayable", "3000")
				.put("amountPaid", "0")
				.put("expireDate", "2024-5-11")
				.put("paymentDate", "2024-5-9")
				.put("paymentMethod", "信用卡")
				.put("remark", "無")
				.put("points", "1")
				.put("invoiceNumber", "16620962")
				.put("lastModifiedEmp", "Alex")
				.put("lastModifiedText", "test");
		String json = obj.toString();

		System.out.println("result = "+ bookingPaymentService.modify(json));
	}
	
//	@Test
	public void testDelete() {
	}


}
