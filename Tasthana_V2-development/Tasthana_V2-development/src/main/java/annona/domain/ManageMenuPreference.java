package annona.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@SuppressWarnings("serial")
@Entity
@Table(name = "MANAGE_MENU_PREFERENCE")
public class ManageMenuPreference implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ROLE_ID")
	private Long roleId;

	@Column(name = "MENU_ID")
	private Long menuId;

	@Column(name = "MENU_PREFERENCE_INDEX")
	private Integer menuPrefrenceIndex;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private List<Long> subMenuId = new ArrayList<Long>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Integer getMenuPrefrenceIndex() {
		return menuPrefrenceIndex;
	}

	public void setMenuPrefrenceIndex(Integer menuPrefrenceIndex) {
		this.menuPrefrenceIndex = menuPrefrenceIndex;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public List<Long> getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(List<Long> subMenuId) {
		this.subMenuId = subMenuId;
	}
	
	

}
