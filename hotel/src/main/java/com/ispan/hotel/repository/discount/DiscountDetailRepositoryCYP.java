package com.ispan.hotel.repository.discount;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.LatestNews;

@Repository
public interface DiscountDetailRepositoryCYP extends JpaRepository<DiscountDetail, Integer> ,DiscountDetailRepositoryCYPCustom{
	
	//內建 save(DiscountDetail d)
	//內建 findById(Integer id)
	//內建 findAll()
	//內建 count()
	//內建 deleteById(Integer id)
	//內建 delete(DiscountDetail d)
	
	//練習CriteriaQuery寫在DiscountdDetailRepositoryCustomImpl裡面->selectByStatusAndType
	
	@Query("from DiscountDetail where status in (:statuses)")
	public List<DiscountDetail> selectByStatus(@Param(value="statuses")List<String> statuses);
	
	@Query("from DiscountDetail where promoCode = :promoCode")
	public List<DiscountDetail> findByPromoCode(String promoCode);
	
	@Query("from DiscountDetail where name = :name")
	public List<DiscountDetail> findByName(String name);
}
