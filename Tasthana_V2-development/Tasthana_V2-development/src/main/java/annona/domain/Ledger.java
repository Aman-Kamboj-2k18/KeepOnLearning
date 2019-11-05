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
@Table(name = "Ledger")
@XmlRootElement
public class Ledger {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long journalId;

	private Long depositId; // If depositId null, that means entry has not come from our application

	private Long customerId; //

	private Date ledgerDateDebit;

	private String particularsDebit;

	private Date ledgerDateCredit;

	private String particularsCredit;
	
	private String debitGLCode;
	
	private String creditGLCode;
	
	private Double debitAmount;

	private Double creditAmount;

	private Double depositBalance;

//	private String glNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJournalId() {
		return journalId;
	}

	public void setJournalId(Long journalId) {
		this.journalId = journalId;
	}

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	public Date getLedgerDateDebit() {
		return ledgerDateDebit;
	}

	public void setLedgerDateDebit(Date ledgerDateDebit) {
		this.ledgerDateDebit = ledgerDateDebit;
	}

	public String getParticularsDebit() {
		return particularsDebit;
	}

	public void setParticularsDebit(String particularsDebit) {
		this.particularsDebit = particularsDebit;
	}

	public Double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}

	public Date getLedgerDateCredit() {
		return ledgerDateCredit;
	}

	public void setLedgerDateCredit(Date ledgerDateCredit) {
		this.ledgerDateCredit = ledgerDateCredit;
	}

	public String getParticularsCredit() {
		return particularsCredit;
	}

	public void setParticularsCredit(String particularsCredit) {
		this.particularsCredit = particularsCredit;
	}

	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Double getDepositBalance() {
		return depositBalance;
	}

	public void setDepositBalance(Double depositBalance) {
		this.depositBalance = depositBalance;
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

//	public String getGlNumber() {
//		return glNumber;
//	}
//
//	public void setGlNumber(String glNumber) {
//		this.glNumber = glNumber;
//	}

}
