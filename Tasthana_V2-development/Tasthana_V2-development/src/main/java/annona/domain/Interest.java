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
@Table(name = "Interest")
@XmlRootElement

public class Interest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long depositId;
	
	private Double interestRate;
	
	private Date interestDate;

	private Double interestAmount;
	
	private Double fixedInterest;
	
	private Double variableInterest;
	
	private String financialYear;
	
	private Integer isCompounded;
	
	private Integer isAdjusted;
	
	private Integer adjustmentInterestId;
	
	private String adjustmentReason;  // Withdraw/Tenure Change/Regular-RecurringDeposit/Recurring-RegularDeposit (when any adjustment happened)
	
	private Integer unscheduledInterestReason;  // Withdraw/Tenure Change/Regular-RecurringDeposit/Recurring-RegularDeposit (if interest calculated middle of the month)
	
	private Integer isCalculated;

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

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Date getInterestDate() {
		return interestDate;
	}

	public void setInterestDate(Date interestDate) {
		this.interestDate = interestDate;
	}
	
	public Double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(Double interestAmount) {
		this.interestAmount = interestAmount;
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

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public Integer getIsCompounded() {
		return isCompounded;
	}

	public void setIsCompounded(Integer isCompounded) {
		this.isCompounded = isCompounded;
	}

	public Integer getIsAdjusted() {
		return isAdjusted;
	}

	public void setIsAdjusted(Integer isAdjusted) {
		this.isAdjusted = isAdjusted;
	}

	public Integer getAdjustmentInterestId() {
		return adjustmentInterestId;
	}

	public void setAdjustmentInterestId(Integer adjustmentInterestId) {
		this.adjustmentInterestId = adjustmentInterestId;
	}

	public String getAdjustmentReason() {
		return adjustmentReason;
	}

	public void setAdjustmentReason(String adjustmentReason) {
		this.adjustmentReason = adjustmentReason;
	}

	public Integer getUnscheduledInterestReason() {
		return unscheduledInterestReason;
	}

	public void setUnscheduledInterestReason(Integer unscheduledInterestReason) {
		this.unscheduledInterestReason = unscheduledInterestReason;
	}

	public Integer getIsCalculated() {
		return isCalculated;
	}

	public void setIsCalculated(Integer isCalculated) {
		this.isCalculated = isCalculated;
	}
	
}
