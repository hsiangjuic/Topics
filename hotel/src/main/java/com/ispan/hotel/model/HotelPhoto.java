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
@Table(name = "HotelPhoto")
public class HotelPhoto {
	
//	hp_id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hp_id")
	private Integer hpId;
	
//	h_id
	@ManyToOne
	@JoinColumn(name="h_id", referencedColumnName = "h_id", nullable=false)
	private Hotel hotel;
	
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


	public HotelPhoto(Integer hpId, Hotel hotel, String photoName, String photoPath, LocalDateTime addedDate) {
		super();
		this.hpId = hpId;
		this.hotel = hotel;
		this.photoName = photoName;
		this.photoPath = photoPath;
		this.addedDate = addedDate;
	}



	public Integer getHpId() {
		return hpId;
	}



	public void setHpId(Integer hpId) {
		this.hpId = hpId;
	}



	public Hotel getHotel() {
		return hotel;
	}



	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
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



	public HotelPhoto() {
	}

}
