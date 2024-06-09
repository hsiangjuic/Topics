package com.ispan.hotel.repository.booking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Booking;
import com.ispan.hotel.model.BookingDetail;


@Repository
public interface BookingRepositoryYTH extends JpaRepository<Booking, Integer>, BookingDAOYTH  {
	@Query("from Booking where bookingDetail = :bookingDetail")
	public List<Booking> findByBookingDetail(BookingDetail bookingDetail);
	
}
