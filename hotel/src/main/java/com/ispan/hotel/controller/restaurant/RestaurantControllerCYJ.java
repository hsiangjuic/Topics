package com.ispan.hotel.controller.restaurant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ispan.hotel.model.Restaurant;
import com.ispan.hotel.repository.restaurant.RestaurantBookingRepositoryCYJ;
import com.ispan.hotel.service.restaurant.RestaurantBookingServiceCYJ;
import com.ispan.hotel.service.restaurant.RestaurantPhotoServiceCYJ;
import com.ispan.hotel.service.restaurant.RestaurantServiceCYJ;
import com.ispan.hotel.util.DatetimeConverter;

@RestController
@CrossOrigin
public class RestaurantControllerCYJ {

	@Autowired
	private RestaurantBookingServiceCYJ restaurantBookingService;

	@Autowired
	private RestaurantPhotoServiceCYJ restaurantPhotoService;

	@Autowired
	private RestaurantServiceCYJ restaurantService;
	
	@Autowired
    private RestaurantBookingRepositoryCYJ restaurantBookingRepository;
	
//	試著自己寫
//	@PostMapping("/restaurant/find")
//	public String bookingcheck(@RequestBody)
	
	
//	判斷是否還可以預定
	@PostMapping("/restaurant/checkBooking")
    public String checkBooking(@RequestBody String json) {
        JSONObject response = new JSONObject();
        JSONObject obj = new JSONObject(json);
        		
        String reserveTimeStr = obj.isNull("reserveTime") ? null : obj.getString("reserveTime");
        if (reserveTimeStr == null || reserveTimeStr.isEmpty()) {
            // 在這裡處理日期為空的情況，例如返回錯誤訊息或採取其他操作
        	System.out.println("卡住了");
        } else {
            LocalDate reserveTime = DatetimeConverter.parseLocalDate(reserveTimeStr, "yyyy-MM-dd");
            // 繼續處理日期
        }
        String mealTime = obj.isNull("mealTime") ? null : obj.getString("mealTime");
        
        // Count total guests on the date
        long totalGuestsOnDate = restaurantBookingRepository.countByReserveTime(LocalDate.parse(reserveTimeStr));
        
        // Check if total guests on the date exceed 200
        boolean allowBookingOnDate = totalGuestsOnDate <= 200;
        
        // Count total guests for specific meal time
        long totalGuestsForMealTime = restaurantBookingRepository.countByReserveTimeAndMealTime(LocalDate.parse(reserveTimeStr), mealTime);
        
        // Check if total guests for specific meal time exceed 30
        boolean allowBookingForMealTime = totalGuestsForMealTime <= 30;
        
        // Booking is allowed if both conditions are satisfied
        boolean allowBooking = allowBookingOnDate && allowBookingForMealTime;
        
        response.put("reId", 1);
        response.put("allowBooking", allowBooking);
        response.put("message", allowBooking ? "Booking is allowed" : "Booking is not allowed");
        
        return response.toString();
    }




}
