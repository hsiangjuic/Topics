package com.ispan.hotel.service.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.hotel.model.Booking;
import com.ispan.hotel.model.Room;
import com.ispan.hotel.model.RoomType;
import com.ispan.hotel.repository.booking.RoomRepositoryYTH;
import com.ispan.hotel.util.DatetimeConverter;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
@Transactional
public class RoomServiceYTH {
	
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}

	@Autowired
	private RoomRepositoryYTH roomRepository;
	
	@Autowired
	private RoomTypeServiceYTH roomTypeService;
	
	public Room create(String json) {
		
		try {
			JSONObject obj = new JSONObject(json);
			
			Integer rtId = obj.isNull("rtId") ? null : obj.getInt("rtId");
			String floor = obj.isNull("floor") ? null : obj.getString("floor");
			String roomStatus = obj.isNull("roomStatus") ? null : obj.getString("roomStatus");
			String roomNumber = obj.isNull("roomNumber") ? null : obj.getString("roomNumber");
			String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
			String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");

			if(rtId != null) {
				RoomType roomType = roomTypeService.findById(rtId);
				
				if(roomType != null) {
					Room insert = new Room();
					insert.setRoomType(roomType);
					insert.setFloor(floor);
					insert.setRoomStatus(roomStatus);
					if(Integer.parseInt(roomNumber) < 10) {
						roomNumber = floor + "0" + roomNumber;
					} else {
						roomNumber = floor + roomNumber;
					}
					insert.setRoomNumber(roomNumber);
					insert.setLastModifiedEmp(lastModifiedEmp);
					insert.setLastModifiedText(lastModifiedText);
					
					return roomRepository.save(insert);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Room findById(Integer id) {
		if(id != null) {
			Optional<Room> opt = roomRepository.findById(id);
			if(opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}
	
	public List<Room> find() {
		return roomRepository.findAll();
	}
	
	public Room findByRoomNumber(String roomNumber) {
		if(roomNumber != null && roomNumber.length()!= 0) {
			return roomRepository.findByRoomNumber(roomNumber);
		}
		return null;
	}
	
	public Room modify(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			
			Integer rId = obj.isNull("rId") ? null : obj.getInt("rId");
			Integer rtId = obj.isNull("rtId") ? null : obj.getInt("rtId");
			String floor = obj.isNull("floor") ? null : obj.getString("floor");
			String roomStatus = obj.isNull("roomStatus") ? null : obj.getString("roomStatus");
			String roomNumber = obj.isNull("roomNumber") ? null : obj.getString("roomNumber");
			String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
			String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");

			if(rtId != null) {
				RoomType roomType = roomTypeService.findById(rtId);
				Optional<Room> opt = roomRepository.findById(rId);
				if(opt.isPresent() && roomType != null) {
					Room update = opt.get();
					update.setRoomType(roomType);
					update.setFloor(floor);
					update.setRoomStatus(roomStatus);
					update.setRoomNumber(roomNumber);
					if(lastModifiedEmp != null) {
						update.setLastModifiedDate(LocalDateTime.now());
						update.setLastModifiedEmp(lastModifiedEmp);
						update.setLastModifiedText(lastModifiedText);
					}
					
					return roomRepository.save(update);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean delete(Integer id) {
		if(id != null) {
			Optional<Room> opt = roomRepository.findById(id);
			if(opt.isPresent()) {
				roomRepository.deleteById(id);
				return true;
			}
			
		}
		return false;
	}
	
	public Long countAvailibleRoom(RoomType roomType) {
		
		return roomRepository.countAvailibleRoom(roomType);
	}
	
	
	public List<Room> findEmptyRoom(JSONObject obj){
		
		
		Integer rtId = obj.isNull("rtId") ? null : obj.getInt("rtId");
		String beginDate = obj.isNull("beginDate") ? null : obj.getString("beginDate");
		String lastDate = obj.isNull("lastDate") ? null : obj.getString("lastDate");
		Integer bId = obj.isNull("bId") ? null : obj.getInt("bId");
		System.out.println("rtId = "+rtId);
	    if(beginDate != null && lastDate != null) {
			CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
			CriteriaQuery<Booking> criteriaQuery = criteriaBuilder.createQuery(Booking.class);

//			from Booking
			Root<Booking> table = criteriaQuery.from(Booking.class);
			
//			where start
			List<Predicate> predicates = new ArrayList<>();
			if (rtId != null) {
	    		predicates.add(criteriaBuilder.equal(table.get("room").get("roomType").get("rtId"), rtId));
	    	}
			
			// 如果room是起用狀態
			
			predicates.add(criteriaBuilder.equal(table.get("room").get("roomStatus"),"空閒中"));
			//尋找這段時間已有訂單的同房型的房間
	    	if (beginDate != null && lastDate != null) {
	    		LocalDate begin = DatetimeConverter.parseLocalDate(beginDate, "yyyy-MM-dd");
	    		LocalDate last = DatetimeConverter.parseLocalDate(lastDate, "yyyy-MM-dd");
	    		
	    		Predicate beginTimeBetween = criteriaBuilder.between(table.get("beginDate"), begin, last);
	    		Predicate lastTimeBetween = criteriaBuilder.between(table.get("lastDate"), begin, last);
	    		Predicate containTimeRange = criteriaBuilder.and(
	    				criteriaBuilder.lessThanOrEqualTo(table.get("beginDate"), begin),
	    				criteriaBuilder.greaterThanOrEqualTo(table.get("lastDate"), last)
	    				);
	    		//如果是在修改時使用這個方法，可以排除掉正在修改的訂單紀錄，以便修改訂購時間
	    		Predicate p = null;
	    		
	    		if(bId != null) {
	    			Predicate p1 = criteriaBuilder.or(beginTimeBetween, lastTimeBetween, containTimeRange);
	    			Predicate isNotModifiedBooking = criteriaBuilder.notEqual(table.get("bId"), bId);
	    			p = criteriaBuilder.and(isNotModifiedBooking, p1);
	    			
	    		} else {
	    			p = criteriaBuilder.or(beginTimeBetween, lastTimeBetween, containTimeRange);
	    		}
	    		predicates.add(p);
	    	}			
			if(predicates!=null && !predicates.isEmpty()) {
				Predicate[] array = predicates.toArray(new Predicate[0]);
				criteriaQuery = criteriaQuery.where(array);
			}
//			where end
			TypedQuery<Booking> typedQuery = this.getSession().createQuery(criteriaQuery);
			
			List<Booking> result = typedQuery.getResultList();
			RoomType roomType = roomTypeService.findById(rtId);
			List<Room> allRoom = roomRepository.findByRoomType(roomType);
			if(result!=null && !result.isEmpty()) {
				List<Room> occupiedRoom = new ArrayList<>();
				for(Booking book : result) {
					occupiedRoom.add(findById(book.getRoom().getrId()));
				}
				allRoom.removeAll(occupiedRoom);
			}
			if(allRoom.isEmpty()) {
				return null;
			} else {
				return allRoom;
			}
	    }
		return null;
	}

}
