package com.ispan.hotel.service.n;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.FacilityDetail;
import com.ispan.hotel.repository.n.FacilityDetailRepositoryCBH;



@Service
public class FacilityDetailServiceCBH {
	
	@Autowired
	private FacilityDetailRepositoryCBH facilityDetailRepository;
	
	public FacilityDetail insert(FacilityDetail facilityDetail) {
		
		return  facilityDetailRepository.save(facilityDetail);
		
	}
	
	
	public FacilityDetail findById(Integer id) {
		
		if(id!=null) {
			Optional<FacilityDetail> byId = facilityDetailRepository.findById(id);
			if(byId.isPresent()) {
				FacilityDetail faDetail = byId.get();
				return faDetail;
			}
		}
		return null;
		
	}

}
