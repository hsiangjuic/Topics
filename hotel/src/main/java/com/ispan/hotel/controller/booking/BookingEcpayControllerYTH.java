package com.ispan.hotel.controller.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.service.booking.BookingEcpayServiceYTH;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class BookingEcpayControllerYTH {
	@Autowired
	private BookingEcpayServiceYTH bookingEcpayService;
	
	

	
	@PostMapping("book/paymentstart")
	@Transactional
	public String ecpay(@RequestBody String json) {
		
		try {
			AllInOne aio = new AllInOne("");
			AioCheckOutALL acoa = bookingEcpayService.createAioCheckOutALL(json);
			System.out.println("epay");
			return aio.aioCheckOut(acoa, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
