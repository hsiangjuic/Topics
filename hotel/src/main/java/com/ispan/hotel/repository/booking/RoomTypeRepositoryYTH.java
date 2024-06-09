package com.ispan.hotel.repository.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.RoomType;


@Repository
public interface RoomTypeRepositoryYTH extends JpaRepository<RoomType, Integer>  {
	
}
