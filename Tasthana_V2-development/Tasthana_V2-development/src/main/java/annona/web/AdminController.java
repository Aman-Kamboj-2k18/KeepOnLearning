
package annona.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.theme.CookieThemeResolver;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import annona.dao.CustomerDAO;
import annona.dao.DTAACountryRatesDAO;
import annona.dao.EndUserDAO;
import annona.dao.RatesDAO;
import annona.dao.TransactionDAO;
import annona.domain.DTAACountry;
import annona.domain.EndUser;
import annona.domain.ManageMenuPreference;
import annona.domain.Menu;
import annona.domain.Permission;
import annona.domain.Role;
import annona.domain.RolePermissionMenu;
import annona.domain.Transaction;
import annona.dto.ManageRoleDTO;
import annona.exception.CustomException;
import annona.form.AddCountryForDTAAForm;
import annona.form.CustomerForm;
import annona.form.EndUserForm;
import annona.form.FixedDepositForm;
import annona.services.ImageService;
import annona.utility.Constants;
import annona.dao.BranchDAO;
import annona.domain.Branch;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	EndUserDAO endUserDAOImpl;

	@Autowired
	EndUserForm endUserForm;

	@Autowired
	TransactionDAO transactionDAO;

	@Autowired
	RatesDAO ratesDAO;
	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	DTAACountryRatesDAO dtaaCountryRatesDAO;
	
	@Autowired
	private BranchDAO branchDAO;

	@Autowired
	private JavaMailSender mailSender;

	CookieThemeResolver themeResolver = new CookieThemeResolver();

	static Logger log = Logger.getLogger(AdminController.class.getName());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	private String getCurrentLoggedUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();

	}

	/**
	 * Method to get current user details
	 */
	private EndUser getCurrentLoggedUserDetails() {

		EndUser endUser = endUserDAOImpl.findByUsername(getCurrentLoggedUserName()).getSingleResult();

		return endUser;

	}

	/**
	 * Method to calling current user details
	 * 
	 * @param model
	 * @return
	 */
	@ModelAttribute("requestCurrentUser")
	public EndUser getUserDetails(ModelMap model) {
		EndUser user = getCurrentLoggedUserDetails();
		if (user.getImageName() != null) {
			String type = ImageService.getImageType(user.getImageName());

			String url = "data:image/" + type + ";base64," + Base64.encodeBase64String(user.getImage());
			user.setImageName(url);
		}

		model.put("user", user);
		return user;
	}

	@RequestMapping(value = "selectLoggedInRole", method = RequestMethod.GET)
	private ModelAndView selectLoggedInRole() {

		return new ModelAndView("redirect:/main/default");

	}

	@RequestMapping(value = "/adminPage", method = RequestMethod.GET)
	public ModelAndView showadminDashBoard(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws CustomException {
		EndUser user = getCurrentLoggedUserDetails();
		Role role=endUserDAOImpl.findByRoleName("Approver");
		List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Approved",role.getId());

		if (list.size() > 0) {
			model.put("approverList", list);

		}
		list = null;
		role=endUserDAOImpl.findByRoleName("Bank Employee");
		list = endUserDAOImpl.getSuspendedCustomerList("Approved", role.getId());

		if (list.size() > 0) {
			model.put("empList", list);

		}
		model.put("endUser", user);
		return new ModelAndView("admin", "model", model);
	}

	/**
	 * Method to editing profile
	 * 
	 * @param model, id
	 * @return view
	 */
	@RequestMapping(value = "/editAdminProfile", method = RequestMethod.GET)
	public ModelAndView editAdminProfile(ModelMap model, @RequestParam("id") Long id, RedirectAttributes attributes)
			throws CustomException {

		EndUser userProfile = endUserDAOImpl.findId(id);

		if (userProfile.getImageName() != null) {
			String type = ImageService.getImageType(userProfile.getImageName());

			String url = "data:image/" + type + ";base64," + Base64.encodeBase64String(userProfile.getImage());
			userProfile.setImageName(url);

			endUserForm.setImageName(url);
		} else {
			endUserForm.setImageName("");
		}

		endUserForm.setId(userProfile.getId());
		endUserForm.setDisplayName(userProfile.getDisplayName());
		endUserForm.setUserName(userProfile.getUserName());
		endUserForm.setAltContactNo(userProfile.getAltContactNo());
		endUserForm.setAltEmail(userProfile.getAltEmail());
		endUserForm.setContactNo(userProfile.getContactNo());
		endUserForm.setEmail(userProfile.getEmail());

		endUserForm.setPassword(userProfile.getPassword());
		endUserForm.setTransactionId(userProfile.getTransactionId());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("editAdminProfile", "model", model);

	}

	/**
	 * Method to updating profile image
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateImageForProfile", method = RequestMethod.POST)
	public String updateImageForProfile(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {

		try {
			EndUser userProfile = endUserDAOImpl.findId(endUserForm.getId());
			userProfile.setImage(endUserForm.getFile().getBytes());
			userProfile.setImageName(endUserForm.getFile().getOriginalFilename());
			endUserDAOImpl.update(userProfile);

		} catch (Exception e) {
			e.getMessage();
		}
		return "redirect:editAdminProfile?id=" + endUserForm.getId();
	}

	/**
	 * Method to confirm edit profile
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/confirmEditAdminProfile", method = RequestMethod.POST)
	public ModelAndView confirmEditAdminProfile(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {

		model.put("endUserForm", endUserForm);

		return new ModelAndView("confirmEditAdminProfile", "model", model);

	}

	/**
	 * Method to updating profile details
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateAdminDetails", method = RequestMethod.POST)
	public ModelAndView updateAdminDetails(ModelMap model, @ModelAttribute EndUserForm endUserForm,
			BindingResult result, RedirectAttributes attributes) throws CustomException {

		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());

		endUser.setId(endUserForm.getId());

		endUser.setDisplayName(endUserForm.getDisplayName());
		endUser.setContactNo(endUserForm.getContactNo());
		endUser.setAltContactNo(endUserForm.getAltContactNo());
		endUser.setEmail(endUserForm.getEmail());
		endUser.setAltEmail(endUserForm.getAltEmail());
		endUser.setUserName(endUserForm.getUserName());
		endUser.setTransactionId(endUserForm.getTransactionId());

		endUserDAOImpl.update(endUser);

		model.put("endUserForm", endUserForm);

		return new ModelAndView("updateAdminSuccess", "model", model);

	}

	/**
	 * Method to edit password details
	 * 
	 * @param model,id
	 * @return
	 */
	@RequestMapping(value = "/editAdminPWD", method = RequestMethod.GET)
	public ModelAndView editAdminPWD(ModelMap model, @RequestParam("id") Long id, RedirectAttributes attributes)
			throws CustomException {

		EndUser userProfile = endUserDAOImpl.findId(id);

		endUserForm.setId(userProfile.getId());

		endUserForm.setTransactionId(userProfile.getTransactionId());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("editAdminPWD", "model", model);

	}

	/**
	 * Method to updating password details
	 * 
	 * @param model, enduserForm
	 * @return
	 */
	@RequestMapping(value = "/updateEditAdminPWD", method = RequestMethod.POST)
	public ModelAndView updateEditAdminPWD(ModelMap model, @ModelAttribute EndUserForm endUserForm,
			BindingResult result, RedirectAttributes attributes) throws CustomException {

		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());

		endUser.setId(endUserForm.getId());

		endUser.setPassword(endUserForm.getNewPassword());
		endUser.setTransactionId(endUserForm.getTransactionId());

		endUserDAOImpl.update(endUser);

		model.put("endUserForm", endUserForm);

		return new ModelAndView("updateAdminSuccess", "model", model);

	}

	/**
	 * Method to change theme
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/themeChange", method = RequestMethod.GET)
	public String getThemeChange(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws CustomException {

		log.info("Received request for theme change");

		EndUser endUser = endUserDAOImpl.findByUsername(getCurrentLoggedUserName()).getSingleResult();
		if (endUser.getTheme() == null)
			themeResolver.setThemeName(request, response, "themeBlue");
		if (!endUser.getTheme().equalsIgnoreCase(request.getParameter("theme"))) {

			if (request.getParameter("theme").equalsIgnoreCase("themeBlue")) {
				themeResolver.setThemeName(request, response, "themeBlue");
			} else if (request.getParameter("theme").equalsIgnoreCase("themeGreen")) {
				themeResolver.setThemeName(request, response, "themeGreen");
			} else if (request.getParameter("theme").equalsIgnoreCase("themeOrange")) {
				themeResolver.setThemeName(request, response, "themeOrange");
			} else if (request.getParameter("theme").equalsIgnoreCase("themeRed")) {
				themeResolver.setThemeName(request, response, "themeRed");
			}

			endUser.setTheme(request.getParameter("theme"));
			endUserDAOImpl.update(endUser);
		} else
			themeResolver.setThemeName(request, response, endUser.getTheme());

		return "redirect:adminPage";
	}

	/**
	 * Method to change language
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getLocaleLang", method = RequestMethod.GET)
	public String getLocaleLang(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws CustomException {

		log.info("Received request for locale change");
		EndUser user = endUserDAOImpl.findByUsername(getCurrentLoggedUserName()).getSingleResult();
		LocaleResolver localeResolver = new CookieLocaleResolver();
		Locale locale = new Locale(request.getParameter("lang"));
		localeResolver.setLocale(request, response, locale);
		user.setPrefferedLanguage(request.getParameter("lang"));

		endUserDAOImpl.update(user);
		return "redirect:adminPage";
	}

	/**
	 * Method to create role for Bank Employee, Approval Manager
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/createUser", method = RequestMethod.GET)
	public ModelAndView createUser(ModelMap model) throws CustomException {

		model.put("endUserForm", endUserForm);
		Collection<EndUser> endUsers = endUserDAOImpl.getList();
		List<Role> roles = endUserDAOImpl.findAll();
		model.put("roles", roles);

		model.put("endUsers", endUsers);
		return new ModelAndView("createUser", "model", model);
	}

	@RequestMapping(value = "/createRole", method = RequestMethod.GET)
	public ModelAndView createRoleByAdmin(ModelMap model, @ModelAttribute Role role) throws CustomException {

		List<Role> roles = endUserDAOImpl.findAll();
		List<Menu> menuList = endUserDAOImpl.findAllMenu();
		List<Permission> permissionList = endUserDAOImpl.findAllPermission();
		model.put("menuList", menuList);
		model.put("permissionList", permissionList);
		model.put("roles", roles);
		return new ModelAndView("createRole", "model", model);
	}

	@RequestMapping(value = "/createRole", method = RequestMethod.POST)
	public ModelAndView createRoleByAdmin(ModelMap model, @ModelAttribute Role role, RedirectAttributes attributes)
			throws CustomException {
		String roleName = "ROLE_" + role.getRole(); // (!role.getRole().startsWith("ROLE_"))? "ROLE_"+role.getRole():
													// role.getRole();
		role.setRole(roleName);
		Role isExistRole = endUserDAOImpl.findByRoleName(role.getRole());
		List<Role> roles = endUserDAOImpl.findAll();
		model.put("roles", roles);
		if (isExistRole != null) {
			model.put("error", "Role already exist");
		} else {

			role.setCreatedBy(getCurrentLoggedUserName());
			role.setModifiedBy(getCurrentLoggedUserName());
			role.setCreatedOn(new Date());
			role.setModifiedOn(new Date());
			role.setIsActive(true);
			role.setMenu(endUserDAOImpl.findAllMenu());
			endUserDAOImpl.createRole(role);
			model.put("sucess", "created Successfully");
			return new ModelAndView("createRole", "model", model);
		}
		return new ModelAndView("createRole", "model", model);
	}

	@RequestMapping(value = "/editRole", method = RequestMethod.GET)
	public ModelAndView editRole(ModelMap model, @RequestParam("id") Long id, RedirectAttributes attributes)
			throws CustomException {
		Role role = endUserDAOImpl.findById(id);

		model.put("role", role);
		return new ModelAndView("editRole", "model", model);

	}

	@RequestMapping(value = "/manageRole", method = RequestMethod.GET)
	public ModelAndView manageRole(ModelMap model, @ModelAttribute("manageRole") ManageRoleDTO manageRole)
			throws CustomException {
		List<Role> roles = endUserDAOImpl.findAll();
		List<Menu> menuList = endUserDAOImpl.getSubMenuAndAllMenu();

		for (Menu menu : menuList) {
			List<Permission> permissionList = new ArrayList<Permission>();
			for (String id : menu.getPossibleAction()) {
				Permission permission = endUserDAOImpl.findByPermissionId(Long.valueOf(id));
				permissionList.add(permission);
				menu.setPermission(permissionList);
			}
		}
		model.put("menuList", menuList);
		model.put("roles", roles);
		return new ModelAndView("manageRole", "model", model);
	}

	@RequestMapping(value = "getByRoleIdAndMenuAndPermissionDetails/{roleId}", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<RolePermissionMenu> getByRoleIdAndMenuAndPermissionDetails(
			@PathVariable("roleId") String roleId) {
		List<RolePermissionMenu> listOfPermissionMenu = endUserDAOImpl
				.getByRoleIdAndMenuAndPermissionDetails(Long.valueOf(roleId));
		return listOfPermissionMenu;

	}

	@RequestMapping(value = "/manageRoleSave", method = RequestMethod.POST)
	public ModelAndView manageRoleSave(ModelMap model, @ModelAttribute("manageRole") ManageRoleDTO manageRole)
			throws CustomException {
		Role role = endUserDAOImpl.findById(manageRole.getRoleId());
		endUserDAOImpl.deleteByRoleId(role.getId());
		List<ManageRoleDTO> maDto = new Gson().fromJson(manageRole.getJsonData(), new TypeToken<List<ManageRoleDTO>>() {
		}.getType());
		List<Menu> menuList = new ArrayList<>();
		List<RolePermissionMenu> permissionList = new ArrayList<>();
		for (ManageRoleDTO manageRoleDTO : maDto) {
			Menu menu = endUserDAOImpl.findByMenuId(Long.valueOf(manageRoleDTO.getMenuId()));
			List<Menu> list = new ArrayList<>();
			list.add(menu);
			if (manageRoleDTO.getPermissionId().contains(",")) {

				String perId[] = manageRoleDTO.getPermissionId().split(",");
				for (String permissionId : perId) {
					Permission permission_ = endUserDAOImpl.findByPermissionId(Long.valueOf(permissionId));
					//endUserDAOImpl.deleteByRoleIdAndMenuId(role.getId(), menu.getId());
					RolePermissionMenu rolePermissionMenu = new RolePermissionMenu();
					rolePermissionMenu.setPermissionId(permission_.getId());
					rolePermissionMenu.setMenuId(menu.getId());
					rolePermissionMenu.setRoleId(role.getId());
					permissionList.add(rolePermissionMenu);
				}
			} else {
				
				RolePermissionMenu rolePermissionMenu = new RolePermissionMenu();
				Permission permission = endUserDAOImpl
						.findByPermissionId(Long.valueOf(manageRoleDTO.getPermissionId()));
				rolePermissionMenu.setPermissionId(permission.getId());
				rolePermissionMenu.setMenuId(menu.getId());
				rolePermissionMenu.setRoleId(role.getId());
				permissionList.add(rolePermissionMenu);
			}
			menu = endUserDAOImpl.updateMenu(menu);
			menuList.add(menu);

		}

		role.setMenu(menuList);
		role = endUserDAOImpl.updateRole(role);
		endUserDAOImpl.saveRolePermissionMenu(permissionList);

		List<Role> roles = endUserDAOImpl.findAll();
		List<Menu> menuList_ = endUserDAOImpl.getSubMenuAndAllMenu();
		for (Menu menu : menuList_) {
			List<Permission> permissions = new ArrayList<Permission>();
			for (String id : menu.getPossibleAction()) {
				Permission permission = endUserDAOImpl.findByPermissionId(Long.valueOf(id));
				permissions.add(permission);
				menu.setPermission(permissions);
			}
		}
		model.put("menuList", menuList_);
		model.put("roles", roles);
		model.addAttribute("sucess", "Updated Successfully !");
		return new ModelAndView("manageRole", "model", model);
	}

	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public ModelAndView updateRole(ModelMap model, @ModelAttribute Role role, RedirectAttributes attributes)
			throws CustomException {
		if (role.getRole().equals("ROLE_ADMIN") || role.getRole().equals("ROLE_USER")) {
			model.put("error", "Can't edit this role");
			model.put("role", role);
			return new ModelAndView("editRole", "model", model);
		}

		if (endUserDAOImpl.existRoleName(role.getRole(), role.getId())) {
			model.put("error", "Role already exist");
			model.put("role", role);
			return new ModelAndView("editRole", "model", model);
		} else {
			role.setModifiedBy(getCurrentLoggedUserName());
			role.setModifiedOn(new Date());
			role.setIsActive(true);
			Role role_ = endUserDAOImpl.updateRole(role);
			model.put("role", role_);
			model.put("sucess", "updated Successfully");
			return new ModelAndView("editRole", "model", model);
		}

	}

	/**
	 * Method to redirecting roles pages
	 * 
	 * @param model, endUserForm
	 * @return
	 */
	@RequestMapping(value = "/confirmUser", method = RequestMethod.POST)
	public ModelAndView confirmUser(@ModelAttribute EndUserForm endUserForm, RedirectAttributes attributes,
			ModelMap model) throws CustomException {

		endUserForm.setUserName(endUserForm.getUserName().split(",")[1]);
		List<EndUser> endUser = endUserDAOImpl.findByUsername(endUserForm.getUserName()).getResultList();

		if (endUser.size() == 0) {

			if (endUserForm != null && endUserForm.getCurrentRole().equals("ROLE_APPROVALMNG")) {

				model.put("endUserForm", endUserForm);

				return new ModelAndView("approvalManager", "model", model);
			} else if (endUserForm != null && endUserForm.getCurrentRole().equals("ROLE_VP")) {

				model.put("endUserForm", endUserForm);
				return new ModelAndView("vicePresident", "model", model);
			} else {

				model.put("endUserForm", endUserForm);
				return new ModelAndView("bankEmployee", "model", model);
			}

		} else {
			endUserForm.setUserName("");
			List<Role> roles = endUserDAOImpl.findAll();
			model.put("roles", roles);
			model.put(Constants.ERROR, Constants.ROLE);
			model.put("endUserForm", endUserForm);
			return new ModelAndView("createUser", "model", model);

		}

	}

	/**
	 * Method to save details
	 * 
	 * @param model, enduserForm
	 * @return
	 */
	@RequestMapping(value = "/insertDetails", method = RequestMethod.POST)
	public String roleApprovalListUpdate(ModelMap model, @ModelAttribute EndUserForm endUserForm,
			RedirectAttributes attributes) throws CustomException {

		EndUser endUser = new EndUser();

		Transaction transaction = new Transaction();

		endUser.setEmail(endUserForm.getEmail());
		endUser.setContactNo(endUserForm.getContactNo());
		endUser.setRole(endUserForm.getRole());
		// String users[] = endUserForm.getUserName().split(",");
		endUser.setUserName(endUserForm.getUserName());
		endUser.setDisplayName(endUserForm.getDisplayName());
		endUser.setBankId(endUserForm.getBankId());
		endUser.setDesignation(endUserForm.getDesignation());
		// endUser.setCurrentRole(endUserForm.getCurrentRole());
		endUser.setPrefferedLanguage("en");
		endUser.setTheme("themeBlue");
		endUser.setNotificationStatus(Constants.PENDING);
		endUser.setCurrentRole(endUserForm.getCurrentRole());

		String rolesIds = endUserForm.getRolesId();
		String splitsRoleIds[] = rolesIds.split(",");
		List<Role> roleList = new ArrayList<>();
		for (String roleId : splitsRoleIds) {
			Long parseRoleId = Long.valueOf(roleId);
			endUser.setRole(Integer.valueOf(roleId));
			Role role = endUserDAOImpl.findById(parseRoleId);
			roleList.add(role);
		}
		endUser.setRoles(roleList);
		endUser.setStatus("Approved");

		/*
		 * if (endUserForm.getCurrentRole().equals("ROLE_VP")) {
		 * 
		 * //Get Role Id's or roles String //Find Role Id's or roles String in Database
		 * //set Roles List in EndUser roles method
		 * 
		 * //endUser.setRoles();
		 * 
		 * 
		 * endUser.setStatus(Constants.APPROVED); }else if
		 * (endUserForm.getCurrentRole().equals("ROLE_APPROVALMNG")) {
		 * endUser.setStatus(Constants.APPROVED); } else {
		 * endUser.setStatus(Constants.PENDING); }
		 */
		endUser.setPassword(endUserForm.getPassword());
		endUser.setApprovallimit(endUserForm.getApprovallimit());
		endUser.setTransactionId(endUserForm.getTransactionId());

		transaction.setTransactionId(endUserForm.getTransactionId());
		transaction.setTransactionType(Constants.MODULEROLE);
		transaction.setTransactionStatus(Constants.ROLEADDED);
		endUser.setPasswordFlag(0);

		String username = endUserForm.getUserName();
		String password = endUserForm.getPassword();
		String currentRole = endUserForm.getCurrentRole();
		String email = endUserForm.getEmail();

		String tex = Constants.BANKSUBJECT;
		try {
			SimpleMailMessage emails = new SimpleMailMessage();
			emails.setTo(email);
			emails.setSubject(tex);
			emails.setText(Constants.HELLO + username + Constants.BANKBODY1 + username + Constants.BANKBODY2 + password
					+ Constants.CURRENTROLE + currentRole + Constants.BANKSIGNATURE);

			mailSender.send(emails);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		endUser.setStartDate(endUserForm.getStartDate());
		endUser.setAccExpiryDate(endUserForm.getAccExpiryDate());

		endUserDAOImpl.createUser(endUser);
		transactionDAO.insertTransaction(transaction);

		attributes.addFlashAttribute(Constants.SUCCESS, Constants.SAVEDROLE);

		attributes.addFlashAttribute("endUserForm", endUserForm);

		return "redirect:savedUserSuccess";

	}

	@RequestMapping(value = "/savedUserSuccess", method = RequestMethod.GET)
	public ModelAndView savedRoleSuccess(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {

		model.put("endUserForm", endUserForm);
		return new ModelAndView("savedUserSuccess", "model", model);

	}

	/**
	 * Method to select role
	 * 
	 * @param model, id
	 * @return
	 */
	@RequestMapping(value = "/editUser", method = RequestMethod.GET)
	public ModelAndView editUser(ModelMap model, @RequestParam("id") Long id, RedirectAttributes attributes)
			throws CustomException {

		EndUser endUser = endUserDAOImpl.findId(id);

		endUserForm.setId(endUser.getId());
		endUserForm.setBankId(endUser.getBankId());
		endUserForm.setRole(endUser.getRole());
		endUserForm.setContactNo(endUser.getContactNo());
		endUserForm.setEmail(endUser.getEmail());
		endUserForm.setDisplayName(endUser.getDisplayName());
		;
		endUserForm.setUserName(endUser.getUserName());
		endUserForm.setTransactionId(endUser.getTransactionId());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("editUser", "model", model);

	}

	/**
	 * Method to updating role
	 * 
	 * @param model, enduserForm
	 * @return
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public ModelAndView roleupdate(ModelMap model, @ModelAttribute EndUserForm endUserForm,
			RedirectAttributes attributes) throws CustomException {

		ModelAndView mav = new ModelAndView();

		if (endUserForm != null && endUserForm.getRole().equals(3)) {

			model.put("endUserForm", endUserForm);

			mav = new ModelAndView("approvalmanagerUpdate", "model", model);
		} else {

			model.put("endUserForm", endUserForm);

			mav = new ModelAndView("bankEmployeeUpdate", "model", model);
		}

		return mav;

	}

	/**
	 * Method to save Approval Manager details
	 * 
	 * @param model, enduserForm
	 * @return
	 */
	@RequestMapping(value = "/aprrovalManagerUpdate", method = RequestMethod.POST)
	public String approvalRoleUpdate(ModelMap model, @ModelAttribute EndUserForm endUserForm, BindingResult result,
			RedirectAttributes attributes) throws CustomException {
		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());
		endUser.setContactNo(endUserForm.getContactNo());
		endUser.setEmail(endUserForm.getEmail());
		endUser.setUserName(endUserForm.getUserName());
		endUser.setNotificationStatus(Constants.PENDING);
		endUser.setStatus("Approved");
		endUser.setTransactionId(endUserForm.getTransactionId());

		endUserDAOImpl.update(endUser);

		attributes.addFlashAttribute("endUserForm", endUserForm);

		return "redirect:updateSuccessPage";

	}

	@RequestMapping(value = "/updateSuccessPage", method = RequestMethod.GET)
	public ModelAndView updateSuccess(ModelMap model, @ModelAttribute EndUserForm endUserForm) throws CustomException {

		model.put("endUserForm", endUserForm);
		return new ModelAndView("updateSuccessPage", "model", model);

	}

	@RequestMapping(value = "/updateAdminSuccess", method = RequestMethod.GET)
	public ModelAndView updateAdminSuccess(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {

		model.put("endUserForm", endUserForm);
		return new ModelAndView("savedRoleSuccess", "model", model);

	}

	/**
	 * Method to get List of Bank Employee details
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bankEmpList", method = RequestMethod.GET)
	public ModelAndView bankEmpList(ModelMap model) throws CustomException {

		List<EndUser> userList = endUserDAOImpl.getByRoleList().getResultList();

		if (userList != null && userList.size() > 0) {
			model.put("userList", userList);

			return new ModelAndView("bankEmpList", "model", model);
		}

		else {
			return new ModelAndView("noDataFoundAdmin", "model", model);
		}
	}

	/**
	 * Method to select Bank Employee details
	 * 
	 * @param model, id
	 * @return
	 */
	@RequestMapping(value = "/selectBank", method = RequestMethod.GET)
	public ModelAndView selectBank(ModelMap model, @RequestParam("id") Long id, @ModelAttribute EndUserForm endUserForm,
			BindingResult result, RedirectAttributes attributes) throws CustomException {

		EndUser endUser = endUserDAOImpl.findId(id);
		endUserForm.setId(endUser.getId());
		endUserForm.setUserName(endUser.getUserName());
		endUserForm.setContactNo(endUser.getContactNo());
		endUserForm.setCurrentRole(endUser.getCurrentRole());
		endUserForm.setEmail(endUser.getEmail());

		endUserForm.setStatus(endUser.getStatus());

		model.put("endUserForm", endUserForm);

		model.put("endUser", endUser);
		attributes.addFlashAttribute(Constants.SUCCESS, Constants.SENTMAIL);
		return new ModelAndView("selectBankEmp", "model", model);

	}

	/**
	 * Method to sending email notification to Bank Employees
	 * 
	 * @param model, endUserForm
	 * @return
	 */
	@RequestMapping(value = "/selectUpdateBankEmp", method = RequestMethod.POST)
	public ModelAndView mailBank(ModelMap model, @ModelAttribute EndUserForm endUserForm, BindingResult result,
			RedirectAttributes attributes) throws CustomException {
		EndUser endUuser = endUserDAOImpl.findId(endUserForm.getId());

		String email = endUuser.getEmail();
		String username = endUuser.getUserName();

		String password = endUuser.getPassword();
		String currentRole = endUuser.getCurrentRole();
		endUuser.setNotificationStatus(Constants.NOTIFICATION);
		endUserDAOImpl.update(endUuser);
		try {
			String subject = Constants.BANKSUBJECT;
			String body = "";
			String stat = endUuser.getStatus();

			if (stat.equals(Constants.APPROVED)) {

				body = Constants.HELLO + username + Constants.BANKBODY1 + username + Constants.BANKBODY2 + password
						+ Constants.CURRENTROLE + currentRole + Constants.BANKSIGNATURE;
			} else if (stat.equals(Constants.CLOSED)) {

				body = Constants.HELLO + username + Constants.BANKBODY5 + Constants.BANKSIGNATURE;

			} else {

				body = Constants.HELLO + username + Constants.BANKBODY6 + Constants.BANKSIGNATURE;
			}

			SimpleMailMessage emails = new SimpleMailMessage();

			emails.setTo(email);
			emails.setSubject(subject);
			emails.setText(body);
			mailSender.send(emails);

		} catch (Exception e) {
			System.out.println(e.getMessage() + e);
		}

		attributes.addFlashAttribute(Constants.SUCCESS, Constants.SENTMAIL);

		return new ModelAndView("redirect:bankEmpList");
	}

	/**
	 * Method to get List of Bank Employee details
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/appMngList", method = RequestMethod.GET)
	public ModelAndView appMngList(ModelMap model) throws CustomException {

		List<EndUser> userList = endUserDAOImpl.getByApprovalMng().getResultList();

		if (userList != null && userList.size() > 0) {
			model.put("userList", userList);

			return new ModelAndView("appMngList", "model", model);
		}

		else {
			return new ModelAndView("noDataFoundAdmin", "model", model);
		}
	}

	@RequestMapping(value = "/VPList", method = RequestMethod.GET)
	public ModelAndView VPList(ModelMap model) throws CustomException {

		List<EndUser> userList = endUserDAOImpl.getByVPList().getResultList();

		if (userList != null && userList.size() > 0) {
			model.put("userList", userList);

			return new ModelAndView("VPList", "model", model);
		}

		else {
			return new ModelAndView("noDataFoundAdmin", "model", model);
		}
	}

	/**
	 * Method to select Approval Manager details
	 * 
	 * @param model, id
	 * @return
	 */
	@RequestMapping(value = "/selectApproveManager", method = RequestMethod.GET)
	public ModelAndView selectApproveManager(ModelMap model, @RequestParam("id") Long id) throws CustomException {

		EndUser endUser = endUserDAOImpl.findId(id);
		endUserForm.setId(endUser.getId());
		endUserForm.setUserName(endUser.getUserName());
		endUserForm.setContactNo(endUser.getContactNo());
		endUserForm.setCurrentRole(endUser.getCurrentRole());
		endUserForm.setEmail(endUser.getEmail());

		model.put("endUserForm", endUserForm);

		model.put("endUser", endUser);

		return new ModelAndView("selectApproveManager", "model", model);

	}

	@RequestMapping(value = "/selectVP", method = RequestMethod.GET)
	public ModelAndView selectVP(ModelMap model, @RequestParam("id") Long id) throws CustomException {

		EndUser endUser = endUserDAOImpl.findId(id);
		endUserForm.setId(endUser.getId());
		endUserForm.setUserName(endUser.getUserName());
		endUserForm.setContactNo(endUser.getContactNo());
		endUserForm.setCurrentRole(endUser.getCurrentRole());
		endUserForm.setEmail(endUser.getEmail());

		model.put("endUserForm", endUserForm);

		model.put("endUser", endUser);

		return new ModelAndView("selectVP", "model", model);

	}

	/**
	 * Method to sending email notification to Approval Managers
	 * 
	 * @param model, endUserForm
	 * @return
	 */
	@RequestMapping(value = "/selectUpdateApproveManager", method = RequestMethod.POST)
	public ModelAndView mailApprovalManagerk(ModelMap model, @ModelAttribute EndUserForm endUserForm,
			BindingResult result, RedirectAttributes attributes) throws CustomException {

		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());
		try {
			String email = endUser.getEmail();
			String username = endUser.getUserName();
			String password = endUser.getPassword();
			String currentRole = endUser.getCurrentRole();
			String tex = Constants.APPROVALMNGSUBJECT;

			endUser.setNotificationStatus(Constants.NOTIFICATION);
			endUserDAOImpl.update(endUser);

			SimpleMailMessage emails = new SimpleMailMessage();
			emails.setTo(email);
			emails.setSubject(tex);
			emails.setText(Constants.HELLO + username + Constants.BANKBODY1 + username + Constants.BANKBODY2 + password
					+ Constants.CURRENTROLE + currentRole + Constants.BANKSIGNATURE);

			mailSender.send(emails);
			SimpleMailMessage emails1 = new SimpleMailMessage();
			emails1.setTo(email);
			emails1.setSubject(tex);
			emails1.setText(Constants.HELLO + username + Constants.BANKBODY4 + Constants.BANKSIGNATURE);

			endUser.setNotificationStatus(Constants.NOTIFICATION);
			endUserDAOImpl.update(endUser);

		} catch (Exception e) {
			System.out.println(e.getMessage() + e);
		}

		attributes.addFlashAttribute(Constants.SUCCESS, Constants.SENTMAIL);

		return new ModelAndView("redirect:appMngList");
	}

	@RequestMapping(value = "/selectUpdateVP", method = RequestMethod.POST)
	public ModelAndView mailApprovalVP(ModelMap model, @ModelAttribute EndUserForm endUserForm, BindingResult result,
			RedirectAttributes attributes) throws CustomException {

		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());
		try {
			String email = endUser.getEmail();
			String username = endUser.getUserName();
			String password = endUser.getPassword();
			String currentRole = endUser.getCurrentRole();
			String tex = Constants.APPROVALMNGSUBJECT;

			endUser.setNotificationStatus(Constants.NOTIFICATION);
			endUserDAOImpl.update(endUser);

			SimpleMailMessage emails = new SimpleMailMessage();
			emails.setTo(email);
			emails.setSubject(tex);
			emails.setText(Constants.HELLO + username + Constants.BANKBODY1 + username + Constants.BANKBODY2 + password
					+ Constants.CURRENTROLE + currentRole + Constants.BANKSIGNATURE);

			mailSender.send(emails);
			SimpleMailMessage emails1 = new SimpleMailMessage();
			emails1.setTo(email);
			emails1.setSubject(tex);
			emails1.setText(Constants.HELLO + username + Constants.BANKBODY4 + Constants.BANKSIGNATURE);

			endUser.setNotificationStatus(Constants.NOTIFICATION);
			endUserDAOImpl.update(endUser);

		} catch (Exception e) {
			System.out.println(e.getMessage() + e);
		}

		attributes.addFlashAttribute(Constants.SUCCESS, Constants.SENTMAIL);

		return new ModelAndView("redirect:VPList");
	}

	/**
	 * Method to search Bank Emp for suspend
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bankEmpSuspendFromAdmin", method = RequestMethod.GET)
	public ModelAndView bankEmpSuspendFromAdmin(ModelMap model) throws CustomException {

		EndUserForm endUserForm1 = new EndUserForm();
		model.put("endUserForm", endUserForm1);
		return new ModelAndView("bankEmpSuspendFromAdmin", "model", model);

	}

	@RequestMapping(value = "/searchBankEmpForAdmin", method = RequestMethod.POST)
	public ModelAndView searchBankEmp(ModelMap model, @ModelAttribute EndUserForm endUserForm) throws CustomException {
		String bankId = endUserForm.getBankId();
		String userName = endUserForm.getUserName();
		String contactNo = endUserForm.getContactNo();
		String email = endUserForm.getEmail();
		

		List<EndUser> endUserList = new ArrayList<EndUser>();
		endUserList = endUserDAOImpl.searchEmployees(bankId, userName, contactNo, email,"Suspended");
		if (endUserList.size() != 0) {
			model.put("endUserList", endUserList);  
			return new ModelAndView("searchBankEmpForAdmin", "model", model);
		} else {
			model.put(Constants.ERROR, Constants.bankEmpNotFound);
			return new ModelAndView("searchBankEmpForAdmin", "model", model);
		}
	}

	/**
	 * Method to select customer Details
	 * 
	 * @param model, id
	 * @return
	 */
	@RequestMapping(value = "/suspendBankEmpFromAdmin", method = RequestMethod.GET)
	public ModelAndView suspendBankEmpFromAdmin(@RequestParam Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {

		EndUser userList = endUserDAOImpl.findId(id);

		endUserForm.setId(userList.getId());
		endUserForm.setUserName(userList.getUserName());
		endUserForm.setContactNo(userList.getContactNo());
		endUserForm.setEmail(userList.getEmail());
		endUserForm.setDisplayName(userList.getDisplayName());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendBankEmpFromAdmin", "model", model);
	}

	@RequestMapping(value = "/suspendBankEmpConfrimFromAdmin", method = RequestMethod.POST)
	public ModelAndView suspendBankEmpConfrim(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {

		model.put("endUserForm", endUserForm);
		return new ModelAndView("suspendBankEmpConfrimFromAdmin", "model", model);
	}

	@RequestMapping(value = "/suspendEmpConfrim", method = RequestMethod.POST)
	public ModelAndView suspendEmpConfrim(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {
		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendEmpConfrim", "model", model);
	}

	@RequestMapping(value = "/updateBankEmpStatus", method = RequestMethod.POST)
	public ModelAndView updateBankEmpStatus(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {

		EndUser userList = endUserDAOImpl.findId(endUserForm.getId());
		Transaction transaction = new Transaction();
		userList.setId(endUserForm.getId());
		userList.setUserName(endUserForm.getUserName());
		userList.setContactNo(endUserForm.getContactNo());
		userList.setEmail(endUserForm.getEmail());
		userList.setStatus(endUserForm.getStatus());
		userList.setTransactionId(endUserForm.getTransactionId());
		transaction.setTransactionId(endUserForm.getTransactionId());
		transaction.setTransactionType(Constants.APPBANK);
		transaction.setTransactionStatus(Constants.APPSTATUS);
		transactionDAO.insertTransaction(transaction);
		endUserDAOImpl.update(userList);

		model.put("endUserForm", endUserForm);

		return new ModelAndView("updateAdminSuccess", "model", model);
	}

	/**
	 * Method to get suspended customer List
	 * 
	 * @param id
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/customerSuspendList", method = RequestMethod.GET)
	public ModelAndView customerSuspendList(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {
		Role role= endUserDAOImpl.findByRoleName("ROLE_USER");
		List<EndUser> suspendedList = endUserDAOImpl.getSuspendedCustomerList("Suspended", role.getId());

		if (suspendedList.size() > 0) {
			model.put("suspendedList", suspendedList);
			model.put("endUserForm", endUserForm);
			return new ModelAndView("customerSuspendedListForAdmin", "model", model);
		} else {

			return new ModelAndView("noDataFoundAdmin");
		}

	}

	/**
	 * Method to confirm customer
	 * 
	 * @param model, endUserForm
	 * @return
	 */
	@RequestMapping(value = "/revokeCustomer", method = RequestMethod.GET)
	public ModelAndView suspendCustomerApprove(Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {
		EndUser endUser = endUserDAOImpl.findUserByCustomerId(id);
		endUserForm.setUserName(endUser.getUserName());
		endUserForm.setEmail(endUser.getEmail());
		endUserForm.setContactNo(endUser.getContactNo());
		endUserForm.setId(endUser.getCustomerId());
		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendCustomerApproveFromAdmin", "model", model);
	}

	/**
	 * Method to update customer Details
	 * 
	 * @param model, endUserForm
	 * @return
	 */
	@RequestMapping(value = "/updateCustomerStatus", method = RequestMethod.POST)
	public ModelAndView updateCustomerStatus(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {

		EndUser userList = endUserDAOImpl.findUserByCustomerId(endUserForm.getId());
		Transaction transaction = new Transaction();
		userList.setId(userList.getId());
		userList.setUserName(endUserForm.getUserName());
		userList.setContactNo(endUserForm.getContactNo());
		userList.setEmail(endUserForm.getEmail());
		userList.setStatus(endUserForm.getStatus());
		userList.setTransactionId(endUserForm.getTransactionId());
		transaction.setTransactionId(endUserForm.getTransactionId());
		transaction.setTransactionType(Constants.APPBANK);
		transaction.setTransactionStatus(Constants.APPSTATUS);
		transactionDAO.insertTransaction(transaction);
		endUserDAOImpl.update(userList);

		model.put("endUserForm", endUserForm);

		return new ModelAndView("updateAdminSuccess", "model", model);
	}

	/**
	 * Method to get suspended customer List
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/bankEmpSuspendList", method = RequestMethod.GET)
	public ModelAndView bankEmpSuspendList(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {
		Role role = endUserDAOImpl.findByRoleName("Bank Employee");
		List<EndUser> suspendedList = endUserDAOImpl.getSuspendedEndUserList();
		

		if (suspendedList.size() > 0) {
			model.put("suspendedList", suspendedList);
			model.put("endUserForm", endUserForm);
			return new ModelAndView("bankEmpSuspendList", "model", model);
		} else {

			return new ModelAndView("noDataFoundAdmin");
		}

	}

	@RequestMapping(value = "/revokeBankEmployee", method = RequestMethod.GET)
	public ModelAndView revokeBankEmployee(Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {
		EndUser endUser = endUserDAOImpl.findId(id);
		endUserForm.setUserName(endUser.getUserName());
		endUserForm.setEmail(endUser.getEmail());
		endUserForm.setContactNo(endUser.getContactNo());
		endUserForm.setId(id);
		model.put("endUserForm", endUserForm);

		return new ModelAndView("bankEmpSuspendApproval", "model", model);
	}

	@RequestMapping(value = "/suspendBankEmpoyeeConfrim", method = RequestMethod.POST)
	public ModelAndView suspendBankEmpoyeeConfrim(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {

		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendBankEmpoyeeConfrim", "model", model);
	}

	/**
	 * Method to get approver Manager Suspend
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/approverManagerSuspend", method = RequestMethod.GET)
	public ModelAndView approverManagerSuspend(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {
		EndUserForm endUserForm1 = new EndUserForm();
		model.put("endUserForm", endUserForm1);
		return new ModelAndView("approverManagerSuspend", "model", model);

	}

	@RequestMapping(value = "/searchApproverManager", method = RequestMethod.POST)
	public ModelAndView searchApproverManager(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {
		String bankId = endUserForm.getBankId();
		String userName = endUserForm.getUserName();
		String contactNo = endUserForm.getContactNo();
		String email = endUserForm.getEmail();

		List<EndUser> endUserList = new ArrayList<EndUser>();
		endUserList = endUserDAOImpl.searchUser(bankId, userName, contactNo, email, 3);
		if (endUserList.size() != 0) {
			model.put("endUserList", endUserList);
			return new ModelAndView("searchApproverManager", "model", model);
		} else {
			model.put(Constants.ERROR, Constants.appMngNotFound);
			return new ModelAndView("searchApproverManager", "model", model);
		}
	}

	@RequestMapping(value = "/suspendApproverManager", method = RequestMethod.GET)
	public ModelAndView suspendApproverManager(@RequestParam Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {

		EndUser userList = endUserDAOImpl.findId(id);

		endUserForm.setId(userList.getId());
		endUserForm.setUserName(userList.getUserName());
		endUserForm.setContactNo(userList.getContactNo());
		endUserForm.setEmail(userList.getEmail());
		endUserForm.setDisplayName(userList.getDisplayName());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendApproverManager", "model", model);
	}

	@RequestMapping(value = "/suspendApproverManagaerConfrim", method = RequestMethod.POST)
	public ModelAndView suspendApproverManagaerConfrim(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {

		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendApproverManagaerConfrim", "model", model);
	}

	@RequestMapping(value = "/updateAppManagerStatus", method = RequestMethod.POST)
	public ModelAndView updateAppManagerStatus(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {
		EndUser userList = endUserDAOImpl.findId(endUserForm.getId());
		Transaction transaction = new Transaction();
		userList.setId(endUserForm.getId());
		userList.setUserName(endUserForm.getUserName());
		userList.setContactNo(endUserForm.getContactNo());
		userList.setEmail(endUserForm.getEmail());
		userList.setStatus(endUserForm.getStatus());
		userList.setTransactionId(endUserForm.getTransactionId());
		transaction.setTransactionId(endUserForm.getTransactionId());
		transaction.setTransactionType(Constants.APPBANK);
		transaction.setTransactionStatus(Constants.APPSTATUS);
		transactionDAO.insertTransaction(transaction);
		endUserDAOImpl.update(userList);

		model.put("endUserForm", endUserForm);

		return new ModelAndView("updateAdminSuccess", "model", model);
	}

	@RequestMapping(value = "/approverManagerSuspendList", method = RequestMethod.GET)
	public ModelAndView approverManagerSuspendList(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {
		Role role=endUserDAOImpl.findByRoleName("Approver");
		List<EndUser> suspendedList = endUserDAOImpl.getSuspendedCustomerList("Suspended", role.getId());

		if (suspendedList.size() > 0) {
			model.put("suspendedList", suspendedList);
			model.put("endUserForm", endUserForm);
			return new ModelAndView("approverManagerSuspendList", "model", model);
		} else {

			return new ModelAndView("noDataFoundAdmin");
		}

	}

	@RequestMapping(value = "/revokeApproverManager", method = RequestMethod.GET)
	public ModelAndView revokeApproverManager(Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {
		EndUser endUser = endUserDAOImpl.findId(id);
		endUserForm.setUserName(endUser.getUserName());
		endUserForm.setEmail(endUser.getEmail());
		endUserForm.setContactNo(endUser.getContactNo());
		endUserForm.setId(id);
		model.put("endUserForm", endUserForm);

		return new ModelAndView("approveSuspendApprovalManager", "model", model);
	}

	@RequestMapping(value = "/suspendApproverManagerConfrim", method = RequestMethod.POST)
	public ModelAndView suspendApproverManagerConfrim(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {
		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendApproverManagerConfrim", "model", model);
	}

	@RequestMapping(value = "/updateApproverManagerStatus", method = RequestMethod.POST)
	public ModelAndView updateApproverManagerStatus(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {

		EndUser userList = endUserDAOImpl.findId(endUserForm.getId());
		Transaction transaction = new Transaction();
		userList.setId(endUserForm.getId());
		userList.setUserName(endUserForm.getUserName());
		userList.setContactNo(endUserForm.getContactNo());
		userList.setEmail(endUserForm.getEmail());
		userList.setStatus(endUserForm.getStatus());
		userList.setTransactionId(endUserForm.getTransactionId());
		transaction.setTransactionId(endUserForm.getTransactionId());
		transaction.setTransactionType(Constants.APPBANK);
		transaction.setTransactionStatus(Constants.APPSTATUS);
		transactionDAO.insertTransaction(transaction);
		endUserDAOImpl.update(userList);

		model.put("endUserForm", endUserForm);

		return new ModelAndView("updateAdminSuccess", "model", model);
	}

	@RequestMapping(value = "/customerSuspendFromAdmin", method = RequestMethod.GET)
	public ModelAndView customerSuspend(ModelMap model) throws CustomException {

		// Collection<Customer> customers = customerDAO.findAllCustomers();
		// fixedDepositForm.setCustomers(customers);
		FixedDepositForm fixedDepositForm = new FixedDepositForm();
		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("customerSuspendFromAdmin", "model", model);

	}

	@RequestMapping(value = "/searchCustomer", method = RequestMethod.POST)
	public ModelAndView searchCustomer(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {
		String custId = fixedDepositForm.getCustomerID();
		String custName = fixedDepositForm.getCustomerName();
		String custMobile = fixedDepositForm.getContactNum();
		String email = fixedDepositForm.getEmail();

		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(custId, custName, custMobile, email);
		if (customerList.size() != 0) {
			model.put("customerList", customerList);
			return new ModelAndView("customerSuspendFromAdmin", "model", model);
		} else {
			model.put(Constants.ERROR, Constants.customerNotFound);
			return new ModelAndView("customerSuspendFromAdmin", "model", model);

		}
	}

	/**
	 * Method to select customer Details
	 * 
	 * @param model, id
	 * @return
	 */
	@RequestMapping(value = "/suspendCustomerFromAdmin", method = RequestMethod.GET)
	public ModelAndView suspendCustomerFromAdmin(@RequestParam Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {

		EndUser userList = endUserDAOImpl.findUserByCustomerId(id);

		endUserForm.setId(userList.getCustomerId());
		endUserForm.setUserName(userList.getUserName());
		endUserForm.setContactNo(userList.getContactNo());
		endUserForm.setEmail(userList.getEmail());
		endUserForm.setDisplayName(userList.getDisplayName());

		model.put("endUserForm", endUserForm);
		model.put("customerId", id);

		return new ModelAndView("suspendCustomerFromAdmin", "model", model);
	}

	/**
	 * Method to confirm customer Details
	 * 
	 * @param model, endUserForm
	 * @return
	 */
	@RequestMapping(value = "/suspendCustomerConfrimForAdmin", method = RequestMethod.POST)
	public ModelAndView suspendCustomerConfrim(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes,Long customerId) throws CustomException {



		model.put("customerId", endUserForm.getId());
		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendCustomerConfrimFromAdmin", "model", model);
	}

	@RequestMapping(value = "/approverActiveList", method = RequestMethod.GET)
	public ModelAndView approverActiveList(ModelMap model) throws CustomException {
		Role role=endUserDAOImpl.findByRoleName("Approver");
		List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Approved", role.getId());

		if (list.size() > 0) {
			model.put("list", list);
			model.put("title", "Approval Manager - Active List");

			return new ModelAndView("endUserList", "model", model);
		} else {

			return new ModelAndView("noDataFoundAdmin");
		}

	}
	
	@RequestMapping(value = "/bankEmpActiveList", method = RequestMethod.GET)
	public ModelAndView bankEmpActiveList(ModelMap model) throws CustomException {
		Role role=endUserDAOImpl.findByRoleName("Bank Employee");
		List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Approved", role.getId());

		if (list.size() > 0) {
			model.put("list", list);
			model.put("title", "Bank Employee - Active List");

			return new ModelAndView("endUserList", "model", model);
		} else {

			return new ModelAndView("noDataFoundAdmin");
		}

	}

	// mandeep 13/05/2019
	@RequestMapping(value = "/VPActiveList", method = RequestMethod.GET)
	public ModelAndView VPActiveList(ModelMap model) throws CustomException {
		Role role=endUserDAOImpl.findByRoleName("Product Head");
		List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Approved", role.getId());

		if (list.size() > 0) {
			model.put("list", list);
			model.put("title", "Vice President - Active List");

			return new ModelAndView("endUserList", "model", model);
		} else {

			return new ModelAndView("noDataFoundAdmin");
		}

	}

	@RequestMapping(value = "/approverPendingList", method = RequestMethod.GET)
	public ModelAndView approverPendingList(ModelMap model) throws CustomException {
		Role role=endUserDAOImpl.findByRoleName("Approver");
		List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Pending", role.getId());

		if (list.size() > 0) {
			model.put("list", list);

			model.put("title", "Approver Manager Pending List");

			return new ModelAndView("endUserList", "model", model);
		} else {

			return new ModelAndView("noDataFoundAdmin");
		}

	}

	@RequestMapping(value = "/bankEmpPendingList", method = RequestMethod.GET)
	public ModelAndView bankEmpPendingList(ModelMap model) throws CustomException {
		Role role=endUserDAOImpl.findByRoleName("Bank Employee");
		List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Approved", role.getId());

		if (list.size() > 0) {
			model.put("list", list);

			model.put("title", "Bank Employee Pending List");

			return new ModelAndView("endUserList", "model", model);
		} else {

			return new ModelAndView("noDataFoundAdmin");
		}

	}

	@RequestMapping(value = "/countryForDTAA", method = RequestMethod.GET)
	public ModelAndView countryForDTAA(ModelMap model) throws CustomException {

		List<DTAACountry> dtaaCountryList = dtaaCountryRatesDAO.getDTAACountryList();

		model.put("dtaaCountryList", dtaaCountryList);
		return new ModelAndView("countryForDTAA", "model", model);
	}

	@RequestMapping(value = "/addCountryForDTAA", method = RequestMethod.GET)
	public ModelAndView addCountryForDTAA(ModelMap model, @ModelAttribute AddCountryForDTAAForm addCountryForDTAAForm)
			throws CustomException {

		model.put("addCountryForDTAAForm", addCountryForDTAAForm);
		return new ModelAndView("addCountryForDTAA", "model", model);
	}

	@RequestMapping(value = "/saveCountryForDTAA", method = RequestMethod.POST)
	public ModelAndView saveCountryForDTAA(ModelMap model, AddCountryForDTAAForm addCountryForDTAAForm,
			BindingResult result, RedirectAttributes attributes) throws CustomException {

		if (addCountryForDTAAForm.getCountry().trim().equals("")) {
			model.put("error", "Invalid Entry. Enter a valid country name.");
			return new ModelAndView("addCountryForDTAA", "model", model);
		}
		if (dtaaCountryRatesDAO.getDTAACountry(addCountryForDTAAForm.getCountry()) != null) {
			model.put("error", "Country '" + addCountryForDTAAForm.getCountry() + "' is already exist.");
			return new ModelAndView("addCountryForDTAA", "model", model);
		} else {
			DTAACountry dtaaCountry = new DTAACountry();
			dtaaCountry.setCountry(addCountryForDTAAForm.getCountry());
			dtaaCountry.setId(null);
			dtaaCountry = dtaaCountryRatesDAO.saveDTAACountry(dtaaCountry);
		}

		return new ModelAndView("updateAdminSuccess", "model", model);

	}

	/*--------------------------------------------Developer   Functionality------------------------------------------------------*/

	@RequestMapping(value = "leftNavbarMenu", method = RequestMethod.GET)
	public ModelAndView leftNavbarMenu(Model model, @ModelAttribute("menu") Menu menu) {
		List<Permission> permissions = endUserDAOImpl.findAllPermission();
		model.addAttribute("permissions", permissions);
		return new ModelAndView("leftNavbarMenu", "model", model);
	}

	@RequestMapping(value = "leftNavbarSubMenu", method = RequestMethod.GET)
	public ModelAndView leftNavbarMenu(@ModelAttribute("subMenuItem") Menu subMenuItem, Model model) {
		List<Menu> menus = endUserDAOImpl.findAllMenu();
		model.addAttribute("menus", menus);
		List<Permission> permissions = endUserDAOImpl.findAllPermission();
		model.addAttribute("permissions", permissions);
		return new ModelAndView("leftNavbarSubMenu");
	}

	@RequestMapping(value = "leftNavbarSubMenu", method = RequestMethod.POST)
	public ModelAndView leftNavbarSubMenuSave(@ModelAttribute("subMenuItem") Menu menu, Model model) {
		// Menu isExist = endUserDAOImpl.findByNameAndUrlPattern(menu.getName(),
		// menu.getUrlPattern());
		List<Menu> menus = endUserDAOImpl.findAllMenu();
		List<Permission> permissions = endUserDAOImpl.findAllPermission();
		model.addAttribute("permissions", permissions);

		Menu parentMenu = endUserDAOImpl.findByMenuId(Long.parseLong(menu.getSubMenuId()));
		Date date = new Date();
		menu.setCreatedBy(getCurrentLoggedUserName());
		menu.setModifiedBy(getCurrentLoggedUserName());
		menu.setCreatedOn(date);
		menu.setModifiedOn(date);
		menu.setMenu(parentMenu);
		endUserDAOImpl.saveMenu(menu);

		model.addAttribute("menus", menus);
		model.addAttribute("sucess", "saved successfully !");
		return new ModelAndView("leftNavbarSubMenu");
	}

	@RequestMapping(value = "leftNavbarMenu", method = RequestMethod.POST)
	public ModelAndView leftNavbarMenuSave(@ModelAttribute("menu") Menu menu, Model model) {
		Menu isExist = endUserDAOImpl.findByNameAndUrlPattern(menu.getName(), menu.getUrlPattern());
		List<Permission> permissions = endUserDAOImpl.findAllPermission();
		model.addAttribute("permissions", permissions);
		if (isExist == null) {
			Date date = new Date();
			menu.setCreatedBy(getCurrentLoggedUserName());
			menu.setModifiedBy(getCurrentLoggedUserName());
			menu.setCreatedOn(date);
			menu.setModifiedOn(date);
			endUserDAOImpl.saveMenu(menu);
		} else {
			boolean name = isExist.getName().equals(menu.getName());
			if (name)
				model.addAttribute("error", "menu name already exist !");
			boolean urlPattern = isExist.getUrlPattern().equals(menu.getUrlPattern());
			if (urlPattern)
				model.addAttribute("error", "menu url pattern already exist !");
			return new ModelAndView("leftNavbarMenu", "model", model);
		}
		model.addAttribute("sucess", "saved successfully !");
		menu.setName(null);
		menu.setUrlPattern(null);
		return new ModelAndView("leftNavbarMenu", "model", model);
	}

	@RequestMapping(value = "findAllLeftNavbarMenu", method = RequestMethod.GET)
	public ModelAndView findAllMenus(@ModelAttribute("menu") Menu menu, Model model) {
		List<Menu> listMenu = endUserDAOImpl.findAllMenu();
		model.addAttribute("listMenu", listMenu);
		return new ModelAndView("findAllLeftNavbarMenu");
	}

	@RequestMapping(value = "/roleCreate", method = RequestMethod.GET)
	public ModelAndView roleCreateByAdmin(ModelMap model, @ModelAttribute Role role) throws CustomException {

		List<Role> roles = endUserDAOImpl.findAll();
		List<Menu> menuList = endUserDAOImpl.findAllMenu();
		List<Permission> permissionList = endUserDAOImpl.findAllPermission();
		model.put("menuList", menuList);
		model.put("permissionList", permissionList);
		model.put("roles", roles);
		return new ModelAndView("roleCreate", "model", model);
	}

	/*---------------------------------------Manage Menu Preferences-------------------------------------------*/

	@RequestMapping(value = "/manageMenuPreference", method = RequestMethod.GET)
	public ModelAndView manageMenuPreference(ModelMap model,
			@ModelAttribute("manageMenuPreference") ManageRoleDTO manageRole) throws CustomException {
		List<Role> roles = endUserDAOImpl.findAll();
		model.put("roles", roles);
		return new ModelAndView("manageMenuPreference", "model", model);
	}

	@RequestMapping(value = "/manageMenuPreference", method = RequestMethod.POST)
	public ModelAndView manageMenuPreferencePOST(ModelMap model,
			@ModelAttribute("manageMenuPreference") ManageRoleDTO manageRole) throws CustomException {
		List<Role> roles = endUserDAOImpl.findAll();
		List<ManageRoleDTO> maDto = new Gson().fromJson(manageRole.getJsonData(), new TypeToken<List<ManageRoleDTO>>() {
		}.getType());
		List<ManageMenuPreference> list = endUserDAOImpl.saveManageMenuPreferencesList(maDto);
		List<Menu> sortedMenu = new ArrayList<Menu>();
		for (ManageMenuPreference manageMenuPreference : list) {
			Menu menu = endUserDAOImpl.findByMenuIdAndChildSubMenuNullWithRoleId(manageMenuPreference.getMenuId(),Long.valueOf(manageRole.getRoleId()));
			sortedMenu.add(menu);
		}
		model.put("sortedMenuList", sortedMenu);
		model.put("roles", roles);
		model.addAttribute("success", "Updated Successfully !");
		return new ModelAndView("manageMenuPreference", "model", model);
	}
	

	@RequestMapping(value = "getRoleIdByMenusDetails", method = RequestMethod.POST)
	public ModelAndView getByRoleIdAndMenusDetails(Model model,
			@ModelAttribute("manageMenuPreference") ManageRoleDTO manageRole) {
		List<Menu> listOfMenus = endUserDAOImpl.findByRoleIdAndOnlyMenus(Long.valueOf(manageRole.getRoleId()));
		List<Role> roles = endUserDAOImpl.findAll();
		model.addAttribute("roles", roles);
		Boolean isExist = endUserDAOImpl.findByRoleIdInMenuPrefrences(Long.valueOf(manageRole.getRoleId()));
		if (isExist) {
			List<Menu> sortedMenu = new ArrayList<Menu>();
			List<ManageMenuPreference> list = endUserDAOImpl
					.findAllByMenuIdManageMenuPreference(Long.valueOf(manageRole.getRoleId()));
			for (ManageMenuPreference manageMenuPreference : list) {
				Menu menu = endUserDAOImpl.findByMenuIdAndChildSubMenuNullWithRoleId(manageMenuPreference.getMenuId(),Long.valueOf(manageRole.getRoleId()));
				sortedMenu.add(menu);
			}
			model.addAttribute("sortedMenuList", sortedMenu);
		} else {
			model.addAttribute("listOfMenus", listOfMenus);
		}
		return new ModelAndView("manageMenuPreference", "model", model);

	}
	
	// Create Branch 
	@RequestMapping(value = "/addBranchFromAdmin")
	public ModelAndView addBranchFromAdmin(Model model, @ModelAttribute Branch branch)
			throws CustomException {

		List<Branch> allList = branchDAO.getAllBranches();
		model.addAttribute("allList", allList);
		model.addAttribute("branch", branch);
	
		return new ModelAndView("addBranchFromAdmin", "model", model);

	}

	@RequestMapping(value = "/addedBranch", method = RequestMethod.POST)
	public ModelAndView addedBranch(Model model, @ModelAttribute Branch branch, RedirectAttributes attribute,
			Long menuId) throws CustomException {
		
		String branchCodeAdded = branch.getBranchCode();
		String branchNameAdded = branch.getBranchName();
		List<Branch> allList = branchDAO.getAllBranches();
		model.addAttribute("allList", allList);
		model.addAttribute("branch", branch);
		
		Long count = branchDAO.getCountOfBranchNameAndCode(branchNameAdded, branchCodeAdded);

		if (count == 0) {

			Branch addBranch = new Branch();
			addBranch.setBranchCode(branch.getBranchCode());
			addBranch.setBranchName(branch.getBranchName());
			addBranch.setArea(branch.getArea());
			addBranch.setCityOrTown(branch.getCityOrTown());
			addBranch.setUrbanOrRural(branch.getUrbanOrRural());
			addBranch.setState(branch.getState());
			branchDAO.save(addBranch);

		}

		return new ModelAndView("updateAdminSuccess", "model", model);
	
	}
}

