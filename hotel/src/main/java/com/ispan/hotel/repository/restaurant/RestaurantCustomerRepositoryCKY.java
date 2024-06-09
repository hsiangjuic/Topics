package com.ispan.hotel.repository.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.hotel.model.RestaurantCustomer;

public interface RestaurantCustomerRepositoryCKY extends JpaRepository<RestaurantCustomer, Integer> {
    
}
