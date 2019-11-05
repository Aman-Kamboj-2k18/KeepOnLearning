package annona.domain;

import java.util.Date;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

/**
 * @author INDIAN
 *
 */
@Entity
@Configurable
@Table(name = "customercategory")
@XmlRootElement

public class CustomerCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private String customerCategory;

	private String createdBy;

	private Date createdDate;

	private String isActive;
	
    private String citizen;
	
	private String nriAccountType; 
	
	private transient Boolean riFlag;
	
	private transient Boolean nriFlag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public Boolean getRiFlag() {
		return riFlag;
	}

	public void setRiFlag(Boolean riFlag) {
		this.riFlag = riFlag;
	}

	public Boolean getNriFlag() {
		return nriFlag;
	}

	public void setNriFlag(Boolean nriFlag) {
		this.nriFlag = nriFlag;
	}

	

	

	
	
}
