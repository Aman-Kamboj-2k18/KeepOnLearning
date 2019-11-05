package annona.form;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class EditDepositForm {

	private String status;

	private String statusTenure;
	private Double penalty;
	private Double addAmount;
	private Double totalPenalty;
	private Date maturityDateNew;
	private String depositConversion;
	private Integer deductionDay;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusTenure() {
		return statusTenure;
	}
	public void setStatusTenure(String statusTenure) {
		this.statusTenure = statusTenure;
	}
	public Double getPenalty() {
		return penalty;
	}
	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}
	public Double getAddAmount() {
		return addAmount;
	}
	public void setAddAmount(Double addAmount) {
		this.addAmount = addAmount;
	}
	public Double getTotalPenalty() {
		return totalPenalty;
	}
	public void setTotalPenalty(Double totalPenalty) {
		this.totalPenalty = totalPenalty;
	}
	public Date getMaturityDateNew() {
		return maturityDateNew;
	}
	public void setMaturityDateNew(Date maturityDateNew) {
		this.maturityDateNew = maturityDateNew;
	}
	public String getDepositConversion() {
		return depositConversion;
	}
	public void setDepositConversion(String depositConversion) {
		this.depositConversion = depositConversion;
	}
	public Integer getDeductionDay() {
		return deductionDay;
	}
	public void setDeductionDay(Integer deductionDay) {
		this.deductionDay = deductionDay;
	}
	
	

}
