package com.ispan.hotel.controller.booking;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.Discount;
import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.RoomPhoto;
import com.ispan.hotel.model.RoomType;
import com.ispan.hotel.service.booking.BookingServiceYTH;
import com.ispan.hotel.service.booking.DiscountServiceYTH;
import com.ispan.hotel.service.booking.RoomPhotoServiceYTH;
import com.ispan.hotel.service.booking.RoomServiceYTH;
import com.ispan.hotel.service.booking.RoomTypeServiceYTH;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class RoomTypeControllerYTH {

	@Autowired
	private RoomTypeServiceYTH roomTypeService;
	
	@Autowired
	private BookingServiceYTH bookingService;
	
	@Autowired
	private RoomPhotoServiceYTH roomPhotoService;
	
	@Autowired
	private RoomServiceYTH roomServiceYTH;
	
	@Autowired
	private DiscountServiceYTH discountService;
	
	@GetMapping("/rooms/checkAddBed/{id}")
	public String checkRoomType(@PathVariable (name = "id") Integer id) {
		RoomType roomtype = roomTypeService.findById(id);
		if(roomtype.getAllowAddBed() == "是" ) {
			
		}
		return null;
	}
	
	@PostMapping("/rooms/find")
	public String find(@RequestBody String json) {
		JSONObject obj = new JSONObject(json);
		JSONObject responseJson = new JSONObject();
		
        JSONArray array = new JSONArray();
        
        List<RoomType> roomTypes = new ArrayList<RoomType>();
        
	    Integer total = 0;
	    
	    //照搜尋條件和分頁尋找房型
	    roomTypes = roomTypeService.find(obj);
        if(roomTypes != null && !roomTypes.isEmpty()) {
        	for(RoomType roomtype : roomTypes) {
        			//	找當天房型被使用的數量
    				Long count = bookingService.countRange(obj, roomtype.getRtId());
    				
    				//找房型為availible的數量
    				Long avai = roomServiceYTH.countAvailibleRoom(roomtype);
    				//  是否還有空房
    				Long roomAmount = 0l;
    				if(avai != null) {
    					roomAmount = avai - count;
    				}
    				
    				
    				JSONObject item = new JSONObject();
    				if(roomAmount > 0) {
    					item.put("amount", roomAmount);
    					//	找照片
    					
    					List<RoomPhoto> roomphotos = roomPhotoService.findByRoomType(roomtype);
    					RoomPhoto roomphoto = new RoomPhoto();
    					for(RoomPhoto rp : roomphotos) {
    						if(rp.getPhotoname().equals("正面1")) {
    							roomphoto = rp;
    							break;
    						}
    					}

    					
    					//	找優惠
    					List<Discount> discounts = discountService.findInUsedByRoomType(roomtype, obj);
    					System.out.println("discounts=" + discounts);
    					
    					item.put("rtId", roomtype.getRtId())
    						.put("roomName", roomtype.getRoomName())
    						.put("flexiblePrice", roomtype.getFlexiblePrice())
    						.put("memberPrice", roomtype.getMemberPrice());
    					
    					if(roomphoto != null) {
    						item.put("photoname", roomphoto.getPhotoname())
    							.put("photoDescription", roomphoto.getPhotoDescription());
    						System.out.println("hahaisphoto");
    					}
    					
    					if(discounts != null && !discounts.isEmpty()) {
    						JSONArray discountArr = new JSONArray();
    						for(Discount discount : discounts) {
    							JSONObject disItem = new JSONObject();
    							DiscountDetail discountDetail = discount.getDiscountDetail();
    							
    							if(discountDetail != null 
    									&& discountDetail.getDiscountType().equals("專案")) {
    								disItem.put("dDId", discountDetail.getDdId())
    								.put("dDName", discountDetail.getName())
    								.put("discountPrice", discountDetail.getDiscountPrice())
    								.put("discountRate", discountDetail.getDiscountRate())
    								.put("memberOnly", discountDetail.getMemberOnly())
    								.put("idVerification", discountDetail.getIdVerification());
    								discountArr.put(disItem);
    								System.out.println("discountArr"+discountArr);
    							}
    						}
    						item.put("discounts", discountArr);
    					}else {
    						System.out.println("discountIsNull");
    					}
    					total ++;
    				
    					array.put(item);
    				}
    				
    				
        		}	
    		} 
        responseJson.put("list", array);
        responseJson.put("total", total);
		return responseJson.toString();
	}
	
	@GetMapping("/rooms/{pk}")
	public String findById(@PathVariable(name = "pk") Integer id, @RequestBody String json) {
		JSONObject obj = new JSONObject(json);
		JSONObject responseJson = new JSONObject();
		
        JSONArray array = new JSONArray();
		
		RoomType roomtype = roomTypeService.findById(id);
		if(roomtype != null) {

//			List<RoomPhoto> roomphotos = roomPhotoService.findByRoomType(roomtype);

		
		//	找優惠
		List<Discount> discounts = discountService.findInUsedByRoomType(roomtype, obj);
		
		
		JSONObject item = new JSONObject()
				.put("rtId", roomtype.getRtId())
				.put("roomName", roomtype.getRoomName())
				.put("flexiblePrice", roomtype.getFlexiblePrice())
				.put("memberPrice", roomtype.getMemberPrice());
//		if(roomphotos != null && !roomphotos.isEmpty()) {
//			RoomPhoto roomphoto = roomphotos.get(0);
//			item.put("rpId", roomphoto.getRpId())
//			.put("photoPath", roomphoto.getPhotoPath())
//			.put("photoname", roomphoto.getPhotoname())
//			.put("photoDescription", roomphoto.getPhotoDescription());
//		}
		
		if(discounts != null && !discounts.isEmpty()) {
			for(Discount discount : discounts) {
				DiscountDetail discountDetail = discount.getDiscountDetail();
				
				if(discountDetail != null 
						&& discountDetail.getDiscountType().equals("專案")) {
					item.put("dDId", discountDetail.getDdId())
					.put("dDName", discountDetail.getName())
					.put("discountPrice", discountDetail.getDiscountPrice())
					.put("discountRate", discountDetail.getDiscountRate())
					.put("memberOnly", discountDetail.getMemberOnly())
					.put("idVerification", discountDetail.getIdVerification());
				}
			}
		}
			array.put(item);
			
		}
		
		responseJson.put("list", array);
        return responseJson.toString();
		
	}
	
    @GetMapping(
		path = "/room/{photoid}",
		produces = {MediaType.IMAGE_JPEG_VALUE}
	)
    public @ResponseBody byte[] findPhotoByPhotoId(@PathVariable(name = "photoid") Integer photoid) {
        RoomPhoto detail = roomPhotoService.findById(photoid);
        byte[] result = this.photo;
        if(detail!=null) {
            result = detail.getPhotoFile();
        }
        return result;
    }
	
	private byte[] photo = null;
    @PostConstruct
    public void initialize() throws IOException {
		byte[] buffer = new byte[8192];

		ClassLoader classLoader = getClass().getClassLoader();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		BufferedInputStream is = new BufferedInputStream(
				classLoader.getResourceAsStream("static/images/no-image.jpg"));
		int len = is.read(buffer);
		while(len!=-1) {
			os.write(buffer, 0, len);
			len = is.read(buffer);
		}
		is.close();
		this.photo = os.toByteArray();
    }
	
}
