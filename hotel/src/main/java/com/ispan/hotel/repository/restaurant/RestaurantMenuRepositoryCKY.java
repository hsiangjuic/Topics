package com.ispan.hotel.repository.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ispan.hotel.model.RestaurantMenu;

public interface RestaurantMenuRepositoryCKY extends JpaRepository <RestaurantMenu,Integer> {

    

//    
//    @Query("from RestaurantMenu where RestaurantMenu = :RestaurantMenu")
//    public RestaurantMenu findByRestaurantMenu(String RestaurantMenu);
    
}
