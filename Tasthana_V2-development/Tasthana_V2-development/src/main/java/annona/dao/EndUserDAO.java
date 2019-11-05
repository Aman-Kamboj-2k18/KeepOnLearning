package annona.dao;

import java.util.Collection;

import java.util.List;

import javax.persistence.TypedQuery;

import annona.domain.EndUser;
import annona.domain.ManageMenuPreference;
import annona.domain.Menu;
import annona.domain.Permission;
import annona.domain.Role;
import annona.domain.RolePermissionMenu;
import annona.dto.ManageRoleDTO;

public interface EndUserDAO {

	/**
	 * Method to get User details
	 * 
	 * @param username
	 * @return
	 */
	public TypedQuery<EndUser> findByUsername(String username);

	/**
	 * Method to get User details
	 * 
	 * @param username
	 * @param email
	 * @return
	 */
	public TypedQuery<EndUser> findByUsernameAndEmail(String username, String email);

	/**
	 * Method to get all users
	 * 
	 * @return
	 */
	public TypedQuery<EndUser> getAllUsers();

	/**
	 * Method to get bank employees details
	 * 
	 * @return
	 */
	public TypedQuery<EndUser> getByBankEmp();

	/**
	 * Method to get pending bank employees details
	 * 
	 * @return
	 */
	public TypedQuery<EndUser> getByRole();

	/**
	 * Method to get bank employee details
	 * 
	 * @return
	 */
	public TypedQuery<EndUser> getByRoleList();

	/**
	 * Method to save users details
	 * 
	 * @return
	 */
	public EndUser createUser(EndUser user);

	/**
	 * Method to get all users details
	 * 
	 * @return
	 */
	public EndUser findId(Long id);

	/**
	 * Method to get all users details
	 * 
	 * @return
	 */
	public EndUser GetUser(String userName);

	/**
	 * Method to merge user data
	 */
	public void update(EndUser endUser);

	/**
	 * Method to get approval manager details
	 * 
	 * @return
	 */
	public TypedQuery<EndUser> getByApprovalMng();

	public TypedQuery<EndUser> getByVPList();

	/**
	 * Method to get List of all users
	 * 
	 * @return
	 */
	public Collection<EndUser> getList();

	/**
	 * Method to get Users for Blocking/Unblocking
	 * 
	 * @return
	 */
	List<EndUser> getUsersForBlockUnblock();

	/**
	 * Method to display USers list for Approval Block/Unblock
	 * 
	 * @return
	 */
	List<EndUser> getUsersForBlockUnblockApproval();

	/**
	 * Method to display BankEmp,ApprovalMng to Block/Unblock/Renew
	 * 
	 * @return
	 */
	List<EndUser> getUsersForBlockUnblockRenew();

	/**
	 * Method to get all users except Admin
	 */
	List<EndUser> getUsersExceptAdmin();

	/**
	 * Method to validate user by username and password
	 */
	List<EndUser> findByUsernameAndPwd(String username, String password);

	public List<EndUser> getSuspendedCustomerList(String status, Long role);
	
	/*public List<EndUser> getActiveBankEmployeeList1();*/

	public List<EndUser> searchUser(String bankId, String userName, String contactNo, String email, Integer role);

	public EndUser getByUserName(String userName);

	public List<EndUser> getAllApprovedUser();

	public EndUser findUserByCustomerId(Long customerId);

	public EndUser findUserByBankId(String bankId);
	/*----------------------------------------Developer Menu -------------------------------------------------*/

	public Role createRole(Role role);

	public Role updateRole(Role role);

	public Role findByRoleName(String roleName);

	public List<Role> findAll();

	public Role findById(Long id);

	public Boolean existRoleName(String roleName, Long id);

	public List<Menu> findAllMenu();

	public Menu findByNameAndUrlPattern(String name, String urlPattern);

	public Menu findByMenuId(Long id);

	public Permission findByPermissionId(Long id);

	public Permission savePermission(Permission permission);

	public Permission updatePermission(Permission permission);

	public Menu saveMenu(Menu menu);

	public List<Permission> findAllPermission();

	public Menu updateMenu(Menu menu);

	public List<Menu> findByMenuChildId(Long subMenuItemId);

	public List<RolePermissionMenu> saveRolePermissionMenu(List<RolePermissionMenu> permission);

	public void deleteByRoleIdAndMenuId(Long roleId, Long menuId);
	
	public void deleteByRoleId(Long roleId);

	public List<RolePermissionMenu> getByRoleIdAndMenuAndPermissionDetails(Long roleId);

	public List<Menu> getSubMenuAndAllMenu();

	public List<Menu> findByRoleIds(List<Role> role);

	public List<Menu> findByRoleId(Long roleId);

	public Menu findByMenuAndRoleId(Long roleId, Long menuId);
	/*-----------------------------------------Developer Menu End---------------------------------------------------*/
	public List<Menu> findByRoleIdAndOnlyMenus(Long roleId);
	public List<ManageMenuPreference> findAllByMenuIdManageMenuPreference(Long roleId);

	public ManageMenuPreference findByMenuIdAndRoleIdManageMenuPreference(Long menuId, Long roleId);

	public List<ManageMenuPreference> saveManageMenuPreferencesList(List<ManageRoleDTO> manageRoleDTO);

	public Boolean findByRoleIdInMenuPrefrences(Long roleId);
	
	public Menu findBySubMenuId(Long id);
	
	public Menu findByMenuIdAndChildSubMenuNull(Long id);
	public Menu findByMenuIdAndChildSubMenuNullWithRoleId(Long id,Long roleId);


	public List<EndUser> searchEmployees(String bankId, String userName, String contactNo, String email, String status);
	public List<EndUser> getSuspendedEndUserList();

	
	/*public List<EndUser> getSuspendedEmployeeList();
	
	public List<EndUser> getAppprovalPendingEmployeeList();*/
}
