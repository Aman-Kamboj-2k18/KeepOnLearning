package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;



@Component
public class TDSForm {

	private Long customerId;
	
	private String customerName;

	private Double tdsAmount; // If deduction applicable then deduction amount, otherwise it will be zero
	
	private Double calculatedTDSAmount; // calculated amount

	private Date tdsCalcDate;
	
	private Integer tdsReportSubmitted;
	
	private Integer tdsDeducted;
	
	private String financialYear;

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
																																																																																																																																																																																					

	
}
