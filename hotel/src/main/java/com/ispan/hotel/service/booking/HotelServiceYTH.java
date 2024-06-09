package com.ispan.hotel.service.booking;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Hotel;
import com.ispan.hotel.repository.booking.HotelRepositoryYTH;

@Service
public class HotelServiceYTH {
	
	@Autowired
	private HotelRepositoryYTH hotelRepository;
	
	public Hotel create(String json) {
		
		try {
			JSONObject obj = new JSONObject(json);
			
			String name = obj.isNull("name") ? null : obj.getString("name");
			String address = obj.isNull("address") ? null : obj.getString("address");
			String tel = obj.isNull("tel") ? null : obj.getString("tel");
			String fax = obj.isNull("fax") ? null : obj.getString("fax");
			String checkinTime = obj.isNull("checkinTime") ? null : obj.getString("checkinTime");
			String checkoutTime = obj.isNull("checkoutTime") ? null : obj.getString("checkoutTime");
			String contactEmail = obj.isNull("contactEmail") ? null : obj.getString("contactEmail");
			String introduction = obj.isNull("introduction") ? null : obj.getString("introduction");
			String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");

			
			
			if(name != null && name.length() != 0) {
				Hotel hotel = hotelRepository.findByName(name);
				
				if(hotel == null) {
					Hotel insert = new Hotel();
					insert.setName(name);
					insert.setAddress(address);
					insert.setTel(tel);
					insert.setFax(fax);
					insert.setCheckinTime(checkinTime);
					insert.setCheckoutTime(checkoutTime);
					insert.setContactEmail(contactEmail);
					insert.setIntroduction(introduction);
					insert.setLastModifiedEmp(lastModifiedEmp);
					
					return hotelRepository.save(insert);
				
				}
				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Hotel findById(Integer id) {
		
		if(id != null) {
			Optional<Hotel> opt = hotelRepository.findById(id);
			
			if(opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}
	
	public Hotel modify(String json) {

		try {
			JSONObject obj = new JSONObject(json);
			
			Integer hId = obj.isNull("hId") ? null : obj.getInt("hId");
			String name = obj.isNull("name") ? null : obj.getString("name");
			String address = obj.isNull("address") ? null : obj.getString("address");
			String tel = obj.isNull("tel") ? null : obj.getString("tel");
			String fax = obj.isNull("fax") ? null : obj.getString("fax");
			String checkinTime = obj.isNull("checkinTime") ? null : obj.getString("checkinTime");
			String checkoutTime = obj.isNull("checkoutTime") ? null : obj.getString("checkoutTime");
			String contactEmail = obj.isNull("contactEmail") ? null : obj.getString("contactEmail");
			String introduction = obj.isNull("introduction") ? null : obj.getString("introduction");
			String lastModifiedEmp = obj.isNull("lastModifiedEmp") ? null : obj.getString("lastModifiedEmp");

			
			if(hId != null) {
				Optional<Hotel> opt = hotelRepository.findById(hId);
				
				if(opt.isPresent()) {
					Hotel update = opt.get();
					update.setName(name);
					update.setAddress(address);
					update.setTel(tel);
					update.setFax(fax);
					update.setCheckinTime(checkinTime);
					update.setCheckoutTime(checkoutTime);
					update.setContactEmail(contactEmail);
					update.setIntroduction(introduction);
					if(lastModifiedEmp != null) {
						update.setLastModifiedDate(LocalDateTime.now());
						update.setLastModifiedEmp(lastModifiedEmp);
					}
					
					return hotelRepository.save(update);
				
				}
				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	
}
