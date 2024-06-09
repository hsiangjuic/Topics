package com.ispan.hotel.repository.booking;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.RoomPhoto;
import com.ispan.hotel.model.RoomType;


@Repository
public interface RoomPhotoRepositoryYTH extends JpaRepository<RoomPhoto, Integer>  {
	@Query("from RoomPhoto where roomType = :roomType")
	public List<RoomPhoto> findByRoomType(RoomType roomType);
}
