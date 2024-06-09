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

import com.ispan.hotel.model.Booking;
import com.ispan.hotel.model.BookingDetail;
import com.ispan.hotel.model.Room;
import com.ispan.hotel.repository.booking.BookingRepositoryYTH;
import com.ispan.hotel.util.DatetimeConverter;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class BookingServiceYTH {
	
	@Autowired
	private BookingRepositoryYTH bookingRepository;
	
	@Autowired
	private BookingDetailServiceYTH bookingDetailService;
	
	@Autowired
	private RoomServiceYTH roomService;
	
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}
	
	public Booking create(JSONObject obj, BookingDetail bookingDetail, Room room) {
		
			String beginDate = obj.isNull("beginDate") ? null : obj.getString("beginDate");
			String lastDate = obj.isNull("lastDate") ? null : obj.getString("lastDate");
			String bookingStatus = obj.isNull("bookingStatus") ? null : obj.getString("bookingStatus");
			Integer additionalBed = obj.isNull("additionalBed") ? null : obj.getInt("additionalBed");
			Boolean breakfast = obj.isNull("breakfast") ? null : obj.getBoolean("breakfast");
			
			if(bookingDetail != null && room != null) {
				Booking insert = new Booking();
				insert.setBookingDetail(bookingDetail);
				insert.setRoom(room);
				
				if(beginDate != null && beginDate.length() != 0) {
					LocalDate temp = DatetimeConverter.parseLocalDate(beginDate, "yyyy-MM-dd");
					insert.setBeginDate(temp);
				}
				if(lastDate != null && lastDate.length() != 0) {
					LocalDate temp = DatetimeConverter.parseLocalDate(lastDate, "yyyy-MM-dd");
					insert.setLastDate(temp);
				}
				if(bookingStatus != null && bookingStatus.length() != 0) {
					insert.setBookingStatus(bookingStatus);
				} else {
					insert.setBookingStatus("待確認");
				}
				
				if(additionalBed != null) {
					insert.setAdditionalBed(additionalBed);
				} else {
					insert.setAdditionalBed(0);
				}
				
				if(breakfast != null) {
					insert.setBreakfast(breakfast);
				} else {
					insert.setBreakfast(false);
				}
				
				return bookingRepository.save(insert);
			}
		
		return null;
	}
	
	public Booking customerModify(JSONObject obj) {
		Integer bId = obj.isNull("bId") ? null : obj.getInt("bId");
		Boolean breakfast = obj.isNull("breakfast") ? null : obj.getBoolean("breakfast");
		
		Optional<Booking> opt = bookingRepository.findById(bId);
		if (opt.isPresent()) {
			Booking booking = opt.get();
			booking.setBreakfast(breakfast);
			return bookingRepository.save(booking);
		}
		return null;
	}
public Booking modify(String json) {
		
		try {
			JSONObject obj = new JSONObject(json);
			
			Integer bId = obj.isNull("bId") ? null : obj.getInt("bId");
			Integer bdId = obj.isNull("bdId") ? null : obj.getInt("bdId");
			Integer rId = obj.isNull("rId") ? null : obj.getInt("rId");
			String beginDate = obj.isNull("beginDate") ? null : obj.getString("beginDate");
			String lastDate = obj.isNull("lastDate") ? null : obj.getString("lastDate");
			String bookingStatus = obj.isNull("bookingStatus") ? null : obj.getString("bookingStatus");
			Integer additionalBed = obj.isNull("additionalBed") ? null : obj.getInt("additionalBed");
			Boolean breakfast = obj.isNull("breakfast") ? null : obj.getBoolean("breakfast");
			String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
			String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");

			
			if(bId != null) {
				BookingDetail bookingDetail = bookingDetailService.findById(bdId);
				Room room = roomService.findById(rId);
				Optional<Booking> opt = bookingRepository.findById(bId);
				if(opt.isPresent() && bookingDetail != null && room != null) {
					Booking update = opt.get();
					update.setBookingDetail(bookingDetail);
					update.setRoom(room);
					
					if(beginDate != null && beginDate.length() != 0) {
						LocalDate temp = DatetimeConverter.parseLocalDate(beginDate, "yyyy-MM-dd");
						update.setBeginDate(temp);
					}
					if(lastDate != null && lastDate.length() != 0) {
						LocalDate temp = DatetimeConverter.parseLocalDate(lastDate, "yyyy-MM-dd");
						update.setLastDate(temp);
					}
					if(bookingStatus != null && bookingStatus.length() != 0) {
						update.setBookingStatus(bookingStatus);
					}
					
					if(additionalBed != null) {
						update.setAdditionalBed(additionalBed);
					}
					
					if(breakfast != null) {
						update.setBreakfast(breakfast);
					}
					if(lastModifiedEmp != null) {
						update.setLastModifiedDate(LocalDateTime.now());
						update.setLastModifiedEmp(lastModifiedEmp);
						update.setLastModifiedText(lastModifiedText);
					}
					
					return bookingRepository.save(update);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Booking hotelModify(String json) {
		
		try {
			JSONObject obj = new JSONObject(json);
			
			Integer bId = obj.isNull("bId") ? null : obj.getInt("bId");
			String roomNumber = obj.isNull("roomNumber") ? null : obj.getString("roomNumber");
			String bookingStatus = obj.isNull("bookingStatus") ? null : obj.getString("bookingStatus");
			String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
			String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
			
			if(bId != null) {
				Optional<Booking> opt = bookingRepository.findById(bId);
				Room room = roomService.findByRoomNumber(roomNumber);
				if(opt.isPresent() && room != null) {
					Booking update = opt.get();
					update.setRoom(room);
					if(bookingStatus != null && bookingStatus.length() != 0) {
						update.setBookingStatus(bookingStatus);
					}
					
					if(lastModifiedEmp != null) {
						update.setLastModifiedDate(LocalDateTime.now());
						update.setLastModifiedEmp(lastModifiedEmp);
						update.setLastModifiedText(lastModifiedText);
					}
					
					return bookingRepository.save(update);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Booking findById(Integer id) {
		if(id != null) {
			Optional<Booking> opt = bookingRepository.findById(id);
			if(opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}
	
	public List<Booking> findByBookingDetail(BookingDetail bookingDetail) {
		if(bookingDetail != null) {
			return bookingRepository.findByBookingDetail(bookingDetail);
		}
		return null;
	}
	
	public List<Booking> find(){
		return bookingRepository.findAll();
	}
	
	public long count(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			return bookingRepository.count(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<Booking> find(JSONObject obj){
		return bookingRepository.find(obj);
	}
	public long countDay(Integer rtId) {
		return 0;
	}
	public long countRange(JSONObject obj, Integer rtId) {
	    String name = obj.isNull("name") ? null : obj.getString("name");
	    String beginDate = obj.isNull("beginDate") ? null : obj.getString("beginDate");
	    String lastDate = obj.isNull("lastDate") ? null : obj.getString("lastDate");
	    Integer bId = obj.isNull("bId") ? null : obj.getInt("bId");
	    
	    if(beginDate != null && lastDate != null) {
	    	CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
	    	CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
	    	Root<Booking> table = criteriaQuery.from(Booking.class);
	    	
	    	criteriaQuery.select(criteriaBuilder.count(table));
	    	
	    	List<Predicate> predicates = new ArrayList<>();
	    	
	    	// 添加房型ID的條件
	    	if (rtId != null) {
	    		predicates.add(criteriaBuilder.equal(table.get("room").get("roomType").get("rtId"), rtId));
	    	}
	    	
			// 如果room是啟用狀態
			
			predicates.add(criteriaBuilder.equal(table.get("room").get("roomStatus"),"空閒中"));
	    	
	    	// 添加房間名稱的模糊查詢條件
	    	if (name != null && !name.isEmpty()) {
	    		predicates.add(criteriaBuilder.like(table.get("room").get("roomName"), "%" + name + "%"));
	    	}
	    	// 添加時間區間的查詢條件
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
	    	
	    	
	    	
	    	// 將所有條件組合成一個整體的查詢條件
	    	if (!predicates.isEmpty()) {
	    		criteriaQuery.where(predicates.toArray(new Predicate[0]));
	    	}
	    	
	    	TypedQuery<Long> typedQuery = this.getSession().createQuery(criteriaQuery);
	    	Long result = typedQuery.getSingleResult();
	    	
	    	// 檢查結果是否為 null
	    	return result != null ? result : 0;
	    }
	    return 0;
	}
	

	public Boolean delete(Integer id) {
		if(id != null) {
			Optional<Booking> opt = bookingRepository.findById(id);
			if(opt.isPresent()) {
				bookingRepository.deleteById(id);
				return true;
			}
		}
		return false;
	}
	
}
