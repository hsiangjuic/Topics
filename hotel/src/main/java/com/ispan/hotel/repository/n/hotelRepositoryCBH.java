package com.ispan.hotel.repository.n;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.hotel.model.Hotel;




public interface hotelRepositoryCBH extends JpaRepository<Hotel, Integer> {
	
	@Query("from Hotel where name = :n")
	public List<Hotel> findByHotelName(@Param("n") String name);
	
		
}

