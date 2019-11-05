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
@Table(name = "UnSuccessfulOverdraft")
@XmlRootElement

public class UnSuccessfulOverdraft {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private Date unsuccessPaymentDate;

	private Long depositId;

	private Double emiAmount;

	private String overdraftAccountNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getUnsuccessPaymentDate() {
		return unsuccessPaymentDate;
	}

	public void setUnsuccessPaymentDate(Date unsuccessPaymentDate) {
		this.unsuccessPaymentDate = unsuccessPaymentDate;
	}

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	public Double getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(Double emiAmount) {
		this.emiAmount = emiAmount;
	}

	public String getOverdraftAccountNumber() {
		return overdraftAccountNumber;
	}

	public void setOverdraftAccountNumber(String overdraftAccountNumber) {
		this.overdraftAccountNumber = overdraftAccountNumber;
	}
	
}
