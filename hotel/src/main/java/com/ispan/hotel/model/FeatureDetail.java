package com.ispan.hotel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "feature_detail")
public class FeatureDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rtfd_id")
    private Integer id;

    @Column(name = "feature_item_detail1")
    private String featureItemDetail1;

    @Column(name = "feature_item_detail2")
    private String featureItemDetail2;

    @Column(name = "feature_item_detail3")
    private String featureItemDetail3;
    
    @Column(name = "feature_item_detail4")
    private String featureItemDetail4;
    
    @Column(name = "feature_item_detail5")
    private String featureItemDetail5;
    
    @Column(name = "feature_item_detail6")
    private String featureItemDetail6;
    
    @Column(name = "feature_item_detail7")
    private String featureItemDetail7;
    
    @Column(name = "feature_item_detail8")
    private String featureItemDetail8;
    
    @Column(name = "feature_item_detail9")
    private String featureItemDetail9;
    
    @Column(name = "feature_item_detail10")
    private String featureItemDetail10;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rtf_id", referencedColumnName = "rtf_id") //要加上 referencedColumnName = "rtf_id"
    @JsonIgnore
    private Feature feature;

    public FeatureDetail(String featureItemDetail1, String featureItemDetail2, String featureItemDetail3, String featureItemDetail4, String featureItemDetail5, String featureItemDetail6, String featureItemDetail7, String featureItemDetail8, String featureItemDetail9, String featureItemDetail10,Feature feature) {
        this.featureItemDetail1 = featureItemDetail1;
        this.featureItemDetail2 = featureItemDetail2;
        this.featureItemDetail3 = featureItemDetail3;
        this.featureItemDetail4 = featureItemDetail4;
        this.featureItemDetail5 = featureItemDetail5;
        this.featureItemDetail6 = featureItemDetail6;
        this.featureItemDetail7 = featureItemDetail7;
        this.featureItemDetail8 = featureItemDetail8;
        this.featureItemDetail9 = featureItemDetail9;
        this.featureItemDetail10 = featureItemDetail10;
        
        this.feature = feature;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFeatureItemDetail1() {
		return featureItemDetail1;
	}

	public void setFeatureItemDetail1(String featureItemDetail1) {
		this.featureItemDetail1 = featureItemDetail1;
	}

	public String getFeatureItemDetail2() {
		return featureItemDetail2;
	}

	public void setFeatureItemDetail2(String featureItemDetail2) {
		this.featureItemDetail2 = featureItemDetail2;
	}

	public String getFeatureItemDetail3() {
		return featureItemDetail3;
	}

	public void setFeatureItemDetail3(String featureItemDetail3) {
		this.featureItemDetail3 = featureItemDetail3;
	}

	public String getFeatureItemDetail4() {
		return featureItemDetail4;
	}

	public void setFeatureItemDetail4(String featureItemDetail4) {
		this.featureItemDetail4 = featureItemDetail4;
	}

	public String getFeatureItemDetail5() {
		return featureItemDetail5;
	}

	public void setFeatureItemDetail5(String featureItemDetail5) {
		this.featureItemDetail5 = featureItemDetail5;
	}

	public String getFeatureItemDetail6() {
		return featureItemDetail6;
	}

	public void setFeatureItemDetail6(String featureItemDetail6) {
		this.featureItemDetail6 = featureItemDetail6;
	}

	public String getFeatureItemDetail7() {
		return featureItemDetail7;
	}

	public void setFeatureItemDetail7(String featureItemDetail7) {
		this.featureItemDetail7 = featureItemDetail7;
	}

	public String getFeatureItemDetail8() {
		return featureItemDetail8;
	}

	public void setFeatureItemDetail8(String featureItemDetail8) {
		this.featureItemDetail8 = featureItemDetail8;
	}

	public String getFeatureItemDetail9() {
		return featureItemDetail9;
	}

	public void setFeatureItemDetail9(String featureItemDetail9) {
		this.featureItemDetail9 = featureItemDetail9;
	}

	public String getFeatureItemDetail10() {
		return featureItemDetail10;
	}

	public void setFeatureItemDetail10(String featureItemDetail10) {
		this.featureItemDetail10 = featureItemDetail10;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	@Override
	public String toString() {
		return "FeatureDetail [id=" + id + ", featureItemDetail1=" + featureItemDetail1 + ", featureItemDetail2="
				+ featureItemDetail2 + ", featureItemDetail3=" + featureItemDetail3 + ", featureItemDetail4="
				+ featureItemDetail4 + ", featureItemDetail5=" + featureItemDetail5 + ", featureItemDetail6="
				+ featureItemDetail6 + ", featureItemDetail7=" + featureItemDetail7 + ", featureItemDetail8="
				+ featureItemDetail8 + ", featureItemDetail9=" + featureItemDetail9 + ", featureItemDetail10="
				+ featureItemDetail10 + ", feature=" + feature + "]";
	}

    
	
	
	
    
}
