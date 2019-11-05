package annona.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table
@XmlRootElement
public class DepositModificationMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long depositId;

	private Double depositAmount;

	private String paymentType; // Daily,monthly,quarterly,half yearly,yearly

	private String tenureType; // months/years

	private Integer tenure;

	private String paymentMode;// debit from saving acc, check book,cash/dd

	private Float interestRate; // change if flexi or else same as deposit table
	
	private Integer stopPayment;

	private Date modifiedDate;

    private String modificationNo;
    
    private String modifiedBy;
    
    private String transactionId;
    
    private Date maturityDate;
    
    private Double maturityAmount;
    
    private String depositConversion;
	
	private Integer deductionDay;
    
	private Integer isConideredForPenalty;

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

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
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

	public Integer getTenure() {
		return tenure;
	}

	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
	}

	public Integer getStopPayment() {
		return stopPayment;
	}

	public void setStopPayment(Integer stopPayment) {
		this.stopPayment = stopPayment;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModificationNo() {
		return modificationNo;
	}

	public void setModificationNo(String modificationNo) {
		this.modificationNo = modificationNo;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Double getMaturityAmount() {
		return maturityAmount;
	}

	public void setMaturityAmount(Double maturityAmount) {
		this.maturityAmount = maturityAmount;
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

	public Integer getIsConideredForPenalty() {
		return isConideredForPenalty;
	}

	public void setIsConideredForPenalty(Integer isConideredForPenalty) {
		this.isConideredForPenalty = isConideredForPenalty;
	}
	
	
}
