package com.ispan.hotel.repository.n;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ispan.hotel.model.RoomPhoto;
import com.ispan.hotel.model.RoomType;



public interface RoomPhotoRepositoryCBH extends JpaRepository<RoomPhoto, Integer>{
	
	
	
}
