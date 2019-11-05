package annona.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class ManageRoleDTO implements Serializable {

	private String menuId;
	private String permissionId;
	private Long roleId;
	private String jsonData;
	private List<ManageMenuPreferencesDTO> manageMenuPreferences;

	public String getMenuId() {
		return menuId;
	}

	
	
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public List<ManageMenuPreferencesDTO> getManageMenuPreferences() {
		return manageMenuPreferences;
	}

	public void setManageMenuPreferences(List<ManageMenuPreferencesDTO> manageMenuPreferences) {
		this.manageMenuPreferences = manageMenuPreferences;
	}

}
