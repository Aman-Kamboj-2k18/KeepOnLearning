package annona.scheduler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase;

import annona.dao.AccountDetailsDAO;
import annona.dao.CustomerDAO;
import annona.dao.DepositHolderDAO;
import annona.dao.DepositModificationDAO;
import annona.dao.DepositModificationDAOImpl;
import annona.dao.EndUserDAO;
import annona.dao.FDRatesDAO;
import annona.dao.FixedDepositDAO;
import annona.dao.FundTransferDAO;
import annona.dao.InterestDAO;
import annona.dao.ModeOfPaymentDAO;
import annona.dao.OverdraftIssueDAO;
import annona.dao.DepositSummaryDAO;
import annona.dao.PaymentDAO;
import annona.dao.PaymentDistributionDAO;
import annona.dao.ProductConfigurationDAO;
import annona.dao.RatesDAO;
import annona.dao.TDSDAO;
import annona.dao.TransactionDAO;
import annona.dao.UnSuccessfulRecurringDAO;
import annona.domain.AccountDetails;
import annona.domain.BankConfiguration;
import annona.domain.BankPayment;

import annona.domain.Customer;
import annona.domain.DDPayment;
import annona.domain.Deposit;
import annona.domain.DepositBeforeRenew;
import annona.domain.DepositHolder;
import annona.domain.DepositModification;
import annona.domain.DepositSummary;
import annona.domain.DepositTDS;
import annona.domain.Distribution;
import annona.domain.EndUser;
import annona.domain.FDRates;
import annona.domain.FundTransfer;
import annona.domain.GST;
import annona.domain.Interest;
import annona.domain.ModeOfPayment;
import annona.domain.ModificationPenalty;
import annona.domain.OverdraftIssue;
import annona.domain.OverdraftPayment;
import annona.domain.Payment;
import annona.domain.ProductConfiguration;
import annona.domain.Rates;
import annona.domain.RenewDeposit;
import annona.domain.TDS;
import annona.domain.Transaction;
import annona.domain.UnSuccessfulOverdraft;
import annona.domain.UnSuccessfulRecurringDeposit;
import annona.domain.WithdrawDeposit;
import annona.event.BankPaymentEvent;
import annona.event.BankPaymentEventPublisher;
import annona.event.CloseDepositEvent;
import annona.event.InterestCalculationEvent;
import annona.exception.CustomException;
import annona.form.AutoDepositForm;
import annona.form.FixedDepositForm;
import annona.services.DateService;
import annona.services.FDService;
import annona.services.ImageService;
import annona.services.LedgerService;
import annona.utility.Constants;
import annona.utility.Event;
import annona.services.CalculationService;

public class NotificationsScheduler {

	@Autowired
	ApplicationContext context;

	@Autowired
	FixedDepositDAO fixedDepositDAO;

	@Autowired
	MailSender mailSender;

	@Autowired
	TransactionDAO transactionDAO;

	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	FDService fdService;

	@Autowired
	FDRatesDAO fdRatesDAO;

	@Autowired
	FundTransferDAO fundTransferDAO;

	@Autowired
	RatesDAO ratesDAO;

	@Autowired
	PaymentDAO paymentDAO;

	@Autowired
	DepositHolderDAO depositHolderDAO;

	@Autowired
	AccountDetailsDAO accountDetailsDAO;

	@Autowired
	PaymentDistributionDAO paymentDistributionDAO;

	@Autowired
	FixedDepositForm fixedDepositForm;

	@Autowired
	InterestDAO interestDAO;

	@Autowired
	TDSDAO tdsDAO;

	@Autowired
	EndUserDAO endUserDAOImpl;

	@Autowired
	DepositModificationDAO depositModificationDAO;

	@Autowired
	DepositSummaryDAO depositSummaryDAO;

	@Autowired
	UnSuccessfulRecurringDAO unSuccessfulRecurringDAO;

	@Autowired
	CalculationService calculationService;
	
	@Autowired
	LedgerService ledgerService;
	
	@Autowired
	ProductConfigurationDAO productConfigurationDAO;
	
	@Autowired
	OverdraftIssueDAO overdraftIssueDAO;
	
	@Autowired
	ModeOfPaymentDAO modeOfPaymentDAO;


	public void calculateInterest() {
		
		// System.out.println(fdService.getTransactionId());

		Date lastDateOfMonth = DateService.getLastDateOfCurrentMonth();

		// everyday at 10PM it will be fired
		// CRON Exception is "0 0 22 * * ?"
		//BankConfiguration bankConfiguration = ratesDAO.findAll();

		if (DateService.getDaysBetweenTwoDates(lastDateOfMonth, DateService.getCurrentDate()) != 0)
			return;

		// Calculate the Interest
		calculationService.calculateInterest();
	
	}

	// this calculation will start just after the interest calculation
	public void calculatePayOff() throws CustomException {

		System.out.println("Going to start PayOff for " + DateService.getCurrentDate() + "...");
		// Get all open deposits.
		List<Deposit> deposits = fixedDepositDAO.getOpenDeposits();

		for (int i = 0; i < deposits.size(); i++) {
			Deposit deposit = deposits.get(i);

			if (deposit.getDepositCategory() != null && deposit.getDepositCategory().equalsIgnoreCase("REVERSE-EMI"))
				continue;
			Date payOffDueDate = deposits.get(i).getPayOffDueDate();

			// pay off not defined
			if (payOffDueDate == null)
				continue;

			if (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), payOffDueDate) == 0)
				calculationService.calculatePayOff(deposit);
		}

		System.out.println("PayOff work is done");
	}
	
	public void compoundInterest() throws CustomException {
		
		System.out.println("Going to start Interest Compounding for " + DateService.getCurrentDate() + "...");

		Date lastDateOfMonth = DateService.getLastDateOfCurrentMonth();

		// everyday at 10PM it will be fired
		// CRON Exception is "0 0 22 * * ?"
		//BankConfiguration bankConfiguration = ratesDAO.findAll();

		if (DateService.getDaysBetweenTwoDates(lastDateOfMonth, DateService.getCurrentDate()) != 0)
			return;

		// Calculate the Interest
		calculationService.compoundInterest();
		
		System.out.println("Interest Compounding is done");
	
	}


	public void calculateModificationPenalty() throws CustomException {

		Date lastDateOfMonth = DateService.getLastDateOfCurrentMonth();

		// everyday at 10PM it will be fired
		// CRON Exception is "0 0 22 * * ?"
		if (DateService.getDaysBetweenTwoDates(lastDateOfMonth, DateService.getCurrentDate()) != 0)
			return;

		Date dt = DateService.getCurrentDate();
		int currentMonth = DateService.getMonth(dt);
		int currentYear = DateService.getYear(dt);

//		// Penalty deduction
//		BankConfiguration bankConfiguration = ratesDAO.findAll();
//		Integer maxLimitOfModification = bankConfiguration.getMaximumTimeOfModification();
//		String modificationPenaltyBasis = bankConfiguration.getModificationBasedOn();
//		Double penaltyPercentage = bankConfiguration.getPanality();
//		Integer penaltyFltAmt = bankConfiguration.getPenalityFlatAmount();
//		if(maxLimitOfModification == null)
//			return;

		String sql = "";
		// Get all open deposits.
		List<Deposit> deposits = fixedDepositDAO.getOpenDeposits();

		for (int i = 0; i < deposits.size(); i++) {
			Deposit deposit = deposits.get(i);
			Double prevPenaltyDue = 0d;

			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
			
			// get the Product Configuration
			ProductConfiguration productConfiguration = productConfigurationDAO.findById(deposit.getProductConfigurationId());
				
			Integer maxLimitOfModification = productConfiguration.getMaximumLimitOfModification();
			String modificationPenaltyBasis = productConfiguration.getModificationBasis();
			Float penaltyPercentage = productConfiguration.getModificationPenaltyPercent();
			Float penaltyFltAmt = productConfiguration.getModificationPenaltyFlatAmount();
			if(maxLimitOfModification == null)
				return;
			
			// Fetch last Penalty of the deposit
			ModificationPenalty modPenalty = depositModificationDAO.getLastModificationPenalty(deposit.getId());
			if (modPenalty != null)
				prevPenaltyDue = modPenalty.getPenaltyDue();

			int modificationCount = 0;
			List<Object[]> objModificationList = depositModificationDAO
					.getByDepositModificationInCurrentFinancialYear(deposit.getId());

			Long count = 0l;
			if(objModificationList != null && objModificationList.size()>0)
			{
				if (modificationPenaltyBasis.equalsIgnoreCase("Monthly")) {
					count = getMonthlyModificationCount(objModificationList);
				} else if (modificationPenaltyBasis.equalsIgnoreCase("Quarterly")) {
					count = getQuarterlyModificationCount(objModificationList);
				} else if (modificationPenaltyBasis.equalsIgnoreCase("Half Yearly")) {
					count = getHalfYearlyModificationCount(objModificationList);
				} else if (modificationPenaltyBasis.equalsIgnoreCase("Yearly")) {
					count = getHalfYearlyModificationCount(objModificationList);
				} else if (modificationPenaltyBasis.equalsIgnoreCase("Full Tenure")) {
	
				}
			}
			int totalModCount = Integer.parseInt(count.toString());
			if (maxLimitOfModification < totalModCount) {
				int excessModification = totalModCount - maxLimitOfModification;
				Distribution lastDistribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());

				// Get last fixed and variable interest
				Double previousBalFixedInterest = lastDistribution.getBalanceFixedInterest();
				Double previousBalVariableInterest = lastDistribution.getBalanceVariableInterest();

				// We can't touch fixed interest as it is used as corpus
			    Double availableInterest = 0d;
				//ConsolidatedInterest consolidatedInterest = 0; //interestSummaryDAO.getConsolidatedInterest(deposit.getId());
//				if (consolidatedInterest != null)
//					availableInterest = consolidatedInterest.getVariableInterestBalance();

				// Double availableInterest = previousBalVariableInterest;
				Double penaltyAmount = 0d;

				if (penaltyPercentage != null) {
					penaltyAmount = ((availableInterest * penaltyPercentage / 100) * excessModification);
				}
				if (penaltyFltAmt != null) {
					penaltyAmount = availableInterest - (penaltyFltAmt * excessModification);
				}

				penaltyAmount = penaltyAmount + prevPenaltyDue;

				Double penaltyAdjusted = (penaltyAmount > availableInterest) ? availableInterest : penaltyAmount;
				Double penaltyDue = penaltyAmount - penaltyAdjusted;

				penaltyAdjusted = fdService.round(penaltyAdjusted, 2);
				penaltyDue = fdService.round(penaltyDue, 2);

				// save in modification penalty
				ModificationPenalty modificationPenalty = new ModificationPenalty();
				modificationPenalty.setDepositId(deposit.getId());
				modificationPenalty.setPenaltyDate(dt);
				modificationPenalty.setPenaltyAmount(penaltyAmount);
				modificationPenalty.setPenaltyAdjusted(penaltyAdjusted);
				modificationPenalty.setPenaltyAdjustedFromFixedInterest(0.0);
				modificationPenalty.setPenaltyAdjustedFromVariableInterest(penaltyAdjusted);
				modificationPenalty.setPenaltyDue(penaltyDue);
				modificationPenalty = depositModificationDAO.saveModificationPenalty(modificationPenalty);

				
				// Insert penalty in PaymentDistribution and Holderwise PaymentDistribution and DepositSummary table
				// -----------------------------------------
				Distribution distribution1 = calculationService.insertInPaymentDistribution(deposit, depositHolderList, null, null, 
						Constants.PENALTY1, null, null, null, null, null, null, penaltyAdjusted);
				
				DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.PENALTY1, null, null, null, null, null, depositHolderList, null, null, penaltyAdjusted);
				// -----------------------------------------
				
				// Insert in Journal & Ledger
				//-----------------------------------------------------------				
				ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(Constants.InternalTransfer);

				ledgerService.insertJournal(deposit.getId(), fdService.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(),
						null, null, Event.MODIFICATION_PENALTY.getValue(), penaltyAdjusted, mop.getId(),
						depositSummary.getTotalPrincipal(), null);
				//-----------------------------------------------------------
			}
		}
	}

	public Long getMonthlyModificationCount(List<Object[]> objModificationList) {
		Date dt = DateService.getCurrentDate();
		int currentMonth = DateService.getMonth(dt);
		int currentYear = DateService.getYear(dt);

		long totalcount = 0;
		long year = 0l;
		long month = 0l;
		long count = 0l;
		String modificationNo = "";
		if (objModificationList == null)
			return 0l;

		for (int j = 0; j < objModificationList.size(); j++) {
			if (objModificationList.get(j)[0] != null)
				modificationNo = objModificationList.get(j)[0].toString();
			if (objModificationList.get(j)[1] != null)
				year = (long) (Double.parseDouble((objModificationList.get(j)[1].toString())));
			if (objModificationList.get(j)[2] != null)
				month = (long) (Double.parseDouble((objModificationList.get(j)[2].toString())));
			if (objModificationList.get(j)[3] != null)
				count = (long) (Double.parseDouble((objModificationList.get(j)[3].toString())));
			else
				count = 0l;

			// Java returns the index of month and sql returns the exact month
			if (year == currentYear && month == (currentMonth + 1)) {
				totalcount = totalcount + 1;
			}
		}

		return totalcount;
	}

	public Long getQuarterlyModificationCount(List<Object[]> objModificationList) {
		Date dt = DateService.getCurrentDate();
		int currentYear = DateService.getYear(dt);
		int quarter = DateService.getQuarterNo(dt);
		int startMonth = 0, endMonth = 11;

		if (quarter == 1) {
			startMonth = 4;
			endMonth = 6;
		} else if (quarter == 2) {
			startMonth = 7;
			endMonth = 9;
		} else if (quarter == 3) {
			startMonth = 10;
			endMonth = 12;
		} else if (quarter == 4) {
			startMonth = 1;
			endMonth = 3;
		}
		Long totalCount = 0l;
		Long year = 0l;
		Long month = 0l;
		Long count = 0l;
		String modificationNo = "";
		if (objModificationList == null)
			return 0l;

		for (int j = 0; j < objModificationList.size(); j++) {

			if (objModificationList.get(j)[0] != null)
				modificationNo = objModificationList.get(j)[0].toString();
			if (objModificationList.get(j)[1] != null)
				year = (long) (Double.parseDouble((objModificationList.get(j)[1].toString())));
			if (objModificationList.get(j)[2] != null)
				month = (long) (Double.parseDouble((objModificationList.get(j)[2].toString())));
			if (objModificationList.get(j)[3] != null)
				count = (long) (Double.parseDouble((objModificationList.get(j)[3].toString())));
			else
				count = 0l;

			if (year == currentYear && (month >= startMonth && month <= endMonth)) {
				totalCount = totalCount + 1;
			}
		}

		return totalCount;
	}

	public Long getHalfYearlyModificationCount(List<Object[]> objModificationList) {
		Date dt = DateService.getCurrentDate();
		int currentYear = DateService.getYear(dt);
		int currentMonth = DateService.getMonth(dt);
		int startMonth = 0, endMonth = 11;

		int half = 1;
		if (currentMonth >= 4 && currentMonth <= 9) {
			half = 1;
		} else if (currentMonth >= 10 || currentMonth <= 3) {
			half = 2;
		}

		Long totalCount = 0l;
		Long year = 0l;
		Long month = 0l;
		Long count = 0l;
		String modificationNo = "";
		if (objModificationList == null)
			return 0l;

		for (int j = 0; j < objModificationList.size(); j++) {

			if (objModificationList.get(j)[0] != null)
				modificationNo = objModificationList.get(j)[0].toString();
			if (objModificationList.get(j)[1] != null)
				year = (long) (Double.parseDouble((objModificationList.get(j)[1].toString())));
			if (objModificationList.get(j)[2] != null)
				month = (long) (Double.parseDouble((objModificationList.get(j)[2].toString())));
			if (objModificationList.get(j)[3] != null)
				count = (long) (Double.parseDouble((objModificationList.get(j)[3].toString())));
			else
				count = 0l;

			if (year == currentYear && half == 1 && (month >= 4 && month <= 9)) {
				totalCount = totalCount + 1;
			} else if (half == 2) {
				if (currentMonth <= 3) {
					if ((year == (currentYear - 1)) && month >= 10) {
						totalCount = totalCount + 1;
					}
					if ((year == (currentYear)) && month <= 3) {
						totalCount = totalCount + 1;
					}
				}

			}
		}

		return totalCount;
	}

	public Long getYearlyModificationCount(List<Object[]> objModificationList) {
		Date dt = DateService.getCurrentDate();
		int currentYear = DateService.getYear(dt);
		int currentMonth = DateService.getMonth(dt);

		Long totalCount = 0l;

		Long year = 0l;
		Long month = 0l;
		Long count = 0l;
		String modificationNo = "";
		if (objModificationList == null)
			return 0l;

		for (int j = 0; j < objModificationList.size(); j++) {

			if (objModificationList.get(j)[0] != null)
				modificationNo = objModificationList.get(j)[0].toString();
			if (objModificationList.get(j)[1] != null)
				year = (long) (Double.parseDouble((objModificationList.get(j)[1].toString())));
			if (objModificationList.get(j)[2] != null)
				month = (long) (Double.parseDouble((objModificationList.get(j)[2].toString())));
			if (objModificationList.get(j)[3] != null)
				count = (long) (Double.parseDouble((objModificationList.get(j)[3].toString())));
			else
				count = 0l;

			if (year == currentYear - 1 && (month >= 4 && month <= 12)) {
				totalCount = totalCount + count;
			} else if ((year == currentYear) && (month <= 3)) {
				if (currentMonth <= 3) {
					if ((year == (currentYear - 1)) && month >= 10) {
						totalCount = totalCount + count;
					}
					if ((year == (currentYear)) && month <= 3) {
						totalCount = totalCount + count;
					}
				}

			}
		}
			
		return totalCount;
	}

	public void transferMoneyOnMaturity() throws CustomException {
		
		calculationService.transferMoneyOnMaturity();
	}

	

	private Double getMaturityAmount(Deposit deposit) {
		List<Date> depositDates = fdService.getDepositDates(DateService.getCurrentDate(), deposit.getNewMaturityDate(),
				deposit);
		Double totalDepositAmount = deposit.getCurrentBalance() + deposit.getDepositAmount() * depositDates.size();

		if(fixedDepositForm.getPaymentType()==null)
			fixedDepositForm.setPaymentType(deposit.getPaymentType());
		
		if(fixedDepositForm.getDeductionDay()== null)
			fixedDepositForm.setDeductionDay(DateService.getDayNoFromDate(deposit.getDueDate()));
		
		if(fixedDepositForm.getFdAmount()== null)
			fixedDepositForm.setFdAmount(deposit.getDepositAmount());
		
//		List<Interest> interestList = fdService.getInterestBreakup(DateService.getCurrentDate(),
//				deposit.getNewMaturityDate(), fixedDepositForm, deposit.getModifiedInterestRate(), 0);
//		Float totalInterest = fdService.getTotalInterestAmount(interestList);

//		fixedDepositForm.setEstimateInterest(Float.parseFloat(((Double) FDService.round(totalInterest, 2)).toString()));
//		Double maturityAmount = totalDepositAmount + totalInterest;
//
//		return maturityAmount;
		
		return 0.0;
	}

	@Transactional
	public void autoDeduction() throws CustomException {
		System.out.println("EMI Deduction from Recurring....");
		List<Deposit> deposits = fixedDepositDAO.getOpenDeposits();

		Date todaysDate = DateService.getCurrentDateTime();
		SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
		String todaysDateStr = smt.format(todaysDate);

		for (int i = 0; i < deposits.size(); i++) {

			Deposit deposit = deposits.get(i);

			if (deposit.getDepositClassification() != null
					&& !deposit.getDepositClassification().equalsIgnoreCase(Constants.RECURRINGDEPOSIT)) {
				continue;
			}
			// If stop payment, then don't go further,
			// don't change the due date also as
			// we are changing this from deposit modification
			if (deposit.getStopPayment() != null && deposit.getStopPayment() == 1) {
				continue;
			}
			if (deposit.getDepositCategory() != null
					&& deposit.getDepositCategory().equalsIgnoreCase(Constants.REVERSEEMI)) {
				continue;
			}

			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
			Date dueDate = deposit.getDueDate();
			String dueDateStr = smt.format(dueDate);
			String depositCreatedDate = smt.format(deposit.getCreatedDate());
			boolean isDeducted = false;

			Long depositId = deposit.getId();

			if (!dueDateStr.equals(depositCreatedDate)) {
				// overriding modification data

				DepositModification modification = depositModificationDAO.getLastByDepositId(depositId);
				if (modification == null) {
					modification = new DepositModification();
					modification.setDepositAmount(deposit.getDepositAmount());
					modification.setPaymentMode(deposit.getPaymentMode());
					modification.setPaymentType(deposit.getPaymentType());
				}
				if(dueDateStr.equals(todaysDateStr))
				{
					if (modification.getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)
							|| modification.getPaymentMode().equals("")) {
	
						AccountDetails accDetails = accountDetailsDAO.findByAccountNo(deposit.getLinkedAccountNumber());
	
						if (accDetails != null) {
	
							String accountNo = accDetails.getAccountNo();
							//String accountType = accDetails.getAccountType();
							
							Double modDepositAmount = depositModificationDAO.getModifiedDepositAmount(depositId);
							Double depositAmount = (modDepositAmount == null) ? modification.getDepositAmount()
									: modDepositAmount;
							Double balanceAmount = accDetails.getAccountBalance();
	
							DepositHolder depositHolder = depositHolderDAO.getDepositHolders(deposit.getId()).get(0);
							Long customerId = depositHolder.getCustomerId();
	
							Double deductAmount = balanceAmount - depositAmount;
							// String action = "Payment";
							Customer cus = customerDAO.getById(customerId);
	
							Float fixedRate = 0f;
							List<Rates> rates = ratesDAO.findByCategory(cus.getCategory()).getResultList();
	
							if (rates.size() > 0) {
								fixedRate = rates.get(0).getFdFixedPercent();
							}
	
							/*********
							 * Updating account table ands distribution table.....
							 *********/
							if (balanceAmount >= depositAmount) {
								accDetails.setAccountBalance(deductAmount);
								accountDetailsDAO.updateUserAccountDetails(accDetails);
								isDeducted = true;
							}
	
							else {
								try {
	
									String tex = Constants.CUSTOMERUNSUCCESSPAYMENTSUBJECT;
									SimpleMailMessage emails = new SimpleMailMessage();
									emails.setTo(cus.getEmail());
									emails.setSubject(tex);
									emails.setText(Constants.HELLO + cus.getCustomerName() + Constants.PAYMENTKBODY1
											+ Constants.BANKSIGNATURE);
									mailSender.send(emails);
									SimpleMailMessage emails1 = new SimpleMailMessage();
									emails1.setTo(cus.getEmail());
	
								} catch (Exception e) {
									System.out.println(e.getMessage() + e);
	
								}
	
								UnSuccessfulRecurringDeposit unSuccessfulDeposit = new UnSuccessfulRecurringDeposit();
								unSuccessfulDeposit.setUnsuccessPaymentDate(todaysDate);
								unSuccessfulDeposit.setCustomerId(customerId);
								unSuccessfulDeposit.setDepositId(depositId);
								unSuccessfulDeposit.setPendingAmount(depositAmount);
								unSuccessfulDeposit.setAccountNumber(accDetails.getAccountNo());
								unSuccessfulDeposit.setAccountType(accDetails.getAccountType());
								unSuccessfulRecurringDAO.insertUnSuccessfulRecurringDeposit(unSuccessfulDeposit);
	
								// update due date
								deposit = this.updateDueDate(deposit, modification);
								break;
							}
	
							if (isDeducted) {
								/******* Inserting in Payment Table... *******/
								ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(modification.getPaymentMode());

								String transactionId = fdService.generateRandomString();
								
								Payment payment = new Payment();
	
								payment.setAmountPaid(Double.valueOf(depositAmount));
								payment.setDepositId(depositId);
								payment.setDepositHolderId(depositHolder.getId());
								payment.setLinkedAccNoForFundTransfer(deposit.getLinkedAccountNumber());
								payment.setPaymentModeId(mop.getId());
								payment.setPaymentMode(mop.getPaymentMode());
								payment.setPaymentDate(todaysDate);
								payment.setTransactionId(transactionId);
								payment.setCreatedBy(Constants.SYSTEM);
	
								Payment pay = paymentDAO.insertPayment(payment);
	
								/*******
								 * Updating PaymentDistribution table....
								 *******/
	
								
								//Insert in Distributor and HolderWiseDistributor
								calculationService.insertInPaymentDistribution(deposit, depositHolderList,depositAmount, pay.getId(),  Constants.PAYMENT, 
										null, null, null, null, null, null, null);
	
								//Insert in DepositSummary and HolderwiseDepositSummary
								DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.PAYMENT, depositAmount, null, null,
										null, null, depositHolderList, null, null, null);
								
								// Insert in Journal & Ledger
								//-----------------------------------------------------------
								// -----------------------------------------------------------
								ledgerService.insertJournal(deposit.getId(), fdService.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(),
										accountNo, deposit.getAccountNumber(), Event.RECURRING_EMI.getValue(), depositAmount, mop.getId(),
										depositSummary.getTotalPrincipal(), transactionId);
							
								//-----------------------------------------------------------	
							
								/*******
								 * Updating Due Date in Deposit table....
								 *******/
								deposit = this.updateDueDate(deposit, modification);
	
								
							}
						}
					}
					else if(modification.getPaymentMode().equalsIgnoreCase("CashPayment") ||
							modification.getPaymentMode().equalsIgnoreCase("DD") ||
							modification.getPaymentMode().equalsIgnoreCase("Cheque") ||
							modification.getPaymentMode().equalsIgnoreCase("Card Payment") ||
							modification.getPaymentMode().equalsIgnoreCase("Net Banking"))
						{
							Double totalPaidAmount = 0d;
							boolean isPaid = false;
						
							// Check the payment is done in the due date
							List<Payment> payments = paymentDAO.getPaymentDetails(depositId, dueDate, dueDate);
							for (int j = 0; j < payments.size(); j++) {
								totalPaidAmount = totalPaidAmount + payments.get(j).getAmountPaid();
							}
							if(totalPaidAmount>=deposit.getDepositAmount())
								isPaid = true;
							else
							{
								
								UnSuccessfulRecurringDeposit unSuccessfulDeposit = new UnSuccessfulRecurringDeposit();
								unSuccessfulDeposit.setUnsuccessPaymentDate(todaysDate);
								unSuccessfulDeposit.setCustomerId(fdService.getPrimaryCustomerId(depositHolderList));
								unSuccessfulDeposit.setDepositId(depositId);
								unSuccessfulDeposit.setPendingAmount(deposit.getDepositAmount());
								unSuccessfulDeposit.setAccountNumber(null);
								unSuccessfulDeposit.setAccountType(null);
								unSuccessfulRecurringDAO.insertUnSuccessfulRecurringDeposit(unSuccessfulDeposit);
	
								// update due date
								deposit = this.updateDueDate(deposit, modification);
							}

						}
					
				}
			}

		}

	}
	
	public Deposit updateDueDate(Deposit deposit, DepositModification modification) {
			
			Date dueDate = DateService.getCurrentDateTime();
			Integer deductionDay = DateService.getDayNoFromDate(dueDate);
		
			Date maturityDate = deposit.getNewMaturityDate() != null ? deposit.getNewMaturityDate()
					: deposit.getMaturityDate();
			
			int monthsToAdd = 0;
			if (modification.getPaymentType().equalsIgnoreCase(Constants.MONTHLY)) {
				monthsToAdd = 1;
			} else if (modification.getPaymentType().equalsIgnoreCase(Constants.QUARTERLY)) {
				monthsToAdd = 3;
			} else if (modification.getPaymentType().equalsIgnoreCase(Constants.FULLYEARLY)
					|| modification.getPaymentType().equalsIgnoreCase(Constants.ANNUALLY))
				monthsToAdd = 12;
			else if (modification.getPaymentType().equalsIgnoreCase(Constants.HALFYEARLY))
				monthsToAdd = 6;

			dueDate = DateService.addDays(DateService.generateMonthsDate(dueDate, monthsToAdd), 1);
			dueDate = DateService.setDate(dueDate, deductionDay);
					

			if (DateService.getDaysBetweenTwoDates(dueDate, maturityDate) > 0) {
				deposit.setDueDate(dueDate);
				deposit = fixedDepositDAO.updateFD(deposit);
			}

			return deposit;
		
//		Date dueDate = DateService.getCurrentDateTime();
//		Integer deductionDay = DateService.getDayNoFromDate(dueDate);
//
//		if (modification.getPaymentType().equalsIgnoreCase("One-Time")
//				|| modification.getPaymentType().equalsIgnoreCase("One Time")) {
//			// dueDate will be the same date of deposit
//		} else if (modification.getPaymentType().equalsIgnoreCase("Monthly")) {
//			dueDate = DateService.addMonths(dueDate,1);
//			//dueDate = DateService.generateMonthsDate(dueDate, 1);
//			dueDate = DateService.setDate(dueDate, deductionDay);
//
//		} else if (modification.getPaymentType().equalsIgnoreCase("Quarterly")) {
//
//			dueDate = DateService.getQuaterlyDeductionDate(deductionDay);
//
//		} else if (modification.getPaymentType().equalsIgnoreCase("Half Yearly")) {
//			
//			dueDate = DateService.getHalfYearlyDeductionDate(deductionDay);
//
//		} else if (modification.getPaymentType().equalsIgnoreCase("Annually")) {
//			/*
//			 * dueDate = DateService.generateMonthsDate(dueDate, 12); dueDate =
//			 * DateService.setDate(dueDate, day);
//			 */
//
//			dueDate = DateService.getAnnualDeductionDate(deductionDay);
//		}
//		else
//			dueDate = null;
//	
//		
//		Date maturityDate = deposit.getNewMaturityDate() != null ? deposit.getNewMaturityDate()
//				: deposit.getMaturityDate();
//		if (DateService.getDaysBetweenTwoDates(dueDate, maturityDate) > 0) {
//			deposit.setDueDate(dueDate);
//			deposit = fixedDepositDAO.updateFD(deposit);
//		}
//
//		return deposit;
	}
	
    public Deposit updateDueDateFromRecurringPayment(Deposit deposit, DepositModification modification) {
		
		Date dueDate = deposit.getDueDate();
		Integer deductionDay = DateService.getDayNoFromDate(dueDate);
	
		Date maturityDate = deposit.getNewMaturityDate() != null ? deposit.getNewMaturityDate()
				: deposit.getMaturityDate();
		
		int monthsToAdd = 0;
		if (modification.getPaymentType().equalsIgnoreCase(Constants.MONTHLY)) {
			monthsToAdd = 1;
		} else if (modification.getPaymentType().equalsIgnoreCase(Constants.QUARTERLY)) {
			monthsToAdd = 3;
		} else if (modification.getPaymentType().equalsIgnoreCase(Constants.FULLYEARLY)
				|| modification.getPaymentType().equalsIgnoreCase(Constants.ANNUALLY))
			monthsToAdd = 12;
		else if (modification.getPaymentType().equalsIgnoreCase(Constants.HALFYEARLY))
			monthsToAdd = 6;

		dueDate = DateService.addDays(DateService.generateMonthsDate(dueDate, monthsToAdd), 1);
		dueDate = DateService.setDate(dueDate, deductionDay);
				

		if (DateService.getDaysBetweenTwoDates(dueDate, maturityDate) > 0) {
			deposit.setDueDate(dueDate);
			deposit = fixedDepositDAO.updateFD(deposit);
		}

		return deposit;
}

	public void createSweepDeposit() {
		System.out.println("Going to create Auto Deposit on " + DateService.getCurrentDate() + "...");

		fdService.createSweepDeposits();

		System.out.println("Auto Deposits updated");
	}

	public void deductAnnuityEMI() throws CustomException {
		System.out.println("Going to deduct EMI for Annuity on " + DateService.getCurrentDate() + "...");

		// fdService.createAutoDeposit();
		// Get all open deposits.
		List<Deposit> deposits = fixedDepositDAO.getOpenReverseEMIDeposits();

		for (int i = 0; i < deposits.size(); i++) {
			Deposit deposit = deposits.get(i);
			Date payOffDueDate = deposits.get(i).getPayOffDueDate();

			// pay off not defined
			if (payOffDueDate == null)
				continue;

			if (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), payOffDueDate) == 0)
				fdService.deductAnnuityEMI(deposit);
		}

		System.out.println("Reverse EMI deducted");
	}

	public void deductOverdraftEMI() {
		System.out.println("Going to deduct Overdraft EMI on " + DateService.getCurrentDate() + "...");

		//Get all active overdraft where EMI is pending
		List<OverdraftIssue> overdraftIssueList = overdraftIssueDAO.getAllActiveOverdrafts();
		
		for (int i = 0; i < overdraftIssueList.size(); i++) {
			OverdraftIssue overdraft = overdraftIssueList.get(i);
			Deposit deposit = fixedDepositDAO.getDeposit(overdraft.getDepositId());

			AccountDetails accDetails = accountDetailsDAO.findByAccountNo(deposit.getLinkedAccountNumber());
			
			if (accDetails != null) {

				boolean isLastEmi = false;
				
				if(deposit.getEmiAmount()!=null && (deposit.getEmiAmount()>=overdraft.getAmountToReturn()))
					isLastEmi = true;
				
				Double emiAmount = 0d;
				if(deposit.getEmiAmount()!=null ) {
				emiAmount=((deposit.getEmiAmount()<overdraft.getAmountToReturn())) ? deposit.getEmiAmount() : overdraft.getAmountToReturn();
				}
				Date todaysDate = DateService.getCurrentDateTime();
				if (accDetails.getAccountBalance()>= emiAmount)
				{
					// Deduct from Saving/Current Account 
					accDetails.setAccountBalance(emiAmount);
					accountDetailsDAO.updateUserAccountDetails(accDetails);

					
					// Update the DepositIssue Table
					overdraft.setAmountToReturn(overdraft.getEMIAmount() - emiAmount);
					overdraft.setTotalAmountPaid(overdraft.getTotalAmountPaid() + emiAmount);
					if(isLastEmi)
						overdraft.setIsActive(0);
					overdraftIssueDAO.updateOverdraftIssue(overdraft);
					
					// update overdraft Payment
					OverdraftPayment overdraftPayment = new OverdraftPayment();					
					overdraftPayment.setOverdraftId(overdraft.getId());
					overdraftPayment.setDepositId(overdraft.getDepositId());
					overdraftPayment.setPaymentAmount(emiAmount);
					overdraftPayment.setPaymentDate(DateService.getCurrentDateTime());
					String transactionId = fdService.generateRandomString();
					overdraftPayment.setTransactionId(transactionId);
					ModeOfPayment paymentMode=modeOfPaymentDAO.getModeOfPayment("Fund Transfer");
					overdraftPayment.setPaymentModeId(paymentMode.getId());				
					overdraftIssueDAO.insertOverdraftPayment(overdraftPayment);
					
					System.out.println("Overdraft EMI deducted");

				}
				else
				{
					// If EMI is not deductedt will go to UnSuccessfulOverdraftPaym
					UnSuccessfulOverdraft unSuccessfulOverdraft = new UnSuccessfulOverdraft();
					unSuccessfulOverdraft.setUnsuccessPaymentDate(todaysDate);

					unSuccessfulOverdraft.setDepositId(overdraft.getDepositId());
					unSuccessfulOverdraft.setEmiAmount(emiAmount);
					unSuccessfulOverdraft.setOverdraftAccountNumber(overdraft.getOverdraftNumber());
					overdraftIssueDAO.insertInUnSuccessfulOverdraft(unSuccessfulOverdraft);

				}
			}
				
		}

		
	}

	
	public void paymentRemindMail() {
		List<Deposit> deposits = fixedDepositDAO.getOpenDeposits();
		for (int i = 0; i < deposits.size(); i++) {

			Deposit deposit = deposits.get(i);
			
			if (deposit.getDepositClassification() != null
					&& !deposit.getDepositClassification().equalsIgnoreCase(Constants.RECURRINGDEPOSIT)) {
				continue;
			}
			
			Date dueDate = deposit.getDueDate();
			DepositHolder depositHolder = depositHolderDAO.getDepositHolders(deposit.getId()).get(0);
			Long customerId = depositHolder.getCustomerId();
			Customer cus = customerDAO.getById(customerId);
			int days = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), dueDate);
			if (days == 2) {
				try {

					String tex = Constants.CUSTOMERREMENDERSUBJECT;
					SimpleMailMessage emails = new SimpleMailMessage();
					emails.setTo(cus.getEmail());
					emails.setSubject(tex);
					emails.setText(Constants.HELLO + cus.getCustomerName() + Constants.PAYMENTKBODY3
							+ Constants.BANKSIGNATURE);
					mailSender.send(emails);
					SimpleMailMessage emails1 = new SimpleMailMessage();
					emails1.setTo(cus.getEmail());

				} catch (Exception e) {
					System.out.println(e.getMessage() + e);

				}

			}
			if (days == 1) {
				try {
					String tex = Constants.CUSTOMERREMENDERSUBJECT;
					SimpleMailMessage emails = new SimpleMailMessage();
					emails.setTo(cus.getEmail());
					emails.setSubject(tex);
					emails.setText(Constants.HELLO + cus.getCustomerName() + Constants.PAYMENTKBODY2
							+ Constants.BANKSIGNATURE);
					mailSender.send(emails);
					SimpleMailMessage emails1 = new SimpleMailMessage();
					emails1.setTo(cus.getEmail());

				} catch (Exception e) {
					System.out.println(e.getMessage() + e);

				}
			}

		}
	}

	@Transactional
	public void deductTDS() throws CustomException {
		fdService.deductTDS();
	}

	/*private Double getMaturityAmountAfterRenew(Deposit deposit)
	{
		List<Date> depositDates = fdService.getDepositDates(DateService.getCurrentDate(), deposit.getNewMaturityDate(),
				deposit);
		Double totalDepositAmount = deposit.getCurrentBalance() + deposit.getDepositAmount() * depositDates.size();

		// Calculate projected interest
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
		fixedDepositForm.setDepositId(deposit.getId());
		fixedDepositForm.setCategory(fdService.getPrimaryHolderCategory(depositHolderList));
		fixedDepositForm = fdService.setParametersForProjectedInterest(fixedDepositForm);
		
		interestDAO.deleteByDepositIdAndDate(deposit.getId());
		Date fromDate=DateService.addDays(DateService.getCurrentDate(), 1);
		List<Interest> interestList = fdService.getInterestBreakupForModification(fromDate,
				deposit.getNewMaturityDate(), fixedDepositForm, deposit);
		for (int j = 0; j < interestList.size(); j++) {
			Interest interest = new Interest();
			interest = interestList.get(j);
			interestDAO.insert(interest);
			if (j == interestList.size() - 1) {
//				deposit.setNewMaturityAmount(interest.getInterestSum());
//				fixedDepositDAO.updateFD(deposit);
			}
		}
	
		return deposit.getNewMaturityAmount();


	}*/
	
	public void calculatePenaltyForMissedPaymentForRecurringDeposit() throws CustomException
	{
		Date lastDateOfMonth = DateService.getLastDateOfCurrentMonth();

		// everyday at 10PM it will be fired
		// CRON Exception is "0 0 22 * * ?"
		//BankConfiguration bankConfiguration = ratesDAO.findAll();

		if (DateService.getDaysBetweenTwoDates(lastDateOfMonth, DateService.getCurrentDate()) != 0)
			return;
		
		// getting data from currentDate
		Integer month = DateService.getMonth(DateService.getCurrentDate());
		Integer year = DateService.getYear(DateService.getCurrentDate());


		// check any unsuccessfull recurring deposits are there in this month
		// if present the cut the penalties for this
		List<UnSuccessfulRecurringDeposit> unSuccessfullDepositList = unSuccessfulRecurringDAO.getUnsuccessfulRecurringDepositOfCurrentMonth(month+1, year);
		for (int i = 0; i < unSuccessfullDepositList.size(); i++) {
			UnSuccessfulRecurringDeposit unSuccessfullDeposit = unSuccessfullDepositList.get(i);
			Long depositId = unSuccessfullDeposit.getDepositId();
			Deposit deposit = fixedDepositDAO.getDeposit(depositId);
			
			ProductConfiguration productConfiguration = productConfigurationDAO.findById(deposit.getProductConfigurationId());
			// Get the bank Configuration
//			BankConfiguration bankConfiguration = null;
//			if (deposit.getPrimaryCitizen().equalsIgnoreCase(Constants.RI)) {
//				bankConfiguration = ratesDAO.findAllDataByCitizen(Constants.RI);
//			}

			// get the grace period
			Float penaltyForMissedPaymentRecurring = productConfiguration.getPenaltyForMissedPaymentForRecurring()== null ? 0 :
				productConfiguration.getPenaltyForMissedPaymentForRecurring();
			
			if(penaltyForMissedPaymentRecurring==null )
				continue;
			
			if(penaltyForMissedPaymentRecurring<=0)
				continue;
			
			
				
			// Get the DepositSummary
			DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(depositId);
			Double totalFixedInterestAccumulated = depositSummary.getTotalFixedInterestAccumulated()==null?0l:depositSummary.getTotalFixedInterestAccumulated();
			Double totalVariableInterestAccumulated = depositSummary.getTotalVariableInterestAccumulated()==null?0l:depositSummary.getTotalVariableInterestAccumulated(); 
			
			Double fixedPenaltyAmount = 0d;
			Double variablePenaltyAmount = 0d;
			
			if((totalFixedInterestAccumulated+totalVariableInterestAccumulated)>0)
			{
				fixedPenaltyAmount = calculationService.round(totalFixedInterestAccumulated * penaltyForMissedPaymentRecurring/100, 2);
				variablePenaltyAmount = calculationService.round(totalVariableInterestAccumulated * penaltyForMissedPaymentRecurring/100,2);
			}
			
			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);
			
			// Insert penalty in PaymentDistribution and Holderwise PaymentDistribution and DepositSummary table
			// -----------------------------------------
			Distribution distribution1 = calculationService.insertInDistributionForPenaltyInFixedVariable(Constants.MissedRecurringPaymentPenalty,
					deposit, depositHolderList, fixedPenaltyAmount, variablePenaltyAmount);
			
			depositSummary = calculationService.upsertInDepositSummaryForPenaltyInFixedVariable(deposit, Constants.MissedRecurringPaymentPenalty, depositHolderList,  fixedPenaltyAmount, variablePenaltyAmount);
			// -----------------------------------------
			
			// Insert in Journal & Ledger
			//-----------------------------------------------------------
			ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(Constants.InternalTransfer);

			ledgerService.insertJournal(deposit.getId(), fdService.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(),
					deposit.getAccountNumber(), null, Event.RECURRING_MISSEDPAYMENT_PENALTY.getValue(), (fixedPenaltyAmount+variablePenaltyAmount), mop.getId(),
					depositSummary.getTotalPrincipal(), null);	
			//-----------------------------------------------------------

		}
	}
}
