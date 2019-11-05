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
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
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
import org.json.JSONArray;
import org.json.JSONObject;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import annona.dao.AccountDetailsDAO;
import annona.dao.BankPaymentDAO;
import annona.dao.BenificiaryDetailsDAO;
import annona.dao.BranchDAO;
import annona.dao.CitizenAndCustomerCategoryDAO;
import annona.dao.CitizenConversionDetailsDAO;
import annona.dao.CurrencyConfigurationDAO;
import annona.dao.CustomerCategoryDAO;
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
import annona.dao.EventOperationsDAO;
import annona.dao.FixedDepositDAO;
import annona.dao.FormSubmissionDAO;
import annona.dao.GSTDeductionDAO;
import annona.dao.HolidayConfigurationDAO;
import annona.dao.InterestDAO;
import annona.dao.LedgerDAO;
import annona.dao.LedgerEventMappingDAO;
import annona.dao.LogDetailsDAO;
import annona.dao.ModeOfPaymentDAO;
import annona.dao.NRIServiceBranchesDAO;
import annona.dao.NomineeDAO;
import annona.dao.OrnamentSubmissionDetailsDAO;
import annona.dao.OrnamentSubmissionMasterDAO;
import annona.dao.OrnamentsMasterDAO;
import annona.dao.OverdraftIssueDAO;
import annona.dao.PaymentDAO;
import annona.dao.PaymentDistributionDAO;
import annona.dao.PayoffDAO;
import annona.dao.ProductConfigurationDAO;
import annona.dao.RatesDAO;
import annona.dao.RoundOffDAO;
import annona.dao.SweepConfigurationDAO;
import annona.dao.TDSDAO;
import annona.dao.TaxExemptionConfigurationDAO;
import annona.dao.TransactionDAO;
import annona.dao.UnSuccessfulPayOffDetailsDAO;
import annona.dao.UploadDAO;
import annona.dao.UploadFileDAO;
import annona.dao.WithdrawDepositDAO;
import annona.dao.WithdrawPenaltyDAO;
import annona.domain.AccountDetails;
import annona.domain.BankPaid;
import annona.domain.BankPayment;
import annona.domain.BankPaymentDetails;
import annona.domain.BenificiaryDetails;
import annona.domain.Branch;
import annona.domain.CitizenAndCustomerCategoryMapping;
import annona.domain.CitizenConversionDetails;
import annona.domain.CurrencyConfiguration;
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
import annona.domain.DepositWiseCustomerTDSForChart;
import annona.domain.Distribution;
import annona.domain.EndUser;
import annona.domain.EventOperations;
import annona.domain.FormSubmission;
import annona.domain.GLConfiguration;
import annona.domain.GSTDeduction;
import annona.domain.HolidayConfiguration;
import annona.domain.Interest;
import annona.domain.JointConsortiumNominees;
import annona.domain.Journal;
import annona.domain.LedgerEventMapping;
import annona.domain.LogDetails;
import annona.domain.LoginDate;
import annona.domain.ManageMenuPreference;
import annona.domain.Menu;
import annona.domain.ModeOfPayment;
import annona.domain.NRIServiceBranches;
import annona.domain.OrnamentSubmissionDetails;
import annona.domain.OrnamentSubmissionMaster;
import annona.domain.OrnamentsMaster;
import annona.domain.OverdraftIssue;
import annona.domain.OverdraftPayment;
import annona.domain.OverdraftWithdrawMaster;
import annona.domain.Payment;
import annona.domain.PayoffDetails;
import annona.domain.Permission;
import annona.domain.ProductConfiguration;
import annona.domain.RatePeriods;
import annona.domain.Rates;
import annona.domain.Role;
import annona.domain.RolePermissionMenu;
import annona.domain.RoundOff;
import annona.domain.SweepConfiguration;
import annona.domain.SweepInFacilityForCustomer;
import annona.domain.TaxExemptionConfiguration;
import annona.domain.Transaction;
import annona.domain.UnSuccessfulPayOff;
import annona.domain.UploadFile;
import annona.domain.UploadedFile;
import annona.domain.WithdrawDeposit;
import annona.domain.WithdrawPenaltyAmountBased;
import annona.domain.WithdrawPenaltyDetails;
import annona.domain.WithdrawPenaltyMaster;
import annona.dto.ManageRoleDTO;
import annona.dto.TaxExemptionConfigurationDTO;
import annona.dto.TaxExemptionConfigurationParser;
import annona.exception.CustomException;
import annona.exception.EndUserNotFoundException;
import annona.form.AccountDetailsForm;
import annona.form.AddCountryForDTAAForm;
import annona.form.AddCountryWiseTaxRateDTAAForm;
import annona.form.AddRateForm;
import annona.form.AutoDepositForm;
import annona.form.BankPaymentForm;
import annona.form.ColumnGraphForm;
import annona.form.CustomerCategoryForm;
import annona.form.CustomerDetailsParser;
import annona.form.CustomerForm;
import annona.form.DepositForm;
import annona.form.DepositHolderForm;
import annona.form.DepositRatesForm;
import annona.form.DepositsList;
import annona.form.EndUserForm;
import annona.form.FileForm;
import annona.form.FixedDepositForm;
import annona.form.FundTransferForm;
import annona.form.GLConfigurationForm;
import annona.form.HolderForm;
import annona.form.InterestCalculationForm;
import annona.form.JointAccount;
import annona.form.LedgerReportForm;
import annona.form.LoginForm;
import annona.form.OrnamentsForm;
import annona.form.OverdraftForm;
import annona.form.PayOfForm;
import annona.form.RatesForm;
import annona.form.ReportForm;
import annona.form.TDSForm;
import annona.form.UploadFileForm;
import annona.form.UploadedFileForm;
import annona.form.WithdrawForm;
import annona.form.WithdrawPenaltyForm;
import annona.form.WithdrawPenaltyFormList;
import annona.scheduler.NotificationsScheduler;
import annona.services.CalculationService;
import annona.services.ColumnGraphService;
import annona.services.DateService;
import annona.services.DepositService;
import annona.services.FDService;
import annona.services.ImageService;
import annona.services.LedgerService;
import annona.services.UploadService;
import annona.utility.Constants;
import annona.utility.Event;
import annona.utility.NRIAccountTypes;
import annona.utility.TaxExemptionConstants;

@RequestMapping("/common/")
@Controller
public class CommonController {

	@Autowired
	FormSubmissionDAO formSubmissionDao;

	@Autowired
	UploadFileDAO uploadFileDAO;

	@Autowired
	UploadFileForm uploadFileForm;

	@Autowired
	ProductConfigurationDAO productConfigurationDAO;

	@Autowired
	EndUserDAO endUserDAO;

	@Autowired
	DepositRateDAO depositRateDAO;

	@Autowired
	DepositRatesDAO depositRatesDAO;

	@Autowired
	RatesForm ratesForm;

	@Autowired
	FDService fdService;

	@Autowired
	EndUserDAO endUserDAOImpl;

	@Autowired
	private BranchDAO branchDAO;

	@Autowired
	private NRIServiceBranchesDAO nRIServiceBranchesDAO;

	@Autowired
	private CustomerDAO customerDAO;

	@Autowired
	private RoundOffDAO roundOffDAO;

	@Autowired
	private LedgerEventMappingDAO ledgerEventMappingDAO;

	@Autowired
	private EventOperationsDAO eventOperationsDAO;

	@Autowired
	private CitizenAndCustomerCategoryDAO citizenAndCustomerCategoryDAO;

	@Autowired
	private CustomerCategoryDAO customerCategoryDAO;

	@Autowired
	WithdrawForm withdrawForm;

	@Autowired
	private ModeOfPaymentDAO modeOfPaymentDAO;

	@Autowired
	private DTAACountryRatesDAO dtaaCountryRatesDAO;

	@Autowired
	private LogDetailsDAO logDetailsDAO;

	@Autowired
	private LedgerService ledgerService;

	@Autowired
	RatesDAO ratesDAO;

	@Autowired
	HolidayConfigurationDAO holidayConfigurationDAO;

	@Autowired
	CurrencyConfigurationDAO currencyConfigurationDAO;

	@Autowired
	LedgerDAO ledgerDAO;

	@Autowired
	private SweepConfigurationDAO sweepConfigurationDAO;

	@Autowired
	FixedDepositDAO fixedDepositDao;

	@Autowired
	WithdrawPenaltyDAO withdrawPenaltyDAO;

	@Autowired
	GSTDeductionDAO gstDeductionDAO;

	@Autowired
	NotificationsScheduler notificationScheduler;

	@Autowired
	CalculationService calculationService;

	@Autowired
	CustomerForm customerForm;

	@Autowired
	EndUserForm endUserForm;
	@Autowired
	AccountDetailsDAO accountDetailsDAO;

	@Autowired
	PaymentDAO paymentDAO;

	@Autowired
	TransactionDAO transactionDAO;

	@Autowired
	DepositHolderDAO depositHolderDAO;

	@Autowired
	PayoffDAO payOffDAO;

	@Autowired
	NomineeDAO nomineeDAO;

	@Autowired
	FixedDepositForm fixedDepositForm;

	@Autowired
	PaymentDistributionDAO paymentDistributionDAO;

	@Autowired
	BenificiaryDetailsDAO benificiaryDetailsDAO;

	@Autowired
	DepositModificationDAO depositModificationDAO;

	@Autowired
	OverdraftIssueDAO overdraftIssueDAO;

	@Autowired
	WithdrawDepositDAO withdrawDepositDAO;

	@Autowired
	AddRateForm addRateForm;

	@Autowired
	DepositHolderWiseConsolidatedInterestDAO depositHolderWiseConsolidatedInterestDAO;

	@Autowired
	AccountDetailsForm accountDetailsForm;

	@Autowired
	UploadDAO uploadDAO;

	@Autowired
	InterestDAO interestDAO;

	@Autowired
	DepositService depositService;

	@Autowired
	DepositsList depositsList;

	@Autowired
	MailSender mailSender;

	@Autowired
	DepositHolderWiseDistributionDAO depositHolderWiseDistributionDAO;

	@Autowired
	DepositForm depositForm;

	@Autowired
	TDSDAO tdsDAO;

	@Autowired
	UnSuccessfulPayOffDetailsDAO unSuccessfulPayOffDetailsDAO;

	@Autowired
	BankPaymentDAO bankPaymentDAO;

	@Autowired
	CitizenConversionDetailsDAO citizenConversionDetailsDAO;

	@Autowired
	PayOfForm payOfForm;

	@Autowired
	ServletContext context;

	@Autowired
	UploadService uploadService;

	@Autowired
	DepositSummaryDAO depositSummaryDAO;

	@Autowired
	OrnamentsMasterDAO ornamentsMasterDAO;

	@Autowired
	OrnamentSubmissionDetailsDAO ornamentSubmissionDetailsDAO;

	@Autowired
	OrnamentSubmissionMasterDAO ornamentSubmissionMasterDAO;

	@Autowired
	private TaxExemptionConfigurationDAO taxExemptionConfigurationDAO;

	private String currentRole;

	private final String COMMA_SEPARATER = ",";

	private EndUser currentLoggedInUser;

	private String branch = null;
	private String branchCode = null;
	List<Menu> menus = new ArrayList<Menu>();
	Long ROLE_ID;

	private EndUser getCurrentLoggedInEndUser() {

		EndUser endUser = endUserDAO.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
				.getSingleResult();
		this.currentLoggedInUser = endUser;
		return endUser;
	}

	private String getCurrentLoggedUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();

	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	private Model menuUtils(Model model, Long menuId) {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		model.addAttribute("branch", this.branch);
		return model;

	}

	private List<Menu> getCurrentRoleMenus(Long roleId) {
		ROLE_ID = roleId;
		List<Menu> menus = endUserDAO.findByRoleId(roleId);
		List<Menu> sortedMenuList = new ArrayList<Menu>();
		for (int i = 0; i < menus.size(); i++) {
			Menu menuOrdered = menus.get(i);
			if (menus.get(i).getMenu() == null) {

				ManageMenuPreference manageMenuPreferences = endUserDAO
						.findByMenuIdAndRoleIdManageMenuPreference(menus.get(i).getId(), roleId);
				if (manageMenuPreferences != null) {

					/*
					 * if (menuOrdered.getChildMenuItems().size() > 0) { for (int subMenuIndex = 0;
					 * subMenuIndex < manageMenuPreferences.getSubMenuId().size(); subMenuIndex++) {
					 * Menu subMenu =
					 * endUserDAO.findBySubMenuId(menuOrdered.getChildMenuItems().get(subMenuIndex).
					 * getId());
					 * 
					 * menuOrdered.getChildMenuItems().set(subMenuIndex, subMenu); }
					 * 
					 * }
					 */
					if (manageMenuPreferences.getMenuPrefrenceIndex() != null) {
						menuOrdered.setMenuIndex(manageMenuPreferences.getMenuPrefrenceIndex());
					} else {
						menuOrdered.setMenuIndex(1000);
					}

				} else {
					menuOrdered.setMenuIndex(1000);
				}
				sortedMenuList.add(menuOrdered);
			}
			/*
			 * else { menuOrdered.setMenuIndex(1000); }
			 */

			/*
			 * else { this.menus = menus; return menus; }
			 */
		}
		if (sortedMenuList.size() > 0) {

			Collections.sort(sortedMenuList, new SortbyMenuIndex());

			this.menus = sortedMenuList;
			return sortedMenuList;
		} else {
			this.menus = menus;
		}

		return menus;

	}

	class SortbyMenuIndex implements Comparator<Menu> {

		public int compare(Menu a, Menu b) {
			return a.getMenuIndex() - b.getMenuIndex();
		}
	}

	@RequestMapping(value = "/dashboard")
	public ModelAndView dashboard(Model model, @RequestParam("roleId") Long roleId,
			RedirectAttributes redirectAttributes) {
		EndUser endUser = this.getCurrentLoggedInEndUser();
		List<Menu> menus = this.getCurrentRoleMenus(roleId);
		List<Branch> allList = branchDAO.getAllBranches();
		redirectAttributes.addFlashAttribute("branches", allList);
		redirectAttributes.addFlashAttribute("menus", menus);
		redirectAttributes.addFlashAttribute("method", RequestMethod.GET);
		redirectAttributes.addFlashAttribute("roleId", roleId);
		redirectAttributes.addFlashAttribute("endUser", endUser);
		Role role = endUserDAO.findById(roleId);
		if (role.getRole().equalsIgnoreCase("ROLE_USER")) {
			this.branch = role.getRole(); // Garbage value only for customer login not show branch pop up
			redirectAttributes.addAttribute("menuId", menus.get(0).getId());
			return new ModelAndView("redirect:homePage");
		} else {
			this.branch = "";
			redirectAttributes.addFlashAttribute("branch", this.branch);
			return new ModelAndView("redirect:bankEmpDashboard");
		}

	}

	@RequestMapping(value = "/bankEmpDashboard")
	public ModelAndView bankEmpDashboard(ModelMap model) throws CustomException {
		return new ModelAndView("dashboard", "model", model);
	}

	@RequestMapping(value = "branchSelect", method = RequestMethod.POST)
	public ModelAndView branchSelect(Model model, @RequestParam("roleId") Long roleId, @ModelAttribute Branch branch) {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		Branch branch_ = branchDAO.getBranchById(branch.getId());
		model.addAttribute("method", RequestMethod.GET);
		model.addAttribute("menus", menus);
		model.addAttribute("roleId", roleId);
		model.addAttribute("endUser", endUser);
		model.addAttribute("success", "Branch selected sucessfully !");
		this.branch = branch_.getBranchName();
		this.branchCode = branch_.getBranchCode();
		model.addAttribute("branch", this.branch);
		return new ModelAndView("dashboard");

	}

	// @RequestMapping(value = "home")
	// public ModelAndView home(Model model, @RequestParam("menuId") Long menuId) {
	// EndUser endUser = this.currentLoggedInUser;
	// List<Menu> menus = this.menus;
	// Menu menu = endUserDAO.findByMenuId(menuId);
	// model.addAttribute("method", RequestMethod.GET);
	// model.addAttribute("menus", menus);
	// model.addAttribute("menuId", endUserDAO.findByMenuId(menuId));
	// model.addAttribute("endUser", endUser);
	// model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
	// model.addAttribute("branch", this.branch);
	// return new ModelAndView("commonDashboard");
	//
	// }

	@RequestMapping(value = "selectLoggedInRole", method = RequestMethod.GET)
	private ModelAndView selectLoggedInRole(@RequestParam(required = false) String role) {
		this.currentRole = role;
		return new ModelAndView("redirect:/main/default");

	}

	@RequestMapping(value = "/updateTransaction", method = RequestMethod.GET)
	public RedirectAttributes updateTransaction(String status, RedirectAttributes attribute) {

		Date curDate = DateService.loginDate;
		attribute.addFlashAttribute(Constants.TRANSACTIONID, fdService.generateRandomString());
		attribute.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
		attribute.addFlashAttribute(Constants.TRANSACTIONSTATUS, status);

		return attribute;
	}

	// Arpita
	// -------------------------------------------------------------------------------------
	@RequestMapping(value = "/addDurationSlab")
	public ModelAndView duration(Model model, @ModelAttribute AddRateForm addRateForm,
			@RequestParam("menuId") Long menuId) throws CustomException {

		model = this.menuUtils(model, menuId);

		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
		if (setCategory.size() > 0) {

			model.addAttribute("setCategory", setCategory);
			model.addAttribute("addRateForm", addRateForm);
			model.addAttribute("list", list);
		}
		// BankConfiguration bankConfiguration = ratesDAO.findAll();
		// if (bankConfiguration != null) {
		// model.addAttribute("bankConfiguration", bankConfiguration);
		// }
		model.addAttribute("list", list);
		model.addAttribute("setCategory", setCategory);
		model.addAttribute("addRateForm", addRateForm);
		return new ModelAndView("addDurationSlab", "model", model);

	}

	@RequestMapping(value = "/durationPost", method = RequestMethod.POST, produces = "text/html")
	public ModelAndView durationPost(Model model, @ModelAttribute AddRateForm addRateForm, RedirectAttributes attribute,
			@RequestBody String myArray, @RequestParam("menuId") Long menuId)
			throws UnsupportedEncodingException, CustomException {
		model = this.menuUtils(model, Long.valueOf(menuId));
		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {
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
				RatePeriods ratePeriodForDuplicateChecking = depositRateDAO.getRatePeriods(startDay, endDay,
						category[0], addRateForm.getCitizen(), addRateForm.getNriAccountType(),
						addRateForm.getDepositClassification());

				if (ratePeriodForDuplicateChecking == null) {
					depositRateDAO.createDuration(ratePeriods);
				}
			}

			attribute = updateTransaction("Saved Successfully", attribute);
			model.addAttribute("list", list);
			model.addAttribute("category", category);
			return new ModelAndView("");
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("addDurationSlab", "model", model);
		}

	}

	@RequestMapping(value = "/viewDurationSlab")
	public ModelAndView viewDuration(Model model, @ModelAttribute AddRateForm addRateForm,
			@RequestParam("menuId") Long menuId) throws CustomException {

		model = this.menuUtils(model, menuId);

		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
		if (setCategory.size() > 0) {

			model.addAttribute("setCategory", setCategory);
			model.addAttribute("addRateForm", addRateForm);
			model.addAttribute("list", list);

		}
		/*
		 * BankConfiguration bankConfiguration = ratesDAO.findAll(); if
		 * (bankConfiguration != null) { model.addAttribute("bankConfiguration",
		 * bankConfiguration); }
		 */
		model.addAttribute("list", list);
		model.addAttribute("menus", menus);
		model.addAttribute("setCategory", setCategory);
		model.addAttribute("addRateForm", addRateForm);

		return new ModelAndView("viewDurationSlab", "model", model);

	}

	@RequestMapping(value = "/getDurations", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<RatePeriods> getDurations(String citizen, String nriAccountType, String customerCategory,
			String depositClassification) {
		List<RatePeriods> ratesPeriod = ratesDAO.getRateDurations(depositClassification, citizen, nriAccountType,
				customerCategory);

		return ratesPeriod;
	}

	@RequestMapping(value = "/addRates")
	public ModelAndView addRates(Model model, @ModelAttribute AddRateForm addRateForm, String nriAccType,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);

		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);

		if (setCategory.size() > 0) {

			model.addAttribute("setCategory", setCategory);
			model.addAttribute("addRateForm", addRateForm);
			model.addAttribute("list", list);

		}
		List<String> depositClassificationList = new ArrayList<>();
		depositClassificationList.add(Constants.fixedDeposit);
		depositClassificationList.add(Constants.recurringDeposit);
		depositClassificationList.add(Constants.taxSavingDeposit);
		depositClassificationList.add(Constants.annuityDeposit);
		model.addAttribute("depositClassificationList", depositClassificationList);

		model.addAttribute("nriAccType", nriAccType);
		model.addAttribute("list", list);
		model.addAttribute("menus", menus);
		model.addAttribute("setCategory", setCategory);
		model.addAttribute("addRateForm", addRateForm);
		model.addAttribute("menuId", menuId);
		return new ModelAndView("addRates", "model", model);

	}

	@RequestMapping(value = "/getRateDurationsByDepositClaasification")
	public ModelAndView getRateDurationsByDepositClaasification(Model model, @ModelAttribute AddRateForm addRateForm,
			String depositClassification, String category, String nriAccType, String currency_, Long menuId)
			throws CustomException {
		model = this.menuUtils(model, menuId);
		Boolean isPermission = this.checkPermissions(menuId, "WRITE");

		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);

		if (setCategory.size() > 0) {

			model.addAttribute("setCategory", setCategory);
			model.addAttribute("addRateForm", addRateForm);
			model.addAttribute("list", list);

		}
		List<String> depositClassificationList = new ArrayList<>();
		depositClassificationList.add(Constants.fixedDeposit);
		depositClassificationList.add(Constants.recurringDeposit);
		depositClassificationList.add(Constants.taxSavingDeposit);
		depositClassificationList.add(Constants.annuityDeposit);
		model.addAttribute("depositClassificationList", depositClassificationList);

		model.addAttribute("nriAccType", nriAccType);
		model.addAttribute("list", list);
		model.addAttribute("menus", menus);
		model.addAttribute("setCategory", setCategory);
		model.addAttribute("addRateForm", addRateForm);

		if (isPermission) {

			if (setCategory.size() > 0) {

				model.addAttribute("setCategory", setCategory);
				model.addAttribute("addRateForm", addRateForm);
				model.addAttribute("list", list);

			}
			if (depositClassification.contains(","))
				depositClassification = depositClassification.substring(0, depositClassification.indexOf(",", 0));
			// String category = null;

			if (category.contains(","))
				category = category.substring(0, category.indexOf(",", 0));

			List<RatePeriods> ratesPeriod = ratesDAO.getRateDurations(depositClassification, addRateForm.getCitizen(),
					addRateForm.getNriAccountType(), category);
			if (ratesPeriod != null) {
				model.addAttribute("ratesPeriod", ratesPeriod);
			}
			if (ratesPeriod == null) {
				model.addAttribute(Constants.ERROR, Constants.durationError);
			}
			model.addAttribute("ratesPeriod", ratesPeriod);
			model.addAttribute("selectedDepositClassification", depositClassification);
			model.addAttribute("category", category);
			model.addAttribute("nriAccType", nriAccType);
			model.addAttribute("currency_", currency_);

			return new ModelAndView("addRates", "model", model);
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("addRates", "model", model);

		}

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "addRatePost", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody ModelAndView addRatePost(Model model, @ModelAttribute AddRateForm addRateForm,
			RedirectAttributes attribute, @RequestParam("rateArrList") String[] rateArrList,
			@RequestParam("dataString") String[] dataString, String citizen, String nriAccountType,
			@RequestParam("menuId") Long menuId) throws UnsupportedEncodingException, CustomException {
		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {
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

				Date effectiveDate = addRateForm.getEffectiveDate();
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
				depositRates.setEffectiveDate(addRateForm.getEffectiveDate());
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
			model.addAttribute("list", list);
			return new ModelAndView("redirect:successfullySaved");
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("addRates", "model", model);
			// return new ModelAndView("editModeOfPayment", "model", model);
		}

	}

	@RequestMapping(value = "/viewRates")
	public ModelAndView viewRates(Model model, String nriAccType, String citizen, String currency,
			String depositClassification, String customerCategory, @RequestParam("menuId") Long menuId)
			throws CustomException {
		model = this.menuUtils(model, menuId);

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

			model.addAttribute("setCategory", setCategory);
			model.addAttribute("ratesForm", ratesForm);

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

		model.addAttribute("depositClassificationList", depositClassificationList);
		model.addAttribute("setCategory", setCategory);
		model.addAttribute("ratesForm", ratesForm);
		model.addAttribute("loginDate", DateService.loginDate);
		model.addAttribute("nriAccountType", nriAccType);
		model.addAttribute("citizenType", citizenShip);
		model.addAttribute("currency", currencyType);
		model.addAttribute("depClassification", depClassification);
		model.addAttribute("customerCategory", customerCategory);

		return new ModelAndView("viewRates", "model", model);

	}

	@RequestMapping(value = "/getAmountFromSlab", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<Double> getAmountFromSlab(Model model, @ModelAttribute RatesForm ratesForm,
			String customerCategory, String currency, String depositClassification, Double amountSlabFrom,
			String citizen, String nriAccountType) throws CustomException {

		// get the from amount slab from the Deposit Rates
		List<Double> amountFromSlablist = depositRatesDAO.getFromAmountSlabListForViewRate(customerCategory, currency,
				depositClassification, citizen, nriAccountType);

		return amountFromSlablist;
	}

	@RequestMapping(value = "/getRateListByCategory_Currency")
	public ModelAndView getRateListBySelectedCategory(Model model, @ModelAttribute RatesForm ratesForm,
			String customerCategory, String currency, String depositClassification, String amountSlabFromVal,
			String amountSlabToVal, Long menuId) throws CustomException {

		model = this.menuUtils(model, menuId);

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
		model.addAttribute("amountFromSlablist", amountFromSlablist);

		List<DepositRates> rateList = depositRatesDAO.getRatesByCategoryAndCitizen(customerCategory, currency,
				depositClassification, ratesForm.getAmountSlabFrom(), ratesForm.getAmountSlabTo(), nriAccType)
				.getResultList();

		if (setCategory.size() > 0) {
			if (customerCategory == null)
				customerCategory = list.get(0).getCustomerCategory();
			model.addAttribute("setCategory", setCategory);

		}

		List<String> depositClassificationList = new ArrayList<>();
		depositClassificationList.add(Constants.fixedDeposit);
		depositClassificationList.add(Constants.recurringDeposit);
		depositClassificationList.add(Constants.taxSavingDeposit);
		depositClassificationList.add(Constants.annuityDeposit);

		ratesForm.setAmountSlabTo(null);
		model.addAttribute("setCategory", setCategory);
		model.addAttribute("loginDate", DateService.loginDate);
		model.addAttribute("selectedCategory", customerCategory);
		model.addAttribute("currency", currency);
		model.addAttribute("ratesForm", ratesForm);
		model.addAttribute("rateList", rateList);
		model.addAttribute("selectedFromSlab", ratesForm.getAmountSlabFrom());
		model.addAttribute("depositClassificationList", depositClassificationList);
		model.addAttribute("selectedDepositClassification", depositClassification);
		model.addAttribute("nriAccType", nriAccType);
		model.addAttribute("citizen", citizen);

		model.addAttribute("ratesForm", ratesForm);
		model.addAttribute("rateList", rateList);
		model.addAttribute("selectedFromSlab", amountSlabFromVal);
		model.addAttribute("selectedFromTo", amountSlabToVal);
		model.addAttribute("depositClassificationList", depositClassificationList);
		model.addAttribute("selectedDepositClassification", depositClassification);
		model.addAttribute("nriAccType", nriAccType);
		return new ModelAndView("viewRates", "model", model);
	}

	@RequestMapping(value = "/getAmountToSlab", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Double getAmountToSlab(Model model, @ModelAttribute RatesForm ratesForm,
			String customerCategory, String currency, String depositClassification, Double amountSlabFrom)
			throws CustomException {

		Double toSlab = depositRatesDAO.getToAmountSlab(customerCategory, currency, depositClassification,
				amountSlabFrom);
		return toSlab;

	}

	@RequestMapping(value = "/successfullySaved", method = RequestMethod.GET)
	public ModelAndView successfullySaved(Model model) {
		EndUser endUser = this.currentLoggedInUser;
		model.addAttribute("endUser", endUser);
		model.addAttribute("menus", menus);
		return new ModelAndView("successfullySaved", "model", model);

	}
	// ------Arpita
	// finishes-----------------------------------------------------------------------------------

	// ------------Nagarjuna Starts------------------------------
	@RequestMapping(value = "/homePage", method = RequestMethod.GET)
	public ModelAndView fdCustomerLists(Model model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		Role role = endUserDAO.findById(ROLE_ID);
		ModelAndView mav = new ModelAndView();
		if (role.getRole().equals("ROLE_USER")) {
			EndUser endUser = this.getCurrentLoggedInEndUser();
			Customer customer = customerDAO.getById(endUser.getCustomerId());
			List<Deposit> depositLists = fixedDepositDao.getDeposits(endUser.getCustomerId());

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
			List<HolderForm> depositHolderList = depositService.getAllDepositByCustomerId(endUser.getCustomerId());

			// Get all open deposits of this customer
			List<Deposit> depositList = fixedDepositDao.getDeposits(endUser.getCustomerId());

			// Fill the class from customer TDS
			List<DepositWiseCustomerTDSForChart> depositWiseTDSList = this.getCustomerTDSforChart(depositList,
					endUser.getCustomerId());

			if (depositHolderList != null) {
				model.addAttribute("depositHolderList", depositHolderList);

			} else {
				return new ModelAndView("noDataFoundCusm", "model", model);
			}
			model.addAttribute("values", values);
			model.addAttribute("categories", categories);
			model.addAttribute("user", customer);
			model.addAttribute("depositWiseTDSList", depositWiseTDSList);
			model.addAttribute("depositList", depositList);
			model.addAttribute("loginDate", DateService.loginDate);
			mav = new ModelAndView("userDashboard", "model", model);
		} else
			mav = new ModelAndView("dashboard", "model", model);
		return mav;
	}

	@RequestMapping(value = "/addBranch")
	public ModelAndView addBranch(Model model, @ModelAttribute Branch branch, @RequestParam("menuId") Long menuId)
			throws CustomException {
		model = this.menuUtils(model, menuId);
		List<Branch> allList = branchDAO.getAllBranches();
		model.addAttribute("allList", allList);
		model.addAttribute("branch", branch);
		model.addAttribute("menus", menus);
		return new ModelAndView("addBranch", "model", model);

	}

	@RequestMapping(value = "/addedBranch", method = RequestMethod.POST)
	public ModelAndView addedBranch(Model model, @ModelAttribute Branch branch, RedirectAttributes attribute,
			Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		String branchCodeAdded = branch.getBranchCode();
		String branchNameAdded = branch.getBranchName();
		List<Branch> allList = branchDAO.getAllBranches();
		model.addAttribute("allList", allList);
		model.addAttribute("branch", branch);
		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {

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

				model.addAttribute(Constants.ERROR, Constants.BranchAlreadyExist);
				return new ModelAndView("addBranch", "model", model);
			}
			attribute = updateTransaction("Saved Successfully", attribute);
			return new ModelAndView("redirect:successfullySaved");
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("addBranch", "model", model);
			// return new ModelAndView("editModeOfPayment", "model", model);
		}
	}

	@RequestMapping(value = "/nriServiceBranch")
	public ModelAndView nriServiceCentres(Model model, @ModelAttribute NRIServiceBranches nRIServiceBranches,
			@RequestParam("menuId") Long menuId) throws CustomException {

		model = this.menuUtils(model, menuId);
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
		model.addAttribute("nRIServiceBranchesList", nRIServiceBranchesList);
		model.addAttribute("nRIServiceBranches", nRIServiceBranches);
		model.addAttribute("branchList", branchList);
		return new ModelAndView("nriServiceBranch", "model", model);

	}

	@RequestMapping(value = "/saveNRIServiceBranches", method = RequestMethod.POST)
	public ModelAndView saveNRIServiceBranches(Model model, @ModelAttribute NRIServiceBranches nRIServiceBranches,
			@RequestParam String[] selectedNRIBranches, RedirectAttributes attributes,
			@RequestParam("menuId") Long menuId) {
		try {
			model = this.menuUtils(model, menuId);

			Boolean isPermission = this.checkPermissions(menuId, "WRITE");
			if (isPermission) {
				String[] array = selectedNRIBranches;
				List<NRIServiceBranches> nriBranches = nRIServiceBranchesDAO.getAllNRIServiceBranches();
				for (int i = 0; i < array.length; i++) {
					NRIServiceBranches newNRIServiceBranches = new NRIServiceBranches();
					newNRIServiceBranches.setBranchCode(array[i]);
					Branch branch = branchDAO.getBranchByBranchCode(array[i]);
					newNRIServiceBranches.setBranchId(branch.getId());
					List<NRIServiceBranches> isExistig = nRIServiceBranchesDAO
							.getNRIServiceBranchesByBranchCode(array[i]);
					if ((isExistig != null)) {
						nRIServiceBranchesDAO.delete(array[i]);
					}
					newNRIServiceBranches.setIsSelected(true);
					nRIServiceBranchesDAO.save(newNRIServiceBranches);
					List<NRIServiceBranches> isNotExistig = nRIServiceBranchesDAO
							.getNRIServiceBranchesNotInByBranchCode(array);
					for (NRIServiceBranches nriBranch : isNotExistig) {
						nriBranch.setIsSelected(false);
						nRIServiceBranchesDAO.update(nriBranch);
					}
				}
				model.addAttribute("menus", menus);
				attributes = updateTransaction("Saved Successfully", attributes);
				return new ModelAndView("redirect:successfullySaved");
			} else {
				model.addAttribute("error", "You do not have sufficient permissions !");
				return new ModelAndView("nriServiceBranch", "model", model);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("nriServiceBranch", "model", model);
	}

	@RequestMapping(value = "/addCustomerCategory")
	public ModelAndView addCustomerCategory(Model model, @RequestParam("menuId") Long menuId,
			@ModelAttribute CustomerCategoryForm customerCategoryForm) throws CustomException {
		model = this.menuUtils(model, menuId);
		List<CustomerCategory> allList = customerDAO.getAllCustomerCategory();
		model.addAttribute("allList", allList);
		model.addAttribute("addCustomerCategoryForm", customerCategoryForm);
		return new ModelAndView("addCustomerCategory", "model", model);

	}

	@RequestMapping("/editCustomerCategory")
	public ModelAndView editCustomerCategory(Model model,@RequestParam("customerCategory") String customerCategory,
			@RequestParam("id") String id,@RequestParam("menuId") Long menuId,@ModelAttribute CustomerCategoryForm customerCategoryForm) {
		System.out.println("_________________________" + customerCategory);
		System.out.println("_________________________" + id);
		
		
		model = this.menuUtils(model, menuId);
		
		CustomerCategory c=customerDAO.getCustomerCategoryById(Long.parseLong(id));
		System.out.println(c.getCustomerCategory());
		c.setCustomerCategory(customerCategory);
		customerDAO.updateCustomerCategory(c);
		System.out.println(c.getCustomerCategory());	
		
		List<CustomerCategory> allList = customerDAO.getAllCustomerCategory();
		model.addAttribute("allList", allList);
	
		return new ModelAndView("addCustomerCategory", "model", model);

	}

	@RequestMapping(value = "/addedCustomer", method = RequestMethod.POST)
	public ModelAndView addedCustomer(Model model, @ModelAttribute CustomerCategoryForm customerCategoryForm,
			RedirectAttributes attribute, @RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);

		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {
			EndUser user = this.currentLoggedInUser;
			String customerCategoryAdded = customerCategoryForm.getCustomerCategory();
			List<CustomerCategory> allList = customerDAO.getAllCustomerCategory();
			model.addAttribute("allList", allList);
			model.addAttribute("menus", menus);
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

				model.addAttribute(Constants.ERROR, Constants.AlreadyExist);
				return new ModelAndView("addCustomerCategory", "model", model);
			}
			attribute = updateTransaction("Saved Successfully", attribute);
			return new ModelAndView("redirect:successfullySaved");
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("addCustomerCategory", "model", model);

		}
	}

	@RequestMapping(value = "/citizenAndCustomerCategory")
	public ModelAndView citizenAndCustomerCategory(Model model,
			@ModelAttribute CitizenAndCustomerCategoryMapping citizenAndCustomerCategory,
			@RequestParam("menuId") Long menuId) {
		model = this.menuUtils(model, menuId);
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
			RedirectAttributes redirectAttributes, String nriTypes, @RequestParam("menuId") Long menuId) {
		try {

			model = this.menuUtils(model, menuId);

			Boolean isPermission = this.checkPermissions(menuId, "WRITE");
			if (isPermission) {
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
						return new ModelAndView("redirect:successfullySaved");
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
						model.addAttribute("menus", menus);
						redirectAttributes = updateTransaction("Saved Successfully", redirectAttributes);
						return new ModelAndView("redirect:successfullySaved");
					} else {
						model.addAttribute("error", "Customer category id not found");
						return new ModelAndView("citizenAndCustomerCategory", "model", model);
					}
				}
			} else {
				model.addAttribute("error", "You do not have sufficient permissions !");
				return new ModelAndView("citizenAndCustomerCategory", "model", model);

			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());

		}
		return new ModelAndView("citizenAndCustomerCategory", "model", model);
	}

	@RequestMapping(value = "/loginDate", method = RequestMethod.GET)
	ModelAndView logindate(Model model, @ModelAttribute LoginForm loginForm, @RequestParam("menuId") Long menuId)
			throws CustomException {
		model = this.menuUtils(model, menuId);
		Date loginDateDetails = logDetailsDAO.findLoginDate();
		if (loginDateDetails != null) {
			loginForm.setLoginDate(loginDateDetails);
		}
		model.addAttribute("loginForm", loginForm);
		return new ModelAndView("loginDate", "model", model);

	}

	@RequestMapping(value = "/logindateSave", method = RequestMethod.POST)
	ModelAndView logindateSave(Model model, @ModelAttribute LoginForm loginForm, RedirectAttributes attributes,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {
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
				model.addAttribute("menus", menus);
				attributes = updateTransaction("Updated Successfully", attributes);

			}

			return new ModelAndView("redirect:loginDateSaved");
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("loginDate", "model", model);
			// return new ModelAndView("editModeOfPayment", "model", model);
		}

	}

	@RequestMapping(value = "/loginDateSaved", method = RequestMethod.GET)
	public ModelAndView loginDateSaved(Model model) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		return new ModelAndView("loginDateSaved", "model", model);

	}

	@RequestMapping(value = "/loginDateForJsp", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Date loginDateForJsp() throws CustomException {

		return DateService.loginDate;
	}

	@RequestMapping(value = "/modeOfPayment")
	public ModelAndView modeOfPayment(Model model, @ModelAttribute ModeOfPayment modeOfPayment,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		List<ModeOfPayment> allList = modeOfPaymentDAO.getAllModeOfPaymentDetails();
		model.addAttribute("allList", allList);
		model.addAttribute("modeOfPayment", modeOfPayment);
		return new ModelAndView("modeOfPayment", "model", model);

	}

	@RequestMapping(value = "/addModeOfPayment", method = RequestMethod.POST)
	public ModelAndView addmodeOfPayment(Model model, @ModelAttribute ModeOfPayment modeOfPayment,
			RedirectAttributes attribute, @RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);

		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {

			String paymentMode = modeOfPayment.getPaymentMode();
			// String output = paymentMode.substring(0, 1).toUpperCase() +
			// paymentMode.substring(1);

			Long count = modeOfPaymentDAO.getCountOfPaymentMode(paymentMode);

			if (count == 0) {

				ModeOfPayment addModeOfPayment = new ModeOfPayment();
				addModeOfPayment.setPaymentMode(paymentMode);
				addModeOfPayment.setIsVisibleInBankSide(modeOfPayment.getIsVisibleInBankSide());
				addModeOfPayment.setIsVisibleInCustomerSide(modeOfPayment.getIsVisibleInCustomerSide());

				modeOfPaymentDAO.save(addModeOfPayment);

			}

			else {

				model.addAttribute(Constants.ERROR, Constants.PAYMENTMODEEXIST);
				return new ModelAndView("modeOfPayment", "model", model);
			}
			attribute = updateTransaction("Saved Successfully", attribute);
			return new ModelAndView("redirect:successfullySaved");
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("modeOfPayment", "model", model);

		}
	}

	@RequestMapping(value = "/modeOfPaymentById", method = RequestMethod.GET)
	public ModelAndView modeOfPaymentById(Model model, @ModelAttribute ModeOfPayment modeOfPayment, Long id) {
		try {
			model.addAttribute("menus", menus);
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
	public ModelAndView editmodeOfPayment(Model model, @ModelAttribute ModeOfPayment modeOfPayment,
			RedirectAttributes attributes) throws CustomException {

		try {
			model.addAttribute("menus", menus);
			if (modeOfPayment.getId() != null) {

				if (modeOfPaymentDAO.getModeOfPaymentExist(modeOfPayment.getPaymentMode(), modeOfPayment.getId())) {
					model.addAttribute("error", "Payment Mode already exist !");
					model.addAttribute("modeOfPayment", modeOfPayment);
					return new ModelAndView("editModeOfPayment", "model", model);
				}

				modeOfPaymentDAO.update(modeOfPayment);
				attributes = updateTransaction("Updated Successfully", attributes);
				return new ModelAndView("redirect:successfullySaved");
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());

		}
		return new ModelAndView("editModeOfPayment", "model", model);

	}

	@RequestMapping(value = "/roundOffOnMaturity")
	public ModelAndView addRoundOffOnMaturity(Model model, @ModelAttribute RoundOff roundOff,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		// model.addAttribute("roundOff",roundOff);
		List<RoundOff> roundOffList = roundOffDAO.getAllDetailsFromRoundOff();
		for (RoundOff roundOff1 : roundOffList) {
			model.addAttribute("roundOff", roundOff1);
		}
		return new ModelAndView("roundOffOnMaturity", "model", model);

	}

	@RequestMapping(value = "/addedRoundOff", method = RequestMethod.POST)
	public ModelAndView addedRoundOFF(Model model, @ModelAttribute RoundOff roundOff, RedirectAttributes attribute,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);

		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {
			Long count = roundOffDAO.getCountOfDecimalPlacesAndNearest(roundOff.getDecimalPlaces(),
					roundOff.getNearestHighestLowest());
			model.addAttribute("menus", menus);
			if (count == 0) {
				RoundOff addRoundOff = new RoundOff();
				addRoundOff.setDecimalPlaces(roundOff.getDecimalPlaces());
				addRoundOff.setNearestHighestLowest(roundOff.getNearestHighestLowest());
				roundOffDAO.save(addRoundOff);
			} else {

				model.addAttribute(Constants.ERROR, "This combination already exist ");
				return new ModelAndView("roundOffOnMaturity", "model", model);
			}
			attribute = updateTransaction("Saved Successfully", attribute);
			return new ModelAndView("redirect:successfullySaved");
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			List<RoundOff> roundOffList = roundOffDAO.getAllDetailsFromRoundOff();
			for (RoundOff roundOff1 : roundOffList) {
				model.addAttribute("roundOff", roundOff1);
			}
			return new ModelAndView("roundOffOnMaturity", "model", model);

		}
	}

	@RequestMapping(value = "/ledgerMapping")
	public ModelAndView ledgerMapping(Model model, @ModelAttribute LedgerEventMapping ledgerEventMapping,
			@RequestParam("menuId") Long menuId, HttpServletRequest request) {

		model = this.menuUtils(model, menuId);
		List<LedgerEventMapping> ledgerEventMappings = new ArrayList<LedgerEventMapping>();
		String event = request.getParameter("event");

		if (event != null && event.length() > 0) {
			ledgerEventMappings = ledgerEventMappingDAO.getLedgerEventMappingByEvent(event);
			model.addAttribute("event", event);
		} else {
			model.addAttribute("event", "select");
		}
		List<EventOperations> eventOperationsList = new ArrayList();
		// Get the event List

		for (Event pl : Event.values()) {
			System.out.println(pl.getValue());
			EventOperations eventOperation = new EventOperations();
			eventOperation.setEventCode(pl.name());
			eventOperation.setEventName(pl.getValue());
			eventOperationsList.add(eventOperation);
		}

		List<ModeOfPayment> modeOfPaymentList = modeOfPaymentDAO.getAllModeOfPaymentDetails();
		List<GLConfiguration> glConfigurationList = ledgerService.getGLConfigurations();
		model.addAttribute("glConfigurationList", glConfigurationList);
		System.out.println("--------" + glConfigurationList.toString());
		model.addAttribute("ledgerEventMappings", ledgerEventMappings);
		model.addAttribute("modeOfPaymentList", modeOfPaymentList);
		model.addAttribute("eventOperationsList", eventOperationsList);
		model.addAttribute("ledgerEventMapping", ledgerEventMapping);
		return new ModelAndView("ledgerMapping", "model", model);

	}

	@RequestMapping(value = "/saveLedgerMapping", method = RequestMethod.POST)
	public ModelAndView saveledgerMapping(Model model, @ModelAttribute LedgerEventMapping ledgerEventMapping,
			RedirectAttributes attributes, @RequestParam("menuId") Long menuId, HttpServletRequest request,
			RedirectAttributes redir) {

		try {
			model = this.menuUtils(model, menuId);
			Boolean isPermission = this.checkPermissions(menuId, "WRITE");
			if (isPermission) {

				String paymentMode[] = ledgerEventMapping.getModeOfPayment().split(",");
				String debitGLAccount[] = ledgerEventMapping.getDebitGLAccount().split(",");
				String creditGLAccount[] = ledgerEventMapping.getCreditGLAccount().split(",");
				ledgerEventMappingDAO.deleteLedgerEventMappingByEvent(ledgerEventMapping.getEvent());
				for (int i = 0; i < paymentMode.length; i++) {

					LedgerEventMapping ledgerMapping = new LedgerEventMapping();
					String payment = paymentMode[i];
					ModeOfPayment modeOfPayment = modeOfPaymentDAO.getModeOfPayment(payment);
					ledgerMapping.setModeOfPaymentId(modeOfPayment.getId());
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
				// attributes = updateTransaction("Saved Successfully", attributes);

				Date curDate = DateService.loginDate;
				model.addAttribute(Constants.TRANSACTIONID, fdService.generateRandomString());
				model.addAttribute(Constants.TRANSACTIONDATE, curDate);
				model.addAttribute(Constants.TRANSACTIONSTATUS, "saved successfully");

				return new ModelAndView("successfullySaved", "model", model);
			} else {
				redir.addFlashAttribute("error", "You do not have sufficient permissions !");
				List<LedgerEventMapping> ledgerEventMappings = new ArrayList<LedgerEventMapping>();
				String event = request.getParameter("event");
				if (event != null && event.length() > 0) {
					ledgerEventMappings = ledgerEventMappingDAO.getLedgerEventMappingByEvent(event);
					redir.addFlashAttribute("event", event);
				}
				List<EventOperations> eventOperationsList = new ArrayList();
				// Get the event List
				for (Event pl : Event.values()) {
					System.out.println(pl.getValue());
					EventOperations eventOperation = new EventOperations();
					eventOperation.setEventCode(pl.name());
					eventOperation.setEventName(pl.getValue());
					eventOperationsList.add(eventOperation);
				}
				List<ModeOfPayment> modeOfPaymentList = modeOfPaymentDAO.getAllModeOfPaymentDetails();
				List<GLConfiguration> glConfigurationList = ledgerService.getGLConfigurations();
				redir.addFlashAttribute("glConfigurationList", glConfigurationList);
				redir.addFlashAttribute("ledgerEventMappings", ledgerEventMappings);
				redir.addFlashAttribute("modeOfPaymentList", modeOfPaymentList);
				redir.addFlashAttribute("eventOperationsList", eventOperationsList);
				redir.addFlashAttribute("ledgerEventMapping", ledgerEventMapping);
				redir.addFlashAttribute("model", model);
				return new ModelAndView("redirect:ledgerMapping?menuId=" + menuId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ledgerMapping", "model", model);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/customerConfiguration")
	public ModelAndView customerConfigurationFromVP(Model model, String citizen, String nriAccountType,
			RatesForm ratesForm, @RequestParam("menuId") Long menuId) throws CustomException {

		model = this.menuUtils(model, menuId);
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
			model.addAttribute("setCategory", setCategory);

		} else {
			model.addAttribute("rates", rates);
			model.addAttribute("setCategory", setCategory);
			model.addAttribute(Constants.ERROR, "Data already present you can search and edit from below list.");
			return new ModelAndView("customerConfiguration", "model", model);
		}

		model.addAttribute("rates", rates);
		model.addAttribute("citizenType", citizenType);
		model.addAttribute("ratesForm", ratesForm);
		model.addAttribute("setCategory", setCategory);
		model.addAttribute("ratesCategory", ratesCategory);
		model.addAttribute("selectedCategory", customerCategory);
		if (citizen != null && nriAccountType != null) {
			return new ModelAndView("confirmcustomerConfiguration", "model", model);
		}
		return new ModelAndView("customerConfiguration", "model", model);
	}

	@RequestMapping(value = "/confirmcustomerConfiguration", method = RequestMethod.POST)
	public ModelAndView confirmcustomerConfiguration(Model model, @ModelAttribute RatesForm ratesForm,
			RedirectAttributes attributes, @RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);

		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {

			model.addAttribute("menus", menus);
			model.addAttribute("ratesForm", ratesForm);
			return new ModelAndView("confirmcustomerConfiguration", "model", model);
		} else {
			List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
			Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
			Collection<Rates> rates = ratesDAO.findAllRates();
			model.addAttribute("rates", rates);
			model.addAttribute("setCategory", setCategory);
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("customerConfiguration", "model", model);

		}
	}

	@RequestMapping(value = "/savecustomerConfiguration", method = RequestMethod.POST)
	public String savecustomerConfiguration(Model model, @ModelAttribute RatesForm ratesForm,
			RedirectAttributes attribute) throws CustomException {

		model.addAttribute("menus", menus);
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
		return "redirect:successfullySaved";
	}

	@RequestMapping(value = "/editCustomerConfiguration")
	public ModelAndView editCustomerConfiguration(Model model, @ModelAttribute RatesForm ratesForm,
			String nriAccountType, String citizen, String type, Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
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
		model.addAttribute("ratesCategory", ratesCategory);
		model.addAttribute("setCategory", setCategory);
		model.addAttribute("ratesForm", ratesForm);
		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);
		return new ModelAndView("editCustomerConfiguration", "model", model);
	}

	@RequestMapping(value = "/editCustomerConfigurationPost")
	public String editCustomerConfigurationPost(Model model, @ModelAttribute RatesForm ratesForm,
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
		model.addAttribute("menus", menus);
		attribute = updateTransaction("Updated Successfully", attribute);

		return "redirect:successfullySaved";
	}

	@RequestMapping(value = "currencyConfiguration", method = RequestMethod.GET)
	public ModelAndView currencyConfiguration(Model model, @ModelAttribute CurrencyConfiguration currencyConfiguration,
			@RequestParam("menuId") Long menuId) {
		try {

			model = this.menuUtils(model, menuId);
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
		return new ModelAndView("currencyConfiguration", "model", model);
	}

	@RequestMapping(value = "saveCurrencyConfiguration", method = RequestMethod.POST)
	public ModelAndView saveCurrencyConfiguration(Model model,
			@ModelAttribute CurrencyConfiguration currencyConfiguration, RedirectAttributes redirectAttributes) {
		try {
			model.addAttribute("menus", menus);
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
					return new ModelAndView("redirect:successfullySaved");
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
					return new ModelAndView("redirect:successfullySaved");
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
					return new ModelAndView("redirect:successfullySaved");

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
					return new ModelAndView("redirect:successfullySaved");
				}
			}

		} catch (Exception e) {
			model.addAttribute("currencyConfiguration", currencyConfiguration);
			model.addAttribute("error", e.getMessage());
		}
		return new ModelAndView("currencyConfigurationVP", "model", model);
	}

	@RequestMapping(value = "/holidayConfigurationStateWise")
	public ModelAndView stateHolidayConfiguration(Model model,
			@ModelAttribute("stateHolidayConfiguration") HolidayConfiguration holidayConfiguration,
			@RequestParam("menuId") Long menuId) {
		model = this.menuUtils(model, menuId);
		model.addAttribute("holidayConfiguration", holidayConfiguration);
		return new ModelAndView("holidayConfigurationStateWise", "model", model);

	}

	@RequestMapping(value = "/stateHolidayConfiguration", method = RequestMethod.POST)
	public ModelAndView saveStateHolidayConfiguration(Model model,
			@ModelAttribute("holidayConfiguration") HolidayConfiguration holidayConfiguration,
			@RequestParam("menuId") Long menuId) {
		try {

			model = this.menuUtils(model, menuId);
			model.addAttribute("holidayConfiguration", holidayConfiguration);
			Boolean isPermission = this.checkPermissions(menuId, "WRITE");
			if (isPermission) {
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
							holidayConfigurationDAO.delete(holidayConfiguration.getYear(),
									holidayConfiguration.getState());

						}
						hConfig.setYear(holidayConfiguration.getYear());
						hConfig.setState(holidayConfiguration.getState());
						holidayConfigurationDAO.save(hConfig);

						model.addAttribute("sucess", "Updated sucessfully!");
					}
				} else {
					model.addAttribute("error", "Please select dates or date!");
				}
			} else {
				model.addAttribute("error", "You do not have sufficient permissions !");
				return new ModelAndView("holidayConfigurationStateWise", "model", model);

			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return new ModelAndView("holidayConfigurationStateWise", "model", model);
	}

	@RequestMapping(value = "/holidayConfigurationBranchWise")
	public ModelAndView holidayConfiguration(Model model,
			@ModelAttribute("holidayConfiguration") HolidayConfiguration holidayConfiguration,
			@RequestParam("menuId") Long menuId) {
		model = this.menuUtils(model, menuId);
		model.addAttribute("holidayConfiguration", holidayConfiguration);
		return new ModelAndView("holidayConfigurationBranchWise", "model", model);

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

	@RequestMapping(value = "/holidayConfiguration", method = RequestMethod.POST)
	public ModelAndView saveHolidayConfiguration(Model model,
			@ModelAttribute("holidayConfiguration") HolidayConfiguration holidayConfiguration,
			@RequestParam String[] myArray, @RequestParam("menuId") Long menuId) {
		try {
			model = this.menuUtils(model, menuId);

			Boolean isPermission = this.checkPermissions(menuId, "WRITE");
			if (isPermission) {

				String[] array = myArray;
				model.addAttribute("menus", menus);
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
							List<HolidayConfiguration> isExistigYear = holidayConfigurationDAO.isPresent(
									holidayConfiguration.getYear(), array[i], holidayConfiguration.getState());
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
			} else {
				model.addAttribute("error", "You do not have sufficient permissions !");
				return new ModelAndView("holidayConfigurationBranchWise", "model", model);

			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return new ModelAndView("holidayConfigurationBranchWise", "model", model);

	}

	/**
	 * 
	 * @param model
	 * @param holidayConfiguration
	 * @return JSON
	 * 
	 */
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

	@RequestMapping(value = "/holidayConfigurationGetByYear", produces = { "application/json" })
	@ResponseBody
	public String holidayConfigurationYear(Model model,
			@ModelAttribute("holidayConfiguration") HolidayConfiguration holidayConfiguration) {
		HolidayConfiguration holidayConfigurationList = null;
		String json = "{}";
		model.addAttribute("menus", menus);
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

	@RequestMapping(value = "/glConfiguration", method = RequestMethod.GET)
	public ModelAndView glConfiguration(Model model,
			@ModelAttribute("glConfigurationForm") GLConfigurationForm glConfigurationForm,
			@RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		model.addAttribute("branch", this.branch);
		Long glConfigurationCountById = ledgerService.countById();
		List<GLConfiguration> glConfigurationList = ledgerService.getGLConfigurations();
		model.addAttribute("glConfigurationList", glConfigurationList);
		model.addAttribute("glConfigurationCountById", glConfigurationCountById);
		return new ModelAndView("glConfiguration", "model", model);
	}

	@Transactional
	@RequestMapping(value = "/saveGLConfiguration", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveGLConfiguration(Model model, RedirectAttributes attributes,
			@ModelAttribute("glConfigurationForm") GLConfigurationForm glConfigurationForm,
			@RequestParam("glCodeList") String glCodeList, @RequestParam("glNumberList") String glNumberList,
			@RequestParam("menuId") Long menuId) throws ParseException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);

		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {
			Boolean isUpadated = (Boolean) ledgerService.insertGLCode(glCodeList, glNumberList,
					this.currentLoggedInUser.getCustomerId());

			if (isUpadated) {
				attributes.addFlashAttribute("GLConfigurationForm", glConfigurationForm);
				attributes = updateTransaction("Saved Successfully", attributes);
				return new ModelAndView("redirect:successfullySaved");
			} else {
				List<GLConfiguration> glConfigurationList = ledgerService.getGLConfigurations();
				model.addAttribute("glConfigurationList", glConfigurationList);
				model.addAttribute("menus", menus);
				return new ModelAndView("glConfiguration", "model", model);
			}
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			List<GLConfiguration> glConfigurationList = ledgerService.getGLConfigurations();
			model.addAttribute("glConfigurationList", glConfigurationList);
			model.addAttribute("menus", menus);
			return new ModelAndView("glConfiguration", "model", model);

		}
	}

	@Transactional
	@RequestMapping(value = "/editSaveGLConfiguration", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveGLConfiguration(Model model, RedirectAttributes attributes,
			@ModelAttribute(value = "glConfigurationFormEdit") GLConfigurationForm glConfigurationFormEdit,
			@ModelAttribute GLConfigurationForm glConfigurationForm) {
		try {
			Long menuId;
			model.addAttribute("menus", menus);
			if (glConfigurationFormEdit.getId() != null) {
				GLConfiguration configuration1 = ledgerDAO.findById(glConfigurationFormEdit.getId());
				if (configuration1 != null) {
					if (configuration1.getGlAccount().equals(glConfigurationFormEdit.getGlAccount())
							&& configuration1.getGlCode().equals(glConfigurationFormEdit.getGlCode())
							&& configuration1.getGlNumber().equals(glConfigurationFormEdit.getGlNumber())) {
						attributes.addFlashAttribute("error", "Please update your data !");
						return new ModelAndView("redirect:glConfiguration?menuId");
					} else {
						configuration1.setGlAccount(glConfigurationFormEdit.getGlAccount());
						configuration1.setGlCode(glConfigurationFormEdit.getGlCode());
						configuration1.setGlNumber(glConfigurationFormEdit.getGlNumber());
						configuration1.setModifiedDate(new Date());
						ledgerService.update(configuration1);
						attributes.addFlashAttribute("success", "Update sucessfully !");
						return new ModelAndView("redirect:glConfiguration?menuId=25");
					}
				} else {
					attributes.addFlashAttribute("error", "id does not exist !");
					return new ModelAndView("redirect:glConfiguration?menuId=25");
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
		return new ModelAndView("redirect:glConfiguration?menuId=25");

	}

	@RequestMapping(value = "addProductWiseConfiguration")
	public ModelAndView productWiseConfiuration(Model model, @RequestParam("menuId") Long menuId,
			@ModelAttribute ProductConfiguration productConfiguration) {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		model.addAttribute("branch", this.branch);

		return new ModelAndView("addProductWiseConfiguration", "model", model);
	}

	@RequestMapping(value = "productWiseConfiguration", method = RequestMethod.POST)
	public ModelAndView saveProductWiseConfiuration(Model model,
			@ModelAttribute ProductConfiguration productConfiguration, RedirectAttributes attributes,
			@RequestParam("menuId") Long menuId) {
		model = this.menuUtils(model, menuId);

		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {
			try {
				ProductConfiguration isExist = productConfigurationDAO
						.findByProductCode(productConfiguration.getProductCode().toLowerCase());
				if (isExist != null) {
					model.addAttribute("error", "Product Code already exist !");
					return new ModelAndView("addProductWiseConfiguration", "model", model);
				}

				productConfiguration.setCreatedDate(new Date());
				productConfiguration.setModifiedDate(new Date());
				productConfiguration.setCreatedBy(getCurrentLoggedInEndUser().getUserName());
				productConfiguration.setProductCode(productConfiguration.getProductCode().toLowerCase());
				productConfigurationDAO.insertProductConfiguration(productConfiguration);
				attributes = updateTransaction("Saved Successfully", attributes);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ModelAndView("redirect:successfullySaved");
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("addProductWiseConfiguration", "model", model);
		}
	}

	@RequestMapping(value = "productConfiurationByAccountType", method = RequestMethod.GET)
	public ModelAndView productConfiurationByAccountType(Model model,
			@ModelAttribute ProductConfiguration productConfiguration, Long id) {
		try {
			model.addAttribute("menus", menus);
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

	@RequestMapping(value = "viewProductsConfiguration", method = RequestMethod.GET)
	public ModelAndView findAll(Model model, @ModelAttribute ProductConfiguration productConfiguration) {
		try {
			model.addAttribute("menus", menus);
			List<ProductConfiguration> productConfigurations = productConfigurationDAO.findAll();
			model.addAttribute("productConfigurations", productConfigurations);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("viewProductConfiguration", "model", model);
	}

	@RequestMapping(value = "editProductWiseConfiguration", method = RequestMethod.POST)
	public ModelAndView editProductConfiuration(Model model, @ModelAttribute ProductConfiguration productConfiguration,
			RedirectAttributes attributes) {
		try {
			model.addAttribute("menus", menus);
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
				productConfiguration.setCreatedBy(getCurrentLoggedInEndUser().getUserName());
				productConfigurationDAO.updateProductConfiguration(productConfiguration);
				attributes = updateTransaction("Updated Successfully", attributes);
				return new ModelAndView("redirect:successfullySaved");
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("productConfiguration", productConfiguration);
		}
		return new ModelAndView("editProductConfiguration", "model", model);
	}

	@RequestMapping(value = "/sweepConfiguration", method = RequestMethod.GET)
	public ModelAndView sweepConfiguration(Model model, @ModelAttribute SweepConfiguration sweepConfiguration,
			@RequestParam("menuId") Long menuId) {

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);

		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		model.addAttribute("branch", this.branch);
		SweepConfiguration sweepConfigurationActive = sweepConfigurationDAO.getActiveSweepConfiguration();
		if (sweepConfigurationActive != null)
			model.addAttribute("sweepConfiguration", sweepConfigurationActive);
		else
			model.addAttribute("sweepConfiguration", new SweepConfiguration());
		return new ModelAndView("sweepConfiguration", "model", model);
	}

	@RequestMapping(value = "/saveSweepConfiguration", method = RequestMethod.POST)
	public ModelAndView saveSweepConfiguration(Model model, @ModelAttribute SweepConfiguration sweepConfiguration,
			RedirectAttributes redirectAttributes, @RequestParam("menuId") Long menuId) {
		try {
			EndUser endUser = this.currentLoggedInUser;
			List<Menu> menus = this.menus;
			model.addAttribute("menus", menus);
			model.addAttribute("endUser", endUser);
			model = this.menuUtils(model, menuId);

			Boolean isPermission = this.checkPermissions(menuId, "WRITE");
			if (isPermission) {

				model.addAttribute("menus", menus);
				final Date CURRENT_DATE = new Date();
				if (sweepConfiguration.getId() != null) {
					SweepConfiguration sweepConfigurationById = sweepConfigurationDAO
							.findById(sweepConfiguration.getId());

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
						return new ModelAndView("redirect:successfullySaved");
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
					return new ModelAndView("redirect:successfullySaved");

				}
			} else {
				model.addAttribute("sweepConfiguration", sweepConfiguration);
				model.addAttribute("error", "You do not have sufficient permissions !");
				return new ModelAndView("sweepConfiguration", "model", model);

			}
		} catch (Exception e) {
			model.addAttribute("sweepConfiguration", sweepConfiguration);
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
		}

		return new ModelAndView("sweepConfiguration", "model", model);
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

	@RequestMapping(value = "/addWithdrawPenalty", method = RequestMethod.GET)
	public ModelAndView addWithdrawPenalty(Model model, @ModelAttribute WithdrawPenaltyForm wthdrawPenaltyForm,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		List<ProductConfiguration> configurations = productConfigurationDAO.findAll();
		model.addAttribute("productConfigurations", configurations);
		model.addAttribute("wthdrawPenaltyForm", wthdrawPenaltyForm);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		return new ModelAndView("addWithdrawPenalty", "model", model);
	}

	@RequestMapping(value = "/saveWithdrawPenalty", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveWithdrawPenalty(Model model, RedirectAttributes attributes,
			@ModelAttribute WithdrawPenaltyForm wthdrawPenaltyForm,
			@RequestParam("withdrawPenaltyFormList") String withdrawPenaltyList, @RequestParam("menuId") Long menuId)
			throws ParseException {
		model = this.menuUtils(model, menuId);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {
			model.addAttribute("menus", menus);
			WithdrawPenaltyMaster penaltyMaster = null;

			WithdrawPenaltyFormList withdrawPenaltyFormList = new Gson().fromJson(withdrawPenaltyList,
					WithdrawPenaltyFormList.class);
			List<WithdrawPenaltyForm> penaltyFormList = withdrawPenaltyFormList.getWithdrawPenaltyFormList();

			for (WithdrawPenaltyForm withdrawPenaltyForm : penaltyFormList) {
				Boolean isExist = withdrawPenaltyDAO.isAmountToAndAmountFromRangeExistNew(withdrawPenaltyForm);
				if (isExist) {
					attributes.addFlashAttribute("error", "Amount entered is already exist !");
					ModelAndView andView = new ModelAndView("redirect:addWithdrawPenalty?menuId=" + menuId);
					return andView;

				}
			}

			for (WithdrawPenaltyForm withdrawPenaltyForm : penaltyFormList) {

				boolean isPreMatureWithdraw = (withdrawPenaltyForm.getIsPrematureWithdraw() != null
						&& withdrawPenaltyForm.getIsPrematureWithdraw() == 1) ? true : false;
				WithdrawPenaltyMaster withdrawPenaltyMaster = withdrawPenaltyDAO
						.getWithdrawPenalyMaster(withdrawPenaltyForm.getProductConfigurationId(), isPreMatureWithdraw);

				/*
				 * if (!(withdrawPenaltyMaster != null &&
				 * withdrawPenaltyMaster.getWithdrawType()
				 * .equalsIgnoreCase(withdrawPenaltyForm.getWithdrawType()))) {withdrawtyppe not
				 * needed now
				 */
				penaltyMaster = new WithdrawPenaltyMaster();
				Date effectiveDate = new SimpleDateFormat("dd/MM/yyyy").parse(withdrawPenaltyForm.getEffectiveDate());
				penaltyMaster.setEffectiveDate(effectiveDate);
				penaltyMaster.setProductConfigurationId(withdrawPenaltyForm.getProductConfigurationId());
				penaltyMaster.setIsPrematureWithdraw(withdrawPenaltyForm.getIsPrematureWithdraw());
				penaltyMaster.setCreatedBy(getCurrentLoggedUserName());
				penaltyMaster.setCreatedDate(new Date());
				penaltyMaster.setModifiedDate(new Date());
				penaltyMaster.setModifiedBy(getCurrentLoggedUserName());

				penaltyMaster = withdrawPenaltyDAO.insertWithdrawPenaltyMaster(penaltyMaster);
				/* } */

				WithdrawPenaltyDetails based = new WithdrawPenaltyDetails();
				based.setAmountSign(withdrawPenaltyForm.getAmountSign());
				based.setTenureSign(withdrawPenaltyForm.getTenureSign());
				based.setAmount(withdrawPenaltyForm.getAmount());
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
				withdrawPenaltyDAO.saveWithdrawPenaltyDetails(based);
			}
			model.addAttribute("wthdrawPenaltyForm", wthdrawPenaltyForm);
			attributes = updateTransaction("Saved Successfully", attributes);
			return new ModelAndView("redirect:WithdrawPenaltySaved");
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			List<ProductConfiguration> configurations = productConfigurationDAO.findAll();
			model.addAttribute("productConfigurations", configurations);
			model.addAttribute("wthdrawPenaltyForm", wthdrawPenaltyForm);

			return new ModelAndView("addWithdrawPenalty", "model", model);

		}
	}

	@RequestMapping(value = "/WithdrawPenaltySaved", method = RequestMethod.GET)
	public ModelAndView withdrawPenaltySaved(Model model, @ModelAttribute WithdrawPenaltyForm wthdrawPenaltyForm)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("wthdrawPenaltyForm", wthdrawPenaltyForm);
		return new ModelAndView("successfullySaved", "model", model);

	}

	@RequestMapping(value = "/viewWithdrawPenalty", method = RequestMethod.GET)
	public ModelAndView viewWithdrawPenalty(Model model,
			@ModelAttribute("wthdrawPenaltyForm") WithdrawPenaltyForm wthdrawPenaltyForm,
			@RequestParam("menuId") Long menuId) throws CustomException, ParseException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		model.addAttribute("branch", this.branch);
		List<Date> effectiveDateList = withdrawPenaltyDAO.getEffectiveDates();

		Date effectiveDate = null;

		if (effectiveDateList != null && effectiveDateList.size() > 0) {
			effectiveDate = effectiveDateList.get(effectiveDateList.size() - 1);
		} else {
			return new ModelAndView("noDataFound", "model", model);

		}

		model.addAttribute("effectiveDateList", effectiveDateList);
		// model.addAttribute("withdrawPenaltyFormList", withdrawPenaltyFormList);
		return new ModelAndView("viewWithdrawPenalty", "model", model);
	}

	@RequestMapping(value = "/wthdrawPenaltyFormEdit", method = RequestMethod.POST)
	public ModelAndView viewWithdrawPenaltyEdit(Model model,
			@ModelAttribute("wthdrawPenaltyForm") WithdrawPenaltyForm wthdrawPenaltyForm, RedirectAttributes attributes)
			throws CustomException, ParseException {
		Long menuId;
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);

		Boolean isExist = withdrawPenaltyDAO.isAmountToAndAmountFromRangeExist(wthdrawPenaltyForm);
		if (isExist) {
			attributes.addFlashAttribute("error", "Amount entered is already exist !");
			attributes.addFlashAttribute("getEffectiveDate", wthdrawPenaltyForm.getTempDate());

			ModelAndView andView = new ModelAndView();
			andView.setViewName("redirect:viewWithdrawPenalty?menuId");
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

		model.addAttribute("effectiveDateList", effectiveDateList);
		model.addAttribute("effectiveDate", wthdrawPenaltyForm.getEffectiveDate());
		model.addAttribute("sucess", "Updated successfully");
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
			withdrawPenaltyForm.setAmountSign((String) penaltyList.get(i)[3]);
			withdrawPenaltyForm.setAmount(new Double(penaltyList.get(i)[4].toString()).doubleValue());
			withdrawPenaltyForm.setTenureSign((String) penaltyList.get(i)[5]);
			withdrawPenaltyForm.setTenure((String) penaltyList.get(i)[6]);
			if (penaltyList.get(i)[7] != null)
				withdrawPenaltyForm.setPenaltyRate(penaltyList.get(i)[7].toString());
			if (penaltyList.get(i)[8] != null)
				withdrawPenaltyForm.setPenaltyFlatAmount(penaltyList.get(i)[8].toString());

			withdrawPenaltyFormList.add(withdrawPenaltyForm);

		}

		return withdrawPenaltyFormList;
	}

	@RequestMapping(value = "dataByProductId", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<ProductConfiguration> dataByProductId(String effectiveDate) throws Exception {
		List<ProductConfiguration> longs = new ArrayList<ProductConfiguration>();
		Date effectiveDateParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(effectiveDate);
		List<Long> list = withdrawPenaltyDAO.getByDates(effectiveDateParse);
		Integer index = 0;
		for (Long withdrawPenaltyMasterProductConfigurationId : list) {
			ProductConfiguration configuration = productConfigurationDAO
					.findById(withdrawPenaltyMasterProductConfigurationId);
			longs.add(configuration);
			index++;
		}
		return longs;
	}

	@RequestMapping(value = "/addCountryForDTAA", method = RequestMethod.GET)
	public ModelAndView countryForDTAA(Model model, @RequestParam("menuId") Long menuId) throws CustomException {

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		model.addAttribute("branch", this.branch);
		List<DTAACountry> dtaaCountryList = dtaaCountryRatesDAO.getDTAACountryList();

		model.addAttribute("dtaaCountryList", dtaaCountryList);
		return new ModelAndView("countryForDTAA", "model", model);
	}

	@RequestMapping(value = "/addingCountryForDTAA", method = RequestMethod.GET)
	public ModelAndView addCountryForDTAA(Model model, @ModelAttribute AddCountryForDTAAForm addCountryForDTAAForm,
			String menuId) throws CustomException {
		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);
		model.addAttribute("addCountryForDTAAForm", addCountryForDTAAForm);
		return new ModelAndView("addCountryForDTAA", "model", model);
	}

	@RequestMapping(value = "/saveCountryForDTAA", method = RequestMethod.POST)
	public ModelAndView saveCountryForDTAA(Model model, AddCountryForDTAAForm addCountryForDTAAForm,
			BindingResult result, RedirectAttributes attributes, @RequestParam("menuId") Long menuId)
			throws CustomException {
		model = this.menuUtils(model, menuId);
		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {
			if (addCountryForDTAAForm.getCountry().trim().equals("")) {
				model.addAttribute("error", "Invalid Entry. Enter a valid country name.");
				return new ModelAndView("addCountryForDTAA", "model", model);
			}
			if (dtaaCountryRatesDAO.getDTAACountry(addCountryForDTAAForm.getCountry()) != null) {
				model.addAttribute("error", "Country '" + addCountryForDTAAForm.getCountry() + "' is already exist.");
				return new ModelAndView("addCountryForDTAA", "model", model);
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
			return new ModelAndView("redirect:successfullySaved");
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			model.addAttribute("addCountryForDTAAForm", addCountryForDTAAForm);
			return new ModelAndView("addCountryForDTAA", "model", model);

		}

	}

	@RequestMapping(value = "/viewDTAATaxRates", method = RequestMethod.GET)
	public ModelAndView getDTAATaxRates(Model model, @RequestParam("menuId") Long menuId, RedirectAttributes attributes)
			throws CustomException {

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		model.addAttribute("branch", this.branch);
		List<DTAACountryRates> dtaaCountryRateList = dtaaCountryRatesDAO.getDTAACountryRatesList();

		model.addAttribute("dtaaCountryRateList", dtaaCountryRateList);
		return new ModelAndView("dtaaCountryRates", "model", model);
	}

	@RequestMapping(value = "/addDTAATaxRate", method = RequestMethod.GET)
	public ModelAndView addDTAATaxRate(Model model,
			@ModelAttribute AddCountryWiseTaxRateDTAAForm addCountryWiseTaxRateDTAAForm, String menuId)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;

		model.addAttribute("branch", this.branch);
		model.addAttribute("endUser", endUser);
		List<DTAACountry> countryList = dtaaCountryRatesDAO.getDTAACountryList();
		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);
		model.addAttribute("countryList", countryList);
		model.addAttribute("addCountryWiseTaxRateDTAAForm", addCountryWiseTaxRateDTAAForm);
		return new ModelAndView("addDTAATaxRate", "model", model);
	}

	@Transactional
	@RequestMapping(value = "/addCountryWiseTaxRateDTAAPost", method = RequestMethod.POST)
	public ModelAndView addCountryWiseTaxRateDTAAPost(Model model, RedirectAttributes attributes,
			@ModelAttribute AddCountryWiseTaxRateDTAAForm addCountryWiseTaxRateDTAAForm,
			@RequestParam("menuId") Long menuId) throws CustomException {

		model = this.menuUtils(model, menuId);

		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {
			DTAACountry dtaaCountry = dtaaCountryRatesDAO
					.getDTAACountry(addCountryWiseTaxRateDTAAForm.getDtaaCountryId());
			if (dtaaCountry == null) {
				List<DTAACountry> countryList = dtaaCountryRatesDAO.getDTAACountryList();

				model.addAttribute("countryList", countryList);
				model.addAttribute("error", "Country is not listed for DTAA.");
				return new ModelAndView("addDTAATaxRate", "model", model);
			}

			if (addCountryWiseTaxRateDTAAForm.getEffectiveDate() == null) {
				List<DTAACountry> countryList = dtaaCountryRatesDAO.getDTAACountryList();

				model.addAttribute("countryList", countryList);
				model.addAttribute("error", "Effective Date is not given.");
				return new ModelAndView("addDTAATaxRate", "model", model);
			}

			if (addCountryWiseTaxRateDTAAForm.getTaxRate() == null) {
				List<DTAACountry> countryList = dtaaCountryRatesDAO.getDTAACountryList();

				model.addAttribute("countryList", countryList);
				model.addAttribute("error", "Rate is not entered.");
				return new ModelAndView("addDTAATaxRate", "model", model);
			}
			if (addCountryWiseTaxRateDTAAForm.getTaxRate() < 0 || addCountryWiseTaxRateDTAAForm.getTaxRate() >= 100) {
				List<DTAACountry> countryList = dtaaCountryRatesDAO.getDTAACountryList();

				model.addAttribute("countryList", countryList);
				model.addAttribute("error", "Rate is not valid. Rate should be greater than 0 and less than 100.");
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
			return new ModelAndView("redirect:successfullySaved");
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			List<DTAACountry> countryList = dtaaCountryRatesDAO.getDTAACountryList();
			model.addAttribute("countryList", countryList);
			model.addAttribute("addCountryWiseTaxRateDTAAForm", addCountryWiseTaxRateDTAAForm);
			return new ModelAndView("addDTAATaxRate", "model", model);
		}
	}

	@RequestMapping(value = "/GST")
	public ModelAndView gstDeduction(Model model, @RequestParam("menuId") Long menuId) {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		model.addAttribute("branch", this.branch);
		GSTDeduction gstDeduction = gstDeductionDAO.findAll();
		if (gstDeduction != null)
			model.addAttribute("gstDeduction", gstDeduction);
		else
			model.addAttribute("gstDeduction", new GSTDeduction());

		return new ModelAndView("GSTDeduction", "model", model);
	}

	@RequestMapping(value = "/gstDeduction", method = RequestMethod.POST)
	public ModelAndView gstDeductionSave(Model model, @ModelAttribute GSTDeduction gstDeduction,
			RedirectAttributes redirectAttributes, @RequestParam("menuId") Long menuId) {
		ModelAndView andView = new ModelAndView("GSTDeduction", "model", model);
		try {

			model = this.menuUtils(model, menuId);
			Boolean isPermission = this.checkPermissions(menuId, "WRITE");
			if (isPermission) {

				String loggedUser = getCurrentLoggedUserName();
				Date currentTimeAndDate = new Date();
				if (gstDeduction != null) {
					if (gstDeduction.getId() != null) {
						gstDeduction.setModifiedDate(currentTimeAndDate);
						gstDeduction.setModifiedBy(loggedUser);
						gstDeduction = gstDeductionDAO.update(gstDeduction);
						model.addAttribute("gstDeduction", gstDeduction);
						redirectAttributes = updateTransaction("Updated Successfully", redirectAttributes);
						andView = new ModelAndView("redirect:successfullySaved");
					} else {
						gstDeduction.setModifiedDate(currentTimeAndDate);
						gstDeduction.setModifiedBy(loggedUser);
						gstDeduction.setCreatedDate(currentTimeAndDate);
						gstDeduction.setCreatedBy(loggedUser);
						gstDeductionDAO.save(gstDeduction);
						redirectAttributes = updateTransaction("Saved Successfully", redirectAttributes);
						andView = new ModelAndView("redirect:successfullySaved");
					}
				}
			} else {
				model.addAttribute("error", "You do not have sufficient permissions !");

				model.addAttribute("gstDeduction", gstDeduction);

				return new ModelAndView("GSTDeduction", "model", model);

			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());

		}
		return andView;
	}

	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/dayEndProcess", method = RequestMethod.GET)
	public ModelAndView showMonthEndProcess(Model model, @ModelAttribute ReportForm reportForm,
			@RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		model.addAttribute("branch", this.branch);
		model.addAttribute("reportForm", reportForm);

		return new ModelAndView("dayEndProcess", "model", model);
	}

	@RequestMapping(value = "/saveDayEndProcess", method = RequestMethod.POST)
	public ModelAndView saveDayEndProcess(Model model, @ModelAttribute ReportForm reportForm,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {
			// Date tillDate = reportForm.getToDate();
			System.out.println("Startng the dayEndProcess...");

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

			// Interest Payoff
			notificationScheduler.calculatePayOff();

			// Calculate and Deduct Modification Penalty if required
			notificationScheduler.calculateModificationPenalty();

			// Calculate and Deduct Missed Payment Penalty if required
			notificationScheduler.calculatePenaltyForMissedPaymentForRecurringDeposit();

			// Compound All Calculated Interest
			notificationScheduler.compoundInterest();

			notificationScheduler.transferMoneyOnMaturity();

			// Deduct Recurring Deposit's EMI
			notificationScheduler.autoDeduction();

			// Deduct Annuity Deposit's EMI
			notificationScheduler.deductAnnuityEMI();

			notificationScheduler.paymentRemindMail();

			// Deduct Overdraft EMI
			notificationScheduler.deductOverdraftEMI();

			// notificationScheduler.deductTDS();

			// notificationScheduler.createAutoDeposit();
			// Post the Journal's Unposted Records
			ledgerService.postInLedger();

			model.addAttribute("succMsg", "Successfully processed.");
			model.addAttribute("menus", menus);
			return new ModelAndView("dayEndProcess", "model", model);
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("dayEndProcess", "model", model);

		}
	}

	// -----Approval Manager--------------------------
	@RequestMapping(value = "/customerApprovalList", method = RequestMethod.GET)
	public ModelAndView customerHeadApproval(Model model, RedirectAttributes attributes,
			@RequestParam("menuId") Long menuId) throws CustomException {

		EndUser endUser = this.getCurrentLoggedInEndUser();
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		List<Customer> customerList = customerDAO.getByPending().getResultList();

		if (customerList != null && customerList.size() > 0) {

			model.addAttribute("customerList", customerList);
			model.addAttribute("customerForm", customerForm);
			return new ModelAndView("customerApprovalList", "model", model);
		} else {
			return new ModelAndView("noDataFound", "model", model);
		}

	}

	@RequestMapping(value = "/depositPendingList", method = RequestMethod.GET)
	public ModelAndView fdPendingList(Model model, RedirectAttributes attributes, @RequestParam("menuId") Long menuId)
			throws CustomException {
		EndUser endUser = this.getCurrentLoggedInEndUser();
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		ModelAndView mav = new ModelAndView();

		List<DepositForm> depositList = fixedDepositDao.getAllPendingDepositsList();
		if (depositList != null && depositList.size() > 0) {

			model.addAttribute("depositList", depositList);
			mav = new ModelAndView("fdPendingList", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/bankEmpActiveList", method = RequestMethod.GET)
	public ModelAndView bankEmpActiveList(Model model, @RequestParam("menuId") Long menuId) throws CustomException {

		EndUser endUser = this.getCurrentLoggedInEndUser();
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		Role role = endUserDAOImpl.findByRoleName("Bank Employee");
		// List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Approved", 2);
		List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Approved", role.getId());
		if (list.size() > 0) {
			model.addAttribute("list", list);
			model.addAttribute("title", "Bank Employee Active List");

			return new ModelAndView("endUserListApr", "model", model);
		} else {

			return new ModelAndView("noDataFound");
		}

	}

	@RequestMapping(value = "/customerActiveList", method = RequestMethod.GET)
	public ModelAndView customerActiveList(Model model, @RequestParam("menuId") Long menuId) throws CustomException {

		EndUser endUser = this.getCurrentLoggedInEndUser();
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		// List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Approved", 4);
		Role role = endUserDAOImpl.findByRoleName("ROLE_USER");
		List<EndUser> list = endUserDAOImpl.getSuspendedCustomerList("Approved", role.getId());
		if (list.size() > 0) {
			model.addAttribute("list", list);
			model.addAttribute("title", "Customer Active List");

			return new ModelAndView("endUserListApr", "model", model);
		} else {

			return new ModelAndView("noDataFound");
		}

	}

	@RequestMapping(value = "/bankEmpSuspend", method = RequestMethod.GET)
	public ModelAndView bankEmpSuspend(Model model, @RequestParam("menuId") Long menuId) throws CustomException {

		EndUser endUser = this.getCurrentLoggedInEndUser();
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		EndUserForm endUserForm1 = new EndUserForm();
		model.addAttribute("endUserForm", endUserForm1);
		return new ModelAndView("bankEmpSuspend", "model", model);

	}

	@RequestMapping(value = "/customerSuspend", method = RequestMethod.GET)
	public ModelAndView customerSuspend(Model model, @RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.getCurrentLoggedInEndUser();
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		// Collection<Customer> customers = customerDAO.findAllCustomers();
		// fixedDepositForm.setCustomers(customers);
		FixedDepositForm fixedDepositForm = new FixedDepositForm();
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("customerSuspend", "model", model);

	}

	@RequestMapping(value = "/approveFdPendingList", method = RequestMethod.GET)
	public ModelAndView approveFdPendingList(@RequestParam Long id, Model model, RedirectAttributes attributes,
			@ModelAttribute FixedDepositForm fixedDepositForm, Long menuId) throws CustomException {
		EndUser endUser = this.getCurrentLoggedInEndUser();
		model.addAttribute("endUser", endUser);
		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);

		Deposit deposit = fixedDepositDao.findById(id);
		fixedDepositForm.setDepositId(deposit.getId());

		fixedDepositForm.setDepositedAmt((double) deposit.getDepositAmount());
		fixedDepositForm.setMaturityDate(deposit.getMaturityDate());

		model.addAttribute("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("approveFdPendingList", "model", model);
	}

	@RequestMapping(value = "/searchBankEmp", method = RequestMethod.POST)
	public ModelAndView searchBankEmp(Model model, @ModelAttribute EndUserForm endUserForm) throws CustomException {
		String bankId = endUserForm.getBankId();
		String userName = endUserForm.getUserName();
		String contactNo = endUserForm.getContactNo();
		String email = endUserForm.getEmail();
		List<EndUser> endUserList = new ArrayList<EndUser>();
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);

		endUserList = endUserDAOImpl.searchEmployees(bankId, userName, contactNo, email, "Approved");

		if (endUserList.size() != 0) {
			model.addAttribute("endUserList", endUserList);
			return new ModelAndView("searchBankEmp", "model", model);
		} else {
			model.addAttribute(Constants.ERROR, Constants.bankEmpNotFound);
			return new ModelAndView("searchBankEmp", "model", model);

		}
	}

	@RequestMapping(value = "/searchCustomer", method = RequestMethod.POST)
	public ModelAndView searchCustomer(Model model, @ModelAttribute FixedDepositForm fixedDepositForm, Long menuId)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		String cusId = fixedDepositForm.getCustomerID();
		String cusName = fixedDepositForm.getCustomerName();
		String cusNum = fixedDepositForm.getContactNum();
		String cusEmail = fixedDepositForm.getEmail();

		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);

		if (customerList.size() != 0) {
			model.addAttribute("customerList", customerList);
			return new ModelAndView("customerSuspend", "model", model);

		} else {
			model.addAttribute(Constants.ERROR, Constants.customerNotFound);
			return new ModelAndView("customerSuspend", "model", model);
		}
	}

	@RequestMapping(value = "/approveCustomer", method = RequestMethod.GET)
	public ModelAndView approveCustomerHead(@RequestParam Long id, String menuId, Model model,
			RedirectAttributes attributes) throws CustomException {
		EndUser endUser = this.getCurrentLoggedInEndUser();
		model.addAttribute("endUser", endUser);
		Customer customer = customerDAO.getById(id);
		customerForm.setId(customer.getId());
		customerForm.setCustomerName(customer.getCustomerName());
		customerForm.setCustomerID(customer.getCustomerID());
		customerForm.setAddress(customer.getAddress());
		customerForm.setContactNum(customer.getContactNum());
		customerForm.setEmail(customer.getEmail());
		customerForm.setUserName(customer.getUserName());
		customerForm.setTransactionId(customer.getTransactionId());
		model.addAttribute("customerForm", customerForm);
		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);
		return new ModelAndView("approveCustomer", "model", model);
	}

	@RequestMapping(value = "/approveCustomerConfirm", method = RequestMethod.POST)
	public ModelAndView approveCustomerConfirm(@ModelAttribute CustomerForm customerForm, Model model,
			RedirectAttributes attributes, String menuId) throws CustomException {
		EndUser endUser = this.getCurrentLoggedInEndUser();
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("customerForm", customerForm);
		model.addAttribute("menus", menus);
		return new ModelAndView("approveCustomerConfirm", "model", model);
	}

	@RequestMapping(value = "/updateCustomerForApproval", method = RequestMethod.POST)
	public String updateCustomerForApproval(@ModelAttribute CustomerForm customerForm, Model model,
			RedirectAttributes attributes) throws CustomException {

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

		customer = customerDAO.updateUser(customer);
		String status = customerForm.getStatus();
		if (status.equals(Constants.APPROVED)) {
			EndUser endUser = endUserDAO.findUserByCustomerId(customer.getId());
			Transaction transaction = new Transaction();
			endUser.setPrefferedLanguage("en");
			endUser.setTheme("themeBlue");
			endUser.setTransactionId(customerForm.getTransactionId());
			endUser.setNotificationStatus(Constants.NOTIFICATION);
			endUser.setPasswordFlag(0);
			endUser.setStatus(Constants.APPROVED);
			endUserDAOImpl.update(endUser);

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
		return "redirect:successfullySaved";

	}

	// ------Admin Controller-------------------
	@RequestMapping(value = "/createRole", method = RequestMethod.GET)
	public ModelAndView createRole(Model model, @RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.getCurrentLoggedInEndUser();
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		model.addAttribute("endUserForm", endUserForm);
		Collection<EndUser> endUsers = endUserDAOImpl.getList();
		List<Role> roles = endUserDAOImpl.findAll();
		model.addAttribute("roles", roles);

		model.addAttribute("endUsers", endUsers);
		return new ModelAndView("createRoleForCommon", "model", model);
	}

	@RequestMapping(value = "/roleCreate", method = RequestMethod.GET)
	public ModelAndView roleCreateByAdmin(Model model, @ModelAttribute Role role, @RequestParam("menuId") Long menuId)
			throws CustomException {

		EndUser endUser = this.getCurrentLoggedInEndUser();
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		List<Role> roles = endUserDAOImpl.findAll();
		List<Menu> menuList = endUserDAOImpl.findAllMenu();
		List<Permission> permissionList = endUserDAOImpl.findAllPermission();
		model.addAttribute("menuList", menuList);
		model.addAttribute("permissionList", permissionList);
		model.addAttribute("roles", roles);
		return new ModelAndView("roleCreateCommon", "model", model);
	}

	@RequestMapping(value = "/roleCreate", method = RequestMethod.POST)
	public ModelAndView roleCreateByAdmin(Model model, @ModelAttribute Role role, RedirectAttributes attributes)
			throws CustomException {
		model.addAttribute("menus", menus);
		Role isExistRole = endUserDAOImpl.findByRoleName(role.getRole());
		List<Role> roles = endUserDAOImpl.findAll();
		model.addAttribute("roles", roles);
		if (isExistRole != null) {
			model.addAttribute("error", "Role already exist");
		} else {
			role.setCreatedBy(getCurrentLoggedUserName());
			role.setModifiedBy(getCurrentLoggedUserName());
			role.setCreatedOn(new Date());
			role.setModifiedOn(new Date());
			role.setIsActive(true);
			role.setMenu(endUserDAOImpl.findAllMenu());
			endUserDAOImpl.createRole(role);
			model.addAttribute("sucess", "created Successfully");
			return new ModelAndView("roleCreate", "model", model);
		}
		return new ModelAndView("roleCreate", "model", model);
	}

	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public ModelAndView updateRole(@ModelAttribute EndUserForm endUserForm, RedirectAttributes attributes, Model model)
			throws CustomException {
		model.addAttribute("menus", menus);
		endUserForm.setUserName(endUserForm.getUserName().split(",")[1]);
		List<EndUser> endUser = endUserDAOImpl.findByUsername(endUserForm.getUserName()).getResultList();

		if (endUser.size() == 0) {

			if (endUserForm != null && endUserForm.getCurrentRole().equals("ROLE_APPROVALMNG")) {

				model.addAttribute("endUserForm", endUserForm);

				return new ModelAndView("approvalManager", "model", model);
			} else if (endUserForm != null && endUserForm.getCurrentRole().equals("ROLE_VP")) {

				model.addAttribute("endUserForm", endUserForm);

				return new ModelAndView("vicePresident", "model", model);
			} else {

				model.addAttribute("endUserForm", endUserForm);

				return new ModelAndView("bankEmployee", "model", model);
			}

		} else {
			endUserForm.setUserName("");
			model.addAttribute(Constants.ERROR, Constants.ROLE);
			model.addAttribute("endUserForm", endUserForm);
			return new ModelAndView("createRoleForCommon", "model", model);

		}

	}

	@RequestMapping(value = "/editRole", method = RequestMethod.GET)
	public ModelAndView editRole(Model model, @RequestParam("id") Long id, RedirectAttributes attributes)
			throws CustomException {
		model.addAttribute("menus", menus);
		Role role = endUserDAOImpl.findById(id);
		model.addAttribute("role", role);
		return new ModelAndView("editRole", "model", model);

	}

	@RequestMapping(value = "/savedRoleSuccess", method = RequestMethod.GET)
	public ModelAndView savedRoleSuccess(Model model, @ModelAttribute EndUserForm endUserForm) throws CustomException {
		model.addAttribute("menus", menus);
		model.addAttribute("endUserForm", endUserForm);
		return new ModelAndView("savedRoleSuccess", "model", model);

	}

	@RequestMapping(value = "/roleUpdate", method = RequestMethod.POST)
	public ModelAndView roleUpdate(Model model, @ModelAttribute("role") Role role, RedirectAttributes attributes)
			throws CustomException {
		model.addAttribute("menus", menus);
		Role isExistRole = endUserDAOImpl.findByRoleName(role.getRole());
		if (isExistRole != null) {
			model.addAttribute("error", "Role already exist");
			model.addAttribute("role", role);
			return new ModelAndView("editRole", "model", model);
		} else {
			role.setModifiedBy(getCurrentLoggedUserName());
			role.setModifiedOn(new Date());
			role.setIsActive(true);
			Role role_ = endUserDAOImpl.updateRole(role);
			model.addAttribute("role", role_);
			model.addAttribute("sucess", "updated Successfully");
			return new ModelAndView("editRole", "model", model);
		}

	}

	@RequestMapping(value = "/updateRole1", method = RequestMethod.POST)
	public ModelAndView roleupdate(Model model, @ModelAttribute EndUserForm endUserForm, RedirectAttributes attributes)
			throws CustomException {

		ModelAndView mav = new ModelAndView();

		if (endUserForm != null && endUserForm.getRole().equals(3)) {

			model.addAttribute("endUserForm", endUserForm);

			mav = new ModelAndView("approvalmanagerUpdate", "model", model);
		} else {

			model.addAttribute("endUserForm", endUserForm);

			mav = new ModelAndView("bankEmployeeUpdate", "model", model);
		}

		return mav;

	}

	@RequestMapping(value = "/manageRole", method = RequestMethod.GET)
	public ModelAndView manageRole(Model model, @ModelAttribute("manageRole") ManageRoleDTO manageRole,
			@RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.getCurrentLoggedInEndUser();
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		List<Role> roles = endUserDAOImpl.findAll();
		List<Menu> menuList = endUserDAOImpl.getSubMenuAndAllMenu();

		for (Menu menu1 : menuList) {
			List<Permission> permissionList = new ArrayList<Permission>();
			for (String id : menu1.getPossibleAction()) {
				Permission permission = endUserDAOImpl.findByPermissionId(Long.valueOf(id));
				permissionList.add(permission);
				menu1.setPermission(permissionList);
			}
		}
		model.addAttribute("menuList", menuList);
		model.addAttribute("roles", roles);
		return new ModelAndView("manageRoleForCommon", "model", model);
	}

	@RequestMapping(value = "getByRoleIdAndMenuAndPermissionDetails/{roleId}", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<RolePermissionMenu> getByRoleIdAndMenuAndPermissionDetails(
			@PathVariable("roleId") String roleId) {
		List<RolePermissionMenu> listOfPermissionMenu = endUserDAOImpl
				.getByRoleIdAndMenuAndPermissionDetails(Long.valueOf(roleId));
		return listOfPermissionMenu;

	}

	@RequestMapping(value = "/manageRoleSave", method = RequestMethod.POST)
	public ModelAndView manageRoleSave(Model model, @ModelAttribute("manageRole") ManageRoleDTO manageRole)
			throws CustomException {
		Role role = endUserDAOImpl.findById(manageRole.getRoleId());
		List<ManageRoleDTO> maDto = new Gson().fromJson(manageRole.getJsonData(), new TypeToken<List<ManageRoleDTO>>() {
		}.getType());
		List<Menu> menuList = new ArrayList<>();
		List<RolePermissionMenu> permissionList = new ArrayList<>();
		for (ManageRoleDTO manageRoleDTO : maDto) {
			Menu menu = endUserDAOImpl.findByMenuId(Long.valueOf(manageRoleDTO.getMenuId()));
			if (menu == null)
				continue;
			List<Menu> list = new ArrayList<>();
			list.add(menu);
			if (manageRoleDTO.getPermissionId().contains(",")) {

				String perId[] = manageRoleDTO.getPermissionId().split(",");
				for (String permissionId : perId) {
					Permission permission_ = endUserDAOImpl.findByPermissionId(Long.valueOf(permissionId));
					endUserDAOImpl.deleteByRoleIdAndMenuId(role.getId(), menu.getId());
					RolePermissionMenu rolePermissionMenu = new RolePermissionMenu();
					rolePermissionMenu.setPermissionId(permission_.getId());
					rolePermissionMenu.setMenuId(menu.getId());
					rolePermissionMenu.setRoleId(role.getId());
					permissionList.add(rolePermissionMenu);
				}
			} else {
				endUserDAOImpl.deleteByRoleIdAndMenuId(role.getId(), menu.getId());
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
		model.addAttribute("menuList", menuList_);
		model.addAttribute("roles", roles);
		model.addAttribute("sucess", "updated successfully !");
		return new ModelAndView("manageRole", "model", model);
	}

	// -------Nagarjuna Finished-------------------------

	private Boolean checkPermissions(Long menuId, String operation) {
		Menu menu = endUserDAO.findByMenuAndRoleId(ROLE_ID, menuId);
		if (menu.getPermissions() != null && menu.getPermissions().indexOf(operation) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(value = "/createSingleDeposit", method = RequestMethod.POST)
	public ModelAndView createSingleDeposit(Model model, @ModelAttribute FixedDepositForm fixedDepositForm, String val,
			@RequestParam String customerDetails, @RequestParam String productId) throws CustomException {

		Customer customer = customerDAO.getById(fixedDepositForm.getId());

		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(fixedDepositForm.getId());
		List<ModeOfPayment> paymentList = modeOfPaymentDAO.getAllModeOfPaymentEmployee();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		fixedDepositForm.setCustomerName(customer.getCustomerName());
		fixedDepositForm.setCitizen(customer.getCitizen());
		fixedDepositForm.setAccountList(accountList);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

		model.addAttribute("customer", customer);
		model.addAttribute("paymentMode", paymentList);

		model.addAttribute("fixedDepositForm", fixedDepositForm);
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		Long pId = Long.parseLong(productId);
		model.addAttribute("todaysDate", date);

		List<Branch> branch = branchDAO.getAllBranches();
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(pId);
		model.addAttribute("productConfiguration", productConfiguration);
		model.addAttribute("branchName", this.branch);
		model.addAttribute("branchCode", branchCode);
		model.addAttribute("nriAccountType", customer.getNriAccountType());
		model.addAttribute("branch", branch);

		model.addAttribute("val", val);
		model.addAttribute("customerDetails", customerDetails);

		return new ModelAndView("createSingleDeposit", "model", model);

	}

	@RequestMapping(value = "/panCardValidation", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody Integer panCardValidation(Model model, @RequestParam Long gaurdianAadhar,
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

	@RequestMapping(value = "/confirmFixedDeposit", method = RequestMethod.POST)
	public ModelAndView confirmFixedDeposit(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			@RequestParam String customerDetails, RedirectAttributes attributes) throws CustomException {

		ModeOfPayment mop = modeOfPaymentDAO
				.getModeOfPaymentById(Long.parseLong(fixedDepositForm.getDepositForm().getPaymentMode()));
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
			model.addAttribute("customer", customer);

			model.addAttribute("fixedDepositForm", fixedDepositForm);

			model.addAttribute("todaysDate", date);

			// BankConfiguration bankConfiguration = ratesDAO.findAll();
			// if (bankConfiguration != null) {
			// model.addAttribute("bankConfiguration", bankConfiguration);
			// }
			EndUser endUser = this.currentLoggedInUser;
			List<Menu> menus = this.menus;

			model.addAttribute("menus", menus);
			model.addAttribute("endUser", endUser);

			model.addAttribute(Constants.ERROR, Constants.invalidTenure);
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
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

		model.addAttribute("paymentMode", mop.getPaymentMode());
		model.addAttribute("fdDeductDate", date);
		model.addAttribute("getcustomerByPanCard", getcustomerByPanCard);
		model.addAttribute("getcustomerByAadharCard", getcustomerByAadharCard);
		model.addAttribute("customerDetails", customerDetails);
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		Role role = endUserDAO.findById(this.ROLE_ID);
		model.addAttribute("role", role);
		return new ModelAndView("getConfirmFixedDepositForm", "model", model);

	}

	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/saveDeposit", method = RequestMethod.POST)
	public String saveDeposit(Model model, RedirectAttributes attributes,
			@ModelAttribute FixedDepositForm fixedDepositForm, HttpServletRequest request) throws CustomException {

		Deposit deposit = new Deposit();
		Customer customer = customerDAO.getById(fixedDepositForm.getId());
		Date date = DateService.getCurrentDateTime();
		String transactionId = fdService.generateRandomString();

		Long mopId = Long.parseLong(fixedDepositForm.getDepositForm().getPaymentMode());
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPaymentById(mopId);

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
		deposit.setPaymentMode(mop.getPaymentMode());
		deposit.setPaymentModeId(mopId);
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

		if (mop.getPaymentMode().equalsIgnoreCase("DD") || mop.getPaymentMode().equalsIgnoreCase("Cheque")) {
			deposit.setClearanceStatus(Constants.WAITINGFORCLEARANCE);
		}

		deposit.setMaturityInstruction(fixedDepositForm.getMaturityInstruction());
		deposit.setMaturityDate(fixedDepositForm.getMaturityDate());
		deposit.setNewMaturityDate(fixedDepositForm.getMaturityDate());
		deposit.setBranchCode(fixedDepositForm.getBranchCode());
		deposit.setProductConfigurationId(fixedDepositForm.getProductConfigurationId());
		deposit.setIsMaturityDisbrsmntInLinkedAccount(fixedDepositForm.getIsMaturityDisbrsmntInLinkedAccount());
		Deposit depositSaves = fixedDepositDao.saveFD(deposit);

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
		fixedDepositDao.updateFD(depositSaves);

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

		/* / Deduction from Linked account / */

		if (mop.getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {
			AccountDetails accountDetails = accountDetailsDAO
					.findByAccountNo(fixedDepositForm.getDepositForm().getLinkedAccountNo());
			accountDetails.setAccountBalance(
					accountDetails.getAccountBalance() - Float.valueOf(fixedDepositForm.getDepositForm().getFdPay()));
			payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getAccountType());
			payment.setLinkedAccNoForFundTransfer(fixedDepositForm.getAccountNo());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getLinkedAccountNo();
			accountDetailsDAO.updateUserAccountDetails(accountDetails);
		}

		Double amountPaid = FDService.round(Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()), 2);
		payment.setAmountPaid(amountPaid);
		payment.setDepositId(depositSaves.getId());

		// For accounting entry only
		if (mop.getPaymentMode().equalsIgnoreCase("Cash")) {
			fromAccountNo = "";

		} else if (mop.getPaymentMode().equalsIgnoreCase("DD") || mop.getPaymentMode().equalsIgnoreCase("Cheque")) {

			payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
			payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
			payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
			payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getChequeNo();

		} else if (mop.getPaymentMode().equalsIgnoreCase("Card Payment")) {

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
		}

		else if (mop.getPaymentMode().equalsIgnoreCase("Net Banking")) {

			payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getDepositForm().getAccountType());
			payment.setLinkedAccNoForFundTransfer(fixedDepositForm.getDepositForm().getLinkedAccountNo());
			payment.setTransferType(fixedDepositForm.getDepositForm().getTransferType());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getLinkedAccountNo();
		}

		payment.setDepositHolderId(depositHolderNew.getId());
		payment.setPaymentMadeByHolderIds(depositHolderNew.getId().toString());
		payment.setPaymentModeId(mopId);
		payment.setPaymentMode(mop.getPaymentMode());
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
		if (!mop.getPaymentMode().equalsIgnoreCase("DD") && !mop.getPaymentMode().equalsIgnoreCase("Cheque")) {
			ledgerService.insertJournal(depositSaves.getId(), customer.getId(), DateService.getCurrentDate(),
					fromAccountNo, depositSaves.getAccountNumber(), Event.OPEN_DEPOSIT.getValue(), amountPaid, mopId,
					depositSummary.getTotalPrincipal(), transactionId);
		}
		// -----------------------------------------------------------

		// fdService.forReports(fixedDepositForm); // For reports
		attributes.addFlashAttribute("fixedDepositForm", fixedDepositForm);

		attributes = updateTransaction("Saved Successfully", attributes);
		return "redirect:successfullySaved";
	}

	@RequestMapping(value = "/jointConsortiumDeposit", method = RequestMethod.POST)
	public ModelAndView jointConsortiumDeposit(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String nriAccType, HttpServletRequest request, @RequestParam String customerDetails,
			@RequestParam String productId) throws JsonParseException, JsonMappingException, IOException {
		model.addAttribute("branchName", this.branch);
		model.addAttribute("branchCode", branchCode);
		int removeIndexOfList = -1;
		int loopIndex = 0;
		Long pId = Long.parseLong(productId);
		Customer customer = customerDAO.getById(fixedDepositForm.getId());
		nriAccType = customer.getNriAccountType();
		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(fixedDepositForm.getId());
		List<ModeOfPayment> paymentList = null;
		if (endUserDAO.findById(ROLE_ID).getRole().equals("ROLE_USER"))
			paymentList = modeOfPaymentDAO.getAllModeOfPaymentCustomer();
		else
			paymentList = modeOfPaymentDAO.getAllModeOfPaymentEmployee();

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
		model.addAttribute("branch", branch);

		model.addAttribute("nriAccType", nriAccType);
		model.addAttribute("accountBal", accountBal);
		model.addAttribute("todaysDate", date);
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		model.addAttribute("customerDetailsParsers", customerDetailsParsers);
		model.addAttribute("customerDetails", customerDetails);
		model.addAttribute("customer", customer);

		model.addAttribute("paymentMode", paymentList);

		ProductConfiguration productConfiguration = productConfigurationDAO.findById(pId);
		model.addAttribute("productConfiguration", productConfiguration);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		Role role = endUserDAO.findById(this.ROLE_ID);
		model.addAttribute("role", role);

		return new ModelAndView("jointConsortiumDepositBankEmp", "model", model);

	}

	@RequestMapping(value = "/jointConsortiumDepositSummary", method = RequestMethod.POST)
	public ModelAndView jointConsortiumDepositSummary(@ModelAttribute FixedDepositForm fixedDepositForm,
			@RequestParam String customerDetails, Model model, HttpServletRequest request,
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
		 * if (getcustomerByPanCard != 0) { model.addAttribute("errorPan",
		 * Constants.PanCardExist); return new
		 * ModelAndView("jointConsortiumDepositBankEmp", "model", model); }
		 * 
		 * if (getcustomerByAadharCard != 0) { model.addAttribute("errorAadhar",
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
		 * if (getJointcustomerByPanCard != 0) { model.addAttribute("errorPanJoint",
		 * Constants.PanCardExist); return new
		 * ModelAndView("jointConsortiumDepositBankEmp", "model", model); }
		 * 
		 * if (getJointcustomerByAadharCard != 0) {
		 * model.addAttribute("errorAadharJoint", Constants.aadharCardExist); return new
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

		// List<Interest> interestList = fdService.getInterestBreakup(todaysDate,
		// maturityDate, fixedDepositForm,
		// rateOfInterest, 0);

		// Double totalDepositAmount = fdService.getTotalDepositAmount(fixedDepositForm,
		// fixedDepositForm.getMaturityDate(), fixedDepositForm.getFdAmount());
		// Float totalInterest = fdService.getTotalInterestAmount(interestList);
		Float totalTDS = 0f;
		// Float totalTDS = fdService.getTotalTDSAmount(todaysDate,
		// maturityDate, fixedDepositForm.getCategory(),
		// fixedDepositForm, interestList);

		fixedDepositForm.setFdCreditAmount(rateOfInterest);
		// fixedDepositForm.setEstimateInterest(totalInterest);
		fixedDepositForm.setEstimateTDSDeduct(totalTDS);
		// fixedDepositForm.setEstimatePayOffAmount(totalDepositAmount + totalInterest -
		// totalTDS);

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
		model.addAttribute("getJointcustomerByPanCard", getJointcustomerByPanCard);
		model.addAttribute("getJointcustomerByAadharCard", getJointcustomerByAadharCard);
		model.addAttribute("getcustomerByPanCard", getJointcustomerByPanCard);
		model.addAttribute("getcustomerByAadharCard", getJointcustomerByAadharCard);
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		model.addAttribute("customerDetails", customerDetails);

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		Role role = endUserDAO.findById(this.ROLE_ID);
		model.addAttribute("role", role);

		return new ModelAndView("jointConsortiumDepositSummary", "model", model);
	}

	@Transactional
	@RequestMapping(value = "/savePostJointConsortium", method = RequestMethod.POST)
	public String savePostJointConsortium(@ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes) throws CustomException {

		/******* Saving Deposit *****/
		String transactionId = fdService.generateRandomString();

		Long mopId = Long.parseLong(fixedDepositForm.getDepositForm().getPaymentMode());
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPaymentById(mopId);

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
		deposit.setPaymentMode(mop.getPaymentMode());
		deposit.setPaymentModeId(mopId);
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
		deposit.setIsMaturityDisbrsmntInLinkedAccount(fixedDepositForm.getIsLinkedAccount());
		Deposit newDeposit = fixedDepositDao.saveFD(deposit);

		List<DepositHolder> depositHolderList = new ArrayList<>();
		/****** Saving DepositHolder ******/

		Integer isContributionRequired = 0;
		ProductConfiguration productConfiguration = productConfigurationDAO
				.findById(fixedDepositForm.getProductConfigurationId());
		isContributionRequired = productConfiguration.getContributionRequiredForJointAccHolders() != null
				? productConfiguration.getContributionRequiredForJointAccHolders()
				: 0;

		Long depositId = newDeposit.getId();
		String paymentMadeByHolderIds = "";
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
		depositHolder.setIsMaturityDisbrsmntInLinkedAccount(fixedDepositForm.getIsMaturityDisbrsmntInLinkedAccount());
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
			depositHolderJoint.setIsMaturityDisbrsmntInSameBank(
					fixedDepositForm.getJointAccounts().get(i).getIsMaturityDisbrsmntInSameBank());

			depositHolderJoint.setMaturityDisbrsmntAccHolderName(
					fixedDepositForm.getJointAccounts().get(i).getMaturityDisbrsmntAccHolderName());

			depositHolderJoint.setMaturityDisbrsmntAccNumber(
					fixedDepositForm.getJointAccounts().get(i).getMaturityDisbrsmntAccNumber());

			depositHolderJoint.setMaturityDisbrsmntTransferType(
					fixedDepositForm.getJointAccounts().get(i).getMaturityDisbrsmntTransferType());

			depositHolderJoint.setMaturityDisbrsmntBankName(
					fixedDepositForm.getJointAccounts().get(i).getMaturityDisbrsmntBankName());

			depositHolderJoint.setMaturityDisbrsmntBankIFSCCode(
					fixedDepositForm.getJointAccounts().get(i).getMaturityDisbrsmntBankIFSCCode());

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
		fixedDepositDao.updateFD(newDeposit);

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

		Payment payment = new Payment();
		payment.setDepositId(depositId);

		Double amountPaid = FDService.round(Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()), 2);
		payment.setAmountPaid(amountPaid);

		if (mop.getPaymentMode().equalsIgnoreCase("Cash")) {
			fromAccountNo = "";
		}

		if (mop.getPaymentMode().equalsIgnoreCase("DD") || mop.getPaymentMode().equalsIgnoreCase("Cheque")) {

			payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
			payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
			payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
			payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());

			// For Accounting Entries Only
			fromAccountNo = fixedDepositForm.getDepositForm().getChequeNo();
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

		if (mop.getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {

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

		}

		payment.setDepositHolderId(calculationService.getPrimaryHolderId(depositHolderList));
		payment.setPaymentMadeByHolderIds(paymentMadeByHolderIds);
		payment.setPaymentModeId(mopId);
		payment.setPaymentMode(mop.getPaymentMode());
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
		if (!mop.getPaymentMode().equalsIgnoreCase("DD") && !mop.getPaymentMode().equalsIgnoreCase("Cheque")) {
			ledgerService.insertJournal(newDeposit.getId(), customer.getId(), DateService.getCurrentDate(),
					fromAccountNo, newDeposit.getAccountNumber(), Event.OPEN_DEPOSIT.getValue(), amountPaid, mopId,
					depositSummary.getTotalPrincipal(), transactionId);
		}
		// --------------------------------------------------------------------

		attributes = updateTransaction("Saved Successfully", attributes);
		return "redirect:successfullySaved";

	}

	@RequestMapping(value = "/newDeposit")
	public ModelAndView newDeposit(Model model, @RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);

		return new ModelAndView("newDeposit", "model", model);
	}

	@RequestMapping(value = "/depositProducts")
	public ModelAndView depositProducts(Model model, String productType) throws CustomException {

		List<ProductConfiguration> productConfigurationListForRI = productConfigurationDAO
				.getProductConfigurationListByProductTypeAndCitizen(productType, "RI");
		List<ProductConfiguration> productConfigurationListForNRI = productConfigurationDAO
				.getProductConfigurationListByProductTypeAndCitizen(productType, "NRI");

		model.addAttribute("productType", productType);
		model.addAttribute("productConfigurationListForRI", productConfigurationListForRI);
		model.addAttribute("productConfigurationListForNRI", productConfigurationListForNRI);
		NRIServiceBranches nriBranch = null;
		try {
			nriBranch = nRIServiceBranchesDAO.getNRIServiceBranchByBranchCode(branchCode);
		} catch (NoResultException nre) {
		}
		model.addAttribute("nriBranch", nriBranch);
		model.addAttribute("branchCode", branchCode);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

		return new ModelAndView("depositProducts", "model", model);
	}

	@RequestMapping(value = "/createDeposit", method = RequestMethod.GET)
	public ModelAndView createDeposit(Model model, Long productId, String name) throws CustomException {
		model.addAttribute("productConfigurationId", productId);
		fixedDepositForm.setProductConfigurationId(productId);
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);

		Role role = endUserDAO.findById(this.ROLE_ID);
		model.addAttribute("role", role);

		if (role.getRole().equals("ROLE_USER")) {
			Long cutomerId = endUser.getCustomerId();
			Customer customer = customerDAO.getById(cutomerId);
			model.addAttribute("customer", customer);

			if (name.toLowerCase().contains("single")) {
				List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(cutomerId);
				List<ModeOfPayment> paymentList = null;
				if (role.getRole().equals("ROLE_USER"))
					paymentList = modeOfPaymentDAO.getAllModeOfPaymentCustomer();
				else
					paymentList = modeOfPaymentDAO.getAllModeOfPaymentEmployee();

				fixedDepositForm.setId(cutomerId);
				fixedDepositForm.setCustomerName(customer.getCustomerName());
				fixedDepositForm.setCitizen(customer.getCitizen());
				fixedDepositForm.setAccountList(accountList);
				model.addAttribute("paymentMode", paymentList);
				model.addAttribute("fixedDepositForm", fixedDepositForm);
				SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
				String date = smt.format(DateService.getCurrentDateTime());
				model.addAttribute("todaysDate", date);
				ProductConfiguration productConfiguration = productConfigurationDAO.findById(productId);
				model.addAttribute("productConfiguration", productConfiguration);
				model.addAttribute("branchName", this.branch);
				model.addAttribute("branchCode", branchCode);
				model.addAttribute("nriAccountType", customer.getNriAccountType());
				return new ModelAndView("createSingleDeposit", "model", model);
			}

			if (name.toLowerCase().contains("amount")) {
				fixedDepositForm.setProductConfigurationId(productId);
				model.addAttribute("branchName", this.branch);
				model.addAttribute("branchCode", branchCode);
				Customer customerDetails = customerDAO.getByUserName(customer.getUserName());
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
				List<Branch> branch = branchDAO.getAllBranches();
				model.addAttribute("productConfiguration", productConfiguration);
				model.addAttribute("branch", branch);
				model.addAttribute("fixedDepositForm", fixedDepositForm);
				model.addAttribute("customerId", customerDetails.getId());
				model.addAttribute("customer", customerDetails);
				model.addAttribute("todaysDate", date);
				model.addAttribute("cashPayment", 0);
				model.addAttribute("ddPayment", 0);
				model.addAttribute("chequePayment", 0);
				model.addAttribute("netBanking", 1);
				model.addAttribute("currency", currency);
				return new ModelAndView("reverseEmiFixedAmountForBankEmp");

			}
			if (name.toLowerCase().contains("tenure")) {
				Customer customerDetails = customerDAO.getByUserName(customer.getUserName());
				model.addAttribute("branchName", this.branch);
				model.addAttribute("branchCode", branchCode);
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

				List<Branch> branch = branchDAO.getAllBranches();
				ProductConfiguration productConfiguration = productConfigurationDAO.findById(productId);
				model.addAttribute("productConfiguration", productConfiguration);
				fixedDepositForm.setProductConfigurationId(productId);
				model.addAttribute("fixedDepositForm", fixedDepositForm);
				model.addAttribute("branch", branch);
				model.addAttribute("customerId", customerDetails.getId());
				model.addAttribute("customerNriAccountType", customerDetails.getNriAccountType());
				model.addAttribute("todaysDate", date);
				model.addAttribute("cashPayment", 0);
				model.addAttribute("ddPayment", 0);
				model.addAttribute("chequePayment", 0);
				model.addAttribute("netBanking", 1);
				model.addAttribute("currency", currency);
				return new ModelAndView("reverseEmiForBankEmp", "model", model);

			}
		}

		return new ModelAndView("applyDeposit", "model", model);

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

	@RequestMapping(value = "/reverseEmiFixedAmountForBankEmp")
	public ModelAndView reverseEmiFixedAmountForBankEmp(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			@RequestParam String userName, RedirectAttributes attributes, String val, Long productId, String name)
			throws CustomException {
		fixedDepositForm.setProductConfigurationId(productId);
		model.addAttribute("branchName", this.branch);
		model.addAttribute("branchCode", branchCode);
		Customer customerDetails = customerDAO.getByUserName(userName);
		/*
		 * BankConfiguration bankConfiguration = ratesDAO.findAll(); if
		 * (bankConfiguration == null) { model.addAttribute("error",
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
		model.addAttribute("productConfiguration", productConfiguration);
		model.addAttribute("branch", branch);
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		model.addAttribute("customerId", customerDetails.getId());
		model.addAttribute("customer", customerDetails);
		// model.addAttribute("bankConfiguration", bankConfiguration);
		model.addAttribute("todaysDate", date);
		model.addAttribute("cashPayment", 1);
		model.addAttribute("ddPayment", 1);
		model.addAttribute("chequePayment", 1);
		model.addAttribute("netBanking", 0);
		model.addAttribute("val", val);
		model.addAttribute("currency", currency);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

		return new ModelAndView("reverseEmiFixedAmountForBankEmp");

	}

	@RequestMapping(value = "/reverseEmiForBankEmp")
	public ModelAndView reverseEmiForBankEmp(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			@ModelAttribute EndUserForm endUserForm, RedirectAttributes attributes, @RequestParam String userName,
			String val, String productId) throws CustomException {
		Customer customerDetails = customerDAO.getByUserName(userName);
		model.addAttribute("branchName", this.branch);
		model.addAttribute("branchCode", branchCode);
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
		model.addAttribute("productConfiguration", productConfiguration);
		fixedDepositForm.setProductConfigurationId(Long.valueOf(productId));
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		model.addAttribute("branch", branch);
		model.addAttribute("customerId", customerDetails.getId());
		model.addAttribute("customerNriAccountType", customerDetails.getNriAccountType());
		model.addAttribute("todaysDate", date);
		/// model.addAttribute("bankConfiguration", bankConfiguration);
		model.addAttribute("cashPayment", 1);
		model.addAttribute("ddPayment", 1);
		model.addAttribute("chequePayment", 1);
		model.addAttribute("netBanking", 0);
		model.addAttribute("val", val);
		model.addAttribute("currency", currency);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

		return new ModelAndView("reverseEmiForBankEmp", "model", model);
	}

	@RequestMapping(value = "/confirmReverseEmiFixedAmountForBank", method = RequestMethod.POST)
	public ModelAndView confirmReverseEmiFixedAmount(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
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
		// Float rateOfInterest =
		// depositRateDAO.getInterestRate(customerDetails.getCategory(),
		// fixedDepositForm.getCurrency(), daysDiffBetweenDepositAndMaturity,
		// Constants.annuityDeposit, fixedDepositForm.getFdAmount());
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

		// List<Interest> interestList = null;
		// if (fixedDepositForm.getPayOffInterestType() != null &&
		// !fixedDepositForm.getPayOffInterestType().equals("")) {
		//
		// List<Date> payoffDateList = null;
		// if (DateService.getDaysBetweenTwoDates(currentDate, gestationEndDate) >= 0)
		// payoffDateList = fdService.getPayoffDatesForFixedAmountEmi(gestationEndDate,
		// fixedDepositForm.getPayOffInterestType(), emiTimes); // Doubt
		// // on
		// // start
		// // date
		// else
		// payoffDateList = fdService.getPayoffDatesForFixedAmountEmi(currentDate,
		// fixedDepositForm.getPayOffInterestType(), emiTimes);// Doubt
		//
		// // To get the interest List for fixed amount emi
		// /*
		// * interestList = fdService.getInterestListForFixedAmountEmi(currentDate,
		// * maturityDate, rateOfInterest, emiAmount, totalDepositAmount,
		// * gestationEndDate, 0l, fixedDepositForm.getPayOffInterestType(), emiTimes,
		// * payoffDateList);
		// */
		//
		// /*
		// * if (payoffDateList != null) { // Date interestPayOffDate = //
		// * fdService.calculateInterestPayOffDueDate(fixedDepositForm.
		// * getPayOffInterestType(), // fixedDepositForm.getMaturityDate(),
		// currentDate);
		// *
		// * fixedDepositForm.setPayoffDate(payoffDateList.get(0)); }
		// */
		// }

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
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		Role role = endUserDAO.findById(this.ROLE_ID);
		model.addAttribute("role", role);

		return new ModelAndView("confirmReverseEmiFixedAmountForBank", "model", model);
	}

	@RequestMapping(value = "/confirmReverseEmiForBankEmp", method = RequestMethod.POST)
	public ModelAndView confirmReverseEmi(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
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
		// Float rateOfInterest =
		// depositRateDAO.getInterestRate(customerDetails.getCategory(),
		// fixedDepositForm.getCurrency(), daysDiff, Constants.annuityDeposit,
		// fixedDepositForm.getFdAmount());

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

		// List<Interest> interestList = null;
		// if (fixedDepositForm.getPayOffInterestType() != null &&
		// !fixedDepositForm.getPayOffInterestType().equals("")) {
		//
		// List<Date> payoffDateList = null;
		// if (DateService.getDaysBetweenTwoDates(currentDate, gestationEndDate) >= 0)
		// payoffDateList = fdService.getPayoffDatesForFixedAmountEmi(gestationEndDate,
		// fixedDepositForm.getPayOffInterestType(), emiTimes);
		// else
		// payoffDateList = fdService.getPayoffDatesForFixedAmountEmi(currentDate,
		// fixedDepositForm.getPayOffInterestType(), emiTimes);
		//
		// // To get the interest List for fixed amount emi
		// interestList = fdService.getInterestListForFixedAmountEmi(currentDate,
		// maturityDate, rateOfInterest,
		// emiAmount, totalDepositAmount, gestationEndDate, 0l,
		// fixedDepositForm.getPayOffInterestType(),
		// emiTimes, payoffDateList);
		//
		// if (payoffDateList != null) {
		// // Date interestPayOffDate =
		// //
		// fdService.calculateInterestPayOffDueDate(fixedDepositForm.getPayOffInterestType(),
		// // fixedDepositForm.getMaturityDate(), currentDate);
		//
		// fixedDepositForm.setPayoffDate(payoffDateList.get(0));
		// }
		// }
		//
		// Float totalInterest = interestList != null ?
		// fdService.getTotalInterestAmount(interestList) : 0f;
		// Float totalTDS = 0f;
		// fixedDepositForm.setEstimateTDSDeduct(totalTDS);
		// fixedDepositForm.setEstimateInterest(totalInterest);
		// fixedDepositForm.setEstimatePayOffAmount(totalDepositAmount + totalInterest -
		// totalTDS);

		/*
		 * List<Date> payoffDateList = fdService.getEMIPayoffDates(gestationEndDate,
		 * maturityDate, fixedDepositForm.getPayOffInterestType()); if
		 * (payoffDateList.size() > 0)
		 * fixedDepositForm.setPayoffDate(payoffDateList.get(0));
		 */

		Date payOffDueDate = fdService.getRevereseEMIPayOffDueDate(DateService.getCurrentDate(), gestationEndDate,
				maturityDate, fixedDepositForm.getPayOffInterestType(), false);
		fixedDepositForm.setPayoffDate(payOffDueDate);
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		Role role = endUserDAO.findById(this.ROLE_ID);
		model.addAttribute("role", role);

		return new ModelAndView("confirmReverseEmiForBankEmp", "model", model);
	}

	@RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
	public ModelAndView addCustomer(Model model, @ModelAttribute CustomerForm customerForm,
			@RequestParam("menuId") Long menuId) throws CustomException {
		List<CustomerCategory> list = customerDAO.findAll();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
		if (setCategory.size() > 0) {
			model.addAttribute("setCategory", setCategory);
			model.addAttribute("customerForm", customerForm);

		}
		model.addAttribute("customerForm", customerForm);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);

		return new ModelAndView("addCustomer", "model", model);

	}

	@RequestMapping(value = "/customerConfirm", method = RequestMethod.POST)
	public ModelAndView customerConfirm(@ModelAttribute CustomerForm customerForm, Model model,
			RedirectAttributes attributes, @RequestParam("menuId") Long menuId) throws ParseException, CustomException {
		/*
		 * SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy"); String date =
		 * smt.format(customerForm.getDateOfBirth()); Date date1 = new
		 * SimpleDateFormat("dd/MM/yyyy").parse(date); String todayDate =
		 * smt.format(DateService.getCurrentDateTime()); Date date2 = new
		 * SimpleDateFormat("dd/MM/yyyy").parse(todayDate); int daysDiff =
		 * DateService.getDaysBetweenTwoDates(date1, date2); if (daysDiff <= 0) {
		 * model.addAttribute("error",
		 * "Selected Date Of Birth is future date please insert correct date"); return
		 * new ModelAndView("addCustomer", "model", model);
		 * 
		 * }
		 */
		model = this.menuUtils(model, menuId);
		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {
			model.addAttribute("customerForm", customerForm);

			// model.addAttribute("menuId", menuId);

			return new ModelAndView("customerConfirm", "model", model);
		} else {
			List<CustomerCategory> list = customerDAO.findAll();
			Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);
			if (setCategory.size() > 0) {
				model.addAttribute("setCategory", setCategory);
				model.addAttribute("customerForm", customerForm);

			}
			model.addAttribute("customerForm", customerForm);
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("addCustomer", "model", model);

		}
	}

	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/savedDetails", method = RequestMethod.POST)
	public String customerSave(@ModelAttribute CustomerForm customerForm, Model model, RedirectAttributes attribute)
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
		customer.setPassword(customerForm.getContactNum());

		if (customerForm.getGuardianName() != null) {

			customer.setGuardianName(customerForm.getGuardianName());
			customer.setGuardianAge(customerForm.getGuardianAge());
			customer.setGuardianRelationShip(customerForm.getGuardianRelationShip());
			customer.setGuardianAddress(customerForm.getGuardianAddress());
			customer.setGuardianPan(
					customerForm.getGuardianPan() == null ? null : customerForm.getGuardianPan().toUpperCase());
			customer.setGuardianAadhar(customerForm.getGuardianAadhar());
		}
		EndUser endUser = new EndUser();
		endUser.setUserName(customerForm.getUserName());
		endUser.setPassword(customerForm.getContactNum());
		endUser.setDisplayName(customerForm.getCustomerName());
		endUser.setEmail(customerForm.getEmail());
		endUser.setStatus(Constants.PENDING);
		endUser.setPrefferedLanguage("en");
		endUser.setTheme("themeBlue");
		endUser.setContactNo(customerForm.getContactNum());
		ArrayList<Role> roles = new ArrayList<Role>();
		Role role = endUserDAO.findByRoleName("ROLE_USER");
		roles.add(role);
		endUser.setRoles(roles);
		endUser.setPasswordFlag(0);
		customer = customerDAO.insertCustomer(customer);
		endUser.setCustomerId(customer.getId());
		endUserDAOImpl.createUser(endUser);
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
				 * // Accounting Entry //
				 * -----------------------------------------------------------------------------
				 * -------------- if
				 * (accountDetails.getAccountType().equalsIgnoreCase("Savings")) {
				 * 
				 * 
				 * ledgerService.insertJournalLedger(null, customer.getId(),
				 * DateService.getCurrentDate(), null, "Cash Account", accNoList[i],
				 * "Saving Account", "Opening Balance of Saving Account",
				 * Double.valueOf(accBalList[i]), "Cash", Double.valueOf(accBalList[i])); } else
				 * if (accountDetails.getAccountType().equalsIgnoreCase("Current")) {
				 * ledgerService.insertJournalLedger(null, customer.getId(),
				 * DateService.getCurrentDate(), null, "Cash Account", accNoList[i],
				 * "Current Account", "Opening Balance of Current Account",
				 * Double.valueOf(accBalList[i]), "Cash", Double.valueOf(accBalList[i]));
				 * ledgerService.insertJournalLedger(null, customer.getId(),
				 * DateService.getCurrentDate(), null, "Cash Account", accNoList[i],
				 * "Deposit Account", "Opening Balance of Deposit Account",
				 * Double.valueOf(accBalList[i]), "Cash", Double.valueOf(accBalList[i])); } //
				 * -----------------------------------------------------------------------------
				 * --------------
				 */ }
		}

		Transaction trans = new Transaction();

		String transactionId = fdService.generateRandomString();
		trans.setTransactionId(transactionId);
		trans.setTransactionType(Constants.CUSTOMERADDED);
		transactionDAO.insertTransaction(trans);

		attribute = updateTransaction("Saved Successfully", attribute);
		return "redirect:successfullySaved";

	}

	@RequestMapping(value = "/viewCustomerList", method = RequestMethod.GET)
	public ModelAndView customerList(Model model, @RequestParam("menuId") Long menuId) throws CustomException {

		Collection<Customer> cusList = customerDAO.findAllCustomers();
		model.addAttribute("cusList", cusList);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);

		return new ModelAndView("customerList", "model", model);

	}

	@Transactional
	@RequestMapping(value = "/saveDepositEmi", method = RequestMethod.POST)
	public String saveDepositEmi(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes, HttpServletRequest request, String userName) throws CustomException {

		String transactionId = fdService.generateRandomString();
		Date date = DateService.getCurrentDateTime();
		Customer customerDetails = customerDAO.getByUserName(fixedDepositForm.getUserName());

		Customer customer = getCustomerDetails(customerDetails.getId());
		String category = calculationService.geCustomerActualCategory(customer);
		fixedDepositForm.setCategory(category);

		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(fixedDepositForm.getDepositForm().getPaymentMode());
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
		deposit.setPaymentMode(mop.getPaymentMode());
		deposit.setPaymentModeId(mop.getId());
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
		EndUser user = this.currentLoggedInUser;

		deposit.setCreatedBy(user.getUserName());

		// Getting the interest rate for the duration
		int daysDiff = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(),
				fixedDepositForm.getMaturityDate()) + 1;
		// Float interestRate =
		// depositRateDAO.getInterestRate(customerDetails.getCategory(),
		// fixedDepositForm.getCurrency(), daysDiff, Constants.annuityDeposit,
		// fixedDepositForm.getFdAmount());

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
		Deposit depositSaves = fixedDepositDao.saveFD(deposit);

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

		Payment payment = new Payment();
		payment.setDepositId(depositSaves.getId());
		payment.setAmountPaid(Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()));

		if (mop.getPaymentMode().contains("Cash") || mop.getPaymentMode().contains("cash")) {
			fromAccountNo = "";

		} else if (mop.getPaymentMode().equalsIgnoreCase("DD") || mop.getPaymentMode().equalsIgnoreCase("Cheque")) {

			payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
			payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
			payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
			payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getChequeNo();

		} else if (mop.getPaymentMode().equalsIgnoreCase("Card Payment")) {

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

		}

		else if (mop.getPaymentMode().equalsIgnoreCase("Net Banking")) {

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

		} else if (mop.getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {

			payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getDepositForm().getAccountType());
			payment.setLinkedAccNoForFundTransfer(fixedDepositForm.getDepositForm().getLinkedAccountNo());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getLinkedAccountNo();

		}

		payment.setDepositHolderId(depositHolderNew.getId());
		payment.setPaymentModeId(mop.getId());
		payment.setPaymentMode(mop.getPaymentMode());
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
		ledgerService.insertJournal(depositSaves.getId(), customer.getId(), DateService.getCurrentDate(), fromAccountNo,
				depositSaves.getAccountNumber(), Event.OPEN_DEPOSIT.getValue(),
				Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()), mop.getId(),
				depositSummary.getTotalPrincipal(), transactionId);
		// -----------------------------------------------------------

		// fdService.forReports(fixedDepositForm); // For reports
		attributes.addFlashAttribute("fixedDepositForm", fixedDepositForm);

		/************* Saving Interest Table **********************************/

		attributes = updateTransaction("Deposit created successfully", attributes);
		return "redirect:successfullySaved";
	}

	@RequestMapping(value = "/approvedDepositList", method = RequestMethod.GET)
	public ModelAndView fdCustomerList(Model model, RedirectAttributes attributes, @RequestParam("menuId") Long menuId)
			throws CustomException {

		ModelAndView mav = new ModelAndView();
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		Role role = endUserDAO.findById(ROLE_ID);
		List<DepositForm> depositList = null;
		if (role.getRoleDisplayName().equals("User")) {
			EndUser user = getCurrentLoggedUserDetails();
			Customer customer = customerDAO.getByUserId(user.getUserName()).getSingleResult();
			depositList = fixedDepositDao.getAllRegularAndAutoDepositsByCustomerId(customer.getId());
		} else {
			depositList = fixedDepositDao.getAllDepositsList();
		}

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

			model.addAttribute("depositList", depositList);

			mav = new ModelAndView("fdListBank", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/clearanceList", method = RequestMethod.GET)
	public ModelAndView clearanceList(Model model, @RequestParam("menuId") Long menuId) throws CustomException {

		ModelAndView mav = new ModelAndView();
		List<Deposit> clearanceList = fixedDepositDao.findByClearanceStatus();
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		if (clearanceList.size() > 0) {
			model.addAttribute("clearanceList", clearanceList);

			mav = new ModelAndView("clearanceList", "model", model);
		} else {

			mav = new ModelAndView("noDataFound", "model", model);
		}
		return mav;
	}

	@RequestMapping(value = "/depositClearance")
	public ModelAndView depositClearance(ModelMap model, @RequestParam Long[] ids, Long menuId) throws CustomException {

		List<Long> depositIds = Arrays.asList(ids);

		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);

		EndUser endUser = this.currentLoggedInUser;

		model.addAttribute("endUser", endUser);

		List<Deposit> depositList = fixedDepositDao.findByMultipleId(depositIds);
		depositsList.setdList(depositList);
		model.put("depositsList", depositsList);

		return new ModelAndView("depositClearance", "model", model);

	}

	@Transactional
	@RequestMapping(value = "/confirmDepositClearance")
	public ModelAndView confirmDepositClearance(ModelMap model, @ModelAttribute DepositsList depositsList,
			RedirectAttributes attributes) throws CustomException {
		List<Deposit> deposits = depositsList.getdList();
		// Deposit deposit = new Deposit();
		for (int i = 0; i < deposits.size(); i++) {

			// deposit.setId(null);

			Deposit deposit = fixedDepositDao.getDeposit(deposits.get(i).getId());
			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
			deposit.setClearanceStatus(null);

			Set<DepositHolder> set = new HashSet<DepositHolder>();
			set.addAll(depositHolderList);
			deposit.setDepositHolder(set);
			fixedDepositDao.updateFD(deposit);

			// Journal Entry
			Payment payment = paymentDAO.getLastPaymentByDepositId(deposit.getId());

			ledgerService.insertJournal(deposit.getId(), calculationService.getPrimaryHolderId(depositHolderList),
					DateService.getCurrentDate(), payment.getChequeDDNumber(), deposit.getAccountNumber(),
					Event.OPEN_DEPOSIT.getValue(), deposit.getDepositAmount(), deposit.getPaymentModeId(),
					deposit.getDepositAmount(), null);
			// ------------------------------------------------------------------------
		}

		attributes = updateTransaction("Deposit cleared successfully", attributes);
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		return new ModelAndView("redirect:successfullySaved");
	}

	@RequestMapping(value = "/depositListToClose", method = RequestMethod.GET)
	public ModelAndView closeFDList(Model model, RedirectAttributes attributes, @RequestParam("menuId") Long menuId)
			throws CustomException {

		ModelAndView mav = new ModelAndView();

		Collection<Deposit> fixedDeposits = null;

		Role role = endUserDAO.findById(ROLE_ID);
		if (role.getRoleDisplayName().equals("User")) {
			EndUser user = getCurrentLoggedUserDetails();
			Customer cus = customerDAO.getByUserId(user.getUserName()).getSingleResult();
			fixedDeposits = fixedDepositDao.getDeposits(cus.getId());
		} else {
			fixedDeposits = fixedDepositDao.getApprovedDeposits();
		}

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		if (fixedDeposits != null && fixedDeposits.size() > 0) {

			model.addAttribute("fixedDeposits", fixedDeposits);

			mav = new ModelAndView("bankclosingFDList", "model", model);

		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/depositList", method = RequestMethod.GET)
	public ModelAndView fdApprovedList(Model model, RedirectAttributes attributes, @RequestParam("menuId") Long menuId)
			throws CustomException {

		ModelAndView mav = new ModelAndView();
		List<DepositForm> depositsList = fixedDepositDao.getAllDepositsList();
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);

		if (depositsList != null && depositsList.size() > 0) {
			model.addAttribute("depositsList", depositsList);

			mav = new ModelAndView("fdApprovedList", "model", model);
		}

		else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	private EndUser getCurrentLoggedUserDetails() {

		EndUser endUser = endUserDAOImpl.findByUsername(getCurrentLoggedUserName()).getSingleResult();

		return endUser;

	}

	public Customer getCustomerDetails(Long id) {
		Customer customer = customerDAO.getById(id);
		return customer;
	}

	/* 30aug */
	@RequestMapping(value = "/customerPayment", method = RequestMethod.GET)
	public ModelAndView payment(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			@RequestParam("menuId") Long menuId, @RequestParam(value = "error", required = false) String error)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("error", error);
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		Role role = endUserDAO.findById(ROLE_ID);
		if (role.getRole().equals("ROLE_USER")) {
			List<Object[]> depositHolderObjList = depositHolderDAO.getOpenDeposit(endUser.getCustomerId());
			if (depositHolderObjList != null && depositHolderObjList.size() > 0) {

				List<DepositHolderForm> depositHolderList = new ArrayList<DepositHolderForm>();

				for (int i = 0; i < depositHolderObjList.size(); i++) {
					if (depositHolderObjList.get(i)[10] != null
							&& depositHolderObjList.get(i)[10].toString().equalsIgnoreCase("AUTO"))
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
					depositform.setDeathCertificateSubmitted((String) depositHolderObjList.get(i)[12] == null ? ""
							: (String) depositHolderObjList.get(i)[12]);

					depositHolderList.add(depositform);
				}

				model.addAttribute("depositHolderList", depositHolderList);

			} else {
				return new ModelAndView("noDataFoundCusm", "model", model);
			}

			return new ModelAndView("fdChangesListForFundTransfer", "model", model);
		}
		return new ModelAndView("fdPayment", "model", model);

	}

	@RequestMapping(value = "/bankPaymentOnMaturity", method = RequestMethod.GET)
	public ModelAndView bankPaymentOnMaturity(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			@RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("bankPaymentOnMaturity", "model", model);

	}

	@RequestMapping(value = "/searchDepositForWithdraw", method = RequestMethod.GET)
	public ModelAndView withDrawFD(Model model, @ModelAttribute WithdrawForm withdrawForm,
			@ModelAttribute DepositForm depositForm, @RequestParam("menuId") Long menuId) throws CustomException {

		model.addAttribute("withdrawForm", withdrawForm);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		return new ModelAndView("withDrawFDSearch", "model", model);

	}

	@RequestMapping(value = "/searchDepositForWithdrawList", method = RequestMethod.GET)
	public ModelAndView withdrawListSearch(Model model, @ModelAttribute WithdrawForm withdrawForm,
			@ModelAttribute DepositForm depositForm, @RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("withdrawForm", withdrawForm);
		return new ModelAndView("withdrawSearch", "model", model);

	}

	@RequestMapping(value = "/issueOverdraft", method = RequestMethod.GET)
	public ModelAndView issueOverdraft(Model model, @ModelAttribute OverdraftForm overdraftForm,
			@RequestParam("menuId") Long menuId) throws CustomException {

		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("todaysDate", date);

		return new ModelAndView("issueingOverdraft", "model", model);

	}

	@RequestMapping(value = "/getOverdraftDetails", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody OverdraftForm getOverdraftDetails(String accountNumber) throws CustomException {
		System.out.println("Arjun-----");
		Deposit deposit = depositHolderDAO.getDepositByAccountNumber(accountNumber);
		System.out.println("productConfigurationId:" + deposit.getProductConfigurationId());
		OverdraftForm overdraftForm = new OverdraftForm();
		System.out.println(deposit.getCurrentBalance());
		overdraftForm.setCurrentBalance(deposit.getCurrentBalance());
		ProductConfiguration productConfiguration = productConfigurationDAO
				.findById(deposit.getProductConfigurationId());
		// System.out.println(productConfiguration.getDefaultOverdraftAmount());
		overdraftForm.setMaximumOverdraftPercentage(productConfiguration.getMaximumOverdraftPercentage());
		overdraftForm.setMinimumOverdraftPercentage(productConfiguration.getMinimumOverdraftPercentage());
		if (productConfiguration.getHigherInterestRate() != null) {
			overdraftForm.setInterestRate(deposit.getInterestRate() + productConfiguration.getHigherInterestRate());
		} else {
			overdraftForm.setInterestRate(deposit.getInterestRate());
		}
		if (productConfiguration.getIsOverdraftFacilityAvailable() == null) {

			overdraftForm = new OverdraftForm();
		}
		if (productConfiguration.getIsOverdraftFacilityAvailable() != null) {

			if (productConfiguration.getIsOverdraftFacilityAvailable() == 0) {

				overdraftForm = new OverdraftForm();
			}
		}
		return overdraftForm;
	}

	@RequestMapping(value = "/overdraftIssue", method = RequestMethod.POST)
	public ModelAndView saveoverdraftIssue(Model model, @ModelAttribute("overdraftForm") OverdraftForm overdraftForm,
			@RequestParam("menuId") Long menuId, RedirectAttributes attributes) {
		try {
			model = this.menuUtils(model, menuId);

			Boolean isPermission = this.checkPermissions(menuId, "WRITE");
			if (isPermission) {

				OverdraftIssue overdraftIssue = new OverdraftIssue();
				Deposit deposit = depositHolderDAO.getDepositByAccountNumber(overdraftForm.getAccountNumber());
				ProductConfiguration productConfiguration = productConfigurationDAO
						.findById(deposit.getProductConfigurationId());
				if (productConfiguration == null) {
					productConfiguration = productConfigurationDAO
							.findByProductCode(deposit.getProductConfigurationId().toString());
				}
				Float rInterest = 0f;
				if (productConfiguration.getHigherInterestRate() != null) {
					rInterest = productConfiguration.getHigherInterestRate() + deposit.getInterestRate();
				} else {
					rInterest = deposit.getInterestRate();
				}
				Date date = overdraftForm.getIssueDate();
				Date overDraftEndDate = overdraftForm.getIssueDate();
				if (overdraftForm.getTenureInYears() != null) {
					overDraftEndDate = DateService.addYear(overDraftEndDate, overdraftForm.getTenureInYears());
				}

				if (overdraftForm.getTenureInDays() != null) {
					overDraftEndDate = DateService.addDays(overDraftEndDate, overdraftForm.getTenureInDays());
				}

				if (overdraftForm.getTenureInMonths() != null) {
					overDraftEndDate = DateService.addMonths(overDraftEndDate, overdraftForm.getTenureInMonths());
				}
				Integer i = DateService.compareDate(deposit.getMaturityDate(), overDraftEndDate);
				if (i > 0) {
					deposit.setMaturityInstruction("autoRenew");
					fixedDepositDao.updateFD(deposit);
				}
				Integer days = DateService.getDaysBetweenTwoDates(date, overDraftEndDate);
				// Double amountToReturn=0d;
				if (overdraftForm.getDefaultOverdraftPercentage() != null) {
					Double amount = (deposit.getCurrentBalance() * overdraftForm.getDefaultOverdraftPercentage()) / 100;
					// amountToReturn= amount+(((amount*rInterest)/(36500))*days);
					overdraftIssue.setSanctionedAmount(amount);
					overdraftIssue.setWithdrawableAmount(amount);
				} else {
					overdraftIssue.setSanctionedAmount(overdraftForm.getDefaultOverdraftAmount());
					overdraftIssue.setWithdrawableAmount(overdraftForm.getDefaultOverdraftAmount());
					// amountToReturn=
					// overdraftForm.getDefaultOverdraftAmount()+(((overdraftForm.getDefaultOverdraftAmount()*rInterest)/(36500))*days);
				}
				// overdraftIssue.setAmountToReturn(amountToReturn);
				overdraftIssue.setInterestRate(rInterest);
				overdraftIssue.setOverdraftEndDate(overDraftEndDate);
				overdraftIssue.setDepositId(deposit.getId());
				overdraftIssue.setIssueDate(overdraftForm.getIssueDate());

				overdraftIssue.setOverdraftNumber(fdService.generateRandomStringOD());
				if (overdraftForm.getDefaultOverdraftPercentage() != null) {
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
			} else {
				model.addAttribute("error", "You do not have sufficient permissions !");
				SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
				String date = smt.format(DateService.getCurrentDateTime());

				model.addAttribute("todaysDate", date);

				return new ModelAndView("issueingOverdraft", "model", model);

			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		attributes = updateTransaction("Saved Successfully", attributes);
		return new ModelAndView("redirect:successfullySaved");
		// return new ModelAndView("issueingOverdraft", "model", model);

	}

	@RequestMapping(value = "/withdrawFromOverdraft", method = RequestMethod.GET)
	public ModelAndView withdrawOverdraft(Model model, @ModelAttribute DepositForm depositForm,
			@RequestParam("menuId") Long menuId) throws CustomException {

		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		model.addAttribute("todaysDate", date);
		model.addAttribute("depositForm", depositForm);
		model = this.menuUtils(model, menuId);
		return new ModelAndView("withdrawOverdraft", "model", model);

	}

	@RequestMapping(value = "/paymentToOverdraft", method = RequestMethod.GET)
	public ModelAndView paymentOverdraft(Model model, @ModelAttribute DepositForm depositForm,
			@RequestParam("menuId") Long menuId) throws CustomException {

		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		List<ModeOfPayment> paymentList = modeOfPaymentDAO.getAllModeOfPaymentEmployee();
		model.addAttribute("paymentMode", paymentList);
		model.addAttribute("todaysDate", date);
		model.addAttribute("depositForm", depositForm);
		model = this.menuUtils(model, menuId);
		return new ModelAndView("paymentOverdraft", "model", model);

	}

	@RequestMapping(value = "/transactionReportList", method = RequestMethod.GET)
	public ModelAndView depositLists(Model model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm, @RequestParam("menuId") Long menuId) throws CustomException {

		ModelAndView mav = new ModelAndView();
		model = this.menuUtils(model, menuId);
		List<DepositForm> depositLists = fixedDepositDao.getAllRegularAndAutoDepositsList();

		if (depositLists != null && depositLists.size() > 0) {
			depositForm.setDepositId(depositLists.get(0).getDepositId());
			model.addAttribute("depositLists", depositLists);
			mav = new ModelAndView("bankfdChangesLists", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/sweepDepositList", method = RequestMethod.GET)
	public ModelAndView sweepDepositList(Model model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm, @RequestParam("menuId") Long menuId) throws CustomException {
		ModelAndView mav = null;
		model = this.menuUtils(model, menuId);
		List<AutoDepositForm> autoDepositLists = fixedDepositDao.getAutoDeposits();
		if (autoDepositLists.size() > 0) {
			model.addAttribute("depositLists", autoDepositLists);
			mav = new ModelAndView("autoDepositList", "model", model);
		}

		else {
			mav = new ModelAndView("noDataFound", "model", model);

		}
		return mav;
	}

	@RequestMapping(value = "/getTenureForReverseEMI", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Double tenureCalculation(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			Double fdAmount, Double emiAmt) {

		double emiFrequency = Math.floor(fdAmount / emiAmt);
		return emiFrequency;

	}

	@RequestMapping(value = "/getHolderDetails", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<DepositForm> getHolderDetails(String accountNumber) throws CustomException {

		List<DepositForm> depositList = paymentDAO.withdrawAccountNumber(accountNumber);

		return depositList;

	}

	@RequestMapping(value = "/getOverdraftIssueDetails", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody OverdraftIssue getOverdraftIssueDetails(String overdraftNumber) throws CustomException {

		OverdraftIssue overdraftDetails = overdraftIssueDAO.getOverdraftIssueDetails(overdraftNumber);
		if (overdraftDetails.getSanctionedPercentage() == null) {
			Double withdrawableAmount = overdraftDetails.getWithdrawableAmount();
			Double withdrawnAmount = overdraftDetails.getTotalAmountWithdrawn();

			overdraftDetails.setWithdrawableAmount(withdrawableAmount - withdrawnAmount);
		} else {

			Deposit deposit = fixedDepositDao.findByDepositId(overdraftDetails.getDepositId());
			Double withdrawableAmount = (overdraftDetails.getSanctionedPercentage() * deposit.getCurrentBalance())
					/ 100;
			Double withdrawnAmount = overdraftDetails.getTotalAmountWithdrawn();

			overdraftDetails.setWithdrawableAmount(withdrawableAmount - withdrawnAmount);
		}
		return overdraftDetails;

	}

	@RequestMapping(value = "/findClosedDeposit", method = RequestMethod.POST)
	public ModelAndView findCloseAccountBankPaymentOnMaturity(Model model,
			@ModelAttribute FixedDepositForm fixedDepositForm, Long menuId) throws CustomException {

		Deposit deposit = fixedDepositDao.getByAccountNumberAndStatusClose(fixedDepositForm.getAccountNo().trim());

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);

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

	@RequestMapping(value = "/depositPayment", method = RequestMethod.POST)
	public ModelAndView depositPayment(Model model, @ModelAttribute FixedDepositForm fixedDepositForm, Long menuId)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		Deposit deposit = fixedDepositDao.getByAccountNumber(fixedDepositForm.getAccountNo().trim());
		if (deposit == null) {
			model.addAttribute("error", "Account Number is not Correct OR this deposit has been closed.");
			return new ModelAndView("fdPayment", "model", model);
		}

		ProductConfiguration productConfiguration = productConfigurationDAO
				.findById(deposit.getProductConfigurationId());

		List<DepositForm> depositList = paymentDAO.paymentAccountNumber(fixedDepositForm.getAccountNo().trim());

		if (depositList.size() > 0) {
			String depositCategory = depositList.get(0).getDepositCategory();

			String depositClassification = depositList.get(0).getDepositClassification();
			if (depositClassification != null && depositClassification.equals("Tax Saving Deposit")) {
				model.addAttribute("taxError", "Sorry you can not make payment for Tax Saving Deposit");
				return new ModelAndView("fdPayment", "model", model);
			}
			if (depositCategory != null && depositCategory.equals("AUTO")) {
				model.addAttribute("error", "Sorry you can not make payment for Auto/Sweep Deposit");
				return new ModelAndView("fdPayment", "model", model);
			}
			fixedDepositForm.setProductConfigurationId(deposit.getProductConfigurationId());
			List<ModeOfPayment> paymentMode = modeOfPaymentDAO.getAllModeOfPaymentEmployee();
			model.addAttribute("paymentMode", paymentMode);
			model.addAttribute("payAndWithdrawTenure", productConfiguration.getPreventionOfTopUpBeforeMaturity());
			model.addAttribute("fixedDepositForm", fixedDepositForm);
			model.addAttribute("depositList", depositList);

			model.addAttribute("productConfigurationId", deposit.getProductConfigurationId());

			return new ModelAndView("fdPayment", "model", model);
		}

		else {
			model.addAttribute("error", "Account Number is not Correct OR this deposit has been closed.");
			return new ModelAndView("fdPayment", "model", model);
		}

	}

	@RequestMapping(value = "/withdrawPaymentList", method = RequestMethod.POST)
	public ModelAndView withdrawPaymentList(Model model, @ModelAttribute DepositForm depositForm,
			@ModelAttribute WithdrawForm withdrawForm, Long menuId) throws CustomException {

		ProductConfiguration _pc = null;
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		List<DepositForm> deposit = paymentDAO.paymentAccountNumber(depositForm.getAccountNumber().trim());
		List<ModeOfPayment> paymentList = modeOfPaymentDAO.getAllModeOfPaymentEmployee();
		if (deposit.size() > 0) {
			if (deposit.get(0).getDepositClassification() != null
					&& deposit.get(0).getDepositClassification().equals(Constants.taxSavingDeposit)) {
				model.addAttribute(Constants.ERROR, Constants.withdrawError);
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

				model.addAttribute(Constants.ERROR, "Withdraw is not allowed for this deposit. you can not withdraw.");
				return new ModelAndView("withDrawFDSearch", "model", model);

				/*
				 * model.addAttribute(Constants.ERROR, Constants.withdrawLockingPeriodError);
				 * return new ModelAndView("withDrawFDSearch", "model", model);
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
					model.addAttribute(Constants.ERROR, Constants.withdrawLockingPeriodError);
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
				model.addAttribute("payAndWithdrawTenure", _pc.getPreventionOfWithdrawBeforeMaturity());
				depositForm.setEmail(deposit.get(0).getEmail());
				model.addAttribute("depositForm", depositForm);
				model.addAttribute("deposit", deposit);
			} else if (depositCategory.equals("AUTO")) {
				model.addAttribute("error", "Sorry you can not withdraw amount from Auto/Sweep Deposit");

			}

			else if (depositCategory.equals("REVERSE-EMI")) {
				model.addAttribute("error", "Sorry you can not withdraw amount from REVERSE-EMI Deposit");
			}

		} else {
			model.addAttribute("error", "Account Number is not Correct");

		}
		model.addAttribute("paymentMode", paymentList);
		return new ModelAndView("withDrawFDSearch", "model", model);
		// return new ModelAndView("withdrawPaymentList", "model", model);

	}

	@RequestMapping(value = "/withdrawList", method = RequestMethod.POST)

	public ModelAndView withdrawList(Model model, @ModelAttribute DepositForm depositForm) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);
		Deposit deposit = fixedDepositDao.getByAccountNumber(depositForm.getAccountNumber().trim());
		if (deposit == null) {
			model.addAttribute(Constants.ERROR, "Incorrect Account Number");
			return new ModelAndView("withdrawSearch", "model", model);
		}
		List<WithdrawDeposit> withdrawList = withdrawDepositDAO.withdrawDepositListByDepositId(deposit.getId());

		if (withdrawList.size() > 0) {

			model.addAttribute("customerName", depositForm.getCustomerName());
			model.addAttribute("withdrawList", withdrawList);

		} else {
			model.addAttribute(Constants.ERROR, Constants.WITHDRAWLISTERROR);
			return new ModelAndView("noDataFound", "model", model);

		}
		return new ModelAndView("withdrawList", "model", model);
	}

	@RequestMapping(value = "/editCustomer", method = RequestMethod.GET)
	public ModelAndView editCustomer(Model model, Long id, Long menuId, @ModelAttribute CustomerForm customerForm)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		CustomerForm accDetails = accountDetailsDAO.editCustomerDetails(id);
		List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
		Set<CustomerCategory> setCategory = new HashSet<CustomerCategory>(list);

		if (setCategory.size() > 0) {

			model.addAttribute("setCategory", setCategory);
			model.addAttribute("addRateForm", addRateForm);
			model.addAttribute("list", list);

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
		model.addAttribute("accDetails", accDetails);
		model.addAttribute("setCategory", setCategory);
		return new ModelAndView("editCustomer", "model", model);

	}

	@Transactional
	@RequestMapping(value = "/editcustomerPost", method = RequestMethod.POST)
	public ModelAndView editcustomerPost(Model model, @ModelAttribute CustomerForm customerForm,
			RedirectAttributes attribute) throws ParseException, CustomException {
		/*
		 * Long getcustomerCountByPanCardEdit = customerDAO
		 * .getCustomerByPanCardForEdit(customerForm.getPan().toUpperCase(),
		 * customerForm.getId()); Long getcustomerCountByAadharCardEdit =
		 * customerDAO.getCustomerByAadharCardForEdit(customerForm.getAadhar(),
		 * customerForm.getId()); if (getcustomerCountByPanCardEdit > 0) {
		 * model.addAttribute("errorPan", Constants.PanCardExist); return new
		 * ModelAndView("redirect:editCustomer?id=" + customerForm.getId()); }
		 * 
		 * if (getcustomerCountByAadharCardEdit > 0) { model.addAttribute("errorAadhar",
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
			model.addAttribute("error", "Selected Date Of Birth is future date please insert correct date");
			return new ModelAndView("editCustomer", "model", model);
		}
		model.addAttribute("date1", date1);
		model.addAttribute("date2", date2);
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
					accDetails.setMinimumBalance(Double.valueOf(minBal[i]));
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
		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "/viewCustomersSavingAccount", method = RequestMethod.GET)
	public ModelAndView allCustomersSaving(Model model, @RequestParam("menuId") Long menuId) throws CustomException {

		Collection<Customer> cusList = customerDAO.findCustomers();
		model.addAttribute("cusList", cusList);
		model = this.menuUtils(model, menuId);

		model.addAttribute("menuId", menuId);
		return new ModelAndView("allCustomersSaving", "model", model);

	}

	@RequestMapping(value = "/showSavingAccount", method = RequestMethod.GET)
	public ModelAndView showSavingAccount(Model model, Long id, Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		List<AccountDetails> accDetails = accountDetailsDAO.findCurrentAndSavingAndDepositByCustomerIdAndIsActive(id);
		for (AccountDetails accountDetails : accDetails) {
			if (accountDetails.getAccountType().equalsIgnoreCase("Savings")) {
				model.addAttribute("accDetails", accountDetails);
				break;
			} else {
				model.addAttribute("accDetails", accountDetails);
				break;
			}

		}

		model.addAttribute("accountDetailsForm", accountDetailsForm);
		return new ModelAndView("showSavingAccount", "model", model);
	}

	@RequestMapping(value = "/addSavingBalance", method = RequestMethod.POST)
	public ModelAndView showSavingAccount(Model model, @ModelAttribute AccountDetailsForm accountDetailsForm,
			RedirectAttributes attributes) throws CustomException {

		AccountDetails accDetails = accountDetailsDAO.findById(accountDetailsForm.getId());
		Double totalAccBalance = accDetails.getAccountBalance() + accountDetailsForm.getAccountBalance2();

		accDetails.setAccountBalance(totalAccBalance);

		accountDetailsDAO.updateUserAccountDetails(accDetails);
		model.addAttribute("accDetails", accDetails);
		attributes = updateTransaction("Amount added successfully", attributes);
		String fromAccountNo = "";
		String toAccountNo = accDetails.getAccountNo();
		String transactionId = fdService.generateRandomString();
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment("Cash");
		ledgerService.insertJournal(null, accDetails.getCustomerID(), DateService.getCurrentDate(), fromAccountNo,
				toAccountNo, Event.ADD_AMOUNT_SAVING.getValue(), accountDetailsForm.getAccountBalance2(), mop.getId(),
				null, transactionId);

		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "/SearchDepositForDeathCertificateSubmission", method = RequestMethod.GET)
	public ModelAndView payment(Model model, RedirectAttributes attributes, @ModelAttribute DepositForm depositForm,
			@RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("depositForm", depositForm);
		return new ModelAndView("deathIssue", "model", model);

	}

	@RequestMapping(value = "/deathIssueSearchPerson", method = RequestMethod.POST)
	public ModelAndView deathIssueSearchPerson(Model model, @ModelAttribute DepositForm depositForm)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);

		List<DepositForm> depositList = paymentDAO.paymentAccountNumber(depositForm.getAccountNumber());
		if (depositList.size() > 0) {
			model.addAttribute("depositForm", depositForm);
			model.addAttribute("depositList", depositList);
		} else {
			model.addAttribute("error", "Please Insert Correct Account Number");
		}
		return new ModelAndView("deathIssue", "model", model);
	}

	@RequestMapping("/savedDeathCertificate")
	public ModelAndView savedDeathCertificate(Model model, @ModelAttribute DepositForm depositForm,
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
			model.addAttribute("error", "Please select the file for Death Certificate");
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
		return new ModelAndView("redirect:successfullySaved");
	}

	@RequestMapping(value = "/searchForDeathCustomer", method = RequestMethod.GET)
	public ModelAndView searchDeathCustomer(Model model, String depositType, @RequestParam("menuId") Long menuId)
			throws CustomException {

		model.addAttribute("fixedDepositForm", fixedDepositForm);
		model = this.menuUtils(model, menuId);
		return new ModelAndView("searchDeathCustomer", "model", model);

	}

	@RequestMapping(value = "/deathCustomerList", method = RequestMethod.POST)
	public ModelAndView deathCustomerList(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String depositType, Long menuId) throws CustomException {
		String cusId = fixedDepositForm.getCustomerID();
		String cusName = fixedDepositForm.getCustomerName();
		String cusNum = fixedDepositForm.getContactNum();
		String cusEmail = fixedDepositForm.getEmail();

		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);
		model.addAttribute("customerList", customerList);
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		return new ModelAndView("deathCustomerList", "model", model);

	}

	@RequestMapping(value = "/depositSummary", method = RequestMethod.GET)
	public ModelAndView depositSummary(Model model, @ModelAttribute DepositForm depositForm, String account)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("depositForm", depositForm);
		model.addAttribute("account", account);
		return new ModelAndView("depositSummary", "model", model);

	}

	@RequestMapping(value = "/searchJournal", method = RequestMethod.GET)
	public ModelAndView ledgerReportSummary(Model model, @ModelAttribute LedgerReportForm ledgerReportForm,
			String account) throws CustomException {
		model.addAttribute("account", account);
		model.addAttribute("ledgerReportForm", ledgerReportForm);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		return new ModelAndView("searchJournal", "model", model);

	}

	@RequestMapping(value = "/ledgerSummaryOfDeposit", method = RequestMethod.GET)
	public ModelAndView ledgerSummaryOfDeposit(Model model, @ModelAttribute LedgerReportForm ledgerReportForm,
			String account, @RequestParam("menuId") Long menuId) throws CustomException {
		model.addAttribute("account", account);
		model.addAttribute("ledgerReportForm", ledgerReportForm);
		model = this.menuUtils(model, menuId);
		return new ModelAndView("searchJournal", "model", model);

	}

	/* , String fdAccountNo, Date fromDate, Date toDate */

	@RequestMapping(value = "/journalList", method = RequestMethod.GET)
	public ModelAndView journalList(Model model, @ModelAttribute LedgerReportForm ledgerReportForm)
			throws CustomException {

		Date fromDate = ledgerReportForm.getFromDate();
		Date toDate = ledgerReportForm.getToDate();
		String fdAccountNumber = ledgerReportForm.getFdAccountNo();
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		Deposit deposit = fixedDepositDao.getByAccountNumber(fdAccountNumber);
		if (deposit == null) {
			model.addAttribute(Constants.ERROR, "Please Insert correct account number.");
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

			journalList = fixedDepositDao.getJournalListByFdAccNumberAndFromAndToDate(deposit.getId(), fromDate,
					toDate);
		} else {

			model.addAttribute(Constants.ERROR, "Please Insert the date.");
			return new ModelAndView("searchJournal", "model", model);
		}

		if (journalList == null) {
			return new ModelAndView("noDataFound", "model", model);
		}
		model.addAttribute("journalList", journalList);
		model.addAttribute("ledgerReportForm", ledgerReportForm);

		return new ModelAndView("searchJournal", "model", model);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/showDepositDetails", method = RequestMethod.POST)
	public ModelAndView showDepositDetails(Model model, @ModelAttribute DepositForm depositForm) throws CustomException

	{
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
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
			Deposit deposit = fixedDepositDao.getByAccountNumber(accountNumber);
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
					model.addAttribute("totInterestAmount", totInterestAmount);
				}

			}

			else {

				totInterestAmount = 0.0;
				model.addAttribute("totInterestAmount", totInterestAmount);
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

				model.addAttribute("dueDate", dueDate);
				model.addAttribute("maturityDate", maturityDate);
				model.addAttribute("createdDate", createdDate);
				model.addAttribute("id", id);
				model.addAttribute("accNumber", accNumber);
				model.addAttribute("depositAmount", depositAmount);
				model.addAttribute("holderStatus", holderStatus);
				model.addAttribute("contribution", contribution);
				model.addAttribute("currentBal", currentBal);
				model.addAttribute("expectedMaturity", expectedMaturity);
				model.addAttribute("depositCategory", depositCategory);
				model.addAttribute("nextPayOffDate", payOffDueDate);
				model.addAttribute("interestRate", interestRate);
				model.addAttribute("gestationPeriod", gestationPeriod);
				model.addAttribute("gestationEndDate", gestationEndDate);
			}

			/* Payment Details */
			if (lastPayment != null) {
				lastAmtPaid = lastPayment.getAmountPaid();
				accountNumberForPay = lastPayment.getAccountNumber();
				modeOfPay = lastPayment.getPaymentMode();
				model.addAttribute("createdBy", lastPayment.getCreatedBy());
				model.addAttribute("accountNumberForPay", accountNumberForPay);
				model.addAttribute("lastAmtPaid", lastAmtPaid);
				model.addAttribute("lastPaymentDate", lastPayment.getPaymentDate());

				model.addAttribute("modeOfPay", modeOfPay);
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
						model.addAttribute("nomineeError", "No Nominee Required for This Deposit");
					}

				}

				else {
					model.addAttribute("nomineeError", "No Nominee for the Deposit");
				}
				totalInterestList.add(depositFormForInterest);

			}

			List<PayoffDetails> payOffDistributionDetails = payOffDAO.payOffDetailsByDepositHolderId(depositHolderId);
			if (payOffDistributionDetails != null && payOffDistributionDetails.size() > 0) {
				model.addAttribute("payOffDistributionDetails", payOffDistributionDetails);

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
			 * model.addAttribute("sumOffPayoffactualamount", sumOffPayoffactualamount); }
			 * if (payOff.size() > 0 && payOff != null) { lastPayoffDistributionDate =
			 * payOff.get(0).getPayOffDistributionDate(); lastPayOffActualAmount =
			 * payOff.get(0).getPayOffActualAmount(); payOffType =
			 * payOff.get(0).getPayOffType(); payOffAccountNumber =
			 * payOff.get(0).getPayOffAccountNumber(); lastPayOffInterestPercent =
			 * payOff.get(0).getPayOffInterestPercent();
			 * model.addAttribute("lastPayoffDistributionDate", lastPayoffDistributionDate);
			 * model.addAttribute("lastPayOffActualAmount", lastPayOffActualAmount);
			 * model.addAttribute("lastpayOffType", payOffType);
			 * model.addAttribute("lastPayOffInterestPercent", lastPayOffInterestPercent);
			 * model.addAttribute("payOff", payOff);
			 * 
			 * }
			 * 
			 * else {
			 * 
			 * model.addAttribute(Constants.payOferror, Constants.payOffError); }
			 * 
			 */
			/*
			 * To get the reverese Emi details from deposit and depositholder table join
			 * with depositid and find with customerid
			 */
			List<DepositHolderForm> reverseDepositDetails = depositHolderDAO.getReverseEmiDepositByDepositId(depositId);
			if (reverseDepositDetails.size() > 0 && reverseDepositDetails != null) {
				model.addAttribute("reverseDepositDetails", reverseDepositDetails);

			} else {
				model.addAttribute(Constants.reverseEmiError, Constants.reverseEmiErrorMsg);
			}

			/*
			 * To get the reverese Emi benificiary details from benificiarydetails table
			 * join with depositid and find with customerid
			 */
			List<DepositHolderForm> benificiaryDetails = benificiaryDetailsDAO
					.getReverseEmiBenificiaryDetailsByDepositId(depositId);
			if (benificiaryDetails.size() > 0 && benificiaryDetails != null) {
				model.addAttribute("benificiaryDetails", benificiaryDetails);

			} else {
				model.addAttribute(Constants.benificiaryError, Constants.benificiaryErrorMsg);
			}
			model.addAttribute("totalInterestList", totalInterestList);

			model.addAttribute("interestAccrued", interestAccrued);

			model.addAttribute("nomineeList", nomineeList);

			if (depositList.size() > 0) {
				model.addAttribute("depositList", depositList);
			}

			mav = new ModelAndView("showDepositDetailsForBankEmp", "model", model);
			return mav;
		} else {
			model.addAttribute("error", "Account Number is not Correct");
			return new ModelAndView("depositSummary", "model", model);

		}

	}

	@RequestMapping(value = "/viewLedger", method = RequestMethod.GET)
	public ModelAndView viewLedger(Model model, @ModelAttribute LedgerReportForm ledgerReportForm, String fdAccountNo)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

		Deposit deposit = fixedDepositDao.getByAccountNumber(fdAccountNo);
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
				model.addAttribute(Constants.ERROR, "Please Insert the date.");
				return new ModelAndView("searchJournal", "model", model);
			}
		}

		if (deposit == null && fdAccountNo != null) {
			model.addAttribute(Constants.ERROR, "Please Insert correct account number.");
			return new ModelAndView("searchJournal", "model", model);
		}
		model.addAttribute("ledgerReportForm", ledgerReportForm);

		return new ModelAndView("viewLedger", "model", model);
	}

	@RequestMapping(value = "/viewDeposit", method = RequestMethod.GET)
	public ModelAndView editFDBank(@RequestParam Long id, Model model, HttpServletRequest request,
			RedirectAttributes attributes, String page, Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("page", page);
		model.addAttribute("menuId", menuId);

		ModelAndView mav;
		HolderForm holderForm = new HolderForm();

		// mav = new ModelAndView("editReverseEMIBank", "model", model);

		List<HolderForm> depositList = depositService.getDepositByDepositId(id);
		if (depositList != null && depositList.size() > 0)
			holderForm = depositList.get(0);
		model.addAttribute("depositHolderList", depositList);
		List<AccountDetails> accountList = accountDetailsDAO
				.findCurrentSavingByCustId(holderForm.getDepositHolder().getCustomerId());
		model.addAttribute("accountList", accountList);
		mav = new ModelAndView("viewDepositBank", "model", model);

		model.addAttribute("depositHolderForm", holderForm);
		model.addAttribute("depositClassification", holderForm.getDeposit().getDepositClassification());

		return mav;

	}

	@RequestMapping(value = "/fdPaymentDetails", method = RequestMethod.POST)
	public ModelAndView fdPaymentDetails(Model model, @ModelAttribute FixedDepositForm fixedDepositForm, Long menuId)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);

		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		List<DepositHolderForm> depositHolderList = depositHolderDAO
				.getDepositHoldersName(fixedDepositForm.getDepositId());

		model.addAttribute("todaysDate", date);
		model.addAttribute("depositHolderList", depositHolderList);
		model.addAttribute("fixedDepositForm", fixedDepositForm);

		ProductConfiguration productConfiguration = productConfigurationDAO
				.findById(fixedDepositForm.getProductConfigurationId());
		model.addAttribute("productConfiguration", productConfiguration);
		/*
		 * model.addAttribute("maxTopUpAmount", productConfiguration == null ? null :
		 * productConfiguration.getMinimumTopupAmount());
		 * model.addAttribute("minDepositAmount", productConfiguration == null ? null :
		 * productConfiguration.getMinimumDepositAmount());
		 */

		Deposit deposit = fixedDepositDao.getDeposit(fixedDepositForm.getDepositId());
		if (productConfiguration.getIsTopupAllowed() != null) {
			if (productConfiguration.getIsTopupAllowed() == 1) {

				String lockingPeriodForTopup = productConfiguration.getLockingPeriodForTopup();
				if (lockingPeriodForTopup != null && lockingPeriodForTopup.trim().equalsIgnoreCase(",,")) {
					model.addAttribute("lockingPeriod", false);
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
						model.addAttribute("lockingPeriod", true);

					} else {
						model.addAttribute("lockingPeriod", false);
					}
				}
			} else {
				model.addAttribute("lockingPeriod", false);
			}
			// if(productConfiguration.getIsTopupAllowed() == 1)
			// model.addAttribute("lockingPeriod",
			// true); else model.addAttribute("lockingPeriod", false);

		}
		ModeOfPayment paymentMode = modeOfPaymentDAO
				.getModeOfPaymentById(Long.parseLong(fixedDepositForm.getPaymentMode()));
		model.addAttribute("paymentMode", paymentMode.getPaymentMode());
		return new ModelAndView("fdPaymentDetails", "model", model);

	}

	@RequestMapping(value = "/fdPaymentDetailsConfirm", method = { RequestMethod.POST })
	public ModelAndView fdCashDataConfirm(Model model, @ModelAttribute DepositForm depositForm,
			@ModelAttribute FixedDepositForm fixedDepositForm, Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);

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
		model.addAttribute("productConfiguration", productConfiguration);
		model.addAttribute("depositHolderList", depositHolderList);
		model.addAttribute("todaysDate", date);
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		ModeOfPayment paymentMode = modeOfPaymentDAO
				.getModeOfPaymentById(Long.parseLong(fixedDepositForm.getPaymentMode()));
		model.addAttribute("paymentMode", paymentMode.getPaymentMode());
		return new ModelAndView("fdPaymentDetailsConfirm", "model", model);

	}

	@Transactional
	@RequestMapping(value = "/savePayment", method = RequestMethod.POST)
	public String savePayment(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes, @RequestParam("menuId") String menuId) throws CustomException {
		String errorMessage = "";
		try {
			String transactionId = depositService.doPayment(fixedDepositForm, getCurrentLoggedUserName());
			if (transactionId != "") {
				Date curDate = DateService.loginDate;

				attributes.addFlashAttribute(Constants.TRANSACTIONID, transactionId);
				attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
				attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Payment Successful");

				// attributes = updateTransaction("Payment Successful", attributes);
				return "redirect:successfullySaved";
			} else
				throw new CustomException("Payment failed, try again.");
		} catch (CustomException e) {
			errorMessage = e.getErrorMessage();
			attributes.addFlashAttribute("error", e.getErrorMessage());
		}
		return "redirect:customerPayment?menuId=" + menuId + "&error=" + errorMessage;

	}

	@RequestMapping(value = "/withdrawFdUser", method = RequestMethod.POST)
	public ModelAndView withdrawFdUser(Model model, @ModelAttribute WithdrawForm withdrawForm, Long menuId,
			@ModelAttribute DepositForm depositForm, RedirectAttributes redirectAttributes) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);

		Distribution paymentDistribution = fixedDepositDao.withdrawDepositAmt(depositForm.getDepositId());
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
		model.addAttribute("todaysDate", date);
		model.addAttribute("depositHolderList", depositHolderList);
		model.addAttribute("withdrawForm", withdrawForm);
		return new ModelAndView("fdWithdraw", "model", model);

	}

	@RequestMapping(value = "/confirmWithdrawFdUser", method = RequestMethod.POST)

	public ModelAndView confirmWithdrawFdUser(Model model, @ModelAttribute WithdrawForm withdrawForm,
			DepositForm depositForm, @RequestParam String accountNum, Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

		String accNo = accountNum != null ? accountNum : null;
		Distribution paymentDistribution = fixedDepositDao.withdrawDepositAmt(depositForm.getDepositId());
		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(withdrawForm.getCustomerId());
		withdrawForm.setAccountList(accountList);
		withdrawForm.setCustomerId(depositForm.getCustomerId());
		withdrawForm.setCompoundVariableAmt(paymentDistribution.getCompoundVariableAmt());
		withdrawForm.setCompoundFixedAmt(paymentDistribution.getCompoundFixedAmt());
		withdrawForm.setDepositHolderId(depositForm.getDepositHolderId());
		withdrawForm.setCustomerName(depositForm.getCustomerName());
		withdrawForm.setEmail(depositForm.getEmail());
		List<DepositHolderForm> depositHolderList = depositHolderDAO.getDepositHoldersName(depositForm.getDepositId());
		model.addAttribute("accNo", accNo);
		model.addAttribute("withdrawForm", withdrawForm);
		model.addAttribute("depositHolderList", depositHolderList);
		return new ModelAndView("confirmWithdrawFdUser", "model", model);

	}

	@Transactional
	@RequestMapping(value = "/saveWithdrAmmount", method = RequestMethod.POST)
	public String saveWithdrAmmount(Model model, @ModelAttribute WithdrawForm withdrawForm,
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
		return "redirect:successfullySaved";

	}

	@RequestMapping(value = "/withdrawPaymentLists", method = RequestMethod.GET)
	public ModelAndView withdrawPaymentLists(Model model, @RequestParam(value = "accountNumber") String accountNumber,
			@RequestParam(value = "customerId") Long customerId) throws CustomException {
		ProductConfiguration _pc = null;
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

		List<DepositForm> deposit = paymentDAO.paymentAccountNumber(accountNumber.trim());

		if (deposit.size() > 0) {
			if (deposit.get(0).getDepositClassification() != null
					&& deposit.get(0).getDepositClassification().equals(Constants.taxSavingDeposit)) {
				model.addAttribute(Constants.ERROR, Constants.withdrawError);
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

				model.addAttribute(Constants.ERROR, "Withdraw is not allowed for this deposit. you can not withdraw.");
				return new ModelAndView("withDrawFDSearch", "model", model);

				/*
				 * model.addAttribute(Constants.ERROR, Constants.withdrawLockingPeriodError);
				 * return new ModelAndView("withDrawFDSearch", "model", model);
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
					model.addAttribute(Constants.ERROR, Constants.withdrawLockingPeriodError);
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
				model.addAttribute("payAndWithdrawTenure", _pc.getPreventionOfWithdrawBeforeMaturity());
				depositForm.setEmail(deposit.get(0).getEmail());
				depositForm.setAccountNumber(accountNumber);
				model.addAttribute("depositForm", depositForm);
				model.addAttribute("deposit", deposit);
			} else if (depositCategory.equals("AUTO")) {
				model.addAttribute("error", "Sorry you can not withdraw amount from Auto/Sweep Deposit");

			}

			else if (depositCategory.equals("REVERSE-EMI")) {
				model.addAttribute("error", "Sorry you can not withdraw amount from REVERSE-EMI Deposit");
			}

		} else {
			model.addAttribute("error", "Account Number is not Correct");

		}

		return new ModelAndView("withDrawFDSearch", "model", model);
		// return new ModelAndView("withdrawPaymentList", "model", model);

	}

	@RequestMapping(value = "/getLooseAmountForWithdraw", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Double getLooseAmountForWithdraw(Model model, Long depositId, Double withdrawAmt)
			throws CustomException {
		System.out.println("getLooseAmountForWithdraw");
		System.out.println("DepositId: " + depositId);
		System.out.println("withdrawAmt: " + withdrawAmt);

		Deposit deposit = fixedDepositDao.getDeposit(depositId);
		Double amountToAdjust = fdService.getAmountToLose(deposit, withdrawAmt);

		amountToAdjust = amountToAdjust == null ? 0d : amountToAdjust;
		System.out.println("amountToAdjust: " + amountToAdjust);
		return fdService.round(amountToAdjust, 2);
	}

	@RequestMapping(value = "/ledgerList", method = RequestMethod.GET)
	public ModelAndView ledgerList(Model model, @ModelAttribute LedgerReportForm ledgerReportForm)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		Deposit deposit = fixedDepositDao.getByAccountNumber(ledgerReportForm.getFdAccountNo());
		List<LedgerReportForm> ledgerList = ledgerService.getLedgersOfDeposit(deposit.getId(),
				ledgerReportForm.getFromDate(), ledgerReportForm.getToDate());

		if (ledgerList == null || ledgerList.isEmpty())
			model.addAttribute("ledgerListError", "No Data Found");

		model.addAttribute("ledgerList", ledgerList);
		model.addAttribute("ledgerReportForm", ledgerReportForm);

		return new ModelAndView("ledgerList", "model", model);
	}

	@RequestMapping(value = "/ledgerAllList", method = RequestMethod.GET)
	public ModelAndView ledgerAllList(Model model, @ModelAttribute LedgerReportForm ledgerReportForm,
			@RequestParam("menuId") String menuId) throws CustomException {

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);

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

		Deposit deposit = fixedDepositDao.getByAccountNumber(ledgerReportForm.getFdAccountNo());
		List<LedgerReportForm> ledgerList = ledgerService.getLedgers(ledgerReportForm.getFromDate(),
				ledgerReportForm.getToDate());

		if (ledgerList == null || ledgerList.isEmpty())
			model.addAttribute("ledgerListError", "No Data Found");

		model.addAttribute("ledgerList", ledgerList);
		model.addAttribute("ledgerReportForm", ledgerReportForm);
		// model.addAttribute("menuId", menuId);

		return new ModelAndView("ledgerAllList", "model", model);

	}

	@RequestMapping(value = "/closeFDList", method = RequestMethod.GET)
	public ModelAndView closeFDList(ModelMap model, RedirectAttributes attributes) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		ModelAndView mav = new ModelAndView();

		Collection<Deposit> fixedDeposits = fixedDepositDao.getApprovedDeposits();
		if (fixedDeposits != null && fixedDeposits.size() > 0) {

			model.put("fixedDeposits", fixedDeposits);
			mav = new ModelAndView("bankclosingFDList", "model", model);

		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/closeFD", method = RequestMethod.GET)
	public ModelAndView closeFD(@RequestParam("id") Long id, Model model, RedirectAttributes attributes)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

		Deposit deposit = fixedDepositDao.getDeposit(id);

		if (deposit != null) {
			if (deposit.getDepositCategory() != null && deposit.getDepositCategory().contains("AUTO")) {
				attributes.addFlashAttribute("error", "Pre mature closing is not allowed for Sweep Deposit.");
				return new ModelAndView("redirect:closeFDList");
			}

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
		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "/saveWithdrawOverdraft", method = RequestMethod.POST)
	public ModelAndView saveWithdrawOverdraft(Model model, @ModelAttribute("depositForm") DepositForm depositForm,
			RedirectAttributes attributes) {

		OverdraftWithdrawMaster overdraftWithdrawMaster = new OverdraftWithdrawMaster();

		ModeOfPayment modeOfpayment = modeOfPaymentDAO.getModeOfPayment(depositForm.getPaymentMode());
		overdraftWithdrawMaster.setPaymentModeId(modeOfpayment.getId());
		overdraftWithdrawMaster.setPaymentMode(modeOfpayment.getPaymentMode());
		OverdraftIssue overdraft = overdraftIssueDAO.getOverdraftIssueDetails(depositForm.getOverdraftNumber());
		double amountWithdrawn = overdraft.getTotalAmountWithdrawn() + depositForm.getWithdrawAmount();
		Double amountToReturn = amountWithdrawn - overdraft.getTotalAmountPaid();
		Date createdDate = depositForm.getCreatedDate();
		Integer days = DateService.getDaysBetweenTwoDates(createdDate, overdraft.getOverdraftEndDate());
		amountToReturn = amountToReturn + (((amountToReturn * overdraft.getInterestRate()) / (36500)) * days);
		if (overdraft.getIsEMI() != null) {
			if (overdraft.getIsEMI() == 1) {
				// Date createdDate=depositForm.getCreatedDate();
				LocalDate createdDate1 = depositForm.getCreatedDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				LocalDate newDate1 = createdDate1.plusMonths(1).withDayOfMonth(1);
				Date startDate = java.sql.Date.valueOf(newDate1);

				Calendar startCalendar = new GregorianCalendar();
				startCalendar.setTime(startDate);
				Calendar endCalendar = new GregorianCalendar();
				endCalendar.setTime(overdraft.getOverdraftEndDate());
				int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
				int emiMonths = (diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH))
						+ 1;

				double emiAmount = (amountWithdrawn - overdraft.getTotalAmountPaid()) / emiMonths;
				overdraft.setEMIAmount(emiAmount);
				// overdraftIssueDAO.updateOverdraftIssue(overdraft);
			}
		}

		// amountWithdrawn=amountWithdrawn+depositForm.getWithdrawAmount();
		overdraft.setTotalAmountWithdrawn(amountWithdrawn);
		overdraft.setAmountToReturn(amountToReturn);
		overdraftIssueDAO.updateOverdraftIssue(overdraft);

		overdraftWithdrawMaster.setOverdraftId(overdraft.getId());
		overdraftWithdrawMaster.setWithdrawAmount(depositForm.getWithdrawAmount());

		overdraftWithdrawMaster.setWithdrawDate(depositForm.getCreatedDate());
		overdraftIssueDAO.insertOverdraftWithdrawMaster(overdraftWithdrawMaster);

		// if(modeOfpayment!=null)
		// {
		// ledgerService.insertJournal(null,this.getPrimaryCustomerId(depositHolderList)
		// , DateService.getCurrentDate(),
		// deposits.get(i).getAccountNumber(), deposits.get(i).getAccountNumber(),
		// Event.TDS.getValue(),
		// totalTDSAmountForDeposit,modeOfpayment.getId(),
		// depositSummary.getTotalPrincipal(), null);
		// }

		attributes.addFlashAttribute("depositForm", depositForm);

		attributes = updateTransaction("Saved Successfully", attributes);
		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "/savePaymentOverdraft", method = RequestMethod.POST)
	public ModelAndView savePaymentOverdraft(Model model, @ModelAttribute("depositForm") DepositForm depositForm,
			RedirectAttributes attributes) {

		OverdraftPayment overdraftPayment = new OverdraftPayment();
		overdraftPayment.setPaymentModeId(Long.parseLong(depositForm.getPaymentMode()));

		OverdraftIssue overdraft = overdraftIssueDAO.getOverdraftIssueDetails(depositForm.getOverdraftNumber());
		double amountPaid = depositForm.getDepositAmount() + overdraft.getTotalAmountPaid();
		double amounttoReturn = overdraft.getAmountToReturn() - amountPaid;
		Date paymentDate = depositForm.getCreatedDate();
		if (overdraft.getIsEMI() != null) {
			if (overdraft.getIsEMI() == 1) {

				LocalDate createdDate1 = depositForm.getCreatedDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				LocalDate newDate1 = createdDate1.plusMonths(1).withDayOfMonth(1);
				Date startDate = java.sql.Date.valueOf(newDate1);

				Calendar startCalendar = new GregorianCalendar();
				startCalendar.setTime(startDate);
				Calendar endCalendar = new GregorianCalendar();
				endCalendar.setTime(overdraft.getOverdraftEndDate());
				int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
				int emiMonths = (diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH))
						+ 1;

				double emiAmount = (amounttoReturn) / emiMonths;
				overdraft.setEMIAmount(emiAmount);
				// overdraftIssueDAO.updateOverdraftIssue(overdraft);
			}
		}

		overdraft.setTotalAmountPaid(amountPaid);
		// overdraft.setAmountToReturn(amounttoReturn);
		overdraftIssueDAO.updateOverdraftIssue(overdraft);

		overdraftPayment.setOverdraftId(overdraft.getId());
		overdraftPayment.setDepositId(overdraft.getDepositId());
		double depositAmount = depositForm.getDepositAmount() == null ? 0d : depositForm.getDepositAmount();
		overdraftPayment.setPaymentAmount(depositAmount);
		overdraftPayment.setPaymentDate(paymentDate);
		String transactionId = fdService.generateRandomString();
		overdraftPayment.setTransactionId(transactionId);
		ModeOfPayment paymentMode = modeOfPaymentDAO.getModeOfPaymentById(Long.parseLong(depositForm.getPaymentMode()));
		if (paymentMode.getPaymentMode().contains("Cash") || paymentMode.getPaymentMode().contains("cash")) {

		} else if (paymentMode.getPaymentMode().equalsIgnoreCase("DD")
				|| paymentMode.getPaymentMode().equalsIgnoreCase("Cheque")) {

			overdraftPayment.setChequeDDDate(depositForm.getChequeDate());
			overdraftPayment.setChequeDDNumber(depositForm.getChequeNo());
			overdraftPayment.setBank(depositForm.getChequeBank());
			overdraftPayment.setBranch(depositForm.getChequeBranch());

		} else if (paymentMode.getPaymentMode().equalsIgnoreCase("Credit Card")
				|| paymentMode.getPaymentMode().contains("Debit Card")) {

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

		/*
		 * else if (fixedDepositForm.getDepositForm().getPaymentMode().
		 * equalsIgnoreCase("Net Banking")) {
		 * 
		 * overdraftPayment.setLinkedAccTypeForFundTransfer(fixedDepositForm.
		 * getDepositForm().getAccountType());
		 * overdraftPayment.setLinkedAccNoForFundTransfer(fixedDepositForm.
		 * getDepositForm().getLinkedAccountNo());
		 * overdraftPayment.setTransferType(fixedDepositForm.getDepositForm().
		 * getTransferType());
		 * 
		 * }
		 */

		overdraftIssueDAO.insertOverdraftPayment(overdraftPayment);
		attributes.addFlashAttribute("depositForm", depositForm);

		attributes = updateTransaction("Saved Successfully", attributes);
		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "/sweepDeposit", method = RequestMethod.POST)
	public ModelAndView sweepDeposit(Model model, @ModelAttribute AccountDetailsForm accountDetailsForm,
			RedirectAttributes attributes, Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);

		// Get the bank Configuration for Sweep In
		SweepConfiguration sweepConfig = sweepConfigurationDAO.getActiveSweepConfiguration();
		if (sweepConfig == null) {
			attributes.addFlashAttribute(Constants.ERROR, "Sweep is not Configured By Bank.");
			return new ModelAndView(
					"redirect:showSavingAccount?id=" + accountDetailsForm.getCustomerID() + "&menuId=" + menuId);
		}

		// Check the customer is opted for sweep facility or not
		SweepInFacilityForCustomer sweepfacilityForCustomer = sweepConfigurationDAO
				.getSweepInFacilityForCustomer(accountDetailsForm.getCustomerID());
		if (sweepfacilityForCustomer == null) {
			attributes.addFlashAttribute(Constants.ERROR, "Customer has not opted for Sweep-In facility.");
			return new ModelAndView(
					"redirect:showSavingAccount?id=" + accountDetailsForm.getCustomerID() + "&menuId=" + menuId);
		}
		if (sweepfacilityForCustomer != null && sweepfacilityForCustomer.getIsSweepInConfigureedByCustomer() == 0) {
			attributes.addFlashAttribute(Constants.ERROR, "Customer has not opted for Sweep-In facility.");
			return new ModelAndView(
					"redirect:showSavingAccount?id=" + accountDetailsForm.getCustomerID() + "&menuId=" + menuId);
		}

		if (sweepfacilityForCustomer != null && sweepfacilityForCustomer.getIsSweepInConfigureedByCustomer() == 1
				&& (sweepfacilityForCustomer.getIsSweepInRestrictedByBank() != null
						&& sweepfacilityForCustomer.getIsSweepInRestrictedByBank() == 1)) {
			attributes.addFlashAttribute(Constants.ERROR,
					"Bank has restricted the Sweep-In facility for the customer.");
			return new ModelAndView(
					"redirect:showSavingAccount?id=" + accountDetailsForm.getCustomerID() + "&menuId=" + menuId);
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
			return new ModelAndView(
					"redirect:showSavingAccount?id=" + accountDetailsForm.getCustomerID() + "&menuId=" + menuId);

		}

		// Create sweep deposit
		fdService.createSweepDeposit(accountDetailsForm.getCustomerID(), balanceAmount, sweepConfig,
				sweepfacilityForCustomer, false);

		attributes = updateTransaction("Auto Deposit Created Successfully", attributes);
		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "/allLedgersReport", method = RequestMethod.GET)
	public ModelAndView ledgerAllReportSummary(Model model, @ModelAttribute LedgerReportForm ledgerReportForm,
			String account, @RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		model.addAttribute("account", account);
		model.addAttribute("ledgerReportForm", ledgerReportForm);
		return new ModelAndView("searchAllLedgerByDate", "model", model);

	}

	@RequestMapping(value = "/reportSummary", method = RequestMethod.GET)
	public ModelAndView reportSummary(Model model, @ModelAttribute ReportForm reportForm,
			@RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

		model.addAttribute("reportForm", reportForm);
		return new ModelAndView("reportSummary", "model", model);
	}

	@RequestMapping(value = "/getSummary", method = RequestMethod.POST)
	public ModelAndView getSummary(Model model, @ModelAttribute ReportForm reportForm) throws CustomException {

		List<ReportForm> reportList = fixedDepositDao.getReportSummary(reportForm.getFromDate(),
				reportForm.getToDate());
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("reportForm", reportForm);
		model.addAttribute("reportList", reportList);
		return new ModelAndView("reportSummary", "model", model);
	}

	@RequestMapping(value = "/closedDeposits", method = RequestMethod.GET)
	public ModelAndView closedTransaction(Model model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm, @RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("depositForm", depositForm);
		return new ModelAndView("closedTransaction", "model", model);

	}

	@RequestMapping(value = "/closedTransactionsList", method = RequestMethod.GET)
	public ModelAndView closedTransactionsList(Model model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm, Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
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

			depositLists = fixedDepositDao.getClosedTransactionsList(fromDate, toDate);
		} else {

			model.addAttribute(Constants.ERROR, "Please Insert the date.");
			return new ModelAndView("closedTransaction", "model", model);
			// depositLists = fixedDepositDAO.getAllClosedTransactionsList();
		}

		if (depositLists != null && depositLists.size() > 0) {
			depositForm.setDepositId(depositLists.get(0).getDepositId());
			model.addAttribute("depositLists", depositLists);
			mav = new ModelAndView("closedTransactionsList", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/listOfCustomers", method = RequestMethod.POST)
	public ModelAndView listOfCustomers(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String depositType, Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);
		model.addAttribute("endUser", endUser);
		String cusId = fixedDepositForm.getCustomerID();
		String cusName = fixedDepositForm.getCustomerName();
		String cusNum = fixedDepositForm.getContactNum();
		String cusEmail = fixedDepositForm.getEmail();
		System.out.println("cust....");
		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);
		model.addAttribute("customerList", customerList);
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("searchCustForModificationReport", "model", model);
	}

	@RequestMapping(value = "/searchCustForModificationReport", method = RequestMethod.GET)
	public ModelAndView searchCustForModificationReport(Model model, String depositType,
			@RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		Role role = endUserDAO.findById(ROLE_ID);
		if (role.getRole().equals("ROLE_USER")) {
			List<Object[]> objList = depositHolderDAO.getOpenDeposit(endUser.getCustomerId());
			if (objList.size() > 0) {

				model.addAttribute("objList", objList);
				return new ModelAndView("modifiedDeposits", "model", model);
			} else {
				model.addAttribute("error", "No deposits found");
				return new ModelAndView("noDataFound", "model", model);
			}
		}

		return new ModelAndView("searchCustForModificationReport", "model", model);
	}

	@RequestMapping(value = "/searchCustomerTdsReport", method = RequestMethod.GET)
	public ModelAndView searchCustomerTdsReport(Model model, String depositType) throws CustomException {
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);
		return new ModelAndView("searchCustomerTdsReport", "model", model);

	}

	@RequestMapping(value = "/searchCustomerTdsReportList", method = RequestMethod.POST)
	public ModelAndView searchCustomerTdsReportList(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String depositType) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);
		String cusId = fixedDepositForm.getCustomerID();
		String cusName = fixedDepositForm.getCustomerName();
		String cusNum = fixedDepositForm.getContactNum();
		String cusEmail = fixedDepositForm.getEmail();

		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);
		model.addAttribute("customerList", customerList);
		model.addAttribute("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("searchCustomerTdsReport", "model", model);

	}

	@RequestMapping(value = "/customerTdsReport")
	public ModelAndView customerTdsReport(Model model, Long customerId, @RequestParam("menuId") Long menuId)
			throws CustomException {
		model = this.menuUtils(model, menuId);
		ModelAndView mav = new ModelAndView();
		Double totalTDS = 0d;
		List<DepositWiseCustomerTDS> customerTdsList = tdsDAO.getCustomerTDSReportByDepositId(customerId);
		Customer cust = customerDAO.getById(customerId);

		if (customerTdsList != null && customerTdsList.size() > 0) {

			for (int i = 0; i < customerTdsList.size(); i++) {

				totalTDS = totalTDS + customerTdsList.get(i).getContributedTDSAmount();
			}

			model.addAttribute("cust", cust.getCustomerName());
			model.addAttribute("totalTDS", fdService.round(totalTDS, 2));
			model.addAttribute("customerTdsList", customerTdsList);
			mav = new ModelAndView("customerTdsReportList", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;

	}

	@RequestMapping(value = "/customerTdsReport", method = RequestMethod.GET)
	public ModelAndView customerTdsReport(Model model) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

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
			model.addAttribute("tdsLists", tdsLists);
			mav = new ModelAndView("allCustomerTdsList", "model", model);
		}

		else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	// To search the bank payment details by account number
	@RequestMapping(value = "/searchBankPayment", method = RequestMethod.GET)
	public ModelAndView searchBankPayment(Model model, @ModelAttribute BankPaymentForm bankPaymentForm,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		model.addAttribute("bankPaymentForm", bankPaymentForm);
		return new ModelAndView("searchBankPayment", "model", model);

	}

	// To get the Bank Payment Details
	@RequestMapping(value = "/bankpaymentDetails", method = RequestMethod.POST)
	ModelAndView bankpaymentDetails(Model model, @ModelAttribute BankPaymentForm bankPaymentForm)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

		ModelAndView mav = new ModelAndView();
		// To get the account number to check whether user input account number
		// is correct or not
		Deposit depositAccNo = fixedDepositDao.getByAccountNumber(bankPaymentForm.getAccountNumber());
		if (bankPaymentForm.getAccountNumber().equals(null) || bankPaymentForm.getAccountNumber().equals("")
				|| depositAccNo == null) {
			model.addAttribute(Constants.ERROR, Constants.ACCOUNTERROR);
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
				model.addAttribute(Constants.bankPaymentDetails, bankPaymentDetails);
				mav = new ModelAndView("bankPaymentDetails", "model", model);
			} else {
				mav = new ModelAndView("noDataFound", "model", model);
			}
		}

		return mav;
	}

	@RequestMapping(value = "/unSuccessfulPayOffDetails", method = RequestMethod.GET)
	public ModelAndView unSuccessfulPayOffDetails(Model model, @RequestParam("menuId") Long menuId)
			throws CustomException {
		model.addAttribute("payOfForm", payOfForm);
		model = this.menuUtils(model, menuId);
		return new ModelAndView("unSuccessfulPayOffDetails", "model", model);

	}

	@RequestMapping(value = "/unSuccessfulPayOffDetailsList", method = RequestMethod.GET)
	public ModelAndView unSuccessfulPayOffDetailsList(Model model, RedirectAttributes attributes,
			@ModelAttribute PayOfForm payOfForm) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
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
			model.addAttribute(Constants.ERROR, "Please Insert the date.");
			return new ModelAndView("unSuccessfulPayOffDetails", "model", model);
			// depositLists = fixedDepositDAO.getAllClosedTransactionsList();

		}

		if (unSuccessfulPayOffDetailsList != null && unSuccessfulPayOffDetailsList.size() > 0) {
			payOfForm.setDepositid(unSuccessfulPayOffDetailsList.get(0).getDepositid());
			model.addAttribute("unSuccessfulPayOffDetailsList", unSuccessfulPayOffDetailsList);
			mav = new ModelAndView("unSuccessfulPayOffDetailsList", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/distributionReports", method = RequestMethod.GET)
	public ModelAndView fdListforHolderWiseResort(Model model, @RequestParam("menuId") Long menuId)
			throws CustomException {
		model = this.menuUtils(model, menuId);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);
		model.addAttribute("endUser", endUser);
		List<DepositForm> depositsList = null;
		Role role = endUserDAO.findById(ROLE_ID);
		if (role.getRoleDisplayName().equals("User")) {
			EndUser user = getCurrentLoggedUserDetails();
			Customer customer = customerDAO.getByUserId(user.getUserName()).getSingleResult();
			depositsList = fixedDepositDao.getAllRegularAndAutoDepositsByCustomerId(customer.getId());
		} else {
			depositsList = fixedDepositDao.getAllDepositsList();
		}
		if (depositsList != null && depositsList.size() > 0) {
			model.addAttribute("depositsList", depositsList);
			return new ModelAndView("fdListforHolderWiseResortBank", "model", model);
		}

		else {
			return new ModelAndView("noDataFound", "model", model);
		}
	}

	@RequestMapping(value = "/holderWiseReport", method = RequestMethod.GET)
	public ModelAndView holderWiseReport(Model model, Long depositId, String depositAccountNo, Long menuId)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		List<Object[]> distributionList = depositHolderWiseDistributionDAO.getDepositHolderWiseDistribution(depositId);

		List<DepositHolderForm> depositHolderList = depositHolderDAO.getDepositHoldersName(depositId);

		if (distributionList.size() > 0) {
			model.addAttribute("distributionList", distributionList);
			model.addAttribute("depositHolderList", depositHolderList);
			model.addAttribute("depositAccountNo", depositAccountNo);
			return new ModelAndView("holderWiseReportBank", "model", model);
		}

		else {
			model.addAttribute("error", "No transaction found for the deposit");
			return new ModelAndView("noDataFound", "model", model);
		}

	}

	@RequestMapping(value = "/distributionReport", method = RequestMethod.GET)
	public ModelAndView distributionReport(Model model, Long depositId, String depositAccountNo, Long menuId)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		List<Object[]> distributionList = depositHolderWiseDistributionDAO.getDepositHolderWiseDistribution(depositId);

		List<DepositHolderForm> depositHolderList = depositHolderDAO.getDepositHoldersName(depositId);

		if (distributionList.size() > 0) {
			model.addAttribute("distributionList", distributionList);
			model.addAttribute("depositHolderList", depositHolderList);
			model.addAttribute("depositAccountNo", depositAccountNo);
			return new ModelAndView("holderWiseReport", "model", model);
		}

		else {
			model.addAttribute("error", "No transaction found for the deposit");
			return new ModelAndView("noDataFound", "model", model);
		}

	}

	@RequestMapping(value = "/interestReportsList", method = RequestMethod.GET)
	public ModelAndView interestReport(Model model, @RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		// List<Menu> menus = this.menus;
		ModelAndView mav = new ModelAndView();
		model = this.menuUtils(model, menuId);
		Role role = endUserDAO.findById(ROLE_ID);
		List<DepositForm> depositLists = null;
		if (role.getRoleDisplayName().equals("User")) {
			EndUser user = getCurrentLoggedUserDetails();
			Customer customer = customerDAO.getByUserId(user.getUserName()).getSingleResult();
			depositLists = fixedDepositDao.getAllRegularAndAutoDepositsByCustomerId(customer.getId());
		} else {
			depositLists = fixedDepositDao.getAllDepositsList();
		}

		if (depositLists != null && depositLists.size() > 0) {
			depositForm.setDepositId(depositLists.get(0).getDepositId());
			model.addAttribute("depositLists", depositLists);
			mav = new ModelAndView("interestReport", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;

	}

	@RequestMapping(value = "/showInterestRecords", method = RequestMethod.GET)
	public ModelAndView showInterestRecords(Model model, Long id, Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);
		model.addAttribute("endUser", endUser);

		ModelAndView mav = new ModelAndView();

		List<Interest> interestList = interestDAO.getByDepositId(id);

		Double totalInterest = 0d;
		if (interestList.size() > 0) {
			Integer index = interestList.size() - 1;
			Deposit deposit = fixedDepositDao.getDeposit(interestList.get(index).getDepositId());
			String customerName = (String) fixedDepositDao.getDepositForInterestRate(id).get(0)[8];

			model.addAttribute("customerName", customerName);
			model.addAttribute("depositAccountNumber", deposit.getAccountNumber());
			model.addAttribute("interestList", interestList);

			for (int i = 0; i < interestList.size(); i++) {
				totalInterest = totalInterest + interestList.get(i).getInterestAmount();
			}
			System.out.println("totalInterest:  " + totalInterest);
			model.addAttribute("interestAmount", FDService.round(totalInterest, 2));
			mav = new ModelAndView("showInterestRecords", "model", model);
		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/tenureCalculation", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Integer tenureCalculation(Model model,
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

	@RequestMapping(value = "/interestCalculation", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Double interestCalculation(Model model, Double principalAmount, Integer tenure,
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

	@RequestMapping(value = "/calculator", method = RequestMethod.GET)
	public ModelAndView calculator(Model model, @ModelAttribute InterestCalculationForm interestCalculationForm,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		model.addAttribute("interestCalculationForm", interestCalculationForm);
		return new ModelAndView("calculator", "model", model);

	}

	@RequestMapping(value = "/customerCitizenConversion", method = RequestMethod.GET)
	public ModelAndView customerCitizenConversion(Model model, @RequestParam("menuId") Long menuId)
			throws CustomException {

		model = this.menuUtils(model, menuId);
		model.addAttribute("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("customerCitizenConversion", "model", model);

	}

	@RequestMapping(value = "/customerCitizenConversionByCustomerId", method = RequestMethod.POST)
	public ModelAndView customerCitizenConversionByCustomerId(Model model, @RequestParam("customerId") Long customerId)
			throws CustomException {

		Customer customer = customerDAO.getById(customerId);
		List<Menu> menus = this.menus;
		model.addAttribute("customer", customer);
		model.addAttribute("menus", menus);

		return new ModelAndView("customerCitizenConversionConfirm", "model", model);
	}

	@RequestMapping(value = "/customerCitizenConversionSave", method = RequestMethod.POST)
	public ModelAndView customerCitizenConversionSave(Model model, @ModelAttribute Customer customer,
			@RequestParam("depositForcefullyClosed") Boolean depositForcefullyClosed, RedirectAttributes attribute)
			throws CustomException {

		model.addAttribute("menus", menus);

		List<Deposit> depositList = fixedDepositDao.getDeposits(customer.getId());

		// Close all the deposit of that customer
		if (depositForcefullyClosed == true) {
			for (int i = 0; i < depositList.size(); i++) {
				calculationService.closeDeposit(depositList.get(i), true);
			}
		} else {
			for (int i = 0; i < depositList.size(); i++) {
				// Get the deposit holder list
				List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositList.get(i).getId());
				String autoDeposit = "";
				if (depositList.get(i).getDepositCategory() != null
						&& depositList.get(i).getDepositCategory().equalsIgnoreCase("AUTO"))
					autoDeposit = "Auto";

				calculationService.calculateInterestInDetail(depositList.get(i), depositHolderList, autoDeposit, null);
			}
		}

		// If customer is converted his citizenship from RI --> NRI or vice versa then
		// converted Citizen will be in Customer Details and Old citizen will be stored
		// in citizenConversionDetails.
		CitizenConversionDetails citizenConversionDetails = new CitizenConversionDetails();
		citizenConversionDetails.setCustId(customer.getId());
		citizenConversionDetails.setCreatedBy(customer.getCreatedBy());
		citizenConversionDetails.setCreatedOn(customer.getCreatedOn());
		citizenConversionDetails.setCurrentDate(new Date());
		citizenConversionDetails.setCustomerID(customer.getCustomerID());
		citizenConversionDetails.setPreviousCitizenStartDate(customer.getCreatedOn());
		String citizen[] = customer.getCitizen().split(",");
		for (int i = 0; i < citizen.length; i++) {
			customer.setCitizen(citizen[i]);
			citizenConversionDetails.setCurrentCitizen(citizen[i]);
			citizenConversionDetails.setPreviousCitizen(citizen[0]);
		}
		if ((customer.getCitizen()).equalsIgnoreCase("RI")) {
			citizenConversionDetails.setPreviousNRIAccountTYpe(customer.getNriAccountType());
			customer.setNriAccountType(null);
		} else {
			citizenConversionDetails.setPreviousNRIAccountTYpe(null);
		}
		citizenConversionDetails.setCurrentNRIAccountType(customer.getNriAccountType());
		customer.setModifiedBy(this.getCurrentLoggedUserName());
		customer.setModifiedOn(new Date());
		Calendar cal = Calendar.getInstance();
		cal.setTime(customer.getModifiedOn());
		cal.add(Calendar.DATE, -1);
		Date previousCitizenEndDate = cal.getTime();
		citizenConversionDetails.setPreviousCitizenEndDate(previousCitizenEndDate);
		citizenConversionDetails.setModifiedBy(customer.getModifiedBy());
		citizenConversionDetails.setModifiedOn(customer.getModifiedOn());
		citizenConversionDetailsDAO.save(citizenConversionDetails);
		customer = customerDAO.updateUser(customer);

		// System will update the New Interest rate for
		// all the deposits of the customer
		if (!depositForcefullyClosed) {
			for (int i = 0; i < depositList.size(); i++) {

				int days = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(),
						depositList.get(i).getNewMaturityDate() == null ? depositList.get(i).getMaturityDate()
								: depositList.get(i).getNewMaturityDate())
						+ 1;

				String category = calculationService.geCustomerActualCategory(customer);

				Float interestRate = calculationService.getDepositInterestRate(days, category,
						depositList.get(i).getDepositCurrency(), depositList.get(i).getDepositAmount(),
						depositList.get(i).getDepositClassification(), customer.getCitizen(),
						customer.getNriAccountType());

				depositList.get(i).setModifiedInterestRate(interestRate);
				fixedDepositDao.updateFD(depositList.get(i));
			}
		}

		attribute = updateTransaction("Updated Successfully", attribute);
		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "consolidatedSummary", method = RequestMethod.GET)
	public ModelAndView consolidatedSummary(Model model, @ModelAttribute DepositForm depositForm) {
		List<Branch> allBranches = branchDAO.getAllBranches();
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("branches", allBranches);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		return new ModelAndView("consolidatedSummary");

	}

	@RequestMapping(value = "findByFromDateAndToDateTotalDeposits", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody Map<String, String> findByFromDateAndToDateTotalDeposits(
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("branch") String branchId) {
		Map<String, String> deposits = new HashMap<String, String>();
		Long openDepositCount = depositHolderDAO.getOpenDepositCount(toDate, fromDate,
				branchId.equals("") ? null : Long.valueOf(branchId));
		Long closedDepositCount = depositHolderDAO.getPrematuredDepositCount(toDate, fromDate,
				branchId.equals("") ? null : Long.valueOf(branchId));
		Long maturedDepositCount = depositHolderDAO.getMaturedDepositCount(toDate, fromDate,
				branchId.equals("") ? null : Long.valueOf(branchId));
		Double totalPaymentReceived = depositHolderDAO.getTotalPaymentReceived(toDate, fromDate,
				branchId.equals("") ? null : Long.valueOf(branchId));
		Double totalWithdrawAmount = depositHolderDAO.getTotalWithdrawAmount(toDate, fromDate,
				branchId.equals("") ? null : Long.valueOf(branchId));
		Double totalIntrest = depositHolderDAO.getTotalIntrest(toDate, fromDate, null);
		Double totalAmountPaidByBank = depositHolderDAO.getTotalAmountPaidByBank(toDate, fromDate, null);

		deposits.put("openDepositCount", String.valueOf(openDepositCount));
		deposits.put("closedDepositCount", String.valueOf(closedDepositCount));
		deposits.put("totalPaymentReceived", String.valueOf(totalPaymentReceived));
		deposits.put("totalWithdrawAmount", String.valueOf(totalWithdrawAmount));
		deposits.put("maturedDepositCount", String.valueOf(maturedDepositCount));
		deposits.put("totalIntrest", String.valueOf(totalIntrest));
		deposits.put("totalAmountPaidByBank", String.valueOf(totalAmountPaidByBank));
		return deposits;
	}

	@RequestMapping(value = "/searchCustomerForSweepFacility", method = RequestMethod.GET)
	public ModelAndView searchCustomerForSweepConfiguration(Model model,
			@ModelAttribute SweepInFacilityForCustomer sweepInFacilityForCustomer) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("customerForm", customerForm);

		Role role = endUserDAO.findById(ROLE_ID);
		if (role.getRole().equals("ROLE_USER")) {
			Customer customer = customerDAO.getById(endUser.getCustomerId());
			AccountDetails accDetails = accountDetailsDAO.findSavingByCustId(customer.getId());
			SweepConfiguration configuration = sweepConfigurationDAO.getActiveSweepConfiguration();
			// Integer minAmount=configuration.getMinimumAmountRequiredForSweepIn();
			// Integer minSavingBal =configuration.getMinimumSavingBalanceForSweepIn();
			Boolean isTenure = false;
			if (configuration != null) {
				isTenure = configuration.getSweepInType().contains("Fixed Tenure");

			}
			model.addAttribute("accDetails", accDetails);
			model.addAttribute("menus", menus);
			if (accDetails == null) {
				model.addAttribute("error", "Sweep deposit can be configured only for Savings bank account");
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
				model.addAttribute("isTenureConfigured", 1);
				model.addAttribute("minAmount", sweepConfiguration.getMinimumAmountRequiredForSweepIn());
				model.addAttribute("minSavingBal", sweepConfiguration.getMinimumSavingBalanceForSweepIn());

			} else {

				sweepConfiguration = new SweepInFacilityForCustomer();
				sweepConfiguration.setIsSweepInConfigureedByCustomer(isSweepRequired);
				sweepConfiguration.setIsSweepInRestrictedByBank(isSweepInRestrictedByBank);
				model.addAttribute("minAmount", configuration.getMinimumAmountRequiredForSweepIn());
				model.addAttribute("minSavingBal", configuration.getMinimumSavingBalanceForSweepIn());
				model.addAttribute("isTenureConfigured", 0);
			}

			sweepConfiguration.setAccountId(accDetails.getId());
			sweepConfiguration.setCustomerId(customer.getId());
			Integer isSweepRequiredDB = sweepConfiguration.getIsSweepInConfigureedByCustomer() == null ? 0
					: sweepConfiguration.getIsSweepInConfigureedByCustomer();
			Integer isSweepInRestrictedByBankDB = sweepConfiguration.getIsSweepInRestrictedByBank() == null ? 0
					: sweepConfiguration.getIsSweepInRestrictedByBank();
			sweepConfiguration.setIsSweepInConfigureedByCustomer(isSweepRequiredDB);
			sweepConfiguration.setIsSweepInRestrictedByBank(isSweepInRestrictedByBankDB);
			model.addAttribute("sweepInFacilityForCustomer", sweepConfiguration);
			model.addAttribute("isTenure", isTenure);
			return new ModelAndView("sweepDepositFacility", "model", model);
		}

		return new ModelAndView("searchCustomerForSweepFacility", "model", model);
	}

	@RequestMapping(value = "/getCustomerListForSweepFacility", method = RequestMethod.POST)
	public ModelAndView getCustomerListForSweepConfiguration(Model model, @ModelAttribute CustomerForm customerForm)
			throws CustomException {
		String cusId = customerForm.getCustomerID();
		String cusName = customerForm.getCustomerName();
		String cusNum = customerForm.getContactNum();
		String cusEmail = customerForm.getEmail();

		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("customerList", customerList);
		model.addAttribute("customerForm", customerForm);

		return new ModelAndView("searchCustomerForSweepFacility", "model", model);

	}

	@RequestMapping(value = "/sweepDepositFacility", method = RequestMethod.POST)
	public ModelAndView sweepConfiguration(Model model, Long customerId,
			@ModelAttribute SweepInFacilityForCustomer sweepInFacilityForCustomer) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		Customer customer = customerDAO.getById(customerId);
		AccountDetails accDetails = accountDetailsDAO.findSavingByCustId(customer.getId());
		SweepConfiguration configuration = sweepConfigurationDAO.getActiveSweepConfiguration();
		// Integer minAmount=configuration.getMinimumAmountRequiredForSweepIn();
		// Integer minSavingBal =configuration.getMinimumSavingBalanceForSweepIn();
		Boolean isTenure = false;
		if (configuration != null) {
			isTenure = configuration.getSweepInType().contains("Fixed Tenure");

		}
		model.addAttribute("accDetails", accDetails);
		model.addAttribute("menus", menus);
		if (accDetails == null) {
			model.addAttribute("error", "Sweep deposit can be configured only for Savings bank account");
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
			model.addAttribute("isTenureConfigured", 1);
			model.addAttribute("minAmount", sweepConfiguration.getMinimumAmountRequiredForSweepIn());
			model.addAttribute("minSavingBal", sweepConfiguration.getMinimumSavingBalanceForSweepIn());

		} else {

			sweepConfiguration = new SweepInFacilityForCustomer();
			sweepConfiguration.setIsSweepInConfigureedByCustomer(isSweepRequired);
			sweepConfiguration.setIsSweepInRestrictedByBank(isSweepInRestrictedByBank);
			model.addAttribute("minAmount", configuration.getMinimumAmountRequiredForSweepIn());
			model.addAttribute("minSavingBal", configuration.getMinimumSavingBalanceForSweepIn());
			model.addAttribute("isTenureConfigured", 0);
		}

		sweepConfiguration.setAccountId(accDetails.getId());
		sweepConfiguration.setCustomerId(customer.getId());
		Integer isSweepRequiredDB = sweepConfiguration.getIsSweepInConfigureedByCustomer() == null ? 0
				: sweepConfiguration.getIsSweepInConfigureedByCustomer();
		Integer isSweepInRestrictedByBankDB = sweepConfiguration.getIsSweepInRestrictedByBank() == null ? 0
				: sweepConfiguration.getIsSweepInRestrictedByBank();
		sweepConfiguration.setIsSweepInConfigureedByCustomer(isSweepRequiredDB);
		sweepConfiguration.setIsSweepInRestrictedByBank(isSweepInRestrictedByBankDB);
		model.addAttribute("sweepInFacilityForCustomer", sweepConfiguration);
		model.addAttribute("isTenure", isTenure);
		return new ModelAndView("sweepDepositFacility", "model", model);

	}

	@RequestMapping(value = "/saveSweepFacility", method = RequestMethod.POST)
	public ModelAndView sweepDeposit(Model model, @ModelAttribute SweepInFacilityForCustomer sweepInFacilityForCustomer,
			RedirectAttributes attributes) throws CustomException {

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
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

		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "/suspendCustomer", method = RequestMethod.GET)
	public ModelAndView suspendCustomer(@RequestParam Long id, Model model, RedirectAttributes attributes, Long menuId)
			throws CustomException {

		EndUser userList = endUserDAOImpl.findUserByCustomerId(id);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);
		model.addAttribute("customerId", id);
		model.addAttribute("endUser", endUser);
		endUserForm.setId(userList.getId());
		endUserForm.setTransactionId(userList.getTransactionId());
		endUserForm.setUserName(userList.getUserName());
		endUserForm.setContactNo(userList.getContactNo());
		endUserForm.setEmail(userList.getEmail());
		endUserForm.setDisplayName(userList.getDisplayName());

		model.addAttribute("endUserForm", endUserForm);

		return new ModelAndView("suspendCustomer", "model", model);
	}

	@RequestMapping(value = "/suspendCustomerConfirm", method = RequestMethod.POST)
	public ModelAndView suspendCustomerConfrim(@ModelAttribute EndUserForm endUserForm, Model model,
			RedirectAttributes attributes, Long menuId, Long customerId) throws CustomException {

		model.addAttribute("endUserForm", endUserForm);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);
		model.addAttribute("customerId", customerId);
		model.addAttribute("endUser", endUser);
		return new ModelAndView("suspendCustomerConfirm", "model", model);
	}

	@Transactional

	@RequestMapping(value = "/updateCustomerStatus", method = RequestMethod.POST)
	public ModelAndView updateCustomerStatus(@ModelAttribute EndUserForm endUserForm, Model model,
			RedirectAttributes attributes) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);

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

		Customer customer = customerDAO.getByUserName(endUserForm.getUserName());
		customer.setStatus(Constants.Suspended);
		customerDAO.updateUser(customer);
		transactionDAO.insertTransaction(transaction);
		endUserDAOImpl.update(userList);
		attributes = updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:successfullySaved");
	}

	@RequestMapping(value = "/approveDepositConfrim", method = RequestMethod.POST)
	public ModelAndView approveBankEmpConfrim(@ModelAttribute FixedDepositForm fixedDepositForm, Model model,
			RedirectAttributes attributes) throws CustomException {

		model.addAttribute("fixedDepositForm", fixedDepositForm);
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		return new ModelAndView("approveDepositConfrim", "model", model);
	}

	@RequestMapping(value = "/updateApprovalStatus", method = RequestMethod.POST)
	public ModelAndView updateApprovalStatus(@ModelAttribute FixedDepositForm fixedDepositForm, Model model,
			RedirectAttributes attributes) throws CustomException {

		Deposit deposit = fixedDepositDao.findById(fixedDepositForm.getId());
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		deposit.setApprovalStatus(fixedDepositForm.getStatus());
		deposit.setComment(fixedDepositForm.getComment());

		fixedDepositDao.updateApprovalStatus(deposit);

		attributes = updateTransaction("Updated Successfully", attributes);
		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
	public ModelAndView deleteAccount(Long id, Long customerId) throws CustomException {

		AccountDetails accountDetails = accountDetailsDAO.findById(id);

		accountDetails.setIsActive(0);
		accountDetailsDAO.updateUserAccountDetails(accountDetails);

		return new ModelAndView("redirect:editCustomer?id=" + customerId);

	}

	@RequestMapping(value = "/uploadBulkDeposits", method = RequestMethod.GET)
	public ModelAndView bulkDeposit(Model model) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);

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
			model.addAttribute("files", files);
		} else {
			folder1.mkdir();
			folder.mkdir();

			model.addAttribute("files", null);
		}

		// model.addAttribute("files", files);
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

	@RequestMapping(value = "/excelUpload")
	public ModelAndView showExcelForm(Model model) throws Exception, CustomException {
		EndUser user = getCurrentLoggedUserDetails();
		if (user != null) {
			model.addAttribute("user", user);
			return new ModelAndView("excelUpload", "model", model);
		} else {
			throw new EndUserNotFoundException("User not Found");
		}
	}

	public void saveDepositFromExcel(FixedDepositForm fixedDepositForm) throws CustomException {

		String transactionId = fdService.generateRandomString();

		String mopName = fixedDepositForm.getPaymentMode();
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(mopName);
		Long mopId = mop.getId();
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
		deposit.setPaymentMode(mop.getPaymentMode());
		deposit.setPaymentModeId(mopId);
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
		Deposit depositSaves = fixedDepositDao.saveFD(deposit);

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
		fixedDepositDao.updateFD(depositSaves);

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

		if (mop.getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {
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
		if (mop.getPaymentMode().equalsIgnoreCase("Cash")) {
			fromAccountNo = "";
			fromAccountType = "Cash Account";
		} else if (mop.getPaymentMode().equalsIgnoreCase("DD") || mop.getPaymentMode().equalsIgnoreCase("Cheque")) {

			payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
			payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
			payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
			payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getChequeNo();
			fromAccountType = fixedDepositForm.getPaymentMode();

		} else if (mop.getPaymentMode().equalsIgnoreCase("Card Payment")) {

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

		else if (mop.getPaymentMode().equalsIgnoreCase("Net Banking")) {

			payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getDepositForm().getAccountType());
			payment.setLinkedAccNoForFundTransfer(fixedDepositForm.getDepositForm().getLinkedAccountNo());
			payment.setTransferType(fixedDepositForm.getDepositForm().getTransferType());

			// For accounting entry only
			fromAccountNo = fixedDepositForm.getDepositForm().getLinkedAccountNo();
			fromAccountType = fixedDepositForm.getAccountType();
		}

		payment.setDepositHolderId(depositHolderNew.getId());
		payment.setPaymentModeId(mopId);
		payment.setPaymentMode(fixedDepositForm.getPaymentMode());
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
		ledgerService.insertJournal(depositSaves.getId(), customer.getId(), DateService.getCurrentDate(), fromAccountNo,
				depositSaves.getAccountNumber(), Event.OPEN_DEPOSIT.getValue(), amountPaid, mopId,
				depositSummary.getTotalPrincipal(), transactionId);

		// -----------------------------------------------------------
	}

	@RequestMapping(value = "/excelUpdate", method = RequestMethod.POST)
	public String excelUpload1(@ModelAttribute("newCaseForm") DepositRatesForm depositRatesForm, Model model,
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

		model.addAttribute("user", user);
		model.addAttribute("depositRatesForm", depositRatesForm);

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
				// log.info("Import rows" + i);
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return 1;
	}

	@RequestMapping(value = "/updateExistingExcel", method = RequestMethod.POST)
	public ModelAndView updateExistingExcel(@ModelAttribute("newCaseForm") DepositRatesForm depositRatesForm,
			Model model, RedirectAttributes attributes, HttpServletResponse response, String currencies)
			throws Exception, CustomException {
		try {
			List<MultipartFile> files = depositRatesForm.getFiles();
			for (MultipartFile multipartFile : files) {
				String fileName = multipartFile.getOriginalFilename().trim();
				InputStream excelFile = multipartFile.getInputStream();

				Workbook workbook = new XSSFWorkbook(excelFile);
				if (fileName.equalsIgnoreCase("Single-Regular-BulkDeposit-Upload.xlsx")) {
					UpdateSingleRegularExcel(fileName, workbook, response, currencies);
				}
				if (fileName.equalsIgnoreCase("Single-Recurring-Deposit.xlsx")) {
					UpdateSingleRecurringExcel(fileName, workbook, response, currencies);
				}
				if (fileName.equalsIgnoreCase("Joint-Recurring-Deposit.xlsx")) {
					UpdateJointRecurringExcel(fileName, workbook, response, currencies);
				}
				if (fileName.equalsIgnoreCase("Joint-Regular-Deposit.xlsx")) {
					UpdateJointRegularExcel(fileName, workbook, response, currencies);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		attributes = updateTransaction("File Processed successfully", attributes);
		return new ModelAndView("redirect:successfullySaved");
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
									ProductConfiguration pc = productConfigurationDAO
											.findById(Long.parseLong(currentCell.getStringCellValue()));
									if (pc == null) {
										pc = productConfigurationDAO
												.findByProductCode(currentCell.getStringCellValue());
									}
									if (pc.getProductName().toLowerCase().contains("single")
											&& pc.getProductName().toLowerCase().contains("regular")) {
										add = true;
									} else {
										add = false;
									}

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
									ProductConfiguration pc = productConfigurationDAO
											.findById(Long.parseLong(currentCell.getStringCellValue()));
									if (pc == null) {
										pc = productConfigurationDAO
												.findByProductCode(currentCell.getStringCellValue());
									}
									if (pc.getProductName().toLowerCase().contains("single")
											&& pc.getProductName().toLowerCase().contains("recurring")) {
										add = true;
									} else {
										add = false;
									}

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

		Object[][] errorData = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][45];
		Object[][] data = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][45];
		Object[][] data1 = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][45];
		Object[] tempData = new Object[45];
		Object[] errorTempData = new Object[45];

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
				for (int cn = 0; cn < 45; cn++) {
					Cell currentCell = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (currentCell == null) {
						tempData[cn] = "";
					} else {
						if (cn == 0 || cn == 6 || cn == 7 || cn == 44) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);
						}
						if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
							if (cn == 0) {
								if (ArrayUtils.contains(productCodes, currentCell.getStringCellValue())) {
									ProductConfiguration pc = productConfigurationDAO
											.findById(Long.parseLong(currentCell.getStringCellValue()));
									if (pc == null) {
										pc = productConfigurationDAO
												.findByProductCode(currentCell.getStringCellValue());
									}
									if (pc.getProductName().toLowerCase().contains("joint")
											&& pc.getProductName().toLowerCase().contains("regular")) {
										add = true;
									} else {
										add = false;
									}

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

					for (int i = 0; i < 45; i++) {
						data[d][i] = tempData[i];
					}
					d++;
				} else {
					for (int i = 0; i < 45; i++) {
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
						fixedDepositForm.setAccountAccessType(datatype[44].toString());
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
								for (int i = column1; i < 44; i++) {
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

		Object[][] errorData = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][47];
		Object[][] data = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][47];
		Object[][] data1 = new Object[datatypeSheet.getPhysicalNumberOfRows() - 1][47];
		Object[] tempData = new Object[47];
		Object[] errorTempData = new Object[47];

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
				for (int cn = 0; cn < 47; cn++) {
					Cell currentCell = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (currentCell == null) {
						tempData[cn] = "";
					} else {
						if (cn == 0 || cn == 6 || cn == 7 || cn == 9 || cn == 46) {
							currentCell.setCellType(Cell.CELL_TYPE_STRING);
						}
						if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
							if (cn == 0) {
								if (ArrayUtils.contains(productCodes, currentCell.getStringCellValue())) {
									ProductConfiguration pc = productConfigurationDAO
											.findById(Long.parseLong(currentCell.getStringCellValue()));
									if (pc == null) {
										pc = productConfigurationDAO
												.findByProductCode(currentCell.getStringCellValue());
									}
									if (pc.getProductName().toLowerCase().contains("joint")
											&& pc.getProductName().toLowerCase().contains("recurring")) {
										add = true;
									} else {
										add = false;
									}

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

					for (int i = 0; i < 47; i++) {
						data[d][i] = tempData[i];
					}
					d++;
				} else {
					for (int i = 0; i < 47; i++) {
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
						fixedDepositForm.setAccountAccessType(datatype[46].toString());
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
								for (int i = column1; i < 46; i++) {
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

		String mopName = fixedDepositForm.getPaymentMode();
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(mopName);
		Long mopId = mop.getId();

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
		deposit.setAccountAccessType(fixedDepositForm.getAccountAccessType());
		if (fixedDepositForm.getAccountNo() != null && !fixedDepositForm.getAccountNo().equals("")) {
			String accArray[] = fixedDepositForm.getAccountNo().split(",");
			deposit.setLinkedAccountNumber(accArray[0]);
		}

		deposit.setMaturityAmount(fixedDepositForm.getEstimatePayOffAmount());
		deposit.setNewMaturityAmount(fixedDepositForm.getEstimatePayOffAmount());
		deposit.setCurrentBalance(Double.valueOf(deposit.getDepositAmount()));
		deposit.setDepositCurrency(fixedDepositForm.getCurrency());
		deposit.setPaymentMode(mop.getPaymentMode());
		deposit.setPaymentModeId(mopId);
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
		Deposit newDeposit = fixedDepositDao.saveFD(deposit);
		// testing done till here
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
		fixedDepositDao.updateFD(newDeposit);

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

		if (mop.getPaymentMode().equalsIgnoreCase("Cash")) {
			fromAccountNo = "";
			fromAccountType = "Cash Account";
		}

		if (mop.getPaymentMode().equalsIgnoreCase("DD") || mop.getPaymentMode().equalsIgnoreCase("Cheque")) {

			payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
			payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
			payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
			payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());

			// For Accounting Entries Only
			fromAccountNo = fixedDepositForm.getDepositForm().getChequeNo();
			fromAccountType = fixedDepositForm.getPaymentMode();
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
			fromAccountType = fixedDepositForm.getDepositForm().getCardType();

		}

		if (mop.getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {

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
		payment.setPaymentModeId(mopId);
		payment.setPaymentMode(mop.getPaymentMode());
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

		ledgerService.insertJournal(newDeposit.getId(), customer.getId(), DateService.getCurrentDate(), fromAccountNo,
				newDeposit.getAccountNumber(), Event.OPEN_DEPOSIT.getValue(), amountPaid,
				Long.parseLong(fixedDepositForm.getDepositForm().getPaymentMode()), depositSummary.getTotalPrincipal(),
				transactionId);
		//
		// ledgerService.insertJournalLedger(newDeposit.getId(), customer.getId(),
		// DateService.getCurrentDate(),
		// fromAccountNo, fromAccountType, newDeposit.getAccountNumber(), "Deposit
		// Account", "Payment", amountPaid,
		// fixedDepositForm.getPaymentMode(), depositSummary.getTotalPrincipal());
		// --------------------------------------------------------------------

	}

	@RequestMapping(value = "/uploadForm15G15H", method = RequestMethod.GET)
	public ModelAndView upload(Model model, String menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
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

			model.addAttribute("fileFormList", fileFormList);
		}

		model.addAttribute("uploadedFile", uploadFileForm);
		return new ModelAndView("uploadFile", "model", model);

	}

	@RequestMapping(value = "/uploadedFile", method = RequestMethod.POST)
	public ModelAndView uploadedFile(Model model, @ModelAttribute UploadFileForm uploadFileForm,
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
				model.addAttribute("uploadedFile", uploadFileForm);
			}

			attributes = updateTransaction("Uploaded Successfully", attributes);
			return new ModelAndView("redirect:successfullySaved");
		}

		else {
			attributes.addFlashAttribute("error", "Please select the file first");

			return new ModelAndView("redirect:uploadForm15G15H");
		}

	}

	@RequestMapping(value = "/customerSearchForFormSubmission", method = RequestMethod.GET)
	public ModelAndView customerSearchForFormSubmission(Model model, String depositType, Long menuId)
			throws CustomException {
		model.addAttribute("fixedDepositForm", fixedDepositForm);
		model = this.menuUtils(model, menuId);

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		return new ModelAndView("customerSearchForFormSubmission", "model", model);

	}

	@RequestMapping(value = "/customerListForFormSubmission", method = RequestMethod.POST)
	public ModelAndView customerListForFormSubmission(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			String depositType, String menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		String cusId = fixedDepositForm.getCustomerID();
		String cusName = fixedDepositForm.getCustomerName();
		String cusNum = fixedDepositForm.getContactNum();
		String cusEmail = fixedDepositForm.getEmail();

		List<CustomerForm> customerList = new ArrayList<CustomerForm>();
		customerList = customerDAO.searchCustomer(cusId, cusName, cusNum, cusEmail);
		model.addAttribute("customerList", customerList);
		model.addAttribute("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("customerSearchForFormSubmission", "model", model);

	}

	@RequestMapping(value = "/formSubmission")
	public ModelAndView formSubmission(Model model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm, Long customerId, String menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		Customer cust = customerDAO.getById(customerId);

		depositForm.setCustomerId(customerId);

		System.out.println("cust.getCategory()...420.");
		model.addAttribute("depositForm", depositForm);
		model.addAttribute("cust", cust);
		model.addAttribute("menuId", menuId);

		return new ModelAndView("formSubmission", "model", model);
	}

	@RequestMapping(value = "/savedFormSubmission", method = RequestMethod.POST)
	public String savedFormSubmission(Model model, @ModelAttribute DepositForm depositForm,
			RedirectAttributes attributes, String menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
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

		return "redirect:successfullySaved";

	}

	@RequestMapping(value = "/suspendBankEmp", method = RequestMethod.GET)
	public ModelAndView suspendBankEmp(@RequestParam Long id, ModelMap model, RedirectAttributes attributes)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		EndUser userList = endUserDAOImpl.findId(id);

		endUserForm.setId(userList.getId());
		endUserForm.setUserName(userList.getUserName());
		endUserForm.setContactNo(userList.getContactNo());
		endUserForm.setEmail(userList.getEmail());
		endUserForm.setDisplayName(userList.getDisplayName());

		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendBankEmp", "model", model);
	}

	@RequestMapping(value = "/suspendBankEmpConfirm", method = RequestMethod.POST)
	public ModelAndView suspendBankEmpConfrim(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.put("endUserForm", endUserForm);

		return new ModelAndView("suspendBankEmpConfirm", "model", model);
	}

	@RequestMapping(value = "/updateBankEmpStatus", method = RequestMethod.POST)
	public ModelAndView updateBankEmpStatus(@ModelAttribute EndUserForm endUserForm, ModelMap model,
			RedirectAttributes attributes) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
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
		return new ModelAndView("redirect:successfullySaved");
	}

	@RequestMapping(value = "/savePaymentMaturityDetails", method = RequestMethod.POST)
	public ModelAndView savePaymentMaturityDetails(Model model, @ModelAttribute FixedDepositForm fixedDepositForm,
			RedirectAttributes attributes) {

		try {
			String DeathCertificateSubmitted = fixedDepositForm.getDeathCertificateSubmitted();
			ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(fixedDepositForm.getPaymentMode());
			float f = fixedDepositForm.getChequeAmount();
			Double amount = (double) f;
			BankPayment bankPayment = bankPaymentDAO.getBankPayment(fixedDepositForm.getDepositId());
			BankPaid bankPaid = calculationService.insertInBankPaid(fixedDepositForm.getDepositId(),
					bankPayment.getId(), amount, "PAID");
			List<String> items = Arrays.asList(fixedDepositForm.getDepositHolders().split("\\s*,\\s*"));
			Integer count = items.size();
			List<DepositHolderForm> depositHolderList = depositHolderDAO
					.getUnpaidDepositHolders(fixedDepositForm.getDepositId());

			if (depositHolderList.size() == items.size()) {
				Deposit deposit = fixedDepositDao.getDeposit(fixedDepositForm.getDepositId());
				deposit.setIsFullyPaid(1);
				depositHolderDAO.updateDeposit(deposit);
			}
			for (String item : items) {

				DepositHolder depositHolder = depositHolderDAO.findById(Long.parseLong(item));

				List<AccountDetails> accountDetailsList = accountDetailsDAO
						.findCurrentSavingByCustId(depositHolder.getCustomerId());
				BankPaymentDetails bankPaymentDetails = bankPaymentDAO
						.getBankPaymentByDepositHolderId(depositHolder.getId());
				bankPaymentDetails.setIsPaid(1);
				bankPaymentDAO.updateBankPaymentDetails(bankPaymentDetails);
				amount = depositHolder.getDistAmtOnMaturity();

				calculationService.insertInBankPaidDetails(bankPaid.getId(), bankPayment.getId(),
						bankPaymentDetails.getId(), depositHolder.getId(), depositHolder.getCustomerId(), amount,
						DateService.getCurrentDateTime(), fixedDepositForm.getPaymentMode(), fixedDepositForm.getBank(),
						fixedDepositForm.getChequeBranch(), String.valueOf(fixedDepositForm.getChequeNo()),
						fixedDepositForm.getChequeDate(), accountDetailsList.get(0).getAccountNo(), null, null, null,
						null, null, null);
				// Insert in Journal & Ledger
				String fromAccountNo = "";
				String transactionId = fdService.generateRandomString();
				DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(fixedDepositForm.getDepositId());
				ledgerService.insertJournal(fixedDepositForm.getDepositId(), depositHolder.getCustomerId(),
						DateService.getCurrentDate(), fromAccountNo, String.valueOf(fixedDepositForm.getChequeNo()),
						Event.TOPUP_DEPOSIT.getValue(), amount, mop.getId(), depositSummary.getTotalPrincipal(),
						transactionId);

				if (DeathCertificateSubmitted == "1") {
					depositHolder.setIsAmountTransferredOnMaturity(1);
					depositHolder.setIsAmountTransferredToNominee(1);
				} else {
					depositHolder.setIsAmountTransferredOnMaturity(1);
					depositHolder.setIsAmountTransferredToNominee(0);
				}
				depositHolderDAO.updateDepositHolder(depositHolder);
			}

			DDCheque cheque = new DDCheque();
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
			return new ModelAndView("redirect:successfullySaved");

		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return new ModelAndView("bankPaymentOnMaturity", "model", model);
	}

	@RequestMapping(value = "/saveMultiplePaymentMaturityDetails", method = RequestMethod.POST)
	public ModelAndView saveMultiplePaymentMaturityDetails(Model model,
			@ModelAttribute FixedDepositForm fixedDepositForm, RedirectAttributes attributes) {

		try {
			float chequeAmount = 0f;
			Integer count = fixedDepositForm.getJointAccounts() == null ? 1
					: fixedDepositForm.getJointAccounts().size();
			List<DepositHolderForm> depositHolderList = depositHolderDAO
					.getUnpaidDepositHolders(fixedDepositForm.getDepositId());
			if (depositHolderList.size() == count) {
				Deposit deposit = fixedDepositDao.getDeposit(fixedDepositForm.getDepositId());
				deposit.setIsFullyPaid(1);
				depositHolderDAO.updateDeposit(deposit);
			}
			for (int i = 0; i < fixedDepositForm.getJointAccounts().size(); i++) {
				chequeAmount += fixedDepositForm.getJointAccounts().get(i).getChequeAmount();
			}
			Double amount = (double) chequeAmount;
			BankPayment bankPayment = bankPaymentDAO.getBankPayment(fixedDepositForm.getDepositId());
			BankPaid bankPaid = calculationService.insertInBankPaid(fixedDepositForm.getDepositId(),
					bankPayment.getId(), amount, "PAID");

			for (int i = 0; i < fixedDepositForm.getJointAccounts().size(); i++) {
				chequeAmount += fixedDepositForm.getJointAccounts().get(i).getChequeAmount();
				String DeathCertificateSubmitted = fixedDepositForm.getJointAccounts().get(i)
						.getDeathCertificateSubmitted();
				DepositHolder depositHolder = depositHolderDAO
						.findById(fixedDepositForm.getJointAccounts().get(i).getId());

				List<AccountDetails> accountDetailsList = accountDetailsDAO
						.findCurrentSavingByCustId(depositHolder.getCustomerId());
				BankPaymentDetails bankPaymentDetails = bankPaymentDAO
						.getBankPaymentByDepositHolderId(depositHolder.getId());
				bankPaymentDetails.setIsPaid(1);
				bankPaymentDAO.updateBankPaymentDetails(bankPaymentDetails);

				calculationService.insertInBankPaidDetails(bankPaid.getId(), bankPayment.getId(),
						bankPaymentDetails.getId(), depositHolder.getId(), depositHolder.getCustomerId(), amount,
						DateService.getCurrentDateTime(), fixedDepositForm.getPaymentMode(),
						fixedDepositForm.getJointAccounts().get(i).getChequeBank(),
						fixedDepositForm.getJointAccounts().get(i).getChequeBranch(),
						String.valueOf(fixedDepositForm.getJointAccounts().get(i).getChequeNo()),
						fixedDepositForm.getJointAccounts().get(i).getChequeDate(),
						accountDetailsList.get(0).getAccountNo(), null, null, null, null, null, null);

				// Insert in Journal & Ledger
				String fromAccountNo = "";
				String transactionId = fdService.generateRandomString();
				ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(fixedDepositForm.getPaymentMode());
				DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(fixedDepositForm.getDepositId());
				ledgerService.insertJournal(fixedDepositForm.getDepositId(), depositHolder.getCustomerId(),
						DateService.getCurrentDate(), fromAccountNo, String.valueOf(fixedDepositForm.getChequeNo()),
						Event.TOPUP_DEPOSIT.getValue(), amount, mop.getId(), depositSummary.getTotalPrincipal(),
						transactionId);

				if (DeathCertificateSubmitted == "1") {
					depositHolder.setIsAmountTransferredOnMaturity(1);
					depositHolder.setIsAmountTransferredToNominee(1);
				} else {
					depositHolder.setIsAmountTransferredOnMaturity(1);
					depositHolder.setIsAmountTransferredToNominee(0);
				}
				depositHolderDAO.updateDepositHolder(depositHolder);

				DDCheque cheque = new DDCheque();
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
			return new ModelAndView("redirect:successfullySaved");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ModelAndView("bankPaymentOnMaturity", "model", model);
	}

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

	@RequestMapping(value = "/modifiedDeposits")
	public ModelAndView modificationReport(ModelMap model, Long customerId, Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
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
	public ModelAndView showModificationList(ModelMap model, Long depositId, Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		List<Object[]> objList = depositModificationDAO.getAllModificationList(depositId);

		if (objList != null) {

			ProductConfiguration productConfiguration = productConfigurationDAO
					.findById(fixedDepositDao.findByDepositId(depositId).getProductConfigurationId());
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
	public ModelAndView compareWithModification(ModelMap model, String modificationNo, String preModificationNo,
			Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
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
	public ModelAndView compareWithDeposit(ModelMap model, String modificationNo, Long depositId, Long menuId)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		DepositHolderForm depositHolderForm = new DepositHolderForm();
		DepositHolderForm depositHolderFormPrev = new DepositHolderForm();

		List<DepositModification> modificationList = depositModificationDAO.getByModificationNo(modificationNo);
		Deposit deposit = fixedDepositDao.getDeposit(depositId);

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

	@RequestMapping(value = "/editFDBank", method = RequestMethod.GET)
	public ModelAndView editFDBank(@RequestParam Long id, ModelMap model, HttpServletRequest request,
			RedirectAttributes attributes, @RequestParam String depositCategory, @RequestParam Long holderId)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		// model.addAttribute("menuId", menuId);

		Deposit deposit = fixedDepositDao.getDeposit(id);
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

	@RequestMapping(value = "/editFdPost", method = RequestMethod.POST)
	public String editPost(ModelMap model, @ModelAttribute HolderForm depositHolderForm, RedirectAttributes attributes)
			throws CustomException {
		// log.info("edit FD POST -------Start =----");
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
					deposit = fixedDepositDao.getDeposit(deposit.getId());

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

			return "redirect:successfullySaved";
		} catch (Exception ex) {
			ex.printStackTrace();
			// log.info("Exception -----------------", ex);
			attributes.addFlashAttribute(Constants.ERROR, ex.getMessage());
			return "redirect:fdListBank";

		}

	}

	@RequestMapping(value = "/confirmEditFdBank", method = RequestMethod.POST)
	public ModelAndView checkResult(ModelMap model, @ModelAttribute HolderForm depositHolderForm,
			RedirectAttributes attributes, @RequestParam Long holderId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
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
		deposit = fixedDepositDao.getDeposit(depositHolderForm.getDeposit().getId());

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

	@RequestMapping(value = "/convertDeposit", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody HashMap<String, Double> convertDeposit(Long depositId) {
		Deposit deposit = fixedDepositDao.findById(depositId);
		// conversionType:
		// Regular Deposit to Recurring Deposit
		// Recurring to Regular Deposit
		return fdService.convertDeposit(deposit);
	}

	@RequestMapping(value = "/saveConvertedDeposit", method = RequestMethod.POST, produces = "text/html")
	public Distribution saveConvertedDeposit(Deposit Deposit, String conversionType) {
		// conversionType:
		// Regular Deposit to Recurring Deposit
		// Recurring to Regular Deposit
		HashMap<String, Double> conversionSummary = fdService.convertDeposit(Deposit);

		return null;
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
			deposit = fixedDepositDao.getDeposit(deposit.getId());

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

	}

	@RequestMapping(value = "/fdListBank", method = RequestMethod.GET)
	public ModelAndView fdCustomerList(ModelMap model, RedirectAttributes attributes) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		ModelAndView mav = new ModelAndView();

		List<DepositForm> depositList = fixedDepositDao.getAllDepositsList();

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

	@RequestMapping(value = "/journalAllList", method = RequestMethod.GET)
	public ModelAndView journalAllList(ModelMap model, @ModelAttribute LedgerReportForm ledgerReportForm)
			throws CustomException {

		Date fromDate = ledgerReportForm.getFromDate();
		Date toDate = ledgerReportForm.getToDate();
		model.put("menus", menus);
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

			journalList = fixedDepositDao.getJournalListByFromAndToDate(fromDate, toDate);

			if (journalList != null && journalList.size() > 0) {
				for (Journal journal : journalList) {
					// DepositId is not mandatorily present in journal.
					// when any user credits his saving account , that time deposit wont be there.
					if (journal.getDepositId() != null) {
						Deposit deposit = fixedDepositDao.getDeposit(journal.getDepositId());
						journal.setDepositAccountNumber(deposit.getAccountNumber());
					}
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

	@RequestMapping(value = "/showDistributionRecords", method = RequestMethod.GET)
	public ModelAndView showDistributionRecords(ModelMap model, Long id, Long menuId, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm) throws CustomException {

		ModelAndView mav = null;
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);
		model.addAttribute("endUser", endUser);

		List<Distribution> distributionList = fixedDepositDao.getDistributionList(id);
		if (distributionList != null) {
			Integer index = distributionList.size() - 1;
			Deposit deposit = fixedDepositDao.getDeposit(distributionList.get(index).getDepositId());
			String customerName = (String) fixedDepositDao.getDepositForInterestRate(id).get(0)[8];
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

	@RequestMapping(value = "/overdraftList", method = RequestMethod.GET)
	public ModelAndView overdraftList(ModelMap model, RedirectAttributes attributes) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		ModelAndView mav = new ModelAndView();

		List<OverdraftIssue> overdraftIssueList = overdraftIssueDAO.getAllOverdrafts();
		if (overdraftIssueList != null && overdraftIssueList.size() > 0) {

			model.put("overdraftIssueList", overdraftIssueList);
			mav = new ModelAndView("overdraftList", "model", model);

		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/overdraftListCust", method = RequestMethod.GET)
	public ModelAndView overdraftListCust(ModelMap model, RedirectAttributes attributes) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		ModelAndView mav = new ModelAndView();
		Customer cus = customerDAO.getById(endUser.getCustomerId());
		List<DepositForm> depositList = fixedDepositDao.getDepositByCustomerId(cus.getId());
		List<OverdraftIssue> overdraftIssueList = overdraftIssueDAO.getMyOverdrafts(depositList);
		if (overdraftIssueList != null && overdraftIssueList.size() > 0) {

			model.put("overdraftIssueList", overdraftIssueList);
			mav = new ModelAndView("overdraftList", "model", model);

		} else {
			mav = new ModelAndView("noDataFound", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/searchAllLedgerByDate", method = RequestMethod.GET)
	public ModelAndView ledgerAllReportSummary(ModelMap model, @ModelAttribute LedgerReportForm ledgerReportForm,
			String account) throws CustomException {
		model.put("account", account);
		model.put("ledgerReportForm", ledgerReportForm);
		return new ModelAndView("searchAllLedgerByDate", "model", model);

	}

	@RequestMapping(value = "/ledgerPosting", method = RequestMethod.GET)
	public ModelAndView ledgerPosting(Model model, @ModelAttribute LedgerReportForm ledgerReportForm,
			@RequestParam("menuId") Long menuId) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		Menu menu = endUserDAO.findByMenuId(menuId);
		model.addAttribute("method", RequestMethod.POST);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		model.addAttribute("urlPattern", menu.getUrlPattern() + "/" + menuId);
		// model.addAttribute("branch", this.branch);
		model.addAttribute("reportForm", ledgerReportForm);

		return new ModelAndView("ledgerPosting", "model", model);
	}

	@RequestMapping(value = "/postLedgers", method = RequestMethod.POST)
	public ModelAndView postLedgers(Model model, @ModelAttribute LedgerReportForm ledgerReportForm,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {

			// Post in Ledgers
			// --------------------------------------
			// Get the Journals which are not posted
			ledgerService.postInLedger();
			// --------------------------------------

			model.addAttribute("succMsg", "Successfully Posted.");
			model.addAttribute("menus", menus);
			return new ModelAndView("ledgerPosting", "model", model);
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("ledgerPosting", "model", model);

		}
	}

	@RequestMapping(value = "/bankPayment", method = RequestMethod.GET)
	public ModelAndView bankPayment(ModelMap model, @ModelAttribute BankPaymentForm bankPaymentForm)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.put("bankPaymentForm", bankPaymentForm);
		return new ModelAndView("bankPayment", "model", model);

	}

	@RequestMapping(value = "/customerList", method = RequestMethod.POST)
	public ModelAndView customerList(ModelMap model, @ModelAttribute BankPaymentForm bankPaymentForm)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
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

	@RequestMapping(value = "/deleteDownloadDocuments")
	public ModelAndView deleteDownloadDocuments(ModelMap model, HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute FixedDepositForm fixedDepositForm, Long menuId)
			throws Exception, CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("menuId", menuId);
		model.addAttribute("endUser", endUser);
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
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
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
			EndUser endUser = this.currentLoggedInUser;
			List<Menu> menus = this.menus;

			model.addAttribute("menus", menus);
			model.addAttribute("endUser", endUser);
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

	@RequestMapping(value = "/documentDelete", method = RequestMethod.POST)
	public ModelAndView deleteDocument(RedirectAttributes attributes, @ModelAttribute UploadedFileForm uploadedFileForm,
			ModelMap model, RedirectAttributes attribute) throws EndUserNotFoundException, CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		if (uploadedFileForm.getIdList().length > 0) {
			for (Long id : uploadedFileForm.getIdList()) {
				uploadDAO.deleteUploadedFile(id);
			}
			attribute = updateTransaction("Saved Successfully", attribute);

			return new ModelAndView("redirect:successfullySaved");
		} else {
			return new ModelAndView("noDataFound", "model", model);
		}
	}

	private List<DepositWiseCustomerTDSForChart> getCustomerTDSforChart(List<Deposit> depositList, Long customerId) {
		List<DepositWiseCustomerTDSForChart> depositWiseTDSList = new ArrayList();
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

	@RequestMapping(value = "/custDepositAmt", method = RequestMethod.GET)
	public ModelAndView fdCustomerLists(ModelMap model, RedirectAttributes attributes, @RequestParam("id") Long id,
			String accountNumber, String newMaurityDate, String depositType) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);

		Deposit deposit = fixedDepositDao.getDeposit(id);
		if (deposit.getProductConfigurationId() == null) {
			attributes.addFlashAttribute("error", "Product id not found");
			return new ModelAndView("redirect:fdListForFundTransfer");
		} else {
			ProductConfiguration productConfiguration = productConfigurationDAO
					.findById(deposit.getProductConfigurationId());
			model.put("productConfiguration", productConfiguration);
			if (productConfiguration.getIsTopupAllowed() != null) {
				if (productConfiguration.getIsTopupAllowed() == 1) {

					String lockingPeriodForTopup = productConfiguration.getLockingPeriodForTopup();
					if (lockingPeriodForTopup != null && lockingPeriodForTopup.trim().equalsIgnoreCase(",,")) {
						model.put("lockingPeriod", false);
					} else {
						String[] arrlockingPeriodForTopup = lockingPeriodForTopup.split(",");
						Date lockingDate = deposit.getCreatedDate();
						for (int i = 0; i < arrlockingPeriodForTopup.length; i++) {
							if (arrlockingPeriodForTopup[i] != null
									&& arrlockingPeriodForTopup[i].trim().length() > 0) {
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

			Long customerId = endUser.getCustomerId();

			Customer user = customerDAO.getById(customerId);

			List<Object[]> depositHolderObjList = depositHolderDAO.getOpenDeposit(user.getId());

			if (depositHolderObjList != null && depositHolderObjList.size() > 0) {
				List<DepositHolderForm> depositHolderList = new ArrayList<DepositHolderForm>();

				for (int i = 0; i < depositHolderObjList.size(); i++) {
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
			// depositForm.setDepositId(id);
			// depositForm.setAccountNumber(accountNumber);
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
			// model.put("depositForm", depositForm);
			model.put("fixedDepositForm", fixedDepositForm);
			model.put("depositType", depositType);
		}
		return new ModelAndView("custDepositAmount", "model", model);
	}

	@RequestMapping(value = "/makePayment", method = RequestMethod.POST)
	public String makePayment(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute FixedDepositForm fixedDepositForm, HttpServletRequest request) throws CustomException {

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		Customer customerDetails = customerDAO.getById(endUser.getCustomerId());

		// String transactionId = depositService.makePaymentFromCustomer(depositForm,
		// customerDetails);

		Deposit deposit = fixedDepositDao.getDeposit(fixedDepositForm.getDepositId());
		ProductConfiguration productConfiguration = productConfigurationDAO
				.findById(deposit.getProductConfigurationId());

		if (productConfiguration.getIsTopupAllowed() != null && productConfiguration.getIsTopupAllowed() == 0) {
			attributes.addFlashAttribute("error", "Sorry, Top-up is not allowed.");
			return "redirect:netBankingPayment?id=" + deposit.getId() + "&accountNumber=" + deposit.getAccountNumber()
					+ "&newMaurityDate=" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fixedDepositForm
							.getMaturityDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}

		Double amountPaid = fixedDepositForm.getFdAmount();
		if (amountPaid < productConfiguration.getMinimumTopupAmount()) {
			attributes.addFlashAttribute("error",
					"Sorry, Minimum Top-up Amount is " + productConfiguration.getMinimumTopupAmount() + ".");
			return "redirect:netBankingPayment?id=" + deposit.getId() + "&accountNumber=" + deposit.getAccountNumber()
					+ "&newMaurityDate=" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fixedDepositForm
							.getMaturityDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		}

		if (amountPaid > productConfiguration.getMaximumTopupAmount()) {
			attributes.addFlashAttribute("error",
					"Sorry, Maximum Top-up Amount is " + productConfiguration.getMaximumTopupAmount() + ".");
			return "redirect:netBankingPayment?id=" + deposit.getId() + "&accountNumber=" + deposit.getAccountNumber()
					+ "&newMaurityDate=" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fixedDepositForm
							.getMaturityDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

		}

		String lockingPeriodForTopup = productConfiguration.getLockingPeriodForTopup();
		String[] arrlockingPeriodForTopup = lockingPeriodForTopup.split(",");

		Date lockingDate = deposit.getCreatedDate();
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
			attributes.addFlashAttribute("error", "Sorry, Deposit is still within the locking period for Top-up.");
			return "redirect:netBankingPayment?id=" + deposit.getId() + "&accountNumber=" + deposit.getAccountNumber()
					+ "&newMaurityDate=" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fixedDepositForm
							.getMaturityDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		}

		Date maturityDate = deposit.getNewMaturityDate() != null ? deposit.getNewMaturityDate()
				: deposit.getMaturityDate();
		if (productConfiguration.getPreventionOfTopUpBeforeMaturity() != null) {
			if (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate) <= productConfiguration
					.getPreventionOfTopUpBeforeMaturity()) {
				attributes.addFlashAttribute("error", "Sorry, Withdraw has prevented for last "
						+ productConfiguration.getPreventionOfTopUpBeforeMaturity() + " days from maturity.");
				return "redirect:netBankingPayment?id=" + deposit.getId() + "&accountNumber="
						+ deposit.getAccountNumber() + "&newMaurityDate="
						+ DateTimeFormatter.ofPattern("yyyy-MM-dd").format(fixedDepositForm.getMaturityDate()
								.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			}
		}
		ModeOfPayment modeOfPayment = null;
		if (fixedDepositForm.getPaymentMode().equals("Net Banking")) {
			modeOfPayment = modeOfPaymentDAO.getModeOfPayment(fixedDepositForm.getPaymentMode());
		} else {
			modeOfPayment = modeOfPaymentDAO.getModeOfPayment(fixedDepositForm.getCardType());
		}

		fixedDepositForm.setPaymentMode(modeOfPayment.getId().toString());
		String transactionId = depositService.doPayment(fixedDepositForm, customerDetails.getUserName());

		Date curDate = DateService.loginDate;

		attributes.addFlashAttribute(Constants.TRANSACTIONID, transactionId);
		attributes.addFlashAttribute(Constants.TRANSACTIONDATE, curDate);
		attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Payment Successful");

		return "redirect:fdSaved";

	}

	@RequestMapping(value = "/netBankingPayment", method = RequestMethod.GET)
	public ModelAndView netBankingPayment(ModelMap model, RedirectAttributes attributes, @RequestParam("id") Long id,
			String accountNumber, String newMaurityDate, String depositType) throws CustomException {
		ProductConfiguration productConfiguration = null;

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;

		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		Customer user = customerDAO.getById(endUser.getCustomerId());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date maturityDate = DateService.getCurrentDateTime();
		try {
			maturityDate = df.parse(newMaurityDate);
			fixedDepositForm.setMaturityDate(maturityDate);
		} catch (ParseException e) {
			fixedDepositForm.setMaturityDate(maturityDate);
			e.printStackTrace();
		}
		List<Object[]> depositHolderObjList = depositHolderDAO.getOpenDeposit(user.getId());
		if (depositHolderObjList != null && depositHolderObjList.size() > 0) {

			List<DepositHolderForm> depositHolderList = new ArrayList<DepositHolderForm>();

			for (int i = 0; i < depositHolderObjList.size(); i++) {
				DepositHolderForm depositform = new DepositHolderForm();
				depositform.setDepositHolderStatus((String) depositHolderObjList.get(i)[0]);
				depositform.setContribution((Float) depositHolderObjList.get(i)[1]);

				Long depositId = Long.parseLong(depositHolderObjList.get(i)[2].toString());
				depositform.setDepositId(depositId);

				depositform.setMaturityDate((Date) depositHolderObjList.get(i)[3]);
				depositform.setStatus((String) depositHolderObjList.get(i)[4]);
				depositform.setCreatedDate((Date) depositHolderObjList.get(i)[5]);
				depositform.setDepositamount((Double) depositHolderObjList.get(i)[6]);
				// depositform.setF((Double) depositHolderObjList.get(i)[6]);
				depositform.setAccountNumber((String) depositHolderObjList.get(i)[8]);
				depositform.setNewMaturityDate((Date) depositHolderObjList.get(i)[9]);
				depositform.setDepositType((String) depositHolderObjList.get(i)[10]);
				depositHolderList.add(depositform);
			}
			Deposit deposit = fixedDepositDao.getDeposit(id);
			if (deposit.getProductConfigurationId() == null) {
				attributes.addFlashAttribute("error", "Product id not found");
				return new ModelAndView("redirect:fdListForFundTransfer");
			} else {
				productConfiguration = productConfigurationDAO.findById(deposit.getProductConfigurationId());
				if (productConfiguration == null) {
					productConfiguration = productConfigurationDAO
							.findByProductCode(deposit.getProductConfigurationId().toString());
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
		// fixedDepositForm.setFdAmount(fdAmount);

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

	@RequestMapping(value = "/fdSaved", method = RequestMethod.GET)
	public ModelAndView fdSaved(ModelMap model, @ModelAttribute FixedDepositForm fixedDepositForm)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.put("fixedDepositForm", fixedDepositForm);
		return new ModelAndView("fdSaved", "model", model);

	}

	@RequestMapping(value = "/fundTransfer")
	public ModelAndView fundTransfer(ModelMap model, @ModelAttribute FundTransferForm fundTransferForm)
			throws CustomException {
		ModelAndView mav = new ModelAndView();
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		Customer customerDetails = customerDAO.getById(endUser.getCustomerId());

		// List<HolderForm> id = fundTransferForm.getDepositHolderObjList();
		List<AccountDetails> accountList = accountDetailsDAO.findCurrentSavingByCustId(customerDetails.getId());

		List<HolderForm> depositHolderObjList = depositService.getAllDepositByCustomerId(customerDetails.getId());

		if (depositHolderObjList != null) {
			// ProductConfiguration
			// pc=depositHolderObjList.get(0).getProductConfiguration();

			// Integer minTopup=pc.getMinimumTopupAmount();
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

	@RequestMapping(value = "/editOrnament", method = RequestMethod.POST)
	public ModelAndView editOrnament(Model model, @ModelAttribute OrnamentsMaster ornamentsMaster,
			RedirectAttributes attributes) throws CustomException {

		try {
			model.addAttribute("menus", menus);
			if (ornamentsMaster.getId() != null) {

				if (ornamentsMasterDAO.getOrnamentExist(ornamentsMaster.getOrnament(), ornamentsMaster.getId())) {
					model.addAttribute("error", "ornament already exist !");
					model.addAttribute("ornamentsMaster", ornamentsMaster);
					return new ModelAndView("editOrnament", "model", model);
				}

				ornamentsMasterDAO.update(ornamentsMaster);
				attributes = updateTransaction("Updated Successfully", attributes);
				return new ModelAndView("redirect:successfullySaved");
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());

		}
		return new ModelAndView("editOrnament", "model", model);

	}

	@RequestMapping(value = "/downloadDocument", method = RequestMethod.GET)
	public ModelAndView downloadDocument(ModelMap model) throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
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

	@RequestMapping(value = "/ornamentsMasterById", method = RequestMethod.GET)
	public ModelAndView ornamentsMasterById(Model model, @ModelAttribute OrnamentsMaster ornamentsMaster, Long id) {
		try {
			model.addAttribute("menus", menus);
			if (id != null) {
				ornamentsMaster = ornamentsMasterDAO.getOrnamentsMastertById(id);
				model.addAttribute("ornamentsMaster", ornamentsMaster);
			} else {

				return new ModelAndView("editOrnament", "model", model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("editOrnament", "model", model);
	}

	@RequestMapping(value = "/addOrnament", method = RequestMethod.POST)
	public ModelAndView addOrnament(Model model, @ModelAttribute OrnamentsMaster ornamentsMaster,
			RedirectAttributes attribute, @RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);

		Boolean isPermission = this.checkPermissions(menuId, "WRITE");
		if (isPermission) {

			String ornament = ornamentsMaster.getOrnament();
			Long count = ornamentsMasterDAO.getCountOfOrnament(ornament);

			if (count == 0) {

				OrnamentsMaster ornaments = new OrnamentsMaster();
				ornaments.setOrnament(ornament);

				ornamentsMasterDAO.save(ornaments);

			}

			else {

				model.addAttribute(Constants.ERROR, Constants.ORNAMENTEXIST);
				return new ModelAndView("ornaments", "model", model);
			}
			attribute = updateTransaction("Saved Successfully", attribute);
			return new ModelAndView("redirect:successfullySaved");
		} else {
			model.addAttribute("error", "You do not have sufficient permissions !");
			return new ModelAndView("ornaments", "model", model);

		}
	}

	@RequestMapping(value = "/ornaments")
	public ModelAndView ornaments(Model model, @ModelAttribute OrnamentsMaster ornamentsMaster,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		List<OrnamentsMaster> allList = ornamentsMasterDAO.getAllOrnamentsMasterDetails();
		model.addAttribute("allList", allList);
		model.addAttribute("ornamentsMaster", ornamentsMaster);
		return new ModelAndView("ornaments", "model", model);

	}

	@RequestMapping(value = "overdraftSummary")
	public ModelAndView overdraftSummary(Model model, @RequestParam("menuId") Long menuId,
			@ModelAttribute OverdraftForm overdraftForm) {
		model = this.menuUtils(model, menuId);
		EndUser endUser = this.currentLoggedInUser;
		Role role = endUserDAO.findById(ROLE_ID);
		model.addAttribute("role", role.getRole());

		List<OverdraftIssue> issues = new ArrayList<>();
		if (role.getRole().equals("ROLE_USER")) {
			List<Object[]> depositHolders = depositHolderDAO.getAllOpenDeposits(endUser.getCustomerId());
			for (Object[] objects : depositHolders) {
				List<OverdraftIssue> overdraftIssue = overdraftIssueDAO
						.getOverdraftIssueDetailsByDepositId(Long.valueOf(objects[2].toString()));
				if (overdraftIssue != null && overdraftIssue.size() > 0) {
					for (OverdraftIssue overdraftIssue2 : overdraftIssue) {
						issues.add(overdraftIssue2);

					}
				}

			}
			if (issues.size() > 0) {
				model.addAttribute("overdraftIssueList", issues);
			}

		}

		return new ModelAndView("overdraftSummary", "model", model);

	}

	@RequestMapping(value = "overdraftSummarySearchByOverdraftNumber", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView overdraftSummarySearchByOverdraftNumber(Model model, @RequestParam("menuId") Long menuId,
			@ModelAttribute OverdraftForm overdraftForm) {
		model = this.menuUtils(model, menuId);
		OverdraftIssue overdraftIssue = overdraftIssueDAO.getOverdraftIssueDetails(overdraftForm.getOverdraftNumber());
		if (overdraftIssue != null)
			model.addAttribute("overdraftIssue", overdraftIssue);
		else
			model.addAttribute("error", "Overdraft Number not found !");

		return new ModelAndView("overdraftSummary", "model", model);

	}

	@RequestMapping(value = "overdraftSummaryWithdrawDetailList", method = RequestMethod.GET)
	public ModelAndView overdraftSummaryWithdrawDetailList(Model model, @RequestParam("menuId") Long menuId,
			@ModelAttribute OverdraftForm overdraftForm, RedirectAttributes attributes) {
		model = this.menuUtils(model, menuId);
		Role role = endUserDAO.findById(ROLE_ID);
		model.addAttribute("role", role.getRole());
		List<OverdraftWithdrawMaster> overdraftWithdrawMasters = overdraftIssueDAO
				.findByOverdraftIdInWithdrawMaster(overdraftForm.getId());
		if (overdraftWithdrawMasters != null && overdraftWithdrawMasters.size() > 0)
			model.addAttribute("overdraftWithdrawMasters", overdraftWithdrawMasters);
		else {
			attributes.addFlashAttribute("information", "overdraft withdraw result not found !");
			attributes.addFlashAttribute("overdraftNumber", overdraftForm.getOverdraftNumber());
			return new ModelAndView("redirect:overdraftSummary?menuId=" + menuId);
		}
		return new ModelAndView("overdraftWithdrawMasterList", "model", model);

	}

	@RequestMapping(value = "overdraftSummaryPaymentDetailList", method = RequestMethod.GET)
	public ModelAndView overdraftSummaryPaymentDetailList(Model model, @RequestParam("menuId") Long menuId,
			@ModelAttribute OverdraftForm overdraftForm, RedirectAttributes attributes) {
		model = this.menuUtils(model, menuId);
		Role role = endUserDAO.findById(ROLE_ID);
		model.addAttribute("role", role.getRole());

		List<OverdraftPayment> overdraftPayments = overdraftIssueDAO
				.findByOverdraftIdInOverdraftPayment(overdraftForm.getId());
		if (overdraftPayments != null && overdraftPayments.size() > 0)
			model.addAttribute("overdraftPayments", overdraftPayments);
		else {
			attributes.addFlashAttribute("information", "overdraft payments result not found !");
			attributes.addFlashAttribute("overdraftNumber", overdraftForm.getOverdraftNumber());
			return new ModelAndView("redirect:overdraftSummary?menuId=" + menuId);

		}
		return new ModelAndView("overdraftPaymentList", "model", model);

	}

	@RequestMapping(value = "overdraftSummaryBackPress", method = { RequestMethod.GET })
	public ModelAndView overdraftSummaryBackPress(Model model, @RequestParam("menuId") Long menuId,
			@ModelAttribute OverdraftForm overdraftForm) {
		model = this.menuUtils(model, menuId);
		OverdraftIssue overdraftIssue = overdraftIssueDAO.getOverdraftIssueDetailsById(overdraftForm.getId());
		model.addAttribute("overdraftIssue", overdraftIssue);
		return new ModelAndView("overdraftSummary", "model", model);

	}

	@RequestMapping(value = "/withdrawPaymentListCust", method = RequestMethod.GET)
	public ModelAndView withdrawPaymentList(ModelMap model, @RequestParam("menuId") Long menuId)
			throws CustomException {
		// BankConfiguration bankConfiguration = ratesDAO.findAll();
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		Customer cus = customerDAO.getById(endUser.getCustomerId());
		List<DepositForm> depositList = fixedDepositDao.getDepositByCustomerId(cus.getId());

		if (depositList.size() > 0) {

			List<AccountDetails> accList = accountDetailsDAO.findCurrentSavingByCustId(cus.getId());
			model.put("accList", accList);
			model.put("withdrawForm", withdrawForm);
			model.put("depositList", depositList);
			model.put("customerName", cus.getCustomerName());
			// model.put("payAndWithdrawTenure",
			// bankConfiguration.getPayAndWithdrawTenure());

		} else {

			model.put("error", "No deposit found");
			return new ModelAndView("noDataFoundCusm", "model", model);

		}

		return new ModelAndView("depositPaymentSearch", "model", model);
	}

	@RequestMapping(value = "/getDeathCertificate", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody String getDeathCertificate(ModelMap model, @RequestParam Long depositId)
			throws CustomException {

		String result = "0";
		List<DepositHolder> depositHolderObjList = depositHolderDAO.getDepositHolders(depositId);
		Iterator<DepositHolder> itr = depositHolderObjList.iterator();
		while (itr.hasNext()) {
			DepositHolder holder = itr.next();
			if (holder.getDeathCertificateSubmitted() != null) {
				if (holder.getDeathCertificateSubmitted().equals("1")
						|| holder.getDeathCertificateSubmitted().equalsIgnoreCase("Yes")) {
					result = "1";
				}
			}

		}

		return result;
	}

	@RequestMapping(value = "/depositListCust", method = RequestMethod.GET)
	public ModelAndView depositLists(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm, Long menuId) throws CustomException {
		ModelAndView mav = new ModelAndView();
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		EndUser user = getCurrentLoggedUserDetails();
		Customer customer = customerDAO.getByUserId(user.getUserName()).getSingleResult();
		// return
		Long id = customer.getId();
		List<DepositForm> depositLists = fixedDepositDao.getAllRegularAndAutoDepositsByCustomerId(id);
		if (depositLists != null && depositLists.size() > 0) {
			depositForm.setDepositId(depositLists.get(0).getDepositId());
			model.put("depositLists", depositLists);
			mav = new ModelAndView("fdChangesListsForCust", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/showDistributionRecordsCust", method = RequestMethod.GET)
	public ModelAndView showDistributionRecordsCust(ModelMap model, Long id, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm, Long menuId) throws CustomException {

		ModelAndView mav = new ModelAndView();
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		List<Distribution> distributionList = fixedDepositDao.getDistributionList(id);

		if (distributionList != null) {
			Integer index = distributionList.size() - 1;
			Deposit deposit = fixedDepositDao.getDeposit(distributionList.get(index).getDepositId());
			String customerName = (String) fixedDepositDao.getDepositForInterestRate(id).get(0)[8];
			model.put("distributionList", distributionList);
			model.put("customerName", customerName);

			model.put("variableBalance", distributionList.get(index).getCompoundVariableAmt());
			model.put("fixedBalance", distributionList.get(index).getCompoundFixedAmt());
			model.put("totalBalance", distributionList.get(index).getTotalBalance());
			model.put("accountNumber", deposit.getAccountNumber());
			mav = new ModelAndView("distributonListsForCustomer", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/closeFDListCust", method = RequestMethod.GET)
	public ModelAndView closeFDListCust(ModelMap model, RedirectAttributes attributes, Long menuId)
			throws CustomException {

		ModelAndView mav = new ModelAndView();
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		Customer cus = customerDAO.getById(endUser.getCustomerId());
		// Customer cus = getUserDetails();
		Collection<Deposit> fixedDeposits = fixedDepositDao.getDeposits(cus.getId());
		if (fixedDeposits != null && fixedDeposits.size() > 0) {

			model.put("fixedDeposits", fixedDeposits);
			mav = new ModelAndView("customerClosingFDList", "model", model);

		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/closeFDCust", method = RequestMethod.GET)
	public ModelAndView closeFD(@RequestParam("id") Long id, ModelMap model, Long menuId, RedirectAttributes attributes)
			throws CustomException {
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		// Customer cus = customerDAO.getById(endUser.getCustomerId());
		Deposit deposit = fixedDepositDao.getDeposit(id);
		if (deposit != null) {
			if (deposit.getProductConfigurationId() != null) {
				ProductConfiguration productConfiguration = productConfigurationDAO
						.findById(deposit.getProductConfigurationId());
				if (productConfiguration != null) {
					if (productConfiguration.getIsPrematureClosingioAllowedForWithdraw() != null) {
						int isEligibleForClose = productConfiguration.getIsPrematureClosingioAllowedForWithdraw();
						if (isEligibleForClose == 1) {
							calculationService.closeDeposit(deposit, true);
						} else {

							attributes.addFlashAttribute("error",
									"Premature closing is not allowed for this deposit. You can not close the deposit.");
							return new ModelAndView("redirect:closeFDListCust");
							// MSG : Premature closing is not allowed for this deposit. You can not close
							// the deposit.

						}
					} else {
						attributes.addFlashAttribute("error", "Is Premature data not found");
						return new ModelAndView("redirect:closeFDListCust");
					}

				} else {
					attributes.addFlashAttribute("error", "Product not found");
					return new ModelAndView("redirect:closeFDListCust");
					// product not found

				}
			} else {
				attributes.addFlashAttribute("error", "Product id not found");
				return new ModelAndView("redirect:closeFDListCust");
				// product not found

			}

		} else {
			attributes.addFlashAttribute("error", "Deposit not found");
			return new ModelAndView("redirect:closeFDListCust");
			// Deposit not found
		}
		attributes.addFlashAttribute(Constants.TRANSACTIONID, fdService.generateRandomString());
		attributes.addFlashAttribute(Constants.TRANSACTIONDATE, DateService.getCurrentDate());
		attributes.addFlashAttribute(Constants.TRANSACTIONSTATUS, "Closed Successfully");
		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "/transactionReportListCust", method = RequestMethod.GET)
	public ModelAndView transactionReportListCust(ModelMap model, RedirectAttributes attributes,
			@ModelAttribute DepositForm depositForm, Long menuId) throws CustomException {
		ModelAndView mav = new ModelAndView();
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		EndUser user = getCurrentLoggedUserDetails();
		Customer customer = customerDAO.getByUserId(user.getUserName()).getSingleResult();
		Long id = customer.getId();
		List<DepositForm> depositLists = fixedDepositDao.getAllRegularAndAutoDepositsByCustomerId(id);
		if (depositLists != null && depositLists.size() > 0) {
			depositForm.setDepositId(depositLists.get(0).getDepositId());
			model.put("depositLists", depositLists);
			mav = new ModelAndView("fdChangesListsForCust", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/interestReport", method = RequestMethod.GET)
	public ModelAndView interestReport(ModelMap model, Long menuId) throws CustomException {

		ModelAndView mav = new ModelAndView();

		// ModelAndView mav = new ModelAndView();
		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		Customer user = customerDAO.getById(endUser.getCustomerId());

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

	@RequestMapping(value = "/distributionReportsCust", method = RequestMethod.GET)
	public ModelAndView fdListforHolderWiseReport(ModelMap model, RedirectAttributes attributes, Long menuId)
			throws CustomException {

		ModelAndView mav = new ModelAndView();

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		Customer user = customerDAO.getById(endUser.getCustomerId());

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

	@RequestMapping(value = "taxExemptionConfiguration", method = { RequestMethod.GET })
	public ModelAndView taxExemptionConfiguration(Model model, @RequestParam("menuId") Long menuId,
			@ModelAttribute TaxExemptionConfiguration taxExemptionConfiguration) {
		model = this.menuUtils(model, menuId);

		return new ModelAndView("taxExemptionConfiguration", "model", model);

	}

	@RequestMapping(value = "saveTaxExemptionConfiguration", method = { RequestMethod.POST })
	public ModelAndView saveTaxExemptionConfiguration(Model model, @RequestParam("menuId") Long menuId,
			@ModelAttribute TaxExemptionConfiguration taxExemptionConfiguration, RedirectAttributes attribute)
			throws ParseException {
		model = this.menuUtils(model, menuId);
		TaxExemptionConfigurationParser exemptionConfigurationDTO = new Gson().fromJson(
				taxExemptionConfiguration.getSaveExemptionConfigurationList(), TaxExemptionConfigurationParser.class);
		List<TaxExemptionConfigurationDTO> taxExemptionConfigurations = exemptionConfigurationDTO
				.getSaveExemptionConfigurationList();
		for (TaxExemptionConfigurationDTO taxExemptionConfigurationDTO : taxExemptionConfigurations) {
			TaxExemptionConfiguration exemptionConfiguration = new TaxExemptionConfiguration();
			SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
			Date effectiveParseredDate = smt.parse(taxExemptionConfigurationDTO.getEffectiveDate());
			exemptionConfiguration.setEffectiveDate(effectiveParseredDate);
			exemptionConfiguration.setExemptionAge(taxExemptionConfigurationDTO.getExemptionAge());
			exemptionConfiguration.setExemptionLimitAmount(taxExemptionConfigurationDTO.getExemptionLimitAmount());
			exemptionConfiguration
					.setExemptionComparisonSign(taxExemptionConfigurationDTO.getExemptionComparisonSign());
			exemptionConfiguration.setCreatedOn(new Date());
			exemptionConfiguration.setModifiedOn(new Date());
			taxExemptionConfigurationDAO.save(exemptionConfiguration);
		}
		attribute = updateTransaction("Saved Successfully", attribute);
		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "viewAndEditTaxExemptionConfiguration", method = { RequestMethod.GET })
	public ModelAndView viewAndEditTaxExemptionConfiguration(Model model, @RequestParam("menuId") Long menuId,
			@ModelAttribute TaxExemptionConfiguration taxExemptionConfiguration) {
		model = this.menuUtils(model, menuId);
		List<Date> effectiveDateList = taxExemptionConfigurationDAO.findByEffectiveDate();
		model.addAttribute("effectiveDateList", effectiveDateList);
		return new ModelAndView("viewAndEditTaxExemptionConfiguration", "model", model);

	}

	@RequestMapping(value = "findAllByDateTaxExemptionConfiguration", method = RequestMethod.GET, headers = "Accept=*/*", produces = "application/json")
	public @ResponseBody List<TaxExemptionConfiguration> findAllByDateTaxExemptionConfiguration(String effectiveDate)
			throws Exception {
		Date effectiveDateParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(effectiveDate);
		List<TaxExemptionConfiguration> exemptionConfigurations = taxExemptionConfigurationDAO
				.findByEffietiveDateTaxExemptionConfiguration(effectiveDateParse);
		for (TaxExemptionConfiguration taxExemptionConfiguration : exemptionConfigurations) {
			String sign = taxExemptionConfiguration.getExemptionComparisonSign();
			if (sign.equals(">")) {

			} else if (sign.equals("<")) {

			} else if (sign.equals("≥")) {
				taxExemptionConfiguration
						.setExemptionComparisonSign(TaxExemptionConstants.GREATER_THAN_EQUAL_TO.name());
			} else if (sign.equals("≤")) {
				taxExemptionConfiguration.setExemptionComparisonSign(TaxExemptionConstants.LESS_THAN_EQUAL_TO.name());
			}

		}
		return exemptionConfigurations;
	}

	@RequestMapping(value = "updateTaxExemptionConfiguration", method = { RequestMethod.POST })
	public ModelAndView updateTaxExemptionConfiguration(Model model, @RequestParam("menuId") Long menuId,
			@ModelAttribute TaxExemptionConfiguration taxExemptionConfiguration, RedirectAttributes attribute)
			throws ParseException {
		model = this.menuUtils(model, menuId);
		TaxExemptionConfiguration configuration = taxExemptionConfigurationDAO
				.findById(taxExemptionConfiguration.getId());
		configuration.setExemptionAge(taxExemptionConfiguration.getExemptionAge());
		configuration.setExemptionComparisonSign(taxExemptionConfiguration.getExemptionComparisonSign());
		configuration.setExemptionLimitAmount(taxExemptionConfiguration.getExemptionLimitAmount());
		configuration.setModifiedOn(new Date());
		taxExemptionConfigurationDAO.update(configuration);
		attribute = updateTransaction("Updated Successfully", attribute);
		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "/withdrawDeposit", method = RequestMethod.POST)
	public ModelAndView withdrawDeposit(ModelMap model, WithdrawForm withdrawForm, RedirectAttributes attributes,
			Long menuId) throws CustomException {

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		Customer cus = customerDAO.getById(endUser.getCustomerId());

		Distribution paymentDistribution = fixedDepositDao.withdrawDepositAmt(withdrawForm.getDepositId());
		ProductConfiguration _pc = null;
		List<DepositForm> deposit = paymentDAO.paymentAccountNumber(withdrawForm.getAccountNumber().trim());

		if (paymentDistribution == null) {
			return new ModelAndView("noDataFoundCusm", "model", model);
		}
		_pc = productConfigurationDAO.findById(Long.parseLong(deposit.get(0).getProductConfigurationId().toString()));
		if (_pc == null) {
			_pc = productConfigurationDAO.findByProductCode(deposit.get(0).getProductConfigurationId().toString());
		}
		if (_pc.getIsWithdrawAllowed() == 0) {

			model.put(Constants.ERROR, "Withdraw is not allowed for this deposit. you can not withdraw.");
			return new ModelAndView("depositPaymentSearch", "model", model);

			/*
			 * model.put(Constants.ERROR, Constants.withdrawLockingPeriodError); return new
			 * ModelAndView("withDrawFDSearch", "model", model);
			 */
		}
		List<AccountDetails> accList = accountDetailsDAO.findCurrentSavingByCustId(cus.getId());
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
				// Customer cus = getUserDetails();
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

		return new ModelAndView("redirect:successfullySaved");
	}

	@RequestMapping(value = "/fdCustomerListForWithdraw", method = RequestMethod.GET)
	public ModelAndView fdCustomerListForWithdraw(ModelMap model, RedirectAttributes attributes, Long menuId)
			throws CustomException {

		ModelAndView mav = new ModelAndView();

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
		Customer cus = customerDAO.getById(endUser.getCustomerId());
		List<HolderForm> depositHolderList = depositService.getAllDepositByCustomerId(cus.getId());

		if (depositHolderList != null) {

			model.put("depositHolderList", depositHolderList);
			mav = new ModelAndView("fdCustomerListForWithdraw", "model", model);
		} else {
			mav = new ModelAndView("noDataFoundCusm", "model", model);
		}

		return mav;
	}

	@RequestMapping(value = "/withdrawListCust", method = RequestMethod.GET)
	public ModelAndView withdrawListCust(@RequestParam Long id, ModelMap model, @ModelAttribute DepositForm depositForm,
			Long menuId) throws CustomException {

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("menuId", menuId);
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

	@RequestMapping(value = "/ornamentSubmission", method = RequestMethod.GET)
	public ModelAndView ornamentSubmission(Model model, @RequestParam("menuId") Long menuId) throws CustomException {

		model = this.menuUtils(model, menuId);
		model.addAttribute("fixedDepositForm", fixedDepositForm);

		return new ModelAndView("ornamentSubmission", "model", model);

	}

	@RequestMapping(value = "/ornamentSubmissionByCustomerId", method = RequestMethod.POST)
	public ModelAndView ornamentSubmissionByCustomerId(Model model, @ModelAttribute OrnamentsForm ornamentsForm,
			@RequestParam("customerId") Long customerId) throws CustomException {

		Customer customer = customerDAO.getById(customerId);
		List<Menu> menus = this.menus;
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String date = smt.format(DateService.getCurrentDateTime());
		List<OrnamentsMaster> ornaments = ornamentsMasterDAO.getAllOrnamentsMasterDetails();
		model.addAttribute("ornaments", ornaments);
		model.addAttribute("todaysDate", date);
		model.addAttribute("customer", customer);
		model.addAttribute("menus", menus);

		return new ModelAndView("ornamentSubmissionConfirm", "model", model);
	}

	@RequestMapping(value = "/ornamentSubmissionSave", method = RequestMethod.POST)
	public ModelAndView addOrnamentSubmission(Model model, @ModelAttribute OrnamentsMaster ornamentsMaster,
			RedirectAttributes attribute, @ModelAttribute OrnamentsForm ornamentsForm, @RequestParam Double[] myArray)
			throws CustomException {
		model.addAttribute("menus", menus);
		OrnamentSubmissionMaster ornamentSubmissionMaster = new OrnamentSubmissionMaster();
		ornamentSubmissionMaster.setCustomerId(ornamentsForm.getCustomerId());
		ornamentSubmissionMaster.setGoldRate(ornamentsForm.getGoldRate());
		ornamentSubmissionMaster.setSubmissionDate(ornamentsForm.getSubmissionDate());
		ornamentSubmissionMaster.setTotalPrice(ornamentsForm.getTotalPrice());
		ornamentSubmissionMaster.setTotalWeight(ornamentsForm.getTotalWeight());
		ornamentSubmissionMasterDAO.save(ornamentSubmissionMaster);
		Long ornamentSubmissionMasterId = ornamentSubmissionMaster.getId();
		for (int i = 0; i < myArray.length; i++) {
			OrnamentSubmissionDetails ornamentSubmissionDetails = new OrnamentSubmissionDetails();
			ornamentSubmissionDetails.setCarat(ornamentsForm.getCarat());
			ornamentSubmissionDetails.setComment(ornamentsForm.getComment());
			ornamentSubmissionDetails.setOrnamentSubmissionMasterId(ornamentSubmissionMasterId);
			Double amount = myArray[i] * ornamentsForm.getGoldRate();
			ornamentSubmissionDetails.setPrice(amount);
			ornamentSubmissionDetails.setPurity(ornamentsForm.getPurity());
			ornamentSubmissionDetails.setWeight(myArray[i]);
			ornamentSubmissionDetailsDAO.save(ornamentSubmissionDetails);
		}
		attribute = updateTransaction("Saved Successfully", attribute);
		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public ModelAndView editProfile(Model model, @RequestParam("id") Long id, @RequestParam("menuId") Long menuId,
			RedirectAttributes attributes) throws CustomException {
		model = this.menuUtils(model, menuId);
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

		model.addAttribute("endUserForm", endUserForm);

		return new ModelAndView("editProfile", "model", model);

	}

	@RequestMapping(value = "/editPWD", method = RequestMethod.GET)
	public ModelAndView editAdminPWD(Model model, @RequestParam("id") Long id, RedirectAttributes attributes,
			@RequestParam("menuId") Long menuId) throws CustomException {
		model = this.menuUtils(model, menuId);
		EndUser userProfile = endUserDAOImpl.findId(id);

		endUserForm.setId(userProfile.getId());

		endUserForm.setTransactionId(userProfile.getTransactionId());

		model.addAttribute("endUserForm", endUserForm);

		return new ModelAndView("editPWD", "model", model);

	}

	@RequestMapping(value = "/updateEditPWD", method = RequestMethod.POST)
	public ModelAndView updateEditPWD(Model model, @ModelAttribute EndUserForm endUserForm, BindingResult result,
			RedirectAttributes attributes) throws CustomException {

		EndUser endUser = endUserDAOImpl.findId(endUserForm.getId());

		endUser.setId(endUserForm.getId());

		endUser.setPassword(endUserForm.getNewPassword());
		endUser.setTransactionId(endUserForm.getTransactionId());

		endUserDAOImpl.update(endUser);

		model.addAttribute("endUserForm", endUserForm);
		attributes = updateTransaction("Update Successfully", attributes);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		return new ModelAndView("redirect:successfullySaved");

	}

	@RequestMapping(value = "/confirmEditProfile", method = RequestMethod.POST)
	public ModelAndView confirmEditProfile(Model model, @ModelAttribute EndUserForm endUserForm)
			throws CustomException {

		EndUser endUser = this.currentLoggedInUser;
		List<Menu> menus = this.menus;
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		model.addAttribute("endUserForm", endUserForm);
		model.addAttribute("menus", menus);
		return new ModelAndView("confirmEditProfile", "model", model);

	}

	@RequestMapping(value = "/updateProfileDetails", method = RequestMethod.POST)
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

		List<Menu> menus = this.menus;
		model.put("endUserForm", endUserForm);

		attributes = updateTransaction("Update Successfully", attributes);
		model.addAttribute("menus", menus);
		model.addAttribute("endUser", endUser);
		return new ModelAndView("redirect:successfullySaved");

	}

}
