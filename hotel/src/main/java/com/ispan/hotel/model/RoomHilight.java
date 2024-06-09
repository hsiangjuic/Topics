package com.ispan.hotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "RoomHilight")
public class RoomHilight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rhl_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rt_id", referencedColumnName = "rt_id")
    private RoomType roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rtf_id", referencedColumnName = "rtf_id")
    private Feature feature;

    public RoomHilight() {}
    
    public RoomHilight(RoomType roomType, Feature feature) {
        this.roomType = roomType;
        this.feature = feature;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	@Override
	public String toString() {
		return "RoomHilight [id=" + id + ", roomType=" + roomType + ", feature=" + feature + "]";
	}

    
    
    
}
