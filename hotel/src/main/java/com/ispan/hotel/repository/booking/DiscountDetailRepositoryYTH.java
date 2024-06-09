package com.ispan.hotel.repository.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.DiscountDetail;


@Repository
public interface DiscountDetailRepositoryYTH extends JpaRepository<DiscountDetail, Integer>  {
	
	@Query("from DiscountDetail where promoCode = :promoCode")
	public DiscountDetail findByPromoCode(String promoCode);
}
