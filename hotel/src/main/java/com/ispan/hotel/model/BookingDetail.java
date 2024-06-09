package com.ispan.hotel.model;

import java.time.LocalDateTime;
import java.util.Set;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "BookingDetail")
public class BookingDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bd_id")
	private Integer bdId;

	@ManyToOne
	@JoinColumn(name = "h_id", referencedColumnName = "h_id", nullable = false)
	private Hotel hotel;

	// c_id
	@ManyToOne
	@JoinColumn(name = "c_id", referencedColumnName = "c_id", nullable = false)
	private Customer customer;

	// customer remark
	@Column(name = "customer_remark")
	private String customerRemark;

	// child number
	@Column(name = "child_number")
	private Integer childNumber;

	// infant utility(T/F)
	@Column(name = "infant_utility")
	private Boolean infantUtility;

	// adult number
	@Column(name = "adult_number")
	private Integer adultNumber;

	// pet number
	@Column(name = "pet_number")
	private Integer petNumber;

	// payment status
	@Column(name = "payment_status")
	private String paymentStatus;

	// arrival time
	@Column(name = "arrival_time")
	private String arrivalTime;
	
//	total room
	@Column(name = "total_room")
	private Integer totalRoom;

	// last modified date
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
	
//	order number
	@Column(name = "order_number")
	private String orderNumber;

	// last modified emp
	@Column(name = "last_modified_emp")
	private String lastModifiedEmp;

	// last modified text
	@Column(name = "last_modified_text")
	private String lastModifiedText;

	@OneToMany(mappedBy = "bookingDetail", cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private Set<Booking> bookings;
	
	

	@Override
	public String toString() {
		return "BookingDetail [bdId=" + bdId + ", hotel=" + hotel + ", customer=" + customer + ", customerRemark="
				+ customerRemark + ", childNumber=" + childNumber + ", infantUtility=" + infantUtility
				+ ", adultNumber=" + adultNumber + ", petNumber=" + petNumber + ", paymentStatus=" + paymentStatus
				+ ", arrivalTime=" + arrivalTime + ", totalRoom=" + totalRoom + ", lastModifiedDate=" + lastModifiedDate
				+ ", lastModifiedEmp=" + lastModifiedEmp + ", lastModifiedText=" + lastModifiedText + "]";
	}



	public BookingDetail(Integer bdId, Hotel hotel, Customer customer, String customerRemark, Integer childNumber,
			Boolean infantUtility, Integer adultNumber, Integer petNumber, String paymentStatus, String arrivalTime,
			Integer totalRoom, LocalDateTime lastModifiedDate, String orderNumber, String lastModifiedEmp,
			String lastModifiedText) {
		super();
		this.bdId = bdId;
		this.hotel = hotel;
		this.customer = customer;
		this.customerRemark = customerRemark;
		this.childNumber = childNumber;
		this.infantUtility = infantUtility;
		this.adultNumber = adultNumber;
		this.petNumber = petNumber;
		this.paymentStatus = paymentStatus;
		this.arrivalTime = arrivalTime;
		this.totalRoom = totalRoom;
		this.lastModifiedDate = lastModifiedDate;
		this.orderNumber = orderNumber;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
	}

	public Integer getBdId() {
		return bdId;
	}

	public void setBdId(Integer bdId) {
		this.bdId = bdId;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCustomerRemark() {
		return customerRemark;
	}

	public void setCustomerRemark(String customerRemark) {
		this.customerRemark = customerRemark;
	}

	public Integer getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(Integer childNumber) {
		this.childNumber = childNumber;
	}

	public Boolean getInfantUtility() {
		return infantUtility;
	}

	public void setInfantUtility(Boolean infantUtility) {
		this.infantUtility = infantUtility;
	}

	public Integer getAdultNumber() {
		return adultNumber;
	}

	public void setAdultNumber(Integer adultNumber) {
		this.adultNumber = adultNumber;
	}

	public Integer getPetNumber() {
		return petNumber;
	}

	public void setPetNumber(Integer petNumber) {
		this.petNumber = petNumber;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	public String getPaymentStatus() {
		return paymentStatus;
	}



	public String getArrivalTime() {
		return arrivalTime;
	}




	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}




	public Integer getTotalRoom() {
		return totalRoom;
	}

	public void setTotalRoom(Integer totalRoom) {
		this.totalRoom = totalRoom;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}




	public String getOrderNumber() {
		return orderNumber;
	}




	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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
//	public void setBookings(Set<Booking> bookings) {
//		this.bookings = bookings;
//	}

	public BookingDetail() {
		super();
	}

}
