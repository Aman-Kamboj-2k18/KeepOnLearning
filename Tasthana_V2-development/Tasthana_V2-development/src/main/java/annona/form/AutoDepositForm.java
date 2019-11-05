package annona.form;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.springframework.stereotype.Component;

import annona.domain.AccountDetails;
import annona.domain.DepositHolder;

@Component
public class AutoDepositForm {

private Long id;
	
	private String accountNumber;
	
	

	private String linkedAccountNumber;
	
	private Double depositAmount;
	
	private String paymentType; //Daily,monthy,quarterly,half yearly,yearly
	
	private String tenureType; //months/years
	
	private Integer tenure;
	
	private String paymentMode;// debit from saving acc, check book,cash/dd 
	
	private Date dueDate;
	
	private Float interestRate;
	
	private String flexiRate; //yes/no
	
	private Double maturityAmount; // expected maturity amount
	
	private Date maturityDate;

	private String payOffInterestType; //monthy,quarterly,half yearly,yearly
	
	private Date payOffDueDate;

	private String depositType; //single, joint,consortium
	
	private String status;//open//close
	
    private String form;
    
    private byte[] formFile;
    
    private String formStatus;
    
    private Date createdDate;
    
    private Date modifiedDate;
    
    private Double maturityAmountOnClosing;
        
    private Date closingDate;
    
    private Double prematurePanaltyAmount;
    
    private Double modifyPanalityAmount;
    
    private String depositCategory;
    
    private String approvalStatus;
    
    private String comment;
    
    private double currentBalance;
    
    private String depositCurrency;
    
    private Float modifiedInterestRate;
    
    private String transactionId;
    
    private String createdBy;
    
    private String customerId;
    
    private String customerName;
    
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

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
