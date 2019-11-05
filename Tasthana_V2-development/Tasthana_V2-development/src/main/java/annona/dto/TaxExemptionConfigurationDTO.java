package annona.dto;

import java.util.Date;

public class TaxExemptionConfigurationDTO {

	private Long id;

	private String effectiveDate;

	private Double exemptionLimitAmount;

	private String exemptionComparisonSign;

	private Integer exemptionAge;

	private Date createdOn;

	private Date modifiedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Double getExemptionLimitAmount() {
		return exemptionLimitAmount;
	}

	public void setExemptionLimitAmount(Double exemptionLimitAmount) {
		this.exemptionLimitAmount = exemptionLimitAmount;
	}

	public String getExemptionComparisonSign() {
		return exemptionComparisonSign;
	}

	public void setExemptionComparisonSign(String exemptionComparisonSign) {
		this.exemptionComparisonSign = exemptionComparisonSign;
	}

	public Integer getExemptionAge() {
		return exemptionAge;
	}

	public void setExemptionAge(Integer exemptionAge) {
		this.exemptionAge = exemptionAge;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
}