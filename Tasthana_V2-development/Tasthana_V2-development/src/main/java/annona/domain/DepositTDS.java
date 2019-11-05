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
public class DepositTDS {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long depositId;
	
	private Date tdsDate;

	private Double calculatedTDSAmount; // Total TDS of Deposit
	
	private Double actualTDSAmount; // Customer TDS amount contribution wise
	
	private String financialYear;

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

	public Date getTdsDate() {
		return tdsDate;
	}

	public void setTdsDate(Date tdsDate) {
		this.tdsDate = tdsDate;
	}

	public Double getCalculatedTDSAmount() {
		return calculatedTDSAmount;
	}

	public void setCalculatedTDSAmount(Double calculatedTDSAmount) {
		this.calculatedTDSAmount = calculatedTDSAmount;
	}

	public Double getActualTDSAmount() {
		return actualTDSAmount;
	}

	public void setActualTDSAmount(Double actualTDSAmount) {
		this.actualTDSAmount = actualTDSAmount;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	
}
