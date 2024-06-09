package com.ispan.hotel.service.restaurant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Restaurant;
import com.ispan.hotel.model.RestaurantPhoto;
import com.ispan.hotel.repository.restaurant.RestaurantPhotoRepositoryCYJ;
import com.ispan.hotel.util.DatetimeConverter;

@Service
public class RestaurantPhotoServiceCYJ {

	@Autowired
	private RestaurantPhotoRepositoryCYJ restaurantPhotoRepository;

	@Autowired
	private RestaurantServiceCYJ restaurantService;

	public RestaurantPhoto create(String json) {
		JSONObject obj = new JSONObject(json);

		Integer reId = obj.isNull("reId") ? null : obj.getInt("reId");
		String photoName = obj.isNull("photoName") ? null : obj.getString("photoName");
		String photoPath = obj.isNull("photoPath") ? null : obj.getString("photoPath");
		String addedDate = obj.isNull("addedDate") ? null : obj.getString("addedDate");

		if (reId != null) {
			Restaurant restaurant = restaurantService.findById(reId);
			if (restaurant != null) {
				RestaurantPhoto insert = new RestaurantPhoto();
				insert.setRestaurant(restaurant);
				insert.setPhotoName(photoName);

				if (addedDate != null && addedDate.length() != 0) {
					LocalDateTime temp = DatetimeConverter.parseLocalDateTime(addedDate, "yyyy-MM-dd HH:mm:ss");
					insert.setAddedDate(temp);
				} else {
					insert.setAddedDate(LocalDateTime.now());
				}

			}
		}

		return null;
	}

	public RestaurantPhoto findById(Integer id) {
		if (id != null) {
			Optional<RestaurantPhoto> opt = restaurantPhotoRepository.findById(id);
			if (opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}

	public List<RestaurantPhoto> find() {
		return restaurantPhotoRepository.findAll();
	}

	public List<RestaurantPhoto> findByRestaurant(Restaurant restaurant) {
		return restaurantPhotoRepository.findByRestaurant(restaurant);
	}

	public RestaurantPhoto modify(String json) {
		JSONObject obj = new JSONObject(json);

		Integer reId = obj.isNull("reId") ? null : obj.getInt("reId");
		Integer repId = obj.isNull("repId") ? null : obj.getInt("repId");
		String photoName = obj.isNull("photoName") ? null : obj.getString("photoName");
		String photoPath = obj.isNull("photoPath") ? null : obj.getString("photoPath");
		String addedDate = obj.isNull("addedDate") ? null : obj.getString("addedDate");

		if (reId != null && repId != null) {
			Restaurant restaurant = restaurantService.findById(reId);
			Optional<RestaurantPhoto> opt = restaurantPhotoRepository.findById(reId);
			if (restaurant != null && opt.isPresent()) {
				RestaurantPhoto update = opt.get();
				update.setRestaurant(restaurant);
				update.setPhotoName(photoName);
				update.setPhotoPath(photoPath);

				return restaurantPhotoRepository.save(update);
			}
		}
		return null;

	}

}
