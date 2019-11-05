package annona.dto;

import java.util.List;

public class ManageMenuPreferencesDTO {

	private Long menuId;
	private Integer menuIndex;
	private List<Long> subMenuIds;
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Integer getMenuIndex() {
		return menuIndex;
	}

	public void setMenuIndex(Integer menuIndex) {
		this.menuIndex = menuIndex;
	}

	public List<Long> getSubMenuIds() {
		return subMenuIds;
	}

	public void setSubMenuIds(List<Long> subMenuIds) {
		this.subMenuIds = subMenuIds;
	}
	
	
	
}