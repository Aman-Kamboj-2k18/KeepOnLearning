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
@Table(name = "bankPaid")
@XmlRootElement

public class BankPaid {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long depositID;

	private Double amount;
	
	private Date paymentDate;
	
	private String comment;
	
	private Long BankPaymentId;


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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getBankPaymentId() {
		return BankPaymentId;
	}

	public void setBankPaymentId(Long bankPaymentId) {
		BankPaymentId = bankPaymentId;
	}

}
