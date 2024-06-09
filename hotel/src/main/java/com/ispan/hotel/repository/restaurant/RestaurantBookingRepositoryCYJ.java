package com.ispan.hotel.repository.restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.loader.ast.spi.Loadable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Customer;
import com.ispan.hotel.model.LatestNews;
import com.ispan.hotel.model.RestaurantBooking;

@Repository
public interface RestaurantBookingRepositoryCYJ extends JpaRepository<RestaurantBooking, Integer>, RestaurantBookingDAOCYJ{
	// 能用嗎?? order by? 
	@Query("from RestaurantBooking where reserveTime in (:reserveTime)")
	public List<RestaurantBooking> selectByStatus(@Param(value = "reserveTime") List<String> reserveTime);

	public long countByReserveTimeAndMealTime(LocalDate parse, String mealTime);

	public long countByReserveTime(LocalDate parse);
	
	@Query("from RestaurantBooking where email = :email and reserveTime = :reserveTime")
	public RestaurantBooking findByEmail(String email, LocalDate reserveTime);
	
	@Query("from RestaurantBooking where reserveTime = :reserveTime ")
	public RestaurantBooking findByReserveTime(String reserveTime);
	
	@Query("from RestaurantBooking where cellphone = :cellphone")
	public RestaurantBooking findByCellphone(String cellphone);
	
//	@Query("from RestaurantBooking where bookingDetail = :bookingDetail")
//	public List<RestaurantBooking> findByBookingDetail(RestaurantBooking bookingDetail);
	
	@Query("from RestaurantBooking where email = :email and reserveTime = :reserveTime")
	public RestaurantBooking findByAll(String email, LocalDate reserveTime);
	
	@Query("from RestaurantBooking where rebId = :rebId")
	public Optional<RestaurantBooking> findById(Integer rebId);

	@Query("from RestaurantBooking where email = :email")
	public RestaurantBooking findByE(String email);
	
	//0603試著改寫  蘋的寫法
	@Query("FROM RestaurantBooking WHERE isCustomer IN (:rebIdArray) ")
	public Page<RestaurantBooking> selectByrebId(@Param("rebIdArray") List<String> rebIdArray, Pageable pageable);


	

}
