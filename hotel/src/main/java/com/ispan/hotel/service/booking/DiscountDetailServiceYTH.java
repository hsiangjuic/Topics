package com.ispan.hotel.service.booking;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Discount;
import com.ispan.hotel.model.DiscountDetail;
import com.ispan.hotel.repository.booking.DiscountDetailRepositoryYTH;
import com.ispan.hotel.repository.booking.DiscountRepositoryYTH;
import com.ispan.hotel.repository.booking.UseDiscountRepositoryYTH;
import com.ispan.hotel.util.DatetimeConverter;

@Service
public class DiscountDetailServiceYTH {
	@Autowired
	private DiscountDetailRepositoryYTH discountDetailRepository;
	
	@Autowired
	private UseDiscountRepositoryYTH useDiscountRepository;
	
	@Autowired
	private DiscountRepositoryYTH discountRepository;
	
	public DiscountDetail findById(Integer id) {
		if(id != null) {
			Optional<DiscountDetail> opt = discountDetailRepository.findById(id);
			if(opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}
	
	public List<DiscountDetail> find(){
		return discountDetailRepository.findAll();
	}
	
	public DiscountDetail findByPromoCode(String promo) {
		return discountDetailRepository.findByPromoCode(promo.toUpperCase());
	}
	
	public String checkDiscount(String promo, String json) {
		JSONObject obj = new JSONObject(json);
		Integer rtId = obj.isNull("rtId") ? null : obj.getInt("rtId");
		String beginDate = obj.isNull("beginDate") ? null : obj.getString("beginDate");
		String lastDate = obj.isNull("lastDate") ? null : obj.getString("lastDate");
		
		//	大小寫不影響優惠碼輸入
		DiscountDetail discountDetail = discountDetailRepository.findByPromoCode(promo.toUpperCase());
		
		if(discountDetail != null) {
			if (!discountDetail.getDiscountType().equals("專案")) {
				List<Discount> discounts = discountRepository.findByDiscountDetail(discountDetail);
				//	確定該房型套用此優惠
				for (Discount discount : discounts) {
					if (discount.getRoomType().getRtId() == rtId) {
						//	確定優惠處於使用期間
						LocalDate discountBegin = discountDetail.getBeginDate();
						LocalDate discountLast = discountDetail.getLastDate();

						LocalDate orderBegin = DatetimeConverter.parseLocalDate(beginDate, "yyyy-MM-dd");
						LocalDate orderLast = DatetimeConverter.parseLocalDate(lastDate, "yyyy-MM-dd");

						if (orderBegin.compareTo(discountBegin) >= 0 && discountLast.compareTo(orderLast) >= 0) {
							//	確定優惠尚未達到使用次數
							if (discountDetail.getMaxTimes() > useDiscountRepository
									.count(discountDetail)) {
								return "已套用此優惠";
							} else {
								return "優惠已達使用次數";
							}
						} else {
							return "非優惠使用期間";
						}
					}
				}
				return "此房型無法套用此優惠";
			}
			return "非可使用折扣碼";
		}
		
		return "優惠碼輸入錯誤";
	}

}
