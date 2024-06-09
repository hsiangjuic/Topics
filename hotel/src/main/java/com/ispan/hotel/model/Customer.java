package com.ispan.hotel.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Customer")
public class Customer {

	// c_id
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "c_id")
	private Integer cId;

	// first name
	@Column(name = "first_name")
	private String firstName;

	// last name
	@Column(name = "last_name")
	private String lastName;

	// id
	@Column(name = "identification", nullable = true)
	private String identification;

	// passport number
	@Column(name = "passport_number", nullable = true)
	private String passportNumber;

	// tel
	@Column(name = "tel")
	private String tel;

	// address
	@Column(name = "address")
	private String address;

	// birthday
	@Column(name = "birthday")
	private LocalDate birthday;

	// gender
	@Column(name = "gender")
	private String gender;

	// country
	@Column(name = "country")
	private String country;

	// email
	@Column(name = "email")
	private String email;

	// remark
	@Column(name = "remark")
	private String remark;

	// titleOfCourtesy
	@Column(name = "titleOfCourtesy")
	private String titleOfCourtesy;

	// allow promotion mail
	@Column(name = "allow_promotion_mail")
	private String allowPromotionMail;

	// mr_id
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "mr_id", referencedColumnName = "mr_id")
	private MemberRank memberRank;

	// username
	@Column(name = "username", nullable = true, unique = true)
	private String username;

	// password
	@Column(name = "password")
	private String password;

	// begin date
	@Column(name = "begin_date")
	private LocalDate beginDate;

	// member status
	@Column(name = "member_status")
	private String memberStatus;

	// total points
	@Column(name = "total_points")
	private Integer totalPoints;
	// 寄驗證郵件OK?
	@Column(name = "verificationToken")
	private String verificationToken;

	// 限制寄驗證信的有效時間
	@Column(name = "verification_token_expiration")
	private LocalDateTime verificationTokenExpiration;
	// 驗證碼
	@Column(name = "verificationCode")
	private String verificationCode;

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public LocalDateTime getVerificationTokenExpiration() {
		return verificationTokenExpiration;
	}

	public void setVerificationTokenExpiration(LocalDateTime verificationTokenExpiration) {
		this.verificationTokenExpiration = verificationTokenExpiration;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	// last modified date
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss EEEE")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified_date")
	private LocalDateTime lastModifiedDate;

	@PrePersist // 當物件狀態要轉移到 Persistent 狀態以前，先執行這個方法
	public void onCreate() {
		if (lastModifiedDate == null && beginDate == null) {
			lastModifiedDate = LocalDateTime.now();
			beginDate = LocalDate.now();
		}
	}

	@PreUpdate
	public void onUpdate() {
		lastModifiedDate = LocalDateTime.now();
	}

	// last modified emp
	@Column(name = "last_modified_emp")
	private String lastModifiedEmp;

	// last modified text
	@Column(name = "last_modified_text")
	private String lastModifiedText;

//	@OneToMany(mappedBy = "customer", cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
//	private List<BookingDetail> bookingDetails;
//
//	// 連結RestaurantPoint
//	@OneToMany(mappedBy = "customer", cascade = { CascadeType.PERSIST, CascadeType.REFRESH }, fetch = FetchType.LAZY)
//	private List<RestaurantPoint> restaurantPoint;

	@Override
	public String toString() {
		return "Customer [cId=" + cId + ", firstName=" + firstName + ", lastName=" + lastName + ", identification="
				+ identification + ", passportNumber=" + passportNumber + ", tel=" + tel + ", address=" + address
				+ ", birthday=" + birthday + ", gender=" + gender + ", country=" + country + ", email=" + email
				+ ", remark=" + remark + ", titleOfCourtesy=" + titleOfCourtesy + ", allowPromotionMail="
				+ allowPromotionMail + ", memberRank=" + memberRank + ", username=" + username + ", password="
				+ password + ", beginDate=" + beginDate + ", memberStatus=" + memberStatus + ", totalPoints="
				+ totalPoints + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedEmp=" + lastModifiedEmp
				+ ", lastModifiedText=" + lastModifiedText + "]";
	}


	public Customer(Integer cId, String firstName, String lastName, String identification, String passportNumber,
			String tel, String address, LocalDate birthday, String gender, String country, String email, String remark,
			String titleOfCourtesy, String allowPromotionMail, MemberRank memberRank, String username, String password,
			LocalDate beginDate, String memberStatus, Integer totalPoints, LocalDateTime lastModifiedDate,
			String lastModifiedEmp, String lastModifiedText) {
		super();
		this.cId = cId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.identification = identification;
		this.passportNumber = passportNumber;
		this.tel = tel;
		this.address = address;
		this.birthday = birthday;
		this.gender = gender;
		this.country = country;
		this.email = email;
		this.remark = remark;
		this.titleOfCourtesy = titleOfCourtesy;
		this.allowPromotionMail = allowPromotionMail;
		this.memberRank = memberRank;
		this.username = username;
		this.password = password;
		this.beginDate = beginDate;
		this.memberStatus = memberStatus;
		this.totalPoints = totalPoints;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedEmp = lastModifiedEmp;
		this.lastModifiedText = lastModifiedText;
	}

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
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

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getTitleOfCourtesy() {
		return titleOfCourtesy;
	}

	public void setTitleOfCourtesy(String titleOfCourtesy) {
		this.titleOfCourtesy = titleOfCourtesy;
	}

	public String getAllowPromotionMail() {
		return allowPromotionMail;
	}

	public void setAllowPromotionMail(String allowPromotionMail) {
		this.allowPromotionMail = allowPromotionMail;
	}

	public MemberRank getMemberRank() {
		return memberRank;
	}

	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
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

//	public List<BookingDetail> getBookingDetails() {
//		return bookingDetails;
//	}
//
//	public void setBookingDetails(List<BookingDetail> bookingDetails) {
//		this.bookingDetails = bookingDetails;
//	}

	public Customer() {
	}
}
