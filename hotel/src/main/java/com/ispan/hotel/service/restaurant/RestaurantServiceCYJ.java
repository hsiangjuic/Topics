package com.ispan.hotel.service.restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Hotel;
import com.ispan.hotel.model.Restaurant;
import com.ispan.hotel.repository.restaurant.RestaurantRepositoryCYJ;
import com.ispan.hotel.service.booking.HotelServiceYTH;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class RestaurantServiceCYJ {
	
	@Autowired
	private RestaurantRepositoryCYJ restaurantRepository;
	
	@Autowired
	private HotelServiceYTH hotelService;
	
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}
	
	public Restaurant create(String json) {
		JSONObject obj = new JSONObject(json);
		
		
//		re_id
		Integer reId = obj.isNull("reId") ? null : obj.getInt("reId");
//		h_id
		Integer hId = obj.isNull("hId") ? null : obj.getInt("hId");
//		name
		String name = obj.isNull("name") ? null : obj.getString("name");
//		total seat
		Integer totalSeat = obj.isNull("totalSeat") ? null : obj.getInt("totalSeat");
//		open time1
		String openTime1 = obj.isNull("openTime1") ? null : obj.getString("openTime1");
//		close time1
		String closeTime1 = obj.isNull("closeTime1") ? null : obj.getString("closeTime1");
//		open time2
		String openTime2 = obj.isNull("openTime2") ? null : obj.getString("openTime2");
//		close time2
		String closeTime2 = obj.isNull("closeTime2") ? null : obj.getString("closeTime2");
//		open time3
		String openTime3 = obj.isNull("openTime3") ? null : obj.getString("openTime3");
//		close time3
		String closeTime3 = obj.isNull("closeTime3") ? null : obj.getString("closeTime3");
//		tel
		String tel = obj.isNull("tel") ? null : obj.getString("tel");
//		address
		String address = obj.isNull("address") ? null : obj.getString("address");
//		number_of_ guests
		Integer numberOfGuests = obj.isNull("numberOfGuests") ? null : obj.getInt("numberOfGuests");
//		最後修改人員
		String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
//		最後修改紀錄
		String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
		
		if(hId != null) {
			Hotel hotel = hotelService.findById(hId);
			if(hotel !=null) {
				Restaurant insert = new Restaurant();
				insert.setHotel(hotel);
				insert.setName(name);
				insert.setTotalSeat(totalSeat);
				insert.setOpenTime1(openTime1);
				insert.setCloseTime1(closeTime1);
				insert.setOpenTime2(openTime2);
				insert.setCloseTime2(closeTime2);
				insert.setOpenTime3(openTime3);
				insert.setCloseTime3(closeTime3);
				insert.setTel(tel);
				insert.setAddress(address);
				insert.setLastModifiedEmp(lastModifiedEmp);
				
				return restaurantRepository.save(insert);
				
			}
		}
		return null;
	}
	
	public Restaurant findById(Integer id) {
		if(id != null) {
			Optional<Restaurant> opt = restaurantRepository.findById(id);
			
			if(opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}
	public List<Restaurant> find(){
		return restaurantRepository.findAll();
	}
	
	public Long countAvailibleSeat(Integer totalSeat) {
		return restaurantRepository.countAvailibleSeat(totalSeat);
	}
	
	
//	public List<Restaurant> find(JSONObject obj){
////		re_id
//		Integer reId = obj.isNull("reId") ? null : obj.getInt("reId");
////		h_id
//		Integer hId = obj.isNull("hId") ? null : obj.getInt("hId");
////		name
//		String name = obj.isNull("name") ? null : obj.getString("name");
////		total seat
//		Integer totalSeat = obj.isNull("totalSeat") ? null : obj.getInt("totalSeat");
////		open time1
//		String openTime1 = obj.isNull("openTime1") ? null : obj.getString("openTime1");
////		close time1
//		String closeTime1 = obj.isNull("closeTime1") ? null : obj.getString("closeTime1");
////		open time2
//		String openTime2 = obj.isNull("openTime2") ? null : obj.getString("openTime2");
////		close time2
//		String closeTime2 = obj.isNull("closeTime2") ? null : obj.getString("closeTime2");
////		open time3
//		String openTime3 = obj.isNull("openTime3") ? null : obj.getString("openTime3");
////		close time3
//		String closeTime3 = obj.isNull("closeTime3") ? null : obj.getString("closeTime3");
////		tel
//		String tel = obj.isNull("tel") ? null : obj.getString("tel");
////		address
//		String address = obj.isNull("address") ? null : obj.getString("address");
////		number_of_ guests
//		Integer numberOfGuests = obj.isNull("numberOfGuests") ? null : obj.getInt("numberOfGuests");
//		
//		Integer peopleAmount = obj.isNull("peopleAmount") ? null : obj.getInt("peopleAmount");
//		
//		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
//		CriteriaQuery<Restaurant> criteriaQuery = criteriaBuilder.createQuery(Restaurant.class);
//		
////		from product
//		Root<Restaurant> table = criteriaQuery.from(Restaurant.class);
//		
////		where start
//		List<Predicate> predicates = new ArrayList<>();
//		if(reId != null) {
//			Predicate p =criteriaBuilder.equal(table.get("reId"), reId);
//		}
//		if(peopleAmount != null && peopleAmount>0) {
//			predicates.add(criteriaBuilder.greaterThanOrEqualTo(table.get("numberOfGuests"), peopleAmount));
//		}
//		
//	if(predicates!= null && !predicates.isEmpty()) {
//		Predicate[] array = predicates.toArray(new Predicate[0]);
//		criteriaQuery = criteriaQuery.where(array);
//	}
////	where end
//	return null;
//	
//	
//		
//	}

}
