package com.ispan.hotel.repository.booking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.BookingPayment;
import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.UseDiscount;


@Repository
public interface UseDiscountRepositoryYTH extends JpaRepository<UseDiscount, Integer>  {
	@Query("select count(*) from UseDiscount where discountDetail = :discountDetail")
	public Integer count(DiscountDetail discountDetail);
	
	@Query("from UseDiscount where bookingPayment = :bookingPayment")
	public List<UseDiscount> findByBookingPayment(BookingPayment bookingPayment);
}
