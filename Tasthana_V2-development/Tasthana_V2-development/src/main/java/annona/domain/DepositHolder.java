package annona.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table
@XmlRootElement
public class DepositHolder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private Long depositId;

	private Long customerId;

	private String depositHolderStatus;// primary/secondary

	private Float contribution;

	private String payOffAccountType; // DepositAccount/SameBank/DiffBank

	private String interestType; // (Part/Percent)

	private Float interestAmt;

	private Float interestPercent;

	private String transferType; // (NEFT/IMPS/RTGS)

	private String nameOnBankAccount;

	private String accountNumber;

	private String bankName;

	private String ifscCode;

	private Double distAmtOnMaturity;

	private String deathCertificateSubmitted;

	private Double emiAmount;

	private String relationship;

	private String depositHolderCategory;
	
	private Integer isAmountTransferredOnMaturity;
	
	private Integer isAmountTransferredToNominee;
	
    private String citizen;
	
	private String nriAccountType;
	
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

	public String getPayOffAccountType() {
		return payOffAccountType;
	}

	public void setPayOffAccountType(String payOffAccountType) {
		this.payOffAccountType = payOffAccountType;
	}

	public String getInterestType() {
		return interestType;
	}

	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}

	public Float getInterestAmt() {
		return interestAmt;
	}

	public void setInterestAmt(Float interestAmt) {
		this.interestAmt = interestAmt;
	}

	public Float getInterestPercent() {
		return interestPercent;
	}

	public void setInterestPercent(Float interestPercent) {
		this.interestPercent = interestPercent;
	}

	public String getTransferType() {
		return transferType;
	}

	public void setTransferType(String transferType) {
		this.transferType = transferType;
	}

	public String getNameOnBankAccount() {
		return nameOnBankAccount;
	}

	public void setNameOnBankAccount(String nameOnBankAccount) {
		this.nameOnBankAccount = nameOnBankAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

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

	public Double getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(Double emiAmount) {
		this.emiAmount = emiAmount;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getDepositHolderCategory() {
		return depositHolderCategory;
	}

	public void setDepositHolderCategory(String depositHolderCategory) {
		this.depositHolderCategory = depositHolderCategory;
	}

	public Integer getIsAmountTransferredOnMaturity() {
		return isAmountTransferredOnMaturity;
	}

	public void setIsAmountTransferredOnMaturity(Integer isAmountTransferredOnMaturity) {
		this.isAmountTransferredOnMaturity = isAmountTransferredOnMaturity;
	}

	public Integer getIsAmountTransferredToNominee() {
		return isAmountTransferredToNominee;
	}

	public void setIsAmountTransferredToNominee(Integer isAmountTransferredToNominee) {
		this.isAmountTransferredToNominee = isAmountTransferredToNominee;
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
	
	
}
