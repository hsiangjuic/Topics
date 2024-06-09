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
@Table(name = "RestaurantCustomer")
public class RestaurantCustomer {

//	rec_id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rec_id")
	private Integer recId;
//	reserve time
@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
@Column(name = "reserve_time")
private LocalDateTime reserveTime;

//	pick time
@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
@Column(name = "pick_time")
private LocalDateTime pickTime;

//	rem_id
	@ManyToOne
	@JoinColumn(name = "rem_id", nullable = false)
	private RestaurantMenu restaurantMenu;

//	menu name
	@Column(name = "menu_name")
	private String menuName;

//	price
	@Column(name = "price")
	private Integer price;

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
	private Integer lastModifiedText;

	public RestaurantCustomer(Integer recId, LocalDateTime reserveTime, LocalDateTime pickTime, RestaurantMenu restaurantMenu,
			String menuName, Integer price, String firstName, String lastName, String cellphone, String email,
			String remark, LocalDateTime lastModifiedDate, String lastModifiedEmp, Integer lastModifiedText) {
		super();
		this.recId = recId;
		this.reserveTime = reserveTime;
		this.pickTime = pickTime;
		this.restaurantMenu = restaurantMenu;
		this.menuName = menuName;
		this.price = price;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cellphone = cellphone;
		this.email = email;
		this.remark = remark;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
	}

	public Integer getRecId() {
		return recId;
	}

	public void setRecId(Integer recId) {
		this.recId = recId;
	}

	public LocalDateTime getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(LocalDateTime reserveTime) {
		this.reserveTime = reserveTime;
	}

	public LocalDateTime getPickTime() {
		return pickTime;
	}

	public void setPickTime(LocalDateTime pickTime) {
		this.pickTime = pickTime;
	}

	public RestaurantMenu getRestaurantMenu() {
		return restaurantMenu;
	}

	public void setRestaurantMenu(RestaurantMenu restaurantMenu) {
		this.restaurantMenu = restaurantMenu;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
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

	public Integer getLastModifiedText() {
		return lastModifiedText;
	}

	public void setLastModifiedText(Integer lastModifiedText) {
		this.lastModifiedText = lastModifiedText;
	}

	public RestaurantCustomer() {
	}

}
