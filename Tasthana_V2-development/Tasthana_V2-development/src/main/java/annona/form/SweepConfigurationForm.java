package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class SweepConfigurationForm {

	private Long id;

	private Long accountID;

	private Long customerID;

	private String tenureType; // Year || Month - User Input

	private String tenure; // 1 yr || 15 months - User Input

	private Integer tenureDays; // 5 days - User Input

	private Integer tenureInDays; // actual tenure in days. We will calculate the interest on that value

	private Float interestRate;

	private Date modifiedDate;

	private Integer isSweepDepositRequired;

	private Double minimumSavingBalanceForSweepIn;

	private Double minimumAmountRequiredForSweepIn;

	public Double getMinimumSavingBalanceForSweepIn() {
		return minimumSavingBalanceForSweepIn;
	}

	public void setMinimumSavingBalanceForSweepIn(Double minimumSavingBalanceForSweepIn) {
		this.minimumSavingBalanceForSweepIn = minimumSavingBalanceForSweepIn;
	}

	public Double getMinimumAmountRequiredForSweepIn() {
		return minimumAmountRequiredForSweepIn;
	}

	public void setMinimumAmountRequiredForSweepIn(Double minimumAmountRequiredForSweepIn) {
		this.minimumAmountRequiredForSweepIn = minimumAmountRequiredForSweepIn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountID() {
		return accountID;
	}

	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getTenureType() {
		return tenureType;
	}

	public void setTenureType(String tenureType) {
		this.tenureType = tenureType;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public Integer getTenureDays() {
		return tenureDays;
	}

	public void setTenureDays(Integer tenureDays) {
		this.tenureDays = tenureDays;
	}

	public Integer getTenureInDays() {
		return tenureInDays;
	}

	public void setTenureInDays(Integer tenureInDays) {
		this.tenureInDays = tenureInDays;
	}

	public Float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getIsSweepDepositRequired() {
		return isSweepDepositRequired;
	}

	public void setIsSweepDepositRequired(Integer isSweepDepositRequired) {
		this.isSweepDepositRequired = isSweepDepositRequired;
	}

}
