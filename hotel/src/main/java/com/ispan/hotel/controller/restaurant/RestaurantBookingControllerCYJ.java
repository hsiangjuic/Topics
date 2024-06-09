package com.ispan.hotel.controller.restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.RestaurantBooking;
import com.ispan.hotel.repository.restaurant.RestaurantBookingRepositoryCYJ;
import com.ispan.hotel.service.restaurant.RestaurantBackendServiceCYJ;
import com.ispan.hotel.service.restaurant.RestaurantBookingServiceCYJ;
import com.ispan.hotel.service.restaurant.RestaurantServiceCYJ;

@RestController
//@RequestMapping("/restaurant/booking")
@CrossOrigin
public class RestaurantBookingControllerCYJ {

	@Autowired
	private RestaurantBookingServiceCYJ restaurantBookingService;

	@Autowired
	private RestaurantServiceCYJ restaurantService;

	@Autowired
	private RestaurantBackendServiceCYJ restaurantBackendService;

	@Autowired
	private RestaurantBookingRepositoryCYJ restaurantBookingRepository;

	// chatgpt給的方法，目前是使用此方法
	@GetMapping("/allData")
	public ResponseEntity<List<RestaurantBooking>> getAllRestaurantBookings() {
		List<RestaurantBooking> restaurantBookings = restaurantBookingService.find();
		return new ResponseEntity<>(restaurantBookings, HttpStatus.OK);
	}

	// 查詢(全部)，6/3再次試著寫
	@PostMapping("/restaurant/findAll")
	public String findAll(@RequestBody Optional<String> jsonOptional) {
		String json = jsonOptional.orElse(null); // 如果jsonOptional是空的，則將其轉換為null
		JSONObject responseJson = new JSONObject(); // 用來裝本方法回傳內容的JSON物件
		JSONArray array = new JSONArray();// 用來裝本頁要顯示的內容
		List<RestaurantBooking> restaurantBooking = restaurantBackendService.findAll(json).getContent();// 找到本頁要顯示的資料list
		if (restaurantBooking != null && !restaurantBooking.isEmpty()) {// 如果有找到
			// 如果找到了相關數據，將其轉換為 JSON 格式
			for (RestaurantBooking one : restaurantBooking) {// 我找到latestNews裡的每個物件one，把one的資料取出後放到JSON物件item中，再把item放進JSONArray
																// array裡
				JSONObject item = new JSONObject()
						.put("rebId", one.getRebId())
						.put("isCustomer", one.getIsCustomer())
						.put("totalAdult", one.getTotalAdult())
						.put("totalChildren", one.getTotalChildren())
						.put("totalTable", one.getTotalTable())
						.put("resverseTime", one.getReserveTime())
						.put("mealTime", one.getMealTime())
						.put("firstName", one.getFirstName())
						.put("lastName", one.getLastName())
						.put("cellphone", one.getCellphone())
						.put("email", one.getEmail())
						.put("remark", one.getRemark())
						.put("lastModifiedDate", one.getLastModifiedDate())
						.put("lastModifiedEmp", one.getLastModifiedEmp());

				
//				if (one.getRestaurant() != null) {
//					item.put("reId", one.getRestaurant().getReId());
//				}

				array.put(item);
			}
		}
		responseJson.put("list", array);// 把array放進responseJson

		responseJson.put("count", restaurantBackendService.findAll(json).getTotalElements()); // 資料總數
		responseJson.put("pages", restaurantBackendService.findAll(json).getTotalPages()); // 總頁數

		return responseJson.toString();
	}

//	查詢單筆 //	後台的資料狀態更新
	@GetMapping("/restaurant/{pk}")
	public String findById(@PathVariable(name = "pk") Integer id) {
		JSONObject responseJson = new JSONObject();
		JSONArray array = new JSONArray();
		if (restaurantBookingService.existsById(id)) { // 如果有查詢到資料
			RestaurantBooking restaurantBooking = restaurantBookingService.findById(id);
			JSONObject item = new JSONObject().put("rebId", restaurantBooking.getRebId())
					.put("reId", restaurantBooking.getRestaurant().getReId())
					.put("isCustomer", restaurantBooking.getIsCustomer())
					.put("totalAdult", restaurantBooking.getTotalAdult())
					.put("totalChildren", restaurantBooking.getTotalChildren())
					.put("resverseTime", restaurantBooking.getReserveTime())
					.put("mealTime", restaurantBooking.getMealTime())
					.put("totalTable", restaurantBooking.getTotalTable())
					.put("firstName", restaurantBooking.getFirstName()).put("lastName", restaurantBooking.getLastName())
					.put("cellphone", restaurantBooking.getCellphone()).put("email", restaurantBooking.getEmail())
					.put("remark", restaurantBooking.getRemark());
			array.put(item);
			responseJson.put("list", array);
		} else {
			responseJson.put("success", false);
			responseJson.put("message", "此id查無資料");
		}
		return responseJson.toString();
	}
////	查詢單筆 //	後台的資料狀態更新
//	@GetMapping("/restaurant/{pk}")
//	public String findById(@PathVariable(name = "pk") Integer id) {
//		JSONObject responseJson = new JSONObject();
//		JSONArray array = new JSONArray();
//		if (restaurantBookingService.existsById(id)) { // 如果有查詢到資料
//			RestaurantBooking restaurantBooking = restaurantBookingService.findById(id);
//			JSONObject item = new JSONObject().put("rebId", restaurantBooking.getRebId())
//					.put("reId", restaurantBooking.getRestaurant().getReId())
//					.put("isCustomer", restaurantBooking.getIsCustomer())
//					.put("totalAdult", restaurantBooking.getTotalAdult())
//					.put("totalChildren", restaurantBooking.getTotalChildren())
//					.put("resverseTime", restaurantBooking.getReserveTime())
//					.put("mealTime", restaurantBooking.getMealTime())
//					.put("totalTable", restaurantBooking.getTotalTable())
//					.put("firstName", restaurantBooking.getFirstName()).put("lastName", restaurantBooking.getLastName())
//					.put("cellphone", restaurantBooking.getCellphone()).put("email", restaurantBooking.getEmail())
//					.put("remark", restaurantBooking.getRemark());
//			array.put(item);
//			responseJson.put("list", array);
//		} else {
//			responseJson.put("success", false);
//			responseJson.put("message", "此id查無資料");
//		}
//		return responseJson.toString();
//	}

//	新增
	@PostMapping("/restaurant/createcustomer")
	public String create(@RequestBody String json) {
		JSONObject responseJson = new JSONObject();
		JSONObject obj = new JSONObject(json);

		RestaurantBooking res = restaurantBookingService.create(json);
		if (res != null) {
			responseJson.put("success", true);
			responseJson.put("message", "成功");
		} else {
			responseJson.put("success", false);
			responseJson.put("message", "失敗");
		}

		return responseJson.toString();

	}


	

	// 後台台找出客戶預訂資料並修改 postman-使用此版本
		@PutMapping("/restaurantbackend/modify/{rebId}")
		public String backendModify(@PathVariable(name = "rebId") Integer rebId, @RequestBody String json) {
			JSONObject responseJSON = new JSONObject();
			RestaurantBooking bookingDetail = restaurantBackendService.findById(rebId);
			System.out.println("bookingDetail = " + bookingDetail);

			// 可以修改備註
			if (bookingDetail == null) {
				responseJSON.put("message", "訂單查詢失敗").toString();
				responseJSON.put("success", false);
			}
//			處理restauarantBooking上的備註
			RestaurantBooking result = restaurantBackendService.backendModify(bookingDetail, json);
//			RestaurantBooking result = restaurantBookingService.modify(json);
			if (result != null) {
				responseJSON.put("rebId", bookingDetail.getRebId());
				responseJSON.put("message", "更新成功");
				responseJSON.put("success", true);
			} else {
				responseJSON.put("message", "更新失敗");
				responseJSON.put("success", false);
			}
			return responseJSON.toString();
		}
		
		

	// 前台找出客戶預訂資料並修改 postman-使用此版本
	@PutMapping("/restaurant/booking/modify/{rebId}")
	public String customerModifyBooking(@PathVariable(name = "rebId") Integer rebId, @RequestBody String json) {
		JSONObject responseJSON = new JSONObject();
		RestaurantBooking bookingDetail = restaurantBookingService.findById(rebId);
		System.out.println("bookingDetail = " + bookingDetail);

		// 可以修改備註
		if (bookingDetail == null) {
			responseJSON.put("message", "訂單查詢失敗").toString();
			responseJSON.put("success", false);
		}
//		處理restauarantBooking上的備註
		RestaurantBooking result = restaurantBookingService.customerModify(bookingDetail, json);
//		RestaurantBooking result = restaurantBookingService.modify(json);
		if (result != null) {
			responseJSON.put("rebId", bookingDetail.getRebId());
			responseJSON.put("message", "更新成功");
			responseJSON.put("success", true);
		} else {
			responseJSON.put("message", "更新失敗");
			responseJSON.put("success", false);
		}
		return responseJSON.toString();
	}

//	刪除  
	@DeleteMapping("/restaurant/booking/remove/{rebId}")
	public String remove(@PathVariable(name = "rebId") Integer rebId) {
		JSONObject responseJson = new JSONObject();
		if (rebId == null) { // 沒給id的話
			responseJson.put("success", false);
			responseJson.put("message", "id是必要欄位");
		} else if (!restaurantBookingService.existsById(rebId)) { // id不存在的話
			responseJson.put("success", false);
			responseJson.put("message", "id不存在");
		} else { // 有給id且id存在的話
			if (restaurantBookingService.delete(rebId)) {// deleteById成功回傳true
				responseJson.put("success", true);
				responseJson.put("message", "刪除成功");
			} else {
				responseJson.put("success", false);// deleteById 失敗回傳false
				responseJson.put("message", "刪除失敗");
			}
		}
		return responseJson.toString();
	}

	// 前端客戶查詢訂單 用在bookingcheck 目前放棄這個做法
	@PostMapping("restaurant/customer/check")
	public String customerCheck(@RequestBody String json) {
		JSONObject responseJSON = new JSONObject();
		JSONObject obj = new JSONObject(json);
		String reserveTimeStr = obj.isNull("reserveTime") ? null : obj.getString("reserveTime");
		String email = obj.isNull("email") ? null : obj.getString("email");

		LocalDate reserveTime = LocalDate.parse(reserveTimeStr);

		if (reserveTimeStr != null && email != null && reserveTimeStr.length() != 0 && email.length() != 0) {

			RestaurantBooking restaurantBooking1 = restaurantBookingService.findByEmail(email, reserveTime);

//			RestaurantBooking restaurantBooking2 = restaurantBookingService.findByReserveTime(reserveTime);
//			if (reserveTime != null && reserveTime.length() != 0) {
//				LocalDate temp = DatetimeConverter.parseLocalDate(reserveTime, "yyyy-MM-dd");
//			}

			if (restaurantBooking1 != null) {
//				 && rb1Time.equals(reserveTime)
//				String rb1Time =DatetimeConverter.toString(restaurantBooking1.getReserveTime(), "yyyy-MM-dd");
				if (restaurantBooking1.getEmail().equals(email)) {
					responseJSON.put("message", "查詢訂單");
					responseJSON.put("success", true);
					responseJSON.put("email", email);
					responseJSON.put("reserveTime", reserveTime);
				} else {
					responseJSON.put("message", "查無資料，請確認輸入無誤1");
					responseJSON.put("success", false);
				}
			} else {
				responseJSON.put("message", "查無資料，請確認輸入無誤2").toString();
				responseJSON.put("success", false);
			}
		} else {
			if (reserveTime == null && email == null) {
				responseJSON.put("message", "請填寫訂單日期跟郵件信箱").toString();
				responseJSON.put("success", false);
			} else if (reserveTime == null) {
				responseJSON.put("message", "請填寫訂單日期").toString();
				responseJSON.put("success", false);
			} else {
				responseJSON.put("message", "請填寫郵件信箱").toString();
				responseJSON.put("success", false);
			}
		}
		return responseJSON.toString();
	}

//	找客戶預訂訂單出來 postman-ok
	@PostMapping("/restaurant/booking/find")
	public String customerFindBooking(@RequestBody String json) {
		JSONObject responseJSON = new JSONObject();
		JSONObject obj = new JSONObject(json);
		String reserveTimeStr = obj.isNull("reserveTime") ? null : obj.getString("reserveTime");
		String email = obj.isNull("email") ? null : obj.getString("email");
//		String cellphone = obj.isNull("cellphone") ? null : obj.getString("cellphone");
//		RestaurantBooking bookingDetail = restaurantBookingService.findByCellphone(cellphone);
		RestaurantBooking bookingDetail = restaurantBookingService.findByE(email);
		if (bookingDetail != null) {
			return restaurantBookingService.findBookingData(bookingDetail);
		} else {
			responseJSON.put("message", "查無資料，請確認輸入無誤").toString();
			responseJSON.put("success", false);
		}
		return responseJSON.toString();
	}

	// 老師的方法
//	@PostMapping("/products/find")
//    public String find(@RequestBody String json) {
//        JSONObject responseJson = new JSONObject();
//
//        JSONArray array = new JSONArray();
//        List<RestaurantBooking> products = restaurantBookingService.find(json);
//        if(products!=null && !products.isEmpty()) {
//            for(RestaurantBooking bookingDetail : products) {
//                String reserveTime = DatetimeConverter.toString(bookingDetail.getReserveTime(), "yyyy-MM-dd");
//                JSONObject item = new JSONObject()
//                		.put("restaurant", bookingDetail.getRestaurant())
//                		.put("isCustomer", bookingDetail.getIsCustomer())
//                				.put("totalAdult", bookingDetail.getTotalAdult())
//                				.put("totalChildren", bookingDetail.getTotalChildren())
//                				.put("setTotalTable", bookingDetail.getTotalTable())
//                				.put("reserveTime", bookingDetail.getReserveTime())
//                				.put("mealTime", bookingDetail.getMealTime())
//                				.put("firstName", bookingDetail.getFirstName())
//                				.put("lastName", bookingDetail.getLastName())
//                				.put("cellphone", bookingDetail.getCellphone())
//                				.put("email", bookingDetail.getEmail())
//                				.put("remark", bookingDetail.getRemark())
//                				.put("rebId", bookingDetail.getRebId());
//                array.put(item);
//            }
//        }
//        responseJson.put("list", array);
//
//
//        return responseJson.toString();
//    }

}
