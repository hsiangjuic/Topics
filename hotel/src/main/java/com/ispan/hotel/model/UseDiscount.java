package com.ispan.hotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "UseDiscount")
public class UseDiscount {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ud_id")
	private Integer udId;
	
	@ManyToOne
	@JoinColumn(name="dd_id", referencedColumnName = "dd_id", nullable=false)
	private DiscountDetail discountDetail;
	
	@ManyToOne
	@JoinColumn(name="bp_id", referencedColumnName = "bp_id", nullable=false)
	private BookingPayment bookingPayment;
	
	

	public UseDiscount(Integer udId, DiscountDetail discountDetail, BookingPayment bookingPayment) {
		super();
		this.udId = udId;
		this.discountDetail = discountDetail;
		this.bookingPayment = bookingPayment;
	}



	public Integer getUdId() {
		return udId;
	}



	public void setUdId(Integer udId) {
		this.udId = udId;
	}



	public DiscountDetail getDiscountDetail() {
		return discountDetail;
	}



	public void setDiscountDetail(DiscountDetail discountDetail) {
		this.discountDetail = discountDetail;
	}



	public BookingPayment getBookingPayment() {
		return bookingPayment;
	}



	public void setBookingPayment(BookingPayment bookingPayment) {
		this.bookingPayment = bookingPayment;
	}



	public UseDiscount() {
	}

}
