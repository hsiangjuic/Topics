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
@Table(name = "Restaurant")
public class Restaurant {

//	re_id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "re_id")
	private Integer reId;

//	@OneToMany(mappedBy = "restaurant", cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
//	private List<RestaurantPhoto> restaurantPhoto;

//	h_id
	@ManyToOne
	@JoinColumn(name = "h_id", nullable = false)
	private Hotel hotel;

//	name
	@Column(name = "name")
	private String name;

//	total seat
	@Column(name = "total_seat")
	private Integer totalSeat;

//	open time1
	@Column(name = "open_time1")
	private String openTime1;

//	close time1
	@Column(name = "close_time1")
	private String closeTime1;

//	open time2
	@Column(name = "open_time2")
	private String openTime2;

//	close time2
	@Column(name = "close_time2")
	private String closeTime2;

//	open time3
	@Column(name = "open_time3")
	private String openTime3;

//	close time3
	@Column(name = "close_time3")
	private String closeTime3;

//	tel
	@Column(name = "tel")
	private String tel;

//	address
	@Column(name = "address")
	private String address;
	
//	number_of_ guests
	@Column(name="number_of_ guests")
	private Integer numberOfGuests;

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

	public Restaurant(Integer reId, Hotel hotel, String name, Integer totalSeat,
			String openTime1, String closeTime1, String openTime2, String closeTime2, String openTime3,
			String closeTime3, String tel, String address, Integer numberOfGuests, LocalDateTime lastModifiedDate, String lastModifiedEmp,
			String lastModifiedText) {
		super();
		this.reId = reId;
		this.hotel = hotel;
		this.name = name;
		this.totalSeat = totalSeat;
		this.openTime1 = openTime1;
		this.closeTime1 = closeTime1;
		this.openTime2 = openTime2;
		this.closeTime2 = closeTime2;
		this.openTime3 = openTime3;
		this.closeTime3 = closeTime3;
		this.tel = tel;
		this.address = address;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
		this.numberOfGuests = numberOfGuests;
	}

	
	public Integer getNumberOfGuests() {
		return numberOfGuests;
	}


	public void setNumberOfGuests(Integer numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}


	public Integer getReId() {
		return reId;
	}

	public void setReId(Integer reId) {
		this.reId = reId;
	}

//	public List<RestaurantPhoto> getRestaurantPhoto() {
//		return restaurantPhoto;
//	}
//
//	public void setRestaurantPhoto(List<RestaurantPhoto> restaurantPhoto) {
//		this.restaurantPhoto = restaurantPhoto;
//	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTotalSeat() {
		return totalSeat;
	}

	public void setTotalSeat(Integer totalSeat) {
		this.totalSeat = totalSeat;
	}

	public String getOpenTime1() {
		return openTime1;
	}

	public void setOpenTime1(String openTime1) {
		this.openTime1 = openTime1;
	}

	public String getCloseTime1() {
		return closeTime1;
	}

	public void setCloseTime1(String closeTime1) {
		this.closeTime1 = closeTime1;
	}

	public String getOpenTime2() {
		return openTime2;
	}

	public void setOpenTime2(String openTime2) {
		this.openTime2 = openTime2;
	}

	public String getCloseTime2() {
		return closeTime2;
	}

	public void setCloseTime2(String closeTime2) {
		this.closeTime2 = closeTime2;
	}

	public String getOpenTime3() {
		return openTime3;
	}

	public void setOpenTime3(String openTime3) {
		this.openTime3 = openTime3;
	}

	public String getCloseTime3() {
		return closeTime3;
	}

	public void setCloseTime3(String closeTime3) {
		this.closeTime3 = closeTime3;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Restaurant() {
	}

}
