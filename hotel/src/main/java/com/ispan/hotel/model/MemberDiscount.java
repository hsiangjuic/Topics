package com.ispan.hotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "MemberDiscount") // 連結MemberDiscountDetail(會員優惠)和MemberRank(會員等級)的中間表
public class MemberDiscount {
	@Id
	@Column(name = "md_id")
	private Integer mdId;

	@ManyToOne
	@JoinColumn(name = "mr_id", referencedColumnName = "mr_id")
	private MemberRank memberRank;

	@ManyToOne
	@JoinColumn(name = "mdd_id", referencedColumnName = "mdd_id")
	private MemberDiscountDetail memberDiscountDetail;

	public Integer getMdId() {
		return mdId;
	}

	public void setMdId(Integer mdId) {
		this.mdId = mdId;
	}

	public MemberRank getMemberRank() {
		return memberRank;
	}

	public void setMemberRank(MemberRank memberRank) {
		this.memberRank = memberRank;
	}

	public MemberDiscountDetail getMemberDiscountDetail() {
		return memberDiscountDetail;
	}

	public void setMemberDiscountDetail(MemberDiscountDetail memberDiscountDetail) {
		this.memberDiscountDetail = memberDiscountDetail;
	}

	public MemberDiscount(Integer mdId, MemberRank memberRank, MemberDiscountDetail memberDiscountDetail) {
		this.mdId = mdId;
		this.memberRank = memberRank;
		this.memberDiscountDetail = memberDiscountDetail;
	}

	public MemberDiscount() {

	}

}
