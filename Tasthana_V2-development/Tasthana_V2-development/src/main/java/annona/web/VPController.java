package annona.web;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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

import annona.dao.AccountDetailsDAO;
import annona.dao.BranchDAO;
import annona.dao.CitizenAndCustomerCategoryDAO;
import annona.dao.CurrencyConfigurationDAO;
import annona.dao.CustomerCategoryDAO;
import annona.dao.CustomerDAO;
import annona.dao.DTAACountryRatesDAO;
import annona.dao.DepositRateDAO;
import annona.dao.DepositRatesDAO;
import annona.dao.EndUserDAO;
import annona.dao.EventOperationsDAO;
import annona.dao.FDRatesDAO;
import annona.dao.FixedDepositDAO;
import annona.dao.FundTransferDAO;
import annona.dao.GSTDeductionDAO;
import annona.dao.HolidayConfigurationDAO;
import annona.dao.LedgerDAO;
import annona.dao.LedgerEventMappingDAO;
import annona.dao.LogDetailsDAO;
import annona.dao.ModeOfPaymentDAO;
import annona.dao.NRIServiceBranchesDAO;
import annona.dao.ProductConfigurationDAO;
import annona.dao.RatesDAO;
import annona.dao.RoundOffDAO;
import annona.dao.SweepConfigurationDAO;
import annona.dao.TransactionDAO;
import annona.dao.WithdrawPenaltyDAO;
import annona.domain.AccountDetails;
import annona.domain.Branch;
import annona.domain.CitizenAndCustomerCategoryMapping;
import annona.domain.CurrencyConfiguration;
import annona.domain.Customer;
import annona.domain.CustomerCategory;
import annona.domain.DTAACountry;
import annona.domain.DTAACountryRates;
import annona.domain.Deposit;
import annona.domain.DepositRates;
import annona.domain.EndUser;
import annona.domain.EventOperations;
import annona.domain.GLConfiguration;
import annona.domain.GSTDeduction;
import annona.domain.HolidayConfiguration;
import annona.domain.LedgerEventMapping;
import annona.domain.LoginDate;
import annona.domain.ModeOfPayment;
import annona.domain.NRIServiceBranches;
import annona.domain.ProductConfiguration;
import annona.domain.RatePeriods;
import annona.domain.Rates;
import annona.domain.Role;
import annona.domain.RoundOff;
import annona.domain.SweepConfiguration;
import annona.domain.SweepInFacilityForCustomer;
import annona.domain.Transaction;
import annona.domain.WithdrawPenaltyAmountBased;
import annona.domain.WithdrawPenaltyMaster;
import annona.domain.WithdrawPenaltyTenureBased;
import annona.exception.CustomException;
import annona.form.AddCountryForDTAAForm;
import annona.form.AddCountryWiseTaxRateDTAAForm;
import annona.form.AddRateForm;
import annona.form.CustomerCategoryForm;
import annona.form.CustomerForm;
import annona.form.DepositForm;
import annona.form.EndUserForm;
import annona.form.FixedDepositForm;
import annona.form.GLConfigurationForm;
import annona.form.HolderForm;
import annona.form.LoginForm;
import annona.form.RatesForm;
import annona.form.ReportForm;
import annona.form.WithdrawPenaltyForm;
import annona.form.WithdrawPenaltyFormList;
import annona.scheduler.NotificationsScheduler;
import annona.services.CalculationService;
import annona.services.DateService;
import annona.services.DepositService;
import annona.services.FDService;
import annona.services.ImageService;
import annona.services.LedgerService;
import annona.utility.Constants;
import annona.utility.NRIAccountTypes;

@Controller
@RequestMapping("/vp")
public class VPController {

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
	DepositRatesDAO depositRatesDAO;
 
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
	DepositRateDAO depositRateDAO;

	@Autowired
	LogDetailsDAO logDetailsDAO;

	@Autowired
	RatesForm ratesForm;

	@Autowired
	DepositService depositService;

	@Autowired
	NotificationsScheduler notificationScheduler;

	@Autowired
	WithdrawPenaltyDAO withdrawPenaltyDAO;

	@Autowired
	DTAACountryRatesDAO dtaaCountryRatesDAO;

	@Autowired
	LedgerService ledgerService;

	@Autowired
	LedgerDAO ledgerDAO;

	@Autowired
	HolidayConfigurationDAO holidayConfigurationDAO;

	@Autowired
	private CitizenAndCustomerCategoryDAO citizenAndCustomerCategoryDAO;

	@Autowired
	private CustomerCategoryDAO customerCategoryDAO;

	@Autowired
	private ProductConfigurationDAO productConfigurationDAO;

	@Autowired
	private GSTDeductionDAO gstDeductionDAO;

	@Autowired
	private SweepConfigurationDAO sweepConfigurationDAO;

	@Autowired
	CurrencyConfigurationDAO currencyConfigurationDAO;

	@Autowired
	BranchDAO branchDAO;

	@Autowired
	RoundOffDAO roundOffDAO;

	@Autowired
	ModeOfPaymentDAO modeOfPaymentDAO;

	@Autowired
	LedgerEventMappingDAO ledgerEventMappingDAO;

	@Autowired
	EventOperationsDAO eventOperationsDAO;

	@Autowired
	NRIServiceBranchesDAO nRIServiceBranchesDAO;
	@Autowired
	CalculationService calculationService;

	CookieThemeResolver themeResolver = new CookieThemeResolver();

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	protected Logger log = Logger.getLogger(VPController.class.getName());

	private String getCurrentLoggedUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();

	}

	private EndUser getCurrentLoggedUserDetails() {

		EndUser endUser = endUserDAOImpl.findByUsername(getCurrentLoggedUserName()).getSingleResult();

		return endUser;

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
	public RedirectAttributes updateTransaction(String status, RedirectAttributes attribute) {

		Date curDate = DateService.loginDate;
		attribute.addFlashAttribute(Constants.TRANSACTIONID, fdService.generateRandomString());
		attribute.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
		attribute.addFlashAttribute(Constants.TRANSACTIONSTATUS, status);

		return attribute;
	}

//
//	@RequestMapping(value = "/editAMProfile", method = RequestMethod.GET)
//	public ModelAndView editAdminProfile(ModelMap model, @RequestParam("id") Long id, RedirectAttributes attributes) throws CustomException{
//
//		EndUser userProfile = endUserDAOImpl.findId(id);
//
//		if (userProfile.getImageName() != null) {
//			String type = ImageService.getImageType(userProfile.getImageName());
//
//			String url = "data:image/" + type + ";base64," + Base64.encodeBase64String(userProfile.getImage());
//			userProfile.setImageName(url);
//
//			endUserForm.setImageName(url);
//		} else {
//			endUserForm.setImageName("");
//		}
//
//		endUserForm.setId(userProfile.getId());
//		endUserForm.setDisplayName(userProfile.getDisplayName());
//		endUserForm.setUserName(userProfile.getUserName());
//		endUserForm.setAltContactNo(userProfile.getAltContactNo());
//		endUserForm.setAltEmail(userProfile.getAltEmail());
//		endUserForm.setContactNo(userProfile.getContactNo());
//		endUserForm.setEmail(userProfile.getEmail());
//
//		endUserForm.setPassword(userProfile.getPassword());
//		endUserForm.setTransactionId(userProfile.getTransactionId());
//
//		model.put("endUserForm", endUserForm);
//
//		return new ModelAndView("editAMProfile", "model", model);
//
//	}

//	@RequestMapping(value = "/updateImageForProfile", method = RequestMethod.POST)
//	public String updateImageForProfile(ModelMap model, @ModelAttribute EndUserForm endUserForm) throws CustomException{
//		try {
//			EndUser userProfile = endUserDAOImpl.findId(endUserForm.getId());
//			userProfile.setImage(endUserForm.getFile().getBytes());
//			userProfile.setImageName(endUserForm.getFile().getOriginalFilename());
//			endUserDAOImpl.update(userProfile);
//
//		} catch (Exception e) {
//			e.getMessage();
//		}
//		return "redirect:editVMProfile?id=" + endUserForm.getId();
//	}

//	@RequestMapping(value = "/confirmEditAMProfile", method = RequestMethod.POST)
//	public ModelAndView confirmEditAdminProfile(ModelMap model, @ModelAttribute EndUserForm endUserForm) throws CustomException{
//
//		model.put("endUserForm", endUserForm);
//
//		return new ModelAndView("confirmEditAMProfile", "model", model);
//
//	}

//	@RequestMapping(value = "/updateAMDetails", method = RequestMethod.POST)
//	public ModelAndView updateAdminDetails(@ModelAttribute EndUserForm endUserForm,
//			BindingResult result, RedirectAttributes attributes) throws CustomException{
//
//		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());
//
//		endUser.setId(endUserForm.getId());
//
//		endUser.setDisplayName(endUserForm.getDisplayName());
//		endUser.setContactNo(endUserForm.getContactNo());
//		endUser.setAltContactNo(endUserForm.getAltContactNo());
//		endUser.setEmail(endUserForm.getEmail());
//		endUser.setAltEmail(endUserForm.getAltEmail());
//		endUser.setUserName(endUserForm.getUserName());
//		endUser.setTransactionId(endUserForm.getTransactionId());
//
//		endUserDAOImpl.update(endUser);
//
//		attributes=updateTransaction("Updated Successfully", attributes);
//		return new ModelAndView("redirect:updateAMSuccess");
//
//	}

	@RequestMapping(value = "/editVPPWD", method = RequestMethod.GET)
	public ModelAndView editAdminPWD(ModelMap model, @RequestParam("id") Long id, RedirectAttributes attributes)
			throws CustomException {

		EndUser userProfile = endUserDAOImpl.findId(id);

		endUserForm.setId(userProfile.getId());

		endUserForm.setTransactionId(userProfile.getTransactionId());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("editVPPWD", "model", model);

	}

	@RequestMapping(value = "/updateEditAMPWD", method = RequestMethod.POST)
	public ModelAndView updateEditAdminPWD(ModelMap model, @ModelAttribute EndUserForm endUserForm,
			BindingResult result, RedirectAttributes attributes) throws CustomException {

		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());

		endUser.setId(endUserForm.getId());

		endUser.setPassword(endUserForm.getNewPassword());
		endUser.setTransactionId(endUserForm.getTransactionId());

		endUserDAOImpl.update(endUser);
		attributes = updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:updateAMSuccess");

	}

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

		return "redirect:apprMng";
	}

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

		return "redirect:apprMng";
	}

	@RequestMapping(value = "/bankEmpApprov", method = RequestMethod.GET)
	public ModelAndView bankempApp(ModelMap model, RedirectAttributes attributes) throws CustomException {

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
	public ModelAndView approvBankEmp(@RequestParam Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {

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
			RedirectAttributes attributes) throws CustomException {

		model.put("endUserForm", endUserForm);

		return new ModelAndView("approveBankEmpConfrim", "model", model);
	}

	@RequestMapping(value = "/updateEmpStatus", method = RequestMethod.POST)
	public ModelAndView updateEmpStatus(@ModelAttribute EndUserForm endUserForm, ModelMap model,
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

		attributes = updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:updateAMSuccess");
	}

	@RequestMapping(value = "/customerApprovalList", method = RequestMethod.GET)
	public ModelAndView customerHeadApproval(ModelMap model, RedirectAttributes attributes) throws CustomException {

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
	public ModelAndView approveCustomerHead(@RequestParam Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {

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
			RedirectAttributes attributes) throws CustomException {

		model.put("customerForm", customerForm);

		return new ModelAndView("approveCustomerConfirm", "model", model);
	}

	@RequestMapping(value = "/customerSave", method = RequestMethod.POST)
	public String customerSave(@ModelAttribute CustomerForm customerForm, ModelMap model, RedirectAttributes attributes)
			throws CustomException {

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
		attributes = updateTransaction("Saved Successfully", attributes);
		return "redirect:updateAMSuccess";

	}

	@RequestMapping(value = "/approveFDConfirm", method = RequestMethod.POST)
	public ModelAndView approveFDConfirm(@ModelAttribute FixedDepositForm fixedDepositForm, ModelMap model)
			throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("approveFDConfirm", "model", model);
	}

	@RequestMapping(value = "/updateFDSuccess", method = RequestMethod.GET)
	public ModelAndView updateFDSuccess(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("updateFDSuccess", "model", model);

	}

	@RequestMapping(value = "/fdPendingList", method = RequestMethod.GET)
	public ModelAndView fdPendingList(ModelMap model, RedirectAttributes attributes) throws CustomException {

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
			@ModelAttribute FixedDepositForm fixedDepositForm) throws CustomException {

		Deposit deposit = fixedDepositDao.findById(id);
		fixedDepositForm.setDepositId(deposit.getId());

		fixedDepositForm.setDepositedAmt((double) deposit.getDepositAmount());
		fixedDepositForm.setMaturityDate(deposit.getMaturityDate());

		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("approveFdPendingList", "model", model);
	}

	@RequestMapping(value = "/approveDepositConfrim", method = RequestMethod.POST)
	public ModelAndView approveBankEmpConfrim(@ModelAttribute FixedDepositForm fixedDepositForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("approveDepositConfrim", "model", model);
	}

	@RequestMapping(value = "/updateAMSuccess", method = RequestMethod.GET)
	public ModelAndView updateAMSuccess() throws CustomException {
		return new ModelAndView("updateAMSuccessVP");

	}

	@RequestMapping(value = "/updateApprovalStatus", method = RequestMethod.POST)
	public ModelAndView updateApprovalStatus(@ModelAttribute FixedDepositForm fixedDepositForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {

		Deposit deposit = fixedDepositDao.findById(fixedDepositForm.getId());

		deposit.setApprovalStatus(fixedDepositForm.getStatus());
		deposit.setComment(fixedDepositForm.getComment());

		fixedDepositDao.updateApprovalStatus(deposit);

		attributes = updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:updateAMSuccess");

	}

	@RequestMapping(value = "/customerSuspend", method = RequestMethod.GET)
	public ModelAndView customerSuspend(ModelMap model) throws CustomException {

		// Collection<Customer> customers = customerDAO.findAllCustomers();
		// fixedDepositForm.setCustomers(customers);
		EndUserForm endUserForm1 = new EndUserForm();
		model.put("endUserForm", endUserForm1);
		return new ModelAndView("customerSuspend", "model", model);

	}

	@RequestMapping(value = "/searchCustomer", method = RequestMethod.POST)
	public ModelAndView searchCustomer(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {
		String userId = fixedDepositForm.getCustomerID();
		String userName = fixedDepositForm.getCustomerName();
		String contactNo = fixedDepositForm.getContactNum();
		String email = fixedDepositForm.getEmail();

		List<CustomerForm> endUserList = new ArrayList<CustomerForm>();
		endUserList = customerDAO.searchCustomer(userId, userName, contactNo, email);
		if (endUserList.size() != 0) {
			model.put("endUserList", endUserList);
			return new ModelAndView("customerSuspend", "model", model);

		} else {
			model.put(Constants.ERROR, Constants.customerNotFound);
			return new ModelAndView("customerSuspend", "model", model);
		}
	}

	@RequestMapping(value = "/suspendCustomer", method = RequestMethod.GET)
	public ModelAndView suspendCustomer(@RequestParam Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {

		EndUser userList = endUserDAOImpl.findId(id);

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
			RedirectAttributes attributes) throws CustomException {

		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendCustomerConfrim", "model", model);
	}

	@RequestMapping(value = "/updateCustomerStatus", method = RequestMethod.POST)
	public ModelAndView updateCustomerStatus(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {

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

		attributes = updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:updateAMSuccess");
	}

	@RequestMapping(value = "/bankEmpSuspend", method = RequestMethod.GET)
	public ModelAndView bankEmpSuspend(ModelMap model) throws CustomException {

		EndUserForm endUserForm1 = new EndUserForm();
		model.put("endUserForm", endUserForm1);
		return new ModelAndView("bankEmpSuspend", "model", model);

	}

	@RequestMapping(value = "/searchBankEmp", method = RequestMethod.POST)
	public ModelAndView searchBankEmp(ModelMap model, @ModelAttribute EndUserForm endUserForm) throws CustomException {
		String bankId = endUserForm.getBankId();
		String userName = endUserForm.getUserName();
		String contactNo = endUserForm.getContactNo();
		String email = endUserForm.getEmail();

		List<EndUser> endUserList = new ArrayList<EndUser>();
		endUserList = endUserDAOImpl.searchUser(bankId, userName, contactNo, email, 2);
		if (endUserList.size() != 0) {
			model.put("endUserList", endUserList);
			return new ModelAndView("searchBankEmp", "model", model);
		} else {
			model.put(Constants.ERROR, Constants.bankEmpNotFound);
			return new ModelAndView("searchBankEmp", "model", model);

		}
	}

	@RequestMapping(value = "/suspendBankEmp", method = RequestMethod.GET)
	public ModelAndView suspendBankEmp(@RequestParam Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {

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
			RedirectAttributes attributes) throws CustomException {

		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendBankEmpConfrim", "model", model);
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
		transaction.setTransactionId(endUserForm.getTransactionId());
		transaction.setTransactionType(Constants.APPBANK);
		transaction.setTransactionStatus(Constants.APPSTATUS);
		transactionDAO.insertTransaction(transaction);
		endUserDAOImpl.update(userList);

		attributes = updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:updateAMSuccess");
	}

	@RequestMapping(value = "/customerSuspendList", method = RequestMethod.GET)
	public ModelAndView customerSuspendList(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {

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
	public ModelAndView suspendCustomerApprove(Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {
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
			RedirectAttributes attributes) throws CustomException {

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

		attributes = updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:updateAMSuccess");
	}

	@RequestMapping(value = "/bankEmpSuspendList", method = RequestMethod.GET)
	public ModelAndView bankEmpSuspendList(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {

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
	public ModelAndView bankEmpActiveList(ModelMap model) throws CustomException {

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
	public ModelAndView customerActiveList(ModelMap model) throws CustomException {
		Role role=endUserDAOImpl.findByRoleName("ROLE_USER");
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
	public ModelAndView addCustomerCategory(ModelMap model, @ModelAttribute CustomerCategoryForm customerCategoryForm)
			throws CustomException {
		List<CustomerCategory> allList = customerDAO.getAllCustomerCategory();
		model.put("allList", allList);
		model.put("addCustomerCategoryForm", customerCategoryForm);
		return new ModelAndView("addCustomerCategoryVP", "model", model);

	}

	@RequestMapping(value = "/addedCustomer", method = RequestMethod.POST)
	public ModelAndView addedCustomer(ModelMap model, @ModelAttribute CustomerCategoryForm customerCategoryForm,
			RedirectAttributes attribute) throws CustomException {
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
			@ModelAttribute CustomerCategoryForm customerCategoryForm) throws CustomException {

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
			RedirectAttributes attributes) throws CustomException {

		CustomerCategory customerCategory = customerDAO.getCustomerCategoryById(customerCategoryForm.getId());
		customerCategory.setCustomerCategory(customerCategoryForm.getCustomerCategory());
		customerCategory.setIsActive(customerCategoryForm.getIsActive());

		customerDAO.updateCustomerCategory(customerCategory);
		attributes = updateTransaction("Updated successfully", attributes);
		return "redirect:updateAMSuccess";
	}

	@RequestMapping(value = "/viewDeposit", method = RequestMethod.GET)
	public ModelAndView editFDBank(@RequestParam Long id, ModelMap model, HttpServletRequest request,
			RedirectAttributes attributes) throws CustomException {

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

	@RequestMapping(value = "/dashboardForVP")
	public ModelAndView dashboard(ModelMap model) throws CustomException {

		return new ModelAndView("dashboardForVP", "model", model);
	}

	@RequestMapping(value = "/addRatesFromVP")
	public ModelAndView addRatesFromVP(ModelMap model, @ModelAttribute AddRateForm addRateForm, String nriAccType)
			throws CustomException {
		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);

		if (setCategory.size() > 0) {

			model.put("setCategory", setCategory);
			model.put("addRateForm", addRateForm);
			model.put("list", list);

		}
		List<String> depositClassificationList = new ArrayList<>();
		depositClassificationList.add(Constants.fixedDeposit);
		depositClassificationList.add(Constants.recurringDeposit);
		depositClassificationList.add(Constants.taxSavingDeposit);
		depositClassificationList.add(Constants.annuityDeposit);
		model.put("depositClassificationList", depositClassificationList);

		model.put("nriAccType", nriAccType);
		model.put("list", list);
		model.put("setCategory", setCategory);
		model.put("addRateForm", addRateForm);

		return new ModelAndView("addRatesFromVP", "model", model);

	}

	@RequestMapping(value = "/getRateDurationsByDepositClaasification")
	public ModelAndView getRateDurationsByDepositClaasification(ModelMap model, @ModelAttribute AddRateForm addRateForm,
			String depositClassification, String category, String nriAccType, String currency_) throws CustomException {

		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();

		List<String> depositClassificationList = new ArrayList<>();
		depositClassificationList.add(Constants.fixedDeposit);
		depositClassificationList.add(Constants.recurringDeposit);
		depositClassificationList.add(Constants.taxSavingDeposit);
		depositClassificationList.add(Constants.annuityDeposit);
		model.put("depositClassificationList", depositClassificationList);

		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);

		if (setCategory.size() > 0) {

			model.put("setCategory", setCategory);
			model.put("addRateForm", addRateForm);
			model.put("list", list);

		}
		if (depositClassification.contains(","))
			depositClassification = depositClassification.substring(0, depositClassification.indexOf(",", 0));
		// String category = null;

		if (category.contains(","))
			category = category.substring(0, category.indexOf(",", 0));

		List<RatePeriods> ratesPeriod = ratesDAO.getRateDurations(depositClassification, addRateForm.getCitizen(),
				addRateForm.getNriAccountType(), category);
		if (ratesPeriod != null) {
			model.put("ratesPeriod", ratesPeriod);
		}
		if (ratesPeriod == null) {
			model.put(Constants.ERROR, Constants.durationError);
		}
		model.put("ratesPeriod", ratesPeriod);
		model.put("selectedDepositClassification", depositClassification);
		model.put("category", category);
		model.put("nriAccType", nriAccType);
		model.put("currency_", currency_);

		return new ModelAndView("addRatesFromVP", "model", model);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/addRatePost", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody ModelAndView addRatePost(ModelMap model, @ModelAttribute AddRateForm addRateForm,
			RedirectAttributes attribute, @RequestParam("rateArrList") String[] rateArrList,
			@RequestParam("dataString") String[] dataString, String citizen, String nriAccountType)
			throws UnsupportedEncodingException, CustomException {
		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();

		for (int i = 0; i < rateArrList.length; i++) {
			DepositRates depositRates = new DepositRates();
			String arrString = rateArrList[i].replace("rateList[", "");

			Integer pipeIndex = arrString.indexOf('|');
			String[] categorySplit = dataString[0].split("=");

			String interstRate = arrString.substring(pipeIndex + 1, arrString.indexOf('#'));
			Float rate = Float.valueOf(interstRate);
			Long Id = Long.parseLong(arrString.substring(0, arrString.indexOf(']')));
			List<RatePeriods> getAllFromAndToDaysRates = ratesDAO.getAllRatesPeriodFromId(Id);

			Date effectiveDate = new Date();//addRateForm.getEffectiveDate();
			if (effectiveDate != null) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String effectiveDateStr = sdf.format(effectiveDate);
				try {
					effectiveDate = sdf.parse(effectiveDateStr);
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}

			String description = getAllFromAndToDaysRates.get(0).getFromDay() + "  day to  "
					+ getAllFromAndToDaysRates.get(0).getToDay() + "  days";
			DepositRates depositRatesDescription = ratesDAO.getRateDescriptionById(Id, addRateForm.getCurrency(),
					categorySplit[1], effectiveDate, description, addRateForm.getDepositClassification(),
					addRateForm.getNriAccountType());
			// Float descriptionRate = depositRatesDescription.getInterestRate()
			// == null ? 0f : depositRatesDescription.getInterestRate();

			depositRates.setCalMaturityPeriodFromInDays(getAllFromAndToDaysRates.get(0).getFromDay());
			depositRates.setCalMaturityPeriodToInDays(getAllFromAndToDaysRates.get(0).getToDay());
			depositRates.setRatePeriodsId(Id);
			depositRates.setInterestRate(rate);
			depositRates.setDescription(description);

			depositRates.setCategory(categorySplit[1]);
			//depositRates.setEffectiveDate(addRateForm.getEffectiveDate());
			depositRates.setCurrency(addRateForm.getCurrency());
			depositRates.setMaturityPeriodSign("=");
			depositRates.setTransactionId(fdService.generateRandomString());
			depositRates.setAmountSlabFrom(addRateForm.getAmountSlabFrom());
			;
			depositRates.setAmountSlabTo(addRateForm.getAmountSlabTo());
			depositRates.setDepositClassification(addRateForm.getDepositClassification());

			if (addRateForm.getCitizen().equals(Constants.NRI)) {
				depositRates.setNriAccountType(addRateForm.getNriAccountType());
			}
			depositRates.setCitizen(addRateForm.getCitizen());
			if (depositRatesDescription == null) {
				depositRateDAO.insertDepositRates(depositRates);
			}
		}
		attribute = updateTransaction("Saved Successfully", attribute);
		model.put("list", list);
		return new ModelAndView("redirect:vpFDSaved");

	}

	@RequestMapping(value = "/viewRateFromVP")
	public ModelAndView viewRate(ModelMap model, String nriAccType, String citizen, String currency,
			String depositClassification, String customerCategory) throws CustomException {

		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
		String citizenShip = null;
		String currencyType = null;
		String depClassification = null;

		if (citizen != null) {
			citizenShip = citizen.split(",")[0];
		}
		if (currency != null) {
			currencyType = currency.split(",")[0];
		}
		if (depositClassification != null) {
			depClassification = depositClassification.split(",")[0];
		}
		if (setCategory.size() > 0) {

			model.put("setCategory", setCategory);
			model.put("ratesForm", ratesForm);

		}

		List<String> depositClassificationList = new ArrayList<>();
		depositClassificationList.add(Constants.fixedDeposit);
		depositClassificationList.add(Constants.recurringDeposit);
		depositClassificationList.add(Constants.taxSavingDeposit);
		depositClassificationList.add(Constants.annuityDeposit);

		boolean matched = false;
		if (nriAccType != null && nriAccType.equalsIgnoreCase("FCNR")) {
			String curFCNR[] = fdService.getFCNRCurrencies();

			// traversing array
			for (int i = 0; i < curFCNR.length; i++)// length is the property of array
			{
				if (curFCNR[i].equalsIgnoreCase(currencyType))
					matched = true;
			}
			if (!matched)
				currencyType = "USD";
		}

		matched = false;
		if (nriAccType != null && nriAccType.equalsIgnoreCase("RFC")) {
			String curRFC[] = fdService.getRFCCurrencies();

			// traversing array
			for (int i = 0; i < curRFC.length; i++)// length is the property of array
			{

				if (curRFC[i] != null) {
					if (curRFC[i].equalsIgnoreCase(currencyType))
						matched = true;
				}
			}
			if (!matched)
				currencyType = "USD";
		}

		model.put("depositClassificationList", depositClassificationList);
		model.put("setCategory", setCategory);
		model.put("ratesForm", ratesForm);
		model.put("loginDate", DateService.loginDate);
		model.put("nriAccountType", nriAccType);
		model.put("citizenType", citizenShip);
		model.put("currency", currencyType);
		model.put("depClassification", depClassification);
		model.put("customerCategory", customerCategory);

		return new ModelAndView("viewRateFromVP", "model", model);

	}

	@RequestMapping(value = "/getRateListByCategory_Currency")
	public ModelAndView getRateListBySelectedCategory(ModelMap model, @ModelAttribute RatesForm ratesForm,
			String customerCategory, String currency, String depositClassification, String amountSlabFromVal,
			String amountSlabToVal) throws CustomException {

		String nriAccType = ratesForm.getNriAccountType();
		String citizen = ratesForm.getCitizen();
		if (nriAccType.contains(","))
			nriAccType = nriAccType.substring(0, nriAccType.indexOf(",", 0));
		if (citizen.contains(","))
			citizen = citizen.substring(0, citizen.indexOf(",", 0));

		if (currency.contains(","))
			currency = currency.substring(0, currency.indexOf(",", 0));

		if (currency.contains(","))
			currency = currency.substring(0, currency.indexOf(",", 0));

		if (depositClassification.contains(","))
			depositClassification = depositClassification.substring(0, depositClassification.indexOf(",", 0));
		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);

		List<Double> amountFromSlablist = new ArrayList<Double>();
		amountFromSlablist.add(Double.valueOf(amountSlabFromVal));
		amountFromSlablist.add(Double.valueOf(amountSlabToVal));
		// depositRatesDAO.getFromAmountSlabList(customerCategory,
		// currency,depositClassification);
		model.put("amountFromSlablist", amountFromSlablist);

		List<DepositRates> rateList = depositRatesDAO.getRatesByCategoryAndCitizen(customerCategory, currency,
				depositClassification, ratesForm.getAmountSlabFrom(), ratesForm.getAmountSlabTo(), nriAccType)
				.getResultList();

		if (setCategory.size() > 0) {
			if (customerCategory == null)
				customerCategory = list.get(0).getCustomerCategory();
			model.put("setCategory", setCategory);

		}

		List<String> depositClassificationList = new ArrayList<>();
		depositClassificationList.add(Constants.fixedDeposit);
		depositClassificationList.add(Constants.recurringDeposit);
		depositClassificationList.add(Constants.taxSavingDeposit);
		depositClassificationList.add(Constants.annuityDeposit);

		ratesForm.setAmountSlabTo(null);
		model.put("setCategory", setCategory);
		model.put("loginDate", DateService.loginDate);
		model.put("selectedCategory", customerCategory);
		model.put("currency", currency);
		model.put("ratesForm", ratesForm);
		model.put("rateList", rateList);
		model.put("selectedFromSlab", ratesForm.getAmountSlabFrom());
		model.put("depositClassificationList", depositClassificationList);
		model.put("selectedDepositClassification", depositClassification);
		model.put("nriAccType", nriAccType);
		model.put("citizen", citizen);

		model.put("ratesForm", ratesForm);
		model.put("rateList", rateList);
		model.put("selectedFromSlab", amountSlabFromVal);
		model.put("selectedFromTo", amountSlabToVal);
		model.put("depositClassificationList", depositClassificationList);
		model.put("selectedDepositClassification", depositClassification);
		model.put("nriAccType", nriAccType);
		return new ModelAndView("viewRateFromVP", "model", model);
	}

//	@RequestMapping(value = "/bankConfigurationFromVP")
//	public ModelAndView bankConfiguration(ModelMap model, @ModelAttribute BankConfigurationForm bankConfigurationForm,
//			String RIRadio, String NRIRadio) throws CustomException {
//
//		BankConfiguration bankConfiguration = null;
//
//		String currencyType = null;
//
//		if ((RIRadio == null && NRIRadio == null) || (RIRadio != null && RIRadio.equals(Constants.RI))) {
//			bankConfiguration = ratesDAO.findAllDataByCitizen(Constants.RI);
//		}
//
//		if (NRIRadio != null && NRIRadio.equals(Constants.NRI)) {
//			bankConfiguration = ratesDAO.findAllDataByCitizenAndAccountType(NRIRadio,
//					bankConfigurationForm.getNriAccountType());
//		}
//
//		if (bankConfiguration != null) {
//			currencyType = bankConfiguration.getCurrency();// to get the currency
//			bankConfigurationForm.setId(bankConfiguration.getId());
//			bankConfigurationForm.setInterestCalculationBasis(bankConfiguration.getInterestCalculationBasis());
//			bankConfigurationForm.setMaximumTimeOfModification(bankConfiguration.getMaximumTimeOfModification());
//			bankConfigurationForm
//					.setMinBalanceForAutodeposit(bankConfiguration.getMinimunSavingBalanceForAutodeposit());
//			bankConfigurationForm.setModificationBasedOn(bankConfiguration.getModificationBasedOn());
//			bankConfigurationForm.setPanality(bankConfiguration.getPanality());
//			bankConfigurationForm.setPrematurePanality(bankConfiguration.getPrematurePanality());
//			bankConfigurationForm.setCurrency(bankConfiguration.getCurrency());
//			bankConfigurationForm.setPenalityFlatAmount(bankConfiguration.getPenalityFlatAmount());
//			bankConfigurationForm.setMinDepositAmount(bankConfiguration.getMinDepositAmount());
//			bankConfigurationForm.setMinBalForSavingAccount(bankConfiguration.getMinBalForSavingAccount());
//			bankConfigurationForm
//					.setMinimumAmountRequiredForAutoDeposit(bankConfiguration.getMinimumAmountRequiredForAutoDeposit());
//			// bankConfigurationForm.setEmiInterestRate(bankConfiguration.getEmiInterestRate());
//			bankConfigurationForm.setPanaltyForWithdraw(bankConfiguration.getPanaltyForWithdraw());
//			bankConfigurationForm.setMinTopUpAmount(bankConfiguration.getMinTopUpAmount());
//			bankConfigurationForm.setPayAndWithdrawTenure(bankConfiguration.getPayAndWithdrawTenure());
//			bankConfigurationForm.setTdsPercentForNoPAN(bankConfiguration.getTdsPercentForNoPAN());
//			bankConfigurationForm.setTdsCalculationOnBasis(bankConfiguration.getTdsCalculationOnBasis());
//			bankConfigurationForm.setReverseEmiOnBasis(bankConfiguration.getReverseEmiOnBasis());
//			bankConfigurationForm.setCitizen(bankConfiguration.getCitizen());
//			bankConfigurationForm.setNriAccountType(bankConfiguration.getNriAccountType());
//			bankConfigurationForm.setInterestCompoundingBasis(bankConfiguration.getInterestCompoundingBasis());
//			bankConfigurationForm
//					.setPenaltyForMissedPaymentForRecurring(bankConfiguration.getPenaltyForMissedPaymentForRecurring());
//			bankConfigurationForm
//					.setGracePeriodForRecurringPayment(bankConfiguration.getGracePeriodForRecurringPayment());
//
//			bankConfigurationForm
//					.setAdjustmentForDepositConversion(bankConfiguration.getAdjustmentForDepositConversion());
//			bankConfigurationForm.setPenaltyForDepositConversion(bankConfiguration.getPenaltyForDepositConversion());
//			bankConfigurationForm.setMinimumTenure(bankConfiguration.getMinimumTenure());
//			bankConfigurationForm.setLockingPeriodForWithdraw(bankConfiguration.getLockingPeriodForWithdraw());
//			if (bankConfiguration.getCgst() != null)
//				bankConfigurationForm.setCgst(bankConfiguration.getCgst());
//			else
//				bankConfigurationForm.setCgst(9f);
//			if (bankConfiguration.getSgst() != null)
//				bankConfigurationForm.setSgst(bankConfiguration.getSgst());
//			else
//				bankConfigurationForm.setSgst(9f);
//			bankConfigurationForm.setIgst(bankConfiguration.getIgst());
//
//			boolean matched = false;
//			if (bankConfigurationForm.getNriAccountType() != null
//					&& bankConfigurationForm.getNriAccountType().equalsIgnoreCase("FCNR")) {
//				String curFCNR[] = fdService.getFCNRCurrencies();
//
//				// traversing array
//				for (int i = 0; i < curFCNR.length; i++)// length is the property of array
//				{
//					if (bankConfiguration.getCurrency() != null) {
//						if (curFCNR[i].equalsIgnoreCase(bankConfiguration.getCurrency()))
//							matched = true;
//					}
//				}
//				if (!matched)
//					currencyType = "USD";
//			}
//
//			matched = false;
//			if (bankConfigurationForm.getNriAccountType() != null
//					&& bankConfigurationForm.getNriAccountType().equalsIgnoreCase("RFC")) {
//				String curRFC[] = fdService.getRFCCurrencies();
//
//				// traversing array
//				for (int i = 0; i < curRFC.length; i++)// length is the property of array
//				{
//
//					if (curRFC[i] != null && bankConfiguration.getCurrency() != null) {
//						if (curRFC[i].equalsIgnoreCase(bankConfiguration.getCurrency()))
//							matched = true;
//					}
//				}
//				if (!matched)
//					currencyType = "USD";
//			}
//		}
//
//		if (bankConfiguration == null) {
//			// bankConfigurationForm.setInterestCalculationBasis("");
//			bankConfigurationForm.setMinimumTenure(0);
//			bankConfigurationForm.setMaximumTimeOfModification(0);
//			bankConfigurationForm.setMinBalanceForAutodeposit(0);
//			bankConfigurationForm.setMinimumAmountRequiredForAutoDeposit(0);
//			bankConfigurationForm.setModificationBasedOn("");
//			bankConfigurationForm.setPanality(0.0);
//			bankConfigurationForm.setPrematurePanality(0.0);
//			// bankConfigurationForm.setCurrency("");
//			bankConfigurationForm.setPenalityFlatAmount(0);
//			bankConfigurationForm.setMinDepositAmount(0);
//			bankConfigurationForm.setMinBalForSavingAccount(0f);
//			// bankConfigurationForm.setEmiInterestRate(bankConfiguration.getEmiInterestRate());
//			bankConfigurationForm.setPanaltyForWithdraw(0.0);
//			bankConfigurationForm.setMinTopUpAmount(0.0);
//			bankConfigurationForm.setPayAndWithdrawTenure(0);
//			bankConfigurationForm.setTdsPercentForNoPAN(0f);
//			bankConfigurationForm.setTdsCalculationOnBasis("");
//			bankConfigurationForm.setReverseEmiOnBasis("");
//
//			bankConfigurationForm.setInterestCompoundingBasis("");
//
//			bankConfigurationForm.setCgst(0f);
//			bankConfigurationForm.setSgst(0f);
//			bankConfigurationForm.setIgst(0f);
//			bankConfigurationForm.setPenaltyForDepositConversion(0f);
//
//			boolean matched = false;
//			if (bankConfigurationForm.getNriAccountType() != null
//					&& bankConfigurationForm.getNriAccountType().equalsIgnoreCase("FCNR")) {
//				String curFCNR[] = fdService.getFCNRCurrencies();
//
//				// traversing array
//				for (int i = 0; i < curFCNR.length; i++)// length is the property of array
//				{
//					if (bankConfigurationForm.getCurrency() != null) {
//						if (curFCNR[i].equalsIgnoreCase(bankConfigurationForm.getCurrency()))
//							matched = true;
//					}
//				}
//				if (!matched)
//					currencyType = "USD";
//			}
//
//			matched = false;
//			if (bankConfigurationForm.getNriAccountType() != null
//					&& bankConfigurationForm.getNriAccountType().equalsIgnoreCase("RFC")) {
//				String curRFC[] = fdService.getRFCCurrencies();
//
//				// traversing array
//				for (int i = 0; i < curRFC.length; i++)// length is the property of array
//				{
//
//					if (curRFC[i] != null && bankConfigurationForm.getCurrency() != null) {
//						if (curRFC[i].equalsIgnoreCase(bankConfigurationForm.getCurrency()))
//							matched = true;
//					}
//				}
//				if (!matched)
//					currencyType = "USD";
//			}
//		}
//
//		model.put("currency", currencyType);
//		model.put("bankConfigurationForm", bankConfigurationForm);
//		return new ModelAndView("bankConfigurationFromVP", "model", model);
//	}
//
//	@RequestMapping(value = "/saveBankConfiguration", method = RequestMethod.POST)
//	public String saveBankConfiguration(ModelMap model, @ModelAttribute BankConfigurationForm bankConfigurationForm,
//			RedirectAttributes attributes) throws CustomException {
//
//		BankConfiguration bankConfiguration1 = null;
//		if (bankConfigurationForm.getCitizen() != null && bankConfigurationForm.getCitizen().equals(Constants.RI)) {
//			bankConfiguration1 = ratesDAO.findAllDataByCitizen(bankConfigurationForm.getCitizen());
//		} else {
//			bankConfiguration1 = ratesDAO.findAllDataByCitizenAndAccountType(bankConfigurationForm.getCitizen(),
//					bankConfigurationForm.getNriAccountType());
//		}
//		int save = 0; // insert
//		if (bankConfiguration1 == null) {
//			bankConfiguration1 = new BankConfiguration();
//			save = 1;
//		}
//		// minBalanceForAutodeposit
//		bankConfiguration1.setInterestCalculationBasis(bankConfigurationForm.getInterestCalculationBasis());
//		bankConfiguration1.setMaximumTimeOfModification(bankConfigurationForm.getMaximumTimeOfModification());
//
//		bankConfiguration1.setMinimumSavingBalanceForAutodeposit(bankConfigurationForm.getMinBalanceForAutodeposit());
//		bankConfiguration1
//				.setMinimumAmountRequiredForAutoDeposit(bankConfigurationForm.getMinimumAmountRequiredForAutoDeposit());
//		// bankConfiguration1.setMinBalForSavingAccount(minBalForSavingAccount);
//		bankConfiguration1.setModificationBasedOn(bankConfigurationForm.getModificationBasedOn());
//		bankConfiguration1.setPanality(bankConfigurationForm.getPanality());
//		bankConfiguration1.setPrematurePanality(bankConfigurationForm.getPrematurePanality());
//		bankConfiguration1.setCurrency(bankConfigurationForm.getCurrency());
//		bankConfiguration1.setPenalityFlatAmount(bankConfigurationForm.getPenalityFlatAmount());
//		bankConfiguration1.setMinDepositAmount(bankConfigurationForm.getMinDepositAmount());
//		bankConfiguration1.setMinBalForSavingAccount(bankConfigurationForm.getMinBalForSavingAccount());
//		// bankConfigurationForm.setEmiInterestRate(bankConfiguration.getEmiInterestRate());
//		bankConfiguration1.setPanaltyForWithdraw(bankConfigurationForm.getPanaltyForWithdraw());
//		bankConfiguration1.setMinTopUpAmount(bankConfigurationForm.getMinTopUpAmount());
//		bankConfiguration1.setPayAndWithdrawTenure(bankConfigurationForm.getPayAndWithdrawTenure());
//
//		if (bankConfigurationForm.getNriAccountType() != null
//				&& (bankConfigurationForm.getNriAccountType().equals("FCNR")
//						|| bankConfigurationForm.getNriAccountType().equals("NRE")
//						|| bankConfigurationForm.getNriAccountType().equals("PRP")))
//			bankConfiguration1.setTdsPercentForNoPAN(0f);
//		else
//			bankConfiguration1.setTdsPercentForNoPAN(bankConfigurationForm.getTdsPercentForNoPAN());
//
//		bankConfiguration1.setTdsCalculationOnBasis(bankConfigurationForm.getTdsCalculationOnBasis());
//		bankConfiguration1.setReverseEmiOnBasis(bankConfigurationForm.getReverseEmiOnBasis());
//		bankConfiguration1.setCitizen(bankConfigurationForm.getCitizen());
//		bankConfiguration1.setPenaltyForDepositConversion(bankConfigurationForm.getPenaltyForDepositConversion());
//		bankConfiguration1.setMinimumTenure(bankConfigurationForm.getMinimumTenure());
//		int adjustmentForDepositConversion = bankConfigurationForm.getAdjustmentForDepositConversion() == 1 ? 1 : 0;
//		bankConfiguration1.setAdjustmentForDepositConversion(adjustmentForDepositConversion);
//		if (bankConfigurationForm.getCitizen() != null && bankConfigurationForm.getCitizen().equals(Constants.NRI)) {
//			bankConfiguration1.setNriAccountType(bankConfigurationForm.getNriAccountType());
//		}
//		bankConfiguration1.setInterestCompoundingBasis(bankConfigurationForm.getInterestCompoundingBasis());
//		bankConfiguration1
//				.setPenaltyForMissedPaymentForRecurring(bankConfigurationForm.getPenaltyForMissedPaymentForRecurring());
//		bankConfiguration1.setGracePeriodForRecurringPayment(bankConfigurationForm.getGracePeriodForRecurringPayment());
//		bankConfiguration1.setLockingPeriodForWithdraw(bankConfigurationForm.getLockingPeriodForWithdraw());
//		if (save == 1) {
//			ratesDAO.createBankConfiguration(bankConfiguration1);
//			attributes = updateTransaction("Saved Successfully", attributes);
//		} else {
//			ratesDAO.updateBankConfiguration(bankConfiguration1);
//			attributes = updateTransaction("Updated Successfully", attributes);
//
//		}
//		return "redirect:vpFDSaved";
//	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/customerConfigurationFromVP")
	public ModelAndView customerConfigurationFromVP(ModelMap model, String citizen, String nriAccountType,
			RatesForm ratesForm) throws CustomException {

		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
		Collection<Rates> rates = ratesDAO.findAllRates();

		String citizenType = null;
		String customerCategory = null;
		String custCategory = ratesForm.getType();
		Rates ratesCategory = null;

		if (setCategory.size() > 0 && citizen != null) {
			customerCategory = custCategory.split("\r")[0];
			citizenType = citizen.split(",")[0];
			if (citizenType != null && citizenType.equals(Constants.RI))

			{
				ratesCategory = ratesDAO.getAllRatesByCustomerCategoryAndCitizen(customerCategory, citizenType);
			}

			if (setCategory.size() > 0 && citizen != null && nriAccountType != null) {
				ratesCategory = ratesDAO.getAllRatesByCustomerCategoryCitizenAndAccountType(customerCategory,
						citizenType, nriAccountType);
			}

		}

		if (ratesCategory == null) {
			model.put("setCategory", setCategory);

		} else {
			model.put("rates", rates);
			model.put("setCategory", setCategory);
			model.put(Constants.ERROR, "Data already present you can search and edit from below list.");
			return new ModelAndView("customerConfigurationFromVP", "model", model);
		}

		model.put("rates", rates);
		model.put("citizenType", citizenType);
		model.put("ratesForm", ratesForm);
		model.put("setCategory", setCategory);
		model.put("ratesCategory", ratesCategory);
		model.put("selectedCategory", customerCategory);
		if (citizen != null && nriAccountType != null) {
			return new ModelAndView("confirmcustomerConfiguration", "model", model);
		}
		return new ModelAndView("customerConfigurationFromVP", "model", model);
	}

	@RequestMapping(value = "/confirmcustomerConfiguration", method = RequestMethod.POST)
	public ModelAndView confirmcustomerConfiguration(ModelMap model, @ModelAttribute RatesForm ratesForm,
			RedirectAttributes attributes) throws CustomException {

		model.put("ratesForm", ratesForm);
		return new ModelAndView("confirmcustomerConfiguration", "model", model);
	}

	@RequestMapping(value = "/savecustomerConfiguration", method = RequestMethod.POST)
	public String savecustomerConfiguration(ModelMap model, @ModelAttribute RatesForm ratesForm,
			RedirectAttributes attribute) throws CustomException {

		Rates rates = new Rates();

		rates.setType(ratesForm.getType());
		rates.setTds(ratesForm.getTds());
		rates.setAssignDate(DateService.getCurrentDateTime());
		rates.setFdFixedPercent(ratesForm.getFdFixedPercent());
		rates.setMinIntAmtForTDSDeduction(ratesForm.getMinIntAmtForTDSDeduction());
		rates.setCitizen(ratesForm.getCitizen());
		rates.setNriAccountType(ratesForm.getNriAccountType());
		ratesDAO.createRate(rates);
		attribute = updateTransaction("Saved Successfully", attribute);
		return "redirect:vpFDSaved";
	}

	@RequestMapping(value = "/editCustomerConfigurationFromVP")
	public ModelAndView editCustomerConfiguration(ModelMap model, @ModelAttribute RatesForm ratesForm,
			String nriAccountType, String citizen, String type) throws CustomException {

		Rates ratesCategory = null;
		if (citizen != null && citizen.equals(Constants.RI)) {
			ratesCategory = ratesDAO.getAllRatesByCustomerCategoryAndCitizen(type, citizen);
		}
		if (type != null && citizen != null && nriAccountType != null && nriAccountType != "") {
			ratesCategory = ratesDAO.getAllRatesByCustomerCategoryCitizenAndAccountType(type, citizen, nriAccountType);
		}

		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);

		ratesForm.setTds(ratesCategory.getTds());
		ratesForm.setFdFixedPercent(ratesCategory.getFdFixedPercent());
		ratesForm.setMinIntAmtForTDSDeduction(ratesCategory.getMinIntAmtForTDSDeduction());
		model.put("ratesCategory", ratesCategory);
		model.put("setCategory", setCategory);
		model.put("ratesForm", ratesForm);
		return new ModelAndView("editCustomerConfigurationFromVP", "model", model);
	}

	@RequestMapping(value = "/editCustomerConfigurationPost")
	public String editCustomerConfigurationPost(ModelMap model, @ModelAttribute RatesForm ratesForm,
			RedirectAttributes attribute) throws CustomException {

		Rates rates = null;
		if (ratesForm.getCitizen() != null && ratesForm.getCitizen().equals(Constants.RI)) {
			rates = ratesDAO.getAllRatesByCustomerCategoryAndCitizen(ratesForm.getType(), ratesForm.getCitizen());
		} else {
			rates = ratesDAO.getAllRatesByCustomerCategoryCitizenAndAccountType(ratesForm.getType(),
					ratesForm.getCitizen(), ratesForm.getNriAccountType());
		}

		rates.setTds(ratesForm.getTds());
		rates.setFdFixedPercent(ratesForm.getFdFixedPercent());
		rates.setMinIntAmtForTDSDeduction(ratesForm.getMinIntAmtForTDSDeduction());
		ratesDAO.update(rates);

		attribute = updateTransaction("Updated Successfully", attribute);

		return "redirect:vpFDSaved";
	}

	@RequestMapping(value = "/vpPage", method = RequestMethod.GET)
	public ModelAndView fdCustomerLists(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm, HttpServletRequest request, HttpServletResponse response)
			throws CustomException {

		ModelAndView mav = new ModelAndView();
		EndUser endUser = getCurrentLoggedUserDetails();

		model.addAttribute("endUser", endUser);

		mav = new ModelAndView("dashboardForVP", "model", model);

		return mav;

//			List<Deposit> depositLists = fixedDepositDao.getPendingDeposits();
//			if (depositLists.size() > 0) {
//				model.put("depositLists", depositLists);
//				mav = new ModelAndView("dashboardForVP", "model", model);
//			} else {
//				mav = new ModelAndView("noDataFound1", "model", model);
//			}

	}

	@RequestMapping(value = "/addDurationSlabFromVP")
	public ModelAndView duration(ModelMap model, @ModelAttribute AddRateForm addRateForm) throws CustomException {
		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
		if (setCategory.size() > 0) {

			model.put("setCategory", setCategory);
			model.put("addRateForm", addRateForm);
			model.put("list", list);

		}
//		BankConfiguration bankConfiguration = ratesDAO.findAll();
//		if (bankConfiguration != null) {
//			model.put("bankConfiguration", bankConfiguration);
//		}
		model.put("list", list);
		model.put("setCategory", setCategory);
		model.put("addRateForm", addRateForm);

		return new ModelAndView("addDurationSlabFromVP", "model", model);

	}

	@RequestMapping(value = "/durationPost", method = RequestMethod.POST, produces = "text/html")
	public ModelAndView durationPost(ModelMap model, @ModelAttribute AddRateForm addRateForm,
			RedirectAttributes attribute, @RequestBody String myArray)
			throws UnsupportedEncodingException, CustomException {

		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		String result = URLDecoder.decode(myArray, "UTF-8");
		String[] splitJson = result.split("&");

		String jsonData = splitJson[0];
		String[] splitJsonData = jsonData.split("=");
		String splitedJsonData = splitJsonData[1];
		String[] splitSplitedJsonData = splitedJsonData.split("&");
		String jsonFinalData = splitSplitedJsonData[0];

		String[] category = addRateForm.getCategory().split("\\\\");

		int startDay;
		int endDay;
		JSONArray array = new JSONArray(jsonFinalData);
		for (int i = 0; i < array.length(); i++) {
			RatePeriods ratePeriods = new RatePeriods();
			JSONObject row = array.getJSONObject(i);
			startDay = row.getInt("startDay");
			endDay = row.getInt("endDay");
			ratePeriods.setDescription(startDay + "  day to  " + endDay + "  days");
			ratePeriods.setFromDay(startDay);
			ratePeriods.setToDay(endDay);
			ratePeriods.setDepositClassification(addRateForm.getDepositClassification());
			ratePeriods.setCitizen(addRateForm.getCitizen());
			ratePeriods.setNriAccountType(addRateForm.getNriAccountType());
			ratePeriods.setCategory(category[0]);
			RatePeriods ratePeriodForDuplicateChecking = depositRateDAO.getRatePeriods(startDay, endDay, category[0],
					addRateForm.getCitizen(), addRateForm.getNriAccountType(), addRateForm.getDepositClassification());

			if (ratePeriodForDuplicateChecking == null) {
				depositRateDAO.createDuration(ratePeriods);
			}
		}

		attribute = updateTransaction("Saved Successfully", attribute);
		model.put("list", list);
		model.put("category", category);
		return new ModelAndView("redirect:vpFDSaved");

	}

	@RequestMapping(value = "/viewDurationFromVP")
	public ModelAndView viewDurationFromVP(ModelMap model, @ModelAttribute AddRateForm addRateForm)
			throws CustomException {
		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
		if (setCategory.size() > 0) {

			model.put("setCategory", setCategory);
			model.put("addRateForm", addRateForm);
			model.put("list", list);

		}
		/*
		 * BankConfiguration bankConfiguration = ratesDAO.findAll(); if
		 * (bankConfiguration != null) { model.put("bankConfiguration",
		 * bankConfiguration); }
		 */
		model.put("list", list);
		model.put("setCategory", setCategory);
		model.put("addRateForm", addRateForm);

		return new ModelAndView("viewDurationFromVP", "model", model);

	}

	@RequestMapping(value = "/showMonthEndProcessFromVP", method = RequestMethod.GET)
	public ModelAndView showMonthEndProcess(ModelMap model, @ModelAttribute ReportForm reportForm)
			throws CustomException {
		model.put("reportForm", reportForm);

		return new ModelAndView("showMonthEndProcessFromVP", "model", model);
	}

	@RequestMapping(value = "/monthEndProcessFromVP", method = RequestMethod.POST)
	public ModelAndView monthEndProcess(ModelMap model, @ModelAttribute ReportForm reportForm) throws CustomException {
		// Date tillDate = reportForm.getToDate();
		System.out.println("Startng the monthEndProcess...");

		// Sequence:
		// -----------------
		// Calculate Interest
		// PayOff
		// Maturity
		// Compounding
		// -----------------
		notificationScheduler.calculateInterest(); // for interest and tds
													// deduction
		// notificationScheduler.deductTDS();

		notificationScheduler.calculatePayOff();

		
		notificationScheduler.calculateModificationPenalty();

		notificationScheduler.calculatePenaltyForMissedPaymentForRecurringDeposit();

		notificationScheduler.compoundInterest();

		notificationScheduler.transferMoneyOnMaturity();

		notificationScheduler.autoDeduction();

		notificationScheduler.deductAnnuityEMI();


		notificationScheduler.paymentRemindMail();

		// notificationScheduler.deductTDS();

		// notificationScheduler.createAutoDeposit();

		model.put("succMsg", "Successfully processed.");

		return new ModelAndView("showMonthEndProcessFromVP", "model", model);
	}

	@RequestMapping(value = "/logindateFromVP", method = RequestMethod.GET)
	ModelAndView logindate(ModelMap model, @ModelAttribute LoginForm loginForm) throws CustomException {
		Date loginDateDetails = logDetailsDAO.findLoginDate();
		if (loginDateDetails != null) {
			loginForm.setLoginDate(loginDateDetails);
		}
		model.put("loginForm", loginForm);
		return new ModelAndView("logindateFromVP", "model", model);

	}

	@RequestMapping(value = "/logindateSave", method = RequestMethod.POST)
	ModelAndView logindateSave(ModelMap model, @ModelAttribute LoginForm loginForm, RedirectAttributes attributes)
			throws CustomException {

		Date loginDate = logDetailsDAO.findLoginDate();
		int save = 0;
		LoginDate loginDateDetails1 = logDetailsDAO.getLoginDate();

		if (loginDateDetails1 == null) {
			loginDateDetails1 = new LoginDate();
			save = 1;
		}

		loginDateDetails1.setLoginDate(loginForm.getLoginDate());

		if (save == 1) {
			logDetailsDAO.saveLoginDate(loginDateDetails1);
			DateService.loginDate = loginForm.getLoginDate();
			attributes = updateTransaction("Saved Successfully", attributes);
		} else {
			logDetailsDAO.updateLoginDate(loginDateDetails1);
			DateService.loginDate = loginForm.getLoginDate();
			attributes = updateTransaction("Updated Successfully", attributes);

		}

		return new ModelAndView("redirect:loginDateSaved");

	}

	@RequestMapping(value = "/loginDateSaved", method = RequestMethod.GET)
	public ModelAndView loginDateSaved(ModelMap model) throws CustomException {

		return new ModelAndView("loginDateSaved", "model", model);

	}

	@RequestMapping(value = "/loginDateForJsp", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Date loginDateForJsp() throws CustomException {

		return DateService.loginDate;
	}

	@RequestMapping(value = "/vpFDSaved", method = RequestMethod.GET)
	public ModelAndView fdSaved(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("vpFDSaved", "model", model);

	}

	@RequestMapping(value = "/countryForDTAAFromVP", method = RequestMethod.GET)
	public ModelAndView countryForDTAA(ModelMap model) throws CustomException {

		List<DTAACountry> dtaaCountryList = dtaaCountryRatesDAO.getDTAACountryList();

		model.put("dtaaCountryList", dtaaCountryList);
		return new ModelAndView("countryForDTAAFromVP", "model", model);
	}

	@RequestMapping(value = "/addCountryForDTAAFromVP", method = RequestMethod.GET)
	public ModelAndView addCountryForDTAA(ModelMap model, @ModelAttribute AddCountryForDTAAForm addCountryForDTAAForm)
			throws CustomException {

		model.put("addCountryForDTAAForm", addCountryForDTAAForm);
		return new ModelAndView("addCountryForDTAAFromVP", "model", model);
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
			return new ModelAndView("addCountryForDTAAFromVP", "model", model);
		} else {
			DTAACountry dtaaCountry = new DTAACountry();
			dtaaCountry.setCountry(addCountryForDTAAForm.getCountry());
			dtaaCountry.setId(null);
			dtaaCountry = dtaaCountryRatesDAO.saveDTAACountry(dtaaCountry);
		}

		/*
		 * Date curDate = DateService.getCurrentDateTime();
		 * attributes.addFlashAttribute(Constants.TRANSACTIONID,
		 * fdService.generateRandomString());
		 * attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
		 * attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS,
		 * "Saved Successfully");
		 */
		attributes = updateTransaction("Saved Successfully", attributes);
		return new ModelAndView("redirect:vpFDSaved");

	}

	@RequestMapping(value = "/dtaaCountryRatesFromVP", method = RequestMethod.GET)
	public ModelAndView getDTAATaxRates(ModelMap model, RedirectAttributes attributes) throws CustomException {

		List<DTAACountryRates> dtaaCountryRateList = dtaaCountryRatesDAO.getDTAACountryRatesList();

		model.put("dtaaCountryRateList", dtaaCountryRateList);
		return new ModelAndView("dtaaCountryRatesFromVP", "model", model);
	}

	@RequestMapping(value = "/addDTAATaxRateFromVP", method = RequestMethod.GET)
	public ModelAndView addDTAATaxRate(ModelMap model,
			@ModelAttribute AddCountryWiseTaxRateDTAAForm addCountryWiseTaxRateDTAAForm) throws CustomException {

		List<DTAACountry> countryList = dtaaCountryRatesDAO.getDTAACountryList();

		model.put("countryList", countryList);
		model.put("addCountryWiseTaxRateDTAAForm", addCountryWiseTaxRateDTAAForm);
		return new ModelAndView("addDTAATaxRateFromVP", "model", model);
	}

	@Transactional
	@RequestMapping(value = "/addCountryWiseTaxRateDTAAPost", method = RequestMethod.POST)
	public ModelAndView addCountryWiseTaxRateDTAAPost(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute AddCountryWiseTaxRateDTAAForm addCountryWiseTaxRateDTAAForm) throws CustomException {

		DTAACountry dtaaCountry = dtaaCountryRatesDAO.getDTAACountry(addCountryWiseTaxRateDTAAForm.getDtaaCountryId());
		if (dtaaCountry == null) {
			List<DTAACountry> countryList = dtaaCountryRatesDAO.getDTAACountryList();

			model.put("countryList", countryList);
			model.put("error", "Country is not listed for DTAA.");
			return new ModelAndView("addDTAATaxRate", "model", model);
		}

		if (addCountryWiseTaxRateDTAAForm.getEffectiveDate() == null) {
			List<DTAACountry> countryList = dtaaCountryRatesDAO.getDTAACountryList();

			model.put("countryList", countryList);
			model.put("error", "Effective Date is not given.");
			return new ModelAndView("addDTAATaxRate", "model", model);
		}

		if (addCountryWiseTaxRateDTAAForm.getTaxRate() == null) {
			List<DTAACountry> countryList = dtaaCountryRatesDAO.getDTAACountryList();

			model.put("countryList", countryList);
			model.put("error", "Rate is not entered.");
			return new ModelAndView("addDTAATaxRate", "model", model);
		}
		if (addCountryWiseTaxRateDTAAForm.getTaxRate() < 0 || addCountryWiseTaxRateDTAAForm.getTaxRate() >= 100) {
			List<DTAACountry> countryList = dtaaCountryRatesDAO.getDTAACountryList();

			model.put("countryList", countryList);
			model.put("error", "Rate is not valid. Rate should be greater than 0 and less than 100.");
			return new ModelAndView("addDTAATaxRate", "model", model);
		}

		DTAACountryRates countryRate = dtaaCountryRatesDAO.getDTAATaxRate(
				addCountryWiseTaxRateDTAAForm.getDtaaCountryId(), addCountryWiseTaxRateDTAAForm.getEffectiveDate());
		if (countryRate == null) {
			countryRate = new DTAACountryRates();
			countryRate.setCountry(dtaaCountry.getCountry());
			countryRate.setDtaaCountryId(addCountryWiseTaxRateDTAAForm.getDtaaCountryId());
			countryRate.setEffectiveDate(addCountryWiseTaxRateDTAAForm.getEffectiveDate());
			countryRate.setTaxRate(addCountryWiseTaxRateDTAAForm.getTaxRate());
			dtaaCountryRatesDAO.saveDTAACountryRates(countryRate);
		} else {
			countryRate.setTaxRate(addCountryWiseTaxRateDTAAForm.getTaxRate());
			dtaaCountryRatesDAO.updateDTAACountryRates(countryRate);
		}

		// fdService.forReports(fixedDepositForm); // For reports
		attributes.addFlashAttribute("addCountryWiseTaxRateDTAAForm", addCountryWiseTaxRateDTAAForm);
		attributes = updateTransaction("Saved Successfully", attributes);
		return new ModelAndView("redirect:vpFDSaved");
	}

	@RequestMapping(value = "/addWithdrawPenalty", method = RequestMethod.GET)
	public ModelAndView addWithdrawPenalty(ModelMap model, @ModelAttribute WithdrawPenaltyForm wthdrawPenaltyForm)
			throws CustomException {
		List<ProductConfiguration> configurations = productConfigurationDAO.findAll();
		model.put("productConfigurations", configurations);
		model.put("wthdrawPenaltyForm", wthdrawPenaltyForm);

		return new ModelAndView("addWithdrawPenalty", "model", model);
	}

	@RequestMapping(value = "/saveWithdrawPenalty", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveWithdrawPenalty1(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute WithdrawPenaltyForm wthdrawPenaltyForm,
			@RequestParam("withdrawPenaltyFormList") String withdrawPenaltyList) throws ParseException {

		WithdrawPenaltyMaster penaltyMaster = null;

		WithdrawPenaltyFormList withdrawPenaltyFormList = new Gson().fromJson(withdrawPenaltyList,
				WithdrawPenaltyFormList.class);
		List<WithdrawPenaltyForm> penaltyFormList = withdrawPenaltyFormList.getWithdrawPenaltyFormList();

		for (WithdrawPenaltyForm withdrawPenaltyForm : penaltyFormList) {
			Boolean isExist = withdrawPenaltyDAO.isAmountToAndAmountFromRangeExistNew(withdrawPenaltyForm);
			if (isExist) {
				attributes.addFlashAttribute("error", "Amount entered is already exist !");
				ModelAndView andView = new ModelAndView();
				andView.setViewName("redirect:addWithdrawPenalty");
				return andView;

			}
		}

		for (WithdrawPenaltyForm withdrawPenaltyForm : penaltyFormList) {

			boolean isPreMatureWithdraw = (withdrawPenaltyForm.getIsPrematureWithdraw() != null
					&& withdrawPenaltyForm.getIsPrematureWithdraw() == 1) ? true : false;
			WithdrawPenaltyMaster withdrawPenaltyMaster = withdrawPenaltyDAO
					.getWithdrawPenalyMaster(withdrawPenaltyForm.getProductConfigurationId(), isPreMatureWithdraw);

			if (!(withdrawPenaltyMaster != null && withdrawPenaltyMaster.getWithdrawType()
					.equalsIgnoreCase(withdrawPenaltyForm.getWithdrawType()))) {
				penaltyMaster = new WithdrawPenaltyMaster();
				Date effectiveDate = new SimpleDateFormat("dd/MM/yyyy").parse(withdrawPenaltyForm.getEffectiveDate());
				penaltyMaster.setEffectiveDate(effectiveDate);
				penaltyMaster.setProductConfigurationId(withdrawPenaltyForm.getProductConfigurationId());
				penaltyMaster.setWithdrawType(withdrawPenaltyForm.getWithdrawType());
				penaltyMaster.setCreatedBy(getCurrentLoggedUserName());
				penaltyMaster.setCreatedDate(new Date());
				penaltyMaster.setModifiedDate(new Date());
				penaltyMaster.setModifiedBy(getCurrentLoggedUserName());
				penaltyMaster.setIsPrematureWithdraw(withdrawPenaltyForm.getIsPrematureWithdraw());
				penaltyMaster = withdrawPenaltyDAO.insertWithdrawPenaltyMaster(penaltyMaster);
			}

			if (withdrawPenaltyForm.getWithdrawType().equals("Amount Based")) {
				WithdrawPenaltyAmountBased penaltyDetails = new WithdrawPenaltyAmountBased();
				penaltyDetails.setWithdrawPenaltyMasterId(penaltyMaster.getId());
				penaltyDetails.setAmountFrom(withdrawPenaltyForm.getAmountFrom());
				penaltyDetails.setAmountTo(withdrawPenaltyForm.getAmountTo());

				Float fPenaltyRate = !withdrawPenaltyForm.getPenaltyRate().equals("")
						&& withdrawPenaltyForm.getPenaltyRate().length() > 0
								? Float.parseFloat(withdrawPenaltyForm.getPenaltyRate())
								: null;
				Float fPenaltyFlatAmount = !withdrawPenaltyForm.getPenaltyFlatAmount().equals("")
						&& withdrawPenaltyForm.getPenaltyFlatAmount().length() > 0
								? Float.valueOf(withdrawPenaltyForm.getPenaltyFlatAmount())
								: null;

				if (fPenaltyRate != null)
					penaltyDetails.setPenaltyRate(Float.valueOf(String.format("%.2f", fPenaltyRate)));
				if (fPenaltyFlatAmount != null)
					penaltyDetails.setPenaltyFlatAmount(Float.valueOf(String.format("%.2f", fPenaltyFlatAmount)));
				penaltyDetails.setCreatedBy(getCurrentLoggedUserName());
				penaltyDetails.setCreatedDate(new Date());
				penaltyDetails.setModifiedDate(new Date());
				penaltyDetails.setModifiedBy(getCurrentLoggedUserName());
				withdrawPenaltyDAO.saveWithdrawPenaltyAmountBased(penaltyDetails);

			} else {

				WithdrawPenaltyTenureBased based = new WithdrawPenaltyTenureBased();
				//based.setSign(withdrawPenaltyForm.getSign());
				based.setTenure(withdrawPenaltyForm.getTenure());
				based.setPenaltyFlatAmount(!withdrawPenaltyForm.getPenaltyFlatAmount().equals("")
						&& withdrawPenaltyForm.getPenaltyFlatAmount().length() > 0
								? Float.valueOf(withdrawPenaltyForm.getPenaltyFlatAmount())
								: null);
				based.setWithdrawPenaltyMasterId(withdrawPenaltyForm.getWithdrawPenaltyMasterId());
				Float fPenaltyRate = !withdrawPenaltyForm.getPenaltyRate().equals("")
						&& withdrawPenaltyForm.getPenaltyRate().length() > 0
								? Float.parseFloat(withdrawPenaltyForm.getPenaltyRate())
								: null;
				based.setPenaltyRate(fPenaltyRate);
				based.setCreatedBy(getCurrentLoggedUserName());
				based.setCreatedDate(new Date());
				based.setModifiedDate(new Date());
				based.setModifiedBy(getCurrentLoggedUserName());
				based.setWithdrawPenaltyMasterId(penaltyMaster.getId());
				withdrawPenaltyDAO.saveWithdrawPenaltyTenureBased(based);

			}
		}
		model.put("wthdrawPenaltyForm", wthdrawPenaltyForm);
		attributes = updateTransaction("Saved Successfully", attributes);
		return new ModelAndView("redirect:vpWithdrawPenaltySaved");
	}

	@RequestMapping(value = "/vpWithdrawPenaltySaved", method = RequestMethod.GET)
	public ModelAndView withdrawPenaltySaved(ModelMap model, @ModelAttribute WithdrawPenaltyForm wthdrawPenaltyForm)
			throws CustomException {

		model.put("wthdrawPenaltyForm", wthdrawPenaltyForm);
		return new ModelAndView("vpFDSaved", "model", model);

	}

	@RequestMapping(value = "/viewWithdrawPenalty", method = RequestMethod.GET)
	public ModelAndView viewWithdrawPenalty(ModelMap model,
			@ModelAttribute("wthdrawPenaltyForm") WithdrawPenaltyForm wthdrawPenaltyForm)
			throws CustomException, ParseException {

		// WithdrawPenaltyFormList withdrawPenaltyFormList = new
		// WithdrawPenaltyFormList();

		List<Date> effectiveDateList = withdrawPenaltyDAO.getEffectiveDates();

		Date effectiveDate = null;

		if (effectiveDateList != null && effectiveDateList.size() > 0) {
			effectiveDate = effectiveDateList.get(effectiveDateList.size() - 1);
		} else {
			return new ModelAndView("noDataFoundVP", "model", model);

		}

		/*
		 * List<Object[]> penaltyList =
		 * withdrawPenaltyDAO.getSlabBasedWithdrawPenalties(effectiveDate);
		 * ArrayList<WithdrawPenaltyForm> withdrawPenaltyFormList = new ArrayList<>();
		 */

		/*
		 * for (int i = 0; i < penaltyList.size(); i++) { // m.id,m.effectiveDate,
		 * d.withdrawPenaltyMasterId, d.amountFrom, d.amountTo, // d.penaltyRate,
		 * "d.penaltyFlatAmount WithdrawPenaltyForm withdrawPenaltyForm = new
		 * WithdrawPenaltyForm(); withdrawPenaltyForm.setId(((BigInteger)
		 * penaltyList.get(i)[0]).longValue());
		 * withdrawPenaltyForm.setEffectiveDate((String)
		 * penaltyList.get(i)[1].toString());
		 * withdrawPenaltyForm.setWithdrawPenaltyMasterId(((BigInteger)
		 * penaltyList.get(i)[2]).longValue());
		 * withdrawPenaltyForm.setAmountFrom((Integer) penaltyList.get(i)[3]);
		 * withdrawPenaltyForm.setAmountTo((Integer) penaltyList.get(i)[4]); if
		 * (penaltyList.get(i)[5] != null)
		 * withdrawPenaltyForm.setPenaltyRate(penaltyList.get(i)[5].toString()); if
		 * (penaltyList.get(i)[6] != null)
		 * withdrawPenaltyForm.setPenaltyFlatAmount(penaltyList.get(i)[6].toString());
		 * 
		 * withdrawPenaltyFormList.add(withdrawPenaltyForm);
		 * 
		 * }
		 */

		model.put("effectiveDateList", effectiveDateList);
		// model.put("withdrawPenaltyFormList", withdrawPenaltyFormList);
		return new ModelAndView("viewWithdrawPenalty", "model", model);
	}

	@RequestMapping(value = "/wthdrawPenaltyFormEdit", method = RequestMethod.POST)
	public ModelAndView viewWithdrawPenaltyEdit(ModelMap model,
			@ModelAttribute("wthdrawPenaltyForm") WithdrawPenaltyForm wthdrawPenaltyForm, RedirectAttributes attributes)
			throws CustomException, ParseException {

		Boolean isExist = withdrawPenaltyDAO.isAmountToAndAmountFromRangeExist(wthdrawPenaltyForm);
		if (isExist) {
			attributes.addFlashAttribute("error", "Amount entered is already exist !");
			attributes.addFlashAttribute("getEffectiveDate", wthdrawPenaltyForm.getTempDate());

			ModelAndView andView = new ModelAndView();
			andView.setViewName("redirect:viewWithdrawPenalty");
			return andView;
		}

		List<Date> effectiveDateList = withdrawPenaltyDAO.getEffectiveDates();

		WithdrawPenaltyAmountBased details = withdrawPenaltyDAO
				.getAmountBasedWithdrawPenalty(wthdrawPenaltyForm.getId());
		details.setAmountFrom(wthdrawPenaltyForm.getAmountFrom());
		details.setAmountTo(wthdrawPenaltyForm.getAmountTo());
		Float fPenaltyRate = !wthdrawPenaltyForm.getPenaltyRate().equals("")
				&& wthdrawPenaltyForm.getPenaltyRate().length() > 0
						? Float.parseFloat(wthdrawPenaltyForm.getPenaltyRate())
						: null;
		Float fPenaltyFlatAmount = !wthdrawPenaltyForm.getPenaltyFlatAmount().equals("")
				&& wthdrawPenaltyForm.getPenaltyFlatAmount().length() > 0
						? Float.parseFloat(wthdrawPenaltyForm.getPenaltyFlatAmount())
						: null;
		details.setPenaltyFlatAmount(fPenaltyFlatAmount);
		details.setPenaltyRate(fPenaltyRate);
		withdrawPenaltyDAO.updateWithdrawPenaltyAmountBased(details);

		model.put("effectiveDateList", effectiveDateList);
		model.put("effectiveDate", wthdrawPenaltyForm.getEffectiveDate());
		model.put("sucess", "Updated successfully");
		return new ModelAndView("viewWithdrawPenalty", "model", model);
	}

	@RequestMapping(value = "/getWithdrawPenaltyList", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<WithdrawPenaltyForm> getWithdrawPenaltyList(String effectiveDate,
			String productConfigurationId, String isPremature) throws Exception {

		Date effectiveDateParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(effectiveDate);

		List<Object[]> penaltyList = withdrawPenaltyDAO.getSlabBasedWithdrawPenalties(effectiveDateParse,
				Long.parseLong(productConfigurationId), Integer.parseInt(isPremature));
		List<WithdrawPenaltyForm> withdrawPenaltyFormList = new ArrayList<>();

		for (int i = 0; i < penaltyList.size(); i++) {
			// m.id,m.effectiveDate, d.withdrawPenaltyMasterId, d.amountFrom, d.amountTo,
			// d.penaltyRate, "d.penaltyFlatAmount
			WithdrawPenaltyForm withdrawPenaltyForm = new WithdrawPenaltyForm();
			withdrawPenaltyForm.setId(new BigInteger(penaltyList.get(i)[0].toString()).longValue());
			withdrawPenaltyForm.setEffectiveDate((String) penaltyList.get(i)[1].toString());
			withdrawPenaltyForm
					.setWithdrawPenaltyMasterId(new BigInteger(penaltyList.get(i)[2].toString()).longValue());
			withdrawPenaltyForm.setAmountFrom((Integer) penaltyList.get(i)[3]);
			withdrawPenaltyForm.setAmountTo((Integer) penaltyList.get(i)[4]);
			if (penaltyList.get(i)[5] != null)
				withdrawPenaltyForm.setPenaltyRate(penaltyList.get(i)[5].toString());
			if (penaltyList.get(i)[6] != null)
				withdrawPenaltyForm.setPenaltyFlatAmount(penaltyList.get(i)[6].toString());

			withdrawPenaltyFormList.add(withdrawPenaltyForm);

		}

		return withdrawPenaltyFormList;
	}

	@RequestMapping(value = "/glConfiguration", method = RequestMethod.GET)
	public ModelAndView glConfiguration(ModelMap model,
			@ModelAttribute("glConfigurationForm") GLConfigurationForm glConfigurationForm) throws CustomException {
		Long glConfigurationCountById = ledgerService.countById();
		List<GLConfiguration> glConfigurationList = ledgerService.getGLConfigurations();
		model.put("glConfigurationList", glConfigurationList);
		model.put("glConfigurationCountById", glConfigurationCountById);
		return new ModelAndView("glConfiguration", "model", model);
	}

	@Transactional
	@RequestMapping(value = "/saveGLConfiguration", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveGLConfiguration(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute("glConfigurationForm") GLConfigurationForm glConfigurationForm,
			@RequestParam("glCodeList") String glCodeList, @RequestParam("glNumberList") String glNumberList)
			throws ParseException {

		Boolean isUpadated = (Boolean) ledgerService.insertGLCode(glCodeList, glNumberList,
				this.getCurrentLoggedUserDetails().getCustomerId());

		if (isUpadated) {
			attributes.addFlashAttribute("GLConfigurationForm", glConfigurationForm);
			attributes = updateTransaction("Saved Successfully", attributes);
			return new ModelAndView("redirect:vpFDSaved");
		} else {
			List<GLConfiguration> glConfigurationList = ledgerService.getGLConfigurations();
			model.put("glConfigurationList", glConfigurationList);
			return new ModelAndView("glConfiguration", "model", model);
		}

	}

	@Transactional
	@RequestMapping(value = "/editSaveGLConfiguration", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveGLConfiguration(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute(value = "glConfigurationFormEdit") GLConfigurationForm glConfigurationFormEdit,
			@ModelAttribute GLConfigurationForm glConfigurationForm) {
		try {

			if (glConfigurationFormEdit.getId() != null) {
				GLConfiguration configuration1 = ledgerDAO.findById(glConfigurationFormEdit.getId());
				if (configuration1 != null) {
					if (configuration1.getGlAccount().equals(glConfigurationFormEdit.getGlAccount())
							&& configuration1.getGlCode().equals(glConfigurationFormEdit.getGlCode())
							&& configuration1.getGlNumber().equals(glConfigurationFormEdit.getGlNumber())) {
						attributes.addFlashAttribute("error", "Please update your data !");
						return new ModelAndView("redirect:glConfiguration");
					} else {
						configuration1.setGlAccount(glConfigurationFormEdit.getGlAccount());
						configuration1.setGlCode(glConfigurationFormEdit.getGlCode());
						configuration1.setGlNumber(glConfigurationFormEdit.getGlNumber());
						configuration1.setModifiedDate(new Date());
						ledgerService.update(configuration1);
						attributes.addFlashAttribute("success", "Update sucessfully !");
						return new ModelAndView("redirect:glConfiguration");
					}
				} else {
					attributes.addFlashAttribute("error", "id does not exist !");
					return new ModelAndView("redirect:glConfiguration");
				}

			} else {
				GLConfiguration configuration = new GLConfiguration();
				configuration.setGlCode(glConfigurationFormEdit.getGlCode());
				configuration.setGlNumber(glConfigurationFormEdit.getGlNumber());
				configuration.setGlAccount(glConfigurationFormEdit.getGlAccount());
				configuration.setCreatedDate(new Date());
				configuration.setModifiedDate(new Date());
				ledgerService.saveGLConfiguration(configuration);
				attributes.addFlashAttribute("success", "Add Sucessfully !");
			}
		} catch (Exception exception) {
			attributes.addFlashAttribute("error", exception.getMessage());
		}
		return new ModelAndView("redirect:glConfiguration");

	}

	@RequestMapping(value = "/editVPProfile", method = RequestMethod.GET)
	public ModelAndView editVPProfile(ModelMap model, @RequestParam("id") Long id, RedirectAttributes attributes)
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

		return new ModelAndView("editVPProfile", "model", model);

	}

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
		return "redirect:editVPProfile?id=" + endUserForm.getId();
	}

	@RequestMapping(value = "/confirmEditVPProfile", method = RequestMethod.POST)
	public ModelAndView confirmEditVPProfile(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {

		model.put("endUserForm", endUserForm);

		return new ModelAndView("confirmEditVPProfile", "model", model);

	}

	@RequestMapping(value = "/updateVPDetails", method = RequestMethod.POST)
	public ModelAndView updateVPDetails(@ModelAttribute EndUserForm endUserForm, BindingResult result,
			RedirectAttributes attributes) throws CustomException {

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

		attributes = updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:vpFDSaved");

	}

	/**
	 * @author Sachin
	 * @since 2019
	 * @version 1
	 * @param holidayConfiguration
	 *                             <p>
	 *                             Model Bean (POJO)
	 *                             </p>
	 * 
	 */
	@RequestMapping(value = "/holidayConfigurationVP")
	public ModelAndView holidayConfiguration(Model model,
			@ModelAttribute("holidayConfigurationVP") HolidayConfiguration holidayConfiguration) {
		model.addAttribute("holidayConfiguration", holidayConfiguration);
		return new ModelAndView("holidayConfigurationVP", "model", model);

	}

	@RequestMapping(value = "/getState", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<Branch> getState(String state) throws CustomException {
		List<Branch> branch = null;
		branch = branchDAO.getAllBranchesByState(state);
		return branch;

	}

	/**
	 * 
	 * 
	 * @author Sachin
	 * @param holidayConfiguration
	 * @return {@link ModelAndView}
	 */
	private final String COMMA_SEPARATER = ",";

	@RequestMapping(value = "/holidayConfiguration", method = RequestMethod.POST)
	public ModelAndView saveHolidayConfiguration(Model model,
			@ModelAttribute("holidayConfiguration") HolidayConfiguration holidayConfiguration,
			@RequestParam String[] myArray) {
		try {
			System.out.println(holidayConfiguration);
			String[] array = myArray;

			for (int i = 0; i < array.length; i++) {
				HolidayConfiguration objHolidayConfiguration = new HolidayConfiguration();

				if (holidayConfiguration.getDates() != null) {
					objHolidayConfiguration.setCreatedOn(DateService.getCurrentDateTime());
					objHolidayConfiguration.setModifiedOn(DateService.getCurrentDateTime());
					String calenderDates = holidayConfiguration.getDates();
					String splitesDates[] = calenderDates.split(COMMA_SEPARATER);
					List<Date> newDatesInstance = new ArrayList<Date>();
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					for (String stringDate : splitesDates) {
						Date stringDateParseToDate = dateFormat.parse(stringDate);
						newDatesInstance.add(stringDateParseToDate);
					}
					if (holidayConfiguration != null) {
						objHolidayConfiguration.setYear(holidayConfiguration.getYear());
						objHolidayConfiguration.setBranchCode(array[i]);
						objHolidayConfiguration.setState(holidayConfiguration.getState());
						objHolidayConfiguration.setDate(newDatesInstance);
						Collections.sort(newDatesInstance);
						// Check Configurations already exist or not
						List<HolidayConfiguration> isExistigYear = holidayConfigurationDAO
								.isPresent(holidayConfiguration.getYear(), array[i], holidayConfiguration.getState());
						if ((isExistigYear != null && isExistigYear.size() > 0)) {
							holidayConfigurationDAO.delete(holidayConfiguration.getYear(), array[i],
									holidayConfiguration.getState());

						}

						holidayConfigurationDAO.save(objHolidayConfiguration);

						model.addAttribute("sucess", "Updated sucessfully!");

					}
				} else {
					model.addAttribute("error", "Please select dates or date!");
				}

			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return new ModelAndView("holidayConfigurationVP", "model", model);

	}

	/**
	 * 
	 * @param model
	 * @param holidayConfiguration
	 * @return JSON
	 * 
	 */

	@RequestMapping(value = "/holidayConfigurationGetByYear", produces = { "application/json" })
	@ResponseBody
	public String holidayConfigurationYear(Model model,
			@ModelAttribute("holidayConfiguration") HolidayConfiguration holidayConfiguration) {
		HolidayConfiguration holidayConfigurationList = null;
		String json = "{}";
		try {
			holidayConfigurationList = holidayConfigurationDAO
					.getConfigurationByYearAndState(holidayConfiguration.getYear(), holidayConfiguration.getState());
			if (holidayConfigurationList != null) {
				model.addAttribute("holidayConfiguration", holidayConfigurationList);
				json = new Gson().toJson(holidayConfigurationList);
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return json;
	}

	/**
	 * <h1>Product Configuration</h1>
	 * 
	 * @param model
	 * @param productConfiguration
	 * @return
	 */

	@RequestMapping(value = "/productWiseConfiguration")
	public ModelAndView productWiseConfiuration(Model model,
			@ModelAttribute ProductConfiguration productConfiguration) {

		return new ModelAndView("productWiseConfiguration", "model", model);
	}

	@RequestMapping(value = "/productWiseConfiguration", method = RequestMethod.POST)
	public ModelAndView saveProductWiseConfiuration(Model model,
			@ModelAttribute ProductConfiguration productConfiguration, RedirectAttributes attributes) {
		try {

			ProductConfiguration isExist = productConfigurationDAO
					.findByProductCode(productConfiguration.getProductCode().toLowerCase());
			if (isExist != null) {
				model.addAttribute("error", "Product Code already exist !");
				return new ModelAndView("productWiseConfiguration", "model", model);
			}

			productConfiguration.setCreatedDate(new Date());
			productConfiguration.setModifiedDate(new Date());
			productConfiguration.setCreatedBy(getCurrentLoggedUserName());
			productConfiguration.setProductCode(productConfiguration.getProductCode().toLowerCase());
			productConfigurationDAO.insertProductConfiguration(productConfiguration);
			attributes = updateTransaction("Saved Successfully", attributes);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:updateAMSuccess");
	}

	@RequestMapping(value = "/productConfiurationByAccountType", method = RequestMethod.GET)
	public ModelAndView productConfiurationByAccountType(Model model,
			@ModelAttribute ProductConfiguration productConfiguration, Long id) {
		try {
			if (id != null) {
				productConfiguration = productConfigurationDAO.findById(id);
				model.addAttribute("productConfiguration", productConfiguration);
			} else {

				return new ModelAndView("editProductConfiguration", "model", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("editProductConfiguration", "model", model);
	}

	@RequestMapping(value = "/getProducts", method = RequestMethod.GET)
	public ModelAndView findAll(Model model, @ModelAttribute ProductConfiguration productConfiguration) {
		try {
			List<ProductConfiguration> productConfigurations = productConfigurationDAO.findAll();
			model.addAttribute("productConfigurations", productConfigurations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("viewProductConfiguration", "model", model);
	}

	@RequestMapping(value = "/editProductWiseConfiguration", method = RequestMethod.POST)
	public ModelAndView editProductConfiuration(Model model, @ModelAttribute ProductConfiguration productConfiguration,
			RedirectAttributes attributes) {
		try {
			if (productConfiguration.getId() != null) {
				if (!productConfigurationDAO.findById(productConfiguration.getId()).getProductCode()
						.equalsIgnoreCase(productConfiguration.getProductCode())) {
					ProductConfiguration isExist = productConfigurationDAO
							.findByProductCode(productConfiguration.getProductCode());
					if (isExist != null) {
						model.addAttribute("error", "Product Code already exist !");
						model.addAttribute("productConfiguration", productConfiguration);
						return new ModelAndView("editProductConfiguration", "model", model);
					}
				}
				productConfiguration.setModifiedDate(new Date());
				productConfiguration.setCreatedBy(getCurrentLoggedUserName());
				productConfigurationDAO.updateProductConfiguration(productConfiguration);
				attributes = updateTransaction("Updated Successfully", attributes);
				return new ModelAndView("redirect:updateAMSuccess");
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("productConfiguration", productConfiguration);
		}
		return new ModelAndView("editProductConfiguration", "model", model);
	}

	/**
	 * 
	 * @param model
	 * @param citizenAndCustomerCategory
	 * @return
	 */

	@RequestMapping(value = "/citizenAndCustomerCategory")
	public ModelAndView citizenAndCustomerCategory(Model model,
			@ModelAttribute CitizenAndCustomerCategoryMapping citizenAndCustomerCategory) {
		List<CustomerCategory> categories = customerCategoryDAO.findAll();

		List<CustomerCategory> customerCategoryRI = new ArrayList<CustomerCategory>();
		List<CustomerCategory> customerCategoryNRI = new ArrayList<CustomerCategory>();

		for (CustomerCategory customerCategory : categories) {
			CitizenAndCustomerCategoryMapping categoryMappingRI = citizenAndCustomerCategoryDAO
					.findByCustomerCategoryIdAndCitizen(customerCategory.getId(), "RI");
			if (categoryMappingRI != null) {
				customerCategory.setRiFlag(true);
				customerCategoryRI.add(customerCategory);
			} else {
				customerCategory.setRiFlag(false);
				customerCategoryRI.add(customerCategory);
			}
			CitizenAndCustomerCategoryMapping categoryMappingNRI = citizenAndCustomerCategoryDAO
					.findByCustomerCategoryIdAndCitizen(customerCategory.getId(), "NRI");
			if (categoryMappingNRI != null) {
				customerCategory.setNriFlag(true);
				customerCategoryNRI.add(customerCategory);
			} else {
				customerCategory.setNriFlag(false);
				customerCategoryNRI.add(customerCategory);
			}

		}
		model.addAttribute("customerCategories", categories);
		return new ModelAndView("citizenAndCustomerCategory", "model", model);
	}

	@RequestMapping(value = "/citizenAndCustomerCategory", method = RequestMethod.POST)
	public ModelAndView citizenCustomerCategory(Model model,
			@ModelAttribute CitizenAndCustomerCategoryMapping citizenAndCustomerCategory,
			RedirectAttributes redirectAttributes, String nriTypes) {
		try {
			String loggedUser = getCurrentLoggedUserName();
			Date currentTimeAndDate = new Date();
			String[] ids = citizenAndCustomerCategory.getCustomerCategoryIds().split(",");
			if (citizenAndCustomerCategory.getCitizen().equals("NRI")) {
				Map<String, String> map = new Gson().fromJson(nriTypes, new TypeToken<Map<String, String>>() {
				}.getType());
				if (!map.isEmpty() && map.size() > 0) {
					citizenAndCustomerCategoryDAO.deleteByCitizen("NRI");
					for (Map.Entry<String, String> entry : map.entrySet()) {
						CitizenAndCustomerCategoryMapping andCustomerCategoryMapping = new CitizenAndCustomerCategoryMapping();
						andCustomerCategoryMapping.setCreatedDate(currentTimeAndDate);
						andCustomerCategoryMapping.setModifiedDate(currentTimeAndDate);
						andCustomerCategoryMapping.setCreatedBy(loggedUser);
						andCustomerCategoryMapping.setModifiedBy(loggedUser);
						andCustomerCategoryMapping.setCustomerCategoryId(Long.parseLong(entry.getKey()));
						andCustomerCategoryMapping.setCitizen(citizenAndCustomerCategory.getCitizen());
						andCustomerCategoryMapping.setNriAccountType(entry.getValue());
						citizenAndCustomerCategoryDAO.save(andCustomerCategoryMapping);

					}
					redirectAttributes = updateTransaction("Saved Successfully", redirectAttributes);
					return new ModelAndView("redirect:updateAMSuccess");
				}
			}
			if (citizenAndCustomerCategory.getCitizen().equals("RI")) {
				if (ids.length > 0) {
					citizenAndCustomerCategoryDAO.deleteByCitizen("RI");
					for (String id : ids) {
						CitizenAndCustomerCategoryMapping andCustomerCategoryMapping = new CitizenAndCustomerCategoryMapping();
						andCustomerCategoryMapping.setCreatedDate(currentTimeAndDate);
						andCustomerCategoryMapping.setModifiedDate(currentTimeAndDate);
						andCustomerCategoryMapping.setCreatedBy(loggedUser);
						andCustomerCategoryMapping.setModifiedBy(loggedUser);
						andCustomerCategoryMapping.setCustomerCategoryId(Long.parseLong(id));
						andCustomerCategoryMapping.setCitizen(citizenAndCustomerCategory.getCitizen());
						andCustomerCategoryMapping.setNriAccountType(null);
						citizenAndCustomerCategoryDAO.save(andCustomerCategoryMapping);

					}
					redirectAttributes = updateTransaction("Saved Successfully", redirectAttributes);
					return new ModelAndView("redirect:updateAMSuccess");
				} else {
					model.addAttribute("error", "Customer category id not found");
					return new ModelAndView("citizenAndCustomerCategory", "model", model);
				}
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());

		}
		return new ModelAndView("citizenAndCustomerCategory", "model", model);
	}

	@RequestMapping(value = "/GSTDeduction")
	public ModelAndView gstDeduction(Model model) {
		GSTDeduction gstDeduction = gstDeductionDAO.findAll();
		if (gstDeduction != null)
			model.addAttribute("gstDeduction", gstDeduction);
		else
			model.addAttribute("gstDeduction", new GSTDeduction());

		return new ModelAndView("GSTDeduction", "model", model);
	}

	@RequestMapping(value = "/gstDeduction", method = RequestMethod.POST)
	public ModelAndView gstDeductionSave(Model model, @ModelAttribute GSTDeduction gstDeduction,
			RedirectAttributes redirectAttributes) {
		ModelAndView andView = new ModelAndView("GSTDeduction", "model", model);
		String loggedUser = getCurrentLoggedUserName();
		Date currentTimeAndDate = new Date();
		try {
			if (gstDeduction != null) {
				if (gstDeduction.getId() != null) {
					gstDeduction.setModifiedDate(currentTimeAndDate);
					gstDeduction.setModifiedBy(loggedUser);
					gstDeduction = gstDeductionDAO.update(gstDeduction);
					model.addAttribute("gstDeduction", gstDeduction);
					redirectAttributes = updateTransaction("Updated Successfully", redirectAttributes);
					andView = new ModelAndView("redirect:updateAMSuccess");
				} else {
					gstDeduction.setModifiedDate(currentTimeAndDate);
					gstDeduction.setModifiedBy(loggedUser);
					gstDeduction.setCreatedDate(currentTimeAndDate);
					gstDeduction.setCreatedBy(loggedUser);
					gstDeductionDAO.save(gstDeduction);
					redirectAttributes = updateTransaction("Saved Successfully", redirectAttributes);
					andView = new ModelAndView("redirect:updateAMSuccess");
				}
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());

		}
		return andView;
	}

	/**
	 * 
	 * @param model
	 * @return {@link ModelAndView}
	 * 
	 *         <p>
	 *         ----------------- Sweep Configuration --------------------
	 * 
	 *         {@link SweepInFacilityForCustomer}
	 * 
	 * @author INDIAN
	 * 
	 *         </p>
	 */

	@RequestMapping(value = "/sweepConfiguration", method = RequestMethod.GET)
	public ModelAndView sweepConfiguration(Model model, @ModelAttribute SweepConfiguration sweepConfiguration) {

		SweepConfiguration sweepConfigurationActive = sweepConfigurationDAO.getActiveSweepConfiguration();
		if (sweepConfigurationActive != null)
			model.addAttribute("sweepConfiguration", sweepConfigurationActive);
		else
			model.addAttribute("sweepConfiguration", new SweepConfiguration());
		return new ModelAndView("sweepConfigurationVP", "model", model);
	}

	@RequestMapping(value = "/saveSweepConfiguration", method = RequestMethod.POST)
	public ModelAndView saveSweepConfiguration(Model model, @ModelAttribute SweepConfiguration sweepConfiguration,
			RedirectAttributes redirectAttributes)  {
		try {
			final Date CURRENT_DATE = new Date();
			if (sweepConfiguration.getId() != null) {
				SweepConfiguration sweepConfigurationById = sweepConfigurationDAO.findById(sweepConfiguration.getId());

				if (sweepConfigurationById != null) {

					// Before closing the active sweep configuration,
					// close all deposits created with same configuration.
					// System will not take any premature closing penalty
					// because bank is forcefully closing the deposits
					HashMap<Long, Double> customerWiseSweepAmount = null;
					if (!sweepConfigurationById.getSweepInType().equals(sweepConfiguration.getSweepInType())) {
						customerWiseSweepAmount = this.closeSweepDeposits(sweepConfigurationById.getSweepInType());
					}
					// Close the existing active Sweep Configuration
					sweepConfigurationById.setClosingDate(CURRENT_DATE);
					sweepConfigurationById.setIsActive(0);
					sweepConfigurationById.setModifiedDate(CURRENT_DATE);
					sweepConfigurationById.setModifiedBy(getCurrentLoggedUserName());
					sweepConfigurationDAO.update(sweepConfigurationById);

					/**
					 * After closing DATE insert new Sweep Data
					 * 
					 */
					sweepConfiguration.setId(null);
					sweepConfiguration.setIsActive(1);
					sweepConfiguration.setCreatedDate(CURRENT_DATE);
					sweepConfiguration.setModifiedDate(CURRENT_DATE);
					sweepConfiguration.setCreatedBy(getCurrentLoggedUserName());
					sweepConfiguration.setModifiedBy(getCurrentLoggedUserName());
					sweepConfiguration = sweepConfigurationDAO.save(sweepConfiguration);

					if (customerWiseSweepAmount != null) {
						this.createNewSweepDeposits(customerWiseSweepAmount, sweepConfiguration);
					}
					model.addAttribute("sweepConfiguration", sweepConfiguration);
					redirectAttributes = updateTransaction("Saved Successfully", redirectAttributes);
					return new ModelAndView("redirect:updateAMSuccess");
				}

			} else {
				/**
				 * first entry save in DB
				 * 
				 */
				sweepConfiguration.setIsActive(1);
				sweepConfiguration.setCreatedDate(CURRENT_DATE);
				sweepConfiguration.setModifiedDate(CURRENT_DATE);
				sweepConfiguration.setModifiedBy(getCurrentLoggedUserName());
				sweepConfiguration.setCreatedBy(getCurrentLoggedUserName());
				sweepConfiguration = sweepConfigurationDAO.save(sweepConfiguration);
				model.addAttribute("sweepConfiguration", sweepConfiguration);
				redirectAttributes = updateTransaction("Saved Successfully", redirectAttributes);
				return new ModelAndView("redirect:updateAMSuccess");

			}

		} catch (Exception e) {
			model.addAttribute("sweepConfiguration", sweepConfiguration);
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
		}

		return new ModelAndView("sweepConfigurationVP", "model", model);
	}

	private HashMap<Long, Double> closeSweepDeposits(String sweepInType) throws CustomException {


		// Get the distinct Customer for whom auto deposit created of existing type
		// --------------------------------
		// SELECT distinct dh.customerId from Deposit d inner join depositholder dh on
		// d.id = dh.depositid
		// where dh.depositHolderStatus='PRIMARY' and d.depositCategory='AUTO' and
		// d.sweepInType='Fixed Interest & Fixed Tenure' and
		// upper(d.status)='OPEN' and d.clearanceStatus is null
		// --------------------------------
		List<BigInteger> customerList = fixedDepositDao.getDistinctCustomerOfAutoDeposits(sweepInType);
		if (customerList == null)
			return null;
		// --------------------------------

		// Loop through the customer and bring the deposits of that customer
		// Close all the deposit of that customer
		// And create a new deposit with summed up value with new sweepInType
		// --------------------------------
		// SELECT d.id depositId,d.currentBalance, dh.customerId from Deposit d inner
		// join depositholder dh on d.id = dh.depositid
		// where dh.depositHolderStatus='PRIMARY' and d.depositCategory='AUTO' and
		// upper(d.status)='OPEN' and d.clearanceStatus is null ORDER BY d.id DESC
		// --------------------------------
		HashMap<Long, Double> sweepDeposits = new HashMap<>();
		for (int i = 0; i < customerList.size(); i++) {
			Double totalAmount = 0d;
			BigInteger cId = (BigInteger) customerList.get(i);
			Long cusId = cId.longValue();
			List<Deposit> autoDepositList = fixedDepositDao.getAutoDeposits(cusId, sweepInType);
			for (int j = 0; j < autoDepositList.size(); j++) {
				totalAmount = totalAmount
						+ calculationService.closeSweepDepositForcefullyByBank(autoDepositList.get(j));
			}

			// Create a new sweep deposit with newSweepType
			sweepDeposits.put(cusId, totalAmount);
			totalAmount = 0d;
		}
		// ---------------------------------

		return sweepDeposits;
	}

	private void createNewSweepDeposits(HashMap<Long, Double> customerWiseSweepAmount,
			SweepConfiguration sweepConfiguration) throws CustomException {
		if (sweepConfiguration == null)
			return;

		for (java.util.Map.Entry<Long, Double> entry : customerWiseSweepAmount.entrySet()) {

			Long customerId = entry.getKey();
			Double amount = entry.getValue();

			// Check the customer is opted for sweep facility or not
			SweepInFacilityForCustomer sweepfacilityForCustomer = sweepConfigurationDAO
					.getSweepInFacilityForCustomer(customerId);

			if (sweepfacilityForCustomer == null)
				return;

			// Create Sweep Deposit
			fdService.createSweepDeposit(customerId, amount, sweepConfiguration, sweepfacilityForCustomer, true);

		}

	}

	@RequestMapping(value = "currencyConfiguration", method = RequestMethod.GET)
	public ModelAndView currencyConfiguration(Model model,
			@ModelAttribute CurrencyConfiguration currencyConfiguration) {
		try {
			List<CurrencyConfiguration> configurations = currencyConfigurationDAO.findAll();

			CurrencyConfiguration configurationRI = null;
			List<CurrencyConfiguration> configurationNRI = new ArrayList<>();
			for (CurrencyConfiguration configuration : configurations) {
				if (configuration.getCitizen().equals("RI")) {
					configurationRI = configuration;
				} else {

					configurationNRI.add(configuration);
				}
			}
			model.addAttribute("currencyConfiguration", currencyConfiguration);
			model.addAttribute("configurationRI", configurationRI);
			model.addAttribute("configurationNRI", configurationNRI);

		} catch (Exception e) {
			model.addAttribute("currencyConfiguration", currencyConfiguration);
			model.addAttribute("error", e.getMessage());
		}
		return new ModelAndView("currencyConfigurationVP", "model", model);
	}

	@RequestMapping(value = "saveCurrencyConfiguration", method = RequestMethod.POST)
	public ModelAndView saveCurrencyConfiguration(Model model,
			@ModelAttribute CurrencyConfiguration currencyConfiguration, RedirectAttributes redirectAttributes) {
		try {
			if (currencyConfiguration.getId() != null || currencyConfiguration.getIds() != null) {
				if (currencyConfiguration.getCitizen().equalsIgnoreCase("RI")) {
					CurrencyConfiguration currencyConfiguration_ = currencyConfigurationDAO
							.findById(currencyConfiguration.getId());
					currencyConfiguration_.setCurrency(currencyConfiguration.getCurrency());
					currencyConfiguration_.setModifiedBy(getCurrentLoggedUserName());
					currencyConfiguration.setModifiedOn(DateService.getCurrentDateTime());

					currencyConfigurationDAO.update(currencyConfiguration_);
					model.addAttribute("currencyConfiguration", currencyConfiguration_);
					redirectAttributes = updateTransaction("Updated Successfully", redirectAttributes);
					return new ModelAndView("redirect:updateAMSuccess");
				} else {

					String ids[] = String.valueOf(currencyConfiguration.getIds()).split(",");
					String[] currencies = currencyConfiguration.getCurrency().split(",");
					for (int i = 0; i < ids.length; i++) {
						CurrencyConfiguration currencyConfiguration_ = currencyConfigurationDAO
								.findById(Long.parseLong(ids[i]));

						currencyConfiguration_.setCurrency(currencies[i]);
						currencyConfiguration_.setModifiedBy(getCurrentLoggedUserName());
						currencyConfiguration.setModifiedOn(DateService.getCurrentDateTime());
						currencyConfigurationDAO.update(currencyConfiguration_);
					}
					redirectAttributes = updateTransaction("Updated Successfully", redirectAttributes);
					return new ModelAndView("redirect:updateAMSuccess");
				}
			} else {

				if (currencyConfiguration.getCitizen().equalsIgnoreCase("RI")) {
					currencyConfiguration.setCreatedBy(getCurrentLoggedUserName());
					currencyConfiguration.setModifiedBy(getCurrentLoggedUserName());
					currencyConfiguration.setCreatedOn(DateService.getCurrentDateTime());
					currencyConfiguration.setModifiedOn(DateService.getCurrentDateTime());
					currencyConfigurationDAO.save(currencyConfiguration);
					model.addAttribute("currencyConfiguration", currencyConfiguration);
					redirectAttributes = updateTransaction("Saved Successfully", redirectAttributes);
					return new ModelAndView("redirect:updateAMSuccess");

				} else {
					String[] currencies = currencyConfiguration.getCurrency().split(",");
					List<CurrencyConfiguration> configurations = new ArrayList<>();
					for (int index = 0; index < currencies.length; index++) {
						currencyConfiguration.setId(null);
						currencyConfiguration.setAccountType(NRIAccountTypes.values()[index].getValue());
						currencyConfiguration.setCurrency(currencies[index]);
						currencyConfiguration.setCreatedBy(getCurrentLoggedUserName());
						currencyConfiguration.setModifiedBy(getCurrentLoggedUserName());
						currencyConfiguration.setCreatedOn(DateService.getCurrentDateTime());
						currencyConfiguration.setModifiedOn(DateService.getCurrentDateTime());
						currencyConfiguration = currencyConfigurationDAO.save(currencyConfiguration);
						configurations.add(currencyConfiguration);
					}
					model.addAttribute("currencyConfiguration", currencyConfiguration);
					redirectAttributes = updateTransaction("Saved Successfully", redirectAttributes);
					return new ModelAndView("redirect:updateAMSuccess");
				}
			}

		} catch (Exception e) {
			model.addAttribute("currencyConfiguration", currencyConfiguration);
			model.addAttribute("error", e.getMessage());
		}
		return new ModelAndView("currencyConfigurationVP", "model", model);
	}

	@RequestMapping(value = "/addBranch")
	public ModelAndView addBranch(ModelMap model, @ModelAttribute Branch branch) throws CustomException {
		List<Branch> allList = branchDAO.getAllBranches();
		model.put("allList", allList);
		model.put("branch", branch);
		return new ModelAndView("addBranch", "model", model);

	}

	@RequestMapping(value = "/addedBranch", method = RequestMethod.POST)
	public ModelAndView addedBranch(ModelMap model, @ModelAttribute Branch branch, RedirectAttributes attribute)
			throws CustomException {
		EndUser user = getCurrentLoggedUserDetails();
		String branchCodeAdded = branch.getBranchCode();
		String branchNameAdded = branch.getBranchName();
		List<Branch> allList = branchDAO.getAllBranches();
		model.put("allList", allList);
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

		else {

			model.put(Constants.ERROR, Constants.BranchAlreadyExist);
			return new ModelAndView("addBranch", "model", model);
		}
		attribute = updateTransaction("Saved Successfully", attribute);
		return new ModelAndView("redirect:updateAMSuccess");
	}

	@RequestMapping(value = "/roundOffOnMaturity")
	public ModelAndView addRoundOffOnMaturity(ModelMap model, @ModelAttribute RoundOff roundOff)
			throws CustomException {
		// model.put("roundOff",roundOff);
		List<RoundOff> roundOffList = roundOffDAO.getAllDetailsFromRoundOff();
		for (RoundOff roundOff1 : roundOffList) {
			model.put("roundOff", roundOff1);
		}
		return new ModelAndView("roundOffOnMaturity", "model", model);

	}

	@RequestMapping(value = "/addedRoundOff", method = RequestMethod.POST)
	public ModelAndView addedRoundOFF(ModelMap model, @ModelAttribute RoundOff roundOff, RedirectAttributes attribute)
			throws CustomException {

		Long count = roundOffDAO.getCountOfDecimalPlacesAndNearest(roundOff.getDecimalPlaces(),
				roundOff.getNearestHighestLowest());

		if (count == 0) {
			RoundOff addRoundOff = new RoundOff();
			addRoundOff.setDecimalPlaces(roundOff.getDecimalPlaces());
			addRoundOff.setNearestHighestLowest(roundOff.getNearestHighestLowest());
			roundOffDAO.save(addRoundOff);
		} else {

			model.put(Constants.ERROR, "This combination already exist ");
			return new ModelAndView("roundOffOnMaturity", "model", model);
		}
		attribute = updateTransaction("Saved Successfully", attribute);
		return new ModelAndView("redirect:updateAMSuccess");
	}

	@RequestMapping(value = "dataByProductId", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Set<Long> dataByProductId(String effectiveDate) throws Exception {
		Set<Long> longs = new HashSet<>();
		//Date effectiveDateParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(effectiveDate);
		/*List<WithdrawPenaltyMaster> list = withdrawPenaltyDAO.getByDates(effectiveDateParse);
		for (WithdrawPenaltyMaster withdrawPenaltyMaster : list) {
			longs.add(withdrawPenaltyMaster.getProductConfigurationId());
		}*/

		return longs;
	}

	@RequestMapping(value = "/stateHolidayConfigurationVP")
	public ModelAndView stateHolidayConfiguration(Model model,
			@ModelAttribute("stateHolidayConfigurationVP") HolidayConfiguration holidayConfiguration) {

		model.addAttribute("holidayConfiguration", holidayConfiguration);
		return new ModelAndView("stateHolidayConfigurationVP", "model", model);

	}

	@RequestMapping(value = "/stateHolidayConfiguration", method = RequestMethod.POST)
	public ModelAndView saveStateHolidayConfiguration(Model model,
			@ModelAttribute("holidayConfiguration") HolidayConfiguration holidayConfiguration) {
		try {

			HolidayConfiguration hConfig = new HolidayConfiguration();

			if (holidayConfiguration.getDates() != null) {
				hConfig.setCreatedOn(new Date());
				hConfig.setModifiedOn(new Date());
				String calenderDates = holidayConfiguration.getDates();
				String splitesDates[] = calenderDates.split(COMMA_SEPARATER);
				List<Date> newDatesInstance = new ArrayList<Date>();
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				for (String stringDate : splitesDates) {
					Date stringDateParseToDate = dateFormat.parse(stringDate);
					newDatesInstance.add(stringDateParseToDate);
				}
				if (holidayConfiguration != null) {
					hConfig.setDate(newDatesInstance);
					Collections.sort(newDatesInstance);
					List<HolidayConfiguration> isExistigYear = holidayConfigurationDAO
							.isPresent(holidayConfiguration.getYear(), holidayConfiguration.getState());
					if ((isExistigYear != null && isExistigYear.size() > 0)) {
						holidayConfigurationDAO.delete(holidayConfiguration.getYear(), holidayConfiguration.getState());

					}
					hConfig.setYear(holidayConfiguration.getYear());
					hConfig.setState(holidayConfiguration.getState());
					holidayConfigurationDAO.save(hConfig);

					model.addAttribute("sucess", "Updated sucessfully!");
				}
			} else {
				model.addAttribute("error", "Please select dates or date!");
			}

		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return new ModelAndView("stateHolidayConfigurationVP", "model", model);
	}

	@RequestMapping(value = "/branchHolidayConfiguration", produces = { "application/json" })
	@ResponseBody
	public String branchHolidayConfiguration(Model model,
			@ModelAttribute("holidayConfiguration") HolidayConfiguration holidayConfiguration) {
		HolidayConfiguration holidayConfigurationList = null;
		String json = "{}";
		try {
			holidayConfigurationList = holidayConfigurationDAO.getConfigurationByBranchAndState(
					holidayConfiguration.getYear(), holidayConfiguration.getState(),
					holidayConfiguration.getBranchCode());
			if (holidayConfigurationList != null) {
				model.addAttribute("holidayConfiguration", holidayConfigurationList);
				json = new Gson().toJson(holidayConfigurationList);
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return json;
	}

	@RequestMapping(value = "/modeOfPayment")
	public ModelAndView modeOfPayment(ModelMap model, @ModelAttribute ModeOfPayment modeOfPayment)
			throws CustomException {
		List<ModeOfPayment> allList = modeOfPaymentDAO.getAllModeOfPaymentDetails();
		model.put("allList", allList);
		model.put("modeOfPayment", modeOfPayment);
		return new ModelAndView("modeOfPayment", "model", model);

	}

	@RequestMapping(value = "/addModeOfPayment", method = RequestMethod.POST)
	public ModelAndView addmodeOfPayment(ModelMap model, @ModelAttribute ModeOfPayment modeOfPayment,
			RedirectAttributes attribute) throws CustomException {

		String paymentMode = modeOfPayment.getPaymentMode();
		// String output = paymentMode.substring(0, 1).toUpperCase() +
		// paymentMode.substring(1);
		List<ModeOfPayment> allList = modeOfPaymentDAO.getAllModeOfPaymentDetails();
		model.put("allList", allList);
		Long count = modeOfPaymentDAO.getCountOfPaymentMode(paymentMode);

		if (count == 0) {

			ModeOfPayment addModeOfPayment = new ModeOfPayment();
			addModeOfPayment.setPaymentMode(paymentMode);
			addModeOfPayment.setIsVisibleInBankSide(modeOfPayment.getIsVisibleInBankSide());
			addModeOfPayment.setIsVisibleInCustomerSide(modeOfPayment.getIsVisibleInCustomerSide());

			modeOfPaymentDAO.save(addModeOfPayment);

		}

		else {

			model.put(Constants.ERROR, Constants.PAYMENTMODEEXIST);
			return new ModelAndView("modeOfPayment", "model", model);
		}
		attribute = updateTransaction("Saved Successfully", attribute);
		return new ModelAndView("redirect:updateAMSuccess");
	}

	@RequestMapping(value = "/modeOfPaymentById", method = RequestMethod.GET)
	public ModelAndView modeOfPaymentById(Model model, @ModelAttribute ModeOfPayment modeOfPayment, Long id) {
		try {
			if (id != null) {
				modeOfPayment = modeOfPaymentDAO.getModeOfPaymentById(id);
				model.addAttribute("modeOfPayment", modeOfPayment);
			} else {

				return new ModelAndView("editModeOfPayment", "model", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("editModeOfPayment", "model", model);
	}

	@RequestMapping(value = "/editModeOfPayment", method = RequestMethod.POST)
	public ModelAndView editmodeOfPayment(ModelMap model, @ModelAttribute ModeOfPayment modeOfPayment,
			RedirectAttributes attributes) throws CustomException {

		try {
			if (modeOfPayment.getId() != null) {

				if (modeOfPaymentDAO.getModeOfPaymentExist(modeOfPayment.getPaymentMode(), modeOfPayment.getId())) {
					model.addAttribute("error", "Payment Mode already exist !");
					model.addAttribute("modeOfPayment", modeOfPayment);
					return new ModelAndView("editModeOfPayment", "model", model);
				}

				modeOfPaymentDAO.update(modeOfPayment);
				attributes = updateTransaction("Updated Successfully", attributes);
				return new ModelAndView("redirect:updateAMSuccess");
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());

		}
		return new ModelAndView("editModeOfPayment", "model", model);

	}

	@RequestMapping(value = "selectLoggedInRole", method = RequestMethod.GET)
	private ModelAndView selectLoggedInRole() {

		return new ModelAndView("redirect:/main/default");

	}

	@RequestMapping(value = "/ledgerMapping")
	public ModelAndView ledgerMapping(ModelMap model, @ModelAttribute LedgerEventMapping ledgerEventMapping) {
		List<EventOperations> eventOperationsList = eventOperationsDAO.getAllEventOperationsDetails();
		List<ModeOfPayment> modeOfPaymentList = modeOfPaymentDAO.getAllModeOfPaymentDetails();
		List<GLConfiguration> glConfigurationList = ledgerService.getGLConfigurations();
		model.put("glConfigurationList", glConfigurationList);
		System.out.println("--------" + glConfigurationList.toString());
		model.put("modeOfPaymentList", modeOfPaymentList);
		model.put("eventOperationsList", eventOperationsList);
		model.put("ledgerEventMapping", ledgerEventMapping);
		return new ModelAndView("ledgerMapping", "model", model);

	}

	@RequestMapping(value = "/saveLedgerMapping", method = RequestMethod.POST)
	public ModelAndView saveledgerMapping(Model model, @ModelAttribute LedgerEventMapping ledgerEventMapping,
			RedirectAttributes attributes) {
		try {
			System.out.println("LedgerEventMappingDetails:" + ledgerEventMapping);
//			LedgerEventMapping isExist = null;
//			try {
//				isExist = ledgerEventMappingDAO.getAllLedgerEventMappingByModeOfPayment(ledgerEventMapping.getEvent(),
//						ledgerEventMapping.getModeOfPayment(), ledgerEventMapping.getDebitGLAccount(),
//						ledgerEventMapping.getCreditGLAccount());
//			} catch (NoResultException nre) {
//			}
//
//			if (isExist != null) {
//				model.addAttribute("error", "This Combination already exist !");
//				return new ModelAndView("ledgerMapping", "model", model);
//			}

			String paymentMode[] = ledgerEventMapping.getModeOfPayment().split(",");
			String debitGLAccount[] = ledgerEventMapping.getDebitGLAccount().split(",");
			String creditGLAccount[] = ledgerEventMapping.getCreditGLAccount().split(",");
			for (int i = 0; i < paymentMode.length; i++) {

				LedgerEventMapping ledgerMapping = new LedgerEventMapping();
				String payment = paymentMode[i];
				ModeOfPayment modeOfPayment = modeOfPaymentDAO.getModeOfPayment(payment);
				ledgerMapping.setModeOfPaymentId(modeOfPayment.getId());
				// ledgerMapping.setCreditGLAccount(ledgerEventMapping.getCreditGLAccount());
				// ledgerMapping.setDebitGLAccount(ledgerEventMapping.getDebitGLAccount());
				ledgerMapping.setEvent(ledgerEventMapping.getEvent());
				ledgerMapping.setModeOfPayment(modeOfPayment.getPaymentMode());
				ledgerMapping.setDebitGLAccount(debitGLAccount[i]);
				ledgerMapping.setCreditGLAccount(creditGLAccount[i]);
				String glCode[] = debitGLAccount[i].split("-");
				String creditGLCode[] = creditGLAccount[i].split("-");
				ledgerMapping.setCreditGLCode(creditGLCode[0]);
				ledgerMapping.setDebitGLCode(glCode[0]);
				ledgerEventMappingDAO.save(ledgerMapping);
			}

			attributes = updateTransaction("Saved Successfully", attributes);
			return new ModelAndView("redirect:updateAMSuccess");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ledgerMapping", "model", model);
	}

	@RequestMapping(value = "/nriServiceCentres")
	public ModelAndView nriServiceCentres(ModelMap model, @ModelAttribute NRIServiceBranches nRIServiceBranches)
			throws CustomException {
		List<NRIServiceBranches> nRIServiceBranchesList = new ArrayList<NRIServiceBranches>();
		List<Branch> branchList = branchDAO.getAllBranches();
		for (Branch branch : branchList) {
			NRIServiceBranches branchId = null;
			try {
				branchId = nRIServiceBranchesDAO.getNRIServiceBranchesByBranchId(branch.getId());
			} catch (NoResultException nre) {
			}
			nRIServiceBranchesList.add(branchId);

		}
		model.put("nRIServiceBranchesList", nRIServiceBranchesList);
		model.put("nRIServiceBranches", nRIServiceBranches);
		model.put("branchList", branchList);
		return new ModelAndView("nriServiceCentres", "model", model);

	}

	@RequestMapping(value = "/saveNRIServiceBranches", method = RequestMethod.POST)
	public ModelAndView saveNRIServiceBranches(Model model, @ModelAttribute NRIServiceBranches nRIServiceBranches,
			@RequestParam String[] myArray, RedirectAttributes attributes) {
		try {

			String[] array = myArray;
			List<NRIServiceBranches> nriBranches = nRIServiceBranchesDAO.getAllNRIServiceBranches();
			for (int i = 0; i < array.length; i++) {
				NRIServiceBranches newNRIServiceBranches = new NRIServiceBranches();
				newNRIServiceBranches.setBranchCode(array[i]);
				Branch branch = branchDAO.getBranchByBranchCode(array[i]);
				newNRIServiceBranches.setBranchId(branch.getId());
				List<NRIServiceBranches> isExistig = nRIServiceBranchesDAO.getNRIServiceBranchesByBranchCode(array[i]);
				if ((isExistig != null)) {
					nRIServiceBranchesDAO.delete(array[i]);
				}
				//newNRIServiceBranches.setMarkAsDeleted(0);
				nRIServiceBranchesDAO.save(newNRIServiceBranches);
				List<NRIServiceBranches> isNotExistig = nRIServiceBranchesDAO
						.getNRIServiceBranchesNotInByBranchCode(array);
				for (NRIServiceBranches nriBranch : isNotExistig) {
					//nriBranch.setMarkAsDeleted(1);
					nRIServiceBranchesDAO.update(nriBranch);
				}
			}
			attributes = updateTransaction("Saved Successfully", attributes);
			return new ModelAndView("redirect:updateAMSuccess");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("nriServiceCentres", "model", model);
	}

}
