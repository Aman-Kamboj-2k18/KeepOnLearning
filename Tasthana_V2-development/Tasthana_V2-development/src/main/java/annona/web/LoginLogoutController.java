package annona.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import annona.dao.CustomerDAO;
import annona.dao.EndUserDAO;
import annona.dao.OrganisationDAO;
import annona.domain.Customer;
import annona.domain.EndUser;
import annona.domain.ModeOfPayment;
import annona.domain.Organisation;
import annona.domain.Role;
import annona.form.EndUserForm;
import annona.form.LoginForm;
import annona.form.OrganizationForm;
import annona.services.DBService;
import annona.utility.Constants;

@Controller
@RequestMapping("/auth")
public class LoginLogoutController {

	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	EndUserDAO endUserDAOImpl;

	@Autowired
	OrganisationDAO organisationDAO;

//	@Autowired
//	DBService dbService;

	@Autowired
	ApplicationContext context;

	// protected static Logger logger = Logger.getLogger("controller");

	protected Logger logger = Logger.getLogger(LoginLogoutController.class.getName());

	@RequestMapping("/login")
	public ModelAndView login(@RequestParam(value = "error", defaultValue = "0") Short error, ModelMap model,
			HttpServletRequest request, @ModelAttribute LoginForm loginForm, EndUserForm endUserForm) {
		logger.debug("Received request to show login page");

		logger.info("Received request to show login page.............");
		/*
		 * if(error == null) { error = 0; }
		 */
		if (error == 1) {
			model.put("error", "You have entered an invalid username or password!");
		} else if (error == 2) {
			model.put("error", "Your account is blocked!!! Please contact Admin");
		} else if (error == 3) {
			model.put("error", "Your account is expired!!! Please contact Admin");
		}

		// Create two Default Roles (ROLE_ADMIN, ROLE_USER) here.
		String strArray[] = { "ROLE_ADMIN", "ROLE_USER" };
		for (int i = 0; i < strArray.length; i++) {
			String roleName = strArray[i];
			Role role = endUserDAOImpl.findByRoleName(roleName);
			if (role == null) {
				// Create Role
				role = new Role();
				role.setRole(roleName);
				role.setRoleDisplayName(roleName.substring(roleName.indexOf("_") + 1));
				role.setCreatedBy("System");
				role.setModifiedBy("System");
				role.setCreatedOn(new Date());
				role.setModifiedOn(new Date());
				role.setIsActive(true);
				// System will not set any menu from here
				// role.setMenu(endUserDAOImpl.findAllMenu());
				endUserDAOImpl.createRole(role);
			}
		}

		model.put("endUserForm", endUserForm);
		List<Organisation> organisationList = organisationDAO.getAllList();
		model.put("organisationList", organisationList);

		// return "loginPage";
		return new ModelAndView("loginPage", "model", model);
	}

	@RequestMapping(value = "/createDB", method = RequestMethod.POST)
	public String createDB(ModelMap model, @ModelAttribute OrganizationForm organizationForm,
			RedirectAttributes attributes) {

		boolean isSuccessful = true;
		DBService dbService = new DBService();
		try {
			dbService.createDB(organizationForm.getOrganisationShortName());
		} catch (Exception e) {
			isSuccessful = false;
			e.printStackTrace();
		}

		try {
			if (isSuccessful) {
				// Restore the blank DB of tasthana
				dbService.restoreDB(organizationForm.getOrganisationShortName());
			}
		} catch (Exception e) {
			isSuccessful = false;
			e.printStackTrace();
		}

		try {
			if (isSuccessful) {
				// In the base database insert a new row for the organization
				Organisation organisation = new Organisation();
				organisation.setOrganisationName(organizationForm.getOrganisationName());
				organisation.setOrganisationShortName(organizationForm.getOrganisationShortName());
				organisationDAO.insertUniqueOrganisation(organisation);
			}

			// Now, in the newly created DB organization and admin needs to create
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/auth/welcome";
	}

	@RequestMapping("/redirectOrganization")
	public String redirectOrganization(ModelMap model,
			@QueryParam("organizationShortName") String organizationShortName) {

		System.out.println("entered in redirecting");

		System.setProperty("database.url", "jdbc:postgresql://localhost:5432/" + organizationShortName);
		((XmlWebApplicationContext) context).refresh();

		return "redirect:/auth/login";
	}

	@RequestMapping("/welcome")
	public ModelAndView welcome(@RequestParam(value = "error", defaultValue = "0") Short error, ModelMap model,
			HttpServletRequest request, OrganizationForm organizationForm) {
		logger.debug("Received request to show welcome page");

		model.put("organizationForm", organizationForm);
		List<Organisation> organisationList = organisationDAO.getAllList();
		model.put("organisationList", organisationList);

		// return "loginPage";
		return new ModelAndView("redirect:main/default", "model", model);
	}

	/*
	 * @RequestMapping(value = "/logout", method = RequestMethod.GET) public String
	 * getlogout() { logger.debug("Received request to show denied page");
	 * 
	 * // This will resolve to /WEB-INF/jsp/deniedpage.jsp return "loginPage"; }
	 */
	@RequestMapping(value = "/denied")
	public String denied() {
		logger.debug("Received request to show Denied page");
		return "redirect:/auth/login";
		// return "access/denied";
	}

	@RequestMapping(value = "/logout")
	public ModelAndView logoutSuccess(EndUserForm endUserForm, ModelMap model) {

		model.put("endUserForm", endUserForm);
		List<Organisation> organisationList = organisationDAO.getAllList();
		model.put("organisationList", organisationList);

		return new ModelAndView("loginPage", "model", model);
	}

	@RequestMapping(value = "/login-alert", method = RequestMethod.GET)
	public String getLoginALertPage(ModelMap model) {

		logger.debug("Received request to show login alert page");
		model.put("error", "You can only log-in once! Either you wait for you session to expire "
				+ "or clear your browser's cache " + "or manually log-out!");

		return "login-alert-page";
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public ModelAndView forgotPassword(ModelMap model, @ModelAttribute EndUserForm endUserForm) {

		model.put("endUserForm", endUserForm);

		return new ModelAndView("forgotPassword", "model", model);

	}

	@RequestMapping(value = "/checkForgotPassword", method = RequestMethod.POST)
	public ModelAndView checkForgotPassword(@ModelAttribute EndUserForm endUserForm, RedirectAttributes attributes,
			ModelMap model) {

		ModelAndView mav = new ModelAndView();

		List<EndUser> endUser = endUserDAOImpl.findByUsernameAndEmail(endUserForm.getUserName(), endUserForm.getEmail())
				.getResultList();

		if (endUser.size() == 0)

		{

			attributes.addFlashAttribute("success", "Incorrect details entered");

			mav = new ModelAndView("redirect:forgotPassword");

		} else {

			endUserForm.setId(endUser.get(0).getId());

			endUserForm.setTransactionId(endUser.get(0).getTransactionId());

			model.put("endUserForm", endUserForm);

			mav = new ModelAndView("newPassword", "model", model);

		}

		return mav;
	}

	@RequestMapping(value = "/newForgotPassword", method = RequestMethod.POST)
	public ModelAndView newForgotPassword(ModelMap model, @ModelAttribute EndUserForm endUserForm, BindingResult result,
			RedirectAttributes attributes) {

		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());

		endUser.setId(endUserForm.getId());

		endUser.setPassword(endUserForm.getNewPassword());
		endUser.setTransactionId(endUserForm.getTransactionId());

		endUserDAOImpl.update(endUser);

		model.put("endUserForm", endUserForm);

		return new ModelAndView("paswwordChanged", "model", model);

	}

	/**
	 * Method to display Change Password screen
	 * 
	 * @param model
	 * @param endUserForm
	 * @return
	 */
	@RequestMapping(value = "/loginChangePassword", method = RequestMethod.GET)
	public ModelAndView getLoginChangePassword(ModelMap model, @RequestParam Long id,
			@ModelAttribute EndUserForm endUserForm) {
		endUserForm.setId(id);
		model.put("endUserForm", endUserForm);
		return new ModelAndView("loginChangePassword", "model", model);
	}

	/**
	 * Method to update EndUser table with Password expiry date and New password
	 * 
	 * @param model
	 * @param endUserForm
	 * @param result
	 * @param attributes
	 * @return redirect to Login Page
	 */
	@RequestMapping(value = "/updateLoginChangePwd", method = RequestMethod.POST)
	public String updateLoginChangePwd(ModelMap model, @ModelAttribute EndUserForm endUserForm, BindingResult result,
			RedirectAttributes attributes) {

		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());
		Customer custDetails = customerDAO.getByUserName(endUser.getUserName());

		if (!endUser.getPassword().equals(endUserForm.getPassword())) {
			attributes.addFlashAttribute("error", "Old Password Incorrect");
			return "redirect:/auth/loginChangePassword?id=" + endUser.getId();
		} else if (endUser.getPassword().equals(endUserForm.getNewPassword())) {
			attributes.addFlashAttribute("error", "Old Password and New Password cannot be same");
			return "redirect:/auth/loginChangePassword?id=" + endUser.getId();
		}
		endUser.setPassword(endUserForm.getNewPassword());

		endUser.setPasswordFlag(1);

		if (custDetails != null) {
			custDetails.setPassword(endUserForm.getNewPassword());
			customerDAO.updateUser(custDetails);
		}
		endUserDAOImpl.update(endUser);

		return "redirect:login";

	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String registerUser(ModelMap model, @ModelAttribute EndUserForm endUserForm, RedirectAttributes attributes) {

		EndUser user = new EndUser();
		user.setDisplayName(endUserForm.getDisplayName());
		user.setEmail(endUserForm.getEmail());
		user.setContactNo(endUserForm.getContactNo());
		user.setUserName(endUserForm.getUserName());
		user.setPassword(endUserForm.getPassword());
		user.setCurrentRole("ROLE_ADMIN");
		user.setDesignation("Admin");
		user.setRole(1);
		user.setTheme("themeBlue");
		user.setPasswordFlag(1);
		user.setTransactionId(endUserForm.getTransactionId());
		user.setStatus("Approved");
		user.setPrefferedLanguage("en");

		List<EndUser> endUser = endUserDAOImpl.findByUsername(endUserForm.getUserName()).getResultList();

		if (endUser.size() == 0) {
			model.put("endUserForm", endUserForm);
			EndUser userCreated = endUserDAOImpl.createUser(user);

			attributes.addFlashAttribute("success", "Registered successfully..Please Login Now");
			return "redirect:/auth/login";

		} else {

			model.put(Constants.ERROR, Constants.ROLE);
			model.put("endUserForm", endUserForm);
			// return new ModelAndView("createRole", "model", model);
			attributes.addFlashAttribute("error", "User already exists");
			return "redirect:/auth/login";
		}

	}
}
