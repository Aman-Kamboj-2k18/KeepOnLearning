package annona.form;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import annona.domain.AccountDetails;

@Component
public class BankPaymentForm {
	private String action;
	private Long depositId;
    private Long nomineeId;
	private Long depositHolderId;
	private Double amount;

	private String accountNumber;

	private Long customerId;
	private String customerName;
	private String address;
	private String email;
	private String contactNum;
	private Date dateOfBirth;

	private String paymentMode;

	private double accountBalance;

	private String chequeNo;

	private Date chequeDate;

	private String chequeBank;

	private String chequeBranch;

	private String fdPayOffType;

	private String cardNo;

	private String deathCertificateSubmitted;

	private String nomineeName;

	private String nomineeAge;

	private String nomineeAddress;

	private String nomineeRelationship;

	private String gaurdianName;

	private String gaurdianAddress;

	private String gaurdianRelation;

	private Double distAmtOnMaturity;
	
	private Integer isAmountTransferredOnMaturity;
	
	private Integer isPaid;

	private Long bankPaymnetDetailsId;
	
	private Long bankPaymentId;
	
	private Date amountPaidDate;
	
	private String reverseEmiOnBasis;
	
	private String depositClassification;
	
	private String taxSavingDeposit;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	public Long getNomineeId() {
		return nomineeId;
	}

	public void setNomineeId(Long nomineeId) {
		this.nomineeId = nomineeId;
	}

	public Long getDepositHolderId() {
		return depositHolderId;
	}

	public void setDepositHolderId(Long depositHolderId) {
		this.depositHolderId = depositHolderId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(double accountBalance) {
		this.accountBalance = accountBalance;
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getDeathCertificateSubmitted() {
		return deathCertificateSubmitted;
	}

	public void setDeathCertificateSubmitted(String deathCertificateSubmitted) {
		this.deathCertificateSubmitted = deathCertificateSubmitted;
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

	public String getGaurdianName() {
		return gaurdianName;
	}

	public void setGaurdianName(String gaurdianName) {
		this.gaurdianName = gaurdianName;
	}

	public String getGaurdianAddress() {
		return gaurdianAddress;
	}

	public void setGaurdianAddress(String gaurdianAddress) {
		this.gaurdianAddress = gaurdianAddress;
	}

	public String getGaurdianRelation() {
		return gaurdianRelation;
	}

	public void setGaurdianRelation(String gaurdianRelation) {
		this.gaurdianRelation = gaurdianRelation;
	}

	public Double getDistAmtOnMaturity() {
		return distAmtOnMaturity;
	}

	public void setDistAmtOnMaturity(Double distAmtOnMaturity) {
		this.distAmtOnMaturity = distAmtOnMaturity;
	}

	public Integer getIsAmountTransferredOnMaturity() {
		return isAmountTransferredOnMaturity;
	}

	public void setIsAmountTransferredOnMaturity(Integer isAmountTransferredOnMaturity) {
		this.isAmountTransferredOnMaturity = isAmountTransferredOnMaturity;
	}

	public Integer getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Integer isPaid) {
		this.isPaid = isPaid;
	}

	public Long getBankPaymnetDetailsId() {
		return bankPaymnetDetailsId;
	}

	public void setBankPaymnetDetailsId(Long bankPaymnetDetailsId) {
		this.bankPaymnetDetailsId = bankPaymnetDetailsId;
	}

	public Long getBankPaymentId() {
		return bankPaymentId;
	}

	public void setBankPaymentId(Long bankPaymentId) {
		this.bankPaymentId = bankPaymentId;
	}

	public Date getAmountPaidDate() {
		return amountPaidDate;
	}

	public void setAmountPaidDate(Date amountPaidDate) {
		this.amountPaidDate = amountPaidDate;
	}

	public String getReverseEmiOnBasis() {
		return reverseEmiOnBasis;
	}

	public void setReverseEmiOnBasis(String reverseEmiOnBasis) {
		this.reverseEmiOnBasis = reverseEmiOnBasis;
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


	
}
