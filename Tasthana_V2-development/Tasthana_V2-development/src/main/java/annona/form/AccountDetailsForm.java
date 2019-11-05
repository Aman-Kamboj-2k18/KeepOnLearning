package annona.form;

import org.springframework.stereotype.Component;

@Component
public class AccountDetailsForm {

	private Long id;

	private Long customerID;

	private String accountNo;

	private String accountType;

	private Double accountBalance;
	
	private Double accountBalance2;

	private Integer isActive;
	
	private Integer isSweepDepositRequired;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Double getAccountBalance2() {
		return accountBalance2;
	}

	public void setAccountBalance2(Double accountBalance2) {
		this.accountBalance2 = accountBalance2;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Integer getIsSweepDepositRequired() {
		return isSweepDepositRequired;
	}

	public void setIsSweepDepositRequired(Integer isSweepDepositRequired) {
		this.isSweepDepositRequired = isSweepDepositRequired;
	}

}
