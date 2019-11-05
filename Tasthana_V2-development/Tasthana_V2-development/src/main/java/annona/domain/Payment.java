package annona.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long depositId ;

	private Long depositHolderId ;  // Primary holder Id
	
	private Date paymentDate;
	
	private Double amountPaid;
	
	private String paymentMode;
	
	private Long paymentModeId;
	
	private String chequeDDNumber;
	
	private Date chequeDDdate;
	
	private String Bank;
	
	private String branch;
	
	private String cardType;
	
	private Long cardNo;
	
	private String cardExpiryDate;
	
	private Integer cardCVV;
	
	private String transferType;//(NEFT/IMPS/RTGS/DD/Cheque/Cash)
	
	private String linkedAccNoForFundTransfer;
	
	private String linkedAccTypeForFundTransfer;
	
	private String transactionId;
	
	private String createdBy;
	
	private Integer topUp;
	
	private String nameOnBankAccount;
	
	private String accountNumber;
	
	private String bankName;
	
	private String ifscCode;
	
	private Double depositAmount;
	
	private String paymentMadeByHolderIds;  // Who have done the payment, may be multiple holders can pay 


	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Distribution> paymentDistributions;
	
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

	public Long getDepositHolderId() {
		return depositHolderId;
	}

	public void setDepositHolderId(Long depositHolderId) {
		this.depositHolderId = depositHolderId;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
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

	public List<Distribution> getPaymentDistributions() {
		return paymentDistributions;
	}

	public void setPaymentDistributions(List<Distribution> paymentDistributions) {
		this.paymentDistributions = paymentDistributions;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Long getCardNo() {
		return cardNo;
	}

	public void setCardNo(Long cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public Integer getCardCvv() {
		return cardCVV;
	}

	public void setCardCvv(Integer cardCVV) {
		this.cardCVV = cardCVV;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getLinkedAccNoForFundTransfer() {
		return linkedAccNoForFundTransfer;
	}

	public void setLinkedAccNoForFundTransfer(String linkedAccNoForFundTransfer) {
		this.linkedAccNoForFundTransfer = linkedAccNoForFundTransfer;
	}

	public String getLinkedAccTypeForFundTransfer() {
		return linkedAccNoForFundTransfer;
	}

	public void setLinkedAccTypeForFundTransfer(String linkedAccTypeForFundTransfer) {
		this.linkedAccTypeForFundTransfer = linkedAccTypeForFundTransfer;
	}

	public Integer getCardCVV() {
		return cardCVV;
	}

	public void setCardCVV(Integer cardCVV) {
		this.cardCVV = cardCVV;
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

	public String getNameOnBankAccount() {
		return nameOnBankAccount;
	}

	public void setNameOnBankAccount(String nameOnBankAccount) {
		this.nameOnBankAccount = nameOnBankAccount;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Integer getTopUp() {
		return topUp;
	}

	public void setTopUp(Integer topUp) {
		this.topUp = topUp;
	}

	public String getPaymentMadeByHolderIds() {
		return paymentMadeByHolderIds;
	}

	public void setPaymentMadeByHolderIds(String paymentMadeByHolderIds) {
		this.paymentMadeByHolderIds = paymentMadeByHolderIds;
	}

}
