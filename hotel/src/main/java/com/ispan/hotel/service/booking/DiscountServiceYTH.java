package com.ispan.hotel.service.booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Discount;
import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.RoomType;
import com.ispan.hotel.repository.booking.DiscountRepositoryYTH;
import com.ispan.hotel.util.DatetimeConverter;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class DiscountServiceYTH {
	
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}
	
	@Autowired
	private DiscountRepositoryYTH discountRepository;
	
	public Discount findById(Integer id) {
		if(id != null) {
			Optional<Discount> opt = discountRepository.findById(id);
			if(opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}
	
	public List<Discount> find(){
		return discountRepository.findAll();
	}
	
	
	public List<Discount> findInUsedByRoomType(RoomType roomType, JSONObject obj){
		
		if(roomType != null) {
			String beginDate = obj.isNull("beginDate") ? null : obj.getString("beginDate");
			String lastDate = obj.isNull("lastDate") ? null : obj.getString("lastDate");
			System.out.println("beginDate = "+ beginDate);
			System.out.println("beginDate = "+ lastDate);
			
			CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
			CriteriaQuery<Discount> criteriaQuery = criteriaBuilder.createQuery(Discount.class);
			
//			from Discount
			Root<Discount> table = criteriaQuery.from(Discount.class);
			
//			where start
			List<Predicate> predicates = new ArrayList<>();
			
			
			Predicate p = criteriaBuilder.equal(table.get("roomType"), roomType);
			predicates.add(p);
			
			
			Predicate p1 = criteriaBuilder.equal(table.get("discountDetail").get("status"), "啟用中");
			
			if (beginDate != null && lastDate != null && !beginDate.isBlank() && !lastDate.isBlank()) {
				LocalDate begin = DatetimeConverter.parseLocalDate(beginDate, "yyyy-MM-dd");
				LocalDate last = DatetimeConverter.parseLocalDate(lastDate, "yyyy-MM-dd");
				
				Predicate p2 = criteriaBuilder.and(
						criteriaBuilder.lessThanOrEqualTo(table.get("discountDetail").get("beginDate"), begin),
						criteriaBuilder.greaterThanOrEqualTo(table.get("discountDetail").get("lastDate"), last)
						);
				predicates.add(criteriaBuilder.and(p1,p2));
			}
			if(predicates!=null && !predicates.isEmpty()) {
				Predicate[] array = predicates.toArray(new Predicate[0]);
				criteriaQuery = criteriaQuery.where(array);
			}
//			where end
			
			criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(table.get("discountDetail").get("name")));
			
			TypedQuery<Discount> typedQuery = this.getSession().createQuery(criteriaQuery);
			
			List<Discount> result = typedQuery.getResultList();
			if(result!=null && !result.isEmpty()) {
				return result;
			} else {
				return null;
			}
			
		}
	
		return null;
	}
}
