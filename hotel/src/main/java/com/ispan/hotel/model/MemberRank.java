package com.ispan.hotel.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "MemberRank")
public class MemberRank {
	// mr_id
	@Id
	@Column(name = "mr_id")
	private Integer mrId;

	// name
	@Column(name = "name")
	private String name;

	// needed points
	@Column(name = "needed_points")
	private Integer neededPoints;

	// needed booking days
	@Column(name = "needed_booking_days")
	private Integer neededBookingDays;

	// gain points
	@Column(name = "gain_points")
	private Integer gainPoints;

	@Column(name = "cardPath")
	private String cardPath;

	@JsonIgnore
	@Lob
	private byte[] photoFile;

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

	@PreUpdate
	public void onUpdate() {
		lastModifiedDate = LocalDateTime.now();
	}

	// last modified emp
	@Column(name = "last_modified_emp")
	private String lastModifiedEmp;

	// last modified text
	@Column(name = "last_modified_text")
	private String lastModifiedText;

//	@OneToMany(mappedBy = "memberRank", cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
//	private List<Customer> customers;
//
//	@OneToMany(mappedBy = "memberRank", cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
//	private List<MemberDiscount> memberDiscounts;

	public MemberRank(Integer mrId, String name, Integer neededPoints, Integer neededBookingDays, Integer gainPoints,
			String cardPath, LocalDateTime lastModifiedDate, String lastModifiedEmp, String lastModifiedText) {
		super();
		this.mrId = mrId;
		this.name = name;
		this.neededPoints = neededPoints;
		this.neededBookingDays = neededBookingDays;
		this.gainPoints = gainPoints;
		this.cardPath = cardPath;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
	}

	public Integer getMrId() {
		return mrId;
	}

	public void setMrId(Integer mrId) {
		this.mrId = mrId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNeededPoints() {
		return neededPoints;
	}

	public void setNeededPoints(Integer neededPoints) {
		this.neededPoints = neededPoints;
	}

	public Integer getNeededBookingDays() {
		return neededBookingDays;
	}

	public void setNeededBookingDays(Integer neededBookingDays) {
		this.neededBookingDays = neededBookingDays;
	}

	public Integer getGainPoints() {
		return gainPoints;
	}

	public void setGainPoints(Integer gainPoints) {
		this.gainPoints = gainPoints;
	}

	public String getCardPath() {
		return cardPath;
	}

	public void setCardPath(String cardPath) {
		this.cardPath = cardPath;
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

//	public List<Customer> getCustomers() {
//		return customers;
//	}
//
//	public void setCustomers(List<Customer> customers) {
//		this.customers = customers;
//	}
//
//	public List<MemberDiscount> getMemberDiscounts() {
//		return memberDiscounts;
//	}
//
//	public void setMemberDiscounts(List<MemberDiscount> memberDiscounts) {
//		this.memberDiscounts = memberDiscounts;
//	}

	public byte[] getPhotoFile() {
		return photoFile;
	}

	public void setPhotoFile(byte[] photoFile) {
		this.photoFile = photoFile;
	}

	public MemberRank() {
	}
}
