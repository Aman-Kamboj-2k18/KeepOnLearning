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
@Table(name = "bankPaymentDetails")
@XmlRootElement

public class BankPaymentDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private  Long bankPaymentId;
	
	private Long nomineeId;

	private Long customerId;
	
	private Long depositId;

	private Long depositHolderID;
	
	private Date amountPaidDate;
	
	private Double amount;

	private String chequeDDNumber;

	private Date chequeDDdate;

	private String Bank;

	private String branch;

    private String savingCurrentAccountNumber;
    
    private Integer isPaid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBankPaymentId() {
		return bankPaymentId;
	}

	public void setBankPaymentId(Long bankPaymentId) {
		this.bankPaymentId = bankPaymentId;
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

	
	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	public Long getDepositHolderID() {
		return depositHolderID;
	}

	public void setDepositHolderID(Long depositHolderID) {
		this.depositHolderID = depositHolderID;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public Date getAmountPaidDate() {
		return amountPaidDate;
	}

	public void setAmountPaidDate(Date amountPaidDate) {
		this.amountPaidDate = amountPaidDate;
	}

	public String getSavingCurrentAccountNumber() {
		return savingCurrentAccountNumber;
	}

	public void setSavingCurrentAccountNumber(String savingCurrentAccountNumber) {
		this.savingCurrentAccountNumber = savingCurrentAccountNumber;
	}

	public Integer getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Integer isPaid) {
		this.isPaid = isPaid;
	}
}
