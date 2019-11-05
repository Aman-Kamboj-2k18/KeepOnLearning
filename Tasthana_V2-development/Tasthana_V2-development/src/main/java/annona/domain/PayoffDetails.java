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
@Table
@XmlRootElement
public class PayoffDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long payoffId;

	private Long depositId;
	
	private Long depositHolderId;
	
	private String payoffInterestType; //(Part/Percent)
	
	private Double payoffInterestPercent; // declared at a time of deposit creation/modification
	
	private Double payoffInterestAmt; // declared at a time of deposit creation/modification
	
	private Double payoffAmt; // Actual payoff amount of the holder

	private String payoffAccountType; //DepositAccount/SameBank/DiffBank
	
	private String payoffTransferType; //(NEFT/IMPS/RTGS)
	
	private String payoffNameOnBankAccount;
	
	private String payoffAccountNumber;
	
	private String payoffBankName;
	
	private String payoffIFSCCode;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPayoffId() {
		return payoffId;
	}

	public void setPayoffId(Long payoffId) {
		this.payoffId = payoffId;
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

	public String getPayoffInterestType() {
		return payoffInterestType;
	}

	public void setPayoffInterestType(String payoffInterestType) {
		this.payoffInterestType = payoffInterestType;
	}

	public Double getPayoffInterestPercent() {
		return payoffInterestPercent;
	}

	public void setPayoffInterestPercent(Double payoffInterestPercent) {
		this.payoffInterestPercent = payoffInterestPercent;
	}

	public Double getPayoffInterestAmt() {
		return payoffInterestAmt;
	}

	public void setPayoffInterestAmt(Double payoffInterestAmt) {
		this.payoffInterestAmt = payoffInterestAmt;
	}

	public Double getPayoffAmt() {
		return payoffAmt;
	}

	public void setPayoffAmt(Double payoffAmt) {
		this.payoffAmt = payoffAmt;
	}

	public String getPayoffAccountType() {
		return payoffAccountType;
	}

	public void setPayoffAccountType(String payoffAccountType) {
		this.payoffAccountType = payoffAccountType;
	}

	public String getPayoffTransferType() {
		return payoffTransferType;
	}

	public void setPayoffTransferType(String payoffTransferType) {
		this.payoffTransferType = payoffTransferType;
	}

	public String getPayoffNameOnBankAccount() {
		return payoffNameOnBankAccount;
	}

	public void setPayoffNameOnBankAccount(String payoffNameOnBankAccount) {
		this.payoffNameOnBankAccount = payoffNameOnBankAccount;
	}

	public String getPayoffAccountNumber() {
		return payoffAccountNumber;
	}

	public void setPayoffAccountNumber(String payoffAccountNumber) {
		this.payoffAccountNumber = payoffAccountNumber;
	}

	public String getPayoffBankName() {
		return payoffBankName;
	}

	public void setPayoffBankName(String payoffBankName) {
		this.payoffBankName = payoffBankName;
	}

	public String getPayoffIFSCCode() {
		return payoffIFSCCode;
	}

	public void setPayoffIFSCCode(String payoffIFSCCode) {
		this.payoffIFSCCode = payoffIFSCCode;
	}

}
