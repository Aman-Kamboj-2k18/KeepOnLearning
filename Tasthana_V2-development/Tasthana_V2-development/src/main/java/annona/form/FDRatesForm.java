package annona.form;

import java.util.Collection;
import java.util.Date;

import org.springframework.stereotype.Component;

import annona.domain.FDRates;

@Component
public class FDRatesForm {

	private Long id;

	private String category;

	private String customerID;

	private float fdAmount;

	private float fdCurrentAmount;

	private Date fdDeductDate;

	private Date fdDueDate;

	private Date fdTenureDate;

	private float interestAmount;

	private String paymentType;

	private String tenureType;

	private int period;

	private String fdId;

	private Collection<FDRates> fdRates;

	public Collection<FDRates> getFdRates() {
		return fdRates;
	}

	public void setFdRates(Collection<FDRates> fdRates) {
		this.fdRates = fdRates;
	}

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public float getFdCurrentAmount() {
		return fdCurrentAmount;
	}

	public void setFdCurrentAmount(float fdCurrentAmount) {
		this.fdCurrentAmount = fdCurrentAmount;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getTenureType() {
		return tenureType;
	}

	public void setTenureType(String tenureType) {
		this.tenureType = tenureType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public float getFdAmount() {
		return fdAmount;
	}

	public void setFdAmount(float fdAmount) {
		this.fdAmount = fdAmount;
	}

	public Date getFdDeductDate() {
		return fdDeductDate;
	}

	public void setFdDeductDate(Date fdDeductDate) {
		this.fdDeductDate = fdDeductDate;
	}

	public Date getFdDueDate() {
		return fdDueDate;
	}

	public void setFdDueDate(Date fdDueDate) {
		this.fdDueDate = fdDueDate;
	}

	public Date getFdTenureDate() {
		return fdTenureDate;
	}

	public void setFdTenureDate(Date fdTenureDate) {
		this.fdTenureDate = fdTenureDate;
	}

	public float getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(float interestAmount) {
		this.interestAmount = interestAmount;
	}

}
