package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ReportForm {

	private Date fromDate;
	
	private Date toDate;
	
	private Long newCount;
	
	private Double newFixedAmount;
	
	private Double newVariableAmount;
	
	private Double existingFixedAmount;
	
	private Double existingVariableAmount;
	
	private Double withdrawAmount;
	
	private Double penalityAmount;
	
    private Double tdsAmount;
	
	private Double gstAmount;
	
	private Double interestAmount;
	

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


	public Long getNewCount() {
		return newCount;
	}

	public void setNewCount(Long newCount) {
		this.newCount = newCount;
	}

	public Double getNewFixedAmount() {
		return newFixedAmount;
	}

	public void setNewFixedAmount(Double newFixedAmount) {
		this.newFixedAmount = newFixedAmount;
	}

	public Double getNewVariableAmount() {
		return newVariableAmount;
	}

	public void setNewVariableAmount(Double newVariableAmount) {
		this.newVariableAmount = newVariableAmount;
	}

	public Double getExistingFixedAmount() {
		return existingFixedAmount;
	}

	public void setExistingFixedAmount(Double existingFixedAmount) {
		this.existingFixedAmount = existingFixedAmount;
	}

	public Double getExistingVariableAmount() {
		return existingVariableAmount;
	}

	public void setExistingVariableAmount(Double existingVariableAmount) {
		this.existingVariableAmount = existingVariableAmount;
	}

	public Double getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(Double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public Double getPenalityAmount() {
		return penalityAmount;
	}

	public void setPenalityAmount(Double penalityAmount) {
		this.penalityAmount = penalityAmount;
	}

	public Double getTdsAmount() {
		return tdsAmount;
	}

	public void setTdsAmount(Double tdsAmount) {
		this.tdsAmount = tdsAmount;
	}

	public Double getGstAmount() {
		return gstAmount;
	}

	public void setGstAmount(Double gstAmount) {
		this.gstAmount = gstAmount;
	}

	public Double getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(Double interestAmount) {
		this.interestAmount = interestAmount;
	}

	
	
}
