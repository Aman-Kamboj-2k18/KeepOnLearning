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
@Table(name = "bankPaidDetails")
@XmlRootElement

public class BankPaidDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long bankPaidId;
	
	private  Long bankPaymentId;
	
	private Long bankPaymentDetailsId;
	
	private Long nomineeId;

	private Long customerId;

	private Long depositHolderID;
	
	private Date amountPaidDate;
	
	private Double amount;
	
	private String paymentMode;
	
	private Long paymentModeId;


	private String chequeDDNumber;

	private Date chequeDDdate;

	private String Bank;   // this bank is for DD and cheque and fund transfer bank also 
	 
	private String branch;

    private String savingCurrentAccountNumber;
    
    // Maturity Fund Transfer In Same or Different Bank
 	//---------------------------------------------- 
 	private String maturityFundTransferAccHolderName;
 	
 	private String maturityFundTransferAccNumber;
 	
 	//private String maturityFundTransferBankName;

 	private String maturityFundTransferBankIFSCCode;
 	//----------------------------------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBankPaidId() {
		return bankPaidId;
	}

	public void setBankPaidId(Long bankPaidId) {
		this.bankPaidId = bankPaidId;
	}

	public Long getBankPaymentId() {
		return bankPaymentId;
	}

	public void setBankPaymentId(Long bankPaymentId) {
		this.bankPaymentId = bankPaymentId;
	}

	public Long getBankPaymentDetailsId() {
		return bankPaymentDetailsId;
	}

	public void setBankPaymentDetailsId(Long bankPaymentDetailsId) {
		this.bankPaymentDetailsId = bankPaymentDetailsId;
	}

	public Long getNomineeId() {
		return nomineeId;
	}

	public void setNomineeId(Long nomineeId) {
		this.nomineeId = nomineeId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getDepositHolderID() {
		return depositHolderID;
	}

	public void setDepositHolderID(Long depositHolderID) {
		this.depositHolderID = depositHolderID;
	}

	public Date getAmountPaidDate() {
		return amountPaidDate;
	}

	public void setAmountPaidDate(Date amountPaidDate) {
		this.amountPaidDate = amountPaidDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public String getSavingCurrentAccountNumber() {
		return savingCurrentAccountNumber;
	}

	public void setSavingCurrentAccountNumber(String savingCurrentAccountNumber) {
		this.savingCurrentAccountNumber = savingCurrentAccountNumber;
	}

	public String getMaturityFundTransferAccHolderName() {
		return maturityFundTransferAccHolderName;
	}

	public void setMaturityFundTransferAccHolderName(String maturityFundTransferAccHolderName) {
		this.maturityFundTransferAccHolderName = maturityFundTransferAccHolderName;
	}

	public String getMaturityFundTransferAccNumber() {
		return maturityFundTransferAccNumber;
	}

	public void setMaturityFundTransferAccNumber(String maturityFundTransferAccNumber) {
		this.maturityFundTransferAccNumber = maturityFundTransferAccNumber;
	}

	public String getMaturityFundTransferBankIFSCCode() {
		return maturityFundTransferBankIFSCCode;
	}

	public void setMaturityFundTransferBankIFSCCode(String maturityFundTransferBankIFSCCode) {
		this.maturityFundTransferBankIFSCCode = maturityFundTransferBankIFSCCode;
	}
    
    
}
