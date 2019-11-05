package annona.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table
@XmlRootElement
public class Deposit{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String accountNumber;

	private String linkedAccountNumber;

	private Double depositAmount;

	private String paymentType; // Daily,monthy,quarterly,half yearly,yearly
	@Transient
	private String tenureType; // months/years
	
	private Integer TenureInYears;
	
	private Integer TenureInMonths;
	
	private Integer TenureInDays;
	
	private Integer totalTenureInDays;
	@Transient
	private Integer tenure;

	private String paymentMode;// debit from saving acc, check book,cash/dd
	
	private Long paymentModeId;

	private Date dueDate;

	private Float interestRate;

	private String flexiRate; // yes/no

	private Double maturityAmount; // expected maturity amount

	private Double newMaturityAmount;

	private Date maturityDate;

	private String payOffInterestType; // monthy,quarterly,half yearly,yearly

	private Date payOffDueDate;
	@Transient
	private String depositType; // Regular/Recurring/Tax Saving/Annuity/Gold

	private String status;// open//close

	private String form;

	private byte[] formFile;

	private String formStatus;

	private Date createdDate;
	
	private String accountAccessType;
	
	

	private Date depositCreationDate;
	
	private Boolean createdFromBulk;

	private Date modifiedDate;

	private Double maturityAmountOnClosing;

	private Date closingDate;

	private Double prematurePanaltyAmount;

	private Double modifyPanalityAmount;

	private String depositCategory;

	private String approvalStatus;

	private String comment;

	private Double currentBalance;

	private String depositCurrency;

	private Float modifiedInterestRate;

	private String transactionId;

	private String createdBy;

	private Integer stopPayment;

	private Date newMaturityDate;

	private String clearanceStatus;

	private String reverseEmiCategory;
	
	private String maturityInstruction;  // Renew / Repay
	
	private Integer isRenewed;
	
	private Date lastRenewDate;
	
	private Integer gestationPeriod;
	
	private Double emiAmount;
	
	private String taxSavingDeposit;

	private String nriAccountType;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "depositId", referencedColumnName = "id")
	private Set<DepositHolder> depositHolder;
	
	private String depositClassification;
	
	private String primaryCitizen;
	
	private String primaryNRIAccountType;
	
	private String primaryCustomerCategory;
	
	private Integer deductionDay;
	
	private String branchCode;
	
	private Date newMaturityDateForHolidays;
	
	private Long productConfigurationId;
	
	private String depositAccountType; //Single/Joint/Consortium

	private String depositArea;
	
	private String sweepInType; // For Sweep/Auto Deposit
	
	private Integer isMaturityDisbrsmntInLinkedAccount;
	
	private Integer isFullyPaid;
	
	private Integer isPreMaturelyClosed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getLinkedAccountNumber() {
		return linkedAccountNumber;
	}

	public void setLinkedAccountNumber(String linkedAccountNumber) {
		this.linkedAccountNumber = linkedAccountNumber;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getTenureType() {
		return tenureType;
	}

	public void setTenureType(String tenureType) {
		this.tenureType = tenureType;
	}

	public Integer getTenureInYears() {
		return TenureInYears;
	}

	public void setTenureInYears(Integer tenureInYears) {
		TenureInYears = tenureInYears;
	}

	public Integer getTenureInMonths() {
		return TenureInMonths;
	}

	public void setTenureInMonths(Integer tenureInMonths) {
		TenureInMonths = tenureInMonths;
	}

	public Integer getTenureInDays() {
		return TenureInDays;
	}

	public void setTenureInDays(Integer tenureInDays) {
		TenureInDays = tenureInDays;
	}

	public Integer getTotalTenureInDays() {
		return totalTenureInDays;
	}

	public void setTotalTenureInDays(Integer totalTenureInDays) {
		this.totalTenureInDays = totalTenureInDays;
	}

	public Integer getTenure() {
		return tenure;
	}

	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Long getPaymentModeId() {
		return paymentModeId;
	}

	public void setPaymentModeId(Long paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
	}

	public String getFlexiRate() {
		return flexiRate;
	}

	public void setFlexiRate(String flexiRate) {
		this.flexiRate = flexiRate;
	}

	public Double getMaturityAmount() {
		return maturityAmount;
	}

	public void setMaturityAmount(Double maturityAmount) {
		this.maturityAmount = maturityAmount;
	}

	public Double getNewMaturityAmount() {
		return newMaturityAmount;
	}

	public void setNewMaturityAmount(Double newMaturityAmount) {
		this.newMaturityAmount = newMaturityAmount;
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

	public Date getPayOffDueDate() {
		return payOffDueDate;
	}

	public void setPayOffDueDate(Date payOffDueDate) {
		this.payOffDueDate = payOffDueDate;
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

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public byte[] getFormFile() {
		return formFile;
	}

	public void setFormFile(byte[] formFile) {
		this.formFile = formFile;
	}

	public String getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(String formStatus) {
		this.formStatus = formStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getAccountAccessType() {
		return accountAccessType;
	}

	public void setAccountAccessType(String accountAccessType) {
		this.accountAccessType = accountAccessType;
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

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Double getMaturityAmountOnClosing() {
		return maturityAmountOnClosing;
	}

	public void setMaturityAmountOnClosing(Double maturityAmountOnClosing) {
		this.maturityAmountOnClosing = maturityAmountOnClosing;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public Double getPrematurePanaltyAmount() {
		return prematurePanaltyAmount;
	}

	public void setPrematurePanaltyAmount(Double prematurePanaltyAmount) {
		this.prematurePanaltyAmount = prematurePanaltyAmount;
	}

	public Double getModifyPanalityAmount() {
		return modifyPanalityAmount;
	}

	public void setModifyPanalityAmount(Double modifyPanalityAmount) {
		this.modifyPanalityAmount = modifyPanalityAmount;
	}

	public String getDepositCategory() {
		return depositCategory;
	}

	public void setDepositCategory(String depositCategory) {
		this.depositCategory = depositCategory;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getDepositCurrency() {
		return depositCurrency;
	}

	public void setDepositCurrency(String depositCurrency) {
		this.depositCurrency = depositCurrency;
	}

	public Float getModifiedInterestRate() {
		return modifiedInterestRate;
	}

	public void setModifiedInterestRate(Float modifiedInterestRate) {
		this.modifiedInterestRate = modifiedInterestRate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getStopPayment() {
		return stopPayment;
	}

	public void setStopPayment(Integer stopPayment) {
		this.stopPayment = stopPayment;
	}

	public Date getNewMaturityDate() {
		return newMaturityDate;
	}

	public void setNewMaturityDate(Date newMaturityDate) {
		this.newMaturityDate = newMaturityDate;
	}

	public String getClearanceStatus() {
		return clearanceStatus;
	}

	public void setClearanceStatus(String clearanceStatus) {
		this.clearanceStatus = clearanceStatus;
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

	public Integer getIsRenewed() {
		return isRenewed;
	}

	public void setIsRenewed(Integer isRenewed) {
		this.isRenewed = isRenewed;
	}

	public Date getLastRenewDate() {
		return lastRenewDate;
	}

	public void setLastRenewDate(Date lastRenewDate) {
		this.lastRenewDate = lastRenewDate;
	}

	public Integer getGestationPeriod() {
		return gestationPeriod;
	}

	public void setGestationPeriod(Integer gestationPeriod) {
		this.gestationPeriod = gestationPeriod;
	}

	public Double getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(Double emiAmount) {
		this.emiAmount = emiAmount;
	}

	public String getTaxSavingDeposit() {
		return taxSavingDeposit;
	}

	public void setTaxSavingDeposit(String taxSavingDeposit) {
		this.taxSavingDeposit = taxSavingDeposit;
	}

	public String getNriAccountType() {
		return nriAccountType;
	}

	public void setNriAccountType(String nriAccountType) {
		this.nriAccountType = nriAccountType;
	}

	public Set<DepositHolder> getDepositHolder() {
		return depositHolder;
	}

	public void setDepositHolder(Set<DepositHolder> depositHolder) {
		this.depositHolder = depositHolder;
	}

	public String getDepositClassification() {
		return depositClassification;
	}

	public void setDepositClassification(String depositClassification) {
		this.depositClassification = depositClassification;
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

	public String getPrimaryCustomerCategory() {
		return primaryCustomerCategory;
	}

	public void setPrimaryCustomerCategory(String primaryCustomerCategory) {
		this.primaryCustomerCategory = primaryCustomerCategory;
	}

	public Integer getDeductionDay() {
		return deductionDay;
	}

	public void setDeductionDay(Integer deductionDay) {
		this.deductionDay = deductionDay;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Date getNewMaturityDateForHolidays() {
		return newMaturityDateForHolidays;
	}

	public void setNewMaturityDateForHolidays(Date newMaturityDateForHolidays) {
		this.newMaturityDateForHolidays = newMaturityDateForHolidays;
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

	public String getDepositArea() {
		return depositArea;
	}

	public void setDepositArea(String depositArea) {
		this.depositArea = depositArea;
	}

	public String getSweepInType() {
		return sweepInType;
	}

	public void setSweepInType(String sweepInType) {
		this.sweepInType = sweepInType;
	}

	public Integer getIsMaturityDisbrsmntInLinkedAccount() {
		return isMaturityDisbrsmntInLinkedAccount;
	}

	public void setIsMaturityDisbrsmntInLinkedAccount(Integer isMaturityDisbrsmntInLinkedAccount) {
		this.isMaturityDisbrsmntInLinkedAccount = isMaturityDisbrsmntInLinkedAccount;
	}

	public Integer getIsFullyPaid() {
		return isFullyPaid;
	}

	public void setIsFullyPaid(Integer isFullyPaid) {
		this.isFullyPaid = isFullyPaid;
	}

	public Integer getIsPreMaturelyClosed() {
		return isPreMaturelyClosed;
	}

	public void setIsPreMaturelyClosed(Integer isPreMaturelyClosed) {
		this.isPreMaturelyClosed = isPreMaturelyClosed;
	}
	
	
	
}
