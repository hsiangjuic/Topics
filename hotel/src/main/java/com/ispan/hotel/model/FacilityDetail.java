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
@Table(name = "facility_detail")
public class FacilityDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fid_id")
    private Integer id;

    @Column(name = "facility_item_detail1")
    private String facilityItemDetail1;

    @Column(name = "facility_item_detail2")
    private String facilityItemDetail2;
    
    @Column(name = "facility_item_detail3")
    private String facilityItemDetail3;
    
    @Column(name = "facility_item_detail4")
    private String facilityItemDetail4;
    
    @Column(name = "facility_item_detail5")
    private String facilityItemDetail5;
    
    @Column(name = "facility_item_detail6")
    private String facilityItemDetail6;
    
    @Column(name = "facility_item_detail7")
    private String facilityItemDetail7;
    
    @Column(name = "facility_item_detail8")
    private String facilityItemDetail8;
    
    @Column(name = "facility_item_detail9")
    private String facilityItemDetail9;
    
    @Column(name = "facility_item_detail10")
    private String facilityItemDetail10;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rfac_id", referencedColumnName = "rfac_id")
    private Facility facility;

    public FacilityDetail(String facilityItemDetail1, String facilityItemDetail2, String facilityItemDetail3, String facilityItemDetail4, String facilityItemDetail5,String facilityItemDetail6, String facilityItemDetail7, String facilityItemDetail8, String facilityItemDetail9,String facilityItemDetail10, Facility facility) {
        this.facilityItemDetail1 = facilityItemDetail1;
        this.facilityItemDetail2 = facilityItemDetail2;
        this.facilityItemDetail3 = facilityItemDetail3;
        this.facilityItemDetail4 = facilityItemDetail4;
        this.facilityItemDetail5 = facilityItemDetail5;
        this.facilityItemDetail6 = facilityItemDetail6;
        this.facilityItemDetail7 = facilityItemDetail7;
        this.facilityItemDetail8 = facilityItemDetail8;
        this.facilityItemDetail9 = facilityItemDetail9;
        this.facilityItemDetail10 = facilityItemDetail10;
        this.facility = facility;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFacilityItemDetail1() {
		return facilityItemDetail1;
	}

	public void setFacilityItemDetail1(String facilityItemDetail1) {
		this.facilityItemDetail1 = facilityItemDetail1;
	}

	public String getFacilityItemDetail2() {
		return facilityItemDetail2;
	}

	public void setFacilityItemDetail2(String facilityItemDetail2) {
		this.facilityItemDetail2 = facilityItemDetail2;
	}

	public String getFacilityItemDetail3() {
		return facilityItemDetail3;
	}

	public void setFacilityItemDetail3(String facilityItemDetail3) {
		this.facilityItemDetail3 = facilityItemDetail3;
	}

	public String getFacilityItemDetail4() {
		return facilityItemDetail4;
	}

	public void setFacilityItemDetail4(String facilityItemDetail4) {
		this.facilityItemDetail4 = facilityItemDetail4;
	}

	public String getFacilityItemDetail5() {
		return facilityItemDetail5;
	}

	public void setFacilityItemDetail5(String facilityItemDetail5) {
		this.facilityItemDetail5 = facilityItemDetail5;
	}

	public String getFacilityItemDetail6() {
		return facilityItemDetail6;
	}

	public void setFacilityItemDetail6(String facilityItemDetail6) {
		this.facilityItemDetail6 = facilityItemDetail6;
	}

	public String getFacilityItemDetail7() {
		return facilityItemDetail7;
	}

	public void setFacilityItemDetail7(String facilityItemDetail7) {
		this.facilityItemDetail7 = facilityItemDetail7;
	}

	public String getFacilityItemDetail8() {
		return facilityItemDetail8;
	}

	public void setFacilityItemDetail8(String facilityItemDetail8) {
		this.facilityItemDetail8 = facilityItemDetail8;
	}

	public String getFacilityItemDetail9() {
		return facilityItemDetail9;
	}

	public void setFacilityItemDetail9(String facilityItemDetail9) {
		this.facilityItemDetail9 = facilityItemDetail9;
	}

	public String getFacilityItemDetail10() {
		return facilityItemDetail10;
	}

	public void setFacilityItemDetail10(String facilityItemDetail10) {
		this.facilityItemDetail10 = facilityItemDetail10;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	@Override
	public String toString() {
		return "FacilityDetail [id=" + id + ", facilityItemDetail1=" + facilityItemDetail1 + ", facilityItemDetail2="
				+ facilityItemDetail2 + ", facilityItemDetail3=" + facilityItemDetail3 + ", facilityItemDetail4="
				+ facilityItemDetail4 + ", facilityItemDetail5=" + facilityItemDetail5 + ", facilityItemDetail6="
				+ facilityItemDetail6 + ", facilityItemDetail7=" + facilityItemDetail7 + ", facilityItemDetail8="
				+ facilityItemDetail8 + ", facilityItemDetail9=" + facilityItemDetail9 + ", facilityItemDetail10="
				+ facilityItemDetail10 + ", facility=" + facility + "]";
	}
	
    
    
}
