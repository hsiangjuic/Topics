package com.ispan.hotel.controller.booking;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.Booking;
import com.ispan.hotel.model.BookingDetail;
import com.ispan.hotel.model.Customer;
import com.ispan.hotel.model.Room;
import com.ispan.hotel.service.booking.BookingDetailServiceYTH;
import com.ispan.hotel.service.booking.BookingFindServiceYTH;
import com.ispan.hotel.service.booking.BookingProcessServiceYTH;
import com.ispan.hotel.service.booking.BookingServiceYTH;
import com.ispan.hotel.service.booking.CustomerServiceYTH;
import com.ispan.hotel.service.booking.RoomServiceYTH;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/booking")
@CrossOrigin
public class BookingControllerYTH {
	
	@Autowired
	private BookingProcessServiceYTH bookingProcessService;
	
	@Autowired
	private CustomerServiceYTH customerServiceYTH;
	
	@Autowired
	private BookingServiceYTH bookingServiceYTH;
	
	@Autowired
	private BookingDetailServiceYTH bookingDetailServiceYTH;
	
	@Autowired
	private BookingFindServiceYTH bookingFindServiceYTH;
	
	@Autowired
	private RoomServiceYTH roomServiceYTH;
	

	
	
	@PostMapping("/book/create")
	@Transactional
	public String createBooking(@RequestBody String json) {
		JSONObject responseJSON = new JSONObject();
		try {
			responseJSON.put("bookingInfo", bookingProcessService.createBooking(json));
			responseJSON.put("message", "成功").toString();
			responseJSON.put("success", true);
			return responseJSON.toString();
		} catch (Exception e) {
			e.printStackTrace();
			responseJSON.put("message", "失敗").toString();
			responseJSON.put("success", false);
			return responseJSON.toString();
		}
		
	}
	
	@GetMapping("/login/{id}")
	public String loginTest(HttpSession session, @PathVariable(name = "id") Integer id) {
		JSONObject responseJSON = new JSONObject();
		Customer customer = customerServiceYTH.findById(id);
		if(customer != null) {
			session.setAttribute("username", customer.getUsername());
			responseJSON.put("username", customer.getUsername());
			responseJSON.put("message", "成功").toString();
			responseJSON.put("success", true);
		}	else {
			responseJSON.put("message", "失敗").toString();
			responseJSON.put("success", false);
		}
		
		return responseJSON.toString();
	}
	
	@GetMapping("/book/hotel/{id}")
	public String hotelFindById(@PathVariable(name = "id") Integer id) {
		JSONObject responseJSON = new JSONObject();
		
		Booking booking = bookingServiceYTH.findById(id);
		if(booking != null) {
			JSONObject result = new JSONObject(bookingFindServiceYTH.findBookingData(booking));
			responseJSON.put("booking", result);
			responseJSON.put("message", "查詢成功");
			responseJSON.put("success", true);
		} else {
			responseJSON.put("message", "查無資料");
			responseJSON.put("success", false);
		}
		return responseJSON.toString();
	}
	
	@PostMapping("/book/hotel/find")
	public String hotelFind(@RequestBody String json) {
		JSONObject responseJSON = new JSONObject();
		JSONObject obj = new JSONObject(json);
		
		List<Booking> bookings = bookingServiceYTH.find(obj);
		JSONArray array = new JSONArray();
		
		if(bookings != null) {
			for(Booking one : bookings) {
				bookingFindServiceYTH.findBookingData(one);
				JSONObject result = new JSONObject(bookingFindServiceYTH.findBookingData(one));
				result.put("emptyRooms",otherEmptyRoom(one, result));
				
				array.put(result);
			}
		} else {
			responseJSON.put("message", "查無資料");
			responseJSON.put("success", false);
		}
		
		if(array != null) {
	        responseJSON.put("count", bookingServiceYTH.count(json));
			responseJSON.put("bookings", array);
			responseJSON.put("message", "查詢成功");
			responseJSON.put("success", true);
		}
		
		
		return responseJSON.toString();

	}
	
	private JSONArray otherEmptyRoom(Booking booking, JSONObject obj) {
		JSONArray array = new JSONArray();
		List<Room> oers = roomServiceYTH.findEmptyRoom(obj);
		if(oers != null) {
			for(Room oer : oers) {
				JSONObject emptyRoom = new JSONObject().put("roomNumber", oer.getRoomNumber());
				array.put(emptyRoom);
			}
		}
		if(array != null && !array.isEmpty()) {
			return array;
		} else {
			return null;
			
		}
	}
	
	@PutMapping("/book/hotel/modify/{bId}")
	public String hotelModifyBooking(@PathVariable(name = "bId") Integer bId, @RequestBody String json) {
		JSONObject responseJSON = new JSONObject();
		Booking booking = bookingServiceYTH.findById(bId);
		if(booking != null) {
			Booking result = bookingServiceYTH.hotelModify(json);
			if(result != null) {
				responseJSON.put("message", "更新成功");
				responseJSON.put("success", true);
				return responseJSON.toString();
			}
		}
		
		responseJSON.put("message", "更新失敗");
		responseJSON.put("success", false);
		return responseJSON.toString();
	}
	
	@DeleteMapping("/book/hotel/delete/{id}")
	public String hotelremove(@PathVariable(name = "id") Integer id) {
		JSONObject responseJson = new JSONObject();
		Booking booking = bookingServiceYTH.findById(id);
        if(id==null) {
            responseJson.put("success", false);
            responseJson.put("message", "按鈕配置錯誤");
        } else if(booking == null) {
            responseJson.put("success", false);
            responseJson.put("message", "訂單不存在");
        } else {
        	BookingDetail bd = booking.getBookingDetail();
            try {
				if(bookingProcessService.removeBooking(id)) {
					//	判斷是否還有同一個bookingDetail的訂單
				    List<Booking> bs = bookingServiceYTH.findByBookingDetail(bd);
				    if(bs == null || bs.isEmpty()) {
				    	if(!bookingDetailServiceYTH.delete(bd.getBdId())) {
				    		responseJson.put("message", "訂單完整刪除失敗").toString();
				    		responseJson.put("success", false);
				    	} else {
				    		responseJson.put("message", "訂單完整刪除成功").toString();
				    		responseJson.put("success", true);
				    	}
				    }else {
				    	responseJson.put("message", "刪除成功");
				    	responseJson.put("success", true);
				    }
				} else {                
				    responseJson.put("success", false);
				    responseJson.put("message", "刪除失敗");
				}
			} catch (Exception e) {
				e.printStackTrace();
				responseJson.put("message", "刪除過程錯誤").toString();
				responseJson.put("success", false);
				return responseJson.toString();
			}
            
        }
        return responseJson.toString();
	}
	
	@PostMapping("/book/customer/check")
	public String customerCheck(@RequestBody String json) {
		JSONObject responseJSON = new JSONObject();
		JSONObject obj = new JSONObject(json);
		String orderNumber = obj.isNull("orderNumber") ? null : obj.getString("orderNumber");
		String email = obj.isNull("email") ? null : obj.getString("email");
		
		
		if(orderNumber != null && email != null && orderNumber.length()!= 0 && email.length()!=0) {
			//find bookingDetail by order_number
			BookingDetail bookingDetail = bookingDetailServiceYTH.findByOrderNumber(orderNumber);
			if(bookingDetail != null) {
				//find customer by cId
				Customer customer = bookingDetail.getCustomer();
				if(customer != null) {
					if(customer.getEmail().equals(email)) {
						responseJSON.put("message", "查詢訂單");
						responseJSON.put("success", true);
						responseJSON.put("orderNumber", orderNumber);
					} else {
						responseJSON.put("message", "查無資料，請確認輸入無誤");
						responseJSON.put("success", false);
					}

				} else {
					responseJSON.put("message", "查無資料，請確認輸入無誤").toString();
					responseJSON.put("success", false);
				}
			} else {
				responseJSON.put("message", "查無資料，請確認輸入無誤").toString();
				responseJSON.put("success", false);
			}
			
		} else {
			if(orderNumber == null && email == null) {
				responseJSON.put("message", "需填寫信箱和訂單編號").toString();
				responseJSON.put("success", false);
			} else if(email == null) {
				responseJSON.put("message", "需填寫信箱號碼").toString();
				responseJSON.put("success", false);
			} else {
				responseJSON.put("message", "需填寫訂單編號").toString();
				responseJSON.put("success", false);
			}
		}
		return responseJSON.toString();
	}
	
	@PostMapping("/book/customer/find")
	public String customerFindBooking(@RequestBody String json) {
		JSONObject responseJSON = new JSONObject();
		JSONObject obj = new JSONObject(json);
		String orderNumber = obj.isNull("orderNumber") ? null : obj.getString("orderNumber");
		//find bookingDetail by order_number
		BookingDetail bookingDetail = bookingDetailServiceYTH.findByOrderNumber(orderNumber);
		if(bookingDetail != null) {
			return bookingFindServiceYTH.findBookingData(bookingDetail);
		} else {
			responseJSON.put("message", "查無資料，請確認輸入無誤").toString();
			responseJSON.put("success", false);
		}
		
		return responseJSON.toString();
	}
	
	@GetMapping("/book/customer/findbyorder/{orderNumber}")
	public String customerFindByOrderNumber(@PathVariable(name = "orderNumber") String orderNumber) {
		JSONObject responseJSON = new JSONObject();
		if(orderNumber.length()!= 0) {
			BookingDetail bookingDetail = bookingDetailServiceYTH.findByOrderNumber(orderNumber);
			responseJSON.put("paymentStatus", bookingDetail.getPaymentStatus());
			responseJSON.put("success", true);
			return responseJSON.toString();
		}
		responseJSON.put("message", "查無資料");
		responseJSON.put("success", false);
		return responseJSON.toString();
	}
	
	@PutMapping("/book/customer/modify/{bdId}")
	public String customerModifyBooking(@PathVariable(name = "bdId") Integer bdId, @RequestBody String json) {
		//可修改 預計抵達時間、備註
		JSONObject responseJSON = new JSONObject();
		BookingDetail bookingDetail = bookingDetailServiceYTH.findById(bdId);
		
		System.out.println("bookingDetail = " + bookingDetail);
		if(bookingDetail == null) {
			responseJSON.put("message", "訂單查詢失敗").toString();
			responseJSON.put("success", false);
		}
		//處理booking上的早餐!
		JSONArray bookings = new JSONObject(json).getJSONArray("bookings");
		if(bookings != null) {
			for(int i = 0; i < bookings.length(); i++) {
				JSONObject bookingObj = bookings.getJSONObject(i);
				Booking booking = bookingServiceYTH.customerModify(bookingObj);
				if(booking == null) {
					responseJSON.put("message", "更新失敗");
					responseJSON.put("success", false);
				}
			}
		}
			
		//處理bookingDetail上的抵達時間以及備註!
		BookingDetail result = bookingDetailServiceYTH.customerModify(bookingDetail, json);
		if(result != null) {
			responseJSON.put("orderNumber", bookingDetail.getOrderNumber());
			responseJSON.put("message", "更新成功");
			responseJSON.put("success", true);
		} else {
			responseJSON.put("message", "更新失敗");
			responseJSON.put("success", false);
		}
		
		
		return responseJSON.toString();
	}
	
	
}
