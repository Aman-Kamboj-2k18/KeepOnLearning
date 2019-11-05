package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class OverdraftForm {
	
	private Long id;
	
	
	private String accountNumber;
	
	private double currentBalance;
	
	private Double defaultOverdraftAmount;
	
	private Integer defaultOverdraftPercentage;
	
	private Date issueDate;
	
	private Long depositId;
	
	private Double withdrawAmount;
	
	
	private String customerID;
	
	private String paymentMode;
	
	private String overdraftNumber;
	
	private Integer tenureInDays;
	private Integer tenureInMonths;
	private Integer tenureInYears;
	
	private Float InterestRate;
	
	private Double AmountToReturn;
	
	private Date OverdraftEndDate;
	
	private Double EMIAmount;

	private Integer IsEMI;
	
	private Integer minimumOverdraftPercentage;
	 
	private Integer maximumOverdraftPercentage;
	
	
	
	public Integer getMinimumOverdraftPercentage() {
		return minimumOverdraftPercentage;
	}

	public void setMinimumOverdraftPercentage(Integer minimumOverdraftPercentage) {
		this.minimumOverdraftPercentage = minimumOverdraftPercentage;
	}

	public Integer getMaximumOverdraftPercentage() {
		return maximumOverdraftPercentage;
	}

	public void setMaximumOverdraftPercentage(Integer maximumOverdraftPercentage) {
		this.maximumOverdraftPercentage = maximumOverdraftPercentage;
	}

	public Integer getIsEMI() {
		return IsEMI;
	}

	public void setIsEMI(Integer isEMI) {
		IsEMI = isEMI;
	}
	
	public Float getInterestRate() {
		return InterestRate;
	}

	public void setInterestRate(Float interestRate) {
		InterestRate = interestRate;
	}

	public Double getAmountToReturn() {
		return AmountToReturn;
	}

	public void setAmountToReturn(Double amountToReturn) {
		AmountToReturn = amountToReturn;
	}

	public Date getOverdraftEndDate() {
		return OverdraftEndDate;
	}

	public void setOverdraftEndDate(Date overdraftEndDate) {
		OverdraftEndDate = overdraftEndDate;
	}

	public Double getEMIAmount() {
		return EMIAmount;
	}

	public void setEMIAmount(Double eMIAmount) {
		EMIAmount = eMIAmount;
	}

	public Integer getTenureInDays() {
		return tenureInDays;
	}

	public void setTenureInDays(Integer tenureInDays) {
		this.tenureInDays = tenureInDays;
	}

	public Integer getTenureInMonths() {
		return tenureInMonths;
	}

	public void setTenureInMonths(Integer tenureInMonths) {
		this.tenureInMonths = tenureInMonths;
	}

	public Integer getTenureInYears() {
		return tenureInYears;
	}

	public void setTenureInYears(Integer tenureInYears) {
		this.tenureInYears = tenureInYears;
	}


	public String getOverdraftNumber() {
		return overdraftNumber;
	}

	public void setOverdraftNumber(String overdraftNumber) {
		this.overdraftNumber = overdraftNumber;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public Double getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(Double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	

	public Double getDefaultOverdraftAmount() {
		return defaultOverdraftAmount;
	}

	public void setDefaultOverdraftAmount(Double defaultOverdraftAmount) {
		this.defaultOverdraftAmount = defaultOverdraftAmount;
	}

	public Integer getDefaultOverdraftPercentage() {
		return defaultOverdraftPercentage;
	}

	public void setDefaultOverdraftPercentage(Integer defaultOverdraftPercentage) {
		this.defaultOverdraftPercentage = defaultOverdraftPercentage;
	}
	
	

}
