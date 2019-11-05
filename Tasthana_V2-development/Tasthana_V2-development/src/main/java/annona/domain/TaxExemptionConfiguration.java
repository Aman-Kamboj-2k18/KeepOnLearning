package annona.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "TAX_EXEMPTION_CONFIGURATION")
public class TaxExemptionConfiguration implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	
	
	
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;

	@Column(name = "EXEMPTION_LIMIT_AMOUNT")
	private Double exemptionLimitAmount;
	
	
	
	
	@Column(name = "EXEMPTION_COMPARISON_SIGN")
	private String exemptionComparisonSign;
	
	@Column(name = "EXEMPTION_AGE")
	private Integer exemptionAge;

	@Column(name = "CREATED_ON")
	private Date createdOn;
	
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;
	
	transient String saveExemptionConfigurationList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getExemptionAge() {
		return exemptionAge;
	}

	public void setExemptionAge(Integer exemptionAge) {
		this.exemptionAge = exemptionAge;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getSaveExemptionConfigurationList() {
		return saveExemptionConfigurationList;
	}

	public void setSaveExemptionConfigurationList(String saveExemptionConfigurationList) {
		this.saveExemptionConfigurationList = saveExemptionConfigurationList;
	}
	
	

}
