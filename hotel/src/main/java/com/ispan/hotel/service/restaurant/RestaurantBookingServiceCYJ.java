package com.ispan.hotel.service.restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Restaurant;
import com.ispan.hotel.model.RestaurantBooking;
import com.ispan.hotel.repository.restaurant.RestaurantBookingRepositoryCYJ;
import com.ispan.hotel.util.DatetimeConverter;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Service
public class RestaurantBookingServiceCYJ {

	@Autowired
	private RestaurantBookingRepositoryCYJ restaurantBookingRepository;

	@Autowired
	private RestaurantServiceCYJ restaurantService;

	@PersistenceContext
	private Session session;

	public Session getSession() {
		return this.session;
	}

//	新增資料
	public RestaurantBooking create(String json) {
		JSONObject obj = new JSONObject(json);

		Integer rebId = obj.isNull("rebId") ? null : obj.getInt("rebId");

//		isCustomer(T/F)
		Boolean isCustomer = obj.isNull("isCustomer") ? true : obj.getBoolean("isCustomer");
//		total adult
		Integer totalAdult = obj.isNull("totalAdult") ? null : obj.getInt("totalAdult");
//		total children
		Integer totalChildren = obj.isNull("totalChildren") ? null : obj.getInt("totalChildren");
//		total table
		Integer totalTable = obj.isNull("totalTable") ? null : obj.getInt("totalTable");

//		reserve time
		String reserveTime = obj.isNull("reserveTime") ? null : obj.getString("reserveTime");

//		meal time
		String mealTime = obj.isNull("mealTime") ? null : obj.getString("mealTime");

//		first name
		String firstName = obj.isNull("firstName") ? null : obj.getString("firstName");
//		last name
		String lastName = obj.isNull("lastName") ? null : obj.getString("lastName");

//		cellphone
		String cellphone = obj.isNull("cellphone") ? null : obj.getString("cellphone");

//		email
		String email = obj.isNull("email") ? null : obj.getString("email");
//		re_id
		Integer reId = obj.isNull("reId") ? null : obj.getInt("reId");
//		remark
		String remark = obj.isNull("remark") ? null : obj.getString("remark");

//		最後修改人員
		String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
//		最後修改紀錄
		String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");

		if (reId != null) {
			Restaurant restaurant = restaurantService.findById(reId);
			if (restaurant != null) {
				RestaurantBooking insert = new RestaurantBooking();

				insert.setRestaurant(restaurant);
				insert.setIsCustomer(isCustomer);
				insert.setTotalAdult(totalAdult);
				insert.setTotalChildren(totalChildren);
				insert.setTotalTable(totalTable);
				if (reserveTime != null && reserveTime.length() != 0) {
					LocalDate temp = DatetimeConverter.parseLocalDate(reserveTime, "yyyy-MM-dd");
					insert.setReserveTime(temp);
				} else {
					insert.setReserveTime(null);
				}
				insert.setMealTime(mealTime);
				insert.setFirstName(firstName);
				insert.setLastName(lastName);
				insert.setCellphone(cellphone);
				insert.setEmail(email);
				insert.setRemark(remark);
				insert.setLastModifiedEmp(lastModifiedEmp);
				insert.setLastModifiedText(lastModifiedText);

				return restaurantBookingRepository.save(insert);
			}

		}
		return null;
	}

//	查詢多筆資料(分頁內容)  先跟著寫
	public List<RestaurantBooking> findPageContent(String json) {
		JSONObject obj = new JSONObject();
		Integer start = obj.isNull("start") ? null : obj.getInt("start");
		Integer rows = obj.isNull("rows") ? null : obj.getInt("rows");
		Boolean dir = obj.isNull("dir") ? null : obj.getBoolean("dir");
		String order = obj.isNull("order") ? null : obj.getString("order");
		String name = obj.isNull("name") ? null : obj.getString("name");
//		{
//	    "start":0,
//	    "rows":3,
//	    "dir":false,
//	    "order":"id",
//	    "name":"a"
//		}
//		分頁從第 1 頁開始
		if (start == null || start < 1) {
			start = 1;
		}
		Pageable pageable = null;
//		指定分頁和排序
		if (dir == true || dir == null) {
			pageable = PageRequest.of(start - 1, rows, Sort.Direction.DESC, order);
		} else if (dir == false) {
			pageable = PageRequest.of(start - 1, rows, Sort.Direction.ASC, order);
		}

//		執行分頁查詢
		Page<RestaurantBooking> page = restaurantBookingRepository.findAll(pageable);

//		返回分頁結果的數據列表
		return page.getContent();
	}

//	這個還未確定
	public List<RestaurantBooking> find() {
		return restaurantBookingRepository.findAll();
	}

//	查詢總共有幾筆資料
	public Long count() {
		return restaurantBookingRepository.count();
	}

//	刪除一筆資料
	public Boolean delete(Integer id) {
		if (id != null) {
			Optional<RestaurantBooking> opt = restaurantBookingRepository.findById(id);
			if (opt.isPresent()) {
				restaurantBookingRepository.deleteById(id);
				return true;
			}
		}
		return false;
	}
//	確認資料是否存在(使用ID)
	public boolean existsById(Integer id) {
		if (id != null) {
			return restaurantBookingRepository.existsById(id);
		}
		return false;
	}
	
	public RestaurantBooking customerModify(RestaurantBooking bookingDetail, String json) {
		JSONObject obj = new JSONObject(json);
		if(bookingDetail != null) {
			
			String remark = obj.isNull("remark") ? "無" : obj.getString("remark");
			Integer totalAdult = obj.isNull("totalAdult") ? null : obj.getInt("totalAdult");
			Integer totalChildren = obj.isNull("totalChildren") ? null : obj.getInt("totalChildren");
			bookingDetail.setRemark(remark);
			bookingDetail.setTotalAdult(totalAdult);
			bookingDetail.setTotalChildren(totalChildren);
			
			return restaurantBookingRepository.save(bookingDetail);
		}
		return null;
	}

	// 修改資料
	public RestaurantBooking modify(String json) {
		JSONObject obj = new JSONObject(json);

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

		

		if (rebId != null ) {
			Optional<RestaurantBooking> opt = restaurantBookingRepository.findById(rebId);
			Restaurant restaurant = restaurantService.findById(1);
			if (opt.isPresent() && restaurant != null) {
				RestaurantBooking update = opt.get();
				update.setRestaurant(restaurant);
				update.setIsCustomer(isCustomer);
				update.setTotalAdult(totalAdult);
				update.setTotalChildren(totalChildren);
				update.setTotalTable(totalTable);
				update.setReserveTime(DatetimeConverter.parseLocalDate(reserveTime, "yyyy-MM-dd"));
				update.setMealTime(mealTime);
				update.setFirstName(firstName);
				update.setLastName(lastName);
				update.setCellphone(cellphone);
				update.setEmail(email);
				update.setRemark(remark);
				update.setLastModifiedEmp(lastModifiedEmp);
				update.setLastModifiedText(lastModifiedText);
				if (lastModifiedEmp != null) {
					update.setLastModifiedDate(LocalDateTime.now());
					update.setLastModifiedEmp(lastModifiedEmp);
					update.setLastModifiedText(lastModifiedText);
				}
				return restaurantBookingRepository.save(update);
			}
		}
		return null;

	}

	// 試著自己寫的計算訂位人數
	public long countRange(JSONObject obj, Integer reId) {
		String reserveTime = obj.isNull("reserveTime") ? null : obj.getString("reserveTime");
		String mealTime = obj.isNull("mealTime") ? null : obj.getString("mealTime");
		Integer totalAdult = obj.isNull("totalAdult") ? null : obj.getInt("totalAdult");
		Integer totalChildren = obj.isNull("totalChildren") ? null : obj.getInt("totalChildren");

		if (reserveTime != null && totalAdult != null) {
			CriteriaBuilder criterBuilder = this.session.getCriteriaBuilder();
			CriteriaQuery<Long> criteriaQuery = criterBuilder.createQuery(Long.class);
			Root<RestaurantBooking> table = criteriaQuery.from(RestaurantBooking.class);

			criteriaQuery.select(criterBuilder.count(table));
			List<Predicate> predicates = new ArrayList<>();
			if (reserveTime != null) {
				LocalDate reserve = DatetimeConverter.parseLocalDate(reserveTime, "yyyy-MM-dd");
				Predicate reservebetween = criterBuilder.equal(table.get(reserveTime), table);

				Predicate p = criterBuilder.or(reservebetween);
				predicates.add(p);
			}
			if (!predicates.isEmpty()) {
				criteriaQuery.where(predicates.toArray(new Predicate[0]));
			}
			TypedQuery<Long> typeQuery = this.getSession().createQuery(criteriaQuery);
			Long result = typeQuery.getSingleResult();

//			檢查結果是否為null
			return result != null ? result : 0;

		}
		return 0;
	}

	// chatgpt幫忙生成的計算人數(一)
	public boolean isBookingAllowed(JSONObject obj, Integer rbId) {
		String reserveTime = obj.isNull("reserveTime") ? null : obj.getString("reserveTime");
		String mealTime = obj.isNull("mealTime") ? null : obj.getString("mealTime");
		Integer totalAdult = obj.isNull("totalAdult") ? null : obj.getInt("totalAdult");
		Integer totalChildren = obj.isNull("totalChildren") ? null : obj.getInt("totalChildren");

		// Count total guests on the date
		long totalGuestsOnDate = restaurantBookingRepository.countByReserveTime(LocalDate.parse(reserveTime));

		// Check if total guests on the date exceed 200
		if (totalGuestsOnDate > 200) {
			return false;
		}

		// Count total guests for specific meal time
		long totalGuestsForMealTime = restaurantBookingRepository
				.countByReserveTimeAndMealTime(LocalDate.parse(reserveTime), mealTime);

		// Check if total guests for specific meal time exceed 30
		if (totalGuestsForMealTime > 30) {
			return false;
		}

		// Booking is allowed if both conditions are satisfied
		return true;
	}

	// chatgpt幫忙生成的計算人數(二)
	public int remainingBookingSlots(JSONObject obj, Integer rbId) {
		String reserveTime = obj.isNull("reserveTime") ? null : obj.getString("reserveTime");
		String mealTime = obj.isNull("mealTime") ? null : obj.getString("mealTime");

		// Count total guests on the date
		long totalGuestsOnDate = restaurantBookingRepository.countByReserveTime(LocalDate.parse(reserveTime));

		// Check if total guests on the date exceed 200
		if (totalGuestsOnDate >= 200) {
			return 0; // No remaining slots
		}

		// Count total guests for specific meal time
		long totalGuestsForMealTime = restaurantBookingRepository
				.countByReserveTimeAndMealTime(LocalDate.parse(reserveTime), mealTime);

		// Calculate remaining slots
		int remainingSlots = (int) Math.min(200 - totalGuestsOnDate, 30 - totalGuestsForMealTime);

		return remainingSlots;
	}

	public RestaurantBooking findByEmail(String email, LocalDate reserveTime) {
		return restaurantBookingRepository.findByEmail(email,reserveTime);
	}
	

	

	public RestaurantBooking findByReserveTime(String reserveTime) {
		return restaurantBookingRepository.findByReserveTime(reserveTime);
	}

//	public RestaurantBooking checkbooking() {
//		return restaurantBookingRepository.save(insert);
//	}

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
	
	public RestaurantBooking findByCellphone(String cellphone) {
		return restaurantBookingRepository.findByCellphone(cellphone);
	}
	public RestaurantBooking findByE(String email) {
		return restaurantBookingRepository.findByE(email);
	}

	@Transactional
	public String findBookingData(RestaurantBooking bookingDetail) {
		JSONObject responseJSON = new JSONObject();
		JSONObject bookingFindObj = new JSONObject();
		if(bookingDetail == null) {
			return createErrorResponse("查無訂單，請重新確認，謝謝");
		}else {
			createBookingDetailJSONObject(bookingDetail, bookingFindObj);
		}
		
		responseJSON.put("booking", bookingFindObj);
		responseJSON.put("message", "查詢成功");
		responseJSON.put("success", true);
		return responseJSON.toString();
	}

	private String createErrorResponse(String message) {
		JSONObject responseJSON = new JSONObject();
		responseJSON.put("message", message);
		responseJSON.put("success", false);
		return responseJSON.toString();
	}

	private JSONObject createBookingDetailJSONObject(RestaurantBooking bookingDetail, 
			JSONObject bookingFindObj) {
		bookingFindObj.put("restaurant", bookingDetail.getRestaurant())
		.put("isCustomer", bookingDetail.getIsCustomer())
				.put("totalAdult", bookingDetail.getTotalAdult())
				.put("totalChildren", bookingDetail.getTotalChildren())
				.put("setTotalTable", bookingDetail.getTotalTable())
				.put("reserveTime", bookingDetail.getReserveTime())
				.put("mealTime", bookingDetail.getMealTime())
				.put("firstName", bookingDetail.getFirstName())
				.put("lastName", bookingDetail.getLastName())
				.put("cellphone", bookingDetail.getCellphone())
				.put("email", bookingDetail.getEmail())
				.put("remark", bookingDetail.getRemark())
				.put("rebId", bookingDetail.getRebId());
		return bookingFindObj;

	}
	


}
