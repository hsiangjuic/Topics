package com.ispan.hotel.model;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "RestaurantPhoto")
public class RestaurantPhoto {

//	rep_id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rep_id")
	private Integer repId;

//	re_id
	@ManyToOne
	@JoinColumn(name = "re_id", nullable = false)
	private Restaurant restaurant;

//	photo name
	@Column(name = "photo_name")
	private String photoName;

//	photo file
	@Column(name = "photo_path")
	private String photoPath;

//	added date
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "added_date")
	private LocalDateTime addedDate;

	@PrePersist
	public void onCreate() {
		if (addedDate == null) {
			addedDate = LocalDateTime.now();
		}
	}

	public RestaurantPhoto(Integer repId, Restaurant restaurant, String photoName, String photoPath,
			LocalDateTime addedDate) {
		super();
		this.repId = repId;
		this.restaurant = restaurant;
		this.photoName = photoName;
		this.photoPath = photoPath;
		this.addedDate = addedDate;
	}

	public Integer getRepId() {
		return repId;
	}

	public void setRepId(Integer repId) {
		this.repId = repId;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public LocalDateTime getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDateTime addedDate) {
		this.addedDate = addedDate;
	}

	public RestaurantPhoto() {
	}

}
