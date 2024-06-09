package com.ispan.hotel.repository.booking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Discount;
import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.RoomType;


@Repository
public interface DiscountRepositoryYTH extends JpaRepository<Discount, Integer>  {
	
	@Query("from Discount where roomType = :roomType")
	public List<Discount> findByRoomType(RoomType roomType);
	
	@Query("from Discount where discountDetail = :discountDetail")
	public List<Discount> findByDiscountDetail(DiscountDetail discountDetail);
	
}
