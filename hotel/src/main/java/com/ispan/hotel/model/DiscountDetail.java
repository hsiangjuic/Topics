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
@Table(name="DiscountDetail")
public class DiscountDetail {
//	dd_id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dd_id")
	private Integer ddId;
	
//	h_id
	@ManyToOne
	@JoinColumn(name="h_id", referencedColumnName = "h_id", nullable=false)
	private Hotel hotel;
	
//	promo code
	@Column(name="promo_code")
	private String promoCode;
	
//	begin date 
	@Column(name="begin_date")
	private LocalDate beginDate;
	
//	last date
	@Column(name="last_date")
	private LocalDate lastDate;
	
//	discount rate
	@Column(name="discount_rate")
	private Integer discountRate;
	
//	discount price
	@Column(name="discount_price")
	private Integer discountPrice;
	
//	name
	@Column(name="name")
	private String name;
	
//	status
	@Column(name="status")
	private String status;
	
//	remark
	@Column(name="remark")
	private String remark;
	
//	max times
	@Column(name="max_times")
	private Integer maxTimes;
	
//	discount type
	@Column(name="discount_type")
	private String discountType;
	
//	member only(T/F)
	@Column(name="member_only")
	private Boolean memberOnly;
	
//	member only(T/F)
	@Column(name="id_verification")
	private Boolean idVerification;
	
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
	private Integer lastModifiedText;
	
	@OneToMany(mappedBy="discountDetail", cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.REMOVE}, fetch = FetchType.LAZY)
	private Set<Discount> discounts;
	
	@OneToMany(mappedBy="discountDetail", cascade = {CascadeType.PERSIST, CascadeType.REFRESH,CascadeType.REMOVE}, fetch = FetchType.LAZY)
	private Set<UseDiscount> useDiscounts;
	

	@Override
	public String toString() {
		return "DiscountDetail [ddId=" + ddId + ", hotel=" + hotel + ", promoCode=" + promoCode + ", beginDate="
				+ beginDate + ", lastDate=" + lastDate + ", discountRate=" + discountRate + ", discountPrice="
				+ discountPrice + ", name=" + name + ", status=" + status + ", remark=" + remark + ", maxTimes="
				+ maxTimes + ", discountType=" + discountType + ", memberOnly=" + memberOnly + ", idVerification="
				+ idVerification + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedEmp=" + lastModifiedEmp
				+ ", lastModifiedText=" + lastModifiedText + "]";
	}


	public DiscountDetail(Integer ddId, Hotel hotel, String promoCode, LocalDate beginDate, LocalDate lastDate,
			Integer discountRate, Integer discountPrice, String name, String status, String remark, Integer maxTimes,
			String discountType, Boolean memberOnly, Boolean idVerification, LocalDateTime lastModifiedDate,
			String lastModifiedEmp, Integer lastModifiedText) {
		super();
		this.ddId = ddId;
		this.hotel = hotel;
		this.promoCode = promoCode;
		this.beginDate = beginDate;
		this.lastDate = lastDate;
		this.discountRate = discountRate;
		this.discountPrice = discountPrice;
		this.name = name;
		this.status = status;
		this.remark = remark;
		this.maxTimes = maxTimes;
		this.discountType = discountType;
		this.memberOnly = memberOnly;
		this.idVerification = idVerification;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
	}


	public Integer getDdId() {
		return ddId;
	}


	public void setDdId(Integer ddId) {
		this.ddId = ddId;
	}


	public Hotel getHotel() {
		return hotel;
	}


	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}


	public String getPromoCode() {
		return promoCode;
	}


	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
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


	public Integer getDiscountRate() {
		return discountRate;
	}


	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}


	public Integer getDiscountPrice() {
		return discountPrice;
	}


	public void setDiscountPrice(Integer discountPrice) {
		this.discountPrice = discountPrice;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Integer getMaxTimes() {
		return maxTimes;
	}


	public void setMaxTimes(Integer maxTimes) {
		this.maxTimes = maxTimes;
	}


	public String getDiscountType() {
		return discountType;
	}


	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}


	public Boolean getMemberOnly() {
		return memberOnly;
	}


	public void setMemberOnly(Boolean memberOnly) {
		this.memberOnly = memberOnly;
	}


	public Boolean getIdVerification() {
		return idVerification;
	}


	public void setIdVerification(Boolean idVerification) {
		this.idVerification = idVerification;
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


	public Integer getLastModifiedText() {
		return lastModifiedText;
	}


	public void setLastModifiedText(Integer lastModifiedText) {
		this.lastModifiedText = lastModifiedText;
	}



	public DiscountDetail() {
		super();
	}

}
