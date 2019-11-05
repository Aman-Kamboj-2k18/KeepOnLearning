package annona.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Distribution {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long depositId;
	
	private Long depositHolderId;

	private Long actionId;
	
	private Date distributionDate;
	
	private Double depositedAmt;
	
	private Double compundDepositAmt;
	
	private Double totalBalance;
	
	private Double fixedAmt;
	
	private Double variableAmt;
	
	private Double compoundFixedAmt; // This will be calculated at the time of interest calculation
	
	private Double compoundVariableAmt; // This will be calculated at the time of interest calculation
	
	private Double fixedInterest;

	private Double variableInterest;
	
	private Double balanceFixedInterest;

	private Double balanceVariableInterest;
	
	private Double payOffAmt;    
	
	private String payOffStatus; // Pending/Done
	
	private Integer daysToCalcInterest;
	
	private String action; // Payment/Withdraw/Interest
	
	private Integer interestAdjusted; // update with 1 if interest is adjusted
	
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
	
	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public Date getDistributionDate() {
		return distributionDate;
	}

	public void setDistributionDate(Date distributionDate) {
		this.distributionDate = distributionDate;
	}

	public Double getDepositedAmt() {
		return depositedAmt;
	}

	public void setDepositedAmt(Double depositedAmt) {
		this.depositedAmt = depositedAmt;
	}

	public Double getCompundDepositAmt() {
		return compundDepositAmt;
	}

	public void setCompundDepositAmt(Double compundDepositAmt) {
		this.compundDepositAmt = compundDepositAmt;
	}

	public Double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
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
	
	public Double getCompoundFixedAmt() {
		return compoundFixedAmt;
	}

	public void setCompoundFixedAmt(Double compoundFixedAmt) {
		this.compoundFixedAmt = compoundFixedAmt;
	}

	public Double getCompoundVariableAmt() {
		return compoundVariableAmt;
	}

	public void setCompoundVariableAmt(Double compoundVariableAmt) {
		this.compoundVariableAmt = compoundVariableAmt;
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

	public Double getPayOffAmt() {
		return payOffAmt;
	}

	public void setPayOffAmt(Double payOffAmt) {
		this.payOffAmt = payOffAmt;
	}

	public String getPayOffStatus() {
		return payOffStatus;
	}

	public void setPayOffStatus(String payOffStatus) {
		this.payOffStatus = payOffStatus;
	}

	public Integer getDaysToCalcInterest() {
		return daysToCalcInterest;
	}

	public void setDaysToCalcInterest(Integer daysToCalcInterest) {
		this.daysToCalcInterest = daysToCalcInterest;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getInterestAdjusted() {
		return interestAdjusted;
	}

	public void setInterestAdjusted(Integer interestAdjusted) {
		this.interestAdjusted = interestAdjusted;
	}
}
