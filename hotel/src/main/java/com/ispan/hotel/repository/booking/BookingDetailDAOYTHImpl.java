package com.ispan.hotel.repository.booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Booking;
import com.ispan.hotel.model.BookingDetail;
import com.ispan.hotel.model.BookingPayment;
import com.ispan.hotel.model.Customer;
import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.Hotel;
import com.ispan.hotel.model.UseDiscount;
import com.ispan.hotel.util.DatetimeConverter;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class BookingDetailDAOYTHImpl implements BookingDetailDAOYTH {
	
	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}
	
	@Override
	public List<BookingDetail> find(JSONObject obj){

//	查詢條件:
//	Customer firstName,lastName from BookingDetail.getCustomer.getxxx
//	Hotel h_id BookingDetail.getHotel.gethId
//	特定日期有哪些訂單 Booking date
//	特定日期入住訂單 Booking beginDate
//	特定日期退房訂單 Booking lastDate
//	房型 RoomType roomName
//	付款狀態 BookingDetail paymentStatus
//	訂單狀態 Booking  bookingStatus
//	使用特定優惠 DiscountDetail name
//	使用特定類型優惠 DiscountDetail discountType
		String firstName = obj.isNull("firstName") ? null : obj.getString("firstName");
		String lastName = obj.isNull("lastName") ? null : obj.getString("lastName");
		Integer hId = obj.isNull("hId") ? null : obj.getInt("hId");
		String date = obj.isNull("date") ? null : obj.getString("date");
		String lastDate = obj.isNull("lastDate") ? null : obj.getString("lastDate");
		String beginDate = obj.isNull("beginDate") ? null : obj.getString("beginDate");
		String roomName = obj.isNull("roomName") ? null : obj.getString("roomName");
		String paymentStatus = obj.isNull("paymentStatus") ? null : obj.getString("paymentStatus");
		String bookingStatus = obj.isNull("bookingStatus") ? null : obj.getString("bookingStatus");
		String dDName = obj.isNull("dDName") ? null : obj.getString("dDName");
		String discountType = obj.isNull("discountType") ? null : obj.getString("discountType");
		
		int start = obj.isNull("start") ? 0 : obj.getInt("start");
		int rows = obj.isNull("rows") ? 10 : obj.getInt("rows");
		String order = obj.isNull("order") ? "id" : obj.getString("order");
		boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");
		
		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<BookingDetail> criteriaQuery = criteriaBuilder.createQuery(BookingDetail.class);
		
//		from BookingDetail
		Root<BookingDetail> table = criteriaQuery.from(BookingDetail.class);
//		反查詢用的子查詢
		
		// 連接到 Hotel
		Join<BookingDetail, Hotel> hotelJoin = table.join("hotel", JoinType.INNER);
		// 連接到 Customer
		Join<BookingDetail, Customer> customerJoin = table.join("customer", JoinType.INNER);
		// 連接到 Booking
		Join<BookingDetail, Booking> bookingJoin = table.joinSet("bookings", JoinType.INNER);
		// 連接到 BookingPayment
		Join<Booking, BookingPayment> bookingPaymentJoin = bookingJoin.joinSet("bookingPayments", JoinType.INNER);
		// 連接到 UseDiscount
        Join<BookingPayment, UseDiscount> useDiscountJoin = bookingPaymentJoin.joinSet("useDiscounts", JoinType.INNER);
		// 連接到 DiscountDetail
		Join<UseDiscount, DiscountDetail> discountDetailJoin = useDiscountJoin.join("discountDetail", JoinType.INNER);
		
		
//		where start
		List<Predicate> predicates = new ArrayList<>();
		if(hId != null) {
			Predicate p = criteriaBuilder.equal(hotelJoin.get("hId"), hId);
			predicates.add(p);
		}
		if(firstName != null && firstName.length() != 0 && lastName != null && lastName.length() != 0) {
			Predicate p = criteriaBuilder.and(criteriaBuilder.equal(customerJoin.get("firstName"), firstName),
											  criteriaBuilder.equal(customerJoin.get("lastName"), lastName));
			predicates.add(p);
		}
		
//		日期相關條件查詢
		//	特定日期住房的訂單
		if(date != null && date.length() != 0) {
			
			Expression<LocalDate> temp = criteriaBuilder.literal(DatetimeConverter.parseLocalDate(date, "yyyy-MM-dd"));
			
		    Predicate p = criteriaBuilder.between(temp, bookingJoin.get("beginDate"), bookingJoin.get("lastDate"));
		    predicates.add(p);
		}
		
		//	特定日期入住的訂單
		if(beginDate != null && beginDate.length() != 0) {
			Expression<LocalDate> temp = criteriaBuilder.literal(DatetimeConverter.parseLocalDate(beginDate, "yyyy-MM-dd"));
			Predicate p = criteriaBuilder.equal(bookingJoin.get("beginDate"), temp);
			predicates.add(p);
		}
		
		//	特定日期退房的訂單
		if(lastDate != null && lastDate.length() != 0) {
			Expression<LocalDate> temp = criteriaBuilder.literal(DatetimeConverter.parseLocalDate(lastDate, "yyyy-MM-dd"));
			Predicate p = criteriaBuilder.equal(bookingJoin.get("lastDate"), temp);
			predicates.add(p);
		}
		
		if(roomName != null && roomName.length() != 0) {
			Predicate p = criteriaBuilder.equal(bookingJoin.get("room").get("roomType").get("roomName"), roomName);
			predicates.add(p);
		}
		
		if(paymentStatus != null && paymentStatus.length() != 0) {
			Predicate p = criteriaBuilder.equal(table.get("paymentStatus"), paymentStatus);
			predicates.add(p);
		}
		
		if(bookingStatus != null && bookingStatus.length() != 0) {
			Predicate p = criteriaBuilder.equal(bookingJoin.get("bookingStatus"), bookingStatus);
			predicates.add(p);
		}
		
		if(dDName != null && dDName.length() != 0) {
			Predicate p = criteriaBuilder.equal(discountDetailJoin.get("name"), dDName);
			predicates.add(p);
		}
		if(discountType != null && discountType.length() != 0) {
			Predicate p = criteriaBuilder.equal(discountDetailJoin.get("discountType"), discountType);
			predicates.add(p);
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
		
		TypedQuery<BookingDetail> typedQuery = this.getSession().createQuery(criteriaQuery)
				.setFirstResult(start)
				.setMaxResults(rows);
		
		List<BookingDetail> result = typedQuery.getResultList();
		if(result!=null && !result.isEmpty()) {
			return result;
		} else {
			return null;
		}
		
	}
	
	

}
