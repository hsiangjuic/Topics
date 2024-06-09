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
import com.ispan.hotel.model.Room;
import com.ispan.hotel.model.RoomType;
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
import jakarta.persistence.criteria.Subquery;

@Repository
public class BookingDAOYTHImpl implements BookingDAOYTH{

	@PersistenceContext
	private Session session;
	public Session getSession() {
		return this.session;
	}
	
	@Override
	public long count(JSONObject obj) {
		String firstName = obj.isNull("firstName") ? null : obj.getString("firstName");
		String lastName = obj.isNull("lastName") ? null : obj.getString("lastName");
		Integer hId = obj.isNull("hId") ? null : obj.getInt("hId");
		String date = obj.isNull("date") ? null : obj.getString("date");
		String lastDate = obj.isNull("lastDate") ? null : obj.getString("lastDate");
		String beginDate = obj.isNull("beginDate") ? null : obj.getString("beginDate");
		String roomName = obj.isNull("roomName") ? null : obj.getString("roomName");
		String paymentStatus = obj.isNull("paymentStatus") ? null : obj.getString("paymentStatus");
		String bookingStatus = obj.isNull("bookingStatus") ? null : obj.getString("bookingStatus");
		String promo = obj.isNull("promo") ? null : obj.getString("promo");
		String pack = obj.isNull("pack") ? null : obj.getString("pack");
		String orderNumber = obj.isNull("orderNumber") ? null : obj.getString("orderNumber");
		
		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
//		from BookingDetail
		Root<Booking> table = criteriaQuery.from(Booking.class);
//		反查詢用的子查詢
		
		// 連接到 BookingDetail
		Join<Booking, BookingDetail> bookingDetailJoin = table.join("bookingDetail", JoinType.INNER);
		// 連接到 Hotel
		Join<BookingDetail, Hotel> hotelJoin = bookingDetailJoin.join("hotel", JoinType.INNER);
		// 連接到 Customer
		Join<BookingDetail, Customer> customerJoin = bookingDetailJoin.join("customer", JoinType.INNER);
		// 連接到 BookingPayment
		Join<Booking, BookingPayment> bookingPaymentJoin = table.joinSet("bookingPayments", JoinType.LEFT);
		// 連接到 UseDiscount
        Join<BookingPayment, UseDiscount> useDiscountJoin = bookingPaymentJoin.joinSet("useDiscounts", JoinType.LEFT);
		// 連接到 DiscountDetail
		Join<UseDiscount, DiscountDetail> discountDetailJoin = useDiscountJoin.join("discountDetail", JoinType.LEFT);
		
//		select count(*)
		criteriaQuery = criteriaQuery.distinct(true).select(criteriaBuilder.countDistinct(table));		
		
		// where 條件
				List<Predicate> predicates = new ArrayList<>();
				if (hId != null) {
				    Predicate p = criteriaBuilder.equal(hotelJoin.get("hId"), hId);
				    predicates.add(p);
				}
				if (firstName != null && firstName.length() != 0 && lastName != null && lastName.length() != 0) {
				    Predicate p = criteriaBuilder.and(criteriaBuilder.equal(customerJoin.get("firstName"), firstName),
				                                      criteriaBuilder.equal(customerJoin.get("lastName"), lastName));
				    predicates.add(p);
				}

				// 日期相關條件查詢
				if (date != null && date.length() != 0) {
				    Expression<LocalDate> temp = criteriaBuilder.literal(DatetimeConverter.parseLocalDate(date, "yyyy-MM-dd"));
				    Predicate p = criteriaBuilder.between(temp, table.get("beginDate"), table.get("lastDate"));
				    predicates.add(p);
				}

				if (beginDate != null && beginDate.length() != 0) {
				    Expression<LocalDate> temp = criteriaBuilder.literal(DatetimeConverter.parseLocalDate(beginDate, "yyyy-MM-dd"));
				    Predicate p = criteriaBuilder.equal(table.get("beginDate"), temp);
				    predicates.add(p);
				}

				if (lastDate != null && lastDate.length() != 0) {
				    Expression<LocalDate> temp = criteriaBuilder.literal(DatetimeConverter.parseLocalDate(lastDate, "yyyy-MM-dd"));
				    Predicate p = criteriaBuilder.equal(table.get("lastDate"), temp);
				    predicates.add(p);
				}

				if (roomName != null && roomName.length() != 0) {
				    Predicate p = criteriaBuilder.equal(table.get("room").get("roomType").get("roomName"), roomName);
				    predicates.add(p);
				}

				if (paymentStatus != null && paymentStatus.length() != 0) {
				    Predicate p = criteriaBuilder.equal(bookingDetailJoin.get("paymentStatus"), paymentStatus);
				    predicates.add(p);
				}

				if (bookingStatus != null && bookingStatus.length() != 0) {
				    Predicate p = criteriaBuilder.equal(table.get("bookingStatus"), bookingStatus);
				    predicates.add(p);
				}
				
				
//				if (promo != null && promo.length() != 0 && pack == null) {
//				    Predicate p = criteriaBuilder.equal(discountDetailJoin.get("name"), promo);
//				    predicates.add(p);
//				}
//
//				if (pack != null && pack.length() != 0 && promo == null) {
//				    Predicate p = criteriaBuilder.equal(discountDetailJoin.get("name"), pack);
//				    predicates.add(p);
//				}
				
				if (promo != null && promo.length() != 0) {
			        Subquery<Long> promoSubquery = criteriaQuery.subquery(Long.class);
			        Root<UseDiscount> promoRoot = promoSubquery.from(UseDiscount.class);
			        Join<UseDiscount, DiscountDetail> promoDiscountDetailJoin = promoRoot.join("discountDetail", JoinType.INNER);
			        promoSubquery.where(
			                         criteriaBuilder.equal(promoRoot.get("bookingPayment").get("booking"), table),
			                         criteriaBuilder.equal(promoDiscountDetailJoin.get("discountType"), "折扣碼"),
			                         criteriaBuilder.equal(promoDiscountDetailJoin.get("name"), promo)
			                     );
			        Predicate promoPredicate = criteriaBuilder.exists(promoSubquery);
			        predicates.add(promoPredicate);
			    }
			    
			    if (pack != null && pack.length() != 0) {
			        Subquery<Long> packSubquery = criteriaQuery.subquery(Long.class);
			        Root<UseDiscount> packRoot = packSubquery.from(UseDiscount.class);
			        Join<UseDiscount, DiscountDetail> packDiscountDetailJoin = packRoot.join("discountDetail", JoinType.INNER);
			        packSubquery.where(
			                        criteriaBuilder.equal(packRoot.get("bookingPayment").get("booking"), table),
			                        criteriaBuilder.equal(packDiscountDetailJoin.get("discountType"), "專案"),
			                        criteriaBuilder.equal(packDiscountDetailJoin.get("name"), pack)
			                    );
			        Predicate packPredicate = criteriaBuilder.exists(packSubquery);
			        predicates.add(packPredicate);
			    }
			    
			    
				
				
				
				if(orderNumber != null && orderNumber.length() != 0) {
					Predicate p = criteriaBuilder.equal(bookingDetailJoin.get("orderNumber"), orderNumber);
				    predicates.add(p);
				}

				if (predicates != null && !predicates.isEmpty()) {
				    Predicate[] array = predicates.toArray(new Predicate[0]);
				    criteriaQuery = criteriaQuery.where(array);
				}

		
//		where end		
		
		TypedQuery<Long> typedQuery = this.getSession().createQuery(criteriaQuery);
		
		Long result = typedQuery.getSingleResult();
		if(result!=null) {
			return result;
		} else {
			return 0;
		}
	}
	
	
	@Override
	public List<Booking> find(JSONObject obj){

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
		String promo = obj.isNull("promo") ? null : obj.getString("promo");
		String pack = obj.isNull("pack") ? null : obj.getString("pack");
		String orderNumber = obj.isNull("orderNumber") ? null : obj.getString("orderNumber");
		
		int start = obj.isNull("start") ? 0 : obj.getInt("start");
		int rows = obj.isNull("rows") ? 10 : obj.getInt("rows");
		String order = obj.isNull("order") ? "bId" : obj.getString("order");
		boolean dir = obj.isNull("dir") ? false : obj.getBoolean("dir");
		CriteriaBuilder criteriaBuilder = this.getSession().getCriteriaBuilder();
		CriteriaQuery<Booking> criteriaQuery = criteriaBuilder.createQuery(Booking.class);
		
//		from BookingDetail
		Root<Booking> table = criteriaQuery.from(Booking.class);
//		反查詢用的子查詢
		
		// 連接到 BookingDetail
		Join<Booking, BookingDetail> bookingDetailJoin = table.join("bookingDetail", JoinType.INNER);
		// 連接到 Hotel
		Join<BookingDetail, Hotel> hotelJoin = bookingDetailJoin.join("hotel", JoinType.INNER);
		// 連接到 Customer
		Join<BookingDetail, Customer> customerJoin = bookingDetailJoin.join("customer", JoinType.INNER);
		// 連接到 BookingPayment
		Join<Booking, BookingPayment> bookingPaymentJoin = table.joinSet("bookingPayments", JoinType.LEFT);
		// 連接到 UseDiscount
        Join<BookingPayment, UseDiscount> useDiscountJoin = bookingPaymentJoin.joinSet("useDiscounts", JoinType.LEFT);
		// 連接到 DiscountDetail
		Join<UseDiscount, DiscountDetail> discountDetailJoin = useDiscountJoin.join("discountDetail", JoinType.LEFT);
		Join<Booking, Room> roomJoin = table.join("room", JoinType.LEFT);
		Join<Booking, RoomType> roomTypeJoin = roomJoin.join("roomType", JoinType.LEFT);
	
		
		
		// 設置 distinct
		criteriaQuery.distinct(true);

		// where 條件
		List<Predicate> predicates = new ArrayList<>();
		if (hId != null) {
		    Predicate p = criteriaBuilder.equal(hotelJoin.get("hId"), hId);
		    predicates.add(p);
		}
		if (firstName != null && firstName.length() != 0 && lastName != null && lastName.length() != 0) {
		    Predicate p = criteriaBuilder.and(criteriaBuilder.equal(customerJoin.get("firstName"), firstName),
		                                      criteriaBuilder.equal(customerJoin.get("lastName"), lastName));
		    predicates.add(p);
		}

		// 日期相關條件查詢
		if (date != null && date.length() != 0) {
		    Expression<LocalDate> temp = criteriaBuilder.literal(DatetimeConverter.parseLocalDate(date, "yyyy-MM-dd"));
		    Predicate p = criteriaBuilder.between(temp, table.get("beginDate"), table.get("lastDate"));
		    predicates.add(p);
		}

		if (beginDate != null && beginDate.length() != 0) {
		    Expression<LocalDate> temp = criteriaBuilder.literal(DatetimeConverter.parseLocalDate(beginDate, "yyyy-MM-dd"));
		    Predicate p = criteriaBuilder.equal(table.get("beginDate"), temp);
		    predicates.add(p);
		}

		if (lastDate != null && lastDate.length() != 0) {
		    Expression<LocalDate> temp = criteriaBuilder.literal(DatetimeConverter.parseLocalDate(lastDate, "yyyy-MM-dd"));
		    Predicate p = criteriaBuilder.equal(table.get("lastDate"), temp);
		    predicates.add(p);
		}

		if (roomName != null && roomName.length() != 0) {
		    Predicate p = criteriaBuilder.equal(roomTypeJoin.get("roomName"), roomName);
		    predicates.add(p);
		}

		if (paymentStatus != null && paymentStatus.length() != 0) {
			System.out.println("paymentStatus find!");
		    Predicate p = criteriaBuilder.equal(bookingDetailJoin.get("paymentStatus"), paymentStatus);
		    predicates.add(p);
		}

		if (bookingStatus != null && bookingStatus.length() != 0) {
		    Predicate p = criteriaBuilder.equal(table.get("bookingStatus"), bookingStatus);
		    predicates.add(p);
		}

//		if (promo != null && promo.length() != 0 && pack == null) {
//		    Predicate p = criteriaBuilder.equal(discountDetailJoin.get("name"), promo);
//		    predicates.add(p);
//		}
//
//		if (pack != null && pack.length() != 0 && promo == null) {
//		    Predicate p = criteriaBuilder.equal(discountDetailJoin.get("name"), pack);
//		    predicates.add(p);
//		}
//		
//		if(promo != null && promo.length() != 0 && pack != null && pack.length() != 0) {
//			
//		}
		
		if (promo != null && promo.length() != 0) {
	        Subquery<Long> promoSubquery = criteriaQuery.subquery(Long.class);
	        Root<UseDiscount> promoRoot = promoSubquery.from(UseDiscount.class);
	        Join<UseDiscount, DiscountDetail> promoDiscountDetailJoin = promoRoot.join("discountDetail", JoinType.INNER);
	        promoSubquery.where(
	                         criteriaBuilder.equal(promoRoot.get("bookingPayment").get("booking"), table),
	                         criteriaBuilder.equal(promoDiscountDetailJoin.get("discountType"), "折扣碼"),
	                         criteriaBuilder.equal(promoDiscountDetailJoin.get("name"), promo)
	                     );
	        Predicate promoPredicate = criteriaBuilder.exists(promoSubquery);
	        predicates.add(promoPredicate);
	    }
	    
	    if (pack != null && pack.length() != 0) {
	        Subquery<Long> packSubquery = criteriaQuery.subquery(Long.class);
	        Root<UseDiscount> packRoot = packSubquery.from(UseDiscount.class);
	        Join<UseDiscount, DiscountDetail> packDiscountDetailJoin = packRoot.join("discountDetail", JoinType.INNER);
	        packSubquery.where(
	                        criteriaBuilder.equal(packRoot.get("bookingPayment").get("booking"), table),
	                        criteriaBuilder.equal(packDiscountDetailJoin.get("discountType"), "專案"),
	                        criteriaBuilder.equal(packDiscountDetailJoin.get("name"), pack)
	                    );
	        Predicate packPredicate = criteriaBuilder.exists(packSubquery);
	        predicates.add(packPredicate);
	    }
		
		
		
		
		if(orderNumber != null && orderNumber.length() != 0) {
			Predicate p = criteriaBuilder.equal(bookingDetailJoin.get("orderNumber"), orderNumber);
		    predicates.add(p);
		}

		if (predicates != null && !predicates.isEmpty()) {
		    Predicate[] array = predicates.toArray(new Predicate[0]);
		    criteriaQuery = criteriaQuery.where(array);
		}

		// Order By
		if (dir) {
		    criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(table.get(order)));
		} else {
		    criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.asc(table.get(order)));
		}

		TypedQuery<Booking> typedQuery = this.getSession().createQuery(criteriaQuery)
		        .setFirstResult(start)
		        .setMaxResults(rows);

		List<Booking> result = typedQuery.getResultList();
		if (result != null && !result.isEmpty()) {
		    return result;
		} else {
		    return null;
		}
	}
}
