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
@Table(name = "ReverseEMI")
@XmlRootElement

public class ReverseEMI {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long depositId;
	private Long depositHolderId;
	private Double emiAmount;
	private String modeOfPayment;
	private String transactionId;
	private String createdBy;
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
	public Double getEmiAmount() {
		return emiAmount;
	}
	public void setEmiAmount(Double emiAmount) {
		this.emiAmount = emiAmount;
	}
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
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
	
}
