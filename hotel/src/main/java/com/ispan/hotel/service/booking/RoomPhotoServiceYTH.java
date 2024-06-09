package com.ispan.hotel.service.booking;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ispan.hotel.model.RoomPhoto;
import com.ispan.hotel.model.RoomType;
import com.ispan.hotel.repository.booking.RoomPhotoRepositoryYTH;
import com.ispan.hotel.util.DatetimeConverter;

@Service
@Transactional
public class RoomPhotoServiceYTH {
	@Autowired
	private RoomPhotoRepositoryYTH roomPhotoRepository;
	
	@Autowired
	private RoomTypeServiceYTH roomTypeService;
	
	public RoomPhoto create(String json) {
		try {
			JSONObject obj = new JSONObject(json);

			Integer rtId = obj.isNull("rtId") ? null : obj.getInt("rtId");
			String photoname = obj.isNull("photoname") ? null : obj.getString("photoname");
			String photoPath = obj.isNull("photoPath") ? null : obj.getString("photoPath");
			String photoDescription = obj.isNull("photoDescription") ? null : obj.getString("photoDescription");
			String addedDate = obj.isNull("addedDate") ? null : obj.getString("addedDate");
			
			if(rtId != null) {
				RoomType roomtype= roomTypeService.findById(rtId);
				if(roomtype != null) {
					RoomPhoto insert = new RoomPhoto();
					insert.setRoomType(roomtype);
					insert.setPhotoname(photoname);
					insert.setPhotoPath(photoPath);
					photoDescription = roomtype.getRoomName()+photoDescription;
					insert.setPhotoDescription(photoDescription);
					
					if(addedDate != null && addedDate.length() != 0) {
						LocalDateTime temp = DatetimeConverter.parseLocalDateTime(addedDate, "yyyy-MM-dd HH:mm:ss");
						insert.setAddedDate(temp);
					} else {
						insert.setAddedDate(LocalDateTime.now());
					}

					return roomPhotoRepository.save(insert);
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public RoomPhoto findById(Integer id) {
		if(id != null) {
			Optional<RoomPhoto> opt = roomPhotoRepository.findById(id);
			if(opt.isPresent()) {
				return opt.get();
			}
		}
		
		return null;
	}
	
	public List<RoomPhoto> find() {
		return roomPhotoRepository.findAll();
	}
	
	public List<RoomPhoto> findByRoomType(RoomType roomtype){
		return roomPhotoRepository.findByRoomType(roomtype);
	}
	
	public RoomPhoto modify(String json) {
		try {
			JSONObject obj = new JSONObject(json);

			Integer rpId = obj.isNull("rpId") ? null : obj.getInt("rpId");
			Integer rtId = obj.isNull("rtId") ? null : obj.getInt("rtId");
			String photoname = obj.isNull("photoname") ? null : obj.getString("photoname");
			String photoPath = obj.isNull("photoPath") ? null : obj.getString("photoPath");
			String photoDescription = obj.isNull("photoDescription") ? null : obj.getString("photoDescription");
			
			if(rtId != null && rpId != null) {
				RoomType roomtype= roomTypeService.findById(rtId);
				Optional<RoomPhoto> opt = roomPhotoRepository.findById(rpId);
				if(roomtype != null && opt.isPresent()) {
					RoomPhoto update = opt.get();
					update.setRoomType(roomtype);
					update.setPhotoname(photoname);
					update.setPhotoPath(photoPath);
					update.setPhotoDescription(photoDescription);
					
					return roomPhotoRepository.save(update);
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
