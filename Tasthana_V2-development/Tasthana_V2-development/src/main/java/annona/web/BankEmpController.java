package annona.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.theme.CookieThemeResolver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

import annona.dao.AccountDetailsDAO;
import annona.dao.BankPaymentDAO;
import annona.dao.BenificiaryDetailsDAO;
import annona.dao.BranchDAO;
import annona.dao.CustomerDAO;
import annona.dao.DTAACountryRatesDAO;
import annona.dao.DepositHolderDAO;
import annona.dao.DepositHolderWiseConsolidatedInterestDAO;
import annona.dao.DepositHolderWiseDistributionDAO;
import annona.dao.DepositModificationDAO;
import annona.dao.DepositRateDAO;
import annona.dao.DepositRatesDAO;
import annona.dao.DepositSummaryDAO;
import annona.dao.EndUserDAO;
import annona.dao.FDRatesDAO;
import annona.dao.FixedDepositDAO;
import annona.dao.FormSubmissionDAO;
import annona.dao.GstDAO;
import annona.dao.InterestDAO;
import annona.dao.JobSchedulerDAO;
import annona.dao.LogDetailsDAO;
import annona.dao.ModeOfPaymentDAO;
import annona.dao.NomineeDAO;
import annona.dao.OrganisationDAO;
import annona.dao.OverdraftIssueDAO;
import annona.dao.PaymentDAO;
import annona.dao.PaymentDistributionDAO;
import annona.dao.PayoffDAO;
import annona.dao.ProductConfigurationDAO;
import annona.dao.RatesDAO;
import annona.dao.SweepConfigurationDAO;
import annona.dao.TDSDAO;
import annona.dao.TransactionDAO;
import annona.dao.UnSuccessfulPayOffDetailsDAO;
import annona.dao.UnSuccessfulRecurringDAO;
import annona.dao.UploadDAO;
import annona.dao.UploadFileDAO;
import annona.dao.WithdrawDepositDAO;
import annona.domain.AccountDetails;
import annona.domain.BankPaid;
import annona.domain.BankPayment;
import annona.domain.BankPaymentDetails;
import annona.domain.BenificiaryDetails;
import annona.domain.Branch;
import annona.domain.Customer;
import annona.domain.CustomerCategory;
import annona.domain.DDCheque;
import annona.domain.DTAACountry;
import annona.domain.DTAACountryRates;
import annona.domain.Deposit;
import annona.domain.DepositHolder;
import annona.domain.DepositHolderNominees;
import annona.domain.DepositModification;
import annona.domain.DepositModificationMaster;
import annona.domain.DepositRates;
import annona.domain.DepositSummary;
import annona.domain.DepositSummaryHolderWise;
import annona.domain.DepositWiseCustomerTDS;
import annona.domain.Distribution;
import annona.domain.EndUser;
import annona.domain.FDRates;
import annona.domain.FormSubmission;
import annona.domain.Interest;
import annona.domain.JobScheduler;
import annona.domain.JointConsortiumNominees;
import annona.domain.Journal;

import annona.domain.LogDetails;
import annona.domain.LoginDate;
import annona.domain.ModeOfPayment;
import annona.domain.OverdraftIssue;
import annona.domain.OverdraftPayment;
import annona.domain.OverdraftWithdrawMaster;
import annona.domain.Payment;
import annona.domain.PayoffDetails;
import annona.domain.ProductConfiguration;
import annona.domain.RatePeriods;
import annona.domain.Rates;
import annona.domain.SweepConfiguration;
import annona.domain.SweepInFacilityForCustomer;
import annona.domain.TDS;
import annona.domain.Transaction;
import annona.domain.UnSuccessfulPayOff;
import annona.domain.UploadFile;
import annona.domain.UploadedFile;
import annona.domain.WithdrawDeposit;
import annona.exception.CustomException;
import annona.exception.EndUserNotFoundException;
import annona.form.AccountDetailsForm;
import annona.form.AddCountryWiseTaxRateDTAAForm;
import annona.form.AddRateForm;
import annona.form.AutoDepositForm;
import annona.form.BankConfigurationForm;
import annona.form.BankPaymentForm;
import annona.form.CustomerCategoryForm;
import annona.form.CustomerDetailsParser;
import annona.form.CustomerForm;
import annona.form.DepositForm;
import annona.form.DepositHolderForm;
import annona.form.DepositRatesForm;
import annona.form.DepositsList;
import annona.form.EndUserForm;
import annona.form.FDRatesForm;
import annona.form.FileForm;
import annona.form.FixedDepositForm;
import annona.form.HolderForm;
import annona.form.InterestCalculationForm;
import annona.form.JointAccount;
import annona.form.LedgerReportForm;
import annona.form.LoginForm;
import annona.form.OverdraftForm;
import annona.form.PayOfForm;
import annona.form.RatesForm;
import annona.form.ReportForm;
import annona.form.ScheduleTaskForm;
import annona.form.SweepConfigurationForm;
import annona.form.TDSForm;
import annona.form.UploadFileForm;
import annona.form.UploadedFileForm;
import annona.form.WithdrawForm;
import annona.scheduler.NotificationsScheduler;
import annona.services.CalculationService;
import annona.services.CustomUserDetailsService;
import annona.services.DateService;
import annona.services.DepositService;
import annona.services.FDService;
import annona.services.ImageService;
import annona.services.LedgerService;
import annona.services.MonthEndProcessorService;
import annona.services.UploadService;
import annona.utility.Constants;
import annona.utility.Event;

@Controller
@RequestMapping("/bnkEmp")
public class BankEmpController {
	@Autowired
	DepositHolderWiseConsolidatedInterestDAO depositHolderWiseConsolidatedInterestDAO;
	@Autowired
	MailSender mailSender;

	@Autowired
	PaymentDistributionDAO paymentDistributionDAO;

	@Autowired
	MonthEndProcessorService monthEndProcessorService;

	@Autowired
	EndUserDAO endUserDAOImpl;

	@Autowired
	EndUserForm endUserForm;

	@Autowired
	TransactionDAO transactionDAO;

	@Autowired
	CustomerForm customerForm;

	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	RatesForm ratesForm;

	@Autowired
	RatesDAO ratesDAO;

	@Autowired
	FixedDepositForm fixedDepositForm;

	@Autowired
	FixedDepositDAO fixedDepositDAO;

	@Autowired
	FDService fdService;

	@Autowired
	CalculationService calculationService;

	@Autowired
	FDRatesDAO fdRatesDAO;

	@Autowired
	FDRatesForm fdRatesForm;

	@Autowired
	PaymentDAO paymentDAO;

	@Autowired
	DepositRatesDAO depositRatesDAO;

	@Autowired
	AccountDetailsDAO accountDetailsDAO;

	@Autowired
	OrganisationDAO organisationDAO;

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	DepositHolderDAO depositHolderDAO;

	@Autowired
	NomineeDAO nomineeDAO;

	@Autowired
	DepositModificationDAO depositModificationDAO;

	@Autowired
	DepositForm depositForm;

	@Autowired
	BankConfigurationForm bankConfigurationForm;

	@Autowired
	DepositService depositService;

	@Autowired
	NotificationsScheduler notificationScheduler;

	@Autowired
	GstDAO gstDAO;

	@Autowired
	InterestDAO interestDAO;

	@Autowired
	TDSDAO tdsDAO;

	@Autowired
	UploadFileDAO uploadFileDAO;

	@Autowired
	UploadFileForm uploadFileForm;

	@Autowired
	FormSubmissionDAO formSubmissionDao;

	@Autowired
	LogDetailsDAO logDetailsDAO;

	@Autowired
	DepositRateDAO depositRateDAO;

	@Autowired
	UploadService uploadService;

	@Autowired
	AccountDetailsForm accountDetailsForm;

	@Autowired
	ServletContext context;

	@Autowired
	DepositHolderWiseDistributionDAO depositHolderWiseDistributionDAO;

	@Autowired
	BankPaymentDAO bankPaymentDAO;

	@Autowired
	DepositsList depositsList;

	@Autowired
	BenificiaryDetailsDAO benificiaryDetailsDAO;

	@Autowired
	PayoffDAO payOffDAO;

	@Autowired
	PayOfForm payOfForm;

	@Autowired
	UnSuccessfulPayOffDetailsDAO unSuccessfulPayOffDetailsDAO;

	@Autowired
	WithdrawDepositDAO withdrawDepositDAO;

	@Autowired
	JobSchedulerDAO jobSchedulerDAO;

	@Autowired
	ScheduleTaskForm scheduleTaskForm;

	@Autowired
	NotificationsScheduler notificationsScheduler;

	@Autowired
	UploadDAO uploadDAO;

	@Autowired
	DTAACountryRatesDAO dtaaCountryRatesDAO;

	@Autowired
	AddRateForm addRateForm;

	@Autowired
	SweepConfigurationForm sweepConfigurationForm;

	@Autowired
	SweepConfigurationDAO sweepConfigurationDAO;

	@Autowired
	UnSuccessfulRecurringDAO unSuccessfulRecurringDAO;

	@Autowired
	DepositSummaryDAO depositSummaryDAO;

	@Autowired
	ProductConfigurationDAO productConfigurationDAO;

	@Autowired
	BranchDAO branchDAO;

	@Autowired
	LedgerService ledgerService;
	
	@Autowired
	OverdraftIssueDAO overdraftIssueDAO;
	
	@Autowired
	ModeOfPaymentDAO modeOfPaymentDAO;

	CookieThemeResolver themeResolver = new CookieThemeResolver();

	protected Logger log = Logger.getLogger(BankEmpController.class.getName());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	private String getCurrentLoggedUserName() {

		return SecurityContextHolder.getContext().getAuthentication().getName();

	}

	public ServletContext getServletContext() {
		return context;
	}

	/**
	 * Method to get current user details
	 */
	private EndUser getCurrentLoggedUserDetails() {

		EndUser endUser = endUserDAOImpl.findByUsername(getCurrentLoggedUserName()).getSingleResult();

		return endUser;

	}

	@ModelAttribute("requestCurrentUser")
	public EndUser getUserDetails(ModelMap model) throws CustomException {
		EndUser user = getCurrentLoggedUserDetails();
		if (user.getImageName() != null) {
			String type = ImageService.getImageType(user.getImageName());

			String url = "data:image/" + type + ";base64," + Base64.encodeBase64String(user.getImage());
			user.setImageName(url);
		}

		model.put("user", user);
		return user;
	}

	public Customer getCustomerDetails(Long id) {
		Customer customer = customerDAO.getById(id);
		return customer;
	}

	@RequestMapping(value = "/updateTransaction", method = RequestMethod.GET)
	public RedirectAttributes updateTransaction(String status, RedirectAttributes attribute) {

		Date curDate = DateService.getCurrentDateTime();
		attribute.addFlashAttribute(Constants.TRANSACTIONID, fdService.generateRandomString());
		attribute.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
		attribute.addFlashAttribute(Constants.TRANSACTIONSTATUS, status);

		return attribute;
	}

	@RequestMapping(value = "/editBankProfile", method = RequestMethod.GET)
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

		return new ModelAndView("editBankProfile", "model", model);

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
		return "redirect:editBankProfile?id=" + endUserForm.getId();
	}

	@RequestMapping(value = "/confirmEditBankProfile", method = RequestMethod.POST)
	public ModelAndView confirmEditAdminProfile(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {

		model.put("endUserForm", endUserForm);

		return new ModelAndView("confirmEditBankProfile", "model", model);

	}

	@RequestMapping(value = "/updateBankDetails", method = RequestMethod.POST)
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

		attributes = updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:bankFDSaved");

	}

	@RequestMapping(value = "/editBankPWD", method = RequestMethod.GET)
	public ModelAndView editAdminPWD(ModelMap model, @RequestParam("id") Long id, RedirectAttributes attributes)
			throws CustomException {

		EndUser userProfile = endUserDAOImpl.findId(id);

		endUserForm.setId(userProfile.getId());

		endUserForm.setTransactionId(userProfile.getTransactionId());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("editBankPWD", "model", model);

	}

	@RequestMapping(value = "/updateEditBankPWD", method = RequestMethod.POST)
	public ModelAndView updateEditAdminPWD(ModelMap model, @ModelAttribute EndUserForm endUserForm,
			BindingResult result, RedirectAttributes attributes) throws CustomException {

		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());

		endUser.setId(endUserForm.getId());

		endUser.setPassword(endUserForm.getNewPassword());
		endUser.setTransactionId(endUserForm.getTransactionId());

		endUserDAOImpl.update(endUser);

		attributes = updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:bankFDSaved");

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

		return "redirect:bankEmp";
	}

	@RequestMapping(value = "/getLocaleLang", method = RequestMethod.GET)
	public String getLocaleLang(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws CustomException {
		log.info("Received request for locale change");
		EndUser enduser = endUserDAOImpl.findByUsername(getCurrentLoggedUserName()).getSingleResult();
		LocaleResolver localeResolver = new CookieLocaleResolver();
		Locale locale = new Locale(request.getParameter("lang"));
		localeResolver.setLocale(request, response, locale);
		enduser.setPrefferedLanguage(request.getParameter("lang"));

		endUserDAOImpl.update(enduser);

		return "redirect:bankEmp";
	}

	@RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
	public ModelAndView addCustomer(ModelMap model, @ModelAttribute CustomerForm customerForm) throws CustomException {
		List<CustomerCategory> list = customerDAO.findAll();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
		if (setCategory.size() > 0) {
			model.put("setCategory", setCategory);
			model.put("customerForm", customerForm);

		}
		model.put("customerForm", customerForm);

		return new ModelAndView("addCustomer", "model", model);

	}

	@RequestMapping(value = "/customerConfirm", method = RequestMethod.POST)
	public ModelAndView customerConfirm(@ModelAttribute CustomerForm customerForm, ModelMap model,
			RedirectAttributes attributes) throws ParseException, CustomException {
		/*
		 * SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy"); String date =
		 * smt.format(customerForm.getDateOfBirth()); Date date1 = new
		 * SimpleDateFormat("dd/MM/yyyy").parse(date); String todayDate =
		 * smt.format(DateService.getCurrentDateTime()); Date date2 = new
		 * SimpleDateFormat("dd/MM/yyyy").parse(todayDate); int daysDiff =
		 * DateService.getDaysBetweenTwoDates(date1, date2); if (daysDiff <= 0) {
		 * model.put("error",
		 * "Selected Date Of Birth is future date please insert correct date"); return
		 * new ModelAndView("addCustomer", "model", model);
		 * 
		 * }
		 */

		model.put("customerForm", customerForm);

		return new ModelAndView("customerConfirm", "model", model);

	}

	@RequestMapping(value = "/savedDetails", method = RequestMethod.POST)
	public String customerSave(@ModelAttribute CustomerForm customerForm, ModelMap model, RedirectAttributes attribute)
			throws CustomException {

		Customer customer = new Customer();
		customer.setCategory(customerForm.getCategory());
		customer.setCustomerName(customerForm.getCustomerName());
		customer.setCustomerID(customerForm.getCustomerID());
		customer.setUserName(customerForm.getUserName());

		customer.setCountry(customerForm.getCountry());
		customer.setState(customerForm.getState());
		customer.setCity(customerForm.getCity());
		customer.setAddress(customerForm.getAddress());
		customer.setPincode(customerForm.getPincode());
		customer.setContactNum(customerForm.getContactNum());
		customer.setAltContactNum(customerForm.getAltContactNum());
		customer.setEmail(customerForm.getEmail());
		customer.setAltEmail(customerForm.getAltEmail());

		customer.setGender(customerForm.getGender());
		customer.setAge(customerForm.getAge());

		customer.setDateOfBirth(customerForm.getDateOfBirth());
		customer.setNotificationStatus(Constants.PENDING);
		customer.setStatus(Constants.PENDING);
		customer.setDateOfBirth(customerForm.getDateOfBirth());
		customer.setCitizen(customerForm.getCitizen());
		customer.setNriAccountType(customerForm.getNriAccountType());
		customer.setTransactionId(customerForm.getTransactionId());
		customer.setAadhar(customerForm.getAadhar());
		customer.setPan(customerForm.getPan().toUpperCase());

		if (customerForm.getGuardianName() != null) {

			customer.setGuardianName(customerForm.getGuardianName());
			customer.setGuardianAge(customerForm.getGuardianAge());
			customer.setGuardianRelationShip(customerForm.getGuardianRelationShip());
			customer.setGuardianAddress(customerForm.getGuardianAddress());
			customer.setGuardianPan(
					customerForm.getGuardianPan() == null ? null : customerForm.getGuardianPan().toUpperCase());
			customer.setGuardianAadhar(customerForm.getGuardianAadhar());
		}

		customerDAO.insertCustomer(customer);
		if (customerForm.getAccountType() != null && customerForm.getAccountNo() != null
				&& customerForm.getAccountBalance() != null) {

			String[] accTypeList = customerForm.getAccountType().split(",");
			String[] accNoList = customerForm.getAccountNo().split(",");
			String[] accBalList = customerForm.getAccountBalance().toString().split(",");
			String[] minimumBalList = customerForm.getMinimumBalance().toString().split(",");

			int saving = 0;
			int current = 0;

			for (int i = 0; i < accTypeList.length; i++) {

				if (accTypeList[i].equals("Savings")) {
					saving = saving + 1;
					if (saving > 1) {
						attribute.addFlashAttribute(Constants.ERROR, "Multiple Saving Account Error");
						return "redirect:addCustomer";
					}
				}

				if (accTypeList[i].equals("Current")) {
					current = current + 1;
					if (current > 1) {
						attribute.addFlashAttribute(Constants.ERROR, "Multiple Current Account Error");
						return "redirect:addCustomer";
					}
				}
			}

			for (int i = 0; i < accTypeList.length; i++) {
				if (accNoList[i] == null || accNoList[i] == "")
					continue;

				AccountDetails accountDetails = new AccountDetails();
				accountDetails.setAccountBalance(Double.valueOf(accBalList[i]));
				accountDetails.setAccountNo(accNoList[i]);
				accountDetails.setAccountType(accTypeList[i]);
				if (minimumBalList[i] != null && minimumBalList[i] != "")
					accountDetails.setMinimumBalance(Double.valueOf(minimumBalList[i]));
				accountDetails.setCustomerID(customer.getId());
				accountDetails.setIsActive(1);

				accountDetailsDAO.insertAccountDetails(accountDetails);
/*
				// Accounting Entry
				// -------------------------------------------------------------------------------------------
				if (accountDetails.getAccountType().equalsIgnoreCase("Savings")) {
					
					
					ledgerService.insertJournalLedger(null, customer.getId(), DateService.getCurrentDate(), null,
							"Cash Account", accNoList[i], "Saving Account", "Opening Balance of Saving Account",
							Double.valueOf(accBalList[i]), "Cash", Double.valueOf(accBalList[i]));
				} else if (accountDetails.getAccountType().equalsIgnoreCase("Current")) {
					ledgerService.insertJournalLedger(null, customer.getId(), DateService.getCurrentDate(), null,
							"Cash Account", accNoList[i], "Current Account", "Opening Balance of Current Account",
							Double.valueOf(accBalList[i]), "Cash", Double.valueOf(accBalList[i]));
				} else if (accountDetails.getAccountType().equalsIgnoreCase("Deposit")) {
					ledgerService.insertJournalLedger(null, customer.getId(), DateService.getCurrentDate(), null,
							"Cash Account", accNoList[i], "Deposit Account", "Opening Balance of Deposit Account",
							Double.valueOf(accBalList[i]), "Cash", Double.valueOf(accBalList[i]));
				}
				// -------------------------------------------------------------------------------------------
*/			}
		}

		Transaction trans = new Transaction();

		String transactionId = fdService.generateRandomString();
		trans.setTransactionId(transactionId);
		trans.setTransactionType(Constants.CUSTOMERADDED);
		transactionDAO.insertTransaction(trans);

		attribute = updateTransaction("Saved Successfully", attribute);
		return "redirect:bankFDSaved";

	}

	@RequestMapping(value = "/setRate", method = RequestMethod.GET)
	public ModelAndView setRate(@RequestParam Long id, ModelMap model) throws CustomException {
		Rates rates = ratesDAO.findById(id);

		ratesForm.setService(rates.getService());
		ratesForm.setSes(rates.getSes());
		ratesForm.setTds(rates.getTds());
		ratesForm.setTransactionId(rates.getTransactionId());
		ratesForm.setType(rates.getType());
		ratesForm.setFdFixedPercent(rates.getFdFixedPercent());
		model.put("ratesForm", ratesForm);

		return new ModelAndView("setRate", "model", model);
	}

	@RequestMapping(value = "/confirmRates", method = RequestMethod.POST)
	public ModelAndView confirmRates(ModelMap model, @ModelAttribute RatesForm ratesForm) throws CustomException {
		model.put("ratesForm", ratesForm);

		return new ModelAndView("confirmRates", "model", model);
	}

	@RequestMapping(value = "/saveRate", method = RequestMethod.POST)
	public String saveRate(ModelMap model, @ModelAttribute RatesForm ratesForm, RedirectAttributes attributes)
			throws CustomException {
		Rates rates = ratesDAO.findByCategory(ratesForm.getType()).getSingleResult();
		Transaction transaction = new Transaction();

		rates.setTds(ratesForm.getTds());
		rates.setSes(ratesForm.getSes());
		rates.setService(ratesForm.getService());
		rates.setTransactionId(ratesForm.getTransactionId());
		rates.setFdFixedPercent(ratesForm.getFdFixedPercent());

		Date updateDate = DateService.getCurrentDateTime();

		rates.setUpdateDate(updateDate);

		transaction.setTransactionId(ratesForm.getTransactionId());
		transaction.setTransactionStatus(Constants.TRSTATUSFORRATE);
		transaction.setTransactionType(Constants.TRTYPEFORRATE);
		transaction.setAccountDate(updateDate);

		ratesDAO.update(rates);

		transactionDAO.insertTransaction(transaction);

		attributes = updateTransaction("Saved Successfully", attributes);
		return "redirect:bankFDSaved";

	}

	@RequestMapping(value = "/addRate", method = RequestMethod.GET)
	public ModelAndView addRates(ModelMap model) throws CustomException {
		model.put("ratesForm", ratesForm);

		List<RatePeriods> ratesPeriod = ratesDAO.getRateDurationsByDepositClaasification(Constants.fixedDeposit);
		model.put("ratesPeriod", ratesPeriod);

		return new ModelAndView("addRate", "model", model);
	}

	@RequestMapping(value = "/confirmAddRates", method = RequestMethod.POST)
	public ModelAndView confirmAddRates(ModelMap model, @ModelAttribute RatesForm ratesForm,
			RedirectAttributes attributes) throws CustomException {

		Collection<Rates> rates = ratesDAO.findByCategory(ratesForm.getType()).getResultList();

		if (rates.size() > 0) {
			attributes.addFlashAttribute(Constants.SUCCESS, Constants.DUPLICATECATEGORY);
			return new ModelAndView("redirect:addRate");
		} else {
			model.put("ratesForm", ratesForm);
			return new ModelAndView("confirmAddRates", "model", model);
		}
	}

	@RequestMapping(value = "/saveAddRates", method = RequestMethod.POST)
	public String saveAddRates(ModelMap model, @ModelAttribute RatesForm ratesForm, RedirectAttributes attribute)
			throws CustomException {

		Rates rates = new Rates();
		rates.setType(ratesForm.getType());
		rates.setTds(ratesForm.getTds());
		rates.setSes(ratesForm.getSes());
		rates.setService(ratesForm.getService());
		rates.setAssignDate(DateService.getCurrentDateTime());
		rates.setFdFixedPercent(ratesForm.getFdFixedPercent());

		ratesDAO.createRate(rates);

		attribute = updateTransaction("Saved Successfully", attribute);
		return "redirect:bankFDSaved";

	}

	@RequestMapping(value = "/updateRates", method = RequestMethod.GET)
	public ModelAndView getRates(ModelMap model) throws CustomException {
		Collection<Rates> rates = ratesDAO.findAllRates();

		model.put("rates", rates);

		return new ModelAndView("listOfRates", "model", model);
	}

	// Remove
	@RequestMapping(value = "/applyOnlineFD", method = RequestMethod.GET)
	public ModelAndView applyOnlineFD(ModelMap model, String depositType) throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);

		if (depositType.equals("joint")) {

			return new ModelAndView("applyFDJoint", "model", model);
		} else {
			return new ModelAndView("applyFD", "model", model);
		}
	}

	/**** single deposit creation *******/

	@RequestMapping(value = "/createSingleDeposit", method = RequestMethod.POST)
	public ModelAndView createSingleDeposit(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String val, @RequestParam String customerDetails, @RequestParam String productId) throws CustomException {

		Customer customer = customerDAO.getById(fixedDepositForm.getId());

		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(fixedDepositForm.getId());
		List<ModeOfPayment> paymentList = modeOfPaymentDAO.getAllModeOfPaymentEmployee();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		fixedDepositForm.setCustomerName(customer.getCustomerName());
		fixedDepositForm.setCitizen(customer.getCitizen());
		fixedDepositForm.setAccountList(accountList);
		model.put("customer", customer);
		model.put("paymentMode", paymentList);

		model.put("fixedDepositForm", fixedDepositForm);
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		Long pId = Long.parseLong(productId);
		model.put("todaysDate", date);

		List<Branch> branch = branchDAO.getAllBranches();
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(pId);
		model.put("productConfiguration", productConfiguration);

		model.put("branch", branch);
		// model.put("branchCode",list);
		
		model.put("nriAccountType", customer.getNriAccountType());
		model.put("val", val);
		model.put("customerDetails", customerDetails);

		return new ModelAndView("createSingleDeposit", "model", model);

	}
	
	@RequestMapping(value = "/createSingleDepositFromCommonLedger", method = RequestMethod.GET)
	public ModelAndView createSingleDepositFromCommonLedger(ModelMap model, Long customerId,
			@RequestParam Long productId, String debitAmount, String creditAmount) throws CustomException {

		Customer customer = customerDAO.getById(customerId);
		String depositAmount = null;
		if(debitAmount!=null && debitAmount!="")
			depositAmount = debitAmount;
		else
			depositAmount = creditAmount;
		

		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(customer.getId());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		fixedDepositForm.setCustomerName(customer.getCustomerName());
		fixedDepositForm.setCitizen(customer.getCitizen());
		fixedDepositForm.setAccountList(accountList);
		model.put("customer", customer);
		fixedDepositForm.setFdAmount(Double.valueOf(depositAmount));
		model.put("fixedDepositForm", fixedDepositForm);
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		model.put("todaysDate", date);

		List<Branch> branch = branchDAO.getAllBranches();
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(productId);
		model.put("productConfiguration", productConfiguration);

		model.put("branch", branch);
		// model.put("branchCode",list);
		model.put("cashPayment", 1);
		model.put("ddPayment", 1);
		model.put("chequePayment", 1);
		model.put("netBanking", 0);
		model.put("nriAccountType", customer.getNriAccountType());
		model.put("val", customer.getNriAccountType());
		model.put("customerDetails", "");

		return new ModelAndView("createSingleDeposit", "model", model);

	}

	@RequestMapping(value = "/panCardValidation", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody Integer panCardValidation(ModelMap model, @RequestParam Long gaurdianAadhar,
			@RequestParam Long nomineeAadhar, @RequestParam String nomineePan, @RequestParam String gaurdianPan,
			@RequestParam String customerName, @RequestParam String jointName, @RequestParam Long gaurdianAadharSec,
			@RequestParam Long nomineeAadharSec, @RequestParam String nomineePanSec,
			@RequestParam String gaurdianPanSec) throws CustomException {

		String userName = customerName;
		String secCust = jointName;

		String nomineePanSecondryCustomer = nomineePanSec;
		String guardianPanSecondryCustomer = gaurdianPanSec;

		Long nomineeAadharSecondryCustomer = nomineeAadharSec;
		Long guardianAadharSecondryCustomer = gaurdianAadharSec;

		String[] splitgetNomineeByPanCard = nomineePan.split(",");
		String[] splitgetgaurdianPanCard = gaurdianPan.split(",");
		String nomineePanCard = splitgetNomineeByPanCard[0];
		String gaurdianPanCard = splitgetgaurdianPanCard[0];
		Long getNomineeByPanCard = 0l;
		Long getNomineeByAadharCard = 0l;
		Long getgaurdianByPanCard = 0l;
		Long getgaurdianByAadharCard = 0l;

		Long getSecondryNomineeByPanCard = 0l;
		Long getSecondryNomineeByAadharCard = 0l;
		Long getSecondryGaurdianByPanCard = 0l;
		Long getSecondryGaurdianByAadharCard = 0l;

		Customer getCustomerByUserName = customerDAO.getCustomerByUserName(userName.trim());
		String custName = getCustomerByUserName.getUserName();

		Customer getSecondryUserName = customerDAO.getCustomerByUserName(secCust.trim());
		String secCustName = getSecondryUserName.getUserName();

		if (nomineePanCard != null && nomineePanCard.trim() != "")
			getNomineeByPanCard = customerDAO.getPanCardForValidation(nomineePanCard.trim());

		if (nomineeAadhar != null)
			getNomineeByAadharCard = customerDAO.getAadharCardForValidation(nomineeAadhar);

		if (gaurdianPanCard != null && gaurdianPanCard.trim() != "")
			getgaurdianByPanCard = customerDAO.getPanCardForValidation(gaurdianPanCard.trim());

		if (gaurdianAadhar != null)
			getgaurdianByAadharCard = customerDAO.getAadharCardForValidation(gaurdianAadhar);

		if (nomineePanSecondryCustomer != null && nomineePanSecondryCustomer.trim() != "")
			getSecondryNomineeByPanCard = customerDAO.getPanCardForValidation(nomineePanSecondryCustomer.trim());

		if (nomineeAadharSecondryCustomer != null)
			getSecondryNomineeByAadharCard = customerDAO.getAadharCardForValidation(nomineeAadharSecondryCustomer);

		if (guardianPanSecondryCustomer != null && guardianPanSecondryCustomer.trim() != "")
			getSecondryGaurdianByPanCard = customerDAO.getPanCardForValidation(guardianPanSecondryCustomer.trim());

		if (guardianAadharSecondryCustomer != null)
			getSecondryGaurdianByAadharCard = customerDAO.getAadharCardForValidation(guardianAadharSecondryCustomer);

		Integer result = 0;
		if (getNomineeByPanCard > 0) {
			if (userName.equals(custName)) {
				result = 1;
			}
			return result;
		}
		if (getNomineeByAadharCard > 0) {
			if (userName.equals(custName)) {
				result = 2;
			}
			return result;
		}
		if (getgaurdianByPanCard > 0) {
			if (userName.equals(custName)) {
				result = 3;
			}
			return result;
		}
		if (getgaurdianByAadharCard > 0) {
			if (userName.equals(custName)) {
				result = 4;
			}
			return result;
		}

		if (getSecondryNomineeByPanCard > 0) {
			if (secCust.equals(secCustName)) {
				result = 5;
			}
			return result;
		}
		if (getSecondryNomineeByAadharCard > 0) {
			if (secCust.equals(secCustName)) {
				result = 6;
			}
			return result;
		}
		if (getSecondryGaurdianByPanCard > 0) {
			if (secCust.equals(secCustName)) {
				result = 7;
			}
			return result;
		}
		if (getSecondryGaurdianByAadharCard > 0) {
			if (secCust.equals(secCustName)) {
				result = 8;
			}
			return result;
		}
		return result;
	}

	/****** for single account *****/
	@Transactional
	@RequestMapping(value = "/saveDeposit", method = RequestMethod.POST)
	public String saveDeposit(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute FixedDepositForm fixedDepositForm, HttpServletRequest request) throws CustomException {

		Deposit deposit = new Deposit();
		Customer customer = customerDAO.getById(fixedDepositForm.getId());
		Date date = DateService.getCurrentDateTime();
		String transactionId = fdService.generateRandomString();
		
		// int days = fixedDepositForm.getDaysValue() != null ?
		// fixedDepositForm.getDaysValue() : 0;
		if (fixedDepositForm.getCitizen().equals(Constants.NRI)) {
			deposit.setPrimaryNRIAccountType(fixedDepositForm.getNriAccountType());

		}

		deposit.setPrimaryCitizen(fixedDepositForm.getCitizen());
		deposit.setDepositArea(fixedDepositForm.getDepositArea());
		deposit.setPrimaryCustomerCategory(fixedDepositForm.getCategory());
		deposit.setAccountNumber(fdService.generateRandomString());
		deposit.setDepositAmount(fixedDepositForm.getFdAmount());
		deposit.setDepositAccountType(Constants.SINGLE);
		deposit.setDepositType(fixedDepositForm.getDepositType());
		deposit.setDepositCurrency(fixedDepositForm.getCurrency());
		deposit.setFlexiRate("Yes");
		deposit.setInterestRate(fixedDepositForm.getFdCreditAmount());
		deposit.setModifiedInterestRate(fixedDepositForm.getFdCreditAmount());
		deposit.setLinkedAccountNumber(fixedDepositForm.getAccountNo());
		deposit.setProductConfigurationId(fixedDepositForm.getProductConfigurationId());
		// Double value = FDService.round(fixedDepositForm.getEstimatePayOffAmount(),
		// 2);

		deposit.setCurrentBalance(Double.valueOf(deposit.getDepositAmount()));
		deposit.setPaymentMode(fixedDepositForm.getDepositForm().getPaymentMode());
		deposit.setDueDate(fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));
		deposit.setPaymentType(fixedDepositForm.getPaymentType());
		deposit.setPayOffInterestType(fixedDepositForm.getPayOffInterestType());
		deposit.setStatus(Constants.OPEN);
		deposit = fdService.getMaturityAndTenureInformation(deposit, fixedDepositForm);
		deposit.setFlexiRate("Yes");
		deposit.setCreatedDate(date);
		deposit.setModifiedDate(date);
		deposit.setApprovalStatus(Constants.PENDING);
		deposit.setDepositClassification(fixedDepositForm.getDepositClassification());
		deposit.setTaxSavingDeposit(fixedDepositForm.getTaxSavingDeposit());
		deposit.setDeductionDay(fixedDepositForm.getDeductionDay());
		/* / / PAY OFF DATE CALCULATION / / */
		if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {
			deposit.setPayOffDueDate(fixedDepositForm.getPayoffDate());
		}

		EndUser user = getCurrentLoggedUserDetails();

		deposit.setTransactionId(transactionId);
		deposit.setCreatedBy(user.getUserName());

		if (deposit.getPaymentMode().equalsIgnoreCase("DD") || deposit.getPaymentMode().equalsIgnoreCase("Cheque")) {
			deposit.setClearanceStatus(Constants.WAITINGFORCLEARANCE);
		}

		deposit.setMaturityInstruction(fixedDepositForm.getMaturityInstruction());
		deposit.setMaturityDate(fixedDepositForm.getMaturityDate());
		deposit.setNewMaturityDate(fixedDepositForm.getMaturityDate());
		deposit.setBranchCode(fixedDepositForm.getBranchCode());
		deposit.setProductConfigurationId(fixedDepositForm.getProductConfigurationId());
		deposit.setIsMaturityDisbrsmntInLinkedAccount(fixedDepositForm.getIsMaturityDisbrsmntInLinkedAccount());
		Deposit depositSaves = fixedDepositDAO.saveFD(deposit);

		List<DepositHolder> depositHolderList = new ArrayList<>();

		DepositHolder depositHolder = new DepositHolder();
		depositHolder.setContribution(100f);
		depositHolder.setCustomerId(Long.valueOf(fixedDepositForm.getId()));
		depositHolder.setDepositHolderStatus(Constants.PRIMARY);
		depositHolder.setDepositHolderCategory(fixedDepositForm.getCategory());
		depositHolder.setDepositId(depositSaves.getId());
		depositHolder.setCitizen(customer.getCitizen());
		
		
		depositHolder.setIsMaturityDisbrsmntInSameBank(fixedDepositForm.getIsMaturityDisbrsmntInSameBank());
		
		depositHolder.setMaturityDisbrsmntAccHolderName(fixedDepositForm.getMaturityDisbrsmntAccHolderName());
		
		depositHolder.setMaturityDisbrsmntAccNumber(fixedDepositForm.getMaturityDisbrsmntAccNumber());
		
		depositHolder.setMaturityDisbrsmntTransferType(fixedDepositForm.getMaturityDisbrsmntTransferType());
		
		depositHolder.setMaturityDisbrsmntBankName(fixedDepositForm.getMaturityDisbrsmntBankName());

		depositHolder.setMaturityDisbrsmntBankIFSCCode(fixedDepositForm.getMaturityDisbrsmntBankIFSCCode());
		
		
		
		if (customer.getCitizen().equals(Constants.NRI))
			depositHolder.setNriAccountType(customer.getNriAccountType());

		if (fixedDepositForm.getPayOffInterestType() != null) {
			depositHolder.setNameOnBankAccount(fixedDepositForm.getOtherName());
			depositHolder.setAccountNumber(fixedDepositForm.getOtherAccount().toString());
			depositHolder.setPayOffAccountType(fixedDepositForm.getFdPayOffAccount());
			if (fixedDepositForm.getFdPayOffAccount().equals("Other")) {
				depositHolder.setTransferType(fixedDepositForm.getOtherPayTransfer());
				depositHolder.setBankName(fixedDepositForm.getOtherBank());
				depositHolder.setIfscCode(fixedDepositForm.getOtherIFSC());
			}

			depositHolder.setInterestType(fixedDepositForm.getInterstPayType());
			if (fixedDepositForm.getInterstPayType().equals("PERCENT")) {
				depositHolder.setInterestPercent(fixedDepositForm.getInterestPercent());
			} else {
				depositHolder.setInterestAmt(fixedDepositForm.getInterestPayAmount());
			}

		}

		DepositHolder depositHolderNew = depositHolderDAO.saveDepositHolder(depositHolder);

		depositHolderList.add(depositHolderNew);

		Set<DepositHolder> set = new HashSet<DepositHolder>();
		set.add(depositHolder);
		depositSaves.setDepositHolder(set);
		fixedDepositDAO.updateFD(depositSaves);

		if (fixedDepositForm.getNomineeName() != "") {
			DepositHolderNominees nominee = new DepositHolderNominees();
			nominee.setNomineeName(fixedDepositForm.getNomineeName());
			nominee.setNomineeAge(fixedDepositForm.getNomineeAge());
			nominee.setNomineeRelationship(fixedDepositForm.getNomineeRelationShip());
			nominee.setNomineeAddress(fixedDepositForm.getNomineeAddress());
			nominee.setDepositHolderId(Long.valueOf(depositHolderNew.getId()));
			nominee.setNomineeAadhar(fixedDepositForm.getNomineeAadhar());
			nominee.setNomineePan(fixedDepositForm.getNomineePan().toUpperCase());
			int age = Integer.parseInt(fixedDepositForm.getNomineeAge());
			if (age < 18) {
				nominee.setGaurdianName(fixedDepositForm.getGuardianName());
				nominee.setGaurdianAddress(fixedDepositForm.getGuardianAddress());
				nominee.setGaurdianRelation(fixedDepositForm.getGuardianRelationShip());
				nominee.setGaurdianAge(Float.parseFloat(fixedDepositForm.getGuardianAge()));
				// nominee.setGaurdianAadhar(fixedDepositForm.getGaurdianAadhar());
				// nominee.setGaurdianPan(fixedDepositForm.getGaurdianPan().toUpperCase());
			}

			nomineeDAO.saveNominee(nominee);

		}

		/*
		 * / .........SAVING PAYMENT INFO...... /
		 */
		Payment payment = new Payment();
		String fromAccountNo = "";
		String fromAccountType = "";

		/* / Deduction from Linked account / */

		if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {
			AccountDetails accountDetails = accountDetailsDAO
					.findByAccountNo(fixedDepositForm.getDepositForm().getLinkedAccountNo());
			accountDetails.setAccountBalance(
					accountDetails.getAccountBalance() - Float.valueOf(fixedDepositForm.getDepositForm().getFdPay()));
			payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getAccountType());
			payment.setLinkedAccNoForFundTransfer(fixedDepositForm.getAccountNo());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getLinkedAccountNo();
			fromAccountType = fixedDepositForm.getAccountType();

			accountDetailsDAO.updateUserAccountDetails(accountDetails);
		}

		Double amountPaid = FDService.round(Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()), 2);
		payment.setAmountPaid(amountPaid);
		payment.setDepositId(depositSaves.getId());

		// For accounting entry only
		if (fixedDepositForm.getDepositForm().getPaymentMode().contains("Cash")
				|| fixedDepositForm.getDepositForm().getPaymentMode().contains("cash")) {
			fromAccountNo = "";
			fromAccountType = "Cash Account";
		} else if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("DD")
				|| fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("Cheque")) {

			payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
			payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
			payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
			payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getChequeNo();
			fromAccountType = fixedDepositForm.getDepositForm().getPaymentMode();

		} else if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("Card Payment")) {

			String[] cardNoArray = fixedDepositForm.getDepositForm().getCardNo().split("-");
			String cardNo = "";
			for (int i = 0; i < cardNoArray.length; i++) {
				cardNo = cardNo + cardNoArray[i];
			}
			payment.setCardNo(Long.valueOf(cardNo));
			payment.setCardType(fixedDepositForm.getDepositForm().getCardType());

			payment.setCardCvv(fixedDepositForm.getDepositForm().getCvv());
			payment.setCardExpiryDate(fixedDepositForm.getDepositForm().getExpiryDate());

			// For accounting entry only
			fromAccountNo = cardNo;
			fromAccountType = fixedDepositForm.getDepositForm().getCardType();

		}

		else if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("Net Banking")) {

			payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getDepositForm().getAccountType());
			payment.setLinkedAccNoForFundTransfer(fixedDepositForm.getDepositForm().getLinkedAccountNo());
			payment.setTransferType(fixedDepositForm.getDepositForm().getTransferType());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getLinkedAccountNo();
			fromAccountType = fixedDepositForm.getDepositForm().getAccountType();
		}

		payment.setDepositHolderId(depositHolderNew.getId());
		payment.setPaymentMadeByHolderIds(depositHolderNew.getId().toString());
		//payment.setModeOfPaymentId(fixedDepositForm.getDepositForm().getPaymentMode());
		payment.setPaymentDate(date);
		payment.setTransactionId(transactionId);
		payment.setCreatedBy(getCurrentLoggedUserName());
		payment = paymentDAO.insertPayment(payment);

		// Insert in Distribution and holderWiseDistribution
		calculationService.insertInPaymentDistribution(depositSaves, depositHolderList, amountPaid, payment.getId(),
				Constants.PAYMENT, null, null, null, null, null, null, null);

		// Insert in DepositSummary and HolderwiseDepositSummary
		DepositSummary depositSummary = calculationService.upsertInDepositSummary(depositSaves, Constants.PAYMENT,
				amountPaid, null, null, null, null, depositHolderList, null, null, null);

		// Insert in Journal & Ledger
		// -----------------------------------------------------------
		if (fixedDepositForm.getDepositForm().getPaymentMode().contains("Cash"))
			fromAccountType = "Cash";

		/*ledgerService.insertJournal(depositSaves.getId(), customer.getId(), DateService.getCurrentDate(),
				fromAccountNo,  depositSaves.getAccountNumber(), Event.K00101.getValue(),
				amountPaid, fixedDepositForm.getDepositForm().getPaymentMode(), depositSummary.getTotalPrincipal(), transactionId);*/
		// -----------------------------------------------------------

		// fdService.forReports(fixedDepositForm); // For reports
		attributes.addFlashAttribute("fixedDepositForm", fixedDepositForm);

		attributes = updateTransaction("Saved Successfully", attributes);
		return "redirect:bankFDSaved";
	}


@RequestMapping(value = "/confirmFixedDeposit", method = RequestMethod.POST)
	public ModelAndView confirmFixedDeposit(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			@RequestParam String customerDetails, RedirectAttributes attributes) throws CustomException {
		
		ModeOfPayment mop=modeOfPaymentDAO.getModeOfPaymentById(Long.parseLong(fixedDepositForm.getDepositForm().getPaymentMode()));
		Date currentDate = DateService.getCurrentDate();
		Long getcustomerByPanCard = customerDAO.getNomineeByPanCard(fixedDepositForm.getNomineePan());
		Long getcustomerByAadharCard = customerDAO.getNomineeByAadharCard(fixedDepositForm.getNomineeAadhar());

		Integer taxSavingDeposit = fixedDepositForm.getTaxSavingDeposit() == null ? 0 : 1;
		if (taxSavingDeposit == 1) {
			fixedDepositForm.setDepositClassification(Constants.taxSavingDeposit);

		}
		if (taxSavingDeposit == 0 && fixedDepositForm.getPaymentType().equals("One-Time")) {
			fixedDepositForm.setDepositClassification(Constants.fixedDeposit);

		}
		if (taxSavingDeposit == 0 && !fixedDepositForm.getPaymentType().equals("One-Time")) {
			fixedDepositForm.setDepositClassification(Constants.recurringDeposit);

		}

		fixedDepositForm = fdService.saveRates(fixedDepositForm);
		Integer days = 0;
		Date maturityDate = null;
		fixedDepositForm.setTaxSavingDeposit(Integer.toString(taxSavingDeposit));
		if (taxSavingDeposit.equals(1)) {

			fixedDepositForm.setPaymentType("One-Time");
			// fixedDepositForm.setFdTenure(5);
			// fixedDepositForm.setFdTenureType(Constants.YEAR);
		}
		/*
		 * if (fixedDepositForm.getFdTenureType().equalsIgnoreCase("Month")) {
		 * maturityDate = DateService.generateMonthsDate(DateService.getCurrentDate(),
		 * fixedDepositForm.getFdTenure()); } else if
		 * (fixedDepositForm.getFdTenureType().equalsIgnoreCase("Year")) { maturityDate
		 * = DateService.generateYearsDate(DateService.getCurrentDate(),
		 * fixedDepositForm.getFdTenure()); int day = 0; if
		 * (fixedDepositForm.getDaysValue() != null) day =
		 * fixedDepositForm.getDaysValue(); maturityDate =
		 * DateService.generateDaysDate(maturityDate, day + 1); } else {
		 */
		maturityDate = DateService.generateDaysDate(DateService.getCurrentDate(),
				fixedDepositForm.getTotalTenureInDays());
		/* } */

		days = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate);
		days = days + 1;

		// Get the customer details to find out the customer category depending on
		// his/her age
		Customer customer = customerDAO.getById(fixedDepositForm.getId());
		String category = calculationService.geCustomerActualCategory(customer);// fixedDepositForm.getCategory();

		Float interestRate = calculationService.getDepositInterestRate(days, category, fixedDepositForm.getCurrency(),
				fixedDepositForm.getFdAmount(), fixedDepositForm.getDepositClassification(),
				fixedDepositForm.getCitizen(), fixedDepositForm.getNriAccountType());

		fixedDepositForm.setCategory(category);

		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());

		if (interestRate == null) {

			List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(fixedDepositForm.getId());
			fixedDepositForm.setCustomerName(customer.getCustomerName());
			fixedDepositForm.setAccountList(accountList);
			model.put("customer", customer);

			model.put("fixedDepositForm", fixedDepositForm);

			model.put("todaysDate", date);

//			BankConfiguration bankConfiguration = ratesDAO.findAll();
//			if (bankConfiguration != null) {
//				model.put("bankConfiguration", bankConfiguration);
//			}

			model.put(Constants.ERROR, Constants.invalidTenure);
			return new ModelAndView("getFixedDeposit", "model", model);

		}

		fixedDepositForm.setFdCreditAmount(interestRate);

		// Double maturityAmount = fdService.getTotalDepositAmount(fixedDepositForm,
		// maturityDate,
		// fixedDepositForm.getFdAmount());

		Double maturityAmount = fdService.getExpectedMaturityAmount(currentDate, maturityDate, fixedDepositForm,
				interestRate);
		Double totalInterest = maturityAmount - (fdService.getFutureDepositDates(fixedDepositForm, maturityDate).size()
				* fixedDepositForm.getFdAmount());

		// Float totalTDS = 0f;
		// Float totalTDS = fdService.getTotalTDSAmount(currentDate,
		// maturityDate, fixedDepositForm.getCategory(),
		// fixedDepositForm, interestList);

		// fixedDepositForm.setEstimateTDSDeduct(Float.parseFloat(((Double)
		// FDService.round(totalTDS, 2)).toString()));
		fixedDepositForm.setFdInterest(interestRate);
		fixedDepositForm.setEstimateInterest(Float.parseFloat(((Double) FDService.round(totalInterest, 2)).toString()));
		// Double maturityAmount = totalDepositAmount + totalInterest - totalTDS;
		fixedDepositForm.setEstimatePayOffAmount(FDService.round(maturityAmount, 2));
		fixedDepositForm.setDepositType(Constants.SINGLE);
		fixedDepositForm.setFlexiInterest(fixedDepositForm.getFlexiInterest());
		String[] accDetail = fixedDepositForm.getAccountNo().split(",");
		fixedDepositForm.setAccountNo(accDetail[0]);

		fixedDepositForm.setMaturityDate(maturityDate);
		fixedDepositForm.setFdTenureDate(maturityDate);
		fixedDepositForm.setMaturityInstruction(fixedDepositForm.getMaturityInstruction());

		if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {
			if ((fixedDepositForm.getPayOffInterestType().equalsIgnoreCase("One-Time"))) {
			} else {
				Date interestPayOffDate = fdService.calculateInterestPayOffDueDate(
						fixedDepositForm.getPayOffInterestType(), fixedDepositForm.getMaturityDate(), currentDate);
				fixedDepositForm.setPayoffDate(interestPayOffDate);

			}

		}
		model.put("paymentMode", mop.getPaymentMode());
		model.put("fdDeductDate", date);
		model.put("getcustomerByPanCard", getcustomerByPanCard);
		model.put("getcustomerByAadharCard", getcustomerByAadharCard);
		model.put("customerDetails", customerDetails);
		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("getConfirmFixedDepositForm", "model", model);

	}

	@RequestMapping(value = "/bankFDSaved", method = RequestMethod.GET)
	public ModelAndView fdSaved(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("bankFDSaved", "model", model);

	}
	
	@RequestMapping(value = "/saveDepositEmi", method = RequestMethod.POST)
	public String saveDepositEmi(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes, HttpServletRequest request, String userName) throws CustomException {

		String transactionId = fdService.generateRandomString();
		Date date = DateService.getCurrentDateTime();
		Customer customerDetails = customerDAO.getByUserName(fixedDepositForm.getUserName());

		Customer customer = getCustomerDetails(customerDetails.getId());
		String category = calculationService.geCustomerActualCategory(customer);
		fixedDepositForm.setCategory(category);

		/*********** Saving deposit table **********************/
		Deposit deposit = new Deposit();
		int days = fixedDepositForm.getDaysValue() != null ? fixedDepositForm.getDaysValue() : 0;

		deposit.setAccountNumber(fdService.generateRandomString());
		deposit.setDepositAccountType(Constants.SINGLE);
		deposit.setTotalTenureInDays(fixedDepositForm.getTotalTenureInDays());
		deposit.setDepositAmount(fixedDepositForm.getFdAmount());
		deposit.setDepositType(Constants.SINGLE);
		deposit.setTenureInDays(fixedDepositForm.getTenureInDays());
		deposit.setTenureInMonths(fixedDepositForm.getTenureInMonths());
		deposit.setTenureInYears(fixedDepositForm.getTenureInYears());
		deposit.setDepositArea(fixedDepositForm.getDepositArea());
		deposit.setDepositCategory(Constants.REVERSEEMI);
		deposit.setDepositClassification(Constants.annuityDeposit);
		deposit.setDueDate(fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));
		deposit.setFlexiRate(fixedDepositForm.getFlexiInterest());
		deposit.setDepositCurrency(fixedDepositForm.getCurrency());
		// deposit.setInterestRate(fixedDepositForm.getFdCreditAmount());
		deposit.setModifiedInterestRate(fixedDepositForm.getFdCreditAmount());
		deposit.setLinkedAccountNumber(fixedDepositForm.getAccountNo());
		deposit.setCurrentBalance(Double.valueOf(deposit.getDepositAmount()));
		/***
		 * Product Code set
		 */
		ProductConfiguration product = productConfigurationDAO.findById(fixedDepositForm.getProductConfigurationId());

		deposit.setProductConfigurationId(Long.valueOf(product.getId()));

		deposit.setComment(Constants.APPROVED);
		deposit.setPaymentMode(fixedDepositForm.getDepositForm().getPaymentMode());
		deposit.setPayOffInterestType(fixedDepositForm.getPayOffInterestType());
		deposit.setMaturityDate(fixedDepositForm.getMaturityDate());
		deposit.setNewMaturityDate(fixedDepositForm.getMaturityDate());
		deposit.setStatus(Constants.OPEN);
		if (deposit.getPaymentMode().equalsIgnoreCase("DD") || deposit.getPaymentMode().equalsIgnoreCase("Cheque")) {
			deposit.setClearanceStatus(Constants.WAITINGFORCLEARANCE);
			deposit.setApprovalStatus(Constants.PENDING);
		} else {
			deposit.setApprovalStatus(Constants.APPROVED);
		}
		deposit = fdService.getMaturityAndTenureInformation(deposit, fixedDepositForm);

		deposit.setCreatedDate(date);
		deposit.setModifiedDate(date);
		deposit.setGestationPeriod(fixedDepositForm.getGestationPeriod());
		deposit.setPayOffDueDate(fixedDepositForm.getPayoffDate());

		deposit.setTransactionId(transactionId);
		deposit.setReverseEmiCategory(fixedDepositForm.getReverseEmiCategory());

		deposit.setMaturityAmount(0d);
		deposit.setNewMaturityAmount(0d);
		EndUser user = getCurrentLoggedUserDetails();

		deposit.setCreatedBy(user.getUserName());

		// Getting the interest rate for the duration
		int daysDiff = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(),
				fixedDepositForm.getMaturityDate()) + 1;
//		Float interestRate = depositRateDAO.getInterestRate(customerDetails.getCategory(),
//				fixedDepositForm.getCurrency(), daysDiff, Constants.annuityDeposit, fixedDepositForm.getFdAmount());

		Float interestRate = calculationService.getDepositInterestRate(daysDiff, category,
				fixedDepositForm.getCurrency(), fixedDepositForm.getFdAmount(), Constants.annuityDeposit,
				fixedDepositForm.getCitizen(), fixedDepositForm.getNriAccountType());

		deposit.setInterestRate(interestRate);
		deposit.setEmiAmount(fdService.round((double) fixedDepositForm.getInterestPayAmount(), 2));
		deposit.setModifiedInterestRate(interestRate);
		deposit.setPrimaryCitizen(fixedDepositForm.getCitizen());
		deposit.setPrimaryCustomerCategory(fixedDepositForm.getCategory());
		if (customerDetails.getCitizen().equalsIgnoreCase(Constants.NRI)) {
			deposit.setNriAccountType(fixedDepositForm.getNriAccountType());
			deposit.setPrimaryNRIAccountType(fixedDepositForm.getNriAccountType());
		}
		deposit.setBranchCode(fixedDepositForm.getBranchCode());
		Deposit depositSaves = fixedDepositDAO.saveFD(deposit);

		/************ Saving depositHolder table ***********************/
		List<DepositHolder> depositHolderList = new ArrayList<>();
		DepositHolder depositHolder = new DepositHolder();
		depositHolder.setContribution(100f);
		depositHolder.setCustomerId(customerDetails.getId());
		depositHolder.setDepositHolderStatus(Constants.PRIMARY);
		depositHolder.setDepositHolderCategory(customerDetails.getCategory());
		depositHolder.setDepositId(depositSaves.getId());
		depositHolder.setPayOffAccountType(fixedDepositForm.getFdPayOffAccount());

		depositHolder.setInterestType(fixedDepositForm.getInterstPayType());
		depositHolder.setInterestAmt(fixedDepositForm.getInterestPayAmount());
		depositHolder.setEmiAmount(fdService.round((double) fixedDepositForm.getInterestPayAmount(), 2));
		DepositHolder depositHolderNew = depositHolderDAO.saveDepositHolder(depositHolder);

		if (fixedDepositForm.getPayOffInterestType() != null) {
			if (!(fixedDepositForm.getStatus().equals("Reverse EMI"))) {
				depositHolder.setNameOnBankAccount(fixedDepositForm.getOtherName());
				depositHolder.setAccountNumber(fixedDepositForm.getOtherAccount().toString());
				depositHolder.setPayOffAccountType(fixedDepositForm.getFdPayOffAccount());
				if (fixedDepositForm.getFdPayOffAccount().equals("Other")) {

					depositHolder.setTransferType(fixedDepositForm.getOtherPayTransfer());
					depositHolder.setBankName(fixedDepositForm.getOtherBank());
					depositHolder.setIfscCode(fixedDepositForm.getOtherIFSC());
				}
			}
			depositHolder.setInterestType(fixedDepositForm.getInterstPayType());

			if (fixedDepositForm.getInterstPayType().equals("PERCENT")) {
				depositHolder.setInterestPercent(fixedDepositForm.getInterestPercent());
			} else {
				depositHolder.setInterestAmt(fixedDepositForm.getInterestPayAmount());
			}

			if (fixedDepositForm.getStatus().equals("Reverse EMI")) {
				// depositHolder.setEmiAmount((double)
				// fixedDepositForm.getInterestPayAmount());
				// depositHolder.setEmiAmount(fdService.round((double)
				// fixedDepositForm.getEmiAmount(), 2));
				deposit.setEmiAmount(fdService.round((double) fixedDepositForm.getInterestPayAmount(), 2));
			}

		}

		depositHolderList.add(depositHolderNew);

		Set<DepositHolder> set = new HashSet<DepositHolder>();
		set.add(depositHolder);
		depositSaves.setDepositHolder(set);

		/************
		 * Saving DepositHolderNominees table
		 ***********************/

		DepositHolderNominees nominee = new DepositHolderNominees();

		nominee.setNomineeName(fixedDepositForm.getNomineeName());
		nominee.setNomineeAge(fixedDepositForm.getNomineeAge());
		nominee.setNomineeRelationship(fixedDepositForm.getNomineeRelationShip());
		nominee.setNomineeAddress(fixedDepositForm.getNomineeAddress());
		nominee.setDepositHolderId(depositHolder.getId());
		nominee.setNomineePan(
				(fixedDepositForm.getNomineePan() != null) ? fixedDepositForm.getNomineePan().toUpperCase()
						: fixedDepositForm.getNomineePan());
		nominee.setNomineeAadhar(fixedDepositForm.getNomineeAadhar());

		int age = Integer.parseInt(fixedDepositForm.getNomineeAge());
		if (age < 18) {
			nominee.setGaurdianName(fixedDepositForm.getGuardianName());
			nominee.setGaurdianAddress(fixedDepositForm.getGuardianAddress());
			nominee.setGaurdianRelation(fixedDepositForm.getGuardianRelationShip());
			nominee.setGaurdianAadhar(fixedDepositForm.getGaurdianAadhar());
			nominee.setGaurdianPan(fixedDepositForm.getGaurdianPan().toUpperCase());
		}

		nomineeDAO.saveNominee(nominee);

		/************ Saving AccountDetails table ***********************/

		if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {
			AccountDetails accountDetails = accountDetailsDAO
					.findByAccountNo(fixedDepositForm.getDepositForm().getLinkedAccountNo());
			accountDetails.setAccountBalance(
					accountDetails.getAccountBalance() - Float.valueOf(fixedDepositForm.getDepositForm().getFdPay()));
			accountDetailsDAO.updateUserAccountDetails(accountDetails);
		}

		/************ Saving BenificiaryDetails table ***********************/

		if (fixedDepositForm.getBankAccountType() != null && fixedDepositForm.getBenificiaryName() != null
				&& fixedDepositForm.getIfscCode() != null) {

			String strAccType = fixedDepositForm.getBankAccountType().toString();
			if (strAccType.startsWith(","))
				strAccType = strAccType.substring(1, strAccType.length());
			String[] accTypeList = strAccType.split(",");

			String strBenificiaryName = fixedDepositForm.getBenificiaryName().toString();
			if (strBenificiaryName.startsWith(","))
				strBenificiaryName = strBenificiaryName.substring(1, strBenificiaryName.length());
			String[] benificiaryName = strBenificiaryName.split(",");

			String strifscCode = fixedDepositForm.getIfscCode().toString();
			if (strifscCode.startsWith(","))
				strifscCode = strifscCode.substring(1, strifscCode.length());
			String[] ifscCode = strifscCode.split(",");

			String straccNo = fixedDepositForm.getBenificiaryAccountNumber().toString();
			if (straccNo.startsWith(","))
				straccNo = straccNo.substring(1, straccNo.length());
			String[] accNoList = straccNo.split(",");

			String strAmount = fixedDepositForm.getAmountToTransfer().toString();
			if (strAmount.startsWith(","))
				strAmount = strAmount.substring(1, strAmount.length());
			String[] amount = strAmount.split(",");

			String strremarks = fixedDepositForm.getAmountToTransfer().toString();
			if (strremarks.startsWith(","))
				strremarks = strremarks.substring(1, strremarks.length());
			String[] remarks = fixedDepositForm.getRemarks().split(",");

			for (int i = 0; i < accTypeList.length; i++) {
				if (accNoList[i] == null || accNoList[i] == "" || benificiaryName[i] == null || benificiaryName[i] == ""
						|| ifscCode[i] == null || ifscCode[i] == "")
					continue;
				BenificiaryDetails benificiaryDetails = new BenificiaryDetails();
				benificiaryDetails.setCustomerId(customerDetails.getId());
				benificiaryDetails.setBenificiaryName(benificiaryName[i]);
				benificiaryDetails.setBankAccountType(accTypeList[i]);
				benificiaryDetails.setBenificiaryAccountNumber(accNoList[i]);
				benificiaryDetails.setIfscCode(ifscCode[i]);
				benificiaryDetails.setRemarks(remarks[i]);
				benificiaryDetails.setAmountToTransfer(Double.parseDouble(amount[i]));
				benificiaryDetails.setDepositId(depositHolder.getDepositId());
				benificiaryDetails.setDepositHolderId(depositHolder.getId());
				benificiaryDetails.setIsActive(1);
				benificiaryDetailsDAO.insertAccountDetails(benificiaryDetails);
			}
		}

		/********* .........SAVING PAYMENT INFO...... ****************/
		String fromAccountNo = "";
		String fromAccountType = "";

		Payment payment = new Payment();
		payment.setDepositId(depositSaves.getId());
		payment.setAmountPaid(Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()));

		if (fixedDepositForm.getDepositForm().getPaymentMode().contains("Cash")
				|| fixedDepositForm.getDepositForm().getPaymentMode().contains("cash")) {
			fromAccountNo = "";
			fromAccountType = "Cash Account";
		} else if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("DD")
				|| fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("Cheque")) {

			payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
			payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
			payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
			payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getChequeNo();
			fromAccountType = fixedDepositForm.getDepositForm().getPaymentMode();

		} else if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("Card Payment")) {

			String[] cardNoArray = fixedDepositForm.getDepositForm().getCardNo().split("-");
			String cardNo = "";
			for (int i = 0; i < cardNoArray.length; i++) {
				cardNo = cardNo + cardNoArray[i];
			}
			payment.setCardNo(Long.valueOf(cardNo));
			payment.setCardType(fixedDepositForm.getDepositForm().getCardType());

			payment.setCardCvv(fixedDepositForm.getDepositForm().getCvv());
			payment.setCardExpiryDate(fixedDepositForm.getDepositForm().getExpiryDate());

			// For accounting entry only
			fromAccountNo = cardNo;
			fromAccountType = fixedDepositForm.getDepositForm().getCardType();

		}

		else if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("Net Banking")) {

			payment.setNameOnBankAccount(fixedDepositForm.getOtherName1());
			payment.setAccountNumber(fixedDepositForm.getOtherAccount1().toString());
			payment.setDepositAmount(fixedDepositForm.getFdAmount());
			payment.setBank(fixedDepositForm.getOtherBank1());

			if (fixedDepositForm.getFdPayType().equals("DifferentBank")) {
				payment.setTransferType(fixedDepositForm.getOtherPayTransfer1());
				payment.setIfscCode(fixedDepositForm.getOtherIFSC1());
				payment.setBank(fixedDepositForm.getOtherBank1());
			}

			// For accounting entry only
			fromAccountNo = "";
			fromAccountType = "Cash Account";
		} else if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {

			payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getDepositForm().getAccountType());
			payment.setLinkedAccNoForFundTransfer(fixedDepositForm.getDepositForm().getLinkedAccountNo());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getLinkedAccountNo();
			fromAccountType = fixedDepositForm.getDepositForm().getAccountType();

			if (fixedDepositForm.getDepositForm().getAccountType() == null)
				fromAccountType = "Savings Account";
		}

		payment.setDepositHolderId(depositHolderNew.getId());
		//payment.setModeOfPaymentId(fixedDepositForm.getDepositForm().getPaymentMode());
		payment.setPaymentDate(date);
		payment.setTransactionId(transactionId);
		payment.setCreatedBy(getCurrentLoggedUserName());
		payment = paymentDAO.insertPayment(payment);

		// Insert in Distribution and holderWiseDistribution
		calculationService.insertInPaymentDistribution(depositSaves, depositHolderList,
				Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()), payment.getId(), Constants.PAYMENT,
				null, null, null, null, null, null, null);

		// Insert in DepositSummary and HolderwiseDepositSummary
		DepositSummary depositSummary = calculationService.upsertInDepositSummary(depositSaves, Constants.PAYMENT,
				Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()), null, null, null, null,
				depositHolderList, null, null, null);

		// Insert in Journal & Ledger
		// -----------------------------------------------------------
		if (fixedDepositForm.getDepositForm().getPaymentMode().contains("Cash"))
			fromAccountType = "Cash";

		ledgerService.insertJournal(depositSaves.getId(), customer.getId(), DateService.getCurrentDate(),
				fromAccountNo,  depositSaves.getAccountNumber(), Event.OPEN_DEPOSIT.getValue(),
				Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()),
				Long.parseLong(fixedDepositForm.getDepositForm().getPaymentMode()), depositSummary.getTotalPrincipal(), transactionId);
		
//		ledgerService.insertJournalLedger(depositSaves.getId(), customer.getId(), DateService.getCurrentDate(),
//				fromAccountNo, fromAccountType, depositSaves.getAccountNumber(), "Deposit Account", "Payment",
//				Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()),
//				fixedDepositForm.getDepositForm().getPaymentMode(), depositSummary.getTotalPrincipal());
		// -----------------------------------------------------------

		// fdService.forReports(fixedDepositForm); // For reports
		attributes.addFlashAttribute("fixedDepositForm", fixedDepositForm);

		/************* Saving Interest Table **********************************/

		attributes = updateTransaction("Deposit created successfully", attributes);
		return "redirect:bankFDSaved";
	}

	@RequestMapping(value = "/fdApprovedList", method = RequestMethod.GET)
	public ModelAndView fdApprovedList(ModelMap model, RedirectAttributes attributes) throws CustomException {

		ModelAndView mav = new ModelAndView();
		List<DepositForm> depositsList = fixedDepositDAO.getAllDepositsList();
		if (depositsList != null && depositsList.size() > 0) {
			model.put("depositsList", depositsList);
			mav = new ModelAndView("fdApprovedList", "model", model);
		}

		else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/depositList", method = RequestMethod.GET)
	public ModelAndView DepositList(ModelMap model, RedirectAttributes attributes) throws CustomException {

		ModelAndView mav = new ModelAndView();

		Collection<Deposit> fixedDeposits = fixedDepositDAO.findAllFDs();
		if (fixedDeposits != null && fixedDeposits.size() > 0) {

			model.put("fixedDeposits", fixedDeposits);
			mav = new ModelAndView("fdList", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/fdListBank", method = RequestMethod.GET)
	public ModelAndView fdCustomerList(ModelMap model, RedirectAttributes attributes) throws CustomException {

		ModelAndView mav = new ModelAndView();

		List<DepositForm> depositList = fixedDepositDAO.getAllDepositsList();

		if (depositList != null && depositList.size() > 0) {

			for (int i = 0; i < depositList.size(); i++) {
				DepositForm deposit = depositList.get(i);

				Long depositId = (Long) deposit.getDepositId();
				DepositModification depMod = depositModificationDAO.getLastByDepositId(depositId);
				double depositAmount = (Double) deposit.getDepositAmount();

				Date maturityDate = deposit.getMaturityDate();
				if (depMod != null) {
					depositAmount = depMod.getDepositAmount();
					maturityDate = depMod.getMaturityDate();
				}
				deposit.setDepositAmount(depositAmount);
				if (maturityDate != null)
					deposit.setMaturityDate(maturityDate);
			}

			model.put("depositList", depositList);
			mav = new ModelAndView("fdListBank", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/editFDBank", method = RequestMethod.GET)
	public ModelAndView editFDBank(@RequestParam Long id, ModelMap model, HttpServletRequest request,
			RedirectAttributes attributes, @RequestParam String depositCategory, @RequestParam Long holderId)
			throws CustomException {

		Deposit deposit = fixedDepositDAO.getDeposit(id);
		ProductConfiguration productConfiguration = productConfigurationDAO
				.findById(deposit.getProductConfigurationId());

		ModelAndView mav;
		HolderForm holderForm = new HolderForm();

		if (depositCategory != null && depositCategory.equalsIgnoreCase("REVERSE-EMI")) {
			holderForm = depositService.getReverseEMIByDepositId(id);
			model.put("holderId", holderId);
			mav = new ModelAndView("editReverseEMIBank", "model", model);
		} else {
			List<HolderForm> depositList = depositService.getDepositByDepositId(id);
			holderForm = depositList.get(0);

			model.put("depositHolderList", depositList);
			List<AccountDetails> accountList = accountDetailsDAO
					.findCurrentSavingByCustId(holderForm.getDepositHolder().getCustomerId());

			model.put("accountList", accountList);
			model.put("holderId", holderId);
			mav = new ModelAndView("editFDBank", "model", model);
		}

		model.put("depositHolderForm", holderForm);
		model.put("holderId", holderId);
		model.put("depositClassification", holderForm.getDeposit().getDepositClassification());
		model.put("productConfiguration", productConfiguration);

		return mav;

	}

	@RequestMapping(value = "/confirmEditReverseEMI", method = RequestMethod.POST)
	public ModelAndView confirmEditReverseEMI(ModelMap model, @ModelAttribute HolderForm depositHolderForm)
			throws CustomException {

		model.put("depositHolderForm", depositHolderForm);
		return new ModelAndView("confirmEditReverseEMIBank", "model", model);

	}

	/* editReverseEMIPost */

	@RequestMapping(value = "/editReverseEMIPost", method = RequestMethod.POST)
	public ModelAndView editReverseEMIPost(ModelMap model, @ModelAttribute HolderForm depositHolderForm,
			RedirectAttributes attributes) throws CustomException {

		Deposit deposit = depositHolderForm.getDeposit();
		DepositHolder depositHolder = depositHolderForm.getDepositHolder();
		Long depositId = deposit.getId();

		Date todayDate = DateService.getCurrentDate();

		Deposit deposit1 = fixedDepositDAO.findById(depositId);
		Float depositInterestRate = deposit1.getModifiedInterestRate();
		Date maturityDate = depositHolderForm.getEditDepositForm().getMaturityDateNew();
		if (maturityDate == null)
			maturityDate = deposit1.getMaturityDate();
		depositInterestRate = (deposit1.getModifiedInterestRate() == null) ? deposit1.getInterestRate()
				: deposit1.getModifiedInterestRate();

		Float newInterestRate = depositInterestRate;

		/************** previous tenure and tenure type *************/
		String tenureArray[] = deposit.getTenureType().split(",");
		String tenureType = tenureArray[0];
		Integer tenure = deposit.getTenure();

		String randomNumber = DateService.getRandomNumBasedOnDate();
		String transactionId = fdService.generateRandomString();
		String userName = getCurrentLoggedUserName();
		Double depositAmount = deposit.getDepositAmount();
		fixedDepositForm.setDepositId(depositId);
		fixedDepositForm.setPaymentType(deposit.getPaymentType());
		fixedDepositForm.setFdDeductDate(deposit.getDueDate());
		fixedDepositForm.setCategory(depositHolderForm.getDepositHolder().getDepositHolderCategory());
		fixedDepositForm.setMaturityDate(maturityDate);
		fixedDepositForm.setDeductionDay(DateService.getDayOfMonth(deposit.getDueDate()));
		fixedDepositForm.setFdAmount(depositAmount);

		boolean tenureChange = depositHolderForm.getEditDepositForm().getStatusTenure() != "";

		/******* .....new tenure and tenure type ************/

		if (tenureChange) {

			maturityDate = depositHolderForm.getEditDepositForm().getMaturityDateNew();
			tenure = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate);
			tenureType = "Day";

//			newInterestRate = fdService.getDepositIntRate(tenure,
//					depositHolderForm.getDepositHolder().getDepositHolderCategory(), deposit.getDepositCurrency(),
//					deposit.getDepositAmount(), deposit.getDepositClassification());

			newInterestRate = calculationService.getDepositInterestRate(tenure, deposit.getPrimaryCustomerCategory(),
					deposit.getDepositCurrency(), deposit.getDepositAmount(), deposit.getDepositClassification(),
					deposit.getPrimaryCitizen(), deposit.getPrimaryNRIAccountType());

			if (newInterestRate == null) {

				attributes.addFlashAttribute(Constants.ERROR, "No rate available for the selected tenure");

				return new ModelAndView("redirect:fdListBank");
			}
			fixedDepositForm.setFdInterest(newInterestRate);
			deposit1.setModifiedInterestRate(newInterestRate);
			deposit1.setNewMaturityDate(maturityDate);

//			// delete from interest saved after the current date
//			Interest lastInterest = interestDAO.deleteByDepositIdAndDate(depositId);
//
//			List<Interest> interestList = fdService.getInterestBreakupForModification(DateService.getCurrentDate(),
//					fixedDepositForm.getMaturityDate(), fixedDepositForm, deposit1);
//
//			for (int i = 0; i < interestList.size(); i++) {
//
//				Interest interest = new Interest();
//				interest = interestList.get(i);
//				if (i == interestList.size() - 1)
////					deposit1.setNewMaturityAmount(interest.getInterestSum());
//
//					if (lastInterest != null) {
//						if (DateService.getDaysBetweenTwoDates(lastInterest.getInterestDate(),
//								interest.getInterestDate()) != 0)
//							interestDAO.insert(interest);
//						else {
//							interestList.remove(i);
//							i--;
//						}
//					} else {
//						interestDAO.insert(interest);
//					}
//
//			}

			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);

			// fdService.getTDSBreakupForModification(fixedDepositForm,
			// interestList, fixedDepositForm.getMaturityDate(),
			// deposit1, depositHolderList);
			// List<TDS> tdsList =
			// fdService.getTDSBreakupForModification(fixedDepositForm,
			// interestList,
			// fixedDepositForm.getMaturityDate(), deposit1, depositHolderList);
			// for (int i = 0; i < tdsList.size(); i++) {
			// // TDS tds = new TDS();
			// TDS tds = tdsList.get(i);
			// tds.setDepositId(fixedDepositForm.getDepositId());
			// tdsDAO.insert(tds);
			// }

			Distribution lastInterestDistribution = calculationService.calculateInterest(deposit1, depositHolderList,
					"", null);

			Distribution lastBaseLine = paymentDistributionDAO.getLastBaseLineForTenureChange(depositId);
			Date fromDate = lastBaseLine == null ? DateService.getDate(deposit.getCreatedDate())
					: lastBaseLine.getDistributionDate();
			Date toDate = DateService.getCurrentDate();
			int daysDiff = DateService.getDaysBetweenTwoDates(fromDate, toDate);

//			Float interestRateForAdjustment = depositRateDAO.getInterestRate(
//					depositHolderForm.getDepositHolder().getDepositHolderCategory(), deposit.getDepositCurrency(),
//					daysDiff, Constants.annuityDeposit, deposit.getDepositAmount());

			Float interestRateForAdjustment = calculationService.getDepositInterestRate(daysDiff,
					depositHolderForm.getDepositHolder().getDepositHolderCategory(), deposit.getDepositCurrency(),
					deposit.getDepositAmount(), Constants.annuityDeposit, deposit1.getPrimaryCitizen(),
					deposit1.getPrimaryNRIAccountType());
			// Adjust the Interest
			String action = "Interest Adjustment For Tenure Change";
			fdService.adjustInterestForTenureChangeOrConversion(action, deposit1, lastInterestDistribution,
					depositHolderList, lastBaseLine, interestRateForAdjustment);

		}

		/* Updating beneficiary table */

		String id = depositHolderForm.getBenificiary().getBenificiaryId().toString();
		String[] idArray = id.split(",");

		String accType = depositHolderForm.getBenificiary().getBankAccountType().toString();
		String[] accTypeArray = accType.split(",");

		String benificiaryName = depositHolderForm.getBenificiary().getBenificiaryName().toString();
		String[] benificiaryNameArray = benificiaryName.split(",");

		String ifscCode = depositHolderForm.getBenificiary().getIfscCode().toString();
		String[] ifscCodeArray = ifscCode.split(",");

		String accNo = depositHolderForm.getBenificiary().getBenificiaryAccountNumber().toString();
		String[] accNoArray = accNo.split(",");

		String amount = depositHolderForm.getBenificiary().getAmountToTransfer().toString();
		String[] amountArray = amount.split(",");

		String remarks = depositHolderForm.getBenificiary().getAmountToTransfer().toString();
		String[] remarksArray = remarks.split(",");

		String isActive = depositHolderForm.getBenificiary().getIsActive().toString();
		String[] isActiveArray = isActive.split(",");

		/* Existing beneficiary */
		int i = 0;
		for (i = 0; i < idArray.length; i++) {
			Long beneficiaryId = Long.valueOf(idArray[i]);
			BenificiaryDetails benificiaryDetails = benificiaryDetailsDAO.findById(beneficiaryId);

			benificiaryDetails.setBenificiaryName(benificiaryNameArray[i]);
			benificiaryDetails.setBankAccountType(accTypeArray[i]);
			benificiaryDetails.setBenificiaryAccountNumber(accNoArray[i]);
			benificiaryDetails.setIfscCode(ifscCodeArray[i]);
			benificiaryDetails.setRemarks(remarksArray[i]);
			benificiaryDetails.setAmountToTransfer(Double.parseDouble(amountArray[i]));
			benificiaryDetails.setIsActive(Integer.valueOf(isActiveArray[i]));
			benificiaryDetailsDAO.updateBeneficiary(benificiaryDetails);
		}
		/* New beneficiary */
		for (int j = i; j < accTypeArray.length; j++) {

			BenificiaryDetails benificiaryDetails = new BenificiaryDetails();
			benificiaryDetails.setCustomerId(depositHolder.getCustomerId());
			benificiaryDetails.setBenificiaryName(benificiaryNameArray[j]);
			benificiaryDetails.setBankAccountType(accTypeArray[j]);
			benificiaryDetails.setBenificiaryAccountNumber(accNoArray[j]);
			benificiaryDetails.setIfscCode(ifscCodeArray[j]);
			benificiaryDetails.setRemarks(remarksArray[j]);
			benificiaryDetails.setAmountToTransfer(Double.parseDouble(amountArray[j]));
			benificiaryDetails.setDepositId(depositId);
			benificiaryDetails.setDepositHolderId(depositHolder.getId());
			benificiaryDetails.setIsActive(1);
			benificiaryDetailsDAO.insertAccountDetails(benificiaryDetails);
		}

		/* Updating Modification table */
		DepositModification depositModification = new DepositModification();

		depositModification.setMaturityDate(maturityDate);
		depositModification.setDepositAmount(depositAmount);
		depositModification.setDepositHolderId(depositHolder.getId());
		depositModification.setDepositId(deposit.getId());
		depositModification.setInterestRate(newInterestRate);

		depositModification.setTenure(tenure);
		depositModification.setTenureType(tenureType);

		depositModification.setModifiedDate(todayDate);
		depositModification.setModificationNo(randomNumber);
		depositModification.setTransactionId(fdService.generateRandomString());
		depositModification.setModifiedBy(userName);
		depositModification = depositModificationDAO.saveDepositHolder(depositModification);

		deposit1.setModifiedDate(todayDate);
		fixedDepositDAO.updateFD(deposit1);

		Date curDate = DateService.getCurrentDateTime();
		attributes.addFlashAttribute(Constants.TRANSACTIONID, transactionId);
		attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
		attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Reverse Annuity Updated Successfully");

		return new ModelAndView("redirect:bankFDSaved");

	}

	@RequestMapping(value = "/compareMaturity", method = RequestMethod.POST)
	public @ResponseBody String compareChanges(@ModelAttribute HolderForm depositHolderForm) throws CustomException {

		Deposit deposit = depositHolderForm.getDeposit();
		DepositHolder holder = depositHolderForm.getDepositHolderList().get(0);
		fixedDepositForm.setDepositId(deposit.getId());
		fixedDepositForm.setDepositHolderId(holder.getId());
		fixedDepositForm.setFdAmount(deposit.getDepositAmount());
		fixedDepositForm.setPaymentType(deposit.getPaymentType());
		fixedDepositForm.setFdDeductDate(deposit.getDueDate());
		fixedDepositForm.setCategory(depositHolderForm.getDepositHolder().getDepositHolderCategory());
		fixedDepositForm.setMaturityDate(depositHolderForm.getEditDepositForm().getMaturityDateNew());
		fixedDepositForm.setDeductionDay(DateService.getDayOfMonth(deposit.getDueDate()));

		int tenure = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(),
				depositHolderForm.getEditDepositForm().getMaturityDateNew());

		if (deposit.getDepositCurrency() == null || deposit.getDepositAmount() == null
				|| deposit.getDepositClassification() == null)
			deposit = fixedDepositDAO.getDeposit(deposit.getId());

		float newInterestRate = calculationService.getDepositInterestRate(tenure, deposit.getPrimaryCustomerCategory(),
				deposit.getDepositCurrency(), deposit.getDepositAmount(), deposit.getDepositClassification(),
				deposit.getPrimaryCitizen(), deposit.getNriAccountType());

		float depositInterestRate = (deposit.getModifiedInterestRate() == null) ? deposit.getInterestRate()
				: deposit.getModifiedInterestRate();

		fixedDepositForm.setFdInterest(newInterestRate);

		// Steps for adjustment amount
		// 1. Interest calculation till date
		// 2. Interest Adjustment for reducing tenure

		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());

		// 1. Interest calculation till date for alert
		// fdService.calculateInterestForAlert(deposit, depositHolderList);
		// Calculate the Fixed And Variable Interest
		HashMap<String, Double> interestDetails = calculationService.calculateInterestAmount(deposit, depositHolderList,
				"", null);

		Double totalFixedInterest = calculationService.round(interestDetails.get(Constants.FIXEDINTEREST), 2);
		Double totalVariableInterest = calculationService.round(interestDetails.get(Constants.VARIABLEINTEREST), 2);

		// Find the Total Interest Amount
		Double amount = calculationService.round(totalFixedInterest + totalVariableInterest, 2);

		// 2. Interest Adjustment and penalty for the Withdraw
		Distribution lastBaseLine = paymentDistributionDAO.getLastBaseLineForTenureChange(deposit.getId());
		Double adjustmentAmount = 0d;
		if (newInterestRate != depositInterestRate) {
			Date fromDate = lastBaseLine == null ? DateService.getDate(deposit.getCreatedDate())
					: lastBaseLine.getDistributionDate();
			Date toDate = DateService.getCurrentDate();
			int daysDiff = DateService.getDaysBetweenTwoDates(fromDate, toDate) + 1;

			Float interestRateForAdjustment = calculationService.getDepositInterestRate(daysDiff,
					fixedDepositForm.getCategory(), deposit.getDepositCurrency(), deposit.getDepositAmount(),
					deposit.getDepositClassification(), deposit.getPrimaryCitizen(),
					deposit.getPrimaryNRIAccountType());

			// Adjust the Interest
			adjustmentAmount = fdService.getAmountToAdjustForTenureChangeAlert(deposit.getId(), lastBaseLine,
					interestRateForAdjustment);
			adjustmentAmount = calculationService.round(adjustmentAmount - amount, 2);
		}

		return adjustmentAmount + "," + newInterestRate;

//		if (deposit.getDepositCategory() == null || deposit.getDepositCategory().equals("")) {
//			List<Interest> interestClassList = fdService.getInterestBreakupForModification(DateService.getCurrentDate(),
//					depositHolderForm.getEditDepositForm().getMaturityDateNew(), fixedDepositForm, deposit);
//			Double maturityAmt = 0d; // interestClassList.get(interestClassList.size() - 1).getInterestSum();
//			return adjustmentAmount + "," + newInterestRate ;
//		} else {
//
//			return adjustmentAmount + "," + newInterestRate;
//		}

	}

	@RequestMapping(value = "/confirmEditFdBank", method = RequestMethod.POST)
	public ModelAndView checkResult(ModelMap model, @ModelAttribute HolderForm depositHolderForm,
			RedirectAttributes attributes, @RequestParam Long holderId) throws CustomException {

		Deposit deposit = null;
		// if(depositHolderForm.getDepositChange()!=null &&
		// depositHolderForm.getDepositChange().equalsIgnoreCase(Constants.CONVERTDEPOSIT))
		String newInterestRate = null;
		String depositAmount = null;
		String loseAmount = null;
		String transferAmount = null;

		Double newIntRate = 0.0;
		Double depositAmt = 0.0;
		Double loseAmt = 0.0;
		Double transferAmt = 0.0;
		deposit = fixedDepositDAO.getDeposit(depositHolderForm.getDeposit().getId());

		// if (depositHolderForm.getDepositChange().equalsIgnoreCase("convertDeposit"))
		// {
		HashMap<String, Double> depositConversionDetails = fdService.convertDeposit(deposit);
		List<String> depositConversionkey = new ArrayList<String>();
		List<Double> depositConversionValue = new ArrayList<Double>();
		if (depositConversionValue.size() > 0) {
			for (java.util.Map.Entry<String, Double> entry : depositConversionDetails.entrySet()) {
				String key = entry.getKey();
				Double value = entry.getValue();
				depositConversionkey.add(key);
				depositConversionValue.add(value);
			}

			newInterestRate = depositConversionkey.get(0);
			depositAmount = depositConversionkey.get(1);
			loseAmount = depositConversionkey.get(2);
			transferAmount = depositConversionkey.get(3);

			newIntRate = depositConversionValue.get(0);
			depositAmt = depositConversionValue.get(1);
			loseAmt = depositConversionValue.get(2);
			transferAmt = depositConversionValue.get(3);

		}
		// }
		model.put("depositPaymentType", deposit.getPaymentType());
		model.put("newInterestRate", newInterestRate);
		model.put("newIntRate", newIntRate);
		model.put("depositAmount", depositAmount);
		model.put("loseAmount", loseAmount);
		model.put("transferAmount", transferAmount);
		model.put("depositAmt", depositAmt);
		model.put("loseAmt", loseAmt);
		model.put("transferAmt", transferAmt);

		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String newMaturityDate = smt.format(deposit.getMaturityDate());
		if (depositHolderForm.getEditDepositForm().getMaturityDateNew() != null) {
			newMaturityDate = smt.format(depositHolderForm.getEditDepositForm().getMaturityDateNew());
		}
		model.put("newMaturityDate", newMaturityDate);

		model.put("depositHolderForm", depositHolderForm);
		model.put("holderId", holderId);

		return new ModelAndView("confirmEditFdBank", "model", model);

	}

	@RequestMapping(value = "/editFdPost", method = RequestMethod.POST)
	public String editPost(ModelMap model, @ModelAttribute HolderForm depositHolderForm, RedirectAttributes attributes)
			throws CustomException {
		log.info("edit FD POST -------Start =----");
		try {
			Deposit deposit = depositHolderForm.getDeposit();
			Long depositId = deposit.getId();
			Customer customer = customerDAO.getById(depositHolderForm.getDepositHolderList().get(0).getCustomerId());
			// Customer customer =
			// customerDAO.getById(fdService.getPrimaryHolderId(depositHolderForm.getDepositHolderList()));

			// if anybody wont change any value then it will be null only
			if (depositHolderForm.getEditDepositForm().getStatusTenure() != null
					&& depositHolderForm.getEditDepositForm().getStatusTenure() != "") {
				// New Tenure
				Date maturityDate = depositHolderForm.getNewMaturityDate();
				Integer tenure = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate);
				String tenureType = "Day";

				if (deposit.getDepositCurrency() == null || deposit.getDepositAmount() == null
						|| deposit.getDepositClassification() == null)
					deposit = fixedDepositDAO.getDeposit(deposit.getId());

				// get the actual category depending on age
				String category = calculationService.geCustomerActualCategory(customer);// fixedDepositForm.getCategory();

				// New Interest rate
				Float newInterestRate = calculationService.getDepositInterestRate(tenure, category,
						deposit.getDepositCurrency(), deposit.getDepositAmount(), deposit.getDepositClassification(),
						deposit.getPrimaryCitizen(), deposit.getNriAccountType());

				if (newInterestRate == null) {
					attributes.addFlashAttribute(Constants.ERROR, "No rate available for the selected tenure");
					return "redirect:fdListBank";
				}
			}

			// Get current logged in User
			EndUser loggedInUser = getCurrentLoggedUserDetails();

			// Save the modified data to respective tables
			DepositModificationMaster depositModificationMaster = depositService
					.editDepositFromCustomer(depositHolderForm, customer, loggedInUser);

			Date curDate = DateService.getCurrentDateTime();
			attributes.addFlashAttribute(Constants.TRANSACTIONID, depositModificationMaster.getTransactionId());
			attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
			attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Deposit Updated Successfully");

			return "redirect:bankFDSaved";
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("Exception -----------------", ex);
			attributes.addFlashAttribute(Constants.ERROR, ex.getMessage());
			return "redirect:fdListBank";

		}

	}

	/*
	 * @Transactional
	 * 
	 * @RequestMapping(value = "/editFdPost", method = RequestMethod.POST) public
	 * String editPost(ModelMap model, @ModelAttribute HolderForm depositHolderForm,
	 * RedirectAttributes attributes) {
	 * 
	 * Deposit deposit = depositHolderForm.getDeposit(); Long depositId =
	 * deposit.getId();
	 * 
	 * Date payOffDueDate = DateService.getCurrentDateTime();
	 * 
	 * Customer customer =
	 * customerDAO.getById(depositHolderForm.getDepositHolderList().get(0).
	 * getCustomerId());
	 * 
	 * Deposit deposit1 = fixedDepositDAO.findById(depositId); Float
	 * depositInterestRate =
	 * deposit1.getModifiedInterestRate()==null?deposit1.getInterestRate():
	 * deposit1.getModifiedInterestRate(); Date maturityDate =
	 * depositHolderForm.getEditDepositForm().getMaturityDateNew(); if (maturityDate
	 * == null) maturityDate = deposit1.getMaturityDate(); depositInterestRate =
	 * (deposit1.getModifiedInterestRate() == null) ? deposit1.getInterestRate() :
	 * deposit1.getModifiedInterestRate();
	 * 
	 * Float newInterestRate = depositInterestRate; Boolean interestChanged = false;
	 * 
	 *//************** previous tenure and tenure type *************/
	/*
	 * String tenureArray[] = deposit.getTenureType().split(","); String tenureType
	 * = tenureArray[0]; Integer tenure = deposit.getTenure();
	 * 
	 * String randomNumber = DateService.getRandomNumBasedOnDate(); String
	 * transactionId = fdService.generateRandomString(); String userName =
	 * getCurrentLoggedUserName();
	 * 
	 * fixedDepositForm.setDepositId(depositId);
	 * fixedDepositForm.setPaymentType(deposit.getPaymentType());
	 * fixedDepositForm.setFdDeductDate(deposit.getDueDate());
	 * fixedDepositForm.setCategory(customer.getCategory());
	 * fixedDepositForm.setMaturityDate(maturityDate);
	 * fixedDepositForm.setDeductionDay(DateService.getDayOfMonth(deposit.
	 * getDueDate()));
	 * 
	 * boolean tenureChange =
	 * depositHolderForm.getEditDepositForm().getStatusTenure() != ""; boolean
	 * amountChange = depositHolderForm.getEditDepositForm().getAddAmount() != null;
	 * boolean paymentTypeChange =
	 * !(deposit.getPaymentType().equalsIgnoreCase(deposit1.getPaymentType()));
	 * 
	 *//******* .....new tenure and tenure type ************/
	/*
	 * 
	 * if (tenureChange) {
	 * 
	 * maturityDate = depositHolderForm.getEditDepositForm().getMaturityDateNew();
	 * tenure = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(),
	 * maturityDate); tenureType = "Day"; interestChanged = true; // if tenure is
	 * reduced, then only calculate the new interest rate // if //
	 * (depositHolderForm.getEditDepositForm().getStatusTenure().
	 * equalsIgnoreCase("reduce")) // { newInterestRate =
	 * fdService.getDepositIntRate(tenure, customer.getCategory()); // } if
	 * (newInterestRate == null) {
	 * 
	 * attributes.addFlashAttribute(Constants.ERROR,
	 * "No rate available for the selected tenure");
	 * 
	 * return "redirect:fdListBank";
	 * 
	 * }
	 * 
	 * }
	 * 
	 *//****** ..add/reduce extra amount... *************/
	/*
	 * Double depositAmount = deposit.getDepositAmount();
	 * 
	 * if (amountChange) { if
	 * (depositHolderForm.getEditDepositForm().getStatus().equals("add")) {
	 * depositAmount = deposit.getDepositAmount() +
	 * depositHolderForm.getEditDepositForm().getAddAmount(); } if
	 * (depositHolderForm.getEditDepositForm().getStatus().equals("reduce")) {
	 * depositAmount = deposit.getDepositAmount() -
	 * depositHolderForm.getEditDepositForm().getAddAmount();
	 * 
	 * } }
	 * 
	 * fixedDepositForm.setFdInterest(newInterestRate);
	 * fixedDepositForm.setFdAmount(depositAmount); List<DepositHolder>
	 * depositHolderList = depositHolderDAO.getDepositHolders(depositId);
	 * 
	 * if (tenureChange || amountChange || paymentTypeChange) { // delete from
	 * interest saved after the current date Interest lastInterest =
	 * interestDAO.deleteByDepositIdAndDate(depositId);
	 * 
	 * List<Interest> interestList =
	 * fdService.getInterestBreakupForModification(DateService.getCurrentDate(),
	 * fixedDepositForm.getMaturityDate(), fixedDepositForm, deposit1);
	 * 
	 * for (int i = 0; i < interestList.size(); i++) {
	 * 
	 * Interest interest = new Interest(); interest = interestList.get(i); if (i ==
	 * interestList.size() - 1)
	 * deposit1.setNewMaturityAmount(interest.getInterestSum()); if (lastInterest !=
	 * null) { if
	 * (DateService.getDaysBetweenTwoDates(lastInterest.getInterestCalcDate(),
	 * interest.getInterestCalcDate()) != 0) interestDAO.insert(interest); else {
	 * interestList.remove(i); i--; } } else { interestDAO.insert(interest); } }
	 * 
	 * // fdService.getTDSBreakupForModification(fixedDepositForm, // interestList,
	 * fixedDepositForm.getMaturityDate(), // deposit1, depositHolderList);
	 * 
	 * // List<TDS> tdsList = //
	 * fdService.getTDSBreakupForModification(fixedDepositForm, // interestList, //
	 * fixedDepositForm.getMaturityDate(), deposit1, depositHolderList); // for (int
	 * i = 0; i < tdsList.size(); i++) { // // TDS tds = new TDS(); // TDS tds =
	 * tdsList.get(i); // tds.setDepositId(fixedDepositForm.getDepositId()); //
	 * tdsDAO.insert(tds); // }
	 * 
	 * }
	 * 
	 * if (depositHolderForm.getEditDepositForm().getStatusTenure() != "") {
	 *//******
		 * adjustment in Interest, Distribution and DepositHolderWiseDistribution table
		 ********/

	/*
	 * 
	 * Date lastAdjustmentDate = deposit.getModifiedDate();
	 * 
	 * // Steps // 1. Interest calculation till date // 2. Interest Adjustment for
	 * reducing tenure // 3. Update the new interest rate in deposit table // 4.
	 * Update the new interest rate in modification table??
	 * 
	 * // 1. Interest calculation till date //
	 * ------------------------------------------------------------------ // This
	 * will be considered here as last distriburion Distribution
	 * lastInterestDistribution = fdService.calculateInterest(deposit1,
	 * depositHolderList); //
	 * ------------------------------------------------------------------
	 * 
	 * // 2. Interest Adjustment and penalty for the Withdraw //
	 * ---------------------------------------------------------------------- //
	 * Calculate the interest to adjust // Insert an interest adjustment for the
	 * withdraw in Distribution // table // Insert the adjustment in Interest table
	 * // Float interestRateForAdjustment = // Getting the duration to // adjust
	 * 
	 * // Distribution lastAdjustment = //
	 * paymentDistributionDAO.getLastAdjustment(depositId); Distribution
	 * lastBaseLine =
	 * paymentDistributionDAO.getLastBaseLineForTenureChange(depositId);
	 * 
	 * Float interestRateForAdjustment = 0f; if(newInterestRate !=
	 * depositInterestRate) { Date fromDate = lastBaseLine == null ?
	 * DateService.getDate(deposit.getCreatedDate()) :
	 * lastBaseLine.getDistributionDate(); Date toDate =
	 * DateService.getCurrentDate(); int daysDiff =
	 * DateService.getDaysBetweenTwoDates(fromDate, toDate);
	 * 
	 * interestRateForAdjustment =
	 * depositRateDAO.getInterestRate(customer.getCategory(), daysDiff); // Adjust
	 * the Interest fdService.adjustInterestForTenureChange(deposit1,
	 * lastInterestDistribution, depositHolderList, lastBaseLine,
	 * interestRateForAdjustment); } //
	 * ----------------------------------------------------------------------
	 * 
	 * // daysDiff = //
	 * DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), // toDate);
	 * // newInterestRate = //
	 * depositRateDAO.getInterestRate(customer.getCategory(), daysDiff); }
	 * 
	 * // Now previous Interest Rate is fetched // Check if the modified interest
	 * rate is lower than the previous rate
	 * 
	 *//***************
		 * inserting in modification table for each holder....
		 ***********//*
						 * 
						 * for (int i = 0; i < depositHolderForm.getDepositHolderList().size(); i++) {
						 * 
						 * DepositModification depositModification = new DepositModification();
						 * 
						 * DepositHolder holder = depositHolderForm.getDepositHolderList().get(i);
						 * 
						 * depositModification.setMaturityDate(maturityDate);
						 * depositModification.setDepositAmount(depositAmount);
						 * depositModification.setDepositHolderId(holder.getId());
						 * depositModification.setDepositId(deposit.getId());
						 * depositModification.setInterestRate(newInterestRate);
						 * depositModification.setPaymentMode(deposit.getPaymentMode ());
						 * depositModification.setPaymentType(deposit.getPaymentType ());
						 * depositModification.setTenure(tenure);
						 * depositModification.setTenureType(tenureType);
						 * 
						 * if (holder.getInterestType() != null &&
						 * (!holder.getInterestType().equals(""))) {
						 * 
						 * depositModification.setPayOffInterestType(holder. getInterestType()); //
						 * part/percent depositModification.setPayOffAccountType(holder.
						 * getPayOffAccountType()); // saving/fd
						 * depositModification.setPayOffNameOnBankAccount(holder.
						 * getNameOnBankAccount()); depositModification.setPayOffAccountNumber(holder.
						 * getAccountNumber());
						 * 
						 * if (holder.getPayOffAccountType() != null &&
						 * !holder.getPayOffAccountType().equals("Saving Account")) {
						 * depositModification.setPayOffBankIFSCCode(holder. getIfscCode());
						 * depositModification.setPayOffBankName(holder.getBankName( ));
						 * depositModification.setPayOffTransferType(holder. getTransferType()); }
						 * 
						 * depositModification.setPayOffType(deposit. getPayOffInterestType()); //
						 * monthly/quarterly depositModification.setPayOffInterestPercent(holder.
						 * getInterestPercent()); depositModification.setPayOffInterestAmt(holder.
						 * getInterestAmt());
						 * 
						 * }
						 * 
						 * depositModification.setModifiedDate(payOffDueDate);
						 * depositModification.setModificationNo(randomNumber);
						 * depositModification.setTransactionId(fdService. generateRandomString());
						 * depositModification.setModifiedBy(userName);
						 * 
						 * if (deposit.getStopPayment() != null && deposit.getStopPayment() == 1)
						 * depositModification.setStopPayment(1); else
						 * depositModification.setStopPayment(0);
						 * 
						 * depositModification = depositModificationDAO.saveDepositHolder(
						 * depositModification);
						 * 
						 * }
						 * 
						 * payOffDueDate = deposit1.getPayOffDueDate(); if
						 * (deposit1.getPayOffInterestType() == null)
						 * deposit1.setPayOffInterestType(""); if
						 * (!deposit1.getPayOffInterestType().equalsIgnoreCase(
						 * deposit.getPayOffInterestType())) {
						 * 
						 * List<PayOffDistribution> distribution =
						 * payOffDAO.getPayOffDistribution(deposit.getId()); if (distribution != null) {
						 * payOffDueDate = distribution.get(0).getPayOffDistributionDate();
						 * 
						 * } else { payOffDueDate = deposit.getCreatedDate(); }
						 * 
						 * if (deposit.getPayOffInterestType() != null) payOffDueDate =
						 * fdService.calculateInterestPayOffDueDateForPayOff(deposit
						 * .getPayOffInterestType(), deposit.getNewMaturityDate(),
						 * deposit1.getCreatedDate());
						 * 
						 * payOffDueDate = fdService.calculateInterestPayOffDueDate( fixedDepositForm.
						 * getPayOffInterestType(), fixedDepositForm.getMaturityDate());
						 * 
						 * else payOffDueDate = null; }
						 * 
						 * // now if user changed stoppayment value from 1 to 0 // that means if he
						 * previously requested for stop payment but // currently wants to contnue the
						 * premium amount // then we have to change the due date from here. Because //
						 * due date is earlier date lying in the database. // Change the DueDate Date
						 * dueDate = deposit1.getDueDate(); if (!(deposit.getStopPayment() != null &&
						 * deposit.getStopPayment() == 1) && (deposit1.getStopPayment() != null &&
						 * deposit1.getStopPayment() == 1)) { int deductionDay =
						 * DateService.getDayOfMonth(deposit1.getDueDate()); dueDate =
						 * fdService.calculateDueDateOnContinuePayment(deposit1. getPaymentType(),
						 * deductionDay); deposit1.setDueDate(dueDate); }
						 * 
						 * // Set Stop Payment in Deposit also if (deposit.getStopPayment() != null &&
						 * deposit.getStopPayment() == 1) deposit1.setStopPayment(1); else
						 * deposit1.setStopPayment(null);
						 * 
						 * deposit1.setPayOffDueDate(payOffDueDate);
						 * deposit1.setModifiedDate(DateService.getCurrentDateTime() ); if
						 * (interestChanged == true) deposit1.setModifiedInterestRate(newInterestRate);
						 * deposit1.setNewMaturityDate(maturityDate);
						 * 
						 * if (deposit.getPaymentMode().equalsIgnoreCase(Constants. SAVINGACCOUNTDEBIT))
						 * { deposit1.setLinkedAccountNumber(deposit. getLinkedAccountNumber()); }
						 * 
						 * Set<DepositHolder> set = new HashSet<DepositHolder>();
						 * set.addAll(depositHolderList); deposit.setDepositHolder(set);
						 * 
						 * deposit.setPaymentMode(deposit.getPaymentMode());
						 * fixedDepositDAO.updateFD(deposit);
						 * 
						 * 
						 * fixedDepositForm.setDepositId(deposit1.getId());
						 * fixedDepositForm.setCategory(customer.getCategory()); fixedDepositForm =
						 * fdService.setParametersForProjectedInterest( fixedDepositForm);
						 * 
						 * // Save the projected interests List<Interest> interestList =
						 * fdService.getInterestBreakupForModification(DateService. getCurrentDate(),
						 * maturityDate, fixedDepositForm, deposit); for (int i = 0; i <
						 * interestList.size(); i++) { Interest interest = new Interest(); interest =
						 * interestList.get(i); interestDAO.insert(interest); if (i ==
						 * interestList.size() - 1) {
						 * deposit.setNewMaturityAmount(interest.getInterestSum()); deposit =
						 * fixedDepositDAO.updateFD(deposit); } }
						 * 
						 * attributes = updateTransaction("Updated Successfully", attributes); return
						 * "redirect:bankFDSaved";
						 * 
						 * }
						 */

	@RequestMapping(value = "/fdRates", method = RequestMethod.GET)
	public ModelAndView fdRates(ModelMap model) throws CustomException {

		Collection<FDRates> fdRates = fdRatesDAO.findAllFDs();

		Set<FDRates> set = new TreeSet<FDRates>(new Comparator<FDRates>() {
			public int compare(FDRates o1, FDRates o2) {

				if (o1.getFdId().equals(o2.getFdId())) {
					return 0;
				} else {
					return 1;
				}
			}
		});

		set.addAll(fdRates);
		fdRates.clear();
		fdRates.addAll(set);

		ModelAndView mav = new ModelAndView();

		if (fdRates != null && fdRates.size() > 0) {

			fdRatesForm.setFdRates(fdRates);
			model.put("fdRatesForm", fdRatesForm);
			return new ModelAndView("listOfFDRates", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/getFDRates", method = RequestMethod.POST)
	public ModelAndView getFDRates(ModelMap model, @ModelAttribute FDRatesForm fdRatesForm) throws CustomException {

		Collection<FDRates> fdRates = fdRatesDAO.getFDRatesData(fdRatesForm.getFdId());
		ModelAndView mav = new ModelAndView();

		if (fdRates != null && fdRates.size() > 0) {

			model.put("fdRates", fdRates);
			return new ModelAndView("fdRatesData", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/getReport", method = RequestMethod.POST)
	public ModelAndView getReport(@ModelAttribute FixedDepositForm fixedDepositForm, ModelMap model)
			throws CustomException {

		ModelAndView mav = new ModelAndView();
		Collection<Transaction> transaction = transactionDAO.getFdData(fixedDepositForm.getFdID());
		if (transaction != null && transaction.size() > 0) {

			model.put("transaction", transaction);
			mav = new ModelAndView("getReportBank", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}
		return mav;
	}

	@RequestMapping(value = "/closeFDList", method = RequestMethod.GET)
	public ModelAndView closeFDList(ModelMap model, RedirectAttributes attributes) throws CustomException {

		ModelAndView mav = new ModelAndView();

		Collection<Deposit> fixedDeposits = fixedDepositDAO.getApprovedDeposits();
		if (fixedDeposits != null && fixedDeposits.size() > 0) {

			model.put("fixedDeposits", fixedDeposits);
			mav = new ModelAndView("bankclosingFDList", "model", model);

		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/closeFD", method = RequestMethod.GET)
	public ModelAndView closeFD(@RequestParam("id") Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {
		Deposit deposit = fixedDepositDAO.getDeposit(id);
		if (deposit != null) {
			if (deposit.getProductConfigurationId() != null) {
				ProductConfiguration productConfiguration = productConfigurationDAO
						.findById(deposit.getProductConfigurationId());

				if (productConfiguration == null) {
					productConfiguration = productConfigurationDAO
							.findByProductCode(deposit.getProductConfigurationId().toString());
				}
				if (productConfiguration != null) {
					if (productConfiguration.getIsPrematureClosingioAllowedForWithdraw() != null) {
						int isEligibleForClose = productConfiguration.getIsPrematureClosingioAllowedForWithdraw();
						if (isEligibleForClose == 1) {
							calculationService.closeDeposit(deposit, true);
						} else {

							attributes.addFlashAttribute("error",
									"Premature closing is not allowed for this deposit. You can not close the deposit.");
							return new ModelAndView("redirect:closeFDList");
							// MSG : Premature closing is not allowed for this deposit. You can not close
							// the deposit.

						}
					} else {
						attributes.addFlashAttribute("error", "Is Premature data not found");
						return new ModelAndView("redirect:closeFDList");
					}

				} else {
					attributes.addFlashAttribute("error", "Product not found");
					return new ModelAndView("redirect:closeFDList");
					// product not found

				}
			} else {
				attributes.addFlashAttribute("error", "Product id not found");
				return new ModelAndView("redirect:closeFDList");
				// product not found

			}

		} else {
			attributes.addFlashAttribute("error", "Deposit not found");
			return new ModelAndView("redirect:closeFDList");
			// Deposit not found
		}

		attributes = updateTransaction("Closed Successfully", attributes);
		return new ModelAndView("redirect:bankFDSaved");

	}

	/**
	 * Method to confirm with draw data
	 * 
	 * @param fixedDepositForm
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/confirmWithDrawData", method = RequestMethod.POST)
	public ModelAndView confirmWithDrawData(@ModelAttribute FixedDepositForm fixedDepositForm, ModelMap model)
			throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("bankconfirmWithDrawData", "model", model);
	}

	/*****
	 * Joint Deposit creation
	 * 
	 * @throws IOException          =
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 *************/

@RequestMapping(value = "/jointConsortiumDeposit", method = RequestMethod.POST)
	public ModelAndView jointConsortiumDeposit(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String nriAccType, HttpServletRequest request, @RequestParam String customerDetails,
			@RequestParam String productId) throws JsonParseException, JsonMappingException, IOException {
		int removeIndexOfList = -1;
		int loopIndex = 0;
		Long pId = Long.parseLong(productId);
		Customer customer = customerDAO.getById(fixedDepositForm.getId());
		nriAccType = customer.getNriAccountType();
		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(fixedDepositForm.getId());
		List<ModeOfPayment> paymentList = modeOfPaymentDAO.getAllModeOfPaymentEmployee();
		String customerDetailsJSON = customerDetails;

		CustomerDetailsParser customerDetailsParser = new Gson().fromJson(customerDetailsJSON,
				CustomerDetailsParser.class);

		List<CustomerDetailsParser> customerDetailsParsers = customerDetailsParser.getData();

		for (CustomerDetailsParser customerDetailsParser2 : customerDetailsParsers) {

			if (Boolean.parseBoolean(customerDetailsParser2.getIsPrimaryHolder()) != true) {
				Customer customerJsonParse = customerDAO.getById(Long.parseLong(customerDetailsParser2.getId()));
				customerDetailsParser2.setAddress(customerJsonParse.getAddress());
				customerDetailsParser2.setCustomerName(customerJsonParse.getCustomerName());
				customerDetailsParser2.setContactNum(customerJsonParse.getContactNum());
				customerDetailsParser2.setGender(customerJsonParse.getGender());
				customerDetailsParser2.setEmail(customerJsonParse.getEmail());

			} else {
				removeIndexOfList = loopIndex;

			}
			loopIndex++;
		}
		if (removeIndexOfList != -1)
			customerDetailsParsers.remove(removeIndexOfList);
		Double accountBal = 0.0;
		for (int i = 0; i < accountList.size(); i++) {

			accountBal = accountList.get(i).getAccountBalance();
		}

		fixedDepositForm.setAccountList(accountList);
		fixedDepositForm.setNriAccountType(customer.getNriAccountType());
		fixedDepositForm.setCitizen(customer.getCitizen());

		//
		// HashMap<Long, String> customerDetails =
		// fixedDepositForm.getCustomerDetails();
		if (fixedDepositForm.getJointAccounts() == null) {
			List<JointAccount> jointAc = new ArrayList<JointAccount>();
			fixedDepositForm.setJointAccounts(jointAc);
		}

		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		List<Branch> branch = branchDAO.getAllBranches();
		model.put("branch", branch);

		model.put("nriAccType", nriAccType);
		model.put("accountBal", accountBal);
		model.put("todaysDate", date);
		model.put("fixedDepositForm", fixedDepositForm);
		model.put("customerDetailsParsers", customerDetailsParsers);
		model.put("customerDetails", customerDetails);
		model.put("customer", customer);

		model.put("paymentMode", paymentList);
		
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(pId);
		model.put("productConfiguration", productConfiguration);
		return new ModelAndView("jointConsortiumDepositBankEmp", "model", model);

	}


@RequestMapping(value = "/jointConsortiumDepositSummary", method = RequestMethod.POST)
public ModelAndView jointConsortiumDepositSummary(@ModelAttribute FixedDepositForm fixedDepositForm,
		@RequestParam String customerDetails, ModelMap model, HttpServletRequest request,
		RedirectAttributes attributes) throws CustomException {

	/*
	 * Long getcustomerByPanCard =
	 * customerDAO.getNomineeByPanCard(fixedDepositForm.getNomineePan()); Long
	 * getcustomerByAadharCard =
	 * customerDAO.getNomineeByAadharCard(fixedDepositForm.getNomineeAadhar( ));
	 */
	Long getJointcustomerByPanCard = 0l;
	Long getJointcustomerByAadharCard = 0l;
	Integer taxSavingDeposit = fixedDepositForm.getTaxSavingDeposit() == null ? 0 : 1;
	if (taxSavingDeposit == 1) {
		fixedDepositForm.setDepositClassification(Constants.taxSavingDeposit);

	}
	if (taxSavingDeposit == 0 && fixedDepositForm.getPaymentType().equals("One-Time")) {
		fixedDepositForm.setDepositClassification(Constants.fixedDeposit);

	}
	if (taxSavingDeposit == 0 && !fixedDepositForm.getPaymentType().equals("One-Time")) {
		fixedDepositForm.setDepositClassification(Constants.recurringDeposit);

	}
	fixedDepositForm.setTaxSavingDeposit(Integer.toString(taxSavingDeposit));
	if (taxSavingDeposit.equals(1)) {

		fixedDepositForm.setPaymentType("One-Time");
		fixedDepositForm.setFdTenure(5);
		fixedDepositForm.setFdTenureType(Constants.YEAR);
	}
	/*
	 * if (getcustomerByPanCard != 0) { model.put("errorPan",
	 * Constants.PanCardExist); return new
	 * ModelAndView("jointConsortiumDepositBankEmp", "model", model); }
	 * 
	 * if (getcustomerByAadharCard != 0) { model.put("errorAadhar",
	 * Constants.aadharCardExist); return new
	 * ModelAndView("jointConsortiumDepositBankEmp", "model", model); }
	 */
	if (fixedDepositForm.getNomineeName() != "" && fixedDepositForm.getNomineeName() != null) {
		for (int i = 0; i < fixedDepositForm.getJointAccounts().size(); i++) {

			/**** Saving DepositHolderNominee ****/

			DepositHolderNominees nomineeJoint = new DepositHolderNominees();
			getJointcustomerByPanCard = customerDAO
					.getNomineeByPanCard(fixedDepositForm.getJointAccounts().get(i).getNominee().getPanNo());
			getJointcustomerByAadharCard = customerDAO
					.getNomineeByAadharCard(fixedDepositForm.getJointAccounts().get(i).getNominee().getAadharNo());

			nomineeJoint.setNomineePan(
					fixedDepositForm.getJointAccounts().get(i).getNominee().getPanNo().toUpperCase());
			nomineeJoint.setNomineeAadhar(fixedDepositForm.getJointAccounts().get(i).getNominee().getAadharNo());

			int ageJoint = Integer
					.parseInt(fixedDepositForm.getJointAccounts().get(i).getNominee().getAge().toString());
			if (ageJoint < 18) {

				nomineeJoint.setGaurdianPan(
						fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianPanNo().toUpperCase());
				nomineeJoint.setGaurdianAadhar(
						fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianAadharNo());
			}

		}
	}

	/*
	 * if (getJointcustomerByPanCard != 0) { model.put("errorPanJoint",
	 * Constants.PanCardExist); return new
	 * ModelAndView("jointConsortiumDepositBankEmp", "model", model); }
	 * 
	 * if (getJointcustomerByAadharCard != 0) { model.put("errorAadharJoint",
	 * Constants.aadharCardExist); return new
	 * ModelAndView("jointConsortiumDepositBankEmp", "model", model); }
	 */

	Integer days = 0;
	Date currentDate = DateService.getCurrentDate();
	Date maturityDate = null;

	/*
	 * if (fixedDepositForm.getFdTenureType().equalsIgnoreCase("Month")) {
	 * 
	 * days = fixedDepositForm.getFdTenure() * 30; maturityDate =
	 * DateService.generateMonthsDate(currentDate, fixedDepositForm.getFdTenure());
	 * 
	 * } else if (fixedDepositForm.getFdTenureType().equalsIgnoreCase("Year")) {
	 * 
	 * days = fixedDepositForm.getFdTenure() * 365; maturityDate =
	 * DateService.generateYearsDate(currentDate, fixedDepositForm.getFdTenure()); }
	 * else if (fixedDepositForm.getFdTenureType().equalsIgnoreCase("Day")) {
	 * 
	 * days = fixedDepositForm.getFdTenure(); maturityDate =
	 * DateService.generateDaysDate(currentDate, fixedDepositForm.getFdTenure()); }
	 */

	days = fixedDepositForm.getTotalTenureInDays();

	maturityDate = DateService.generateDaysDate(DateService.getCurrentDate(),
			fixedDepositForm.getTotalTenureInDays());

	days = days + 1;

	// Get the customer details to find out the age and category
	Customer customer = getCustomerDetails(fixedDepositForm.getId());
	String category = calculationService.geCustomerActualCategory(customer);// fixedDepositForm.getCategory();

	Float rateOfInterest = calculationService.getDepositInterestRate(days, category, fixedDepositForm.getCurrency(),
			fixedDepositForm.getFdAmount(), fixedDepositForm.getDepositClassification(),
			fixedDepositForm.getCitizen(), fixedDepositForm.getNriAccountType());

	if (rateOfInterest == null) {
		attributes.addFlashAttribute(Constants.ERROR, Constants.invalidTenure);

		return new ModelAndView("redirect:jointConsortiumDeposit");

	}

	/* Estimated Interest, TDS and Maturity calculation */

	fixedDepositForm.setMaturityDate(maturityDate);
	fixedDepositForm.setCategory(customer.getCategory());

	Date todaysDate = DateService.getCurrentDate();

//	List<Interest> interestList = fdService.getInterestBreakup(todaysDate, maturityDate, fixedDepositForm,
//			rateOfInterest, 0);

//	Double totalDepositAmount = fdService.getTotalDepositAmount(fixedDepositForm,
//			fixedDepositForm.getMaturityDate(), fixedDepositForm.getFdAmount());
//	Float totalInterest = fdService.getTotalInterestAmount(interestList);
	Float totalTDS = 0f;
	// Float totalTDS = fdService.getTotalTDSAmount(todaysDate,
	// maturityDate, fixedDepositForm.getCategory(),
	// fixedDepositForm, interestList);

	fixedDepositForm.setFdCreditAmount(rateOfInterest);
//	fixedDepositForm.setEstimateInterest(totalInterest);
	fixedDepositForm.setEstimateTDSDeduct(totalTDS);
//	fixedDepositForm.setEstimatePayOffAmount(totalDepositAmount + totalInterest - totalTDS);

	Double maturityAmount = fdService.getExpectedMaturityAmount(currentDate, maturityDate, fixedDepositForm,
			rateOfInterest);
	Double totalInterest = maturityAmount - (fdService.getFutureDepositDates(fixedDepositForm, maturityDate).size()
			* fixedDepositForm.getFdAmount());

	fixedDepositForm.setFdInterest(rateOfInterest);
	fixedDepositForm.setEstimateInterest(Float.parseFloat(((Double) FDService.round(totalInterest, 2)).toString()));
	fixedDepositForm.setEstimatePayOffAmount(FDService.round(maturityAmount, 2));

	if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {
		if (!(fixedDepositForm.getPayOffInterestType().equals("One-Time"))) {
			Date interestPayOffDate = fdService.calculateInterestPayOffDueDate(
					fixedDepositForm.getPayOffInterestType(), fixedDepositForm.getMaturityDate(), currentDate);
			fixedDepositForm.setPayoffDate(interestPayOffDate);
		}
	}

	fixedDepositForm
			.setFdDeductDate(fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));
	model.put("getJointcustomerByPanCard", getJointcustomerByPanCard);
	model.put("getJointcustomerByAadharCard", getJointcustomerByAadharCard);
	model.put("getcustomerByPanCard", getJointcustomerByPanCard);
	model.put("getcustomerByAadharCard", getJointcustomerByAadharCard);
	model.put("fixedDepositForm", fixedDepositForm);
	model.put("customerDetails", customerDetails);
	return new ModelAndView("jointConsortiumDepositSummary", "model", model);
}

@RequestMapping(value = "/savePostJointConsortium", method = RequestMethod.POST)
public String savePostJointConsortium(@ModelAttribute FixedDepositForm fixedDepositForm,
		RedirectAttributes attributes) throws CustomException {

	/******* Saving Deposit *****/
	String transactionId = fdService.generateRandomString();
	Deposit deposit = new Deposit();
	int days = fixedDepositForm.getDaysValue() != null ? fixedDepositForm.getDaysValue() : 0;
	Customer customer = customerDAO.getById(fixedDepositForm.getId());
	deposit.setAccountNumber(fdService.generateRandomString());
	deposit.setDepositAccountType(fixedDepositForm.getDepositAccountType());
	deposit.setAccountAccessType(fixedDepositForm.getAccountAccessType());

	deposit.setDepositAmount(fixedDepositForm.getFdAmount());
	deposit.setDepositArea(fixedDepositForm.getDepositArea());
	deposit.setDepositType(fixedDepositForm.getDepositType());
	// deposit.setDueDate(fdService.calculateDueDate(fixedDepositForm));
	deposit.setDueDate(fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));
	deposit.setFlexiRate("Yes");
	deposit.setInterestRate(fixedDepositForm.getFdCreditAmount());
	deposit.setModifiedInterestRate(fixedDepositForm.getFdCreditAmount());
	if (fixedDepositForm.getAccountNo() != null && !fixedDepositForm.getAccountNo().equals("")) {
		String accArray[] = fixedDepositForm.getAccountNo().split(",");
		deposit.setLinkedAccountNumber(accArray[0]);
	}

	deposit.setMaturityAmount(fixedDepositForm.getEstimatePayOffAmount());
	deposit.setNewMaturityAmount(fixedDepositForm.getEstimatePayOffAmount());
	deposit.setCurrentBalance(Double.valueOf(deposit.getDepositAmount()));
	deposit.setDepositCurrency(fixedDepositForm.getCurrency());
	deposit.setPaymentMode(fixedDepositForm.getDepositForm().getPaymentMode());
	Date date = DateService.getCurrentDateTime();
	deposit.setPaymentType(fixedDepositForm.getPaymentType());
	deposit.setPayOffInterestType(fixedDepositForm.getPayOffInterestType());
	deposit.setStatus(Constants.OPEN);
	deposit = fdService.getMaturityAndTenureInformation(deposit, fixedDepositForm);

	deposit.setCreatedDate(date);
	deposit.setProductConfigurationId(fixedDepositForm.getProductConfigurationId());
	deposit.setModifiedDate(date);
	deposit.setApprovalStatus(Constants.PENDING);
	deposit.setDepositClassification(fixedDepositForm.getDepositClassification());
	deposit.setTaxSavingDeposit(fixedDepositForm.getTaxSavingDeposit());

	deposit.setPrimaryCitizen(fixedDepositForm.getCitizen());
	deposit.setPrimaryCustomerCategory(fixedDepositForm.getCategory());
	if (fixedDepositForm.getCitizen().equalsIgnoreCase(Constants.NRI)) {

		deposit.setNriAccountType(fixedDepositForm.getNriAccountType());
		deposit.setPrimaryNRIAccountType(fixedDepositForm.getNriAccountType());
	}

	/****** / PAY OFF DATE CALCULATION / ******/

	if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {
		deposit.setPayOffDueDate(fixedDepositForm.getPayoffDate());

	}
	EndUser user = getCurrentLoggedUserDetails();

	deposit.setTransactionId(transactionId);
	deposit.setCreatedBy(user.getUserName());
	if (deposit.getPaymentMode().equalsIgnoreCase("DD") || deposit.getPaymentMode().equalsIgnoreCase("Cheque")) {
		deposit.setClearanceStatus(Constants.WAITINGFORCLEARANCE);
	}
	if (fixedDepositForm.getNriAccountType() != null) {
		deposit.setNriAccountType(fixedDepositForm.getNriAccountType());
	}
	deposit.setMaturityInstruction(fixedDepositForm.getMaturityInstruction());
	deposit.setDeductionDay(fixedDepositForm.getDeductionDay());
	deposit.setBranchCode(fixedDepositForm.getBranchCode());
	deposit.setIsMaturityDisbrsmntInLinkedAccount(fixedDepositForm.getIsMaturityDisbrsmntInLinkedAccount());
	Deposit newDeposit = fixedDepositDAO.saveFD(deposit);

	List<DepositHolder> depositHolderList = new ArrayList<>();
	/****** Saving DepositHolder ******/

	Integer isContributionRequired = 0;
	ProductConfiguration productConfiguration = productConfigurationDAO
			.findById(fixedDepositForm.getProductConfigurationId());
	isContributionRequired = productConfiguration.getContributionRequiredForJointAccHolders() != null
			? productConfiguration.getContributionRequiredForJointAccHolders()
			: 0;

	Long depositId = newDeposit.getId();
	String paymentMadeByHolderIds ="";
	DepositHolder depositHolder = new DepositHolder();
	if (isContributionRequired == 1)
		depositHolder.setContribution(fixedDepositForm.getUserContribution());
	else
		depositHolder.setContribution(0f);

	depositHolder.setCustomerId(Long.valueOf(fixedDepositForm.getId()));
	depositHolder.setDepositHolderStatus(Constants.PRIMARY);
	depositHolder.setDepositHolderCategory(fixedDepositForm.getCategory());
	depositHolder.setDepositId(depositId);
	depositHolder.setCitizen(customer.getCitizen());
	if (customer.getCitizen().equalsIgnoreCase(Constants.NRI)) {
		depositHolder.setNriAccountType(customer.getNriAccountType());
	}

	if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {

		depositHolder.setInterestType(fixedDepositForm.getInterstPayType());

		if (fixedDepositForm.getInterstPayType().equals("PERCENT")) {
			depositHolder.setInterestPercent(fixedDepositForm.getInterestPercent());
		} else {
			depositHolder.setInterestAmt(fixedDepositForm.getInterestPayAmount());
		}

		depositHolder.setPayOffAccountType(fixedDepositForm.getFdPayOffAccount());
		depositHolder.setNameOnBankAccount(fixedDepositForm.getOtherName());
		depositHolder.setAccountNumber(fixedDepositForm.getOtherAccount().toString());

		if (fixedDepositForm.getFdPayOffAccount().equals("Other")) {
			depositHolder.setTransferType(fixedDepositForm.getOtherPayTransfer());
			depositHolder.setBankName(fixedDepositForm.getOtherBank());
			depositHolder.setIfscCode(fixedDepositForm.getOtherIFSC());
		}
	}
	

	depositHolder.setIsMaturityDisbrsmntInSameBank(fixedDepositForm.getIsMaturityDisbrsmntInSameBank());
	
	depositHolder.setMaturityDisbrsmntAccHolderName(fixedDepositForm.getMaturityDisbrsmntAccHolderName());
	
	depositHolder.setMaturityDisbrsmntAccNumber(fixedDepositForm.getMaturityDisbrsmntAccNumber());
	
	depositHolder.setMaturityDisbrsmntTransferType(fixedDepositForm.getMaturityDisbrsmntTransferType());
	
	depositHolder.setMaturityDisbrsmntBankName(fixedDepositForm.getMaturityDisbrsmntBankName());

	depositHolder.setMaturityDisbrsmntBankIFSCCode(fixedDepositForm.getMaturityDisbrsmntBankIFSCCode());
	

	
	DepositHolder depositHolderNew = depositHolderDAO.saveDepositHolder(depositHolder);
	paymentMadeByHolderIds = depositHolderNew.getId().toString();
	depositHolderList.add(depositHolderNew);
	// Creating a HashSet for holding depositHolder reference in Deposit
	// class

	Set<DepositHolder> set = new HashSet<DepositHolder>();
	set.add(depositHolder);

	for (int i = 0; i < fixedDepositForm.getJointAccounts().size(); i++) {

		/****** Saving DepositHolder ******/

		DepositHolder depositHolderJoint = new DepositHolder();
		depositHolderJoint.setRelationship(fixedDepositForm.getJointAccounts().get(i).getRelationship());

		if (isContributionRequired == 1)
			depositHolderJoint.setContribution(fixedDepositForm.getJointAccounts().get(i).getContributionPercent());
		else
			depositHolderJoint.setContribution(0f);

		depositHolderJoint.setCustomerId(fixedDepositForm.getJointAccounts().get(i).getId());

		Customer secondaryCustomer = customerDAO.getById(fixedDepositForm.getJointAccounts().get(i).getId());
		depositHolderJoint.setCitizen(secondaryCustomer.getCitizen());
		if (secondaryCustomer.getCitizen().equalsIgnoreCase(Constants.NRI)) {
			depositHolderJoint.setNriAccountType(secondaryCustomer.getNriAccountType());
		}
		/////////		
		depositHolderJoint.setIsMaturityDisbrsmntInSameBank(fixedDepositForm.getJointAccounts().get(i).getIsMaturityDisbrsmntInSameBank());
		
		depositHolderJoint.setMaturityDisbrsmntAccHolderName(fixedDepositForm.getJointAccounts().get(i).getMaturityDisbrsmntAccHolderName());
		
		depositHolderJoint.setMaturityDisbrsmntAccNumber(fixedDepositForm.getJointAccounts().get(i).getMaturityDisbrsmntAccNumber());
		
		depositHolderJoint.setMaturityDisbrsmntTransferType(fixedDepositForm.getJointAccounts().get(i).getMaturityDisbrsmntTransferType());
		
		depositHolderJoint.setMaturityDisbrsmntBankName(fixedDepositForm.getJointAccounts().get(i).getMaturityDisbrsmntBankName());

		depositHolderJoint.setMaturityDisbrsmntBankIFSCCode(fixedDepositForm.getJointAccounts().get(i).getMaturityDisbrsmntBankIFSCCode());
		

		
		//////

		String depositHolderCategory = customerDAO.getById(depositHolderJoint.getCustomerId()).getCategory();
		depositHolderJoint.setDepositHolderStatus(Constants.SECONDARY);
		depositHolderJoint.setDepositId(depositId);
		depositHolderJoint.setDepositHolderCategory(depositHolderCategory);

		if (fixedDepositForm.getPayOffInterestType() != null
				&& !fixedDepositForm.getPayOffInterestType().equals("")) {

			depositHolderJoint.setInterestType(
					fixedDepositForm.getJointAccounts().get(i).getContributions().getPaymentType());

			if (fixedDepositForm.getJointAccounts().get(i).getContributions().getPaymentType().equals("PERCENT")) {
				depositHolderJoint.setInterestPercent(
						fixedDepositForm.getJointAccounts().get(i).getContributions().getInterestPercentage());
			}

			else {
				depositHolderJoint.setInterestAmt(
						fixedDepositForm.getJointAccounts().get(i).getContributions().getInterestAmtPart());
			}

			depositHolderJoint.setPayOffAccountType(
					fixedDepositForm.getJointAccounts().get(i).getContributions().getPayOffAccPartOne());

			depositHolderJoint.setNameOnBankAccount(
					fixedDepositForm.getJointAccounts().get(i).getContributions().getBeneficiaryOne());
			depositHolderJoint.setAccountNumber(fixedDepositForm.getJointAccounts().get(i).getContributions()
					.getBeneficiaryAccOne().toString());

			if (depositHolderJoint.getPayOffAccountType().equals("Other")) {
				depositHolderJoint.setTransferType(
						fixedDepositForm.getJointAccounts().get(i).getContributions().getTransferModeOne());
				depositHolderJoint.setBankName(
						fixedDepositForm.getJointAccounts().get(i).getContributions().getBeneficiaryBankOne());
				depositHolderJoint.setIfscCode(
						fixedDepositForm.getJointAccounts().get(i).getContributions().getBeneficiaryIFSCOne());
			}
		}
		DepositHolder depositHolderJointNew = depositHolderDAO.saveDepositHolder(depositHolderJoint);
		paymentMadeByHolderIds = paymentMadeByHolderIds + "," + depositHolderJointNew.getId().toString();
		depositHolderList.add(depositHolderJointNew);

		// add the deposit holder to the set else it will not update deposit id in
		// deposit holder table
		set.add(depositHolderJointNew);

		/****** Saving DepositHolderNominee ******/

		if (fixedDepositForm.getJointAccounts().get(i).getNominee() != null
				&& fixedDepositForm.getJointAccounts().get(i).getNominee().getName() != null
				&& fixedDepositForm.getJointAccounts().get(i).getNominee().getName() != "") {
			DepositHolderNominees nomineeJoint = new DepositHolderNominees();
			nomineeJoint.setNomineeName(fixedDepositForm.getJointAccounts().get(i).getNominee().getName());
			nomineeJoint.setNomineeAge(fixedDepositForm.getJointAccounts().get(i).getNominee().getAge().toString());
			nomineeJoint.setNomineeRelationship(
					fixedDepositForm.getJointAccounts().get(i).getNominee().getRelationship());
			nomineeJoint.setNomineeAddress(fixedDepositForm.getJointAccounts().get(i).getNominee().getAddress());
			nomineeJoint.setNomineePan(
					fixedDepositForm.getJointAccounts().get(i).getNominee().getPanNo().toUpperCase());
			nomineeJoint.setNomineeAadhar(fixedDepositForm.getJointAccounts().get(i).getNominee().getAadharNo());

			nomineeJoint.setDepositHolderId(depositHolderJointNew.getId());

			int ageJoint = Integer
					.parseInt(fixedDepositForm.getJointAccounts().get(i).getNominee().getAge().toString());
			if (ageJoint < 18) {
				nomineeJoint
						.setGaurdianName(fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianName());
				nomineeJoint.setGaurdianAge(
						Float.valueOf(fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianAge()));
				nomineeJoint.setGaurdianAddress(
						fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianAddress());
				nomineeJoint.setGaurdianRelation(
						fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianRelation());
				nomineeJoint.setGaurdianPan(
						fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianPanNo().toUpperCase());
				nomineeJoint.setGaurdianAadhar(
						fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianAadharNo());
			}

			nomineeDAO.saveNominee(nomineeJoint);
		}

	}

	newDeposit.setDepositHolder(set);
	newDeposit.setProductConfigurationId(fixedDepositForm.getProductConfigurationId());
	fixedDepositDAO.updateFD(newDeposit);

	// add the hasset to the newly created deposit

	/****** Saving DepositHolderNominee ******/

	if (fixedDepositForm.getNomineeName() != null && fixedDepositForm.getNomineeName() != "") {
		DepositHolderNominees nominee = new DepositHolderNominees();

		nominee.setNomineeName(fixedDepositForm.getNomineeName());
		nominee.setNomineeAge(fixedDepositForm.getNomineeAge());
		nominee.setNomineeRelationship(fixedDepositForm.getNomineeRelationShip());
		nominee.setNomineeAddress(fixedDepositForm.getNomineeAddress());
		nominee.setDepositHolderId(depositHolderNew.getId());
		nominee.setNomineeAadhar(fixedDepositForm.getNomineeAadhar());
		nominee.setNomineePan(fixedDepositForm.getNomineePan().toUpperCase());
		int age = Integer.parseInt(fixedDepositForm.getNomineeAge());
		if (age < 18) {
			nominee.setGaurdianName(fixedDepositForm.getGuardianName());
			nominee.setNomineeAge(fixedDepositForm.getNomineeAge());
			nominee.setGaurdianAddress(fixedDepositForm.getGuardianAddress());
			nominee.setGaurdianRelation(fixedDepositForm.getGuardianRelationShip());
			nominee.setGaurdianAadhar(fixedDepositForm.getGaurdianAadhar());
			nominee.setGaurdianPan(fixedDepositForm.getGaurdianPan().toUpperCase());
		}
		nomineeDAO.saveNominee(nominee);
	}

	/****** Deduction from Linked account ******/
	AccountDetails accountDetails = null;
	if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {
		accountDetails = accountDetailsDAO.findByAccountNo(fixedDepositForm.getDepositForm().getLinkedAccountNo());
		accountDetails.setAccountBalance(
				accountDetails.getAccountBalance() - Float.valueOf(fixedDepositForm.getDepositForm().getFdPay()));
		accountDetailsDAO.updateUserAccountDetails(accountDetails);
	}

	/****** SAVING PAYMENT INFO ******/

	String fromAccountNo = "";
	String fromAccountType = "";

	Payment payment = new Payment();
	payment.setDepositId(depositId);

	Double amountPaid = FDService.round(Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()), 2);
	payment.setAmountPaid(amountPaid);

	if (fixedDepositForm.getDepositForm().getPaymentMode().contains("Cash")
			|| fixedDepositForm.getDepositForm().getPaymentMode().contains("cash")) {
		fromAccountNo = "";
		fromAccountType = "Cash Account";
	}

	if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("DD")
			|| fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("Cheque")) {

		payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
		payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
		payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
		payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());

		// For Accounting Entries Only
		fromAccountNo = fixedDepositForm.getDepositForm().getChequeNo();
		fromAccountType = fixedDepositForm.getDepositForm().getPaymentMode();
	}

	if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("Card Payment")) {

		String[] cardNoArray = fixedDepositForm.getDepositForm().getCardNo().split("-");
		String cardNo = "";
		for (int i = 0; i < cardNoArray.length; i++) {
			cardNo = cardNo + cardNoArray[i];
		}
		payment.setCardNo(Long.valueOf(cardNo));
		payment.setCardType(fixedDepositForm.getDepositForm().getCardType());

		payment.setCardCvv(fixedDepositForm.getDepositForm().getCvv());
		payment.setCardExpiryDate(fixedDepositForm.getDepositForm().getExpiryDate());

		// For Accounting Entries Only
		fromAccountNo = cardNo;
		fromAccountType = fixedDepositForm.getDepositForm().getCardType();

	}

	if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {

		payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getAccountType());
		String accountNo = fixedDepositForm.getAccountNo();
		if (accountNo.contains(",")) {
			accountNo = accountNo.substring(0, accountNo.indexOf(","));
		}
		payment.setLinkedAccNoForFundTransfer(accountNo);

		if (accountDetails != null)
			payment.setLinkedAccTypeForFundTransfer(accountDetails.getAccountType());

		// For Accounting Entries Only
		fromAccountNo = accountNo;
		fromAccountType = accountDetails.getAccountType();
	}

	payment.setDepositHolderId(calculationService.getPrimaryHolderId(depositHolderList));
	payment.setPaymentMadeByHolderIds(paymentMadeByHolderIds);
	//payment.setModeOfPaymentId(fixedDepositForm.getDepositForm().getPaymentMode());
	payment.setPaymentDate(date);
	payment.setTransactionId(transactionId);
	payment.setCreatedBy(getCurrentLoggedUserName());
	payment = paymentDAO.insertPayment(payment);

	// Insert in Distribution and holderWiseDistribution
	calculationService.insertInPaymentDistribution(newDeposit, depositHolderList, amountPaid, payment.getId(),
			Constants.PAYMENT, null, null, null, null, null, null, null);

	// Insert in DepositSummary and DepositHolderWiseSummary
	DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.PAYMENT,
			amountPaid, null, null, null, null, depositHolderList, null, null, null);

	// Insert in Journal & Ledger
	// --------------------------------------------------------------------
	if (fixedDepositForm.getDepositForm().getPaymentMode().contains("Cash"))
		fromAccountType = "Cash";

	/*ledgerService.insertJournal(newDeposit.getId(), customer.getId(), DateService.getCurrentDate(),
			fromAccountNo, newDeposit.getAccountNumber(), Event.K00101.getValue(),
			amountPaid, fixedDepositForm.getDepositForm().getPaymentMode(), depositSummary.getTotalPrincipal(), transactionId);*/
	
//	ledgerService.insertJournalLedger(newDeposit.getId(), customer.getId(), DateService.getCurrentDate(),
//			fromAccountNo, fromAccountType, newDeposit.getAccountNumber(), "Deposit Account", "Payment", amountPaid,
//			fixedDepositForm.getDepositForm().getPaymentMode(), depositSummary.getTotalPrincipal());
	// --------------------------------------------------------------------

	attributes = updateTransaction("Saved Successfully", attributes);
	return "redirect:bankFDSaved";

}


//	@RequestMapping(value = "/searchSecondaryCustomer", method = RequestMethod.POST)
//	public ModelAndView searchSecondaryCustomer(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
//			throws CustomException {
//		/* searching secondary customer */
//
//		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
//		customerList = customerDAO.searchCustomer(fixedDepositForm.getCustomerID(),fixedDepositForm.getCustomerName(), fixedDepositForm.getContactNum(),
//				fixedDepositForm.getEmail());
//
//		model.put("customerList", customerList);
//
//		/* for primary customer */
//
//		Customer customer = customerDAO.getById(fixedDepositForm.getId());
//
//		List<AccountDetails> accountList = accountDetailsDAO.findByCustomerId(fixedDepositForm.getId());
//		fixedDepositForm.setAccountList(accountList);
//
//		fixedDepositForm.setNriAccountType(fixedDepositForm.getNriAccountType());
//		if (fixedDepositForm.getJointAccounts() == null) {
//			List<JointAccount> jointAcc = new ArrayList<JointAccount>();
//			fixedDepositForm.setJointAccounts(jointAcc);
//		}
//
//		BankConfiguration bankConfiguration = ratesDAO.findAll();
//		if (bankConfiguration != null) {
//			model.put("bankConfiguration", bankConfiguration);
//
//		}
//		model.put("customer", customer);
//		model.put("fixedDepositForm", fixedDepositForm);
//		model.put("cashPayment", 1);
//		model.put("ddPayment", 1);
//		model.put("chequePayment", 1);
//		model.put("netBanking", 0);
//
//		return new ModelAndView("jointConsortiumDepositBankEmp", "model", model);
//
//	}

//	@RequestMapping(value = "/addSecondary", method = RequestMethod.POST)
//	public ModelAndView addSecondary(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
//			throws CustomException {
//
//		/* for primary customer */
//		Customer customer = customerDAO.getById(fixedDepositForm.getId());
//
//		List<AccountDetails> accountList = accountDetailsDAO.findByCustomerId(fixedDepositForm.getId());
//		fixedDepositForm.setAccountList(accountList);
//
////		BankConfiguration bankConfiguration = ratesDAO.findAll();
////		if (bankConfiguration != null) {
////			model.put("bankConfiguration", bankConfiguration);
////		}
//
//		/* secondary customer to add */
//		Customer secondaryCustomer = customerDAO.getById(fixedDepositForm.getJointAccount().getId());
//
//		JointAccount jointAcc = new JointAccount();
//		List<JointAccount> jointAccList = new ArrayList<JointAccount>();
//
//		if (fixedDepositForm.getDepositType().equals(Constants.JOINT)) {
//
//			jointAcc.setId(secondaryCustomer.getId());
//			jointAcc.setName(secondaryCustomer.getCustomerName());
//			jointAcc.setAddress(secondaryCustomer.getAddress());
//			jointAcc.setAge(secondaryCustomer.getAge());
//			jointAcc.setCity(secondaryCustomer.getCity());
//			jointAcc.setContactNo(secondaryCustomer.getContactNum());
//			jointAcc.setGender(secondaryCustomer.getGender());
//			jointAcc.setEmail(secondaryCustomer.getEmail());
//			jointAcc.setPincode(secondaryCustomer.getPincode());
//			jointAcc.setCountry(secondaryCustomer.getCountry());
//			jointAcc.setState(secondaryCustomer.getState());
//
//			jointAccList.add(jointAcc);
//
//		}
//
//		else if (fixedDepositForm.getDepositType().equals(Constants.CONSORTIUM)) {
//
//			if (fixedDepositForm.getJointAccounts() == null) {
//				fixedDepositForm.setJointAccounts(jointAccList);
//			}
//
//			jointAccList = fixedDepositForm.getJointAccounts();
//
//			jointAcc.setId(secondaryCustomer.getId());
//			jointAcc.setName(secondaryCustomer.getCustomerName());
//			jointAcc.setAddress(secondaryCustomer.getAddress());
//			jointAcc.setAge(secondaryCustomer.getAge());
//			jointAcc.setCity(secondaryCustomer.getCity());
//			jointAcc.setContactNo(secondaryCustomer.getContactNum());
//			jointAcc.setGender(secondaryCustomer.getGender());
//			jointAcc.setEmail(secondaryCustomer.getEmail());
//			jointAcc.setPincode(secondaryCustomer.getPincode());
//			jointAcc.setCountry(secondaryCustomer.getCountry());
//			jointAcc.setState(secondaryCustomer.getState());
//
//			jointAccList.add(jointAcc);
//
//		}
//
//		fixedDepositForm.setJointAccounts(jointAccList);
//
//		model.put("fixedDepositForm", fixedDepositForm);
//		model.put("cashPayment", 1);
//		model.put("ddPayment", 1);
//		model.put("chequePayment", 1);
//		model.put("netBanking", 0);
//		model.put("customer", customer);
//
//		return new ModelAndView("jointConsortiumDepositBankEmp", "model", model);
//	}

//	@RequestMapping(value = "/removeAllConsotium", method = RequestMethod.POST)
//	public ModelAndView removeAllConsotium(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
//			throws CustomException {
//
//		/* for removing selected consortium customer */
//		List<JointAccount> jointAccList = new ArrayList<JointAccount>();
//		fixedDepositForm.setJointAccounts(jointAccList);
//
//		/* for primary customer */
//		Customer customer = customerDAO.getById(fixedDepositForm.getId());
//
//		List<AccountDetails> accountList = accountDetailsDAO.findByCustomerId(fixedDepositForm.getId());
//		fixedDepositForm.setAccountList(accountList);
//
//		BankConfiguration bankConfiguration = ratesDAO.findAll();
//		if (bankConfiguration != null) {
//			model.put("bankConfiguration", bankConfiguration);
//		}
//
//		if (fixedDepositForm.getJointAccounts() == null) {
//			List<JointAccount> jointAc = new ArrayList<JointAccount>();
//			fixedDepositForm.setJointAccounts(jointAc);
//		}
//
//		model.put("cashPayment", 1);
//		model.put("ddPayment", 1);
//		model.put("chequePayment", 1);
//		model.put("netBanking", 0);
//		model.put("customer", customer);
//		model.put("fixedDepositForm", fixedDepositForm);
//		return new ModelAndView("jointConsortiumDepositBankEmp", "model", model);
//	}

//	@RequestMapping(value = "/removeSecondaryCustomer", method = RequestMethod.POST)
//	public ModelAndView removeSecondaryCustomer(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
//			Long id, Long customerId) throws CustomException {
//
//		/* for primary customer */
//
//		Customer customer = customerDAO.getById(customerId);
//		fixedDepositForm.setId(customerId);
//		List<AccountDetails> accountList = accountDetailsDAO.findByCustomerId(fixedDepositForm.getId());
//		fixedDepositForm.setAccountList(accountList);
//
//		/* removing customer */
//
//		List<JointAccount> jointCustomers = fixedDepositForm.getJointAccounts();
//		for (int i = 0; i < jointCustomers.size(); i++) {
//			if ((long) (jointCustomers.get(i).getId()) == (long) id) {
//				jointCustomers.remove(i);
//				break;
//			}
//		}
//		fixedDepositForm.setJointAccounts(jointCustomers);
//
//		BankConfiguration bankConfiguration = ratesDAO.findAll();
//		if (bankConfiguration != null) {
//			model.put("bankConfiguration", bankConfiguration);
//		}
//
//		if (fixedDepositForm.getJointAccounts() == null) {
//			List<JointAccount> jointAc = new ArrayList<JointAccount>();
//			fixedDepositForm.setJointAccounts(jointAc);
//		}
//
//		model.put("cashPayment", 1);
//		model.put("ddPayment", 1);
//		model.put("chequePayment", 1);
//		model.put("netBanking", 0);
//		model.put("customer", customer);
//		model.put("fixedDepositForm", fixedDepositForm);
//
//		return new ModelAndView("jointConsortiumDepositBankEmp", "model", model);
//
//	}

	@RequestMapping(value = "/viewRate")
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

		return new ModelAndView("viewRate", "model", model);

	}

	@RequestMapping(value = "/customerList", method = RequestMethod.GET)
	public ModelAndView customerList(ModelMap model) throws CustomException {

		Collection<Customer> cusList = customerDAO.findAllCustomers();
		model.put("cusList", cusList);

		return new ModelAndView("customerList", "model", model);

	}

	@RequestMapping(value = "/editCustomer", method = RequestMethod.GET)
	public ModelAndView editCustomer(ModelMap model, Long id, @ModelAttribute CustomerForm customerForm)
			throws CustomException {

		CustomerForm accDetails = accountDetailsDAO.editCustomerDetails(id);
		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);

		if (setCategory.size() > 0) {

			model.put("setCategory", setCategory);
			model.put("addRateForm", addRateForm);
			model.put("list", list);

		}
		customerForm.setCategory(accDetails.getCategory());
		customerForm.setCustomerName(accDetails.getCustomerName());
		customerForm.setUserName(accDetails.getUserName());
		customerForm.setGender(accDetails.getGender());
		customerForm.setAge(accDetails.getAge());
		customerForm.setContactNum(accDetails.getContactNum());
		customerForm.setEmail(accDetails.getEmail());
		customerForm.setAltEmail(accDetails.getAltEmail());
		customerForm.setAddress(accDetails.getAddress());
		customerForm.setPincode(accDetails.getPincode());
		customerForm.setCountry(accDetails.getCountry());
		customerForm.setState(accDetails.getState());
		customerForm.setCity(accDetails.getCity());
		customerForm.setNomineeName(accDetails.getNomineeName());
		customerForm.setNomineeAge(accDetails.getNomineeAge());
		customerForm.setNomineeAddress(accDetails.getNomineeAddress());
		customerForm.setNomineeRelationShip(accDetails.getNomineeRelationShip());
		customerForm.setAccountDetails(accDetails.getAccountDetails());
		customerForm.setDateOfBirth(accDetails.getDateOfBirth());
		customerForm.setPan(accDetails.getPan().toUpperCase());
		customerForm.setAadhar(accDetails.getAadhar());
		customerForm.setGuardianName(accDetails.getGuardianName());
		customerForm.setGuardianAge(accDetails.getGuardianAge());
		customerForm.setGuardianAddress(accDetails.getGuardianAddress());
		customerForm.setGuardianRelationShip(accDetails.getGuardianRelationShip());
		customerForm
				.setGuardianPan(accDetails.getGuardianPan() == null ? "" : accDetails.getGuardianPan().toUpperCase());
		customerForm.setGuardianAadhar(accDetails.getGuardianAadhar());
		customerForm.setNriAccountType(accDetails.getNriAccountType());
		customerForm.setCitizen(accDetails.getCitizen());
		model.put("accDetails", accDetails);
		model.put("setCategory", setCategory);
		return new ModelAndView("editCustomer", "model", model);

	}

	@Transactional
	@RequestMapping(value = "/editcustomerPost", method = RequestMethod.POST)
	public ModelAndView editcustomerPost(ModelMap model, @ModelAttribute CustomerForm customerForm,
			RedirectAttributes attribute) throws ParseException, CustomException {
		/*
		 * Long getcustomerCountByPanCardEdit = customerDAO
		 * .getCustomerByPanCardForEdit(customerForm.getPan().toUpperCase(),
		 * customerForm.getId()); Long getcustomerCountByAadharCardEdit =
		 * customerDAO.getCustomerByAadharCardForEdit(customerForm.getAadhar(),
		 * customerForm.getId()); if (getcustomerCountByPanCardEdit > 0) {
		 * model.put("errorPan", Constants.PanCardExist); return new
		 * ModelAndView("redirect:editCustomer?id=" + customerForm.getId()); }
		 * 
		 * if (getcustomerCountByAadharCardEdit > 0) { model.put("errorAadhar",
		 * Constants.aadharCardExist); return new
		 * ModelAndView("redirect:editCustomer?id=" + customerForm.getId()); }
		 */
		/*
		 * EndUser endUSer = endUserDAOImpl.getByUserName(customerForm.getUserName());
		 * 
		 * endUSer.setDisplayName(customerForm.getCustomerName());
		 * endUserDAOImpl.update(endUSer);
		 */

		Customer cus = customerDAO.getById(customerForm.getId());
		cus.setId(customerForm.getId());
		cus.setCategory(customerForm.getCategory());
		cus.setCustomerName(customerForm.getCustomerName());
		cus.setGender(customerForm.getGender());
		cus.setAddress(customerForm.getAddress());
		cus.setCity(customerForm.getCity());
		cus.setPincode(customerForm.getPincode());
		cus.setDateOfBirth(customerForm.getDateOfBirth());
		String country[] = customerForm.getCountry().split(",");
		cus.setCountry(country[0]);
		cus.setState(customerForm.getState());
		cus.setContactNum(customerForm.getContactNum());
		cus.setAltContactNum(customerForm.getAltContactNum());
		cus.setEmail(customerForm.getEmail());
		cus.setAltEmail(customerForm.getAltEmail());
		// cus.setAge(customerForm.getAge());
		cus.setNomineeName(customerForm.getNomineeName());
		cus.setNomineeAge(customerForm.getNomineeAge());
		cus.setNomineeAddress(customerForm.getNomineeAddress());
		cus.setNomineeRelationShip(customerForm.getNomineeRelationShip());
		cus.setPan(customerForm.getPan().toLowerCase().toUpperCase());
		cus.setAadhar(customerForm.getAadhar());
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		String birthYear = df.format(customerForm.getDateOfBirth());
		String currentYear = df.format(DateService.getCurrentDateTime());
		Integer age = Integer.parseInt(currentYear) - Integer.parseInt(birthYear);
		cus.setAge(age.toString());
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(customerForm.getDateOfBirth());
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		String todayDate = smt.format(DateService.getCurrentDateTime());
		Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(todayDate);
		int daysDiff = DateService.getDaysBetweenTwoDates(date1, date2);
		if (daysDiff <= 0) {
			model.put("error", "Selected Date Of Birth is future date please insert correct date");
			return new ModelAndView("editCustomer", "model", model);
		}
		model.put("date1", date1);
		model.put("date2", date2);
		cus.setDateOfBirth(customerForm.getDateOfBirth());
		cus.setGuardianName(customerForm.getGuardianName());
		cus.setGuardianAge(customerForm.getGuardianAge());
		cus.setGuardianAddress(customerForm.getGuardianAddress());
		cus.setGuardianRelationShip(customerForm.getGuardianRelationShip());
		cus.setGuardianPan(customerForm.getGuardianPan() == null ? null : customerForm.getGuardianPan().toUpperCase());
		cus.setGuardianAadhar(customerForm.getGuardianAadhar());
		cus.setNriAccountType(customerForm.getNriAccountType());
		customerDAO.updateUser(cus);
		if (customerForm.getAccountNo() != null) {
			String[] accBal = customerForm.getAccountBalance().split(",");
			String[] accNo = customerForm.getAccountNo().split(",");
			String[] accType = customerForm.getAccountType().split(",");
			String[] minBal = customerForm.getMinimumBalance().split(",");
			int saving = 0;
			int current = 0;
			for (int i = 0; i < accType.length; i++) {
				if (accType[i].equals("Savings")) {
					saving = saving + 1;
					if (saving > 1) {
						attribute.addFlashAttribute(Constants.ERROR, "Multiple Saving Account Error");
						return new ModelAndView("redirect:editCustomer?id=" + customerForm.getId());
					}
				}
				if (accType[i].equals("Current")) {
					current = current + 1;
					if (current > 1) {
						attribute.addFlashAttribute(Constants.ERROR, "Multiple Current Account Error");
						return new ModelAndView("redirect:editCustomer?id=" + customerForm.getId());
					}
				}
			}

			if (customerForm.getAccountId() != null) {
				String[] accId = customerForm.getAccountId().split(",");

				for (int i = 0; i < accId.length; i++) {

					AccountDetails accDetails = accountDetailsDAO.findById(Long.parseLong(accId[i]));
					accDetails.setAccountBalance(Double.valueOf(accBal[i]));
					accDetails.setAccountNo(accNo[i]);
					accDetails.setAccountType(accType[i]);
					accDetails.setMinimumBalance(Double.valueOf(minBal[i]));
					accDetails.setId(Long.valueOf(accId[i]));
					accDetails.setCustomerID(customerForm.getId());
					accDetails.setIsActive(1);

					accountDetailsDAO.updateUserAccountDetails(accDetails);
				}

				if (accId.length < accNo.length) {

					for (int i = accId.length; i < accNo.length; i++) {

						AccountDetails accDetails = new AccountDetails();
						accDetails.setAccountBalance(Double.valueOf(accBal[i]));
						accDetails.setAccountNo(accNo[i]);
						accDetails.setAccountType(accType[i]);
						accDetails.setMinimumBalance(Double.valueOf(minBal[i]));
						accDetails.setIsActive(1);
						accDetails.setCustomerID(customerForm.getId());

						accountDetailsDAO.insertAccountDetails(accDetails);
					}

				}
			}

			else if (customerForm.getAccountId() == null) {

				for (int i = 0; i < accNo.length; i++) {

					AccountDetails accDetails = new AccountDetails();
					accDetails.setAccountBalance(Double.valueOf(accBal[i]));
					accDetails.setAccountNo(accNo[i]);
					accDetails.setAccountType(accType[i]);
					accDetails.setIsActive(1);
					accDetails.setCustomerID(customerForm.getId());

					accountDetailsDAO.insertAccountDetails(accDetails);
				}
			}
		}

		Transaction trans = new Transaction();
		String transactionId = fdService.generateRandomString();
		trans.setTransactionId(transactionId);
		trans.setTransactionStatus(Constants.CUSTOMER);
		trans.setTransactionType(Constants.CUSTOMEREDITED);
		transactionDAO.insertTransaction(trans);

		attribute = updateTransaction("Updated Successfully", attribute);
		return new ModelAndView("redirect:bankFDSaved");

	}

	@RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
	public ModelAndView deleteAccount(Long id, Long customerId) throws CustomException {

		AccountDetails accountDetails = accountDetailsDAO.findById(id);

		accountDetails.setIsActive(0);
		accountDetailsDAO.updateUserAccountDetails(accountDetails);

		return new ModelAndView("redirect:editCustomer?id=" + customerId);

	}

//	@RequestMapping(value = "/bankEmp")
//	public ModelAndView getRateByCategory(ModelMap model, @ModelAttribute RatesForm ratesForm) throws CustomException {
//
//		String category = null;
//		String currency = null;
//		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
//		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
//		for (CustomerCategory customerCategory : setCategory) {
//			category = customerCategory.getCustomerCategory();
//			if (category.equalsIgnoreCase(Constants.regular) || category.equalsIgnoreCase(Constants.general))
//				break;
//		}
//		/*BankConfiguration bankConfiguration = ratesDAO.findAll();
//		if (bankConfiguration == null)
//			throw new CustomException("Please enter the basic confguration from Bank Configuration");*/
//
//		// You did a defauldCurrency that we have used in sweep, here we can fetch from that defaultcurrency   
//		//this method is not used 
//		//you are correct 
//		// shall I delete? yes
//		
//		
//		if (ratesForm.getCurrency() == null)
//			currency = bankConfiguration.getCurrency();
//		else
//			currency = ratesForm.getCurrency();
//
//		// get the from amount slab from the Deposit Rates
//		List<Double> amountFromSlablist = depositRatesDAO.getFromAmountSlabList(category, currency,
//				Constants.fixedDeposit);
//		model.put("amountFromSlablist", amountFromSlablist);
//
//		Double amountSlabFrom = ratesForm.getAmountSlabFrom();
//		if (amountFromSlablist != null && amountFromSlablist.size() > 0) {
//			amountSlabFrom = amountFromSlablist.get(0);
//			// this is temporary basis checking. This needs to remove
//			if (amountSlabFrom == null && amountFromSlablist.size() > 1)
//				amountSlabFrom = amountFromSlablist.get(1);
//
//			if (amountSlabFrom != null) {
//				model.put("selecteAmountFromSlablist", amountSlabFrom);
//			}
//		}
//
//		List<DepositRates> rateList = null;
//		if (category != null && currency != null) {
//			rateList = depositRatesDAO.getRatesByCategory(category, currency, Constants.fixedDeposit, amountSlabFrom,
//					ratesForm.getAmountSlabTo()).getResultList();
//		}
//
//		if (setCategory.size() > 0) {
//			if (category == null)
//				category = list.get(0).getCustomerCategory();
//
//			if (bankConfiguration != null && ratesForm.getCurrency() == null)
//				currency = bankConfiguration.getCurrency();
//			model.put("setCategory", setCategory);
//			model.put("selectedCategory", category);
//			model.put("currency", currency);
//			model.put("bankConfiguration", bankConfiguration);
//			model.put("ratesForm", ratesForm);
//			model.put("rateList", rateList);
//		}
//
//		List<String> depositClassificationList = new ArrayList<>();
//		depositClassificationList.add(Constants.fixedDeposit);
//		depositClassificationList.add(Constants.recurringDeposit);
//		depositClassificationList.add(Constants.taxSavingDeposit);
//		depositClassificationList.add(Constants.annuityDeposit);
//
//		model.put("setCategory", setCategory);
//		model.put("ratesForm", ratesForm);
//
//		model.put("ratesForm", ratesForm);
//		model.put("bankConfiguration", bankConfiguration);
//		model.put("rateList", rateList);
//		model.put("depositClassificationList", depositClassificationList);
//		model.put("loginDate", DateService.loginDate);
//
//		return new ModelAndView("viewRate", "model", model);
//	}

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

		List<Double> amountFromSlablist = depositRatesDAO.getFromAmountSlabList(customerCategory, currency,
				depositClassification);
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
		return new ModelAndView("viewRate", "model", model);
	}

	@RequestMapping(value = "/getAmountToSlab", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Double getAmountToSlab(ModelMap model, @ModelAttribute RatesForm ratesForm,
			String customerCategory, String currency, String depositClassification, Double amountSlabFrom)
			throws CustomException {

		Double toSlab = depositRatesDAO.getToAmountSlab(customerCategory, currency, depositClassification,
				amountSlabFrom);
		return toSlab;

	}

	@RequestMapping(value = "/getAmountFromSlab", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<Double> getAmountFromSlab(ModelMap model, @ModelAttribute RatesForm ratesForm,
			String customerCategory, String currency, String depositClassification, Double amountSlabFrom,
			String citizen, String nriAccountType) throws CustomException {

		// get the from amount slab from the Deposit Rates
		List<Double> amountFromSlablist = depositRatesDAO.getFromAmountSlabListForViewRate(customerCategory, currency,
				depositClassification, citizen, nriAccountType);

		return amountFromSlablist;
	}

//	@RequestMapping(value = "/viewRatesByCategoryAndMaturity", method = RequestMethod.GET)
//	public ModelAndView viewRatesByCategoryAndMaturity(ModelMap model, @ModelAttribute DepositRates depositRates)
//			throws CustomException {
//
//		String currency = null;
//		BankConfiguration bankConfiguration = ratesDAO.findAll();
//		if (bankConfiguration != null)
//			currency = bankConfiguration.getCurrency();
//
//		List<DepositRates> rateList = depositRatesDAO.getRatesByCategory("Regular", currency, Constants.fixedDeposit,
//				ratesForm.getAmountSlabFrom(), ratesForm.getAmountSlabTo()).getResultList();
//		if (rateList == null)
//			rateList = depositRatesDAO.getRatesByCategory("General", currency, Constants.fixedDeposit,
//					ratesForm.getAmountSlabFrom(), ratesForm.getAmountSlabTo()).getResultList();
//		model.put("depositRates", depositRates);
//		model.put("rateList", rateList);
//
//		return new ModelAndView("viewRatesByCategoryAndMaturity", "model", model);
//	}
//
//	@RequestMapping(value = "/viewRatesByCategoryAndMaturityPost", method = RequestMethod.POST)
//	public ModelAndView viewRatesByCategoryAndMaturityPost(ModelMap model, @ModelAttribute DepositRates depositRates)
//			throws CustomException {
//		Float rate = calculationService.getDepositInterestRateForSlab(depositRates.getCalMaturityPeriodFromInDays(),
//				depositRates.getCategory(), depositRates.getCurrency(), depositRates.getAmountSlabFrom(),
//				depositRates.getAmountSlabTo(), depositRates.getDepositClassification(), depositRates.getCitizen(),
//				depositRates.getNriAccountType());
//
//		model.put("ratesForm", ratesForm);
//		model.put("rate", rate);
//		return new ModelAndView("viewRatesByCategoryAndMaturity", "model", model);
//	}

	@RequestMapping(value = "/searchCustomer", method = RequestMethod.POST)
	public ModelAndView searchCustomer(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String depositType) throws CustomException {
		String cusId = fixedDepositForm.getCustomerID();
		String cusName = fixedDepositForm.getCustomerName();
		String cusNum = fixedDepositForm.getContactNum();
		String cusEmail = fixedDepositForm.getEmail();

		// List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		List<CustomerForm> customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);
		model.put("customerList", customerList);
		model.put("fixedDepositForm", fixedDepositForm);

		if (depositType.equals("joint")) {
			return new ModelAndView("applyFDJoint", "model", model);
		} else {
			return new ModelAndView("applyFD", "model", model);
		}

	}

	@RequestMapping(value = "/findCustomer", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<CustomerForm> findCustomer(String customerId, String customerName, String contactNumber,
			String emailId) {
		List<Customer> customerList = customerDAO.findCustomer(customerId, customerName, contactNumber, emailId);

		List<CustomerForm> cusFormList = new ArrayList();
		for (int i = 0; i < customerList.size(); i++) {
			CustomerForm cusForm = new CustomerForm();
			cusForm.setId(Long.valueOf(customerList.get(i).getId()));
			cusForm.setCustomerName(customerList.get(i).getCustomerName());
			cusForm.setEmail(customerList.get(i).getEmail());
			cusForm.setContactNum(customerList.get(i).getContactNum());
			cusForm.setDateOfBirth(customerList.get(i).getDateOfBirth());
			cusForm.setCitizen(customerList.get(i).getCitizen());
			cusForm.setNriAccountType(customerList.get(i).getNriAccountType());
			cusFormList.add(cusForm);

		}
		return cusFormList;
	}

	@RequestMapping(value = "/findCustomerById", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody CustomerForm findCustomerById(Long customerId) {
		Customer customer = customerDAO.getById(customerId);

		if (customer == null)
			return null;

		CustomerForm cusForm = new CustomerForm();
		cusForm.setId(Long.valueOf(customer.getId()));
		cusForm.setCustomerName(customer.getCustomerName());
		cusForm.setNriAccountType(customer.getNriAccountType());
		cusForm.setEmail(customer.getEmail());
		cusForm.setContactNum(customer.getContactNum());
		cusForm.setDateOfBirth(customer.getDateOfBirth());
		cusForm.setAddress(customer.getAddress());
		cusForm.setGender(customer.getGender());

		return cusForm;
	}

	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public ModelAndView payment(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("fdPayment", "model", model);

	}

	@RequestMapping(value = "/depositPayment", method = RequestMethod.POST)
	public ModelAndView depositPayment(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {

		Deposit deposit = fixedDepositDAO.getByAccountNumber(fixedDepositForm.getAccountNo().trim());
		if (deposit == null) {
			model.put("error", "Account Number is not Correct OR this deposit has been closed.");
			return new ModelAndView("fdPayment", "model", model);
		}

		ProductConfiguration productConfiguration = productConfigurationDAO
				.findById(deposit.getProductConfigurationId());

		List<DepositForm> depositList = paymentDAO.paymentAccountNumber(fixedDepositForm.getAccountNo().trim());

		if (depositList.size() > 0) {
			String depositCategory = depositList.get(0).getDepositCategory();

			String depositClassification = depositList.get(0).getDepositClassification();
			if (depositClassification != null && depositClassification.equals("Tax Saving Deposit")) {
				model.put("taxError", "Sorry you can not make payment for Tax Saving Deposit");
				return new ModelAndView("fdPayment", "model", model);
			}
			if (depositCategory != null && depositCategory.equals("AUTO")) {
				model.put("error", "Sorry you can not make payment for Auto/Sweep Deposit");
				return new ModelAndView("fdPayment", "model", model);
			}
			fixedDepositForm.setProductConfigurationId(deposit.getProductConfigurationId());
			List<ModeOfPayment> paymentMode=modeOfPaymentDAO.getAllModeOfPaymentEmployee();
			model.put("paymentMode", paymentMode);
			model.put("payAndWithdrawTenure", productConfiguration.getPreventionOfTopUpBeforeMaturity());
			model.put("fixedDepositForm", fixedDepositForm);
			model.put("depositList", depositList);

			model.put("productConfigurationId", deposit.getProductConfigurationId());

			return new ModelAndView("fdPayment", "model", model);
		}

		else {
			model.put("error", "Account Number is not Correct OR this deposit has been closed.");
			return new ModelAndView("fdPayment", "model", model);
		}

	}


	@RequestMapping(value = "/fdPaymentDetails", method = RequestMethod.POST)
	public ModelAndView fdPaymentDetails(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {

		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		List<DepositHolderForm> depositHolderList = depositHolderDAO
				.getDepositHoldersName(fixedDepositForm.getDepositId());

		model.put("todaysDate", date);
		model.put("depositHolderList", depositHolderList);
		model.put("fixedDepositForm", fixedDepositForm);

		ProductConfiguration productConfiguration = productConfigurationDAO
				.findById(fixedDepositForm.getProductConfigurationId());
		model.put("productConfiguration", productConfiguration);
		/*
		 * model.put("maxTopUpAmount", productConfiguration == null ? null :
		 * productConfiguration.getMinimumTopupAmount()); model.put("minDepositAmount",
		 * productConfiguration == null ? null :
		 * productConfiguration.getMinimumDepositAmount());
		 */

		Deposit deposit = fixedDepositDAO.getDeposit(fixedDepositForm.getDepositId());
		if (productConfiguration.getIsTopupAllowed() != null) {
			if (productConfiguration.getIsTopupAllowed() == 1) {

				String lockingPeriodForTopup = productConfiguration.getLockingPeriodForTopup();
				if (lockingPeriodForTopup != null && lockingPeriodForTopup.trim().equalsIgnoreCase(",,")) {
					model.put("lockingPeriod", false);
				} else {
					String[] arrlockingPeriodForTopup = lockingPeriodForTopup.split(",");
					Date lockingDate = deposit.getCreatedDate();
					for (int i = 0; i < arrlockingPeriodForTopup.length; i++) {
						if (arrlockingPeriodForTopup[i] != null && arrlockingPeriodForTopup[i].trim().length() > 0) {
							if (arrlockingPeriodForTopup[i].toString().endsWith("Year")) {
								String num = arrlockingPeriodForTopup[i].replaceAll(" Year", "");
								lockingDate = DateService.addYear(lockingDate, Integer.parseInt(num));
							} else if (arrlockingPeriodForTopup[i].toString().endsWith("Month")) {
								String num = arrlockingPeriodForTopup[i].replaceAll(" Month", "");
								lockingDate = DateService.addMonths(lockingDate, Integer.parseInt(num));
							} else if (arrlockingPeriodForTopup[i].toString().endsWith("Day")) {
								String num = arrlockingPeriodForTopup[i].replaceAll(" Day", "");
								lockingDate = DateService.addDays(lockingDate, Integer.parseInt(num));
							}
						}
					}

					if (DateService.getDaysBetweenTwoDates(lockingDate, DateService.getCurrentDate()) < 0) {
						model.put("lockingPeriod", true);

					} else {
						model.put("lockingPeriod", false);
					}
				}
			} else {
				model.put("lockingPeriod", false);
			}
			// if(productConfiguration.getIsTopupAllowed() == 1) model.put("lockingPeriod",
			// true); else model.put("lockingPeriod", false);

		}
		ModeOfPayment paymentMode=modeOfPaymentDAO.getModeOfPaymentById(Long.parseLong(fixedDepositForm.getPaymentMode()));
		model.put("paymentMode", paymentMode.getPaymentMode());
		return new ModelAndView("fdPaymentDetails", "model", model);

	}

	@RequestMapping(value = "/fdPaymentDetailsConfirm", method = RequestMethod.POST)
	public ModelAndView fdCashDataConfirm(ModelMap model, @ModelAttribute DepositForm depositForm,
			@ModelAttribute FixedDepositForm fixedDepositForm) throws CustomException {
		fixedDepositForm.setCustomerId(depositForm.getCustomerId());

		fixedDepositForm.setDepositId(depositForm.getDepositId());
		fixedDepositForm.setDepositHolderId(depositForm.getDepositHolderId());
		fixedDepositForm.setCustomerName(depositForm.getCustomerName());
		fixedDepositForm.setEmail(depositForm.getEmail());
		fixedDepositForm.setPaymentMode(depositForm.getPaymentMode());
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		List<DepositHolderForm> depositHolderList = depositHolderDAO.getDepositHoldersName(depositForm.getDepositId());
		ProductConfiguration productConfiguration = productConfigurationDAO
				.findById(fixedDepositForm.getProductConfigurationId());
		model.put("productConfiguration", productConfiguration);
		model.put("depositHolderList", depositHolderList);
		model.put("todaysDate", date);
		model.put("fixedDepositForm", fixedDepositForm);
		ModeOfPayment paymentMode=modeOfPaymentDAO.getModeOfPaymentById(Long.parseLong(fixedDepositForm.getPaymentMode()));
		model.put("paymentMode", paymentMode.getPaymentMode());
		return new ModelAndView("fdPaymentDetailsConfirm", "model", model);

	}

	
	@Transactional
	@RequestMapping(value = "/savePayment", method = RequestMethod.POST)
	public String savePayment(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes) throws CustomException {

		String transactionId = depositService.doPayment(fixedDepositForm, getCurrentLoggedUserName());
		if (transactionId != "") {
			Date curDate = DateService.loginDate;

			attributes.addFlashAttribute(Constants.TRANSACTIONID, transactionId);
			attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
			attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Payment Successful");

			// attributes = updateTransaction("Payment Successful", attributes);
			return "redirect:bankFDSaved";
		} else
			throw new CustomException("Payment failed, try again.");

		// **************** Saving in Payment Table *******/
//		String fromAccountNo = "";
//		String fromAccountType = "";
//
//		Deposit deposit = fixedDepositDAO.getDeposit(fixedDepositForm.getDepositId());
//		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(fixedDepositForm.getDepositId());
//		Long customerId = fdService.getPrimaryHolderId(depositHolderList);
//		deposit.setTenureInDays(fixedDepositForm.getTenureInDays());
//		deposit.setTenureInMonths(fixedDepositForm.getTenureInMonths());
//		deposit.setTenureInYears(fixedDepositForm.getTenureInYears());
//		deposit.setTotalTenureInDays(fixedDepositForm.getTotalTenureInDays());
//		if (deposit.getDepositClassification() != null
//				&& deposit.getDepositClassification().equalsIgnoreCase("Recurring Deposit")) {
//			Date todaysDate = DateService.getCurrentDateTime();
//			SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
//			String todaysDateStr = smt.format(todaysDate);
//
//			// Get the deposit due date
//			Date dueDate = deposit.getDueDate();
//
//			// Check it is is the actual due date or not. If there is some unsuccessful
//			// payment in this date,
//			// then actual due date is the unsuccessful payment date.
//			// Then check the grace period. If payment is done within the grace period,
//			// then there is no penalty charged.
//			UnSuccessfulRecurringDeposit unSuccessfulRecurringDeposit = unSuccessfulRecurringDAO
//					.getLastUnSuccessfulRecurringDeposit(deposit.getId());
//			if (unSuccessfulRecurringDeposit != null)
//				dueDate = unSuccessfulRecurringDeposit.getUnsuccessPaymentDate();
//
//			Integer daysDifference = DateService.getDaysBetweenTwoDates(dueDate, todaysDate);
//
//			// check if the date difference is within the grace period then
//			// take the payment as recurring payment else top up
//
//			// Get the Bank Configuration to find grace period
//			BankConfiguration bankConfiguration = null;
//			if (deposit.getPrimaryCitizen().equalsIgnoreCase(Constants.RI)) {
//				bankConfiguration = ratesDAO.findAllDataByCitizen(Constants.RI);
//			}
//
//			if (deposit.getPrimaryCitizen().equalsIgnoreCase(Constants.NRI)) {
//				bankConfiguration = ratesDAO.findAllDataByCitizenAndAccountType(Constants.NRI,
//						deposit.getPrimaryNRIAccountType());
//			}
//			if (bankConfiguration != null) {
//				// get the grace period
//				Integer gracePeriod = bankConfiguration.getGracePeriodForRecurringPayment() == null ? 0
//						: bankConfiguration.getGracePeriodForRecurringPayment();
//
//				if (daysDifference>=0 && gracePeriod >= daysDifference) {
//					// isTopUp = 0; // recurring
//					// Get the modification last record
//					// overriding modification data
//					DepositModification modification = depositModificationDAO.getLastByDepositId(deposit.getId());
//					if (modification == null) {
//						modification = new DepositModification();
//						modification.setDepositAmount(deposit.getDepositAmount());
//						modification.setPaymentMode(deposit.getPaymentMode());
//						modification.setPaymentType(deposit.getPaymentType());
//					}
//
//					// Now check the amount
//					// -----------------------------------------------------------------
//					Double recurringAmount = modification.getDepositAmount();
//					List<Payment> payments = paymentDAO.getPaymentDetails(deposit.getId(), dueDate, todaysDate);
//					depositService.makePayment(deposit, fixedDepositForm, getCurrentLoggedUserName(), payments,
//							recurringAmount, unSuccessfulRecurringDeposit);
//				} else {
//					Integer isTopUp = 1;
//					depositService.savePayment(deposit, fixedDepositForm, isTopUp, getCurrentLoggedUserName(),
//							fixedDepositForm.getFdAmount(), unSuccessfulRecurringDeposit);
//				}
//			}
//
//		} else {
//			depositService.savePayment(deposit, fixedDepositForm, 1, getCurrentLoggedUserName(),
//					fixedDepositForm.getFdAmount(), null);
//		}
//		if (fixedDepositForm.getPaymentMode().equalsIgnoreCase("Cash")) {
//			// For accounting entry only
//			fromAccountNo = "";
//			fromAccountType = "Cash";
//		} else if (!(fixedDepositForm.getPaymentMode().equalsIgnoreCase("Card Payment"))
//				&& !(fixedDepositForm.getPaymentMode().equalsIgnoreCase("onLinePayment"))) {
//			// For accounting entry only
//			fromAccountNo = fixedDepositForm.getChequeNo().toString();
//			fromAccountType = fixedDepositForm.getPaymentMode();
//
//		}
//
//		else if (fixedDepositForm.getPaymentMode().equalsIgnoreCase("Card Payment")) {
//			String[] cardNoArray = fixedDepositForm.getDepositForm().getCardNo().split("-");
//			String cardNo = "";
//			for (int i = 0; i < cardNoArray.length; i++) {
//				cardNo = cardNo + cardNoArray[i];
//			}
//			// For accounting entry only
//			fromAccountNo = cardNo;
//			fromAccountType = fixedDepositForm.getDepositForm().getCardType();
//
//		} else if (fixedDepositForm.getPaymentMode().equalsIgnoreCase("onLinePayment")) {
//
//			if (fixedDepositForm.getFdPayOffAccount().contains("Same")) {
//				AccountDetails accDetails = accountDetailsDAO
//						.findByAccountNo(fixedDepositForm.getOtherAccount().toString());
//				if (accDetails != null) {
//					fromAccountNo = accDetails.getAccountNo();
//					fromAccountType = accDetails.getAccountType();
//				}
//
//			}
//
//		}
//		// Insert in DepositSummary and HolderwiseDepositSummary
//		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(deposit.getId());
//
//		// Insert in Journal & Ledger
//		// -----------------------------------------------------------
//
//		ledgerService.insertJournalLedger(deposit.getId(), DateService.getCurrentDate(), fromAccountNo, fromAccountType,
//				deposit.getAccountNumber(), "Deposit Account", "Payment",
//				Double.valueOf(fixedDepositForm.getFdAmount()), fixedDepositForm.getPaymentMode(),
//				depositSummary.getTotalPrincipal());
//		// -----------------------------------------------------------
//
//		if (deposit.getReverseEmiCategory() != null
//				&& deposit.getReverseEmiCategory().equalsIgnoreCase("Fixed Amount")) {
//			double totalDepositAmount = deposit.getDepositAmount() + fixedDepositForm.getFdAmount();
//			float rateOfInterest = deposit.getInterestRate();
//			double emiAmount = depositHolderList.get(0).getEmiAmount();
//			float tenure = fdService.calculateEmiTenure(totalDepositAmount, emiAmount, rateOfInterest);
//
//			int daysTenure = (int) (tenure * 30);
//
//			Date maturityDate = DateService.generateDaysDate(deposit.getCreatedDate(), daysTenure);
//			
//			deposit.setNewMaturityDate(maturityDate);
//			deposit.setDepositAmount(totalDepositAmount);
//		}
//
//		deposit = fixedDepositDAO.updateFD(deposit);
//
//		attributes = updateTransaction("Payment Successful", attributes);
//		return "redirect:bankFDSaved";

	}

	
	
	@RequestMapping(value = "/withDrawFDSearch", method = RequestMethod.GET)
	public ModelAndView withDrawFD(ModelMap model, @ModelAttribute WithdrawForm withdrawForm,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		model.put("withdrawForm", withdrawForm);
		return new ModelAndView("withDrawFDSearch", "model", model);

	}

	@RequestMapping(value = "/withdrawPaymentList", method = RequestMethod.POST)
	public ModelAndView withdrawPaymentList(ModelMap model, @ModelAttribute DepositForm depositForm,
			@ModelAttribute WithdrawForm withdrawForm) throws CustomException {

		ProductConfiguration _pc = null;

		List<DepositForm> deposit = paymentDAO.paymentAccountNumber(depositForm.getAccountNumber().trim());
		List<ModeOfPayment> paymentList = modeOfPaymentDAO.getAllModeOfPaymentEmployee();
		if (deposit.size() > 0) {
			if (deposit.get(0).getDepositClassification() != null
					&& deposit.get(0).getDepositClassification().equals(Constants.taxSavingDeposit)) {
				model.put(Constants.ERROR, Constants.withdrawError);
				return new ModelAndView("withDrawFDSearch", "model", model);
			}
			// bankConfiguration =
			// ratesDAO.findAllDataByCitizenAndAccountType(deposit.get(0).getPrimaryCitizen(),
			// deposit.get(0).getPrimaryNRIAccountType());
			_pc = productConfigurationDAO
					.findById(Long.parseLong(deposit.get(0).getProductConfigurationId().toString()));
			if (_pc == null) {
				_pc = productConfigurationDAO.findByProductCode(deposit.get(0).getProductConfigurationId().toString());
			}
			if (_pc.getIsWithdrawAllowed() == 0) {

				model.put(Constants.ERROR, "Withdraw is not allowed for this deposit. you can not withdraw.");
				return new ModelAndView("withDrawFDSearch", "model", model);

				/*
				 * model.put(Constants.ERROR, Constants.withdrawLockingPeriodError); return new
				 * ModelAndView("withDrawFDSearch", "model", model);
				 */
			}
			if (_pc.getLockingPeriodForWithdraw() != null) {
				String lockingPeriodForWithdraw = _pc.getLockingPeriodForWithdraw();
				String[] arrlockingPeriodForTopup = lockingPeriodForWithdraw.split(",");

				Date lockingDate = deposit.get(0).getCreatedDate();
				for (int i = 0; i < arrlockingPeriodForTopup.length; i++) {
					if (arrlockingPeriodForTopup[i] != null) {
						if (arrlockingPeriodForTopup[i].toString().endsWith("Year")) {
							String num = arrlockingPeriodForTopup[i].replaceAll(" Year", "");
							lockingDate = DateService.addYear(lockingDate, Integer.parseInt(num));
						} else if (arrlockingPeriodForTopup[i].toString().endsWith("Month")) {
							String num = arrlockingPeriodForTopup[i].replaceAll(" Month", "");
							lockingDate = DateService.addMonths(lockingDate, Integer.parseInt(num));
						} else if (arrlockingPeriodForTopup[i].toString().endsWith("Day")) {
							String num = arrlockingPeriodForTopup[i].replaceAll(" Day", "");
							lockingDate = DateService.addDays(lockingDate, Integer.parseInt(num));
						}
					}
				}

				if (DateService.getDaysBetweenTwoDates(lockingDate, DateService.getCurrentDate()) < 0) {
					model.put(Constants.ERROR, Constants.withdrawLockingPeriodError);
					return new ModelAndView("withDrawFDSearch", "model", model);
				}

			}
		}
		if (deposit.size() > 0) {

			String depositCategory = deposit.get(0).getDepositCategory();

			if (depositCategory == null) {
				depositForm.setCustomerId(withdrawForm.getCustomerId());
				depositForm.setCustomerName(deposit.get(0).getCustomerName());
				depositForm.setDepositId(deposit.get(0).getDepositId());
				depositForm.setDepositHolderId(deposit.get(0).getDepositHolderId());
				model.put("payAndWithdrawTenure", _pc.getPreventionOfWithdrawBeforeMaturity());
				depositForm.setEmail(deposit.get(0).getEmail());
				model.put("depositForm", depositForm);
				model.put("deposit", deposit);
			} else if (depositCategory.equals("AUTO")) {
				model.put("error", "Sorry you can not withdraw amount from Auto/Sweep Deposit");

			}

			else if (depositCategory.equals("REVERSE-EMI")) {
				model.put("error", "Sorry you can not withdraw amount from REVERSE-EMI Deposit");
			}

		} else {
			model.put("error", "Account Number is not Correct");

		}
		model.put("paymentMode", paymentList);
		return new ModelAndView("withDrawFDSearch", "model", model);
		// return new ModelAndView("withdrawPaymentList", "model", model);

	}

	
	@RequestMapping(value = "/withdrawPaymentLists", method = RequestMethod.GET)
	public ModelAndView withdrawPaymentLists(ModelMap model,
			@RequestParam(value = "accountNumber") String accountNumber,
			@RequestParam(value = "customerId") Long customerId) throws CustomException {
		ProductConfiguration _pc = null;

		List<DepositForm> deposit = paymentDAO.paymentAccountNumber(accountNumber.trim());

		if (deposit.size() > 0) {
			if (deposit.get(0).getDepositClassification() != null
					&& deposit.get(0).getDepositClassification().equals(Constants.taxSavingDeposit)) {
				model.put(Constants.ERROR, Constants.withdrawError);
				return new ModelAndView("withDrawFDSearch", "model", model);
			}
			// bankConfiguration =
			// ratesDAO.findAllDataByCitizenAndAccountType(deposit.get(0).getPrimaryCitizen(),
			// deposit.get(0).getPrimaryNRIAccountType());
			_pc = productConfigurationDAO
					.findById(Long.parseLong(deposit.get(0).getProductConfigurationId().toString()));
			if (_pc == null) {
				_pc = productConfigurationDAO.findByProductCode(deposit.get(0).getProductConfigurationId().toString());
			}
			if (_pc.getIsWithdrawAllowed() == 0) {

				model.put(Constants.ERROR, "Withdraw is not allowed for this deposit. you can not withdraw.");
				return new ModelAndView("withDrawFDSearch", "model", model);

				/*
				 * model.put(Constants.ERROR, Constants.withdrawLockingPeriodError); return new
				 * ModelAndView("withDrawFDSearch", "model", model);
				 */
			}
			if (_pc.getLockingPeriodForWithdraw() != null) {
				String lockingPeriodForWithdraw = _pc.getLockingPeriodForWithdraw();
				String[] arrlockingPeriodForTopup = lockingPeriodForWithdraw.split(",");

				Date lockingDate = deposit.get(0).getCreatedDate();
				for (int i = 0; i < arrlockingPeriodForTopup.length; i++) {
					if (arrlockingPeriodForTopup[i] != null) {
						if (arrlockingPeriodForTopup[i].toString().endsWith("Year")) {
							String num = arrlockingPeriodForTopup[i].replaceAll(" Year", "");
							lockingDate = DateService.addYear(lockingDate, Integer.parseInt(num));
						} else if (arrlockingPeriodForTopup[i].toString().endsWith("Month")) {
							String num = arrlockingPeriodForTopup[i].replaceAll(" Month", "");
							lockingDate = DateService.addMonths(lockingDate, Integer.parseInt(num));
						} else if (arrlockingPeriodForTopup[i].toString().endsWith("Day")) {
							String num = arrlockingPeriodForTopup[i].replaceAll(" Day", "");
							lockingDate = DateService.addDays(lockingDate, Integer.parseInt(num));
						}
					}
				}

				if (DateService.getDaysBetweenTwoDates(lockingDate, DateService.getCurrentDate()) < 0) {
					model.put(Constants.ERROR, Constants.withdrawLockingPeriodError);
					return new ModelAndView("withDrawFDSearch", "model", model);
				}

			}
		}
		if (deposit.size() > 0) {

			String depositCategory = deposit.get(0).getDepositCategory();

			if (depositCategory == null) {
				depositForm.setCustomerId(customerId);
				depositForm.setCustomerName(deposit.get(0).getCustomerName());
				depositForm.setDepositId(deposit.get(0).getDepositId());
				depositForm.setDepositHolderId(deposit.get(0).getDepositHolderId());
				model.put("payAndWithdrawTenure", _pc.getPreventionOfWithdrawBeforeMaturity());
				depositForm.setEmail(deposit.get(0).getEmail());
				depositForm.setAccountNumber(accountNumber);
				model.put("depositForm", depositForm);
				model.put("deposit", deposit);
			} else if (depositCategory.equals("AUTO")) {
				model.put("error", "Sorry you can not withdraw amount from Auto/Sweep Deposit");

			}

			else if (depositCategory.equals("REVERSE-EMI")) {
				model.put("error", "Sorry you can not withdraw amount from REVERSE-EMI Deposit");
			}

		} else {
			model.put("error", "Account Number is not Correct");

		}

		return new ModelAndView("withDrawFDSearch", "model", model);
		// return new ModelAndView("withdrawPaymentList", "model", model);

	}

	@RequestMapping(value = "/withdrawFdUser", method = RequestMethod.POST)
	public ModelAndView withdrawFdUser(ModelMap model, @ModelAttribute WithdrawForm withdrawForm,
			@ModelAttribute DepositForm depositForm, RedirectAttributes redirectAttributes) throws CustomException {

		Distribution paymentDistribution = fixedDepositDAO.withdrawDepositAmt(depositForm.getDepositId());
		if (paymentDistribution == null) {
			redirectAttributes.addFlashAttribute("error", "Deposit id not found in distribution");
			return new ModelAndView("redirect:withdrawPaymentLists?accountNumber=" + depositForm.getAccountNumber()
					+ "&customerId=" + withdrawForm.getCustomerId());
		}
		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(withdrawForm.getCustomerId());
		withdrawForm.setAccountList(accountList);
		List<DepositHolderForm> depositHolderList = depositHolderDAO.getDepositHoldersName(depositForm.getDepositId());

		withdrawForm.setCustomerId(depositForm.getCustomerId());
		if (depositForm.getPaymentMode() != null && !(depositForm.getPaymentMode().equalsIgnoreCase("")))
			withdrawForm.setModeOfPayment(depositForm.getPaymentMode());

		withdrawForm.setCompoundVariableAmt(paymentDistribution.getCompoundVariableAmt());
		withdrawForm.setCompoundFixedAmt(paymentDistribution.getCompoundFixedAmt());
		withdrawForm.setDepositHolderId(depositForm.getDepositHolderId());
		withdrawForm.setPaymentMadeByHolderIds(depositForm.getPaymentMadeByHolderIds());
		
		withdrawForm.setAccountNumber(depositForm.getAccountNumber());
		withdrawForm.setCustomerName(depositForm.getCustomerName());
		withdrawForm.setEmail(depositForm.getEmail());
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		model.put("todaysDate", date);
		model.put("depositHolderList", depositHolderList);
		model.put("withdrawForm", withdrawForm);
		return new ModelAndView("fdWithdraw", "model", model);

	}

	@RequestMapping(value = "/confirmWithdrawFdUser", method = RequestMethod.POST)

	public ModelAndView confirmWithdrawFdUser(ModelMap model, @ModelAttribute WithdrawForm withdrawForm,
			DepositForm depositForm, @RequestParam String accountNum) throws CustomException {
		String accNo = accountNum != null ? accountNum : null;
		Distribution paymentDistribution = fixedDepositDAO.withdrawDepositAmt(depositForm.getDepositId());
		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(withdrawForm.getCustomerId());
		withdrawForm.setAccountList(accountList);
		withdrawForm.setCustomerId(depositForm.getCustomerId());
		withdrawForm.setCompoundVariableAmt(paymentDistribution.getCompoundVariableAmt());
		withdrawForm.setCompoundFixedAmt(paymentDistribution.getCompoundFixedAmt());
		withdrawForm.setDepositHolderId(depositForm.getDepositHolderId());
		withdrawForm.setCustomerName(depositForm.getCustomerName());
		withdrawForm.setEmail(depositForm.getEmail());
		List<DepositHolderForm> depositHolderList = depositHolderDAO.getDepositHoldersName(depositForm.getDepositId());
		model.put("accNo", accNo);
		model.put("withdrawForm", withdrawForm);
		model.put("depositHolderList", depositHolderList);
		return new ModelAndView("confirmWithdrawFdUser", "model", model);

	}

	@Transactional
	@RequestMapping(value = "/saveWithdrAmmount", method = RequestMethod.POST)
	public String saveWithdrAmmount(ModelMap model, @ModelAttribute WithdrawForm withdrawForm,
			RedirectAttributes attributes, @RequestParam String accountNum) throws CustomException {
		EndUser user = getCurrentLoggedUserDetails();
		Date today = DateService.getCurrentDateTime();
		String accNo = accountNum != null ? accountNum : null;
		if (withdrawForm.getModeOfPayment().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {
			AccountDetails accountDetails = accountDetailsDAO.findByAccountNo(accNo);
			accountDetails.setAccountBalance(accountDetails.getAccountBalance() + withdrawForm.getWithdrawAmount());
			accountDetailsDAO.updateUserAccountDetails(accountDetails);
		}

		fdService.withdrawOperation(withdrawForm, user.getUserName());
		try {

			String tex = Constants.CUSTOMERREMENDERSUBJECT;
			SimpleMailMessage emails = new SimpleMailMessage();
			emails.setTo(withdrawForm.getEmail());
			emails.setSubject(tex);
			emails.setText(Constants.HELLO + withdrawForm.getCustomerName() + Constants.WITHDRAWBODY1
					+ withdrawForm.getWithdrawAmount() + Constants.WITHDRAWDATE + today + Constants.BANKSIGNATURE);
			mailSender.send(emails);
			SimpleMailMessage emails1 = new SimpleMailMessage();
			emails1.setTo(withdrawForm.getEmail());

		} catch (Exception e) {
			System.out.println(e.getMessage() + e);

		}
		attributes = updateTransaction("Transaction Successful", attributes);
		return "redirect:bankFDSaved";

	}

	/*
	 * @Transactional
	 * 
	 * @RequestMapping(value = "/saveWithdrAmmount", method = RequestMethod.POST)
	 * public String saveWithdrAmmount(ModelMap model, @ModelAttribute WithdrawForm
	 * withdrawForm, RedirectAttributes attributes) { EndUser user =
	 * getCurrentLoggedUserDetails(); Double withdrawPenalty = 0d; Date currentDate
	 * = DateService.getCurrentDate(); // delete from interest saved after the
	 * current date Interest lastInterestProjected =
	 * interestDAO.deleteByDepositIdAndDate(withdrawForm.getDepositId());
	 * DepositHolder depositHolder =
	 * depositHolderDAO.findById(withdrawForm.getDepositHolderId());
	 * 
	 * // Steps // 1. Interest calculation till date // 2. Withdraw the amount // 3.
	 * Interest Adjustment and penalty for the Withdraw Deposit deposit =
	 * fixedDepositDAO.findById(withdrawForm.getDepositId()); List<DepositHolder>
	 * depositHolderList =
	 * depositHolderDAO.getDepositHolders(withdrawForm.getDepositId()); Distribution
	 * lastInterestDistribution =
	 * paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());
	 * 
	 * Date createdDate = deposit.getCreatedDate(); if
	 * (DateService.getDaysBetweenTwoDates(createdDate, currentDate) <= 7) {
	 * 
	 * if (DateService.compareDate(DateService.getLastDateOfCurrentMonth(),
	 * currentDate) == 0 || DateService.getMonth(createdDate) !=
	 * DateService.getMonth(currentDate)) { Distribution adjustmentFor7Days =
	 * paymentDistributionDAO.getLastAdjustmentFor7Days(deposit.getId()); if
	 * (adjustmentFor7Days == null) { Distribution lastIntDistribution =
	 * paymentDistributionDAO .getLastInterestCalculated(deposit.getId()); if
	 * (lastIntDistribution != null) {
	 * 
	 * Double fixedInterest = lastIntDistribution.getFixedInterest(); Double
	 * variableInterest = lastIntDistribution.getVariableInterest(); Double
	 * totalInterest = fixedInterest + variableInterest; Distribution
	 * paymentDistribution = new Distribution();
	 * 
	 * paymentDistribution.setDepositedAmt(-totalInterest);
	 * paymentDistribution.setDepositId(deposit.getId());
	 * paymentDistribution.setDistributionDate(currentDate);
	 * paymentDistribution.setAction(Constants.INTERESTADJUSTMENTFORWITHDRAW);
	 * paymentDistribution
	 * .setFixedInterest(lastInterestDistribution.getFixedInterest() -
	 * fixedInterest); paymentDistribution
	 * .setVariableInterest(lastInterestDistribution.getVariableInterest() -
	 * variableInterest); paymentDistribution
	 * .setCompoundFixedAmt(lastInterestDistribution.getCompoundFixedAmt() -
	 * fixedInterest); paymentDistribution.setCompoundVariableAmt(
	 * lastInterestDistribution.getCompoundVariableAmt() - variableInterest);
	 * paymentDistribution.setBalanceFixedInterest(
	 * lastInterestDistribution.getBalanceFixedInterest() - fixedInterest);
	 * paymentDistribution.setBalanceVariableInterest(
	 * lastInterestDistribution.getBalanceVariableInterest() - variableInterest);
	 * paymentDistribution.setTotalBalance(lastInterestDistribution.
	 * getTotalBalance() - totalInterest);
	 * 
	 * lastInterestDistribution = paymentDistributionDAO
	 * .insertPaymentDistribution(paymentDistribution); Interest interest = new
	 * Interest(); interest.setDepositId(deposit.getId());
	 * interest.setFinancialYear(DateService.getFinancialYear(currentDate));
	 * interest.setInterestAmount(-totalInterest);
	 * interest.setInterestCalcDate(currentDate); interest.setInterestDeducted(1);
	 * interest.setInterestRate(lastInterestProjected.getInterestRate());
	 * interestDAO.insert(interest);
	 * 
	 * } } } }
	 * 
	 * 
	 * // Start Date -- 12/3/2018 // 1. First Calculate the interest up to the
	 * withdrawalDate // 2. Withdraw the Amount // 3. Adjust the Interest
	 * 
	 * // 1. Calculate the interest Distribution lastInterestCalculated =
	 * fdService.calculateInterest(deposit, depositHolderList);
	 * 
	 * // 2. Withdraw the Amount // Get the last BaseLine Distribution lastBaseLine
	 * = paymentDistributionDAO.getLastBaseLine(deposit.getId()); Double withdrawAmt
	 * = withdrawForm.getWithdrawAmount(); Distribution lastDistribution =
	 * fdService.withdrawFromDeposit(deposit, withdrawForm, lastInterestCalculated,
	 * depositHolderList, lastBaseLine,
	 * user.getUserName(),withdrawForm.getCustomerName());
	 * 
	 * // 3. Adjust the Interest lastDistribution =
	 * fdService.adjustInterestForWithdraw(deposit, withdrawForm, lastDistribution,
	 * depositHolderList, lastBaseLine);
	 * 
	 * 
	 * // set required parameters for interest calculation in fixedDepositForm
	 * Customer customer = getCustomerDetails(depositHolder.getCustomerId());
	 * fixedDepositForm.setDepositId(withdrawForm.getDepositId());
	 * fixedDepositForm.setCategory(customer.getCategory()); fixedDepositForm =
	 * fdService.setParametersForProjectedInterest(fixedDepositForm);
	 * 
	 * //Save the projected interests List<Interest> interestList =
	 * fdService.getInterestBreakupForModification(DateService.getCurrentDate(),
	 * fixedDepositForm.getMaturityDate(), fixedDepositForm, deposit); for (int i =
	 * 0; i < interestList.size(); i++) { Interest interest = new Interest();
	 * interest = interestList.get(i); interestDAO.insert(interest); if (i ==
	 * interestList.size() - 1) {
	 * deposit.setNewMaturityAmount(interest.getInterestSum());
	 * fixedDepositDAO.updateFD(deposit); } }
	 * 
	 * List<TDS> tdsList = fdService.getTDSBreakupForModification(fixedDepositForm,
	 * interestList, fixedDepositForm.getMaturityDate()); for (int i = 0; i <
	 * tdsList.size(); i++) { // TDS tds = new TDS(); TDS tds = tdsList.get(i);
	 * tds.setDepositId(fixedDepositForm.getDepositId()); tdsDAO.insert(tds); }
	 * 
	 * attributes = updateTransaction("Transaction Successful", attributes); return
	 * "redirect:bankFDSaved";
	 * 
	 * }
	 */

//	@RequestMapping(value = "/bankConfiguration")
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
//			bankConfigurationForm.setPanality(0d);
//			bankConfigurationForm.setPrematurePanality(0d);
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
//		return new ModelAndView("bankConfiguration", "model", model);
//	}

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
//				&& (bankConfigurationForm.getNriAccountType().equals("FCNR") || bankConfigurationForm.getNriAccountType().equals("NRE")
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
//		int adjustmentForDepositConversion = bankConfigurationForm.getAdjustmentForDepositConversion().equals("Yes") ? 1
//				: 0;
//		bankConfiguration1.setAdjustmentForDepositConversion(adjustmentForDepositConversion);
//		if (bankConfigurationForm.getCitizen() != null && bankConfigurationForm.getCitizen().equals(Constants.NRI)) {
//			bankConfiguration1.setNriAccountType(bankConfigurationForm.getNriAccountType());
//		}
//		bankConfiguration1.setInterestCompoundingBasis(bankConfigurationForm.getInterestCompoundingBasis());
//		bankConfiguration1.setPenaltyForMissedPaymentForRecurring(bankConfigurationForm.getPenaltyForMissedPaymentForRecurring());
//		bankConfiguration1.setGracePeriodForRecurringPayment(bankConfigurationForm.getGracePeriodForRecurringPayment());
//		if (save == 1) {
//			ratesDAO.createBankConfiguration(bankConfiguration1);
//			attributes = updateTransaction("Saved Successfully", attributes);
//		} else {
//			ratesDAO.updateBankConfiguration(bankConfiguration1);
//			attributes = updateTransaction("Updated Successfully", attributes);
//
//		}
//		return "redirect:bankFDSaved";
//	}

	public List<DepositModification> searchDepositholderPayOff(Long depositId) throws CustomException {
		List<DepositModification> depositModificationList = new ArrayList<DepositModification>();

		List<DepositHolder> depositHolders = depositHolderDAO.getDepositHolders(depositId);
		for (int i = 0; i < depositHolders.size(); i++) {
			Long depositHolderId = depositHolders.get(i).getDepositId();

			DepositModification depositModification = depositModificationDAO
					.getDepositHolderModification(depositHolderId);
			if (depositModification == null) {
				DepositModification depositMod = new DepositModification();
				depositMod.setDepositId(depositHolders.get(i).getDepositId());
				depositMod.setDepositHolderId(depositHolders.get(i).getId());
				// depositMod.setPaymentMode(paymentMode);
				// depositMod.setPaymentType(paymentType);
				depositMod.setPayOffAccountNumber(depositHolders.get(i).getAccountNumber());
				depositMod.setPayOffAccountType(depositHolders.get(i).getPayOffAccountType());
				depositMod.setPayOffBankIFSCCode(depositHolders.get(i).getIfscCode());
				depositMod.setPayOffBankName(depositHolders.get(i).getBankName());
				depositMod.setPayOffNameOnBankAccount(depositHolders.get(i).getNameOnBankAccount());
				depositMod.setPayOffTransferType(depositHolders.get(i).getTransferType());
				// depositMod.setPayOffType(depositHolders.get(i).payOffType);
			} else {
				depositModificationList.add(depositModification);
			}

		}

		return depositModificationList;
	}

	@RequestMapping(value = "/deathIssue", method = RequestMethod.GET)
	public ModelAndView payment(ModelMap model, RedirectAttributes attributes, @ModelAttribute DepositForm depositForm)
			throws CustomException {

		model.put("depositForm", depositForm);
		return new ModelAndView("deathIssue", "model", model);

	}

	@RequestMapping(value = "/deathIssueSearchPerson", method = RequestMethod.POST)

	public ModelAndView deathIssueSearchPerson(ModelMap model, @ModelAttribute DepositForm depositForm)
			throws CustomException {

		List<DepositForm> depositList = paymentDAO.paymentAccountNumber(depositForm.getAccountNumber());
		if (depositList.size() > 0) {
			model.put("depositForm", depositForm);
			model.put("depositList", depositList);
		} else {
			model.put("error", "Please Insert Correct Account Number");
		}
		return new ModelAndView("deathIssue", "model", model);
	}

	@RequestMapping("/savedDeathCertificate")
	public ModelAndView savedDeathCertificate(ModelMap model, @ModelAttribute DepositForm depositForm,
			RedirectAttributes attributes, BindingResult result) throws RuntimeException, IOException, CustomException {

		UploadedFile upload = new UploadedFile();

		List<MultipartFile> files = depositForm.getFiles();
		Set<byte[]> filesList = new HashSet<byte[]>();
		Set<String> fileNameList = new HashSet<String>();
		Set<String> fileContentTypeList = new HashSet<String>();

		Boolean emptyFile = false;

		for (MultipartFile multipartFile : files) {
			if (multipartFile.isEmpty()) {
				emptyFile = true;
				break;
			}
			filesList.add(multipartFile.getBytes());
			fileNameList.add(multipartFile.getOriginalFilename());
			fileContentTypeList.add(multipartFile.getContentType());

		}
		if (emptyFile) {
			model.put("error", "Please select the file for Death Certificate");
			return new ModelAndView("deathIssue", "model", model);
			/* return "redirect:deathIssue"; */
		}
		upload.setFiles(filesList);
		upload.setFileNames(fileNameList);
		upload.setCustomerName(depositForm.getCustomerName());
		upload.setDepositId(depositForm.getDepositId());
		upload.setDocument(depositForm.getDocument());
		upload.setFileContentType(fileContentTypeList);
		upload.setDepositAccountNumber(depositForm.getAccountNumber());
		upload.setCustomerId(depositForm.getCustomerId());
		if (upload.getFiles().size() > 2000000) {
			throw new MultipartException("Image size exceeds");
		}

		Date uploadDate = DateService.getCurrentDateTime();
		upload.setUploadDate(uploadDate);

		uploadDAO.createUser(upload);

		DepositHolder depositHolderData = depositHolderDAO.getDepositHolder(depositForm.getDepositId(),
				depositForm.getCustomerId());
		depositHolderData.setDeathCertificateSubmitted("1");

		depositHolderDAO.updateDepositHolder(depositHolderData);
		Deposit deposit = depositHolderDAO.getDepositByDepositIdAndCustId(depositForm.getDepositId());

		// Set status means OPEN/CLOSE
		deposit.setStatus(depositForm.getStatus());

		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date today = DateService.getCurrentDateTime();
		Date todayDate = null;

		try {
			todayDate = formatter.parse(formatter.format(today));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		deposit.setClosingDate(todayDate);
		deposit = depositHolderDAO.updateDeposit(deposit);

		// close the deposit
		if (depositForm.getStatus().equalsIgnoreCase("CLOSE")) {
			calculationService.closeDeposit(deposit, true);
		}
		/* / / / return "redirect:savedSuccess"; / / / */

		Transaction trans = new Transaction();

		String transactionId = fdService.generateRandomString();

		trans.setTransactionId(transactionId);
		trans.setTransactionStatus(Constants.CUSTOMER);
		trans.setTransactionType(Constants.CUSTOMEREDITED);
		transactionDAO.insertTransaction(trans);

		attributes = updateTransaction("Saved Successfully", attributes);

		/* return "redirect:bankFDSaved"; */
		return new ModelAndView("redirect:bankFDSaved");
	}

	@RequestMapping(value = "/searchDeathCustomer", method = RequestMethod.GET)
	public ModelAndView searchDeathCustomer(ModelMap model, String depositType) throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("searchDeathCustomer", "model", model);

	}

	@RequestMapping(value = "/deathCustomerList", method = RequestMethod.POST)
	public ModelAndView deathCustomerList(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String depositType) throws CustomException {
		String cusId = fixedDepositForm.getCustomerID();
		String cusName = fixedDepositForm.getCustomerName();
		String cusNum = fixedDepositForm.getContactNum();
		String cusEmail = fixedDepositForm.getEmail();

		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);
		model.put("customerList", customerList);
		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("deathCustomerList", "model", model);

	}

	@RequestMapping(value = "/deleteDownloadDocuments")
	public ModelAndView deleteDownloadDocuments(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws Exception, CustomException {

		List<UploadedFile> cust = uploadDAO.getByCustomerId(fixedDepositForm.getId());
		if (cust != null) {

			model.put("cust", cust);

			return new ModelAndView("deleteDownloadDocuments", "model", model);
		} else {
			return new ModelAndView("noDataFound", "model", model);
		}

	}

	@RequestMapping(value = "/documentsList", method = RequestMethod.GET)
	public ModelAndView documentsList(@RequestParam Long customerId, ModelMap model, HttpServletResponse response,
			@ModelAttribute UploadedFileForm uploadedFileForm, String accountNumber) throws Exception, CustomException {

		List<UploadedFile> filesList = uploadDAO.getFilesByCustomerIdAndAccountNumber(customerId, accountNumber);
		if (filesList != null) {
			model.addAttribute("filesList", filesList);
			model.put("uploadedFileForm", uploadedFileForm);
			return new ModelAndView("documentsList", "model", model);
		} else {
			return new ModelAndView("noDataFound", "model", model);
		}

	}

	@RequestMapping(value = "/documentDownload", method = RequestMethod.POST)
	public String documentDownload(ModelMap model, HttpServletResponse response,
			@ModelAttribute UploadedFileForm uploadedFileForm) throws CustomException {

		try {
			response.setContentType("Content-type: text/zip");
			response.setHeader("Content-Disposition", "attachment; filename=download.zip");

			ServletOutputStream out = response.getOutputStream();
			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(out));

			for (Long id : uploadedFileForm.getIdList()) {

				UploadedFile uploadedFile = uploadDAO.findId(id);
				Iterator<String> itr = uploadedFile.getFileNames().iterator();

				while (itr.hasNext()) {
					String filename = itr.next();
					zos.putNextEntry(new ZipEntry(filename));

					try {
						if (uploadedFile.getFiles().iterator().hasNext()) {
							zos.write(uploadedFile.getFiles().iterator().next());
						}
					} catch (FileNotFoundException fnfe) {
						// If the file does not exists, write an error entry
						// instead of
						// file
						// contents
						zos.write(("ERROR file not found" + filename).getBytes());
						zos.closeEntry();
						continue;
					}
					zos.closeEntry();
				}
			}
			zos.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method to delete files
	 * 
	 * @param attributes
	 * @param uploadedFileForm
	 * @return
	 * 
	 */
	@RequestMapping(value = "/documentDelete", method = RequestMethod.POST)
	public ModelAndView deleteDocument(RedirectAttributes attributes, @ModelAttribute UploadedFileForm uploadedFileForm,
			ModelMap model) throws EndUserNotFoundException, CustomException {
		if (uploadedFileForm.getIdList().length > 0) {
			for (Long id : uploadedFileForm.getIdList()) {
				uploadDAO.deleteUploadedFile(id);
			}
			return new ModelAndView("redirect:deletedSuccessfully");
			// return "redirect:deleteDownloadDocuments";
		} else {
			return new ModelAndView("noDataFound", "model", model);
		}
	}

	@RequestMapping(value = "/deletedSuccessfully", method = RequestMethod.GET)
	public ModelAndView deletedSuccessfully(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		model.put("deletedSuccessfully", Constants.deletedSuccessfully);
		return new ModelAndView("deletedSuccessfully", "model", model);

	}

	@RequestMapping(value = "/bankPayment", method = RequestMethod.GET)
	public ModelAndView bankPayment(ModelMap model, @ModelAttribute BankPaymentForm bankPaymentForm)
			throws CustomException {

		model.put("bankPaymentForm", bankPaymentForm);
		return new ModelAndView("bankPayment", "model", model);

	}

	@RequestMapping(value = "/customerList", method = RequestMethod.POST)
	public ModelAndView customerList(ModelMap model, @ModelAttribute BankPaymentForm bankPaymentForm)
			throws CustomException {

		List<BankPaymentForm> bankPaymentListAll = paymentDAO
				.customerListForBankPayment(bankPaymentForm.getAccountNumber());
		if (bankPaymentListAll.size() > 0) {
			if (bankPaymentListAll.get(0).getDepositClassification() != null
					&& bankPaymentListAll.get(0).getDepositClassification().equals(Constants.taxSavingDeposit)) {
				model.put("error", "Can not make payment for tax saving deposit");
				return new ModelAndView("bankPayment", "model", model);
			}
		}
		if (bankPaymentListAll.size() == 0) {
			model.put("error", "Please insert correct Account Number");
			return new ModelAndView("bankPayment", "model", model);
		} else {

			Boolean isMoneyTransferred = true;
			for (int i = 0; i < bankPaymentListAll.size(); i++) {
				if (bankPaymentListAll.get(i).getIsPaid() == null)
					isMoneyTransferred = false;
			}

			if (isMoneyTransferred && bankPaymentListAll.size() > 0) {
				model.put("error", "Amount is alreay tranferred.");
				return new ModelAndView("bankPayment", "model", model);
			}

			List<BankPaymentForm> bankPaymentList = new ArrayList();
			for (int i = 0; i < bankPaymentListAll.size(); i++) {
				if (bankPaymentListAll.get(i).getIsAmountTransferredOnMaturity() != null)
					continue;

				DepositHolderNominees depositHolderNominees = nomineeDAO
						.accountHoderNominee(bankPaymentListAll.get(i).getDepositHolderId());
				bankPaymentForm.setCustomerId(bankPaymentListAll.get(i).getCustomerId());
				bankPaymentForm.setCustomerName(bankPaymentListAll.get(i).getCustomerName());
				bankPaymentForm.setDepositHolderId(bankPaymentListAll.get(i).getDepositHolderId());
				bankPaymentForm.setDepositId(bankPaymentListAll.get(i).getDepositId());
				if (depositHolderNominees != null) {
					bankPaymentForm.setNomineeName(depositHolderNominees.getNomineeName());
					bankPaymentForm.setNomineeAge(depositHolderNominees.getNomineeAge());
					bankPaymentForm.setNomineeAddress(depositHolderNominees.getNomineeAddress());
					bankPaymentForm.setNomineeRelationship(depositHolderNominees.getNomineeRelationship());
				}

				bankPaymentForm.setDeathCertificateSubmitted(bankPaymentListAll.get(i).getDeathCertificateSubmitted());
				bankPaymentForm.setDistAmtOnMaturity(bankPaymentListAll.get(i).getDistAmtOnMaturity());
				bankPaymentForm.setBankPaymnetDetailsId(bankPaymentListAll.get(i).getBankPaymnetDetailsId());
				bankPaymentForm.setBankPaymentId(bankPaymentListAll.get(i).getBankPaymentId());
				bankPaymentForm.setIsAmountTransferredOnMaturity(null);

				bankPaymentList.add(bankPaymentListAll.get(i));

				model.put("bankPaymentForm", bankPaymentForm);
				model.put("depositList", bankPaymentList);
			}
		}

		return new ModelAndView("bankPayment", "model", model);
	}

	@RequestMapping(value = "/depositeHolderNomineeDetails")
	public ModelAndView depositeHolderNomineeDetails(@RequestParam Long depositHolderId, ModelMap model,
			RedirectAttributes attributes, @ModelAttribute BankPaymentForm bankPaymentForm) throws CustomException {

		ModelAndView mav = new ModelAndView();
		DepositHolder depositHolder = depositHolderDAO.getDepositHoldersByID(bankPaymentForm.getDepositHolderId());
		Double amount = depositHolder.getDistAmtOnMaturity() == null ? 0d : depositHolder.getDistAmtOnMaturity();
		DepositHolderNominees depositHolderLists = nomineeDAO.accountHoderNominee(depositHolder.getId());
		model.put(Constants.amount, amount);
		bankPaymentForm.setAmount(amount);
		if (depositHolderLists == null)
			depositHolderLists = new DepositHolderNominees();

		mav = new ModelAndView("depositeHolderNomineeDetails", "model", model);

		return mav;
	}

	@RequestMapping(value = "/payAmountToHolder", method = RequestMethod.POST)
	public ModelAndView payAmountToHolder(ModelMap model, @ModelAttribute BankPaymentForm bankPaymentForm,
			RedirectAttributes attributes) throws CustomException {

		DepositHolder depositHolder = depositHolderDAO.getDepositHoldersByID(bankPaymentForm.getDepositHolderId());

		model.put("bankPaymentForm", bankPaymentForm);
		model.put("error", "");
		if (bankPaymentForm.getPaymentMode().equalsIgnoreCase("Cash")) {
			model.put("showBank", 0);
		} else {
			model.put("showBank", 1);
		}

		if (depositHolder.getDeathCertificateSubmitted() != null && depositHolder.getDeathCertificateSubmitted().equalsIgnoreCase("Yes")) {

			Long nomineeId = 0l;
			String nomineeName = null;
			String nomineeAge = null;
			String address = null;
			String relationship = null;
			DepositHolderNominees depositHolderNominee = nomineeDAO.accountHoderNominee(depositHolder.getId());
			if (depositHolderNominee != null) {
				nomineeId = depositHolderNominee.getId();
				nomineeName = bankPaymentForm.getNomineeName();
				nomineeAge = bankPaymentForm.getNomineeAge();
				address = bankPaymentForm.getNomineeAddress();
				relationship = bankPaymentForm.getNomineeRelationship();
				model.put(Constants.amount, bankPaymentForm.getAmount());

				model.put("nomineeName", nomineeName);
				model.put("nomineeAge", nomineeAge);
				model.put("address", address);
				model.put("relationship", relationship);
				model.put("nomineeId", nomineeId);
				model.put("bankPaymentId", bankPaymentForm.getBankPaymentId());
				model.put("bankPaymentDetailsId", bankPaymentForm.getBankPaymnetDetailsId());

			}

			return new ModelAndView("payAmountToNominee", "model", model);
		}

		if (bankPaymentDAO.getBankPaymentByDepositHolderId(depositHolder.getId()) != null) {
			model.put("error", "Payment has been made for this deposit.");
			return new ModelAndView("bankPayment", "model", model);
		}

		// get deposit holder list from DepositHolder table
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(bankPaymentForm.getDepositId());

		// Insert in BankPayment Table
		// BankPayment bankPayment =
		// fdService.insertInBankPayment(bankPaymentForm.getDepositId(),
		// bankPaymentForm.getAmount(), Constants.CLOSED);

		// Insert in Detail table
		// for(int i=0; i<depositHolderList.size(); i++)
		// {

		Double contributedAmount = bankPaymentForm.getAmount() * depositHolder.getContribution() / 100;
		List<AccountDetails> accountDetailsList = accountDetailsDAO
				.findCurrentSavingByCustId(depositHolder.getCustomerId());
		String savingAccountNo = null;
		Long nomineeId = null;
		if (accountDetailsList.size() > 0)
			savingAccountNo = accountDetailsList.get(0).getAccountNo();

		// fdService.insertInBankPaymentDetails(bankPayment.getId(),
		// bankPaymentForm.getDepositId(),depositHolder.getId(),
		// depositHolder.getCustomerId(),
		// contributedAmount, DateService.loginDate,
		// bankPaymentForm.getPaymentMode(),
		// bankPaymentForm.getChequeBank(), bankPaymentForm.getChequeBranch(),
		// bankPaymentForm.getChequeNo(),
		// bankPaymentForm.getChequeDate(), savingAccountNo, nomineeId);

		// Do fund transfer while paying
		calculationService.insertInBankPaidDetails(null, bankPaymentForm.getBankPaymentId(),
				bankPaymentForm.getBankPaymnetDetailsId(), depositHolder.getId(), depositHolder.getCustomerId(),
				contributedAmount, DateService.loginDate, bankPaymentForm.getPaymentMode(),
				bankPaymentForm.getChequeBank(), bankPaymentForm.getChequeBranch(), bankPaymentForm.getChequeNo(),
				bankPaymentForm.getChequeDate(), savingAccountNo, nomineeId, null, null, null, null, null);
		// }

		// down vote
		// For question 1, Model provides a method that returns the model
		// attributes as a map. In your test, you can do:
		//
		ModelAndView mav = new ModelAndView("bankPayment");
		// Map<String, Object> modelMap = mav.getModel();
		// Object bankPaymentList = modelMap.get("depositList");
		// List<BankPaymentForm> bankPaymentList = new ArrayList();

		// -----------------------------------
		BankPaymentDetails bankPaymentDetails = bankPaymentDAO
				.getBankPaymentDetails(bankPaymentForm.getBankPaymnetDetailsId());
		bankPaymentDetails.setIsPaid(1);
		bankPaymentDAO.updateBankPaymentDetails(bankPaymentDetails);
		// model.put("depositList", bankPaymentList);
		model.put("bankPaymentForm", bankPaymentForm);
		model.put("error", "");
		if (bankPaymentForm.getPaymentMode().equalsIgnoreCase("Cash")) {
			model.put("showBank", 0);
		} else {
			model.put("showBank", 1);
		}

		attributes = updateTransaction("Transaction Successful", attributes);

		return new ModelAndView("redirect:bankFDSaved");
		// return new ModelAndView("payAmountToNominee", "model", model);

	}

	@RequestMapping(value = "/payAmountToNominee", method = RequestMethod.POST)
	public ModelAndView payAmountToNominee(ModelMap model, @ModelAttribute BankPaymentForm bankPaymentForm,
			RedirectAttributes attributes) throws CustomException {

		// Insert in BankPaymnet
		/*
		 * BankPayment bankPayment = new BankPayment();
		 * bankPayment.setDepositID(bankPaymentForm.getDepositId());
		 * bankPayment.setAmount(bankPaymentForm.getAmount());
		 * bankPayment.setPaymentDate(DateService.loginDate);
		 * bankPayment.setComment(Constants.onDeath);
		 * bankPaymentDAO.saveBankPayment(bankPayment);
		 */

		/*
		 * // Insert in BankPaymnet Details BankPaymentDetails bankPaymentDetails = new
		 * BankPaymentDetails(); BankPayment bankPaymentDetail =
		 * bankPaymentDAO.getBankPayment(bankPaymentForm.getDepositId());
		 * bankPaymentDetails.setAmount(bankPaymentForm.getAmount());
		 * bankPaymentDetails.setBank(bankPaymentForm.getChequeBank());
		 * bankPaymentDetails.setBranch(bankPaymentForm.getChequeBranch());
		 * bankPaymentDetails.setBankPaymentId(bankPaymentDetail.getId());
		 * bankPaymentDetails.setChequeDDdate(bankPaymentForm.getChequeDate());
		 * bankPaymentDetails.setChequeDDNumber(bankPaymentForm.getCardNo());
		 * bankPaymentDetails.setCustomerId(bankPaymentForm.getCustomerId());
		 * bankPaymentDetails.setDepositHolderID(bankPaymentForm. getDepositHolderId());
		 * bankPaymentDetails.setModeOfPayment(bankPaymentForm.getPaymentMode()) ;
		 * bankPaymentDetails.setNomineeId(bankPaymentForm.getNomineeId());
		 * bankPaymentDAO.saveBankPaymentDetails(bankPaymentDetails);
		 */

		calculationService.insertInBankPaidDetails(null, bankPaymentForm.getBankPaymentId(),
				bankPaymentForm.getBankPaymnetDetailsId(), bankPaymentForm.getDepositHolderId(),
				bankPaymentForm.getCustomerId(), bankPaymentForm.getAmount(), DateService.loginDate,
				bankPaymentForm.getPaymentMode(), bankPaymentForm.getChequeBank(), bankPaymentForm.getChequeBranch(),
				bankPaymentForm.getChequeNo(), bankPaymentForm.getChequeDate(), null, bankPaymentForm.getNomineeId(), null, null, null, null, null);

		BankPaymentDetails bankPaymentDetails = bankPaymentDAO
				.getBankPaymentDetails(bankPaymentForm.getBankPaymnetDetailsId());
		bankPaymentDetails.setIsPaid(1);
		bankPaymentDAO.updateBankPaymentDetails(bankPaymentDetails);

		/*
		 * DepositHolder depositHolder =
		 * depositHolderDAO.getDepositHoldersByID(bankPaymentForm.
		 * getDepositHolderId()); depositHolder.setIsAmountTransferredOnMaturity(1);
		 * depositHolder.setIsAmountTransferredToNominee(1);
		 * depositHolderDAO.updateDepositHolder(depositHolder);
		 */
		attributes.addFlashAttribute(Constants.TRANSACTIONID, fdService.generateRandomString());
		attributes.addFlashAttribute(Constants.TRANSACTIONDATE, DateService.loginDate);
		attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Payment made successfully");

		return new ModelAndView("redirect:bankFDSaved");
	}

	@RequestMapping(value = "/fdLists", method = RequestMethod.GET)
	public ModelAndView fdCustomerLists(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		ModelAndView mav = new ModelAndView();

		List<DepositForm> depositLists = fixedDepositDAO.getAllDepositsList();

		if (depositLists != null && depositLists.size() > 0) {
			depositForm.setDepositId(depositLists.get(0).getDepositId());
			model.put("depositLists", depositLists);
			mav = new ModelAndView("bankfdChangesLists", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/depositLists", method = RequestMethod.GET)
	public ModelAndView depositLists(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		ModelAndView mav = new ModelAndView();

		List<DepositForm> depositLists = fixedDepositDAO.getAllRegularAndAutoDepositsList();

		if (depositLists != null && depositLists.size() > 0) {
			depositForm.setDepositId(depositLists.get(0).getDepositId());
			model.put("depositLists", depositLists);
			mav = new ModelAndView("bankfdChangesLists", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/showDistributionRecords", method = RequestMethod.GET)
	public ModelAndView showDistributionRecords(ModelMap model, Long id, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		ModelAndView mav = null;

		List<Distribution> distributionList = fixedDepositDAO.getDistributionList(id);
		if (distributionList != null) {
			Integer index = distributionList.size() - 1;
			Deposit deposit = fixedDepositDAO.getDeposit(distributionList.get(index).getDepositId());
			String customerName = (String) fixedDepositDAO.getDepositForInterestRate(id).get(0)[8];
			model.put("distributionList", distributionList);
			model.put("customerName", customerName);
			model.put("variableBalance", distributionList.get(index).getCompoundVariableAmt());
			model.put("fixedBalance", distributionList.get(index).getCompoundFixedAmt());
			model.put("totalBalance", distributionList.get(index).getTotalBalance());
			model.put("accountNumber", deposit.getAccountNumber());
			mav = new ModelAndView("distributonList", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/autoDepositList", method = RequestMethod.GET)
	public ModelAndView autoDepositList(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {
		ModelAndView mav = null;
		List<AutoDepositForm> autoDepositLists = fixedDepositDAO.getAutoDeposits();
		if (autoDepositLists.size() > 0) {
			model.put("depositLists", autoDepositLists);
			mav = new ModelAndView("autoDepositList", "model", model);
		}

		else {
			mav = new ModelAndView("noDataFound", "model", model);

		}
		return mav;
	}

	@RequestMapping(value = "/scheduledTask", method = RequestMethod.GET)
	public ModelAndView scheduledTask(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		ModelAndView mav = new ModelAndView("scheduledTask", "model", model);
		return mav;
	}

	@RequestMapping(value = "/calculateInterestTemp", method = RequestMethod.GET)
	public String calculateInterestTemp(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		notificationScheduler.calculateInterest();
		return "redirect:scheduledTask";
	}

	@RequestMapping(value = "/calculatePayOffTemp", method = RequestMethod.GET)
	public String calculatePayOffTemp(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		notificationScheduler.calculatePayOff();
		return "redirect:scheduledTask";
	}

	@RequestMapping(value = "/createAutoDepositTemp", method = RequestMethod.GET)
	public String createAutoDepositTemp(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		notificationScheduler.createSweepDeposit();
		return "redirect:scheduledTask";
	}

	@RequestMapping(value = "/deductPremiumTemp", method = RequestMethod.GET)
	public String deductPremiumTemp(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		notificationScheduler.autoDeduction();
		return "redirect:scheduledTask";
	}

	@RequestMapping(value = "/transferMoneyOnMaturityTemp", method = RequestMethod.GET)
	public String transferMoneyOnMaturityTemp(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		notificationScheduler.transferMoneyOnMaturity();
		;
		return "redirect:scheduledTask";
	}

	@RequestMapping(value = "/interestReport", method = RequestMethod.GET)
	public ModelAndView interestReport(ModelMap model) throws CustomException {

		ModelAndView mav = new ModelAndView();

		List<DepositForm> depositLists = fixedDepositDAO.getAllDepositsList();

		if (depositLists != null && depositLists.size() > 0) {
			depositForm.setDepositId(depositLists.get(0).getDepositId());
			model.put("depositLists", depositLists);
			mav = new ModelAndView("interestReport", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;

	}

	@RequestMapping(value = "/showInterestRecords", method = RequestMethod.GET)
	public ModelAndView showInterestRecords(ModelMap model, Long id) throws CustomException {

		ModelAndView mav = new ModelAndView();

		List<Interest> interestList = interestDAO.getByDepositId(id);

		Double totalInterest = 0d;
		if (interestList.size() > 0) {
			Integer index = interestList.size() - 1;
			Deposit deposit = fixedDepositDAO.getDeposit(interestList.get(index).getDepositId());
			String customerName = (String) fixedDepositDAO.getDepositForInterestRate(id).get(0)[8];

			model.put("customerName", customerName);
			model.put("depositAccountNumber", deposit.getAccountNumber());
			model.put("interestList", interestList);

			for (int i = 0; i < interestList.size(); i++) {
				totalInterest = totalInterest + interestList.get(i).getInterestAmount();
			}
			System.out.println("totalInterest:  " + totalInterest);
			model.put("interestAmount", FDService.round(totalInterest, 2));
			mav = new ModelAndView("showInterestRecords", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;

	}

	@RequestMapping(value = "/showTdsRecords", method = RequestMethod.GET)
	public ModelAndView showTdsRecords(ModelMap model, Long id) throws CustomException {
		ModelAndView mav = new ModelAndView();
		Double tDSAmountToDeduct = 0.0;
		Double sum = 0.0;
		List<TDS> tdsList = tdsDAO.getByDepositId(id);
		for (int i = 0; i < tdsList.size(); i++) {
			tDSAmountToDeduct = tdsList.get(i).getTdsAmount();
			sum = sum + tDSAmountToDeduct;

		}

		if (tdsList.size() > 0) {
			Integer index = tdsList.size() - 1;
			Deposit deposit = null; // fixedDepositDAO.getDeposit(tdsList.get(index).getDepositId());
			String customerName = (String) fixedDepositDAO.getDepositForInterestRate(id).get(0)[8];//not used now

			model.put("customerName", customerName);
			model.put("tDSAmountToDeduct", sum);
			model.put("depositAccountNumber", deposit.getAccountNumber());
			model.put("tdsList", tdsList);

			mav = new ModelAndView("showTdsRecords", "model", model);
		} else {
			mav = new ModelAndView(Constants.NODATACUSTOMER, "model", model);
		}

		return mav;

	}

	@RequestMapping(value = "/customerSearchForFormSubmission", method = RequestMethod.GET)
	public ModelAndView customerSearchForFormSubmission(ModelMap model, String depositType) throws CustomException {
		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("customerSearchForFormSubmission", "model", model);

	}

	@RequestMapping(value = "/customerListForFormSubmission", method = RequestMethod.POST)
	public ModelAndView customerListForFormSubmission(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String depositType) throws CustomException {
		String cusId = fixedDepositForm.getCustomerID();
		String cusName = fixedDepositForm.getCustomerName();
		String cusNum = fixedDepositForm.getContactNum();
		String cusEmail = fixedDepositForm.getEmail();

		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);
		model.put("customerList", customerList);
		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("customerSearchForFormSubmission", "model", model);

	}

	@RequestMapping(value = "/formSubmission")
	public ModelAndView formSubmission(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm, Long customerId) throws CustomException {

		Customer cust = customerDAO.getById(customerId);

		depositForm.setCustomerId(customerId);

		System.out.println("cust.getCategory()...420.");
		model.put("depositForm", depositForm);
		model.put("cust", cust);

		return new ModelAndView("formSubmission", "model", model);
	}

	@RequestMapping(value = "/savedFormSubmission", method = RequestMethod.POST)
	public String savedFormSubmission(ModelMap model, @ModelAttribute DepositForm depositForm,
			RedirectAttributes attributes) throws CustomException {

		FormSubmission form = formSubmissionDao.findcustomerId(depositForm.getCustomerId());

		if (depositForm.getDocumentSubmitted().equalsIgnoreCase("NO")) {
			attributes.addFlashAttribute(Constants.ERROR, "Form not submitted");
			return "redirect:formSubmission";
		}

		String document = Constants.SUBMISSION15G;
		if (depositForm.getCategory().equals("Senior Citizen")) {
			document = Constants.SUBMISSION15H;
		}

		if (form == null) {
			form = new FormSubmission();

			form.setDocumentSubmitted(document);
			form.setCustomerId(depositForm.getCustomerId());
			form.setFinancialYear(DateService.getFinancialYear(DateService.getCurrentDate()));
			formSubmissionDao.insertFormSubmittedFor15HAnd15H(form);

		} else {
			attributes.addFlashAttribute(Constants.ERROR, "Form Already Submitted");
			return "redirect:formSubmission";
		}

		if (depositForm.getCategory().equals("Senior Citizen")) {
			attributes = updateTransaction("15H Submitted Successfully", attributes);

		} else {
			attributes = updateTransaction("15G Submitted Successfully", attributes);

		}

		return "redirect:bankFDSaved";

	}

	@RequestMapping(value = "/depositAccHolderDetails", method = RequestMethod.POST)

	public ModelAndView depositAccHolderDetails(ModelMap model, @ModelAttribute DepositForm depositForm)
			throws CustomException {

		List<DepositForm> depositList = formSubmissionDao.getDepositList(depositForm.getAccountNumber());

		if (depositList.size() > 0) {
			model.put("depositForm", depositForm);
			model.put("depositList", depositList);
		} else {
			model.put(Constants.ERROR, Constants.ACCOUNTERROR);
		}
		return new ModelAndView("formSubmission", "model", model);
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
	public ModelAndView upload(ModelMap model) throws CustomException {

		List<UploadFile> uploadedFileList = uploadFileDAO.getAllFile();
		if (uploadedFileList.size() > 0) {

			List<FileForm> fileFormList = new ArrayList<FileForm>();

			for (int i = 0; i < uploadedFileList.size(); i++) {
				if (uploadedFileList.get(i).getFileName() != null) {
					FileForm fileForm = new FileForm();
					String type = ImageService.getImageType(uploadedFileList.get(i).getFileName());
					String url = "data:image/" + type + ";base64,"
							+ Base64.encodeBase64String(uploadedFileList.get(i).getFiles());

					fileForm.setFileName(uploadedFileList.get(i).getFileName());
					fileForm.setUrl(url);
					fileFormList.add(fileForm);
				}

			}

			model.put("fileFormList", fileFormList);
		}

		model.put("uploadedFile", uploadFileForm);
		return new ModelAndView("uploadFile", "model", model);

	}

	@RequestMapping(value = "/uploadedFile", method = RequestMethod.POST)
	public ModelAndView uploadedFile(ModelMap model, @ModelAttribute UploadFileForm uploadFileForm,
			RedirectAttributes attributes) throws IOException, CustomException {
		List<MultipartFile> files = uploadFileForm.getFiles();
		if (files.get(0).getOriginalFilename() != "") {
			if (files != null) {

				for (MultipartFile file : files) {
					UploadFile uploadFile = new UploadFile();

					uploadFile.setFileContentType(file.getContentType());
					uploadFile.setFileName(file.getOriginalFilename());
					uploadFile.setFiles(file.getBytes());
					uploadFile.setUploadDate(DateService.getCurrentDate());
					uploadFileDAO.saveFile(uploadFile);
				}
				model.put("uploadedFile", uploadFileForm);
			}

			attributes = updateTransaction("Uploaded Successfully", attributes);
			return new ModelAndView("redirect:bankFDSaved");
		}

		else {
			attributes.addFlashAttribute("error", "Please select the file first");

			return new ModelAndView("redirect:uploadFile");
		}

	}

	@RequestMapping(value = "/closedTransaction", method = RequestMethod.GET)
	public ModelAndView closedTransaction(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		model.put("depositForm", depositForm);
		return new ModelAndView("closedTransaction", "model", model);

	}

	@RequestMapping(value = "/closedTransactionsList", method = RequestMethod.GET)
	public ModelAndView closedTransactionsList(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		ModelAndView mav = new ModelAndView();
		Date fromDate = depositForm.getClosingDate();
		Date toDate = depositForm.getChequeDate();
		List<DepositForm> depositLists = new ArrayList<>();
		if (fromDate != null && toDate != null) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromDateStr = sdf.format(fromDate);
			try {
				fromDate = sdf.parse(fromDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			String toDateStr = sdf.format(toDate);
			try {
				toDate = sdf.parse(toDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			depositLists = fixedDepositDAO.getClosedTransactionsList(fromDate, toDate);
		} else {

			model.put(Constants.ERROR, "Please Insert the date.");
			return new ModelAndView("closedTransaction", "model", model);
			// depositLists = fixedDepositDAO.getAllClosedTransactionsList();
		}

		if (depositLists != null && depositLists.size() > 0) {
			depositForm.setDepositId(depositLists.get(0).getDepositId());
			model.put("depositLists", depositLists);
			mav = new ModelAndView("closedTransactionsList", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/depositRate")
	public ModelAndView depositRate(ModelMap model) throws Exception, CustomException {
		EndUser user = getCurrentLoggedUserDetails();
		if (user != null) {
			model.put("user", user);
			return new ModelAndView("depositRate", "model", model);
		} else {
			throw new EndUserNotFoundException("User not Found");
		}
	}

	@RequestMapping(value = "/excelUpload")
	public ModelAndView showExcelForm(ModelMap model) throws Exception, CustomException {
		EndUser user = getCurrentLoggedUserDetails();
		if (user != null) {
			model.put("user", user);
			return new ModelAndView("excelUpload", "model", model);
		} else {
			throw new EndUserNotFoundException("User not Found");
		}
	}

	public void saveDepositFromExcel(FixedDepositForm fixedDepositForm) throws CustomException {

		String transactionId = fdService.generateRandomString();
		Deposit deposit = new Deposit();
		Customer customer = customerDAO.getAge(fixedDepositForm.getCustomerID());
		AccountDetails ad = accountDetailsDAO.findByAccountNo(fixedDepositForm.getAccountNo());
		fixedDepositForm.setAccountType(ad.getAccountType());
		// Date date = DateService.getCurrentDateTime();
		// int days = fixedDepositForm.getDaysValue() != null ?
		// fixedDepositForm.getDaysValue() : 0;
		if (fixedDepositForm.getCitizen().equals(Constants.NRI)) {
			deposit.setPrimaryNRIAccountType(fixedDepositForm.getNriAccountType());

		}

		deposit.setPrimaryCitizen(fixedDepositForm.getCitizen());
		deposit.setDepositArea(fixedDepositForm.getDepositArea());
		deposit.setPrimaryCustomerCategory(fixedDepositForm.getCategory());
		deposit.setAccountNumber(fdService.generateRandomString());
		deposit.setDepositAmount(fixedDepositForm.getFdAmount());
		deposit.setDepositAccountType(Constants.SINGLE);
		deposit.setDepositType(fixedDepositForm.getDepositType());
		deposit.setDepositCurrency(fixedDepositForm.getCurrency());
		deposit.setFlexiRate("Yes");
		deposit.setInterestRate(fixedDepositForm.getFdCreditAmount());
		deposit.setModifiedInterestRate(fixedDepositForm.getFdCreditAmount());
		deposit.setLinkedAccountNumber(fixedDepositForm.getAccountNo());
		deposit.setProductConfigurationId(fixedDepositForm.getProductConfigurationId());
		// Double value = FDService.round(fixedDepositForm.getEstimatePayOffAmount(),
		// 2);

		deposit.setCurrentBalance(Double.valueOf(deposit.getDepositAmount()));
		deposit.setPaymentMode(fixedDepositForm.getPaymentMode());
		if (fixedDepositForm.getDepositClassification().equals(Constants.fixedDeposit)) {
			deposit.setDueDate(fixedDepositForm.getDepositDate());
		} else {
			deposit.setDueDate(fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));
		}
		deposit.setPaymentType(fixedDepositForm.getPaymentType());
		deposit.setPayOffInterestType(fixedDepositForm.getPayOffInterestType());
		deposit.setStatus(Constants.OPEN);
		deposit = fdService.getMaturityAndTenureInformation(deposit, fixedDepositForm);
		deposit.setFlexiRate("Yes");
		deposit.setCreatedDate(fixedDepositForm.getDepositDate());
		deposit.setModifiedDate(fixedDepositForm.getDepositDate());
		deposit.setDepositCreationDate(fixedDepositForm.getDepositCreationDate());
		deposit.setCreatedFromBulk(true);
		deposit.setApprovalStatus(Constants.PENDING);
		deposit.setDepositClassification(fixedDepositForm.getDepositClassification());
		deposit.setTaxSavingDeposit(fixedDepositForm.getTaxSavingDeposit());
		deposit.setDeductionDay(fixedDepositForm.getDeductionDay());
		/* / / PAY OFF DATE CALCULATION / / */
		if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {
			deposit.setPayOffDueDate(fixedDepositForm.getPayoffDate());
		}

		EndUser user = getCurrentLoggedUserDetails();

		deposit.setTransactionId(transactionId);
		deposit.setCreatedBy(user.getUserName());

		if (deposit.getPaymentMode().equalsIgnoreCase("DD") || deposit.getPaymentMode().equalsIgnoreCase("Cheque")) {
			deposit.setClearanceStatus(Constants.WAITINGFORCLEARANCE);
		}

		deposit.setMaturityInstruction(fixedDepositForm.getMaturityInstruction());
		deposit.setMaturityDate(fixedDepositForm.getMaturityDate());
		deposit.setNewMaturityDate(fixedDepositForm.getMaturityDate());
		deposit.setBranchCode(fixedDepositForm.getBranchCode());
		deposit.setProductConfigurationId(fixedDepositForm.getProductConfigurationId());
		Deposit depositSaves = fixedDepositDAO.saveFD(deposit);

		List<DepositHolder> depositHolderList = new ArrayList<>();

		DepositHolder depositHolder = new DepositHolder();
		depositHolder.setContribution(100f);
		depositHolder.setCustomerId(customer.getId());
		depositHolder.setDepositHolderStatus(Constants.PRIMARY);
		depositHolder.setDepositHolderCategory(fixedDepositForm.getCategory());
		depositHolder.setDepositId(depositSaves.getId());
		depositHolder.setCitizen(customer.getCitizen());
		if (customer.getCitizen().equals(Constants.NRI))
			depositHolder.setNriAccountType(customer.getNriAccountType());

		if (fixedDepositForm.getPayOffInterestType() != null) {
			depositHolder.setNameOnBankAccount(fixedDepositForm.getOtherName());
			depositHolder.setAccountNumber(fixedDepositForm.getOtherAccount().toString());
			depositHolder.setPayOffAccountType(fixedDepositForm.getFdPayOffAccount());
			if (fixedDepositForm.getFdPayOffAccount().equals("Other")) {
				depositHolder.setTransferType(fixedDepositForm.getOtherPayTransfer());
				depositHolder.setBankName(fixedDepositForm.getOtherBank());
				depositHolder.setIfscCode(fixedDepositForm.getOtherIFSC());
			}

			depositHolder.setInterestType(fixedDepositForm.getInterstPayType());
			if (fixedDepositForm.getInterstPayType().equals("PERCENT")) {
				depositHolder.setInterestPercent(fixedDepositForm.getInterestPercent());
			} else {
				depositHolder.setInterestAmt(fixedDepositForm.getInterestPayAmount());
			}

		}

		DepositHolder depositHolderNew = depositHolderDAO.saveDepositHolder(depositHolder);

		depositHolderList.add(depositHolderNew);

		Set<DepositHolder> set = new HashSet<DepositHolder>();
		set.add(depositHolder);
		depositSaves.setDepositHolder(set);
		fixedDepositDAO.updateFD(depositSaves);

		if (fixedDepositForm.getNomineeName() != "") {
			DepositHolderNominees nominee = new DepositHolderNominees();
			nominee.setNomineeName(fixedDepositForm.getNomineeName());
			nominee.setNomineeAge(fixedDepositForm.getNomineeAge());
			nominee.setNomineeRelationship(fixedDepositForm.getNomineeRelationShip());
			nominee.setNomineeAddress(fixedDepositForm.getNomineeAddress());
			nominee.setDepositHolderId(Long.valueOf(depositHolderNew.getId()));
			nominee.setNomineeAadhar(fixedDepositForm.getNomineeAadhar());
			nominee.setNomineePan(fixedDepositForm.getNomineePan().toUpperCase());
			int age = Integer.parseInt(fixedDepositForm.getNomineeAge());
			if (age < 18) {
				nominee.setGaurdianName(fixedDepositForm.getGuardianName());
				nominee.setGaurdianAddress(fixedDepositForm.getGuardianAddress());
				nominee.setGaurdianRelation(fixedDepositForm.getGuardianRelationShip());
				nominee.setGaurdianAge(Float.parseFloat(fixedDepositForm.getGuardianAge()));
				// nominee.setGaurdianAadhar(fixedDepositForm.getGaurdianAadhar());
				// nominee.setGaurdianPan(fixedDepositForm.getGaurdianPan().toUpperCase());
			}

			nomineeDAO.saveNominee(nominee);

		}

		/*
		 * / .........SAVING PAYMENT INFO...... /
		 */
		Payment payment = new Payment();
		String fromAccountNo = "";
		String fromAccountType = "";

		/* / Deduction from Linked account / */

		if (fixedDepositForm.getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {
			AccountDetails accountDetails = accountDetailsDAO
					.findByAccountNo(fixedDepositForm.getDepositForm().getLinkedAccountNo());
			accountDetails.setAccountBalance(
					accountDetails.getAccountBalance() - Float.valueOf(fixedDepositForm.getDepositForm().getFdPay()));
			payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getAccountType());
			payment.setLinkedAccNoForFundTransfer(fixedDepositForm.getAccountNo());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getLinkedAccountNo();
			fromAccountType = fixedDepositForm.getAccountType();

			accountDetailsDAO.updateUserAccountDetails(accountDetails);
		}

		Double amountPaid = FDService.round(Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()), 2);
		payment.setAmountPaid(amountPaid);
		payment.setDepositId(depositSaves.getId());

		// For accounting entry only
		if (fixedDepositForm.getPaymentMode().contains("Cash") || fixedDepositForm.getPaymentMode().contains("cash")) {
			fromAccountNo = "";
			fromAccountType = "Cash Account";
		} else if (fixedDepositForm.getPaymentMode().contains("DD")
				|| fixedDepositForm.getPaymentMode().contains("Cheque")) {

			payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
			payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
			payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
			payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getChequeNo();
			fromAccountType = fixedDepositForm.getPaymentMode();

		} else if (fixedDepositForm.getPaymentMode().equalsIgnoreCase("Card Payment")) {

			String[] cardNoArray = fixedDepositForm.getDepositForm().getCardNo().split("-");
			String cardNo = "";
			for (int i = 0; i < cardNoArray.length; i++) {
				cardNo = cardNo + cardNoArray[i];
			}
			payment.setCardNo(Long.valueOf(cardNo));
			payment.setCardType(fixedDepositForm.getDepositForm().getCardType());

			payment.setCardCvv(fixedDepositForm.getDepositForm().getCvv());
			payment.setCardExpiryDate(fixedDepositForm.getDepositForm().getExpiryDate());

			// For accounting entry only
			fromAccountNo = cardNo;
			fromAccountType = fixedDepositForm.getDepositForm().getCardType();

		}

		else if (fixedDepositForm.getPaymentMode().equalsIgnoreCase("Net Banking")) {

			payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getDepositForm().getAccountType());
			payment.setLinkedAccNoForFundTransfer(fixedDepositForm.getDepositForm().getLinkedAccountNo());
			payment.setTransferType(fixedDepositForm.getDepositForm().getTransferType());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getLinkedAccountNo();
			fromAccountType = fixedDepositForm.getAccountType();
		}

		payment.setDepositHolderId(depositHolderNew.getId());
		//payment.setModeOfPaymentId(fixedDepositForm.getPaymentMode());
		payment.setPaymentDate(fixedDepositForm.getDepositDate());
		payment.setTransactionId(transactionId);
		payment.setCreatedBy(getCurrentLoggedUserName());
		payment = paymentDAO.insertPayment(payment);

		// Insert in Distribution and holderWiseDistribution
		calculationService.insertInPaymentDistribution(depositSaves, depositHolderList, amountPaid, payment.getId(),
				Constants.PAYMENT, null, null, null, null, null, null, null);

		// Insert in DepositSummary and HolderwiseDepositSummary
		DepositSummary depositSummary = calculationService.upsertInDepositSummary(depositSaves, Constants.PAYMENT,
				amountPaid, null, null, null, null, depositHolderList, null, null, null);

		// Insert in Journal & Ledger
		// -----------------------------------------------------------
		if (fixedDepositForm.getPaymentMode().contains("Cash"))
			fromAccountType = "Cash";

		ledgerService.insertJournal(depositSaves.getId(), customer.getId(), DateService.getCurrentDate(),
				fromAccountNo, depositSaves.getAccountNumber(), Event.OPEN_DEPOSIT.getValue(),
				amountPaid, Long.parseLong(fixedDepositForm.getDepositForm().getPaymentMode()), depositSummary.getTotalPrincipal(), transactionId);
		
//		ledgerService.insertJournalLedger(depositSaves.getId(), customer.getId(), DateService.getCurrentDate(),
//				fromAccountNo, fromAccountType, depositSaves.getAccountNumber(), "Deposit Account", "Payment",
//				amountPaid, fixedDepositForm.getPaymentMode(), depositSummary.getTotalPrincipal());
		
		
		// -----------------------------------------------------------

		// fdService.forReports(fixedDepositForm); // For reports

	}
	@RequestMapping(value = "/excelUpdate", method = RequestMethod.POST)
	public String excelUpload1(@ModelAttribute("newCaseForm") DepositRatesForm depositRatesForm, ModelMap model,
			RedirectAttributes attributes) throws Exception, CustomException {

		List<MultipartFile> files = depositRatesForm.getFiles();
		for (MultipartFile multipartFile : files) {

			String fileName = multipartFile.getOriginalFilename();
			if (fileName != null && !fileName.equals("")) {
				uploadService.saveImage(context.getRealPath("/") + "/images" + "/" + fileName, multipartFile);

				int fileImport = dumExceltoDB(fileName, depositRatesForm);
				if (fileImport == 0) {
					attributes.addFlashAttribute(Constants.ERROR, Constants.EXCELERROR);
					return "redirect:depositRate";
				}

			} else {
				if (multipartFile.getSize() > 2000000) {
					throw new MultipartException("File size exceeds");
				}
			}
		}
		EndUser user = getCurrentLoggedUserDetails();

		model.put("user", user);
		model.put("depositRatesForm", depositRatesForm);

		LogDetails details = new LogDetails();
		details.setUpdatedDate(DateService.getCurrentDate());
		details.setSummary("Imported new deposit rate");

		logDetailsDAO.save(details);
		return "redirect:depositRate";

	}

	private Row handleNullValues(Row row) {
		for (int i = 1; i < 5; i++) {
			if (row.getCell(i) == null || row.getCell(i).getCellType() == Cell.CELL_TYPE_BLANK) {
				row.createCell(i).setCellValue("");
			}
		}
		return row;
	}

	private int dumExceltoDB(String fileName, DepositRatesForm depositRateForm) throws IOException {

		try {

			String excelName = context.getRealPath("/") + "/images" + "/" + fileName;
			// Workbook wb1 = new Workbook(file);

			Row row;
			Workbook wb = WorkbookFactory.create(new File(excelName));
			Sheet sheet = wb.getSheetAt(0);
			// Sheet sheet=wb.createSheet("FirstSheet");

			/* for validating excel if null value */
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				row = handleNullValues(sheet.getRow(i));
				// String effectiveDate = new
				// DataFormatter().formatCellValue(row.getCell(0));
				String daysFrom = new DataFormatter().formatCellValue(row.getCell(1));
				String daysTo = new DataFormatter().formatCellValue(row.getCell(2));
				String rate = new DataFormatter().formatCellValue(row.getCell(3));
				String category = new DataFormatter().formatCellValue(row.getCell(4));

				if (daysFrom == "" || daysTo == "" || rate == "" || category == "") {
					return 0;
				}

			}

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				row = handleNullValues(sheet.getRow(i));
				DepositRates map = new DepositRates();

				depositRateDAO.deletebyCategory(row.getCell(4).getStringCellValue(), row.getCell(0).getDateCellValue());
				map.setEffectiveDate(row.getCell(0).getDateCellValue());
				map.setDaysFrom((int) row.getCell(1).getNumericCellValue());
				map.setDaysTo((int) row.getCell(2).getNumericCellValue());
				map.setRate((float) row.getCell(3).getNumericCellValue());
				map.setCategory(row.getCell(4).getStringCellValue());
				depositRateDAO.insertDepositRates(map);
				log.info("Import rows" + i);
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return 1;
	}

	@RequestMapping(value = "/allCustomersSaving", method = RequestMethod.GET)
	public ModelAndView allCustomersSaving(ModelMap model) throws CustomException {

		Collection<Customer> cusList = customerDAO.findCustomers();
		model.put("cusList", cusList);
		return new ModelAndView("allCustomersSaving", "model", model);

	}

	@RequestMapping(value = "/showSavingAccount", method = RequestMethod.GET)
	public ModelAndView showSavingAccount(ModelMap model, Long id) throws CustomException {

		List<AccountDetails> accDetails = accountDetailsDAO.findCurrentAndSavingAndDepositByCustomerIdAndIsActive(id);
		for (AccountDetails accountDetails : accDetails) {
			if (accountDetails.getAccountType().equalsIgnoreCase("Savings")) {
				model.put("accDetails", accountDetails);
				break;
			} else {
				model.put("accDetails", accountDetails);
				break;
			}

		}

		model.put("accountDetailsForm", accountDetailsForm);
		return new ModelAndView("showSavingAccount", "model", model);
	}

	@RequestMapping(value = "/addSavingBalance", method = RequestMethod.POST)
	public ModelAndView showSavingAccount(ModelMap model, @ModelAttribute AccountDetailsForm accountDetailsForm,
			RedirectAttributes attributes) throws CustomException {

		AccountDetails accDetails = accountDetailsDAO.findById(accountDetailsForm.getId());
		Double totalAccBalance = accDetails.getAccountBalance() + accountDetailsForm.getAccountBalance2();

		accDetails.setAccountBalance(totalAccBalance);

		accountDetailsDAO.updateUserAccountDetails(accDetails);
		model.put("accDetails", accDetails);
		attributes = updateTransaction("Amount added successfully", attributes);
//
//		String fromAccountNo = "";
//		String toAccountNo = accDetails.getAccountNo();
//		ledgerService.insertJournalLedger(null, accDetails.getCustomerID(), DateService.getCurrentDate(), fromAccountNo,
//				"", toAccountNo, accDetails.getAccountType(), "Payment to Saving Account",
//				accountDetailsForm.getAccountBalance2(), "Cash", totalAccBalance);

		return new ModelAndView("redirect:bankFDSaved");

	}

	@RequestMapping(value = "/sweepDeposit", method = RequestMethod.POST)
	public ModelAndView sweepDeposit(ModelMap model, @ModelAttribute AccountDetailsForm accountDetailsForm,
			RedirectAttributes attributes) throws CustomException {

		// Get the bank Configuration for Sweep In
		SweepConfiguration sweepConfig = sweepConfigurationDAO.getActiveSweepConfiguration();
		if (sweepConfig == null) {
			attributes.addFlashAttribute(Constants.ERROR, "Sweep is not Configured By Bank.");
			return new ModelAndView("redirect:showSavingAccount?id=" + accountDetailsForm.getCustomerID());
		}

		// Check the customer is opted for sweep facility or not
		SweepInFacilityForCustomer sweepfacilityForCustomer = sweepConfigurationDAO
				.getSweepInFacilityForCustomer(accountDetailsForm.getCustomerID());
		if (sweepfacilityForCustomer == null) {
			attributes.addFlashAttribute(Constants.ERROR, "Customer has not opted for Sweep-In facility.");
			return new ModelAndView("redirect:showSavingAccount?id=" + accountDetailsForm.getCustomerID());
		}
		if (sweepfacilityForCustomer != null && sweepfacilityForCustomer.getIsSweepInConfigureedByCustomer() == 0) {
			attributes.addFlashAttribute(Constants.ERROR, "Customer has not opted for Sweep-In facility.");
			return new ModelAndView("redirect:showSavingAccount?id=" + accountDetailsForm.getCustomerID());
		}

		if (sweepfacilityForCustomer != null && sweepfacilityForCustomer.getIsSweepInConfigureedByCustomer() == 1
				&& (sweepfacilityForCustomer.getIsSweepInRestrictedByBank() != null
						&& sweepfacilityForCustomer.getIsSweepInRestrictedByBank() == 1)) {
			attributes.addFlashAttribute(Constants.ERROR,
					"Bank has restricted the Sweep-In facility for the customer.");
			return new ModelAndView("redirect:showSavingAccount?id=" + accountDetailsForm.getCustomerID());
		}

		Long sweepConfigId = sweepConfig.getId();
		int minBalanceInSavingAcc = (sweepConfig.getMinimumSavingBalanceForSweepIn() == null) ? 0
				: sweepConfig.getMinimumSavingBalanceForSweepIn();
		int minAutoDepositAmount = (sweepConfig.getMinimumAmountRequiredForSweepIn() == null) ? 0
				: sweepConfig.getMinimumAmountRequiredForSweepIn();

		// Get the balance amount above the minimum balance that needs to keep in Saving
		// deposit for sweep
		AccountDetails accDetails = accountDetailsDAO.findSavingByCustId(accountDetailsForm.getCustomerID());
		Double balanceAmount = (double) accDetails.getAccountBalance() - minBalanceInSavingAcc;

		if (balanceAmount < minAutoDepositAmount) {

			attributes.addFlashAttribute(Constants.ERROR, "Insufficient Balance for Sweeping");
			return new ModelAndView("redirect:showSavingAccount?id=" + accountDetailsForm.getCustomerID());

		}

		// Create sweep deposit
		fdService.createSweepDeposit(accountDetailsForm.getCustomerID(), balanceAmount, sweepConfig,
				sweepfacilityForCustomer, false);

		attributes = updateTransaction("Auto Deposit Created Successfully", attributes);
		return new ModelAndView("redirect:bankFDSaved");

	}

	@RequestMapping(value = "/reportSummary", method = RequestMethod.GET)
	public ModelAndView reportSummary(ModelMap model, @ModelAttribute ReportForm reportForm) throws CustomException {

		model.put("reportForm", reportForm);
		return new ModelAndView("reportSummary", "model", model);
	}

	@RequestMapping(value = "/getSummary", method = RequestMethod.POST)
	public ModelAndView getSummary(ModelMap model, @ModelAttribute ReportForm reportForm) throws CustomException {

		List<ReportForm> reportList = fixedDepositDAO.getReportSummary(reportForm.getFromDate(),
				reportForm.getToDate());

		model.put("reportForm", reportForm);
		model.put("reportList", reportList);

		return new ModelAndView("reportSummary", "model", model);
	}

	@RequestMapping(value = "/fdListforHolderWiseResort", method = RequestMethod.GET)
	public ModelAndView fdListforHolderWiseResort(ModelMap model) throws CustomException {

		List<Deposit> depositsList = fixedDepositDAO.getOpenDeposits();
		if (depositsList != null && depositsList.size() > 0) {
			model.put("depositsList", depositsList);
			return new ModelAndView("fdListforHolderWiseResortBank", "model", model);
		}

		else {
			return new ModelAndView("noDataFound", "model", model);
		}
	}

	@RequestMapping(value = "/holderWiseReport", method = RequestMethod.GET)
	public ModelAndView holderWiseReport(ModelMap model, Long depositId, String depositAccountNo)
			throws CustomException {

		List<Object[]> distributionList = depositHolderWiseDistributionDAO.getDepositHolderWiseDistribution(depositId);

		List<DepositHolderForm> depositHolderList = depositHolderDAO.getDepositHoldersName(depositId);

		if (distributionList.size() > 0) {
			model.put("distributionList", distributionList);
			model.put("depositHolderList", depositHolderList);
			model.put("depositAccountNo", depositAccountNo);
			return new ModelAndView("holderWiseReportBank", "model", model);
		}

		else {
			model.put("error", "No transaction found for the deposit");
			return new ModelAndView("noDataFound", "model", model);
		}

	}

	@RequestMapping(value = "/customerSearch", method = RequestMethod.GET)
	public ModelAndView customerSuspend(ModelMap model) throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("customerSearch", "model", model);

	}

	@RequestMapping(value = "/searchCustomerForBankEmp", method = RequestMethod.POST)
	public ModelAndView searchCustomerForBankEmp(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String depositType) throws CustomException {
		String cusId = fixedDepositForm.getCustomerID();
		String cusName = fixedDepositForm.getCustomerName();
		String cusNum = fixedDepositForm.getContactNum();
		String cusEmail = fixedDepositForm.getEmail();

		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);

		model.put("customerList", customerList);
		model.put("fixedDepositForm", fixedDepositForm);

		if (customerList.size() != 0) {
			model.put("endUserList", customerList);
			return new ModelAndView("customerSearch", "model", model);

		} else {
			model.put(Constants.ERROR, Constants.customerNotFound);
			return new ModelAndView("customerSearch", "model", model);
		}

	}

//	@RequestMapping(value = "/reverseEmiOnBasis")
//	public ModelAndView reverseEmiOnBasis(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
//			@RequestParam Long customerId, @RequestParam String userName, RedirectAttributes attributes)
//			throws CustomException {
//
//		Customer customer = this.getCustomerDetails(customerId);
//		BankConfiguration bankConfiguration = ratesDAO.getBankConfiguration(customer.getCitizen(),
//				customer.getNriAccountType());
//
//		if (bankConfiguration.getReverseEmiOnBasis().equals(Constants.fixedAmountEmi)) {
//			return new ModelAndView("redirect:reverseEmiFixedAmountForBankEmp?userName=" + userName);
//
//		} else if (bankConfiguration.getReverseEmiOnBasis().equals(Constants.fixedTenureEmi)) {
//			return new ModelAndView("redirect:reverseEmiForBankEmp?userName=" + userName);
//		}
//
//		return new ModelAndView("redirect:reverseEmiDefault?userName=" + userName);
//
//	}

	@RequestMapping(value = "/reverseEmiDefault")
	public ModelAndView reverseEmiDefault(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			@RequestParam String userName) throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("reverseEmiDefaultForBankEmp");

	}

	@RequestMapping(value = "/reverseEmiFixedAmountForBankEmp")
	public ModelAndView reverseEmiFixedAmountForBankEmp(ModelMap model,
			@ModelAttribute FixedDepositForm fixedDepositForm, @RequestParam String userName,
			RedirectAttributes attributes, String val, Long productId) throws CustomException {
		fixedDepositForm.setProductConfigurationId(productId);

		Customer customerDetails = customerDAO.getByUserName(userName);
		/*
		 * BankConfiguration bankConfiguration = ratesDAO.findAll(); if
		 * (bankConfiguration == null) { model.put("error",
		 * "Please configure bank configuration first for creating fixed deposit");
		 * return new ModelAndView("customerSearch");
		 * 
		 * }
		 */

		ProductConfiguration productConfiguration = productConfigurationDAO.findById(productId);

		String currency = null;
		if (fixedDepositForm.getCurrency() != null) {
			currency = fixedDepositForm.getCurrency();
		}
		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(customerDetails.getId());
		fixedDepositForm.setAccountList(accountList);
		fixedDepositForm.setId(customerDetails.getId());
		fixedDepositForm.setCategory(customerDetails.getCategory());
		fixedDepositForm.setUserName(customerDetails.getUserName());
		fixedDepositForm.setEmiAmount(fixedDepositForm.getEmiAmount());

		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		boolean matched = false;
		if (val != null && val.equalsIgnoreCase("FCNR")) {
			String curFCNR[] = fdService.getFCNRCurrencies();

			// traversing array
			for (int i = 0; i < curFCNR.length; i++)// length is the property of array
			{
				if (curFCNR[i].equalsIgnoreCase(currency))
					matched = true;
			}
			if (!matched)
				currency = "USD";
		}

		matched = false;
		if (val != null && val.equalsIgnoreCase("RFC")) {
			String curRFC[] = fdService.getRFCCurrencies();

			// traversing array
			for (int i = 0; i < curRFC.length; i++)// length is the property of array
			{

				if (curRFC[i] != null) {
					if (curRFC[i].equalsIgnoreCase(currency))
						matched = true;
				}
			}
			if (!matched)
				currency = "USD";
		}
		List<Branch> branch = branchDAO.getAllBranches();
		// Long pId=Long.parseLong(productId);
		// ProductConfiguration productConfiguration =
		// productConfigurationDAO.findById(productId);
		model.put("productConfiguration", productConfiguration);
		model.put("branch", branch);
		model.put("fixedDepositForm", fixedDepositForm);
		model.put("customerId", customerDetails.getId());
		model.put("customer", customerDetails);
		// model.put("bankConfiguration", bankConfiguration);
		model.put("todaysDate", date);
		model.put("cashPayment", 1);
		model.put("ddPayment", 1);
		model.put("chequePayment", 1);
		model.put("netBanking", 0);
		model.put("val", val);
		model.put("currency", currency);
		return new ModelAndView("reverseEmiFixedAmountForBankEmp");

	}

	@RequestMapping(value = "/reverseEmiForBankEmp")
	public ModelAndView reverseEmiForBankEmp(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			@ModelAttribute EndUserForm endUserForm, RedirectAttributes attributes, @RequestParam String userName,
			String val, String productId) throws CustomException {
		Customer customerDetails = customerDAO.getByUserName(userName);
		/*
		 * BankConfiguration bankConfiguration = ratesDAO.findAll(); if
		 * (bankConfiguration == null) { model.put("error",
		 * "Please configure bank configuration first for creating fixed deposit");
		 * return new ModelAndView("customerSearch"); }
		 */
		String currency = null;

		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(customerDetails.getId());
		fixedDepositForm.setAccountList(accountList);
		fixedDepositForm.setId(customerDetails.getId());
		fixedDepositForm.setCategory(customerDetails.getCategory());
		fixedDepositForm.setCitizen(customerDetails.getCitizen());
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());

		if (fixedDepositForm.getCurrency() != null) {
			currency = fixedDepositForm.getCurrency();
		}

		boolean matched = false;
		if (val != null && val.equalsIgnoreCase("FCNR")) {
			String curFCNR[] = fdService.getFCNRCurrencies();

			// traversing array
			for (int i = 0; i < curFCNR.length; i++)// length is the property of array
			{
				if (curFCNR[i].equalsIgnoreCase(currency))
					matched = true;
			}
			if (!matched)
				currency = "USD";
		}

		matched = false;
		if (val != null && val.equalsIgnoreCase("RFC")) {
			String curRFC[] = fdService.getRFCCurrencies();

			// traversing array
			for (int i = 0; i < curRFC.length; i++)// length is the property of array
			{

				if (curRFC[i] != null) {
					if (curRFC[i].equalsIgnoreCase(currency))
						matched = true;
				}
			}
			if (!matched)
				currency = "USD";
		}
		List<Branch> branch = branchDAO.getAllBranches();
		Long pId = Long.parseLong(productId);
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(pId);
		model.put("productConfiguration", productConfiguration);
		fixedDepositForm.setProductConfigurationId(Long.valueOf(productId));
		model.put("fixedDepositForm", fixedDepositForm);
		model.put("branch", branch);
		model.put("customerId", customerDetails.getId());
		model.put("customerNriAccountType", customerDetails.getNriAccountType());
		model.put("todaysDate", date);
		/// model.put("bankConfiguration", bankConfiguration);
		model.put("cashPayment", 1);
		model.put("ddPayment", 1);
		model.put("chequePayment", 1);
		model.put("netBanking", 0);
		model.put("val", val);
		model.put("currency", currency);
		return new ModelAndView("reverseEmiForBankEmp", "model", model);
	}

	@RequestMapping(value = "/confirmReverseEmiFixedAmountForBank", method = RequestMethod.POST)
	public ModelAndView confirmReverseEmiFixedAmount(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes, @ModelAttribute EndUserForm endUserForm) throws CustomException {

		String payoff = fixedDepositForm.getPayOffInterestType();
		Integer tennure = fixedDepositForm.getEmiTenure();
		Integer tenureInMonths = 0;
		switch (payoff) {
		case "Monthly":
			tenureInMonths = tennure;
			tennure = tennure * 30;

			break;
		case "quarterly":
			tenureInMonths = tennure * 3;
			tennure = tennure * 3 * 30;

			break;
		case "halfYearly":
			tenureInMonths = tennure * 6;
			tennure = tennure * 6 * 30;

			break;
		case "annually":
			tenureInMonths = tennure * 12;
			tennure = tennure * 12 * 30;

			break;
		default:
			break;
		}
		fixedDepositForm.setTotalTenureInDays(tennure);
		fixedDepositForm.setTenureInMonths(tenureInMonths);
		fixedDepositForm.setFdFixed(0d);
		fixedDepositForm.setFdChangeable(fixedDepositForm.getFdAmount());
		fixedDepositForm.setDepositType(Constants.SINGLE);
		fixedDepositForm.setEmiAmount(fixedDepositForm.getEmiAmount());
		fixedDepositForm.setUserName(fixedDepositForm.getUserName());
		String[] accDetail = fixedDepositForm.getAccountNo().split(",");
		fixedDepositForm.setAccountNo(accDetail[0]);
		Date currentDate = DateService.getCurrentDate();
		Date maturityDate = null;
		// To get the Gestation End Date (Parameter Deposit Date and gestation
		// period)
		Date gestationDate = DateService.addYear(currentDate, fixedDepositForm.getGestationPeriod());
		Date gestationEndDate = DateService.addDays(gestationDate, -1);
		// To get the interest date till gestation
		// List<Date> interestDateTillGestation =
		// fdService.getInterestDatesTillGestation(currentDate, gestationEndDate);
		Double totalDepositAmount = fixedDepositForm.getFdAmount();
		double emiAmount = fixedDepositForm.getEmiAmount();
		// Getting the interest rate for the duration
		Customer customerDetails = customerDAO.getByUserName(fixedDepositForm.getUserName());

		fixedDepositForm.setCitizen(customerDetails.getCitizen());
		// tenure we are calculating not considering the interest as
		// we do not get the interest rate without tenure

		// Getting the interest rate for the duration
		double emiFrequency = Math.floor(totalDepositAmount / emiAmount);
		int emiTimes = (int) emiFrequency;
		maturityDate = fdService.getMaturityDate(fixedDepositForm.getPayOffInterestType(), gestationEndDate, emiTimes);
		int daysDiffBetweenDepositAndMaturity = DateService.getDaysBetweenTwoDates(currentDate, maturityDate);
		// int daysTenure = (int) (tenure * 30);
//		Float rateOfInterest = depositRateDAO.getInterestRate(customerDetails.getCategory(),
//				fixedDepositForm.getCurrency(), daysDiffBetweenDepositAndMaturity, Constants.annuityDeposit, fixedDepositForm.getFdAmount());
		Float rateOfInterest = calculationService.getDepositInterestRate(daysDiffBetweenDepositAndMaturity,
				customerDetails.getCategory(), fixedDepositForm.getCurrency(), fixedDepositForm.getFdAmount(),
				Constants.annuityDeposit, fixedDepositForm.getCitizen(), fixedDepositForm.getNriAccountType());

		if (rateOfInterest == null) {
			attributes.addFlashAttribute(Constants.ERROR, "Please contact bank for EMI Deposit");
			return new ModelAndView(
					"redirect:reverseEmiFixedAmountForBankEmp?userName=" + fixedDepositForm.getUserName());
		}

		fixedDepositForm.setFdTenureType("Days");
		fixedDepositForm.setInterestPayAmount((float) emiAmount);
		fixedDepositForm.setFdCreditAmount(rateOfInterest);
		fixedDepositForm.setMaturityDate(maturityDate);

//		List<Interest> interestList = null;
//		if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {
//
//			List<Date> payoffDateList = null;
//			if (DateService.getDaysBetweenTwoDates(currentDate, gestationEndDate) >= 0)
//				payoffDateList = fdService.getPayoffDatesForFixedAmountEmi(gestationEndDate,
//						fixedDepositForm.getPayOffInterestType(), emiTimes); // Doubt
//																				// on
//																				// start
//																				// date
//			else
//				payoffDateList = fdService.getPayoffDatesForFixedAmountEmi(currentDate,
//						fixedDepositForm.getPayOffInterestType(), emiTimes);// Doubt
//
//			// To get the interest List for fixed amount emi
//			/*
//			 * interestList = fdService.getInterestListForFixedAmountEmi(currentDate,
//			 * maturityDate, rateOfInterest, emiAmount, totalDepositAmount,
//			 * gestationEndDate, 0l, fixedDepositForm.getPayOffInterestType(), emiTimes,
//			 * payoffDateList);
//			 */
//
//			/*
//			 * if (payoffDateList != null) { // Date interestPayOffDate = //
//			 * fdService.calculateInterestPayOffDueDate(fixedDepositForm.
//			 * getPayOffInterestType(), // fixedDepositForm.getMaturityDate(), currentDate);
//			 * 
//			 * fixedDepositForm.setPayoffDate(payoffDateList.get(0)); }
//			 */
//		}

		// Float totalInterest = interestList != null ?
		// fdService.getTotalInterestAmount(interestList) : 0f;
		// fdService.getTotalTDSAmount(currentDate,
		// maturityDate, fixedDepositForm.getCategory(),
		// fixedDepositForm, interestList);

		// fixedDepositForm.setEstimateTDSDeduct(totalTDS);
		// fixedDepositForm.setEstimateInterest(totalInterest);
		// fixedDepositForm.setEstimatePayOffAmount(totalDepositAmount + totalInterest -
		// totalTDS);

		/*
		 * PAY OFF DATE CALCULATION
		 * 
		 * Date interestPayOffDate =
		 * fdService.calculateInterestPayOffDueDate(fixedDepositForm.
		 * getPayOffInterestType(), fixedDepositForm.getMaturityDate(), currentDate);
		 * fixedDepositForm.setPayoffDate(interestPayOffDate);
		 */

		Float totalTDS = 0f;

		Date payOffDueDate = fdService.getRevereseEMIPayOffDueDate(DateService.getCurrentDate(), gestationEndDate,
				maturityDate, fixedDepositForm.getPayOffInterestType(), false);
		fixedDepositForm.setPayoffDate(payOffDueDate);
		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("confirmReverseEmiFixedAmountForBank", "model", model);
	}

	@RequestMapping(value = "/confirmReverseEmiForBankEmp", method = RequestMethod.POST)
	public ModelAndView confirmReverseEmi(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes) throws CustomException {

		Date currentDate = DateService.getCurrentDate();
		Customer customerDetails = customerDAO.getByUserName(fixedDepositForm.getUserName());
		Customer customer = getCustomerDetails(customerDetails.getId());
		String category = calculationService.geCustomerActualCategory(customer);
		fixedDepositForm.setCategory(category);

		fixedDepositForm.setFdFixed(0d);
		fixedDepositForm.setFdChangeable(fixedDepositForm.getFdAmount());
		fixedDepositForm.setUserName(fixedDepositForm.getUserName());
		fixedDepositForm.setDepositType(Constants.SINGLE);
		double emiAmount = fixedDepositForm.getEmiAmount();
		String[] accDetail = fixedDepositForm.getAccountNo().split(",");
		fixedDepositForm.setAccountNo(accDetail[0]);
		Date maturityDate = null;
		Integer gestationPeriod = fixedDepositForm.getGestationPeriod();
		Date gestationDate = DateService.addYear(currentDate, gestationPeriod);
		Date gestationEndDate = DateService.addDays(gestationDate, -1);
		Double totalDepositAmount = fixedDepositForm.getFdAmount();
		double emiFrequency = Math.floor(totalDepositAmount / emiAmount);
		// double remainEmiAmount = totalDepositAmount - emiAmount;
		int emiTimes = (int) emiFrequency;

		maturityDate = fdService.getMaturityDate(fixedDepositForm.getPayOffInterestType(), gestationEndDate, emiTimes);
		/*
		 * if (fixedDepositForm.getFdTenureType().equalsIgnoreCase(Constants.YEAR) &&
		 * fixedDepositForm.getDaysValue() != null) { maturityDate =
		 * DateService.generateDaysDate(maturityDate, fixedDepositForm.getDaysValue() +
		 * 1);
		 * 
		 * }
		 */

		int daysDiff = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate) + 1;
//		Float rateOfInterest = depositRateDAO.getInterestRate(customerDetails.getCategory(),
//				fixedDepositForm.getCurrency(), daysDiff, Constants.annuityDeposit, fixedDepositForm.getFdAmount());

		Float rateOfInterest = calculationService.getDepositInterestRate(daysDiff, category,
				fixedDepositForm.getCurrency(), fixedDepositForm.getFdAmount(), Constants.annuityDeposit,
				fixedDepositForm.getCitizen(), fixedDepositForm.getNriAccountType());

		if (rateOfInterest == null) {
			attributes.addFlashAttribute(Constants.ERROR, Constants.invalidTenure);
			return new ModelAndView("redirect:reverseEmiForBankEmp");
		}
		fixedDepositForm.setInterestPayAmount((float) emiAmount);
		fixedDepositForm.setFdCreditAmount(rateOfInterest);
		fixedDepositForm.setMaturityDate(maturityDate);

//		List<Interest> interestList = null;
//		if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {
//
//			List<Date> payoffDateList = null;
//			if (DateService.getDaysBetweenTwoDates(currentDate, gestationEndDate) >= 0)
//				payoffDateList = fdService.getPayoffDatesForFixedAmountEmi(gestationEndDate,
//						fixedDepositForm.getPayOffInterestType(), emiTimes);
//			else
//				payoffDateList = fdService.getPayoffDatesForFixedAmountEmi(currentDate,
//						fixedDepositForm.getPayOffInterestType(), emiTimes);
//
//			// To get the interest List for fixed amount emi
//			interestList = fdService.getInterestListForFixedAmountEmi(currentDate, maturityDate, rateOfInterest,
//					emiAmount, totalDepositAmount, gestationEndDate, 0l, fixedDepositForm.getPayOffInterestType(),
//					emiTimes, payoffDateList);
//
//			if (payoffDateList != null) {
//				// Date interestPayOffDate =
//				// fdService.calculateInterestPayOffDueDate(fixedDepositForm.getPayOffInterestType(),
//				// fixedDepositForm.getMaturityDate(), currentDate);
//
//				fixedDepositForm.setPayoffDate(payoffDateList.get(0));
//			}
//		}
//
//		Float totalInterest = interestList != null ? fdService.getTotalInterestAmount(interestList) : 0f;
//		Float totalTDS = 0f;
//		fixedDepositForm.setEstimateTDSDeduct(totalTDS);
//		fixedDepositForm.setEstimateInterest(totalInterest);
//		fixedDepositForm.setEstimatePayOffAmount(totalDepositAmount + totalInterest - totalTDS);

		/*
		 * List<Date> payoffDateList = fdService.getEMIPayoffDates(gestationEndDate,
		 * maturityDate, fixedDepositForm.getPayOffInterestType()); if
		 * (payoffDateList.size() > 0)
		 * fixedDepositForm.setPayoffDate(payoffDateList.get(0));
		 */

		Date payOffDueDate = fdService.getRevereseEMIPayOffDueDate(DateService.getCurrentDate(), gestationEndDate,
				maturityDate, fixedDepositForm.getPayOffInterestType(), false);
		fixedDepositForm.setPayoffDate(payOffDueDate);
		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("confirmReverseEmiForBankEmp", "model", model);
	}

	

	@RequestMapping(value = "/clearanceList", method = RequestMethod.GET)
	public ModelAndView clearanceList(ModelMap model) throws CustomException {

		ModelAndView mav = new ModelAndView();
		List<Deposit> clearanceList = fixedDepositDAO.findByClearanceStatus();
		if (clearanceList.size() > 0) {
			model.put("clearanceList", clearanceList);
			mav = new ModelAndView("clearanceList", "model", model);
		} else {

			mav = new ModelAndView("noDataFound", "model", model);
		}
		return mav;
	}

	@RequestMapping(value = "/depositClearance")
	public ModelAndView depositClearance(ModelMap model, @RequestParam Long[] ids) throws CustomException {

		List<Long> depositIds = Arrays.asList(ids);

		List<Deposit> depositList = fixedDepositDAO.findByMultipleId(depositIds);

		depositsList.setdList(depositList);

		model.put("depositsList", depositsList);

		return new ModelAndView("depositClearance", "model", model);

	}

	@RequestMapping(value = "/confirmDepositClearance")
	public ModelAndView confirmDepositClearance(ModelMap model, @ModelAttribute DepositsList depositsList,
			RedirectAttributes attributes) throws CustomException {

		List<Deposit> deposits = depositsList.getdList();
		// Deposit deposit = new Deposit();
		for (int i = 0; i < deposits.size(); i++) {

			// deposit.setId(null);

			Deposit deposit = fixedDepositDAO.getDeposit(deposits.get(i).getId());
			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
			deposit.setClearanceStatus(null);

			Set<DepositHolder> set = new HashSet<DepositHolder>();
			set.addAll(depositHolderList);
			deposit.setDepositHolder(set);
			fixedDepositDAO.updateFD(deposit);
		}

		attributes = updateTransaction("Deposit cleared successfully", attributes);
		return new ModelAndView("redirect:bankFDSaved");
	}

	@RequestMapping(value = "/unSuccessfulPayOffDetails", method = RequestMethod.GET)
	public ModelAndView unSuccessfulPayOffDetails(ModelMap model) throws CustomException {
		model.put("payOfForm", payOfForm);
		return new ModelAndView("unSuccessfulPayOffDetails", "model", model);

	}

	@RequestMapping(value = "/unSuccessfulPayOffDetailsList", method = RequestMethod.GET)
	public ModelAndView unSuccessfulPayOffDetailsList(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute PayOfForm payOfForm) throws CustomException {

		ModelAndView mav = new ModelAndView();
		Date fromDate = payOfForm.getUnSuccessfulPayoffDetailsDateFrom();
		Date toDate = payOfForm.getUnSuccessfulPayoffDetailsDateTo();
		List<UnSuccessfulPayOff> unSuccessfulPayOffDetailsList = new ArrayList<>();
		if (fromDate != null && toDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromDateStr = sdf.format(fromDate);
			try {
				fromDate = sdf.parse(fromDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			String toDateStr = sdf.format(toDate);
			try {
				toDate = sdf.parse(toDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			unSuccessfulPayOffDetailsList = unSuccessfulPayOffDetailsDAO.unSuccessfulPayOffDetailsList(fromDate,
					toDate);
		} else {
			model.put(Constants.ERROR, "Please Insert the date.");
			return new ModelAndView("unSuccessfulPayOffDetails", "model", model);
			// depositLists = fixedDepositDAO.getAllClosedTransactionsList();

		}

		if (unSuccessfulPayOffDetailsList != null && unSuccessfulPayOffDetailsList.size() > 0) {
			payOfForm.setDepositid(unSuccessfulPayOffDetailsList.get(0).getDepositid());
			model.put("unSuccessfulPayOffDetailsList", unSuccessfulPayOffDetailsList);
			mav = new ModelAndView("unSuccessfulPayOffDetailsList", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/getLooseAmountForWithdraw", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Double getLooseAmountForWithdraw(ModelMap model, Long depositId, Double withdrawAmt)
			throws CustomException {
		System.out.println("getLooseAmountForWithdraw");
		System.out.println("DepositId: " + depositId);
		System.out.println("withdrawAmt: " + withdrawAmt);

		Deposit deposit = fixedDepositDAO.getDeposit(depositId);
		Double amountToAdjust = fdService.getAmountToLose(deposit, withdrawAmt);

		amountToAdjust = amountToAdjust == null ? 0d : amountToAdjust;
		System.out.println("amountToAdjust: " + amountToAdjust);
		return fdService.round(amountToAdjust, 2);
	}

	@RequestMapping(value = "/searchCustForModificationReport", method = RequestMethod.GET)
	public ModelAndView searchCustForModificationReport(ModelMap model, String depositType) throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("searchCustForModificationReport", "model", model);
	}

	@RequestMapping(value = "/listOfCustomers", method = RequestMethod.POST)
	public ModelAndView listOfCustomers(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String depositType) throws CustomException {
		String cusId = fixedDepositForm.getCustomerID();
		String cusName = fixedDepositForm.getCustomerName();
		String cusNum = fixedDepositForm.getContactNum();
		String cusEmail = fixedDepositForm.getEmail();
		System.out.println("cust....");
		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);
		model.put("customerList", customerList);
		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("searchCustForModificationReport", "model", model);
	}

	@RequestMapping(value = "/modifiedDeposits")
	public ModelAndView modificationReport(ModelMap model, Long customerId) throws CustomException {
		List<Object[]> objList = depositHolderDAO.getOpenDeposit(customerId);
		if (objList.size() > 0) {

			model.put("objList", objList);
			return new ModelAndView("modifiedDeposits", "model", model);
		} else {
			model.put("error", "No deposits found");
			return new ModelAndView("noDataFound", "model", model);
		}
	}

	@RequestMapping(value = "/showModificationList", method = RequestMethod.GET)
	public ModelAndView showModificationList(ModelMap model, Long depositId) throws CustomException {

		List<Object[]> objList = depositModificationDAO.getAllModificationList(depositId);

		if (objList != null) {

			ProductConfiguration productConfiguration = productConfigurationDAO
					.findById(fixedDepositDAO.findByDepositId(depositId).getProductConfigurationId());
			String modificationBasis = productConfiguration.getModificationBasis();
			Integer configurationCount = productConfiguration.getMaximumLimitOfModification() != null
					? productConfiguration.getMaximumLimitOfModification()
					: 0;
			Long count = 0l;

			BigInteger idBig;
			if (productConfiguration != null) {
				for (int i = 0; i < objList.size(); i++) {
					idBig = (BigInteger) objList.get(i)[0];
					count = depositModificationDAO.getModificationCount(idBig.longValue(), modificationBasis);

					if (count >= configurationCount) {

						objList.get(i)[4] = "Yes";
					} else {
						objList.get(i)[4] = "No";
					}

				}
			}
			model.put("objList", objList);
			return new ModelAndView("showModificationList", "model", model);
		} else {
			model.put("error", "No modification found for deposit id:" + depositId);
			return new ModelAndView("noDataFound", "model", model);
		}
	}

	@RequestMapping(value = "/compareWithModification", method = RequestMethod.GET)
	public ModelAndView compareWithModification(ModelMap model, String modificationNo, String preModificationNo)
			throws CustomException {
		DepositHolderForm depositHolderForm = new DepositHolderForm();
		DepositHolderForm depositHolderFormPrev = new DepositHolderForm();

		List<DepositModification> modificationList = depositModificationDAO.getByModificationNo(modificationNo);
		List<DepositModification> modificationListPrev = depositModificationDAO.getByModificationNo(preModificationNo);

		depositHolderForm.setDepositId(modificationList.get(0).getDepositId());

		depositHolderForm.setCreatedDate(modificationList.get(0).getModifiedDate());
		depositHolderForm.setStatus(modificationList.get(0).getModifiedBy());
		depositHolderFormPrev.setCreatedDate(modificationListPrev.get(0).getModifiedDate());

		Boolean value1isEmpty = false;
		Boolean value2isEmpty = false;

		Boolean notNullValue = false;
		Boolean nullValue = false;
		Integer isChanged = 0;

		if (!(Double.compare(modificationList.get(0).getDepositAmount(),
				modificationListPrev.get(0).getDepositAmount()) == 0)) {

			depositHolderForm.setDepositamount(modificationList.get(0).getDepositAmount());
			depositHolderFormPrev.setDepositamount(modificationListPrev.get(0).getDepositAmount());
			isChanged = 1;
		}

		if (!(modificationList.get(0).getPaymentType().equals(modificationListPrev.get(0).getPaymentType()))) {

			value1isEmpty = StringUtils.isEmpty(modificationList.get(0).getPaymentType());
			value2isEmpty = StringUtils.isEmpty(modificationListPrev.get(0).getPaymentType());

			if (!(value1isEmpty && value2isEmpty)) {

				depositHolderForm.setPaymentType(modificationList.get(0).getPaymentType());
				depositHolderFormPrev.setPaymentType(modificationListPrev.get(0).getPaymentType());
				isChanged = 1;
			}

		}

		if (modificationList.get(0).getTenure() != modificationListPrev.get(0).getTenure()
				|| !(modificationList.get(0).getTenureType().equals(modificationListPrev.get(0).getTenureType()))) {

			depositHolderForm.setTenure(modificationList.get(0).getTenure());
			depositHolderFormPrev.setTenure(modificationListPrev.get(0).getTenure());

			depositHolderForm.setTenureType(modificationList.get(0).getTenureType());
			depositHolderFormPrev.setTenureType(modificationListPrev.get(0).getTenureType());
			isChanged = 1;

		}

		if (!(modificationList.get(0).getPaymentMode().equals(modificationListPrev.get(0).getPaymentMode()))) {

			value1isEmpty = StringUtils.isEmpty(modificationList.get(0).getPaymentMode());
			value2isEmpty = StringUtils.isEmpty(modificationListPrev.get(0).getPaymentMode());

			if (!(value1isEmpty && value2isEmpty)) {

				depositHolderForm.setPaymentMode(modificationList.get(0).getPaymentMode());
				depositHolderFormPrev.setPaymentMode(modificationListPrev.get(0).getPaymentMode());
				isChanged = 1;

			}
		}

		if (!(Float.compare(modificationList.get(0).getInterestRate(),
				modificationListPrev.get(0).getInterestRate()) == 0)) {

			depositHolderForm.setInterestRate(modificationList.get(0).getInterestRate());
			depositHolderFormPrev.setInterestRate(modificationListPrev.get(0).getInterestRate());
			isChanged = 1;

		}
		if (modificationList.get(0).getMaturityDate().compareTo(modificationListPrev.get(0).getMaturityDate()) != 0) {

			depositHolderForm.setMaturityDate(modificationList.get(0).getMaturityDate());
			depositHolderFormPrev.setMaturityDate(modificationListPrev.get(0).getMaturityDate());
			isChanged = 1;

		}

		if (!(modificationList.get(0).getPayOffType().equals(modificationListPrev.get(0).getPayOffType()))) {

			value1isEmpty = StringUtils.isEmpty(modificationList.get(0).getPayOffType());
			value2isEmpty = StringUtils.isEmpty(modificationListPrev.get(0).getPayOffType());

			if (!(value1isEmpty && value2isEmpty)) {

				depositHolderForm.setInterstPayType(modificationList.get(0).getPayOffType());
				depositHolderFormPrev.setInterstPayType(modificationListPrev.get(0).getPayOffType());
				isChanged = 1;

			}
		}

		model.put("isChanged", isChanged);
		/* /comparing single fields with the previous modification */

		/* comparing pay off changes............ */

		List<DepositHolder> depositHolderList = new ArrayList<DepositHolder>();
		List<DepositHolder> depositHolderListPrev = new ArrayList<DepositHolder>();

		for (int i = 0; i < modificationList.size(); i++) {
			DepositModification depModification = modificationList.get(i);
			Long depositHolderId = depModification.getDepositHolderId();

			DepositModification prevModListForHolder = depositModificationDAO.getPreviousModification(depositHolderId,
					depModification.getId());

			int changed = 0;
			DepositHolder depositHolder = new DepositHolder();
			DepositHolder depositHolderPrev = new DepositHolder();

			depositHolder.setId(depositHolderId);
			/* comparing with depositModification table */

			if (prevModListForHolder != null && depModification.getPayOffInterestType() != null
					&& prevModListForHolder.getPayOffInterestType() != null) {

				if (!(depModification.getPayOffInterestType().equals(prevModListForHolder.getPayOffInterestType()))) {

					value1isEmpty = StringUtils.isEmpty(depModification.getPayOffInterestType());
					value2isEmpty = StringUtils.isEmpty(prevModListForHolder.getPayOffInterestType());

					if (!(value1isEmpty && value2isEmpty)) {

						depositHolder.setInterestType(depModification.getPayOffInterestType());
						depositHolderPrev.setInterestType(prevModListForHolder.getPayOffInterestType());

						changed = 1;

					}
				}

				notNullValue = depModification.getPayOffInterestAmt() != null
						&& prevModListForHolder.getPayOffInterestAmt() != null;
				nullValue = depModification.getPayOffInterestAmt() == null
						&& prevModListForHolder.getPayOffInterestAmt() == null;

				if (notNullValue) {
					if (Float.compare(depModification.getPayOffInterestAmt(),
							prevModListForHolder.getPayOffInterestAmt()) != 0) {
						depositHolder.setInterestAmt(depModification.getPayOffInterestAmt());
						depositHolderPrev.setInterestAmt(prevModListForHolder.getPayOffInterestAmt());
						changed = 1;
					}
				} else if (nullValue) {
				} else {
					depositHolder.setInterestAmt(depModification.getPayOffInterestAmt());
					depositHolderPrev.setInterestAmt(prevModListForHolder.getPayOffInterestAmt());
					changed = 1;
				}

				notNullValue = depModification.getPayOffInterestPercent() != null
						&& prevModListForHolder.getPayOffInterestPercent() != null;
				nullValue = depModification.getPayOffInterestPercent() == null
						&& prevModListForHolder.getPayOffInterestPercent() == null;
				if (notNullValue) {
					if (Float.compare(depModification.getPayOffInterestPercent(),
							prevModListForHolder.getPayOffInterestPercent()) != 0) {

						depositHolder.setInterestPercent(depModification.getPayOffInterestPercent());
						depositHolderPrev.setInterestPercent(prevModListForHolder.getPayOffInterestPercent());

						changed = 1;
					}
				} else if (nullValue) {
				}

				else {
					depositHolder.setInterestPercent(depModification.getPayOffInterestPercent());
					depositHolderPrev.setInterestPercent(prevModListForHolder.getPayOffInterestPercent());

					changed = 1;
				}

				if (!(depModification.getPayOffAccountType().equals(prevModListForHolder.getPayOffAccountType()))) {

					value1isEmpty = StringUtils.isEmpty(depModification.getPayOffAccountType());
					value2isEmpty = StringUtils.isEmpty(prevModListForHolder.getPayOffAccountType());

					if (!(value1isEmpty && value2isEmpty)) {

						depositHolder.setPayOffAccountType(depModification.getPayOffAccountType());
						depositHolderPrev.setPayOffAccountType(prevModListForHolder.getPayOffAccountType());

						changed = 1;
					}
				}

				if (depModification.getPayOffTransferType() != null) {
					if (!(depModification.getPayOffTransferType()
							.equals(prevModListForHolder.getPayOffTransferType()))) {

						value1isEmpty = StringUtils.isEmpty(depModification.getPayOffTransferType());
						value2isEmpty = StringUtils.isEmpty(prevModListForHolder.getPayOffTransferType());

						if (!(value1isEmpty && value2isEmpty)) {

							depositHolder.setTransferType(depModification.getPayOffTransferType());
							depositHolderPrev.setTransferType(prevModListForHolder.getPayOffTransferType());

							changed = 1;
						}
					}
				}
				if (!(depModification.getPayOffNameOnBankAccount()
						.equals(prevModListForHolder.getPayOffNameOnBankAccount()))) {

					value1isEmpty = StringUtils.isEmpty(depModification.getPayOffNameOnBankAccount());
					value2isEmpty = StringUtils.isEmpty(prevModListForHolder.getPayOffNameOnBankAccount());

					if (!(value1isEmpty && value2isEmpty)) {

						depositHolder.setNameOnBankAccount(depModification.getPayOffNameOnBankAccount());
						depositHolderPrev.setNameOnBankAccount(prevModListForHolder.getPayOffNameOnBankAccount());

						changed = 1;
					}
				}

				if (!(depModification.getPayOffAccountNumber().equals(prevModListForHolder.getPayOffAccountNumber()))) {

					value1isEmpty = StringUtils.isEmpty(depModification.getPayOffAccountNumber());
					value2isEmpty = StringUtils.isEmpty(prevModListForHolder.getPayOffAccountNumber());

					if (!(value1isEmpty && value2isEmpty)) {

						depositHolder.setAccountNumber(depModification.getPayOffAccountNumber());
						depositHolderPrev.setAccountNumber(prevModListForHolder.getPayOffAccountNumber());

						changed = 1;
					}
				}

				if (depModification.getPayOffBankName() != null) {
					if (!(depModification.getPayOffBankName().equals(prevModListForHolder.getPayOffBankName()))) {

						value1isEmpty = StringUtils.isEmpty(depModification.getPayOffBankName());
						value2isEmpty = StringUtils.isEmpty(prevModListForHolder.getPayOffBankName());

						if (!(value1isEmpty && value2isEmpty)) {

							depositHolder.setBankName(depModification.getPayOffBankName());
							depositHolderPrev.setBankName(prevModListForHolder.getPayOffBankName());

							changed = 1;
						}
					}
				}

				if (depModification.getPayOffBankIFSCCode() != null) {
					if (!(depModification.getPayOffBankIFSCCode()
							.equals(prevModListForHolder.getPayOffBankIFSCCode()))) {

						value1isEmpty = StringUtils.isEmpty(depModification.getPayOffBankIFSCCode());
						value2isEmpty = StringUtils.isEmpty(prevModListForHolder.getPayOffBankIFSCCode());

						if (!(value1isEmpty && value2isEmpty)) {

							depositHolder.setIfscCode(depModification.getPayOffBankIFSCCode());
							depositHolderPrev.setIfscCode(prevModListForHolder.getPayOffBankIFSCCode());

							changed = 1;
						}
					}
				}
			}

			else {
				DepositHolder depositHolderExisting = depositHolderDAO.findById(depositHolderId);

				if (depModification.getPayOffInterestType() == null)
					depModification.setPayOffInterestType("");

				if (depositHolderExisting.getInterestType() == null)
					depositHolderExisting.setInterestType("");

				if (!(depModification.getPayOffInterestType().equals(depositHolderExisting.getInterestType()))) {

					value1isEmpty = StringUtils.isEmpty(depModification.getPayOffInterestType());
					value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getInterestType());

					if (!(value1isEmpty && value2isEmpty)) {

						depositHolder.setInterestType(depModification.getPayOffInterestType());
						depositHolderPrev.setInterestType(depositHolderExisting.getInterestType());

						changed = 1;

					}
				}

				notNullValue = depModification.getPayOffInterestAmt() != null
						&& depositHolderExisting.getInterestAmt() != null;
				nullValue = depModification.getPayOffInterestAmt() == null
						&& depositHolderExisting.getInterestAmt() == null;

				if (notNullValue) {
					if (Float.compare(depModification.getPayOffInterestAmt(),
							depositHolderExisting.getInterestAmt()) != 0) {

						depositHolder.setInterestAmt(depModification.getPayOffInterestAmt());
						depositHolderPrev.setInterestAmt(depositHolderExisting.getInterestAmt());

						changed = 1;
					}
				} else if (nullValue) {
				}

				else {
					depositHolder.setInterestAmt(depModification.getPayOffInterestAmt());
					depositHolderPrev.setInterestAmt(depositHolderExisting.getInterestAmt());

					changed = 1;
				}

				notNullValue = depModification.getPayOffInterestPercent() != null
						&& depositHolderExisting.getInterestPercent() != null;
				nullValue = depModification.getPayOffInterestPercent() == null
						&& depositHolderExisting.getInterestPercent() == null;

				if (notNullValue) {
					if (Float.compare(depModification.getPayOffInterestPercent(),
							depositHolderExisting.getInterestPercent()) != 0) {

						depositHolder.setInterestPercent(depModification.getPayOffInterestPercent());
						depositHolderPrev.setInterestPercent(depositHolderExisting.getInterestPercent());

						changed = 1;
					}
				} else if (nullValue) {
				}

				else {
					depositHolder.setInterestPercent(depModification.getPayOffInterestPercent());
					depositHolderPrev.setInterestPercent(depositHolderExisting.getInterestPercent());

					changed = 1;
				}
				if (depModification.getPayOffAccountType() != null) {
					if (!(depModification.getPayOffAccountType()
							.equals(depositHolderExisting.getPayOffAccountType()))) {

						value1isEmpty = StringUtils.isEmpty(depModification.getPayOffAccountType());
						value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getPayOffAccountType());

						if (!(value1isEmpty && value2isEmpty)) {

							depositHolder.setPayOffAccountType(depModification.getPayOffAccountType());
							depositHolderPrev.setPayOffAccountType(depositHolderExisting.getPayOffAccountType());

							changed = 1;
						}
					}
				}

				if (depModification.getPayOffTransferType() != null) {

					if (!(depModification.getPayOffTransferType().equals(depositHolderExisting.getTransferType()))) {
						value1isEmpty = StringUtils.isEmpty(depModification.getPayOffTransferType());
						value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getTransferType());

						if (!(value1isEmpty && value2isEmpty)) {

							depositHolder.setTransferType(depModification.getPayOffTransferType());
							depositHolderPrev.setTransferType(depositHolderExisting.getTransferType());

							changed = 1;
						}
					}
				}

				if (depModification.getPayOffNameOnBankAccount() != null) {
					if (!(depModification.getPayOffNameOnBankAccount()
							.equals(depositHolderExisting.getNameOnBankAccount()))) {
						value1isEmpty = StringUtils.isEmpty(depModification.getPayOffNameOnBankAccount());
						value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getNameOnBankAccount());

						if (!(value1isEmpty && value2isEmpty)) {

							depositHolder.setNameOnBankAccount(depModification.getPayOffNameOnBankAccount());
							depositHolderPrev.setNameOnBankAccount(depositHolderExisting.getNameOnBankAccount());

							changed = 1;
						}
					}
				}

				if (depModification.getPayOffAccountNumber() != null) {
					if (!(depModification.getPayOffAccountNumber().equals(depositHolderExisting.getAccountNumber()))) {

						value1isEmpty = StringUtils.isEmpty(depModification.getPayOffAccountNumber());
						value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getAccountNumber());

						if (!(value1isEmpty && value2isEmpty)) {

							depositHolder.setAccountNumber(depModification.getPayOffAccountNumber());
							depositHolderPrev.setAccountNumber(depositHolderExisting.getAccountNumber());

							changed = 1;
						}
					}
				}
				if (depModification.getPayOffBankName() != null) {
					if (!(depModification.getPayOffBankName().equals(depositHolderExisting.getBankName()))) {

						value1isEmpty = StringUtils.isEmpty(depModification.getPayOffBankName());
						value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getBankName());

						if (!(value1isEmpty && value2isEmpty)) {

							depositHolder.setBankName(depModification.getPayOffBankName());
							depositHolderPrev.setBankName(depositHolderExisting.getBankName());

							changed = 1;
						}
					}
				}

				if (depModification.getPayOffBankIFSCCode() != null) {
					if (!(depModification.getPayOffBankIFSCCode().equals(depositHolderExisting.getIfscCode()))) {

						value1isEmpty = StringUtils.isEmpty(depModification.getPayOffBankIFSCCode());
						value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getIfscCode());

						if (!(value1isEmpty && value2isEmpty)) {

							depositHolder.setIfscCode(depModification.getPayOffBankIFSCCode());
							depositHolderPrev.setIfscCode(depositHolderExisting.getIfscCode());

							changed = 1;
						}
					}
				}

			}

			if (changed == 1) {

				depositHolderList.add(depositHolder);
				depositHolderListPrev.add(depositHolderPrev);
			}

		}

		depositHolderForm.setDepositHolder(depositHolderList);
		depositHolderFormPrev.setDepositHolder(depositHolderListPrev);

		model.put("depositHolderForm", depositHolderForm);
		model.put("depositHolderFormPrev", depositHolderFormPrev);

		return new ModelAndView("compareWithModification", "model", model);
	}

	@RequestMapping(value = "/compareWithDeposit", method = RequestMethod.GET)
	public ModelAndView compareWithDeposit(ModelMap model, String modificationNo, Long depositId)
			throws CustomException {
		DepositHolderForm depositHolderForm = new DepositHolderForm();
		DepositHolderForm depositHolderFormPrev = new DepositHolderForm();

		List<DepositModification> modificationList = depositModificationDAO.getByModificationNo(modificationNo);
		Deposit deposit = fixedDepositDAO.getDeposit(depositId);

		depositHolderForm.setDepositId(modificationList.get(0).getDepositId());

		depositHolderForm.setCreatedDate(modificationList.get(0).getModifiedDate());
		depositHolderForm.setStatus(modificationList.get(0).getModifiedBy());
		depositHolderFormPrev.setCreatedDate(deposit.getModifiedDate());

		Boolean value1isEmpty = false;
		Boolean value2isEmpty = false;
		Integer isChanged = 0;

		/* comparing single fields with the previous modification */

		if (!(Double.compare(modificationList.get(0).getDepositAmount(), deposit.getDepositAmount()) == 0)) {

			/*
			 * if (modificationList.get(0).getDepositAmount() != deposit.getDepositAmount())
			 * {
			 */
			depositHolderForm.setDepositamount(modificationList.get(0).getDepositAmount());
			depositHolderFormPrev.setDepositamount(deposit.getDepositAmount());
			isChanged = 1;
		}

		if (!(modificationList.get(0).getPaymentType().equals(deposit.getPaymentType()))) {

			value1isEmpty = StringUtils.isEmpty(modificationList.get(0).getPaymentType());
			value2isEmpty = StringUtils.isEmpty(deposit.getPaymentType());

			if (!(value1isEmpty && value2isEmpty)) {
				depositHolderForm.setPaymentType(modificationList.get(0).getPaymentType());
				depositHolderFormPrev.setPaymentType(deposit.getPaymentType());
				isChanged = 1;
			}

		}

		if (modificationList.get(0).getTenure() != deposit.getTenure()
				|| !(modificationList.get(0).getTenureType().equals(deposit.getTenureType()))) {

			depositHolderForm.setTenure(modificationList.get(0).getTenure());
			depositHolderFormPrev.setTenure(deposit.getTenure());

			depositHolderForm.setTenureType(modificationList.get(0).getTenureType());
			depositHolderFormPrev.setTenureType(deposit.getTenureType());
			isChanged = 1;

		}
		if (!(modificationList.get(0).getPaymentMode().equals(deposit.getPaymentMode()))) {

			value1isEmpty = StringUtils.isEmpty(modificationList.get(0).getPaymentMode());
			value2isEmpty = StringUtils.isEmpty(deposit.getPaymentMode());

			if (!(value1isEmpty && value2isEmpty)) {

				depositHolderForm.setPaymentMode(modificationList.get(0).getPaymentMode());
				depositHolderFormPrev.setPaymentMode(deposit.getPaymentMode());
				isChanged = 1;

			}
		}

		Boolean notNullValue = false;
		Boolean nullValue = false;

		notNullValue = modificationList.get(0).getInterestRate() != null && deposit.getInterestRate() != null;
		nullValue = modificationList.get(0).getInterestRate() == null && deposit.getInterestRate() == null;

		if (notNullValue) {
			if (Float.compare(modificationList.get(0).getInterestRate(), deposit.getInterestRate()) != 0) {

				depositHolderForm.setInterestRate(modificationList.get(0).getInterestRate());
				depositHolderFormPrev.setInterestRate(deposit.getInterestRate());
				isChanged = 1;
			}
		} else if (nullValue) {
		} else {
			depositHolderForm.setInterestRate(modificationList.get(0).getInterestRate());
			depositHolderFormPrev.setInterestRate(deposit.getInterestRate());
			isChanged = 1;
		}

		if (modificationList.get(0).getMaturityDate().compareTo(deposit.getMaturityDate()) != 0) {

			depositHolderForm.setMaturityDate(modificationList.get(0).getMaturityDate());
			depositHolderFormPrev.setMaturityDate(deposit.getMaturityDate());
			isChanged = 1;

		}

		if (deposit.getPayOffInterestType() != null && modificationList.get(0).getPayOffType() != null
				&& !(modificationList.get(0).getPayOffType().equals(deposit.getPayOffInterestType()))) {

			value1isEmpty = StringUtils.isEmpty(modificationList.get(0).getPayOffType());
			value2isEmpty = StringUtils.isEmpty(deposit.getPayOffInterestType());

			if (!(value1isEmpty && value2isEmpty)) {

				depositHolderForm.setInterstPayType(modificationList.get(0).getPayOffType());
				depositHolderFormPrev.setInterstPayType(deposit.getPayOffInterestType());
				isChanged = 1;
			}
		}

		model.put("isChanged", isChanged);
		/* /comparing single fields with the previous modification */

		/* comparing pay off changes..from deposit holder table.......... */

		List<DepositHolder> depositHolderList = new ArrayList<DepositHolder>();
		List<DepositHolder> depositHolderListPrev = new ArrayList<DepositHolder>();

		for (int i = 0; i < modificationList.size(); i++) {
			DepositModification depModification = modificationList.get(i);
			Long depositHolderId = depModification.getDepositHolderId();

			int changed = 0;
			DepositHolder depositHolder = new DepositHolder();
			DepositHolder depositHolderPrev = new DepositHolder();

			depositHolder.setId(depositHolderId);

			/* comparing with deposit holder table */

			DepositHolder depositHolderExisting = depositHolderDAO.findById(depositHolderId);
			if (depModification.getPayOffInterestType() != null && depositHolderExisting.getInterestType() != null
					&& !(depModification.getPayOffInterestType().equals(depositHolderExisting.getInterestType()))) {

				value1isEmpty = StringUtils.isEmpty(depModification.getPayOffInterestType());
				value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getInterestType());

				if (!(value1isEmpty && value2isEmpty)) {

					depositHolder.setInterestType(depModification.getPayOffInterestType());
					depositHolderPrev.setInterestType(depositHolderExisting.getInterestType());

					changed = 1;

				}
			}

			notNullValue = depModification.getPayOffInterestAmt() != null
					&& depositHolderExisting.getInterestAmt() != null;
			nullValue = depModification.getPayOffInterestAmt() == null
					&& depositHolderExisting.getInterestAmt() == null;

			if (notNullValue) {

				if (Float.compare(depModification.getPayOffInterestAmt(),
						depositHolderExisting.getInterestAmt()) != 0) {

					depositHolder.setInterestAmt(depModification.getPayOffInterestAmt());
					depositHolderPrev.setInterestAmt(depositHolderExisting.getInterestAmt());

					changed = 1;
				}
			} else if (nullValue) {
			} else {
				depositHolder.setInterestAmt(depModification.getPayOffInterestAmt());
				depositHolderPrev.setInterestAmt(depositHolderExisting.getInterestAmt());
				changed = 1;
			}

			notNullValue = depModification.getPayOffInterestPercent() != null
					&& depositHolderExisting.getInterestPercent() != null;
			nullValue = depModification.getPayOffInterestPercent() == null
					&& depositHolderExisting.getInterestPercent() == null;

			if (notNullValue) {

				if (Float.compare(depModification.getPayOffInterestPercent(),
						depositHolderExisting.getInterestPercent()) != 0) {

					depositHolder.setInterestPercent(depModification.getPayOffInterestPercent());
					depositHolderPrev.setInterestPercent(depositHolderExisting.getInterestPercent());

					changed = 1;
				}
			} else if (nullValue) {
			} else {

				depositHolder.setInterestPercent(depModification.getPayOffInterestPercent());
				depositHolderPrev.setInterestPercent(depositHolderExisting.getInterestPercent());

				changed = 1;
			}
			if (depositHolderExisting.getPayOffAccountType() != null
					&& !(depModification.getPayOffAccountType().equals(depositHolderExisting.getPayOffAccountType()))) {

				value1isEmpty = StringUtils.isEmpty(depModification.getPayOffAccountType());
				value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getPayOffAccountType());

				if (!(value1isEmpty && value2isEmpty)) {

					depositHolder.setPayOffAccountType(depModification.getPayOffAccountType());
					depositHolderPrev.setPayOffAccountType(depositHolderExisting.getPayOffAccountType());

					changed = 1;
				}
			}
			if (depModification.getPayOffTransferType() != null) {
				if (!(depModification.getPayOffTransferType().equals(depositHolderExisting.getTransferType()))) {

					value1isEmpty = StringUtils.isEmpty(depModification.getPayOffTransferType());
					value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getTransferType());

					if (!(value1isEmpty && value2isEmpty)) {

						depositHolder.setTransferType(depModification.getPayOffTransferType());
						depositHolderPrev.setTransferType(depositHolderExisting.getTransferType());

						changed = 1;
					}
				}
			}
			if (depModification.getPayOffNameOnBankAccount() != null) {
				if (!(depModification.getPayOffNameOnBankAccount()
						.equals(depositHolderExisting.getNameOnBankAccount()))) {

					value1isEmpty = StringUtils.isEmpty(depModification.getPayOffNameOnBankAccount());
					value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getNameOnBankAccount());

					if (!(value1isEmpty && value2isEmpty)) {

						depositHolder.setNameOnBankAccount(depModification.getPayOffNameOnBankAccount());
						depositHolderPrev.setNameOnBankAccount(depositHolderExisting.getNameOnBankAccount());

						changed = 1;
					}
				}
			}

			if (depModification.getPayOffAccountNumber() != null) {
				if (!(depModification.getPayOffAccountNumber().equals(depositHolderExisting.getAccountNumber()))) {

					value1isEmpty = StringUtils.isEmpty(depModification.getPayOffAccountNumber());
					value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getAccountNumber());

					if (!(value1isEmpty && value2isEmpty)) {

						depositHolder.setAccountNumber(depModification.getPayOffAccountNumber());
						depositHolderPrev.setAccountNumber(depositHolderExisting.getAccountNumber());

						changed = 1;
					}
				}
			}
			if (depModification.getPayOffBankName() != null) {
				if (!(depModification.getPayOffBankName().equals(depositHolderExisting.getBankName()))) {
					value1isEmpty = StringUtils.isEmpty(depModification.getPayOffBankName());
					value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getBankName());

					if (!(value1isEmpty && value2isEmpty)) {

						depositHolder.setBankName(depModification.getPayOffBankName());
						depositHolderPrev.setBankName(depositHolderExisting.getBankName());

						changed = 1;
					}
				}
			}
			if (depModification.getPayOffBankIFSCCode() != null) {
				if (!(depModification.getPayOffBankIFSCCode().equals(depositHolderExisting.getIfscCode()))) {
					value1isEmpty = StringUtils.isEmpty(depModification.getPayOffBankIFSCCode());
					value2isEmpty = StringUtils.isEmpty(depositHolderExisting.getIfscCode());

					if (!(value1isEmpty && value2isEmpty)) {

						depositHolder.setIfscCode(depModification.getPayOffBankIFSCCode());
						depositHolderPrev.setIfscCode(depositHolderExisting.getIfscCode());

						changed = 1;
					}
				}
			}
			if (changed == 1) {
				depositHolderList.add(depositHolder);
				depositHolderListPrev.add(depositHolderPrev);
			}
		}

		depositHolderForm.setDepositHolder(depositHolderList);
		depositHolderFormPrev.setDepositHolder(depositHolderListPrev);

		model.put("depositHolderForm", depositHolderForm);
		model.put("depositHolderFormPrev", depositHolderFormPrev);

		return new ModelAndView("compareWithModification", "model", model);
	}

	@RequestMapping(value = "/withdrawSearch", method = RequestMethod.GET)
	public ModelAndView withdrawListSearch(ModelMap model, @ModelAttribute WithdrawForm withdrawForm,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		model.put("withdrawForm", withdrawForm);
		return new ModelAndView("withdrawSearch", "model", model);

	}

	@RequestMapping(value = "/withdrawList", method = RequestMethod.POST)

	public ModelAndView withdrawList(ModelMap model, @ModelAttribute DepositForm depositForm) throws CustomException {

		Deposit deposit = fixedDepositDAO.getByAccountNumber(depositForm.getAccountNumber().trim());
		if (deposit == null) {
			model.put(Constants.ERROR, "Incorrect Account Number");
			return new ModelAndView("withdrawSearch", "model", model);
		}
		List<WithdrawDeposit> withdrawList = withdrawDepositDAO.withdrawDepositListByDepositId(deposit.getId());

		if (withdrawList.size() > 0) {

			model.put("customerName", depositForm.getCustomerName());
			model.put("withdrawList", withdrawList);

		} else {
			model.put(Constants.ERROR, Constants.WITHDRAWLISTERROR);
			return new ModelAndView("noDataFound", "model", model);

		}
		return new ModelAndView("withdrawList", "model", model);
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
		return new ModelAndView("redirect:bankFDSaved");
	}

	/* / Method to add the customer category / */
	@RequestMapping(value = "/addCustomerCategory")
	public ModelAndView addCustomerCategory(ModelMap model, @ModelAttribute CustomerCategoryForm customerCategoryForm)
			throws CustomException {
		List<CustomerCategory> allList = customerDAO.getAllCustomerCategory();
		model.put("allList", allList);
		model.put("addCustomerCategoryForm", customerCategoryForm);
		return new ModelAndView("addCustomerCategory", "model", model);

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
		return "redirect:bankFDSaved";
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/customerConfiguration")
	public ModelAndView customerConfiguration(ModelMap model, String citizen, String nriAccountType,
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
			return new ModelAndView("customerConfiguration", "model", model);
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
		return new ModelAndView("customerConfiguration", "model", model);
	}

//	@RequestMapping(value = "/confirmcustomerConfiguration", method = RequestMethod.POST)
//	public ModelAndView confirmcustomerConfiguration(ModelMap model, @ModelAttribute RatesForm ratesForm,
//			RedirectAttributes attributes) throws CustomException {
//
//		model.put("ratesForm", ratesForm);
//		return new ModelAndView("confirmcustomerConfiguration", "model", model);
//	}
//
//	@RequestMapping(value = "/savecustomerConfiguration", method = RequestMethod.POST)
//	public String savecustomerConfiguration(ModelMap model, @ModelAttribute RatesForm ratesForm,
//			RedirectAttributes attribute) throws CustomException {
//
//		Rates rates = new Rates();
//
//		rates.setType(ratesForm.getType());
//		rates.setTds(ratesForm.getTds());
//		rates.setAssignDate(DateService.getCurrentDateTime());
//		rates.setFdFixedPercent(ratesForm.getFdFixedPercent());
//		rates.setMinIntAmtForTDSDeduction(ratesForm.getMinIntAmtForTDSDeduction());
//		rates.setCitizen(ratesForm.getCitizen());
//		rates.setNriAccountType(ratesForm.getNriAccountType());
//		ratesDAO.createRate(rates);
//		attribute = updateTransaction("Saved Successfully", attribute);
//		return "redirect:bankFDSaved";
//	}

	@RequestMapping(value = "/editCustomerConfiguration")
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
		return new ModelAndView("editCustomerConfiguration", "model", model);
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

		return "redirect:bankFDSaved";
	}

	@RequestMapping(value = "/schedulerConfiguration")
	public ModelAndView schedulerConfiguration(ModelMap model, @RequestParam String jobName) throws CustomException {

		scheduleTaskForm = new ScheduleTaskForm();

		if (jobName == null || jobName.isEmpty()) {
			jobName = Constants.INTERESTCALCULATION;
			;
		} else {
			jobName = jobName.split(",")[0];
		}

		scheduleTaskForm.setJobName(jobName);

		JobScheduler job = jobSchedulerDAO.findByJobName(jobName);
		List<JobScheduler> jobList = jobSchedulerDAO.findAllJobs();

		model.put("jobList", jobList);

		if (job != null) {
			scheduleTaskForm.setStartdate(job.getStartDate());
			scheduleTaskForm.setEnddate(job.getEndDate());

			String cronMeaning = job.getCronMeaning();
			// Duration:+Monthly;startTime:10;month:12;day:10;week:sun
			if (cronMeaning != null) {
				String cronMeanings[] = cronMeaning.split(";");
				scheduleTaskForm.setStarttime(cronMeanings[1].split(":")[1]);
				if (cronMeanings[3].split(":")[1] == null && cronMeanings[3].split(":")[1] == ""
						&& cronMeanings[3].split(":")[1].isEmpty()) {
				} else {
					scheduleTaskForm.setDayOfMonth(Integer.valueOf(cronMeanings[3].split(":")[1]));

				}
				scheduleTaskForm.setMonthOfYear(cronMeanings[2].split(":")[1]);
				scheduleTaskForm.setSchedulerDuration(cronMeanings[0].split(":")[1]);
				scheduleTaskForm.setWeek(cronMeanings[0].split(":")[1]);
				// Duration:;startTime:7;month:1;day:null;week:SUN
			}

		}

		model.put("scheduleTaskForm", scheduleTaskForm);
		return new ModelAndView("schedulerConfiguration", "model", model);
	}

	@RequestMapping(value = "/saveScheduledTask", method = RequestMethod.POST)
	public ModelAndView saveScheduledTask(ModelMap model, @ModelAttribute ScheduleTaskForm scheduleTaskForm,
			RedirectAttributes attribute) throws UnsupportedEncodingException, IOException, CustomException {

		String[] cronExpression = { "0", "0", "*", "*", "*", "*", "*" };

		cronExpression[2] = scheduleTaskForm.getStarttime();

		if (scheduleTaskForm.getSchedulerDuration().equals("Daily")) {
			cronExpression[3] = "?";

		}

		else if (scheduleTaskForm.getSchedulerDuration().equals("Weekly")) {
			cronExpression[3] = "?";
			cronExpression[5] = "SUN";

		} else if (scheduleTaskForm.getSchedulerDuration().equals("Fortnightly")) {

			cronExpression[3] = "1,15";
			cronExpression[5] = "?";

			// 0 0 13 1/15 ?
		} else if (scheduleTaskForm.getSchedulerDuration().equals("Monthly")) {
			cronExpression[3] = scheduleTaskForm.getDayOfMonth().toString();
			cronExpression[4] = "1/1";
			cronExpression[5] = "?";

			// 0 0 12 23 1/1 ? *
		} else if (scheduleTaskForm.getSchedulerDuration().equals("Quarterly")) {
			cronExpression[3] = scheduleTaskForm.getDayOfMonth().toString();
			cronExpression[4] = "JAN,APR,JUL,OCT";

			// 0 0 7 23 JAN,APR,JUL,OCT ? *
		} else if (scheduleTaskForm.getSchedulerDuration().equals("Annually")) {

			cronExpression[3] = scheduleTaskForm.getDayOfMonth().toString();
			cronExpression[4] = scheduleTaskForm.getMonthOfYear();
			cronExpression[5] = "?";

			// 0 0 12 1 4 ? *
		}

		String cronExp = cronExpression[0];
		for (int i = 1; i < cronExpression.length; i++) {

			cronExp = cronExp + " " + cronExpression[i];

		}

		JobScheduler job = jobSchedulerDAO.findByJobName(scheduleTaskForm.getJobName());

		if (scheduleTaskForm.getDayOfMonth() == null)
			scheduleTaskForm.setDayOfMonth(0);

		if (scheduleTaskForm.getStarttime() == null)
			scheduleTaskForm.setStarttime("");

		if (scheduleTaskForm.getMonthOfYear() == null)
			scheduleTaskForm.setMonthOfYear("");

		if (scheduleTaskForm.getWeek() == null)
			scheduleTaskForm.setWeek("");

		String comment = "Duration:" + scheduleTaskForm.getSchedulerDuration() + ";startTime:"
				+ scheduleTaskForm.getStarttime() + ";month:" + scheduleTaskForm.getMonthOfYear() + ";day:"
				+ scheduleTaskForm.getDayOfMonth() + ";week:" + scheduleTaskForm.getWeek();

		boolean save = false;
		if (job == null) {
			save = true;
			job = new JobScheduler();
		}

		SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd");

		Date endDate = scheduleTaskForm.getEnddate();
		Date startDate = scheduleTaskForm.getStartdate();

		if (scheduleTaskForm.getStartdate() != null) {
			String startDateStr = smt.format(scheduleTaskForm.getStartdate());

			try {
				startDate = smt.parse(startDateStr);
			} catch (ParseException e) {
			}
			job.setStartDate(startDate);
		}

		if (scheduleTaskForm.getEnddate() != null) {
			String endDateStr = smt.format(scheduleTaskForm.getEnddate());

			try {
				endDate = smt.parse(endDateStr);
			} catch (ParseException e) {
			}
			job.setEndDate(endDate);
		}

		job.setCronExpression(cronExp);
		job.setCronMeaning(comment);

		if (save == true) {
			job.setJobName(scheduleTaskForm.getJobName());
			jobSchedulerDAO.saveJob(job);
		} else {
			jobSchedulerDAO.updateJob(job);
		}

		attribute = updateTransaction("Saved Successfully", attribute);
		return new ModelAndView("redirect:bankFDSaved");

	}

	@RequestMapping(value = "/searchCustomerTdsReport", method = RequestMethod.GET)
	public ModelAndView searchCustomerTdsReport(ModelMap model, String depositType) throws CustomException {
		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("searchCustomerTdsReport", "model", model);

	}

	@RequestMapping(value = "/searchCustomerTdsReportList", method = RequestMethod.POST)
	public ModelAndView searchCustomerTdsReportList(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String depositType) throws CustomException {
		String cusId = fixedDepositForm.getCustomerID();
		String cusName = fixedDepositForm.getCustomerName();
		String cusNum = fixedDepositForm.getContactNum();
		String cusEmail = fixedDepositForm.getEmail();

		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);
		model.put("customerList", customerList);
		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("searchCustomerTdsReport", "model", model);

	}

	@RequestMapping(value = "/customerTdsReport")
	public ModelAndView customerTdsReport(ModelMap model, Long customerId) throws CustomException {

		ModelAndView mav = new ModelAndView();
		Double totalTDS = 0d;
		List<DepositWiseCustomerTDS> customerTdsList = tdsDAO.getCustomerTDSReportByDepositId(customerId);
		Customer cust = customerDAO.getById(customerId);

		if (customerTdsList != null && customerTdsList.size() > 0) {

			for (int i = 0; i < customerTdsList.size(); i++) {

				totalTDS = totalTDS + customerTdsList.get(i).getContributedTDSAmount();
			}

			model.put("cust", cust.getCustomerName());
			model.put("totalTDS", fdService.round(totalTDS, 2));
			model.put("customerTdsList", customerTdsList);
			mav = new ModelAndView("customerTdsReportList", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;

	}

	@RequestMapping(value = "/customerTdsReport", method = RequestMethod.GET)
	public ModelAndView customerTdsReport(ModelMap model) throws CustomException {
		List<Object[]> customerTds = tdsDAO.getAllCustomerTds();
		ModelAndView mav = new ModelAndView();
		if (customerTds != null && customerTds.size() > 0) {

			List<TDSForm> tdsLists = new ArrayList<TDSForm>();

			for (int i = 0; i < customerTds.size(); i++) {
				TDSForm tdsForm = new TDSForm();

				tdsForm.setTdsAmount((Double) customerTds.get(i)[0]);
				tdsForm.setCalculatedTDSAmount((Double) customerTds.get(i)[1]);
				tdsForm.setTdsCalcDate((Date) customerTds.get(i)[2]);
				tdsForm.setFinancialYear((String) customerTds.get(i)[3]);
				tdsForm.setCustomerId(((BigInteger) customerTds.get(i)[4]).longValue());
				tdsForm.setCustomerName((String) customerTds.get(i)[5]);

				tdsLists.add(tdsForm);
			}
			model.put("tdsLists", tdsLists);
			mav = new ModelAndView("allCustomerTdsList", "model", model);
		}

		else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/showMonthEndProcess", method = RequestMethod.GET)
	public ModelAndView showMonthEndProcess(ModelMap model, @ModelAttribute ReportForm reportForm)
			throws CustomException {
		model.put("reportForm", reportForm);

		return new ModelAndView("monthEndProcess", "model", model);
	}

	@RequestMapping(value = "/monthEndProcess", method = RequestMethod.POST)
	public ModelAndView monthEndProcess(ModelMap model, @ModelAttribute ReportForm reportForm) throws CustomException {
		// Date tillDate = reportForm.getToDate();

		// Sequence:
		// -----------------
		// Calculate Interest
		// PayOff
		// Maturity
		// Compounding
		// -----------------
		notificationsScheduler.calculateInterest(); // for interest and tds
													// deduction
		// notificationsScheduler.deductTDS();

		notificationsScheduler.calculatePayOff();

		notificationsScheduler.calculateModificationPenalty();

		notificationScheduler.calculatePenaltyForMissedPaymentForRecurringDeposit();

		notificationsScheduler.compoundInterest();

		notificationsScheduler.transferMoneyOnMaturity();

		notificationsScheduler.autoDeduction();

		notificationsScheduler.deductAnnuityEMI();


		notificationsScheduler.paymentRemindMail();
		
		ledgerService.postInLedger();

		// notificationsScheduler.deductTDS();

		// notificationsScheduler.createAutoDeposit();

		model.put("succMsg", "Successfully processed.");

		return new ModelAndView("monthEndProcess", "model", model);
	}

	/*
	 * Method : addRates created by : Ravi Pratap Singh perpous : Method to add
	 * rates for perticular customer
	 */
	/*
	 * Method : addRates created by : Ravi Pratap Singh perpous : Method to add
	 * rates for perticular customer
	 */
//	@RequestMapping(value = "/addRates")
//	public ModelAndView addRate(ModelMap model, @ModelAttribute AddRateForm addRateForm, String nriAccType)
//			throws CustomException {
//		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
//		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
//
//		if (setCategory.size() > 0) {
//
//			model.put("setCategory", setCategory);
//			model.put("addRateForm", addRateForm);
//			model.put("list", list);
//
//		}
//		List<String> depositClassificationList = new ArrayList<>();
//		depositClassificationList.add(Constants.fixedDeposit);
//		depositClassificationList.add(Constants.recurringDeposit);
//		depositClassificationList.add(Constants.taxSavingDeposit);
//		depositClassificationList.add(Constants.annuityDeposit);
//		model.put("depositClassificationList", depositClassificationList);
//
//		BankConfiguration bankConfiguration = ratesDAO.findAll();
//		if (bankConfiguration != null) {
//			model.put("bankConfiguration", bankConfiguration);
//		}
//
//		model.put("nriAccType", nriAccType);
//		model.put("list", list);
//		model.put("setCategory", setCategory);
//		model.put("addRateForm", addRateForm);
//
//		return new ModelAndView("addRates", "model", model);
//
//	}
//
//	@RequestMapping(value = "/getRateDurationsByDepositClaasification")
//	public ModelAndView getRateDurationsByDepositClaasification(ModelMap model, @ModelAttribute AddRateForm addRateForm,
//			String depositClassification, String category, String nriAccType) throws CustomException {
//
//		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
//
//		List<String> depositClassificationList = new ArrayList<>();
//		depositClassificationList.add(Constants.fixedDeposit);
//		depositClassificationList.add(Constants.recurringDeposit);
//		depositClassificationList.add(Constants.taxSavingDeposit);
//		depositClassificationList.add(Constants.annuityDeposit);
//		model.put("depositClassificationList", depositClassificationList);
//
//		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
//
//		if (setCategory.size() > 0) {
//
//			model.put("setCategory", setCategory);
//			model.put("addRateForm", addRateForm);
//			model.put("list", list);
//
//		}
//		if (depositClassification.contains(","))
//			depositClassification = depositClassification.substring(0, depositClassification.indexOf(",", 0));
//		// String category = null;
//
//		if (category.contains(","))
//			category = category.substring(0, category.indexOf(",", 0));
//
//		List<RatePeriods> ratesPeriod = ratesDAO.getRateDurations(depositClassification, addRateForm.getCitizen(),
//				addRateForm.getNriAccountType(), category);
//		if (ratesPeriod != null) {
//			model.put("ratesPeriod", ratesPeriod);
//		}
//		if (ratesPeriod == null) {
//			model.put(Constants.ERROR, Constants.durationError);
//		}
//		model.put("ratesPeriod", ratesPeriod);
//		model.put("selectedDepositClassification", depositClassification);
//		model.put("category", category);
//		model.put("nriAccType", nriAccType);
//
//		return new ModelAndView("addRates", "model", model);
//	}

	@RequestMapping(value = "/getTenureForReverseEMI", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Double tenureCalculation(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			Double fdAmount, Double emiAmt) {

		double emiFrequency = Math.floor(fdAmount / emiAmt);
		return emiFrequency;

	}

//	@SuppressWarnings("unused")
//	@RequestMapping(value = "/addRatePost", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
//	public @ResponseBody ModelAndView addRatePost(ModelMap model, @ModelAttribute AddRateForm addRateForm,
//			RedirectAttributes attribute, @RequestParam("rateArrList") String[] rateArrList,
//			@RequestParam("dataString") String[] dataString, String citizen, String nriAccountType)
//			throws UnsupportedEncodingException, CustomException {
//		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
//
//		for (int i = 0; i < rateArrList.length; i++) {
//			DepositRates depositRates = new DepositRates();
//			String arrString = rateArrList[i].replace("rateList[", "");
//
//			Integer pipeIndex = arrString.indexOf('|');
//			String[] categorySplit = dataString[0].split("=");
//
//			String interstRate = arrString.substring(pipeIndex + 1, arrString.indexOf('#'));
//			Float rate = Float.valueOf(interstRate);
//			Long Id = Long.parseLong(arrString.substring(0, arrString.indexOf(']')));
//			List<RatePeriods> getAllFromAndToDaysRates = ratesDAO.getAllRatesPeriodFromId(Id);
//
//			Date effectiveDate = addRateForm.getEffectiveDate();
//			if (effectiveDate != null) {
//
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				String effectiveDateStr = sdf.format(effectiveDate);
//				try {
//					effectiveDate = sdf.parse(effectiveDateStr);
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//
//			}
//
//			String description = getAllFromAndToDaysRates.get(0).getFromDay() + "  day to  "
//					+ getAllFromAndToDaysRates.get(0).getToDay() + "  days";
//			DepositRates depositRatesDescription = ratesDAO.getRateDescriptionById(Id, addRateForm.getCurrency(),
//					categorySplit[1], effectiveDate, description, addRateForm.getDepositClassification(),
//					addRateForm.getNriAccountType());
//			// Float descriptionRate = depositRatesDescription.getInterestRate()
//			// == null ? 0f : depositRatesDescription.getInterestRate();
//
//			depositRates.setCalMaturityPeriodFromInDays(getAllFromAndToDaysRates.get(0).getFromDay());
//			depositRates.setCalMaturityPeriodToInDays(getAllFromAndToDaysRates.get(0).getToDay());
//			depositRates.setRatePeriodsId(Id);
//			depositRates.setInterestRate(rate);
//			depositRates.setDescription(description);
//
//			depositRates.setCategory(categorySplit[1]);
//			depositRates.setEffectiveDate(addRateForm.getEffectiveDate());
//			depositRates.setCurrency(addRateForm.getCurrency());
//			depositRates.setMaturityPeriodSign("=");
//			depositRates.setTransactionId(fdService.generateRandomString());
//			depositRates.setAmountSlabFrom(addRateForm.getAmountSlabFrom());
//			;
//			depositRates.setAmountSlabTo(addRateForm.getAmountSlabTo());
//			depositRates.setDepositClassification(addRateForm.getDepositClassification());
//
//			if (addRateForm.getCitizen().equals(Constants.NRI)) {
//				depositRates.setNriAccountType(addRateForm.getNriAccountType());
//			}
//			depositRates.setCitizen(addRateForm.getCitizen());
//			if (depositRatesDescription == null) {
//				depositRateDAO.createUser(depositRates);
//			}
//		}
//		attribute = updateTransaction("Saved Successfully", attribute);
//		model.put("list", list);
//		return new ModelAndView("redirect:bankFDSaved");
//
//	}

	@RequestMapping(value = "/getEMIAmount", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Double getEMIAmount(String tenureType, Integer tenure, Double depositAmount,
			Integer gestationPeriod, String beneficiaryPayoffType, String userName, Integer day, String currency,
			String citizen, String nriAccountType, String productId) throws CustomException {
		System.out.println("In getEMIAmount in Bank Employee...");
		Date maturityDate = null;
		int days = 0;
		// String tenureType = "Month";
		Customer customerDetails = customerDAO.getByUserName(userName);
		System.out.println(userName);
		if (day != null) {
			days = day;
		}

		Date currentDate = DateService.getCurrentDate();
		if (tenureType.equalsIgnoreCase("Month")) {
			maturityDate = DateService.generateMonthsDate(currentDate, tenure);
		} else if (tenureType.equalsIgnoreCase("Year")) {

			if (days == 0) {

				maturityDate = DateService.generateYearsDate(currentDate, tenure);

			} else {
				maturityDate = DateService.generateYearsDate(currentDate, tenure);
				maturityDate = DateService.generateDaysDate(maturityDate, days);
			}

		} else if (tenureType.equalsIgnoreCase("Day")) {
			maturityDate = DateService.generateDaysDate(currentDate, tenure);
		}

		// Customer customerDetails = getUserDetails();
		Integer daysDiff = DateService.getDaysBetweenTwoDates(currentDate, maturityDate) + 1;

		Float rateOfInterest = calculationService.getDepositInterestRate(daysDiff, customerDetails.getCategory(),
				currency, depositAmount, Constants.fixedDeposit, citizen, nriAccountType);

		String interestCalculationBasis = Constants.MONTHLY;
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(Long.parseLong(productId));
		if (productConfiguration == null)
			productConfiguration = productConfigurationDAO.findByProductCode(productId);
		if (productConfiguration != null)
			interestCalculationBasis = productConfiguration.getInterestCalculationBasis();

		Double emiAmount = fdService.calculateEMI(currentDate, maturityDate, depositAmount, gestationPeriod,
				rateOfInterest, beneficiaryPayoffType, interestCalculationBasis);

		return emiAmount;
	}

	/*
	 * created by: Ravi Singh To get the completete information of deposit,
	 * depositholder , nominee details , Interest, payoff
	 */

	@RequestMapping(value = "/depositSummary", method = RequestMethod.GET)
	public ModelAndView depositSummary(ModelMap model, @ModelAttribute DepositForm depositForm, String account)
			throws CustomException {

		model.put("depositForm", depositForm);
		model.put("account", account);
		return new ModelAndView("depositSummary", "model", model);

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/showDepositDetails", method = RequestMethod.POST)
	public ModelAndView showDepositDetails(ModelMap model, @ModelAttribute DepositForm depositForm)
			throws CustomException

	{

		/* Getting user account number number given by bank employee */
		String accountNumber = depositForm.getAccountNumber();
		List<DepositForm> depositList = paymentDAO.paymentAccountNumber(accountNumber.trim());
		if (depositList.size() > 0) {
			ModelAndView mav = new ModelAndView();

			/* Initialized Local variable with there default value */
			String depositHolderNomineeName = null;
			String accountNumberForPay = null;
			String modeOfPay = null;
			Double lastAmtPaid = 0.0;
			Long id = 0l;
			String accNumber = null;
			Double depositAmount = 0.0;
			String holderStatus = null;
			Integer gestationPeriod = 0;
			Float contribution = 0f;
			Double currentBal = 0.0;
			Double expectedMaturity = 0.0;
			String depositCategory = null;
			Float interestRate = 0f;
			Double totInterestAmount = 0.0;
			Date maturityDate = null;
			Date createdDate = null;
			Date dueDate = null;
			Date gestationEndDate = null;

			/* To get the Depossit Id */
			Deposit deposit = fixedDepositDAO.getByAccountNumber(accountNumber);
			Long depositId = deposit.getId();

			/* To get the Customer Name and Id */
			List<DepositHolderForm> depositHolderList = depositHolderDAO.getDepositHoldersName(depositId);
			String userName = depositHolderList.get(0).getCustomerName();
			Long customerId = depositHolderList.get(0).getCustomerId();

			Date lastPayoffDistributionDate = null;
			Double lastPayOffActualAmount = 0.0;
			String payOffType = null;
			String payOffAccountNumber = null;
			Date payOffDueDate = null;
			Double lastPayOffInterestPercent = 0.0;
			Long depositHolderId = 0l;
			List<DepositForm> nomineeList = new ArrayList<DepositForm>();
			List<DepositForm> totalInterestList = new ArrayList<DepositForm>();
			List<HolderForm> depositHolderObjList = depositService
					.getAllDepositByCustomerIdDepositIdAndAccountNo(customerId, depositId, accountNumber);

			DepositHolder depositHolder = depositHolderDAO.getDepositHolder(depositId, customerId);
			Payment lastPayment = paymentDAO.getLastPaymentByDepositId(depositId);

			/* interest details */
			List<Interest> interestList = interestDAO.getByDepositId(depositId);
			Double interestAccrued = interestDAO.getTotalInterestAccumulated(depositId, null, null);

			if (interestList.size() > 0) {

				for (int i = 0; i < interestList.size(); i++)

				{
					totInterestAmount = totInterestAmount + interestList.get(i).getInterestAmount();
					model.put("totInterestAmount", totInterestAmount);
				}

			}

			else {

				totInterestAmount = 0.0;
				model.put("totInterestAmount", totInterestAmount);
			}
			/* Deposit Details */
			if (depositHolderObjList != null) {

				id = depositHolderObjList.get(0).getDeposit().getId();
				accNumber = depositHolderObjList.get(0).getDeposit().getAccountNumber();
				depositAmount = depositHolderObjList.get(0).getDeposit().getDepositAmount();

				holderStatus = depositHolderObjList.get(0).getDepositHolder().getDepositHolderStatus();
				if (holderStatus.equals(Constants.PRIMARY)) {
					contribution = depositHolderObjList.get(0).getDepositHolder().getContribution();
				}

				currentBal = depositHolderObjList.get(0).getDeposit().getCurrentBalance();
				expectedMaturity = depositHolderObjList.get(0).getDeposit().getNewMaturityAmount();
				depositCategory = depositHolderObjList.get(0).getDeposit().getDepositCategory();
				if (depositCategory != null) {
					if (depositCategory.equals("AUTO") || depositCategory.equals("REVERSE-EMI")) {
						depositCategory = depositHolderObjList.get(0).getDeposit().getDepositCategory();
					}

				} else {
					depositCategory = "REGULAR";

				}
				interestRate = depositHolderObjList.get(0).getDeposit().getInterestRate();
				payOffDueDate = depositHolderObjList.get(0).getDeposit().getPayOffDueDate();
				maturityDate = depositHolderObjList.get(0).getDeposit().getMaturityDate();
				createdDate = depositHolderObjList.get(0).getDeposit().getCreatedDate();
				dueDate = depositHolderObjList.get(0).getDeposit().getDueDate();
				gestationPeriod = (depositHolderObjList.get(0).getDeposit().getGestationPeriod() != null)
						? depositHolderObjList.get(0).getDeposit().getGestationPeriod()
						: 0;
				gestationEndDate = DateService.generateYearsDate(createdDate, gestationPeriod);

				model.put("dueDate", dueDate);
				model.put("maturityDate", maturityDate);
				model.put("createdDate", createdDate);
				model.put("id", id);
				model.put("accNumber", accNumber);
				model.put("depositAmount", depositAmount);
				model.put("holderStatus", holderStatus);
				model.put("contribution", contribution);
				model.put("currentBal", currentBal);
				model.put("expectedMaturity", expectedMaturity);
				model.put("depositCategory", depositCategory);
				model.put("nextPayOffDate", payOffDueDate);
				model.put("interestRate", interestRate);
				model.put("gestationPeriod", gestationPeriod);
				model.put("gestationEndDate", gestationEndDate);
			}

			/* Payment Details */
			if (lastPayment != null) {
				lastAmtPaid = lastPayment.getAmountPaid();
				accountNumberForPay = lastPayment.getAccountNumber();
				//modeOfPay = lastPayment.getModeOfPaymentId();
				model.put("createdBy", lastPayment.getCreatedBy());
				model.put("accountNumberForPay", accountNumberForPay);
				model.put("lastAmtPaid", lastAmtPaid);
				model.put("lastPaymentDate", lastPayment.getPaymentDate());

				model.put("modeOfPay", modeOfPay);
			} else {
				// lastAmtPaid = Double.parseDouble("Not Paid");
				accountNumberForPay = null;
			}

			/* Nominee, Deposit Holder and Interest Details */
			for (int i = 0; i < depositList.size(); i++) {
				DepositForm depositFormForNominee = new DepositForm();
				DepositForm depositFormForInterest = new DepositForm();

				depositHolderId = Long.parseLong(depositList.get(i).getDepositHolderId().toString());
				DepositHolderNominees nomineeDetails = nomineeDAO.accountHoderNominee(depositHolderId);
				DepositSummaryHolderWise totalIntrestFromHolderWiseConsolited = depositHolderWiseConsolidatedInterestDAO
						.getTotalInterestByDepositHoldeId(depositHolderId);

				if (totalIntrestFromHolderWiseConsolited != null) {
					depositFormForInterest
							.setTotalInterest(totalIntrestFromHolderWiseConsolited.getTotalInterestAccumulated());
					depositFormForInterest.setDepositId(totalIntrestFromHolderWiseConsolited.getDepositId());
					depositFormForInterest
							.setDepositHolderId(totalIntrestFromHolderWiseConsolited.getDepositHolderId());
					Customer customer = customerDAO.getById(depositList.get(i).getCustomerId());

					depositFormForInterest.setCustomerName(customer.getCustomerName());
				}

				if (nomineeDetails != null) {
					depositFormForNominee.setNomineeName(nomineeDetails.getNomineeName());
					depositFormForNominee.setNomineeAge(nomineeDetails.getNomineeAge());
					depositFormForNominee.setNomineeRelationship(nomineeDetails.getNomineeRelationship());
					depositFormForNominee.setNomineeAddress(nomineeDetails.getNomineeAddress());
					depositFormForNominee.setNomineePan(nomineeDetails.getNomineePan());
					depositFormForNominee.setNomineeAadhar(nomineeDetails.getNomineeAadhar());
					depositFormForNominee.setDepositHolderId(nomineeDetails.getDepositHolderId());
					depositFormForNominee.setDepositId(nomineeDetails.getDepositId());
					nomineeList.add(depositFormForNominee);
				} else if (depositList.size() > 0) {
					String depositCat = (String) (depositList.get(0).getDepositCategory() != null
							? depositList.get(i).getDepositCategory().equals(Constants.AUTO)
							: "noCAtegory");
					if (depositCat.equalsIgnoreCase("noCAtegory")) {
						model.put("nomineeError", "No Nominee Required for This Deposit");
					}

				}

				else {
					model.put("nomineeError", "No Nominee for the Deposit");
				}
				totalInterestList.add(depositFormForInterest);

			}

			List<PayoffDetails> payOffDistributionDetails = payOffDAO.payOffDetailsByDepositHolderId(depositHolderId);
			if (payOffDistributionDetails != null && payOffDistributionDetails.size() > 0) {
				model.put("payOffDistributionDetails", payOffDistributionDetails);

			}

			/*
			 * List<DepositForm> payOffAllDetailsList = new ArrayList<DepositForm>();
			 * for(int i=0;i<depositList.size();i++){ DepositForm depositFormPayOff = new
			 * DepositForm(); depositHolderId = depositList.get(i).getDepositHolderId();
			 * List<PayOffDistribution> payOffDistributionDetails =
			 * payOffDAO.payOffDistributionByDepositHolderId(depositHolderId);
			 * depositFormPayOff.setDepositHolderId(payOffDistributionDetails.
			 * get(i).getDepositHolderId());
			 * depositFormPayOff.setPayOffActualAmount(payOffDistributionDetails
			 * .get(i).getPayOffActualAmount());
			 * depositFormPayOff.setPayOffDistributionDate(
			 * payOffDistributionDetails.get(i).getPayOffDistributionDate());
			 * depositFormPayOff.setPayOffInterestPercent(
			 * payOffDistributionDetails.get(i).getPayOffInterestPercent());
			 * depositFormPayOff.setPayOffInterestType(payOffDistributionDetails
			 * .get(i).getPayOffInterestType());
			 * depositFormPayOff.setPayOffType(payOffDistributionDetails.get(i).
			 * getPayOffType()); payOffAllDetailsList.add(depositFormPayOff); }
			 */
			/* PayOff Details */

			// COM
			/*
			 * List<PayoffDetails> payOff =
			 * payOffDAO.payOffDistributionByDepositId(depositId); Double
			 * sumOffPayoffactualamount =
			 * payOffDAO.getSumOffPayoffactualamountByDepositId(depositId); if
			 * (sumOffPayoffactualamount != null) {
			 * 
			 * model.put("sumOffPayoffactualamount", sumOffPayoffactualamount); } if
			 * (payOff.size() > 0 && payOff != null) { lastPayoffDistributionDate =
			 * payOff.get(0).getPayOffDistributionDate(); lastPayOffActualAmount =
			 * payOff.get(0).getPayOffActualAmount(); payOffType =
			 * payOff.get(0).getPayOffType(); payOffAccountNumber =
			 * payOff.get(0).getPayOffAccountNumber(); lastPayOffInterestPercent =
			 * payOff.get(0).getPayOffInterestPercent();
			 * model.put("lastPayoffDistributionDate", lastPayoffDistributionDate);
			 * model.put("lastPayOffActualAmount", lastPayOffActualAmount);
			 * model.put("lastpayOffType", payOffType);
			 * model.put("lastPayOffInterestPercent", lastPayOffInterestPercent);
			 * model.put("payOff", payOff);
			 * 
			 * }
			 * 
			 * else {
			 * 
			 * model.put(Constants.payOferror, Constants.payOffError); }
			 * 
			 */
			/*
			 * To get the reverese Emi details from deposit and depositholder table join
			 * with depositid and find with customerid
			 */
			List<DepositHolderForm> reverseDepositDetails = depositHolderDAO.getReverseEmiDepositByDepositId(depositId);
			if (reverseDepositDetails.size() > 0 && reverseDepositDetails != null) {
				model.put("reverseDepositDetails", reverseDepositDetails);

			} else {
				model.put(Constants.reverseEmiError, Constants.reverseEmiErrorMsg);
			}

			/*
			 * To get the reverese Emi benificiary details from benificiarydetails table
			 * join with depositid and find with customerid
			 */
			List<DepositHolderForm> benificiaryDetails = benificiaryDetailsDAO
					.getReverseEmiBenificiaryDetailsByDepositId(depositId);
			if (benificiaryDetails.size() > 0 && benificiaryDetails != null) {
				model.put("benificiaryDetails", benificiaryDetails);

			} else {
				model.put(Constants.benificiaryError, Constants.benificiaryErrorMsg);
			}
			model.put("totalInterestList", totalInterestList);

			model.put("interestAccrued", interestAccrued);

			model.put("nomineeList", nomineeList);

			if (depositList.size() > 0) {
				model.put("depositList", depositList);
			}

			mav = new ModelAndView("showDepositDetailsForBankEmp", "model", model);
			return mav;
		} else {
			model.put("error", "Account Number is not Correct");
			return new ModelAndView("depositSummary", "model", model);

		}

	}


	@RequestMapping(value = "/logindate", method = RequestMethod.GET)
	ModelAndView logindate(ModelMap model, @ModelAttribute LoginForm loginForm) throws CustomException {
		Date loginDateDetails = logDetailsDAO.findLoginDate();
		if (loginDateDetails != null) {
			loginForm.setLoginDate(loginDateDetails);
		}
		model.put("loginForm", loginForm);
		return new ModelAndView("logindate", "model", model);

	}

//	@RequestMapping(value = "/bulkDeposit", method = RequestMethod.GET)
//	ModelAndView bulkDeposit(ModelMap model) throws CustomException {
//		File folder = new File(context.getRealPath("/")+"/DepositExcel");
//		File[] listOfFiles = folder.listFiles();
//		Integer length=listOfFiles.length;
//		String[] files=new String[length];
//		for (int i = 0; i < length; i++) {
//			  if (listOfFiles[i].isFile()) {
//			    files[i]=listOfFiles[i].getName();
//			  }
//			}
//		
//		
//		
//		model.put("files", files);
//		return new ModelAndView("bulkDeposit", "model", model);
//
//	}

	@RequestMapping(value = "/loginDateSavedBank", method = RequestMethod.GET)
	public ModelAndView loginDateSaved(ModelMap model) throws CustomException {

		return new ModelAndView("loginDateSavedBank", "model", model);

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

		return new ModelAndView("redirect:loginDateSavedBank");

	}

	@RequestMapping(value = "/loginDateForJsp", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Date loginDateForJsp() throws CustomException {

		return DateService.loginDate;
	}

	// To search the bank payment details by account number
	@RequestMapping(value = "/searchBankPayment", method = RequestMethod.GET)
	public ModelAndView searchBankPayment(ModelMap model, @ModelAttribute BankPaymentForm bankPaymentForm)
			throws CustomException {

		model.put("bankPaymentForm", bankPaymentForm);
		return new ModelAndView("searchBankPayment", "model", model);

	}

	// To get the Bank Payment Details
	@RequestMapping(value = "/bankpaymentDetails", method = RequestMethod.POST)
	ModelAndView bankpaymentDetails(ModelMap model, @ModelAttribute BankPaymentForm bankPaymentForm)
			throws CustomException {
		ModelAndView mav = new ModelAndView();
		// To get the account number to check whether user input account number
		// is correct or not
		Deposit depositAccNo = fixedDepositDAO.getByAccountNumber(bankPaymentForm.getAccountNumber());
		if (bankPaymentForm.getAccountNumber().equals(null) || bankPaymentForm.getAccountNumber().equals("")
				|| depositAccNo == null) {
			model.put(Constants.ERROR, Constants.ACCOUNTERROR);
			return new ModelAndView("searchBankPayment", "model", model);
		} else {
			// Getting all payment details by bank either to nominee or holder
			// in bankPaymentDetails list
			List<BankPaymentForm> bankPaymentDetails = bankPaymentDAO
					.getBankPaymentDetailsByAccountNo(bankPaymentForm.getAccountNumber());// Get
																							// all
																							// payment
																							// details
																							// in
																							// list
			if (bankPaymentDetails.size() > 0) {
				model.put(Constants.bankPaymentDetails, bankPaymentDetails);
				mav = new ModelAndView("bankPaymentDetails", "model", model);
			} else {
				mav = new ModelAndView("noDataFound", "model", model);
			}
		}

		return mav;
	}

	@RequestMapping(value = "/duration")
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

		return new ModelAndView("duration", "model", model);

	}

	/*
	 * @RequestMapping(value = "/getAllRatesPeriod") public ModelAndView
	 * getAllRatesPeriod(ModelMap model, @ModelAttribute FundTransferForm
	 * fundTransferForm) { ModelAndView mav = new ModelAndView();
	 * 
	 * RatePeriods ratesPeriod = ratesDAO.getAllRatesPeriod();
	 * 
	 * if (ratesPeriod != null) {
	 * 
	 * model.put("ratesPeriod", ratesPeriod); mav = new
	 * ModelAndView("multipleDepositFundTransfer", "model", model); } else { mav =
	 * new ModelAndView("noDataFoundCusm", "model", model); }
	 * 
	 * fundTransferForm.setAccountList(accountList);
	 * fundTransferForm.setDepositHolderObjList(depositHolderObjList);
	 * 
	 * model.put("fundTransferForm", fundTransferForm);
	 * 
	 * model.put("accountList", accountList);
	 * 
	 * return mav; }
	 */
//Method to open tenure and maturity amount calculator 

	@RequestMapping(value = "/calculator", method = RequestMethod.GET)
	public ModelAndView calculator(ModelMap model, @ModelAttribute InterestCalculationForm interestCalculationForm)
			throws CustomException {

		model.put("interestCalculationForm", interestCalculationForm);
		return new ModelAndView("calculator", "model", model);

	}

	// Method to calculate maturity amount
	@RequestMapping(value = "/interestCalculation", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Double interestCalculation(ModelMap model,
			@ModelAttribute InterestCalculationForm interestCalculationForm, Double principalAmount, Integer tenure,
			String tenureType, Float rateOfInterest) throws CustomException {

		Integer getTenureDays = 0;

		Integer yearDays = DateService.getNumOfDaysInYear(); // to get the number of days in a year
		Date tenureEndDate = null; // To get the tenure end date based on years and months for interest

		if (tenureType.equals(Constants.YEAR)) {
			tenureEndDate = DateService.generateYearsDate(DateService.getCurrentDate(), tenure);

		}

		else if (tenureType.equals(Constants.MONTH)) {
			tenureEndDate = DateService.addMonths(DateService.getCurrentDate(), tenure);

		}

		// To get the days between current date and tenure end date
		if (tenureEndDate != null) {
			Integer getTenureDay = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), tenureEndDate);
			getTenureDays = getTenureDay + 1;
		}

		// Interest formula to get the interest
		// I = [p ((1 + R/100)/365)^(D T / D) ]

		Double finalInterest = (principalAmount
				* (Math.pow(1 + ((rateOfInterest / 100) / yearDays), (yearDays * getTenureDays / 365))))
				- principalAmount;
		Double interestAmount = finalInterest + principalAmount;
		Double interestAmountGround = (double) Math.round(interestAmount);

		return interestAmountGround;

	}

	// Method to calculate tenure
	@SuppressWarnings("unused")
	@RequestMapping(value = "/tenureCalculation", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Integer tenureCalculation(ModelMap model,
			@ModelAttribute InterestCalculationForm interestCalculationForm, Double principalAmount,
			Double interestTenure, Float rateOfInterest) throws CustomException {

		Integer getTenureDays = 0;

		Integer yearDays = DateService.getNumOfDaysInYear(); // to get the number of days in a year
		Date tenureEndDate = null; // To get the tenure end date based on years and months for interest

		// To get the days between current date and tenure end date
		if (tenureEndDate != null) {
			Integer getTenureDay = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), tenureEndDate);
			getTenureDays = getTenureDay + 1;
		}

		// To get the the amount from the formula A = I + P
		Double amount = interestTenure - principalAmount;
		// To get the tenure . Teure formula is T = log(I+P) / log(((d*100) + R) /
		// d*100)

		Double tenure1 = (double) (yearDays * 100); // 365* 100
		Double tenure2 = tenure1 + rateOfInterest; // 365100 + R
		Double tenure3 = (amount + principalAmount) / principalAmount; //
		Double tenure4 = Math.log10(tenure3);
		Double tenure5 = tenure2 / tenure1;
		Double tenure6 = Math.log10(tenure5);
		Double tenure7 = tenure4 / tenure6;
		Double finalTenure = fdService.round(tenure7, 0);
		Integer finalTenureInt = finalTenure.intValue();
		return finalTenureInt;

	}

//	@RequestMapping(value = "/convertDeposit", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
//	public  @ResponseBody HashMap<String,Double> convertDeposit(Deposit Deposit, String conversionType)
//	{
//		// conversionType:
//			// Regular Deposit to Recurring Deposit
//			// Recurring to Regular Deposit
//		return fdService.convertDeposit(Deposit);
//	}

	@RequestMapping(value = "/saveConvertedDeposit", method = RequestMethod.POST, produces = "text/html")
	public Distribution saveConvertedDeposit(Deposit Deposit, String conversionType) {
		// conversionType:
		// Regular Deposit to Recurring Deposit
		// Recurring to Regular Deposit
		HashMap<String, Double> conversionSummary = fdService.convertDeposit(Deposit);

		return null;
	}

	@RequestMapping(value = "/convertDeposit", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody HashMap<String, Double> convertDeposit(Long depositId) {
		Deposit deposit = fixedDepositDAO.findById(depositId);
		// conversionType:
		// Regular Deposit to Recurring Deposit
		// Recurring to Regular Deposit
		return fdService.convertDeposit(deposit);
	}

	@RequestMapping(value = "/searchJournal", method = RequestMethod.GET)
	public ModelAndView ledgerReportSummary(ModelMap model, @ModelAttribute LedgerReportForm ledgerReportForm,
			String account) throws CustomException {
		model.put("account", account);
		model.put("ledgerReportForm", ledgerReportForm);
		return new ModelAndView("searchJournal", "model", model);

	}

	/* , String fdAccountNo, Date fromDate, Date toDate */

	@RequestMapping(value = "/journalList", method = RequestMethod.GET)
	public ModelAndView journalList(ModelMap model, @ModelAttribute LedgerReportForm ledgerReportForm)
			throws CustomException {

		Date fromDate = ledgerReportForm.getFromDate();
		Date toDate = ledgerReportForm.getToDate();
		String fdAccountNumber = ledgerReportForm.getFdAccountNo();

		Deposit deposit = fixedDepositDAO.getByAccountNumber(fdAccountNumber);
		if (deposit == null) {
			model.put(Constants.ERROR, "Please Insert correct account number.");
			return new ModelAndView("searchJournal", "model", model);
		}

		List<Journal> journalList = null;
		if (fromDate != null && toDate != null) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromDateStr = sdf.format(fromDate);
			try {
				fromDate = sdf.parse(fromDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			String toDateStr = sdf.format(toDate);
			try {
				toDate = sdf.parse(toDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			journalList = fixedDepositDAO.getJournalListByFdAccNumberAndFromAndToDate(deposit.getId(), fromDate,
					toDate);
		} else {

			model.put(Constants.ERROR, "Please Insert the date.");
			return new ModelAndView("searchJournal", "model", model);
		}

		if (journalList == null) {
			return new ModelAndView("noDataFound", "model", model);
		}
		model.put("journalList", journalList);
		model.put("ledgerReportForm", ledgerReportForm);

		return new ModelAndView("searchJournal", "model", model);
	}

	@RequestMapping(value = "/viewLedger", method = RequestMethod.GET)
	public ModelAndView viewLedger(ModelMap model, @ModelAttribute LedgerReportForm ledgerReportForm,
			String fdAccountNo) throws CustomException {

		Deposit deposit = fixedDepositDAO.getByAccountNumber(fdAccountNo);
		Date fromDate = ledgerReportForm.getFromDate();
		Date toDate = ledgerReportForm.getToDate();
		if (fromDate != null && toDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromDateStr = sdf.format(fromDate);
			try {
				fromDate = sdf.parse(fromDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			String toDateStr = sdf.format(toDate);
			try {
				toDate = sdf.parse(toDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			if (fdAccountNo != null) {
				model.put(Constants.ERROR, "Please Insert the date.");
				return new ModelAndView("searchJournal", "model", model);
			}
		}

		if (deposit == null && fdAccountNo != null) {
			model.put(Constants.ERROR, "Please Insert correct account number.");
			return new ModelAndView("searchJournal", "model", model);
		}
		model.put("ledgerReportForm", ledgerReportForm);

		return new ModelAndView("viewLedger", "model", model);
	}

	@RequestMapping(value = "/viewAllLedger", method = RequestMethod.GET)
	public ModelAndView viewAllLedger(ModelMap model, @ModelAttribute LedgerReportForm ledgerReportForm)
			throws CustomException {
		Date fromDate = ledgerReportForm.getFromDate();
		Date toDate = ledgerReportForm.getToDate();
		if (fromDate != null && toDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromDateStr = sdf.format(fromDate);
			try {
				fromDate = sdf.parse(fromDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			String toDateStr = sdf.format(toDate);
			try {
				toDate = sdf.parse(toDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {

			model.put(Constants.ERROR, "Please Insert the date.");
			return new ModelAndView("searchJournal", "model", model);

		}

		model.put("ledgerReportForm", ledgerReportForm);

		return new ModelAndView("viewAllLedger", "model", model);
	}

	@RequestMapping(value = "/ledgerList", method = RequestMethod.POST)
	public ModelAndView ledgerList(ModelMap model, @ModelAttribute LedgerReportForm ledgerReportForm,
			@RequestParam String[] myArray) throws CustomException {

		Date fromDate = ledgerReportForm.getFromDate();
		Date toDate = ledgerReportForm.getToDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fromDateStr = sdf.format(fromDate);
		try {
			fromDate = sdf.parse(fromDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String toDateStr = sdf.format(toDate);
		try {
			toDate = sdf.parse(toDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Deposit deposit = fixedDepositDAO.getByAccountNumber(ledgerReportForm.getFdAccountNo());

		String[] array = myArray;
		/*for (int i = 0; i < array.length; i++) {
			if (array[i].toString().contains("SavingAccount")) {
				List<LedgerSavingsAccount> ledgerSavingsAccount = fixedDepositDAO
						.getLedgerSavingAccountByDepositId(deposit.getId(), fromDate, toDate);

				if (ledgerSavingsAccount == null)
					model.put("ledgerSavingsAccountError", "No Data Found For Ledger Savings Account");

				model.put("ledgerSavingsAccount", ledgerSavingsAccount);
			} else if (array[i].toString().contains("InterestAccount")) {
				List<LedgerInterestAccount> ledgerInterestAccount = fixedDepositDAO
						.getLedgerInterestAccountByDepositId(deposit.getId(), fromDate, toDate);

				if (ledgerInterestAccount == null)
					model.put("ledgerInterestAccountError", "No Data Found For Ledger Interest Account");

				model.put("ledgerInterestAccount", ledgerInterestAccount);
			} else if (array[i].toString().contains("DepositAccount")) {
				List<LedgerDepositAccount> ledgerDepositAccount = fixedDepositDAO
						.getLedgerdepositAccountAccountByDepositId(deposit.getId(), fromDate, toDate);

				if (ledgerDepositAccount == null)
					model.put("ledgerDepositAccountError", "No Data Found For Ledger Deposit Account");

				model.put("ledgerDepositAccount", ledgerDepositAccount);
			} else if (array[i].toString().contains("DDAccount")) {
				List<LedgerDDAccount> ledgerDDAccount = fixedDepositDAO.getLedgerDDAccountByDepositId(deposit.getId(),
						fromDate, toDate);

				if (ledgerDDAccount == null)
					model.put("ledgerDDAccountError", "No Data Found For Ledger DD Account");

				model.put("ledgerDDAccount", ledgerDDAccount);
			} else if (array[i].toString().contains("CurrentAccount")) {
				List<LedgerCurrentAccount> ledgerCurrentAccount = fixedDepositDAO
						.getLedgerCurrentAccountByDepositId(deposit.getId(), fromDate, toDate);

				if (ledgerCurrentAccount == null)
					model.put("ledgerCurrentAccountError", "No Data Found For Ledger Current Account");

				model.put("ledgerCurrentAccount", ledgerCurrentAccount);
			} else if (array[i].toString().contains("ChequeAccount")) {
				List<LedgerChequeAccount> ledgerChequeAccount = fixedDepositDAO
						.getLedgerChequeAccountByDepositId(deposit.getId(), fromDate, toDate);

				if (ledgerChequeAccount == null)
					model.put("ledgerChequeAccountError", "No Data Found For Ledger Cheque Account");

				model.put("ledgerChequeAccount", ledgerChequeAccount);
			} else if (array[i].toString().contains("ChargesAccount")) {
				List<LedgerChargesAccount> ledgerChargesAccount = fixedDepositDAO
						.getLedgerChargesAccountByDepositId(deposit.getId(), fromDate, toDate);

				if (ledgerChargesAccount == null)
					model.put("ledgerChargesAccountError", "No Data Found For Ledger Charges Account");

				model.put("ledgerChargesAccount", ledgerChargesAccount);
			} else if (array[i].toString().contains("CashAccount")) {
				List<LedgerCashAccount> ledgerCashAccount = fixedDepositDAO
						.getLedgerCashAccountByDepositId(deposit.getId(), fromDate, toDate);

				if (ledgerCashAccount == null)
					model.put("ledgerCashAccountError", "No Data Found For Ledger Cash Account");

				model.put("ledgerCashAccount", ledgerCashAccount);
			}
		}*/

		model.put("ledgerReportForm", ledgerReportForm);

		return new ModelAndView("ledgerList", "model", model);
	}

	@RequestMapping(value = "/ledgerAllList", method = RequestMethod.POST)
	public ModelAndView ledgerAllList(ModelMap model, @ModelAttribute LedgerReportForm ledgerReportForm,
			@RequestParam String[] myArray) throws CustomException {
		Date fromDate = ledgerReportForm.getFromDate();
		Date toDate = ledgerReportForm.getToDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fromDateStr = sdf.format(fromDate);
		try {
			fromDate = sdf.parse(fromDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String toDateStr = sdf.format(toDate);
		try {
			toDate = sdf.parse(toDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// Deposit deposit =
		// fixedDepositDAO.getByAccountNumber(ledgerReportForm.getFdAccountNo());

		String[] array = myArray;
		/*for (int i = 0; i < array.length; i++) {
			if (array[i].toString().contains("SavingAccount")) {
				List<LedgerReportForm> ledgerSavingsAccount = fixedDepositDAO
						.getLedgerSavingAccountByFromDateAndToDateByUsingLedgerReportForm(fromDate, toDate);

				if (ledgerSavingsAccount == null || ledgerSavingsAccount.isEmpty())
					model.put("ledgerSavingsAccountError", "No Data Found For Ledger Savings Account");

				model.put("ledgerSavingsAccount", ledgerSavingsAccount);
			} else if (array[i].toString().contains("InterestAccount")) {
				List<LedgerReportForm> ledgerInterestAccount = fixedDepositDAO
						.getLedgerInterestAccountByFromDateAndToDateByUsingLedgerReportForm(fromDate, toDate);

				if (ledgerInterestAccount == null)
					model.put("ledgerInterestAccountError", "No Data Found For Ledger Interest Account");

				model.put("ledgerInterestAccount", ledgerInterestAccount);
			} else if (array[i].toString().contains("DepositAccount")) {

				List<LedgerDepositAccount> ledgerDepositAccount = fixedDepositDAO
						.getLedgerdepositAccountAccountByFromDateAndToDate(fromDate, toDate);
				List<LedgerReportForm> LedgerReportForm = fixedDepositDAO
						.getLedgerReportFormByFromDateAndToDate(fromDate, toDate);

				if (ledgerDepositAccount == null)
					model.put("ledgerDepositAccountError", "No Data Found For Ledger Deposit Account");

				model.put("LedgerReportForm", LedgerReportForm);
				model.put("ledgerDepositAccount", ledgerDepositAccount);
			} else if (array[i].toString().contains("DDAccount")) {
				List<LedgerReportForm> ledgerDDAccount = fixedDepositDAO
						.getLedgerDDAccountByFromDateAndToDateByUsingLedgerReportForm(fromDate, toDate);

				if (ledgerDDAccount == null)
					model.put("ledgerDDAccountError", "No Data Found For Ledger DD Account");

				model.put("ledgerDDAccount", ledgerDDAccount);
			} else if (array[i].toString().contains("CurrentAccount")) {
				List<LedgerReportForm> ledgerCurrentAccount = fixedDepositDAO
						.getLedgerCurrentAccountByFromDateAndToDateByUsingLedgerReportForm(fromDate, toDate);

				if (ledgerCurrentAccount == null)
					model.put("ledgerCurrentAccountError", "No Data Found For Ledger Current Account");

				model.put("ledgerCurrentAccount", ledgerCurrentAccount);
			} else if (array[i].toString().contains("ChequeAccount")) {
				List<LedgerReportForm> ledgerChequeAccount = fixedDepositDAO
						.getLedgerChequeAccountByFromDateAndToDateByUsingLedgerReportForm(fromDate, toDate);

				if (ledgerChequeAccount == null)
					model.put("ledgerChequeAccountError", "No Data Found For Ledger Cheque Account");

				model.put("ledgerChequeAccount", ledgerChequeAccount);
			} else if (array[i].toString().contains("ChargesAccount")) {
				List<LedgerReportForm> ledgerChargesAccount = fixedDepositDAO
						.getLedgerChargesAccountByFromDateAndToDateByUsingLedgerReportForm(fromDate, toDate);

				if (ledgerChargesAccount == null)
					model.put("ledgerChargesAccountError", "No Data Found For Ledger Charges Account");

				model.put("ledgerChargesAccount", ledgerChargesAccount);
			} else if (array[i].toString().contains("CashAccount")) {
				List<LedgerReportForm> ledgerCashAccount = fixedDepositDAO
						.getLedgerCashAccountByFromDateAndToDateByUsingLedgerReportForm(fromDate, toDate);

				if (ledgerCashAccount == null)
					model.put("ledgerCashAccountError", "No Data Found For Ledger Cash Account");

				model.put("ledgerCashAccount", ledgerCashAccount);
			} else if (array[i].toString().contains("ledgerCommonAccount")) {
				List<LedgerReportForm> ledgerCommonAccount = fixedDepositDAO
						.getLedgerCommonAccountByFromDateAndToDateByUsingLedgerReportForm(fromDate, toDate);

				if (ledgerCommonAccount == null)
					model.put("ledgerCommonAccountError", "No Data Found For Ledger Cash Account");

				model.put("ledgerCommonAccount", ledgerCommonAccount);
			}
		}*/

		model.put("ledgerReportForm", ledgerReportForm);

		return new ModelAndView("ledgerAllList", "model", model);

	}

	@RequestMapping(value = "/dtaaCountryRates", method = RequestMethod.GET)
	public ModelAndView getDTAATaxRates(ModelMap model, RedirectAttributes attributes) throws CustomException {

		List<DTAACountryRates> dtaaCountryRateList = dtaaCountryRatesDAO.getDTAACountryRatesList();

		model.put("dtaaCountryRateList", dtaaCountryRateList);
		return new ModelAndView("dtaaCountryRates", "model", model);
	}

	@RequestMapping(value = "/addDTAATaxRate", method = RequestMethod.GET)
	public ModelAndView addDTAATaxRate(ModelMap model,
			@ModelAttribute AddCountryWiseTaxRateDTAAForm addCountryWiseTaxRateDTAAForm) throws CustomException {

		List<DTAACountry> countryList = dtaaCountryRatesDAO.getDTAACountryList();

		model.put("countryList", countryList);
		model.put("addCountryWiseTaxRateDTAAForm", addCountryWiseTaxRateDTAAForm);
		return new ModelAndView("addDTAATaxRate", "model", model);
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
		return new ModelAndView("redirect:bankFDSaved");
	}

	@RequestMapping(value = "/dashboard")
	public ModelAndView dashboard(ModelMap model) throws CustomException {
		return new ModelAndView("dashboard", "model", model);
	}

	@RequestMapping(value = "/searchCustomerForSweepConfiguration", method = RequestMethod.GET)
	public ModelAndView searchCustomerForSweepConfiguration(ModelMap model) throws CustomException {

		model.put("customerForm", customerForm);
		return new ModelAndView("searchCustomerForSweepConfiguration", "model", model);
	}

	@RequestMapping(value = "/getCustomerListForSweepConfiguration", method = RequestMethod.POST)
	public ModelAndView getCustomerListForSweepConfiguration(ModelMap model, @ModelAttribute CustomerForm customerForm)
			throws CustomException {
		String cusId = customerForm.getCustomerID();
		String cusName = customerForm.getCustomerName();
		String cusNum = customerForm.getContactNum();
		String cusEmail = customerForm.getEmail();

		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);
		model.put("customerList", customerList);
		model.put("customerForm", customerForm);

		return new ModelAndView("searchCustomerForSweepConfiguration", "model", model);

	}

	@RequestMapping(value = "/sweepConfiguration", method = RequestMethod.POST)
	public ModelAndView sweepConfiguration(ModelMap model, Long customerId,
			@ModelAttribute SweepInFacilityForCustomer sweepInFacilityForCustomer) throws CustomException {

		Customer customer = customerDAO.getById(customerId);
		AccountDetails accDetails = accountDetailsDAO.findSavingByCustId(customer.getId());
		SweepConfiguration configuration = sweepConfigurationDAO.getActiveSweepConfiguration();
		// Integer minAmount=configuration.getMinimumAmountRequiredForSweepIn();
		// Integer minSavingBal =configuration.getMinimumSavingBalanceForSweepIn();
		Boolean isTenure = false;
		if (configuration != null) {
			isTenure = configuration.getSweepInType().contains("Fixed Tenure");

		}
		model.put("accDetails", accDetails);

		if (accDetails == null) {
			model.put("error", "Sweep deposit can be configured only for Savings bank account");
			return new ModelAndView("noDataFound", "model", model);
		}
		// if(accDetails!=null || accDetails.getIsSweepDepositRequired()!=null)
		SweepInFacilityForCustomer sweepConfiguration = sweepConfigurationDAO
				.getSweepInFacilityForCustomer(customer.getId());
		Integer isSweepRequired = sweepInFacilityForCustomer.getIsSweepInConfigureedByCustomer() == null ? 0
				: sweepInFacilityForCustomer.getIsSweepInConfigureedByCustomer();
		Integer isSweepInRestrictedByBank = sweepInFacilityForCustomer.getIsSweepInRestrictedByBank() == null ? 0
				: sweepInFacilityForCustomer.getIsSweepInRestrictedByBank();
		if (sweepConfiguration != null) {
			model.put("isTenureConfigured", 1);
			model.put("minAmount", sweepConfiguration.getMinimumAmountRequiredForSweepIn());
			model.put("minSavingBal", sweepConfiguration.getMinimumSavingBalanceForSweepIn());

		} else {

			sweepConfiguration = new SweepInFacilityForCustomer();
			sweepConfiguration.setIsSweepInConfigureedByCustomer(isSweepRequired);
			sweepConfiguration.setIsSweepInRestrictedByBank(isSweepInRestrictedByBank);
			model.put("minAmount", configuration.getMinimumAmountRequiredForSweepIn());
			model.put("minSavingBal", configuration.getMinimumSavingBalanceForSweepIn());
			model.put("isTenureConfigured", 0);
		}

		sweepConfiguration.setAccountId(accDetails.getId());
		sweepConfiguration.setCustomerId(customer.getId());
		Integer isSweepRequiredDB = sweepConfiguration.getIsSweepInConfigureedByCustomer() == null ? 0
				: sweepConfiguration.getIsSweepInConfigureedByCustomer();
		Integer isSweepInRestrictedByBankDB = sweepConfiguration.getIsSweepInRestrictedByBank() == null ? 0
				: sweepConfiguration.getIsSweepInRestrictedByBank();
		sweepConfiguration.setIsSweepInConfigureedByCustomer(isSweepRequiredDB);
		sweepConfiguration.setIsSweepInRestrictedByBank(isSweepInRestrictedByBankDB);
		model.put("sweepInFacilityForCustomer", sweepConfiguration);
		model.put("isTenure", isTenure);
		return new ModelAndView("sweepConfigurationFromEmp", "model", model);

	}

	@RequestMapping(value = "/saveSweepConfiguration", method = RequestMethod.POST)
	public ModelAndView sweepDeposit(ModelMap model,
			@ModelAttribute SweepInFacilityForCustomer sweepInFacilityForCustomer, RedirectAttributes attributes)
			throws CustomException {

		Integer isSweepRequired = sweepInFacilityForCustomer.getIsSweepInConfigureedByCustomer() == null ? 0
				: sweepInFacilityForCustomer.getIsSweepInConfigureedByCustomer();
		Integer isSweepInRestrictedByBank = sweepInFacilityForCustomer.getIsSweepInRestrictedByBank() == null ? 0
				: sweepInFacilityForCustomer.getIsSweepInRestrictedByBank();
		sweepInFacilityForCustomer.setIsSweepInConfigureedByCustomer(isSweepRequired);
		sweepInFacilityForCustomer.setIsSweepInRestrictedByBank(isSweepInRestrictedByBank);
		fdService.saveSweepInFacilityForCustomer(isSweepInRestrictedByBank, sweepInFacilityForCustomer.getCustomerId(),
				isSweepRequired, sweepInFacilityForCustomer.getTenure(),
				sweepInFacilityForCustomer.getMinimumAmountRequiredForSweepIn(),
				sweepInFacilityForCustomer.getMinimumSavingBalanceForSweepIn());

		Date curDate = DateService.getCurrentDateTime();
		attributes.addFlashAttribute(Constants.TRANSACTIONID, fdService.generateRandomString());
		attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
		attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Saved Successfully");

		return new ModelAndView("redirect:bankFDSaved");

	}

	@RequestMapping(value = "/getDurations", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<RatePeriods> getDurations(String citizen, String nriAccountType, String customerCategory,
			String depositClassification) {
		List<RatePeriods> ratesPeriod = ratesDAO.getRateDurations(depositClassification, citizen, nriAccountType,
				customerCategory);

		return ratesPeriod;
	}

	@RequestMapping(value = "/newDeposit")
	public ModelAndView newDeposit(ModelMap model) throws CustomException {

		return new ModelAndView("newDeposit", "model", model);
	}

	@RequestMapping(value = "/depositProducts")
	public ModelAndView depositProducts(ModelMap model, String productType) throws CustomException {

		List<ProductConfiguration> productConfigurationListForRI = productConfigurationDAO
				.getProductConfigurationListByProductTypeAndCitizen(productType, "RI");
		List<ProductConfiguration> productConfigurationListForNRI = productConfigurationDAO
				.getProductConfigurationListByProductTypeAndCitizen(productType, "NRI");

		model.put("productType", productType);
		model.put("productConfigurationListForRI", productConfigurationListForRI);
		model.put("productConfigurationListForNRI", productConfigurationListForNRI);

		return new ModelAndView("depositProducts", "model", model);
	}

	@RequestMapping(value = "/createDeposit", method = RequestMethod.GET)
	public ModelAndView createDeposit(ModelMap model, Long productId) throws CustomException {

		model.put("productConfigurationId", productId);
		fixedDepositForm.setProductConfigurationId(productId);
		model.put("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("applyDeposit", "model", model);

	}

	// Excel upload - BY MANDEEP
	// ------------------------------------------------------------------

@RequestMapping(value = "/updateExistingExcel", method = RequestMethod.POST)
	public ModelAndView updateExistingExcel(@ModelAttribute("newCaseForm") DepositRatesForm depositRatesForm, ModelMap model,
			RedirectAttributes attributes,HttpServletResponse response, String currencies) throws Exception, CustomException {
		try{
		List<MultipartFile> files = depositRatesForm.getFiles();
		for (MultipartFile multipartFile : files) {
			String fileName = multipartFile.getOriginalFilename().trim();
			InputStream excelFile = multipartFile.getInputStream(); 
			
			Workbook workbook = new XSSFWorkbook(excelFile);
			if(fileName.equalsIgnoreCase("Single-Regular-BulkDeposit-Upload.xlsx")){
				UpdateSingleRegularExcel(fileName,workbook,response,currencies);
			}
			if(fileName.equalsIgnoreCase("Single-Recurring-Deposit.xlsx")){
				UpdateSingleRecurringExcel(fileName,workbook,response,currencies);
			}
			if(fileName.equalsIgnoreCase("Joint-Recurring-Deposit.xlsx")){
				UpdateJointRecurringExcel(fileName,workbook,response,currencies);
			}
			if(fileName.equalsIgnoreCase("Joint-Regular-Deposit.xlsx")){
				UpdateJointRegularExcel(fileName,workbook,response,currencies);
			}
		}
		}
		catch (Exception e) {
            e.printStackTrace();
        } 
		attributes = updateTransaction("File Processed successfully", attributes);
		return new ModelAndView("redirect:bankFDSaved");
	}

	@RequestMapping(value = "/updateExcel", method = RequestMethod.POST)
	public String updateExcel(@ModelAttribute("newCaseForm") DepositRatesForm depositRatesForm, ModelMap model,
			RedirectAttributes attributes) throws Exception, CustomException {
		List<MultipartFile> files = depositRatesForm.getFiles();
		for (MultipartFile multipartFile : files) {
			String fileName = multipartFile.getOriginalFilename();
			if (fileName != null && !fileName.equals("")) {
				uploadService.saveImage(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/" + fileName,
						multipartFile);
				/*
				 * int fileImport = dumExceltoDB(fileName, depositRatesForm); if (fileImport ==
				 * 0) { attributes.addFlashAttribute(Constants.ERROR, Constants.EXCELERROR);
				 * return "redirect:depositRate"; }
				 */
			} else {
				if (multipartFile.getSize() > 2000000) {
					throw new MultipartException("File size exceeds");
				}
			}
		}
		return "redirect:bulkDeposit";
	}

	public void UpdateSingleRegularExcel(String fileName, Workbook workbook, HttpServletResponse response,
			String currencies) {
		List<ProductConfiguration> productConfiguration = productConfigurationDAO.findAll();
		String[] productCodes = new String[productConfiguration.size()];
		for (int i = 0; i < productConfiguration.size(); i++) {
			productCodes[i] = productConfiguration.get(i).getProductCode();
		}
		List<Customer> cust = customerDAO.findCustomers();// this is have created a method in customers
		String[] customers = new String[cust.size()];
		for (int i = 0; i < cust.size(); i++) {
			customers[i] = cust.get(i).getCustomerID();
		}
		String currencyArray[] = currencies.split(",");
		Sheet datatypeSheet = workbook.getSheetAt(0);

		Object[][] errorData = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][29];
		Object[][] data = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][29];
		Object[][] data1 = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][29];
		Object[] tempData = new Object[29];
		Object[] errorTempData = new Object[29];

		int d = 0;
		Boolean add = false;
		int d_ = 0;
		String date_ = "";
		for (int rowNum = 1; rowNum <= datatypeSheet.getLastRowNum(); rowNum++) {
			add = false;
			Row r = datatypeSheet.getRow(rowNum);
			if (r == null) {

				continue;
			} else {
				for (int cn = 0; cn < 29; cn++) {
					Cell currentCell = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (currentCell == null) {
						tempData[cn] = "";
					} else {
						if (cn == 0 || cn == 11) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);
						}
						if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
							if (cn == 0) {
								if (ArrayUtils.contains(productCodes, currentCell.getStringCellValue())) {
									add = true;

								} else {
									add = false;

								}
							}
							if (cn == 4 && add == true) {
								if (ArrayUtils.contains(currencyArray, currentCell.getStringCellValue())) {
									add = true;

								} else {
									add = false;

								}
							}
							if (cn == 1 && add == true) {
								if (ArrayUtils.contains(customers, currentCell.getStringCellValue())) {
									add = true;

								} else {
									add = false;

								}
							}
						}
						if (cn == 2) {
							date_ = currentCell.getStringCellValue();
						}
						if (add == true && cn == 2) {

							currentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
							if (DateUtil.isValidExcelDate(currentCell.getNumericCellValue())) {
								add = true;

								currentCell.setCellType(Cell.CELL_TYPE_STRING);

							} else {
								add = false;

							}
						}
						if ((cn == 3 || cn == 5 || cn == 6 || cn == 7 || cn == 18 || cn == 22) && add == true) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);

							try {

								if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
									if (!currentCell.getStringCellValue().isEmpty()
											&& currentCell.getStringCellValue() != "") {
										Long.parseLong(currentCell.getStringCellValue());
										add = true;
									} else {
										add = false;
									}
								}

							} catch (NumberFormatException e) {
								add = false;

							} catch (NullPointerException e) {
								add = false;

							} catch (Exception e) {
								add = false;

							}

						}
						if ((cn == 24 || cn == 28) && add == true) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);

							try {
								if (Integer.parseInt(tempData[18].toString()) < 18) {

									if (!currentCell.getStringCellValue().isEmpty()
											&& currentCell.getStringCellValue() != "") {
										Long.parseLong(currentCell.getStringCellValue());
										add = true;
									} else {
										add = false;
									}
								}

							} catch (NumberFormatException e) {
								add = false;

							} catch (NullPointerException e) {
								add = false;

							} catch (Exception e) {
								add = false;

							}

						}
						if (cn == 2) {
							tempData[cn] = date_;
						} else {
							if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {

								tempData[cn] = currentCell.getStringCellValue();
							} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								tempData[cn] = currentCell.getNumericCellValue();
							}
						}
						if (add == true) {
							errorTempData[cn] = "0";
						} else {
							errorTempData[cn] = "1";
						}
					}
				}

				int nomineeAge = 0;
				if (tempData[18] != null && tempData[18] != "") {
					nomineeAge = Integer.parseInt((tempData[18]).toString());
				}
				int gaurdianAge = 0;
				if (tempData[24] != null && tempData[24] != "") {
					gaurdianAge = Integer.parseInt((tempData[24]).toString());
				}

				/*
				 * if(tempData[13]!=null && nomineeAge >= 18){ add=true; } else
				 */
				if (tempData[18] != null && tempData[18] != "" && nomineeAge < 18
						&& (gaurdianAge < 18 || gaurdianAge == 0 || tempData[23] == null || tempData[25] == null
								|| tempData[26] == null || tempData[27] == null || tempData[28] == null)) {
					add = false;
					errorTempData[23] = "1";
					errorTempData[24] = "1";
					errorTempData[25] = "1";
					errorTempData[26] = "1";
					errorTempData[27] = "1";
					errorTempData[28] = "1";
				}
				if (add == true) {

					for (int i = 0; i < 29; i++) {
						data[d][i] = tempData[i];
					}
					d++;
				} else {
					for (int i = 0; i < 29; i++) {
						data1[d_][i] = tempData[i];
						errorData[d_][i] = errorTempData[i];

					}
					d_++;
				}
			}
		}
		try {
			if (data[0][0] != null) {
				Workbook workbook1 = new XSSFWorkbook(
						new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/" + fileName));
				Sheet datatypeSheet1 = workbook1.getSheetAt(0);
				int rowNum = datatypeSheet1.getPhysicalNumberOfRows();
				/*
				 * wb1.close(); Workbook workbook1 = new XSSFWorkbook(); Sheet datatypeSheet1 =
				 * workbook1.createSheet();
				 */

				for (Object[] datatype : data) {
					if (datatype[0] != null) {

						//////////////////////// block starts
						////// save data from excel to db
						FixedDepositForm fixedDepositForm = new FixedDepositForm();
						// Date currentDate = DateService.getCurrentDate();

						fixedDepositForm.setDepositClassification(Constants.fixedDeposit);

						Integer months_ = datatype[6].toString().equals("") ? 0
								: Integer.parseInt(datatype[6].toString());
						Integer days_ = datatype[7].toString().equals("") ? 0
								: Integer.parseInt(datatype[7].toString());
						Integer years_ = datatype[5].toString().equals("") ? 0
								: Integer.parseInt(datatype[5].toString());

						Date createdDate = new SimpleDateFormat("dd/MM/yyyy").parse(datatype[2].toString()); // It will
																												// be
																												// depositDate
																												// from
																												// Excel
						fixedDepositForm.setDepositDate(createdDate);
						fixedDepositForm.setDepositCreationDate(DateService.getCurrentDate());
						Date maturityDate = DateService.addYear(createdDate, years_);
						maturityDate = DateService.addMonths(maturityDate, months_);
						maturityDate = DateService.addDays(maturityDate, days_);
						Integer totalDays = DateService.getDaysBetweenTwoDates(createdDate, maturityDate) - 1;

						// Integer totalDays=years_*365+months_*30+days_;
						fixedDepositForm.setTenureInMonths(months_);
						fixedDepositForm.setTenureInYears(years_);
						fixedDepositForm.setTenureInDays(days_);
						fixedDepositForm.setTotalTenureInDays(totalDays);

						// Get the customer details to find out the customer category depending on
						// his/her age
						Customer customer = customerDAO.getAge(datatype[1].toString());
						fixedDepositForm.setCustomer(customer);
						fixedDepositForm.setCustomerID(datatype[1].toString());
						fixedDepositForm.setContactNum(customer.getContactNum());
						fixedDepositForm.setEmail(customer.getEmail());
						fixedDepositForm.setCitizen(customer.getCitizen());
						if (fixedDepositForm.getCitizen().equals(Constants.NRI)) {
							fixedDepositForm.setNriAccountType(customer.getNriAccountType());
						}
						fixedDepositForm.setCategory(customer.getCategory());
						fixedDepositForm.setFdAmount(Double.parseDouble(datatype[3].toString()));
						fixedDepositForm.setCurrency(datatype[4].toString());
						fixedDepositForm.setFdDeductDate(createdDate);
						fixedDepositForm.setBranchCode(datatype[8].toString());
						fixedDepositForm.setDepositArea(datatype[9].toString());
						fixedDepositForm.setProductConfigurationId(Long.parseLong(datatype[0].toString()));
						fixedDepositForm.setPaymentMode(datatype[10].toString());
						fixedDepositForm.setDepositType(Constants.SINGLE);
						// fixedDepositForm.setcur(Constants.SINGLE);
						List<AccountDetails> accDetail = accountDetailsDAO.findCurrentSavingByCustId(customer.getId());
						fixedDepositForm.setAccountNo(accDetail.get(0).getAccountNo());

						fixedDepositForm.setNomineeAge(datatype[18].toString());
						fixedDepositForm.setNomineeName(datatype[17].toString());
						fixedDepositForm.setNomineeAddress(datatype[19].toString());
						fixedDepositForm.setNomineeRelationShip(datatype[20].toString());
						fixedDepositForm.setNomineePan(datatype[21].toString());
						fixedDepositForm.setNomineeAadhar(Long.parseLong(datatype[22].toString()));

						if (Integer.parseInt(datatype[18].toString()) < 18) {
							fixedDepositForm.setGuardianAge(datatype[24].toString());
							fixedDepositForm.setGuardianName(datatype[23].toString());
							fixedDepositForm.setGuardianAddress(datatype[25].toString());
							fixedDepositForm.setGuardianRelationShip(datatype[26].toString());
							fixedDepositForm.setGaurdianPan(datatype[27].toString());
							fixedDepositForm.setGaurdianAadhar(Long.parseLong(datatype[28].toString()));
						}
						fixedDepositForm.setDepositAccountType(Constants.SINGLE);
						fixedDepositForm.setPaymentType(Constants.ONETIME);
						fixedDepositForm = fdService.saveRates(fixedDepositForm);
						Integer days = 0;
						// Date maturityDate = null;
						fixedDepositForm.setTaxSavingDeposit("0");

						// maturityDate = DateService.generateDaysDate(DateService.getCurrentDate(),
						// totalDays);

						days = DateService.getDaysBetweenTwoDates(createdDate, maturityDate);
						days = days + 1;

						String category = calculationService.geCustomerActualCategory(customer);// fixedDepositForm.getCategory();

						Float interestRate = calculationService.getDepositInterestRate(days, category,
								fixedDepositForm.getCurrency(), fixedDepositForm.getFdAmount(),
								fixedDepositForm.getDepositClassification(), fixedDepositForm.getCitizen(), "");

						fixedDepositForm.setCategory(category);

						fixedDepositForm.setFdCreditAmount(interestRate);
						// fixedDepositForm.setDepositForm.set

						// Double maturityAmount = fdService.getTotalDepositAmount(fixedDepositForm,
						// maturityDate,
						// fixedDepositForm.getFdAmount());

						Double maturityAmount = fdService.getExpectedMaturityAmount(createdDate, maturityDate,
								fixedDepositForm, interestRate);
						Double totalInterest = maturityAmount
								- (fdService.getFutureDepositDates(fixedDepositForm, maturityDate).size()
										* fixedDepositForm.getFdAmount());

						// Float totalTDS = 0f;
						// Float totalTDS = fdService.getTotalTDSAmount(currentDate,
						// maturityDate, fixedDepositForm.getCategory(),
						// fixedDepositForm, interestList);

						// fixedDepositForm.setEstimateTDSDeduct(Float.parseFloat(((Double)
						// FDService.round(totalTDS, 2)).toString()));
						fixedDepositForm.setFdInterest(interestRate);
						fixedDepositForm.setEstimateInterest(
								Float.parseFloat(((Double) FDService.round(totalInterest, 2)).toString()));
						// Double maturityAmount = totalDepositAmount + totalInterest - totalTDS;
						fixedDepositForm.setEstimatePayOffAmount(FDService.round(maturityAmount, 2));

						fixedDepositForm.setFlexiInterest(fixedDepositForm.getFlexiInterest());

						fixedDepositForm.setMaturityDate(maturityDate);
						fixedDepositForm.setFdTenureDate(maturityDate);
						fixedDepositForm.setMaturityInstruction(datatype[16].toString());

						DepositForm dForm = new DepositForm();
						if (datatype[11].toString() != "") {
							dForm.setLinkedAccountNo(datatype[11].toString());
						}

						dForm.setFdPay(fixedDepositForm.getFdAmount().toString());

						dForm.setChequeDate(DateService.getCurrentDateTime());

						if (datatype[11].toString() != "") {
							dForm.setChequeNo(datatype[11].toString());
						}
						if (datatype[12].toString() != "") {
							dForm.setChequeBank(datatype[12].toString());
						}
						if (datatype[13].toString() != "") {
							dForm.setChequeBranch(datatype[13].toString());
						}
						if (datatype[11].toString() != "") {
							dForm.setCardNo(datatype[11].toString());
						}
						if (datatype[12].toString() != "") {
							dForm.setCardType(datatype[12].toString());
						}
						if (datatype[15].toString() != "") {
							dForm.setCvv(Integer.parseInt(datatype[15].toString()));
						}
						if (datatype[13].toString() != "" && datatype[14].toString() != "") {
							dForm.setExpiryDate(datatype[13].toString() + "-" + datatype[14].toString());
						}
						fixedDepositForm.setDepositForm(dForm);

						saveDepositFromExcel(fixedDepositForm);
						/////////////////////// block ends
						Row row = datatypeSheet1.createRow(rowNum++);
						int colNum = 0;
						for (Object field : datatype) {

							Cell cell = row.createCell(colNum++);
							if (field instanceof String) {
								cell.setCellValue((String) field);

							} else if (field instanceof Integer) {
								cell.setCellValue((Integer) field);
							}
						}
					}
				}
				FileOutputStream outputStream = new FileOutputStream(
						context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/" + fileName, true);
				workbook1.write(outputStream);
				workbook1.close();
			}

			if (data1[0][0] != null) {
				Workbook workbook1 = new XSSFWorkbook(
						new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/_" + fileName));
				Sheet datatypeSheet1 = workbook1.getSheetAt(0);

				CellStyle style = workbook1.createCellStyle();
				style.setBorderBottom((short) 0x1);
				style.setBottomBorderColor(IndexedColors.RED.getIndex());
				style.setBorderRight((short) 0x1);
				style.setRightBorderColor(IndexedColors.RED.getIndex());
				style.setBorderTop((short) 0x1);
				style.setTopBorderColor(IndexedColors.RED.getIndex());

				int rowNums = datatypeSheet1.getPhysicalNumberOfRows();
				for (int i = 1; i < rowNums; i++) {
					datatypeSheet1.removeRow(datatypeSheet1.getRow(i));
				}
				int rowNum = 1;
				int row1 = 0;
				int column1 = 0;
				for (Object[] datatype : data1) {
					if (datatype[0] != null) {
						Row row = datatypeSheet1.createRow(rowNum++);
						column1 = 0;
						int colNum = 0;
						for (Object field : datatype) {
							Cell cell = row.createCell(colNum++);
							if (errorData[row1][column1] == "1") {
								for (int i = column1; i < 28; i++) {
									errorData[row1][i] = 0;
								}
								cell.setCellStyle(style);
							}
							column1++;
							if (field instanceof String) {
								cell.setCellValue((String) field);

							} else if (field instanceof Integer) {
								cell.setCellValue((Integer) field);
							}
						}
						row1++;
					}
				}
				FileOutputStream outputStream = new FileOutputStream(
						context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/_" + fileName, true);
				workbook1.write(outputStream);
				workbook1.close();

				File file = new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/_" + fileName);
				if (file.exists()) {
					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}
					response.setContentType(mimeType);
					response.setContentLength((int) file.length());
					response.setHeader("Content-Disposition",
							String.format("attachment; filename=\"" + file.getName() + "\""));
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

					FileCopyUtils.copy(inputStream, response.getOutputStream());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void UpdateSingleRecurringExcel(String fileName, Workbook workbook, HttpServletResponse response,
			String currencies) {
		List<ProductConfiguration> productConfiguration = productConfigurationDAO.findAll();
		String[] productCodes = new String[productConfiguration.size()];
		for (int i = 0; i < productConfiguration.size(); i++) {
			productCodes[i] = productConfiguration.get(i).getProductCode();
		}
		List<Customer> cust = customerDAO.findCustomers();
		String[] customers = new String[cust.size()];
		for (int i = 0; i < cust.size(); i++) {
			customers[i] = cust.get(i).getCustomerID();
		}
		String currencyArray[] = currencies.split(",");
		Sheet datatypeSheet = workbook.getSheetAt(0);

		Object[][] errorData = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][31];
		Object[][] data = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][31];
		Object[][] data1 = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][31];
		Object[] tempData = new Object[31];
		Object[] errorTempData = new Object[31];

		int d = 0;
		Boolean add = false;
		int d_ = 0;
		String date_ = "";
		for (int rowNum = 1; rowNum <= datatypeSheet.getLastRowNum(); rowNum++) {
			add = false;
			Row r = datatypeSheet.getRow(rowNum);
			if (r == null) {

				continue;
			} else {
				for (int cn = 0; cn < 31; cn++) {
					Cell currentCell = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (currentCell == null) {
						tempData[cn] = "";
					} else {
						if (cn == 0 || cn == 13 || cn == 5) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);
						}
						if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
							if (cn == 0) {
								if (ArrayUtils.contains(productCodes, currentCell.getStringCellValue())) {
									add = true;

								} else {
									add = false;

								}
							}
							if (cn == 4 && add == true) {
								if (ArrayUtils.contains(currencyArray, currentCell.getStringCellValue())) {
									add = true;

								} else {
									add = false;

								}
							}
							if (cn == 1 && add == true) {
								if (ArrayUtils.contains(customers, currentCell.getStringCellValue())) {
									add = true;

								} else {
									add = false;

								}
							}
						}
						if (cn == 2) {
							date_ = currentCell.getStringCellValue();
						}
						if (add == true && cn == 2) {

							currentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
							if (DateUtil.isValidExcelDate(currentCell.getNumericCellValue())) {
								add = true;

								currentCell.setCellType(Cell.CELL_TYPE_STRING);

							} else {
								add = false;

							}
						}
						if ((cn == 3 || cn == 7 || cn == 8 || cn == 9 || cn == 20 || cn == 24) && add == true) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);

							try {

								if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
									if (!currentCell.getStringCellValue().isEmpty()
											&& currentCell.getStringCellValue() != "") {
										Long.parseLong(currentCell.getStringCellValue());
										add = true;
									} else {
										add = false;
									}
								}

							} catch (NumberFormatException e) {
								add = false;

							} catch (NullPointerException e) {
								add = false;

							} catch (Exception e) {
								add = false;

							}

						}
						if ((cn == 26 || cn == 30) && add == true) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);

							try {
								if (Integer.parseInt(tempData[20].toString()) < 18) {

									if (!currentCell.getStringCellValue().isEmpty()
											&& currentCell.getStringCellValue() != "") {
										Long.parseLong(currentCell.getStringCellValue());
										add = true;
									} else {
										add = false;
									}
								}

							} catch (NumberFormatException e) {
								add = false;

							} catch (NullPointerException e) {
								add = false;

							} catch (Exception e) {
								add = false;

							}

						}
						if (cn == 2) {
							tempData[cn] = date_;
						} else if (cn == 5) {

							tempData[cn] = Math.round(Float.parseFloat(currentCell.getStringCellValue()));
						} else {
							if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {

								tempData[cn] = currentCell.getStringCellValue();
							} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								tempData[cn] = currentCell.getNumericCellValue();
							}
						}
						if (add == true) {
							errorTempData[cn] = "0";
						} else {
							errorTempData[cn] = "1";
						}
					}
				}

				int nomineeAge = 0;
				if (tempData[20] != null && tempData[20] != "") {
					nomineeAge = Integer.parseInt((tempData[20]).toString());
				}
				int gaurdianAge = 0;
				if (tempData[26] != null && tempData[26] != "") {
					gaurdianAge = Integer.parseInt((tempData[26]).toString());
				}

				/*
				 * if(tempData[13]!=null && nomineeAge >= 18){ add=true; } else
				 */
				if (tempData[20] != null && tempData[20] != "" && nomineeAge < 18
						&& (gaurdianAge < 18 || gaurdianAge == 0 || tempData[25] == null || tempData[27] == null
								|| tempData[28] == null || tempData[29] == null || tempData[30] == null)) {
					add = false;
					errorTempData[25] = "1";
					errorTempData[26] = "1";
					errorTempData[27] = "1";
					errorTempData[28] = "1";
					errorTempData[29] = "1";
					errorTempData[30] = "1";
				}
				if (add == true) {

					for (int i = 0; i < 31; i++) {
						data[d][i] = tempData[i];
					}
					d++;
				} else {
					for (int i = 0; i < 31; i++) {
						data1[d_][i] = tempData[i];
						errorData[d_][i] = errorTempData[i];

					}
					d_++;
				}
			}
		}
		try {
			if (data[0][0] != null) {
				Workbook workbook1 = new XSSFWorkbook(
						new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/" + fileName));
				Sheet datatypeSheet1 = workbook1.getSheetAt(0);
				int rowNum = datatypeSheet1.getPhysicalNumberOfRows();
				for (Object[] datatype : data) {
					if (datatype[0] != null) {

						//////////////////////// block starts
						////// save data from excel to db
						FixedDepositForm fixedDepositForm = new FixedDepositForm();
						// Date currentDate = DateService.getCurrentDate();
						// testing from here pending
						fixedDepositForm.setDepositClassification(Constants.recurringDeposit);

						Integer months_ = datatype[8].toString().equals("") ? 0
								: Integer.parseInt(datatype[8].toString());
						Integer days_ = datatype[9].toString().equals("") ? 0
								: Integer.parseInt(datatype[9].toString());
						Integer years_ = datatype[7].toString().equals("") ? 0
								: Integer.parseInt(datatype[7].toString());

						Date createdDate = new SimpleDateFormat("dd/MM/yyyy").parse(datatype[2].toString()); // It will
																												// be
																												// depositDate
																												// from
																												// Excel
						fixedDepositForm.setDepositDate(createdDate);
						fixedDepositForm.setDepositCreationDate(DateService.getCurrentDate());
						Date maturityDate = DateService.addYear(createdDate, years_);
						maturityDate = DateService.addMonths(maturityDate, months_);
						maturityDate = DateService.addDays(maturityDate, days_);
						Integer totalDays = DateService.getDaysBetweenTwoDates(createdDate, maturityDate) - 1;

						// Integer totalDays=years_*365+months_*30+days_;
						fixedDepositForm.setTenureInMonths(months_);
						fixedDepositForm.setTenureInYears(years_);
						fixedDepositForm.setTenureInDays(days_);
						fixedDepositForm.setTotalTenureInDays(totalDays);

						// Get the customer details to find out the customer category depending on
						// his/her age
						Customer customer = customerDAO.getAge(datatype[1].toString());
						fixedDepositForm.setCustomer(customer);

						fixedDepositForm.setCustomerID(datatype[1].toString());
						fixedDepositForm.setContactNum(customer.getContactNum());
						fixedDepositForm.setEmail(customer.getEmail());
						fixedDepositForm.setCitizen(customer.getCitizen());
						if (fixedDepositForm.getCitizen().equals(Constants.NRI)) {
							fixedDepositForm.setNriAccountType(customer.getNriAccountType());
						}
						fixedDepositForm.setCategory(customer.getCategory());
						fixedDepositForm.setFdAmount(Double.parseDouble(datatype[3].toString()));
						fixedDepositForm.setCurrency(datatype[4].toString());
						fixedDepositForm.setFdDeductDate(createdDate);
						fixedDepositForm.setBranchCode(datatype[10].toString());
						fixedDepositForm.setDepositArea(datatype[11].toString());
						fixedDepositForm.setProductConfigurationId(Long.parseLong(datatype[0].toString()));
						fixedDepositForm.setPaymentMode(datatype[12].toString());
						fixedDepositForm.setDepositType(Constants.SINGLE);
						// fixedDepositForm.setcur(Constants.SINGLE);
						List<AccountDetails> accDetail = accountDetailsDAO.findCurrentSavingByCustId(customer.getId());
						fixedDepositForm.setAccountNo(accDetail.get(0).getAccountNo());

						fixedDepositForm.setNomineeAge(datatype[20].toString());
						fixedDepositForm.setNomineeName(datatype[19].toString());
						fixedDepositForm.setNomineeAddress(datatype[21].toString());
						fixedDepositForm.setNomineeRelationShip(datatype[22].toString());
						fixedDepositForm.setNomineePan(datatype[23].toString());
						fixedDepositForm.setNomineeAadhar(Long.parseLong(datatype[24].toString()));

						if (Integer.parseInt(datatype[20].toString()) < 18) {
							fixedDepositForm.setGuardianAge(datatype[26].toString());
							fixedDepositForm.setGuardianName(datatype[25].toString());
							fixedDepositForm.setGuardianAddress(datatype[27].toString());
							fixedDepositForm.setGuardianRelationShip(datatype[28].toString());
							fixedDepositForm.setGaurdianPan(datatype[29].toString());
							fixedDepositForm.setGaurdianAadhar(Long.parseLong(datatype[30].toString()));
						}
						fixedDepositForm.setDepositAccountType(Constants.SINGLE);
						fixedDepositForm.setPaymentType(datatype[6].toString());
						fixedDepositForm.setDeductionDay(Math.round(Float.parseFloat(datatype[5].toString())));
						fixedDepositForm = fdService.saveRates(fixedDepositForm);

						Integer days = 0;
						// Date maturityDate = null;
						fixedDepositForm.setTaxSavingDeposit("0");

						// maturityDate = DateService.generateDaysDate(DateService.getCurrentDate(),
						// totalDays);

						days = DateService.getDaysBetweenTwoDates(createdDate, maturityDate);
						days = days + 1;

						String category = calculationService.geCustomerActualCategory(customer);// fixedDepositForm.getCategory();

						Float interestRate = calculationService.getDepositInterestRate(days, category,
								fixedDepositForm.getCurrency(), fixedDepositForm.getFdAmount(),
								fixedDepositForm.getDepositClassification(), fixedDepositForm.getCitizen(), "");

						fixedDepositForm.setCategory(category);

						fixedDepositForm.setFdCreditAmount(interestRate);
						// fixedDepositForm.setDepositForm.set

						// Double maturityAmount = fdService.getTotalDepositAmount(fixedDepositForm,
						// maturityDate,
						// fixedDepositForm.getFdAmount());

						Double maturityAmount = fdService.getExpectedMaturityAmount(createdDate, maturityDate,
								fixedDepositForm, interestRate);
						Double totalInterest = maturityAmount
								- (fdService.getFutureDepositDates(fixedDepositForm, maturityDate).size()
										* fixedDepositForm.getFdAmount());

						// Float totalTDS = 0f;
						// Float totalTDS = fdService.getTotalTDSAmount(currentDate,
						// maturityDate, fixedDepositForm.getCategory(),
						// fixedDepositForm, interestList);

						// fixedDepositForm.setEstimateTDSDeduct(Float.parseFloat(((Double)
						// FDService.round(totalTDS, 2)).toString()));
						fixedDepositForm.setFdInterest(interestRate);
						fixedDepositForm.setEstimateInterest(
								Float.parseFloat(((Double) FDService.round(totalInterest, 2)).toString()));
						// Double maturityAmount = totalDepositAmount + totalInterest - totalTDS;
						fixedDepositForm.setEstimatePayOffAmount(FDService.round(maturityAmount, 2));

						fixedDepositForm.setFlexiInterest(fixedDepositForm.getFlexiInterest());

						fixedDepositForm.setMaturityDate(maturityDate);
						fixedDepositForm.setFdTenureDate(maturityDate);
						fixedDepositForm.setMaturityInstruction(datatype[16].toString());

						DepositForm dForm = new DepositForm();
						if (datatype[13].toString() != "") {
							dForm.setLinkedAccountNo(datatype[13].toString());
						}

						dForm.setFdPay(fixedDepositForm.getFdAmount().toString());

						dForm.setChequeDate(DateService.getCurrentDateTime());

						if (datatype[13].toString() != "") {
							dForm.setChequeNo(datatype[13].toString());
						}
						if (datatype[14].toString() != "") {
							dForm.setChequeBank(datatype[14].toString());
						}
						if (datatype[15].toString() != "") {
							dForm.setChequeBranch(datatype[15].toString());
						}
						if (datatype[13].toString() != "") {
							dForm.setCardNo(datatype[13].toString());
						}
						if (datatype[14].toString() != "") {
							dForm.setCardType(datatype[14].toString());
						}
						if (datatype[17].toString() != "") {
							dForm.setCvv(Integer.parseInt(datatype[17].toString()));
						}
						if (datatype[15].toString() != "" && datatype[16].toString() != "") {
							dForm.setExpiryDate(datatype[15].toString() + "-" + datatype[16].toString());
						}
						fixedDepositForm.setDepositForm(dForm);

						saveDepositFromExcel(fixedDepositForm);
						/////////////////////// block ends
						Row row = datatypeSheet1.createRow(rowNum++);
						int colNum = 0;
						for (Object field : datatype) {

							Cell cell = row.createCell(colNum++);
							if (field instanceof String) {
								cell.setCellValue((String) field);

							} else if (field instanceof Integer) {
								cell.setCellValue((Integer) field);
							}
						}
					}
				}
				FileOutputStream outputStream = new FileOutputStream(
						context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/" + fileName, true);
				workbook1.write(outputStream);
				workbook1.close();
			}

			if (data1[0][0] != null) {
				Workbook workbook1 = new XSSFWorkbook(
						new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/_" + fileName));
				Sheet datatypeSheet1 = workbook1.getSheetAt(0);

				CellStyle style = workbook1.createCellStyle();
				style.setBorderBottom((short) 0x1);
				style.setBottomBorderColor(IndexedColors.RED.getIndex());
				style.setBorderRight((short) 0x1);
				style.setRightBorderColor(IndexedColors.RED.getIndex());
				style.setBorderTop((short) 0x1);
				style.setTopBorderColor(IndexedColors.RED.getIndex());

				int rowNums = datatypeSheet1.getPhysicalNumberOfRows();
				for (int i = 1; i < rowNums; i++) {
					datatypeSheet1.removeRow(datatypeSheet1.getRow(i));
				}
				int rowNum = 1;
				int row1 = 0;
				int column1 = 0;
				for (Object[] datatype : data1) {
					if (datatype[0] != null) {
						Row row = datatypeSheet1.createRow(rowNum++);
						column1 = 0;
						int colNum = 0;
						for (Object field : datatype) {
							Cell cell = row.createCell(colNum++);
							if (errorData[row1][column1] == "1") {
								for (int i = column1; i < 30; i++) {
									errorData[row1][i] = 0;
								}
								cell.setCellStyle(style);
							}
							column1++;
							if (field instanceof String) {
								cell.setCellValue((String) field);

							} else if (field instanceof Integer) {
								cell.setCellValue((Integer) field);
							}
						}
						row1++;
					}
				}
				FileOutputStream outputStream = new FileOutputStream(
						context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/_" + fileName, true);
				workbook1.write(outputStream);
				workbook1.close();

				File file = new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/_" + fileName);
				if (file.exists()) {
					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}
					response.setContentType(mimeType);
					response.setContentLength((int) file.length());
					response.setHeader("Content-Disposition",
							String.format("attachment; filename=\"" + file.getName() + "\""));
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void UpdateJointRegularExcel(String fileName, Workbook workbook, HttpServletResponse response,
			String currencies) {
		List<ProductConfiguration> productConfiguration = productConfigurationDAO.findAll();
		String[] productCodes = new String[productConfiguration.size()];
		for (int i = 0; i < productConfiguration.size(); i++) {
			productCodes[i] = productConfiguration.get(i).getProductCode();
		}
		List<Customer> cust = customerDAO.findCustomers();
		String[] customers = new String[cust.size()];
		for (int i = 0; i < cust.size(); i++) {
			customers[i] = cust.get(i).getCustomerID();
		}
		String currencyArray[] = currencies.split(",");
		Sheet datatypeSheet = workbook.getSheetAt(0);

		Object[][] errorData = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][44];
		Object[][] data = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][44];
		Object[][] data1 = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][44];
		Object[] tempData = new Object[44];
		Object[] errorTempData = new Object[44];

		int d = 0;
		Boolean add = false;
		int d_ = 0;
		String date_ = "";
		for (int rowNum = 1; rowNum <= datatypeSheet.getLastRowNum(); rowNum++) {
			add = false;
			Row r = datatypeSheet.getRow(rowNum);
			if (r == null) {

				continue;
			} else {
				for (int cn = 0; cn < 44; cn++) {
					Cell currentCell = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (currentCell == null) {
						tempData[cn] = "";
					} else {
						if (cn == 0 || cn == 6 || cn == 7) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);
						}
						if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
							if (cn == 0) {
								if (ArrayUtils.contains(productCodes, currentCell.getStringCellValue())) {
									add = true;

								} else {
									add = false;

								}
							}
							if (cn == 5 && add == true) {
								if (ArrayUtils.contains(currencyArray, currentCell.getStringCellValue())) {
									add = true;

								} else {
									add = false;

								}
							}
							if ((cn == 1 || cn == 2) && add == true) {
								if (ArrayUtils.contains(customers, currentCell.getStringCellValue())) {
									add = true;

								} else {
									add = false;

								}
							}

						}
						if (cn == 3) {
							date_ = currentCell.getStringCellValue();
						}
						if (add == true && cn == 3) {

							currentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
							if (DateUtil.isValidExcelDate(currentCell.getNumericCellValue())) {
								add = true;

								currentCell.setCellType(Cell.CELL_TYPE_STRING);

							} else {
								add = false;

							}
						}

						// testing done till here
						if ((cn == 4 || cn == 8 || cn == 9 || cn == 10 || cn == 21 || cn == 25 || cn == 33 || cn == 37)
								&& add == true) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);

							try {

								if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
									if (!currentCell.getStringCellValue().isEmpty()
											&& currentCell.getStringCellValue() != "") {
										Long.parseLong(currentCell.getStringCellValue());
										add = true;
									} else {
										add = false;
									}
								}

							} catch (NumberFormatException e) {
								add = false;

							} catch (NullPointerException e) {
								add = false;

							} catch (Exception e) {
								add = false;

							}

						}
						if ((cn == 27 || cn == 31) && add == true) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);

							try {
								if (Integer.parseInt(tempData[21].toString()) < 18) {

									if (!currentCell.getStringCellValue().isEmpty()
											&& currentCell.getStringCellValue() != "") {
										Long.parseLong(currentCell.getStringCellValue());
										add = true;
									} else {
										add = false;
									}
								}

							} catch (NumberFormatException e) {
								add = false;

							} catch (NullPointerException e) {
								add = false;

							} catch (Exception e) {
								add = false;

							}

						}
						if ((cn == 39 || cn == 43) && add == true) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);

							try {
								if (Integer.parseInt(tempData[33].toString()) < 18) {

									if (!currentCell.getStringCellValue().isEmpty()
											&& currentCell.getStringCellValue() != "") {
										Long.parseLong(currentCell.getStringCellValue());
										add = true;
									} else {
										add = false;
									}
								}

							} catch (NumberFormatException e) {
								add = false;

							} catch (NullPointerException e) {
								add = false;

							} catch (Exception e) {
								add = false;

							}

						}
						if (cn == 3) {
							tempData[cn] = date_;
						}

						else {
							if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {

								tempData[cn] = currentCell.getStringCellValue();
							} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								tempData[cn] = currentCell.getNumericCellValue();
							}
						}
						if (add == true) {
							errorTempData[cn] = "0";
						} else {
							errorTempData[cn] = "1";
						}
					}
				}

				int PnomineeAge = 0;
				if (tempData[21] != null && tempData[21] != "") {
					PnomineeAge = Integer.parseInt((tempData[21]).toString());
				}
				int PgaurdianAge = 0;
				if (tempData[27] != null && tempData[27] != "") {
					PgaurdianAge = Integer.parseInt((tempData[27]).toString());
				}

				if (tempData[21] != null && tempData[21] != "" && PnomineeAge < 18
						&& (PgaurdianAge < 18 || PgaurdianAge == 0 || tempData[26] == null || tempData[28] == null
								|| tempData[29] == null || tempData[30] == null || tempData[31] == null)) {
					add = false;
					errorTempData[26] = "1";
					errorTempData[27] = "1";
					errorTempData[28] = "1";
					errorTempData[29] = "1";
					errorTempData[30] = "1";
					errorTempData[31] = "1";
				}

				int SnomineeAge = 0;
				if (tempData[33] != null && tempData[33] != "") {
					SnomineeAge = Integer.parseInt((tempData[33]).toString());
				}
				int SgaurdianAge = 0;
				if (tempData[39] != null && tempData[39] != "") {
					SgaurdianAge = Integer.parseInt((tempData[39]).toString());
				}

				if (tempData[33] != null && tempData[33] != "" && SnomineeAge < 18
						&& (SgaurdianAge < 18 || SgaurdianAge == 0 || tempData[38] == null || tempData[40] == null
								|| tempData[41] == null || tempData[42] == null || tempData[43] == null)) {
					add = false;
					errorTempData[38] = "1";
					errorTempData[39] = "1";
					errorTempData[40] = "1";
					errorTempData[41] = "1";
					errorTempData[42] = "1";
					errorTempData[43] = "1";
				}

				if (add == true) {

					for (int i = 0; i < 44; i++) {
						data[d][i] = tempData[i];
					}
					d++;
				} else {
					for (int i = 0; i < 44; i++) {
						data1[d_][i] = tempData[i];
						errorData[d_][i] = errorTempData[i];

					}
					d_++;
				}
			}
		}
		try {
			if (data[0][0] != null) {
				Workbook workbook1 = new XSSFWorkbook(
						new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/" + fileName));
				Sheet datatypeSheet1 = workbook1.getSheetAt(0);
				int rowNum = datatypeSheet1.getPhysicalNumberOfRows();
				for (Object[] datatype : data) {
					if (datatype[0] != null) {

						//////////////////////// block starts save data from excel to db
						FixedDepositForm fixedDepositForm = new FixedDepositForm();
						// Date currentDate = DateService.getCurrentDate();
						// testing from here pending
						fixedDepositForm.setDepositClassification(Constants.fixedDeposit);
						fixedDepositForm.setUserContribution(Float.parseFloat(datatype[6].toString()));
						Integer months_ = datatype[9].toString().equals("") ? 0
								: Integer.parseInt(datatype[9].toString());
						Integer days_ = datatype[10].toString().equals("") ? 0
								: Integer.parseInt(datatype[10].toString());
						Integer years_ = datatype[8].toString().equals("") ? 0
								: Integer.parseInt(datatype[8].toString());

						Date createdDate = new SimpleDateFormat("dd/MM/yyyy").parse(datatype[3].toString()); // It will
																												// be
																												// depositDate
																												// from
																												// Excel
						fixedDepositForm.setDepositDate(createdDate);
						fixedDepositForm.setDepositCreationDate(DateService.getCurrentDate());
						Date maturityDate = DateService.addYear(createdDate, years_);
						maturityDate = DateService.addMonths(maturityDate, months_);
						maturityDate = DateService.addDays(maturityDate, days_);
						Integer totalDays = DateService.getDaysBetweenTwoDates(createdDate, maturityDate) - 1;

						// Integer totalDays=years_*365+months_*30+days_;
						fixedDepositForm.setTenureInMonths(months_);
						fixedDepositForm.setTenureInYears(years_);
						fixedDepositForm.setTenureInDays(days_);
						fixedDepositForm.setTotalTenureInDays(totalDays);

						Customer customer = customerDAO.getAge(datatype[1].toString());
						fixedDepositForm.setCustomer(customer);
						fixedDepositForm.setCustomerID(datatype[1].toString());
						fixedDepositForm.setContactNum(customer.getContactNum());
						fixedDepositForm.setEmail(customer.getEmail());
						fixedDepositForm.setCitizen(customer.getCitizen());
						if (fixedDepositForm.getCitizen().equals(Constants.NRI)) {
							fixedDepositForm.setNriAccountType(customer.getNriAccountType());
						}
						fixedDepositForm.setCategory(customer.getCategory());

						fixedDepositForm.setFdAmount(Double.parseDouble(datatype[4].toString()));
						fixedDepositForm.setCurrency(datatype[5].toString());
						fixedDepositForm.setFdDeductDate(createdDate);
						fixedDepositForm.setBranchCode(datatype[11].toString());
						fixedDepositForm.setDepositArea(datatype[12].toString());
						fixedDepositForm.setProductConfigurationId(Long.parseLong(datatype[0].toString()));
						fixedDepositForm.setPaymentMode(datatype[13].toString());
						fixedDepositForm.setDepositType(Constants.JOINT);

						List<AccountDetails> accDetail = accountDetailsDAO.findCurrentSavingByCustId(customer.getId());
						fixedDepositForm.setAccountNo(accDetail.get(0).getAccountNo());

						fixedDepositForm.setNomineeAge(datatype[21].toString());
						fixedDepositForm.setNomineeName(datatype[20].toString());
						fixedDepositForm.setNomineeAddress(datatype[22].toString());
						fixedDepositForm.setNomineeRelationShip(datatype[23].toString());
						fixedDepositForm.setNomineePan(datatype[24].toString());
						fixedDepositForm.setNomineeAadhar(Long.parseLong(datatype[25].toString()));

						if (Integer.parseInt(datatype[21].toString()) < 18) {
							fixedDepositForm.setGuardianAge(datatype[27].toString());
							fixedDepositForm.setGuardianName(datatype[26].toString());
							fixedDepositForm.setGuardianAddress(datatype[28].toString());
							fixedDepositForm.setGuardianRelationShip(datatype[29].toString());
							fixedDepositForm.setGaurdianPan(datatype[30].toString());
							fixedDepositForm.setGaurdianAadhar(Long.parseLong(datatype[31].toString()));
						}
						fixedDepositForm.setDepositAccountType(Constants.JOINT);
						fixedDepositForm.setPaymentType(Constants.ONETIME);
						// fixedDepositForm.setDeductionDay(Math.round(Float.parseFloat(datatype[9].toString())));
						fixedDepositForm = fdService.saveRates(fixedDepositForm);

						fixedDepositForm.setTaxSavingDeposit("0");
						List<JointAccount> accounts = new ArrayList<JointAccount>();
						Customer sCustomer = customerDAO.getAge(datatype[2].toString());

						JointAccount account = new JointAccount();
						account.setId(sCustomer.getId());
						account.setName(sCustomer.getCustomerName());
						account.setGender(sCustomer.getGender());
						account.setAge(sCustomer.getAge());
						account.setContactNo(sCustomer.getContactNum());
						account.setEmail(sCustomer.getEmail());
						account.setAddress(sCustomer.getAddress());
						account.setCity(sCustomer.getCity());
						account.setState(sCustomer.getState());
						account.setCountry(sCustomer.getCountry());
						account.setPincode(sCustomer.getPincode());
						account.setDepositType(Constants.JOINT);
						account.setContributionPercent(Float.parseFloat(datatype[7].toString()));
						JointConsortiumNominees nominee = new JointConsortiumNominees();
						nominee.setAadharNo(Long.parseLong(datatype[37].toString()));
						nominee.setPanNo(datatype[36].toString());
						nominee.setRelationship(datatype[35].toString());
						nominee.setAge(Integer.parseInt(datatype[33].toString()));
						nominee.setName(datatype[32].toString());
						nominee.setAddress(datatype[34].toString());

						if (Integer.parseInt(datatype[33].toString()) < 18) {
							nominee.setGaurdianAadharNo(Long.parseLong(datatype[43].toString()));
							nominee.setGaurdianPanNo(datatype[42].toString());
							nominee.setGaurdianRelation(datatype[41].toString());
							nominee.setGaurdianAge(Integer.parseInt(datatype[39].toString()));
							nominee.setGaurdianName(datatype[38].toString());
							nominee.setGaurdianAddress(datatype[40].toString());
						}
						account.setNominee(nominee);

						accounts.add(account);

						fixedDepositForm.setJointAccounts(accounts);

						if (fixedDepositForm.getNomineeName() != "" && fixedDepositForm.getNomineeName() != null) {
							for (int i = 0; i < fixedDepositForm.getJointAccounts().size(); i++) {

								/***** Saving DepositHolderNominee *****/

								DepositHolderNominees nomineeJoint = new DepositHolderNominees();
								/*
								 * getJointcustomerByPanCard = customerDAO
								 * .getNomineeByPanCard(fixedDepositForm.getJointAccounts().get(i).getNominee().
								 * getPanNo()); getJointcustomerByAadharCard = customerDAO
								 * .getNomineeByAadharCard(fixedDepositForm.getJointAccounts().get(i).getNominee
								 * ().getAadharNo());
								 */
								nomineeJoint.setNomineePan(fixedDepositForm.getJointAccounts().get(i).getNominee()
										.getPanNo().toUpperCase());
								nomineeJoint.setNomineeAadhar(
										fixedDepositForm.getJointAccounts().get(i).getNominee().getAadharNo());

								int ageJoint = Integer.parseInt(
										fixedDepositForm.getJointAccounts().get(i).getNominee().getAge().toString());
								if (ageJoint < 18) {

									nomineeJoint.setGaurdianPan(fixedDepositForm.getJointAccounts().get(i).getNominee()
											.getGaurdianPanNo().toUpperCase());
									nomineeJoint.setGaurdianAadhar(fixedDepositForm.getJointAccounts().get(i)
											.getNominee().getGaurdianAadharNo());
								}

							}
						}
						Integer days = 0;
						Date currentDate = DateService.getCurrentDate();
						days = fixedDepositForm.getTotalTenureInDays();
						days = days + 1;

						String category = calculationService.geCustomerActualCategory(customer);// fixedDepositForm.getCategory();
						Float rateOfInterest = calculationService.getDepositInterestRate(days, category,
								fixedDepositForm.getCurrency(), fixedDepositForm.getFdAmount(),
								fixedDepositForm.getDepositClassification(), fixedDepositForm.getCitizen(),
								fixedDepositForm.getNriAccountType());

						Float totalTDS = 0f;
						fixedDepositForm.setFdCreditAmount(rateOfInterest);
						fixedDepositForm.setEstimateTDSDeduct(totalTDS);

						Double maturityAmount = fdService.getExpectedMaturityAmount(currentDate, maturityDate,
								fixedDepositForm, rateOfInterest);
						Double totalInterest = maturityAmount
								- (fdService.getFutureDepositDates(fixedDepositForm, maturityDate).size()
										* fixedDepositForm.getFdAmount());
						fixedDepositForm.setEstimateInterest(
								Float.parseFloat(((Double) FDService.round(totalInterest, 2)).toString()));
						fixedDepositForm.setEstimatePayOffAmount(FDService.round(maturityAmount, 2));

						fixedDepositForm.setFlexiInterest(fixedDepositForm.getFlexiInterest());
						fixedDepositForm.setMaturityDate(maturityDate);
						fixedDepositForm.setFdTenureDate(maturityDate);
						fixedDepositForm.setMaturityInstruction(datatype[19].toString());

						fixedDepositForm.setFdInterest(rateOfInterest);
						fixedDepositForm.setEstimateInterest(
								Float.parseFloat(((Double) FDService.round(totalInterest, 2)).toString()));
						fixedDepositForm.setEstimatePayOffAmount(FDService.round(maturityAmount, 2));

						fixedDepositForm.setFdDeductDate(
								fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));

						fixedDepositForm.setCategory(category);

						///////////////////////
						DepositForm dForm = new DepositForm();
						if (datatype[14].toString() != "") {
							dForm.setLinkedAccountNo(datatype[14].toString());
							dForm.setChequeNo(datatype[14].toString());
							dForm.setCardNo(datatype[14].toString());

						}

						dForm.setFdPay(fixedDepositForm.getFdAmount().toString());

						dForm.setChequeDate(DateService.getCurrentDateTime());

						if (datatype[15].toString() != "") {
							dForm.setChequeBank(datatype[15].toString());
							dForm.setCardType(datatype[15].toString());
						}
						if (datatype[16].toString() != "") {
							dForm.setChequeBranch(datatype[16].toString());
						}

						if (datatype[18].toString() != "") {
							dForm.setCvv(Integer.parseInt(datatype[18].toString()));
						}
						if (datatype[16].toString() != "" && datatype[17].toString() != "") {
							dForm.setExpiryDate(datatype[16].toString() + "-" + datatype[17].toString());
						}
						fixedDepositForm.setDepositForm(dForm);

						saveJointDepositFromExcel(fixedDepositForm);
						/////////////////////// block ends
						Row row = datatypeSheet1.createRow(rowNum++);
						int colNum = 0;
						for (Object field : datatype) {

							Cell cell = row.createCell(colNum++);
							if (field instanceof String) {
								cell.setCellValue((String) field);

							} else if (field instanceof Integer) {
								cell.setCellValue((Integer) field);
							}
						}
					}
				}
				FileOutputStream outputStream = new FileOutputStream(
						context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/" + fileName, true);
				workbook1.write(outputStream);
				workbook1.close();
			}

			if (data1[0][0] != null) {
				Workbook workbook1 = new XSSFWorkbook(
						new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/_" + fileName));
				Sheet datatypeSheet1 = workbook1.getSheetAt(0);

				CellStyle style = workbook1.createCellStyle();
				style.setBorderBottom((short) 0x1);
				style.setBottomBorderColor(IndexedColors.RED.getIndex());
				style.setBorderRight((short) 0x1);
				style.setRightBorderColor(IndexedColors.RED.getIndex());
				style.setBorderTop((short) 0x1);
				style.setTopBorderColor(IndexedColors.RED.getIndex());

				int rowNums = datatypeSheet1.getPhysicalNumberOfRows();
				for (int i = 1; i < rowNums; i++) {
					datatypeSheet1.removeRow(datatypeSheet1.getRow(i));
				}
				int rowNum = 1;
				int row1 = 0;
				int column1 = 0;
				for (Object[] datatype : data1) {
					if (datatype[0] != null) {
						Row row = datatypeSheet1.createRow(rowNum++);
						column1 = 0;
						int colNum = 0;
						for (Object field : datatype) {
							Cell cell = row.createCell(colNum++);
							if (errorData[row1][column1] == "1") {
								for (int i = column1; i < 43; i++) {
									errorData[row1][i] = 0;
								}
								cell.setCellStyle(style);
							}
							column1++;
							if (field instanceof String) {
								cell.setCellValue((String) field);

							} else if (field instanceof Integer) {
								cell.setCellValue((Integer) field);
							}
						}
						row1++;
					}
				}
				FileOutputStream outputStream = new FileOutputStream(
						context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/_" + fileName, true);
				workbook1.write(outputStream);
				workbook1.close();

				File file = new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/_" + fileName);
				if (file.exists()) {
					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}
					response.setContentType(mimeType);
					response.setContentLength((int) file.length());
					response.setHeader("Content-Disposition",
							String.format("attachment; filename=\"" + file.getName() + "\""));
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void UpdateJointRecurringExcel(String fileName, Workbook workbook, HttpServletResponse response,
			String currencies) {
		List<ProductConfiguration> productConfiguration = productConfigurationDAO.findAll();
		String[] productCodes = new String[productConfiguration.size()];
		for (int i = 0; i < productConfiguration.size(); i++) {
			productCodes[i] = productConfiguration.get(i).getProductCode();
		}
		List<Customer> cust = customerDAO.findCustomers();
		String[] customers = new String[cust.size()];
		for (int i = 0; i < cust.size(); i++) {
			customers[i] = cust.get(i).getCustomerID();
		}
		String currencyArray[] = currencies.split(",");
		Sheet datatypeSheet = workbook.getSheetAt(0);

		Object[][] errorData = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][46];
		Object[][] data = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][46];
		Object[][] data1 = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][46];
		Object[] tempData = new Object[46];
		Object[] errorTempData = new Object[46];

		int d = 0;
		Boolean add = false;
		int d_ = 0;
		String date_ = "";
		for (int rowNum = 1; rowNum <= datatypeSheet.getLastRowNum(); rowNum++) {
			add = false;
			Row r = datatypeSheet.getRow(rowNum);
			if (r == null) {

				continue;
			} else {
				for (int cn = 0; cn < 46; cn++) {
					Cell currentCell = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (currentCell == null) {
						tempData[cn] = "";
					} else {
						if (cn == 0 || cn == 6 || cn == 7 || cn == 9) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);
						}
						if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
							if (cn == 0) {
								if (ArrayUtils.contains(productCodes, currentCell.getStringCellValue())) {
									add = true;

								} else {
									add = false;

								}
							}
							if (cn == 5 && add == true) {
								if (ArrayUtils.contains(currencyArray, currentCell.getStringCellValue())) {
									add = true;

								} else {
									add = false;

								}
							}
							if ((cn == 1 || cn == 2) && add == true) {
								if (ArrayUtils.contains(customers, currentCell.getStringCellValue())) {
									add = true;

								} else {
									add = false;

								}
							}

						}
						if (cn == 3) {
							date_ = currentCell.getStringCellValue();
						}
						if (add == true && cn == 3) {

							currentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
							if (DateUtil.isValidExcelDate(currentCell.getNumericCellValue())) {
								add = true;

								currentCell.setCellType(Cell.CELL_TYPE_STRING);

							} else {
								add = false;

							}
						}

						// testing done till here
						if ((cn == 4 || cn == 10 || cn == 11 || cn == 12 || cn == 23 || cn == 27 || cn == 35
								|| cn == 39) && add == true) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);

							try {

								if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
									if (!currentCell.getStringCellValue().isEmpty()
											&& currentCell.getStringCellValue() != "") {
										Long.parseLong(currentCell.getStringCellValue());
										add = true;
									} else {
										add = false;
									}
								}

							} catch (NumberFormatException e) {
								add = false;

							} catch (NullPointerException e) {
								add = false;

							} catch (Exception e) {
								add = false;

							}

						}
						if ((cn == 29 || cn == 33) && add == true) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);

							try {
								if (Integer.parseInt(tempData[23].toString()) < 18) {

									if (!currentCell.getStringCellValue().isEmpty()
											&& currentCell.getStringCellValue() != "") {
										Long.parseLong(currentCell.getStringCellValue());
										add = true;
									} else {
										add = false;
									}
								}

							} catch (NumberFormatException e) {
								add = false;

							} catch (NullPointerException e) {
								add = false;

							} catch (Exception e) {
								add = false;

							}

						}
						if ((cn == 41 || cn == 45) && add == true) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);

							try {
								if (Integer.parseInt(tempData[35].toString()) < 18) {

									if (!currentCell.getStringCellValue().isEmpty()
											&& currentCell.getStringCellValue() != "") {
										Long.parseLong(currentCell.getStringCellValue());
										add = true;
									} else {
										add = false;
									}
								}

							} catch (NumberFormatException e) {
								add = false;

							} catch (NullPointerException e) {
								add = false;

							} catch (Exception e) {
								add = false;

							}

						}
						if (cn == 3) {
							tempData[cn] = date_;
						} else if (cn == 9) {

							tempData[cn] = Math.round(Float.parseFloat(currentCell.getStringCellValue()));
						} else {
							if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {

								tempData[cn] = currentCell.getStringCellValue();
							} else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								tempData[cn] = currentCell.getNumericCellValue();
							}
						}
						if (add == true) {
							errorTempData[cn] = "0";
						} else {
							errorTempData[cn] = "1";
						}
					}
				}

				int PnomineeAge = 0;
				if (tempData[23] != null && tempData[23] != "") {
					PnomineeAge = Integer.parseInt((tempData[23]).toString());
				}
				int PgaurdianAge = 0;
				if (tempData[29] != null && tempData[29] != "") {
					PgaurdianAge = Integer.parseInt((tempData[29]).toString());
				}

				if (tempData[23] != null && tempData[23] != "" && PnomineeAge < 18
						&& (PgaurdianAge < 18 || PgaurdianAge == 0 || tempData[28] == null || tempData[30] == null
								|| tempData[31] == null || tempData[32] == null || tempData[33] == null)) {
					add = false;
					errorTempData[28] = "1";
					errorTempData[29] = "1";
					errorTempData[30] = "1";
					errorTempData[31] = "1";
					errorTempData[32] = "1";
					errorTempData[33] = "1";
				}

				int SnomineeAge = 0;
				if (tempData[35] != null && tempData[35] != "") {
					SnomineeAge = Integer.parseInt((tempData[35]).toString());
				}
				int SgaurdianAge = 0;
				if (tempData[41] != null && tempData[41] != "") {
					SgaurdianAge = Integer.parseInt((tempData[41]).toString());
				}

				if (tempData[35] != null && tempData[35] != "" && SnomineeAge < 18
						&& (SgaurdianAge < 18 || SgaurdianAge == 0 || tempData[40] == null || tempData[42] == null
								|| tempData[43] == null || tempData[44] == null || tempData[45] == null)) {
					add = false;
					errorTempData[40] = "1";
					errorTempData[41] = "1";
					errorTempData[42] = "1";
					errorTempData[43] = "1";
					errorTempData[44] = "1";
					errorTempData[45] = "1";
				}

				if (add == true) {

					for (int i = 0; i < 46; i++) {
						data[d][i] = tempData[i];
					}
					d++;
				} else {
					for (int i = 0; i < 46; i++) {
						data1[d_][i] = tempData[i];
						errorData[d_][i] = errorTempData[i];

					}
					d_++;
				}
			}
		}
		try {
			if (data[0][0] != null) {
				Workbook workbook1 = new XSSFWorkbook(
						new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/" + fileName));
				Sheet datatypeSheet1 = workbook1.getSheetAt(0);
				int rowNum = datatypeSheet1.getPhysicalNumberOfRows();
				for (Object[] datatype : data) {
					if (datatype[0] != null) {

						//////////////////////// block starts save data from excel to db
						FixedDepositForm fixedDepositForm = new FixedDepositForm();
						// Date currentDate = DateService.getCurrentDate();
						// testing from here pending
						fixedDepositForm.setDepositClassification(Constants.recurringDeposit);
						fixedDepositForm.setUserContribution(Float.parseFloat(datatype[6].toString()));
						Integer months_ = datatype[11].toString().equals("") ? 0
								: Integer.parseInt(datatype[11].toString());
						Integer days_ = datatype[12].toString().equals("") ? 0
								: Integer.parseInt(datatype[12].toString());
						Integer years_ = datatype[10].toString().equals("") ? 0
								: Integer.parseInt(datatype[10].toString());

						Date createdDate = new SimpleDateFormat("dd/MM/yyyy").parse(datatype[3].toString()); // It will
																												// be
																												// depositDate
																												// from
																												// Excel
						fixedDepositForm.setDepositDate(createdDate);
						fixedDepositForm.setDepositCreationDate(DateService.getCurrentDate());
						Date maturityDate = DateService.addYear(createdDate, years_);
						maturityDate = DateService.addMonths(maturityDate, months_);
						maturityDate = DateService.addDays(maturityDate, days_);
						Integer totalDays = DateService.getDaysBetweenTwoDates(createdDate, maturityDate) - 1;

						// Integer totalDays=years_*365+months_*30+days_;
						fixedDepositForm.setTenureInMonths(months_);
						fixedDepositForm.setTenureInYears(years_);
						fixedDepositForm.setTenureInDays(days_);
						fixedDepositForm.setTotalTenureInDays(totalDays);

						Customer customer = customerDAO.getAge(datatype[1].toString());
						fixedDepositForm.setCustomer(customer);
						fixedDepositForm.setCustomerID(datatype[1].toString());
						fixedDepositForm.setContactNum(customer.getContactNum());
						fixedDepositForm.setEmail(customer.getEmail());
						fixedDepositForm.setCitizen(customer.getCitizen());
						if (fixedDepositForm.getCitizen().equals(Constants.NRI)) {
							fixedDepositForm.setNriAccountType(customer.getNriAccountType());
						}
						fixedDepositForm.setCategory(customer.getCategory());

						fixedDepositForm.setFdAmount(Double.parseDouble(datatype[4].toString()));
						fixedDepositForm.setCurrency(datatype[5].toString());
						fixedDepositForm.setFdDeductDate(createdDate);
						fixedDepositForm.setBranchCode(datatype[13].toString());
						fixedDepositForm.setDepositArea(datatype[14].toString());
						fixedDepositForm.setProductConfigurationId(Long.parseLong(datatype[0].toString()));
						fixedDepositForm.setPaymentMode(datatype[15].toString());
						fixedDepositForm.setDepositType(Constants.JOINT);

						List<AccountDetails> accDetail = accountDetailsDAO.findCurrentSavingByCustId(customer.getId());
						fixedDepositForm.setAccountNo(accDetail.get(0).getAccountNo());

						fixedDepositForm.setNomineeAge(datatype[23].toString());
						fixedDepositForm.setNomineeName(datatype[22].toString());
						fixedDepositForm.setNomineeAddress(datatype[24].toString());
						fixedDepositForm.setNomineeRelationShip(datatype[25].toString());
						fixedDepositForm.setNomineePan(datatype[26].toString());
						fixedDepositForm.setNomineeAadhar(Long.parseLong(datatype[27].toString()));

						if (Integer.parseInt(datatype[23].toString()) < 18) {
							fixedDepositForm.setGuardianAge(datatype[29].toString());
							fixedDepositForm.setGuardianName(datatype[28].toString());
							fixedDepositForm.setGuardianAddress(datatype[30].toString());
							fixedDepositForm.setGuardianRelationShip(datatype[31].toString());
							fixedDepositForm.setGaurdianPan(datatype[32].toString());
							fixedDepositForm.setGaurdianAadhar(Long.parseLong(datatype[33].toString()));
						}
						fixedDepositForm.setDepositAccountType(Constants.JOINT);
						fixedDepositForm.setPaymentType(datatype[8].toString());
						fixedDepositForm.setDeductionDay(Math.round(Float.parseFloat(datatype[9].toString())));
						fixedDepositForm = fdService.saveRates(fixedDepositForm);

						fixedDepositForm.setTaxSavingDeposit("0");
						List<JointAccount> accounts = new ArrayList<JointAccount>();
						Customer sCustomer = customerDAO.getAge(datatype[2].toString());

						JointAccount account = new JointAccount();
						account.setId(sCustomer.getId());
						account.setName(sCustomer.getCustomerName());
						account.setGender(sCustomer.getGender());
						account.setAge(sCustomer.getAge());
						account.setContactNo(sCustomer.getContactNum());
						account.setEmail(sCustomer.getEmail());
						account.setAddress(sCustomer.getAddress());
						account.setCity(sCustomer.getCity());
						account.setState(sCustomer.getState());
						account.setCountry(sCustomer.getCountry());
						account.setPincode(sCustomer.getPincode());
						account.setDepositType(Constants.JOINT);
						account.setContributionPercent(Float.parseFloat(datatype[7].toString()));
						JointConsortiumNominees nominee = new JointConsortiumNominees();
						nominee.setAadharNo(Long.parseLong(datatype[39].toString()));
						nominee.setPanNo(datatype[38].toString());
						nominee.setRelationship(datatype[37].toString());
						nominee.setAge(Integer.parseInt(datatype[35].toString()));
						nominee.setName(datatype[34].toString());
						nominee.setAddress(datatype[36].toString());
						if (Integer.parseInt(datatype[35].toString()) < 18) {
							nominee.setGaurdianAadharNo(Long.parseLong(datatype[45].toString()));
							nominee.setGaurdianPanNo(datatype[44].toString());
							nominee.setGaurdianRelation(datatype[43].toString());
							nominee.setGaurdianAge(Integer.parseInt(datatype[41].toString()));
							nominee.setGaurdianName(datatype[40].toString());
							nominee.setGaurdianAddress(datatype[42].toString());
						}
						account.setNominee(nominee);

						accounts.add(account);

						fixedDepositForm.setJointAccounts(accounts);

						if (fixedDepositForm.getNomineeName() != "" && fixedDepositForm.getNomineeName() != null) {
							for (int i = 0; i < fixedDepositForm.getJointAccounts().size(); i++) {

								/***** Saving DepositHolderNominee *****/

								DepositHolderNominees nomineeJoint = new DepositHolderNominees();
								/*
								 * getJointcustomerByPanCard = customerDAO
								 * .getNomineeByPanCard(fixedDepositForm.getJointAccounts().get(i).getNominee().
								 * getPanNo()); getJointcustomerByAadharCard = customerDAO
								 * .getNomineeByAadharCard(fixedDepositForm.getJointAccounts().get(i).getNominee
								 * ().getAadharNo());
								 */
								nomineeJoint.setNomineePan(fixedDepositForm.getJointAccounts().get(i).getNominee()
										.getPanNo().toUpperCase());
								nomineeJoint.setNomineeAadhar(
										fixedDepositForm.getJointAccounts().get(i).getNominee().getAadharNo());

								int ageJoint = Integer.parseInt(
										fixedDepositForm.getJointAccounts().get(i).getNominee().getAge().toString());
								if (ageJoint < 18) {

									nomineeJoint.setGaurdianPan(fixedDepositForm.getJointAccounts().get(i).getNominee()
											.getGaurdianPanNo().toUpperCase());
									nomineeJoint.setGaurdianAadhar(fixedDepositForm.getJointAccounts().get(i)
											.getNominee().getGaurdianAadharNo());
								}

							}
						}
						Integer days = 0;
						Date currentDate = DateService.getCurrentDate();
						days = fixedDepositForm.getTotalTenureInDays();
						days = days + 1;

						String category = calculationService.geCustomerActualCategory(customer);// fixedDepositForm.getCategory();
						Float rateOfInterest = calculationService.getDepositInterestRate(days, category,
								fixedDepositForm.getCurrency(), fixedDepositForm.getFdAmount(),
								fixedDepositForm.getDepositClassification(), fixedDepositForm.getCitizen(),
								fixedDepositForm.getNriAccountType());

						Float totalTDS = 0f;
						fixedDepositForm.setFdCreditAmount(rateOfInterest);
						fixedDepositForm.setEstimateTDSDeduct(totalTDS);

						Double maturityAmount = fdService.getExpectedMaturityAmount(currentDate, maturityDate,
								fixedDepositForm, rateOfInterest);
						Double totalInterest = maturityAmount
								- (fdService.getFutureDepositDates(fixedDepositForm, maturityDate).size()
										* fixedDepositForm.getFdAmount());
						fixedDepositForm.setEstimateInterest(
								Float.parseFloat(((Double) FDService.round(totalInterest, 2)).toString()));
						fixedDepositForm.setEstimatePayOffAmount(FDService.round(maturityAmount, 2));

						fixedDepositForm.setFlexiInterest(fixedDepositForm.getFlexiInterest());
						fixedDepositForm.setMaturityDate(maturityDate);
						fixedDepositForm.setFdTenureDate(maturityDate);
						fixedDepositForm.setMaturityInstruction(datatype[21].toString());

						fixedDepositForm.setFdInterest(rateOfInterest);
						fixedDepositForm.setEstimateInterest(
								Float.parseFloat(((Double) FDService.round(totalInterest, 2)).toString()));
						fixedDepositForm.setEstimatePayOffAmount(FDService.round(maturityAmount, 2));

						fixedDepositForm.setFdDeductDate(
								fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));

						fixedDepositForm.setCategory(category);

						///////////////////////
						DepositForm dForm = new DepositForm();
						if (datatype[16].toString() != "") {
							dForm.setLinkedAccountNo(datatype[16].toString());
							dForm.setChequeNo(datatype[16].toString());
							dForm.setCardNo(datatype[16].toString());

						}

						dForm.setFdPay(fixedDepositForm.getFdAmount().toString());

						dForm.setChequeDate(DateService.getCurrentDateTime());

						if (datatype[17].toString() != "") {
							dForm.setChequeBank(datatype[17].toString());
							dForm.setCardType(datatype[17].toString());
						}
						if (datatype[18].toString() != "") {
							dForm.setChequeBranch(datatype[18].toString());
						}

						if (datatype[20].toString() != "") {
							dForm.setCvv(Integer.parseInt(datatype[20].toString()));
						}
						if (datatype[18].toString() != "" && datatype[19].toString() != "") {
							dForm.setExpiryDate(datatype[18].toString() + "-" + datatype[19].toString());
						}
						fixedDepositForm.setDepositForm(dForm);

						saveJointDepositFromExcel(fixedDepositForm);
						/////////////////////// block ends
						Row row = datatypeSheet1.createRow(rowNum++);
						int colNum = 0;
						for (Object field : datatype) {

							Cell cell = row.createCell(colNum++);
							if (field instanceof String) {
								cell.setCellValue((String) field);

							} else if (field instanceof Integer) {
								cell.setCellValue((Integer) field);
							}
						}
					}
				}
				FileOutputStream outputStream = new FileOutputStream(
						context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/" + fileName, true);
				workbook1.write(outputStream);
				workbook1.close();
			}

			if (data1[0][0] != null) {
				Workbook workbook1 = new XSSFWorkbook(
						new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/_" + fileName));
				Sheet datatypeSheet1 = workbook1.getSheetAt(0);

				CellStyle style = workbook1.createCellStyle();
				style.setBorderBottom((short) 0x1);
				style.setBottomBorderColor(IndexedColors.RED.getIndex());
				style.setBorderRight((short) 0x1);
				style.setRightBorderColor(IndexedColors.RED.getIndex());
				style.setBorderTop((short) 0x1);
				style.setTopBorderColor(IndexedColors.RED.getIndex());

				int rowNums = datatypeSheet1.getPhysicalNumberOfRows();
				for (int i = 1; i < rowNums; i++) {
					datatypeSheet1.removeRow(datatypeSheet1.getRow(i));
				}
				int rowNum = 1;
				int row1 = 0;
				int column1 = 0;
				for (Object[] datatype : data1) {
					if (datatype[0] != null) {
						Row row = datatypeSheet1.createRow(rowNum++);
						column1 = 0;
						int colNum = 0;
						for (Object field : datatype) {
							Cell cell = row.createCell(colNum++);
							if (errorData[row1][column1] == "1") {
								for (int i = column1; i < 45; i++) {
									errorData[row1][i] = 0;
								}
								cell.setCellStyle(style);
							}
							column1++;
							if (field instanceof String) {
								cell.setCellValue((String) field);

							} else if (field instanceof Integer) {
								cell.setCellValue((Integer) field);
							}
						}
						row1++;
					}
				}
				FileOutputStream outputStream = new FileOutputStream(
						context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/_" + fileName, true);
				workbook1.write(outputStream);
				workbook1.close();

				File file = new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel" + "/_" + fileName);
				if (file.exists()) {
					String mimeType = URLConnection.guessContentTypeFromName(file.getName());
					if (mimeType == null) {
						mimeType = "application/octet-stream";
					}
					response.setContentType(mimeType);
					response.setContentLength((int) file.length());
					response.setHeader("Content-Disposition",
							String.format("attachment; filename=\"" + file.getName() + "\""));
					InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	public void saveJointDepositFromExcel(@ModelAttribute FixedDepositForm fixedDepositForm) throws CustomException {

		/******** Saving Deposit ******/
		String transactionId = fdService.generateRandomString();
		Deposit deposit = new Deposit();
		int days = fixedDepositForm.getDaysValue() != null ? fixedDepositForm.getDaysValue() : 0;
		Customer customer = customerDAO.getAge(fixedDepositForm.getCustomerID());
		deposit.setAccountNumber(fdService.generateRandomString());
		deposit.setDepositAccountType(fixedDepositForm.getDepositAccountType());

		deposit.setDepositAmount(fixedDepositForm.getFdAmount());
		deposit.setDepositArea(fixedDepositForm.getDepositArea());
		deposit.setDepositType(fixedDepositForm.getDepositType());
		// deposit.setDueDate(fdService.calculateDueDate(fixedDepositForm));
		deposit.setDueDate(fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));
		deposit.setFlexiRate("Yes");
		deposit.setInterestRate(fixedDepositForm.getFdCreditAmount());
		deposit.setModifiedInterestRate(fixedDepositForm.getFdCreditAmount());
		if (fixedDepositForm.getAccountNo() != null && !fixedDepositForm.getAccountNo().equals("")) {
			String accArray[] = fixedDepositForm.getAccountNo().split(",");
			deposit.setLinkedAccountNumber(accArray[0]);
		}

		deposit.setMaturityAmount(fixedDepositForm.getEstimatePayOffAmount());
		deposit.setNewMaturityAmount(fixedDepositForm.getEstimatePayOffAmount());
		deposit.setCurrentBalance(Double.valueOf(deposit.getDepositAmount()));
		deposit.setDepositCurrency(fixedDepositForm.getCurrency());
		deposit.setPaymentMode(fixedDepositForm.getPaymentMode());
		Date date = DateService.getCurrentDateTime();
		deposit.setPaymentType(fixedDepositForm.getPaymentType());
		deposit.setPayOffInterestType(fixedDepositForm.getPayOffInterestType());
		deposit.setStatus(Constants.OPEN);
		deposit = fdService.getMaturityAndTenureInformation(deposit, fixedDepositForm);

		deposit.setCreatedDate(fixedDepositForm.getDepositDate());
		deposit.setDepositCreationDate(date);
		deposit.setCreatedFromBulk(true);
		deposit.setProductConfigurationId(fixedDepositForm.getProductConfigurationId());
		deposit.setModifiedDate(fixedDepositForm.getDepositDate());
		deposit.setApprovalStatus(Constants.PENDING);
		deposit.setDepositClassification(fixedDepositForm.getDepositClassification());
		deposit.setTaxSavingDeposit(fixedDepositForm.getTaxSavingDeposit());

		deposit.setPrimaryCitizen(fixedDepositForm.getCitizen());
		deposit.setPrimaryCustomerCategory(fixedDepositForm.getCategory());
		if (fixedDepositForm.getCitizen().equalsIgnoreCase(Constants.NRI)) {

			deposit.setNriAccountType(fixedDepositForm.getNriAccountType());
			deposit.setPrimaryNRIAccountType(fixedDepositForm.getNriAccountType());
		}

		/******* / PAY OFF DATE CALCULATION / *******/

		if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {
			deposit.setPayOffDueDate(fixedDepositForm.getPayoffDate());

		}
		EndUser user = getCurrentLoggedUserDetails();

		deposit.setTransactionId(transactionId);
		deposit.setCreatedBy(user.getUserName());
		if (deposit.getPaymentMode().equalsIgnoreCase("DD") || deposit.getPaymentMode().equalsIgnoreCase("Cheque")) {
			deposit.setClearanceStatus(Constants.WAITINGFORCLEARANCE);
		}
		if (fixedDepositForm.getNriAccountType() != null) {
			deposit.setNriAccountType(fixedDepositForm.getNriAccountType());
		}
		deposit.setMaturityInstruction(fixedDepositForm.getMaturityInstruction());
		deposit.setDeductionDay(fixedDepositForm.getDeductionDay());
		deposit.setBranchCode(fixedDepositForm.getBranchCode());
		Deposit newDeposit = fixedDepositDAO.saveFD(deposit);
//testing done till here
		List<DepositHolder> depositHolderList = new ArrayList<>();
		/******* Saving DepositHolder *******/

		Integer isContributionRequired = 0;
		ProductConfiguration productConfiguration = productConfigurationDAO
				.findByProductCode(fixedDepositForm.getProductConfigurationId().toString());
		isContributionRequired = productConfiguration.getContributionRequiredForJointAccHolders() != null
				? productConfiguration.getContributionRequiredForJointAccHolders()
				: 0;

		Long depositId = newDeposit.getId();
		DepositHolder depositHolder = new DepositHolder();
		if (isContributionRequired == 1)
			depositHolder.setContribution(fixedDepositForm.getUserContribution());
		else
			depositHolder.setContribution(0f);

		depositHolder.setCustomerId(customer.getId());
		depositHolder.setDepositHolderStatus(Constants.PRIMARY);
		depositHolder.setDepositHolderCategory(fixedDepositForm.getCategory());
		depositHolder.setDepositId(depositId);
		depositHolder.setCitizen(customer.getCitizen());
		if (customer.getCitizen().equalsIgnoreCase(Constants.NRI)) {
			depositHolder.setNriAccountType(customer.getNriAccountType());
		}

		if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {

			depositHolder.setInterestType(fixedDepositForm.getInterstPayType());

			if (fixedDepositForm.getInterstPayType().equals("PERCENT")) {
				depositHolder.setInterestPercent(fixedDepositForm.getInterestPercent());
			} else {
				depositHolder.setInterestAmt(fixedDepositForm.getInterestPayAmount());
			}

			depositHolder.setPayOffAccountType(fixedDepositForm.getFdPayOffAccount());
			depositHolder.setNameOnBankAccount(fixedDepositForm.getOtherName());
			depositHolder.setAccountNumber(fixedDepositForm.getOtherAccount().toString());

			if (fixedDepositForm.getFdPayOffAccount().equals("Other")) {
				depositHolder.setTransferType(fixedDepositForm.getOtherPayTransfer());
				depositHolder.setBankName(fixedDepositForm.getOtherBank());
				depositHolder.setIfscCode(fixedDepositForm.getOtherIFSC());
			}
		}
		DepositHolder depositHolderNew = depositHolderDAO.saveDepositHolder(depositHolder);
		depositHolderList.add(depositHolderNew);
		// Creating a HashSet for holding depositHolder reference in Deposit
		// class

		Set<DepositHolder> set = new HashSet<DepositHolder>();
		set.add(depositHolder);

		for (int i = 0; i < fixedDepositForm.getJointAccounts().size(); i++) {

			/******* Saving DepositHolder *******/

			DepositHolder depositHolderJoint = new DepositHolder();
			depositHolderJoint.setRelationship(fixedDepositForm.getJointAccounts().get(i).getRelationship());

			if (isContributionRequired == 1)
				depositHolderJoint.setContribution(fixedDepositForm.getJointAccounts().get(i).getContributionPercent());
			else
				depositHolderJoint.setContribution(0f);

			depositHolderJoint.setCustomerId(fixedDepositForm.getJointAccounts().get(i).getId());

			Customer secondaryCustomer = customerDAO.getById(fixedDepositForm.getJointAccounts().get(i).getId());
			depositHolderJoint.setCitizen(secondaryCustomer.getCitizen());
			if (secondaryCustomer.getCitizen().equalsIgnoreCase(Constants.NRI)) {
				depositHolderJoint.setNriAccountType(secondaryCustomer.getNriAccountType());
			}

			String depositHolderCategory = secondaryCustomer.getCategory();
			depositHolderJoint.setDepositHolderStatus(Constants.SECONDARY);
			depositHolderJoint.setDepositId(depositId);
			depositHolderJoint.setDepositHolderCategory(depositHolderCategory);

			if (fixedDepositForm.getPayOffInterestType() != null
					&& !fixedDepositForm.getPayOffInterestType().equals("")) {

				depositHolderJoint.setInterestType(
						fixedDepositForm.getJointAccounts().get(i).getContributions().getPaymentType());

				if (fixedDepositForm.getJointAccounts().get(i).getContributions().getPaymentType().equals("PERCENT")) {
					depositHolderJoint.setInterestPercent(
							fixedDepositForm.getJointAccounts().get(i).getContributions().getInterestPercentage());
				}

				else {
					depositHolderJoint.setInterestAmt(
							fixedDepositForm.getJointAccounts().get(i).getContributions().getInterestAmtPart());
				}

				depositHolderJoint.setPayOffAccountType(
						fixedDepositForm.getJointAccounts().get(i).getContributions().getPayOffAccPartOne());

				depositHolderJoint.setNameOnBankAccount(
						fixedDepositForm.getJointAccounts().get(i).getContributions().getBeneficiaryOne());
				depositHolderJoint.setAccountNumber(fixedDepositForm.getJointAccounts().get(i).getContributions()
						.getBeneficiaryAccOne().toString());

				if (depositHolderJoint.getPayOffAccountType().equals("Other")) {
					depositHolderJoint.setTransferType(
							fixedDepositForm.getJointAccounts().get(i).getContributions().getTransferModeOne());
					depositHolderJoint.setBankName(
							fixedDepositForm.getJointAccounts().get(i).getContributions().getBeneficiaryBankOne());
					depositHolderJoint.setIfscCode(
							fixedDepositForm.getJointAccounts().get(i).getContributions().getBeneficiaryIFSCOne());
				}
			}
			DepositHolder depositHolderJointNew = depositHolderDAO.saveDepositHolder(depositHolderJoint);

			depositHolderList.add(depositHolderJointNew);

			// add the deposit holder to the set else it will not update deposit id in
			// deposit holder table
			set.add(depositHolderJointNew);

			/******* Saving DepositHolderNominee *******/

			if (fixedDepositForm.getJointAccounts().get(i).getNominee() != null
					&& fixedDepositForm.getJointAccounts().get(i).getNominee().getName() != null
					&& fixedDepositForm.getJointAccounts().get(i).getNominee().getName() != "") {
				DepositHolderNominees nomineeJoint = new DepositHolderNominees();
				nomineeJoint.setNomineeName(fixedDepositForm.getJointAccounts().get(i).getNominee().getName());
				nomineeJoint.setNomineeAge(fixedDepositForm.getJointAccounts().get(i).getNominee().getAge().toString());
				nomineeJoint.setNomineeRelationship(
						fixedDepositForm.getJointAccounts().get(i).getNominee().getRelationship());
				nomineeJoint.setNomineeAddress(fixedDepositForm.getJointAccounts().get(i).getNominee().getAddress());
				nomineeJoint.setNomineePan(
						fixedDepositForm.getJointAccounts().get(i).getNominee().getPanNo().toUpperCase());
				nomineeJoint.setNomineeAadhar(fixedDepositForm.getJointAccounts().get(i).getNominee().getAadharNo());

				nomineeJoint.setDepositHolderId(depositHolderJointNew.getId());

				int ageJoint = Integer
						.parseInt(fixedDepositForm.getJointAccounts().get(i).getNominee().getAge().toString());
				if (ageJoint < 18) {
					nomineeJoint
							.setGaurdianName(fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianName());
					nomineeJoint.setGaurdianAge(
							Float.valueOf(fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianAge()));
					nomineeJoint.setGaurdianAddress(
							fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianAddress());
					nomineeJoint.setGaurdianRelation(
							fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianRelation());
					nomineeJoint.setGaurdianPan(
							fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianPanNo().toUpperCase());
					nomineeJoint.setGaurdianAadhar(
							fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianAadharNo());
				}

				nomineeDAO.saveNominee(nomineeJoint);
			}

		}

		newDeposit.setDepositHolder(set);
		newDeposit.setProductConfigurationId(fixedDepositForm.getProductConfigurationId());
		fixedDepositDAO.updateFD(newDeposit);

		// add the hasset to the newly created deposit

		/******* Saving DepositHolderNominee *******/

		if (fixedDepositForm.getNomineeName() != null && fixedDepositForm.getNomineeName() != "") {
			DepositHolderNominees nominee = new DepositHolderNominees();

			nominee.setNomineeName(fixedDepositForm.getNomineeName());
			nominee.setNomineeAge(fixedDepositForm.getNomineeAge());
			nominee.setNomineeRelationship(fixedDepositForm.getNomineeRelationShip());
			nominee.setNomineeAddress(fixedDepositForm.getNomineeAddress());
			nominee.setDepositHolderId(depositHolderNew.getId());
			nominee.setNomineeAadhar(fixedDepositForm.getNomineeAadhar());
			nominee.setNomineePan(fixedDepositForm.getNomineePan().toUpperCase());
			int age = Integer.parseInt(fixedDepositForm.getNomineeAge());
			if (age < 18) {
				nominee.setGaurdianName(fixedDepositForm.getGuardianName());
				nominee.setNomineeAge(fixedDepositForm.getNomineeAge());
				nominee.setGaurdianAddress(fixedDepositForm.getGuardianAddress());
				nominee.setGaurdianRelation(fixedDepositForm.getGuardianRelationShip());
				nominee.setGaurdianAadhar(fixedDepositForm.getGaurdianAadhar());
				nominee.setGaurdianPan(fixedDepositForm.getGaurdianPan().toUpperCase());
			}
			nomineeDAO.saveNominee(nominee);
		}

		/******* Deduction from Linked account *******/
		AccountDetails accountDetails = null;
		if (fixedDepositForm.getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {
			accountDetails = accountDetailsDAO.findByAccountNo(fixedDepositForm.getDepositForm().getLinkedAccountNo());
			accountDetails.setAccountBalance(
					accountDetails.getAccountBalance() - Float.valueOf(fixedDepositForm.getDepositForm().getFdPay()));
			accountDetailsDAO.updateUserAccountDetails(accountDetails);
		}

		/******* SAVING PAYMENT INFO *******/

		String fromAccountNo = "";
		String fromAccountType = "";

		Payment payment = new Payment();
		payment.setDepositId(depositId);

		Double amountPaid = FDService.round(Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()), 2);
		payment.setAmountPaid(amountPaid);

		if (fixedDepositForm.getPaymentMode().contains("Cash") || fixedDepositForm.getPaymentMode().contains("cash")) {
			fromAccountNo = "";
			fromAccountType = "Cash Account";
		}

		if (fixedDepositForm.getPaymentMode().equalsIgnoreCase("DD")
				|| fixedDepositForm.getPaymentMode().equalsIgnoreCase("Cheque")) {

			payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
			payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
			payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
			payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());

			// For Accounting Entries Only
			fromAccountNo = fixedDepositForm.getDepositForm().getChequeNo();
			fromAccountType = fixedDepositForm.getPaymentMode();
		}

		if (fixedDepositForm.getPaymentMode().equalsIgnoreCase("Card Payment")) {

			String[] cardNoArray = fixedDepositForm.getDepositForm().getCardNo().split("-");
			String cardNo = "";
			for (int i = 0; i < cardNoArray.length; i++) {
				cardNo = cardNo + cardNoArray[i];
			}
			payment.setCardNo(Long.valueOf(cardNo));
			payment.setCardType(fixedDepositForm.getDepositForm().getCardType());

			payment.setCardCvv(fixedDepositForm.getDepositForm().getCvv());
			payment.setCardExpiryDate(fixedDepositForm.getDepositForm().getExpiryDate());

			// For Accounting Entries Only
			fromAccountNo = cardNo;
			fromAccountType = fixedDepositForm.getDepositForm().getCardType();

		}

		if (fixedDepositForm.getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {

			payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getAccountType());
			String accountNo = fixedDepositForm.getAccountNo();
			if (accountNo.contains(",")) {
				accountNo = accountNo.substring(0, accountNo.indexOf(","));
			}
			payment.setLinkedAccNoForFundTransfer(accountNo);

			if (accountDetails != null)
				payment.setLinkedAccTypeForFundTransfer(accountDetails.getAccountType());

			// For Accounting Entries Only
			fromAccountNo = accountNo;
			fromAccountType = accountDetails.getAccountType();
		}

		payment.setDepositHolderId(depositHolderNew.getId());
		//payment.setModeOfPaymentId(fixedDepositForm.getDepositForm().getPaymentMode());
		payment.setPaymentDate(date);
		payment.setTransactionId(transactionId);
		payment.setCreatedBy(getCurrentLoggedUserName());
		payment = paymentDAO.insertPayment(payment);

		// Insert in Distribution and holderWiseDistribution
		calculationService.insertInPaymentDistribution(newDeposit, depositHolderList, amountPaid, payment.getId(),
				Constants.PAYMENT, null, null, null, null, null, null, null);

		// Insert in DepositSummary and DepositHolderWiseSummary
		DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.PAYMENT,
				amountPaid, null, null, null, null, depositHolderList, null, null, null);

		// Insert in Journal & Ledger
		// --------------------------------------------------------------------
		if (fixedDepositForm.getPaymentMode().contains("Cash"))
			fromAccountType = "Cash";
		

		ledgerService.insertJournal(newDeposit.getId(), customer.getId(), DateService.getCurrentDate(),
				fromAccountNo, newDeposit.getAccountNumber(), Event.OPEN_DEPOSIT.getValue(),
				amountPaid, Long.parseLong(fixedDepositForm.getDepositForm().getPaymentMode()), depositSummary.getTotalPrincipal(), transactionId);
//
//		ledgerService.insertJournalLedger(newDeposit.getId(), customer.getId(), DateService.getCurrentDate(),
//				fromAccountNo, fromAccountType, newDeposit.getAccountNumber(), "Deposit Account", "Payment", amountPaid,
//				fixedDepositForm.getPaymentMode(), depositSummary.getTotalPrincipal());
		// --------------------------------------------------------------------

	}

	public Date calculateDueDate(FixedDepositForm fixedDepositForm, Integer deductionDay) {

		Date dueDate = fixedDepositForm.getDepositDate();
		if (dueDate == null) {
			dueDate = DateService.getCurrentDate();
		}
		if (fixedDepositForm.getDeductionDay() != null)
			dueDate = DateService.setDate(dueDate, fixedDepositForm.getDeductionDay());

		int monthsToAdd = 0;
		if (fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.MONTHLY)) {
			monthsToAdd = 1;
		} else if (fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.QUARTERLY)) {
			monthsToAdd = 3;
		} else if (fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.FULLYEARLY)
				|| fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.ANNUALLY))
			monthsToAdd = 12;
		else if (fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.HALFYEARLY))
			monthsToAdd = 6;

		dueDate = DateService.addDays(DateService.generateMonthsDate(dueDate, monthsToAdd), 1);
		if (fixedDepositForm.getDeductionDay() != null)
			dueDate = DateService.setDate(dueDate, fixedDepositForm.getDeductionDay());

		if (DateService.getDaysBetweenTwoDates(dueDate, fixedDepositForm.getMaturityDate()) > 0)
			return dueDate;
		else
			return fixedDepositForm.getMaturityDate();

//		
//		
//		
//		
//		
//		//--------------------------------------------
//		Date dueDate = DateService.getCurrentDateTime();
//
//		if (fixedDepositForm.getPaymentType().equalsIgnoreCase("One-Time")
//				|| fixedDepositForm.getPaymentType().equalsIgnoreCase("One Time")) {
//			// dueDate will be the same date of deposit
//		} else if (fixedDepositForm.getPaymentType().equalsIgnoreCase("Monthly")) {
//			dueDate = DateService.addMonths(dueDate,1);
//			//dueDate = DateService.generateMonthsDate(dueDate, 1);
//			dueDate = DateService.setDate(dueDate, deductionDay);
//
//		} else if (fixedDepositForm.getPaymentType().equalsIgnoreCase("Quarterly")) {
//
//			/*
//			 * dueDate = DateService.generateMonthsDate(dueDate, 3); dueDate =
//			 * DateService.setDate(dueDate, day);
//			 */
//
//			dueDate = DateService.getQuaterlyDeductionDate(deductionDay);
//
//		} else if (fixedDepositForm.getPaymentType().equalsIgnoreCase("Half Yearly")) {
//			/*
//			 * dueDate = DateService.generateMonthsDate(dueDate, 6); dueDate =
//			 * DateService.setDate(dueDate, day);
//			 */
//
//			dueDate = DateService.getHalfYearlyDeductionDate(deductionDay);
//
//		} else if (fixedDepositForm.getPaymentType().equalsIgnoreCase("Annually")) {
//			/*
//			 * dueDate = DateService.generateMonthsDate(dueDate, 12); dueDate =
//			 * DateService.setDate(dueDate, day);
//			 */
//
//			dueDate = DateService.getAnnualDeductionDate(deductionDay);
//		}
//		return dueDate;
	}

	@RequestMapping(value = "/bulkDeposit", method = RequestMethod.GET)
	public ModelAndView bulkDeposit(ModelMap model) throws CustomException {
		File folder = new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel/Downloads");
		File folder1 = new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel");
		if (folder1.exists()) {
			File[] listOfFiles = folder.listFiles();
			Integer length = listOfFiles.length;
			String[] files = new String[length];
			for (int i = 0; i < length; i++) {
				if (listOfFiles[i].isFile()) {
					files[i] = listOfFiles[i].getName();
				}
			}
			model.put("files", files);
		} else {
			folder1.mkdir();
			folder.mkdir();

			model.put("files", null);
		}

		// model.put("files", files);
		return new ModelAndView("bulkDeposit", "model", model);

	}

	@RequestMapping(value = "/downloadExcel", method = RequestMethod.POST)
	public void downloadExcel(String filename, HttpServletResponse response) throws Exception, CustomException {

		if (filename != null && !filename.equals("")) {
			File file = new File(context.getRealPath("/") + "/resources/Dowload/DepositExcel/Downloads/" + filename);
			if (file.exists()) {
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				response.setContentLength((int) file.length());
				response.setHeader("Content-Disposition",
						String.format("attachment; filename=\"" + file.getName() + "\""));
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				FileCopyUtils.copy(inputStream, response.getOutputStream());

			}

		}

	}

	public static boolean isValidDate(String inDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

	// -------------------------------------------------------------------

	@RequestMapping(value = "/bankPaymentOnMaturity", method = RequestMethod.GET)
	public ModelAndView bankPaymentOnMaturity(Model model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {

		model.addAttribute("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("bankPaymentOnMaturity", "model", model);

	}

	@RequestMapping(value = "/findClosedDeposit", method = RequestMethod.POST)
	public ModelAndView findCloseAccountBankPaymentOnMaturity(Model model,
			@ModelAttribute FixedDepositForm fixedDepositForm) throws CustomException {

		Deposit deposit = fixedDepositDAO.getByAccountNumberAndStatusClose(fixedDepositForm.getAccountNo().trim());
		
		

		if (deposit == null) {
			model.addAttribute("error", "Account Number is not correct OR this Deposit is not matured.");
			return new ModelAndView("bankPaymentOnMaturity", "model", model);
		}
		List<DepositHolderForm> depositHolderList = depositHolderDAO.getUnpaidDepositHolders(deposit.getId());
		if (depositHolderList.size() == 0) {
			model.addAttribute("error", "Maturity amount has already been paid");
			return new ModelAndView("bankPaymentOnMaturity", "model", model);
		}
		ProductConfiguration product = productConfigurationDAO.findById(deposit.getProductConfigurationId());
		if (product != null) {
			if (!(product.getPaymentModeOnMaturity() != "Fund Tranfer" || product.getPaymentModeOnMaturity() == null
					|| product.getPaymentModeOnMaturity() == "")) {

				model.addAttribute("error", "Account Number is not correct OR this Deposit is not matured");
				return new ModelAndView("bankPaymentOnMaturity", "model", model);
			} else {

				model.addAttribute("deposit", deposit);
				model.addAttribute("depositHolders", depositHolderList);
				model.addAttribute("productCode", product.getProductCode());
				model.addAttribute("paymentModeMaturity", product.getPaymentModeOnMaturity());

			}
		}

		return new ModelAndView("bankPaymentOnMaturity", "model", model);

	}


	/*@RequestMapping(value = "/saveBankPaymentMaturityPaidDetails", method = RequestMethod.POST)
	public ModelAndView saveBankPaymentMaturityPaidDetails(Model model,
			@ModelAttribute FixedDepositForm fixedDepositForm, RedirectAttributes attributes) {

		try {

			Deposit deposit = fixedDepositDAO.getDeposit(fixedDepositForm.getDepositId());
			Double amount = 0d;
			BankPaymentDetails bankPaymentDetails = bankPaymentDAO.findByDepositId(deposit.getId());
			BankPaid bankPaid = calculationService.insertInBankPaid(deposit.getId(), bankPaymentDetails.getId(), amount,
					"PAID");

			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
			Long primaryDepositHolderId = calculationService.getPrimaryHolderId(depositHolderList);
			for (DepositHolder depositHolder : depositHolderList) {
				// here we have to update the paid details for each holder
				List<AccountDetails> accountDetailsList = accountDetailsDAO
						.findCurrentSavingByCustId(depositHolder.getCustomerId());

				if (depositHolder.getId() == primaryDepositHolderId)
					amount = deposit.getMaturityAmountOnClosing();
				else
					amount = 0d;

				calculationService.insertInBankPaidDetails(bankPaid.getId(), bankPaymentDetails.getBankPaymentId(),
						bankPaymentDetails.getId(), depositHolder.getId(), depositHolder.getCustomerId(), amount,
						DateService.getCurrentDateTime(), fixedDepositForm.getPaymentMode(), fixedDepositForm.getBank(),
						fixedDepositForm.getChequeBranch(), String.valueOf(fixedDepositForm.getChequeNo()),
						fixedDepositForm.getChequeDate(), accountDetailsList.get(0).getAccountNo(), null, null, null, null, null, null);

			}

			attributes.addFlashAttribute("fixedDepositForm", fixedDepositForm);

			attributes = updateTransaction("Saved Successfully", attributes);
			return new ModelAndView("redirect:bankFDSaved");

		} catch (Exception ex) {

		}

		return new ModelAndView("bankPaymentOnMaturity", "model", model);
	}*/
	
	@RequestMapping(value = "/savePaymentMaturityDetails", method = RequestMethod.POST)
	public ModelAndView savePaymentMaturityDetails(Model model,
			@ModelAttribute FixedDepositForm fixedDepositForm, RedirectAttributes attributes) {

		try {
			String DeathCertificateSubmitted=fixedDepositForm.getDeathCertificateSubmitted();
			float f = fixedDepositForm.getChequeAmount();
			Double amount = (double)f;
			BankPayment bankPayment = bankPaymentDAO.getBankPayment(fixedDepositForm.getDepositId());
			BankPaid bankPaid = calculationService.insertInBankPaid(fixedDepositForm.getDepositId(), bankPayment.getId(), amount,
					"PAID");
			List<String> items = Arrays.asList(fixedDepositForm.getDepositHolders().split("\\s*,\\s*"));
			Integer count=items.size();
			List<DepositHolderForm> depositHolderList = depositHolderDAO.getUnpaidDepositHolders(fixedDepositForm.getDepositId());
			if(depositHolderList.size()==items.size()){
				Deposit deposit = fixedDepositDAO.getDeposit(fixedDepositForm.getDepositId());
				deposit.setIsFullyPaid(1);
				depositHolderDAO.updateDeposit(deposit);
			}
			for (String item : items) {
				
				DepositHolder depositHolder=depositHolderDAO.findById(Long.parseLong(item));
				
				List<AccountDetails> accountDetailsList = accountDetailsDAO
						.findCurrentSavingByCustId(depositHolder.getCustomerId());
				BankPaymentDetails bankPaymentDetails = bankPaymentDAO.getBankPaymentByDepositHolderId(depositHolder.getId());
				bankPaymentDetails.setIsPaid(1);
				bankPaymentDAO.updateBankPaymentDetails(bankPaymentDetails);
				amount=depositHolder.getDistAmtOnMaturity();

				calculationService.insertInBankPaidDetails(bankPaid.getId(), bankPayment.getId(),
						bankPaymentDetails.getId(), depositHolder.getId(), depositHolder.getCustomerId(), amount,
						DateService.getCurrentDateTime(), fixedDepositForm.getPaymentMode(), fixedDepositForm.getBank(),
						fixedDepositForm.getChequeBranch(), String.valueOf(fixedDepositForm.getChequeNo()),
						fixedDepositForm.getChequeDate(), accountDetailsList.get(0).getAccountNo(), null, null, null, null, null, null);
				
				
				if(DeathCertificateSubmitted=="1"){
					depositHolder.setIsAmountTransferredOnMaturity(1);
				    depositHolder.setIsAmountTransferredToNominee(1);
				}
				else{
					depositHolder.setIsAmountTransferredOnMaturity(1);
					depositHolder.setIsAmountTransferredToNominee(0);
				}
				depositHolderDAO.updateDepositHolder(depositHolder);
			}
			
			DDCheque cheque=new DDCheque();
			cheque.setAmount(fixedDepositForm.getChequeAmount());
			cheque.setBank(fixedDepositForm.getChequeBank());
			cheque.setBranch(fixedDepositForm.getChequeBranch());
			cheque.setDate(fixedDepositForm.getChequeDate());
			cheque.setName(fixedDepositForm.getChequeName());
			cheque.setNumber(fixedDepositForm.getChequeNo());
			cheque.setPaymentMode(fixedDepositForm.getPaymentMode());
			cheque.setPurpose("Maturity Payment");
			
			bankPaymentDAO.saveDDCheque(cheque);
			attributes.addFlashAttribute("fixedDepositForm", fixedDepositForm);

			attributes = updateTransaction("Saved Successfully", attributes);
			return new ModelAndView("redirect:bankFDSaved");

		} catch (Exception ex) {

		}

		return new ModelAndView("bankPaymentOnMaturity", "model", model);
	}
	@RequestMapping(value = "/saveMultiplePaymentMaturityDetails", method = RequestMethod.POST)
	public ModelAndView saveMultiplePaymentMaturityDetails(Model model,
			@ModelAttribute FixedDepositForm fixedDepositForm, RedirectAttributes attributes) {

		try {
			float f =0f;
			Integer count=fixedDepositForm.getJointAccounts().size();
			List<DepositHolderForm> depositHolderList = depositHolderDAO.getUnpaidDepositHolders(fixedDepositForm.getDepositId());
			if(depositHolderList.size()==count){
				Deposit deposit = fixedDepositDAO.getDeposit(fixedDepositForm.getDepositId());
				deposit.setIsFullyPaid(1);
				depositHolderDAO.updateDeposit(deposit);
			}
			for(int i=0;i<fixedDepositForm.getJointAccounts().size();i++){
				f += fixedDepositForm.getJointAccounts().get(i).getChequeAmount();
			}
			Double amount = (double)f;
			BankPayment bankPayment = bankPaymentDAO.getBankPayment(fixedDepositForm.getDepositId());
			BankPaid bankPaid = calculationService.insertInBankPaid(fixedDepositForm.getDepositId(), bankPayment.getId(), amount,
					"PAID");
			
			
			for(int i=0;i<fixedDepositForm.getJointAccounts().size();i++){
				f += fixedDepositForm.getJointAccounts().get(i).getChequeAmount();
				String DeathCertificateSubmitted=fixedDepositForm.getJointAccounts().get(i).getDeathCertificateSubmitted();
                DepositHolder depositHolder=depositHolderDAO.findById(fixedDepositForm.getJointAccounts().get(i).getId());
				
				List<AccountDetails> accountDetailsList = accountDetailsDAO
						.findCurrentSavingByCustId(depositHolder.getCustomerId());
				BankPaymentDetails bankPaymentDetails = bankPaymentDAO.getBankPaymentByDepositHolderId(depositHolder.getId());
				bankPaymentDetails.setIsPaid(1);
				bankPaymentDAO.updateBankPaymentDetails(bankPaymentDetails);
				
			
				calculationService.insertInBankPaidDetails(bankPaid.getId(), bankPayment.getId(),
						bankPaymentDetails.getId(), depositHolder.getId(), depositHolder.getCustomerId(), amount,
						DateService.getCurrentDateTime(), fixedDepositForm.getPaymentMode(), fixedDepositForm.getJointAccounts().get(i).getChequeBank(),
						fixedDepositForm.getJointAccounts().get(i).getChequeBranch(), String.valueOf(fixedDepositForm.getJointAccounts().get(i).getChequeNo()),
						fixedDepositForm.getJointAccounts().get(i).getChequeDate(), accountDetailsList.get(0).getAccountNo(), null, null, null, null, null, null);
				
				
				if(DeathCertificateSubmitted=="1"){
					depositHolder.setIsAmountTransferredOnMaturity(1);
				    depositHolder.setIsAmountTransferredToNominee(1);
				}
				else{
					depositHolder.setIsAmountTransferredOnMaturity(1);
					depositHolder.setIsAmountTransferredToNominee(0);
				}
				depositHolderDAO.updateDepositHolder(depositHolder);
			
				DDCheque cheque=new DDCheque();
				cheque.setAmount(fixedDepositForm.getJointAccounts().get(i).getChequeAmount());
				cheque.setBank(fixedDepositForm.getJointAccounts().get(i).getChequeBank());
				cheque.setBranch(fixedDepositForm.getJointAccounts().get(i).getChequeBranch());
				cheque.setDate(fixedDepositForm.getJointAccounts().get(i).getChequeDate());
				cheque.setName(fixedDepositForm.getJointAccounts().get(i).getChequeName());
				cheque.setNumber(fixedDepositForm.getJointAccounts().get(i).getChequeNo());
				cheque.setPaymentMode(fixedDepositForm.getPaymentMode());
				cheque.setPurpose("Maturity Payment");
				
				bankPaymentDAO.saveDDCheque(cheque);
			
			}
	
			attributes.addFlashAttribute("fixedDepositForm", fixedDepositForm);

			attributes = updateTransaction("Saved Successfully", attributes);
			return new ModelAndView("redirect:bankFDSaved");

		} catch (Exception ex) {

		}

		return new ModelAndView("bankPaymentOnMaturity", "model", model);
	}

	
	@RequestMapping(value = "/journalAllList", method = RequestMethod.GET)
	public ModelAndView journalAllList(ModelMap model, @ModelAttribute LedgerReportForm ledgerReportForm)
			throws CustomException {

		Date fromDate = ledgerReportForm.getFromDate();
		Date toDate = ledgerReportForm.getToDate();

		List<Journal> journalList = null;
		if (fromDate != null && toDate != null) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromDateStr = sdf.format(fromDate);
			try {
				fromDate = sdf.parse(fromDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			String toDateStr = sdf.format(toDate);
			try {
				toDate = sdf.parse(toDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			journalList = fixedDepositDAO.getJournalListByFromAndToDate(fromDate, toDate);

			if (journalList != null && journalList.size() > 0) {
				for (Journal journal : journalList) {
					Deposit deposit = fixedDepositDAO.getDeposit(journal.getDepositId());
					journal.setDepositAccountNumber(deposit.getAccountNumber());

				}

			}
		} else {

			model.put(Constants.ERROR, "Please Insert the date.");
			return new ModelAndView("searchAllLedgerByDate", "model", model);
		}

		if (journalList == null) {
			return new ModelAndView("noDataFound", "model", model);
		}
		model.put("journalList", journalList);
		model.put("ledgerReportForm", ledgerReportForm);

		return new ModelAndView("searchAllLedgerByDate", "model", model);
	}

	@RequestMapping(value = "/searchAllLedgerByDate", method = RequestMethod.GET)
	public ModelAndView ledgerAllReportSummary(ModelMap model, @ModelAttribute LedgerReportForm ledgerReportForm,
			String account) throws CustomException {
		model.put("account", account);
		model.put("ledgerReportForm", ledgerReportForm);
		return new ModelAndView("searchAllLedgerByDate", "model", model);

	}
	
	@RequestMapping(value = "/viewDeposit", method = RequestMethod.GET)
	public ModelAndView editFDBank(@RequestParam Long id, ModelMap model, HttpServletRequest request,
			RedirectAttributes attributes) throws CustomException {

		ModelAndView mav;
		HolderForm holderForm = new HolderForm();

//				mav = new ModelAndView("editReverseEMIBank", "model", model);

		List<HolderForm> depositList = depositService.getDepositByDepositId(id);
		if(depositList != null && depositList.size() > 0)
		holderForm = depositList.get(0);
		model.put("depositHolderList", depositList);
		List<AccountDetails> accountList = accountDetailsDAO
				.findCurrentSavingByCustId(holderForm.getDepositHolder().getCustomerId());
		model.put("accountList", accountList);
		mav = new ModelAndView("viewDepositBank", "model", model);

		model.put("depositHolderForm", holderForm);
		model.put("depositClassification", holderForm.getDeposit().getDepositClassification());

		return mav;

	}
	
	
	
	@RequestMapping(value = "/getDeathCertificate", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody String getDeathCertificate(ModelMap model, @RequestParam Long depositId) throws CustomException {

				String result = "0";
		List<DepositHolder> depositHolderObjList = depositHolderDAO.getDepositHolders(depositId);	
		Iterator<DepositHolder> itr = depositHolderObjList.iterator();
		while (itr.hasNext()) {
			DepositHolder holder = itr.next();
			if(holder.getDeathCertificateSubmitted()!=null){
		    if(holder.getDeathCertificateSubmitted().equals("1") || holder.getDeathCertificateSubmitted().equalsIgnoreCase("Yes")){
		    	result="1";
		    }
			}
		    
		} 
			
			return result;
	}
	
	@RequestMapping(value = "/issueOverdraft", method = RequestMethod.GET)
	public ModelAndView issueOverdraft(ModelMap model, @ModelAttribute OverdraftForm overdraftForm) throws CustomException {
		
		
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		model.put("todaysDate", date);
		
		return new ModelAndView("issueingOverdraft", "model", model);

	}
	
	@RequestMapping(value = "/getOverdraftDetails", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody OverdraftForm getOverdraftDetails(String accountNumber) throws CustomException {
		System.out.println("Arjun-----");
		Deposit deposit = depositHolderDAO.getDepositByAccountNumber(accountNumber);
		System.out.println("productConfigurationId:"+deposit.getProductConfigurationId());
		OverdraftForm overdraftForm = new OverdraftForm();
		System.out.println(deposit.getCurrentBalance());
		overdraftForm.setCurrentBalance(deposit.getCurrentBalance());
		ProductConfiguration productConfiguration =productConfigurationDAO.findById(deposit.getProductConfigurationId());
		overdraftForm.setMaximumOverdraftPercentage(productConfiguration.getMaximumOverdraftPercentage());
		overdraftForm.setMinimumOverdraftPercentage(productConfiguration.getMinimumOverdraftPercentage());
		if(productConfiguration.getHigherInterestRate()!=null){
			overdraftForm.setInterestRate(deposit.getInterestRate()+productConfiguration.getHigherInterestRate());
		}
		else{
			overdraftForm.setInterestRate(deposit.getInterestRate());
		}
		if(productConfiguration.getIsOverdraftFacilityAvailable()==null){
			
			overdraftForm=new OverdraftForm();
		}
		if(productConfiguration.getIsOverdraftFacilityAvailable()!=null){
			
			if(productConfiguration.getIsOverdraftFacilityAvailable()==0){
				
				overdraftForm=new OverdraftForm();
			}
		}
		return overdraftForm;
	}
	
	@RequestMapping(value = "/overdraftIssue", method = RequestMethod.POST)
	public ModelAndView saveoverdraftIssue(Model model,
			@ModelAttribute("overdraftForm") OverdraftForm overdraftForm) {
		try {
			OverdraftIssue overdraftIssue = new OverdraftIssue();
			Deposit deposit = depositHolderDAO.getDepositByAccountNumber(overdraftForm.getAccountNumber());
			ProductConfiguration productConfiguration = productConfigurationDAO.findById(deposit.getProductConfigurationId());
			if(productConfiguration==null)
			{
				productConfiguration = productConfigurationDAO.findByProductCode(deposit.getProductConfigurationId().toString());
			}
			Float rInterest=0f;
			if(productConfiguration.getHigherInterestRate()!=null){
				rInterest=productConfiguration.getHigherInterestRate()+deposit.getInterestRate();
			}
			else{
				rInterest= deposit.getInterestRate();
			}
			Date date=overdraftForm.getIssueDate();
			Date overDraftEndDate=overdraftForm.getIssueDate();
				if(overdraftForm.getTenureInYears()!=null){
					overDraftEndDate=DateService.addYear(overDraftEndDate, overdraftForm.getTenureInYears());
				}
				
				if(overdraftForm.getTenureInDays()!=null){
					overDraftEndDate=DateService.addDays(overDraftEndDate, overdraftForm.getTenureInDays());
				}
				
				if(overdraftForm.getTenureInMonths()!=null){
					overDraftEndDate=DateService.addMonths(overDraftEndDate, overdraftForm.getTenureInMonths());
				}
				Integer i=DateService.compareDate(deposit.getMaturityDate(),overDraftEndDate);
			if(i>0)
			{
			deposit.setMaturityInstruction("autoRenew");
			fixedDepositDAO.updateFD(deposit);
			}
			Integer days=DateService.getDaysBetweenTwoDates(date, overDraftEndDate);
			//Double amountToReturn=0d;
			if(overdraftForm.getDefaultOverdraftPercentage()!=null){
				Double amount=(deposit.getCurrentBalance()*overdraftForm.getDefaultOverdraftPercentage())/100;
				//amountToReturn=	amount+(((amount*rInterest)/(36500))*days);
				overdraftIssue.setSanctionedAmount(amount);
				overdraftIssue.setWithdrawableAmount(amount);
			}
			else{
				overdraftIssue.setSanctionedAmount(overdraftForm.getDefaultOverdraftAmount());
				overdraftIssue.setWithdrawableAmount(overdraftForm.getDefaultOverdraftAmount());
				//amountToReturn=	overdraftForm.getDefaultOverdraftAmount()+(((overdraftForm.getDefaultOverdraftAmount()*rInterest)/(36500))*days);
			}
			//overdraftIssue.setAmountToReturn(amountToReturn);
			overdraftIssue.setInterestRate(rInterest);
			overdraftIssue.setOverdraftEndDate(overDraftEndDate);
			overdraftIssue.setDepositId(deposit.getId());
			overdraftIssue.setIssueDate(overdraftForm.getIssueDate());
			
			overdraftIssue.setOverdraftNumber(fdService.generateRandomStringOD());
			if(overdraftForm.getDefaultOverdraftPercentage()!= null) {
			overdraftIssue.setSanctionedPercentage(overdraftForm.getDefaultOverdraftPercentage());
			}
			overdraftIssue.setIsActive(1);
			overdraftIssue.setTotalAmountWithdrawn(0d);
			
			
			overdraftIssue.setTenureInMonths(overdraftForm.getTenureInMonths());
			overdraftIssue.setTenureInDays(overdraftForm.getTenureInDays());
			overdraftIssue.setTenureInYears(overdraftForm.getTenureInYears());
			overdraftIssue.setIsEMI(overdraftForm.getIsEMI());
			overdraftIssue.setTotalAmountPaid(0d);
			overdraftIssueDAO.insertOverdraftIssue(overdraftIssue);
			model.addAttribute("sucess", "saved sucessfully!");
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		
		
		return new ModelAndView("issueingOverdraft", "model", model);
		
	}
	
	@RequestMapping(value = "/withdrawOverdraft", method = RequestMethod.GET)
	public ModelAndView withdrawOverdraft(ModelMap model, @ModelAttribute DepositForm depositForm) throws CustomException {
	
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		model.put("todaysDate", date);
		model.put("depositForm",depositForm);
		return new ModelAndView("withdrawOverdraft", "model", model);

	}
	
	@RequestMapping(value = "/paymentOverdraft", method = RequestMethod.GET)
	public ModelAndView paymentOverdraft(ModelMap model, @ModelAttribute DepositForm depositForm) throws CustomException {
	
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		List<ModeOfPayment> paymentList = modeOfPaymentDAO.getAllModeOfPaymentEmployee();
		model.put("paymentMode", paymentList);
		model.put("todaysDate", date);
		model.put("depositForm",depositForm);
		return new ModelAndView("paymentOverdraft", "model", model);

	}
	
	
	@RequestMapping(value = "/getHolderDetails", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<DepositForm> getHolderDetails(String accountNumber) throws CustomException {
		
		System.out.println("Arjun-----");
		List<DepositForm> depositList = paymentDAO.withdrawAccountNumber(accountNumber);
		
		return depositList;
	
	}
	
	@RequestMapping(value = "/getOverdraftIssueDetails", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody OverdraftIssue getOverdraftIssueDetails(String overdraftNumber) throws CustomException {
		
		
		OverdraftIssue overdraftDetails = overdraftIssueDAO.getOverdraftIssueDetails(overdraftNumber);
		if(overdraftDetails.getSanctionedPercentage()==null){
			Double withdrawableAmount=  overdraftDetails.getWithdrawableAmount();
			Double withdrawnAmount=  overdraftDetails.getTotalAmountWithdrawn();
			
			overdraftDetails.setWithdrawableAmount(withdrawableAmount-withdrawnAmount);
		}
		else{
			
			Deposit deposit=fixedDepositDAO.findByDepositId(overdraftDetails.getDepositId());
			Double withdrawableAmount=(overdraftDetails.getSanctionedPercentage()*deposit.getCurrentBalance())/100;
			Double withdrawnAmount=  overdraftDetails.getTotalAmountWithdrawn();
			
			overdraftDetails.setWithdrawableAmount(withdrawableAmount-withdrawnAmount);
		}
		return overdraftDetails;
	
	}
	@RequestMapping(value = "selectLoggedInRole", method = RequestMethod.GET)
	private ModelAndView selectLoggedInRole() {

	return new ModelAndView("redirect:/main/default");

	}
	
	@RequestMapping(value = "/saveWithdrawOverdraft", method = RequestMethod.POST)
	public ModelAndView saveWithdrawOverdraft(Model model,
			@ModelAttribute("depositForm") DepositForm depositForm, RedirectAttributes attributes){
		
		OverdraftWithdrawMaster overdraftWithdrawMaster=new OverdraftWithdrawMaster();
		overdraftWithdrawMaster.setPaymentMode(depositForm.getPaymentMode());
		
		
		OverdraftIssue overdraft=overdraftIssueDAO.getOverdraftIssueDetails(depositForm.getOverdraftNumber());
		double amountWithdrawn=overdraft.getTotalAmountWithdrawn()+depositForm.getWithdrawAmount();
		Double amountToReturn=amountWithdrawn-overdraft.getTotalAmountPaid();
		Date createdDate=depositForm.getCreatedDate();
		Integer days=DateService.getDaysBetweenTwoDates(createdDate, overdraft.getOverdraftEndDate());
		amountToReturn=	amountToReturn+(((amountToReturn*overdraft.getInterestRate())/(36500))*days);
		if(overdraft.getIsEMI()!=null){
			if(overdraft.getIsEMI()==1){
				//Date createdDate=depositForm.getCreatedDate();
				LocalDate createdDate1=depositForm.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate newDate1=createdDate1.plusMonths(1).withDayOfMonth(1);
				Date startDate=java.sql.Date.valueOf(newDate1);
				
				Calendar startCalendar = new GregorianCalendar();
				startCalendar.setTime(startDate);
				Calendar endCalendar = new GregorianCalendar();
				endCalendar.setTime(overdraft.getOverdraftEndDate());
				int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
				int emiMonths = (diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH))+1;
				
				double emiAmount=(amountWithdrawn-overdraft.getTotalAmountPaid())/emiMonths;
				overdraft.setEMIAmount(emiAmount);
				//overdraftIssueDAO.updateOverdraftIssue(overdraft);
			}
		}
		
		
		//amountWithdrawn=amountWithdrawn+depositForm.getWithdrawAmount();
		overdraft.setTotalAmountWithdrawn(amountWithdrawn);
		overdraft.setAmountToReturn(amountToReturn);
		overdraftIssueDAO.updateOverdraftIssue(overdraft);
		
		
		overdraftWithdrawMaster.setOverdraftId(overdraft.getId());
		overdraftWithdrawMaster.setWithdrawAmount(depositForm.getWithdrawAmount());
		
		overdraftWithdrawMaster.setWithdrawDate(depositForm.getCreatedDate());
		overdraftIssueDAO.insertOverdraftWithdrawMaster(overdraftWithdrawMaster);
		attributes.addFlashAttribute("depositForm", depositForm);

		attributes = updateTransaction("Saved Successfully", attributes);
		return new ModelAndView("redirect:bankFDSaved");

	}

	@RequestMapping(value = "/savePaymentOverdraft", method = RequestMethod.POST)
	public ModelAndView savePaymentOverdraft(Model model,
			@ModelAttribute("depositForm") DepositForm depositForm, RedirectAttributes attributes){
		
		OverdraftPayment overdraftPayment=new OverdraftPayment();
		overdraftPayment.setPaymentModeId(Long.parseLong(depositForm.getPaymentMode()));
		
		
		OverdraftIssue overdraft=overdraftIssueDAO.getOverdraftIssueDetails(depositForm.getOverdraftNumber());
		double amountPaid=depositForm.getDepositAmount()+overdraft.getTotalAmountPaid();
		double amounttoReturn=overdraft.getAmountToReturn()-amountPaid;
		Date paymentDate=depositForm.getCreatedDate();
		if(overdraft.getIsEMI()!=null){
			if(overdraft.getIsEMI()==1){
				
				LocalDate createdDate1=depositForm.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate newDate1=createdDate1.plusMonths(1).withDayOfMonth(1);
				Date startDate=java.sql.Date.valueOf(newDate1);
				
				Calendar startCalendar = new GregorianCalendar();
				startCalendar.setTime(startDate);
				Calendar endCalendar = new GregorianCalendar();
				endCalendar.setTime(overdraft.getOverdraftEndDate());
				int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
				int emiMonths = (diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH))+1;
				
				double emiAmount=(amounttoReturn)/emiMonths;
				overdraft.setEMIAmount(emiAmount);
				//overdraftIssueDAO.updateOverdraftIssue(overdraft);
			}
		}
		
		
		overdraft.setTotalAmountPaid(amountPaid);
		overdraft.setAmountToReturn(amounttoReturn);
		overdraftIssueDAO.updateOverdraftIssue(overdraft);
		
		
		overdraftPayment.setOverdraftId(overdraft.getId());
		overdraftPayment.setDepositId(overdraft.getDepositId());
		overdraftPayment.setPaymentAmount(depositForm.getDepositAmount());
		overdraftPayment.setPaymentDate(paymentDate);
		String transactionId = fdService.generateRandomString();
		overdraftPayment.setTransactionId(transactionId);
		ModeOfPayment paymentMode=modeOfPaymentDAO.getModeOfPaymentById(Long.parseLong(depositForm.getPaymentMode()));
		if (paymentMode.getPaymentMode().contains("Cash")
				|| paymentMode.getPaymentMode().contains("cash")) {
			
		} else if (paymentMode.getPaymentMode().equalsIgnoreCase("DD")
				|| paymentMode.getPaymentMode().equalsIgnoreCase("Cheque")) {

			overdraftPayment.setChequeDDDate(depositForm.getChequeDate());
			overdraftPayment.setChequeDDNumber(depositForm.getChequeNo());
			overdraftPayment.setBank(depositForm.getChequeBank());
			overdraftPayment.setBranch(depositForm.getChequeBranch());

			
		} else if (paymentMode.getPaymentMode().equalsIgnoreCase("Credit Card")|| paymentMode.getPaymentMode().contains("Debit Card")) {

			String[] cardNoArray = depositForm.getCardNo().split("-");
			String cardNo = "";
			for (int i = 0; i < cardNoArray.length; i++) {
				cardNo = cardNo + cardNoArray[i];
			}
			overdraftPayment.setCardNo(Long.valueOf(cardNo));
			overdraftPayment.setCardType(depositForm.getCardType());

			overdraftPayment.setCardCVV(depositForm.getCvv());
			overdraftPayment.setCardExpiryDate(depositForm.getExpiryDate());

			
		}

		/*else if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("Net Banking")) {

			overdraftPayment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getDepositForm().getAccountType());
			overdraftPayment.setLinkedAccNoForFundTransfer(fixedDepositForm.getDepositForm().getLinkedAccountNo());
			overdraftPayment.setTransferType(fixedDepositForm.getDepositForm().getTransferType());

			}
*/
		
		
		overdraftIssueDAO.insertOverdraftPayment(overdraftPayment);
		attributes.addFlashAttribute("depositForm", depositForm);

		attributes = updateTransaction("Saved Successfully", attributes);
		return new ModelAndView("redirect:bankFDSaved");

	}

//	
//	@RequestMapping(value = "selectLoggedInRole", method = RequestMethod.GET)
//	private ModelAndView selectLoggedInRole() {
//
//	return new ModelAndView("redirect:/main/default");
//
//	}


	
}
