package com.ispan.hotel.controller.booking;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.service.booking.DiscountDetailServiceYTH;

@RestController
@CrossOrigin
public class DiscountControllerYTH {

	@Autowired
	private DiscountDetailServiceYTH discountDetailService;
	
	@PostMapping("booking/discount/check")
	public String checkDiscount(@RequestBody String json) {
		JSONObject responseJson = new JSONObject();
		JSONObject obj = new JSONObject(json);
		String promo = obj.isNull("promo") ? null : obj.getString("promo");

		
		String check = discountDetailService.checkDiscount(promo,json);
		
		if(check.equals("已套用此優惠")) {
            responseJson.put("success", true);
            responseJson.put("message", check);
            DiscountDetail discount = discountDetailService.findByPromoCode(promo);
            
            if(discount.getDiscountPrice() != null) {
            	responseJson.put("discountMethod", "discountPrice");
            	responseJson.put("discount", discount.getDiscountPrice());
            }
            if(discount.getDiscountRate() != null) {
            	responseJson.put("discountMethod", "discountRate");
            	responseJson.put("discount", discount.getDiscountRate());
            }
            
            responseJson.put("ddId", discount.getDdId());
            responseJson.put("dDName", discount.getName());
            
            
        } else {
            responseJson.put("success", false);
            responseJson.put("message", check);
        }
		
		return responseJson.toString();
	}
	
	@PostMapping("booking/discount/find")
	public String findAllDiscount() {
		JSONObject responseJson = new JSONObject();
		List<DiscountDetail> dds = discountDetailService.find();
		JSONArray pack = new JSONArray();
		JSONArray promo = new JSONArray();
		for(DiscountDetail one : dds) {
			JSONObject temp = new JSONObject();
			if(one.getDiscountType().equals("專案")) {
				temp.put("packName", one.getName());
				temp.put("ddId", one.getDdId());
				pack.put(temp);
			} else {
				temp.put("dDName", one.getName());
				temp.put("ddId", one.getDdId());
				promo.put(temp);
			}
			
		}
		if(pack != null && promo != null) {
			responseJson.put("pack", pack);
            responseJson.put("promo", promo);
            responseJson.put("success", true);
            responseJson.put("message", "成功");
		} else {
			responseJson.put("success", false);
            responseJson.put("message", "找不到任何優惠");
		}
		
		return responseJson.toString();
	}
}
