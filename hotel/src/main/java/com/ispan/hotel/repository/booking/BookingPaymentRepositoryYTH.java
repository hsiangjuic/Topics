package com.ispan.hotel.repository.booking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Booking;
import com.ispan.hotel.model.BookingPayment;


@Repository
public interface BookingPaymentRepositoryYTH extends JpaRepository<BookingPayment, Integer>  {
	@Query("from BookingPayment where booking = :booking")
	public List<BookingPayment> findByBooking(Booking booking);
	
}
