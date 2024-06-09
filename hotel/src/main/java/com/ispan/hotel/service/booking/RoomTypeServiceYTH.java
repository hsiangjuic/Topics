package com.ispan.hotel.service.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Hotel;
import com.ispan.hotel.model.RoomType;
import com.ispan.hotel.repository.booking.RoomTypeRepositoryYTH;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class RoomTypeServiceYTH {
	
	@Autowired
	private RoomTypeRepositoryYTH roomTypeRepository;
	
	@Autowired
	private HotelServiceYTH hotelService;
	
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}
	
	public RoomType create(String json) {
		
		JSONObject obj = new JSONObject(json);
		
		Integer hId = obj.isNull("hId") ? null : obj.getInt("hId");
		String roomName = obj.isNull("roomName") ? null : obj.getString("roomName");
		Integer roomAmount = obj.isNull("roomAmount") ? null : obj.getInt("roomAmount");
		Integer bedNumber = obj.isNull("bedNumber") ? null : obj.getInt("bedNumber");
		String bedType = obj.isNull("bedType") ? null : obj.getString("bedType");
		Integer flexiblePrice = obj.isNull("flexiblePrice") ? null : obj.getInt("flexiblePrice");
		Integer memberPrice = obj.isNull("memberPrice") ? null : obj.getInt("memberPrice");
		String pet = obj.isNull("pet") ? null : obj.getString("pet");
		String allowAddBed = obj.isNull("allowAddBed") ? null : obj.getString("allowAddBed");
		String accessibleRoom = obj.isNull("accessibleRoom") ? null : obj.getString("accessibleRoom");
		Integer peopleMaxAmount = obj.isNull("peopleMaxAmount") ? null : obj.getInt("peopleMaxAmount");
		String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
		String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
		
		
		if(hId != null) {
			Hotel hotel = hotelService.findById(hId);
			if(hotel != null) {
				RoomType insert = new RoomType();
				insert.setHotel(hotel);
				insert.setRoomName(roomName);
				insert.setRoomAmount(roomAmount);
				insert.setBedNumber(bedNumber);
				insert.setBedType(bedType);
				insert.setFlexiblePrice(flexiblePrice);
				insert.setMemberPrice(memberPrice);
				insert.setPet(pet);
				insert.setAllowAddBed(allowAddBed);
				insert.setAccessibleRoom(accessibleRoom);
				insert.setPeopleMaxAmount(peopleMaxAmount);
				insert.setLastModifiedEmp(lastModifiedEmp);
				insert.setLastModifiedText(lastModifiedText);
				
				return roomTypeRepository.save(insert);
			}
		}

		return null;
	}
	
	
	public RoomType modify(String json) {
		
		JSONObject obj = new JSONObject(json);
		
		Integer rtId = obj.isNull("rtId") ? null : obj.getInt("rtId");
		Integer hId = obj.isNull("hId") ? null : obj.getInt("hId");
		String roomName = obj.isNull("roomName") ? null : obj.getString("roomName");
		Integer roomAmount = obj.isNull("roomAmount") ? null : obj.getInt("roomAmount");
		Integer bedNumber = obj.isNull("bedNumber") ? null : obj.getInt("bedNumber");
		String bedType = obj.isNull("bedType") ? null : obj.getString("bedType");
		Integer flexiblePrice = obj.isNull("flexiblePrice") ? null : obj.getInt("flexiblePrice");
		Integer memberPrice = obj.isNull("memberPrice") ? null : obj.getInt("memberPrice");
		String pet = obj.isNull("pet") ? null : obj.getString("pet");
		String allowAddBed = obj.isNull("allowAddBed") ? null : obj.getString("allowAddBed");
		String accessibleRoom = obj.isNull("accessibleRoom") ? null : obj.getString("accessibleRoom");
		Integer peopleMaxAmount = obj.isNull("peopleMaxAmount") ? null : obj.getInt("peopleMaxAmount");
		String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");
		String lastModifiedText = obj.isNull("lastModifiedText") ? null : obj.getString("lastModifiedText");
		
		if(rtId != null && hId != null) {
			Optional<RoomType> opt = roomTypeRepository.findById(rtId);
			Hotel hotel = hotelService.findById(hId);
			if(opt.isPresent() && hotel != null) {
				RoomType update = opt.get();
				update.setHotel(hotelService.findById(hId));
				update.setRoomName(roomName);
				update.setRoomAmount(roomAmount);
				update.setBedNumber(bedNumber);
				update.setBedType(bedType);
				update.setFlexiblePrice(flexiblePrice);
				update.setMemberPrice(memberPrice);
				update.setPet(pet);
				update.setAllowAddBed(allowAddBed);
				update.setAccessibleRoom(accessibleRoom);
				update.setPeopleMaxAmount(peopleMaxAmount);
				if(lastModifiedEmp != null) {
					update.setLastModifiedDate(LocalDateTime.now());
					update.setLastModifiedEmp(lastModifiedEmp);
					update.setLastModifiedText(lastModifiedText);
				}
				
				return roomTypeRepository.save(update);
			}
		}

		return null;
	}
	
	public RoomType findById(Integer id) {
		if(id != null) {
			Optional<RoomType> opt = roomTypeRepository.findById(id);
			if(opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}
	
	
	public Boolean delete(Integer id) {
		if(id != null) {
			Optional<RoomType> opt = roomTypeRepository.findById(id);
			if(opt.isPresent()) {
				roomTypeRepository.deleteById(id);
				return true;
			}
		}
		return false;
	}
	
	public List<RoomType> find(){
		return roomTypeRepository.findAll();
	}

	public List<RoomType> find(JSONObject obj) {
		Integer rtId = obj.isNull("rtId") ? null : obj.getInt("rtId");
		String name = obj.isNull("name") ? null : obj.getString("name");
		Integer startPrice = obj.isNull("startPrice") ? null : obj.getInt("startPrice") ;
		Integer endPrice = obj.isNull("endPrice") ? null : obj.getInt("endPrice") ;
		Integer peopleAmount = obj.isNull("peopleAmount") ? null : obj.getInt("peopleAmount");
		
		int start = obj.isNull("start") ? 0 : obj.getInt("start");
		int rows = obj.isNull("rows") ? 10 : obj.getInt("rows");
		String order = obj.isNull("order") ? "id" : obj.getString("order");
		boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");

		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<RoomType> criteriaQuery = criteriaBuilder.createQuery(RoomType.class);

//		from product
		Root<RoomType> table = criteriaQuery.from(RoomType.class);
		
//		where start
		List<Predicate> predicates = new ArrayList<>();
		if(rtId!=null) {
			Predicate p = criteriaBuilder.equal(table.get("rtId"), rtId);
			predicates.add(p);
		}
		if(name!=null && name.length()!=0) {
			predicates.add(criteriaBuilder.like(table.get("roomName"), "%"+name+"%"));
		}
		if(startPrice!=null) {
			predicates.add(criteriaBuilder.greaterThan(table.get("flexiblePrice"), startPrice));
		}
		if(endPrice!=null) {
			predicates.add(criteriaBuilder.lessThan(table.get("flexiblePrice"), endPrice));
		}
		if(peopleAmount!=null && peopleAmount > 0) {
			predicates.add(criteriaBuilder.greaterThanOrEqualTo(table.get("peopleMaxAmount"), peopleAmount));
		}
		
		if(predicates!=null && !predicates.isEmpty()) {
			Predicate[] array = predicates.toArray(new Predicate[0]);
			criteriaQuery = criteriaQuery.where(array);
		}
//		where end
		
//		Order By
		if(dir) {
			criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(table.get(order)));
		} else {
			criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.asc(table.get(order)));
		}

		TypedQuery<RoomType> typedQuery = this.getSession().createQuery(criteriaQuery)
				.setFirstResult(start)
				.setMaxResults(rows);
		
		List<RoomType> result = typedQuery.getResultList();
		if(result!=null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}
	}
	
	
}
