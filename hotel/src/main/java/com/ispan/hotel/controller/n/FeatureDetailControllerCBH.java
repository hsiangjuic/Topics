package com.ispan.hotel.controller.n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.Feature;
import com.ispan.hotel.model.FeatureDetail;
import com.ispan.hotel.service.n.FeatureDetailServiceCBH;
import com.ispan.hotel.service.n.FeatureServiceCBH;




@CrossOrigin
@RestController
public class FeatureDetailControllerCBH {
	
	@Autowired
	private FeatureDetailServiceCBH featureDetailService;
	
	@Autowired
	private FeatureServiceCBH featureService;
	
	@GetMapping("/featuredetail/insert")
	public FeatureDetail insert() {
		
		Feature ft1 = featureService.findById(1);
		
		FeatureDetail f1 = new FeatureDetail("附:嬰兒浴、兒童用小板凳、奶瓶清潔劑、溫奶器 (如有需要)", "附:兒童用小板凳、奶瓶清潔劑、溫奶器 (如有需要)", "附:嬰兒浴奶瓶清潔劑、溫奶器 (如有需要)", "附:嬰兒浴、兒童用小板凳、奶瓶清潔劑", "附:兒童用小板凳、奶瓶清潔劑、溫奶器 (如有需要)","","","","","", ft1);
		
		FeatureDetail result = featureDetailService.insert(f1);
		
		
		
		return result;
		
		
	}
	
				
	@GetMapping("/featuredetail/findbyfeaturedetailid")
	public FeatureDetail findByFeatureDetailId(@RequestParam("id") Integer id) {
		
		FeatureDetail featureDetail = featureDetailService.findById(id);
		if(featureDetail != null) {
			return  featureDetail;
		} 
		
		return null;
		
		
	}

}
