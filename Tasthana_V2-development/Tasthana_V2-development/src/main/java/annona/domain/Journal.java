package annona.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

import annona.utility.Constants;

@Entity
@Configurable
@Table(name = "Journal")
@XmlRootElement

public class Journal {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long serialNo; // This will LastNo+1 and it will be same for Debit and credit for single transaction. it will act as transaction
	
	private Long depositId;
	
	private Long customerId;

	private Date journalDate;
	
	private String particulars;
	
	private String debitGLCode;
	
	private Double debitAmount;
	
	private String creditGLCode;
	
	private Double creditAmount;
	
	//private String glNumber;
	private String event;
	
	private Integer isPosted;
	
	private String transaction;
	
	transient String depositAccountNumber;
	
	private String fromAccountNumber;
	
	private String toAccountNumber;

	private Double depositBalance;
	
	private Long mopId;
	
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

	public Date getJournalDate() {
		return journalDate;
	}

	public void setJournalDate(Date journalDate) {
		this.journalDate = journalDate;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public Double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}

	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getDebitGLCode() {
		return debitGLCode;
	}

	public void setDebitGLCode(String debitGLCode) {
		this.debitGLCode = debitGLCode;
	}

	public String getCreditGLCode() {
		return creditGLCode;
	}

	public void setCreditGLCode(String creditGLCode) {
		this.creditGLCode = creditGLCode;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getDepositAccountNumber() {
		return depositAccountNumber;
	}

	public void setDepositAccountNumber(String depositAccountNumber) {
		this.depositAccountNumber = depositAccountNumber;
	}

	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public Integer getIsPosted() {
		return isPosted;
	}

	public void setIsPosted(Integer isPosted) {
		this.isPosted = isPosted;
	}

	public String getFromAccountNumber() {
		return fromAccountNumber;
	}

	public void setFromAccountNumber(String fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}

	public String getToAccountNumber() {
		return toAccountNumber;
	}

	public void setToAccountNumber(String toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}

	public Double getDepositBalance() {
		return depositBalance;
	}

	public void setDepositBalance(Double depositBalance) {
		this.depositBalance = depositBalance;
	}

	public Long getMopId() {
		return mopId;
	}

	public void setMopId(Long mopId) {
		this.mopId = mopId;
	}
	
}

