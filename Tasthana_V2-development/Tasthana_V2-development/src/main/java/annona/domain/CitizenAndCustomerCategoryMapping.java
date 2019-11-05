package annona.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.*;

@Entity
@Table
public class CitizenAndCustomerCategoryMapping {

	//----------Start- General Information of Product------------
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String citizen;
		
	private String nriAccountType;
	
	private Long customerCategoryId;
	
	private String createdBy;

	private Date createdDate;
	
	private String modifiedBy;

	private Date modifiedDate;
	
	private transient String customerCategoryIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getCustomerCategoryId() {
		return customerCategoryId;
	}

	public void setCustomerCategoryId(Long customerCategoryId) {
		this.customerCategoryId = customerCategoryId;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCustomerCategoryIds() {
		return customerCategoryIds;
	}

	public void setCustomerCategoryIds(String customerCategoryIds) {
		this.customerCategoryIds = customerCategoryIds;
	}
	
	

}
