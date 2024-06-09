package com.ispan.hotel.repository.n;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.hotel.model.RoomType;



public interface RoomTypeRepositoryCBH extends JpaRepository<RoomType, Integer> {
	
	
	@Query("from RoomType where roomName = :n")
	public List<RoomType> findByRoomName(@Param("n") String name);
	
	@Query("from RoomType where roomName = :n")
	Optional<RoomType> findByRoomTypeNameOptimal(@Param("n") String name);
	
	
	
	
	
}
