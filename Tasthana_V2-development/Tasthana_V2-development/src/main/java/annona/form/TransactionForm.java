package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TransactionForm {
	
	private Long id;

	private String transactionId;

	private String transactionType;

	private String transactionStatus;

	private String customerID;

	private String accountNo;

	private float accountBalance;
	
	private float fdAmount;
	
    private float fdFixed;
	
	private float fdChangeable;

	private float debited;

	private float credited;

	private Date AccountDate;
	
	private float interestAmount;
	
    private Date fdDeductDate;
	
	private Date fdTenureDate;
	
	private float rateOfInterest;
	
	public Date getFdDeductDate() {
		return fdDeductDate;
	}

	public void setFdDeductDate(Date fdDeductDate) {
		this.fdDeductDate = fdDeductDate;
	}

	public Date getFdTenureDate() {
		return fdTenureDate;
	}

	public void setFdTenureDate(Date fdTenureDate) {
		this.fdTenureDate = fdTenureDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public float getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}

	public float getFdAmount() {
		return fdAmount;
	}

	public void setFdAmount(float fdAmount) {
		this.fdAmount = fdAmount;
	}

	public float getDebited() {
		return debited;
	}

	public void setDebited(float debited) {
		this.debited = debited;
	}

	public float getCredited() {
		return credited;
	}

	public void setCredited(float credited) {
		this.credited = credited;
	}

	public Date getAccountDate() {
		return AccountDate;
	}

	public void setAccountDate(Date accountDate) {
		AccountDate = accountDate;
	}

	public float getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(float interestAmount) {
		this.interestAmount = interestAmount;
	}

	public float getRateOfInterest() {
		return rateOfInterest;
	}

	public void setRateOfInterest(float rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	public float getFdFixed() {
		return fdFixed;
	}

	public void setFdFixed(float fdFixed) {
		this.fdFixed = fdFixed;
	}

	public float getFdChangeable() {
		return fdChangeable;
	}

	public void setFdChangeable(float fdChangeable) {
		this.fdChangeable = fdChangeable;
	}
	
	

}
