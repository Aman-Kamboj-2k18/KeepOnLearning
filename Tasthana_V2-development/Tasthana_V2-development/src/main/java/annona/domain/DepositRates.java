package annona.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class DepositRates {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private Integer maturityPeriodFromYear;
	
	private Integer maturityPeriodFromMonth;
	
	private Integer maturityPeriodFromDays;
	
	private Integer calMaturityPeriodFromInDays;
	
	private Integer maturityPeriodToYear;
	
	private Integer maturityPeriodToMonth;
	
	private Integer maturityPeriodToDays;
	
	private Integer calMaturityPeriodToInDays;
	
	private String maturityPeriodSign;

	private String category;
	
	private Float interestRate;

	private Date effectiveDate;
	
	private String transactionId;
	
	private Integer daysFrom;
	
	private Integer daysTo;
	
	private Float rate;
	
	private String currency;
	
	private Long ratePeriodsId;
	
	private String description;
	
    private Double amountSlabFrom;
	
	private Double amountSlabTo;
	
	private String depositClassification;
	
	private String citizen;
	
	private String nriAccountType; 
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getMaturityPeriodFromYear() {
		return maturityPeriodFromYear;
	}

	public void setMaturityPeriodFromYear(Integer maturityPeriodFromYear) {
		this.maturityPeriodFromYear = maturityPeriodFromYear;
	}
	
	public Integer getMaturityPeriodFromMonth() {
		return maturityPeriodFromMonth;
	}

	public void setMaturityPeriodFromMonth(Integer maturityPeriodFromMonth) {
		this.maturityPeriodFromMonth = maturityPeriodFromMonth;
	}
	
	public Integer getMaturityPeriodFromDays() {
		return maturityPeriodFromDays;
	}
	
	public void setMaturityPeriodFromDays(Integer maturityPeriodFromDays) {
		this.maturityPeriodFromDays = maturityPeriodFromDays;
	}
	
	public Integer getCalMaturityPeriodFromInDays() {
		return calMaturityPeriodFromInDays;
	}

	public void setCalMaturityPeriodFromInDays(Integer calMaturityPeriodFromInDays) {
		this.calMaturityPeriodFromInDays = calMaturityPeriodFromInDays;
	}
	
	public Integer getMaturityPeriodToYear() {
		return maturityPeriodToYear;
	}

	public void setMaturityPeriodToYear(Integer maturityPeriodToYear) {
		this.maturityPeriodToYear = maturityPeriodToYear;
	}
	
	public Integer getMaturityPeriodToMonth() {
		return maturityPeriodToMonth;
	}

	public void setMaturityPeriodToMonth(Integer maturityPeriodToMonth) {
		this.maturityPeriodToMonth = maturityPeriodToMonth;
	}
	
	public Integer getMaturityPeriodToDays() {
		return maturityPeriodToDays;
	}

	public void setMaturityPeriodToDays(Integer maturityPeriodToDays) {
		this.maturityPeriodToDays = maturityPeriodToDays;
	}
	
	public Integer getCalMaturityPeriodToInDays() {
		return calMaturityPeriodToInDays;
	}

	public void setCalMaturityPeriodToInDays(Integer calMaturityPeriodToInDays) {
		this.calMaturityPeriodToInDays = calMaturityPeriodToInDays;
	}
	
	public String getMaturityPeriodSign() {
		return maturityPeriodSign;
	}

	public void setMaturityPeriodSign(String maturityPeriodSign) {
		this.maturityPeriodSign = maturityPeriodSign;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public Float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Float interestRate) {
		this.interestRate = interestRate;
	}
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getDaysFrom() {
		return daysFrom;
	}

	public void setDaysFrom(Integer daysFrom) {
		this.daysFrom = daysFrom;
	}

	public Integer getDaysTo() {
		return daysTo;
	}

	public void setDaysTo(Integer daysTo) {
		this.daysTo = daysTo;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Long getRatePeriodsId() {
		return ratePeriodsId;
	}

	public void setRatePeriodsId(Long ratePeriodsId) {
		this.ratePeriodsId = ratePeriodsId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmountSlabFrom() {
		return amountSlabFrom;
	}

	public void setAmountSlabFrom(Double amountSlabFrom) {
		this.amountSlabFrom = amountSlabFrom;
	}

	public Double getAmountSlabTo() {
		return amountSlabTo;
	}

	public void setAmountSlabTo(Double amountSlabTo) {
		this.amountSlabTo = amountSlabTo;
	}

	public String getDepositClassification() {
		return depositClassification;
	}

	public void setDepositClassification(String depositClassification) {
		this.depositClassification = depositClassification;
	}

	public String getCitizen() {
		return citizen;
	}

	public void setCitizen(String citizen) {
		this.citizen = citizen;
	}

	public String getNriAccountType() {
		return nriAccountType;
	}

	public void setNriAccountType(String nriAccountType) {
		this.nriAccountType = nriAccountType;
	}

	
	
}
