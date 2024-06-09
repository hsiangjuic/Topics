package com.ispan.hotel.model;

import java.time.LocalDate;
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
@Table(name = "RestaurantBooking")
public class RestaurantBooking {

//	reb_id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rebId")
	private Integer rebId;

//	isCustomer(T/F)
	@Column(name = "isCustomer")
	private boolean isCustomer;

//	total adult
	@Column(name = "total_adult")
	private Integer totalAdult;

//	total children
	@Column(name = "total_children")
	private Integer totalChildren;

//	total table
	@Column(name = "total_table")
	private Integer totalTable;

//	reserve time
	@Column(name = "reserve_time")
	private LocalDate reserveTime;
	
//	meal time
	@Column(name = "meal_time")
	private String mealTime;

//	first name
	@Column(name = "first_name")
	private String firstName;

//	last name
	@Column(name = "last_name")
	private String lastName;

//	cellphone
	@Column(name = "cellphone")
	private String cellphone;

//	email
	@Column(name = "email")
	private String email;

//	re_id
	@ManyToOne
	@JoinColumn(name = "re_id", referencedColumnName = "re_id", nullable = false)
	private Restaurant restaurant;

//	remark
	@Column(name = "remark")
	private String remark;

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

	public RestaurantBooking(Integer rebId, boolean isCustomer, Integer totalAdult, Integer totalChildren,
			Integer totalTable, LocalDate reserveTime, String firstName, String lastName, String cellphone,
			String email, Restaurant restaurant, String remark, LocalDateTime lastModifiedDate, String lastModifiedEmp,
			String lastModifiedText, String mealTime) {
		super();
		this.rebId = rebId;
		this.isCustomer = isCustomer;
		this.totalAdult = totalAdult;
		this.totalChildren = totalChildren;
		this.totalTable = totalTable;
		this.reserveTime = reserveTime;
		this.mealTime = mealTime;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cellphone = cellphone;
		this.email = email;
		this.restaurant = restaurant;
		this.remark = remark;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
	}

	public Integer getRebId() {
		return rebId;
	}

	public void setRebId(Integer rebId) {
		this.rebId = rebId;
	}

	public boolean getIsCustomer() {
		return isCustomer;
	}

	public void setIsCustomer(boolean isCustomer) {
		this.isCustomer = isCustomer;
	}

	public Integer getTotalAdult() {
		return totalAdult;
	}

	public void setTotalAdult(Integer totalAdult) {
		this.totalAdult = totalAdult;
	}

	public Integer getTotalChildren() {
		return totalChildren;
	}

	public void setTotalChildren(Integer totalChildren) {
		this.totalChildren = totalChildren;
	}

	public Integer getTotalTable() {
		return totalTable;
	}

	public void setTotalTable(Integer totalTable) {
		this.totalTable = totalTable;
	}

	public LocalDate getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(LocalDate reserveTime) {
		this.reserveTime = reserveTime;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getMealTime() {
		return mealTime;
	}

	public void setMealTime(String mealTime) {
		this.mealTime = mealTime;
	}

	public RestaurantBooking() {

	}

	@Override
	public String toString() {
		return "RestaurantBooking [rebId=" + rebId + ", isCustomer=" + isCustomer + ", totalAdult=" + totalAdult
				+ ", totalChildren=" + totalChildren + ", totalTable=" + totalTable + ", reserveTime=" + reserveTime
				+ ", mealTime=" + mealTime + ", firstName=" + firstName + ", lastName=" + lastName + ", cellphone="
				+ cellphone + ", email=" + email + ", restaurant=" + restaurant + ", remark=" + remark
				+ ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedEmp=" + lastModifiedEmp
				+ ", lastModifiedText=" + lastModifiedText + "]";
	}

	
	

}
