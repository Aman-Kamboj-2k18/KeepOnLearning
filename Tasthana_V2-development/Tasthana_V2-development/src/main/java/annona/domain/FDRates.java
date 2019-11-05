package annona.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class FDRates {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	public Long getId() {
		return id;
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
