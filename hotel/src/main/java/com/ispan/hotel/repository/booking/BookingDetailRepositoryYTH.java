package com.ispan.hotel.repository.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.BookingDetail;


@Repository
public interface BookingDetailRepositoryYTH extends JpaRepository<BookingDetail, Integer>, BookingDetailDAOYTH{
	@Query("from BookingDetail where orderNumber = :orderNumber")
	public BookingDetail findByOrderNumber(String orderNumber);
	
}
