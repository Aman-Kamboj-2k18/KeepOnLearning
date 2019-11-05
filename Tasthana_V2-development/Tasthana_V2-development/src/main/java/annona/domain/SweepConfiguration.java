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
@Table(name = "sweepconfiguration")
@XmlRootElement

public class SweepConfiguration {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;
	
	private String sweepInType; // Fixed Interest & Fixed Tenure || Fixed Interest & Dynamic Tenure || Dynamic Interest & Fixed Tenure || Dynamic Interest & Dynamic Tenure

	private Integer minimumSavingBalanceForSweepIn;
	
	private Integer minimumAmountRequiredForSweepIn; 
	
	private Integer isSweepOutAllowed;    
	
	private Integer isActive;  
	
	private Date closingDate;  
	
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

	public String getSweepInType() {
		return sweepInType;
	}

	public void setSweepInType(String sweepInType) {
		this.sweepInType = sweepInType;
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

	public Integer getIsSweepOutAllowed() {
		return isSweepOutAllowed;
	}

	public void setIsSweepOutAllowed(Integer isSweepOutAllowed) {
		this.isSweepOutAllowed = isSweepOutAllowed;
	}
	
	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
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

