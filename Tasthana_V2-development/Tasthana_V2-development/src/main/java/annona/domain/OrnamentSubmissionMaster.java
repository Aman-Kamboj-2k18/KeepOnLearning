package annona.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class OrnamentSubmissionMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long customerId;
	
	private Date submissionDate;
	
	private Double goldRate;
	
	private Double totalWeight;
	
	private Double totalPrice;
	
	private Integer isDepositCreated;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public Double getGoldRate() {
		return goldRate;
	}

	public void setGoldRate(Double goldRate) {
		this.goldRate = goldRate;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getIsDepositCreated() {
		return isDepositCreated;
	}

	public void setIsDepositCreated(Integer isDepositCreated) {
		this.isDepositCreated = isDepositCreated;
	}

	
	

}
