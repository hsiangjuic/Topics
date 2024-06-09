package com.ispan.hotel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "BookingPayment")
public class BookingPayment {

//	bp_id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bp_id")
	private Integer bpId;
	
//	b_id
	@ManyToOne
	@JoinColumn(name="b_id", referencedColumnName = "b_id" , nullable=false)
	private Booking booking;
	
//	type name
	@Column(name = "type_name")
	private String typeName;
	
//	amount payable 
	@Column(name = "amount_payable")
	private Integer amountPayable;
	
//	amount paid
	@Column(name = "amount_paid")
	private Integer amountPaid;
	
//	expire date
	@Column(name = "expire_date")
	private LocalDate expireDate;
	
//	payment date
	@Column(name = "payment_date")
	private LocalDate paymentDate;
	
//	payment method
	@Column(name = "payment_method")
	private String paymentMethod;
	
//	remark
	@Column(name = "remark")
	private String remark;
	
//	points
	@Column(name = "points")
	private Integer points;
	
//	create date
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "create_date")
	private LocalDate createDate;
	
//	invoice number
	@Column(name = "invoice_number")
	private String invoiceNumber;
	
//	last modified date
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Column(name = "last_modified_date")
	private LocalDateTime lastModifiedDate;
	
	@PrePersist // 當物件狀態要轉移到 Persistent 狀態以前，先執行這個方法
	public void onCreate() {
		if (lastModifiedDate == null) {
			lastModifiedDate = LocalDateTime.now();
		}
		if (createDate == null) {
			createDate = LocalDate.now();
		}
	}

//	last modified emp
	@Column(name = "last_modified_emp")
	private String lastModifiedEmp;

//	last modified text
	@Column(name = "last_modified_text")
	private String lastModifiedText;
	
	@OneToMany(mappedBy="bookingPayment", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
	private Set<UseDiscount> useDiscounts;


	@Override
	public String toString() {
		return "BookingPayment [bpId=" + bpId + ", booking=" + booking + ", typeName=" + typeName + ", amountPayable="
				+ amountPayable + ", amountPaid=" + amountPaid + ", expireDate=" + expireDate + ", paymentDate="
				+ paymentDate + ", paymentMethod=" + paymentMethod + ", remark=" + remark + ", points=" + points
				+ ", createDate=" + createDate + ", invoiceNumber=" + invoiceNumber + ", lastModifiedDate="
				+ lastModifiedDate + ", lastModifiedEmp=" + lastModifiedEmp + ", lastModifiedText=" + lastModifiedText
				+ "]";
	}


	public BookingPayment(Integer bpId, Booking booking, String typeName, Integer amountPayable, Integer amountPaid,
			LocalDate expireDate, LocalDate paymentDate, String paymentMethod, String remark, Integer points,
			LocalDate createDate, String invoiceNumber, LocalDateTime lastModifiedDate, String lastModifiedEmp,
			String lastModifiedText, Set<UseDiscount> useDiscounts) {
		super();
		this.bpId = bpId;
		this.booking = booking;
		this.typeName = typeName;
		this.amountPayable = amountPayable;
		this.amountPaid = amountPaid;
		this.expireDate = expireDate;
		this.paymentDate = paymentDate;
		this.paymentMethod = paymentMethod;
		this.remark = remark;
		this.points = points;
		this.createDate = createDate;
		this.invoiceNumber = invoiceNumber;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
	}


	public Integer getBpId() {
		return bpId;
	}


	public void setBpId(Integer bpId) {
		this.bpId = bpId;
	}


	public Booking getBooking() {
		return booking;
	}


	public void setBooking(Booking booking) {
		this.booking = booking;
	}


	public String getTypeName() {
		return typeName;
	}


	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public Integer getAmountPayable() {
		return amountPayable;
	}


	public void setAmountPayable(Integer amountPayable) {
		this.amountPayable = amountPayable;
	}


	public Integer getAmountPaid() {
		return amountPaid;
	}


	public void setAmountPaid(Integer amountPaid) {
		this.amountPaid = amountPaid;
	}


	public LocalDate getExpireDate() {
		return expireDate;
	}


	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}


	public LocalDate getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}


	public String getPaymentMethod() {
		return paymentMethod;
	}


	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Integer getPoints() {
		return points;
	}


	public void setPoints(Integer points) {
		this.points = points;
	}


	public LocalDate getCreateDate() {
		return createDate;
	}


	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}


	public String getInvoiceNumber() {
		return invoiceNumber;
	}


	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
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


//	public Set<UseDiscount> getUseDiscounts() {
//		return useDiscounts;
//	}
//
//
//	public void setUseDiscounts(Set<UseDiscount> useDiscounts) {
//		this.useDiscounts = useDiscounts;
//	}


	public BookingPayment() {
		super();
	}

}
