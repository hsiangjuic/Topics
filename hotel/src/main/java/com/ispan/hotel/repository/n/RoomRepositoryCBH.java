package com.ispan.hotel.repository.n;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.hotel.model.Room;
import com.ispan.hotel.model.RoomType;



public interface RoomRepositoryCBH extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room> {
	
	@Query("from Room where roomType = :n")
	public List<RoomType> findByRoomName(@Param("n") RoomType roomType);
	
}
