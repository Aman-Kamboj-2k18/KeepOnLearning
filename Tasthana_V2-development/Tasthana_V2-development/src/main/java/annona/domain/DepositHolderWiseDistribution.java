package annona.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table
@XmlRootElement
public class DepositHolderWiseDistribution {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id;
	
	private Long depositId;
	
	private Long depositHolderId;
	
	private Long customerId;
	
	private Date distributionDate;
	
	private String action;
	
	private Long actionId;
	
	//private Double actionAmount;
	
	private Float contribution;
	
	private Double fixedAmt;
	
	private Double variableAmt;
	
	private Double fixedInterest;
	
	private Double variableInterest;
	
	private Double fixedCompoundAmount;
	
	private Double variableCompoundAmount;
	
	private Double payoffAmount;
	
	private Double tdsAmount;
	
//	private Double fixedInhandInterestForCoumpounding;
//	
//	private Double variableInhandInterestForCoumpounding;
	
	private Double balanceFixedInterest;

	private Double balanceVariableInterest;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	public Long getDepositHolderId() {
		return depositHolderId;
	}

	public void setDepositHolderId(Long depositHolderId) {
		this.depositHolderId = depositHolderId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getDistributionDate() {
		return distributionDate;
	}

	public void setDistributionDate(Date distributionDate) {
		this.distributionDate = distributionDate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

//	public Double getActionAmount() {
//		return actionAmount;
//	}
//
//	public void setActionAmount(Double actionAmount) {
//		this.actionAmount = actionAmount;
//	}

	public Float getContribution() {
		return contribution;
	}

	public void setContribution(Float contribution) {
		this.contribution = contribution;
	}

	public Double getFixedAmt() {
		return fixedAmt;
	}

	public void setFixedAmt(Double fixedAmt) {
		this.fixedAmt = fixedAmt;
	}

	public Double getVariableAmt() {
		return variableAmt;
	}

	public void setVariableAmt(Double variableAmt) {
		this.variableAmt = variableAmt;
	}

	public Double getFixedInterest() {
		return fixedInterest;
	}

	public void setFixedInterest(Double fixedInterest) {
		this.fixedInterest = fixedInterest;
	}

	public Double getVariableInterest() {
		return variableInterest;
	}

	public void setVariableInterest(Double variableInterest) {
		this.variableInterest = variableInterest;
	}

	public Double getFixedCompoundAmount() {
		return fixedCompoundAmount;
	}

	public void setFixedCompoundAmount(Double fixedCompoundAmount) {
		this.fixedCompoundAmount = fixedCompoundAmount;
	}

	public Double getVariableCompoundAmount() {
		return variableCompoundAmount;
	}

	public void setVariableCompoundAmount(Double variableCompoundAmount) {
		this.variableCompoundAmount = variableCompoundAmount;
	}

	public Double getPayoffAmount() {
		return payoffAmount;
	}

	public void setPayoffAmount(Double payoffAmount) {
		this.payoffAmount = payoffAmount;
	}

	public Double getTdsAmount() {
		return tdsAmount;
	}

	public void setTdsAmount(Double tdsAmount) {
		this.tdsAmount = tdsAmount;
	}

	public Double getBalanceFixedInterest() {
		return balanceFixedInterest;
	}

	public void setBalanceFixedInterest(Double balanceFixedInterest) {
		this.balanceFixedInterest = balanceFixedInterest;
	}

	public Double getBalanceVariableInterest() {
		return balanceVariableInterest;
	}

	public void setBalanceVariableInterest(Double balanceVariableInterest) {
		this.balanceVariableInterest = balanceVariableInterest;
	}

	
//	public Double getFixedInhandInterestForCoumpounding() {
//		return fixedInhandInterestForCoumpounding;
//	}
//
//	public void setFixedInhandInterestForCoumpounding(Double fixedInhandInterestForCoumpounding) {
//		this.fixedInhandInterestForCoumpounding = fixedInhandInterestForCoumpounding;
//	}
//
//	public Double getVariableInhandInterestForCoumpounding() {
//		return variableInhandInterestForCoumpounding;
//	}
//
//	public void setVariableInhandInterestForCoumpounding(Double variableInhandInterestForCoumpounding) {
//		this.variableInhandInterestForCoumpounding = variableInhandInterestForCoumpounding;
//	}
}
