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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "LatestNews")  //ok
public class LatestNews {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ln_id")
	private Integer lnId;
	
	@ManyToOne
	@JoinColumn(name="h_id", referencedColumnName = "h_id")
	private Hotel hotel;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "end_date")
	private LocalDate endDate;
	
	@Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
	
//	@Column(name = "content")
//	private String content;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "type")
	private String type;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified_date")
	private LocalDateTime lastModifiedDate;
	
	@PrePersist
	public void onCreate() {
		if (lastModifiedDate == null) {
			lastModifiedDate = LocalDateTime.now();
		}
	}
	
	@Column(name = "last_modified_emp")
	private String lastModifiedEmp;

	public LatestNews(Integer lnId, Hotel hotel, String title, LocalDate startDate, LocalDate endDate, String content,
			String image, String status, String type, LocalDateTime lastModifiedDate, String lastModifiedEmp) {
		super();
		this.lnId = lnId;
		this.hotel = hotel;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.content = content;
		this.image = image;
		this.status = status;
		this.type = type;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
	}



	public Integer getLnId() {
		return lnId;
	}



	public void setLnId(Integer lnId) {
		this.lnId = lnId;
	}



	public Hotel getHotel() {
		return hotel;
	}



	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
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



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
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



	public LatestNews() {
		super();
	}



	@Override
	public String toString() {
		return "LatestNews [lnId=" + lnId + ", hotel=" + hotel + ", title=" + title + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", content=" + content + ", image=" + image + ", status=" + status
				+ ", type=" + type + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedEmp=" + lastModifiedEmp
				+ "]";
	}
	
	

}
