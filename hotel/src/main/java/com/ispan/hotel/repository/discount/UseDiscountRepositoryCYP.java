package com.ispan.hotel.repository.discount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.UseDiscount;

@Repository
public interface UseDiscountRepositoryCYP extends JpaRepository<UseDiscount, Integer> {
	
	//內建 save(DiscountDetail d)
	//內建 findById(Integer id)
	//內建 findAll()
	//內建 count()
	//內建 deleteById(Integer id)
	//內建 delete(DiscountDetail d)
	
	//select count(*) from UseDiscount where ddId= :ddId
	@Query("SELECT COUNT(u) FROM UseDiscount u WHERE u.discountDetail = :discountDetail")
	public Long countByDiscountDetail(@Param("discountDetail") DiscountDetail discountDetail);

}
