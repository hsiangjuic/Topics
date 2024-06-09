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
@Table(name = "Discount")
public class Discount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="d_id")
	private Integer dId;
	
	@ManyToOne
	@JoinColumn(name="dd_id", referencedColumnName = "dd_id", nullable=false)
	private DiscountDetail discountDetail;
	
	@ManyToOne
	@JoinColumn(name="rt_id", referencedColumnName = "rt_id", nullable=false)
	private RoomType roomType;
	
	
	
	public Discount(Integer dId, DiscountDetail discountDetail, RoomType roomType) {
		super();
		this.dId = dId;
		this.discountDetail = discountDetail;
		this.roomType = roomType;
	}



	public Integer getdId() {
		return dId;
	}



	public void setdId(Integer dId) {
		this.dId = dId;
	}



	public DiscountDetail getDiscountDetail() {
		return discountDetail;
	}



	public void setDiscountDetail(DiscountDetail discountDetail) {
		this.discountDetail = discountDetail;
	}



	public RoomType getRoomType() {
		return roomType;
	}



	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}



	public Discount() {
	}

}
