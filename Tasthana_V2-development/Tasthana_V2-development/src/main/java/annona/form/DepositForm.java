package annona.form;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import annona.domain.AccountDetails;

@Component
public class DepositForm {

	@Override
	public String toString() {
		return "DepositForm [depositId=" + depositId + ", depositHolderId=" + depositHolderId + ", accountNumber="
				+ accountNumber + ", customerId=" + customerId + ", customerName=" + customerName + ", address="
				+ address + ", email=" + email + ", contactNum=" + contactNum + ", dateOfBirth=" + dateOfBirth
				+ ", fdPay=" + fdPay + ", paymentMode=" + paymentMode + ", accountType=" + accountType + ", ddDate="
				+ ddDate + ", accountBalance=" + accountBalance + ", ddBank=" + ddBank + ", ddBranch=" + ddBranch
				+ ", chequeNo=" + chequeNo + ", chequeDate=" + chequeDate + ", chequeBank=" + chequeBank
				+ ", chequeBranch=" + chequeBranch + ", fdPayOffType=" + fdPayOffType + ", cardType=" + cardType
				+ ", cardNo=" + cardNo + ", expiryDate=" + expiryDate + ", cvv=" + cvv + ", linkedAccountNo="
				+ linkedAccountNo + ", transferType=" + transferType + ", deathCertificateSubmitted="
				+ deathCertificateSubmitted + ", depositAmount=" + depositAmount + ", approvalStatus=" + approvalStatus
				+ ", documentSubmitted=" + documentSubmitted + ", category=" + category + ", closingDate=" + closingDate
				+ ", depositCategory=" + depositCategory + ", fdPayType=" + fdPayType + ", otherPayTransfer1="
				+ otherPayTransfer1 + ", otherName1=" + otherName1 + ", otherBank1=" + otherBank1 + ", otherBranch1="
				+ otherBranch1 + ", interestPayAmount1=" + interestPayAmount1 + ", otherIFSC1=" + otherIFSC1
				+ ", otherAccount1=" + otherAccount1 + ", withdrawAmount=" + withdrawAmount + ", compoundVariableAmt="
				+ compoundVariableAmt + ", Bank=" + Bank + ", currentBalance=" + currentBalance + ", maturityDate="
				+ maturityDate + ", createdDate=" + createdDate + ", status=" + status + ", depositType=" + depositType
				+ ", depositHolderStatus=" + depositHolderStatus + ", contribution=" + contribution
				+ ", newMaturityDate=" + newMaturityDate + ", topUp=" + topUp + ", files=" + files + ", document="
				+ document + ", nomineeName=" + nomineeName + ", nomineeAge=" + nomineeAge + ", nomineeAddress="
				+ nomineeAddress + ", nomineeRelationship=" + nomineeRelationship + ", nomineePan=" + nomineePan
				+ ", nomineeAadhar=" + nomineeAadhar + ", totalInterest=" + totalInterest + ", payOffActualAmount="
				+ payOffActualAmount + ", payOfInterestAmt=" + payOfInterestAmt + ", payOffInterestPercent="
				+ payOffInterestPercent + ", payOffInterestType=" + payOffInterestType + ", payOffDistributionDate="
				+ payOffDistributionDate + ", payOffType=" + payOffType + ", depositClassification="
				+ depositClassification + ", taxSavingDeposit=" + taxSavingDeposit + ", primaryCitizen="
				+ primaryCitizen + ", primaryNRIAccountType=" + primaryNRIAccountType + ", productConfigurationId="
				+ productConfigurationId + ", accountAccessType=" + accountAccessType + ", paymentMadeByHolderIds="
				+ paymentMadeByHolderIds + "]";
	}

	private Long depositId;

	private Long depositHolderId;

	private String accountNumber;

	private Long customerId;
	private String customerName;
	private String address;
	private String email;
	private String contactNum;
	private Date dateOfBirth;
	private String fdPay;
	private String paymentMode;
	private String accountType;
	private Date ddDate;

	private double accountBalance;

	private String ddBank;

	private String ddBranch;

	private String chequeNo;

	private Date chequeDate;

	private String chequeBank;

	private String chequeBranch;

	private String fdPayOffType;

	private String cardType;

	private String cardNo;

	private String expiryDate;

	private Integer cvv;

	private String linkedAccountNo;

	private String transferType;

	private String deathCertificateSubmitted;

	private Double depositAmount;

	private String approvalStatus;

	private String documentSubmitted;

	private String category;

	private Date closingDate;

	private String depositCategory;

	private String fdPayType;

	private String otherPayTransfer1;

	private String otherName1;

	private String otherBank1;

	private String otherBranch1;

	private Float interestPayAmount1;

	private String otherIFSC1;

	private String otherAccount1;

	private Double withdrawAmount;

	private Double compoundVariableAmt;
	private String Bank;
	private double currentBalance;

	private Date maturityDate;

	private Date createdDate;

	private String status;// open//close

	private String depositType;

	private String depositHolderStatus;

	private Float contribution;

	private Date newMaturityDate;

	private Integer topUp;
	
	private List<MultipartFile> files;
	 
	 private String document;
	 
	 private String nomineeName;

	 private String nomineeAge;

	 private String nomineeAddress;

	 private String nomineeRelationship;
	 
	 private String nomineePan;

	 private Long nomineeAadhar;
	 
	 private Double totalInterest;
	 
	 private Double payOffActualAmount;

	 private Double payOfInterestAmt;
	 
	 private Double payOffInterestPercent;

	 private String payOffInterestType; //(Part/Percent)
	 
	 private Date payOffDistributionDate;
	 
	 private String payOffType; // Monthly/Quarterly/SemiAnnually/Annually/EndOfTenure 

	 private String depositClassification;
		
	 private String taxSavingDeposit;
	 
     private String primaryCitizen;
     
     private String primaryNRIAccountType;
     
     private String productConfigurationId;
     
     private String accountAccessType;
     private String paymentMadeByHolderIds;
     
     
     private String overdraftNumber;
     
     private Double withdrawableAmount;
     

	public String getOverdraftNumber() {
		return overdraftNumber;
	}

	public void setOverdraftNumber(String overdraftNumber) {
		this.overdraftNumber = overdraftNumber;
	}

	public Double getWithdrawableAmount() {
		return withdrawableAmount;
	}

	public void setWithdrawableAmount(Double withdrawableAmount) {
		this.withdrawableAmount = withdrawableAmount;
	}

	public String getPaymentMadeByHolderIds() {
		return paymentMadeByHolderIds;
	}

	public void setPaymentMadeByHolderIds(String paymentMadeByHolderIds) {
		this.paymentMadeByHolderIds = paymentMadeByHolderIds;
	}

	public String getAccountAccessType() {
		return accountAccessType;
	}

	public void setAccountAccessType(String accountAccessType) {
		this.accountAccessType = accountAccessType;
	}

	public Date getDdDate() {
		return ddDate;
	}

	public void setDdDate(Date ddDate) {
		this.ddDate = ddDate;
	}

	public String getDdBank() {
		return ddBank;
	}

	public void setDdBank(String ddBank) {
		this.ddBank = ddBank;
	}

	public String getDdBranch() {
		return ddBranch;
	}

	public void setDdBranch(String ddBranch) {
		this.ddBranch = ddBranch;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getChequeBank() {
		return chequeBank;
	}

	public void setChequeBank(String chequeBank) {
		this.chequeBank = chequeBank;
	}

	public String getChequeBranch() {
		return chequeBranch;
	}

	public void setChequeBranch(String chequeBranch) {
		this.chequeBranch = chequeBranch;
	}

	public String getFdPayOffType() {
		return fdPayOffType;
	}

	public void setFdPayOffType(String fdPayOffType) {
		this.fdPayOffType = fdPayOffType;
	}

	public Long getDepositHolderId() {
		return depositHolderId;
	}

	public void setDepositHolderId(Long depositHolderId) {
		this.depositHolderId = depositHolderId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getFdPay() {
		return fdPay;
	}

	public void setFdPay(String fdPay) {
		this.fdPay = fdPay;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getCvv() {
		return cvv;
	}

	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}

	public String getLinkedAccountNo() {
		return linkedAccountNo;
	}

	public void setLinkedAccountNo(String linkedAccountNo) {
		this.linkedAccountNo = linkedAccountNo;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getDeathCertificateSubmitted() {
		return deathCertificateSubmitted;
	}

	public void setDeathCertificateSubmitted(String deathCertificateSubmitted) {
		this.deathCertificateSubmitted = deathCertificateSubmitted;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getDocumentSubmitted() {
		return documentSubmitted;
	}

	public void setDocumentSubmitted(String documentSubmitted) {
		this.documentSubmitted = documentSubmitted;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public String getDepositCategory() {
		return depositCategory;
	}

	public void setDepositCategory(String depositCategory) {
		this.depositCategory = depositCategory;
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

	public String getOtherBranch1() {
		return otherBranch1;
	}

	public void setOtherBranch1(String otherBranch1) {
		this.otherBranch1 = otherBranch1;
	}

	public Float getInterestPayAmount1() {
		return interestPayAmount1;
	}

	public void setInterestPayAmount1(Float interestPayAmount1) {
		this.interestPayAmount1 = interestPayAmount1;
	}

	public String getOtherIFSC1() {
		return otherIFSC1;
	}

	public void setOtherIFSC1(String otherIFSC1) {
		this.otherIFSC1 = otherIFSC1;
	}

	public String getOtherAccount1() {
		return otherAccount1;
	}

	public void setOtherAccount1(String otherAccount1) {
		this.otherAccount1 = otherAccount1;
	}

	public Double getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(Double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public Double getCompoundVariableAmt() {
		return compoundVariableAmt;
	}

	public void setCompoundVariableAmt(Double compoundVariableAmt) {
		this.compoundVariableAmt = compoundVariableAmt;
	}

	public String getBank() {
		return Bank;
	}

	public void setBank(String bank) {
		Bank = bank;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public String getDepositHolderStatus() {
		return depositHolderStatus;
	}

	public void setDepositHolderStatus(String depositHolderStatus) {
		this.depositHolderStatus = depositHolderStatus;
	}

	public Float getContribution() {
		return contribution;
	}

	public void setContribution(Float contribution) {
		this.contribution = contribution;
	}

	public Date getNewMaturityDate() {
		return newMaturityDate;
	}

	public void setNewMaturityDate(Date newMaturityDate) {
		this.newMaturityDate = newMaturityDate;
	}

	public Integer getTopUp() {
		return topUp;
	}

	public void setTopUp(Integer topUp) {
		this.topUp = topUp;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getNomineeName() {
		return nomineeName;
	}

	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	public String getNomineeAge() {
		return nomineeAge;
	}

	public void setNomineeAge(String nomineeAge) {
		this.nomineeAge = nomineeAge;
	}

	public String getNomineeAddress() {
		return nomineeAddress;
	}

	public void setNomineeAddress(String nomineeAddress) {
		this.nomineeAddress = nomineeAddress;
	}

	public String getNomineeRelationship() {
		return nomineeRelationship;
	}

	public void setNomineeRelationship(String nomineeRelationship) {
		this.nomineeRelationship = nomineeRelationship;
	}

	public String getNomineePan() {
		return nomineePan;
	}

	public void setNomineePan(String nomineePan) {
		this.nomineePan = nomineePan;
	}

	public Long getNomineeAadhar() {
		return nomineeAadhar;
	}

	public void setNomineeAadhar(Long nomineeAadhar) {
		this.nomineeAadhar = nomineeAadhar;
	}

	public Double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(Double totalInterest) {
		this.totalInterest = totalInterest;
	}

	public Double getPayOffActualAmount() {
		return payOffActualAmount;
	}

	public void setPayOffActualAmount(Double payOffActualAmount) {
		this.payOffActualAmount = payOffActualAmount;
	}

	public Double getPayOfInterestAmt() {
		return payOfInterestAmt;
	}

	public void setPayOfInterestAmt(Double payOfInterestAmt) {
		this.payOfInterestAmt = payOfInterestAmt;
	}

	public Double getPayOffInterestPercent() {
		return payOffInterestPercent;
	}

	public void setPayOffInterestPercent(Double payOffInterestPercent) {
		this.payOffInterestPercent = payOffInterestPercent;
	}

	public String getPayOffInterestType() {
		return payOffInterestType;
	}

	public void setPayOffInterestType(String payOffInterestType) {
		this.payOffInterestType = payOffInterestType;
	}

	public Date getPayOffDistributionDate() {
		return payOffDistributionDate;
	}

	public void setPayOffDistributionDate(Date payOffDistributionDate) {
		this.payOffDistributionDate = payOffDistributionDate;
	}

	public String getPayOffType() {
		return payOffType;
	}

	public void setPayOffType(String payOffType) {
		this.payOffType = payOffType;
	}

	public String getDepositClassification() {
		return depositClassification;
	}

	public void setDepositClassification(String depositClassification) {
		this.depositClassification = depositClassification;
	}

	public String getTaxSavingDeposit() {
		return taxSavingDeposit;
	}

	public void setTaxSavingDeposit(String taxSavingDeposit) {
		this.taxSavingDeposit = taxSavingDeposit;
	}

	public String getPrimaryCitizen() {
		return primaryCitizen;
	}

	public void setPrimaryCitizen(String primaryCitizen) {
		this.primaryCitizen = primaryCitizen;
	}

	public String getPrimaryNRIAccountType() {
		return primaryNRIAccountType;
	}

	public void setPrimaryNRIAccountType(String primaryNRIAccountType) {
		this.primaryNRIAccountType = primaryNRIAccountType;
	}
	public String getProductConfigurationId() {
		return productConfigurationId;
	}

	public void setProductConfigurationId(String productConfigurationId) {
		this.productConfigurationId = productConfigurationId;
	}
}
