package com.ispan.hotel.service.booking;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.BookingPayment;
import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.model.UseDiscount;
import com.ispan.hotel.repository.booking.UseDiscountRepositoryYTH;

@Service
public class UseDiscountServiceYTH {

	@Autowired
	private UseDiscountRepositoryYTH useDiscountRepository;
	
	@Autowired
	private BookingPaymentServiceYTH bookingService;
	
	@Autowired
	private DiscountDetailServiceYTH discountDetailService;
	
	public UseDiscount create(BookingPayment bookingPayment, Integer ddId) {
		
		if (bookingPayment != null && ddId != null) {
			DiscountDetail discountDetail = discountDetailService.findById(ddId);
			if (discountDetail != null) {
				UseDiscount insert = new UseDiscount();
				insert.setBookingPayment(bookingPayment);
				insert.setDiscountDetail(discountDetail);

				return useDiscountRepository.save(insert);
			} 
		}
		return null;
	}
	
	public UseDiscount modify(Integer udId,Integer bId, Integer ddId) {
		
		if (udId != null && bId != null && ddId != null) {
			Optional<UseDiscount> opt = useDiscountRepository.findById(udId);
			BookingPayment bookingPayment = bookingService.findById(bId);
			DiscountDetail discountDetail = discountDetailService.findById(ddId);
			if (opt.isPresent() && bookingPayment != null && discountDetail != null) {
				UseDiscount update = opt.get();
				update.setBookingPayment(bookingPayment);
				update.setDiscountDetail(discountDetail);

				return useDiscountRepository.save(update);
			} 
		}
		return null;
	}
	
	public UseDiscount findById(Integer id) {
		if(id != null) {
			Optional<UseDiscount> opt = useDiscountRepository.findById(id);
			if(opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}
	
	public List<UseDiscount> findByBookingPayment(BookingPayment bookingPayment){
		if(bookingPayment != null) {
			return useDiscountRepository.findByBookingPayment(bookingPayment);
		}
		return null;
	}
	
	public List<UseDiscount> find() {
		return useDiscountRepository.findAll();
	}
	
	public Boolean delete(Integer id) {
		if(id != null) {
			Optional<UseDiscount> opt = useDiscountRepository.findById(id);
			if(opt.isPresent()) {
				useDiscountRepository.deleteById(id);
				return true;
			}
		}
		return false; 
	}
	
}
