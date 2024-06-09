package com.ispan.hotel.controller.booking;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.Customer;
import com.ispan.hotel.service.booking.CustomerServiceYTH;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class CustomerControllerYTH {
	
	@Autowired
	private CustomerServiceYTH customerServiceYTH;
	
	@GetMapping("/customer/{username}")
	public String findByUsername(@PathVariable(name = "username") String username) {
		Customer customer = customerServiceYTH.findByUsername(username);
		
		JSONObject responseJSON = new JSONObject()
				.put("firstName", customer.getFirstName())
				.put("lastName", customer.getLastName())
				.put("identification", customer.getIdentification())
				.put("passportNumber", customer.getPassportNumber())
				.put("tel", customer.getTel())
				.put("address", customer.getAddress())
				.put("birthday", customer.getBirthday())
				.put("gender", customer.getGender())
				.put("country", customer.getCountry())
				.put("email", customer.getEmail())
				.put("titleOfCourtesy", customer.getTitleOfCourtesy())
				.put("allowPromotionMail", customer.getAllowPromotionMail())
				.put("memberRank", customer.getMemberRank())
				.put("username", customer.getUsername());
		
		return responseJSON.toString();
		
	}
}
