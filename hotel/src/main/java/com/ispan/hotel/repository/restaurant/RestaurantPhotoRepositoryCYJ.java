package com.ispan.hotel.repository.restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Restaurant;
import com.ispan.hotel.model.RestaurantPhoto;
import com.ispan.hotel.model.RoomPhoto;
import com.ispan.hotel.model.RoomType;

@Repository
public interface RestaurantPhotoRepositoryCYJ extends JpaRepository<RestaurantPhoto, Integer>{
	@Query("from RestaurantPhoto where restaurant = :restaurant")
	public List<RestaurantPhoto> findByRestaurant(Restaurant restaurant);

}
