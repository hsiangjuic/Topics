package com.ispan.hotel.repository.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.RestaurantPoint;



@Repository
public interface RestaurantPointRepositoryCKY extends JpaRepository<RestaurantPoint, Integer>{

//    @Query("from RestaurantPoint where restaurant = :restaurant")
//    public RestaurantPoint findByRestaurant(String restaurant);


}
