package com.ispan.hotel.service.n;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Facility;
import com.ispan.hotel.repository.n.FacilityRepositoryCBH;



@Service
public class FacilityServiceCBH {
	
	@Autowired
	private FacilityRepositoryCBH facilityRepository;
	
	public Facility findById(Integer id) {
		
		if(id != null) {
			
			Optional<Facility> byId = facilityRepository.findById(id);
			if(byId.isPresent()) {
				
				Facility facilityData = byId.get();
				return facilityData;
				
			}
			
		}
		return null;
		
	}
	
	
	public Facility insert(Facility facility) {
		
		return facilityRepository.save(facility);
		
	}
	

}
