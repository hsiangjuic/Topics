package com.ispan.hotel.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Hotel")
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "h_id")
	private Integer hId;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "tel")
	private String tel;

	@Column(name = "fax")
	private String fax;

	@Column(name = "checkin_time")
	private String checkinTime;

	@Column(name = "checkout_time")
	private String checkoutTime;

	@Column(name = "contact_email")
	private String contactEmail;

	@Column(name = "introduction")
	private String introduction;

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


//	@OneToMany(mappedBy = "hotel", cascade = { CascadeType.PERSIST, CascadeType.REFRESH },fetch = FetchType.LAZY)
//	private List<HotelPhoto> hotelPhotos;
//
//	@OneToMany(mappedBy = "hotel", cascade = { CascadeType.PERSIST, CascadeType.REFRESH },fetch = FetchType.LAZY)
//	private List<RoomType> roomTypes;
//
//	@OneToMany(mappedBy = "hotel", cascade = { CascadeType.PERSIST, CascadeType.REFRESH },fetch = FetchType.LAZY)
//	private List<BookingDetail> bookingDetails;
//
//	@OneToMany(mappedBy = "hotel", cascade = { CascadeType.PERSIST, CascadeType.REFRESH },fetch = FetchType.LAZY)
//	private List<DiscountDetail> discountDetails;
//
////連結LatestNews
//	@OneToMany(mappedBy = "hotel", cascade = { CascadeType.PERSIST, CascadeType.REFRESH },fetch = FetchType.LAZY)
//	private List<LatestNews> latestNews;
//
//	// 連結Dept
//	@OneToMany(mappedBy = "hotel", cascade = { CascadeType.PERSIST, CascadeType.REFRESH },fetch = FetchType.LAZY)
//	private List<Dept> depts;
//
//	// 連結Restaurant
//	@OneToMany(mappedBy = "hotel", cascade = { CascadeType.PERSIST, CascadeType.REFRESH },fetch = FetchType.LAZY)
//	private List<Restaurant> Restaurant;


	public Hotel(Integer hId, String name, String address, String tel, String fax, String checkinTime,
			String checkoutTime, String contactEmail, String introduction, LocalDateTime lastModifiedDate,
			String lastModifiedEmp) {
		super();
		this.hId = hId;
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.fax = fax;
		this.checkinTime = checkinTime;
		this.checkoutTime = checkoutTime;
		this.contactEmail = contactEmail;
		this.introduction = introduction;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;

	}

	public Integer gethId() {
		return hId;
	}

	public void sethId(Integer hId) {
		this.hId = hId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(String checkinTime) {
		this.checkinTime = checkinTime;
	}

	public String getCheckoutTime() {
		return checkoutTime;
	}

	public void setCheckoutTime(String checkoutTime) {
		this.checkoutTime = checkoutTime;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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


//	public List<HotelPhoto> getHotelPhotos() {
//		return hotelPhotos;
//	}
//
//	public void setHotelPhotos(List<HotelPhoto> hotelPhotos) {
//		this.hotelPhotos = hotelPhotos;
//	}
//
//	public List<RoomType> getRoomTypes() {
//		return roomTypes;
//	}
//
//	public void setRoomTypes(List<RoomType> roomTypes) {
//		this.roomTypes = roomTypes;
//	}

//
//	public List<BookingDetail> getBookingDetails() {
//		return bookingDetails;
//	}
//
//	public void setBookingDetails(List<BookingDetail> bookingDetails) {
//		this.bookingDetails = bookingDetails;
//	}
//
//	public List<DiscountDetail> getDiscountDetails() {
//		return discountDetails;
//	}
//
//	public void setDiscountDetails(List<DiscountDetail> discountDetails) {
//		this.discountDetails = discountDetails;
//	}
//
//	public List<LatestNews> getLatestNews() {
//		return latestNews;
//	}
//
//	public void setLatestNews(List<LatestNews> latestNews) {
//		this.latestNews = latestNews;
//	}
//
//	public List<Dept> getDepts() {
//		return depts;
//	}
//
//	public void setDepts(List<Dept> depts) {
//		this.depts = depts;
//	}


	public Hotel() {
	}

}
