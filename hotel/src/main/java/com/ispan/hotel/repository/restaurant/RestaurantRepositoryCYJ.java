package com.ispan.hotel.repository.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Restaurant;

@Repository
public interface RestaurantRepositoryCYJ extends JpaRepository<Restaurant, Integer> {

	@Query("SELECT r.totalSeat FROM Restaurant r WHERE r.reId = :id")
	public Long countAvailibleSeat(@Param("id") Integer id);

}
