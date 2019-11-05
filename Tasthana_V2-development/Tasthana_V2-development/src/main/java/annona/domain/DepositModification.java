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
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table
@XmlRootElement
public class DepositModification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long depositId;

	private Long depositHolderId;

	private Double depositAmount;

	private String paymentType; // Daily,monthly,quarterly,half yearly,yearly

	private String tenureType; // months/years

	private Integer tenure;

	private String paymentMode;// debit from saving acc, check book,cash/dd

	private Float interestRate; // change if flexi or else same as deposit table

	private Date modifiedDate;

	private String payOffInterestType; //(Part/Percent)
	
	private Float payOffInterestAmt;
	
	private Float payOffInterestPercent;
	
//	private Float payOffActualAmount;
	
	private String payOffType; // Monthly/Quarterly/SemiAnnually/Annually/EndOfTenure 
	
	private String payOffAccountType; //DepositAccount/SameBank/DiffBank
	
	private String payOffTransferType; //(NEFT/IMPS/RTGS)
	
	private String payOffNameOnBankAccount;
	
	private String payOffAccountNumber;
	
	private String payOffBankName;
	
	private String payOffBankIFSCCode;
	
	private Date maturityDate;
	
    private String  depositType; // Single, Joint
    
    private String modificationNo;
    
    private String modifiedBy;
    
    private String transactionId;

    private Integer stopPayment;

    private String depositConversion;
	
	private Integer deductionDay;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	
	public String getPayOffInterestType() {
		return payOffInterestType;
	}

	public void setPayOffInterestType(String payOffInterestType) {
		this.payOffInterestType = payOffInterestType;
	}

	public Float getPayOffInterestAmt() {
		return payOffInterestAmt;
	}

	public void setPayOffInterestAmt(Float payOffInterestAmt) {
		this.payOffInterestAmt = payOffInterestAmt;
	}

	public Float getPayOffInterestPercent() {
		return payOffInterestPercent;
	}

	public void setPayOffInterestPercent(Float payOffInterestPercent) {
		this.payOffInterestPercent = payOffInterestPercent;
	}
//
//	public Float getPayOffActualAmount() {
//		return payOffActualAmount;
//	}
//
//	public void setPayOffActualAmount(Float payOffActualAmount) {
//		this.payOffActualAmount = payOffActualAmount;
//	}
//	
	public String getPayOffType() {
		return payOffType;
	}

	public void setPayOffType(String payOffType) {
		this.payOffType = payOffType;
	}

	public String getPayOffAccountType() {
		return payOffAccountType;
	}

	public void setPayOffAccountType(String payOffAccountType) {
		this.payOffAccountType = payOffAccountType;
	}

	public String getPayOffTransferType() {
		return payOffTransferType;
	}

	public void setPayOffTransferType(String payOffTransferType) {
		this.payOffTransferType = payOffTransferType;
	}

	public String getPayOffNameOnBankAccount() {
		return payOffNameOnBankAccount;
	}

	public void setPayOffNameOnBankAccount(String payOffNameOnBankAccount) {
		this.payOffNameOnBankAccount = payOffNameOnBankAccount;
	}

	public String getPayOffAccountNumber() {
		return payOffAccountNumber;
	}

	public void setPayOffAccountNumber(String payOffAccountNumber) {
		this.payOffAccountNumber = payOffAccountNumber;
	}

	public String getPayOffBankName() {
		return payOffBankName;
	}

	public void setPayOffBankName(String payOffBankName) {
		this.payOffBankName = payOffBankName;
	}

	public String getPayOffBankIFSCCode() {
		return payOffBankIFSCCode;
	}

	public void setPayOffBankIFSCCode(String payOffBankIFSCCode) {
		this.payOffBankIFSCCode = payOffBankIFSCCode;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	
	public String getDepositType() {
		return depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public String getModificationNo() {
		return modificationNo;
	}

	public void setModificationNo(String modificationNo) {
		this.modificationNo = modificationNo;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getStopPayment() {
		return stopPayment;
	}

	public void setStopPayment(Integer stopPayment) {
		this.stopPayment = stopPayment;
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
