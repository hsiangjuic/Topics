package com.ispan.hotel.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;




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
@Table(name = "room")
public class Room {
	
//	r_id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "r_id")
	private Integer rId;
	
//	rt_id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="rt_id", referencedColumnName = "rt_id", nullable=false)
	private RoomType roomType;
	
//	floor
	@Column(name = "floor")
	private String floor;
	
//	room status 
	@Column(name = "room_status")
	private String roomStatus;
	
//	room number	
	@Column(name = "room_number")
	private String roomNumber;

//	last modified date
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified_date")
	private LocalDateTime lastModifiedDate;
	
	@PrePersist // 當物件狀態要轉移到 Persistent 狀態以前，先執行這個方法
	public void onCreate() {
		if (lastModifiedDate == null) {
			lastModifiedDate = LocalDateTime.now();
		}
	}

//	last modified emp
	@Column(name = "last_modified_emp")
	private String lastModifiedEmp;

//	last modified text
	@Column(name = "last_modified_text")
	private String lastModifiedText;
	

//	@OneToMany(mappedBy="room", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
//	private Set<Booking> bookings;


	
	
	public Room() {}
	
	public Room(String floor, String roomStatus, String roomNumber,LocalDateTime lastModifiedDate, String lastModifiedEmp, String lastModifiedText) {
		
		this.floor = floor;
		this.roomStatus = roomStatus;
		this.roomNumber = roomNumber;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText; 
		
	}
	
	public Room(String floor, String roomStatus, String roomNumber,LocalDateTime lastModifiedDate, String lastModifiedEmp, String lastModifiedText,RoomType roomType ) {
		
		this.roomType = roomType;
		this.floor = floor;
		this.roomStatus = roomStatus;
		this.roomNumber = roomNumber;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText; 
		
	}
	
	public Room(Integer rId, RoomType roomType, String floor, String roomStatus, String roomNumber,
			LocalDateTime lastModifiedDate, String lastModifiedEmp, String lastModifiedText) {
		super();
		this.rId = rId;
		this.roomType = roomType;
		this.floor = floor;
		this.roomStatus = roomStatus;
		this.roomNumber = roomNumber;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;

	}


	public Integer getrId() {
		return rId;
	}


	public void setrId(Integer rId) {
		this.rId = rId;
	}


	public RoomType getRoomType() {
		return roomType;
	}


	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}


	public String getFloor() {
		return floor;
	}


	public void setFloor(String floor) {
		this.floor = floor;
	}


	public String getRoomStatus() {
		return roomStatus;
	}


	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}


	public String getRoomNumber() {
		return roomNumber;
	}


	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}


	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}


	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}


	public String getLastModifiedEmp() {
		return lastModifiedEmp;
	}


	public void setLastModifiedEmp(String lastModifiedEmp) {
		this.lastModifiedEmp = lastModifiedEmp;
	}


	public String getLastModifiedText() {
		return lastModifiedText;
	}


	public void setLastModifiedText(String lastModifiedText) {
		this.lastModifiedText = lastModifiedText;
	}



//	public Set<Booking> getBookings() {
//		return bookings;
//	}
//
//
//	public void setBookings(Set<Booking> bookings) {
//		this.bookings = bookings;
//	}


	@Override
	public String toString() {
		return "Room [rId=" + rId + ", roomType=" + roomType + ", floor=" + floor + ", roomStatus=" + roomStatus
				+ ", roomNumber=" + roomNumber + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedEmp="
				+ lastModifiedEmp + ", lastModifiedText=" + lastModifiedText + "]";
	}


	


	


}
