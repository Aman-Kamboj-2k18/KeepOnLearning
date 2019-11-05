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
@Table(name = "bankPayment")
@XmlRootElement

public class TDSDistribution {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long customerId;

	private Long depositID;

	private Long depositHolderID;

	private String customerName;

	private String nomineeName;

	private String nomineeAddress;

	private String nomineeRelationship;

	private String nomineeAge;

	private Double amount;

	private String action;

	private String modeOfPayment;

	private String chequeDDNumber;

	private Date chequeDDdate;

	private String Bank;

	private String branch;

	private Date dateOfDepositNomineeAmt;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDepositID() {
		return depositID;
	}

	public void setDepositID(Long depositID) {
		this.depositID = depositID;
	}

	public Long getDepositHolderID() {
		return depositHolderID;
	}

	public void setDepositHolderID(Long depositHolderID) {
		this.depositHolderID = depositHolderID;
	}

	public String getNomineeName() {
		return nomineeName;
	}

	public void setNomineeName(String nomineeName) {
		this.nomineeName = nomineeName;
	}

	public String getNomineeAddress() {
		return nomineeAddress;
	}

	public void setNomineeAddress(String nomineeAddress) {
		this.nomineeAddress = nomineeAddress;
	}

	public String getNomineeRelationship() {
		return nomineeRelationship;
	}

	public void setNomineeRelationship(String nomineeRelationship) {
		this.nomineeRelationship = nomineeRelationship;
	}

	public String getNomineeAge() {
		return nomineeAge;
	}

	public void setNomineeAge(String nomineeAge) {
		this.nomineeAge = nomineeAge;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getModeOfPayment() {
		return modeOfPayment;
	}

	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
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

	public Date getDateOfDepositNomineeAmt() {
		return dateOfDepositNomineeAmt;
	}

	public void setDateOfDepositNomineeAmt(Date dateOfDepositNomineeAmt) {
		this.dateOfDepositNomineeAmt = dateOfDepositNomineeAmt;
	}

}
