package com.ispan.hotel.service.n;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Feature;
import com.ispan.hotel.repository.n.FeatureRepositoryCBH;






@Service
public class FeatureServiceCBH {
	
	@Autowired
	private FeatureRepositoryCBH featureRepository;
	
	// 查詢
	public Feature findById(Integer id) {
		
		if(id!=null) {
			Optional<Feature> byId = featureRepository.findById(id);
			if(byId.isPresent()) {
				Feature feature = byId.get();
				return feature;
			}
		}
		return null;
		
	}
	
	public Feature insert(Feature feature) {
		
		return featureRepository.save(feature);
		
	}
	
	
	

}
