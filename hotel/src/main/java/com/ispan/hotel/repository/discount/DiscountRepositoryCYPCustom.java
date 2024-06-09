package com.ispan.hotel.repository.discount;

import java.util.List;

import com.ispan.hotel.model.Discount;
import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.RoomType;

public interface DiscountRepositoryCYPCustom {


	public Discount selectByDiscountDetailAndRoomType(DiscountDetail discountDetail, RoomType roomType);
	
	
}
