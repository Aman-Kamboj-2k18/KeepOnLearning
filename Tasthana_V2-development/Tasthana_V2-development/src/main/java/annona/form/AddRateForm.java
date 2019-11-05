package annona.form;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import annona.domain.DepositRates;

@Component
public class AddRateForm {

	private Long id;

	private Integer fromDay;
	
	private Integer toDay;

    private String category;

	private Float depositAmountRates;
	
	private Date effectiveDate;
	
	private String type;
	
	private Float rate;
	
	private String currency;
	
	private String description;
	
	private Float[] rateList; 
	
	private List<DepositRates> interestRate;

	private Long ratePeriodsId;
	
    private Double amountSlabFrom;
	
	private Double amountSlabTo;
	
	private String depositClassification;
	
	private String citizen;
		
	private String nriAccountType; 
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Integer getFromDay() {
		return fromDay;
	}

	public void setFromDay(Integer fromDay) {
		this.fromDay = fromDay;
	}

	public Integer getToDay() {
		return toDay;
	}

	public void setToDay(Integer toDay) {
		this.toDay = toDay;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	

	public Float getDepositAmountRates() {
		return depositAmountRates;
	}

	public void setDepositAmountRates(Float depositAmountRates) {
		this.depositAmountRates = depositAmountRates;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float[] getRateList() {
		return rateList;
	}

	public void setRateList(Float[] rateList) {
		this.rateList = rateList;
	}

	public List<DepositRates> getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(List<DepositRates> rates) {
		this.interestRate = rates;
	}

	public Long getRatePeriodsId() {
		return ratePeriodsId;
	}

	public void setRatePeriodsId(Long ratePeriodsId) {
		this.ratePeriodsId = ratePeriodsId;
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
