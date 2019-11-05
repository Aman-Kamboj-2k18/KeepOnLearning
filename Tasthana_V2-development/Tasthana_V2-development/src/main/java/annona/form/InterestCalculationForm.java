package annona.form;

import org.springframework.stereotype.Component;

@Component
public class InterestCalculationForm {

	 private Double principalAmount;

	 private Integer tenure;
	 
	 private Double interest;
	 
	 private String tenureType;
	 
	 private Float rateOfInterest;
	 	 
	 private Double principalAmountTenure;

	 private Integer tenureTenure;
	 
	 private Double interestTenure;
	 
	 private String tenureTypeTenure;
	 
	 private Float rateOfInterestTenure;
	 	
	public Double getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(Double principalAmount) {
		this.principalAmount = principalAmount;
	}

	public Integer getTenure() {
		return tenure;
	}

	public void setTenure(Integer tenure) {
		this.tenure = tenure;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public String getTenureType() {
		return tenureType;
	}

	public void setTenureType(String tenureType) {
		this.tenureType = tenureType;
	}

	public Float getRateOfInterest() {
		return rateOfInterest;
	}

	public void setRateOfInterest(Float rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	public Double getPrincipalAmountTenure() {
		return principalAmountTenure;
	}

	public void setPrincipalAmountTenure(Double principalAmountTenure) {
		this.principalAmountTenure = principalAmountTenure;
	}

	public Integer getTenureTenure() {
		return tenureTenure;
	}

	public void setTenureTenure(Integer tenureTenure) {
		this.tenureTenure = tenureTenure;
	}

	public Double getInterestTenure() {
		return interestTenure;
	}

	public void setInterestTenure(Double interestTenure) {
		this.interestTenure = interestTenure;
	}

	public String getTenureTypeTenure() {
		return tenureTypeTenure;
	}

	public void setTenureTypeTenure(String tenureTypeTenure) {
		this.tenureTypeTenure = tenureTypeTenure;
	}

	public Float getRateOfInterestTenure() {
		return rateOfInterestTenure;
	}

	public void setRateOfInterestTenure(Float rateOfInterestTenure) {
		this.rateOfInterestTenure = rateOfInterestTenure;
	}

	
	
}
