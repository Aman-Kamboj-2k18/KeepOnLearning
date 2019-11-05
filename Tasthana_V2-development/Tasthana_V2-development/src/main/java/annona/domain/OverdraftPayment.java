package annona.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table(name = "overdraftPayment")
@XmlRootElement
public class OverdraftPayment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long depositId;
	
	private Long overdraftId;
	
	private Date paymentDate;
	
    private Double paymentAmount;
    
    private String paymentMode;
	
	private Long paymentModeId;
	
	private String bank;
	
	private String branch;
	 
	private String chequeDDNumber;
	
	private Date chequeDDDate;
	
	private String ifscCode;
	
	private String linkedAccountNumberForFundTransfer;
	
	private String linkedAccountTypeForFundTransfer;
	
	private String cardType;
	
	private Long cardNo;
	
	private String cardExpiryDate;
	
	private Integer cardCVV;
	
	
	private String transactionId;

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

	public Long getOverdraftId() {
		return overdraftId;
	}

	public void setOverdraftId(Long overdraftId) {
		this.overdraftId = overdraftId;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
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

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getChequeDDNumber() {
		return chequeDDNumber;
	}

	public void setChequeDDNumber(String chequeDDNumber) {
		this.chequeDDNumber = chequeDDNumber;
	}

	public Date getChequeDDDate() {
		return chequeDDDate;
	}

	public void setChequeDDDate(Date chequeDDDate) {
		this.chequeDDDate = chequeDDDate;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getLinkedAccountNumberForFundTransfer() {
		return linkedAccountNumberForFundTransfer;
	}

	public void setLinkedAccountNumberForFundTransfer(String linkedAccountNumberForFundTransfer) {
		this.linkedAccountNumberForFundTransfer = linkedAccountNumberForFundTransfer;
	}

	public String getLinkedAccountTypeForFundTransfer() {
		return linkedAccountTypeForFundTransfer;
	}

	public void setLinkedAccountTypeForFundTransfer(String linkedAccountTypeForFundTransfer) {
		this.linkedAccountTypeForFundTransfer = linkedAccountTypeForFundTransfer;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public Integer getCardCVV() {
		return cardCVV;
	}

	public void setCardCVV(Integer cardCVV) {
		this.cardCVV = cardCVV;
	}
	
}
