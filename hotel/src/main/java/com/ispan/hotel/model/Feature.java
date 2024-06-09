package com.ispan.hotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rtf_id")
    private Integer id;

    @Column(name = "feature_item")
    private String featureItem;
    
//  @OneToMany(mappedBy="feature", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY) //加了cascade才會儲存關聯表的物件, onetomany 的 fetch 默認值就是 lazy 按住 ctrl 點 OneToMany 進去看
//  private List<FeatureDetail> FeatureDetail;
    
    public Feature() {}
    
    public Feature(String featureItem) {
        this.featureItem = featureItem;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFeatureItem() {
		return featureItem;
	}

	public void setFeatureItem(String featureItem) {
		this.featureItem = featureItem;
	}

	@Override
	public String toString() {
		return "Feature [id=" + id + ", featureItem=" + featureItem + "]";
	}

//	public List<FeatureDetail> getFeatureDetail() {
//		return FeatureDetail;
//	}
//
//	public void setFeatureDetail(List<FeatureDetail> featureDetail) {
//		FeatureDetail = featureDetail;
//	}

	
    
	
    
    
}
