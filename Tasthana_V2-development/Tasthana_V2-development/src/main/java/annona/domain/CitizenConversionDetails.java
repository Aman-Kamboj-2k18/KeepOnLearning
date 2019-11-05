package annona.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table(name = "CitizenConversionDetails")
@XmlRootElement
public class CitizenConversionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long custId;
	
	private String customerID;

	private Date currentDate;
	
	private String previousCitizen;
	
	private String previousNRIAccountTYpe;
	
	private Date previousCitizenStartDate;
	
	private Date previousCitizenEndDate;
	
	private String currentCitizen;
	
	private String currentNRIAccountType;
	
	private Date createdOn;

	private Date modifiedOn;

	private String createdBy;

	private String modifiedBy;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCustId() {
		return custId;
	}

	public void setCustId(Long custId) {
		this.custId = custId;
	}


	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public String getPreviousCitizen() {
		return previousCitizen;
	}

	public void setPreviousCitizen(String previousCitizen) {
		this.previousCitizen = previousCitizen;
	}

	public String getPreviousNRIAccountTYpe() {
		return previousNRIAccountTYpe;
	}

	public void setPreviousNRIAccountTYpe(String previousNRIAccountTYpe) {
		this.previousNRIAccountTYpe = previousNRIAccountTYpe;
	}

	public Date getPreviousCitizenStartDate() {
		return previousCitizenStartDate;
	}

	public void setPreviousCitizenStartDate(Date previousCitizenStartDate) {
		this.previousCitizenStartDate = previousCitizenStartDate;
	}

	public Date getPreviousCitizenEndDate() {
		return previousCitizenEndDate;
	}

	public void setPreviousCitizenEndDate(Date previousCitizenEndDate) {
		this.previousCitizenEndDate = previousCitizenEndDate;
	}

	public String getCurrentCitizen() {
		return currentCitizen;
	}

	public void setCurrentCitizen(String currentCitizen) {
		this.currentCitizen = currentCitizen;
	}

	public String getCurrentNRIAccountType() {
		return currentNRIAccountType;
	}

	public void setCurrentNRIAccountType(String currentNRIAccountType) {
		this.currentNRIAccountType = currentNRIAccountType;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
}
