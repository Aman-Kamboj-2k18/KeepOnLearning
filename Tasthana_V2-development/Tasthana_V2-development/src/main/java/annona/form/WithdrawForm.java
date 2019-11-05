package annona.form;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import annona.domain.AccountDetails;
@Component
public class WithdrawForm {
	  private Long id;
	  private Long depositId;
	  private Long distributionId;
	  private Long depositHolderId ;
	  private Double withdrawAmount;
	  private String modeOfPayment;
	  private String chequeDDNumber;
	  private Date chequeDDdate;
	  private String chequeDDBank;
	  private String Bank;
	  private String branch;
	  private String accountNumber;
	  private Long customerId;
	  private String customerName;
	  private Date dateOfBirth;
	  private String email;
	  private String address;
	  private String contactNum;
	  private Double compoundVariableAmt;
	  private Double compoundFixedAmt;
	  private Double accountBalance;
	  private String fdPayOffType;
	  private String fdPayType;
	  private String otherPayTransfer1;
	  private String otherName1;
      private String otherBank1;
      private String otherAccount1;
      private String otherIFSC1;
	  private String linkedAccountNo;
	  private String accountNo;
	  private String accountType;
	  
	  private List<AccountDetails> accountList;
	  private String paymentMadeByHolderIds;
	public String getPaymentMadeByHolderIds() {
		return paymentMadeByHolderIds;
	}
	public void setPaymentMadeByHolderIds(String paymentMadeByHolderIds) {
		this.paymentMadeByHolderIds = paymentMadeByHolderIds;
	}
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
	public Long getDistributionId() {
		return distributionId;
	}
	public void setDistributionId(Long distributionId) {
		this.distributionId = distributionId;
	}
	public Long getDepositHolderId() {
		return depositHolderId;
	}
	public void setDepositHolderId(Long depositHolderId) {
		this.depositHolderId = depositHolderId;
	}
	public Double getWithdrawAmount() {
		return withdrawAmount;
	}
	public void setWithdrawAmount(Double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	public String getChequeDDNumber() {
		return chequeDDNumber;
	}
	public void setChequeDDNumber(String chequeDDNumber) {
		this.chequeDDNumber = chequeDDNumber;
	}
	public Date getChequeDDdate() {
		return chequeDDdate;
	}
	public void setChequeDDdate(Date chequeDDdate) {
		this.chequeDDdate = chequeDDdate;
	}
	public String getChequeDDBank() {
		return chequeDDBank;
	}
	public void setChequeDDBank(String chequeDDBank) {
		this.chequeDDBank = chequeDDBank;
	}
	public String getBank() {
		return Bank;
	}
	public void setBank(String bank) {
		Bank = bank;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNum() {
		return contactNum;
	}
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
	public Double getCompoundVariableAmt() {
		return compoundVariableAmt;
	}
	public void setCompoundVariableAmt(Double compoundVariableAmt) {
		this.compoundVariableAmt = compoundVariableAmt;
	}
	public Double getCompoundFixedAmt() {
		return compoundFixedAmt;
	}
	public void setCompoundFixedAmt(Double compoundFixedAmt) {
		this.compoundFixedAmt = compoundFixedAmt;
	}
	public Double getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getFdPayOffType() {
		return fdPayOffType;
	}
	public void setFdPayOffType(String fdPayOffType) {
		this.fdPayOffType = fdPayOffType;
	}
	public String getFdPayType() {
		return fdPayType;
	}
	public void setFdPayType(String fdPayType) {
		this.fdPayType = fdPayType;
	}
	public String getOtherPayTransfer1() {
		return otherPayTransfer1;
	}
	public void setOtherPayTransfer1(String otherPayTransfer1) {
		this.otherPayTransfer1 = otherPayTransfer1;
	}
	public String getOtherName1() {
		return otherName1;
	}
	public void setOtherName1(String otherName1) {
		this.otherName1 = otherName1;
	}
	public String getOtherBank1() {
		return otherBank1;
	}
	public void setOtherBank1(String otherBank1) {
		this.otherBank1 = otherBank1;
	}
	public String getOtherAccount1() {
		return otherAccount1;
	}
	public void setOtherAccount1(String otherAccount1) {
		this.otherAccount1 = otherAccount1;
	}
	public String getOtherIFSC1() {
		return otherIFSC1;
	}
	public void setOtherIFSC1(String otherIFSC1) {
		this.otherIFSC1 = otherIFSC1;
	}
	public String getLinkedAccountNo() {
		return linkedAccountNo;
	}
	public void setLinkedAccountNo(String linkedAccountNo) {
		this.linkedAccountNo = linkedAccountNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public List<AccountDetails> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<AccountDetails> accountList) {
		this.accountList = accountList;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	
	  
}
