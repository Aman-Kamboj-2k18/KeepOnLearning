package annona.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.IndexColumn;

@SuppressWarnings("serial")
@Entity
@Table(name = "menu")
public class Menu implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "NAME")
	private String name;

	@Column(name = "URL_PATTERN")
	private String urlPattern;
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;

	@Column(name = "CREATED_ON")
	private Date createdOn;
	@Column(name = "MODIFIED_ON")
	private Date modifiedOn;

	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	
	@Column(name = "DESCRIPTION",length = 2000)
	private String descripition;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Column(name = "POSSIBLE_ACTION")
	@OrderColumn(name="list_index")
	private List<String> possibleAction = new ArrayList<String>();
	
	@Column(name = "MAX_PRIVILEGES")
	private Integer maxPrivileges;
	@Column(name = "MIN_PRIVILEGES")
	private Integer minPrivileges;

	@Transient
	private String subMenuId;
	
	@Transient
	private Integer menuIndex;

	public Integer getMenuIndex() {
		return menuIndex;
	}

	public void setMenuIndex(Integer menuIndex) {
		this.menuIndex = menuIndex;
	}

	@ManyToOne
	@JoinColumn(name = "menu_parent_id")
	private Menu menu;

	@OneToMany(mappedBy = "menu", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Menu> childMenuItems;
	/*-------------------------------------Permission--------------------------------------------- */

	/*
	 * @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	 * 
	 * @JoinTable(name = "MENU_PERMISSION", joinColumns = {
	 * 
	 * @JoinColumn(name = "MENU_ID", referencedColumnName = "ID") },
	 * inverseJoinColumns = {
	 * 
	 * @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID"),
	 * 
	 * @JoinColumn(table = "Role", referencedColumnName = "ID", name = "ROLE_ID") })
	 */
	@Transient
	private List<Permission> permission;
	
	@Transient
	private String permissions;
	
	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	/*
	 * -----------------------------------------------------------------------------
	 * ----------------
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Menu> getChildMenuItems() {
		return childMenuItems;
	}

	public void setChildMenuItems(List<Menu> childMenuItems) {
		this.childMenuItems = childMenuItems;
	}

	public String getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(String subMenuId) {
		this.subMenuId = subMenuId;
	}

	public List<Permission> getPermission() {
		return permission;
	}

	public void setPermission(List<Permission> permission) {
		this.permission = permission;
	}

	public Integer getMaxPrivileges() {
		return maxPrivileges;
	}

	public void setMaxPrivileges(Integer maxPrivileges) {
		this.maxPrivileges = maxPrivileges;
	}

	public Integer getMinPrivileges() {
		return minPrivileges;
	}

	public void setMinPrivileges(Integer minPrivileges) {
		this.minPrivileges = minPrivileges;
	}

	public String getDescripition() {
		return descripition;
	}

	public void setDescripition(String descripition) {
		this.descripition = descripition;
	}

	public List<String> getPossibleAction() {
		return possibleAction;
	}

	public void setPossibleAction(List<String> possibleAction) {
		this.possibleAction = possibleAction;
	}

	
	
}
