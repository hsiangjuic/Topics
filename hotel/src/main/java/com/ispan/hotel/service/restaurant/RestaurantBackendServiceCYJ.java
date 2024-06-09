package com.ispan.hotel.service.restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.RestaurantBooking;
import com.ispan.hotel.repository.restaurant.RestaurantBookingRepositoryCYJ;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RestaurantBackendServiceCYJ {

	@Autowired
	private RestaurantBookingRepositoryCYJ restaurantBookingRepository;

	@Autowired
	private RestaurantServiceCYJ restaurantService;

	@Autowired
	private RestaurantBookingServiceCYJ restaurantbookingService;

	@PersistenceContext
	private Session session;

	public Session getSession() {
		return this.session;
	}

	// 查詢多筆資料:依據狀態及類別(有分頁) 0515的努力成果^^(0520更新為回傳page物件
	public Page<RestaurantBooking> findAll(String json) {// List<String> statuses
		JSONObject obj = new JSONObject(json);
		JSONArray isCustomer = obj.isNull("isCustomer") ? null : obj.getJSONArray("isCustomer"); // 取得JSONArray

		// JSONArray轉換為List<String> stringList
		List<String> rebIdList = new ArrayList<>();
		for (int i = 0; i < isCustomer.length(); i++) {
			String value = isCustomer.getString(i);
			rebIdList.add(value);
		}

		// 有分頁版本
		// 解析 JSON，獲取分頁相關的參數
		Pageable pageable = getPageableFromJson(obj);
		// 執行分頁查詢
		Page<RestaurantBooking> pageResult = restaurantBookingRepository.selectByrebId(rebIdList, pageable);
//			// 返回分頁結果的數據列表
//			List<LatestNews> result = pageResult.getContent();    

		return pageResult;
	}

	// [分頁用private方法]
	private Pageable getPageableFromJson(JSONObject obj) {
		Integer current = obj.isNull("current") ? 1 : obj.getInt("current");
		Integer rows = obj.isNull("rows") ? 10 : obj.getInt("rows");
		Boolean dir = obj.isNull("dir") ? true : obj.getBoolean("dir"); // 預設為true
		String order = obj.isNull("order") ? "rebId" : obj.getString("order"); // 預設為"rebId"
		// 指定分頁和排序
		Sort.Direction direction = dir ? Sort.Direction.DESC : Sort.Direction.ASC; // 學學OwO
		return PageRequest.of(current - 1, rows, direction, order);
	}
	
//	查詢一筆資料(使用id)
	public RestaurantBooking findById(Integer rebId) {
		if (rebId != null) {
			Optional<RestaurantBooking> optional = restaurantBookingRepository.findById(rebId);
			if (optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}
	
	public RestaurantBooking backendModify(RestaurantBooking bookingDetail, String json) {
		JSONObject obj = new JSONObject(json);
		if(bookingDetail != null) {
			
			Integer rebId = obj.isNull("rebId") ? null : obj.getInt("rebId");
			Boolean isCustomer = obj.isNull("isCustomer") ? true : obj.getBoolean("isCustomer");
			Integer totalAdult = obj.isNull("totalAdult") ? null : obj.getInt("totalAdult");
			Integer totalChildren = obj.isNull("totalChildren") ? null : obj.getInt("totalChildren");
			Integer totalTable = obj.isNull("totalTable") ? null : obj.getInt("totalTable");
			String reserveTime = obj.isNull("reserveTime") ? null : obj.getString("reserveTime");
			String mealTime = obj.isNull("mealTime") ? null : obj.getString("mealTime");
			String firstName = obj.isNull("firstName") ? null : obj.getString("firstName");
			String lastName = obj.isNull("lastName") ? null : obj.getString("lastName");
			String cellphone = obj.isNull("cellphone") ? null : obj.getString("cellphone");
			String email = obj.isNull("email") ? null : obj.getString("email");
			Integer reId = obj.isNull("reId") ? null : obj.getInt("reId");
			String remark = obj.isNull("remark") ? null : obj.getString("remark");
			String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
			String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
			
			bookingDetail.setIsCustomer(true);
			bookingDetail.setTotalAdult(totalAdult);
			bookingDetail.setTotalChildren(totalChildren);
//			bookingDetail.setReserveTime(reserveTime);
			bookingDetail.setMealTime(mealTime);
			bookingDetail.setFirstName(firstName);
			bookingDetail.setLastName(lastName);
			bookingDetail.setCellphone(cellphone);
			bookingDetail.setEmail(email);
			bookingDetail.setRemark(remark);
			
			
			return restaurantBookingRepository.save(bookingDetail);
		}
		return null;
	}

	//------------------老師的寫法，目前無法使用--------------------------------
//	public List<RestaurantBooking> find (String json){
//			try {
//				JSONObject obj = new JSONObject(json);
//				return restaurantBookingRepository.find(obj);
//			}catch(JSONException e){
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//	public long count(String json) {
//		try {
//			JSONObject obj = new JSONObject(json);
//			return restaurantBookingRepository.count(obj);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}

}
