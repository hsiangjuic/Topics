package com.ispan.hotel.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;



import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="room_type")
public class RoomType {

//	rt_id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rt_id")
	private Integer rtId;
	
//	h_id
	@ManyToOne
	@JoinColumn(name="h_id", referencedColumnName = "h_id", nullable=false)
	private Hotel hotel;
	
//	room name
	@Column(name = "room_name")
	private String roomName;
	
	@Column(name = "feature_tittle")
	private String featureTittle;
	
	@Column(name = "feature_tittle_content")
	private String featureTittleContent;
	
//	room amount
	@Column(name = "room_amount")
	private Integer roomAmount;
	
//	bed number
	@Column(name = "bed_number")
	private Integer bedNumber;
	
//	bed type(1,2)
	@Column(name = "bed_type")
	private String bedType;
	
//	flexible price 
	@Column(name = "flexible_price")
	private Integer flexiblePrice;
	
//	member price 
	@Column(name = "member_price")
	private Integer memberPrice;
	
//	pet(T/F)
	@Column(name = "pet")
	private String pet;
	
//	allow add bed 
	@Column(name = "allow_add_bed")
	private String allowAddBed;
	
//	accessible room(T/F)
	@Column(name = "accessible_room")
	private String accessibleRoom;
	
//	people max amount 
	@Column(name = "people_max_amount ")
	private Integer peopleMaxAmount;
	
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
	

//	@OneToMany(mappedBy="roomType", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
//	@JsonIgnore
//	private Set<Room> rooms;
	
//	@OneToMany(mappedBy="roomType", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
//	private Set<Discount> discounts;
	
//	@OneToMany(mappedBy="roomType", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
//	private Set<RoomPhoto> roomPhotos;

	
	// 跟 Feature 做單向多對多, 中間表使用 RoomHilight
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name="RoomHilight", joinColumns= {@JoinColumn(name="rt_id")}, inverseJoinColumns = {@JoinColumn(name="rtf_id")})  
    private List<Feature> feature;
	
	// 跟 Facility 做單向多對多, 中間表使用 RoomEquipment
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name="RoomEquipment", joinColumns= {@JoinColumn(name="rt_id")}, inverseJoinColumns = {@JoinColumn(name="rfac_id")})  
    private List<Facility> facility;
	
	
	
	public RoomType() {}
	
	public RoomType(String roomName, String featureTittle, String featureTittleContent, Integer roomAmount,
			Integer bedNumber, String bedType, Integer flexiblePrice, Integer memberPrice, String pet,
			String allowAddBed, String accessibleRoom, Integer peopleMaxAmount, LocalDateTime lastModifiedDate,
			String lastModifiedEmp, String lastModifiedText) {
		super();
		this.roomName = roomName;
		this.featureTittle = featureTittle;
		this.featureTittleContent = featureTittleContent;
		this.roomAmount = roomAmount;
		this.bedNumber = bedNumber;
		this.bedType = bedType;
		this.flexiblePrice = flexiblePrice;
		this.memberPrice = memberPrice;
		this.pet = pet;
		this.allowAddBed = allowAddBed;
		this.accessibleRoom = accessibleRoom;
		this.peopleMaxAmount = peopleMaxAmount;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
	}

	


	public RoomType(String roomName, String featureTittle, String featureTittleContent, Integer roomAmount,
			Integer bedNumber, String bedType, Integer flexiblePrice, Integer memberPrice, String pet,
			String allowAddBed, String accessibleRoom, Integer peopleMaxAmount, LocalDateTime lastModifiedDate,
			String lastModifiedEmp, String lastModifiedText, Hotel hotel) {
		super();
		this.hotel = hotel;
		this.roomName = roomName;
		this.featureTittle = featureTittle;
		this.featureTittleContent = featureTittleContent;
		this.roomAmount = roomAmount;
		this.bedNumber = bedNumber;
		this.bedType = bedType;
		this.flexiblePrice = flexiblePrice;
		this.memberPrice = memberPrice;
		this.pet = pet;
		this.allowAddBed = allowAddBed;
		this.accessibleRoom = accessibleRoom;
		this.peopleMaxAmount = peopleMaxAmount;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
	}

	public RoomType(Integer rtId, String roomName, String featureTittle, String featureTittleContent,
			Integer roomAmount, Integer bedNumber, String bedType, Integer flexiblePrice, Integer memberPrice,
			String pet, String allowAddBed, String accessibleRoom, Integer peopleMaxAmount,
			LocalDateTime lastModifiedDate, String lastModifiedEmp, String lastModifiedText) {
		super();
		this.rtId = rtId;
		this.roomName = roomName;
		this.featureTittle = featureTittle;
		this.featureTittleContent = featureTittleContent;
		this.roomAmount = roomAmount;
		this.bedNumber = bedNumber;
		this.bedType = bedType;
		this.flexiblePrice = flexiblePrice;
		this.memberPrice = memberPrice;
		this.pet = pet;
		this.allowAddBed = allowAddBed;
		this.accessibleRoom = accessibleRoom;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
	}




	public RoomType(Integer rtId, Hotel hotel, String roomName, String featureTittle, String featureTittleContent,
			Integer roomAmount, Integer bedNumber, String bedType, Integer flexiblePrice, Integer memberPrice,
			String pet, String allowAddBed, String accessibleRoom, Integer peopleMaxAmount,
			LocalDateTime lastModifiedDate, String lastModifiedEmp, String lastModifiedText, List<Feature> feature, List<Facility> facility) {
		super();
		this.rtId = rtId;
		this.hotel = hotel;
		this.roomName = roomName;
		this.featureTittle = featureTittle;
		this.featureTittleContent = featureTittleContent;
		this.roomAmount = roomAmount;
		this.bedNumber = bedNumber;
		this.bedType = bedType;
		this.flexiblePrice = flexiblePrice;
		this.memberPrice = memberPrice;
		this.pet = pet;
		this.allowAddBed = allowAddBed;
		this.accessibleRoom = accessibleRoom;
		this.peopleMaxAmount = peopleMaxAmount;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
		
		this.feature = feature;
		this.facility = facility;
	}





//	public Set<RoomPhoto> getRoomPhotos() {
//		return roomPhotos;
//	}
//
//	public void setRoomPhotos(Set<RoomPhoto> roomPhotos) {
//		this.roomPhotos = roomPhotos;
//	}


	public Integer getRtId() {
		return rtId;
	}

	public void setRtId(Integer rtId) {
		this.rtId = rtId;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getFeatureTittle() {
		return featureTittle;
	}

	public void setFeatureTittle(String featureTittle) {
		this.featureTittle = featureTittle;
	}

	public String getFeatureTittleContent() {
		return featureTittleContent;
	}

	public void setFeatureTittleContent(String featureTittleContent) {
		this.featureTittleContent = featureTittleContent;
	}

	public Integer getRoomAmount() {
		return roomAmount;
	}

	public void setRoomAmount(Integer roomAmount) {
		this.roomAmount = roomAmount;
	}

	public Integer getBedNumber() {
		return bedNumber;
	}

	public void setBedNumber(Integer bedNumber) {
		this.bedNumber = bedNumber;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public Integer getFlexiblePrice() {
		return flexiblePrice;
	}

	public void setFlexiblePrice(Integer flexiblePrice) {
		this.flexiblePrice = flexiblePrice;
	}

	public Integer getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(Integer memberPrice) {
		this.memberPrice = memberPrice;
	}

	public String getPet() {
		return pet;
	}

	public void setPet(String pet) {
		this.pet = pet;
	}

	public String getAllowAddBed() {
		return allowAddBed;
	}

	public void setAllowAddBed(String allowAddBed) {
		this.allowAddBed = allowAddBed;
	}

	public String getAccessibleRoom() {
		return accessibleRoom;
	}

	public void setAccessibleRoom(String accessibleRoom) {
		this.accessibleRoom = accessibleRoom;
	}

	public Integer getPeopleMaxAmount() {
		return peopleMaxAmount;
	}

	public void setPeopleMaxAmount(Integer peopleMaxAmount) {
		this.peopleMaxAmount = peopleMaxAmount;
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


//	public Set<Room> getRooms() {
//		return rooms;
//	}
//
//	public void setRooms(Set<Room> rooms) {
//		this.rooms = rooms;
//	}

//	public Set<Discount> getDiscounts() {
//		return discounts;
//	}
//
//	public void setDiscounts(Set<Discount> discounts) {
//		this.discounts = discounts;
//	}


	public List<Feature> getFeature() {
		return feature;
	}

	public void setFeature(List<Feature> feature) {
		this.feature = feature;
	}

	public List<Facility> getFacility() {
		return facility;
	}

	public void setFacility(List<Facility> facility) {
		this.facility = facility;
	}




	@Override
	public String toString() {
		return "RoomType [rtId=" + rtId + ", hotel=" + hotel + ", roomName=" + roomName + ", featureTittle="
				+ featureTittle + ", featureTittleContent=" + featureTittleContent + ", roomAmount=" + roomAmount
				+ ", bedNumber=" + bedNumber + ", bedType=" + bedType + ", flexiblePrice=" + flexiblePrice
				+ ", memberPrice=" + memberPrice + ", pet=" + pet + ", allowAddBed=" + allowAddBed + ", accessibleRoom="
				+ accessibleRoom + ", peopleMaxAmount=" + peopleMaxAmount + ", lastModifiedDate=" + lastModifiedDate
				+ ", lastModifiedEmp=" + lastModifiedEmp + ", lastModifiedText=" + lastModifiedText + ", feature="
				+ feature + ", facility=" + facility + "]";
	}

	

	
	


	

}
