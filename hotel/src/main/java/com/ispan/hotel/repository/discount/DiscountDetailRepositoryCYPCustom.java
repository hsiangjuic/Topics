package com.ispan.hotel.repository.discount;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ispan.hotel.model.DiscountDetail;

public interface DiscountDetailRepositoryCYPCustom {

	 //public Page<DiscountDetail> findByCriteria(List<String> statusArray, List<String> typeArray, Boolean memberOnly, Pageable pageable);
	
	
	public List<DiscountDetail> selectByStatusAndType(List<String> statusArray, List<String> typeArray,Boolean dir);
}
