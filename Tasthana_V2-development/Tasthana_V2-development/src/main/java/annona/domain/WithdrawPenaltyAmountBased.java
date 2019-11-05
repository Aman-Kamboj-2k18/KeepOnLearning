package annona.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@XmlRootElement
public class WithdrawPenaltyAmountBased {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private Long withdrawPenaltyMasterId;
	    
	private Integer amountFrom;
	
	private Integer amountTo;
	
	private Float penaltyRate;
	
	private Float penaltyFlatAmount;
	
	
	private String createdBy;

	private Date createdDate;
	
	private String modifiedBy;

	private Date modifiedDate;

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWithdrawPenaltyMasterId() {
		return withdrawPenaltyMasterId;
	}

	public void setWithdrawPenaltyMasterId(Long withdrawPenaltyMasterId) {
		this.withdrawPenaltyMasterId = withdrawPenaltyMasterId;
	}

	public Integer getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(Integer amountFrom) {
		this.amountFrom = amountFrom;
	}

	public Integer getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(Integer amountTo) {
		this.amountTo = amountTo;
	}

	public Float getPenaltyRate() {
		return penaltyRate;
	}

	public void setPenaltyRate(Float penaltyRate) {
		this.penaltyRate = penaltyRate;
	}

	public Float getPenaltyFlatAmount() {
		return penaltyFlatAmount;
	}

	public void setPenaltyFlatAmount(Float penaltyFlatAmount) {
		this.penaltyFlatAmount = penaltyFlatAmount;
	}
	
	
	
}
