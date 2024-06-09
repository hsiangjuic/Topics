package com.ispan.hotel.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "room_photo")
public class RoomPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rp_id")
    private Integer rpId;

    @Column(name = "photo_name")
    private String photoname;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "photo_description")
    private String photoDescription;
    
    @Column(name = "photo_file", columnDefinition = "image")
	private byte[] photoFile;
    


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

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "rt_id", referencedColumnName = "rt_id")
    private RoomType roomType;
    

	@Override
	public String toString() {
		return "RoomPhoto [rpId=" + rpId + ", photoname=" + photoname + ", photoPath=" + photoPath
				+ ", photoDescription=" + photoDescription + ", addedDate=" + addedDate + "]";
	}



	public RoomPhoto(Integer rpId, String photoname, String photoPath, String photoDescription, LocalDateTime addedDate,
			RoomType roomType) {
		super();
		this.rpId = rpId;
		this.photoname = photoname;
		this.photoPath = photoPath;
		this.photoDescription = photoDescription;
		this.addedDate = addedDate;
		this.roomType = roomType;
	}

	

	public RoomPhoto(String photoname, byte[] photoFile,String photoDescription,  LocalDateTime addedDate) {
		super();
		this.photoname = photoname;
		this.photoDescription = photoDescription;
		this.photoFile = photoFile;
		this.addedDate = addedDate;
	}

	


	public RoomPhoto(String photoname, byte[] photoFile ,String photoDescription, LocalDateTime addedDate,
			RoomType roomType) {
		super();
		this.photoname = photoname;
		this.photoDescription = photoDescription;
		this.photoFile = photoFile;
		this.addedDate = addedDate;
		this.roomType = roomType;
	}



	public Integer getRpId() {
		return rpId;
	}



	public void setRpId(Integer rpId) {
		this.rpId = rpId;
	}



	public String getPhotoname() {
		return photoname;
	}



	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}



	public String getPhotoPath() {
		return photoPath;
	}



	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}



	public String getPhotoDescription() {
		return photoDescription;
	}
	

	public byte[] getPhotoFile() {
		return photoFile;
	}
	

	public void setPhotoFile(byte[] photoFile) {
		this.photoFile = photoFile;
	}
	

	public void setPhotoDescription(String photoDescription) {
		this.photoDescription = photoDescription;
	}



	public LocalDateTime getAddedDate() {
		return addedDate;
	}



	public void setAddedDate(LocalDateTime addedDate) {
		this.addedDate = addedDate;
	}



	public RoomType getRoomType() {
		return roomType;
	}



	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}



	public RoomPhoto() {
		super();
	}
    
}