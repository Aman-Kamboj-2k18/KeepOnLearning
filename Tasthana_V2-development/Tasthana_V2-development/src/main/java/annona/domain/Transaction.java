package annona.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Transaction {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String transactionId;

	private String transactionType;

	private String transactionStatus;

	private String customerID;

	private String accountNo;

	private Double accountBalance;
	
	private float fdInterest;
	
	private Double fdAmount;
	
	private Double fdFixed;
	
	private Double fdChangeable;

	private Double debited;

	private Double credited;

	private Date AccountDate;
	
	private float interestAmount;
	
	private Date fdDeductDate;
	
	private Date fdTenureDate;
	
	private float rateOfInterest;
	
	private String depositType;
	
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

	public float getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(float interestAmount) {
		this.interestAmount = interestAmount;
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

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Double getDebited() {
		return debited;
	}

	public void setDebited(Double debited) {
		this.debited = debited;
	}

	public Double getCredited() {
		return credited;
	}

	public void setCredited(Double credited) {
		this.credited = credited;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Date getAccountDate() {
		return AccountDate;
	}

	public void setAccountDate(Date accountDate) {
		AccountDate = accountDate;
	}

	public Double getFdAmount() {
		return fdAmount;
	}

	public void setFdAmount(Double fdAmount) {
		this.fdAmount = fdAmount;
	}

	public float getFdInterest() {
		return fdInterest;
	}

	public void setFdInterest(float fdInterest) {
		this.fdInterest = fdInterest;
	}

	public float getRateOfInterest() {
		return rateOfInterest;
	}

	public void setRateOfInterest(float rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	public Double getFdFixed() {
		return fdFixed;
	}

	public void setFdFixed(Double fdFixed) {
		this.fdFixed = fdFixed;
	}

	public Double getFdChangeable() {
		return fdChangeable;
	}

	public void setFdChangeable(Double fdChangeable) {
		this.fdChangeable = fdChangeable;
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}
	

}
