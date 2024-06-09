package com.ispan.hotel.model;

import java.time.LocalDate;
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
@Table(name = "Booking")
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "b_id")
	private Integer bId;
	
//	bd_id
	@ManyToOne
	@JoinColumn(name="bd_id", referencedColumnName = "bd_id", nullable=false)
	private BookingDetail bookingDetail;
	
//	r_id
	@ManyToOne
	@JoinColumn(name="r_id", referencedColumnName = "r_id", nullable=false)
	private Room room;
	
//	begin date
	@Column(name = "begin_date")
	private LocalDate beginDate;
	
//	last date
	@Column(name = "last_date")
	private LocalDate lastDate;

//	booking status
	@Column(name = "booking_status")
	private String bookingStatus;

//	additional bed
	@Column(name = "additional_bed")
	private Integer additionalBed;

//	breakfast(T/F)
	@Column(name = "breakfast")
	private Boolean breakfast;

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
	
	@OneToMany(mappedBy="booking", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	private Set<BookingPayment> bookingPayments;
	
	
	
	@Override
	public String toString() {
		return "Booking [bId=" + bId + ", bookingDetail=" + bookingDetail + ", room=" + room + ", beginDate="
				+ beginDate + ", lastDate=" + lastDate + ", bookingStatus=" + bookingStatus + ", additionalBed="
				+ additionalBed + ", breakfast=" + breakfast + ", lastModifiedDate=" + lastModifiedDate
				+ ", lastModifiedEmp=" + lastModifiedEmp + ", lastModifiedText=" + lastModifiedText + "]";
	}

	public Booking(Integer bId, BookingDetail bookingDetail, Room room, LocalDate beginDate, LocalDate lastDate,
			String bookingStatus, Integer additionalBed, Boolean breakfast, LocalDateTime lastModifiedDate,
			String lastModifiedEmp, String lastModifiedText) {
		super();
		this.bId = bId;
		this.bookingDetail = bookingDetail;
		this.room = room;
		this.beginDate = beginDate;
		this.lastDate = lastDate;
		this.bookingStatus = bookingStatus;
		this.additionalBed = additionalBed;
		this.breakfast = breakfast;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
	}

	public Integer getbId() {
		return bId;
	}

	public void setbId(Integer bId) {
		this.bId = bId;
	}

	public BookingDetail getBookingDetail() {
		return bookingDetail;
	}

	public void setBookingDetail(BookingDetail bookingDetail) {
		this.bookingDetail = bookingDetail;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDate getLastDate() {
		return lastDate;
	}

	public void setLastDate(LocalDate lastDate) {
		this.lastDate = lastDate;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public Integer getAdditionalBed() {
		return additionalBed;
	}

	public void setAdditionalBed(Integer additionalBed) {
		this.additionalBed = additionalBed;
	}

	public Boolean getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(Boolean breakfast) {
		this.breakfast = breakfast;
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

//	public Set<BookingPayment> getBookingPayments() {
//		return bookingPayments;
//	}
//
//	public void setBookingPayments(Set<BookingPayment> bookingPayments) {
//		this.bookingPayments = bookingPayments;
//	}

	public Booking() {
	}

}
