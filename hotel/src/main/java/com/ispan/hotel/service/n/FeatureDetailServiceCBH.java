package com.ispan.hotel.service.n;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.FeatureDetail;
import com.ispan.hotel.repository.n.FeatureDetailRepositoryCBH;



@Service
public class FeatureDetailServiceCBH {
	
	@Autowired
	private FeatureDetailRepositoryCBH featureDetailRepository;
	
	public FeatureDetail insert(FeatureDetail featureDetail) {
		
		return  featureDetailRepository.save(featureDetail);
		
	}
	
	
	public FeatureDetail findById(Integer id) {
		
		if(id!=null) {
			Optional<FeatureDetail> byId = featureDetailRepository.findById(id);
			if(byId.isPresent()) {
				FeatureDetail featureDetail = byId.get();
				return featureDetail;
			}
		}
		return null;
		
	}

}
