package com.ispan.hotel.controller.n;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.Facility;

import com.ispan.hotel.model.Feature;
import com.ispan.hotel.model.RoomType;
import com.ispan.hotel.repository.n.RoomTypeRepositoryCBH;
import com.ispan.hotel.service.n.FacilityServiceCBH;
import com.ispan.hotel.service.n.FeatureServiceCBH;
import com.ispan.hotel.service.n.RoomTypeServiceCBH;





@CrossOrigin
@RestController
public class RoomTypeControllerCBH {
	
	@Autowired
	private RoomTypeServiceCBH roomTypeService;
	
	@Autowired
	private RoomTypeRepositoryCBH roomTypeRepository;
	
	@Autowired
	private FeatureServiceCBH featureService;
	
	@Autowired
	private FacilityServiceCBH facilityService;
	
	// 在頁面中使用page
	// http://localhost:8080/roomtype/page?p=2
	@PostMapping("/roomtype/page")
	public Page<RoomType> listRoomtypePage(@RequestBody String json) {
		
		JSONObject pageJson = new JSONObject(json);
		Integer pageNumber = pageJson.getInt("pageNumber");
		Integer rows = pageJson.getInt("rows");
		
		Page<RoomType> roomTypePage = roomTypeService.findByPage(pageNumber, rows);
		
		
		return roomTypePage;
	}
	
	
	// 接收表單插入新資料
	@PostMapping("/roomtype/save")
	public String create(@RequestBody String json) {
		
		System.out.println(json);
		
		JSONObject responseJson = new JSONObject();
		
		RoomType roomTypeData = roomTypeService.insertJsonRoomType(json);
		roomTypeRepository.save(roomTypeData);  
		
		if(roomTypeData == null) {
			
			responseJson.put("message", "新增失敗");
			return responseJson.toString();
			
		} else {
			responseJson.put("message", "新增成功");
			return responseJson.toString();
		}
		
		
		
	}
	
	// 接受表單更新資料
	@PutMapping("/roomtype/update")
	public String update(@RequestBody String json) {
		
		JSONObject responseJson = new JSONObject();
		
        RoomType roomTypeUpdate = roomTypeService.updateJsonRoomType(json);
        if(roomTypeUpdate==null) {
            responseJson.put("success", false);
            responseJson.put("message", "修改失敗");
        } else {
            responseJson.put("success", true);
            responseJson.put("message", "修改成功");
        }
        
        return responseJson.toString();
		
	}
	
	
	// 以 id 尋找
	@GetMapping("/roomtype/findById")
	public RoomType findById(@RequestParam("id") Integer id) {
		
		RoomType findById = roomTypeService.findById(id);
		if(findById != null) {
			return  findById;
		} 
		
		return null;
		
		
	}
	
	//以 id 刪除
	@DeleteMapping("/roomtype/deleteById")
	public void deleteRoomType(@RequestParam("rtId") Integer rtId) {
		
		roomTypeService.deleteRoomTypeById(rtId);
		
		
	}
	
	// 找所有房型
	@GetMapping("/roomtype/findAll")
	public List<RoomType> findAll() {
			
		return roomTypeService.findAllRoomType();
				
	}
		
	// 後端目前用到以上-------------------------
	// 前端豪華客房單人房
	@GetMapping("/roomtype/findsuperiorroom")
	public String findSuperiorRoomType(){
		
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
		
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("豪華客房單人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
				.put("HotelName", roomtype.getHotel().getName())
				.put("HotelAddress", roomtype.getHotel().getAddress())
				.put("HotelTel", roomtype.getHotel().getTel())
				.put("HotelFax", roomtype.getHotel().getFax())
				.put("HotelEmail", roomtype.getHotel().getContactEmail())
				.put("roomName", roomtype.getRoomName())
				.put("featureTittle", roomtype.getFeatureTittle())
				.put("featureTittleContent", roomtype.getFeatureTittleContent());
		
		array.put(item);
		responseJson.put("list", array);
		
		return responseJson.toString();
		
				
		
	}
	
	// 前端豪華客房兩人房
	@GetMapping("/roomtype/findsuperiorroom2")
	public String findSuperiorRoomType2(){
			
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
			
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("豪華客房兩人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
				.put("HotelName", roomtype.getHotel().getName())
				.put("HotelAddress", roomtype.getHotel().getAddress())
				.put("HotelTel", roomtype.getHotel().getTel())
				.put("HotelFax", roomtype.getHotel().getFax())
				.put("HotelEmail", roomtype.getHotel().getContactEmail())
				.put("roomName", roomtype.getRoomName())
				.put("featureTittle", roomtype.getFeatureTittle())
				.put("featureTittleContent", roomtype.getFeatureTittleContent());
			
		array.put(item);
		responseJson.put("list", array);
			
		return responseJson.toString();
				
	}
	
	// 前端豪華客房四人房
	@GetMapping("/roomtype/findsuperiorroom4")
	public String findSuperiorRoomType4(){
				
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
				
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("豪華客房四人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
				.put("HotelName", roomtype.getHotel().getName())
				.put("HotelAddress", roomtype.getHotel().getAddress())
				.put("HotelTel", roomtype.getHotel().getTel())
				.put("HotelFax", roomtype.getHotel().getFax())
				.put("HotelEmail", roomtype.getHotel().getContactEmail())
				.put("roomName", roomtype.getRoomName())
				.put("featureTittle", roomtype.getFeatureTittle())
				.put("featureTittleContent", roomtype.getFeatureTittleContent());
				
		array.put(item);
		responseJson.put("list", array);
				
		return responseJson.toString();
					
	}
	
	// 前端尊榮客房單人房 Deluxe
	@GetMapping("/roomtype/deluxe1")
	public String findDeluxeRoomType1(){
					
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
					
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("尊榮客房單人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
				.put("HotelName", roomtype.getHotel().getName())
				.put("HotelAddress", roomtype.getHotel().getAddress())
				.put("HotelTel", roomtype.getHotel().getTel())
				.put("HotelFax", roomtype.getHotel().getFax())
				.put("HotelEmail", roomtype.getHotel().getContactEmail())
				.put("roomName", roomtype.getRoomName())
				.put("featureTittle", roomtype.getFeatureTittle())
				.put("featureTittleContent", roomtype.getFeatureTittleContent());
					
		array.put(item);
		responseJson.put("list", array);
					
		return responseJson.toString();
						
	}
	
	// 前端尊榮客房兩人房
	@GetMapping("/roomtype/deluxe2")
		public String findDeluxeRoomType2(){
						
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
						
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("尊榮客房兩人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
				.put("HotelName", roomtype.getHotel().getName())
				.put("HotelAddress", roomtype.getHotel().getAddress())
				.put("HotelTel", roomtype.getHotel().getTel())
				.put("HotelFax", roomtype.getHotel().getFax())
				.put("HotelEmail", roomtype.getHotel().getContactEmail())
				.put("roomName", roomtype.getRoomName())
				.put("featureTittle", roomtype.getFeatureTittle())
				.put("featureTittleContent", roomtype.getFeatureTittleContent());
						
		array.put(item);
		responseJson.put("list", array);
						
		return responseJson.toString();
							
	}
	
	// 前端尊榮客房四人房
	@GetMapping("/roomtype/deluxe4")
	public String findDeluxeRoomType4(){
							
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
							
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("尊榮客房四人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
							
		array.put(item);
		responseJson.put("list", array);
							
		return responseJson.toString();
								
	}
	
	// 前端景觀尊榮客房單人房 Deluxe View Room
	@GetMapping("/roomtype/deluxeviewroom1")
	public String findDeluxeViewRoomType1(){
		
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
							
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("景觀尊榮客房單人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
							
		array.put(item);
		responseJson.put("list", array);
							
		return responseJson.toString();
								
	}	
	
	// 前端景觀尊榮客房兩人房 Deluxe View Room
	@GetMapping("/roomtype/deluxeviewroom2")
	public String findDeluxeViewRoomType2(){
			
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
								
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("景觀尊榮客房兩人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
								
		array.put(item);
		responseJson.put("list", array);
								
		return responseJson.toString();
									
	}	
	
	// 前端景觀尊榮客房四人房 Deluxe View Room
	@GetMapping("/roomtype/deluxeviewroom4")
	public String findDeluxeViewRoomType4(){
				
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
									
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("景觀尊榮客房四人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
									
		array.put(item);
		responseJson.put("list", array);
									
		return responseJson.toString();
									
	}
	
	// 前端特選尊榮家庭客房四人房 Grand Deluxe Family Room
	@GetMapping("/roomtype/deluxefamilyroom4")
	public String findDeluxeFamilyRoomType4(){
				
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
									
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("特選尊榮家庭客房四人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
									
		array.put(item);
		responseJson.put("list", array);
									
		return responseJson.toString();
									
	}
	
	// 前端超豪華客房單人房 Premier Room
	@GetMapping("/roomtype/premierroomtype1")
	public String findPremierRoomRoomType1(){
				
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
									
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("超豪華客房單人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
									
		array.put(item);
		responseJson.put("list", array);
									
		return responseJson.toString();
									
	}
	
	// 前端超豪華客房兩人房 Premier Room
	@GetMapping("/roomtype/premierroomtype2")
	public String findPremierRoomRoomType2(){
					
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
										
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("超豪華客房兩人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
										
		array.put(item);
		responseJson.put("list", array);
										
		return responseJson.toString();
										
	}
	
	// 前端超豪華客房四人房 Premier Room
	@GetMapping("/roomtype/premierroomtype4")
	public String findPremierRoomRoomType4(){
						
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
											
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("超豪華客房四人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
											
		array.put(item);
		responseJson.put("list", array);
											
		return responseJson.toString();
											
	}
	
	// 前端豪華閣豪華客房單人房 Horizon Superior Room
	@GetMapping("/roomtype/horizonsuperiorroomtype1")
	public String findHorizonSuperiorRoomRoomType1(){
						
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
											
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("豪華閣豪華客房單人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
											
		array.put(item);
		responseJson.put("list", array);
											
		return responseJson.toString();
											
	}
	
	// 前端豪華閣豪華客房兩人房 Horizon Superior Room
	@GetMapping("/roomtype/horizonsuperiorroomtype2")
	public String findHorizonSuperiorRoomRoomType2(){
							
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
												
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("豪華閣豪華客房兩人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
												
		array.put(item);
		responseJson.put("list", array);
												
		return responseJson.toString();
												
	}
	
	// 前端豪華閣豪華客房四人房 Horizon Superior Room
	@GetMapping("/roomtype/horizonsuperiorroomtype4")
	public String findHorizonSuperiorRoomRoomType4(){
								
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
													
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("豪華閣豪華客房四人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
													
		array.put(item);
		responseJson.put("list", array);
													
		return responseJson.toString();
													
	}
	
	// 豪華閣尊榮客房單人房 Horizon Deluxe Room
	@GetMapping("/roomtype/horizondeluxeroomtype1")
	public String findHorizonDeluxeRoomRoomType1() {
								
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
													
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("豪華閣尊榮客房單人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
													
		array.put(item);
		responseJson.put("list", array);
													
		return responseJson.toString();
													
	}
	
	// 豪華閣尊榮客房兩人房 Horizon Deluxe Room
	@GetMapping("/roomtype/horizondeluxeroomtype2")
	public String findHorizonDeluxeRoomRoomType2() {
									
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
														
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("豪華閣尊榮客房兩人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
														
		array.put(item);
		responseJson.put("list", array);
														
		return responseJson.toString();
														
	}
	
	// 豪華閣尊榮客房四人房 Horizon Deluxe Room
	@GetMapping("/roomtype/horizondeluxeroomtype4")
	public String findHorizonDeluxeRoomRoomType4() {
										
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
															
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("豪華閣尊榮客房四人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
															
		array.put(item);
		responseJson.put("list", array);
															
		return responseJson.toString();
															
	}
	
	//景觀超豪華客房單人房 Premier View Room
	@GetMapping("/roomtype/premierviewroomroomtype1")
	public String findPremierViewRoomRoomType1() {
										
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
															
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("景觀超豪華客房單人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
															
		array.put(item);
		responseJson.put("list", array);
															
		return responseJson.toString();
															
	}
	
	//景觀超豪華客房兩人房 Premier View Room
	@GetMapping("/roomtype/premierviewroomroomtype2")
	public String findPremierViewRoomRoomType2() {
											
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
																
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("景觀超豪華客房兩人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
																
		array.put(item);
		responseJson.put("list", array);
																
		return responseJson.toString();
																
	}
	
	//景觀超豪華客房四人房 Premier View Room
	@GetMapping("/roomtype/premierviewroomroomtype4")
	public String findPremierViewRoomRoomType4() {
												
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
																	
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("景觀超豪華客房四人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
																	
		array.put(item);
		responseJson.put("list", array);
																	
		return responseJson.toString();
																	
	}
	
	//特級套房單人房 Specialty Suite
	@GetMapping("/roomtype/specialtysuiteroomtype1")
	public String findSpecialtySuiteRoomType1() {
													
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
																		
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("特級套房單人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
																		
		array.put(item);
		responseJson.put("list", array);
																		
	return responseJson.toString();
																		
	}
	
	//特級套房兩人房 Specialty Suite
	@GetMapping("/roomtype/specialtysuiteroomtype2")
	public String findSpecialtySuiteRoomType2() {
														
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
																			
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("特級套房兩人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
																			
		array.put(item);
		responseJson.put("list", array);
																			
	return responseJson.toString();
																			
	}
	
	//特級套房四人房 Specialty Suite
	@GetMapping("/roomtype/specialtysuiteroomtype4")
	public String findSpecialtySuiteRoomType4() {
															
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
																				
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("特級套房四人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
																				
		array.put(item);
		responseJson.put("list", array);
																				
	return responseJson.toString();
																				
	}
	
	// 雅仕套房兩人房Plaza Suite
	@GetMapping("/roomtype/plazasuiteroomtype2")
	public String findPlazaSuiteRoomType2() {
															
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
																				
		Optional<RoomType> findbyname = roomTypeRepository.findByRoomTypeNameOptimal("雅仕套房兩人房");
		RoomType roomtype = findbyname.get();
		JSONObject item = new JSONObject()
			.put("HotelName", roomtype.getHotel().getName())
			.put("HotelAddress", roomtype.getHotel().getAddress())
			.put("HotelTel", roomtype.getHotel().getTel())
			.put("HotelFax", roomtype.getHotel().getFax())
			.put("HotelEmail", roomtype.getHotel().getContactEmail())
			.put("roomName", roomtype.getRoomName())
			.put("featureTittle", roomtype.getFeatureTittle())
			.put("featureTittleContent", roomtype.getFeatureTittleContent());
																				
		array.put(item);
		responseJson.put("list", array);
																				
	return responseJson.toString();
																				
	}
	
	//--------------------------------------------------------------------------
	
	
	// 多對多插入 Feature
	@GetMapping("/roomtype/manytomanyfeatureinsert")
	public RoomType manytomanyfeatureinsert() {
		
		
		// 多對多 Feature 側
		List<Feature> featureData = new ArrayList<>();
		featureData.add(featureService.findById(1));
		
		// 多對多 RoomType 側
		
		RoomType rt1 = roomTypeService.findById(1);
		// 將另一側的多方集合存到 RoomType 中
		rt1.setFeature(featureData);
		
		roomTypeService.insertRoomType(rt1);
		return rt1;
		
	}
	
	// 多對多插入 Facility
		@GetMapping("/roomtype/manytomanyfacilityinsert")
		public RoomType manytomanyfacilityinsert() {
			
			
			// 多對多 Feature 側
			List<Facility> facilityData = new ArrayList<>();
			facilityData.add(facilityService.findById(1));
			
			// 多對多 RoomType 側
			RoomType rt1 = roomTypeService.findById(1);
			rt1.setFacility(facilityData);
			
			roomTypeService.insertRoomType(rt1);
			return rt1;
			
		}
	
	
	
		
		
	// 多對多修改
	@GetMapping("/roomtype/manytomanyupdate")
	public RoomType manytomanyupdate() {
		
		RoomType rt1 = roomTypeService.findById(2);
		
		List<Feature> feature = new ArrayList<>();
		feature.add(featureService.findById(7));
		feature.add(featureService.findById(11));
		
		rt1.setFeature(feature);
		
		roomTypeService.insertRoomType(rt1);
		return rt1;
		
	}
	
	// 多對多查詢
	@GetMapping("/roomtype/manytomanyfind")
	public String manytomanyfindById(@RequestParam("id") Integer id) {
		RoomType rt1 = roomTypeService.findById(id);
		return rt1.toString();
		
	}
	
	// 多對多刪除就是刪除指定 id 的 RoomType, 上面寫過了
	
	// 使用 HQL 語法以房型名稱當作篩選條件
	//用網址測試: http://localhost:8080/roomtype/name?name=尊榮客房
	@GetMapping("/roomtypename/name")
	public List<RoomType> findByRoomTypeName(@RequestParam("name") String name) {
		
		return roomTypeRepository.findByRoomName(name);
		
	}
	

	

}
