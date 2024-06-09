package com.ispan.hotel.controller.n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.Facility;
import com.ispan.hotel.model.FacilityDetail;
import com.ispan.hotel.service.n.FacilityDetailServiceCBH;
import com.ispan.hotel.service.n.FacilityServiceCBH;



@CrossOrigin
@RestController
public class FacilityDetailControllerCBH {
	
	@Autowired
	private FacilityDetailServiceCBH facilityDetailServic;
	
	@Autowired
	private FacilityServiceCBH facilityService;
	
	@GetMapping("/facilitydetail/insert")
	public FacilityDetail insert() {
		
		Facility fd1 = facilityService.findById(4);
		
		FacilityDetail f1 = new FacilityDetail("免費 Wi-Fi 上網", "40吋液晶電視", "浴室附設平板電視", "", "","","","","","", fd1);
		
		FacilityDetail result = facilityDetailServic.insert(f1);
		
		return result;
		
		
	}
	
	@GetMapping("/facilitydetail/findbyfacilitydetailid")
	public FacilityDetail findByFeatureDetailId(@RequestParam("id") Integer id) {
		
		FacilityDetail facilityDetail = facilityDetailServic.findById(id);
		if(facilityDetail != null) {
			return  facilityDetail;
		} 
		
		return null;
		
		
	}

}
