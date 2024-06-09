package com.ispan.hotel.controller.n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.Facility;
import com.ispan.hotel.service.n.FacilityServiceCBH;



@CrossOrigin
@RestController
public class FacilityControllerCBH {
	
	@Autowired
	private FacilityServiceCBH facilityService;
	
	@GetMapping("/facility/insert")
	public Facility insert() {
		
		Facility facObj = new Facility("飲品小食4");
		return facilityService.insert(facObj);
		
	}
	
	@GetMapping("/facility/findbyfacilityId") 
	public Facility findByFacilityId (@RequestParam("id") Integer id) {
		
		Facility facObj = facilityService.findById(id);
		if(facObj != null) {
			return facObj;
		}
		
		return null;
		
		
	}
	

}
