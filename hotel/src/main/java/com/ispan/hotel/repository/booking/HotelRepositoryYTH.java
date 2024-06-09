package com.ispan.hotel.repository.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Hotel;


@Repository
public interface HotelRepositoryYTH extends JpaRepository<Hotel, Integer>  {
	
	@Query("from Hotel where name = :name")
	public Hotel findByName(String name);
}
