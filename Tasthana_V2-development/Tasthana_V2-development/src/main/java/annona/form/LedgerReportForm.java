package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class LedgerReportForm {

    private Long id;
	
	private Long journalId;
	
	private Long depositId;
	
	private String fdAccountNo;

	private Date ledgerDateDebit;
	
	private String particularsDebit;
	
	private Double debitAmount;

	private Date ledgerDateCredit;
	
	private String particularsCredit;
	
	private Double creditAmount;
	
	private Date fromDate;
	
	private Date toDate;
	private Long customerId;
	
	private String accountNumber;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

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
	

	public String getFdAccountNo() {
		return fdAccountNo;
	}

	public void setFdAccountNo(String fdAccountNo) {
		this.fdAccountNo = fdAccountNo;
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

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	
	
}
