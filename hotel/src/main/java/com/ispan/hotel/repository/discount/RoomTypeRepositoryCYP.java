package com.ispan.hotel.repository.discount;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.RoomType;

@Repository
public interface RoomTypeRepositoryCYP extends JpaRepository<RoomType, Integer>  {
	//內建 save(DiscountDetail d)
	//內建 findById(Integer id)
	//內建 findAll() 會用到
	//內建 count()
	//內建 deleteById(Integer id)
	//內建 delete(DiscountDetail d)
	
//	//用discountDetail找到所有對應的RoomType列表
//	@Query("from Discount where discountDetail=:discountDetail")
//	public List<RoomType> findByDiscountDetail(@Param("discountDetail") DiscountDetail discountDetail);


}



