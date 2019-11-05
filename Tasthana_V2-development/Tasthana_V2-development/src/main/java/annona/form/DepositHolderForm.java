package annona.form;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import annona.domain.DepositHolder;

public class DepositHolderForm {

	private Long depositId;
	
	private String depositHolderStatus;
	
	private Float contribution;
	
	private Date maturityDate;
	
	private String status;
	
	private String statusTenure;
	
	private Date createdDate;
	
	private Double depositamount;
	
	private String depositType;
	
	private String paymentMode;
	
    private String tenureType; //months/years
	
	private Integer tenure;
	
	private String paymentType; 
	
	private Date dueDate;
	
    private String accountNumber; 
	
	private String linkedAccountNumber; 
	
	private Long depositHolderId;
	
	private String flexiRate;
	
	private Float interestRate;
	
	private Long customerId;
	
	private String payOffType;
	
	private String interstPayType;
	
	private Float interestPercent;
	
	private Float interestPayAmount;
	
	private String fdPayOffAccount;
	
	private String transferType;
	
	private String beneficiaryName;
	
	private String beneficiaryAccountNumber;
	
	private String beneficiaryBank;
	
	private String beneficiaryIfsc;
	
	private Date payOffDueDate;
	
	private List<DepositHolder> depositHolder;

	private Double currentBalance;
	
	private Double penalty;
	
	private Double totalPenalty;
	
	private String category;
	
	private String dataList;
	
	private Double addAmount;
	
	private Date maturityDateNew;
	
	private String customerName;
	
	private Double distAmtOnMaturity;
	
	public Double getDistAmtOnMaturity() {
		return distAmtOnMaturity;
	}

	public void setDistAmtOnMaturity(Double distAmtOnMaturity) {
		this.distAmtOnMaturity = distAmtOnMaturity;
	}

	public String getDeathCertificateSubmitted() {
		return deathCertificateSubmitted;
	}

	public void setDeathCertificateSubmitted(String deathCertificateSubmitted) {
		this.deathCertificateSubmitted = deathCertificateSubmitted;
	}

	private Date newMaturityDate;
	
	 private Double amountToTransfer;
	 
	 private Double emiAmount;
	 
	 private String createdBy;
	 
	 private String depositConversion;
		
	 private Integer deductionDay;
	 
	 private String accountAccessType;
	 
	private String deathCertificateSubmitted;
	 
	 

	
	public String getAccountAccessType() {
		return accountAccessType;
	}

	public void setAccountAccessType(String accountAccessType) {
		this.accountAccessType = accountAccessType;
	}

	

	

	

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
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

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Double getDepositamount() {
		return depositamount;
	}

	public void setDepositamount(Double depositamount) {
		this.depositamount = depositamount;
	}

	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getTenureType() {
		return tenureType;
	}

	public void setTenureType(String tenureType) {
		this.tenureType = tenureType;
	}

	public Integer getTenure() {
		return tenure;
	}

	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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

	public Long getDepositHolderId() {
		return depositHolderId;
	}

	public void setDepositHolderId(Long depositHolderId) {
		this.depositHolderId = depositHolderId;
	}

	public String getFlexiRate() {
		return flexiRate;
	}

	public void setFlexiRate(String flexiRate) {
		this.flexiRate = flexiRate;
	}

	public Float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getPayOffType() {
		return payOffType;
	}

	public void setPayOffType(String payOffType) {
		this.payOffType = payOffType;
	}

	public String getInterstPayType() {
		return interstPayType;
	}

	public void setInterstPayType(String interstPayType) {
		this.interstPayType = interstPayType;
	}

	public Float getInterestPercent() {
		return interestPercent;
	}

	public void setInterestPercent(Float interestPercent) {
		this.interestPercent = interestPercent;
	}

	public Float getInterestPayAmount() {
		return interestPayAmount;
	}

	public void setInterestPayAmount(Float interestPayAmount) {
		this.interestPayAmount = interestPayAmount;
	}

	public String getFdPayOffAccount() {
		return fdPayOffAccount;
	}

	public void setFdPayOffAccount(String fdPayOffAccount) {
		this.fdPayOffAccount = fdPayOffAccount;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	public String getBeneficiaryBank() {
		return beneficiaryBank;
	}

	public void setBeneficiaryBank(String beneficiaryBank) {
		this.beneficiaryBank = beneficiaryBank;
	}

	public String getBeneficiaryIfsc() {
		return beneficiaryIfsc;
	}

	public void setBeneficiaryIfsc(String beneficiaryIfsc) {
		this.beneficiaryIfsc = beneficiaryIfsc;
	}

	public Date getPayOffDueDate() {
		return payOffDueDate;
	}

	public void setPayOffDueDate(Date payOffDueDate) {
		this.payOffDueDate = payOffDueDate;
	}	
	
	public List<DepositHolder> getDepositHolder() {
		return depositHolder;
	}

	public void setDepositHolder(List<DepositHolder> depositHolder) {
		this.depositHolder = depositHolder;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public Double getTotalPenalty() {
		return totalPenalty;
	}

	public void setTotalPenalty(Double totalPenalty) {
		this.totalPenalty = totalPenalty;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDataList() {
		return dataList;
	}

	public void setDataList(String dataList) {
		this.dataList = dataList;
	}

	public Double getAddAmount() {
		return addAmount;
	}

	public void setAddAmount(Double addAmount) {
		this.addAmount = addAmount;
	}

	public String getStatusTenure() {
		return statusTenure;
	}

	public void setStatusTenure(String statusTenure) {
		this.statusTenure = statusTenure;
	}

	public Date getMaturityDateNew() {
		return maturityDateNew;
	}

	public void setMaturityDateNew(Date newMaturityDate) {
		this.maturityDateNew = newMaturityDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	private String nomineeName;
	
	public String getNomineeName() {
		return nomineeName;
	}

	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	public Date getNewMaturityDate() {
		return newMaturityDate;
	}

	public void setNewMaturityDate(Date newMaturityDate) {
		this.newMaturityDate = newMaturityDate;
	}

	public Double getAmountToTransfer() {
		return amountToTransfer;
	}

	public void setAmountToTransfer(Double amountToTransfer) {
		this.amountToTransfer = amountToTransfer;
	}

	public Double getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(Double emiAmount) {
		this.emiAmount = emiAmount;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDepositConversion() {
		return depositConversion;
	}

	public void setDepositConversion(String depositConversion) {
		this.depositConversion = depositConversion;
	}

	public Integer getDeductionDay() {
		return deductionDay;
	}

	public void setDeductionDay(Integer deductionDay) {
		this.deductionDay = deductionDay;
	}

}
