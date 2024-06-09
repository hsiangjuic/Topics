package com.ispan.hotel.repository.discount;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Discount;
import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.RoomType;

@Repository
public interface DiscountRepositoryCYP extends JpaRepository<Discount, Integer> ,DiscountRepositoryCYPCustom{
	

	//內建 save(DiscountDetail d)
	//內建 findById(Integer id)
	//內建 findAll()
	//內建 count()
	//內建 deleteById(Integer id)
	//內建 delete(DiscountDetail d)
	
	//另外有寫List<Discount>  selectByDiscountDetailAndRoomType(DiscountDetail discountDetail, RoomType roomType)
	//寫在DiscountRepositoryCYPCustomImpl
	
	//用discountDetail找到所有對應的RoomType的Id列表
    @Query("select d.roomType.id from Discount d where d.discountDetail = :discountDetail")
    public List<Integer> findIdByDiscountDetail(@Param("discountDetail") DiscountDetail discountDetail);
    
    //用discountDetail找到所有對應的RoomType列表
    @Query("select d.roomType from Discount d where d.discountDetail = :discountDetail")
    public List<RoomType> findRoomTypeListByDiscountDetail(@Param("discountDetail") DiscountDetail discountDetail);
    
    

//    @Transactional不符合命名慣例不能用
//    @Modifying
//    @Query("delete from DiscountRoomMapping drm where drm.discountDetail = :updateObj and drm.roomType.id in :removedIds")
//    public void deleteByDiscountDetailAndRoomTypeIds(@Param("updateObj") DiscountDetail updateObj, @Param("removedIds") List<Integer> removedIds);

    

}
