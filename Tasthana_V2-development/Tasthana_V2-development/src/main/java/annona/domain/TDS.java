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
@Table(name = "tds")
@XmlRootElement

public class TDS {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

//	private Long depositId;
//	
//	private String depositAccountNo;
	
	private Long customerId;

	private Double tdsAmount; // If deduction applicable then deduction amount, otherwise it will be zero
	
	private Double calculatedTDSAmount; // calculated amount

	private Date tdsCalcDate;
	
	private Integer tdsReportSubmitted;
	
	private Integer tdsDeducted;
	
	private String financialYear;
	
	@Column(columnDefinition = "int default 0")
	private Integer partlyCalculated;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Double getTdsAmount() {
		return tdsAmount;
	}

	public void setTdsAmount(Double tdsAmount) {
		this.tdsAmount = tdsAmount;
	}

	public Double getCalculatedTDSAmount() {
		return calculatedTDSAmount;
	}

	public void setCalculatedTDSAmount(Double calculatedTDSAmount) {
		this.calculatedTDSAmount = calculatedTDSAmount;
	}
	
	public Date getTdsCalcDate() {
		return tdsCalcDate;
	}

	public void setTdsCalcDate(Date tdsCalcDate) {
		this.tdsCalcDate = tdsCalcDate;
	}

	public Integer getTdsReportSubmitted() {
		return tdsReportSubmitted;
	}

	public void setTdsReportSubmitted(Integer tdsReportSubmitted) {
		this.tdsReportSubmitted = tdsReportSubmitted;
	}

	public Integer getTdsDeducted() {
		return tdsDeducted;
	}

	public void setTdsDeducted(Integer tdsDeducted) {
		this.tdsDeducted = tdsDeducted;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public Integer getPartlyCalculated() {
		return partlyCalculated;
	}

	public void setPartlyCalculated(Integer partlyCalculated) {
		this.partlyCalculated = partlyCalculated;
	}

}
