package annona.web;

import java.math.BigInteger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
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

import annona.dao.AccountDetailsDAO;
import annona.dao.BenificiaryDetailsDAO;
import annona.dao.BranchDAO;
import annona.dao.CurrencyConfigurationDAO;
import annona.dao.CustomerDAO;
import annona.dao.DepositHolderDAO;
import annona.dao.DepositHolderWiseDistributionDAO;
import annona.dao.DepositModificationDAO;
import annona.dao.DepositRateDAO;
import annona.dao.DepositRatesDAO;
import annona.dao.EndUserDAO;
import annona.dao.FDRatesDAO;
import annona.dao.FixedDepositDAO;
import annona.dao.FundTransferDAO;
import annona.dao.GstDAO;
import annona.dao.InterestDAO;
import annona.dao.ModeOfPaymentDAO;
import annona.dao.NomineeDAO;
import annona.dao.PayoffDAO;
import annona.dao.ProductConfigurationDAO;
import annona.dao.PaymentDAO;
import annona.dao.PaymentDistributionDAO;
import annona.dao.RatesDAO;
import annona.dao.SweepConfigurationDAO;
import annona.dao.TDSDAO;
import annona.dao.TransactionDAO;
import annona.dao.UploadFileDAO;
import annona.dao.WithdrawDepositDAO;
import annona.domain.AccountDetails;
import annona.domain.BankConfiguration;
import annona.domain.BenificiaryDetails;
import annona.domain.Branch;
import annona.domain.CurrencyConfiguration;
import annona.domain.Customer;
import annona.domain.CustomerCategory;
import annona.domain.Deposit;
import annona.domain.DepositHolder;
import annona.domain.DepositHolderNominees;
import annona.domain.DepositHolderWiseDistribution;
import annona.domain.DepositModification;
import annona.domain.DepositModificationMaster;
import annona.domain.DepositRates;
import annona.domain.DepositSummary;
import annona.domain.Distribution;
import annona.domain.EndUser;
import annona.domain.Interest;
import annona.domain.ModeOfPayment;
import annona.domain.MultipleFundTransfer;
import annona.domain.PayoffDetails;
import annona.domain.ProductConfiguration;
import annona.domain.SweepConfiguration;
import annona.domain.SweepInFacilityForCustomer;
import annona.domain.Payment;
import annona.domain.TDS;
import annona.domain.Transaction;
import annona.domain.UploadFile;
import annona.domain.WithdrawDeposit;
import annona.exception.CustomException;
import annona.domain.DepositWiseCustomerTDSForChart;
import annona.form.AccountDetailsForm;
import annona.form.AutoDepositForm;
import annona.form.ColumnGraphForm;
import annona.form.CustomerDetailsParser;
import annona.form.CustomerForm;
import annona.form.DepositForm;
import annona.form.DepositHolderForm;
import annona.form.EndUserForm;
import annona.form.FileForm;
import annona.form.FixedDepositForm;
import annona.form.FundTransferForm;
import annona.form.HolderForm;
import annona.form.JointAccount;
import annona.form.MultipleFundTransferForm;
import annona.form.RatesForm;
import annona.form.ReportForm;
import annona.form.SweepConfigurationForm;
import annona.form.TransactionForm;
import annona.form.UploadFileForm;
import annona.form.WithdrawForm;
import annona.services.DepositService;
import annona.services.CalculationService;
import annona.services.ColumnGraphService;
import annona.services.DateService;
import annona.services.FDService;
import annona.services.ImageService;
import annona.services.LedgerService;
import annona.utility.Constants;
import annona.utility.Event;

@Controller
@RequestMapping("/users")
public class CustomerController {

	@Autowired
	DepositRatesDAO depositRatesDAO;

	@Autowired
	MailSender mailSender;

	@Autowired
	BenificiaryDetailsDAO benificiaryDetailsDAO;

	@Autowired
	EndUserDAO endUserDAOImpl;

	@Autowired
	EndUserForm endUserForm;

	@Autowired
	FixedDepositForm fixedDepositForm;

	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	FixedDepositDAO fixedDepositDao;

	@Autowired
	TransactionDAO transactionDAO;

	@Autowired
	RatesDAO ratesDAO;

	@Autowired
	FDService fdService;

	@Autowired
	FDRatesDAO fdRatesDAO;

	@Autowired
	FundTransferForm fundTransferForm;

	@Autowired
	FundTransferDAO fundTransferDAO;

	@Autowired
	TransactionForm transactionForm;

	@Autowired
	AccountDetailsDAO accountDetailsDAO;

	@Autowired
	FixedDepositDAO fixedDepositDAO;

	@Autowired
	NomineeDAO nomineeDAO;

	@Autowired
	DepositHolderDAO depositHolderDAO;

	@Autowired
	DepositService depositService;

	@Autowired
	DepositModificationDAO depositModificationDAO;

	@Autowired
	DepositForm depositForm;

	@Autowired
	PaymentDAO paymentDAO;

	@Autowired
	PaymentDistributionDAO paymentDistributionDAO;

	@Autowired
	WithdrawForm withdrawForm;

	@Autowired
	GstDAO gstDAO;

	@Autowired
	InterestDAO interestDAO;

	@Autowired
	TDSDAO tdsDAO;

	@Autowired
	UploadFileForm uploadFileForm;

	@Autowired
	UploadFileDAO uploadFileDAO;

	@Autowired
	DepositRateDAO depositRateDAO;

	@Autowired
	DepositHolderWiseDistributionDAO depositHolderWiseDistributionDAO;

	@Autowired
	WithdrawDepositDAO withdrawDepositDAO;

	@Autowired
	PayoffDAO payOffDAO;

	@Autowired
	RatesForm ratesForm;

	@Autowired
	CalculationService calculationService;
	
	@Autowired
	SweepConfigurationForm sweepConfigurationForm;
	
	@Autowired
	SweepConfigurationDAO sweepConfigurationDAO;
	
	@Autowired
	ProductConfigurationDAO productConfigurationDAO;
	
	@Autowired
	BranchDAO branchDAO;


	@Autowired
	LedgerService ledgerService;
	
	@Autowired
	CurrencyConfigurationDAO currencyConfigurationDAO;
	
	@Autowired
	ModeOfPaymentDAO modeOfPaymentDAO;

	CookieThemeResolver themeResolver = new CookieThemeResolver();

	// Used to display log messages
	protected Logger log = Logger.getLogger(AdminController.class.getName());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	private String getCurrentLoggedUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();

	}

	@ModelAttribute("requestCurrentUser")
	private EndUser getCurrentLoggedUserDetails() {

		EndUser endUser = endUserDAOImpl.findByUsername(getCurrentLoggedUserName()).getSingleResult();
		ModelMap model = new ModelMap();
		model.put("user", endUser);
		return endUser;

	}

	@ModelAttribute("getCustomerDetails")
	public Customer getUserDetails() {
		EndUser user = getCurrentLoggedUserDetails();
		if (user.getImageName() != null) {
			String type = ImageService.getImageType(user.getImageName());

			String url = "data:image/" + type + ";base64," + Base64.encodeBase64String(user.getImage());
			user.setImageName(url);
		}
		Customer customer = customerDAO.getByUserId(user.getUserName()).getSingleResult();

		return customer;
	}

	@ModelAttribute("getCustomerId")
	public Long getCustomerId() {
		EndUser user = getCurrentLoggedUserDetails();
		Customer customer = customerDAO.getByUserId(user.getUserName()).getSingleResult();
		return customer.getId();
	}

	@RequestMapping(value = "/editCustomerProfile", method = RequestMethod.GET)
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

		return new ModelAndView("editCustomerProfile", "model", model);

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
		return "redirect:editCustomerProfile?id=" + endUserForm.getId();
	}

	@RequestMapping(value = "/confirmEditCustomerProfile", method = RequestMethod.POST)
	public ModelAndView confirmEditAdminProfile(ModelMap model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {

		model.put("endUserForm", endUserForm);

		return new ModelAndView("confirmEditCustomerProfile", "model", model);

	}

	@RequestMapping(value = "/updateCustomerDetails", method = RequestMethod.POST)
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

		return new ModelAndView("updateCustomerSuccess", "model", model);

	}

	@RequestMapping(value = "/editCustomerPWD", method = RequestMethod.GET)
	public ModelAndView editAdminPWD(ModelMap model, @RequestParam("id") Long id, RedirectAttributes attributes)
			throws CustomException {

		EndUser userProfile = endUserDAOImpl.findId(id);

		endUserForm.setId(userProfile.getId());

		endUserForm.setTransactionId(userProfile.getTransactionId());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("editCustomerPWD", "model", model);

	}

	@RequestMapping(value = "/updateEditCustomerPWD", method = RequestMethod.POST)
	public ModelAndView updateEditAdminPWD(ModelMap model, @ModelAttribute EndUserForm endUserForm,
			BindingResult result, RedirectAttributes attributes) throws CustomException {

		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());

		endUser.setId(endUserForm.getId());

		endUser.setPassword(endUserForm.getNewPassword());

		endUser.setTransactionId(endUserForm.getTransactionId());

		endUserDAOImpl.update(endUser);

		model.put("endUserForm", endUserForm);

		return new ModelAndView("updateCustomerSuccess", "model", model);

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

		return "redirect:user";
	}

	@RequestMapping(value = "/getLocaleLang", method = RequestMethod.GET)
	public String getLocaleLang(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws CustomException {

		log.info("Received request for locale change");
		EndUser endUser = endUserDAOImpl.findByUsername(getCurrentLoggedUserName()).getSingleResult();
		LocaleResolver localeResolver = new CookieLocaleResolver();
		Locale locale = new Locale(request.getParameter("lang"));
		localeResolver.setLocale(request, response, locale);
		endUser.setPrefferedLanguage(request.getParameter("lang"));

		endUserDAOImpl.update(endUser);

		return "redirect:user";
	}

	/********* Single deposit creation ********/

	@RequestMapping(value = "/newDeposit")
	public ModelAndView newDeposit(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm, String val, Long productId,String depositType)
			throws CustomException {
		Customer customerDetails = getUserDetails();
		Customer customer = customerDAO.getById(customerDetails.getId());
		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(customerDetails.getId());
		fixedDepositForm.setAccountList(accountList);
		fixedDepositForm.setId(customerDetails.getId());
		fixedDepositForm.setCategory(customerDetails.getCategory());
		fixedDepositForm.setNriAccountType(customer.getNriAccountType());
		fixedDepositForm.setCitizen(customer.getCitizen());
		
		model.put("fixedDepositForm", fixedDepositForm);
		model.put("customerId", customerDetails.getCustomerID());
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		model.put("todaysDate", date);
		CurrencyConfiguration currencyConfiguration = null;
		if(customer.getNriAccountType() != null)
		currencyConfiguration = currencyConfigurationDAO.findByAccountType(customer.getNriAccountType().trim());

		/* / for deposit payment.. / */

		model.put("cashPayment", 0);
		model.put("ddPayment", 0);
		model.put("chequePayment", 0);
		model.put("nriAccountType", customer.getNriAccountType());
		model.put("netBanking", 1);
		model.put("val", val);
		model.put("depositType",depositType);
		
		model.put("defaultAccountTypeCurrency",currencyConfiguration!=null?currencyConfiguration.getCurrency():"INR");
		return new ModelAndView("fixedDepositForm", "model", model);

	}


	@RequestMapping(value = "/confirmFixedDeposit", method = RequestMethod.POST)
	public ModelAndView confirmFixedDeposit(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm, @RequestParam String customerDetails,
			RedirectAttributes attributes) throws CustomException {

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
			//fixedDepositForm.setFdTenure(5);
			//fixedDepositForm.setFdTenureType(Constants.YEAR);
		}
		/*if (fixedDepositForm.getFdTenureType().equalsIgnoreCase("Month")) {
			maturityDate = DateService.generateMonthsDate(DateService.getCurrentDate(), fixedDepositForm.getFdTenure());
		} else if (fixedDepositForm.getFdTenureType().equalsIgnoreCase("Year")) {
			maturityDate = DateService.generateYearsDate(DateService.getCurrentDate(), fixedDepositForm.getFdTenure());
			int day = 0;
			if (fixedDepositForm.getDaysValue() != null)
				day = fixedDepositForm.getDaysValue();
			maturityDate = DateService.generateDaysDate(maturityDate, day + 1);
		} else {*/
		
		Date date1 = DateService.getCurrentDate();
		if(fixedDepositForm.getTenureInYears()!=null){
				date1=DateService.addYear(date1, fixedDepositForm.getTenureInYears());
		}
		if(fixedDepositForm.getTenureInMonths()!=null){
			date1 = DateService.addMonths(date1, fixedDepositForm.getTenureInMonths());
		}
		if(fixedDepositForm.getTenureInDays()!=null){
			date1 = DateService.addDays(date1, fixedDepositForm.getTenureInDays());
		}
			Integer totalDays = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), date1);
			
			
			fixedDepositForm.setTotalTenureInDays(totalDays);
		
		
		
			maturityDate = DateService.generateDaysDate(DateService.getCurrentDate(), fixedDepositForm.getTotalTenureInDays());
		/*}*/

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
		model.put("fdDeductDate", date);
		model.put("getcustomerByPanCard", getcustomerByPanCard);
		model.put("getcustomerByAadharCard", getcustomerByAadharCard);
		model.put("customerDetails", customerDetails);
		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("getConfirmFixedDepositForm", "model", model);

	}


	@RequestMapping(value = "/saveDeposit", method = RequestMethod.POST)
	public ModelAndView saveDeposit(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes, HttpServletRequest request) throws CustomException {

//		try {

		Customer customerDetails = getUserDetails();

		String isTaxSavingDeposit = fixedDepositForm.getTaxSavingDeposit() != null
				? fixedDepositForm.getTaxSavingDeposit()
				: "0";
		Integer taxSavingDeposit = Integer.parseInt(isTaxSavingDeposit);
		Deposit deposit = depositService.saveDepositFromCustomer(fixedDepositForm, customerDetails, taxSavingDeposit);

		// fdService.forReports(fixedDepositForm); // For reports
		attributes.addFlashAttribute("fixedDepositForm", fixedDepositForm);
		Date curDate = DateService.loginDate;
		attributes.addFlashAttribute(Constants.TRANSACTIONID, deposit.getTransactionId());
		attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
		attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Deposit Created Successfully");

		return new ModelAndView("redirect:fdSaved");
//		} catch (Exception ex) {
//			return new ModelAndView("redirect:#");
//		}

	}

	@RequestMapping(value = "/fdSaved", method = RequestMethod.GET)
	public ModelAndView fdSaved(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("fdSaved", "model", model);

	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView showBankEmpDashBoard(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws CustomException {

		EndUser user = getCurrentLoggedUserDetails();
		Customer customer = getUserDetails();
		Long customerId = customer.getId();

		List<Deposit> depositLists = fixedDepositDAO.getDeposits(customerId);

		List<ColumnGraphForm> columnGraphList = new ArrayList<ColumnGraphForm>();
		List<String> categoryList = new ArrayList<>();
		// Populate Graphs for BuyerSameBankEvent
		if (depositLists != null && depositLists.size() > 0) {

			double[] currentBalanceData = new double[depositLists.size()];
			double[] tenure = new double[depositLists.size()];
			String[] accNumber = new String[depositLists.size()];
			double[] newMaturityAmountData = new double[depositLists.size()];

			for (int i = 0; i < depositLists.size(); i++) {

				String accountNumber = depositLists.get(i).getAccountNumber();
				if (depositLists.get(i).getAccountNumber() == null) {
					categoryList.addAll(null);
				}

				else {
					categoryList.add(accountNumber);
				}

				if (depositLists.get(i).getDepositAmount() == null) {
					depositLists.get(i).setDepositAmount(0.0);
				}
				if (depositLists.get(i).getAccountNumber() == null) {
					depositLists.get(i).setAccountNumber(null);
				}
				if (depositLists.get(i).getId() == null) {
					depositLists.get(i).setId(0l);
				}
				currentBalanceData[i] = depositLists.get(i).getCurrentBalance();
				accNumber[i] = depositLists.get(i).getAccountNumber();
				if (depositLists.get(i).getNewMaturityAmount() != null) {
					newMaturityAmountData[i] = depositLists.get(i).getNewMaturityAmount();
				}
				if (depositLists.get(i).getTenure() != null) {
					tenure[i] = depositLists.get(i).getTenure();
				}
			}
			columnGraphList.add(ColumnGraphService.generateObject("Current Balance", currentBalanceData));
			columnGraphList.add(ColumnGraphService.generateObject("Tenure", tenure));
			columnGraphList.add(ColumnGraphService.generateObject("Maturity Amount", newMaturityAmountData));

		}
		String[] category = categoryList.toArray(new String[categoryList.size()]);
		Gson gson = new Gson();
		String values = gson.toJson(columnGraphList);
		String categories = gson.toJson(category);

		Customer user1 = getUserDetails();
		List<HolderForm> depositHolderList = depositService.getAllDepositByCustomerId(user1.getId());

		// Get all open deposits of this customer
		List<Deposit> depositList = fixedDepositDAO.getDeposits(customerId);

		// Fill the class from customer TDS
		List<DepositWiseCustomerTDSForChart> depositWiseTDSList = this.getCustomerTDSforChart(depositList);

		if (depositHolderList != null) {
			model.put("depositHolderList", depositHolderList);

		} else {
			return new ModelAndView("noDataFoundCusm", "model", model);
		}

		model.put("values", values);
		model.put("categories", categories);

		model.put("user", user);
		model.put("depositWiseTDSList", depositWiseTDSList);
		model.put("depositList", depositList);
		model.put("loginDate", DateService.loginDate);
		return new ModelAndView("customerfdChangesLists", "model", model);
	}

	private List<DepositWiseCustomerTDSForChart> getCustomerTDSforChart(List<Deposit> depositList) {

		List<DepositWiseCustomerTDSForChart> depositWiseTDSList = new ArrayList();
		Customer user = getUserDetails();
		Long customerId = Long.parseLong(user.getId().toString());
		String prevFinancialYear = "2017-2018";

		for (int i = 0; i < depositList.size(); i++) {
			DepositWiseCustomerTDSForChart depositWiseTDS = new DepositWiseCustomerTDSForChart();
			depositWiseTDS.setDepositNo(depositList.get(i).getAccountNumber());
			Double tdsAmount = tdsDAO.getCustomerTDSForDeposit(customerId, depositList.get(i).getId(),
					prevFinancialYear);
			depositWiseTDS.setTdsAmount(tdsAmount);
			depositWiseTDSList.add(depositWiseTDS);

		}

		return depositWiseTDSList;
	}

	@RequestMapping(value = "/fdList", method = RequestMethod.GET)
	public ModelAndView fdList(ModelMap model, RedirectAttributes attributes) throws CustomException {

		ModelAndView mav = new ModelAndView();

		Customer user = getUserDetails();

		List<HolderForm> depositHolderObjList = depositService.getAllDepositByCustomerId(user.getId());

		if (depositHolderObjList != null) {

			model.put("depositHolderList", depositHolderObjList);
			mav = new ModelAndView("fdChangesDepositList", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/editFD", method = RequestMethod.GET)
	public ModelAndView editFD(@RequestParam Long id, @RequestParam Long holderId, @RequestParam String depositCategory,
			ModelMap model, RedirectAttributes attributes, HttpServletRequest request) throws CustomException {

//
//		Customer user = getUserDetails();
//		HolderForm holderForm = new HolderForm();
//		ModelAndView mav;
//
//		if (depositCategory != null && depositCategory.equalsIgnoreCase("REVERSE-EMI")) {
//			holderForm = depositService.getReverseEMIByDepositId(id);
//			mav = new ModelAndView("editReverseEMI", "model", model);
//		} else {
//			holderForm = depositService.getByDepositIdHolderIdCusId(id, holderId, user.getId());
//			List<AccountDetails> accountList = accountDetailsDAO
//					.findCurrentSavingByCustId(holderForm.getDepositHolder().getCustomerId());
//			model.put("accountList", accountList);
//			mav = new ModelAndView("editFD", "model", model);
//
//		}
//
//		model.put("depositClassification", holderForm.getDeposit().getDepositClassification());
//		model.put("depositHolderForm", holderForm);
//		model.put("customerName", user.getCustomerName());
//		model.put("bankConfiguration", bankConfiguration);
//		model.put("user", user);
//
//		return mav;

//	
		ModelAndView mav;
		HolderForm holderForm = new HolderForm();

		if (depositCategory != null && depositCategory.equalsIgnoreCase("REVERSE-EMI")) {
			holderForm = depositService.getReverseEMIByDepositId(id);

			mav = new ModelAndView("editReverseEMIBank", "model", model);
		} else {

			List<HolderForm> depositList = depositService.getDepositByDepositId(id);
			holderForm = depositList.get(0);
			model.put("depositHolderList", depositList);
			List<AccountDetails> accountList = accountDetailsDAO
					.findCurrentSavingByCustId(holderForm.getDepositHolder().getCustomerId());
			model.put("accountList", accountList);
			mav = new ModelAndView("editFD", "model", model);
		}
		List<ProductConfiguration> productConfiguration = productConfigurationDAO.findAll();
		for(ProductConfiguration productConfiguration1:productConfiguration) {
			model.put("productConfiguration",productConfiguration1);
			
		}

		model.put("depositHolderForm", holderForm);
		model.put("depositClassification", holderForm.getDeposit().getDepositClassification());
		

		return mav;

	}

	@RequestMapping(value = "/editFDBank", method = RequestMethod.GET)
	public ModelAndView editFDBank(@RequestParam Long id, ModelMap model, HttpServletRequest request,
			RedirectAttributes attributes, @RequestParam String depositCategory, @RequestParam Long holderId)
			throws CustomException {

		ProductConfiguration productConfiguration = productConfigurationDAO.findById(fixedDepositDao.findByDepositId(id).getProductConfigurationId());
		ModelAndView mav;
		HolderForm holderForm = new HolderForm();
		model.put("productConfiguration", productConfiguration);
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

		return mav;

	}
	@RequestMapping(value = "/confirmEditReverseEMI", method = RequestMethod.POST)
	public ModelAndView confirmEditReverseEMI(ModelMap model, @ModelAttribute HolderForm depositHolderForm)
			throws CustomException {

		model.put("depositHolderForm", depositHolderForm);
		return new ModelAndView("confirmEditReverseEMI", "model", model);

	}

	@RequestMapping(value = "/editReverseEMIPost", method = RequestMethod.POST)
	public ModelAndView editReverseEMIPost(ModelMap model, @ModelAttribute HolderForm depositHolderForm,
			RedirectAttributes attributes) throws CustomException {

		Deposit deposit = depositHolderForm.getDeposit();
		DepositHolder depositHolder = depositHolderForm.getDepositHolder();
		Long depositId = deposit.getId();

		Date todayDate = DateService.getCurrentDate();

		Customer customer = getUserDetails();
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
		fixedDepositForm.setCategory(customer.getCategory());
		fixedDepositForm.setMaturityDate(maturityDate);
		fixedDepositForm.setDeductionDay(DateService.getDayOfMonth(deposit.getDueDate()));
		fixedDepositForm.setFdAmount(depositAmount);

		boolean tenureChange = depositHolderForm.getEditDepositForm().getStatusTenure() != "";

		/******* .....new tenure and tenure type ************/

		if (tenureChange) {

			maturityDate = depositHolderForm.getEditDepositForm().getMaturityDateNew();
			tenure = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate);
			tenureType = "Day";

			newInterestRate = calculationService.getDepositInterestRate(tenure, customer.getCategory(),
					deposit.getDepositCurrency(), depositAmount, deposit1.getDepositClassification(),
					deposit1.getPrimaryCitizen(), deposit1.getPrimaryNRIAccountType());

			if (newInterestRate == null) {

				attributes.addFlashAttribute(Constants.ERROR, "No rate available for the selected tenure");

				return new ModelAndView("redirect:fdList");
			}
			fixedDepositForm.setFdInterest(newInterestRate);
			deposit1.setModifiedInterestRate(newInterestRate);
			deposit1.setNewMaturityDate(maturityDate);

			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);

			Distribution lastInterestDistribution = calculationService.calculateInterest(deposit1, depositHolderList, "", null);

			Distribution lastBaseLine = paymentDistributionDAO.getLastBaseLineForTenureChange(depositId);
			Date fromDate = lastBaseLine == null ? DateService.getDate(deposit.getCreatedDate())
					: lastBaseLine.getDistributionDate();
			Date toDate = DateService.getCurrentDate();
			int daysDiff = DateService.getDaysBetweenTwoDates(fromDate, toDate);

//			Float interestRateForAdjustment = depositRateDAO.getInterestRate(customer.getCategory(),
//					deposit.getDepositCurrency(), daysDiff,  Constants.annuityDeposit, deposit.getDepositAmount());
			Float interestRateForAdjustment = calculationService.getDepositInterestRate(daysDiff,
					customer.getCategory(), fixedDepositForm.getCurrency(), deposit.getDepositAmount(),
					Constants.fixedDeposit, fixedDepositForm.getCitizen(), fixedDepositForm.getNriAccountType());

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
			benificiaryDetails.setCustomerId(customer.getId());
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

		Date curDate = DateService.loginDate;
		attributes.addFlashAttribute(Constants.TRANSACTIONID, transactionId);
		attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
		attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Reverse Annuity Updated Successfully");

		return new ModelAndView("redirect:fdSaved");

	}

	@RequestMapping(value = "/compareMaturity", method = RequestMethod.POST)
	public @ResponseBody String demoAjax(@ModelAttribute HolderForm depositHolderForm) throws CustomException {

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

//		float newInterestRate = fdService.getDepositIntRate(tenure,
//				depositHolderForm.getDepositHolder().getDepositHolderCategory(), deposit.getDepositCurrency(),
//				deposit.getDepositAmount(), deposit.getDepositClassification());
		float newInterestRate = calculationService.getDepositInterestRate(tenure,
				depositHolderForm.getDepositHolder().getDepositHolderCategory(), deposit.getDepositCurrency(),
				deposit.getDepositAmount(), deposit.getDepositClassification(), deposit.getPrimaryCitizen(),
				deposit.getNriAccountType());

		fixedDepositForm.setFdInterest(newInterestRate);

		float depositInterestRate = (deposit.getModifiedInterestRate() == null) ? deposit.getInterestRate()
				: deposit.getModifiedInterestRate();

		// Steps for adjustment amount
		// 1. Interest calculation till date
		// 2. Interest Adjustment for reducing tenure

		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
		// 1. Interest calculation till date for alert

		fdService.calculateInterestForAlert(deposit, depositHolderList);

		// 2. Interest Adjustment and penalty for the Withdraw

		Distribution lastBaseLine = paymentDistributionDAO.getLastBaseLineForTenureChange(deposit.getId());

		Float interestRateForAdjustment = 0f;
		Double adjustmentAmount = 0d;
		if (newInterestRate != depositInterestRate) {
			Date fromDate = lastBaseLine == null ? DateService.getDate(deposit.getCreatedDate())
					: lastBaseLine.getDistributionDate();
			Date toDate = DateService.getCurrentDate();
			int daysDiff = DateService.getDaysBetweenTwoDates(fromDate, toDate);

//			interestRateForAdjustment = depositRateDAO.getInterestRate(fixedDepositForm.getCategory(),
//					deposit.getDepositCurrency(), daysDiff, deposit.getDepositClassification(), deposit.getDepositAmount());

			interestRateForAdjustment = calculationService.getDepositInterestRate(daysDiff,
					fixedDepositForm.getCategory(), fixedDepositForm.getCurrency(), deposit.getDepositAmount(),
					deposit.getDepositClassification(), deposit.getPrimaryCitizen(), deposit.getNriAccountType());

			// Adjust the Interest
			adjustmentAmount = fdService.getAmountToAdjustForTenureChangeAlert(deposit.getId(), lastBaseLine,
					interestRateForAdjustment);
		}

		if (deposit.getDepositCategory() == null) {
			//List<Interest> interestClassList = fdService.getInterestBreakupForModification(DateService.getCurrentDate(),
					//depositHolderForm.getEditDepositForm().getMaturityDateNew(), fixedDepositForm, deposit);

			Double maturityAmt = 0d; // interestClassList.get(interestClassList.size() - 1).getInterestSum();
			return adjustmentAmount + "," + newInterestRate + "," + maturityAmt;

		} else {
			return adjustmentAmount + "," + newInterestRate;
		}

	}

//	@RequestMapping(value = "/confirmEditFd", method = RequestMethod.POST)
//	public ModelAndView confirmEditSingle(ModelMap model, @ModelAttribute HolderForm depositHolderForm)
//			throws CustomException {
//		  
//		  Deposit deposit  = fixedDepositDAO.getDeposit(depositHolderForm.getDeposit().getId());
//		model.put("depositHolderForm", depositHolderForm);
//		  model.put("depositPaymentType", deposit.getPaymentType());
//		return new ModelAndView("confirmEditFd", "model", model);
//
//	}

//	@RequestMapping(value = "/editFdPost", method = RequestMethod.POST)
//	public ModelAndView editSinglePost(ModelMap model, @ModelAttribute HolderForm depositHolderForm,
//			RedirectAttributes attributes) throws CustomException {
//
//		// Get the customer details
//		Customer customer = getUserDetails();
//		
//
//		try {
//			// if anybody wont change any value then it will be null only
//			if (depositHolderForm.getEditDepositForm().getStatusTenure() != null
//					&& depositHolderForm.getEditDepositForm().getStatusTenure() != "") {
//				// New Tenure
//				Date maturityDate = depositHolderForm.getEditDepositForm().getMaturityDateNew();
//				Integer tenure = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate);
//				String tenureType = "Day";
//
//				Deposit deposit = fixedDepositDAO.getDeposit(depositHolderForm.getDeposit().getId());
//				// New Interest rate
//				Float newInterestRate = calculationService.getDepositInterestRate(tenure, customer.getCategory(),
//						deposit.getDepositCurrency(), depositHolderForm.getDeposit().getDepositAmount(),
//						deposit.getDepositClassification(), deposit.getPrimaryCitizen(), deposit.getPrimaryNRIAccountType());
//
//				if (newInterestRate == null) {
//					attributes.addFlashAttribute(Constants.ERROR, "No rate available for the selected tenure");
//					return new ModelAndView("redirect:fdList");
//				}
//			}
//
//			// Save the modified data to respective tables
//			DepositModificationMaster depositModificationMaster = depositService.editDepositFromCustomer(depositHolderForm, customer, getCurrentLoggedUserDetails());
//					//.editDepositFromCustomer(depositHolderForm, customer);
//			Date curDate = DateService.loginDate;
//			attributes.addFlashAttribute(Constants.TRANSACTIONID, depositModificationMaster.getTransactionId());
//			attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
//			attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Deposit Updated Successfully");
//
//			return new ModelAndView("redirect:fdSaved");
//		} catch (Exception ex) {
//			return new ModelAndView("redirect:#");
//		}
//	}

	@RequestMapping(value = "/confirmEditFd", method = RequestMethod.POST)
	public ModelAndView checkResult(ModelMap model, @ModelAttribute HolderForm depositHolderForm,
			RedirectAttributes attributes) throws CustomException {

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
		return new ModelAndView("confirmEditFd", "model", model);

	}

	@RequestMapping(value = "/editFdPost", method = RequestMethod.POST)
	public String editPost(ModelMap model, @ModelAttribute HolderForm depositHolderForm, RedirectAttributes attributes)
			throws CustomException {

		try {
			Deposit deposit = depositHolderForm.getDeposit();
			// Long depositId = deposit.getId();
			Customer customer = getUserDetails();
			// Customer customer =
			// customerDAO.getById(depositHolderForm.getDepositHolderList().get(0).getCustomerId());
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

			Date curDate = DateService.loginDate;
			attributes.addFlashAttribute(Constants.TRANSACTIONID, depositModificationMaster.getTransactionId());
			attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
			attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Deposit Updated Successfully");

			return "redirect:fdSaved";
		} catch (Exception ex) {
			return "redirect#";
		}

	}

	public ModelAndView getResult(FixedDepositForm fixedDepositForm, ModelMap model)
			throws ParseException, CustomException {
		fixedDepositForm = fdService.gettingEditFDData(fixedDepositForm);

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("checkResult", "model", model);

	}

	@RequestMapping(value = "/changePayOffs", method = RequestMethod.POST)
	public ModelAndView changePayOffs(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes) throws CustomException {

		Float estimateInterest = fixedDepositForm.getEstimateInterest() - fixedDepositForm.getEstimateServiceTax()
				- fixedDepositForm.getEstimateSESDeduct() - fixedDepositForm.getEstimateTDSDeduct();

		float interest = (float) (FDService.round(estimateInterest, 2));

		fixedDepositForm.setEstimateInterest(interest);

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("changePayOffs", "model", model);

	}

	@RequestMapping(value = "/fdUpdated", method = RequestMethod.GET)
	public ModelAndView fdUpdated(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("fdUpdated", "model", model);

	}

	@RequestMapping(value = "/confirmFDTransferData", method = RequestMethod.POST)
	public ModelAndView confirmFDTransferData(ModelMap model, @ModelAttribute FundTransferForm fundTransferForm)
			throws CustomException {

		model.put("fundTransferForm", fundTransferForm);
		return new ModelAndView("confirmFDTransferData", "model", model);

	}

	@RequestMapping(value = "/confirmWithDrawData", method = RequestMethod.POST)
	public ModelAndView confirmWithDrawData(@ModelAttribute FixedDepositForm fixedDepositForm, ModelMap model)
			throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("confirmWithDrawData", "model", model);
	}

	@RequestMapping(value = "/updateWithDrawSuccess", method = RequestMethod.GET)
	public ModelAndView updateWithDrawSuccess(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("updateWithDrawSuccess", "model", model);

	}

	@RequestMapping(value = "/getReport", method = RequestMethod.POST)
	public ModelAndView getReport(@ModelAttribute FixedDepositForm fixedDepositForm, ModelMap model)
			throws CustomException {

		ModelAndView mav = new ModelAndView();
		Collection<Transaction> transaction = transactionDAO.getFdData(fixedDepositForm.getFdID());
		if (transaction != null && transaction.size() > 0) {

			model.put("transaction", transaction);
			mav = new ModelAndView("getReport", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}
		return mav;
	}

	@RequestMapping(value = "/updateStatus", method = RequestMethod.GET)
	public ModelAndView updateStatus(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {
		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("updateStatus", "model", model);
	}

	/************** Joint Deposit creation *************/
	
	
	
	@RequestMapping(value = "/applyOnlineFD", method = RequestMethod.GET)
	public ModelAndView applyOnlineFD(ModelMap model, String depositType) throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);

		Customer customer = getUserDetails();
		String customerName=customer.getCustomerName();
		model.put("customer", customerName);
		model.put("customerInfo", customer);
		
		if (depositType.equals("joint")) {

			return new ModelAndView("applyFDJointCustomer", "model", model);
		} else {
			return new ModelAndView("applyFD", "model", model);
		}

	}
	

	@RequestMapping(value = "/jointConsortiumDeposit")
	public ModelAndView jointConsortiumDeposit(ModelMap model, HttpServletRequest request,
			@ModelAttribute FixedDepositForm fixedDepositForm, String nriAccType , @RequestParam String customerDetails, @RequestParam String productId) throws CustomException {
		int removeIndexOfList = -1;
		int loopIndex = 0 ;
		Long pId=Long.parseLong(productId);
		Customer customer = getUserDetails();
		Long currentUserId = customer.getId();
		//Customer customer = customerDAO.getById(fixedDepositForm.getId());
		nriAccType = customer.getNriAccountType();
		
		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(currentUserId);
		String customerDetailsJSON = customerDetails;
		
		CustomerDetailsParser customerDetailsParser =  new Gson().fromJson(customerDetailsJSON,CustomerDetailsParser.class);
		
		List<CustomerDetailsParser> customerDetailsParsers = customerDetailsParser.getData();
		
		for (CustomerDetailsParser customerDetailsParser2 : customerDetailsParsers) {
			
			if(Boolean.parseBoolean(customerDetailsParser2.getIsPrimaryHolder()) != true){
			Customer customerJsonParse = customerDAO.getById(Long.parseLong(customerDetailsParser2.getId()));
			customerDetailsParser2.setAddress(customerJsonParse.getAddress());
			customerDetailsParser2.setCustomerName(customerJsonParse.getCustomerName());
			customerDetailsParser2.setContactNum(customerJsonParse.getContactNum());
			customerDetailsParser2.setGender(customerJsonParse.getGender());
			customerDetailsParser2.setEmail(customerJsonParse.getEmail());
			}else {
				removeIndexOfList = loopIndex;
			}
			loopIndex++;
		}
		
		if(removeIndexOfList!=-1)
			customerDetailsParsers.remove(removeIndexOfList);
	
		Double accountBal = 0.0;
		for (int i = 0; i < accountList.size(); i++) {
		
			accountBal = accountList.get(i).getAccountBalance();
		}
		
		fixedDepositForm.setAccountList(accountList);
		fixedDepositForm.setNriAccountType(customer.getNriAccountType());
		fixedDepositForm.setCitizen(customer.getCitizen());
		if (fixedDepositForm.getJointAccounts() == null) {
			List<JointAccount> jointAc = new ArrayList<JointAccount>();
			fixedDepositForm.setJointAccounts(jointAc);
		}
		
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(pId);
		model.put("productConfiguration", productConfiguration);

		model.put("customerDetailsParsers", customerDetailsParsers);
		model.put("customerDetails", customerDetails);
		model.put("customer", customer);
		model.put("nriAccType", nriAccType);
		model.put("fixedDepositForm", fixedDepositForm);
		
        model.put("accountBal", accountBal);
		model.put("todaysDate", date);

		model.put("cashPayment", 0);
		model.put("ddPayment", 0);
		model.put("chequePayment", 0);
		model.put("netBanking", 1);
	


		return new ModelAndView("jointConsortiumDeposit", "model", model);
	}


//	@RequestMapping(value = "/searchSecondaryCustomer", method = RequestMethod.POST)
//	public ModelAndView searchSecondaryCustomer(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
//			throws CustomException {
//
//		/***** searching secondary customer *****/
//
//		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
//
//		customerList = customerDAO.searchCustomer(fixedDepositForm.getCustomerId().toString(),fixedDepositForm.getCustomerName(), fixedDepositForm.getContactNum(),
//				fixedDepositForm.getEmail());
//
//		model.put("customerList", customerList);
//
//		/***** for primary customer *****/
//		Customer customer = customerDAO.getById(fixedDepositForm.getId());
//
//		List<AccountDetails> accountList = accountDetailsDAO.findByCustomerId(fixedDepositForm.getId());
//		fixedDepositForm.setAccountList(accountList);
//		fixedDepositForm.setNriAccountType(customer.getNriAccountType());
//		fixedDepositForm.setCitizen(customer.getCitizen());
//		BankConfiguration bankConfiguration = ratesDAO.findAll();
//		if (bankConfiguration != null) {
//			model.put("bankConfiguration", bankConfiguration);
//		}
//
//		if (fixedDepositForm.getJointAccounts() == null) {
//			List<JointAccount> jointAcc = new ArrayList<JointAccount>();
//			fixedDepositForm.setJointAccounts(jointAcc);
//		}
//
//		model.put("cashPayment", 0);
//		model.put("ddPayment", 0);
//		model.put("chequePayment", 0);
//		model.put("netBanking", 1);
//
//		model.put("customer", customer);
//		model.put("fixedDepositForm", fixedDepositForm);
//		return new ModelAndView("jointConsortiumDeposit", "model", model);
//
//	}

//	@RequestMapping(value = "/addSecondary", method = RequestMethod.POST)
//	public ModelAndView addSecondary(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
//			throws CustomException {
//
//		/**** for primary customer ****/
//		Customer customer = customerDAO.getById(fixedDepositForm.getId());
//
//		List<AccountDetails> accountList = accountDetailsDAO.findByCustomerId(fixedDepositForm.getId());
//		fixedDepositForm.setAccountList(accountList);
//		fixedDepositForm.setCitizen(customer.getCitizen());
//		model.put("customer", customer);
//
//		model.put("fixedDepositForm", fixedDepositForm);
////		BankConfiguration bankConfiguration = ratesDAO.findAll();
////		if (bankConfiguration != null) {
////			model.put("bankConfiguration", bankConfiguration);
////		}
//
//		/***** secondary customer to add *****/
//		Customer secondaryCustomer = customerDAO.getById(fixedDepositForm.getJointAccount().getId());
//
//		JointAccount jointAcc = new JointAccount();
//
//		if (fixedDepositForm.getJointAccounts() == null) {
//			List<JointAccount> jointAc = new ArrayList<JointAccount>();
//			fixedDepositForm.setJointAccounts(jointAc);
//		}
//
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
//			/**** secondary customer already added ****/
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
//		model.put("cashPayment", 0);
//		model.put("ddPayment", 0);
//		model.put("chequePayment", 0);
//		model.put("netBanking", 1);
//
//		model.put("fixedDepositForm", fixedDepositForm);
//
//		return new ModelAndView("jointConsortiumDeposit", "model", model);
//	}

	/*@RequestMapping(value = "/removeAllConsotium", method = RequestMethod.POST)
	public ModelAndView removeAllConsotium(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {

//		*//****** for removing selected consortium customer ******//*
		List<JointAccount> jointAccList = new ArrayList<JointAccount>();
		fixedDepositForm.setJointAccounts(jointAccList);

		*//****** for primary customer ******//*
		Customer customer = customerDAO.getById(fixedDepositForm.getId());

		List<AccountDetails> accountList = accountDetailsDAO.findByCustomerId(fixedDepositForm.getId());
		fixedDepositForm.setAccountList(accountList);

		if (fixedDepositForm.getJointAccounts() == null) {
			List<JointAccount> jointAc = new ArrayList<JointAccount>();
			fixedDepositForm.setJointAccounts(jointAc);
		}

		BankConfiguration bankConfiguration = ratesDAO.findAll();
		if (bankConfiguration != null) {
			model.put("bankConfiguration", bankConfiguration);
		}

		model.put("cashPayment", 0);
		model.put("ddPayment", 0);
		model.put("chequePayment", 0);
		model.put("netBanking", 1);

		model.put("customer", customer);
		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("jointConsortiumDeposit", "model", model);
	}*/

//	@RequestMapping(value = "/removeSecondaryCustomer", method = RequestMethod.POST)
//	public ModelAndView removeSecondaryCustomer(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
//			Long id, Long customerId) throws CustomException {
//
//		/****** for primary customer ******/
//
//		Customer customer = customerDAO.getById(customerId);
//
//		List<AccountDetails> accountList = accountDetailsDAO.findByCustomerId(fixedDepositForm.getId());
//		fixedDepositForm.setAccountList(accountList);
//
//		/****** removing customer ******/
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
//		model.put("cashPayment", 0);
//		model.put("ddPayment", 0);
//		model.put("chequePayment", 0);
//		model.put("netBanking", 1);
//
//		model.put("customer", customer);
//		model.put("fixedDepositForm", fixedDepositForm);
//		return new ModelAndView("jointConsortiumDeposit", "model", model);
//
//	}
//
	@RequestMapping(value = "/jointConsortiumDepositSummary", method = RequestMethod.POST)
	public ModelAndView jointConsortiumDepositSummary(@ModelAttribute FixedDepositForm fixedDepositForm, ModelMap model,
				RedirectAttributes attributes,@RequestParam(required = false,value="customerDetails") String customerDetail ) throws CustomException {

			Integer days = 0;
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

			Customer customerDetails = customerDAO.getById(fixedDepositForm.getId());
			fixedDepositForm.setCitizen(customerDetails.getCitizen());
			Date currentDate = DateService.getCurrentDate();
			Date maturityDate = null;
			/*if (fixedDepositForm.getFdTenureType().equalsIgnoreCase("Month")) {

				days = fixedDepositForm.getFdTenure() * 30;
				maturityDate = DateService.generateMonthsDate(currentDate, fixedDepositForm.getFdTenure());

			} else if (fixedDepositForm.getFdTenureType().equalsIgnoreCase("Year")) {

				days = fixedDepositForm.getFdTenure() * 365;
				maturityDate = DateService.generateYearsDate(currentDate, fixedDepositForm.getFdTenure());
			} else if (fixedDepositForm.getFdTenureType().equalsIgnoreCase("Day")) {

				days = fixedDepositForm.getFdTenure();
				maturityDate = DateService.generateDaysDate(currentDate, fixedDepositForm.getFdTenure());
			}*/
			
	        days = fixedDepositForm.getTotalTenureInDays();
			
			maturityDate = DateService.generateDaysDate(DateService.getCurrentDate(), fixedDepositForm.getTotalTenureInDays());
			
			days = days + 1;

			// Get the customer details to find out the age and category
			Customer customer = getUserDetails();
			String category = calculationService.geCustomerActualCategory(customer);// fixedDepositForm.getCategory();

			Float rateOfInterest = calculationService.getDepositInterestRate(days, category, fixedDepositForm.getCurrency(),
					fixedDepositForm.getFdAmount(), fixedDepositForm.getDepositClassification(),
					fixedDepositForm.getCitizen(), fixedDepositForm.getNriAccountType());

			fixedDepositForm.setCategory(category);
			if (rateOfInterest == null) {
				attributes.addFlashAttribute(Constants.ERROR, Constants.invalidTenure);

				return new ModelAndView("redirect:jointConsortiumDeposit");

			}

			fixedDepositForm.setMaturityDate(maturityDate);
			fixedDepositForm
					.setFdDeductDate(fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));
			fixedDepositForm.setCategory(customer.getCategory());

			Date todaysDate = DateService.getCurrentDate();

			fixedDepositForm.setFdCreditAmount(rateOfInterest);
			fixedDepositForm.setEstimateTDSDeduct(0f);


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
			model.put("fixedDepositForm", fixedDepositForm);
			model.put("customerDetails",customerDetail);
			return new ModelAndView("jointConsortiumDepositSummaryCust", "model", model);

		}

	@RequestMapping(value = "/savePostJointConsortium", method = RequestMethod.POST)
	public String savePostJointConsortium(@ModelAttribute FixedDepositForm fixedDepositForm,
				RedirectAttributes attributes, HttpServletRequest request) throws CustomException {

		
		Long mopId = Long.parseLong(fixedDepositForm.getDepositForm().getPaymentMode());
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPaymentById(mopId);
		
			/***** Saving Deposit *****/
			String transactionId = fdService.generateRandomString();
			Deposit deposit = new Deposit();
			int days = fixedDepositForm.getDaysValue() != null ? fixedDepositForm.getDaysValue() : 0;
			deposit.setAccountNumber(fdService.generateRandomString());
			deposit.setDepositAmount(fixedDepositForm.getFdAmount());
			deposit.setDepositArea(fixedDepositForm.getDepositArea());
			deposit.setDepositType(fixedDepositForm.getDepositType());
			deposit.setDueDate(fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));
			deposit.setFlexiRate("Yes");
			deposit.setInterestRate(fixedDepositForm.getFdCreditAmount());
			deposit.setModifiedInterestRate(fixedDepositForm.getFdCreditAmount());
			deposit.setAccountAccessType(fixedDepositForm.getAccountAccessType());
			if (fixedDepositForm.getAccountNo() != null && !fixedDepositForm.getAccountNo().equals("")) {
				String accArray[] = fixedDepositForm.getAccountNo().split(",");
				deposit.setLinkedAccountNumber(accArray[0]);
			}
			deposit.setProductConfigurationId(fixedDepositForm.getProductConfigurationId());
			deposit.setMaturityAmount(fixedDepositForm.getEstimatePayOffAmount());
			deposit.setNewMaturityAmount(fixedDepositForm.getEstimatePayOffAmount());
			deposit.setCurrentBalance(Double.valueOf(deposit.getDepositAmount()));
			deposit.setDepositCurrency(fixedDepositForm.getCurrency());
			deposit.setPaymentModeId(mopId);
			deposit.setPaymentMode(mop.getPaymentMode());

			Date date = DateService.getCurrentDateTime();
			deposit.setPaymentType(fixedDepositForm.getPaymentType());
			deposit.setStatus(Constants.OPEN);
			deposit = fdService.getMaturityAndTenureInformation(deposit, fixedDepositForm);
			deposit.setCreatedDate(date);
			deposit.setModifiedDate(date);
			if (fixedDepositForm.getFdAmount() > 10000000) {
				deposit.setApprovalStatus(Constants.PENDING);
				deposit.setComment(null);
			} else {
				deposit.setApprovalStatus(Constants.APPROVED);
				deposit.setComment(Constants.APPROVED);
			}
			deposit.setDepositAccountType(fixedDepositForm.getDepositAccountType());
			deposit.setDepositClassification(fixedDepositForm.getDepositClassification());
			deposit.setTaxSavingDeposit(fixedDepositForm.getTaxSavingDeposit());	
			deposit.setIsMaturityDisbrsmntInLinkedAccount(fixedDepositForm.getIsMaturityDisbrsmntInLinkedAccount());
			
			Customer customerDetails = customerDAO.getById(fixedDepositForm.getId());
			fixedDepositForm.setCitizen(customerDetails.getCitizen());

			/***** PAY OFF DATE CALCULATION *****/
			deposit.setPayOffInterestType(fixedDepositForm.getPayOffInterestType());

			if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {
				deposit.setPayOffDueDate(fixedDepositForm.getPayoffDate());
			}

			String user = getCurrentLoggedUserName();
			deposit.setTransactionId(transactionId);
			deposit.setCreatedBy(user);
			deposit.setMaturityInstruction(fixedDepositForm.getMaturityInstruction());
			deposit.setPrimaryCitizen(fixedDepositForm.getCitizen());
			deposit.setPrimaryCustomerCategory(fixedDepositForm.getCategory());

			if (fixedDepositForm.getCitizen().equalsIgnoreCase(Constants.NRI)) {
				deposit.setNriAccountType(fixedDepositForm.getNriAccountType());
				deposit.setPrimaryNRIAccountType(fixedDepositForm.getNriAccountType());
			}

			deposit.setDeductionDay(fixedDepositForm.getDeductionDay());
			Deposit newDeposit = fixedDepositDAO.saveFD(deposit);

			/***** Saving DepositHolder *****/

			List<DepositHolder> depositHolderList = new ArrayList<>();
			DepositHolder depositHolder = new DepositHolder();
			depositHolder.setContribution(fixedDepositForm.getUserContribution());
			depositHolder.setCustomerId(Long.valueOf(fixedDepositForm.getId()));
			depositHolder.setDepositHolderStatus(Constants.PRIMARY);
			depositHolder.setDepositHolderCategory(fixedDepositForm.getCategory());
			depositHolder.setDepositId(newDeposit.getId());
			depositHolder.setCitizen(customerDetails.getCitizen());
		
			
			depositHolder.setIsMaturityDisbrsmntInSameBank(fixedDepositForm.getIsMaturityDisbrsmntInSameBank());
			
			depositHolder.setMaturityDisbrsmntAccHolderName(fixedDepositForm.getMaturityDisbrsmntAccHolderName());
			
			depositHolder.setMaturityDisbrsmntAccNumber(fixedDepositForm.getMaturityDisbrsmntAccNumber());
			
			depositHolder.setMaturityDisbrsmntTransferType(fixedDepositForm.getMaturityDisbrsmntTransferType());
			
			depositHolder.setMaturityDisbrsmntBankName(fixedDepositForm.getMaturityDisbrsmntBankName());

			depositHolder.setMaturityDisbrsmntBankIFSCCode(fixedDepositForm.getMaturityDisbrsmntBankIFSCCode());
			if (customerDetails.getCitizen().equals(Constants.NRI)) {
				customerDetails.setNriAccountType(customerDetails.getNriAccountType());

			}
			if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {

				depositHolder.setInterestType(fixedDepositForm.getInterstPayType());

				if (fixedDepositForm.getInterstPayType().equals("PERCENT")) {
					depositHolder.setInterestPercent(fixedDepositForm.getInterestPercent());

				}

				else {
					depositHolder.setInterestAmt(fixedDepositForm.getInterestPayAmount());
				}

				depositHolder.setPayOffAccountType(fixedDepositForm.getFdPayOffAccount());

				if (fixedDepositForm.getFdPayOffAccount().equals("FD Account")) {
				}

				else if (fixedDepositForm.getFdPayOffAccount().equals("Saving Account")) {
					depositHolder.setTransferType(fixedDepositForm.getOtherPayTransfer());
					depositHolder.setNameOnBankAccount(fixedDepositForm.getOtherName());
					depositHolder.setAccountNumber(fixedDepositForm.getOtherAccount().toString());

				}

				else if (fixedDepositForm.getFdPayOffAccount().equals("Other")) {
					depositHolder.setTransferType(fixedDepositForm.getOtherPayTransfer());
					depositHolder.setNameOnBankAccount(fixedDepositForm.getOtherName());
					depositHolder.setAccountNumber(fixedDepositForm.getOtherAccount().toString());
					depositHolder.setBankName(fixedDepositForm.getOtherBank());
					depositHolder.setIfscCode(fixedDepositForm.getOtherIFSC());
				}
			}

			DepositHolder depositHolderNew = depositHolderDAO.saveDepositHolder(depositHolder);
			depositHolderList.add(depositHolderNew);

			/***** SECONDARY CUSTOMER . *****/
			Set<DepositHolder> set = new HashSet<DepositHolder>();
			set.add(depositHolder);

			for (int i = 0; i < fixedDepositForm.getJointAccounts().size(); i++) {

				/***** Saving DepositHolder *****/

				DepositHolder depositHolderJoint = new DepositHolder();
				depositHolderJoint.setRelationship(fixedDepositForm.getJointAccounts().get(i).getRelationship());
				depositHolderJoint.setContribution(fixedDepositForm.getJointAccounts().get(i).getContributionPercent());
				depositHolderJoint.setCustomerId(fixedDepositForm.getJointAccounts().get(i).getId());

				Customer secondaryCustomer = customerDAO.getById(fixedDepositForm.getJointAccounts().get(i).getId());
				depositHolderJoint.setCitizen(secondaryCustomer.getCitizen());
				if (secondaryCustomer.getCitizen().equalsIgnoreCase(Constants.NRI)) {
					depositHolderJoint.setNriAccountType(secondaryCustomer.getNriAccountType());
				}

				depositHolderJoint.setDepositHolderStatus(Constants.SECONDARY);
				String depositHolderCategory = customerDAO.getById(depositHolderJoint.getCustomerId()).getCategory();
				depositHolderJoint.setDepositHolderCategory(depositHolderCategory);
				depositHolderJoint.setDepositId(newDeposit.getId());
				
				depositHolderJoint.setIsMaturityDisbrsmntInSameBank(fixedDepositForm.getIsMaturityDisbrsmntInSameBank());
				
				depositHolderJoint.setMaturityDisbrsmntAccHolderName(fixedDepositForm.getMaturityDisbrsmntAccHolderName());
				
				depositHolderJoint.setMaturityDisbrsmntAccNumber(fixedDepositForm.getMaturityDisbrsmntAccNumber());
				
				depositHolderJoint.setMaturityDisbrsmntTransferType(fixedDepositForm.getMaturityDisbrsmntTransferType());
				
				depositHolderJoint.setMaturityDisbrsmntBankName(fixedDepositForm.getMaturityDisbrsmntBankName());

				depositHolderJoint.setMaturityDisbrsmntBankIFSCCode(fixedDepositForm.getMaturityDisbrsmntBankIFSCCode());

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

					if (depositHolderJoint.getPayOffAccountType().equals("FD Account")) {
					}

					else if (depositHolderJoint.getPayOffAccountType().equals("Saving Account")) {
						/*
						 * depositHolderJoint.setTransferType(
						 * fixedDepositForm.getJointAccounts().get(i).
						 * getContributions().getTransferModeOne());
						 */
						depositHolderJoint.setNameOnBankAccount(
								fixedDepositForm.getJointAccounts().get(i).getContributions().getBeneficiaryOne());
						depositHolderJoint.setAccountNumber(fixedDepositForm.getJointAccounts().get(i).getContributions()
								.getBeneficiaryAccOne().toString());

					}

					else if (depositHolderJoint.getPayOffAccountType().equals("Other")) {
						depositHolderJoint.setTransferType(
								fixedDepositForm.getJointAccounts().get(i).getContributions().getTransferModeOne());
						depositHolderJoint.setNameOnBankAccount(
								fixedDepositForm.getJointAccounts().get(i).getContributions().getBeneficiaryOne());
						depositHolderJoint.setAccountNumber(fixedDepositForm.getJointAccounts().get(i).getContributions()
								.getBeneficiaryAccOne().toString());
						depositHolderJoint.setBankName(
								fixedDepositForm.getJointAccounts().get(i).getContributions().getBeneficiaryBankOne());
						depositHolderJoint.setIfscCode(
								fixedDepositForm.getJointAccounts().get(i).getContributions().getBeneficiaryIFSCOne());
					}
				}
				depositHolderJoint.setDepositId(newDeposit.getId());
				DepositHolder depositHolderJointNew = depositHolderDAO.saveDepositHolder(depositHolderJoint);
				depositHolderList.add(depositHolderJointNew);

				
				set.add(depositHolderJointNew);
				
				/***** Saving DepositHolderNominee *****/

				DepositHolderNominees nomineeJoint = new DepositHolderNominees();

				nomineeJoint.setNomineeName(fixedDepositForm.getJointAccounts().get(i).getNominee().getName());
				nomineeJoint.setNomineeAge(fixedDepositForm.getJointAccounts().get(i).getNominee().getAge().toString());
				nomineeJoint
						.setNomineeRelationship(fixedDepositForm.getJointAccounts().get(i).getNominee().getRelationship());
				nomineeJoint.setNomineeAddress(fixedDepositForm.getJointAccounts().get(i).getNominee().getAddress());
				nomineeJoint.setNomineePan(fixedDepositForm.getJointAccounts().get(i).getNominee().getPanNo());
				nomineeJoint.setNomineeAadhar(fixedDepositForm.getJointAccounts().get(i).getNominee().getAadharNo());

				nomineeJoint.setDepositHolderId(depositHolderJointNew.getId());

				int ageJoint = Integer
						.parseInt(fixedDepositForm.getJointAccounts().get(i).getNominee().getAge().toString());
				if (ageJoint < 18) {
					nomineeJoint.setGaurdianName(fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianName());
					nomineeJoint.setGaurdianAge(
							Float.valueOf(fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianAge()));
					nomineeJoint.setGaurdianAddress(
							fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianAddress());
					nomineeJoint.setGaurdianRelation(
							fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianRelation());
					nomineeJoint.setGaurdianPan(fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianPanNo());
					nomineeJoint.setGaurdianAadhar(
							fixedDepositForm.getJointAccounts().get(i).getNominee().getGaurdianAadharNo());
				}

				nomineeDAO.saveNominee(nomineeJoint);

			}

			newDeposit.setDepositHolder(set);
			fixedDepositDAO.updateFD(newDeposit);
			/***** Saving DepositHolderNominee *....primary customer ****/

			DepositHolderNominees nominee = new DepositHolderNominees();

			nominee.setNomineeName(fixedDepositForm.getNomineeName());
			nominee.setNomineeAge(fixedDepositForm.getNomineeAge());
			nominee.setNomineeRelationship(fixedDepositForm.getNomineeRelationShip());
			nominee.setNomineeAddress(fixedDepositForm.getNomineeAddress());
			nominee.setDepositHolderId(depositHolderNew.getId());
			nominee.setNomineeAadhar(fixedDepositForm.getNomineeAadhar());
			nominee.setNomineePan(fixedDepositForm.getNomineePan());

			int age = Integer.parseInt(fixedDepositForm.getNomineeAge());

			if (age < 18) {

				nominee.setGaurdianName(fixedDepositForm.getGuardianName());
				nominee.setGaurdianAge(Float.valueOf(fixedDepositForm.getGuardianAge()));
				nominee.setGaurdianAddress(fixedDepositForm.getGuardianAddress());
				nominee.setGaurdianRelation(fixedDepositForm.getGuardianRelationShip());
				nominee.setGaurdianAadhar(fixedDepositForm.getGaurdianAadhar());
				nominee.setGaurdianPan(fixedDepositForm.getGaurdianPan());
			}

			nomineeDAO.saveNominee(nominee);

			/***** Deduction from Linked account *****/

			AccountDetails accountDetails = null;
			if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {
				accountDetails = accountDetailsDAO.findByAccountNo(fixedDepositForm.getDepositForm().getLinkedAccountNo());
				accountDetails.setAccountBalance(
						accountDetails.getAccountBalance() - Float.valueOf(fixedDepositForm.getDepositForm().getFdPay()));
				accountDetailsDAO.updateUserAccountDetails(accountDetails);
			}

			/***** SAVING PAYMENT INFO *****/

			Double amountPaid = FDService.round(Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()), 2);
			
			
			Payment payment = new Payment();
			payment.setDepositId(newDeposit.getId());
			payment.setAmountPaid(amountPaid);
			payment.setDepositHolderId(depositHolderNew.getId());
			payment.setPaymentModeId(mopId);
			payment.setPaymentMode(mop.getPaymentMode());
			payment.setPaymentDate(date);
			payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getAccountType());

			String fromAccountNo = "";

			if (mop.getPaymentMode().equalsIgnoreCase("Cash")) {
				fromAccountNo = "";
			}

			if (mop.getPaymentMode().equalsIgnoreCase("DD")) {

				payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
				payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
				payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
				payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());

				// For Accounting Entries Only
				fromAccountNo = fixedDepositForm.getDepositForm().getChequeNo();
			}

			if (mop.getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {

				payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getDepositForm().getAccountType());
				// payment.setLinkedAccNoForFundTransfer(fixedDepositForm.getDepositForm().getLinkedAccountNo());

				String accountNo = fixedDepositForm.getDepositForm().getLinkedAccountNo(); // fixedDepositForm.getAccountNo();
				if (accountNo.contains(",")) {
					accountNo = accountNo.substring(0, accountNo.indexOf(","));
				}
				payment.setLinkedAccNoForFundTransfer(accountNo);

				if (accountDetails != null)
					payment.setLinkedAccTypeForFundTransfer(accountDetails.getAccountType());

				// For Accounting Entries Only
				fromAccountNo = accountNo;

			}

			if (mop.getPaymentMode().equalsIgnoreCase("Net Banking")) {

				payment.setTransferType(fixedDepositForm.getOtherPayTransfer1());
				payment.setNameOnBankAccount(fixedDepositForm.getOtherName1());
				payment.setAccountNumber(fixedDepositForm.getOtherAccount1().toString());

				if (fixedDepositForm.getFdPayType().equals("sameBank")) {

				}
				if (fixedDepositForm.getFdPayType().equals("differentBank")) {
					payment.setIfscCode(fixedDepositForm.getOtherIFSC1());
					payment.setBank(fixedDepositForm.getOtherBank1());
				}

			}

			if (mop.getPaymentMode().equalsIgnoreCase("Card Payment")) {

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

			}

			payment.setTransactionId(transactionId);
			payment.setCreatedBy(getCurrentLoggedUserName());
			payment = paymentDAO.insertPayment(payment);

			/**** insert in distribution ****/

			// Insert in Distributor and HolderWiseDistributor
			calculationService.insertInPaymentDistribution(deposit, depositHolderList,
					amountPaid, payment.getId(), Constants.PAYMENT,
					null, null, null, null, null, null, null);

			// Insert in DepositSummary and DepositHolderWiseSummary
			DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.PAYMENT,
					amountPaid, null, null, null, null, depositHolderList, null, null, null);

			// Insert in Journal & Ledger
			// --------------------------------------------------------------------
			ledgerService.insertJournal(newDeposit.getId(), customerDetails.getId(), DateService.getCurrentDate(),
					fromAccountNo, newDeposit.getAccountNumber(), Event.OPEN_DEPOSIT.getValue(),
					amountPaid, mopId, depositSummary.getTotalPrincipal(), transactionId);
			// --------------------------------------------------------------------



			Date curDate = DateService.loginDate;
			attributes.addFlashAttribute(Constants.TRANSACTIONID, deposit.getTransactionId());
			attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
			attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Deposit Created Successfully");

			return "redirect:fdSaved";

		}

	@RequestMapping(value = "/withdrawJointConsortiumFD", method = RequestMethod.GET)
	public ModelAndView withdrawJointConsortiumFD(ModelMap model) throws CustomException {

		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("withdrawJointConsortiumFD");
	}

	@RequestMapping(value = "/closeFDList", method = RequestMethod.GET)
	public ModelAndView closeFDList(ModelMap model, RedirectAttributes attributes) throws CustomException {

		ModelAndView mav = new ModelAndView();
		Customer cus = getUserDetails();
		Collection<Deposit> fixedDeposits = fixedDepositDAO.getDeposits(cus.getId());
		if (fixedDeposits != null && fixedDeposits.size() > 0) {

			model.put("fixedDeposits", fixedDeposits);
			mav = new ModelAndView("customerClosingFDList", "model", model);

		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/closeFD", method = RequestMethod.GET)
	public ModelAndView closeFD(@RequestParam("id") Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException{
		Deposit deposit = fixedDepositDAO.getDeposit(id);
		if (deposit != null) {
			if (deposit.getProductConfigurationId() != null) {
				ProductConfiguration productConfiguration = productConfigurationDAO
						.findById(deposit.getProductConfigurationId());
				if (productConfiguration != null) {
					if(productConfiguration.getIsPrematureClosingioAllowedForWithdraw()!=null) {
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
					}else {
						attributes.addFlashAttribute("error", "Is Premature data not found");
						return new  ModelAndView("redirect:closeFDList");
					}

				} else {
					attributes.addFlashAttribute("error", "Product not found");
					return new  ModelAndView("redirect:closeFDList");
					// product not found

				}
			}else {
				attributes.addFlashAttribute("error", "Product id not found");
				return new  ModelAndView("redirect:closeFDList");
				// product not found

			}


		} else {
			attributes.addFlashAttribute("error", "Deposit not found");
			return new ModelAndView("redirect:closeFDList");
			// Deposit not found
		}
		attributes.addFlashAttribute(Constants.TRANSACTIONID, fdService.generateRandomString());
		attributes.addFlashAttribute(Constants.TRANSACTIONDATE, DateService.getCurrentDate());
		attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Closed Successfully");
		return new ModelAndView("redirect:fdSaved");

	}

	@RequestMapping(value = "/withDrawDepositSearch", method = RequestMethod.GET)
	public ModelAndView withDrawFD(ModelMap model, @ModelAttribute WithdrawForm withdrawForm,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		List<AccountDetails> accList = accountDetailsDAO.findCurrentSavingByCustId(getUserDetails().getId());
		model.put("withdrawForm", withdrawForm);
		model.put("accList", accList);
		return new ModelAndView("depositPaymentSearch", "model", model);

	}

	@RequestMapping(value = "/withdrawPaymentList", method = RequestMethod.GET)
	public ModelAndView withdrawPaymentList(ModelMap model) throws CustomException {
		//BankConfiguration bankConfiguration = ratesDAO.findAll();
		Customer cus = getUserDetails();
		List<DepositForm> depositList = fixedDepositDao.getDepositByCustomerId(cus.getId());

		if (depositList.size() > 0) {

			List<AccountDetails> accList = accountDetailsDAO.findCurrentSavingByCustId(cus.getId());
			model.put("accList", accList);
			model.put("withdrawForm", withdrawForm);
			model.put("depositList", depositList);
			model.put("customerName", cus.getCustomerName());
			//model.put("payAndWithdrawTenure", bankConfiguration.getPayAndWithdrawTenure());

		} else {

			model.put("error", "No deposit found");
			return new ModelAndView("noDataFoundCusm", "model", model);

		}

		return new ModelAndView("depositPaymentSearch", "model", model);
	}

	@RequestMapping(value = "/withdrawDeposit", method = RequestMethod.POST)
	public ModelAndView withdrawDeposit(ModelMap model, WithdrawForm withdrawForm, RedirectAttributes attributes)
			throws CustomException {

		Distribution paymentDistribution = fixedDepositDAO.withdrawDepositAmt(withdrawForm.getDepositId());
		ProductConfiguration _pc=null;
		List<DepositForm> deposit = paymentDAO.paymentAccountNumber(withdrawForm.getAccountNumber().trim());
		 

		if (paymentDistribution == null) {
			return new ModelAndView("noDataFoundCusm", "model", model);
		}
		_pc=productConfigurationDAO.findById(Long.parseLong(deposit.get(0).getProductConfigurationId().toString()));
		if(_pc==null)
		{
			_pc=productConfigurationDAO.findByProductCode(deposit.get(0).getProductConfigurationId().toString());
		}
		if (_pc.getIsWithdrawAllowed() == 0) 
		{
			
				model.put(Constants.ERROR, "Withdraw is not allowed for this deposit. you can not withdraw.");
				return new ModelAndView("depositPaymentSearch", "model", model);
			
			/*model.put(Constants.ERROR, Constants.withdrawLockingPeriodError);
			return new ModelAndView("withDrawFDSearch", "model", model);*/
		}
		List<AccountDetails> accList = accountDetailsDAO.findCurrentSavingByCustId(getUserDetails().getId());
		List<DepositHolderForm> depositHolderList = depositHolderDAO.getDepositHoldersName(withdrawForm.getDepositId());

		withdrawForm.setCompoundVariableAmt(paymentDistribution.getCompoundVariableAmt());
		withdrawForm.setCompoundFixedAmt(paymentDistribution.getCompoundFixedAmt());
		model.put("withdrawForm", withdrawForm);
		model.put("depositHolderList", depositHolderList);
		model.put("accList", accList);
		model.put("config", _pc);

		if (_pc.getLockingPeriodForWithdraw() != null) {
			String lockingPeriodForWithdraw = _pc.getLockingPeriodForWithdraw();
			String[] arrlockingPeriodForTopup = lockingPeriodForWithdraw.split(",");
			
			
			Date lockingDate = deposit.get(0).getCreatedDate();
			for(int i = 0; i<arrlockingPeriodForTopup.length; i++)
			{
				if(arrlockingPeriodForTopup[i]!=null)
				{
					if(arrlockingPeriodForTopup[i].toString().endsWith("Year"))
					{
						String num = arrlockingPeriodForTopup[i].replaceAll(" Year", "" );
						lockingDate = DateService.addYear(lockingDate, Integer.parseInt(num));
					}
					else if(arrlockingPeriodForTopup[i].toString().endsWith("Month"))
					{
						String num = arrlockingPeriodForTopup[i].replaceAll(" Month", "");
						lockingDate = DateService.addMonths(lockingDate, Integer.parseInt(num));
					}
					else if(arrlockingPeriodForTopup[i].toString().endsWith("Day"))
					{
						String num = arrlockingPeriodForTopup[i].replaceAll(" Day", "");
						lockingDate = DateService.addDays(lockingDate, Integer.parseInt(num));
					}
				}
			}
			
			if (DateService.getDaysBetweenTwoDates(lockingDate,
					DateService.getCurrentDate()) <0) {
				Customer cus = getUserDetails();
				List<DepositForm> depositList = fixedDepositDao.getDepositByCustomerId(cus.getId());
				model.put("withdrawForm", withdrawForm);
				model.put("depositList", depositList);
				model.put("customerName", cus.getCustomerName());
				model.put("payAndWithdrawTenure", _pc.getPreventionOfWithdrawBeforeMaturity());
				model.put(Constants.ERROR, Constants.withdrawLockingPeriodError);
				return new ModelAndView("depositPaymentSearch", "model", model);
			}
		}

		return new ModelAndView("depositWithdraw", "model", model);

	}

	// @Transactional

	// @Transactional
	@RequestMapping(value = "/saveWithdrawAmmount", method = RequestMethod.POST)
	public ModelAndView saveWithdrawAmmount(ModelMap model, @ModelAttribute WithdrawForm withdrawForm,
			RedirectAttributes attributes) throws CustomException {

		EndUser user = getCurrentLoggedUserDetails();
		Customer customer = customerDAO.getByUserName(getCurrentLoggedUserName());
		fdService.withdrawOperation(withdrawForm, user.getUserName());
		try {

			String tex = Constants.CUSTOMERREMENDERSUBJECT;
			SimpleMailMessage emails = new SimpleMailMessage();
			emails.setTo(customer.getEmail());
			emails.setSubject(tex);
			emails.setText(Constants.HELLO + getCurrentLoggedUserName() + Constants.WITHDRAWBODY1
					+ withdrawForm.getWithdrawAmount() + Constants.WITHDRAWDATE + DateService.getCurrentDate()
					+ Constants.BANKSIGNATURE);
			mailSender.send(emails);
			SimpleMailMessage emails1 = new SimpleMailMessage();
			emails1.setTo(customer.getEmail());

		} catch (Exception e) {
			System.out.println(e.getMessage() + e);

		}
		Date curDate = DateService.loginDate;

		/*
		 * 
		 * // delete from interest saved after the current date Interest lastInterest =
		 * interestDAO.deleteByDepositIdAndDate(withdrawForm.getDepositId()); //
		 * DepositHolder depositHolder = //
		 * depositHolderDAO.findById(withdrawForm.getDepositHolderId());
		 * 
		 * // Steps // 1. Interest calculation till date // 2. Withdraw the amount // 3.
		 * Interest Adjustment and penalty for the Withdraw Deposit deposit =
		 * fixedDepositDAO.findById(withdrawForm.getDepositId()); List<DepositHolder>
		 * depositHolderList =
		 * depositHolderDAO.getDepositHolders(withdrawForm.getDepositId());
		 * 
		 * // 1. Interest calculation till date //
		 * ------------------------------------------------------------------ // This
		 * will be considered here as last distriburion Distribution
		 * lastInterestDistribution = fdService.calculateInterest(deposit,
		 * depositHolderList); //
		 * ------------------------------------------------------------------
		 * 
		 * // 2. Withdraw the amount //
		 * ------------------------------------------------------------------
		 * WithdrawDeposit withdrawDeposit = new WithdrawDeposit();
		 * withdrawDeposit.setDepositHolderId(withdrawForm.getDepositHolderId()) ;
		 * withdrawDeposit.setDepositId(withdrawForm.getDepositId());
		 * withdrawDeposit.setWithdrawAmount(withdrawForm.getWithdrawAmount());
		 * withdrawDeposit.setModeOfPayment(withdrawForm.getModeOfPayment()); Customer
		 * customerDetails = getUserDetails();
		 * 
		 * withdrawDeposit.setTransactionId(fdService.generateRandomString());
		 * withdrawDeposit.setCreatedBy(customerDetails.getUserName());
		 * 
		 * withdrawDeposit.setDepositHolderId(withdrawForm.getDepositHolderId()) ;
		 * withdrawDeposit.setDepositId(withdrawForm.getDepositId());
		 * withdrawDeposit.setWithdrawAmount(withdrawForm.getWithdrawAmount());
		 * //withdrawDeposit.setModeOfPayment(withdrawForm.getModeOfPayment());
		 * withdrawDeposit.setModeOfPayment(Constants.FUNDTRANSFER);
		 * 
		 * withdrawDeposit.setTransactionId(fdService.generateRandomString());
		 * withdrawDeposit.setCustomerName(customerDetails.getCustomerName()); if
		 * (withdrawForm.getModeOfPayment().equalsIgnoreCase("netBanking") &&
		 * withdrawForm.getFdPayType().equalsIgnoreCase("SameBank")) {
		 * withdrawDeposit.setTransferType(withdrawForm.getOtherPayTransfer1());
		 * withdrawDeposit.setBenificiaryName(withdrawForm.getOtherName1());
		 * withdrawDeposit.setAccountNumber(withdrawForm.getOtherAccount1()); } if
		 * (withdrawForm.getModeOfPayment().equalsIgnoreCase("netBanking") &&
		 * withdrawForm.getFdPayType().equalsIgnoreCase("DifferentBank")) {
		 * withdrawDeposit.setAccountNumber(withdrawForm.getOtherAccount1());
		 * withdrawDeposit.setTransferType(withdrawForm.getOtherPayTransfer1());
		 * withdrawDeposit.setBenificiaryName(withdrawForm.getOtherName1());
		 * withdrawDeposit.setBank(withdrawForm.getBank());
		 * withdrawDeposit.setIfscCode(withdrawForm.getOtherIFSC1()); } withdrawDeposit
		 * = paymentDistributionDAO.insertWithDrawPayment(withdrawDeposit);
		 * 
		 * String action = "Withdraw"; Double withdrawAmt =
		 * withdrawForm.getWithdrawAmount();
		 * 
		 * double compoundVariableAmt = 0d; double compoundFixedAmt = 0d; if
		 * (lastInterestDistribution.getCompoundVariableAmt() >= withdrawAmt) {
		 * compoundVariableAmt = lastInterestDistribution.getCompoundVariableAmt() -
		 * withdrawAmt; compoundFixedAmt =
		 * lastInterestDistribution.getCompoundFixedAmt();//
		 * withdrawForm.getCompoundFixedAmt(); }
		 * 
		 * Double withdrawAmtDeduction = withdrawAmt * -1;
		 * 
		 * compoundFixedAmt = withdrawForm.getCompoundFixedAmt();
		 * 
		 * Distribution distribution =
		 * paymentDistributionDAO.getLastPaymentDistribution(withdrawForm.
		 * getDepositId()); Distribution paymentDistribution = new Distribution();
		 * 
		 * paymentDistribution.setDepositedAmt(withdrawAmtDeduction);
		 * paymentDistribution.setCompoundVariableAmt(FDService.round(
		 * compoundVariableAmt, 2)); paymentDistribution.setAction(action);
		 * paymentDistribution.setActionId(withdrawDeposit.getId());
		 * paymentDistribution.setDistributionDate(DateService.getCurrentDate()) ;
		 * paymentDistribution.setCompoundFixedAmt(FDService.round(withdrawForm.
		 * getCompoundFixedAmt(), 2));
		 * paymentDistribution.setTotalBalance(FDService.round((compoundFixedAmt +
		 * compoundVariableAmt), 2));
		 * paymentDistribution.setDepositId(withdrawForm.getDepositId());
		 * paymentDistribution.setDepositHolderId(withdrawForm. getDepositHolderId());
		 * paymentDistribution.setFixedAmt(0.0);
		 * paymentDistribution.setVariableAmt(FDService.round( withdrawAmtDeduction,
		 * 2)); paymentDistribution.setBalanceFixedInterest(distribution.
		 * getBalanceFixedInterest());
		 * paymentDistribution.setBalanceVariableInterest(distribution.
		 * getBalanceVariableInterest());
		 * 
		 * Distribution lastDistribution =
		 * paymentDistributionDAO.insertPaymentDistribution(paymentDistribution) ;
		 * 
		 * fdService.insertInDepositHolderWiseDistribution(depositHolderList, action,
		 * withdrawDeposit.getId(), withdrawAmtDeduction);
		 * 
		 * // update the deposit table // Deposit deposit = //
		 * fixedDepositDAO.findById(withdrawForm.getDepositId());
		 * deposit.setCurrentBalance(compoundFixedAmt + compoundVariableAmt);
		 * 
		 * // update accountDetails table..
		 * 
		 * if (!withdrawForm.getModeOfPayment().equalsIgnoreCase("netBanking")) {
		 * 
		 * AccountDetails accDetails =
		 * accountDetailsDAO.findByAccountNo(withdrawForm.getLinkedAccountNo());
		 * accDetails.setAccountBalance(accDetails.getAccountBalance() +
		 * withdrawForm.getWithdrawAmount());
		 * accountDetailsDAO.updateUserAccountDetails(accDetails); } //
		 * ---------------------------------------------------------------
		 * 
		 * // 3. Interest Adjustment and penalty for the Withdraw //
		 * --------------------------------------------------------------------- - //
		 * Calculate the interest to adjust // Insert an interest adjustment for the
		 * withdraw in Distribution table // Insert the adjustment in Interest table
		 * fdService.adjustInterestForWithdraw(deposit, depositHolderList,
		 * lastInterestDistribution, lastDistribution); //
		 * --------------------------------------------------------------------- -
		 * 
		 * // // delete from interest saved after the current date //
		 * interestDAO.deleteByDepositIdAndDate(fixedDepositForm.getDepositId()) ;
		 * 
		 * // set required parameters for interest calculation in fixedDepositForm
		 * fixedDepositForm.setDepositId(withdrawForm.getDepositId());
		 * fixedDepositForm.setCategory(customerDetails.getCategory()); fixedDepositForm
		 * = fdService.setParametersForProjectedInterest(fixedDepositForm);
		 * 
		 * // Save the projected interests List<Interest> interestList =
		 * fdService.getInterestBreakupForModification(DateService. getCurrentDate(),
		 * fixedDepositForm.getMaturityDate(), fixedDepositForm, deposit);
		 * 
		 * for (int i = 0; i < interestList.size(); i++) {
		 * 
		 * Interest interest = new Interest(); interest = interestList.get(i); if (i ==
		 * interestList.size() - 1) {
		 * deposit.setNewMaturityAmount(interest.getInterestSum());
		 * fixedDepositDAO.updateFD(deposit); } if (lastInterest!=null){ if
		 * (DateService.getDaysBetweenTwoDates(lastInterest.getInterestCalcDate( ),
		 * interest.getInterestCalcDate()) != 0) interestDAO.insert(interest); else {
		 * interestList.remove(i); i--; }
		 * 
		 * } else{ interestDAO.insert(interest); }
		 * 
		 * }
		 * 
		 * List<TDS> tdsList = fdService.getTDSBreakupForModification(fixedDepositForm,
		 * interestList, fixedDepositForm.getMaturityDate()); for (int j = 0; j <
		 * tdsList.size(); j++) { // TDS tds = new TDS(); TDS tds = tdsList.get(j);
		 * tds.setDepositId(fixedDepositForm.getDepositId()); tdsDAO.insert(tds);
		 * 
		 * }
		 */
		attributes.addFlashAttribute(Constants.TRANSACTIONID, fdService.generateRandomString());
		attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
		attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Withdraw Successful");

		return new ModelAndView("redirect:fdSaved");
	}

	

	/*
	 * @RequestMapping(value = "/showDistributionRecords", method =
	 * RequestMethod.GET) public ModelAndView showDistributionRecords(ModelMap
	 * model, Long id, RedirectAttributes attributes,
	 * 
	 * @ModelAttribute DepositForm depositForm) throws CustomException {
	 * 
	 * ModelAndView mav = new ModelAndView();
	 * 
	 * List<Distribution> distributionList =
	 * fixedDepositDAO.getDistributionList(id);
	 * 
	 * if (distributionList != null) { Integer index = distributionList.size() - 1;
	 * Deposit deposit =
	 * fixedDepositDAO.getDeposit(distributionList.get(index).getDepositId());
	 * String customerName = (String)
	 * fixedDepositDAO.getDepositForInterestRate(id).get(0)[8];
	 * model.put("distributionList", distributionList); model.put("customerName",
	 * customerName);
	 * 
	 * model.put("variableBalance",
	 * distributionList.get(index).getCompoundVariableAmt());
	 * model.put("fixedBalance", distributionList.get(index).getCompoundFixedAmt());
	 * model.put("totalBalance", distributionList.get(index).getTotalBalance());
	 * model.put("accountNumber", deposit.getAccountNumber()); mav = new
	 * ModelAndView("distributonListsForCustomer", "model", model); } else { mav =
	 * new ModelAndView("noDataFoundCusm", "model", model); }
	 * 
	 * return mav; }
	 */
	@RequestMapping(value = "/fdListForFundTransfer", method = RequestMethod.GET)
	public ModelAndView fdListForFundTransfer(ModelMap model, RedirectAttributes attributes) throws CustomException {

		Customer user = getUserDetails();

		List<Object[]> depositHolderObjList = depositHolderDAO.getOpenDeposit(user.getId());
		if (depositHolderObjList != null && depositHolderObjList.size() > 0) {

			List<DepositHolderForm> depositHolderList = new ArrayList<DepositHolderForm>();

			for (int i = 0; i < depositHolderObjList.size(); i++) {
				if(depositHolderObjList.get(i)[10]!=null && depositHolderObjList.get(i)[10].toString().equalsIgnoreCase("AUTO"))
					continue;
				
				DepositHolderForm depositform = new DepositHolderForm();
				depositform.setDepositHolderStatus((String) depositHolderObjList.get(i)[0]);
				depositform.setContribution((Float) depositHolderObjList.get(i)[1]);
				Long depositId = Long.parseLong(depositHolderObjList.get(i)[2].toString());
				depositform.setDepositId(depositId);

				depositform.setMaturityDate((Date) depositHolderObjList.get(i)[3]);
				depositform.setStatus((String) depositHolderObjList.get(i)[4]);
				depositform.setCreatedDate((Date) depositHolderObjList.get(i)[5]);
				depositform.setDepositamount((Double) depositHolderObjList.get(i)[6]);
				depositform.setAccountNumber((String) depositHolderObjList.get(i)[8]);
				depositform.setNewMaturityDate((Date) depositHolderObjList.get(i)[9]);
				depositform.setDepositType((String) depositHolderObjList.get(i)[10]);
				depositform.setAccountAccessType((String) depositHolderObjList.get(i)[11]);
				depositform.setDeathCertificateSubmitted((String) depositHolderObjList.get(i)[12]==null?"":(String) depositHolderObjList.get(i)[12]);
				
				depositHolderList.add(depositform);
			}

			model.put("depositHolderList", depositHolderList);

		} else {
			return new ModelAndView("noDataFoundCusm", "model", model);
		}

		return new ModelAndView("fdChangesListForFundTransfer", "model", model);
	}

	@RequestMapping(value = "/custDepositAmt", method = RequestMethod.GET)
	public ModelAndView fdCustomerLists(ModelMap model, RedirectAttributes attributes,@RequestParam("id")  Long id, String accountNumber,
			String newMaurityDate ,String depositType) throws CustomException {
		Deposit deposit = fixedDepositDAO.getDeposit(id);
		if (deposit.getProductConfigurationId() == null) {
			attributes.addFlashAttribute("error", "Product id not found");
			return new  ModelAndView("redirect:fdListForFundTransfer");
		}else {
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(deposit.getProductConfigurationId());
		model.put("productConfiguration", productConfiguration );
		if (productConfiguration.getIsTopupAllowed() != null) {
			if (productConfiguration.getIsTopupAllowed() == 1) {
				
				String lockingPeriodForTopup = productConfiguration.getLockingPeriodForTopup();
				if(lockingPeriodForTopup !=null && lockingPeriodForTopup.trim().equalsIgnoreCase(",,")) {
					model.put("lockingPeriod", false);
				}else {
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
			}else {
				model.put("lockingPeriod", false);
			}
			// if(productConfiguration.getIsTopupAllowed() == 1) model.put("lockingPeriod",
			// true); else model.put("lockingPeriod", false);

		}

		Customer user = getUserDetails();

		List<Object[]> depositHolderObjList = depositHolderDAO.getOpenDeposit(user.getId());
		
		if (depositHolderObjList != null && depositHolderObjList.size() > 0) {
			List<DepositHolderForm> depositHolderList = new ArrayList<DepositHolderForm>();
			

			for (int i = 0; i < depositHolderObjList.size(); i++) {
				DepositHolderForm depositform = new DepositHolderForm();
				depositform.setDepositHolderStatus((String) depositHolderObjList.get(i)[0]);
				depositform.setContribution((Float) depositHolderObjList.get(i)[1]);
				
				Long depositId=Long.parseLong(depositHolderObjList.get(i)[2].toString());
				depositform.setDepositId(depositId);

				depositform.setMaturityDate((Date) depositHolderObjList.get(i)[3]);
				depositform.setStatus((String) depositHolderObjList.get(i)[4]);
				depositform.setCreatedDate((Date) depositHolderObjList.get(i)[5]);
				depositform.setDepositamount((Double) depositHolderObjList.get(i)[6]);
				depositform.setAccountNumber((String) depositHolderObjList.get(i)[8]);
				depositform.setNewMaturityDate((Date) depositHolderObjList.get(i)[9]);
				depositform.setDepositType((String) depositHolderObjList.get(i)[10]);
				depositHolderList.add(depositform);
			}
			
			if (deposit.getDepositClassification() != null
					&& deposit.getDepositClassification().equals(Constants.taxSavingDeposit)) {
				model.put(Constants.ERROR, Constants.taxSavingError);
				model.put("depositHolderList", depositHolderList);
				return new ModelAndView("fdChangesListForFundTransfer", "model", model);
			}
			model.put("depositHolderList", depositHolderList);
			model.put("depositHolderLists", depositHolderList);
			

		}
		

		newMaurityDate = newMaurityDate.split(" ")[0];
//		depositForm.setDepositId(id);
//		depositForm.setAccountNumber(accountNumber);
		fixedDepositForm.setDepositId(id);
		fixedDepositForm.setAccountNo(accountNumber);
		fixedDepositForm.setPaymentMode("Card Payment");
		
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date maturityDate = DateService.getCurrentDateTime();
		try {
			maturityDate = df.parse(newMaurityDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int daysDiff = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate);

		List<DepositHolderForm> depositHolderList = depositHolderDAO.getDepositHoldersName(id);

		model.put("payAndWithdrawTenure", productConfiguration.getPreventionOfTopUpBeforeMaturity());
		model.put("daysDiff", daysDiff);
		model.put("depositHolderList", depositHolderList);
		//model.put("depositForm", depositForm);
		model.put("fixedDepositForm", fixedDepositForm);
		model.put("depositType",depositType);
		}
		return new ModelAndView("custDepositAmount", "model", model);
	}
	
	@RequestMapping(value = "/makePayment", method = RequestMethod.POST)
	public String makePayment(ModelMap model, RedirectAttributes attributes, @ModelAttribute FixedDepositForm fixedDepositForm,
			HttpServletRequest request) throws CustomException {

		Customer customerDetails = getUserDetails();
		
		//String transactionId = depositService.makePaymentFromCustomer(depositForm, customerDetails);
		
		Deposit deposit = fixedDepositDAO.getDeposit(fixedDepositForm.getDepositId());
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(deposit.getProductConfigurationId());
		
		if(productConfiguration.getIsTopupAllowed() != null && productConfiguration.getIsTopupAllowed()==0) {
			attributes.addFlashAttribute("error","Sorry, Top-up is not allowed.");
			return "redirect:netBankingPayment?id="+deposit.getId()+"&accountNumber="+deposit.getAccountNumber()+"&newMaurityDate="+DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fixedDepositForm.getMaturityDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
		
		Double amountPaid = fixedDepositForm.getFdAmount();
		if(amountPaid<productConfiguration.getMinimumTopupAmount()) {
			attributes.addFlashAttribute("error","Sorry, Minimum Top-up Amount is " + productConfiguration.getMinimumTopupAmount() + ".");
			return "redirect:netBankingPayment?id="+deposit.getId()+"&accountNumber="+deposit.getAccountNumber()+"&newMaurityDate="+DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fixedDepositForm.getMaturityDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			
		}
			
		if(amountPaid>productConfiguration.getMaximumTopupAmount()) {
			attributes.addFlashAttribute("error","Sorry, Maximum Top-up Amount is " + productConfiguration.getMaximumTopupAmount() + ".");
			return "redirect:netBankingPayment?id="+deposit.getId()+"&accountNumber="+deposit.getAccountNumber()+"&newMaurityDate="+DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fixedDepositForm.getMaturityDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			
		}
		
		String lockingPeriodForTopup = productConfiguration.getLockingPeriodForTopup();
		String[] arrlockingPeriodForTopup = lockingPeriodForTopup.split(",");
		
		Date lockingDate = deposit.getCreatedDate();
		for(int i = 0; i<arrlockingPeriodForTopup.length; i++)
		{
			if(arrlockingPeriodForTopup[i]!=null)
			{
				if(arrlockingPeriodForTopup[i].toString().endsWith("Year"))
				{
					String num = arrlockingPeriodForTopup[i].replaceAll(" Year", "" );
					lockingDate = DateService.addYear(lockingDate, Integer.parseInt(num));
				}
				else if(arrlockingPeriodForTopup[i].toString().endsWith("Month"))
				{
					String num = arrlockingPeriodForTopup[i].replaceAll(" Month", "");
					lockingDate = DateService.addMonths(lockingDate, Integer.parseInt(num));
				}
				else if(arrlockingPeriodForTopup[i].toString().endsWith("Day"))
				{
					String num = arrlockingPeriodForTopup[i].replaceAll(" Day", "");
					lockingDate = DateService.addDays(lockingDate, Integer.parseInt(num));
				}
			}
		}
		
		if(DateService.getDaysBetweenTwoDates(lockingDate, DateService.getCurrentDate()) < 0) {
			attributes.addFlashAttribute("error","Sorry, Deposit is still within the locking period for Top-up.");
			return "redirect:netBankingPayment?id="+deposit.getId()+"&accountNumber="+deposit.getAccountNumber()+"&newMaurityDate="+DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fixedDepositForm.getMaturityDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}
		
		Date maturityDate = deposit.getNewMaturityDate() != null? deposit.getNewMaturityDate() : deposit.getMaturityDate();
		if(productConfiguration.getPreventionOfTopUpBeforeMaturity()!=null)
		{
			if(DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate) <= productConfiguration.getPreventionOfTopUpBeforeMaturity()) {
				attributes.addFlashAttribute("error","Sorry, Withdraw has prevented for last " + productConfiguration.getPreventionOfTopUpBeforeMaturity() + " days from maturity.");
				return "redirect:netBankingPayment?id="+deposit.getId()+"&accountNumber="+deposit.getAccountNumber()+"&newMaurityDate="+DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fixedDepositForm.getMaturityDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			}
		}
		
		
		
		
		
		String transactionId = depositService.doPayment(fixedDepositForm, customerDetails.getUserName());

		Date curDate = DateService.loginDate;

		attributes.addFlashAttribute(Constants.TRANSACTIONID, transactionId);
		attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
		attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Payment Successful");

		return "redirect:fdSaved";

	}


	@RequestMapping(value = "/netBankingPayment", method = RequestMethod.GET)
	public ModelAndView netBankingPayment(ModelMap model,RedirectAttributes attributes, @RequestParam("id") Long id, String accountNumber,
			String newMaurityDate,String depositType) throws CustomException {
		ProductConfiguration productConfiguration=null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date maturityDate = DateService.getCurrentDateTime();
		try {
			maturityDate = df.parse(newMaurityDate);
			fixedDepositForm.setMaturityDate(maturityDate);
		} catch (ParseException e) {
			fixedDepositForm.setMaturityDate(maturityDate);
			e.printStackTrace();
		}

		Customer user = getUserDetails();

		List<Object[]> depositHolderObjList = depositHolderDAO.getOpenDeposit(user.getId());
		if (depositHolderObjList != null && depositHolderObjList.size() > 0) {

			List<DepositHolderForm> depositHolderList = new ArrayList<DepositHolderForm>();

			for (int i = 0; i < depositHolderObjList.size(); i++) {
				DepositHolderForm depositform = new DepositHolderForm();
				depositform.setDepositHolderStatus((String) depositHolderObjList.get(i)[0]);
				depositform.setContribution((Float) depositHolderObjList.get(i)[1]);
				
				Long depositId=Long.parseLong(depositHolderObjList.get(i)[2].toString());
				depositform.setDepositId(depositId);

				depositform.setMaturityDate((Date) depositHolderObjList.get(i)[3]);
				depositform.setStatus((String) depositHolderObjList.get(i)[4]);
				depositform.setCreatedDate((Date) depositHolderObjList.get(i)[5]);
				depositform.setDepositamount((Double) depositHolderObjList.get(i)[6]);
				//depositform.setF((Double) depositHolderObjList.get(i)[6]);
				depositform.setAccountNumber((String) depositHolderObjList.get(i)[8]);
				depositform.setNewMaturityDate((Date) depositHolderObjList.get(i)[9]);
				depositform.setDepositType((String) depositHolderObjList.get(i)[10]);
				depositHolderList.add(depositform);
			}
			Deposit deposit = fixedDepositDAO.getDeposit(id);
			if (deposit.getProductConfigurationId() == null) {
				attributes.addFlashAttribute("error", "Product id not found");
				return new  ModelAndView("redirect:fdListForFundTransfer");
			}else {
			productConfiguration = productConfigurationDAO.findById(deposit.getProductConfigurationId());
			if(productConfiguration==null){
			 productConfiguration = productConfigurationDAO.findByProductCode(deposit.getProductConfigurationId().toString());
			}
			if (deposit.getDepositClassification() != null
					&& deposit.getDepositClassification().equals(Constants.taxSavingDeposit)) {
				model.put(Constants.ERROR, Constants.taxSavingError);
				model.put("depositHolderList", depositHolderList);
				return new ModelAndView("fdChangesListForFundTransfer", "model", model);
			}
			model.put("depositHolderList", depositHolderList);
			
			}

		}

		int daysDiff = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate);
		fixedDepositForm.setDepositId(id);
		fixedDepositForm.setAccountNo(accountNumber);
		fixedDepositForm.setPaymentMode("Net Banking");
		//fixedDepositForm.setFdAmount(fdAmount);
		
		List<DepositHolderForm> depositHolderList = depositHolderDAO.getDepositHoldersName(id);
		model.put("daysDiff", daysDiff);
		model.put("preventionOfWithdrawBeforeMaturity", productConfiguration.getPreventionOfWithdrawBeforeMaturity());
		model.put("depositHolderList", depositHolderList);
		model.put("depositHolderList", depositHolderList);
		model.put("depositType", depositType);
		model.put("fixedDepositForm", fixedDepositForm);
		model.put("minTopUpAmount", productConfiguration.getMinimumTopupAmount());
	
		return new ModelAndView("netBankingPayment", "model", model);
	}


//	// @Transactional
//	@RequestMapping(value = "/paymentThroughNetBanking", method = RequestMethod.POST)
//	public String saveDepositPaymentThroughNetBanking(ModelMap model, RedirectAttributes attributes,
//			@ModelAttribute FixedDepositForm fixedDepositForm, HttpServletRequest request) throws CustomException {
//
//		
//		
//		Customer customer = customerDAO.getByUserName(getCurrentLoggedUserName());
//		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(fixedDepositForm.getDepositId());
//
//		String fromAccountNo = "";
//		String fromAccountType = "";
//
//		Deposit deposit = fixedDepositDAO.getDeposit(fixedDepositForm.getDepositId());
//
//		DepositHolder primaryDepositHolder = null;
//		for (int i = 0; i < depositHolderList.size(); i++) {
//			if (depositHolderList.get(i).getDepositHolderStatus().equalsIgnoreCase("PRIMARY"))
//				primaryDepositHolder = depositHolderList.get(i);
//		}
//
//		Double depositAmt = fixedDepositForm.getDepositAmount();
//		Payment payment = new Payment();
//		payment.setDepositAmount(fixedDepositForm.getDepositAmount());
//		payment.setAmountPaid(fixedDepositForm.getDepositAmount());
//		payment.setPaymentDate(DateService.getCurrentDate());
//		//payment.setModeOfPayment("Net Banking");
//		payment.setModeOfPayment(fixedDepositForm.getPaymentMode());
//		payment.setCreatedBy(getCurrentLoggedUserName());
//		payment.setDepositId(fixedDepositForm.getDepositId());
//		payment.setDepositHolderId(primaryDepositHolder.getId());
//		payment.setTopUp(fixedDepositForm.getDepositForm().getTopUp());
//		payment.setNameOnBankAccount(fixedDepositForm.getOtherName());
//		payment.setAccountNumber(fixedDepositForm.getOtherAccount().toString());
//
//		if (fixedDepositForm.getFdPayOffAccount().equals("Other")) {
//
//			payment.setTransferType(fixedDepositForm.getOtherPayTransfer());
//			payment.setBankName(fixedDepositForm.getOtherBank());
//			payment.setIfscCode(fixedDepositForm.getOtherIFSC());
//		}
//
//		payment.setTransactionId(fdService.generateRandomString());
//		payment = paymentDAO.insertPayment(payment);
//
//		try {
//
//			String tex = Constants.CUSTOMERREMENDERSUBJECT;
//			SimpleMailMessage emails = new SimpleMailMessage();
//			emails.setTo(customer.getEmail());
//			emails.setSubject(tex);
//			emails.setText(Constants.HELLO + getCurrentLoggedUserName() + Constants.PAYMENTKBODY4
//					+ fixedDepositForm.getDepositAmount() + Constants.PAYMENTKBODY5 + Constants.BANKSIGNATURE);
//			mailSender.send(emails);
//			SimpleMailMessage emails1 = new SimpleMailMessage();
//			emails1.setTo(customer.getEmail());
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage() + e);
//
//		}
//
//		// Insert in Distributor and HolderWiseDistributor
//		calculationService.insertInPaymentDistribution(deposit, depositHolderList, fixedDepositForm.getDepositAmount(),
//				payment.getId(), Constants.PAYMENT, null, null, null, null, null, null, null);
//
//		// Insert in DepositSummary and HolderwiseDepositSummary
//		DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.PAYMENT,
//				fixedDepositForm.getDepositAmount(), null, null, null, null, depositHolderList, null, null, null);
//
//		// Insert in Journal & Ledger
//		// -----------------------------------------------------------
//		// For accounting entry only
//
//		// Because it is Net Banking, then first cash account will be debited and
//		// deposit ammount will be credited
//		fromAccountNo = "";
//		fromAccountType = "Cash";
//
//		// Check the account from where Deposit account is credited
//		if (fixedDepositForm.getFdPayOffAccount().contains("Same")) {
//			List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(customer.getId());
//			if (accountList.size() > 0) {
//				for (int i = 0; i < accountList.size(); i++) {
//					if (accountList.get(i).getAccountNo()
//							.equalsIgnoreCase(fixedDepositForm.getOtherAccount().toString())) {
//						fromAccountNo = fixedDepositForm.getOtherAccount().toString();
//						fromAccountType = accountList.get(i).getAccountType();
//						break;
//					}
//				}
//			}
//		}
//
//		if (fromAccountType.equals(""))
//			fromAccountType = "Cash";
//
//		ledgerService.insertJournalLedger(deposit.getId(), DateService.getCurrentDate(), fromAccountNo, fromAccountType,
//				deposit.getAccountNumber(), "Deposit Account", "Payment", fixedDepositForm.getDepositAmount(),
//				"Net Banking", depositSummary.getTotalPrincipal());
//		// -----------------------------------------------------------
//
//		if (deposit.getDepositCategory() != null && deposit.getDepositCategory().equals(Constants.REVERSEEMI)) {
//			// If the deposit in the Annuity deposit/ Reverse EMI then
//			// change the emi amount as well as interest projection
//			// get the deposit
//			Integer gestationPeriod = deposit.getGestationPeriod();
//			Date gestationEndDate = DateService.generateYearsDate(deposit.getCreatedDate(), gestationPeriod);
//
//			Distribution lastDistribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());
//			Double uncalculatedInterestAmount = fdService.getUncalculatedInterestAmount(deposit);
//
//			String interestCalculationBasis = Constants.MONTHLY;
//			BankConfiguration bankConfiguration = ratesDAO.getBankConfiguration(deposit.getPrimaryCitizen(),
//					deposit.getNriAccountType());
//			if (bankConfiguration != null)
//				interestCalculationBasis = bankConfiguration.getInterestCalculationBasis();
//
//			// Calculate the future EMI
//			Double emiAmount = fdService.calculateEMIOnTopUp(lastDistribution.getTotalBalance(), gestationEndDate,
//					deposit.getModifiedInterestRate(), DateService.getCurrentDate(), deposit.getNewMaturityDate(),
//					deposit.getPayOffInterestType(), uncalculatedInterestAmount, interestCalculationBasis);
//
//			deposit.setEmiAmount(emiAmount);
//			fixedDepositDao.updateFD(deposit);
//		}
//
//		if (deposit.getReverseEmiCategory() != null
//				&& deposit.getReverseEmiCategory().equalsIgnoreCase("Fixed Amount")) {
//			double totalDepositAmount = deposit.getDepositAmount() + depositAmt;
//			float rateOfInterest = deposit.getInterestRate();
//			double emiAmount = depositHolderList.get(0).getEmiAmount();
//			float tenure = fdService.calculateEmiTenure(totalDepositAmount, emiAmount, rateOfInterest);
//
//			int daysTenure = (int) (tenure * 30);
//
//			Date maturityDate = DateService.generateDaysDate(deposit.getCreatedDate(), daysTenure);
//			deposit.setTenure(daysTenure);
//			deposit.setNewMaturityDate(maturityDate);
//			deposit.setDepositAmount(totalDepositAmount);
//		}
//
//		deposit = fixedDepositDAO.updateFD(deposit);
//
//		// fdService.getTDSBreakupForModification(fixedDepositForm,
//		// interestList,
//		// fixedDepositForm.getMaturityDate(), deposit, depositHolderList);
//		//
//		// List<TDS> tdsList =
//		// fdService.getTDSBreakupForModification(fixedDepositForm,
//		// interestList,
//		// fixedDepositForm.getMaturityDate(), deposit, depositHolderList);
//		// for (int i = 0; i < tdsList.size(); i++) {
//		// // TDS tds = new TDS();
//		// TDS tds = tdsList.get(i);
//		// tds.setDepositId(fixedDepositForm.getDepositId());
//		// tdsDAO.insert(tds);
//		//
//		// }
//
//		Date curDate = DateService.loginDate;
//		attributes.addFlashAttribute(Constants.TRANSACTIONID, fdService.generateRandomString());
//		attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
//		attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Payment Successfully");
//		return "redirect:fdSaved";
//	}

	@RequestMapping(value = "/fdCustomerList", method = RequestMethod.GET)
	public ModelAndView fdCustomerList(ModelMap model, RedirectAttributes attributes) throws CustomException {

		ModelAndView mav = new ModelAndView();

		Customer user = getUserDetails();
		List<HolderForm> depositHolderList = depositService.getAllDepositByCustomerId(user.getId());

		if (depositHolderList != null) {

			model.put("depositHolderList", depositHolderList);
			mav = new ModelAndView("fdCustomerList", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/withdrawFromSaving", method = RequestMethod.GET)
	public ModelAndView withdrawFromSaving(ModelMap model) throws CustomException {
		Customer user = getUserDetails();
		AccountDetails savingAcc = accountDetailsDAO.findSavingByCustId(user.getId());
		if (savingAcc != null) {

			withdrawForm.setCustomerId(user.getId());
			withdrawForm.setAccountBalance(Double.valueOf(savingAcc.getAccountBalance()));
			withdrawForm.setAccountNumber(savingAcc.getAccountNo());

			List<Deposit> autoDepositList = fixedDepositDAO.getAutoDepositList(user.getId());

			Double totBalanceAvailableToWithdraw = 0d;
			if (autoDepositList != null) {
				for (int i = 0; i < autoDepositList.size(); i++) {
					Deposit deposit = autoDepositList.get(i);

					Distribution distribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());
					if (distribution != null) {
						totBalanceAvailableToWithdraw = totBalanceAvailableToWithdraw + distribution.getTotalBalance();
					}
				}

				withdrawForm.setCustomerId(user.getId());
				withdrawForm.setCompoundVariableAmt(totBalanceAvailableToWithdraw);

			} else {
				withdrawForm.setCompoundVariableAmt(0d);
			}
			Double compoundVarAmt = withdrawForm.getCompoundVariableAmt() == null ? 0
					: withdrawForm.getCompoundVariableAmt();
			model.put("totalBalance", withdrawForm.getAccountBalance() + compoundVarAmt);
			model.put("readonly", "false");
		}

		else {
			model.put("readonly", "true");
			model.put("error", Constants.SAVINGACCOUNTERROR);
		}
		model.put("withdrawForm", withdrawForm);
		return new ModelAndView("withdrawFromSaving", "model", model);
	}

	@RequestMapping(value = "/withdrawFromSavingConfirm", method = RequestMethod.POST)
	public ModelAndView withdrawFromSavingConfirm(ModelMap model, @ModelAttribute WithdrawForm withdrawForm)
			throws CustomException {

		return new ModelAndView("withdrawFromSavingConfirm", "model", model);
	}

	@RequestMapping(value = "/withdrawFromSavingPost", method = RequestMethod.POST)
	public ModelAndView withdrawFromSavingPost(ModelMap model, @ModelAttribute WithdrawForm withdrawForm,
			RedirectAttributes attributes) throws CustomException {
		Customer user = getUserDetails();

		try {
			fdService.withdrawFromSaving(withdrawForm, user);
		} catch (Exception e) {
			attributes.addFlashAttribute("error", "Insufficient Balance");
			return new ModelAndView("redirect:withdrawFromSaving");

		}

		Transaction trans = new Transaction();

		String transactionId = fdService.generateRandomString();
		trans.setTransactionId(transactionId);
		trans.setTransactionType(Constants.ACCOUNTUPDATED);
		trans.setTransactionStatus(Constants.ACCOUNT);
		transactionDAO.insertTransaction(trans);

		Date curDate = DateService.loginDate;

		model.put(Constants.TRANSACTIONID, transactionId);
		model.put(Constants.TRANSACTIONDATE, curDate);
		model.put(Constants.TRANSACTIONSTATUS, "Withdrawn Successfully");

		return new ModelAndView("fdSaved", "model", model);
	}

	@RequestMapping(value = "/interestReport", method = RequestMethod.GET)
	public ModelAndView interestReport(ModelMap model) throws CustomException {

		ModelAndView mav = new ModelAndView();

		Customer user = getUserDetails();

		List<Object[]> depositHolderObjList = depositHolderDAO.getAllDeposit(user.getId());

		if (depositHolderObjList != null && depositHolderObjList.size() > 0) {

			List<DepositHolderForm> depositLists = new ArrayList<DepositHolderForm>();

			for (int i = 0; i < depositHolderObjList.size(); i++) {
				DepositHolderForm depositform = new DepositHolderForm();

				depositform.setDepositHolderStatus((String) depositHolderObjList.get(i)[0]);
				depositform.setContribution((Float) depositHolderObjList.get(i)[1]);
				depositform.setDepositId((Long) depositHolderObjList.get(i)[2]);
				depositform.setMaturityDate((Date) depositHolderObjList.get(i)[3]);
				depositform.setStatus((String) depositHolderObjList.get(i)[4]);
				depositform.setCreatedDate((Date) depositHolderObjList.get(i)[5]);
				depositform.setDepositamount((Double) depositHolderObjList.get(i)[6]);
				depositform.setCurrentBalance((Double) depositHolderObjList.get(i)[7]);
				depositform.setAccountNumber((String) depositHolderObjList.get(i)[8]);
				depositform.setNewMaturityDate((Date) depositHolderObjList.get(i)[9]);
				depositform.setCategory((String) depositHolderObjList.get(i)[10]);
				depositLists.add(depositform);
			}

			model.put("depositLists", depositLists);
			mav = new ModelAndView("interestReportCus", "model", model);

		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;

	}

	@RequestMapping(value = "/showInterestRecords", method = RequestMethod.GET)
	public ModelAndView showInterestRecords(ModelMap model, Long id) throws CustomException {

		ModelAndView mav = new ModelAndView();

		List<Interest> interestList = interestDAO.getByDepositId(id);

		if (interestList.size() > 0) {
			Integer index = interestList.size() - 1;
			Deposit deposit = fixedDepositDAO.getDeposit(interestList.get(index).getDepositId());
			String customerName = (String) fixedDepositDAO.getDepositForInterestRate(id).get(0)[8];
			model.put("customerName", customerName);
			// model.put("interestAmount",
			// interestList.get(index).getInterestAmount());
			model.put("interestList", interestList);

			Double totInterestAmount = 0d;
			for (int i = 0; i < interestList.size(); i++) {
				totInterestAmount = totInterestAmount + interestList.get(i).getInterestAmount();
			}
			model.put("interestAmount", fdService.round(totInterestAmount, 2));
			model.put("depositAccountNumber", deposit.getAccountNumber());
			mav = new ModelAndView("showInterestRecordsCus", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;

	}

	@RequestMapping(value = "/showTdsRecords", method = RequestMethod.GET)
	public ModelAndView showTdsRecords(ModelMap model, Long id) throws CustomException {

		ModelAndView mav = new ModelAndView();

		List<TDS> tdsList = tdsDAO.getByDepositId(id);

		if (tdsList.size() > 0) {

			model.put("tdsList", tdsList);
			// mav = new ModelAndView("distributonListsForCustomer", "model",
			// model);
			mav = new ModelAndView("showTdsRecords", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;

	}

	@RequestMapping(value = "/downloadDocument", method = RequestMethod.GET)
	public ModelAndView downloadDocument(ModelMap model) throws CustomException {

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

		return new ModelAndView("downloadDocument", "model", model);

	}

	@RequestMapping(value = "/autoDepositList", method = RequestMethod.GET)
	public ModelAndView autoDepositList(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {
		ModelAndView mav = null;
		List<AutoDepositForm> autoDeposit = fixedDepositDAO.getAutoDepositListForCustomer(getUserDetails().getId());
		if (autoDeposit != null && autoDeposit.size() > 0) {
			model.put("customerName", getUserDetails().getCustomerName());
			model.put("autoDeposit", autoDeposit);
			mav = new ModelAndView("autoDepositListForCustomer", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);

		}
		return mav;
	}

	@RequestMapping(value = "/modifiedDeposits", method = RequestMethod.GET)
	public ModelAndView modificationReport(ModelMap model) throws CustomException {

		List<Object[]> objList = depositHolderDAO.getOpenDeposit(getUserDetails().getId());
		if (objList.size() > 0) {

			model.put("objList", objList);
			return new ModelAndView("modifiedDepositsCus", "model", model);
		} else {
			model.put("error", "No deposits found");
			return new ModelAndView("noDataFoundCusm", "model", model);
		}
	}

	@RequestMapping(value = "/showModificationList", method = RequestMethod.GET)
	public ModelAndView showModificationList(ModelMap model, Long depositId) throws CustomException {

		List<Object[]> objList = depositModificationDAO.getAllModificationList(depositId);

		if (objList != null) {

			//BankConfiguration bankConfiguration = ratesDAO.findAll();
			ProductConfiguration productConfiguration = productConfigurationDAO.findById(fixedDepositDAO.findByDepositId(depositId).getProductConfigurationId());
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
			return new ModelAndView("showModificationListCust", "model", model);
		} else {
			model.put("error", "No modification found for deposit id:" + depositId);
			return new ModelAndView("noDataFoundCusm", "model", model);
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

		return new ModelAndView("compareWithModificationCust", "model", model);
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

		if (deposit.getPayOffInterestType() != null && deposit.getPayOffInterestType() != ""
				&& modificationList.get(0).getPayOffType() != null
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

		return new ModelAndView("compareWithModificationCust", "model", model);
	}

	@RequestMapping(value = "/reportSummary", method = RequestMethod.GET)
	public ModelAndView reportSummary(ModelMap model, @ModelAttribute ReportForm reportForm) throws CustomException {

		model.put("reportForm", reportForm);
		return new ModelAndView("reportSummaryCus", "model", model);
	}

	@RequestMapping(value = "/getSummary", method = RequestMethod.POST)
	public ModelAndView getSummary(ModelMap model, @ModelAttribute ReportForm reportForm) throws CustomException {
		Customer cus = getUserDetails();
		List<ReportForm> reportList = fixedDepositDAO.getReportSummaryForCus(reportForm.getFromDate(),
				reportForm.getToDate(), cus.getId());

		model.put("reportForm", reportForm);
		model.put("reportList", reportList);

		return new ModelAndView("reportSummaryCus", "model", model);
	}

	@RequestMapping(value = "/fdListforHolderWiseResort", method = RequestMethod.GET)
	public ModelAndView fdListforHolderWiseResort(ModelMap model, RedirectAttributes attributes)
			throws CustomException {

		ModelAndView mav = new ModelAndView();

		Customer user = getUserDetails();

		List<DepositHolderForm> depositHolderList = depositService.getOpenDepositsByCustomerId(user.getId());
		if (depositHolderList.size() > 0) {
			model.put("depositHolderList", depositHolderList);
			mav = new ModelAndView("fdListforHolderWiseResort", "model", model);
		} else {
			model.put("error", "No Deposit Found");
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;
	}

	/*
	 * @RequestMapping(value = "/holderWiseReport", method = RequestMethod.GET)
	 * public ModelAndView holderWiseReport(ModelMap model, Long depositId, String
	 * depositAccountNo) throws CustomException { Customer cus = getUserDetails();
	 * 
	 * List<DepositHolderWiseDistribution> distributionList =
	 * depositHolderWiseDistributionDAO .getByCustomerIdAndDepositId(depositId,
	 * cus.getId());
	 * 
	 * if (distributionList.size() > 0) { model.put("distributionList",
	 * distributionList); model.put("depositAccountNo", depositAccountNo); }
	 * 
	 * else { model.put("error", "No transaction found for the deposit"); return new
	 * ModelAndView("noDataFoundForCus", "model", model); }
	 * 
	 * return new ModelAndView("holderWiseReport", "model", model); }
	 */

	@RequestMapping(value = "/holderWiseReport", method = RequestMethod.GET)
	public ModelAndView holderWiseReport(ModelMap model, Long depositId, String depositAccountNo)
			throws CustomException {
		Customer cus = getUserDetails();
		ModelAndView mav = new ModelAndView();
		// DepositHolderWiseDistribution depositHolderWiseDistribution =
		// depositHolderWiseDistributionDAO.getDepositHolderWiseDistribution(depositId);
		DepositHolder depositHolder = depositHolderDAO.getDepositHolder(depositId, cus.getId());
		List<Object[]> distributionList = depositHolderWiseDistributionDAO
				.getDepositHolderWiseDistributionByDepositHolderId(depositHolder.getId());

		if (distributionList.size() > 0) {
			model.put("distributionList", distributionList);
			model.put("depositAccountNo", depositAccountNo);
			mav = new ModelAndView("holderWiseReport", "model", model);
		}

		else {
			model.put("error", "No transaction found for the deposit");
			mav = new ModelAndView("noDataFoundForCus", "model", model);
		}

		model.put("depositId", depositId);
		model.put("customerId", cus.getId());
		model.put("contribution", depositHolder.getContribution());
		return mav;
	}

	@RequestMapping(value = "/tenureCalculation", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Double tenureCalculation(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			Double fdAmount, Double emiAmt) throws CustomException {

		double emiFrequency = Math.floor(fdAmount / emiAmt);

		return emiFrequency;

	}

//	@RequestMapping(value = "/reverseEmiOnBasis")
//	public ModelAndView reverseEmiOnBasis(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
//			RedirectAttributes attributes) throws CustomException {
//
//		Customer customer = getUserDetails();;		
//		BankConfiguration bankConfiguration = ratesDAO.getBankConfiguration(customer.getCitizen(), customer.getNriAccountType());
//
//		if (bankConfiguration.getReverseEmiOnBasis().equals(Constants.fixedAmountEmi)) {
//			return new ModelAndView("redirect:reverseEmiFixedAmount");
//
//		} else if (bankConfiguration.getReverseEmiOnBasis().equals(Constants.fixedTenureEmi)) {
//			return new ModelAndView("redirect:reverseEmi");
//		}
//
//		else {
//			return new ModelAndView("reverseEmiDefault");
//		}
//
//	}

	@RequestMapping(value = "/reverseEmiDefault", method = RequestMethod.GET)
	public ModelAndView reverseEmiDefault() throws CustomException {

		return new ModelAndView("reverseEmiDefault");
	}

	@RequestMapping(value = "/reverseEmi")
	public ModelAndView reverseEmi(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes, String val, String productId) throws CustomException {

		ProductConfiguration _pc= productConfigurationDAO.findById(Long.parseLong(productId));
		if (_pc == null) {
			attributes.addAttribute("error", "Product Configuration is null");
			return new ModelAndView("redirect:reverseEmiDefault");
		}

		Customer customerDetails = getUserDetails();

		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(customerDetails.getId());
		fixedDepositForm.setAccountList(accountList);
		fixedDepositForm.setProductConfigurationId(Long.valueOf(productId));
		fixedDepositForm.setId(customerDetails.getId());
		fixedDepositForm.setCategory(customerDetails.getCategory());
		fixedDepositForm.setCitizen(customerDetails.getCitizen());
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		String currency = null;
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
		model.put("productConfiguration", _pc);
		model.put("fixedDepositForm", fixedDepositForm);
		model.put("customerId", customerDetails.getCustomerID());
		model.put("customerNriAccountType", customerDetails.getNriAccountType());
		model.put("todaysDate", date);
		model.put("cashPayment", 0);
		model.put("ddPayment", 0);
		model.put("chequePayment", 0);
		model.put("netBanking", 1);
		model.put("val", val);
		model.put("currency", currency);

		return new ModelAndView("reverseEmi", "model", model);
	}

	@RequestMapping(value = "/confirmReverseEmi", method = RequestMethod.POST)
	public ModelAndView confirmReverseEmi(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes) throws CustomException {

		fixedDepositForm.setFdFixed(0d);
		fixedDepositForm.setFdChangeable(fixedDepositForm.getFdAmount());

		fixedDepositForm.setDepositType(Constants.SINGLE);
		String[] accDetail = fixedDepositForm.getAccountNo().split(",");
		fixedDepositForm.setAccountNo(accDetail[0]);

		Double totalDepositAmount = fixedDepositForm.getFdAmount();
		double emiAmount = fixedDepositForm.getEmiAmount();

		Integer gestationPeriod = fixedDepositForm.getGestationPeriod();
		// Getting the interest rate for the duration

		Date maturityDate = null;
		Date currentDate = DateService.getCurrentDate();
		double emiFrequency = Math.floor(totalDepositAmount / emiAmount);
		// double remainEmiAmount = totalDepositAmount - emiAmount;
		int emiTimes = (int) emiFrequency;
		Date gestationDate = DateService.addYear(currentDate, gestationPeriod);
		Date gestationEndDate = DateService.addDays(gestationDate, -1);
		maturityDate = fdService.getMaturityDate(fixedDepositForm.getPayOffInterestType(), gestationEndDate, emiTimes);
		/*if (fixedDepositForm.getFdTenureType().equalsIgnoreCase(Constants.YEAR)
				&& fixedDepositForm.getDaysValue() != null) {
			maturityDate = DateService.generateDaysDate(maturityDate, fixedDepositForm.getDaysValue() + 1);

		}*/

		Customer customerDetails = getUserDetails();
		int daysDiff = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate)+1;
//		Float rateOfInterest = depositRateDAO.getInterestRate(customerDetails.getCategory(),
//				fixedDepositForm.getCurrency(), daysDiff, Constants.annuityDeposit, fixedDepositForm.getFdAmount());

		Float rateOfInterest = calculationService.getDepositInterestRate(daysDiff, customerDetails.getCategory(),
				fixedDepositForm.getCurrency(), fixedDepositForm.getFdAmount(), Constants.annuityDeposit,
				fixedDepositForm.getCitizen(), fixedDepositForm.getNriAccountType());

		if (rateOfInterest == null) {
			attributes.addFlashAttribute(Constants.ERROR, Constants.invalidTenure);
			return new ModelAndView("redirect:reverseEmi");
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
//						fixedDepositForm.getPayOffInterestType(), emiTimes); // Doubt
//																				// on
//																				// start
//																				// date
//			else
//				payoffDateList = fdService.getPayoffDatesForFixedAmountEmi(currentDate,
//						fixedDepositForm.getPayOffInterestType(), emiTimes);// Doubt

		// To get the interest List for fixed amount emi
		/*
		 * interestList = fdService.getInterestListForFixedAmountEmi(currentDate,
		 * maturityDate, rateOfInterest, emiAmount, totalDepositAmount,
		 * gestationEndDate, 0l, fixedDepositForm.getPayOffInterestType(), emiTimes,
		 * payoffDateList);
		 */

//			if (payoffDateList != null) {
//				// Date interestPayOffDate =
//				// fdService.calculateInterestPayOffDueDate(fixedDepositForm.getPayOffInterestType(),
//				// fixedDepositForm.getMaturityDate(), currentDate);
//
//				fixedDepositForm.setPayoffDate(payoffDateList.get(0));
//			}
		// }

//		Float totalInterest = interestList != null ? fdService.getTotalInterestAmount(interestList) : 0f;

		Float totalTDS = 0f;// fdService.getTotalTDSAmount(currentDate,
		// maturityDate, fixedDepositForm.getCategory(),
		// fixedDepositForm, interestList);
//		fixedDepositForm.setInterestList(interestList);
//		fixedDepositForm.setEstimateTDSDeduct(totalTDS);
//		fixedDepositForm.setEstimateInterest(totalInterest);
		// fixedDepositForm.setEstimatePayOffAmount(totalDepositAmount + totalInterest -
		// totalTDS);

		Date payOffDueDate = fdService.getRevereseEMIPayOffDueDate(DateService.getCurrentDate(), gestationEndDate,
				maturityDate, fixedDepositForm.getPayOffInterestType(), false);
		fixedDepositForm.setPayoffDate(payOffDueDate);
		model.put("fixedDepositForm", fixedDepositForm);
		model.put("customerDetails", customerDetails);

		return new ModelAndView("confirmReverseEmi", "model", model);
	}

	@RequestMapping(value = "/reverseEmiFixedAmount")
	public ModelAndView reverseEmiFixedAmount(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes, String val,String productId) throws CustomException {

		ProductConfiguration _pc= productConfigurationDAO.findById(Long.parseLong(productId));
		if (_pc == null) {
			attributes.addAttribute("error", "Product Configuration is null");
			return new ModelAndView("redirect:reverseEmiDefault");
		}


		String currency = null;
		if (fixedDepositForm.getCurrency() != null) {
			currency = fixedDepositForm.getCurrency();
		}

		Customer customerDetails = getUserDetails();
		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(customerDetails.getId());
		fixedDepositForm.setProductConfigurationId(Long.valueOf(productId));
		fixedDepositForm.setAccountList(accountList);
		fixedDepositForm.setId(customerDetails.getId());
		fixedDepositForm.setCategory(customerDetails.getCategory());
		fixedDepositForm.setCitizen(customerDetails.getCitizen());
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
		model.put("productConfiguration", _pc);
		//model.put("bankConfiguration", bankConfiguration);
		model.put("fixedDepositForm", fixedDepositForm);
		model.put("val", val);
		model.put("customerId", customerDetails.getCustomerID());
		model.put("todaysDate", date);
		model.put("customerNriAccountType", customerDetails.getNriAccountType());
		model.put("cashPayment", 0);
		model.put("ddPayment", 0);
		model.put("chequePayment", 0);
		model.put("netBanking", 1);
		model.put("currency", currency);

		return new ModelAndView("reverseEmiFixedAmount", "model", model);

	}

	@RequestMapping(value = "/confirmReverseEmiFixedAmount", method = RequestMethod.POST)
	public ModelAndView confirmReverseEmiFixedAmount(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes) throws CustomException {

		Date maturityDate = null;
		fixedDepositForm.setFdFixed(0d);
		fixedDepositForm.setFdChangeable(fixedDepositForm.getFdAmount());
		fixedDepositForm.setDepositType(Constants.SINGLE);

		String[] accDetail = fixedDepositForm.getAccountNo().split(",");
		fixedDepositForm.setAccountNo(accDetail[0]);
		Date currentDate = DateService.getCurrentDate();

		// To get the Gestation End Date (Parameter Deposit Date and gestation
		// period)
		Date gestationDate = DateService.addYear(currentDate, fixedDepositForm.getGestationPeriod());
		Date gestationEndDate = DateService.addDays(gestationDate, -1);
		Customer customerDetails = getUserDetails();
		fixedDepositForm.setCitizen(customerDetails.getCitizen());
		Double totalDepositAmount = fixedDepositForm.getFdAmount(); // To get
																	// the
																	// Deposit
																	// amount
		double emiAmount = fixedDepositForm.getEmiAmount(); // to get the emi
															// amount

		// To get the interest date till gestation
		// List<Date> interestDateTillGestation =
		// fdService.getInterestDatesTillGestation(currentDate, gestationEndDate);

		// tenure we are calculating not considering the interest as
		// we do not get the interest rate without tenure

		// Getting the interest rate for the duration
		double emiFrequency = Math.floor(totalDepositAmount / emiAmount);
		// double remainEmiAmount = totalDepositAmount - emiAmount;
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
			return new ModelAndView("redirect:reverseEmiFixedAmount");
		}

		// fixedDepositForm.setFdTenure(daysTenure);
		fixedDepositForm.setFdTenureType("Days");
		fixedDepositForm.setInterestPayAmount((float) emiAmount);
		fixedDepositForm.setFdCreditAmount(rateOfInterest);
		fixedDepositForm.setMaturityDate(maturityDate);

		Float totalTDS = 0f;// fdService.getTotalTDSAmount(currentDate,
		
		fixedDepositForm.setEstimateTDSDeduct(totalTDS);
		

		/* / / PAY OFF DATE CALCULATION / / */

		Date payOffDueDate = fdService.getRevereseEMIPayOffDueDate(DateService.getCurrentDate(), gestationEndDate,
				maturityDate, fixedDepositForm.getPayOffInterestType(), false);
		fixedDepositForm.setPayoffDate(payOffDueDate);
		model.put("fixedDepositForm", fixedDepositForm);
		model.put("customerDetails", customerDetails);

		return new ModelAndView("confirmReverseEmiFixedAmount", "model", model);
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

	@RequestMapping(value = "/fdCustomerListForWithdraw", method = RequestMethod.GET)
	public ModelAndView fdCustomerListForWithdraw(ModelMap model, RedirectAttributes attributes)
			throws CustomException {

		ModelAndView mav = new ModelAndView();

		Customer user = getUserDetails();
		List<HolderForm> depositHolderList = depositService.getAllDepositByCustomerId(user.getId());

		if (depositHolderList != null) {

			model.put("depositHolderList", depositHolderList);
			mav = new ModelAndView("fdCustomerListForWithdraw", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/withdrawList", method = RequestMethod.GET)

	public ModelAndView withdrawList(@RequestParam Long id, ModelMap model, @ModelAttribute DepositForm depositForm)
			throws CustomException {
		List<WithdrawDeposit> withdrawList = withdrawDepositDAO.withdrawDepositListByDepositId(id);

		if (withdrawList.size() > 0) {

			model.put("customerName", depositForm.getCustomerName());
			model.put("withdrawList", withdrawList);

		} else {
			model.put("error", "No withdrawal found for deposit id:" + id);
			return new ModelAndView("noDataFoundCusm", "model", model);

		}
		return new ModelAndView("withdrawListCust", "model", model);
	}

	@RequestMapping(value = "/fundTransfer")
	public ModelAndView fundTransfer(ModelMap model, @ModelAttribute FundTransferForm fundTransferForm)
			throws CustomException {
		ModelAndView mav = new ModelAndView();

		Customer customerDetails = getUserDetails();
		List<HolderForm> id = fundTransferForm.getDepositHolderObjList();
		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(customerDetails.getId());

		List<HolderForm> depositHolderObjList = depositService.getAllDepositByCustomerId(customerDetails.getId());
		
		if (depositHolderObjList != null) {
			//ProductConfiguration pc=depositHolderObjList.get(0).getProductConfiguration();
			
			//Integer minTopup=pc.getMinimumTopupAmount();
			// model.put("minTopup", minTopup);
			model.put("depositHolderList", depositHolderObjList);
			mav = new ModelAndView("multipleDepositFundTransfer", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		fundTransferForm.setAccountList(accountList);
		fundTransferForm.setDepositHolderObjList(depositHolderObjList);
       
		model.put("fundTransferForm", fundTransferForm);

		model.put("accountList", accountList);

		return mav;
	}

	@RequestMapping(value = "/multipleDepositFundTransferPost", method = RequestMethod.POST)
	public @ResponseBody ModelAndView multipleDepositFundTransferPost(ModelMap model,
			@ModelAttribute FundTransferForm fundTransferForm,
			@RequestParam("transferArrList") String[] transferArrList) throws CustomException {

//		String accountNo = "";
//		String accountType = "";
		Customer customerDetails = getUserDetails();
		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(customerDetails.getId());
//		if (accountList.size() > 0) {
//			accountNo = accountList.get(0).getAccountNo();
//			accountType = accountList.get(0).getAccountType();
//		}


		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(Constants.FUNDTRANSFER);
		// multipleTransferAmount[786]|123#
		for (int i = 0; i < transferArrList.length; i++) {
			String arrString = transferArrList[i].replace("multipleTransferAmount[", "");
			Long depositId = Long.parseLong(arrString.substring(0, arrString.indexOf(']')));
			Double amount = Double.parseDouble(arrString.substring(arrString.indexOf('|') + 1, arrString.indexOf('#')));

			Deposit deposit = fixedDepositDAO.findByDepositId(depositId);
			String linkedAccountNo = deposit.getLinkedAccountNumber();

			String accountNo = "";
			String accountType = "";
			
			String transactionId = fdService.generateRandomString();

			AccountDetails accDetails = accountDetailsDAO.findByAccountNo(linkedAccountNo);
			if (accDetails != null) {
				accountNo = accDetails.getAccountNo();
				accountType = accDetails.getAccountType();
			}

			DepositHolder payyDepositHolder = depositHolderDAO.getDepositHolder(depositId, customerDetails.getId());

			Payment payment = new Payment();
			payment.setAmountPaid(amount);
			payment.setPaymentDate(DateService.getCurrentDateTime());
			payment.setPaymentMode(mop.getPaymentMode());
			payment.setPaymentModeId(mop.getId());
			payment.setLinkedAccNoForFundTransfer(accountNo);
			payment.setLinkedAccTypeForFundTransfer(accountType);
			payment.setCreatedBy(customerDetails.getCustomerName());
			payment.setDepositId(depositId);
			payment.setDepositHolderId(payyDepositHolder.getId());
			payment.setTransactionId(transactionId);
			payment = paymentDAO.insertPayment(payment);

		

			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);
			// For Regular deposits
			if (deposit.getDepositCategory() == null) {

				// Insert in Distributor and HolderWiseDistributor
				calculationService.insertInPaymentDistribution(deposit, depositHolderList, amount, payment.getId(),
						Constants.PAYMENT, null, null, null, null, null, null, null);

				// Insert in DepositSummary and HolderwiseDepositSummary
				DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.PAYMENT,
						amount, null, null, null, null, depositHolderList, null, null, null);

				// Insert in Journal & Ledger
				// -----------------------------------------------------------

					ledgerService.insertJournal(deposit.getId(), customerDetails.getId(), DateService.getCurrentDate(),
						 deposit.getAccountNumber(),accountNo, Event.TOPUP_DEPOSIT.getValue(),
						amount, mop.getId(), depositSummary.getTotalPrincipal(), transactionId);


				// -----------------------------------------------------------

			}
			// For ReverseEMI
			if (deposit.getDepositCategory() != null && deposit.getDepositCategory().equals(Constants.REVERSEEMI)) {

				fdService.insertInPaymentDistributionForReverseEMI(depositHolderList, amount, payment.getId(),
						Constants.PAYMENT);

				// Insert in DepositSummary and HolderwiseDepositSummary
				calculationService.upsertInDepositSummary(deposit, Constants.PAYMENT, amount, null, null, null, null,
						depositHolderList, null, null, null);

				// If the deposit in the Annuity deposit/ Reverse EMI then
				// change the emi amount as well as interest projection
				// get the deposit
				Integer gestationPeriod = deposit.getGestationPeriod();
				Date gestationEndDate = DateService.generateYearsDate(deposit.getCreatedDate(), gestationPeriod);

				Distribution lastDistribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());
				Double uncalculatedInterestAmount = fdService.getUncalculatedInterestAmount(deposit);

//				BankConfiguration bankConfiguration = ratesDAO.getBankConfiguration(customerDetails.getCitizen(),
//						customerDetails.getNriAccountType());
//				if (bankConfiguration != null)
//					interestCalculationBasis = bankConfiguration.getInterestCalculationBasis();
				ProductConfiguration productConfiguration = productConfigurationDAO.findById(deposit.getProductConfigurationId());
				String interestCalculationBasis  = productConfiguration.getInterestCalculationBasis();
				
				// Calculate the future EMI
				Double emiAmount = fdService.calculateEMIOnTopUp(lastDistribution.getTotalBalance(), gestationEndDate,
						deposit.getModifiedInterestRate(), DateService.getCurrentDate(), deposit.getNewMaturityDate(),
						deposit.getPayOffInterestType(), uncalculatedInterestAmount, interestCalculationBasis);

				deposit.setEmiAmount(emiAmount);
				fixedDepositDao.updateFD(deposit);
			}

		}
		return new ModelAndView("fdSaved", "model", model);
	}

	@RequestMapping(value = "/getEMIAmount", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Double getEMIAmount(String tenureType, Integer tenure, Double depositAmount,
			Integer gestationPeriod, String beneficiaryPayoffType, Integer day, String currency, String citizen,
			String nriAccountType,String productId) throws CustomException {
		System.out.println("In getEMIAmount in Bank Employee...");
		Date maturityDate = null;
		int days = 0;
		// String tenureType = "Month";
		Customer customerDetails = customerDAO.getByUserName(getCurrentLoggedUserName());
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
	
	@RequestMapping(value = "/getRateByCategory")
	public ModelAndView getRateByCategory(ModelMap model, @ModelAttribute RatesForm ratesForm) throws CustomException {

		String category = null;
		String currency = null;
		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
		for (CustomerCategory customerCategory : setCategory) {
			category = customerCategory.getCustomerCategory();
			if (category.equalsIgnoreCase(Constants.regular) || category.equalsIgnoreCase(Constants.general))
				break;
		}
		/*
		 * BankConfiguration bankConfiguration = ratesDAO.findAll(); if
		 * (ratesForm.getCurrency() == null) currency = bankConfiguration.getCurrency();
		 * else currency = ratesForm.getCurrency();
		 */

		// get the from amount slab from the Deposit Rates
		List<Double> amountFromSlablist = depositRatesDAO.getFromAmountSlabList(category, currency,
				Constants.fixedDeposit);
		model.put("amountFromSlablist", amountFromSlablist);

		Double amountSlabFrom = ratesForm.getAmountSlabFrom();
		if (amountFromSlablist != null && amountFromSlablist.size() > 0) {
			amountSlabFrom = amountFromSlablist.get(0);
			// this is temporary basis checking. This needs to remove
			if (amountSlabFrom == null && amountFromSlablist.size() > 1)
				amountSlabFrom = amountFromSlablist.get(1);

			if (amountSlabFrom != null) {
				model.put("selecteAmountFromSlablist", amountSlabFrom);
			}
		}

		List<String> depositClassificationList = new ArrayList<>();
		depositClassificationList.add(Constants.fixedDeposit);
		depositClassificationList.add(Constants.recurringDeposit);
		depositClassificationList.add(Constants.taxSavingDeposit);
		depositClassificationList.add(Constants.annuityDeposit);

		List<DepositRates> rateList = depositRatesDAO.getRatesByCategory(category, currency, Constants.fixedDeposit,
				ratesForm.getAmountSlabFrom(), ratesForm.getAmountSlabTo()).getResultList();
		if (setCategory.size() > 0) {
			if (category == null)
				category = list.get(0).getCustomerCategory();
			model.put("setCategory", setCategory);
			model.put("selectedCategory", category);

		}

		model.put("setCategory", setCategory);
		model.put("currency", currency);
		model.put("ratesForm", ratesForm);
		model.put("rateList", rateList);
		model.put("ratesForm", ratesForm);
		model.put("depositClassificationList", depositClassificationList);
		model.put("loginDate", DateService.loginDate);

		return new ModelAndView("viewRateForCustomer", "model", model);
	}
	
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
		model.put("citizenShip", citizenShip);
		model.put("currency", currencyType);
		model.put("depClassification", depClassification);
		model.put("customerCategory", customerCategory);

		return new ModelAndView("viewRateForCustomer", "model", model);

	}
	

	/*@RequestMapping(value = "/getRateByCategory")
	public ModelAndView getRateByCategory(ModelMap model, @ModelAttribute RatesForm ratesForm) throws CustomException {

		String category = null;
		String currency = null;
		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
		for (CustomerCategory customerCategory : setCategory) {
			category = customerCategory.getCustomerCategory();
			if (category.equalsIgnoreCase(Constants.regular) || category.equalsIgnoreCase(Constants.general))
				break;
		}
		BankConfiguration bankConfiguration = ratesDAO.findAll();
		if (ratesForm.getCurrency() == null)
			currency = bankConfiguration.getCurrency();
		else
			currency = ratesForm.getCurrency();

		// get the from amount slab from the Deposit Rates
		List<Double> amountFromSlablist = depositRatesDAO.getFromAmountSlabList(category, currency,
				Constants.fixedDeposit);
		model.put("amountFromSlablist", amountFromSlablist);

		Double amountSlabFrom = ratesForm.getAmountSlabFrom();
		if (amountFromSlablist != null && amountFromSlablist.size() > 0) {
			amountSlabFrom = amountFromSlablist.get(0);
			// this is temporary basis checking. This needs to remove
			if (amountSlabFrom == null && amountFromSlablist.size() > 1)
				amountSlabFrom = amountFromSlablist.get(1);

			if (amountSlabFrom != null) {
				model.put("selecteAmountFromSlablist", amountSlabFrom);
			}
		}

		List<String> depositClassificationList = new ArrayList<>();
		depositClassificationList.add(Constants.fixedDeposit);
		depositClassificationList.add(Constants.recurringDeposit);
		depositClassificationList.add(Constants.taxSavingDeposit);
		depositClassificationList.add(Constants.annuityDeposit);

		List<DepositRates> rateList = depositRatesDAO.getRatesByCategory(category, currency, Constants.fixedDeposit,
				ratesForm.getAmountSlabFrom(), ratesForm.getAmountSlabTo()).getResultList();
		if (setCategory.size() > 0) {
			if (category == null)
				category = list.get(0).getCustomerCategory();
			model.put("setCategory", setCategory);
			model.put("selectedCategory", category);

		}

		model.put("setCategory", setCategory);
		model.put("currency", currency);
		model.put("ratesForm", ratesForm);
		model.put("rateList", rateList);
		model.put("ratesForm", ratesForm);
		model.put("depositClassificationList", depositClassificationList);
		model.put("loginDate", DateService.loginDate);

		return new ModelAndView("viewRateForCustomer", "model", model);
	}

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
		model.put("citizenShip", citizenShip);
		model.put("currency", currencyType);
		model.put("depClassification", depClassification);
		model.put("customerCategory", customerCategory);

		return new ModelAndView("viewRateForCustomer", "model", model);

	}
	*/
	@RequestMapping(value = "/getRateListByCategory_Currency")
	public ModelAndView getRateBySelectedCategory(ModelMap model, @ModelAttribute RatesForm ratesForm,
			String customerCategory, String currency, String depositClassification, String citizen,
			String nriAccountType,String amountSlabFromVal , String amountSlabToVal) throws CustomException {

//		
//		String citizenType = citizen.split(",")[0];
//		String nriAccoType = nriAccountType.split(",")[0];
//		if (currency.contains(","))
//			currency = currency.substring(0, currency.indexOf(",", 0));
//		if (depositClassification.contains(","))
//			depositClassification = depositClassification.substring(0, depositClassification.indexOf(",", 0));
//		// String category = null;
//		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
//		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
//
//		List<Double> amountFromSlablist = depositRatesDAO.getFromAmountSlabList(customerCategory, currency, depositClassification);
//		model.put("amountFromSlablist", amountFromSlablist);
//		
//		List<DepositRates> rateList = depositRatesDAO.getRatesByCategoryDependsOnCitizen(customerCategory, currency ,depositClassification, ratesForm.getAmountSlabFrom(), ratesForm.getAmountSlabTo(),citizenType,nriAccoType).getResultList();
//		if (setCategory.size() > 0) {
//			if (customerCategory == null)
//				customerCategory = list.get(0).getCustomerCategory();
//
//			model.put("setCategory", setCategory);
//			model.put("selectedCategory", customerCategory);
//			
//		}
//		
//		List<String> depositClassificationList = new ArrayList<>();
//		depositClassificationList.add(Constants.fixedDeposit);
//		depositClassificationList.add(Constants.recurringDeposit);
//		depositClassificationList.add(Constants.taxSavingDeposit);
//		depositClassificationList.add(Constants.annuityDeposit);
//
//		ratesForm.setAmountSlabTo(null);
//		model.put("currency", currency);
//		model.put("depositClassification", depositClassification);
//		model.put("depositType", ratesForm.getType());
//		model.put("citizenType", citizenType);
//		model.put("ratesForm", ratesForm);
//		model.put("rateList", rateList);
//		model.put("setCategory", setCategory);
//		model.put("ratesForm", ratesForm);	
//		model.put("loginDate", DateService.loginDate);
//		model.put("depositClassificationList", depositClassificationList);

		String nriAccType = ratesForm.getNriAccountType();
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

		/// List<DepositRates> rateList =
		/// depositRatesDAO.getRatesByCategory(customerCategory, currency
		/// ,depositClassification, ratesForm.getAmountSlabFrom(),
		/// ratesForm.getAmountSlabTo()).getResultList();
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
		model.put("citizen", citizen);
		model.put("rateList", rateList);
		model.put("amountSlabFromVal", amountSlabFromVal);
		model.put("amountSlabToVal", amountSlabToVal);
		model.put("selectedFromSlab", ratesForm.getAmountSlabFrom());
		model.put("depositClassificationList", depositClassificationList);
		model.put("selectedDepositClassification", depositClassification);
		model.put("nriAccType", nriAccType);

		return new ModelAndView("viewRateForCustomer", "model", model);
	}
	
	@RequestMapping(value = "/sweepConfiguration", method = RequestMethod.GET)
	public ModelAndView sweepConfiguration(ModelMap model, @ModelAttribute SweepInFacilityForCustomer sweepInFacilityForCustomer) throws CustomException {
		Customer customer =  this.getUserDetails();
		System.out.println(customer.getId());
		AccountDetails accDetails = accountDetailsDAO.findSavingByCustId(customer.getId());
		SweepConfiguration configuration = sweepConfigurationDAO.getActiveSweepConfiguration();
		Boolean isTenure = false;
		if(configuration != null) {
			isTenure = configuration.getSweepInType().contains("Fixed Tenure");
				
		}
		model.put("accDetails", accDetails);
		if(accDetails == null){
			model.put("customerAccountTypeNotFound", "Sweep deposit can be configured only for Savings bank account");
			return new ModelAndView("customerAccountTypeNotFound", "model", model);
		}
	
		//sweepConfigurationForm.setIsSweepDepositRequired(accDetails.getIsSweepDepositRequired()==null?0 : accDetails.getIsSweepDepositRequired());
		
		SweepInFacilityForCustomer sweepConfiguration = sweepConfigurationDAO.getSweepInFacilityForCustomer(customer.getId());
		if(sweepConfiguration!=null)
		{
			model.put("isTenureConfigured", 1);
			model.put("minAmount",sweepConfiguration.getMinimumAmountRequiredForSweepIn());
		    model.put("minSavingBal",sweepConfiguration.getMinimumSavingBalanceForSweepIn());
			
		}
		else {
			Integer isSweepRequired = sweepInFacilityForCustomer.getIsSweepInConfigureedByCustomer()== null ? 0 : sweepInFacilityForCustomer.getIsSweepInConfigureedByCustomer();
			sweepConfiguration = new SweepInFacilityForCustomer();
			sweepConfiguration.setIsSweepInConfigureedByCustomer(isSweepRequired);
			System.out.println(configuration.getMinimumAmountRequiredForSweepIn());
			System.out.println(configuration.getMinimumSavingBalanceForSweepIn());
			model.put("minAmount",configuration.getMinimumAmountRequiredForSweepIn());
			model.put("minSavingBal",configuration.getMinimumSavingBalanceForSweepIn());
			model.put("isTenureConfigured", 0);
		}
		
		sweepConfiguration.setAccountId(accDetails.getId());
		sweepConfiguration.setCustomerId(customer.getId());
		
		model.put("sweepInFacilityForCustomer", sweepConfiguration);
		model.put("isTenure", isTenure);

		return new ModelAndView("sweepConfiguration", "model", model);

	}

	
	
	@RequestMapping(value = "/saveSweepConfiguration", method = RequestMethod.POST)
	public ModelAndView saveSweepConfiguration(ModelMap model, @ModelAttribute SweepInFacilityForCustomer sweepInFacilityForCustomer,
			RedirectAttributes attributes) throws CustomException {

		/*
		 * if (sweepConfigurationForm.getTenureType() == null ||
		 * sweepConfigurationForm.getTenure()==null) {
		 * attributes.addFlashAttribute(Constants.ERROR, Constants.invalidTenure);
		 * return new ModelAndView("redirect:sweepConfiguration"); }
		 */
		
		Integer isSweepRequired = sweepInFacilityForCustomer.getIsSweepInConfigureedByCustomer()== null ? 0 : sweepInFacilityForCustomer.getIsSweepInConfigureedByCustomer();
		fdService.saveSweepInFacilityForCustomer(0,sweepInFacilityForCustomer.getCustomerId(), isSweepRequired, 
				 sweepInFacilityForCustomer.getTenure(),sweepInFacilityForCustomer.getMinimumAmountRequiredForSweepIn(),sweepInFacilityForCustomer.getMinimumSavingBalanceForSweepIn());
		
		Date curDate = DateService.getCurrentDateTime();
		attributes.addFlashAttribute(Constants.TRANSACTIONID, fdService.generateRandomString());
		attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
		attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Saved Successfully");

		return new ModelAndView("redirect:fdSaved");
		
	}
	
	
	@RequestMapping(value = "/openNewDeposit")
	public ModelAndView newDeposit(ModelMap model) throws CustomException {

		return new ModelAndView("openNewDeposit", "model", model);
	}
	
	@RequestMapping(value = "/depositProducts")
	public ModelAndView depositProducts(ModelMap model, String productType) throws CustomException {
     
		Customer user = getUserDetails();
		List<ProductConfiguration> productConfigurationList = null;
		if(user.getCitizen().equalsIgnoreCase("RI"))
		{
			productConfigurationList= productConfigurationDAO.getProductConfigurationListByProductTypeAndCitizen(productType, "RI");
			model.put("productConfigurationList", productConfigurationList);
		}
		else
		{
			productConfigurationList = productConfigurationDAO.getProductConfigurationListByProductTypeAndCitizenAndNRIAccountType(productType, "NRI",user.getNriAccountType());
			model.put("productConfigurationList", productConfigurationList);
		}
		model.put("productType", productType);	
		
		return new ModelAndView("depositProductsForCustomer", "model", model);
	}
	
	@RequestMapping(value = "/createDeposit", method = RequestMethod.GET)
	public ModelAndView createDeposit(ModelMap model, Long productId) throws CustomException {

		model.put("productConfigurationId", productId);
		model.put("fixedDepositForm", fixedDepositForm);
		Customer customer=getUserDetails();
		model.put("customerInfo", customer);
		return new ModelAndView("applyDepositForCustomer", "model", model);

	}


	@RequestMapping(value = "/depositLists", method = RequestMethod.GET)
	public ModelAndView depositLists(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {
		ModelAndView mav = new ModelAndView();
		Long id = getCustomerId();
		List<DepositForm> depositLists = fixedDepositDAO.getAllRegularAndAutoDepositsByCustomerId(id);
		if (depositLists != null && depositLists.size() > 0) {
			depositForm.setDepositId(depositLists.get(0).getDepositId());
			model.put("depositLists", depositLists);
			mav = new ModelAndView("fdChangesListsForCust", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;
	}
	@RequestMapping(value = "/createSingleDeposit", method = RequestMethod.POST)
	public ModelAndView createSingleDeposit(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm, String val, @RequestParam String customerDetails, @RequestParam String productId)
			throws CustomException {

		Customer customer = customerDAO.getById(fixedDepositForm.getId());

		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(fixedDepositForm.getId());
		
		 List< Map<String,Object>> list = new ArrayList< Map<String,Object>>(); 
		fixedDepositForm.setCustomerName(customer.getCustomerName());
		fixedDepositForm.setCitizen(customer.getCitizen());
		fixedDepositForm.setAccountList(accountList);
		model.put("customer", customer);

		model.put("fixedDepositForm", fixedDepositForm);
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
        Long pId=Long.parseLong(productId);
		model.put("todaysDate", date);

       List<Branch> branch = branchDAO.getAllBranches();
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(pId);
		model.put("productConfiguration", productConfiguration);
	
		
		model.put("branch",branch);
		//model.put("branchCode",list);
		model.put("cashPayment", 0);
		model.put("ddPayment", 0);
		model.put("chequePayment", 0);
		model.put("netBanking", 1);
		model.put("nriAccountType", customer.getNriAccountType());
		model.put("val", val);
		model.put("customerDetails", customerDetails);

		return new ModelAndView("createSingleDepositCustomer", "model", model);

	}
	
}
