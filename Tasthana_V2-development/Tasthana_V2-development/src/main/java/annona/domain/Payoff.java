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
@Table(name = "Payoff")
@XmlRootElement

public class Payoff {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long depositId;
	
	private Date payoffDate;
	
	private String payoffType; // Monthly/Quarterly/SemiAnnually/Annually/EndOfTenure 
	
	private Double totalPayoffAmt;
	
	private String transactionId;

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

	public Date getPayoffDate() {
		return payoffDate;
	}

	public void setPayoffDate(Date payoffDate) {
		this.payoffDate = payoffDate;
	}

	public String getPayoffType() {
		return payoffType;
	}

	public void setPayoffType(String payoffType) {
		this.payoffType = payoffType;
	}

	public Double getTotalPayoffAmt() {
		return totalPayoffAmt;
	}

	public void setTotalPayoffAmt(Double totalPayoffAmt) {
		this.totalPayoffAmt = totalPayoffAmt;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
