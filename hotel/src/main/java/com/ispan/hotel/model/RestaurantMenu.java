package com.ispan.hotel.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "RestaurantMenu")
public class RestaurantMenu {

//	rem_id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rem_id")
	private Integer remId;

//	@OneToMany(mappedBy = "restaurantMenu", cascade = { CascadeType.PERSIST, CascadeType.REFRESH })
//	private List<RestaurantCustomer> restaurantCustomer;

//	menu name
	@Column(name = "menu_name")
	private String menuName;

//	price
	@Column(name = "price")
	private Integer price;

//	start date
	@Column(name = "start_date")
	private LocalDate startDate;

//	end date
	@Column(name = "end_date")
	private LocalDate endDate;

	public RestaurantMenu(Integer remId, String menuName, Integer price,
			LocalDate startDate, LocalDate endDate) {
		super();
		this.remId = remId;
		this.menuName = menuName;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Integer getRemId() {
		return remId;
	}

	public void setRemId(Integer remId) {
		this.remId = remId;
	}

//	public List<RestaurantCustomer> getRestaurantCustomer() {
//		return restaurantCustomer;
//	}
//
//	public void setRestaurantCustomer(List<RestaurantCustomer> restaurantCustomer) {
//		this.restaurantCustomer = restaurantCustomer;
//	}

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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public RestaurantMenu() {
	}

}
