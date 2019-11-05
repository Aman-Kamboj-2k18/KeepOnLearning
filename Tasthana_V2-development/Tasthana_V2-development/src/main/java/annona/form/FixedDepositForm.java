package annona.form;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;
import annona.domain.AccountDetails;
import annona.domain.Customer;
import annona.domain.Interest;

@Component
public class FixedDepositForm {

	private Long id;

	private Long depositId;

	private Long customerId;

	private String category;

	private String customerName;

	private String email;

	private String contactNum;

	private String customerID;

	private String accountNo;

	private String accountType;

	private Double accountBalance;

	private String fdID;

	private Double fdAmount;

	private Double fdFixed;

	private Double fdChangeable;

	private String fdTenureType;

	private Integer fdTenure;
	
private Integer tenureInYears;
	
	private Integer tenureInMonths;
	
	private Integer tenureInDays;
	
	private Integer totalTenureInDays;
	
	private String depositArea;
	
	private Date depositCreationDate;
	private Date depositDate;
	
	private Boolean createdFromBulk;
	
    private String accountAccessType;
    
    private Integer isLinkedAccount;
	
	public Integer getIsLinkedAccount() {
		return isLinkedAccount;
	}

	public void setIsLinkedAccount(Integer isLinkedAccount) {
		this.isLinkedAccount = isLinkedAccount;
	}

	public String getAccountAccessType() {
		return accountAccessType;
	}

	public void setAccountAccessType(String accountAccessType) {
		this.accountAccessType = accountAccessType;
	}

	public String getDepositArea() {
		return depositArea;
	}

	public void setDepositArea(String depositArea) {
		this.depositArea = depositArea;
	}

	public Integer getTenureInYears() {
		return tenureInYears;
	}

	public void setTenureInYears(Integer tenureInYears) {
		this.tenureInYears = tenureInYears;
	}

	public Integer getTenureInMonths() {
		return tenureInMonths;
	}

	public void setTenureInMonths(Integer tenureInMonths) {
		this.tenureInMonths = tenureInMonths;
	}

	public Integer getTenureInDays() {
		return tenureInDays;
	}

	public void setTenureInDays(Integer tenureInDays) {
		this.tenureInDays = tenureInDays;
	}

	public Integer getTotalTenureInDays() {
		return totalTenureInDays;
	}

	public void setTotalTenureInDays(Integer totalTenureInDays) {
		this.totalTenureInDays = totalTenureInDays;
	}

	private Float fdInterest;

	private String depositType;

	private String status;

	private String comment;

	private String paymentType;

	private Date fdDeductDate;

	private Date fdTenureDate;

	private Float estimateInterest;

	private Float estimateTDSDeduct;

	private Float estimateSESDeduct;

	private Float estimateServiceTax;

	private Double estimatePayOffAmount;

	private Date fdDueDate;

	private Double fdCurrentAmount;

	private String changePaymentType;

	private Double changeFDAmount;

	private String changeFDPayType;

	private String changeFDPay;

	private String changeFDTenureType;

	private Date changeFDDeductDate;

	private Integer changeFDTenure;

	private String fdPayType;

	private String fdPay;

	private Long chequeNo;

	private String chequeBank;
	
	private String chequeName;
	
	private Float chequeAmount;
	
	private String depositHolders;

	

	private String chequeBranch;

	private String fdPayOffAccount;

	private String otherAccount;

	private String otherIFSC;

	private String otherPayTransfer;

	private String otherName;

	private String otherBank;

	private Float fdCreditAmount;

	private String interstPayType;

	private Float interestPayAmount;

	private String otherAccount1;

	private String otherIFSC1;

	private String otherPayTransfer1;

	private String otherName1;

	private String otherBank1;
	
	private String cardType;

	private String cardNo;

	private String expiryDate;

	private Integer cvv;

	private Float interestPayAmount1;

	private Double payOffPARTInterest1;

	private String paymentMode;

	private String age;

	private String nomineeName;

	private String nomineeAge;

	private String nomineeAddress;

	private String nomineeRelationShip;

	private String guardianName;

	private String guardianAge;

	private String guardianAddress;

	private String guardianRelationShip;

	private Float interestPercent;

	private List<JointAccount> jointAccounts;

	private JointAccount jointAccount;

	private Float userContribution;

	private Float userAmount;

	private Float userInterestAmt;

	private List<AccountDetails> accountList;

	private String flexiInterest;

	private Long depositHolderId;

	private Double depositedAmt;

	private String action; // Payment/Withdraw/Interest

	private Customer customer;

	private Date maturityDate;

	private String payOffInterestType;

	private String currency;

	private DepositForm depositForm;

	private Double depositAmount;

	private Date payoffDate;

	private Integer deductionDay;

	private String userName;

	private Double emiAmount;

	private String fixedAmountEmi;

	private String fixedTenureEmi;

	private String reverseEmiBasic;

	private Date chequeDate;

	private String benificiaryName;

	private String bankAccountType;

	private String ifscCode;

	private String remarks;

	private String benificiaryAccountNumber;

	private String amountToTransfer;

	private String nomineePan;

	private Long nomineeAadhar;

	private Long gaurdianAadhar;

	private String gaurdianPan;

	private String reverseEmiCategory;
	
	private String maturityInstruction;  // Renew / Repay
	
	private Integer gestationPeriod;
	
	private List<Interest> interestList;
	
	private Integer daysValue;
	
	private Integer emiTenure;
	
	private String depositClassification;
	
	private String taxSavingDeposit;

	private String citizen;
	
	private String nriAccountType;
	
	private String branchCode;

	private Long productConfigurationId;
	
	public String depositAccountType;
	
	private String Bank;
	
	private String paymentMadeByHolderIds;

	private String deathCertificateSubmitted;
	
	
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	

	public String getPaymentMadeByHolderIds() {
		return paymentMadeByHolderIds;
	}

	public void setPaymentMadeByHolderIds(String paymentMadeByHolderIds) {
		this.paymentMadeByHolderIds = paymentMadeByHolderIds;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getFdID() {
		return fdID;
	}

	public void setFdID(String fdID) {
		this.fdID = fdID;
	}

	public Double getFdAmount() {
		return fdAmount;
	}

	public void setFdAmount(Double fdAmount) {
		this.fdAmount = fdAmount;
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

	public String getFdTenureType() {
		return fdTenureType;
	}

	public void setFdTenureType(String fdTenureType) {
		this.fdTenureType = fdTenureType;
	}

	public Integer getFdTenure() {
		return fdTenure;
	}

	public void setFdTenure(Integer fdTenure) {
		this.fdTenure = fdTenure;
	}

	public Float getFdInterest() {
		return fdInterest;
	}

	public void setFdInterest(Float fdInterest) {
		this.fdInterest = fdInterest;
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

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

	public Float getEstimateInterest() {
		return estimateInterest;
	}

	public void setEstimateInterest(Float estimateInterest) {
		this.estimateInterest = estimateInterest;
	}

	public Float getEstimateTDSDeduct() {
		return estimateTDSDeduct;
	}

	public void setEstimateTDSDeduct(Float estimateTDSDeduct) {
		this.estimateTDSDeduct = estimateTDSDeduct;
	}

	public Float getEstimateSESDeduct() {
		return estimateSESDeduct;
	}

	public void setEstimateSESDeduct(Float estimateSESDeduct) {
		this.estimateSESDeduct = estimateSESDeduct;
	}

	public Float getEstimateServiceTax() {
		return estimateServiceTax;
	}

	public void setEstimateServiceTax(Float estimateServiceTax) {
		this.estimateServiceTax = estimateServiceTax;
	}

	public Double getEstimatePayOffAmount() {
		return estimatePayOffAmount;
	}

	public void setEstimatePayOffAmount(Double estimatePayOffAmount) {
		this.estimatePayOffAmount = estimatePayOffAmount;
	}

	public Date getFdDueDate() {
		return fdDueDate;
	}

	public void setFdDueDate(Date fdDueDate) {
		this.fdDueDate = fdDueDate;
	}

	public Double getFdCurrentAmount() {
		return fdCurrentAmount;
	}

	public void setFdCurrentAmount(Double fdCurrentAmount) {
		this.fdCurrentAmount = fdCurrentAmount;
	}

	public String getChangePaymentType() {
		return changePaymentType;
	}

	public void setChangePaymentType(String changePaymentType) {
		this.changePaymentType = changePaymentType;
	}

	public Double getChangeFDAmount() {
		return changeFDAmount;
	}

	public void setChangeFDAmount(Double changeFDAmount) {
		this.changeFDAmount = changeFDAmount;
	}

	public String getChangeFDPayType() {
		return changeFDPayType;
	}

	public void setChangeFDPayType(String changeFDPayType) {
		this.changeFDPayType = changeFDPayType;
	}

	public String getChangeFDPay() {
		return changeFDPay;
	}

	public void setChangeFDPay(String changeFDPay) {
		this.changeFDPay = changeFDPay;
	}

	public String getChangeFDTenureType() {
		return changeFDTenureType;
	}

	public void setChangeFDTenureType(String changeFDTenureType) {
		this.changeFDTenureType = changeFDTenureType;
	}
	
	public String getBank() {
		return Bank;
	}

	public void setBank(String bank) {
		Bank = bank;
	}

	public Date getChangeFDDeductDate() {
		return changeFDDeductDate;
	}

	public void setChangeFDDeductDate(Date changeFDDeductDate) {
		this.changeFDDeductDate = changeFDDeductDate;
	}

	public Integer getChangeFDTenure() {
		return changeFDTenure;
	}

	public void setChangeFDTenure(Integer changeFDTenure) {
		this.changeFDTenure = changeFDTenure;
	}

	public String getFdPayType() {
		return fdPayType;
	}

	public void setFdPayType(String fdPayType) {
		this.fdPayType = fdPayType;
	}

	public String getFdPay() {
		return fdPay;
	}

	public void setFdPay(String fdPay) {
		this.fdPay = fdPay;
	}

	public Long getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(Long chequeNo) {
		this.chequeNo = chequeNo;
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

	public String getFdPayOffAccount() {
		return fdPayOffAccount;
	}

	public void setFdPayOffAccount(String fdPayOffAccount) {
		this.fdPayOffAccount = fdPayOffAccount;
	}

	public String getOtherAccount() {
		return otherAccount;
	}

	public void setOtherAccount(String otherAccount) {
		this.otherAccount = otherAccount;
	}

	public String getOtherIFSC() {
		return otherIFSC;
	}

	public void setOtherIFSC(String otherIFSC) {
		this.otherIFSC = otherIFSC;
	}

	public String getOtherPayTransfer() {
		return otherPayTransfer;
	}

	public void setOtherPayTransfer(String otherPayTransfer) {
		this.otherPayTransfer = otherPayTransfer;
	}

	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}

	public String getOtherBank() {
		return otherBank;
	}

	public void setOtherBank(String otherBank) {
		this.otherBank = otherBank;
	}

	public Float getFdCreditAmount() {
		return fdCreditAmount;
	}

	public void setFdCreditAmount(Float fdCreditAmount) {
		this.fdCreditAmount = fdCreditAmount;
	}

	public String getInterstPayType() {
		return interstPayType;
	}

	public void setInterstPayType(String interstPayType) {
		this.interstPayType = interstPayType;
	}

	public Float getInterestPayAmount() {
		return interestPayAmount;
	}

	public void setInterestPayAmount(Float interestPayAmount) {
		this.interestPayAmount = interestPayAmount;
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

	public Float getInterestPayAmount1() {
		return interestPayAmount1;
	}

	public void setInterestPayAmount1(Float interestPayAmount1) {
		this.interestPayAmount1 = interestPayAmount1;
	}

	public Double getPayOffPARTInterest1() {
		return payOffPARTInterest1;
	}

	public void setPayOffPARTInterest1(Double payOffPARTInterest1) {
		this.payOffPARTInterest1 = payOffPARTInterest1;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

	public String getNomineeRelationShip() {
		return nomineeRelationShip;
	}

	public void setNomineeRelationShip(String nomineeRelationShip) {
		this.nomineeRelationShip = nomineeRelationShip;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getGuardianAge() {
		return guardianAge;
	}

	public void setGuardianAge(String guardianAge) {
		this.guardianAge = guardianAge;
	}

	public String getGuardianAddress() {
		return guardianAddress;
	}

	public void setGuardianAddress(String guardianAddress) {
		this.guardianAddress = guardianAddress;
	}

	public String getGuardianRelationShip() {
		return guardianRelationShip;
	}

	public void setGuardianRelationShip(String guardianRelationShip) {
		this.guardianRelationShip = guardianRelationShip;
	}

	public Float getInterestPercent() {
		return interestPercent;
	}

	public void setInterestPercent(Float interestPercent) {
		this.interestPercent = interestPercent;
	}

	public List<JointAccount> getJointAccounts() {
		return jointAccounts;
	}

	public void setJointAccounts(List<JointAccount> jointAccounts) {
		this.jointAccounts = jointAccounts;
	}

	public JointAccount getJointAccount() {
		return jointAccount;
	}

	public void setJointAccount(JointAccount jointAccount) {
		this.jointAccount = jointAccount;
	}

	public Float getUserContribution() {
		return userContribution;
	}

	public void setUserContribution(Float userContribution) {
		this.userContribution = userContribution;
	}

	public Float getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(Float userAmount) {
		this.userAmount = userAmount;
	}

	public Float getUserInterestAmt() {
		return userInterestAmt;
	}

	public void setUserInterestAmt(Float userInterestAmt) {
		this.userInterestAmt = userInterestAmt;
	}

	public List<AccountDetails> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<AccountDetails> accountList) {
		this.accountList = accountList;
	}

	public String getFlexiInterest() {
		return flexiInterest;
	}

	public void setFlexiInterest(String flexiInterest) {
		this.flexiInterest = flexiInterest;
	}

	public Long getDepositHolderId() {
		return depositHolderId;
	}

	public void setDepositHolderId(Long depositHolderId) {
		this.depositHolderId = depositHolderId;
	}

	public Double getDepositedAmt() {
		return depositedAmt;
	}

	public void setDepositedAmt(Double depositedAmt) {
		this.depositedAmt = depositedAmt;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getPayOffInterestType() {
		return payOffInterestType;
	}

	public void setPayOffInterestType(String payOffInterestType) {
		this.payOffInterestType = payOffInterestType;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public DepositForm getDepositForm() {
		return depositForm;
	}

	public void setDepositForm(DepositForm depositForm) {
		this.depositForm = depositForm;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Date getPayoffDate() {
		return payoffDate;
	}

	public void setPayoffDate(Date payoffDate) {
		this.payoffDate = payoffDate;
	}

	public Integer getDeductionDay() {
		return deductionDay;
	}

	public void setDeductionDay(Integer deductionDay) {
		this.deductionDay = deductionDay;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(Double emiAmount) {
		this.emiAmount = emiAmount;
	}

	public String getFixedAmountEmi() {
		return fixedAmountEmi;
	}

	public void setFixedAmountEmi(String fixedAmountEmi) {
		this.fixedAmountEmi = fixedAmountEmi;
	}

	public String getFixedTenureEmi() {
		return fixedTenureEmi;
	}

	public void setFixedTenureEmi(String fixedTenureEmi) {
		this.fixedTenureEmi = fixedTenureEmi;
	}

	public String getReverseEmiBasic() {
		return reverseEmiBasic;
	}

	public void setReverseEmiBasic(String reverseEmiBasic) {
		this.reverseEmiBasic = reverseEmiBasic;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getBenificiaryName() {
		return benificiaryName;
	}

	public void setBenificiaryName(String benificiaryName) {
		this.benificiaryName = benificiaryName;
	}

	public String getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBenificiaryAccountNumber() {
		return benificiaryAccountNumber;
	}

	public void setBenificiaryAccountNumber(String benificiaryAccountNumber) {
		this.benificiaryAccountNumber = benificiaryAccountNumber;
	}

	public String getAmountToTransfer() {
		return amountToTransfer;
	}

	public void setAmountToTransfer(String amountToTransfer) {
		this.amountToTransfer = amountToTransfer;
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

	public Long getGaurdianAadhar() {
		return gaurdianAadhar;
	}

	public void setGaurdianAadhar(Long gaurdianAadhar) {
		this.gaurdianAadhar = gaurdianAadhar;
	}

	public String getGaurdianPan() {
		return gaurdianPan;
	}

	public void setGaurdianPan(String gaurdianPan) {
		this.gaurdianPan = gaurdianPan;
	}

	public String getReverseEmiCategory() {
		return reverseEmiCategory;
	}

	public void setReverseEmiCategory(String reverseEmiCategory) {
		this.reverseEmiCategory = reverseEmiCategory;
	}

	public String getMaturityInstruction() {
		return maturityInstruction;
	}

	public void setMaturityInstruction(String maturityInstruction) {
		this.maturityInstruction = maturityInstruction;
	}

	public Integer getGestationPeriod() {
		return gestationPeriod;
	}

	public void setGestationPeriod(Integer gestationPeriod) {
		this.gestationPeriod = gestationPeriod;
	}

	public List<Interest> getInterestList() {
		return interestList;
	}

	public void setInterestList(List<Interest> interestList) {
		this.interestList = interestList;
	}

	public Integer getDaysValue() {
		return daysValue;
	}

	public void setDaysValue(Integer daysValue) {
		this.daysValue = daysValue;
	}

	public Integer getEmiTenure() {
		return emiTenure;
	}

	public void setEmiTenure(Integer emiTenure) {
		this.emiTenure = emiTenure;
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

	public String getCitizen() {
		return citizen;
	}

	public void setCitizen(String citizen) {
		this.citizen = citizen;
	}

	public String getNriAccountType() {
		return nriAccountType;
	}

	public void setNriAccountType(String nriAccountType) {
		this.nriAccountType = nriAccountType;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Long getProductConfigurationId() {
		return productConfigurationId;
	}

	public void setProductConfigurationId(Long productConfigurationId) {
		this.productConfigurationId = productConfigurationId;
	}

	public String getDepositAccountType() {
		return depositAccountType;
	}

	public void setDepositAccountType(String depositAccountType) {
		this.depositAccountType = depositAccountType;
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

	public Date getDepositCreationDate() {
		return depositCreationDate;
	}

	public void setDepositCreationDate(Date depositCreationDate) {
		this.depositCreationDate = depositCreationDate;
	}

	public Boolean getCreatedFromBulk() {
		return createdFromBulk;
	}

	public void setCreatedFromBulk(Boolean createdFromBulk) {
		this.createdFromBulk = createdFromBulk;
	}

	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}
	// Maturity Disbursement
		//----------------------------------------------
		private Integer isMaturityDisbrsmntInLinkedAccount;
		
		private Integer isMaturityDisbrsmntInSameBank;
		
		private String maturityDisbrsmntAccHolderName;
		
		private String maturityDisbrsmntAccNumber;
		
		private String maturityDisbrsmntTransferType;
		
		private String maturityDisbrsmntBankName;

		private String maturityDisbrsmntBankIFSCCode;
		//----------------------------------------------

		public Integer getIsMaturityDisbrsmntInLinkedAccount() {
			return isMaturityDisbrsmntInLinkedAccount;
		}

		public void setIsMaturityDisbrsmntInLinkedAccount(Integer isMaturityDisbrsmntInLinkedAccount) {
			this.isMaturityDisbrsmntInLinkedAccount = isMaturityDisbrsmntInLinkedAccount;
		}

		public Integer getIsMaturityDisbrsmntInSameBank() {
			return isMaturityDisbrsmntInSameBank;
		}

		public void setIsMaturityDisbrsmntInSameBank(Integer isMaturityDisbrsmntInSameBank) {
			this.isMaturityDisbrsmntInSameBank = isMaturityDisbrsmntInSameBank;
		}

		public String getMaturityDisbrsmntAccHolderName() {
			return maturityDisbrsmntAccHolderName;
		}

		public void setMaturityDisbrsmntAccHolderName(String maturityDisbrsmntAccHolderName) {
			this.maturityDisbrsmntAccHolderName = maturityDisbrsmntAccHolderName;
		}

		public String getMaturityDisbrsmntAccNumber() {
			return maturityDisbrsmntAccNumber;
		}

		public void setMaturityDisbrsmntAccNumber(String maturityDisbrsmntAccNumber) {
			this.maturityDisbrsmntAccNumber = maturityDisbrsmntAccNumber;
		}

		public String getMaturityDisbrsmntTransferType() {
			return maturityDisbrsmntTransferType;
		}

		public void setMaturityDisbrsmntTransferType(String maturityDisbrsmntTransferType) {
			this.maturityDisbrsmntTransferType = maturityDisbrsmntTransferType;
		}

		public String getMaturityDisbrsmntBankName() {
			return maturityDisbrsmntBankName;
		}

		public void setMaturityDisbrsmntBankName(String maturityDisbrsmntBankName) {
			this.maturityDisbrsmntBankName = maturityDisbrsmntBankName;
		}

		public String getMaturityDisbrsmntBankIFSCCode() {
			return maturityDisbrsmntBankIFSCCode;
		}

		public void setMaturityDisbrsmntBankIFSCCode(String maturityDisbrsmntBankIFSCCode) {
			this.maturityDisbrsmntBankIFSCCode = maturityDisbrsmntBankIFSCCode;
		}
		
		public String getChequeName() {
			return chequeName;
		}

		public void setChequeName(String chequeName) {
			this.chequeName = chequeName;
		}

		public Float getChequeAmount() {
			return chequeAmount;
		}

		public void setChequeAmount(Float chequeAmount) {
			this.chequeAmount = chequeAmount;
		}

		public String getDepositHolders() {
			return depositHolders;
		}

		public void setDepositHolders(String depositHolders) {
			this.depositHolders = depositHolders;
		}

		public String getDeathCertificateSubmitted() {
			return deathCertificateSubmitted;
		}

		public void setDeathCertificateSubmitted(String deathCertificateSubmitted) {
			this.deathCertificateSubmitted = deathCertificateSubmitted;
		}
	
}
