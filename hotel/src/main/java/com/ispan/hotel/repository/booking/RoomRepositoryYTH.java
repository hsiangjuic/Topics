package com.ispan.hotel.repository.booking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Room;
import com.ispan.hotel.model.RoomType;


@Repository
public interface RoomRepositoryYTH extends JpaRepository<Room, Integer>  {
	
	@Query("from Room where roomType = :roomType")
	public List<Room> findByRoomType(RoomType roomType);
	
	@Query("select count(*) from Room where roomType = :roomType and roomStatus = '空閒中'")
	public Long countAvailibleRoom(RoomType roomType);
	
	public Room findByRoomNumber(String roomNumber);
	
}
