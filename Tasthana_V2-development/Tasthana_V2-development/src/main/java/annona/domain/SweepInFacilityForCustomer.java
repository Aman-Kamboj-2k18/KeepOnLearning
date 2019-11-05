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
@Table(name = "sweepInFacilityForCustomer")
@XmlRootElement

public class SweepInFacilityForCustomer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;
	
	private Integer isSweepInConfigureedByCustomer;
	
	private Integer isSweepInRestrictedByBank;
	
	private Long accountId;

	private Long customerId;
	
	private String tenure; // String: 1 Year, 2 month, 5 Day
	
	private Integer tenureInDays; // actual tenure in days. We will calculate the interest on that value
	
	private Float initialInterestRate;
	
	private Integer minimumSavingBalanceForSweepIn;
	
	private Integer minimumAmountRequiredForSweepIn; 
	
	private String createdBy;

	private Date createdDate;
	
	private Date modifiedDate;
	
	private String modifiedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIsSweepInConfigureedByCustomer() {
		return isSweepInConfigureedByCustomer;
	}

	public void setIsSweepInConfigureedByCustomer(Integer isSweepInConfigureedByCustomer) {
		this.isSweepInConfigureedByCustomer = isSweepInConfigureedByCustomer;
	}

	public Integer getIsSweepInRestrictedByBank() {
		return isSweepInRestrictedByBank;
	}

	public void setIsSweepInRestrictedByBank(Integer isSweepInRestrictedByBank) {
		this.isSweepInRestrictedByBank = isSweepInRestrictedByBank;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public Integer getTenureInDays() {
		return tenureInDays;
	}

	public void setTenureInDays(Integer tenureInDays) {
		this.tenureInDays = tenureInDays;
	}

	public Float getInitialInterestRate() {
		return initialInterestRate;
	}

	public void setInitialInterestRate(Float initialInterestRate) {
		this.initialInterestRate = initialInterestRate;
	}

	public Integer getMinimumSavingBalanceForSweepIn() {
		return minimumSavingBalanceForSweepIn;
	}

	public void setMinimumSavingBalanceForSweepIn(Integer minimumSavingBalanceForSweepIn) {
		this.minimumSavingBalanceForSweepIn = minimumSavingBalanceForSweepIn;
	}

	public Integer getMinimumAmountRequiredForSweepIn() {
		return minimumAmountRequiredForSweepIn;
	}

	public void setMinimumAmountRequiredForSweepIn(Integer minimumAmountRequiredForSweepIn) {
		this.minimumAmountRequiredForSweepIn = minimumAmountRequiredForSweepIn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}

