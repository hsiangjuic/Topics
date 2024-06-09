package com.ispan.hotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "facility")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rfac_id")
    private Integer id;

    @Column(name = "facility_item")
    private String facilityItem;

    public Facility() {}
    public Facility(String facilityItem) {
        this.facilityItem = facilityItem;
    }
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFacilityItem() {
		return facilityItem;
	}
	public void setFacilityItem(String facilityItem) {
		this.facilityItem = facilityItem;
	}
	@Override
	public String toString() {
		return "Facility [id=" + id + ", facilityItem=" + facilityItem + "]";
	}
	
	

    
}
