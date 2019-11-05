package annona.form;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import annona.domain.AccountDetails;
import annona.domain.Deposit;

@Component
public class FundTransferForm {
	
	private Long id;

	private String category;

	private String customerName;

	private String email;

	private String contactNum;

	private String accountNo;

	private String accountType;

	private float accountBalance;

	private String customerID;

	private String fdId;

	private Double fdAmount;
	
	private String payTransfer;

	private Date fdDate;

	private Date fdTenureDate;

	private float interestAmount;
	
    private Date fdMonthly;
    
    private List<AccountDetails> accountList;
    
    private Double transferAmount; 
    
    private Double totalTransferAmount; 
    
    private Double[] multipleTransferAmount; 
    
    List<HolderForm> depositHolderObjList;
    
	public Date getFdMonthly() {
		return fdMonthly;
	}

	public void setFdMonthly(Date fdMonthly) {
		this.fdMonthly = fdMonthly;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
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

	public float getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public Double getFdAmount() {
		return fdAmount;
	}

	public void setFdAmount(Double fdAmount) {
		this.fdAmount = fdAmount;
	}

	public String getPayTransfer() {
		return payTransfer;
	}

	public void setPayTransfer(String payTransfer) {
		this.payTransfer = payTransfer;
	}

	public Date getFdDate() {
		return fdDate;
	}

	public void setFdDate(Date fdDate) {
		this.fdDate = fdDate;
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

	public List<AccountDetails> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<AccountDetails> accountList) {
		this.accountList = accountList;
	}

	public Double getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}

	public Double getTotalTransferAmount() {
		return totalTransferAmount;
	}

	public void setTotalTransferAmount(Double totalTransferAmount) {
		this.totalTransferAmount = totalTransferAmount;
	}

	public Double[] getMultipleTransferAmount() {
		return multipleTransferAmount;
	}

	public void setMultipleTransferAmount(Double[] multipleTransferAmount) {
		this.multipleTransferAmount = multipleTransferAmount;
	}

	public List<HolderForm> getDepositHolderObjList() {
		return depositHolderObjList;
	}

	public void setDepositHolderObjList(List<HolderForm> depositHolderObjList) {
		this.depositHolderObjList = depositHolderObjList;
	}

	
	
	
}
