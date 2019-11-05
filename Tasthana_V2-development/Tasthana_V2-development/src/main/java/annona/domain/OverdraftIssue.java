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
@Table(name = "overdraftIssue")
@XmlRootElement
public class OverdraftIssue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long depositId;
	
	private Date issueDate;
	
   private Double sanctionedAmount;
	
	private Integer sanctionedPercentage;
	
	private String overdraftNumber;
	
	private Double withdrawableAmount;
	
	private Integer isActive;
	 
	private Double totalAmountWithdrawn;
	
	private Integer tenureInDays;
	
	private Integer tenureInMonths;
	
	private Integer tenureInYears;
	
	private Float InterestRate;
	
	private Double AmountToReturn;
	
	private Date OverdraftEndDate;
	
	private Double EMIAmount;
	
	private Integer IsEMI;
	
	private Double totalAmountPaid;
	
	public Double getTotalAmountPaid() {
		return totalAmountPaid;
	}

	public void setTotalAmountPaid(Double totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}

	public Integer getIsEMI() {
		return IsEMI;
	}

	public void setIsEMI(Integer isEMI) {
		IsEMI = isEMI;
	}

	public Float getInterestRate() {
		return InterestRate;
	}

	public void setInterestRate(Float interestRate) {
		InterestRate = interestRate;
	}

	public Double getAmountToReturn() {
		return AmountToReturn;
	}

	public void setAmountToReturn(Double amountToReturn) {
		AmountToReturn = amountToReturn;
	}

	public Date getOverdraftEndDate() {
		return OverdraftEndDate;
	}

	public void setOverdraftEndDate(Date overdraftEndDate) {
		OverdraftEndDate = overdraftEndDate;
	}

	public Double getEMIAmount() {
		return EMIAmount;
	}

	public void setEMIAmount(Double eMIAmount) {
		EMIAmount = eMIAmount;
	}

	

	public Integer getTenureInDays() {
		return tenureInDays;
	}

	public void setTenureInDays(Integer tenureInDays) {
		this.tenureInDays = tenureInDays;
	}

	public Integer getTenureInMonths() {
		return tenureInMonths;
	}

	public void setTenureInMonths(Integer tenureInMonths) {
		this.tenureInMonths = tenureInMonths;
	}

	public Integer getTenureInYears() {
		return tenureInYears;
	}

	public void setTenureInYears(Integer tenureInYears) {
		this.tenureInYears = tenureInYears;
	}

	public Double getTotalAmountWithdrawn() {
		return totalAmountWithdrawn;
	}

	public void setTotalAmountWithdrawn(Double totalAmountWithdrawn) {
		this.totalAmountWithdrawn = totalAmountWithdrawn;
	}

	public Double getWithdrawableAmount() {
		return withdrawableAmount;
	}

	public void setWithdrawableAmount(Double withdrawableAmount) {
		this.withdrawableAmount = withdrawableAmount;
	}

	

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

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

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	

	public Double getSanctionedAmount() {
		return sanctionedAmount;
	}

	public void setSanctionedAmount(Double sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	public Integer getSanctionedPercentage() {
		return sanctionedPercentage;
	}

	public void setSanctionedPercentage(Integer sanctionedPercentage) {
		this.sanctionedPercentage = sanctionedPercentage;
	}

	public String getOverdraftNumber() {
		return overdraftNumber;
	}

	public void setOverdraftNumber(String overdraftNumber) {
		this.overdraftNumber = overdraftNumber;
	}

	
	
	
	

}
