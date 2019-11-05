package annona.web;

import java.text.SimpleDateFormat;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.theme.CookieThemeResolver;

import annona.dao.AccountDetailsDAO;
import annona.dao.CustomerDAO;
import annona.dao.DepositHolderDAO;
import annona.dao.DepositModificationDAO;
import annona.dao.EndUserDAO;
import annona.dao.FDRatesDAO;
import annona.dao.FixedDepositDAO;
import annona.dao.FundTransferDAO;
import annona.dao.NomineeDAO;
import annona.dao.OrganisationDAO;
import annona.dao.RatesDAO;
import annona.dao.TransactionDAO;
import annona.domain.AccountDetails;
import annona.domain.BankConfiguration;
import annona.domain.Customer;
import annona.domain.CustomerCategory;
import annona.domain.Deposit;
import annona.domain.EndUser;
import annona.domain.Role;
import annona.domain.Transaction;
import annona.exception.CustomException;
import annona.form.BankConfigurationForm;
import annona.form.CustomerCategoryForm;
import annona.form.CustomerForm;
import annona.form.DepositForm;
import annona.form.EndUserForm;
import annona.form.FixedDepositForm;
import annona.form.HolderForm;
import annona.services.CustomUserDetailsService;
import annona.services.DateService;
import annona.services.DepositService;
import annona.services.FDService;
import annona.services.ImageService;
import annona.utility.Constants;

@Controller
@RequestMapping("/appMng")
public class ApprovalMngController {

	@PersistenceContext(name = "PersistentUnitName")
	private EntityManager entityManager;
 
	@Autowired
	EndUserDAO endUserDAOImpl;

	@Autowired
	EndUserForm endUserForm;

	@Autowired
	TransactionDAO transactionDAO;

	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	CustomerForm customerForm;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	FixedDepositDAO fixedDepositDao;

	@Autowired
	FixedDepositForm fixedDepositForm;

	@Autowired
	RatesDAO ratesDAO;

	@Autowired
	FDService fdService;

	@Autowired
	FDRatesDAO fdRatesDAO;

	@Autowired
	FundTransferDAO fundTransferDAO;
	
	@Autowired
	AccountDetailsDAO accountDetailsDAO;

	@Autowired
	DepositService depositService;


	CookieThemeResolver themeResolver = new CookieThemeResolver();

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	protected Logger log = Logger.getLogger(ApprovalMngController.class.getName());

	private String getCurrentLoggedUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();

	}

	private EndUser getCurrentLoggedUserDetails() {

		EndUser endUser = endUserDAOImpl.findByUsername(getCurrentLoggedUserName()).getSingleResult();

		return endUser;

	}
	@RequestMapping(value = "selectLoggedInRole", method = RequestMethod.GET)
	private ModelAndView selectLoggedInRole() {

	return new ModelAndView("redirect:/main/default");

	}
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

	@RequestMapping(value = "/updateTransaction", method = RequestMethod.GET)
	 public RedirectAttributes updateTransaction(String status,RedirectAttributes attribute) {

	  Date curDate = DateService.loginDate;
	  attribute.addFlashAttribute(Constants.TRANSACTIONID, fdService.generateRandomString());
	  attribute.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
	  attribute.addFlashAttribute(Constants.TRANSACTIONSTATUS, status);

	  return attribute;
	 }
	
	@RequestMapping(value = "/editAMProfile", method = RequestMethod.GET)
	public ModelAndView editAdminProfile(ModelMap model, @RequestParam("id") Long id, RedirectAttributes attributes) throws CustomException{

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

		return new ModelAndView("editAMProfile", "model", model);

	}

	@RequestMapping(value = "/updateImageForProfile", method = RequestMethod.POST)
	public String updateImageForProfile(ModelMap model, @ModelAttribute EndUserForm endUserForm) throws CustomException{
		try {
			EndUser userProfile = endUserDAOImpl.findId(endUserForm.getId());
			userProfile.setImage(endUserForm.getFile().getBytes());
			userProfile.setImageName(endUserForm.getFile().getOriginalFilename());
			endUserDAOImpl.update(userProfile);

		} catch (Exception e) {
			e.getMessage();
		}
		return "redirect:editAMProfile?id=" + endUserForm.getId();
	}

	@RequestMapping(value = "/confirmEditAMProfile", method = RequestMethod.POST)
	public ModelAndView confirmEditAdminProfile(ModelMap model, @ModelAttribute EndUserForm endUserForm) throws CustomException{

		model.put("endUserForm", endUserForm);

		return new ModelAndView("confirmEditAMProfile", "model", model);

	}

	@RequestMapping(value = "/updateAMDetails", method = RequestMethod.POST)
	public ModelAndView updateAdminDetails(@ModelAttribute EndUserForm endUserForm,
			BindingResult result, RedirectAttributes attributes) throws CustomException{

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

		attributes=updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:updateAMSuccess");

	}

	@RequestMapping(value = "/editAMPWD", method = RequestMethod.GET)
	public ModelAndView editAdminPWD(ModelMap model, @RequestParam("id") Long id, RedirectAttributes attributes) throws CustomException{

		EndUser userProfile = endUserDAOImpl.findId(id);

		endUserForm.setId(userProfile.getId());

		endUserForm.setTransactionId(userProfile.getTransactionId());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("editAMPWD", "model", model);

	}

	@RequestMapping(value = "/updateEditAMPWD", method = RequestMethod.POST)
	public ModelAndView updateEditAdminPWD(ModelMap model, @ModelAttribute EndUserForm endUserForm,
			BindingResult result, RedirectAttributes attributes) throws CustomException{

		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());

		endUser.setId(endUserForm.getId());

		endUser.setPassword(endUserForm.getNewPassword());
		endUser.setTransactionId(endUserForm.getTransactionId());

		endUserDAOImpl.update(endUser);
		attributes=updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:updateAMSuccess");

	}

	@RequestMapping(value = "/themeChange", method = RequestMethod.GET)
	public String getThemeChange(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws CustomException{
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

		return "redirect:apprMng";
	}


	@RequestMapping(value = "/getLocaleLang", method = RequestMethod.GET)
	public String getLocaleLang(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws CustomException{

		log.info("Received request for locale change");
		EndUser user = endUserDAOImpl.findByUsername(getCurrentLoggedUserName()).getSingleResult();
		LocaleResolver localeResolver = new CookieLocaleResolver();
		Locale locale = new Locale(request.getParameter("lang"));
		localeResolver.setLocale(request, response, locale);
		user.setPrefferedLanguage(request.getParameter("lang"));

		endUserDAOImpl.update(user);

		return "redirect:apprMng";
	}

	@RequestMapping(value = "/bankEmpApprov", method = RequestMethod.GET)
	public ModelAndView bankempApp(ModelMap model, RedirectAttributes attributes) throws CustomException{

		List<EndUser> userbankList = endUserDAOImpl.getByRole().getResultList();
		if (userbankList != null && userbankList.size() > 0) {
			model.put("userbankList", userbankList);
			model.put("endUserForm", endUserForm);
			return new ModelAndView("bankEmpApprov", "model", model);
		} else {
			return new ModelAndView("noDataFound1", "model", model);
		}

	}


	@RequestMapping(value = "/approveBankEmp", method = RequestMethod.GET)
	public ModelAndView approvBankEmp(@RequestParam Long id, ModelMap model, RedirectAttributes attributes) throws CustomException{

		EndUser userList = endUserDAOImpl.findId(id);

		endUserForm.setId(userList.getId());
		endUserForm.setUserName(userList.getUserName());
		endUserForm.setContactNo(userList.getContactNo());
		endUserForm.setEmail(userList.getEmail());
		endUserForm.setDisplayName(userList.getDisplayName());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("approveBankEmp", "model", model);
	}


	@RequestMapping(value = "/approveBankEmpConfrim", method = RequestMethod.POST)
	public ModelAndView approveBankEmpConfrim(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException{

		model.put("endUserForm", endUserForm);

		return new ModelAndView("approveBankEmpConfrim", "model", model);
	}

	@RequestMapping(value = "/updateEmpStatus", method = RequestMethod.POST)
	public ModelAndView updateEmpStatus(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException{

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

		attributes=updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:updateAMSuccess");
	}

	@RequestMapping(value = "/customerApprovalList", method = RequestMethod.GET)
	public ModelAndView customerHeadApproval(ModelMap model, RedirectAttributes attributes) throws CustomException{

		List<Customer> customerList = customerDAO.getByPending().getResultList();

		if (customerList != null && customerList.size() > 0) {

			model.put("customerList", customerList);
			model.put("customerForm", customerForm);
			return new ModelAndView("customerApprovalList", "model", model);
		} else {
			return new ModelAndView("noDataFound1", "model", model);
		}

	}

	@RequestMapping(value = "/approveCustomer", method = RequestMethod.GET)
	public ModelAndView approveCustomerHead(@RequestParam Long id, ModelMap model, RedirectAttributes attributes) throws CustomException{

		Customer customer = customerDAO.getById(id);

		customerForm.setId(customer.getId());
		customerForm.setCustomerName(customer.getCustomerName());
		customerForm.setCustomerID(customer.getCustomerID());
		customerForm.setAddress(customer.getAddress());
		customerForm.setContactNum(customer.getContactNum());
		customerForm.setEmail(customer.getEmail());
		customerForm.setUserName(customer.getUserName());
		customerForm.setTransactionId(customer.getTransactionId());
		model.put("customerForm", customerForm);
		return new ModelAndView("approveCustomer", "model", model);
	}

	@RequestMapping(value = "/approveCustomerConfirm", method = RequestMethod.POST)
	public ModelAndView approveCustomerConfirm(@ModelAttribute CustomerForm customerForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException{

		model.put("customerForm", customerForm);

		return new ModelAndView("approveCustomerConfirm", "model", model);
	}

	@RequestMapping(value = "/customerSave", method = RequestMethod.POST)
	  public String customerSave(@ModelAttribute CustomerForm customerForm, ModelMap model,
	    RedirectAttributes attributes) throws CustomException{

	   Customer customer = customerDAO.getById(customerForm.getId());

	   customer.setCustomerName(customerForm.getCustomerName());
	   customer.setAddress(customerForm.getAddress());
	   customer.setStatus(customerForm.getStatus());
	   customer.setComment(customerForm.getComment());
	   Date date = DateService.getCurrentDateTime();
	   customer.setApproveDate(date);
	   customer.setUniqueKey(customerForm.getUniqueKey());
	   customer.setPassword(customer.getContactNum());
	   customer.setTransactionId(customerForm.getTransactionId());
	  
	   customerDAO.updateUser(customer);

	   String status = customerForm.getStatus();

	   if (status.equals(Constants.APPROVED)) {
	    EndUser endUser = new EndUser();
	    Transaction transaction = new Transaction();
	    endUser.setUserName(customerForm.getUserName());
	    endUser.setPassword(customerForm.getContactNum());
	    endUser.setContactNo(customerForm.getContactNum());
	    endUser.setCurrentRole("ROLE_USER");
	    endUser.setRole(4);
	    endUser.setPrefferedLanguage("en");
	    endUser.setTheme("themeBlue");
	    endUser.setEmail(customerForm.getEmail());
	    endUser.setDisplayName(customerForm.getCustomerName());
	    endUser.setStatus(customerForm.getStatus());
	    endUser.setTransactionId(customerForm.getTransactionId());
	    endUser.setNotificationStatus(Constants.NOTIFICATION);
	    endUser.setPasswordFlag(0);
	    endUser.setCustomerId(customer.getId());

	    endUserDAOImpl.createUser(endUser);

	   Date accountDate = DateService.getCurrentDateTime();

	   transaction.setTransactionId(customerForm.getTransactionId());
	   transaction.setTransactionStatus(Constants.CUSTOMERSTATUS);
	   transaction.setTransactionType(Constants.CUSTOMER);
	   transaction.setCustomerID(customerForm.getCustomerID());
	   transaction.setAccountDate(accountDate);
	   transactionDAO.insertTransaction(transaction);

	  }

	  Transaction trans = new Transaction();
	  trans.setTransactionId(customerForm.getTransactionId());
	  trans.setTransactionStatus(Constants.APPCUSTOMER);
	  trans.setTransactionType(Constants.APPSTATUS);

	  transactionDAO.insertTransaction(trans);

	  String stat = customerForm.getStatus();
	  String username = customerForm.getUserName();
	  String email = customerForm.getEmail();
	  String password = customer.getContactNum();
	  String currentrole = "ROLE_USER";
	  try {
	   if (stat.equals(Constants.APPROVED)) {
	    String tex = Constants.CUSTOMERSUBJECT;
	    SimpleMailMessage emails = new SimpleMailMessage();
	    emails.setTo(email);
	    emails.setSubject(tex);
	    emails.setText(Constants.HELLO + customerForm.getCustomerName() + Constants.BANKBODY1 + username
	      + Constants.BANKBODY2 + password + Constants.CURRENTROLE + currentrole
	      + Constants.BANKSIGNATURE);
	    mailSender.send(emails);
	    SimpleMailMessage emails1 = new SimpleMailMessage();
	    emails1.setTo(email);

	   } else if (status.equals(Constants.CLOSED)) {
	    String tex = Constants.CUSTOMERSUBJECT;
	    SimpleMailMessage emails = new SimpleMailMessage();
	    emails.setTo(email);
	    emails.setSubject(tex);
	    emails.setText(Constants.HELLO + customerForm.getCustomerName() + Constants.BANKBODY5
	      + Constants.BANKSIGNATURE);
	    mailSender.send(emails);
	   } else {
	    String tex = Constants.CUSTOMERSUBJECT;
	    SimpleMailMessage emails = new SimpleMailMessage();
	    emails.setTo(email);
	    emails.setSubject(tex);
	    emails.setText(Constants.HELLO + customerForm.getCustomerName() + Constants.BANKBODY6
	      + Constants.BANKSIGNATURE);
	    mailSender.send(emails);
	   }

	  } catch (Exception e) {
	   System.out.println(e.getMessage() + e);

	  }
	  attributes=updateTransaction("Saved Successfully", attributes);
	  return "redirect:updateAMSuccess";
	 
	 }


	@RequestMapping(value = "/approveFDConfirm", method = RequestMethod.POST)
	public ModelAndView approveFDConfirm(@ModelAttribute FixedDepositForm fixedDepositForm, ModelMap model) throws CustomException{

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("approveFDConfirm", "model", model);
	}

	@RequestMapping(value = "/updateFDSuccess", method = RequestMethod.GET)
	public ModelAndView updateFDSuccess(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm) throws CustomException{

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("updateFDSuccess", "model", model);

	}

	@RequestMapping(value = "/fdPendingList", method = RequestMethod.GET)
	 public ModelAndView fdPendingList(ModelMap model, RedirectAttributes attributes) throws CustomException{

	  ModelAndView mav = new ModelAndView();

	  List<DepositForm> depositList = fixedDepositDao.getAllPendingDepositsList();
	  if (depositList != null && depositList.size() > 0) {

	   model.put("depositList", depositList);
	   mav = new ModelAndView("fdPendingList", "model", model);
	  } else {
	   mav = new ModelAndView("noDataFound1", "model", model);
	  }

	  return mav;
	 }

	@RequestMapping(value = "/approveFdPendingList", method = RequestMethod.GET)
	public ModelAndView approveFdPendingList(@RequestParam Long id, ModelMap model, RedirectAttributes attributes,
			@ModelAttribute FixedDepositForm fixedDepositForm) throws CustomException{

		Deposit deposit = fixedDepositDao.findById(id);
		fixedDepositForm.setDepositId(deposit.getId());

		fixedDepositForm.setDepositedAmt((double) deposit.getDepositAmount());
		fixedDepositForm.setMaturityDate(deposit.getMaturityDate());

		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("approveFdPendingList", "model", model);
	}

	@RequestMapping(value = "/approveDepositConfrim", method = RequestMethod.POST)
	public ModelAndView approveBankEmpConfrim(@ModelAttribute FixedDepositForm fixedDepositForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException{

		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("approveDepositConfrim", "model", model);
	}

	
	@RequestMapping(value = "/updateAMSuccess", method = RequestMethod.GET)
	public ModelAndView updateAMSuccess() throws CustomException{
		return new ModelAndView("updateAMSuccess");

	}
	
	@RequestMapping(value = "/updateApprovalStatus", method = RequestMethod.POST)
	public ModelAndView updateApprovalStatus(@ModelAttribute FixedDepositForm fixedDepositForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException{

		Deposit deposit = fixedDepositDao.findById(fixedDepositForm.getId());

		deposit.setApprovalStatus(fixedDepositForm.getStatus());
		deposit.setComment(fixedDepositForm.getComment());

		fixedDepositDao.updateApprovalStatus(deposit);

		attributes=updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:updateAMSuccess");
	

	}

	@RequestMapping(value = "/apprMng", method = RequestMethod.GET)
	public ModelAndView fdCustomerLists(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm, HttpServletRequest request, HttpServletResponse response) throws CustomException{

		ModelAndView mav = new ModelAndView();
		EndUser user = getCurrentLoggedUserDetails();

		List<Deposit> depositLists = fixedDepositDao.getPendingDeposits();
		if (depositLists.size() > 0) {
			model.put("depositLists", depositLists);
			mav = new ModelAndView("approverMngPendingDepositList", "model", model);
		} else {
			mav = new ModelAndView("noDataFound1", "model", model);
		}
		model.put("endUser", user);
		return mav;
	}

	@RequestMapping(value = "/customerSuspend", method = RequestMethod.GET)
	public ModelAndView customerSuspend(ModelMap model) throws CustomException{

		// Collection<Customer> customers = customerDAO.findAllCustomers();
		// fixedDepositForm.setCustomers(customers);
		FixedDepositForm fixedDepositForm = new FixedDepositForm();
		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("customerSuspend", "model", model);

	}

	 @RequestMapping(value = "/searchCustomer", method = RequestMethod.POST)
	 public ModelAndView searchCustomer(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm) throws CustomException{

		 String cusId = fixedDepositForm.getCustomerID();
			String cusName = fixedDepositForm.getCustomerName();
			String cusNum = fixedDepositForm.getContactNum();
			String cusEmail = fixedDepositForm.getEmail();

			List<CustomerForm> customerList = new ArrayList<CustomerForm>();
			customerList = customerDAO.searchCustomer(cusId,cusName, cusNum, cusEmail);
			
	  if(customerList.size()!=0){
	   model.put("customerList", customerList);
	   return new ModelAndView("customerSuspend", "model", model);
	  
	  }
	  else{
	   model.put(Constants.ERROR, Constants.customerNotFound);
	   return new ModelAndView("customerSuspend", "model", model);
	  }
	 }

	@RequestMapping(value = "/suspendCustomer", method = RequestMethod.GET)
	public ModelAndView suspendCustomer(@RequestParam Long id, ModelMap model, RedirectAttributes attributes) throws CustomException{

		EndUser userList = endUserDAOImpl.findUserByCustomerId(id);

		endUserForm.setId(userList.getId());
		endUserForm.setUserName(userList.getUserName());
		endUserForm.setContactNo(userList.getContactNo());
		endUserForm.setEmail(userList.getEmail());
		endUserForm.setDisplayName(userList.getDisplayName());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendCustomer", "model", model);
	}


	@RequestMapping(value = "/suspendCustomerConfrim", method = RequestMethod.POST)
	public ModelAndView suspendCustomerConfrim(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException{

		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendCustomerConfrim", "model", model);
	}

	@RequestMapping(value = "/updateCustomerStatus", method = RequestMethod.POST)
	  public ModelAndView updateCustomerStatus(@ModelAttribute EndUserForm endUserForm, ModelMap model,
	    RedirectAttributes attributes) throws CustomException{

	   EndUser userList = endUserDAOImpl.findId(endUserForm.getId());
	   Transaction transaction = new Transaction();
	   userList.setId(endUserForm.getId());
	   userList.setUserName(endUserForm.getUserName());
	   userList.setContactNo(endUserForm.getContactNo());
	   userList.setEmail(endUserForm.getEmail());
	   userList.setStatus(endUserForm.getStatus());
	   transaction.setTransactionId(endUserForm.getTransactionId());
	   transaction.setTransactionType(Constants.APPBANK);
	   transaction.setTransactionStatus(Constants.APPSTATUS);
	   transactionDAO.insertTransaction(transaction);
	   endUserDAOImpl.update(userList);
	   
	   Customer customer = customerDAO.getByUserName(endUserForm.getUserName());
	   customer.setStatus(Constants.Suspended);
	   customerDAO.updateUser(customer);
	   
	   attributes=updateTransaction("Updated Successfully", attributes);
	   return new ModelAndView("redirect:updateAMSuccess");
	  }

	@RequestMapping(value = "/bankEmpSuspend", method = RequestMethod.GET)
	public ModelAndView bankEmpSuspend(ModelMap model) throws CustomException{

		EndUserForm endUserForm1 = new EndUserForm();
		model.put("endUserForm", endUserForm1);
		return new ModelAndView("bankEmpSuspend", "model", model);

	}

	@RequestMapping(value = "/searchBankEmp", method = RequestMethod.POST)
	 public ModelAndView searchBankEmp(ModelMap model, @ModelAttribute EndUserForm endUserForm) throws CustomException{
		String bankId = endUserForm.getBankId();
	  String userName = endUserForm.getUserName();
	  String contactNo = endUserForm.getContactNo();
	  String email = endUserForm.getEmail();

	  List<EndUser> endUserList = new ArrayList<EndUser>();
	  endUserList = endUserDAOImpl.searchUser(bankId,userName, contactNo, email, 2);
	  if(endUserList.size()!=0){
	  model.put("endUserList", endUserList);
	  return new ModelAndView("searchBankEmp", "model", model);
	  }
	  else
	  {
	   model.put(Constants.ERROR, Constants.bankEmpNotFound);
	   return new ModelAndView("searchBankEmp", "model", model); 
	   
	  }
	 }

	@RequestMapping(value = "/suspendBankEmp", method = RequestMethod.GET)
	public ModelAndView suspendBankEmp(@RequestParam Long id, ModelMap model, RedirectAttributes attributes) throws CustomException{

		EndUser userList = endUserDAOImpl.findId(id);

		endUserForm.setId(userList.getId());
		endUserForm.setUserName(userList.getUserName());
		endUserForm.setContactNo(userList.getContactNo());
		endUserForm.setEmail(userList.getEmail());
		endUserForm.setDisplayName(userList.getDisplayName());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendBankEmp", "model", model);
	}


	@RequestMapping(value = "/suspendBankEmpConfrim", method = RequestMethod.POST)
	public ModelAndView suspendBankEmpConfrim(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException{

		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendBankEmpConfrim", "model", model);
	}


	@RequestMapping(value = "/updateBankEmpStatus", method = RequestMethod.POST)
	 public ModelAndView updateBankEmpStatus(@ModelAttribute EndUserForm endUserForm, ModelMap model,
	   RedirectAttributes attributes) throws CustomException{

	  EndUser userList = endUserDAOImpl.findId(endUserForm.getId());
	  Transaction transaction = new Transaction();
	  userList.setId(endUserForm.getId());
	  userList.setUserName(endUserForm.getUserName());
	  userList.setContactNo(endUserForm.getContactNo());
	  userList.setEmail(endUserForm.getEmail());
	  userList.setStatus(endUserForm.getStatus());
	  transaction.setTransactionId(endUserForm.getTransactionId());
	  transaction.setTransactionType(Constants.APPBANK);
	  transaction.setTransactionStatus(Constants.APPSTATUS);
	  transactionDAO.insertTransaction(transaction);
	  endUserDAOImpl.update(userList);

	  attributes=updateTransaction("Updated Successfully", attributes);
	  return new ModelAndView("redirect:updateAMSuccess");
	 }
	
	@RequestMapping(value = "/customerSuspendList", method = RequestMethod.GET)
	public ModelAndView customerSuspendList(ModelMap model, @ModelAttribute EndUserForm endUserForm) throws CustomException{



		Role role=endUserDAOImpl.findByRoleName("ROLE_USER");
		List<EndUser> suspendedList = endUserDAOImpl.getSuspendedCustomerList("Suspended", role.getId());



		if (suspendedList.size() > 0) {
			model.put("suspendedList", suspendedList);
			model.put("endUserForm", endUserForm);
			return new ModelAndView("customerSuspendedList", "model", model);
		} else {

			return new ModelAndView("noDataFound1");
		}

	}


	@RequestMapping(value = "/revokeCustomer", method = RequestMethod.GET)
	public ModelAndView suspendCustomerApprove(Long id, ModelMap model, RedirectAttributes attributes) throws CustomException{
		EndUser endUser = endUserDAOImpl.findId(id);
		endUserForm.setUserName(endUser.getUserName());
		endUserForm.setEmail(endUser.getEmail());
		endUserForm.setContactNo(endUser.getContactNo());
		endUserForm.setId(id);
		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendCustomerApprove", "model", model);
	}

	@RequestMapping(value = "/updateCustomerStatusAppr", method = RequestMethod.POST)
	 public ModelAndView updateCustomerStatusAppr(@ModelAttribute EndUserForm endUserForm, ModelMap model,
	   RedirectAttributes attributes) throws CustomException{

	  EndUser userList = endUserDAOImpl.findId(endUserForm.getId());
	  Transaction transaction = new Transaction();
	  userList.setId(endUserForm.getId());
	  userList.setUserName(endUserForm.getUserName());
	  userList.setContactNo(endUserForm.getContactNo());
	  userList.setEmail(endUserForm.getEmail());
	  userList.setStatus(endUserForm.getStatus());
	  transaction.setTransactionType(Constants.APPBANK);
	  transaction.setTransactionStatus(Constants.APPSTATUS);
	  transactionDAO.insertTransaction(transaction);
	  endUserDAOImpl.update(userList);

	  attributes=updateTransaction("Updated Successfully", attributes);
	  return new ModelAndView("redirect:updateAMSuccess");
	 }

	@RequestMapping(value = "/bankEmpSuspendList", method = RequestMethod.GET)
	public ModelAndView bankEmpSuspendList(ModelMap model, @ModelAttribute EndUserForm endUserForm) throws CustomException{


		Role role=endUserDAOImpl.findByRoleName("Bank Employee");
		List<EndUser> suspendedList = endUserDAOImpl.getSuspendedCustomerList("Suspended", role.getId());


		

		if (suspendedList.size() > 0) {
			model.put("suspendedList", suspendedList);
			model.put("endUserForm", endUserForm);
			return new ModelAndView("customerSuspendedList", "model", model);
		} else {

			return new ModelAndView("noDataFound1");
		}

	}
	
	@RequestMapping(value = "/bankEmpActiveList", method = RequestMethod.GET)
	 public ModelAndView bankEmpActiveList(ModelMap model) throws CustomException{



	  Role role=endUserDAOImpl.findByRoleName("Bank Employee");
		
		List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Approved", role.getId());

	  if (list.size() > 0) {
	   model.put("list", list);
	   model.put("title", "Bank Employee Active List");

	   return new ModelAndView("endUserListApr", "model", model);
	  } else {

	   return new ModelAndView("noDataFound1");
	  }

	 }

	 @RequestMapping(value = "/customerActiveList", method = RequestMethod.GET)
	 public ModelAndView customerActiveList(ModelMap model) throws CustomException{

		 Role role=endUserDAOImpl.findByRoleName("ROLE_USER");
			//List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Approved", 2);
			List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Approved", role.getId());


	  if (list.size() > 0) {
	   model.put("list", list);
	   model.put("title", "Customer Active List");

	   return new ModelAndView("endUserListApr", "model", model);
	  } else {

	   return new ModelAndView("noDataFound1");
	  }

	 }
	 
	 @RequestMapping(value = "/addCustomerCategory")
	  public ModelAndView addCustomerCategory(ModelMap model, @ModelAttribute CustomerCategoryForm customerCategoryForm) throws CustomException{
	   List<CustomerCategory> allList = customerDAO.getAllCustomerCategory();
	   model.put("allList", allList);
	   model.put("addCustomerCategoryForm", customerCategoryForm);
	   return new ModelAndView("addCustomerCategory", "model", model);

	  }

	  @RequestMapping(value = "/addedCustomer", method = RequestMethod.POST)
	  public ModelAndView addedCustomer(ModelMap model, @ModelAttribute CustomerCategoryForm customerCategoryForm,
	    RedirectAttributes attribute) throws CustomException{
	   EndUser user = getCurrentLoggedUserDetails();
	   String customerCategoryAdded = customerCategoryForm.getCustomerCategory();
	   List<CustomerCategory> allList = customerDAO.getAllCustomerCategory();
	   model.put("allList", allList);
	   Long countCategory = customerDAO.getCountOfCategory(customerCategoryAdded);
	   if (countCategory == 0) {

	    CustomerCategory addCustomerCategory = new CustomerCategory();
	    addCustomerCategory.setCustomerCategory(customerCategoryForm.getCustomerCategory());
	    addCustomerCategory.setCreatedBy(user.getUserName());
	    addCustomerCategory.setCreatedDate(DateService.getCurrentDateTime());
	    addCustomerCategory.setIsActive("Yes");
	    customerDAO.insertCustomerCategory(addCustomerCategory);

	   }

	   else {

	    model.put(Constants.ERROR, Constants.AlreadyExist);
	    return new ModelAndView("addCustomerCategory", "model", model);
	   }
	   attribute = updateTransaction("Saved Successfully", attribute);
	     return new ModelAndView("redirect:updateAMSuccess");
	  }

	  @RequestMapping(value = "/editCustomerCategory", method = RequestMethod.GET)
	  public ModelAndView editCustomerCategory(ModelMap model, @RequestParam("id") Long id, RedirectAttributes attributes,
	    @ModelAttribute CustomerCategoryForm customerCategoryForm) throws CustomException{

	   CustomerCategory customerCategory = customerDAO.getCustomerCategoryById(id);
	   if (customerCategory != null) {
	    customerCategoryForm.setId(id);
	    customerCategoryForm.setCustomerCategory(customerCategory.getCustomerCategory());
	    customerCategoryForm.setCreatedBy(customerCategory.getCreatedBy());
	    customerCategoryForm.setCreatedDate(customerCategory.getCreatedDate());
	    customerCategoryForm.setIsActive(customerCategory.getIsActive());
	   }

	   return new ModelAndView("editCustomerCategory", "model", model);

	  }

	  @RequestMapping(value = "/updateCustomerCategory", method = RequestMethod.POST)
	  public String updateCustomerCategory(ModelMap model, @ModelAttribute CustomerCategoryForm customerCategoryForm,
	    RedirectAttributes attributes) throws CustomException{

	   CustomerCategory customerCategory = customerDAO.getCustomerCategoryById(customerCategoryForm.getId());
	   customerCategory.setCustomerCategory(customerCategoryForm.getCustomerCategory());
	   customerCategory.setIsActive(customerCategoryForm.getIsActive());

	   customerDAO.updateCustomerCategory(customerCategory);
	   attributes = updateTransaction("Updated successfully", attributes);
	    return "redirect:updateAMSuccess";
	  }

	  
	  @RequestMapping(value = "/viewDeposit", method = RequestMethod.GET)
		public ModelAndView editFDBank(@RequestParam Long id, ModelMap model, HttpServletRequest request,
				RedirectAttributes attributes)
				throws CustomException {
		

			ModelAndView mav;
			HolderForm holderForm = new HolderForm();
			
//				mav = new ModelAndView("editReverseEMIBank", "model", model);
			

			List<HolderForm> depositList = depositService.getDepositByDepositId(id);
			holderForm = depositList.get(0);
			model.put("depositHolderList", depositList);
			List<AccountDetails> accountList = accountDetailsDAO
					.findCurrentSavingByCustId(holderForm.getDepositHolder().getCustomerId());
			model.put("accountList", accountList);
			mav = new ModelAndView("viewDeposit", "model", model);

			model.put("depositHolderForm", holderForm);
			model.put("depositClassification", holderForm.getDeposit().getDepositClassification());
			
			
			return mav;

		}
}
