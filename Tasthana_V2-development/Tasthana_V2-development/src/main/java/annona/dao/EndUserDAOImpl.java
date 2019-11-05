package annona.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.EndUser;
import annona.domain.ManageMenuPreference;
import annona.domain.Menu;
import annona.domain.Permission;
import annona.domain.Role;
import annona.domain.RolePermissionMenu;
import annona.dto.ManageMenuPreferencesDTO;
import annona.dto.ManageRoleDTO;

@Repository
public class EndUserDAOImpl implements EndUserDAO {

	@PersistenceContext
	EntityManager em;

	public EntityManager entityManager() {
		EntityManager em = new EndUserDAOImpl().em;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public TypedQuery<EndUser> findByUsername(String username) {
		if (username == null || username.length() == 0)
			throw new IllegalArgumentException("The username argument is required");

		TypedQuery<EndUser> q = em.createQuery("SELECT o FROM EndUser AS o WHERE o.userName = :username",
				EndUser.class);
		q.setParameter("username", username);
		return q;
	}

	public TypedQuery<EndUser> findByUsernameAndEmail(String username, String email) {
		if (username == null || username.length() == 0)
			throw new IllegalArgumentException("The username argument is required");

		TypedQuery<EndUser> q = em.createQuery(
				"SELECT o FROM EndUser AS o WHERE o.userName = :username and o.email =:email", EndUser.class);
		q.setParameter("username", username);
		q.setParameter("email", email);
		return q;
	}

	@SuppressWarnings("unchecked")
	public Collection<EndUser> getList() {

		Query query = em.createQuery("SELECT o FROM EndUser o where o.currentRole = 'ROLE_BANKEMP'");

		return (Collection<EndUser>) query.getResultList();
	}

	@Override
	public TypedQuery<EndUser> getByRole() {

		TypedQuery<EndUser> q = em.createQuery(
				"SELECT o FROM EndUser o where (o.status = 'Pending' OR o.status = 'Suspended' OR o.status = 'Closed') ",
				// "SELECT o FROM EndUser o where o.currentRole = 'ROLE_BANKEMP' and (o.status =
				// 'Pending' OR o.status = 'Suspended' OR o.status = 'Closed') ",
				EndUser.class);
		return q;

	}

	@Override
	public TypedQuery<EndUser> getByRoleList() {

		TypedQuery<EndUser> q = em.createQuery("SELECT o FROM EndUser o where o.currentRole = 'ROLE_BANKEMP'",
				EndUser.class);
		return q;

	}

	@Override
	public TypedQuery<EndUser> getByBankEmp() {

		TypedQuery<EndUser> q = em.createQuery(
				"SELECT o FROM EndUser o where o.currentRole = 'ROLE_BANKEMP' and o.status = 'Approved'",
				EndUser.class);
		return q;

	}

	@Override
	public TypedQuery<EndUser> getByApprovalMng() {

		TypedQuery<EndUser> q = em.createQuery("SELECT o FROM EndUser o where o.currentRole = 'ROLE_APPROVALMNG' ",
				EndUser.class);
		return q;

	}

	@Override
	public TypedQuery<EndUser> getByVPList() {

		TypedQuery<EndUser> q = em.createQuery("SELECT o FROM EndUser o where o.currentRole = 'ROLE_VP' ",
				EndUser.class);
		return q;

	}

	public EndUser findId(Long id) {

		return em.find(EndUser.class, id);

	}

	public EndUser findUserByCustomerId(Long customerId) {
		return (EndUser) em.createQuery("from EndUser o where o.customerId = :customerId")
				.setParameter("customerId", customerId).getSingleResult();
	}

	public EndUser findUserByBankId(String bankId) {
		return (EndUser) em.createQuery("from EndUser o where o.bankId = :bankId").setParameter("bankId", bankId)
				.getSingleResult();
	}

	@Transactional
	public EndUser createUser(EndUser user) {

		em.persist(user);
		return user;
	}

	@Transactional
	public void update(EndUser endUser) {

		em.merge(endUser);

		em.flush();
	}

	public EndUser GetUser(String userName) {

		return em.find(EndUser.class, userName);

	}

	public TypedQuery<EndUser> getAllUsers() {

		TypedQuery<EndUser> q = em.createQuery("SELECT o FROM EndUser o where o.currentRole = 'ROLE_USER' ",
				EndUser.class);
		return q;
	}

	/**
	 * Method to get Users for Blocking/Unblocking
	 * 
	 * @return
	 */
	@Override
	public List<EndUser> getUsersForBlockUnblock() {
		TypedQuery<EndUser> q = em.createQuery(
				"SELECT NEW EndUser(o.id,o.userName,o.currentRole,o.accessStatus,o.accExpiryDate,o.accRenewStatus) FROM EndUser o WHERE o.role NOT IN (1,2,3)",
				EndUser.class);
		return q.getResultList();
	}

	/**
	 * Method to get Users for Blocking/Unblocking
	 * 
	 * @return
	 */
	@Override
	public List<EndUser> getUsersForBlockUnblockApproval() {
		TypedQuery<EndUser> q = em.createQuery(
				"SELECT new EndUser(o.id,o.userName,o.currentRole,o.accessStatus,o.accExpiryDate,o.accRenewStatus) FROM EndUser o WHERE o.role NOT IN (1,2,3) and ((o.accessStatus='Block' or o.accessStatus='Unblock') or o.accRenewStatus='Pending')",
				EndUser.class);
		return q.getResultList();
	}

	/**
	 * Method to get BankEmp,ApprovalMng for Blocking/Unblocking/Renewing
	 * 
	 * @return
	 */
	@Override
	public List<EndUser> getUsersForBlockUnblockRenew() {
		TypedQuery<EndUser> q = em.createQuery(
				"SELECT NEW EndUser(o.id,o.userName,o.currentRole,o.accessStatus,o.accExpiryDate,o.accRenewStatus) FROM EndUser o WHERE o.role IN (2,3)",
				EndUser.class);
		return q.getResultList();
	}

	/**
	 * Method to get all users except Admin
	 */
	@Override
	public List<EndUser> getUsersExceptAdmin() {
		TypedQuery<EndUser> q = em.createQuery("SELECT o FROM EndUser o WHERE o.role NOT IN (1)", EndUser.class);
		return q.getResultList();
	}

	/**
	 * Method to validate user with username and password
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public List<EndUser> findByUsernameAndPwd(String username, String password) {
		if (username == null || username.length() == 0 || password == null || password.length() == 0)
			throw new IllegalArgumentException("The username and/or password argument is required");

		TypedQuery<EndUser> q = em.createQuery(
				"SELECT o FROM EndUser AS o WHERE o.userName = :username and o.password =:password", EndUser.class);

		q.setParameter("username", username);
		q.setParameter("password", password);

		return q.getResultList();
	}

	private String buildWhereClause(String bankId, String userName, String contactNo, String email, Integer role) {
		if (userName == "" && bankId == "" && contactNo == "" && email == "")
			return " o.status='Approved' AND o.role=" + role;

		String whereString = "";
		if (userName != "") {
			whereString = " LOWER(o.userName) LIKE :userName AND ";
		}
		if (bankId != "") {
			whereString = whereString + " o.bankId=:bankId AND ";
		}
		if (contactNo != "") {
			whereString = whereString + " o.contactNo=:contactNo AND ";
		}
		if (email != "") {
			whereString = whereString + " LOWER(o.email)=:email AND ";
		}
		whereString = whereString + " o.status='Approved' AND o.role=" + role;

		return whereString;
	}

	public List<EndUser> searchUser(String bankId, String userName, String contactNo, String email, Integer role) {

		userName = userName.toLowerCase();
		email = email.toLowerCase();
		String whereString = this.buildWhereClause(bankId, userName, contactNo, email, role);

		Query query = em.createQuery(
				"SELECT o.id,o.userName,o.email,o.contactNo," + "o.displayName from EndUser as o where " + whereString);

		if (userName != "" || !userName.isEmpty()) {
			query.setParameter("userName", "%" + userName.trim() + "%");
		}
		if (bankId != "" || !bankId.isEmpty()) {
			query.setParameter("bankId", bankId.trim());
		}
		if (email != "" || !email.isEmpty()) {
			query.setParameter("email", email.trim());
		}
		if (contactNo != "" || !contactNo.isEmpty()) {
			query.setParameter("contactNo", contactNo.trim());
		}

		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();

		List<EndUser> endUserList = new ArrayList<EndUser>();
		for (Object[] row : rows) {
			EndUser endUser = new EndUser();
			endUser.setId(Long.valueOf(row[0].toString()));
			endUser.setUserName((String) row[1]);
			endUser.setEmail((String) row[2]);
			endUser.setContactNo((String) row[3]);
			endUser.setDisplayName((String) row[4]);
			endUserList.add(endUser);

		}
		return endUserList;

	}

	@Override
	public List<EndUser> getSuspendedCustomerList(String status, Long role) {

		Query q = em.createNativeQuery(

				"SELECT o.* FROM EndUser o inner join enduser_role r on o.id =  r.enduser_id where o.status=:status and r.role_id  = :roleId",

				EndUser.class);

		q.setParameter("status", status);

		q.setParameter("roleId", role);

		return (List<EndUser>) q.getResultList();
	}


	@Override
	public List<EndUser> getSuspendedEndUserList() {

		Query q = em.createNativeQuery(
				
				"SELECT o.* FROM EndUser o inner join enduser_role r on o.id  =  r.enduser_id where  o.status='Suspended' AND r.role_id IN (SELECT rol.id from role rol where rol.role NOT IN ('ROLE_USER','ROLE_ADMIN'))" 
				,

				

				EndUser.class);
		return (List<EndUser>) q.getResultList();
	}

	
	/*@Override
	public List<EndUser> getActiveBankEmployeeList1() {

		Query q = em.createNativeQuery(
				"SELECT o.* FROM EndUser o where o.status='Approved' and "
				+ "(o.currentRole!='ROLE_USER' and o.currentRole!='ROLE_ADMIN')",
				EndUser.class);


		return (List<EndUser>) q.getResultList();
	}*/
	public EndUser getByUserName(String userName) {

		Query query = em.createQuery("SELECT o From EndUser o where o.userName=:userName");
		query.setParameter("userName", userName);
		return (EndUser) query.getResultList().get(0);
	}

	@Override
	public List<EndUser> getAllApprovedUser() {

		TypedQuery<EndUser> query = em.createQuery(
				"SELECT o FROM EndUser o where o.currentRole = 'ROLE_USER' and o.status = 'Approved'", EndUser.class);
		return (List<EndUser>) query.getResultList();

	}

	/*----------------------------------Developer Menu--------------------------------------*/

	@Override
	@Transactional
	public Role createRole(Role role) {
		em.persist(role);
		return role;
	}

	@Override
	public List<Role> findAll() {
		return em.createQuery("from Role r where r.role != 'ROLE_ADMIN' order by r.id", Role.class).getResultList();
	}

	@Override
	public Role findByRoleName(String roleName) {
		try {
			Query query = em.createQuery("from Role where role = :roleName").setParameter("roleName", roleName);
			return (Role) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Role findById(Long id) {

		return em.find(Role.class, id);
	}

	@Override
	public Boolean existRoleName(String roleName, Long id) {

		Query query = em.createQuery("from Role where role = :roleName and id != :id")
				.setParameter("roleName", roleName).setParameter("id", id);
		List lst = query.getResultList();
		if (lst != null && lst.size() > 0)
			return true;
		else
			return false;
	}

	@Override
	@Transactional
	public Role updateRole(Role role) {
		// TODO Auto-generated method stub
		em.merge(role);
		return role;
	}

	@Override
	public List<Menu> findAllMenu() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
		Root<Menu> rootEntry = cq.from(Menu.class);
		CriteriaQuery<Menu> all = cq.select(rootEntry).where(cb.isNull(rootEntry.get("menu")))
				.orderBy(cb.asc(rootEntry.get("id")));
		TypedQuery<Menu> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

	@Override
	public Menu findByNameAndUrlPattern(String name, String urlPattern) {
		try {
			Query query = em.createQuery("from Menu where name = :name OR urlPattern = :urlPattern")
					.setParameter("name", name).setParameter("urlPattern", urlPattern);
			return (Menu) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public Menu saveMenu(Menu menu) {
		em.persist(menu);
		return menu;
	}

	@Override
	public List<Permission> findAllPermission() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Permission> cq = cb.createQuery(Permission.class);
		Root<Permission> rootEntry = cq.from(Permission.class);
		CriteriaQuery<Permission> all = cq.select(rootEntry).orderBy(cb.asc(rootEntry.get("id")));
		TypedQuery<Permission> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

	@Override
	public Menu findByMenuId(Long id) {
		try {
			Menu menu = em.createQuery("from Menu m where m.id = :menuId", Menu.class).setParameter("menuId", id)
					.getSingleResult();
			if (menu != null)
				return menu;
			else
				return null;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public Menu findByMenuIdAndChildSubMenuNull(Long id) {
		try {
			Menu menu = em.createQuery("from Menu m where m.id = :menuId AND m.menu IS NULL", Menu.class)
					.setParameter("menuId", id).getSingleResult();
			if (menu != null)
				return menu;
			else
				return null;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public Menu findByMenuIdAndChildSubMenuNullWithRoleId(Long id, Long roleId) {
		try {
			List<Menu> newChildMenuList = new ArrayList<>();
			Menu menu = em.createQuery("from Menu m where m.id = :menuId", Menu.class).setParameter("menuId", id)
					.getSingleResult();
			if (menu != null && menu.getChildMenuItems().size() > 0) {
				List<Menu> childMenuList = em
						.createNativeQuery("select * from menu cm where cm.menu_parent_id = :id", Menu.class)
						.setParameter("id", menu.getId()).getResultList();
				for (Menu chidMenu : childMenuList) {
					try {
						Query query = em.createNativeQuery(
								"select * from Role_Permission_Menu rpm where role_id = :roleId AND menu_id =:menuId",
								RolePermissionMenu.class);
						query.setParameter("roleId", roleId);
						query.setParameter("menuId", chidMenu.getId());
						List<RolePermissionMenu> q = query.getResultList();
						if (q.size() > 0) {
							List<Permission> permission = new ArrayList<Permission>();
							String permissions = "";
							for (RolePermissionMenu rm : q) {
								Permission pm = em.find(Permission.class, rm.getPermissionId());
								permissions += pm.getAction() + ",";
								permission.add(pm);
							}
							if (!permissions.equals("")) {
								chidMenu.setPermission(permission);
								chidMenu.setPermissions(permissions);
								newChildMenuList.add(chidMenu);
							}

						}

					} catch (NoResultException e) {
						// TODO: handle exception
					}
				}
				menu.setChildMenuItems(newChildMenuList);
			}
			return menu;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public Permission findByPermissionId(Long id) {
		// TODO Auto-generated method stub
		return em.find(Permission.class, id);
	}

	@Override
	@Transactional
	public Permission savePermission(Permission permission) {
		em.persist(permission);
		return permission;
	}

	@Override
	@Transactional
	public Permission updatePermission(Permission permission) {
		em.merge(permission);
		return permission;
	}

	@Override
	@Transactional
	public Menu updateMenu(Menu menu) {
		em.merge(menu);
		return menu;
	}

	public List<Menu> findByMenuChildId(Long subMenuItemId) {
		Query query = em.createNativeQuery("select * from menu m where  m.menu_parent_id=:id", Menu.class);
		query.setParameter("id", subMenuItemId);
		List<Menu> listMenu = query.getResultList();
		if (listMenu != null && listMenu.size() > 0)
			return listMenu;
		return null;
	}

	@Override
	@Transactional
	public List<RolePermissionMenu> saveRolePermissionMenu(List<RolePermissionMenu> permission) {
		List<RolePermissionMenu> rl = new ArrayList<>();
		for (RolePermissionMenu rolePermissionMenu : permission) {
			em.persist(rolePermissionMenu);
			rl.add(rolePermissionMenu);
		}
		return rl;
	}

	@Override
	@Transactional
	public void deleteByRoleIdAndMenuId(Long roleId, Long menuId) {
		Query query = em.createNativeQuery(
				"select * from ROLE_PERMISSION_MENU where role_Id = :roleId AND menu_Id = :menuId",
				RolePermissionMenu.class);
		query.setParameter("roleId", roleId);
		query.setParameter("menuId", menuId);
		List<RolePermissionMenu> list = query.getResultList();
		if (list != null && list.size() > 0) {
			for (RolePermissionMenu r : list) {
				em.createNativeQuery("DELETE FROM ROLE_PERMISSION_MENU where id =:id ").setParameter("id", r.getId())
						.executeUpdate();
			}
		}

	}

	@Override
	@Transactional
	public void deleteByRoleId(Long roleId) {

		em.createNativeQuery("DELETE FROM ROLE_PERMISSION_MENU where role_Id =:role_Id ")
				.setParameter("role_Id", roleId).executeUpdate();

	}

	@Override
	public List<RolePermissionMenu> getByRoleIdAndMenuAndPermissionDetails(Long roleId) {
		Query query = em.createNativeQuery("select * from ROLE_PERMISSION_MENU where role_Id = :roleId");
		query.setParameter("roleId", roleId);
		List<RolePermissionMenu> list = query.getResultList();
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@Override
	public List<Menu> getSubMenuAndAllMenu() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
		Root<Menu> rootEntry = cq.from(Menu.class);// .where(cb.isNull(rootEntry.get("menu")))
		CriteriaQuery<Menu> all = cq.select(rootEntry).orderBy(cb.asc(rootEntry.get("id")));
		TypedQuery<Menu> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

	@Override
	public List<Menu> findByRoleIds(List<Role> roles) {
		List<Menu> newMenuList = new ArrayList<>();
		for (Role role : roles) {
			List<Long> rpm = em.createQuery(
					"select distinct rpm.menuId from RolePermissionMenu rpm where rpm.roleId = :roleId order by rpm.menuId ASC",
					Long.class).setParameter("roleId", role.getId()).getResultList();
			for (Long r : rpm) {
				Menu m = em.find(Menu.class, r);
				Query query = em.createNativeQuery(
						"select * from Role_Permission_Menu rpm where role_id = :roleId AND menu_id =:menuId",
						RolePermissionMenu.class);
				query.setParameter("roleId", role.getId());
				query.setParameter("menuId", m.getId());
				List<RolePermissionMenu> q = query.getResultList();
				List<Permission> permission = new ArrayList<Permission>();
				for (RolePermissionMenu rm : q) {
					permission.add(em.find(Permission.class, rm.getPermissionId()));
				}
				m.setPermission(permission);
				boolean isExist = newMenuList.contains(m);
				if (!isExist) {
					newMenuList.add(m);
				}
			}
		}
		return newMenuList;
	}

	public List<Menu> findByRoleId(Long roleId) {
		List<Menu> newMenuList = new ArrayList<>();
		List<Long> rpm = em.createQuery(
				"select distinct rpm.menuId from RolePermissionMenu rpm where rpm.roleId = :roleId order by rpm.menuId ASC",
				Long.class).setParameter("roleId", roleId).getResultList();
		for (Long r : rpm) {
			Menu m = em.find(Menu.class, r);

			if (m != null) {
				Query query = em.createNativeQuery(
						"select * from Role_Permission_Menu rpm where role_id = :roleId AND menu_id =:menuId",
						RolePermissionMenu.class);
				query.setParameter("roleId", roleId);
				query.setParameter("menuId", m.getId());
				List<RolePermissionMenu> q = query.getResultList();
				List<Permission> permission = new ArrayList<Permission>();
				/*
				 * for (RolePermissionMenu rm : q) { permission.add(em.find(Permission.class,
				 * rm.getPermissionId())); } m.setPermission(permission);
				 */

				String permissions = "";
				for (RolePermissionMenu rm : q) {
					Permission pm = em.find(Permission.class, rm.getPermissionId());
					permissions += pm.getAction() + ",";
					permission.add(pm);
				}

				m.setPermission(permission);
				m.setPermissions(permissions);
			}

			List<Menu> mofifiedList = new ArrayList<>();
			if (m != null && m.getChildMenuItems().size() > 0) {
				List<Menu> childMenuList = em
						.createNativeQuery("select * from menu cm where cm.menu_parent_id = :id", Menu.class)
						.setParameter("id", m.getId()).getResultList();
				for (Menu chidMenu : childMenuList) {
					try {
						/*
						 * Query query1 = em.createNativeQuery(
						 * "select rm.menu_id from role_menu rm where rm.role_id = :roleId AND rm.menu_id =:menuId"
						 * ); .setParameter("roleId", roleId); query1.setParameter("menuId",
						 * chidMenu.getId()); Object ml = query1.getSingleResult();
						 */
						Query query = em.createNativeQuery(
								"select * from Role_Permission_Menu rpm where role_id = :roleId AND menu_id =:menuId",
								RolePermissionMenu.class);
						query.setParameter("roleId", roleId);
						query.setParameter("menuId", chidMenu.getId());
						List<RolePermissionMenu> q = query.getResultList();
						if (q.size() > 0) {
							List<Permission> permission = new ArrayList<Permission>();
							String permissions = "";
							for (RolePermissionMenu rm : q) {

								Permission pm = em.find(Permission.class, rm.getPermissionId());
								permissions += pm.getAction() + ",";
								permission.add(pm);

								// permission.add(em.find(Permission.class,rm.getPermissionId()));
							}
							if (!permissions.equals("")) {
								chidMenu.setPermission(permission);
								chidMenu.setPermissions(permissions);
								mofifiedList.add(chidMenu);
							}

						}

					} catch (NoResultException e) {

					}
				}
				// if (mofifiedList.size() > 0) {
				m.setChildMenuItems(mofifiedList);
				// }
			}

			if (m != null) {
				boolean isExist = newMenuList.contains(m);
				if (!isExist) {
					newMenuList.add(m);
				}
			}
		}
		return newMenuList;
	}

	public Menu findByMenuAndRoleId(Long roleId, Long menuId) {
		List<Long> roles = em.createQuery(
				"select distinct rpm.menuId from RolePermissionMenu rpm where rpm.roleId = :roleId and rpm.menuId= :menuId order by rpm.menuId ASC",
				Long.class).setParameter("roleId", roleId).setParameter("menuId", menuId).getResultList();
		Long role1;
		if (roles == null || roles.isEmpty()) {
			Menu menu = new Menu();
			return menu;
		} else {
			role1 = roles.get(0);
		}
		Menu m = em.find(Menu.class, role1);
		List<Menu> childMenuList = new ArrayList<>();
		if (m.getChildMenuItems().size() > 0) {
			childMenuList = em.createNativeQuery("select * from menu cm where cm.menu_parent_id = :id", Menu.class)
					.setParameter("id", m.getId()).getResultList();
			for (Menu chidMenu : childMenuList) {
				Query query = em.createNativeQuery(
						"select * from Role_Permission_Menu rpm where role_id = :roleId AND menu_id =:menuId",
						RolePermissionMenu.class);
				query.setParameter("roleId", roleId);
				query.setParameter("menuId", chidMenu.getId());
				List<RolePermissionMenu> q = query.getResultList();
				List<Permission> permission = new ArrayList<Permission>();
				for (RolePermissionMenu rm : q) {
					permission.add(em.find(Permission.class, rm.getPermissionId()));
				}
				chidMenu.setPermission(permission);
			}
			if (childMenuList.size() > 0) {
				m.setChildMenuItems(childMenuList);
			}

		} else {
			Query query = em.createNativeQuery(
					"select * from Role_Permission_Menu rpm where role_id = :roleId AND menu_id =:menuId",
					RolePermissionMenu.class);
			query.setParameter("roleId", roleId);
			query.setParameter("menuId", m.getId());
			List<RolePermissionMenu> q = query.getResultList();
			List<Permission> permission = new ArrayList<Permission>();
			String permissions = "";
			for (RolePermissionMenu rm : q) {
				Permission pm = em.find(Permission.class, rm.getPermissionId());
				permissions += pm.getAction() + ",";
				permission.add(pm);
			}

			m.setPermission(permission);
			m.setPermissions(permissions);
		}
		return m;
	}

	/*--------------------------------------------------------------------------------------------------------*/
	public List<Menu> findByRoleIdAndOnlyMenus(Long roleId) {
		List<Menu> onlyMenus = new ArrayList<Menu>();
		try {
			List<Long> rpm = em.createQuery(
					"select distinct rpm.menuId from RolePermissionMenu rpm where rpm.roleId = :roleId order by rpm.menuId ASC",
					Long.class).setParameter("roleId", roleId).getResultList();

			for (Long r : rpm) {
				try {
					Menu m = em.createQuery("from Menu m where m.id = :menuId AND m.menu IS NULL", Menu.class)
							.setParameter("menuId", r).getSingleResult();
					if (m != null && m.getChildMenuItems().size() > 0) {
						// m.setChildMenuItems(null);
					}

					onlyMenus.add(m);
				} catch (NoResultException e) {

				}
			}

		} catch (Exception e) {
			return null;
		}
		return onlyMenus;

	}

	@Override
	@Transactional
	public List<ManageMenuPreference> saveManageMenuPreferencesList(List<ManageRoleDTO> dto) {
		List<ManageMenuPreference> preferences = new ArrayList<ManageMenuPreference>();
		for (ManageRoleDTO manageRoleDTO : dto) {
			em.createNativeQuery(
					"DELETE FROM managemenupreference_submenuid WHERE managemenupreference_id IN (SELECT id FROM manage_menu_preference where role_id = :roleId)")
					.setParameter("roleId", manageRoleDTO.getRoleId()).executeUpdate();
			em.createQuery("delete from ManageMenuPreference mmp where mmp.roleId = :roleId")
					.setParameter("roleId", manageRoleDTO.getRoleId()).executeUpdate();
			for (ManageMenuPreferencesDTO manageMenuPreferencesDTO : manageRoleDTO.getManageMenuPreferences()) {
				ManageMenuPreference manageMenuPreference = new ManageMenuPreference();
				manageMenuPreference.setRoleId(manageRoleDTO.getRoleId());
				manageMenuPreference.setMenuPrefrenceIndex(manageMenuPreferencesDTO.getMenuIndex());
				manageMenuPreference.setMenuId(manageMenuPreferencesDTO.getMenuId());
				List<Long> subMenuIds = new ArrayList<Long>();
				if (manageMenuPreferencesDTO.getSubMenuIds() != null
						&& manageMenuPreferencesDTO.getSubMenuIds().size() > 0) {
					for (Long subMenuId : manageMenuPreferencesDTO.getSubMenuIds()) {
						subMenuIds.add(subMenuId);
					}

				}
				manageMenuPreference.setSubMenuId(subMenuIds);
				em.persist(manageMenuPreference);
				preferences.add(manageMenuPreference);

			}

		}
		return preferences;
	}

	@Override
	public Boolean findByRoleIdInMenuPrefrences(Long roleId) {
		try {
			Query query = em.createNativeQuery("select * from manage_menu_preference rpm where role_id = :roleId",
					ManageMenuPreference.class);
			query.setParameter("roleId", roleId);
			List<ManageMenuPreference> q = query.getResultList();
			if (q.size() > 0) {
				return true;
			}
		} catch (Exception e) {

		}
		return false;
	}

	@Override
	public List<ManageMenuPreference> findAllByMenuIdManageMenuPreference(Long roleId) {
		try {
			Query query = em.createNativeQuery("select * from manage_menu_preference rpm where role_id = :roleId",
					ManageMenuPreference.class);
			query.setParameter("roleId", roleId);
			List<ManageMenuPreference> q = query.getResultList();
			if (q.size() > 0) {
				return q;
			}
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public ManageMenuPreference findByMenuIdAndRoleIdManageMenuPreference(Long menuId, Long roleId) {

		try {
			Query query = em.createNativeQuery(
					"select * from manage_menu_preference rpm where role_id = :roleId AND menu_id =:menuId",
					ManageMenuPreference.class);
			query.setParameter("roleId", roleId);
			query.setParameter("menuId", menuId);
			ManageMenuPreference q = (ManageMenuPreference) query.getSingleResult();
			return q;

		} catch (Exception e) {

		}
		return null;
	}

	@Override
	@Transactional
	public Menu findBySubMenuId(Long id) {
		try {
			Menu menu = em.createQuery("from Menu m where m.id = :menuId", Menu.class).setParameter("menuId", id)
					.getSingleResult();
			if (menu != null)
				return menu;
			else
				return null;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	
	
	@Override
	public List<EndUser> searchEmployees(String bankId, String userName, String contactNo, String email, String status) {
		Role role=this.findByRoleName("Bank Employee");
		userName = userName.toLowerCase();
		email = email.toLowerCase();

		String whereString = this.buildWhereClauseForEmployees(bankId, userName, contactNo, email,role.getId(), status);

		
	
		
		Query query = em.createNativeQuery(
				"Select o.id,o.userName,o.email,o.contactNo,o.displayName from enduser o  where o.id IN  "
				+ "("
				+ "SELECT DISTINCT e.enduser_id from enduser_role as e inner join Enduser en on e.role_id  IN "
				+ "("
				+ "(select rol.id from Role rol where rol.role NOT IN ('ROLE_USER','ROLE_ADMIN'))"
				+ ")"
				+ ")  AND " + whereString);


		if (userName != "" || !userName.isEmpty()) {
			query.setParameter("userName", userName.trim() + "%");
		}
		if (bankId != "" || !bankId.isEmpty()) {
			query.setParameter("bankId", bankId.trim());
		}
		if (email != "" || !email.isEmpty()) {
			query.setParameter("email", email.trim());
		}
		if (contactNo != "" || !contactNo.isEmpty()) {
			query.setParameter("contactNo", contactNo.trim());
		}

		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();

		List<EndUser> endUserList = new ArrayList<EndUser>();
		for (Object[] row : rows) {
			EndUser endUser = new EndUser();
			endUser.setId(Long.valueOf(row[0].toString()));
			endUser.setUserName((String) row[1]);
			endUser.setEmail((String) row[2]);
			endUser.setContactNo((String) row[3]);
			endUser.setDisplayName((String) row[4]);
			endUserList.add(endUser);

		}
		return endUserList;

	}

	private String buildWhereClauseForEmployees(String bankId, String userName, String contactNo, String email, Long roleId, String status) {
		if (userName == "" && bankId == "" && contactNo == "" && email == "")
			return " o.status='"+status+"'";

		String whereString = "";
		if (userName != "") {
			whereString = " LOWER(o.userName) LIKE :userName AND ";
		}
		if (bankId != "") {
			whereString = whereString + " o.bankId=:bankId AND ";
		}
		if (contactNo != "") {
			whereString = whereString + " o.contactNo=:contactNo AND ";
		}
		if (email != "") {
			whereString = whereString + " LOWER(o.email)=:email AND ";
		}

		whereString = whereString + " o.status='"+status+"'";

		return whereString;
	}
	
	/*public List<EndUser> getSuspendedEmployeeList1() {

		Query q = em.createNativeQuery(

				"SELECT o.* FROM EndUser o left join enduser_role r on o.id  IN ( (select rol.id from Role rol where rol.role NOT IN ('ROLE_USER','ROLE_ADMIN')) ) where o.status='Suspended'",
				EndUser.class);

				"SELECT o.* FROM EndUser o  where o.status='Suspended' and o.currentRole!='ROLE_ADMIN' and  o.currentRole!='ROLE_USER'",
				EndUser.class);s


		return (List<EndUser>) q.getResultList();
	}


	public List<EndUser> getAppprovalPendingEmployeeList1() {


		Query q = em.createNativeQuery(
				"SELECT o.* FROM EndUser o left join enduser_role r on o.id  IN ((select rol.id from Role rol where rol.role NOT IN ('ROLE_USER','ROLE_ADMIN') )) where o.status='Pending'",
				EndUser.class);

		return (List<EndUser>) q.getResultList();
	}
	*/
}
