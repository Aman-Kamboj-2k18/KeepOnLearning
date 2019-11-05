package annona.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table(name = "ReverseEMIPayoffDetails")
@XmlRootElement

public class ReverseEMIPayoffDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long depositId;
	private Long depositHolderId;
	private Long reverseEMIId;
	private Long beneficiaryDetailsId;
	private Double emiAmount;
	private String beneficiaryName;
	private String beneficiaryAccountType;
	private String beneficiaryAccountNumber;
	private String beneficiaryIFSCCode;
	private Double payoffAmount;
	private String payOffBankName;
	private String payOffBranch;
	
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
	public Long getDepositHolderId() {
		return depositHolderId;
	}
	public void setDepositHolderId(Long depositHolderId) {
		this.depositHolderId = depositHolderId;
	}
	public Long getReverseEMIId() {
		return reverseEMIId;
	}
	public void setReverseEMIId(Long reverseEMIId) {
		this.reverseEMIId = reverseEMIId;
	}
	public Long getBeneficiaryDetailsId() {
		return beneficiaryDetailsId;
	}
	public void setBeneficiaryDetailsId(Long beneficiaryDetailsId) {
		this.beneficiaryDetailsId = beneficiaryDetailsId;
	}
	public Double getEmiAmount() {
		return emiAmount;
	}
	public void setEmiAmount(Double emiAmount) {
		this.emiAmount = emiAmount;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getBeneficiaryAccountType() {
		return beneficiaryAccountType;
	}
	public void setBeneficiaryAccountType(String beneficiaryAccountType) {
		this.beneficiaryAccountType = beneficiaryAccountType;
	}
	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}
	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}
	public String getBeneficiaryIFSCCode() {
		return beneficiaryIFSCCode;
	}
	public void setBeneficiaryIFSCCode(String beneficiaryIFSCCode) {
		this.beneficiaryIFSCCode = beneficiaryIFSCCode;
	}
	public Double getPayoffAmount() {
		return payoffAmount;
	}
	public void setPayoffAmount(Double payoffAmount) {
		this.payoffAmount = payoffAmount;
	}
	public String getPayOffBankName() {
		return payOffBankName;
	}
	public void setPayOffBankName(String payOffBankName) {
		this.payOffBankName = payOffBankName;
	}
	public String getPayOffBranch() {
		return payOffBranch;
	}
	public void setPayOffBranch(String payOffBranch) {
		this.payOffBranch = payOffBranch;
	}
	
}
