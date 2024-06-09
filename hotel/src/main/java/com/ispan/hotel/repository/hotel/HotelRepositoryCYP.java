package com.ispan.hotel.repository.hotel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.hotel.model.Hotel;

public interface HotelRepositoryCYP extends JpaRepository<Hotel, Integer> {
	
	//Keyword Sample JPQL snippet
	public Optional<Hotel> findByName(String name);
}
