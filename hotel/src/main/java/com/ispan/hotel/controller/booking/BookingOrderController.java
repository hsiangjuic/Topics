package com.ispan.hotel.controller.booking;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import com.ispan.hotel.service.booking.BookingOrderServiceYTH;
import com.ispan.hotel.util.DatetimeConverter;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BookingOrderController {
	
	@Autowired
	BookingOrderServiceYTH bookingOrderServiceYTH;
	
	@PostMapping("booking/book/paymentend")
	@Transactional
	public String ecpayend(HttpServletRequest request) {
		System.out.println("paymentend");
		String payType = request.getParameter("CustomField2");
		String pd = request.getParameter("PaymentDate");
		LocalDate paymentDate = DatetimeConverter.parseLocalDateTime(pd, "yyyy/MM/dd HH:mm:ss").toLocalDate();
		Integer paid = Integer.parseInt(request.getParameter("TradeAmt"));
		String paymentMethod = request.getParameter("PaymentType");

		
		if("1".equals(request.getParameter("RtnCode"))) {
			System.out.println("rtncode is 1");
			String orderNumber = request.getParameter("CustomField1");
			System.out.println("orderNumber = "+orderNumber);
			
			bookingOrderServiceYTH.ecpayProcess(payType, pd, paymentDate, paid, paymentMethod, orderNumber);
			
		} else {
			System.out.println("rtncode is not 1");
		}
		
		
		return "redirect:" + "http://192.168.31.160:5173/bookingPages/bookingFinal";
	}
}
