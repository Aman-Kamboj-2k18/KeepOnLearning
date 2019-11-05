package annona.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table(name = "DepositWiseCustomerTDS")
@XmlRootElement

public class DepositWiseCustomerTDS {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long tdsId;

	private Long customerId;
	
	private Long depositId;
	
	private String depositAccountNo;
	
	private Date tdsDate;
	
	private Double tdsRate;

	private Double calculatedTDSAmount; // Total TDS of Deposit
	
	private Double contributedTDSAmount; // Customer TDS amount contribution wise
	
	private String financialYear;
	
	@Column(columnDefinition = "int default 0")
	private Integer isTDSDeducted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTdsId() {
		return tdsId;
	}

	public void setTdsId(Long tdsId) {
		this.tdsId = tdsId;
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

	public String getDepositAccountNo() {
		return depositAccountNo;
	}

	public void setDepositAccountNo(String depositAccountNo) {
		this.depositAccountNo = depositAccountNo;
	}

	public Double getTdsRate() {
		return tdsRate;
	}

	public void setTdsRate(Double tdsRate) {
		this.tdsRate = tdsRate;
	}

	public Double getCalculatedTDSAmount() {
		return calculatedTDSAmount;
	}

	public void setCalculatedTDSAmount(Double calculatedTDSAmount) {
		this.calculatedTDSAmount = calculatedTDSAmount;
	}

	public Double getContributedTDSAmount() {
		return contributedTDSAmount;
	}

	public void setContributedTDSAmount(Double contributedTDSAmount) {
		this.contributedTDSAmount = contributedTDSAmount;
	}

	public Date getTdsDate() {
		return tdsDate;
	}

	public void setTdsDate(Date tdsDate) {
		this.tdsDate = tdsDate;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public Integer getIsTDSDeducted() {
		return isTDSDeducted;
	}

	public void setIsTDSDeducted(Integer isTDSDeducted) {
		this.isTDSDeducted = isTDSDeducted;
	}

}
