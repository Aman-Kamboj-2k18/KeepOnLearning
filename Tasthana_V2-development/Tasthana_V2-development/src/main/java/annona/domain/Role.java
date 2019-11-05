package annona.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Configurable;

@Entity
@Configurable
@Table(name = "role")
@XmlRootElement
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String role;

	private String roleDisplayName;

	private Boolean isActive;

	private String createdBy;

	private String modifiedBy;

	private Date createdOn;

	private Date modifiedOn;
	
	private Integer rightsTOApprove;
	
	private Double amountLimit;

	/*---------------------------------------Menu-------------------------------------------- */

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ROLE_MENU", joinColumns = {
			@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "MENU_ID", referencedColumnName = "ID") })
	private List<Menu> menu;

	/*
	 * -----------------------------------------------------------------------------
	 * ----
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public String getRoleDisplayName() {
		return roleDisplayName;
	}

	public void setRoleDisplayName(String roleDisplayName) {
		this.roleDisplayName = roleDisplayName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}

	public Integer getRightsTOApprove() {
		return rightsTOApprove;
	}

	public void setRightsTOApprove(Integer rightsTOApprove) {
		this.rightsTOApprove = rightsTOApprove;
	}

	public Double getAmountLimit() {
		return amountLimit;
	}

	public void setAmountLimit(Double amountLimit) {
		this.amountLimit = amountLimit;
	}
	
	

}
