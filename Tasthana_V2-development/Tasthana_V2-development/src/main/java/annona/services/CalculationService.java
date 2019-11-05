package annona.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import annona.dao.AccountDetailsDAO;
import annona.dao.BankPaymentDAO;
import annona.dao.CustomerDAO;
import annona.dao.DepositHolderDAO;
import annona.dao.DepositHolderWiseDistributionDAO;
import annona.dao.DepositModificationDAO;
import annona.dao.DepositRateDAO;
import annona.dao.DepositRatesDAO;
import annona.dao.FixedDepositDAO;
import annona.dao.FormSubmissionDAO;
import annona.dao.GSTDeductionDAO;
import annona.dao.GstDAO;
import annona.dao.HolidayConfigurationDAO;
import annona.dao.InterestDAO;
import annona.dao.ModeOfPaymentDAO;
import annona.dao.PaymentDAO;
import annona.dao.DepositSummaryDAO;
import annona.dao.PaymentDistributionDAO;
import annona.dao.PayoffDAO;
import annona.dao.ProductConfigurationDAO;
import annona.dao.RatesDAO;
import annona.dao.RoundOffDAO;
import annona.dao.TDSDAO;
import annona.dao.TransactionDAO;
import annona.dao.WithdrawPenaltyDAO;
import annona.dao.paymentDAOImpl;
import annona.domain.AccountDetails;
import annona.domain.BankConfiguration;
import annona.domain.BankPaid;
import annona.domain.BankPaidDetails;
import annona.domain.BankPayment;
import annona.domain.BankPaymentDetails;
import annona.domain.Customer;
import annona.domain.CustomerCategory;
import annona.domain.Deposit;
import annona.domain.DepositBeforeRenew;
import annona.domain.DepositHolder;
import annona.domain.DepositHolderWiseDistribution;
import annona.domain.DepositModification;
import annona.domain.DepositRates;
import annona.domain.DepositSummaryHolderWise;
import annona.domain.DepositTDS;
import annona.domain.DepositWiseCustomerTDS;
import annona.domain.Distribution;
import annona.domain.GST;
import annona.domain.GSTDeduction;
import annona.domain.HolidayConfiguration;
import annona.domain.Interest;
import annona.domain.ModeOfPayment;
import annona.domain.ModificationPenalty;
import annona.domain.Payoff;
import annona.domain.PayoffDetails;
import annona.domain.ProductConfiguration;
import annona.domain.Rates;
import annona.domain.RenewDeposit;
import annona.domain.RoundOff;
import annona.domain.TDS;
import annona.domain.UnSuccessfulPayOff;
import annona.domain.WithdrawPenaltyAmountBased;
import annona.domain.WithdrawPenaltyDetails;
import annona.domain.WithdrawPenaltyMaster;
import annona.domain.WithdrawPenaltyTenureBased;
import annona.domain.DepositSummary;
import annona.event.CloseDepositEvent;
import annona.event.InterestCalculationEvent;
import annona.exception.CustomException;
import annona.form.AutoDepositForm;
import annona.form.FixedDepositForm;
import annona.services.DateService;
import annona.services.CalculationService;
import annona.utility.Constants;
import annona.utility.Event;

@Service
public class CalculationService {

	@Autowired
	ApplicationContext context;

	@Autowired
	RatesDAO ratesDAO;

	@Autowired
	MailSender mailSender;

	@Autowired
	TransactionDAO transactionDAO;

	@Autowired
	FixedDepositDAO fixedDepositDAO;

	@Autowired
	DepositHolderDAO depositHolderDAO;

	@Autowired
	InterestDAO interestDAO;

	@Autowired
	PaymentDistributionDAO paymentDistributionDAO;

	@Autowired
	DepositSummaryDAO depositSummaryDAO;

	@Autowired
	DepositHolderWiseDistributionDAO depositHolderWiseDistributionDAO;
	
	@Autowired
	DepositRateDAO depositRateDAO;
	
	@Autowired
	PayoffDAO payOffDAO;
	
	@Autowired
	DepositModificationDAO depositModificationDAO;
	
	@Autowired
	DepositRatesDAO depositRatesDAO;
	
	@Autowired
	PaymentDAO paymentDAO;
	
	@Autowired
	BankPaymentDAO bankPaymentDAO;
	
	@Autowired
	AccountDetailsDAO accountDetailsDAO;
	
	@Autowired
	GstDAO gstDAO;
	
	@Autowired
	CustomerDAO customerDAO;
	
	@Autowired
	LedgerService ledgerService;
	
	@Autowired
	FDService fdService;
	
	@Autowired
	TDSDAO tdsDAO;
	
	@Autowired
	HolidayConfigurationDAO holidayConfigurationDAO;

	@Autowired
	ProductConfigurationDAO productConfigurationDAO;
	
	@Autowired
	WithdrawPenaltyDAO withdrawPenaltyDAO;
	
	@Autowired
	GSTDeductionDAO gstDeductionDAO;
	
	@Autowired
	FormSubmissionDAO formSubmissionDAO;
	
	@Autowired
	RoundOffDAO roundOffDAO;
	
	@Autowired
	ModeOfPaymentDAO modeOfPaymentDAO;

	protected static Logger logger = Logger.getLogger("service");

	public CalculationService() {

	}

	public void calculateInterest() {

		// getting data from currentDate
		Integer month = DateService.getMonth(DateService.getCurrentDate());
		Integer year = DateService.getYear(DateService.getCurrentDate());

		// Find last date of the month
		Date lastDateOfMonth = DateService.getLastDateOfCurrentMonth();

		// If it is not last date of month then return
		if (DateService.getDaysBetweenTwoDates(lastDateOfMonth, DateService.getCurrentDate()) != 0)
			return;

		Boolean isInterestCalculated = false;
		List<Deposit> depositList = null;

		
		List<ProductConfiguration> productConfigurationList = productConfigurationDAO
				.getProductConfigurationListByInterestCalculationBasis(Constants.MONTHLY);
		if (productConfigurationList != null && productConfigurationList.size() > 0)
		{
			for (int i = 0; i < productConfigurationList.size(); i++)
			{
				depositList = fixedDepositDAO.getDepositsByProductConfiguration(productConfigurationList.get(i).getId());

				for (int j = 0; j < depositList.size(); j++)
				{
					Deposit deposit = depositList.get(j);

					// Get the deposit holder list
					List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());

					// Get Last day interest of current Month
					Interest interest = interestDAO.getInterestByMonth(deposit.getId(), month + 1, year,
							deposit.getNewMaturityDate());

					// check if this month interest is already deducted or not.
					// if deducted then dont allow to deduct anymore
					if (interest == null) 
					{
						this.calculateInterestInDetail(deposit, depositHolderList, "", null);
						isInterestCalculated = true;
					}

				}
			}
		}

		// Get all the configurations which have monthly interest calculation
		productConfigurationList = productConfigurationDAO
				.getProductConfigurationListByInterestCalculationBasis(Constants.QUARTERLY);
		if (productConfigurationList != null && productConfigurationList.size() > 0)
		{
			if ((month == 2 || month == 5 || month == 8 || month == 11))
			{

				for (int i = 0; i < productConfigurationList.size(); i++) 
				{
					depositList = fixedDepositDAO.getDepositsByProductConfiguration(productConfigurationList.get(i).getId());
	
					for (int j = 0; j < depositList.size(); j++) 
					{
						Deposit deposit = depositList.get(j);
	
						// Get the deposit holder list
						List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
	
						// Get Last day interest of current Month
						Interest interest = interestDAO.getInterestByMonth(deposit.getId(), month + 1, year,
								deposit.getNewMaturityDate());
	
						// check if this month interest is already deducted or not.
						// if deducted then dont allow to deduct anymore
						if (interest == null)
						{
							this.calculateInterestInDetail(deposit, depositHolderList, "",null);						
							isInterestCalculated = true;
						}
					}
				}
			}
		}

		// Get all the configurations which have monthly interest calculation
		productConfigurationList = productConfigurationDAO
				.getProductConfigurationListByInterestCalculationBasis(Constants.HALFYEARLY);
		if (productConfigurationList != null && productConfigurationList.size() > 0) {
			if ((month == 2 || month == 8))
			{
				for (int i = 0; i < productConfigurationList.size(); i++)
				{
					depositList = fixedDepositDAO.getDepositsByProductConfiguration(productConfigurationList.get(i).getId());

					for (int j = 0; j < depositList.size(); j++) {
						Deposit deposit = depositList.get(j);

						// Get the deposit holder list
						List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());

						// Get Last day interest of current Month
						Interest interest = interestDAO.getInterestByMonth(deposit.getId(), month + 1, year,
								deposit.getNewMaturityDate());

						// check if this month interest is already deducted or not.
						// if deducted then dont allow to deduct anymore
						if (interest == null) {
							this.calculateInterestInDetail(deposit, depositHolderList, "", null);
							isInterestCalculated = true;
						}
					}
				}

			}																						
		}

		// Get all the configurations which have monthly interest calculation
		productConfigurationList = productConfigurationDAO
				.getProductConfigurationListByInterestCalculationBasis(Constants.ANNUALLY);
		if (productConfigurationList != null && productConfigurationList.size() > 0) {
			if ((month == 2))
			{
				for (int i = 0; i < productConfigurationList.size(); i++) 
				{
					depositList = fixedDepositDAO.getDepositsByProductConfiguration(productConfigurationList.get(i).getId());

					for (int j = 0; j < depositList.size(); j++) {
						Deposit deposit = depositList.get(j);

						// Get the deposit holder list
						List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());

						// Get Last day interest of current Month
						Interest interest = interestDAO.getInterestByMonth(deposit.getId(), month + 1, year,
								deposit.getNewMaturityDate());

						// check if this month interest is already deducted or not.
						// if deducted then dont allow to deduct anymore
						if (interest == null) {
							this.calculateInterestInDetail(deposit, depositHolderList, "",null);
							isInterestCalculated = true;
						}
					}											
				}
			}
		}

		
		
		// Here all interest are calculated based on product.
		// But Sweep Deposit is not related to Product
		// So now we have to calculate Sweep based on Sweep Configuration
		// We are considering that sweep deposit interest calculation
		// will be done monthly and compounding will be done Quarterly.
		
			List<Deposit> sweepDeposits = fixedDepositDAO.getSweepDeposits();
			if ((month == 2 || month == 5 || month == 8 || month == 11))
			{
				
				for (int i = 0; i < sweepDeposits.size(); i++) {
	
					Deposit sweepDeposit = sweepDeposits.get(i);
					fixedDepositDAO.getDeposit(sweepDeposit.getId());
					
					// Get the deposit holder list
					List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(sweepDeposit.getId());
	
					// Get Last day interest of current Month
					Interest interest = interestDAO.getInterestByMonth(sweepDeposit.getId(), month + 1, year,
							sweepDeposit.getNewMaturityDate());
	
					if (interest == null) {
						this.calculateInterestInDetail(sweepDeposit, depositHolderList, "Auto",null);
						isInterestCalculated = true;
					}
				}
			}


		System.out.println("Interest calculation is done");

		// Raise event for interest calculation
		// From this event, TDS will be calculated
		//	if(isInterestCalculated = true)
		//		context.publishEvent(new InterestCalculationEvent(this,"InterestCalculation", null));
	}

	@Transactional
	public Distribution calculateInterestInDetail(Deposit deposit, List<DepositHolder> depositHolderList,
			String autoDeposit, Date increasedDateForMaturityOnHoliday) {
		
		// Get the interest Rate 
		Float interestRate = (deposit.getModifiedInterestRate() == null) ? deposit.getInterestRate()
				: deposit.getModifiedInterestRate();

		// Calculate the Fixed And Variable Interest
		HashMap<String, Double> interestDetails = calculateInterestAmount(deposit, depositHolderList, autoDeposit, increasedDateForMaturityOnHoliday);

		Double totalFixedInterest = this.round(interestDetails.get(Constants.FIXEDINTEREST),2);
		Double totalVariableInterest = this.round(interestDetails.get(Constants.VARIABLEINTEREST),2);
		
		// Find the Total Interest Amount
		Double amount = this.round(totalFixedInterest + totalVariableInterest, 2);

		// Saving in Distribution and HolderWise Distribution
		Distribution paymentDistribution = this.insertInPaymentDistribution(deposit, depositHolderList, null, null, Constants.INTEREST, totalFixedInterest,
				totalVariableInterest, null, null, null, null, null);

		
		// insert in Interest table
		Interest interest = new Interest();
		interest.setInterestRate(this.round((double) interestRate, 2));
		interest.setInterestAmount(amount);
		interest.setInterestDate(DateService.getCurrentDate());
		interest.setDepositId(deposit.getId());
		interest.setFinancialYear(DateService.getFinancialYear(DateService.getCurrentDate()));
		interest.setFixedInterest(totalFixedInterest);
		interest.setVariableInterest(totalVariableInterest);
		interest.getIsCalculated();
		interestDAO.insert(interest);

		// Insert/ Update in DepositSummary and DepositSummaryHolderWise table
		this.upsertInDepositSummary(deposit, Constants.INTEREST, null, totalFixedInterest, totalVariableInterest, null, null,
				depositHolderList, null, null, null);

		return paymentDistribution;
	}

	public Distribution calculateInterest(Deposit deposit, List<DepositHolder> depositHolderList, String autoDeposit, Date increasedDateForMaturityOnHoliday) {
		return this.calculateInterestInDetail(deposit, depositHolderList, autoDeposit, increasedDateForMaturityOnHoliday);
	}

	@Transactional
	public Distribution addInterest(Deposit deposit, List<DepositHolder> depositHolderList, Float interestRate,
			Double fixedInterest, Double variableInterest) {

		Distribution paymentDistribution = null;

		Long depositId = deposit.getId();

		

		// Saving in Distribution and HolderWise Distribution
		this.insertInPaymentDistribution(deposit, depositHolderList, null, null, Constants.INTEREST, fixedInterest,
				variableInterest, null, null, null, null, null);

		Double amount = this.round(fixedInterest + variableInterest, 2);

		// insert in Interest table
		Interest interest = new Interest();
		interest.setInterestRate((double) interestRate);
		interest.setInterestAmount(amount);
		interest.setInterestDate(DateService.getCurrentDate());
		interest.setDepositId(depositId);
		interest.setFinancialYear(DateService.getFinancialYear(DateService.getCurrentDate()));
		interest.setFixedInterest(fixedInterest);
		interest.setVariableInterest(variableInterest);
		interest.getIsCalculated();
		interestDAO.insert(interest);

		// Insert/ Update in DepositSummary and DepositSummaryHolderWise table
		this.upsertInDepositSummary(deposit, Constants.INTEREST, null, fixedInterest, variableInterest, null, null,
				depositHolderList, null, null, null);

		return paymentDistribution;
	}
	public void compoundInterest() throws CustomException {

		List<Deposit> depositList = new ArrayList();

		// getting data from currentDate
		Integer month = DateService.getMonth(DateService.getCurrentDate());
		Integer year = DateService.getYear(DateService.getCurrentDate());

		// Find last date of the month
		Date lastDateOfMonth = DateService.getLastDateOfCurrentMonth();

		// If it is not last date of month then return
		if (DateService.getDaysBetweenTwoDates(lastDateOfMonth, DateService.getCurrentDate()) != 0)
			return;

		List<Deposit> depositListLocal = null;
		List<ProductConfiguration> productConfigurationList = productConfigurationDAO
				.getProductConfigurationListByInterestCompoundingBasis(Constants.MONTHLY);
		if (productConfigurationList != null && productConfigurationList.size() > 0) {
			for (int i = 0; i < productConfigurationList.size(); i++) {
				depositListLocal = fixedDepositDAO.getDepositsByProductConfiguration( productConfigurationList.get(i).getId());
				if(depositListLocal != null && depositListLocal.size() > 0)
					depositList.addAll(depositListLocal);
			}
			depositListLocal = null;
		}

		productConfigurationList = productConfigurationDAO.getProductConfigurationListByInterestCompoundingBasis(Constants.QUARTERLY);
		if (productConfigurationList != null && productConfigurationList.size() > 0) {
			
			if ((month == 2 || month == 5 || month == 8 || month == 11))
			{
				for (int i = 0; i < productConfigurationList.size(); i++) {
	
					depositListLocal = fixedDepositDAO.getDepositsByProductConfiguration( productConfigurationList.get(i).getId());
					if(depositListLocal != null  && depositListLocal.size() > 0)
						depositList.addAll(depositListLocal);
				}		
				depositListLocal = null;
			}
		}

		productConfigurationList = productConfigurationDAO.getProductConfigurationListByInterestCompoundingBasis(Constants.HALFYEARLY);
		if (productConfigurationList != null && productConfigurationList.size() > 0) {
			
			if ((month == 2 || month == 8))
			{
				for (int i = 0; i < productConfigurationList.size(); i++) {
					depositListLocal = fixedDepositDAO.getDepositsByProductConfiguration( productConfigurationList.get(i).getId());
					
					if(depositListLocal != null  && depositListLocal.size() > 0)
						depositList.addAll(depositListLocal);
				}
				depositListLocal = null;
			}
		}

		productConfigurationList = productConfigurationDAO.getProductConfigurationListByInterestCompoundingBasis(Constants.ANNUALLY);
		if (productConfigurationList != null && productConfigurationList.size() > 0) {
			
			if (month == 2)
			{
				for (int i = 0; i < productConfigurationList.size(); i++) {
					depositListLocal = fixedDepositDAO.getDepositsByProductConfiguration( productConfigurationList.get(i).getId());
					
					if(depositListLocal != null  && depositListLocal.size() > 0)
						depositList.addAll(depositListLocal);
				}
				depositListLocal = null;
			}
		}
		
		
		// Search for all Sweep Deposit		
		if ((month == 2 || month == 5 || month == 8 || month == 11))
		{
			depositListLocal = fixedDepositDAO.getAllSweepDeposits();
				
			if(depositListLocal != null  && depositListLocal.size() > 0)
				depositList.addAll(depositListLocal);
				
			depositListLocal = null;
		}

		
		this.compoundInterest(depositList);
	}

	public void compoundInterest(List<Deposit> depositList) throws CustomException {

		for (int j = 0; j < depositList.size(); j++) {
			Deposit deposit = depositList.get(j);

			// Deduct the TDS 
			this.deductTDSDepositWise(deposit);
			
			// Compound 
			this.compoundInterestDepositWise(deposit);
		}

	}

	public Distribution compoundInterestDepositWise(Deposit deposit) throws CustomException
	{
		Double totalBalance = 0d;

		Long depositId = deposit.getId();
		// Get Interest Summary of the deposit
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(depositId);

		// Get Interest Summary of DepositHolders of the deposit
		List<DepositSummaryHolderWise> holderWiseInterestSummaryList = depositSummaryDAO
				.getHolderWiseDepositSummary(depositId);

		// Compound the Interest
		if (depositSummary == null)
			return null;

		// InhandInterest will be added to principal Amount
		// 1. One Row will be added in Distribution table
		// 2. Rows will be added to HolderWiseDistribution table
		// 3. Deposit will be updated with current balance
		// 4. InhandInterest will be zero in Interest Summary.
		// 5. FixedBalance and VariableBalance will be updated in DepositHolder table

		// Step 1: One Row will be added in Distribution table
		// ----------------------------------------------------------------------------------
		// Getting the last distribution record
		Distribution lastDistribution = paymentDistributionDAO.getLastPaymentDistribution(depositId);

		// Getting in Hand Interest which is not compounded
		Double fixedInHandInterest = depositSummary.getTotalFixedInterestInHand()==null? 0d: depositSummary.getTotalFixedInterestInHand();
		Double variableInHandInterest = depositSummary.getTotalVariableInterestInHand()==null? 0d: depositSummary.getTotalVariableInterestInHand(); 

		// Saving in Payment Distribution
		Distribution paymentDistribution = new Distribution();
		paymentDistribution.setDistributionDate(DateService.getCurrentDate());
		paymentDistribution.setDepositId(depositId);
		paymentDistribution.setAction("Interest Compounding");
		paymentDistribution.setCompoundFixedAmt(lastDistribution.getCompoundFixedAmt() + fixedInHandInterest);
		paymentDistribution
				.setCompoundVariableAmt(lastDistribution.getCompoundVariableAmt() + variableInHandInterest);

		// InterestBalance is just like an Inhand Interest Balance After Each
		// Transaction.
		// After Compounding It will be Zero.
		// Each Time after interest calculation this will be added with previous
		// balance.
		// And In Payoff, Pay Off amount will be deducted from the balance.
		paymentDistribution.setBalanceFixedInterest(0d);
		paymentDistribution.setBalanceVariableInterest(0d);

		totalBalance = this
				.round(paymentDistribution.getCompoundFixedAmt() + paymentDistribution.getCompoundVariableAmt(), 2);
		paymentDistribution.setTotalBalance(totalBalance);
		paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);
		// ----------------------------------------------------------------------------------

		// 2. Rows will be added to HolderWiseDistribution table
		// ----------------------------------------------------------------------------------
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);
		// Insert in DepositHolderWiseDistribution
		// fdService.insertInDepositHolderWiseDistribution(depositHolderList, "Interest
		// Compounding", paymentDistribution.getId(), fixedInHandInterest,
		// variableInHandInterest);

		this.insertInDepositHolderWiseDistribution(depositHolderList, Constants.INTERESTCOMPOUNDING,
				paymentDistribution.getId(), null, null, null, null, null, null, null);
		
		// ----------------------------------------------------------------------------------

//		// 3. Update the current Balance
//		// ----------------------------------------------------------------------------------
//		deposit.setCurrentBalance(totalBalance);
//		fixedDepositDAO.updateFD(deposit);
//		// ----------------------------------------------------------------------------------

		
		// 3. Update Interest Summary-Inhand Interest will be zero.
		// Inside the Deposit Summary, JournalLedger  is present. 
		// It is not possible to take out from here because we are not getting
		// the amount from  here
		// ----------------------------------------------------------------------------------
		depositSummary = this.upsertInDepositSummary(deposit, Constants.INTERESTCOMPOUNDING, null, null, null, null, null,
				depositHolderList, null, null, null);
		// ----------------------------------------------------------------------------------
		
		// Insert in Journal & Ledger
		//-----------------------------------------------------------			
		ModeOfPayment modeOfpayment = modeOfPaymentDAO.getModeOfPayment("Internal Transfer");
		if(modeOfpayment!=null)
		{
			ledgerService.insertJournal(deposit.getId(),this.getPrimaryCustomerId(depositHolderList) , DateService.getCurrentDate(),
					null, deposit.getAccountNumber(), Event.INTEREST_ACCRUAL.getValue(),
					(fixedInHandInterest + variableInHandInterest), modeOfpayment.getId(), depositSummary.getTotalPrincipal(), null);
		}
//		ledgerService.insertJournalLedger(deposits.get(i).getId(),this.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(), 
//				deposits.get(i).getAccountNumber(), fromAccountType, null, toAccountType, 
//				 Constants.TDS, totalTDSAmountForDeposit, "Internal Tranasfer", 
//				depositSummary.getTotalPrincipal());	
		//-----------------------------------------------------------
		
		
		
		
		//context.publishEvent(new InterestCalculationEvent(this,"InterestCalculation", deposit));
		
	
		return paymentDistribution;
	}
	
	@Transactional
	public void deductTDSDepositWise(Deposit deposit) throws CustomException
	{
		Date currentDate = DateService.getCurrentDate();
		int month = DateService.getMonth(currentDate);
		// if (!(month == 2))
		// return;
		//
		// if (!DateService.isLastDateOfFinancialYear(currentDate))
		// return;

		Integer year = DateService.getYear(currentDate);
		String financialYear = DateService.getFinancialYear(currentDate);
		
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
		HashMap<Long, Long> customerTotalTDSList = new HashMap<>();
		for(int i=0; i< depositHolderList.size(); i++)
		{
			// Calculate TDS needs to be deducted of a customer
			TDS customerTotalTDS = this.calculateTDS(depositHolderList.get(i).getCustomerId(), deposit);
			if(customerTotalTDS != null)
				customerTotalTDSList.put(depositHolderList.get(i).getCustomerId(), customerTotalTDS.getId());
		}
		
		
		boolean isCustomerNotTaxable = false;
		for(int j=0; j<depositHolderList.size(); j++)
		{
			isCustomerNotTaxable = (depositHolderList.get(j).getNriAccountType()!=null) && (depositHolderList.get(j).getNriAccountType().equalsIgnoreCase("NRE") ||
					depositHolderList.get(j).getNriAccountType().equalsIgnoreCase("FCNR") ||
					depositHolderList.get(j).getNriAccountType().equalsIgnoreCase("PRP"));
			
			if(isCustomerNotTaxable == true)
				continue;
			else
				break;
		}
		
		if(isCustomerNotTaxable)
			return;
		
		
		DepositTDS depositTDS = tdsDAO.getConsolidatedTDS(deposit.getId(), financialYear, DateService.getCurrentDate());
		// insert in deposit TDS
		depositTDS = tdsDAO.insertDepositTDS(depositTDS);
		Double totalTDSAmountForDeposit = (depositTDS != null && depositTDS.getCalculatedTDSAmount() != null)
				? depositTDS.getCalculatedTDSAmount() : 0;

		// update depositwisecustomertds as tds is deducted for the deposit
		tdsDAO.updateDeductTDSInDepositWiseCustomerTDS(deposit.getId());

//		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);

		Double tdsAmountOfDepositHolder = 0d;
		HashMap<Long, Double> tdsToDeduct = new HashMap<>();
		for (int j = 0; j < depositHolderList.size(); j++) {
			Long customerId = depositHolderList.get(j).getCustomerId();

			Long tdsId = customerTotalTDSList.get(customerId)==null?0l: customerTotalTDSList.get(customerId);
			
			// check the TDS amount of this customer
			tdsAmountOfDepositHolder = tdsDAO.getCustomerTDSForDeposit(customerId, deposit.getId(), tdsId, financialYear);
			tdsToDeduct.put(depositHolderList.get(j).getId(), tdsAmountOfDepositHolder);

		}
		if (depositTDS != null && depositTDS.getCalculatedTDSAmount() != null) {
			Distribution lastDistribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());
			if (lastDistribution == null)
				return;

			// Deduct from Transaction
			Distribution distribution = new Distribution();
			distribution.setAction(Constants.TDS);
			distribution.setDepositId(deposit.getId());
			distribution.setDistributionDate(DateService.getCurrentDate());
			if(totalTDSAmountForDeposit == 0)
				distribution.setDepositedAmt(0d);
			else
				distribution.setDepositedAmt(-totalTDSAmountForDeposit);
			
			distribution.setBalanceFixedInterest(lastDistribution.getBalanceFixedInterest());
			distribution.setBalanceVariableInterest((lastDistribution.getBalanceVariableInterest()==null?0d: lastDistribution.getBalanceVariableInterest())- totalTDSAmountForDeposit);
			distribution.setFixedAmt(lastDistribution.getFixedAmt());
			distribution.setVariableAmt(lastDistribution.getVariableAmt());
			distribution.setCompoundFixedAmt(lastDistribution.getCompoundFixedAmt());
			distribution.setCompoundVariableAmt(lastDistribution.getCompoundVariableAmt());
			distribution.setTotalBalance(lastDistribution.getTotalBalance());
			
			distribution = paymentDistributionDAO.insertPaymentDistribution(distribution);

			// Deduct from depositHolder as per Holderwise ratio
			this.insertInDepositHolderWiseTDSDistribution(depositHolderList, tdsToDeduct, totalTDSAmountForDeposit,
					distribution.getId());

			//Insert in DepositSummary and HolderwiseDepositSummary
			DepositSummary depositSummary = this.upsertInDepositSummary(deposit, Constants.TDS, totalTDSAmountForDeposit, null, null,
					null, tdsToDeduct, depositHolderList, null, null, null);

			
			// Insert in Journal & Ledger
			//-----------------------------------------------------------
			String fromAccountType = "Interest Account";
			String toAccountType = "TDS Account";
			//ToDo:
//			ledgerService.insertJournalLedger(deposit.getId(), this.getPrimaryCustomerId(depositHolderList),DateService.getCurrentDate(), 
//					deposit.getAccountNumber(), fromAccountType, null, toAccountType, 
//					 Constants.TDS, totalTDSAmountForDeposit, "Internal Tranasfer", 
//					depositSummary.getTotalPrincipal());	
			
			ModeOfPayment modeOfpayment = modeOfPaymentDAO.getModeOfPayment("Internal Transfer");
			if(modeOfpayment!=null)
			{
				ledgerService.insertJournal(deposit.getId(),this.getPrimaryCustomerId(depositHolderList) , DateService.getCurrentDate(),
						deposit.getAccountNumber(),null, Event.TDS.getValue(),
						totalTDSAmountForDeposit,modeOfpayment.getId(), depositSummary.getTotalPrincipal(), null);
			}
			//-----------------------------------------------------------
		
		}
	}
	public TDS calculateTDS(Long customerId, Deposit deposit) {
		String financialYear = DateService.getFinancialYear(DateService.getCurrentDate());
		Double calculatedTDSAmount = 0.0;
		Boolean isEligibleForForm15GH = false;
		Boolean isForm15GSubmitted = false;
		Boolean isTDSDeductionRequired = false;
		
		// Get the customer category from customer details
		Customer customer = customerDAO.getById(customerId);
		
		// Get all open deposits(No sweep/auto deposit considered).
		// List<Deposit> deposits = fixedDepositDao.getAllCategoryDepositsOfCurrentYear(customerId);// fixedDepositDao.getDeposits(customerId);
		
		// Get all open deposits(No sweep/auto deposit considered).
		List<Deposit> deposits = null;
//		if(customer.getCitizen().equalsIgnoreCase("RI"))
//			deposits = fixedDepositDao.getAllCategoryDepositsOfCurrentYear(customerId);
//		else
//			deposits = fixedDepositDao.getAllDepositsOfNRIsOfCurrentYear(customerId);
		deposits = fixedDepositDAO.getDeposits(customerId);
		
			
		Distribution lastDistribution = null;
		int isTdsPartyCalculated = 1;
		if (deposits.size() == 1)
			isTdsPartyCalculated = 0;


		Rates customerConfig = ratesDAO.getRatesByCategory(customer.getCategory());

		// Get the tds rate and the minimum InetestAmount For TDS deduction
		// upto the amount of minInetestAmountForTDS, TDS will not be deducted
		// If the amount exceeds, then TDS will be deducted from the customer
		Float tdsRate = customerConfig.getTds();
		Long minInterestAmountForTDS = customerConfig.getMinIntAmtForTDSDeduction();
		Double totalPaymentOfAllDeposit = 0d;
		Double totalContributionOnProjectedInterestOfAllDeposit = 0d;
		Double totalInterestGivenToAllDeposits = 0d;
		HashMap<Long, Double> depositWiseTotalProjectedInterest = new HashMap<>();
		HashMap<Long, Double> depositWiseTotalInterestGiven = new HashMap<>();
		
		// Get the last Interest compounded for the deposit
		// Interest lastInterest =
		// interestDAO.getLastInterestCompounded(depositId);
		TDS lastTDS = tdsDAO.getLastTDSDeducted(customerId, financialYear);
		Double totalInterestEarnedFromTheDeposit = 0d;
		Date depositFromDate = null;
		
		ProductConfiguration productConfiguration = null;

		// Go through the all deposits of the customer
		for (int i = 0; i < deposits.size(); i++) {

			Deposit otherDeposit = deposits.get(i);
			Long depositId = otherDeposit.getId();
			
			Long productConfigurationId = deposit.getProductConfigurationId();
			if(productConfigurationId == null)
				productConfiguration = productConfigurationDAO.findById(otherDeposit.getProductConfigurationId());

			// Get Deposit Holder to find out the contribution%
			// in the deposit
			DepositHolder depositHolder = depositHolderDAO.getDepositHolder(depositId, customerId);
			Float contribution = depositHolder.getContribution();

			// Calculate the Total Interest / Projected Interest through out the
			// year for the customer
			// --------------------------------------------------------------------
			Double totalProjectedInterestAmountOfDeposit = interestDAO.getTotAnnualInterestAmount(depositId,
					financialYear);

			// Total contributed interest of all deposits
			Double contributedProjectedInterestAmount = totalProjectedInterestAmountOfDeposit * contribution / 100;
			depositWiseTotalProjectedInterest.put(depositId, contributedProjectedInterestAmount);

			totalContributionOnProjectedInterestOfAllDeposit = totalContributionOnProjectedInterestOfAllDeposit
					+ contributedProjectedInterestAmount;
			// --------------------------------------------------------------------

			// Calculate the total interest given to customer
			// ------------------------------------------------
			// Get total Interest Given
			Date fromDate = (lastTDS == null) ? otherDeposit.getCreatedDate()
					: DateService.addDays(lastTDS.getTdsCalcDate(), 1);

			if (depositFromDate == null) {
				depositFromDate = otherDeposit.getCreatedDate();
			} else {
				if (DateService.getDaysBetweenTwoDates(depositFromDate, otherDeposit.getCreatedDate()) > 0)
					depositFromDate = otherDeposit.getCreatedDate();
			}
			Double interestGivenToTheDeposit = interestDAO.getTotInterestGivenForPeriod(depositId, fromDate,
					DateService.getCurrentDate()); // interestDAO.getTotInterestGiven(depositId,
													// financialYear);
			Double contributedInterestGiven = interestGivenToTheDeposit * contribution / 100;

			if (depositId.longValue() == deposit.getId().longValue())
				totalInterestEarnedFromTheDeposit = contributedInterestGiven;

			depositWiseTotalInterestGiven.put(depositId, contributedInterestGiven);
			totalInterestGivenToAllDeposits = totalInterestGivenToAllDeposits + contributedInterestGiven;
			// --------------------------------------------------

		}

		// Check if the customer is eligible for Form 15G/H
		Period age = DateService.calculateAge(customer.getDateOfBirth());

		// For regular(<60) customer, 2.5 lac is the exemption limit
		// For Sr Citizen(>=60 to <80) 3 lac is the exemption limit
		// For Super Sr Citizen(>=80) 5 lac is the exemption limit
		Integer limitForTax = 250000;
		if (age.getYears() >= 60 && age.getYears() < 80)
			limitForTax = 300000;
		else if (age.getYears() >= 80)
			limitForTax = 500000;

		// if (totalPaymentOfAllDeposit < limitForTax)
		if (totalContributionOnProjectedInterestOfAllDeposit < limitForTax) {
			isEligibleForForm15GH = true;
		} else {
			isEligibleForForm15GH = false;
			isTDSDeductionRequired = true;
		}

		if (isEligibleForForm15GH) {
			// Check holder is submitted the 15G/H Form
			isForm15GSubmitted = formSubmissionDAO.isFormSubmitted(customerId, financialYear);

			if (isForm15GSubmitted)
				isTDSDeductionRequired = false;
			else {
				// if interest income exceeds 10,000, then
				// deduct tds
				if (totalContributionOnProjectedInterestOfAllDeposit <= minInterestAmountForTDS) {
					isTDSDeductionRequired = false;
				} else {
					isTDSDeductionRequired = true;
				}
			}

		}

		// This TDS is for full full year
		if (isTDSDeductionRequired) {
			if (customer.getPan() != null && customer.getPan() != "") {
				// nothing to do
			} else {
				// Get the Rate from BProductConfiguration			
				tdsRate = productConfiguration.getTdsPercentForNoPAN();//  20f;
			}
			// get the total earned from this deposit
			calculatedTDSAmount = round(totalInterestGivenToAllDeposits * tdsRate / 100, 2);
			// calculatedTDSAmount = round(totalInterestEarnedFromTheDeposit *
			// tdsRate / 100,2);
		}

		// Check in this period any TDS has been deducted from client
		// for closing or for withdrawal. then deduct that tds from
		// calculatedTDSAmount for the customer
		
		if( deposits.size()>0)
		{
			Date fromDate = (lastTDS == null) ? depositFromDate : DateService.addDays(lastTDS.getTdsCalcDate(), 1);
			Double totalPartlyDeductedTDS = tdsDAO.getTotalPartlyCalculatedTDS(customerId, fromDate,
					DateService.getCurrentDate());
			calculatedTDSAmount = calculatedTDSAmount - totalPartlyDeductedTDS;
		}

		// Now pending TDS should be divided no of occurrence of remaining TDS
		// if my interest calculation is monthly, total no of tds will be 12
		// like that...
		// if my interest calculation is quarterly, total no of tds will be 4
		// like that...

		// Find out the no of occurrence of pending TDS
		if (isTDSDeductionRequired && calculatedTDSAmount > 0) {
			// Find out the TDS Amount to deduct for the deposit
			calculatedTDSAmount = totalInterestEarnedFromTheDeposit / totalInterestGivenToAllDeposits
					* calculatedTDSAmount;

			// Insert in TDS table
			// Customer consolidated TDS
			// --------------------------------------------------------
			TDS tds = new TDS();

			tds.setTdsAmount(round(calculatedTDSAmount, 2));
			tds.setCalculatedTDSAmount(round(calculatedTDSAmount, 2));
			tds.setTdsCalcDate(DateService.getCurrentDate());
			tds.setCustomerId(customerId);
			tds.setFinancialYear(financialYear);

			tds.setTdsDeducted(1);
			tds.setPartlyCalculated(isTdsPartyCalculated);
			tds = tdsDAO.insert(tds);
			// ---------------------------------------------------------------

			// Insert in Deposit wise Customer TDS
			for (int i = 0; i < deposits.size(); i++) {

				Deposit newDeposit = deposits.get(i);
				Long depositId = newDeposit.getId();

				Double depositInterestAmount = depositWiseTotalInterestGiven.get(depositId);
				double depositWiseInterestPercentage = depositInterestAmount / totalInterestGivenToAllDeposits * 100;
				double depositWiseCustomerWiseTDS = calculatedTDSAmount * depositWiseInterestPercentage / 100;

				// Customer TDS
				DepositWiseCustomerTDS depositWiseCustomerTDS = new DepositWiseCustomerTDS();
				depositWiseCustomerTDS.setTdsId(tds.getId());
				depositWiseCustomerTDS.setTdsRate(Double.parseDouble(tdsRate.toString()));

				depositWiseCustomerTDS.setCalculatedTDSAmount(round(depositWiseCustomerWiseTDS, 2));
				// customerTDS.setContributedTDSAmount(tdsAmount);
				depositWiseCustomerTDS.setTdsDate(DateService.getCurrentDate());
				depositWiseCustomerTDS.setCustomerId(customerId);
				depositWiseCustomerTDS.setDepositId(depositId);
				depositWiseCustomerTDS.setDepositAccountNo(deposit.getAccountNumber());
				depositWiseCustomerTDS.setFinancialYear(financialYear);
				depositWiseCustomerTDS.setIsTDSDeducted(0);
				tdsDAO.insertCustomerTDS(depositWiseCustomerTDS);

			}

			// Insert in DepositTDS
			for (int i = 0; i < deposits.size(); i++) {
				DepositTDS depositTDS = tdsDAO.getConsolidatedTDS(deposits.get(i).getId(), financialYear,
						DateService.getCurrentDate());
				// insert in deposit TDS
				tdsDAO.insertDepositTDS(depositTDS);
			}
			return tds;
		} else {
			// Insert in TDS table
			// Customer consolidated TDS
			// --------------------------------------------------------
			calculatedTDSAmount = 0d;
			TDS tds = new TDS();

			tds.setTdsAmount(round(calculatedTDSAmount, 2));
			tds.setCalculatedTDSAmount(round(calculatedTDSAmount, 2));
			tds.setTdsCalcDate(DateService.getCurrentDate());
			tds.setCustomerId(customerId);
			tds.setFinancialYear(financialYear);

			tds.setTdsDeducted(1);
			tds.setPartlyCalculated(isTdsPartyCalculated);
			tds = tdsDAO.insert(tds);

			return tds;
			// ---------------------------------------------------------------
		}
	}
	
	public void insertInDepositHolderWiseTDSDistribution(List<DepositHolder> depositHolderList,
			HashMap<Long, Double> tdsToDeduct, Double totalAmount, Long actionId) {

		String action = "TDS";
		
			for (int i = 0; i < depositHolderList.size(); i++) {

				// Get the last distribution of the holder from holderwisedistribution
				DepositHolderWiseDistribution lastDistribution = depositHolderWiseDistributionDAO
						.getDepositHolderWiseLastDistribution(depositHolderList.get(i).getDepositId(),
								depositHolderList.get(i).getId());
				
				DepositHolderWiseDistribution depHolderDistribution = new DepositHolderWiseDistribution();
				depHolderDistribution.setAction(action);
				depHolderDistribution.setActionId(actionId);
				depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
				depHolderDistribution.setCustomerId(depositHolderList.get(i).getCustomerId());
				depHolderDistribution.setDepositHolderId(depositHolderList.get(i).getId());
				depHolderDistribution.setDepositId(depositHolderList.get(i).getDepositId());
				//depHolderDistribution.setActionAmount(round(totalAmount, 2));
				depHolderDistribution.setContribution(depositHolderList.get(i).getContribution());
				Double distributedAmt = tdsToDeduct.get(depositHolderList.get(i).getId()) == null ? 0d
						: tdsToDeduct.get(depositHolderList.get(i).getId());
				depHolderDistribution.setVariableAmt(round(-distributedAmt, 2));
				
				depHolderDistribution.setBalanceFixedInterest(lastDistribution.getBalanceFixedInterest());
				depHolderDistribution.setBalanceVariableInterest((lastDistribution.getBalanceVariableInterest()==null ? 0d : lastDistribution.getBalanceVariableInterest()) - distributedAmt);
				//depHolderDistribution.setBalanceVariableInterest(lastDistribution.getBalanceVariableInterest());
				depHolderDistribution.setFixedCompoundAmount(lastDistribution.getFixedCompoundAmount());
				depHolderDistribution.setVariableCompoundAmount(lastDistribution.getVariableCompoundAmount());
						
				depositHolderWiseDistributionDAO.saveDepositHolderWiseDistribution(depHolderDistribution);
			}
	}
	///////////////////////////////////////////////////////////////////////////////////////

	public DepositSummary upsertInDepositSummary(Deposit deposit, String action, Double amountPaid, Double fixedInterest,
			Double variableInterest, Double payOffAmount, HashMap<Long, Double> depositHolderAmount,
			List<DepositHolder> depositHolderList, Double fixedInterestAdjustmentAmt, Double variableInterestAdjustmentAmount, 
			Double penaltyAmount) {

		String fromAccountNo = "";
		String fromAccountType = "";
		
		String toAccountNo = "";
		String toAccountType = "";
		
		// getAllRatesByCustomerCategoryCitizenAndAccountType
		// Get last Deposit Summary
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(deposit.getId());	

		Double fixedAmount = null;
		Float fixedPercent = 0f;
		if(action.equalsIgnoreCase(Constants.PAYMENT))
		{
			Rates rate = ratesDAO.getAllRatesByCustomerCategoryCitizenAndAccountType(deposit.getPrimaryCustomerCategory(),
					deposit.getPrimaryCitizen(), deposit.getPrimaryNRIAccountType());
			
			if (amountPaid != null && amountPaid > 0 && rate != null && deposit.getDepositCategory() == null)
				fixedPercent = rate.getFdFixedPercent();
		}
		
		Double variableAmount = null;
		if (amountPaid != null) {
			fixedAmount = amountPaid * fixedPercent / 100;
			variableAmount = amountPaid - fixedAmount;
		}

		if (fixedInterest != null)
			fixedInterest = this.round(fixedInterest, 2);

		if (variableInterest != null)
			variableInterest = this.round(variableInterest, 2);

		if (payOffAmount != null)
			payOffAmount = this.round(payOffAmount, 2);
		
		boolean insert = false;
		if(depositSummary == null)
		{
			depositSummary = new DepositSummary();
			insert = true;
		}
		
		depositSummary.setDepositId(deposit.getId());

			if(action.equalsIgnoreCase(Constants.PAYMENT))
			{
				depositSummary.setTotalFixedPrincipal((depositSummary.getTotalFixedPrincipal()==null?0d:
					depositSummary.getTotalFixedPrincipal()) + fixedAmount);
				
				depositSummary.setTotalVariablePrincipal((depositSummary.getTotalVariablePrincipal()==null?0d:
					depositSummary.getTotalVariablePrincipal()) + variableAmount);
				
				depositSummary.setTotalPrincipal((depositSummary.getTotalPrincipal()==null?0d:
					depositSummary.getTotalPrincipal()) + fixedAmount + variableAmount);
			}
			
			if(action.equalsIgnoreCase(Constants.WITHDRAW))
			{
				depositSummary.setTotalFixedPrincipal((depositSummary.getTotalFixedPrincipal()==null?0d:
					depositSummary.getTotalFixedPrincipal()) - fixedAmount);
				
				depositSummary.setTotalVariablePrincipal((depositSummary.getTotalVariablePrincipal()==null?0d:
					depositSummary.getTotalVariablePrincipal()) - variableAmount);
				
				depositSummary.setTotalPrincipal((depositSummary.getTotalPrincipal()==null?0d:
					depositSummary.getTotalPrincipal()) -(fixedAmount + variableAmount));
			}
			
			if(action.equalsIgnoreCase(Constants.INTEREST))
			{
				fixedInterest = fixedInterest == null ? 0d : fixedInterest;
				variableInterest = variableInterest == null ? 0d : variableInterest;

				depositSummary.setTotalFixedInterestInHand((depositSummary.getTotalFixedInterestInHand() == null ? 0
						: depositSummary.getTotalFixedInterestInHand()) + fixedInterest);
				depositSummary.setTotalVariableInterestInHand((depositSummary.getTotalVariableInterestInHand() == null ? 0
						: depositSummary.getTotalVariableInterestInHand()) + variableInterest);

				
				depositSummary
						.setTotalFixedInterestAccumulated((depositSummary.getTotalFixedInterestAccumulated() == null ? 0
								: depositSummary.getTotalFixedInterestAccumulated()) + fixedInterest);
				depositSummary.setTotalVariableInterestAccumulated((depositSummary.getTotalVariableInterestAccumulated() == null ? 0
								: depositSummary.getTotalVariableInterestAccumulated()) + variableInterest);
				
				depositSummary.setTotalInterestAccumulated((depositSummary.getTotalInterestAccumulated() == null ? 0
								: depositSummary.getTotalInterestAccumulated()) + fixedInterest + variableInterest);

				
				// Insert in Journal & Ledger
				//-----------------------------------------------------------
				fromAccountType = "Cash Account";		
				toAccountType = "Interest Account";		
				
				//ToDo:
//				ledgerService.insertJournalLedger(deposit.getId(),this.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(), 
//						fromAccountNo, fromAccountType, toAccountNo, toAccountType, 
//						"Interest", (fixedInterest + fixedInterest), "Internal Transfer", 
//						depositSummary.getTotalPrincipal());		
				//-----------------------------------------------------------	
			}
			
			if(action.equalsIgnoreCase(Constants.PAYOFF))
			{
				payOffAmount = payOffAmount == null ? 0d : payOffAmount;
				
				depositSummary.setTotalVariableInterestPaidOff((depositSummary.getTotalVariableInterestPaidOff()==null?
						0 : depositSummary.getTotalVariableInterestPaidOff()) + payOffAmount);
				
				depositSummary.setTotalVariableInterestInHand((depositSummary.getTotalVariableInterestInHand() == null ? 0
						: depositSummary.getTotalVariableInterestInHand()) - payOffAmount);
				
				depositSummary.setTotalVariableInterestAccumulated((depositSummary.getTotalVariableInterestAccumulated() == null ? 0
						: depositSummary.getTotalVariableInterestAccumulated()) - payOffAmount);
		
				depositSummary.setTotalInterestAccumulated((depositSummary.getTotalInterestAccumulated() == null ? 0
						: depositSummary.getTotalInterestAccumulated()) - payOffAmount);
			}
			
			if(action.equalsIgnoreCase(Constants.EMI))
			{
				// Reverse EMI will effect in variable amount only
				depositSummary.setTotalVariablePrincipal((depositSummary.getTotalVariablePrincipal()==null?0d:
					depositSummary.getTotalVariablePrincipal()) + variableAmount);
				
				depositSummary.setTotalPrincipal((depositSummary.getTotalPrincipal()==null?0d:
					depositSummary.getTotalPrincipal()) +  variableAmount);
			}
			
			if(action.equalsIgnoreCase(Constants.INTERESTCOMPOUNDING))
			{
				payOffAmount = payOffAmount == null ? 0d : payOffAmount;
				
				Double fixedInterestInHand = depositSummary.getTotalFixedInterestInHand() == null ? 0
						: depositSummary.getTotalFixedInterestInHand();
				
				Double variableInterestInHand = depositSummary.getTotalVariableInterestInHand() == null ? 0
						: depositSummary.getTotalVariableInterestInHand();
				
				depositSummary.setTotalFixedPrincipal((depositSummary.getTotalFixedPrincipal()==null?0d:
					depositSummary.getTotalFixedPrincipal()) + fixedInterestInHand);
				
				depositSummary.setTotalVariablePrincipal((depositSummary.getTotalVariablePrincipal()==null?0d:
					depositSummary.getTotalVariablePrincipal()) + variableInterestInHand);
				
				depositSummary.setTotalPrincipal((depositSummary.getTotalFixedPrincipal()==null?0d:
					depositSummary.getTotalFixedPrincipal()) + (depositSummary.getTotalVariablePrincipal()==null?0d:
					depositSummary.getTotalVariablePrincipal()));			
				
				depositSummary.setTotalFixedInterestCompounded((depositSummary.getTotalFixedInterestCompounded()==null?0d:
					depositSummary.getTotalFixedInterestCompounded()) + fixedInterestInHand);
				
				depositSummary.setTotalVariableInterestCompounded((depositSummary.getTotalVariableInterestCompounded()==null?0d:
					depositSummary.getTotalVariableInterestCompounded()) + variableInterestInHand);
				
				
				depositSummary.setTotalFixedInterestInHand(0d);
				depositSummary.setTotalVariableInterestInHand(0d);
				
				
				// Insert in Journal & Ledger
				//-----------------------------------------------------------
				fromAccountType = "Interest Account";
				
				toAccountNo = deposit.getAccountNumber();
				toAccountType = "Deposit Account";		
				
				//ToDo:
//				ledgerService.insertJournalLedger(deposit.getId(),this.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(), 
//						fromAccountNo, fromAccountType, toAccountNo, toAccountType, 
//						"Interest", (fixedInterestInHand + variableInterestInHand), "Internal Transfer", 
//						depositSummary.getTotalPrincipal());		
				//-----------------------------------------------------------	
				
			}
			if(action.equalsIgnoreCase(Constants.TDS))
			{
				amountPaid = amountPaid == null ? 0d : amountPaid;
				
				depositSummary.setTotalVariableInterestInHand((depositSummary.getTotalVariableInterestInHand() == null ? 0
						: depositSummary.getTotalVariableInterestInHand()) - amountPaid);
				
				depositSummary.setTotalVariableInterestAccumulated((depositSummary.getTotalVariableInterestAccumulated() == null ? 0
						: depositSummary.getTotalVariableInterestAccumulated()) - amountPaid);
		
				depositSummary.setTotalInterestAccumulated((depositSummary.getTotalInterestAccumulated() == null ? 0
						: depositSummary.getTotalInterestAccumulated()) - amountPaid);
				

			}
			if(action.contains("Interest Adjustment For Withdraw"))
			{
				
				fixedInterestAdjustmentAmt = fixedInterestAdjustmentAmt == null ? 0d : fixedInterestAdjustmentAmt;
				variableInterestAdjustmentAmount = variableInterestAdjustmentAmount == null ? 0d : variableInterestAdjustmentAmount;

				depositSummary.setTotalFixedInterestAdjusted((depositSummary.getTotalFixedInterestAdjusted()==null?0d:
					depositSummary.getTotalFixedInterestAdjusted()) + fixedInterestAdjustmentAmt);
				
				depositSummary.setTotalVariableIneterestAdjusted((depositSummary.getTotalVariableIneterestAdjusted()==null?0d:
					depositSummary.getTotalVariableIneterestAdjusted()) + variableInterestAdjustmentAmount);
				
				
				// because InterestAdjustmentAmount is coming as negative we need to do addition here
				depositSummary.setTotalFixedInterestAccumulated((depositSummary.getTotalFixedInterestAccumulated() == null ? 0
						: depositSummary.getTotalFixedInterestAccumulated()) + fixedInterestAdjustmentAmt);
				
				depositSummary.setTotalVariableInterestAccumulated((depositSummary.getTotalVariableInterestAccumulated() == null ? 0
						: depositSummary.getTotalVariableInterestAccumulated()) + variableInterestAdjustmentAmount);
		
				depositSummary.setTotalInterestAccumulated((depositSummary.getTotalInterestAccumulated() == null ? 0
						: depositSummary.getTotalInterestAccumulated()) + (fixedInterestAdjustmentAmt+variableInterestAdjustmentAmount));
				
				Double fixedInterestInHand = depositSummary.getTotalFixedInterestInHand() == null ? 0
						: depositSummary.getTotalFixedInterestInHand();
				
				depositSummary.setTotalFixedPrincipal((depositSummary.getTotalFixedPrincipal()==null?0d:
					depositSummary.getTotalFixedPrincipal()) + fixedInterestAdjustmentAmt + fixedInterestInHand);
				
				depositSummary.setTotalVariablePrincipal((depositSummary.getTotalVariablePrincipal()==null?0d:
					depositSummary.getTotalVariablePrincipal()) + variableInterestAdjustmentAmount);
				
				depositSummary.setTotalPrincipal((depositSummary.getTotalFixedPrincipal()==null?0d:
					depositSummary.getTotalFixedPrincipal())+ (depositSummary.getTotalVariablePrincipal()==null?0d:
						depositSummary.getTotalVariablePrincipal()));
				
//				if(fixedInterestAdjustmentAmt != null)
//					depositSummary.setTotalFixedInterestInHand(0d);
				
				depositSummary.setTotalFixedInterestInHand(0d);
				depositSummary.setTotalVariableInterestInHand(0d);
				
			}
			
			if(action.equalsIgnoreCase(Constants.PENALTY1)  || action.equalsIgnoreCase(Constants.PENALTY))
			{
				Double totalPenaltyAdjusted = 0d;

				if(penaltyAmount<=depositSummary.getTotalVariableInterestInHand())
				{
					depositSummary.setTotalVariableInterestInHand(penaltyAmount);
					totalPenaltyAdjusted = penaltyAmount;
					depositSummary.setTotalVariableInterestAccumulated((depositSummary.getTotalVariableInterestAccumulated() == null ? 0
							: depositSummary.getTotalVariableInterestAccumulated()) - totalPenaltyAdjusted);
				}
				else
				{
					if(penaltyAmount<=(depositSummary.getTotalFixedInterestInHand() + depositSummary.getTotalVariableInterestInHand()))
					{
						Double variableInhandInterest = depositSummary.getTotalVariableInterestInHand();
						depositSummary.setTotalVariableInterestInHand(0d);
						depositSummary.setTotalVariableInterestAccumulated(depositSummary.getTotalVariableInterestAccumulated() - variableInhandInterest);
						
						depositSummary.setTotalFixedInterestInHand(depositSummary.getTotalFixedInterestInHand()-
								(penaltyAmount-variableInhandInterest));	
						
						depositSummary.setTotalFixedInterestAccumulated(depositSummary.getTotalFixedInterestAccumulated()-
								(penaltyAmount-variableInhandInterest));
			
						
						totalPenaltyAdjusted = 0d;
					}
					else
					{
						Double variableInhandInterest = depositSummary.getTotalVariableInterestInHand();
						Double fixedInhandInterest = depositSummary.getTotalFixedInterestInHand();
						depositSummary.setTotalVariableInterestInHand(0d);
						depositSummary.setTotalVariableInterestAccumulated(depositSummary.getTotalVariableInterestAccumulated() - variableInhandInterest);
						
						depositSummary.setTotalFixedInterestInHand(0d);							
						depositSummary.setTotalFixedInterestAccumulated(depositSummary.getTotalFixedInterestAccumulated()-fixedInhandInterest);
			
						
						totalPenaltyAdjusted = variableInhandInterest + fixedInhandInterest;
						depositSummary.setTotalVariableInterestAccumulated(depositSummary.getTotalVariableInterestAccumulated()-(penaltyAmount-totalPenaltyAdjusted));
						depositSummary.setTotalVariableInterestCompounded(depositSummary.getTotalVariableInterestCompounded()-(penaltyAmount-totalPenaltyAdjusted));
					}
				}			

			}
			if(action.contains("Interest Adjustment For Tenure Change"))
			{
				
				fixedInterestAdjustmentAmt = fixedInterestAdjustmentAmt == null ? 0d : fixedInterestAdjustmentAmt;
				variableInterestAdjustmentAmount = variableInterestAdjustmentAmount == null ? 0d : variableInterestAdjustmentAmount;

				depositSummary.setTotalFixedInterestAdjusted((depositSummary.getTotalFixedInterestAdjusted()==null?0d:
					depositSummary.getTotalFixedInterestAdjusted()) + fixedInterestAdjustmentAmt);
				
				depositSummary.setTotalVariableIneterestAdjusted((depositSummary.getTotalVariableIneterestAdjusted()==null?0d:
					depositSummary.getTotalVariableIneterestAdjusted()) + variableInterestAdjustmentAmount);
				
				
				
				depositSummary.setTotalFixedInterestAccumulated((depositSummary.getTotalFixedInterestAccumulated() == null ? 0
						: depositSummary.getTotalFixedInterestAccumulated()) - fixedInterestAdjustmentAmt);
				
				depositSummary.setTotalVariableInterestAccumulated((depositSummary.getTotalVariableInterestAccumulated() == null ? 0
						: depositSummary.getTotalVariableInterestAccumulated()) - variableInterestAdjustmentAmount);
		
				depositSummary.setTotalInterestAccumulated((depositSummary.getTotalInterestAccumulated() == null ? 0
						: depositSummary.getTotalInterestAccumulated()) - (fixedInterestAdjustmentAmt+variableInterestAdjustmentAmount));
				
				
				depositSummary.setTotalFixedPrincipal((depositSummary.getTotalFixedPrincipal()==null?0d:
					depositSummary.getTotalFixedPrincipal()) - fixedInterestAdjustmentAmt);
				
				depositSummary.setTotalVariablePrincipal((depositSummary.getTotalVariablePrincipal()==null?0d:
					depositSummary.getTotalVariablePrincipal()) - variableInterestAdjustmentAmount);
				
				depositSummary.setTotalPrincipal((depositSummary.getTotalPrincipal()==null?0d:
					depositSummary.getTotalPrincipal())- (fixedInterestAdjustmentAmt + variableInterestAdjustmentAmount));
				
				if(fixedInterestAdjustmentAmt != null)
					depositSummary.setTotalFixedInterestInHand(0d);
				
				depositSummary.setTotalVariableInterestInHand(0d);
				
			}
			if(action.equalsIgnoreCase("Interest Adjustment for Deposit Conversion"))
			{
				// Below two is coming as negative 
				fixedInterestAdjustmentAmt = fixedInterestAdjustmentAmt == null ? 0d : fixedInterestAdjustmentAmt;
				variableInterestAdjustmentAmount = variableInterestAdjustmentAmount == null ? 0d : variableInterestAdjustmentAmount;

				depositSummary.setTotalFixedInterestAdjusted((depositSummary.getTotalFixedInterestAdjusted()==null?0d:
					depositSummary.getTotalFixedInterestAdjusted()) + fixedInterestAdjustmentAmt);
				
				depositSummary.setTotalVariableIneterestAdjusted((depositSummary.getTotalVariableIneterestAdjusted()==null?0d:
					depositSummary.getTotalVariableIneterestAdjusted()) + variableInterestAdjustmentAmount);
				
				
				
				depositSummary.setTotalFixedInterestAccumulated((depositSummary.getTotalFixedInterestAccumulated() == null ? 0
						: depositSummary.getTotalFixedInterestAccumulated()) + fixedInterestAdjustmentAmt);
				
				depositSummary.setTotalVariableInterestAccumulated((depositSummary.getTotalVariableInterestAccumulated() == null ? 0
						: depositSummary.getTotalVariableInterestAccumulated()) + variableInterestAdjustmentAmount);
		
				depositSummary.setTotalInterestAccumulated((depositSummary.getTotalInterestAccumulated() == null ? 0
						: depositSummary.getTotalInterestAccumulated()) + (fixedInterestAdjustmentAmt+variableInterestAdjustmentAmount));
				
				
				depositSummary.setTotalFixedPrincipal((depositSummary.getTotalFixedPrincipal()==null?0d:
					depositSummary.getTotalFixedPrincipal()) + fixedInterestAdjustmentAmt);
				
				depositSummary.setTotalVariablePrincipal((depositSummary.getTotalVariablePrincipal()==null?0d:
					depositSummary.getTotalVariablePrincipal()) + variableInterestAdjustmentAmount);
				
				depositSummary.setTotalPrincipal((depositSummary.getTotalPrincipal()==null?0d:
					depositSummary.getTotalPrincipal())+ (fixedInterestAdjustmentAmt + variableInterestAdjustmentAmount));
				
				if(fixedInterestAdjustmentAmt != null)
					depositSummary.setTotalFixedInterestInHand(0d);
				
				depositSummary.setTotalVariableInterestInHand(0d);
				
			}
			if(action.equalsIgnoreCase(Constants.DEPOSITCONVERTED))
			{				
				depositSummary.setTotalFixedInterestAdjusted(null);				
				depositSummary.setTotalVariableIneterestAdjusted(null);
				depositSummary.setTotalFixedInterestAccumulated(null);				
				depositSummary.setTotalVariableInterestAccumulated(null);		
				depositSummary.setTotalInterestAccumulated(null);					
				depositSummary.setTotalFixedPrincipal(null);				
				depositSummary.setTotalVariablePrincipal(null);		
				depositSummary.setTotalPrincipal(null);
				depositSummary.setTotalFixedInterestInHand(null);					
				depositSummary.setTotalVariableInterestInHand(null);
			}
			
			if(insert)
			{
				depositSummary = depositSummaryDAO.insert(depositSummary);
			}
			else
			{
				depositSummary = depositSummaryDAO.update(depositSummary);
			}
		
			//Insert or Update HolderWiseDepositSummary
		this.upsertHolderWiseDepositSummary(deposit, action, fixedAmount, variableAmount, fixedInterest, variableInterest, depositHolderAmount, depositHolderList, 
				fixedInterestAdjustmentAmt, variableInterestAdjustmentAmount, depositSummary, penaltyAmount);
		
		

		// Update the currentBalance of the deposit
		if(depositSummary.getTotalPrincipal()==null)
			deposit.setCurrentBalance(0d);
		else
			deposit.setCurrentBalance(depositSummary.getTotalPrincipal());
		fixedDepositDAO.updateFD(deposit);
		
		return depositSummary;
	}

	@Transactional
	@SuppressWarnings("unused")
	private void upsertHolderWiseDepositSummary(Deposit deposit, String action, Double fixedAmount, Double variableAmount,
			Double fixedInterest, Double variableInterest, HashMap<Long, Double> depositHolderAmount,
			List<DepositHolder> depositHolderList, Double fixedInterestAdjustmentAmt, Double variableInterestAdjustmentAmount,
			DepositSummary depositSummary, Double penaltyAmount)
	{	
		
		boolean insert = false;
		for (int i = 0; i < depositHolderList.size(); i++) {
			
			insert = false; 
					
			DepositSummaryHolderWise depositSummaryHolderWise = depositSummaryDAO.getHolderWiseDepositSummary(deposit.getId(), depositHolderList.get(i).getId());
			
			if(depositSummaryHolderWise == null)
			{
				depositSummaryHolderWise = new DepositSummaryHolderWise();
				insert = true;
			}
			depositSummaryHolderWise.setDepositId(deposit.getId());
			depositSummaryHolderWise.setDepositHolderId(depositHolderList.get(i).getId());
			depositSummaryHolderWise.setCustomerId(depositHolderList.get(i).getCustomerId());
		
		
			if(action.equalsIgnoreCase(Constants.PAYMENT))
			{
				Double contributedFixedAmount = 0d;
				Double contributedVariableAmount = 0d;
				
				if (fixedAmount != null)
					contributedFixedAmount = this.round(fixedAmount, 2)
							* depositHolderList.get(i).getContribution()/100;

				if (variableAmount != null)
					contributedVariableAmount = this.round(variableAmount, 2)
							* depositHolderList.get(i).getContribution()/100;

				depositSummaryHolderWise.setTotalFixedPrincipal((depositSummaryHolderWise.getTotalFixedPrincipal()==null?0d:
					depositSummaryHolderWise.getTotalFixedPrincipal()) + contributedFixedAmount);
				
				depositSummaryHolderWise.setTotalVariablePrincipal((depositSummaryHolderWise.getTotalVariablePrincipal()==null?0d:
					depositSummaryHolderWise.getTotalVariablePrincipal()) + contributedVariableAmount);
				
				depositSummaryHolderWise.setTotalPrincipal((depositSummaryHolderWise.getTotalPrincipal()==null?0d:
					depositSummaryHolderWise.getTotalPrincipal()) + contributedFixedAmount + contributedVariableAmount);
			}
			
			if(action.equalsIgnoreCase(Constants.WITHDRAW))
			{
				Double contributedFixedAmount = 0d;
				Double contributedVariableAmount = 0d;
				
				if (fixedAmount != null)
					contributedFixedAmount = this.round(fixedAmount, 2)
							* depositHolderList.get(i).getContribution()/100;

				if (variableAmount != null)
					contributedVariableAmount = this.round(variableAmount, 2)
							* depositHolderList.get(i).getContribution()/100;

				depositSummaryHolderWise.setTotalFixedPrincipal((depositSummaryHolderWise.getTotalFixedPrincipal()==null?0d:
					depositSummaryHolderWise.getTotalFixedPrincipal()) - contributedFixedAmount);
				
				depositSummaryHolderWise.setTotalVariablePrincipal((depositSummaryHolderWise.getTotalVariablePrincipal()==null?0d:
					depositSummaryHolderWise.getTotalVariablePrincipal()) - contributedVariableAmount);
				
				depositSummaryHolderWise.setTotalPrincipal((depositSummaryHolderWise.getTotalPrincipal()==null?0d:
					depositSummaryHolderWise.getTotalPrincipal()) - (contributedFixedAmount + contributedVariableAmount));
			}
			else if(action.equalsIgnoreCase(Constants.INTEREST))
			{
				Double contributedFixedInterest = 0d;
				Double contributedVariableInterest = 0d;		
			
				if (fixedInterest != null)
					contributedFixedInterest = this.round(fixedInterest, 2)
							* depositHolderList.get(i).getContribution()/100;

				if (variableInterest != null)
					contributedVariableInterest = this.round(variableInterest, 2)
							* depositHolderList.get(i).getContribution()/100;

				depositSummaryHolderWise.setTotalFixedInterestInHand((depositSummaryHolderWise.getTotalFixedInterestInHand() == null ? 0
						: depositSummaryHolderWise.getTotalFixedInterestInHand()) + contributedFixedInterest);
				depositSummaryHolderWise.setTotalVariableInterestInHand((depositSummaryHolderWise.getTotalVariableInterestInHand() == null ? 0
						: depositSummaryHolderWise.getTotalVariableInterestInHand()) + contributedVariableInterest);
	
				
				depositSummaryHolderWise
						.setTotalFixedInterestAccumulated((depositSummaryHolderWise.getTotalFixedInterestAccumulated() == null ? 0
								: depositSummaryHolderWise.getTotalFixedInterestAccumulated()) + contributedFixedInterest);
				depositSummaryHolderWise.setTotalVariableInterestAccumulated((depositSummaryHolderWise.getTotalVariableInterestAccumulated() == null ? 0
								: depositSummaryHolderWise.getTotalVariableInterestAccumulated()) + contributedVariableInterest);
				
				depositSummaryHolderWise.setTotalInterestAccumulated((depositSummaryHolderWise.getTotalInterestAccumulated() == null ? 0
								: depositSummaryHolderWise.getTotalInterestAccumulated()) + contributedFixedInterest + contributedVariableInterest);
	
			}
			
			else if(action.equalsIgnoreCase(Constants.PAYOFF))
			{
				Double payOffAmount = depositHolderAmount.get(depositHolderList.get(i).getId()) == null ? 0d : depositHolderAmount.get(depositHolderList.get(i).getId());
				
				depositSummaryHolderWise.setTotalVariableInterestPaidOff((depositSummaryHolderWise.getTotalVariableInterestPaidOff()==null?
						0 : depositSummaryHolderWise.getTotalVariableInterestPaidOff()) + payOffAmount);
				
				depositSummaryHolderWise.setTotalVariableInterestInHand((depositSummaryHolderWise.getTotalVariableInterestInHand() == null ? 0
						: depositSummaryHolderWise.getTotalVariableInterestInHand()) - payOffAmount);
				
				depositSummaryHolderWise.setTotalVariableInterestAccumulated((depositSummaryHolderWise.getTotalVariableInterestAccumulated() == null ? 0
						: depositSummaryHolderWise.getTotalVariableInterestAccumulated()) - payOffAmount);
		
				depositSummaryHolderWise.setTotalInterestAccumulated((depositSummaryHolderWise.getTotalInterestAccumulated() == null ? 0
						: depositSummaryHolderWise.getTotalInterestAccumulated()) - payOffAmount);
	
			}
			
			else if(action.equalsIgnoreCase(Constants.INTERESTCOMPOUNDING))
			{
								
				depositSummaryHolderWise.setTotalFixedPrincipal((depositSummaryHolderWise.getTotalFixedPrincipal()==null?0d:
					depositSummaryHolderWise.getTotalFixedPrincipal()) + (depositSummaryHolderWise.getTotalFixedInterestInHand() == null ? 0
							: depositSummaryHolderWise.getTotalFixedInterestInHand()));
				
				depositSummaryHolderWise.setTotalVariablePrincipal((depositSummaryHolderWise.getTotalVariablePrincipal()==null?0d:
					depositSummaryHolderWise.getTotalVariablePrincipal()) + (depositSummaryHolderWise.getTotalVariableInterestInHand() == null ? 0
							: depositSummaryHolderWise.getTotalVariableInterestInHand()));
				
				depositSummaryHolderWise.setTotalPrincipal((depositSummaryHolderWise.getTotalFixedPrincipal()==null?0d:
					depositSummaryHolderWise.getTotalFixedPrincipal()) + (depositSummaryHolderWise.getTotalVariablePrincipal()==null?0d:
					depositSummaryHolderWise.getTotalVariablePrincipal()));			
				
				depositSummaryHolderWise.setTotalFixedInterestCompounded((depositSummaryHolderWise.getTotalFixedInterestCompounded()==null?0d:
					depositSummaryHolderWise.getTotalFixedInterestCompounded()) + (depositSummaryHolderWise.getTotalFixedInterestInHand() == null ? 0
							: depositSummaryHolderWise.getTotalFixedInterestInHand()));
				
				depositSummaryHolderWise.setTotalVariableInterestCompounded((depositSummaryHolderWise.getTotalVariableInterestCompounded()==null?0d:
					depositSummaryHolderWise.getTotalVariableInterestCompounded()) + (depositSummaryHolderWise.getTotalVariableInterestInHand() == null ? 0
							: depositSummaryHolderWise.getTotalVariableInterestInHand()));
				
				
				depositSummaryHolderWise.setTotalFixedInterestInHand(0d);
				depositSummaryHolderWise.setTotalVariableInterestInHand(0d);
				
			}
			else if(action.equalsIgnoreCase(Constants.TDS))
			{
				Double tdsAmount = depositHolderAmount.get(depositHolderList.get(i).getId()) == null ? 0d : depositHolderAmount.get(depositHolderList.get(i).getId());
				
				depositSummaryHolderWise.setTotalVariableInterestInHand((depositSummaryHolderWise.getTotalVariableInterestInHand() == null ? 0
						: depositSummaryHolderWise.getTotalVariableInterestInHand()) - tdsAmount);
				
				depositSummaryHolderWise.setTotalVariableInterestAccumulated((depositSummaryHolderWise.getTotalVariableInterestAccumulated() == null ? 0
						: depositSummaryHolderWise.getTotalVariableInterestAccumulated()) - tdsAmount);
		
				depositSummaryHolderWise.setTotalInterestAccumulated((depositSummaryHolderWise.getTotalInterestAccumulated() == null ? 0
						: depositSummaryHolderWise.getTotalInterestAccumulated()) - tdsAmount);
	
			}
			else if(action.contains(Constants.INTERESTADJUSTMENT))
			{
				
				Double contributedFixedInterestAdjustmentAmt = 0d;
				Double contributedVariableInterestAdjustmentAmt = 0d;		
			
				if (fixedInterestAdjustmentAmt != null)
					contributedFixedInterestAdjustmentAmt = this.round(fixedInterestAdjustmentAmt, 2)
							* depositHolderList.get(i).getContribution()/100;

				if (variableInterestAdjustmentAmount != null)
					contributedVariableInterestAdjustmentAmt = this.round(contributedVariableInterestAdjustmentAmt, 2)
							* depositHolderList.get(i).getContribution()/100;

				depositSummaryHolderWise.setTotalFixedInterestAdjusted((depositSummaryHolderWise.getTotalFixedInterestAdjusted()==null?0d:
					depositSummaryHolderWise.getTotalFixedInterestAdjusted()) + contributedFixedInterestAdjustmentAmt);
				
				depositSummaryHolderWise.setTotalVariableIneterestAdjusted((depositSummaryHolderWise.getTotalVariableIneterestAdjusted()==null?0d:
					depositSummaryHolderWise.getTotalVariableIneterestAdjusted()) + contributedVariableInterestAdjustmentAmt);
				
				
				
				depositSummaryHolderWise.setTotalFixedInterestAccumulated((depositSummaryHolderWise.getTotalFixedInterestAccumulated() == null ? 0
						: depositSummaryHolderWise.getTotalFixedInterestAccumulated()) + contributedFixedInterestAdjustmentAmt);
				
				depositSummaryHolderWise.setTotalVariableInterestAccumulated((depositSummaryHolderWise.getTotalVariableInterestAccumulated() == null ? 0
						: depositSummaryHolderWise.getTotalVariableInterestAccumulated()) + contributedVariableInterestAdjustmentAmt);
		
				depositSummaryHolderWise.setTotalInterestAccumulated((depositSummaryHolderWise.getTotalInterestAccumulated() == null ? 0
						: depositSummaryHolderWise.getTotalInterestAccumulated())+ (contributedFixedInterestAdjustmentAmt + contributedVariableInterestAdjustmentAmt));
				
				
				depositSummaryHolderWise.setTotalFixedPrincipal((depositSummaryHolderWise.getTotalFixedPrincipal()==null?0d:
					depositSummaryHolderWise.getTotalFixedPrincipal()) + contributedFixedInterestAdjustmentAmt +
						+ (depositSummaryHolderWise.getTotalFixedInterestInHand() == null ? 0
								: depositSummaryHolderWise.getTotalFixedInterestInHand()));
				
				depositSummaryHolderWise.setTotalVariablePrincipal((depositSummaryHolderWise.getTotalVariablePrincipal()==null?0d:
					depositSummaryHolderWise.getTotalVariablePrincipal()) + contributedVariableInterestAdjustmentAmt);
				
				depositSummaryHolderWise.setTotalPrincipal((depositSummaryHolderWise.getTotalFixedPrincipal()==null?0d:
					depositSummaryHolderWise.getTotalFixedPrincipal()) + (depositSummaryHolderWise.getTotalVariablePrincipal()==null?0d:
						depositSummaryHolderWise.getTotalVariablePrincipal()));
				
				depositSummaryHolderWise.setTotalFixedInterestInHand(0d);
				depositSummaryHolderWise.setTotalVariableInterestInHand(0d);
				
			}
			else if(action.equalsIgnoreCase(Constants.PENALTY1))
			{
				Double totalPenaltyAdjusted = 0d;

				if(penaltyAmount<=depositSummaryHolderWise.getTotalVariableInterestInHand())
				{
					depositSummaryHolderWise.setTotalVariableInterestInHand(penaltyAmount);
					totalPenaltyAdjusted = penaltyAmount;
					depositSummaryHolderWise.setTotalVariableInterestAccumulated((depositSummaryHolderWise.getTotalVariableInterestAccumulated() == null ? 0
							: depositSummaryHolderWise.getTotalVariableInterestAccumulated()) - totalPenaltyAdjusted);
				}
				else
				{
					if(penaltyAmount<=(depositSummaryHolderWise.getTotalFixedInterestInHand() + depositSummaryHolderWise.getTotalVariableInterestInHand()))
					{
						Double variableInhandInterest = depositSummaryHolderWise.getTotalVariableInterestInHand();
						depositSummaryHolderWise.setTotalVariableInterestInHand(0d);
						depositSummaryHolderWise.setTotalVariableInterestAccumulated(depositSummaryHolderWise.getTotalVariableInterestAccumulated() - variableInhandInterest);
						
						depositSummaryHolderWise.setTotalFixedInterestInHand(depositSummaryHolderWise.getTotalFixedInterestInHand()-
								(penaltyAmount-variableInhandInterest));	
						
						depositSummaryHolderWise.setTotalFixedInterestAccumulated(depositSummaryHolderWise.getTotalFixedInterestAccumulated()-
								(penaltyAmount-variableInhandInterest));
			
						
						totalPenaltyAdjusted = 0d;
					}
					else
					{
						Double variableInhandInterest = depositSummaryHolderWise.getTotalVariableInterestInHand();
						Double fixedInhandInterest = depositSummaryHolderWise.getTotalFixedInterestInHand();
						depositSummaryHolderWise.setTotalVariableInterestInHand(0d);
						depositSummaryHolderWise.setTotalVariableInterestAccumulated(depositSummaryHolderWise.getTotalVariableInterestAccumulated() - variableInhandInterest);
						
						depositSummaryHolderWise.setTotalFixedInterestInHand(0d);							
						depositSummaryHolderWise.setTotalFixedInterestAccumulated(depositSummaryHolderWise.getTotalFixedInterestAccumulated()-fixedInhandInterest);
			
						
						totalPenaltyAdjusted = variableInhandInterest + fixedInhandInterest;
						depositSummaryHolderWise.setTotalVariableInterestAccumulated(depositSummaryHolderWise.getTotalVariableInterestAccumulated()-(penaltyAmount-totalPenaltyAdjusted));
						depositSummaryHolderWise.setTotalVariableInterestCompounded(depositSummaryHolderWise.getTotalVariableInterestCompounded()-(penaltyAmount-totalPenaltyAdjusted));
					}
				}			

			}
			else if(action.equalsIgnoreCase("Interest Adjustment for Deposit Conversion"))
			{
				// Below two is coming as negative 
				fixedInterestAdjustmentAmt = fixedInterestAdjustmentAmt == null ? 0d : fixedInterestAdjustmentAmt;
				variableInterestAdjustmentAmount = variableInterestAdjustmentAmount == null ? 0d : variableInterestAdjustmentAmount;

				depositSummaryHolderWise.setTotalFixedInterestAdjusted((depositSummaryHolderWise.getTotalFixedInterestAdjusted()==null?0d:
					depositSummaryHolderWise.getTotalFixedInterestAdjusted()) + fixedInterestAdjustmentAmt);
				
				depositSummaryHolderWise.setTotalVariableIneterestAdjusted((depositSummaryHolderWise.getTotalVariableIneterestAdjusted()==null?0d:
					depositSummaryHolderWise.getTotalVariableIneterestAdjusted()) + variableInterestAdjustmentAmount);
				
				
				
				depositSummaryHolderWise.setTotalFixedInterestAccumulated((depositSummaryHolderWise.getTotalFixedInterestAccumulated() == null ? 0
						: depositSummaryHolderWise.getTotalFixedInterestAccumulated()) + fixedInterestAdjustmentAmt);
				
				depositSummaryHolderWise.setTotalVariableInterestAccumulated((depositSummaryHolderWise.getTotalVariableInterestAccumulated() == null ? 0
						: depositSummaryHolderWise.getTotalVariableInterestAccumulated()) + variableInterestAdjustmentAmount);
		
				depositSummaryHolderWise.setTotalInterestAccumulated((depositSummaryHolderWise.getTotalInterestAccumulated() == null ? 0
						: depositSummaryHolderWise.getTotalInterestAccumulated()) + (fixedInterestAdjustmentAmt+variableInterestAdjustmentAmount));
				
				
				depositSummaryHolderWise.setTotalFixedPrincipal((depositSummaryHolderWise.getTotalFixedPrincipal()==null?0d:
					depositSummaryHolderWise.getTotalFixedPrincipal()) + fixedInterestAdjustmentAmt);
				
				depositSummaryHolderWise.setTotalVariablePrincipal((depositSummaryHolderWise.getTotalVariablePrincipal()==null?0d:
					depositSummaryHolderWise.getTotalVariablePrincipal()) + variableInterestAdjustmentAmount);
				
				depositSummaryHolderWise.setTotalPrincipal((depositSummaryHolderWise.getTotalPrincipal()==null?0d:
					depositSummaryHolderWise.getTotalPrincipal())+ (fixedInterestAdjustmentAmt + variableInterestAdjustmentAmount));
				
				if(fixedInterestAdjustmentAmt != null)
					depositSummaryHolderWise.setTotalFixedInterestInHand(0d);
				
				depositSummaryHolderWise.setTotalVariableInterestInHand(0d);
				
			}
			else if(action.contains(Constants.DEPOSITCONVERTED))
			{

				depositSummaryHolderWise.setTotalFixedInterestAdjusted(null);			
				depositSummaryHolderWise.setTotalVariableIneterestAdjusted(null);		
				depositSummaryHolderWise.setTotalFixedInterestAccumulated(null);			
				depositSummaryHolderWise.setTotalVariableInterestAccumulated(null);		
				depositSummaryHolderWise.setTotalInterestAccumulated(null);	
				depositSummaryHolderWise.setTotalFixedPrincipal(null);				
				depositSummaryHolderWise.setTotalVariablePrincipal(null);				
				depositSummaryHolderWise.setTotalPrincipal(null);			
				depositSummaryHolderWise.setTotalFixedInterestInHand(null);
				depositSummaryHolderWise.setTotalVariableInterestInHand(null);
				
			}
			
			if(insert)
			{
				depositSummaryHolderWise = depositSummaryDAO.insertDepositSummaryHolderWise(depositSummaryHolderWise);
			}
			else
			{
				depositSummaryHolderWise = depositSummaryDAO.updateDepositSummaryHolderWise(depositSummaryHolderWise);
			}
			
			
		}
	}
	
	@Transactional
	public Distribution insertInPaymentDistribution(Deposit deposit, List<DepositHolder> depositHolderList, Double payAmount,
			Long actionId, String action, Double totalFixedInterest, Double totalVariableInterest,
			Double fixedInHandInterest, Double variableInHandInterest, Double totalPayoffAmt, HashMap<Long, Double> depositHolderWisePayoff, Double penaltyAmount) {

		Distribution previousPaymentDistribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());
		Double totalBalance = 0d;
		
		Distribution paymentDistribution = null;

		if (action.equalsIgnoreCase(Constants.PAYMENT) || action.equalsIgnoreCase(Constants.WITHDRAW) || action.equalsIgnoreCase(Constants.EMI)) {

			// String action = "Payment";
			// Customer cus = customerDAO.getById(primaryDepositHolder.getCustomerId());

			Float fixedRate = 0f;
			Rates rates = ratesDAO.getAllRatesByCustomerCategoryCitizenAndAccountType(
					deposit.getPrimaryCustomerCategory(), deposit.getPrimaryCitizen(),
					deposit.getPrimaryNRIAccountType());

			if (!action.equalsIgnoreCase(Constants.AUTOPAYMENT) && (deposit.getDepositCategory() == null)) {
				if (rates != null) {
					fixedRate = rates.getFdFixedPercent();
				}
			}
			if (action.equalsIgnoreCase(Constants.WITHDRAW) )
			{
				fixedRate = 0f;
				payAmount = payAmount * -1;
			}
			
			if (action.equalsIgnoreCase(Constants.EMI) )
			{
				fixedRate = 0f;
				payAmount = payAmount>=0?(payAmount * -1):payAmount;
			}
			
			Double previousTotalBalance = 0d;
			Double previousCompundFixed = 0d;
			Double previousCompoundVariable = 0d;
			Double previousBalanceFixedInterest = 0d;
			Double previousBalanceVariableInterest = 0d;

			if (previousPaymentDistribution != null) {
				previousTotalBalance = previousPaymentDistribution.getTotalBalance();
				previousCompundFixed = previousPaymentDistribution.getCompoundFixedAmt();
				previousCompoundVariable = previousPaymentDistribution.getCompoundVariableAmt();
				// previousFixedInterest =
				// previousPaymentDistribution.getFixedInterest();
				// previousVariableInterest =
				// previousPaymentDistribution.getVariableInterest();
				previousBalanceFixedInterest = previousPaymentDistribution.getBalanceFixedInterest()==null ? 0d : previousPaymentDistribution.getBalanceFixedInterest();
				previousBalanceVariableInterest = previousPaymentDistribution.getBalanceVariableInterest()==null ? 0d : previousPaymentDistribution.getBalanceVariableInterest();
			}

			Double fixAmt = this.roundToHalf(payAmount * (fixedRate / 100));
			Double variableAmt = this.roundToHalf((payAmount - fixAmt));
			Double compundFixed = this.round((fixAmt + 0 + previousCompundFixed), 2);
			Double compunndVariable = this.round((variableAmt + 0 + previousCompoundVariable), 2);
			totalBalance = this.round((compundFixed + compunndVariable), 2);

			paymentDistribution = new Distribution();
			paymentDistribution.setDepositId(deposit.getId());
			paymentDistribution.setDistributionDate(DateService.getCurrentDate());
			paymentDistribution.setAction(action);
			paymentDistribution.setDepositedAmt(payAmount);
			paymentDistribution.setFixedAmt(fixAmt);
			paymentDistribution.setVariableAmt(variableAmt);
			paymentDistribution.setActionId(actionId);
			// paymentDistribution.setFixedInterest(previousFixedInterest);
			// paymentDistribution.setVariableInterest(previousVariableInterest);
			paymentDistribution.setBalanceFixedInterest(previousBalanceFixedInterest);
			paymentDistribution.setBalanceVariableInterest(previousBalanceVariableInterest);

			paymentDistribution.setCompoundFixedAmt(this.round(compundFixed, 2));
			paymentDistribution.setCompoundVariableAmt(this.round(compunndVariable, 2));
			paymentDistribution.setTotalBalance(this.round(totalBalance, 2));
			paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

			this.insertInDepositHolderWiseDistribution(depositHolderList, action, paymentDistribution.getId(), fixAmt,
					variableAmt, null, null, null, depositHolderWisePayoff, null);
		}

		if (action.equals(Constants.INTEREST)) {

			paymentDistribution = new Distribution();
			paymentDistribution.setDistributionDate(DateService.getCurrentDate());
			paymentDistribution.setDepositId(deposit.getId());
			paymentDistribution.setAction(Constants.INTEREST);

			if (totalFixedInterest != null && totalFixedInterest > 0)
				totalFixedInterest = this.round(totalFixedInterest, 2);

			totalVariableInterest = this.round(totalVariableInterest, 2);

			if (deposit.getDepositCategory() == null) {
				paymentDistribution.setFixedInterest(totalFixedInterest);
				paymentDistribution.setCompoundFixedAmt(previousPaymentDistribution.getCompoundFixedAmt());
			} else {
				paymentDistribution.setFixedInterest(0d);
				paymentDistribution.setCompoundFixedAmt(0d);

			}

			paymentDistribution.setVariableInterest(totalVariableInterest);
			paymentDistribution.setCompoundVariableAmt(previousPaymentDistribution.getCompoundVariableAmt());

			paymentDistribution.setBalanceFixedInterest(
					(previousPaymentDistribution.getBalanceFixedInterest() == null? 0d:previousPaymentDistribution.getBalanceFixedInterest()) + totalFixedInterest);
			paymentDistribution.setBalanceVariableInterest(
					(previousPaymentDistribution.getBalanceVariableInterest() == null? 0d:previousPaymentDistribution.getBalanceVariableInterest()) + totalVariableInterest);

			paymentDistribution.setTotalBalance(previousPaymentDistribution.getCompoundFixedAmt()
					+ previousPaymentDistribution.getCompoundVariableAmt());
			paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

			// Double amount = FDService.round(totalFixedInterest + totalVariableInterest,
			// 2);
			// Insert in DepositHolderWiseDistribution
			totalBalance = paymentDistribution.getTotalBalance();
			this.insertInDepositHolderWiseDistribution(depositHolderList, Constants.INTEREST,
					paymentDistribution.getId(), null, null, totalFixedInterest, totalVariableInterest, null, null, null);
		}

		if (action.equals(Constants.INTERESTCOMPOUNDING)) {
			// Step 1: One Row will be added in Distribution table
			// ----------------------------------------------------------------------------------
			// Getting the last distribution record
			// Distribution lastDistribution =
			// paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());

			// Saving in Payment Distribution
			paymentDistribution = new Distribution();
			paymentDistribution.setDistributionDate(DateService.getCurrentDate());
			paymentDistribution.setDepositId(deposit.getId());
			paymentDistribution.setAction(action);
			paymentDistribution
					.setCompoundFixedAmt(previousPaymentDistribution.getCompoundFixedAmt() + fixedInHandInterest);
			paymentDistribution.setCompoundVariableAmt(
					previousPaymentDistribution.getCompoundVariableAmt() + variableInHandInterest);

			// InterestBalance is just like an Inhand Interest Balance After Each
			// Transaction.
			// After Compounding It will be Zero.
			// Each Time after interest calculation this will be added with previous
			// balance.
			// And In Payoff, Pay Off amount will be deducted from the balance.
			paymentDistribution.setBalanceFixedInterest(0d);
			paymentDistribution.setBalanceVariableInterest(0d);
			paymentDistribution.setTotalBalance(
					paymentDistribution.getCompoundFixedAmt() + paymentDistribution.getCompoundVariableAmt());
			paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);
			// ----------------------------------------------------------------------------------

			// 2. Rows will be added to HolderWiseDistribution table
			// ----------------------------------------------------------------------------------
			// Insert in DepositHolderWiseDistribution
			// fdService.insertInDepositHolderWiseDistribution(depositHolderList, "Interest
			// Compounding", paymentDistribution.getId(), fixedInHandInterest,
			// variableInHandInterest);

			this.insertInDepositHolderWiseDistribution(depositHolderList, Constants.INTERESTCOMPOUNDING,
					paymentDistribution.getId(), null, null, null, null, null, null, null);
			// ----------------------------------------------------------------------------------)
		}
		 
		if (action.contains(Constants.INTERESTADJUSTMENT)) {

			paymentDistribution = new Distribution();
			paymentDistribution.setDistributionDate(DateService.getCurrentDate());
			paymentDistribution.setDepositId(deposit.getId());
			paymentDistribution.setAction(action);

			if (totalFixedInterest != null && totalFixedInterest > 0)
				totalFixedInterest = this.round(totalFixedInterest, 2);

			totalVariableInterest = this.round(totalVariableInterest, 2);

			if (deposit.getDepositCategory() == null) {
				paymentDistribution.setFixedInterest(totalFixedInterest);
				paymentDistribution.setCompoundFixedAmt((previousPaymentDistribution.getCompoundFixedAmt() + totalFixedInterest) +
						(previousPaymentDistribution.getBalanceFixedInterest() == null? 0d:previousPaymentDistribution.getBalanceFixedInterest()));					
				paymentDistribution.setBalanceFixedInterest(0d);
			} else {
				paymentDistribution.setFixedInterest(0d);
				paymentDistribution.setCompoundFixedAmt(previousPaymentDistribution.getCompoundFixedAmt() +
						(previousPaymentDistribution.getBalanceFixedInterest() == null? 0d:previousPaymentDistribution.getBalanceFixedInterest()));					
				paymentDistribution.setBalanceFixedInterest(0d);

			}

			paymentDistribution.setVariableInterest(totalVariableInterest);
			paymentDistribution.setCompoundVariableAmt((previousPaymentDistribution.getCompoundVariableAmt() + totalVariableInterest) +
					(previousPaymentDistribution.getBalanceVariableInterest() == null? 0d:previousPaymentDistribution.getBalanceVariableInterest()));
			paymentDistribution.setBalanceVariableInterest(0d);

			paymentDistribution.setTotalBalance(paymentDistribution.getCompoundVariableAmt() + paymentDistribution.getCompoundFixedAmt());
			paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);
			
			// Double amount = FDService.round(totalFixedInterest + totalVariableInterest,
			// 2);
			// Insert in DepositHolderWiseDistribution
			totalBalance = paymentDistribution.getTotalBalance();
			this.insertInDepositHolderWiseDistribution(depositHolderList, action,
					paymentDistribution.getId(), null, null, totalFixedInterest, totalVariableInterest, null, null, null);
		}
		if (action.equals(Constants.PAYOFF)) {
			// Step 1: One Row will be added in Distribution table
			// ----------------------------------------------------------------------------------
			// Save the payOff record in Distribution table
			paymentDistribution = new Distribution();
			paymentDistribution.setDistributionDate(DateService.getCurrentDate());
			paymentDistribution.setDepositId(deposit.getId());
			paymentDistribution.setAction(Constants.PAYOFF);
			paymentDistribution.setFixedAmt(0.0);
			paymentDistribution.setVariableAmt(0.0);
			paymentDistribution.setCompoundFixedAmt(previousPaymentDistribution.getCompoundFixedAmt());
			paymentDistribution.setCompoundVariableAmt(previousPaymentDistribution.getCompoundVariableAmt()); //???
			paymentDistribution.setBalanceFixedInterest(previousPaymentDistribution.getBalanceFixedInterest());
			paymentDistribution.setBalanceVariableInterest((previousPaymentDistribution.getBalanceVariableInterest() ==null?
					0d:previousPaymentDistribution.getBalanceVariableInterest()) - totalPayoffAmt);
			paymentDistribution.setPayOffAmt(totalPayoffAmt);
			paymentDistribution.setTotalBalance(previousPaymentDistribution.getTotalBalance());
			paymentDistribution.setPayOffStatus(Constants.DONEPS);
			paymentDistribution.setDistributionDate(DateService.getCurrentDate());
			paymentDistribution.setDepositedAmt(null);
			paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);
			// ----------------------------------------------------------------------------------
			

			// 2. Rows will be added to HolderWiseDistribution table
			// ----------------------------------------------------------------------------------
			// Insert in DepositHolderWiseDistribution
			// fdService.insertInDepositHolderWiseDistribution(depositHolderList, "Interest
			// Compounding", paymentDistribution.getId(), fixedInHandInterest,
			// variableInHandInterest);

			this.insertInDepositHolderWiseDistribution(depositHolderList,Constants.PAYOFF,
					paymentDistribution.getId(), null, null, null, null, null, depositHolderWisePayoff, null);
			// ----------------------------------------------------------------------------------)
		}
		if(action.equalsIgnoreCase(Constants.PENALTY) || action.equalsIgnoreCase(Constants.PENALTY1))
		{
			
			// Step 1: One Row will be added in Distribution table
			// ----------------------------------------------------------------------------------
			paymentDistribution = new Distribution();
			paymentDistribution.setDistributionDate(DateService.getCurrentDate());
			paymentDistribution.setDepositId(deposit.getId());
			paymentDistribution.setAction(action);
			
			paymentDistribution.setDepositedAmt(-penaltyAmount);


			if (penaltyAmount <= previousPaymentDistribution.getBalanceVariableInterest()) {
				paymentDistribution.setBalanceFixedInterest(previousPaymentDistribution.getBalanceFixedInterest());
				paymentDistribution.setBalanceVariableInterest(
						round(((previousPaymentDistribution.getBalanceVariableInterest()==null? 0d:previousPaymentDistribution.getBalanceVariableInterest()) - penaltyAmount), 2));
				
				paymentDistribution.setCompoundFixedAmt(previousPaymentDistribution.getCompoundFixedAmt());
				paymentDistribution
				.setCompoundVariableAmt(
						round(((previousPaymentDistribution.getCompoundVariableAmt()==null? 0d:previousPaymentDistribution.getCompoundVariableAmt()) - penaltyAmount), 2));
				
			} else {
				paymentDistribution.setBalanceVariableInterest(0.0);
				paymentDistribution.setBalanceFixedInterest(round((previousPaymentDistribution.getBalanceFixedInterest()==null? 0d:previousPaymentDistribution.getBalanceFixedInterest())
						- (penaltyAmount - (previousPaymentDistribution.getBalanceVariableInterest()==null? 0d:previousPaymentDistribution.getBalanceVariableInterest())), 2));
				
				paymentDistribution.setCompoundVariableAmt(0.0);
				paymentDistribution.setCompoundFixedAmt(round((previousPaymentDistribution.getCompoundFixedAmt() -
						(penaltyAmount - previousPaymentDistribution.getCompoundVariableAmt())),
						2));
			}

			paymentDistribution.setTotalBalance(
					round((paymentDistribution.getCompoundVariableAmt() + paymentDistribution.getCompoundFixedAmt()), 2));
			paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);
			// ----------------------------------------------------------------------------------
			
			// 2. Rows will be added to HolderWiseDistribution table
			// ----------------------------------------------------------------------------------
			this.insertInDepositHolderWiseDistribution(depositHolderList, action,
					paymentDistribution.getId(), null, null, null, null, null, depositHolderWisePayoff, penaltyAmount);
			// ----------------------------------------------------------------------------------
		}
		
		if(action.equalsIgnoreCase(Constants.WithdrawnInRenewal))
		{
			Float fixedRate = 0f;
			Rates rates = ratesDAO.getAllRatesByCustomerCategoryCitizenAndAccountType(
					deposit.getPrimaryCustomerCategory(), deposit.getPrimaryCitizen(),
					deposit.getPrimaryNRIAccountType());

			fixedRate = rates.getFdFixedPercent();
			payAmount = payAmount * -1;
			
		
			//Double previousTotalBalance = 0d;
			Double previousCompundFixed = 0d;
			Double previousCompoundVariable = 0d;
			Double previousBalanceFixedInterest = 0d;
			Double previousBalanceVariableInterest = 0d;

			if (previousPaymentDistribution != null) {
				//previousTotalBalance = previousPaymentDistribution.getTotalBalance();
				previousCompundFixed = previousPaymentDistribution.getCompoundFixedAmt();
				previousCompoundVariable = previousPaymentDistribution.getCompoundVariableAmt();
				// previousFixedInterest =
				// previousPaymentDistribution.getFixedInterest();
				// previousVariableInterest =
				// previousPaymentDistribution.getVariableInterest();
				previousBalanceFixedInterest = previousPaymentDistribution.getBalanceFixedInterest()==null?0d: previousPaymentDistribution.getBalanceFixedInterest();
				previousBalanceVariableInterest = previousPaymentDistribution.getBalanceVariableInterest()==null?0d: previousPaymentDistribution.getBalanceVariableInterest();
			}

			Double fixAmt = this.roundToHalf(payAmount * (fixedRate / 100));
			Double variableAmt = this.roundToHalf((payAmount - fixAmt));
			Double compundFixed = this.round((fixAmt + 0 + previousCompundFixed), 2);
			Double compunndVariable = this.round((variableAmt + 0 + previousCompoundVariable), 2);
			totalBalance = this.round((compundFixed + compunndVariable), 2);

			paymentDistribution = new Distribution();
			paymentDistribution.setDepositId(deposit.getId());
			paymentDistribution.setDistributionDate(DateService.getCurrentDate());
			paymentDistribution.setAction(action);
			paymentDistribution.setDepositedAmt(payAmount);
			paymentDistribution.setFixedAmt(fixAmt);
			paymentDistribution.setVariableAmt(variableAmt);
			paymentDistribution.setActionId(actionId);
			// paymentDistribution.setFixedInterest(previousFixedInterest);
			// paymentDistribution.setVariableInterest(previousVariableInterest);
			paymentDistribution.setBalanceFixedInterest(previousBalanceFixedInterest);
			paymentDistribution.setBalanceVariableInterest(previousBalanceVariableInterest);

			paymentDistribution.setCompoundFixedAmt(this.round(compundFixed, 2));
			paymentDistribution.setCompoundVariableAmt(this.round(compunndVariable, 2));
			paymentDistribution.setTotalBalance(this.round(totalBalance, 2));
			paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

			this.insertInDepositHolderWiseDistribution(depositHolderList, action, paymentDistribution.getId(), fixAmt,
					variableAmt, null, null, null, depositHolderWisePayoff, null);
		}
		if(action.equalsIgnoreCase(Constants.Renew))
		{
			Float fixedRate = 0f;
			Rates rates = ratesDAO.getAllRatesByCustomerCategoryCitizenAndAccountType(
					deposit.getPrimaryCustomerCategory(), deposit.getPrimaryCitizen(),
					deposit.getPrimaryNRIAccountType());

			fixedRate = rates.getFdFixedPercent();
			
		
			//Double previousTotalBalance = 0d;
			Double previousCompundFixed = 0d;
			Double previousCompoundVariable = 0d;
			Double previousBalanceFixedInterest = 0d;
			Double previousBalanceVariableInterest = 0d;

//			if (previousPaymentDistribution != null) {
//				//previousTotalBalance = previousPaymentDistribution.getTotalBalance();
//				previousCompundFixed = previousPaymentDistribution.getCompoundFixedAmt();
//				previousCompoundVariable = previousPaymentDistribution.getCompoundVariableAmt();
//				// previousFixedInterest =
//				// previousPaymentDistribution.getFixedInterest();
//				// previousVariableInterest =
//				// previousPaymentDistribution.getVariableInterest();
//				previousBalanceFixedInterest = previousPaymentDistribution.getBalanceFixedInterest();
//				previousBalanceVariableInterest = previousPaymentDistribution.getBalanceVariableInterest();
//			}

			Double fixAmt = this.roundToHalf(payAmount * (fixedRate / 100));
			Double variableAmt = this.roundToHalf((payAmount - fixAmt));
			Double compundFixed = this.round((fixAmt + 0 + previousCompundFixed), 2);
			Double compunndVariable = this.round((variableAmt + 0 + previousCompoundVariable), 2);
			totalBalance = this.round((compundFixed + compunndVariable), 2);

			paymentDistribution = new Distribution();
			paymentDistribution.setDepositId(deposit.getId());
			paymentDistribution.setDistributionDate(DateService.getCurrentDate());
			paymentDistribution.setAction(action);
			paymentDistribution.setDepositedAmt(payAmount);
			paymentDistribution.setFixedAmt(fixAmt);
			paymentDistribution.setVariableAmt(variableAmt);
			paymentDistribution.setActionId(actionId);
			paymentDistribution.setFixedInterest(null);
			paymentDistribution.setVariableInterest(null);
			paymentDistribution.setBalanceFixedInterest(null);
			paymentDistribution.setBalanceVariableInterest(null);

			paymentDistribution.setCompoundFixedAmt(this.round(compundFixed, 2));
			paymentDistribution.setCompoundVariableAmt(this.round(compunndVariable, 2));
			paymentDistribution.setTotalBalance(this.round(totalBalance, 2));
			paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

			this.insertInDepositHolderWiseDistribution(depositHolderList, action, paymentDistribution.getId(), fixAmt,
					variableAmt, null, null, null, depositHolderWisePayoff, null);
		}
		
		if(action.equalsIgnoreCase(Constants.DEPOSITCONVERTED))
		{
		

			paymentDistribution = new Distribution();
			paymentDistribution.setDepositId(deposit.getId());
			paymentDistribution.setDistributionDate(DateService.getCurrentDate());
			paymentDistribution.setAction(action);
			paymentDistribution.setDepositedAmt(null);
			paymentDistribution.setFixedAmt(null);
			paymentDistribution.setVariableAmt(null);
			paymentDistribution.setActionId(actionId);
			paymentDistribution.setFixedInterest(null);
			paymentDistribution.setVariableInterest(null);
			paymentDistribution.setBalanceFixedInterest(null);
			paymentDistribution.setBalanceVariableInterest(null);

			paymentDistribution.setCompoundFixedAmt(null);
			paymentDistribution.setCompoundVariableAmt(null);
			paymentDistribution.setTotalBalance(null);
			paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

			this.insertInDepositHolderWiseDistribution(depositHolderList, action, paymentDistribution.getId(), null,
					null, null, null, null, depositHolderWisePayoff, null);
		}
		

		// update the deposit table

		deposit.setCurrentBalance(totalBalance);
		System.out.println("totalBalance++++" + totalBalance);
		fixedDepositDAO.updateFD(deposit);
		
		return paymentDistribution;

	}

	// This is for all transaction except payoff
	public void insertInDepositHolderWiseDistribution(List<DepositHolder> depositHolderList, String action,
			Long distributionId, Double fixedAmount, Double variableAmount, Double fixedInterest,
			Double variableInterest, Double TDSAmount, HashMap<Long, Double> depositHolderWisePayoff, Double penaltyAmount) {

		
			for (int i = 0; i < depositHolderList.size(); i++) {
														
				DepositHolderWiseDistribution depHolderDistribution = new DepositHolderWiseDistribution();
				depHolderDistribution.setAction(action);
				depHolderDistribution.setActionId(distributionId);
				depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
				depHolderDistribution.setCustomerId(depositHolderList.get(i).getCustomerId());
				depHolderDistribution.setDepositHolderId(depositHolderList.get(i).getId());
				depHolderDistribution.setDepositId(depositHolderList.get(i).getDepositId());
				depHolderDistribution.setContribution(depositHolderList.get(i).getContribution());

				// Get the last distribution of the holder from holderwisedistribution
				DepositHolderWiseDistribution lastDistribution = depositHolderWiseDistributionDAO
						.getDepositHolderWiseLastDistribution(depositHolderList.get(i).getDepositId(),
								Long.parseLong(depositHolderList.get(i).getId().toString()));

				if (action.equalsIgnoreCase(Constants.PAYMENT) || action.equalsIgnoreCase(Constants.WITHDRAW)) {
					if (fixedAmount != null)
						fixedAmount = fixedAmount * depositHolderList.get(i).getContribution() / 100;

					if (variableAmount != null)
						variableAmount = variableAmount * depositHolderList.get(i).getContribution() / 100;

					depHolderDistribution.setFixedAmt(fixedAmount);
					depHolderDistribution.setVariableAmt(variableAmount);

					if (lastDistribution == null) {
						depHolderDistribution.setFixedCompoundAmount(fixedAmount);
						depHolderDistribution.setVariableCompoundAmount(variableAmount);
						
					} else {
						depHolderDistribution
								.setFixedCompoundAmount((lastDistribution.getFixedCompoundAmount()==null?0d :
									lastDistribution.getFixedCompoundAmount())  + fixedAmount);
						depHolderDistribution.setVariableCompoundAmount(
								(lastDistribution.getVariableCompoundAmount()==null?0d: lastDistribution.getVariableCompoundAmount()) + variableAmount);
					
					}

				}

				else if (action.equalsIgnoreCase(Constants.INTEREST)) {
					
					// Get the last distribution of the holder from holderwisedistribution
					lastDistribution = depositHolderWiseDistributionDAO
							.getDepositHolderWiseLastDistribution(depositHolderList.get(i).getDepositId(),
									depositHolderList.get(i).getId());
					
					if (fixedInterest != null)
						fixedInterest = fixedInterest * depositHolderList.get(i).getContribution() / 100;
					else
						fixedInterest = 0d;

					if (variableInterest != null)
						variableInterest = variableInterest * depositHolderList.get(i).getContribution() / 100;
					else
						variableInterest = 0d;

					depHolderDistribution.setFixedInterest(fixedInterest);
					depHolderDistribution.setVariableInterest(variableInterest);

					Double fixedCompoundAmount =(lastDistribution == null? 0d : (lastDistribution.getFixedCompoundAmount() == null? 0d: lastDistribution.getFixedCompoundAmount()));
					Double variableCompoundAmount =(lastDistribution == null? 0d : (lastDistribution.getVariableCompoundAmount()==null?0d: lastDistribution.getVariableCompoundAmount()));
					
					depHolderDistribution.setFixedCompoundAmount(fixedCompoundAmount);
					depHolderDistribution.setVariableCompoundAmount(variableCompoundAmount);

					depHolderDistribution.setBalanceFixedInterest(lastDistribution== null? 0d :
							(lastDistribution.getBalanceFixedInterest()==null?0d: lastDistribution.getBalanceFixedInterest()) + fixedInterest);
					depHolderDistribution.setBalanceVariableInterest(lastDistribution== null? 0d :
							(lastDistribution.getBalanceVariableInterest()==null?0d:lastDistribution.getBalanceVariableInterest()) + variableInterest);
				} 
				else if (action.contains(Constants.TDS)) {
					// TDS is not yet Implemented

				} else if (action.contains(Constants.INTERESTCOMPOUNDING)) {

					// Get Interest Summary of DepositHolders of the deposit
					DepositSummaryHolderWise lastHolderWisedepositSummary = depositSummaryDAO
							.getHolderWiseDepositSummary(depositHolderList.get(i).getDepositId(), depositHolderList.get(i).getId());
					
					depHolderDistribution.setFixedCompoundAmount(lastDistribution.getFixedCompoundAmount() + 
							(lastHolderWisedepositSummary.getTotalFixedInterestInHand()==null?0d:lastHolderWisedepositSummary.getTotalFixedInterestInHand()));
					depHolderDistribution.setVariableCompoundAmount(lastDistribution.getVariableCompoundAmount() + 
							(lastHolderWisedepositSummary.getTotalVariableInterestInHand()==null?0d:lastHolderWisedepositSummary.getTotalVariableInterestInHand()));

					// InterestBalance is just like an Inhand Interest Balance After Each
					// Transaction.
					// After Compounding It will be Zero.
					// Each Time after interest calculation this will be added with previous
					// balance.
					// And In Payoff, Pay Off amount will be deducted from the balance.
					depHolderDistribution.setBalanceFixedInterest(0d);
					depHolderDistribution.setBalanceVariableInterest(0d);

				}
				else if (action.contains(Constants.INTERESTADJUSTMENT)) {
					
					// Get the last distribution of the holder from holderwisedistribution
					lastDistribution = depositHolderWiseDistributionDAO
							.getDepositHolderWiseLastDistribution(depositHolderList.get(i).getDepositId(),
									depositHolderList.get(i).getId());
					
					if (fixedInterest != null)
						fixedInterest = fixedInterest * depositHolderList.get(i).getContribution() / 100;
					else
						fixedInterest = 0d;

					if (variableInterest != null)
						variableInterest = variableInterest * depositHolderList.get(i).getContribution() / 100;
					else
						variableInterest = 0d;

					depHolderDistribution.setFixedInterest(fixedInterest);
					depHolderDistribution.setVariableInterest(variableInterest);

					Double fixedCompoundAmount =(lastDistribution == null? 0d : (lastDistribution.getFixedCompoundAmount() == null? 0d: lastDistribution.getFixedCompoundAmount()));
					Double variableCompoundAmount =(lastDistribution == null? 0d : (lastDistribution.getVariableCompoundAmount()==null?0d: lastDistribution.getVariableCompoundAmount()));
					
					depHolderDistribution.setFixedInterest(fixedInterest);
					depHolderDistribution.setFixedCompoundAmount((lastDistribution.getFixedCompoundAmount() + fixedInterest) +
								(lastDistribution.getBalanceFixedInterest() == null? 0d:lastDistribution.getBalanceFixedInterest()));					
					depHolderDistribution.setBalanceFixedInterest(0d);
					

					depHolderDistribution.setVariableInterest(variableCompoundAmount);

					depHolderDistribution.setVariableCompoundAmount((lastDistribution.getVariableCompoundAmount() + variableInterest) +
							(lastDistribution.getBalanceVariableInterest() == null? 0d:lastDistribution.getBalanceVariableInterest()));
					depHolderDistribution.setBalanceVariableInterest(0d);
					
					depHolderDistribution.setFixedCompoundAmount(fixedCompoundAmount);
					depHolderDistribution.setVariableCompoundAmount(variableCompoundAmount);

					depHolderDistribution.setBalanceFixedInterest(lastDistribution== null? 0d :
							(lastDistribution.getBalanceFixedInterest()==null?0d: lastDistribution.getBalanceFixedInterest()) + fixedInterest);
					depHolderDistribution.setBalanceVariableInterest(lastDistribution== null? 0d :
							(lastDistribution.getBalanceVariableInterest()==null?0d:lastDistribution.getBalanceVariableInterest()) + variableInterest);
				} 
				else if (action.equalsIgnoreCase(Constants.PAYOFF)) {		    	

					Double holderPayoffAmount = depositHolderWisePayoff.get(depositHolderList.get(i).getId());

					// Get the last distribution of the holder from holder wise distribution
					DepositHolderWiseDistribution lastHolderWiseDistribution = depositHolderWiseDistributionDAO.getDepositHolderWiseLastDistribution(depositHolderList.get(i).getDepositId(), depositHolderList.get(i).getId());	
							
					depHolderDistribution = new DepositHolderWiseDistribution();
					depHolderDistribution.setAction(Constants.PAYOFF);
					depHolderDistribution.setActionId(distributionId);
					depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
					depHolderDistribution.setCustomerId(depositHolderList.get(i).getCustomerId());
					depHolderDistribution.setDepositHolderId(depositHolderList.get(i).getId());
					depHolderDistribution.setDepositId(depositHolderList.get(i).getDepositId());
					depHolderDistribution.setContribution(depositHolderList.get(i).getContribution());
										
					depHolderDistribution.setPayoffAmount(holderPayoffAmount);
									
					depHolderDistribution.setFixedCompoundAmount(lastHolderWiseDistribution.getFixedCompoundAmount());
					depHolderDistribution.setVariableCompoundAmount(lastHolderWiseDistribution.getVariableCompoundAmount());
					
					depHolderDistribution.setBalanceFixedInterest(lastHolderWiseDistribution.getBalanceFixedInterest());
					depHolderDistribution.setBalanceFixedInterest((lastDistribution.getBalanceVariableInterest()==null?0d:lastDistribution.getBalanceVariableInterest())  - holderPayoffAmount);
				
					
				}
				
				else if(action.equalsIgnoreCase(Constants.PENALTY) || action.equalsIgnoreCase(Constants.PENALTY1))
				{
					// Get the last distribution of the holder from holder wise distribution
					DepositHolderWiseDistribution lastHolderWiseDistribution = depositHolderWiseDistributionDAO.getDepositHolderWiseLastDistribution(depositHolderList.get(i).getDepositId(), depositHolderList.get(i).getId());	
				
					// Step 1: One Row will be added in Distribution table
					// ----------------------------------------------------------------------------------
					depHolderDistribution = new DepositHolderWiseDistribution();
					depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
					depHolderDistribution.setAction(action);
					depHolderDistribution.setActionId(distributionId);
					depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
					depHolderDistribution.setCustomerId(depositHolderList.get(i).getCustomerId());
					depHolderDistribution.setDepositHolderId(depositHolderList.get(i).getId());
					depHolderDistribution.setDepositId(depositHolderList.get(i).getDepositId());
					depHolderDistribution.setContribution(depositHolderList.get(i).getContribution());
						
					Double contributedPenalty = 0d;
					if (penaltyAmount != null)
						contributedPenalty = penaltyAmount * depositHolderList.get(i).getContribution() / 100;


					if (contributedPenalty <= lastHolderWiseDistribution.getBalanceVariableInterest()) {
						depHolderDistribution.setBalanceFixedInterest(lastHolderWiseDistribution.getBalanceFixedInterest());
						depHolderDistribution.setBalanceVariableInterest(
								round((lastHolderWiseDistribution.getBalanceVariableInterest() - contributedPenalty), 2));
						
						depHolderDistribution.setFixedCompoundAmount(lastHolderWiseDistribution.getFixedCompoundAmount());
						depHolderDistribution
						.setVariableCompoundAmount(round((lastHolderWiseDistribution.getVariableCompoundAmount() - contributedPenalty), 2));
						
					} else {
						depHolderDistribution.setBalanceVariableInterest(0.0);
						depHolderDistribution.setBalanceFixedInterest(round((lastHolderWiseDistribution.getBalanceFixedInterest()==null? 0d:lastHolderWiseDistribution.getBalanceFixedInterest())
								- (contributedPenalty - (lastHolderWiseDistribution.getBalanceVariableInterest()==null? 0d:lastHolderWiseDistribution.getBalanceVariableInterest())), 2));
						
						depHolderDistribution.setVariableCompoundAmount(0.0);
						depHolderDistribution.setFixedCompoundAmount(round((lastHolderWiseDistribution.getFixedCompoundAmount() -
								(contributedPenalty - lastHolderWiseDistribution.getVariableCompoundAmount())),
								2));
					}
				}
				else if(action.equalsIgnoreCase(Constants.WithdrawnInRenewal))
				{
					// Get the last distribution of the holder from holder wise distribution
					DepositHolderWiseDistribution lastHolderWiseDistribution = depositHolderWiseDistributionDAO.getDepositHolderWiseLastDistribution(depositHolderList.get(i).getDepositId(), depositHolderList.get(i).getId());	
				
					
					//Double previousTotalBalance = 0d;
					Double previousCompundFixed = 0d;
					Double previousCompoundVariable = 0d;
					Double previousBalanceFixedInterest = 0d;
					Double previousBalanceVariableInterest = 0d;

					if (lastHolderWiseDistribution != null) {
						//previousTotalBalance = previousPaymentDistribution.getTotalBalance();
						previousCompundFixed = lastHolderWiseDistribution.getFixedCompoundAmount();
						previousCompoundVariable = lastHolderWiseDistribution.getVariableCompoundAmount();
						// previousFixedInterest =
						// previousPaymentDistribution.getFixedInterest();
						// previousVariableInterest =
						// previousPaymentDistribution.getVariableInterest();
						previousBalanceFixedInterest = lastHolderWiseDistribution.getBalanceFixedInterest()==null?0d: lastHolderWiseDistribution.getBalanceFixedInterest();
						previousBalanceVariableInterest = lastHolderWiseDistribution.getBalanceVariableInterest()==null?0d: lastHolderWiseDistribution.getBalanceVariableInterest();
					}

			
					Double compundFixed = this.round((fixedAmount + 0 + previousCompundFixed), 2);
					Double compunndVariable = this.round((variableAmount + 0 + previousCompoundVariable), 2);
					Double totalBalance = this.round((compundFixed + compunndVariable), 2);

					// Step 1: One Row will be added in Distribution table
					// ----------------------------------------------------------------------------------
					depHolderDistribution = new DepositHolderWiseDistribution();
					depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
					depHolderDistribution.setAction(Constants.PENALTY);
					depHolderDistribution.setActionId(distributionId);
					depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
					depHolderDistribution.setCustomerId(depositHolderList.get(i).getCustomerId());
					depHolderDistribution.setDepositHolderId(depositHolderList.get(i).getId());
					depHolderDistribution.setDepositId(depositHolderList.get(i).getDepositId());
					depHolderDistribution.setContribution(depositHolderList.get(i).getContribution());
					// paymentDistribution.setFixedInterest(previousFixedInterest);
					// paymentDistribution.setVariableInterest(previousVariableInterest);
					depHolderDistribution.setBalanceFixedInterest(previousBalanceFixedInterest);
					depHolderDistribution.setBalanceVariableInterest(previousBalanceVariableInterest);

					depHolderDistribution.setFixedCompoundAmount(this.round(compundFixed, 2));
					depHolderDistribution.setVariableCompoundAmount(this.round(compunndVariable, 2));
			
				}
				else if(action.equalsIgnoreCase(Constants.Renew))
				{
					

					Double compundFixed = this.round(fixedAmount, 2);
					Double compunndVariable = this.round(variableAmount, 2);
					
					depHolderDistribution = new DepositHolderWiseDistribution();
					depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
					depHolderDistribution.setAction(Constants.PENALTY);
					depHolderDistribution.setActionId(distributionId);
					depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
					depHolderDistribution.setCustomerId(depositHolderList.get(i).getCustomerId());
					depHolderDistribution.setDepositHolderId(depositHolderList.get(i).getId());
					depHolderDistribution.setDepositId(depositHolderList.get(i).getDepositId());
					depHolderDistribution.setContribution(depositHolderList.get(i).getContribution());
					
					depHolderDistribution.setFixedAmt(fixedAmount);
					depHolderDistribution.setVariableAmt(variableAmount);
					depHolderDistribution.setFixedInterest(null);
					depHolderDistribution.setVariableInterest(null);
					depHolderDistribution.setBalanceFixedInterest(null);
					depHolderDistribution.setBalanceVariableInterest(null);

					depHolderDistribution.setFixedCompoundAmount(compundFixed);
					depHolderDistribution.setVariableCompoundAmount(compunndVariable);

				}

				else if(action.equalsIgnoreCase(Constants.DEPOSITCONVERTED))
				{
					
					depHolderDistribution = new DepositHolderWiseDistribution();
					depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
					depHolderDistribution.setAction(action);
					depHolderDistribution.setActionId(distributionId);
					depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
					depHolderDistribution.setCustomerId(depositHolderList.get(i).getCustomerId());
					depHolderDistribution.setDepositHolderId(depositHolderList.get(i).getId());
					depHolderDistribution.setDepositId(depositHolderList.get(i).getDepositId());
					depHolderDistribution.setContribution(depositHolderList.get(i).getContribution());
					
					depHolderDistribution.setFixedAmt(null);
					depHolderDistribution.setVariableAmt(null);
					depHolderDistribution.setFixedInterest(null);
					depHolderDistribution.setVariableInterest(null);
					depHolderDistribution.setBalanceFixedInterest(null);
					depHolderDistribution.setBalanceVariableInterest(null);

					depHolderDistribution.setFixedCompoundAmount(null);
					depHolderDistribution.setVariableCompoundAmount(null);

				}
				depositHolderWiseDistributionDAO.saveDepositHolderWiseDistribution(depHolderDistribution);
			}
		}
	
	
	public Distribution adjustInterestForWithdraw(Deposit deposit, Distribution lastDistribution,
			List<DepositHolder> depositHolderList, Distribution lastBaseLine, ProductConfiguration productConfiguration) 
					throws CustomException
	
	{
		// Insert in Distribution and holderwise distribution
		lastDistribution = this.InsertInDistributionForWithdrawAdjustment(deposit, lastDistribution, depositHolderList, lastBaseLine);
		
		DepositSummary depositSummary = this.upsertInDepositSummary(deposit, "Interest Adjustment For Withdraw", null, null, null, null, null,
				depositHolderList, null, lastDistribution.getVariableInterest(), null);
		//this.updateInDepositSummaryForWithdrawAdjustment(deposit, lastDistribution.getVariableInterest(), depositHolderList);
		
		// Insert in Journal & Ledger
		//-----------------------------------------------------------
		
		ModeOfPayment modeOfpayment = modeOfPaymentDAO.getModeOfPayment("Internal Transfer");
		if(modeOfpayment!=null)
		{
			ledgerService.insertJournal(deposit.getId(), this.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(), 
				deposit.getAccountNumber(), null, Event.INTEREST_ADJUSTMENT.getValue(), lastDistribution.getVariableInterest(), modeOfpayment.getId(),
				depositSummary.getTotalPrincipal(), null);
		}
		//-----------------------------------------------------------
		
		return lastDistribution;
	}
	
	private Distribution InsertInDistributionForWithdrawAdjustment(Deposit deposit, Distribution lastDistribution,
			List<DepositHolder> depositHolderList, Distribution lastBaseLine)

	{
		
		// Getting the duration from the base
		Integer durationFromBase = DateService.getDaysBetweenTwoDates(lastBaseLine.getDistributionDate(),
				DateService.getCurrentDate()) + 1;

		String action = "Interest Adjustment For Withdraw";

		// Get total Interest earned from Baseline
		// It included both fixed and variable amount
		// From there We have to find out only variable interest
		// as withdraw is done only from variable balance

		
		// Get the Interest from the Distribution that needs to be adjusted
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(deposit.getId());

		Double fixedInterest = 0d;
		Double variableInterest = (depositSummary.getTotalVariableInterestAccumulated() == null)? 0d: depositSummary.getTotalVariableInterestAccumulated();
		variableInterest = variableInterest + ((depositSummary.getTotalVariableInterestPaidOff() == null)? 0d: 
			depositSummary.getTotalVariableInterestPaidOff());
		
		// Add the TDS with the accumulated Interest
		Double totalTDS=tdsDAO.getTotalTDS(deposit.getId(), lastBaseLine.getDistributionDate(), DateService.getCurrentDate());
		
		variableInterest = this.roundToHalf(variableInterest + totalTDS); 	
		
		// Finding the interestRate for this much duration
		Float interestRate = this.getDepositInterestRate(durationFromBase,
				deposit.getPrimaryCustomerCategory(), deposit.getDepositCurrency(), deposit.getDepositAmount(),
				deposit.getDepositClassification(), deposit.getPrimaryCitizen(),
				deposit.getPrimaryNRIAccountType());
		
		Double variableInterestShouldGet = ((lastBaseLine.getCompoundVariableAmt() * interestRate/100/365) * durationFromBase);
		variableInterestShouldGet = this.roundToHalf(variableInterestShouldGet);
		
		variableInterest = (variableInterest-variableInterestShouldGet) * -1;


		// Insert the interest adjustment in Interest table
		Interest interest = new Interest();
		interest.setDepositId(deposit.getId());
		interest.setFinancialYear(DateService.getFinancialYear(DateService.loginDate));
		interest.setInterestAmount(variableInterest);
		interest.setInterestDate(DateService.loginDate);
		interest.setIsCalculated(1);
		interest.setInterestRate(round((double) interestRate, 2));
		//interest.setInterestSum(round((compoundFixedAmt + compoundVariableAmt), 2));
		interestDAO.insert(interest);
		
		
		// Insert in Distribution
		Distribution paymentDistribution = new Distribution();
		paymentDistribution.setAction(action);
		paymentDistribution.setDistributionDate(DateService.getCurrentDate());

		paymentDistribution.setFixedInterest(null);
		paymentDistribution.setVariableInterest(variableInterest);

		Double inhandFixedInterest = depositSummary.getTotalFixedInterestInHand() == null? 0: depositSummary.getTotalFixedInterestInHand();
		
		paymentDistribution.setCompoundFixedAmt(lastDistribution.getCompoundFixedAmt() + inhandFixedInterest);
		paymentDistribution.setCompoundVariableAmt(lastDistribution.getCompoundVariableAmt() + variableInterest);

		paymentDistribution.setTotalBalance(paymentDistribution.getCompoundFixedAmt() + paymentDistribution.getCompoundVariableAmt());
		paymentDistribution.setDepositId(deposit.getId());
		paymentDistribution.setDepositHolderId(this.getPrimaryHolderId(depositHolderList));
		// paymentDistribution.setActionId(withdrawDeposit.getId());
		paymentDistribution.setBalanceFixedInterest(0d);
		paymentDistribution
				.setBalanceVariableInterest(0d);

		lastDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

		// this.insertInDepositHolderWiseDistribution(depositHolderList, action, null, fixedInterest + variableInterest);
		
		// Insert in Holderwise Distribution
		for (int i = 0; i < depositHolderList.size(); i++) {
					
			DepositHolderWiseDistribution depHolderDistribution = new DepositHolderWiseDistribution();
			depHolderDistribution.setAction(action);
			depHolderDistribution.setActionId(lastDistribution.getId());
			depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
			depHolderDistribution.setCustomerId(depositHolderList.get(i).getCustomerId());
			depHolderDistribution.setDepositHolderId(depositHolderList.get(i).getId());
			depHolderDistribution.setDepositId(depositHolderList.get(i).getDepositId());
			depHolderDistribution.setContribution(depositHolderList.get(i).getContribution());
			
			// Get the last distribution of the holder from holderwisedistribution
			DepositHolderWiseDistribution lastHolderawiseDistribution = depositHolderWiseDistributionDAO
					.getDepositHolderWiseLastDistribution(depositHolderList.get(i).getDepositId(),
							depositHolderList.get(i).getId());
			
			if (fixedInterest != null)
				fixedInterest = fixedInterest * depositHolderList.get(i).getContribution() / 100;
			else
				fixedInterest = 0d;

			if (variableInterest != null)
				variableInterest = variableInterest * depositHolderList.get(i).getContribution() / 100;
			else
				variableInterest = 0d;

			depHolderDistribution.setFixedInterest(fixedInterest);
			depHolderDistribution.setVariableInterest(variableInterest);

			DepositSummaryHolderWise depositHolderWiseSummary = depositSummaryDAO.getHolderWiseDepositSummary(deposit.getId(), depositHolderList.get(i).getId());

			Double inhandFixedInterestDepHolder = depositHolderWiseSummary.getTotalFixedInterestInHand() == null? 0: depositHolderWiseSummary.getTotalFixedInterestInHand();
			depHolderDistribution.setFixedCompoundAmount(lastHolderawiseDistribution.getFixedCompoundAmount() + inhandFixedInterestDepHolder);
			depHolderDistribution.setBalanceFixedInterest(0d);
							
			depHolderDistribution.setVariableCompoundAmount(lastHolderawiseDistribution.getVariableCompoundAmount());
			depHolderDistribution.setBalanceVariableInterest(0d);
								
			
			depositHolderWiseDistributionDAO.saveDepositHolderWiseDistribution(depHolderDistribution);
		}

		// update the deposit table
		deposit.setCurrentBalance(lastDistribution.getCompoundFixedAmt() + lastDistribution.getCompoundVariableAmt());
		fixedDepositDAO.updateFD(deposit);

		
		return lastDistribution;
	}
	
	
	public DepositSummary updateInDepositSummaryForWithdrawAdjustment(Deposit deposit, 
			Double variableInterest, List<DepositHolder> depositHolderList) {

		// getAllRatesByCustomerCategoryCitizenAndAccountType
		// Get last Deposit Summary
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(deposit.getId());
//		List<DepositSummaryHolderWise> interestSummaryHolderWiseList = depositSummaryDAO
//				.getHolderWiseDepositSummary(deposit.getId());

//		Rates rate = ratesDAO.getAllRatesByCustomerCategoryCitizenAndAccountType(deposit.getPrimaryCustomerCategory(),
//				deposit.getPrimaryCitizen(), deposit.getPrimaryNRIAccountType());
//		

		// Update in DepositSummary Table
		// ---------------------------------------------------------
		variableInterest = variableInterest == null ? 0d : this.round(variableInterest,2);
		
		// Make it positive
		if(variableInterest<0)
			variableInterest = this.roundToHalf(variableInterest) *-1;

		depositSummary.setTotalVariableIneterestAdjusted((depositSummary.getTotalVariableIneterestAdjusted() == null ? 0
				: depositSummary.getTotalVariableIneterestAdjusted()) + variableInterest);

		if(depositSummary.getTotalInterestAccumulated()>= variableInterest)
		{
			depositSummary.setTotalInterestAccumulated((depositSummary.getTotalInterestAccumulated() == null ? 0
				: depositSummary.getTotalInterestAccumulated()) - variableInterest);			
		}
		else
		{
			Double balInt = (depositSummary.getTotalInterestAccumulated() == null ? 0
					: depositSummary.getTotalInterestAccumulated())-variableInterest;
			
			depositSummary.setTotalInterestAccumulated(0d); 
			depositSummary.setTotalVariableInterestInHand((depositSummary.getTotalVariableInterestInHand() == null ? 0
					: depositSummary.getTotalVariableInterestInHand()) - balInt);
						
		}

		depositSummary = depositSummaryDAO.update(depositSummary);
		// ---------------------------------------------------------

		// Update in InterestSummaryHolderWise
		// ---------------------------------------------------------
		Double contributedVariableInterest = null;
		for (int i = 0; i < depositHolderList.size(); i++) {

			if (variableInterest != null)
				contributedVariableInterest = this.round(variableInterest, 2)
						* depositHolderList.get(i).getContribution();

			DepositSummaryHolderWise depositSummaryHolderWise = new DepositSummaryHolderWise();
			depositSummaryHolderWise.setDepositId(deposit.getId());
			depositSummaryHolderWise.setDepositHolderId(depositHolderList.get(i).getId());
			depositSummaryHolderWise.setCustomerId(depositHolderList.get(i).getCustomerId());
				
					
			depositSummaryHolderWise.setTotalVariableIneterestAdjusted((depositSummaryHolderWise.getTotalVariableIneterestAdjusted() == null ? 0
					: depositSummaryHolderWise.getTotalVariableIneterestAdjusted()) + contributedVariableInterest);

			if(depositSummaryHolderWise.getTotalInterestAccumulated() != null && depositSummaryHolderWise.getTotalInterestAccumulated()>= contributedVariableInterest)
			{
				depositSummaryHolderWise.setTotalInterestAccumulated((depositSummaryHolderWise.getTotalInterestAccumulated() == null ? 0
					: depositSummaryHolderWise.getTotalInterestAccumulated()) - contributedVariableInterest);			
			}
			else
			{
				Double balInt = (depositSummary.getTotalInterestAccumulated() == null ? 0
						: depositSummary.getTotalInterestAccumulated())-contributedVariableInterest;
				
				depositSummaryHolderWise.setTotalInterestAccumulated(0d); 
				depositSummaryHolderWise.setTotalVariableInterestInHand((depositSummaryHolderWise.getTotalVariableInterestInHand() == null ? 0
						: depositSummaryHolderWise.getTotalVariableInterestInHand()) - balInt);							
			}


			depositSummaryDAO.updateDepositSummaryHolderWise(depositSummaryHolderWise);
		}

		// ---------------------------------------------------------

		return depositSummary;

	}
	
	
	
	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		try {
			BigDecimal bd = new BigDecimal(value);
			bd = bd.setScale(places, RoundingMode.HALF_UP);
			return bd.doubleValue();
		} catch (Exception e) {
			return value;
		}

	}

	public double roundToHalf(double number) {
		double diff = number - (int) number;
		if (diff < 0.03)
			return (int) number;

		return round(number, 2); // Math.round(number * 2) / 2.0;
	}
	
	public Long getPrimaryHolderId(List<DepositHolder> depositHolderList) {
		Long depositHolderId = 0l;
		for (int i = 0; i < depositHolderList.size(); i++) {
			if (depositHolderList.get(i).getDepositHolderStatus().equalsIgnoreCase("PRIMARY"))
				depositHolderId = depositHolderList.get(i).getId();
		}

		return depositHolderId;
	}
	
	
	@Transactional
	public void calculatePayOff(Deposit deposit) throws CustomException {
		Long depositId = deposit.getId();
		
		// Get last payoff details of each deposit holders
		List<DepositModification> depositModificationList = searchDepositHolderPayOff(depositId);

		String payOffType = deposit.getPayOffInterestType();
		if (depositModificationList != null && depositModificationList.size() > 0)
			payOffType = depositModificationList.get(depositModificationList.size() - 1).getPayOffType();

		if(payOffType==null || (payOffType!=null && payOffType.equals("")))
			return;
		
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);
//		String modifiedPayOffType = "";
//		boolean insufficientAmt = false;
//		List<Distribution> interestDistributionList = new ArrayList<>();
		
		// Get the deposit Summary information
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(depositId);

		Double totalPayoffAmt = 0d;
		HashMap<Long, Double> payOffDetails = new HashMap<>();
		for (int i = 0; i < depositModificationList.size(); i++) {

			DepositModification depModification = depositModificationList.get(i);
			
			// Get the "PART" or "Percent" payoff Interest Type
			String payOffInterestType = depModification.getPayOffInterestType();

			Long depositHolderId = depModification.getDepositHolderId();
			Double payOffAmount = 0d;
			
			// Get the holder Inhand Interest
			DepositSummaryHolderWise depositHolderWiseSummary = depositSummaryDAO.getHolderWiseDepositSummary(depositId, depositHolderId);
			Double totalFixedInterstInhand = depositHolderWiseSummary.getTotalFixedInterestInHand();
			Double totalVariableInterstInhand = depositHolderWiseSummary.getTotalVariableInterestInHand();

			if(totalVariableInterstInhand!=null && totalVariableInterstInhand>0)
			{
				// Now find the payOff interest amount
				if (payOffInterestType != null && payOffInterestType != "") {
					if (payOffInterestType.toUpperCase().equals("PART")) {
						
						// get the PayOff amount if it is PART Amount, not Percentage
						payOffAmount = Double.parseDouble(depModification.getPayOffInterestAmt().toString());
						// if amount is available the cut the amount
						if (totalVariableInterstInhand >= payOffAmount)
						{
							payOffDetails.put(depositHolderId, payOffAmount);
							
							//totalVariableInterstInhand = totalVariableInterstInhand - payOffAmount;
						}
						else {
							payOffAmount = 0d;
							UnSuccessfulPayOff UnSuccessfulPayOff = new UnSuccessfulPayOff();
							UnSuccessfulPayOff.setDepositid(depositId);
							UnSuccessfulPayOff.setDepositHolderId(depositHolderId);
							UnSuccessfulPayOff.setUnSuccessfulPayoffDetailsDate(DateService.getCurrentDate());
							UnSuccessfulPayOff.setPayOffAmount(payOffAmount);
	//						UnSuccessfulPayOff
	//								.setAvailableVariableInterestAmountForPayOff(availableVariableInterestAmountForPayOff);
							payOffDAO.insertUnSuccessfulPayOff(UnSuccessfulPayOff);
						}
					} else if (depModification.getPayOffInterestType().toUpperCase().equals("PERCENT")) {
						// Deduct the interest amt directly
						payOffAmount = totalVariableInterstInhand * depModification.getPayOffInterestPercent()/ 100;
						if(payOffAmount > 0)
							payOffDetails.put(depositHolderId, payOffAmount);
	
					}
				}
	
				if(payOffAmount > 0)
					totalPayoffAmt = totalPayoffAmt + payOffAmount;
			}
		}
			
		// 1. Insert in Pay Off Table
		// 2. Insert In PayoffDetail table
		// 3. Insert in in Payment Distribution and HolderWiseDistribution 
		// 4. Update in Deposit Summary Holder Wise and Deposit Summary Holder Wise
		// 5. Update the Deposit table 
		
		if(totalPayoffAmt ==0)
		{
			// Update PayOff Due Date and Current Balance in Deposit table
			//------------------------------------------------------------------------------
			Date dueDate = DateService.addMonths(DateService.getCurrentDateTime(), 1);
			Date payOffDueDate = getPayOffDueDate(payOffType, deposit.getNewMaturityDate(), dueDate);
			deposit.setPayOffDueDate(payOffDueDate);
			fixedDepositDAO.updateFD(deposit);
			//------------------------------------------------------------------------------

			return;
		}
		
		
		totalPayoffAmt = round(totalPayoffAmt,2);
		String transactionId = generateRandomString();
		// 1. Insert in Pay Off Table
		// ----------------------------------- 
		Payoff payoffMaster= new Payoff();
		payoffMaster.setDepositId(depositId);
		payoffMaster.setPayoffDate(DateService.getCurrentDateTime());
		payoffMaster.setPayoffType(payOffType); // Monthly/Quarterly/HalfYearly/Annually
		payoffMaster.setTotalPayoffAmt(totalPayoffAmt);
		payoffMaster.setTransactionId(transactionId);
		payoffMaster = payOffDAO.insertPayoff(payoffMaster);
		// -----------------------------------
				
		for (int i = 0; i < depositModificationList.size(); i++) {

			DepositModification depModification = depositModificationList.get(i);
			String payOffInterestType = depModification.getPayOffInterestType();

			Long depositHolderId = depModification.getDepositHolderId();
			Double payOffAmount = payOffDetails.get(depositHolderId);
			
		
			// 2. Insert In PayoffDetail table
			// -----------------------------------
			if(payOffAmount!= null && payOffAmount <= 0)
				continue;
	
			PayoffDetails payoffDetails= new PayoffDetails();
			payoffDetails.setPayoffId(payoffMaster.getId());
			payoffDetails.setDepositId(depositId);
			payoffDetails.setDepositHolderId(depositHolderId);
			payoffDetails.setPayoffInterestType(depModification.getPayOffInterestType());   // Part/Percent
			payoffDetails.setPayoffTransferType(depModification.getPayOffTransferType());   // NEFT/IMPS/RTGS
			payoffDetails.setPayoffAccountType(depModification.getPayOffAccountType());  //DepositAccount/SameBank/DiffBank
			payoffDetails.setPayoffNameOnBankAccount(depModification.getPayOffBankName());  
			payoffDetails.setPayoffAccountNumber(depModification.getPayOffAccountNumber()); 			
			payoffDetails.setPayoffIFSCCode(depModification.getPayOffBankIFSCCode());
			payoffDetails.setPayoffAmt(payOffAmount);
			
			if (depModification.getPayOffInterestPercent() != null)
				payoffDetails.setPayoffInterestPercent(
						Double.parseDouble(depModification.getPayOffInterestPercent().toString()));
			if (depModification.getPayOffInterestAmt() != null)
				payoffDetails
						.setPayoffInterestAmt(Double.parseDouble(depModification.getPayOffInterestAmt().toString()));
			
			payoffDetails = payOffDAO.insertPayoffDetails(payoffDetails);
			// -----------------------------------
			
		}
		
		 // 3 Insert in Payment Distribution and HolderWiseDistribution 
		 // -----------------------------------
		Distribution distribution = this.insertInPaymentDistribution(deposit, depositHolderList, null, payoffMaster.getId(),
				 Constants.PAYOFF, null, null, null, null, totalPayoffAmt, payOffDetails, null);
		// -----------------------------------------------------------------------------

		// 4. Update in DepositSummary
		// -----------------------------------------------------------------------------
		this.upsertInDepositSummary(deposit, Constants.PAYOFF, null, null, null, 
				totalPayoffAmt, payOffDetails, depositHolderList, null, null, null);
		// -----------------------------------------------------------------------------
		

		// Insert in Journal & Ledger

		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(Constants.FUNDTRANSFER);
		
		for (int i = 0; i < depositModificationList.size(); i++) 
		{
			
			Long depositHolderId = depositModificationList.get(i).getDepositHolderId();
			Double payOffAmount = payOffDetails.get(depositHolderId);
			Long customerId = 0l;
			for (int j = 0; j < depositHolderList.size(); j++) {
				if(depositHolderList.get(j).getId() == depositHolderId)
				{
					customerId = depositHolderList.get(j).getCustomerId();
					break;
				}
			}
			ledgerService.insertJournal(deposit.getId(),customerId , DateService.getCurrentDate(),
					deposit.getAccountNumber(), null, Event.INTEREST_PAYOFF.getValue(), payOffAmount, mop.getId(),
					depositSummary.getTotalPrincipal(), transactionId);
		}
		
		//-----------------------------------------------------------	
		
		
		
		// 5. Update PayOff Due Date and Current Balance in Deposit table
		//------------------------------------------------------------------------------
		Date dueDate = DateService.addMonths(DateService.getCurrentDateTime(), 1);
		Date payOffDueDate = getPayOffDueDate(payOffType, deposit.getNewMaturityDate(), dueDate);
		deposit.setPayOffDueDate(payOffDueDate);
		deposit.setCurrentBalance(distribution.getCompoundFixedAmt()+distribution.getCompoundVariableAmt());
		fixedDepositDAO.updateFD(deposit);
		//------------------------------------------------------------------------------

	
	}
	

	private List<DepositModification> searchDepositHolderPayOff(Long depositId) {
		List<DepositModification> depositModificationList = new ArrayList<DepositModification>();
		Deposit deposit = fixedDepositDAO.getDeposit(depositId);
		List<DepositHolder> depositHolders = depositHolderDAO.getDepositHolders(depositId);
		for (int i = 0; i < depositHolders.size(); i++) {
			Long depositHolderId = depositHolders.get(i).getId();

			DepositModification depositModification = depositModificationDAO
					.getDepositHolderModification(depositHolderId);
			if (depositModification == null) {
				DepositModification depositMod = new DepositModification();
				depositMod.setDepositId(depositHolders.get(i).getDepositId());
				depositMod.setDepositHolderId(depositHolders.get(i).getId());
				depositMod.setPaymentMode(deposit.getPaymentMode());
				depositMod.setPaymentType(deposit.getPaymentType());
				depositMod.setPayOffInterestType(depositHolders.get(i).getInterestType());
				depositMod.setPayOffInterestPercent(depositHolders.get(i).getInterestPercent());
				depositMod.setPayOffInterestAmt(depositHolders.get(i).getInterestAmt());

				depositMod.setPayOffAccountNumber(depositHolders.get(i).getAccountNumber());
				depositMod.setPayOffAccountType(depositHolders.get(i).getPayOffAccountType());
				depositMod.setPayOffBankIFSCCode(depositHolders.get(i).getIfscCode());
				depositMod.setPayOffBankName(depositHolders.get(i).getBankName());
				depositMod.setPayOffNameOnBankAccount(depositHolders.get(i).getNameOnBankAccount());
				depositMod.setPayOffTransferType(depositHolders.get(i).getTransferType());
				depositMod.setPayOffType(deposit.getPayOffInterestType());

				depositModificationList.add(depositMod);
			} else {
				depositModificationList.add(depositModification);
			}
		}

		return depositModificationList;
	}

	private void updatePayOffDueDate(Deposit deposit, String payOffType) {
		if (payOffType == "")
			return;

		Date dueDate = DateService.addMonths(DateService.getCurrentDateTime(), 1);
		Date payOffDueDate = getPayOffDueDate(payOffType, deposit.getNewMaturityDate(), dueDate);
		deposit.setPayOffDueDate(payOffDueDate);

		deposit.setPayOffDueDate(payOffDueDate);
		fixedDepositDAO.updateFD(deposit);
	}
	
	public Date getPayOffDueDate(String payOffInterestType, Date maturityDate, Date dueDate) {

		Date fromDate = dueDate;
		int month = DateService.getMonth(fromDate);
		if (payOffInterestType.equalsIgnoreCase("One-Time") || payOffInterestType.equalsIgnoreCase("One Time")) {

			dueDate = DateService.getLastDateOfMonth(dueDate);
		} else if (payOffInterestType.equalsIgnoreCase("Monthly")) {

			if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate), maturityDate) < 0) {
				// dueDate = maturityDate;
				return dueDate;
			}
			dueDate = DateService.getLastDateOfMonth(fromDate);

		} else if (payOffInterestType.equalsIgnoreCase("Quarterly")) {

			while (true) {
				if (month == 2 || month == 5 || month == 8 || month == 11) {
					break;
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
					month = DateService.getMonth(fromDate);
				}
			}
			if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate), maturityDate) < 0) {
				// dueDate = maturityDate;
				return dueDate;
			}
			dueDate = DateService.getLastDateOfMonth(fromDate);

		} else if (payOffInterestType.equalsIgnoreCase("Half Yearly")
				|| payOffInterestType.equalsIgnoreCase("Semiannual")) {

			while (true) {
				if (month == 2 || month == 8) {
					break;
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
					month = DateService.getMonth(fromDate);
				}
			}
			if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate), maturityDate) < 0) {
				// dueDate = maturityDate;
				return dueDate;
			}
			dueDate = DateService.getLastDateOfMonth(fromDate);

		} else if (payOffInterestType.equalsIgnoreCase("Annually") || payOffInterestType.equalsIgnoreCase("Annual")) {

			while (true) {
				if (month == 2) {
					break;
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
					month = DateService.getMonth(fromDate);
				}
			}
			if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate), maturityDate) < 0) {
				// dueDate = maturityDate;
				return dueDate;
			}
			dueDate = DateService.getLastDateOfMonth(fromDate);

		} else if (payOffInterestType.equalsIgnoreCase("End of Tenure")) {

			dueDate = maturityDate;
		}
		return dueDate;

	}


	public void transferMoneyOnMaturity() throws CustomException {
		System.out.println("Going to start Maturity Process on " + DateService.getCurrentDate() + "...");
		Date currentDate = DateService.getCurrentDate();

		// Get all open deposits whose maturity date is today.
		List<Deposit> deposits = fixedDepositDAO.getDepositsForMaturity();
		for (int i = 0; i < deposits.size(); i++) {

			Deposit deposit = deposits.get(i);		
			Date fromDate = deposit.getLastRenewDate() == null? deposit.getCreatedDate() : DateService.addDays(deposit.getLastRenewDate(), 1);

			Date maturityDate = (deposit.getNewMaturityDate() == null) ? deposit.getMaturityDate() : deposit.getNewMaturityDate();
			Float rateOfInterest = 0.0f;
			List<DepositHolder> depositHolderList = null;
			if (DateService.getDaysBetweenTwoDates(currentDate, maturityDate) != 0) 
				continue;
									
			// Common functions for RepayPrincipalAndInterest/AutoRenew/AutoRenewInterestAndRepayPricipal/AutoRenewPrincipalAndRepayInterest
			// Step1: CalculateInterest
			// Step2: Deduct Penalty
			// Step3: CompoundInterest
			// Step4: Calculate the GST				
			
			
			// Close the deposit and pay all the money if 
			// maturity Instruction is RepayPrincipalAndInterest
			if (deposit.getMaturityInstruction() !=null && deposit.getMaturityInstruction().equalsIgnoreCase("repay"))
			{
				// Close the deposit and pay all the money				
				// Here we did not call the commonProcessForDepositMaturity as 
				// it has been already called from CloseDeposit method
				this.closeDeposit(deposit, false);
				
				// raise the close Event to deduct the TDS
				//context.publishEvent(new CloseDepositEvent(this, "CloseDeposit", deposit));
		
			}
			else
			{
				Distribution distribution = this.commonProcessForDepositMaturity(deposit, false);
			}
				
			if (deposit.getMaturityInstruction() !=null && (deposit.getMaturityInstruction().equalsIgnoreCase("autoRenew") ||
					deposit.getMaturityInstruction().equalsIgnoreCase("autoRenewInterestAndRepayPrincipal") ||
					deposit.getMaturityInstruction().equalsIgnoreCase("autoRenewPrincipalAndRepayInterest")))
			{
				
				
				// 1. Find the next Maturity Date
				// 2. Insert in Renew Deposit
				// 3. If Maturity Instruction is AutoRenew, then compound the interest, so that with the new principal amount deposit starts again
				// 4. If Maturity Instruction is AutoRenewInterestAndRepayPricipal or autoRenewPrincipalAndRepayInterest, then do not compound the interest, 
								// whatever the principal and interest amount, do it according to instruction
				
				
				// Find the next maturity date
				//----------------------------------------------		
				maturityDate = DateService.generateDaysDate(DateService.getCurrentDate(), deposit.getTotalTenureInDays());
				//----------------------------------------------
				
				// Get the new interest rate for the renewed deposit
				//----------------------------------------------
				int days = DateService.getDaysBetweenTwoDates(currentDate, maturityDate);
				days = days + 1;

				// Get the InterestRate of the primary customer						
				depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());

				// Get the customer details to find out the age and category
				// Get the customer details to find out the customer category depending on his/her age
				Customer customer = customerDAO.getById(depositHolderList.get(0).getCustomerId());
				String category = this.geCustomerActualCategory(customer);
				
				rateOfInterest = this.getDepositInterestRate(days, category, deposit.getDepositCurrency(),
						deposit.getDepositAmount(), deposit.getDepositClassification(), deposit.getPrimaryCitizen(), deposit.getNriAccountType());
				//----------------------------------------------
				

				// Renew the Deposit
				//----------------------------------------------
				RenewDeposit renewDeposit = new RenewDeposit();
				renewDeposit.setDepositId(deposit.getId());
				renewDeposit.setRenewDate(currentDate);
				fixedDepositDAO.saveRenewDeposit(renewDeposit);
				//----------------------------------------------
				
				// Save the deposit to RenewedDeposit table
				//----------------------------------------------
				// copy the saved deposit to DepositBeforeRenew table
				DepositBeforeRenew existingDeposit = this.copyDeposit(deposit);
				fixedDepositDAO.saveDepositBeforeRenew(existingDeposit);
				//----------------------------------------------
				

				// Change in required  field of deposit
				deposit.setNewMaturityDate(maturityDate);
				deposit.setModifiedInterestRate(rateOfInterest);
				//deposit.setMaturityAmount(this.getMaturityAmount(deposit));
				deposit.setIsRenewed(1);
				deposit.setLastRenewDate(currentDate);
				deposit.setDueDate(this.getDueDate(deposit));
				//deposit.setMaturityAmount(this.getExpectedMaturityAmount(deposit));		
				
				deposit.setPrimaryCustomerCategory(category);
				deposit = fixedDepositDAO.updateFD(deposit);
				
				depositHolderList.get(0).setDepositHolderCategory(category);
				depositHolderDAO.updateDepositHolder(depositHolderList.get(0));

			}
			
			if (deposit.getMaturityInstruction() !=null && deposit.getMaturityInstruction().equalsIgnoreCase("autoRenew")) {
				
				this.InsertInDistributionForRenew(deposit, depositHolderList, this.round(deposit.getCurrentBalance(),2), 0d);
				
				

			} 
			else if (deposit.getMaturityInstruction() !=null && deposit.getMaturityInstruction().equalsIgnoreCase("autoRenewPrincipalAndRepayInterest")) {
										
				
				// Get the Principal
				Double totalPrincipalAmount = paymentDAO.getTotalPayment(deposit.getId(), fromDate, DateService.getCurrentDate());

				//  Get the interest left with
				Double totalInterest = deposit.getCurrentBalance() - totalPrincipalAmount;
				
				// Save in Bank Payment
				// So that bank can pay this to customer
				this.insertInBankPayment(deposit, depositHolderList, totalInterest, "autoRenewPrincipalAndRepayInterest");
					
				// Update saving bank account with the interest amount
				this.updateSavingAccount(deposit, depositHolderList, totalInterest, "autoRenewPrincipalAndRepayInterest");
				
				// Insert In Distribution and Deposit Summary table
				this.InsertInDistributionForRenew(deposit, depositHolderList, this.round(totalPrincipalAmount,2),  this.round(totalInterest,2));

			} 
			else if (deposit.getMaturityInstruction() !=null && deposit.getMaturityInstruction().equalsIgnoreCase("autoRenewInterestAndRepayPrincipal")) {
				
				// Get the Principal
				Double totalPrincipalAmount = paymentDAO.getTotalPayment(deposit.getId(), fromDate, DateService.getCurrentDate());

				//  Get the interest left with
				Double totalInterest = deposit.getCurrentBalance() - totalPrincipalAmount;
				
				// Save in Bank Payment
				// So that bank can pay this to customer
				this.insertInBankPayment(deposit, depositHolderList, totalPrincipalAmount, "autoRenewInterestAndRepayPrincipal");

				// Update saving bank account with the interest amount
				this.updateSavingAccount(deposit, depositHolderList, totalPrincipalAmount, "autoRenewPrincipalAndRepayInterest");
				
				// Insert In Distribution and Deposit Summary table
				this.InsertInDistributionForRenew(deposit, depositHolderList,  this.round(totalInterest,2),  this.round(totalPrincipalAmount,2));

			}
			

		}

		System.out.println("Money transfer on matutity is done");
	}
	
	private void updateSavingAccount(Deposit deposit, List<DepositHolder> depositHolderList, Double amount, String action)
	{
		for (int i = 0; i < depositHolderList.size(); i++) {

			DepositHolder depositHolder = depositHolderList.get(i);
			float contribution = depositHolder.getContribution();
			Double distributedAmt = amount * contribution / 100;

			depositHolder.setDistAmtOnMaturity((depositHolder.getDistAmtOnMaturity()== null? 0d:
				depositHolder.getDistAmtOnMaturity()) + distributedAmt);
			depositHolderDAO.updateDepositHolder(depositHolder);
	
			// BankPayment bankPayment = this.saveBankPayment();
			List<AccountDetails> accountList = accountDetailsDAO
					.findCurrentSavingByCustId(depositHolder.getCustomerId());
			if (accountList.size() > 0) {
				if (depositHolder.getDeathCertificateSubmitted() != null
						&& depositHolder.getDeathCertificateSubmitted().equalsIgnoreCase("YES"))
					continue;
				// Credited the saving account
				accountList.get(0).setAccountBalance(accountList.get(0).getAccountBalance() + distributedAmt);
				depositHolder.setIsAmountTransferredOnMaturity(1);
				accountDetailsDAO.updateUserAccountDetails(accountList.get(0));
				depositHolderDAO.updateDepositHolder(depositHolder);
				
				// Insert in Journal & Ledger
				//-----------------------------------------------------------
				String accountType = "";
				if(accountList.get(0).getAccountType().equalsIgnoreCase("Savings"))
					accountType = "Savings Account";
				else if(accountList.get(0).getAccountType().equalsIgnoreCase("Current"))
					accountType = "Current Account";
				if(accountList.get(0).getAccountType().equalsIgnoreCase("Deposit"))
					accountType = "Deposit Account";
				
				//ToDo:
//				ledgerService.insertJournalLedger(deposit.getId(),this.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(), 
//						accountList.get(0).getAccountNo(), accountType,deposit.getAccountNumber(),  "Deposit Account", 
//						 Constants.FUNDTRANSFER, distributedAmt, "Internal Tranasfer", 
//						0d);		
				//-----------------------------------------------------------
			}

		}

	}

	
	public Distribution commonProcessForDepositMaturity(Deposit deposit, boolean isPrematurelyClosed) throws CustomException {
		
		Date currentDate = DateService.getCurrentDate();
		Double totalAmount = 0d;

		// delete all projected interest and tds of future dates
		/*
		 * interestDAO.deleteByDepositIdAndDate(depositId);
		 * tdsDAO.deleteByDepositId(id);
		 */
		
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());

		// fetch the maturity date
		Date maturityDate = (deposit.getNewMaturityDate() != null) ? deposit.getNewMaturityDate() : deposit.getMaturityDate();

		Distribution distribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());
		totalAmount = distribution.getTotalBalance();

		// calculate interest till closing date
		// -------------------------------------------
		Distribution interestDistribution = null;
		
		// Important: if the maturity day comes on Holiday, then
		// give the extra interest for the holiday. It may happen,
		// two or tree consecutive days are configured as holidays.
		// So we have to calculate interest for those holidays.
		// But if the customer does not have saving bank account, 
		// then only extra interest comes into picture. if saving bank account
		// is there in the bank, fund can be transferred in holiday also.
		
		//Date currentMaturityDate = DateService.getCurrentDate(); //(deposit.getNewMaturityDate()!=null)? deposit.getNewMaturityDate() : deposit.getMaturityDate();
		Boolean isMaturityFallsInHoliday = false;
		Integer noOfHolidays = 0;
		ProductConfiguration productConfiguration  =  productConfigurationDAO.findById(deposit.getProductConfigurationId());
		if (deposit.getMaturityInstruction() !=null && deposit.getMaturityInstruction().equalsIgnoreCase("repay"))
		{
			// For Auto Deposit, ProductConfiguation will be null
			if(productConfiguration != null)
			{
				if(productConfiguration.getPaymentModeOnMaturity().equalsIgnoreCase("Fund Transfer"))
				{
					// check the date is holiday or not...
					// if holiday, then check for next working day..
					// all holiday interest bank has to give to him...
					// Get the next 10 days including maturity dates from maturity and find any working date is there in between
					HolidayConfiguration holidayConfiguration = null;
					Customer customer = customerDAO.getById(this.getPrimaryCustomerId(depositHolderList));
					// get the Holiday configuration. If deposit is done from from employee
					// get the configuration by branchcode.
					// if it is done from customer, that search for state level holiday
					if(deposit.getBranchCode() != null)
					{
						holidayConfiguration = holidayConfigurationDAO.getHolidayConfiguration(DateService.getCurrentDate(), deposit.getBranchCode());
					}
					else
					{					
						holidayConfiguration = holidayConfigurationDAO.getHolidayConfigurationByState(maturityDate, customer.getState());
					}
					
					if(holidayConfiguration == null)
					{
						isMaturityFallsInHoliday = false;
					}
					else
					{
						isMaturityFallsInHoliday = true;
						noOfHolidays = 1;
						Date nextDate = DateService.addDays(DateService.getCurrentDate(), 1);
						List<Object> holidayList = null;
						if(deposit.getBranchCode() != null)
							holidayList = holidayConfigurationDAO.getHolidayConfigurationListForNext10Days(nextDate, deposit.getBranchCode());	
						else
							holidayList = holidayConfigurationDAO.getStateWiseHolidayConfigurationListForNext10Days(nextDate, customer.getState());
						if(holidayList != null)
						{
							for(int i=0; i< holidayList.size(); i++)
							{
								Date dt = (Date) holidayList.get(i);
								
								if(DateService.getDaysBetweenTwoDates(nextDate, dt) == 0)
								{
									// if next day is also holiday then increase no of holidays
									noOfHolidays = noOfHolidays + 1;
									nextDate = DateService.addDays(nextDate, 1);
								}
								else
								{
									break;
								}
							}
						}
						
						
					}
					
	//				// if maturity falls in holidays then change 
	//				// the maturity days temporary for interest calculation
	//				if (isMaturityFallsInHoliday)
	//				{
	//					deposit.setNewMaturityDate(DateService.addDays(DateService.getCurrentDate(), noOfHolidays));
	//					deposit.setNewMaturityDateForHolidays(DateService.addDays(DateService.getCurrentDate(), noOfHolidays));
	//				}
	//				
				}
			}
		}
		
		Date increasedDayForMaturityOnHoliday= DateService.addDays(DateService.getCurrentDate(), noOfHolidays);
		interestDistribution = this.calculateInterest(deposit, depositHolderList, "", increasedDayForMaturityOnHoliday);
		
		// After interest calculation set back to the actual
		// maturity days
		if (isMaturityFallsInHoliday)
		{
			deposit.setNewMaturityDateForHolidays(increasedDayForMaturityOnHoliday);		
		}
//		
		// ---------------------------------------------
		if (interestDistribution != null) {
			totalAmount = distribution.getTotalBalance();
			distribution = interestDistribution;
		}
		// -------------------------------------------

		// deduct TDS
		// -------------------------------------------
		String financialYear = DateService.getFinancialYear(currentDate);
		// -------------------------------------------

		// Compound the Interest
		// -------------------------------------------
		distribution = this.compoundInterestDepositWise(deposit);
		totalAmount = distribution.getTotalBalance();
		// -------------------------------------------
		
		// Deduct penaltyDue from Total Amount
		// -----------------------------------------
		Double totalpenaltyAmount = 0d;
		Double prevPenaltyDue = 0d;
		// Fetch last Penalty of the deposit
		ModificationPenalty modPenalty = depositModificationDAO.getLastModificationPenalty(deposit.getId());
		if (modPenalty != null)
			prevPenaltyDue = modPenalty.getPenaltyDue();
		// -----------------------------------------
		
		// It will skip for AutoDeposit
		if(productConfiguration!=null)
		{
			if(isPrematurelyClosed)
			{
				// Deduct the penalty from the withdraw amount
				// -----------------------------------------
				// Check if SlabBasedPenaltyRequiredForWithdraw is configured or not 
				// if configured, then deduct the penalty amount
				if((productConfiguration.getIsSlabBasedPenaltyRequiredForWithdraw() != null && productConfiguration.getIsSlabBasedPenaltyRequiredForWithdraw() == 1))
				{
					distribution = this.deductWithdrawPenalty(deposit, totalAmount, distribution, isPrematurelyClosed, prevPenaltyDue);	
					totalpenaltyAmount = (distribution.getFixedAmt()==null?0d:distribution.getFixedAmt()) +  (distribution.getVariableAmt()==null?0d:distribution.getVariableAmt());
				}
			}
		}

		if(totalpenaltyAmount>0)
		{
			
			// GST calCuLation 
			// -----------------------------------------
			Double sgst = 0d;
			Double cgst = 0d;
			Double igst = 0d;
	
			GSTDeduction gstDeduction = gstDeductionDAO.findAll();
			if(gstDeduction!=null)
			if (gstDeduction.getSgst() != null) {
				sgst = gstDeduction.getSgst() * totalpenaltyAmount / 100;
				// sgst = Math.floor(sgst * 100) / 100;
			}
	
			if (gstDeduction.getCgst() != null) {
				cgst = gstDeduction.getCgst() * totalpenaltyAmount / 100;
			}
	
			if (gstDeduction.getIgst() != null) {
				igst = gstDeduction.getIgst() * totalpenaltyAmount / 100;
			}
	
			GST gst = new GST();
			gst.setAction(Constants.PENALTY1);
			gst.setCgst(cgst);
			gst.setCreatedDate(currentDate);
			gst.setDepositId(deposit.getId());
			gst.setIgst(igst);
			gst.setPenaltyAmount(totalpenaltyAmount);
			gst.setSgst(sgst);
	
			gstDAO.insertGST(gst);
		}
		// -----------------------------------------
		
		return distribution;
	}
	
	@Transactional
	public void closeDeposit(Deposit deposit, boolean isPrematurlyClosed) throws CustomException {
		
		// Calculate Interest, Compound the Interest and 
		// deduct penalty if required
		//-----------------------------------
		commonProcessForDepositMaturity(deposit, isPrematurlyClosed);
		//-----------------------------------
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(deposit.getPaymentMode());

		// Get the latest Balance
		//-----------------------------------
		Double depsitBal = 0d;
		// Get the last distribution for the latest balance
		Distribution distribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());
		if (distribution != null && distribution.getTotalBalance() != null)
			depsitBal = round(distribution.getTotalBalance(), 2);
		//-----------------------------------
		
		// Here we are not subtracting the payoff as it is already deducted
		// from distribution.totalBalance
		// deposit Bal + Sum of All Interests
		// depsitBal = depsitBal + paymentDistributionDAO.getTotalInterest(id);

		// Double maturityAmt = round((depsitBal - totalpenaltyAmount +
		// lastInterestAmt),2);
		
		// Close the Deposit
		//-----------------------------------
		Double maturityAmt = null;
		
		// Check the round off option
		RoundOff roundOff = roundOffDAO.getRoundingOff();
	
		if(roundOff !=null)
		{
			if(roundOff.getNearestHighestLowest().equalsIgnoreCase("Nearest Highest"))
				maturityAmt = new BigDecimal(depsitBal).setScale(roundOff.getDecimalPlaces(), RoundingMode.UP).doubleValue();
			else
				maturityAmt = new BigDecimal(depsitBal).setScale(roundOff.getDecimalPlaces(), RoundingMode.DOWN).doubleValue();
		}
		else
		{
			// Default 2 decimal places
			maturityAmt = new BigDecimal(depsitBal).setScale(2, RoundingMode.UP).doubleValue();
		}
		


		//Close the deposit
		deposit.setMaturityAmountOnClosing(maturityAmt);
		deposit.setCurrentBalance(maturityAmt);
		//deposit.setPrematurePanaltyAmount(premeturePanality);
		deposit.setClosingDate(DateService.getCurrentDate());
		deposit.setStatus(Constants.CLOSESTATUS);
		deposit.setIsPreMaturelyClosed(isPrematurlyClosed?1:0);
		fixedDepositDAO.updateFD(deposit);
		//-----------------------------------
		
		// Insert in Bank Payment table
		//-----------------------------------
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
		this.insertInBankPayment(deposit, depositHolderList, maturityAmt, "CloseDeposit");
		//-----------------------------------
		// BankPayment bankPayment = this.saveBankPayment(Deposit depositId,
		// List<DepositHolder>depositHolderUser);

		// update holder wise maturity amount in DepositHolder table
		//-----------------------------------			
		for (int i = 0; i < depositHolderList.size(); i++) {

			DepositHolder depositHolder = depositHolderList.get(i);
			float contribution = depositHolder.getContribution();
			Double distributedAmt = maturityAmt * contribution / 100;

			// if (depositHolder.getDepositHolderStatus().equals("PRIMARY"))
			// primaryCustId = depositHolder.getCustomerId();
            String fromAccountNo = "";
			
			String transactionId = fdService.generateRandomString();
			depositHolder.setDistAmtOnMaturity(distributedAmt);
			depositHolderDAO.updateDepositHolder(depositHolder);
			ledgerService.insertJournal(deposit.getId(), depositHolder.getCustomerId(), DateService.getCurrentDate(), fromAccountNo,
					deposit.getAccountNumber(), Event.OPEN_DEPOSIT.getValue(),
					depsitBal, mop.getId(),
					null, transactionId);

		}
		//-----------------------------------			

	}
	
	public Double closeSweepDepositForcefullyByBank(Deposit deposit) throws CustomException {
		
		Date currentDate = DateService.getCurrentDate();
		Double totalAmount = 0d;
	
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());

		// fetch the maturity date
		Date maturityDate = (deposit.getNewMaturityDate() != null) ? deposit.getNewMaturityDate() : deposit.getMaturityDate();

		Distribution distribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());
		totalAmount = distribution.getTotalBalance();

		// calculate interest till closing date
		// -------------------------------------------
		Distribution interestDistribution = null;
		
		interestDistribution = this.calculateInterest(deposit, depositHolderList, "Auto", null);
		if (interestDistribution != null) {
			totalAmount = distribution.getTotalBalance();
			distribution = interestDistribution;
		}
		// -------------------------------------------

		// deduct TDS
		// -------------------------------------------
		String financialYear = DateService.getFinancialYear(currentDate);
		// distribution = this.calculateTDS(deposit, financialYear);

		// if (distribution != null) {
		// totalAmount = distribution.getTotalBalance();
		// }
		// -------------------------------------------

		// Compound the Interest
		// -------------------------------------------
		distribution = this.compoundInterestDepositWise(deposit);
		totalAmount = round(distribution.getTotalBalance(), 2);
		// -------------------------------------------
			
			
		// Close the Deposit
		//-----------------------------------
		totalAmount = round(totalAmount, 2);

		//Close the deposit
		deposit.setMaturityAmountOnClosing(totalAmount);
		deposit.setCurrentBalance(totalAmount);
		//deposit.setPrematurePanaltyAmount(premeturePanality);
		deposit.setClosingDate(DateService.getCurrentDate());
		deposit.setStatus(Constants.CLOSESTATUS);
		fixedDepositDAO.updateFD(deposit);
		//-----------------------------------
		
		// Insert in Bank Payment table
		//-----------------------------------
		this.insertInBankPayment(deposit, depositHolderList, totalAmount, "CloseDeposit");
		//-----------------------------------
		// BankPayment bankPayment = this.saveBankPayment(Deposit depositId,
		// List<DepositHolder>depositHolderUser);

		// update holder wise maturity amount in DepositHolder table
		//-----------------------------------			
		for (int i = 0; i < depositHolderList.size(); i++) {

			DepositHolder depositHolder = depositHolderList.get(i);
			float contribution = depositHolder.getContribution();
			Double distributedAmt = totalAmount * contribution / 100;

			// if (depositHolder.getDepositHolderStatus().equals("PRIMARY"))
			// primaryCustId = depositHolder.getCustomerId();

			depositHolder.setDistAmtOnMaturity(distributedAmt);
			depositHolderDAO.updateDepositHolder(depositHolder);

			// Insert in Bank Payment table
			// BankPayment bankPayment = this.saveBankPayment();

			List<AccountDetails> accountList = accountDetailsDAO
					.findCurrentSavingByCustId(depositHolder.getCustomerId());
			if (accountList.size() > 0) {
				if (depositHolder.getDeathCertificateSubmitted() != null
						&& depositHolder.getDeathCertificateSubmitted().equalsIgnoreCase("YES"))
					break;
				// Credited the saving account
				accountList.get(0).setAccountBalance(accountList.get(0).getAccountBalance() + distributedAmt);
				depositHolder.setIsAmountTransferredOnMaturity(1);
				accountDetailsDAO.updateUserAccountDetails(accountList.get(0));
				depositHolderDAO.updateDepositHolder(depositHolder);
			}

		}
		return totalAmount;
		//-----------------------------------			

	}
	private void InsertInDistributionForRenew(Deposit deposit, List<DepositHolder> depositHolderList, Double renewAmount, Double withdrawAmt)
	{
		AccountDetails accountDetails = accountDetailsDAO.findByAccountNo(deposit.getLinkedAccountNumber());
		String toAccountNo = accountDetails != null? accountDetails.getAccountNo() : "" ;
		String toAccountDetails = accountDetails != null? accountDetails.getAccountType() : "";
			

		// Withdraw either Principal or Interest amount
		//---------------------------------------------------------------------
		if(withdrawAmt != null && withdrawAmt>0)
		{
			//Insert in Payment Distribution and holderwise Payment Distribution
			this.insertInPaymentDistribution(deposit, depositHolderList, withdrawAmt, null, Constants.WithdrawnInRenewal, null, null, null, null, null, null, null);
			
			//Insert in DepositSummary and HolderwiseDepositSummary
			DepositSummary depositSummary = this.upsertInDepositSummary(deposit, Constants.WithdrawnInRenewal, withdrawAmt, null, null, 
					null, null, depositHolderList, null, null, null);
						
			
			// Insert in Journal & Ledger
			//-----------------------------------------------------------
			//ToDo:
//			ledgerService.insertJournalLedger(deposit.getId(),this.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(), 
//					deposit.getAccountNumber(), "Deposit Account", toAccountNo, toAccountDetails, 
//					 Constants.WithdrawnInRenewal, withdrawAmt, "Internal Tranasfer", 
//					depositSummary.getTotalPrincipal());		
			//-----------------------------------------------------------
		}
		//---------------------------------------------------------------------
		
		// Renew the amount
		//---------------------------------------------------------------------
		if(renewAmount != null && renewAmount>0)
		{
			//Insert in Payment Distribution  holderwise Payment Distribution
			this.insertInPaymentDistribution(deposit, depositHolderList, renewAmount, null, Constants.Renew, null, null, null, null, null, null, null);
			
			//Insert in DepositSummary and HolderwiseDepositSummary
			DepositSummary depositSummary = this.upsertInDepositSummary(deposit, Constants.Renew, renewAmount, null, null,
							null, null, depositHolderList, null, null, null);
			
			// Insert in Journal & Ledger
			//-----------------------------------------------------------
			//ToDo:
//			ledgerService.insertJournalLedger(deposit.getId(),this.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(), 
//					deposit.getAccountNumber(), "Deposit Account", toAccountNo, toAccountDetails, 
//					 Constants.WithdrawnInRenewal, withdrawAmt, "Internal Tranasfer", 
//					depositSummary.getTotalPrincipal());		
			//-----------------------------------------------------------
		}
		//---------------------------------------------------------------------
	}

	
	public Double getExpectedMaturityAmount(Deposit deposit, Float interestRate) throws CustomException   {

		ProductConfiguration productConfiguration = productConfigurationDAO.findById(deposit.getProductConfigurationId());
				
		List<Date> interestDates = this.getFutureInterestDates(DateService.getCurrentDate(), deposit.getNewMaturityDate(), productConfiguration.getInterestCalculationBasis());
		List<Date> compoundingDates = this.getFutureCompoundingDates(DateService.getCurrentDate(), deposit.getNewMaturityDate(), productConfiguration.getInterestCompoundingBasis());
		List<Date> depositDates = this.getFutureDepositDates(deposit.getDeductionDay(), deposit.getPaymentType(), deposit.getNewMaturityDate());

		//DepositSummary depositSummary = new DepositSummary();

		Double depositAmt = (double) deposit.getDepositAmount();
		Double compoundPrincipalAmt = 0.0;
		Double totalUncomoundedInterestAmt = 0.0;
		int k = 0;
		int n = 0;
		int daysDiff = 0;
		//Date lastInterestDate = null;
	//	Boolean isLastTransactionInterest = false;
		Date startDate = null;
		Date nextDepositDate = null;

		for (int i = 0; i < depositDates.size(); i++) {
//			if(i > 0 && !isLastTransactionInterest)
//			{
//				// Calculate the temporary interest for last deposit date to current deposit date
//				// before next payment
//				daysDiff = DateService.getDaysBetweenTwoDates(startDate, nextDepositDate);
//				Double interestAmount = (compoundPrincipalAmt * interestRate/100/365) * daysDiff;
//				totalUncomoundedInterestAmt = totalUncomoundedInterestAmt + interestAmount;
//			}
			
			Date depositDate = depositDates.get(i);
			startDate = depositDate;
			nextDepositDate = depositDate;
			
			if (i < depositDates.size() - 1)
				nextDepositDate = depositDates.get(i + 1);

			compoundPrincipalAmt = compoundPrincipalAmt + depositAmt;
			
			// isLastTransactionInterest = false;

			for (int j = k; j < interestDates.size(); j++) {
				// if next deposit date < interest date then
				// add the deposit amount to compundAmt

				Date interestDate = interestDates.get(j);
				
				Date nextInterestDate = interestDate;
				if (j < interestDates.size() - 1)
					nextInterestDate = interestDates.get(j + 1);
				
				if(DateService.getDaysBetweenTwoDates(interestDate, nextDepositDate) >= 0)
				{
					daysDiff = DateService.getDaysBetweenTwoDates(startDate, interestDate) + 1;
					Double interestAmount = (compoundPrincipalAmt * interestRate/100/365) * daysDiff;
					totalUncomoundedInterestAmt = totalUncomoundedInterestAmt + interestAmount;
					
					startDate = DateService.addDays(interestDate, 1);
					// isLastTransactionInterest = true;
					k++; 
				}
				else if (DateService.getDaysBetweenTwoDates(depositDate, nextDepositDate) == 0)
				{
					// For one time payment where depositDate and nextDepositDate will be same
					daysDiff = DateService.getDaysBetweenTwoDates(startDate, interestDate) + 1;
					Double interestAmount = (compoundPrincipalAmt * interestRate/100/365) * daysDiff;
					totalUncomoundedInterestAmt = totalUncomoundedInterestAmt + interestAmount;
					
					startDate = DateService.addDays(interestDate, 1);
					// isLastTransactionInterest = true;
					k++; 
					
				}
				else if (DateService.getDaysBetweenTwoDates(depositDate, nextDepositDate) > 0)
				{
//					// For one time payment where depositDate and nextDepositDate will be same
//					daysDiff = DateService.getDaysBetweenTwoDates(startDate, nextDepositDate);
//					Double interestAmount = (compoundPrincipalAmt * interestRate/100/365) * daysDiff;
//					totalUncomoundedInterestAmt = totalUncomoundedInterestAmt + interestAmount;
//					
//					startDate = DateService.addDays(interestDate, 1);
					
					// it is temporary interest calculation, not in 
					// end of month, so isLastTransactionInterest will be false only
					daysDiff = DateService.getDaysBetweenTwoDates(startDate, nextDepositDate);
					Double interestAmount = (compoundPrincipalAmt * interestRate/100/365) * daysDiff;
					totalUncomoundedInterestAmt = totalUncomoundedInterestAmt + interestAmount;
					//isLastTransactionInterest = false;
					break;
					
				}
				else
				{
					//isLastTransactionInterest = false;
					break;
					
				}
				
				for (int m = n; m < compoundingDates.size(); m++)
				{
					Date compoundingDate = compoundingDates.get(m);

						// Check the compoundingDate is equal to the interestDate
						if(DateService.getDaysBetweenTwoDates(interestDate, compoundingDate) == 0)
						{
							// If interest date and compound date is same 
							compoundPrincipalAmt = compoundPrincipalAmt + totalUncomoundedInterestAmt;
							totalUncomoundedInterestAmt = 0d;
							n++;
						}
						else
						{
							break;
						}
				}
			}

		}
		return compoundPrincipalAmt;

	}
	
	private Date calculateMaturityDate(String tenureType, Integer tenure, Integer days)
	{
		Date maturityDate = DateService.getCurrentDate();
		if (tenureType.equalsIgnoreCase(Constants.MONTH)) {
			maturityDate = DateService.generateMonthsDate(DateService.getCurrentDate(), tenure);
		} else if (tenureType.equalsIgnoreCase("Year")) {
			maturityDate = DateService.generateYearsDate(DateService.getCurrentDate(), tenure);
			if (days != null)
				maturityDate = DateService.generateDaysDate(maturityDate, days + 1);
		} else {
			maturityDate = DateService.generateDaysDate(DateService.getCurrentDate(), tenure);
		}
		return maturityDate;
	}
	
	public Float getDepositInterestRate(Integer tenure, String category, String currency, Double depositAmount, String depositClassification , String citizen, String nriAccountType) {
		DepositRates depositeRate = depositRatesDAO.getInterestRate(tenure, category, currency, depositAmount, depositClassification, citizen, nriAccountType);
		if (depositeRate != null) {
			return depositeRate.getInterestRate();
		} else {
			return 0f;
		}

	}
	private DepositBeforeRenew copyDeposit(Deposit deposit) {
		DepositBeforeRenew previousDeposit = new DepositBeforeRenew();
		previousDeposit.setAccountNumber(deposit.getAccountNumber());
		previousDeposit.setApprovalStatus(deposit.getApprovalStatus());
		previousDeposit.setClearanceStatus(deposit.getClearanceStatus());
		previousDeposit.setClosingDate(deposit.getClosingDate());
		previousDeposit.setComment(deposit.getComment());
		previousDeposit.setCreatedBy(deposit.getCreatedBy());
		previousDeposit.setCreatedDate(deposit.getCreatedDate());
		previousDeposit.setCurrentBalance(deposit.getCurrentBalance());
		previousDeposit.setDepositAmount(deposit.getDepositAmount());
		previousDeposit.setDepositCategory(deposit.getDepositCategory());
		previousDeposit.setDepositCurrency(deposit.getDepositCurrency());
		previousDeposit.setDepositId(deposit.getId());
		previousDeposit.setDepositType(deposit.getDepositType());
		previousDeposit.setDueDate(deposit.getDueDate());
		previousDeposit.setEmiAmount(deposit.getEmiAmount());
		previousDeposit.setFlexiRate(deposit.getFlexiRate());
		previousDeposit.setForm(deposit.getForm());
		previousDeposit.setFormFile(deposit.getFormFile());
		previousDeposit.setFormStatus(deposit.getFormStatus());
		previousDeposit.setGestationPeriod(deposit.getGestationPeriod());
		previousDeposit.setInterestRate(deposit.getInterestRate());
		previousDeposit.setIsRenewed(deposit.getIsRenewed());
		previousDeposit.setLastRenewDate(deposit.getLastRenewDate());
		previousDeposit.setLinkedAccountNumber(deposit.getLinkedAccountNumber());
		previousDeposit.setMaturityAmount(deposit.getMaturityAmount());
		previousDeposit.setMaturityAmountOnClosing(deposit.getMaturityAmountOnClosing());
		previousDeposit.setMaturityDate(deposit.getMaturityDate());
		previousDeposit.setMaturityInstruction(deposit.getMaturityInstruction());
		previousDeposit.setModifiedDate(deposit.getModifiedDate());
		previousDeposit.setModifiedInterestRate(deposit.getModifiedInterestRate());
		previousDeposit.setModifyPanalityAmount(deposit.getModifyPanalityAmount());
		previousDeposit.setNewMaturityAmount(deposit.getNewMaturityAmount());
		previousDeposit.setNewMaturityDate(deposit.getNewMaturityDate());
		previousDeposit.setPaymentMode(deposit.getPaymentMode());
		previousDeposit.setPaymentType(deposit.getPaymentType());
		previousDeposit.setPayOffDueDate(deposit.getPayOffDueDate());
		previousDeposit.setPayOffInterestType(deposit.getPayOffInterestType());
		previousDeposit.setPrematurePanaltyAmount(deposit.getPrematurePanaltyAmount());
		previousDeposit.setReverseEmiCategory(deposit.getReverseEmiCategory());
		previousDeposit.setStatus(deposit.getStatus());
		previousDeposit.setStopPayment(deposit.getStopPayment());
		previousDeposit.setTenure(deposit.getTenure());
		previousDeposit.setTenureType(deposit.getTenureType());
		previousDeposit.setTransactionId(deposit.getTransactionId());
		previousDeposit.setTaxSavingDeposit(deposit.getTaxSavingDeposit());		
		previousDeposit.setDepositClassification(deposit.getDepositClassification());	
		previousDeposit.setPrimaryCitizen(deposit.getPrimaryCitizen());		
		previousDeposit.setPrimaryNRIAccountType(deposit.getPrimaryNRIAccountType());
		previousDeposit.setPrimaryCustomerCategory(deposit.getPrimaryCustomerCategory());

		return previousDeposit;
	}
	
	private Date getDueDate(Deposit deposit) {
		Date dueDate = deposit.getDueDate();
		Integer deductionDay = deposit.getDeductionDay();

		DepositModification modification = depositModificationDAO.getLastByDepositId(deposit.getId());
		if (modification == null) {
			modification = new DepositModification();
			modification.setDepositAmount(deposit.getDepositAmount());
			modification.setPaymentMode(deposit.getPaymentMode());
			modification.setPaymentType(deposit.getPaymentType());
		}

		Integer monthsToAdd = 0;
		if (modification.getPaymentType().equalsIgnoreCase(Constants.MONTHLY)) {
			monthsToAdd = 1;
		} else if (modification.getPaymentType().equalsIgnoreCase(Constants.QUARTERLY)) {
			monthsToAdd = 3;
		} else if (modification.getPaymentType().equalsIgnoreCase(Constants.FULLYEARLY)
				|| modification.getPaymentType().equalsIgnoreCase(Constants.ANNUALLY))
		{
			monthsToAdd = 12;
		}
		else if (modification.getPaymentType().equalsIgnoreCase(Constants.HALFYEARLY))
		{
			monthsToAdd = 6;
		}

		dueDate = DateService.addDays(DateService.generateMonthsDate(dueDate, monthsToAdd), 1);
		if(deductionDay!=null)
			dueDate = DateService.setDate(dueDate, deductionDay);
		
		return dueDate;
	}

	public List<Date> getFutureInterestDates(Date fromDate, Date maturityDate, String interestCalculationBasis) {

		List<Date> interestDates = new ArrayList();
		//BankConfiguration bankConfiguration = ratesDAO.findAll();

		while (true) {

			int month = DateService.getMonth(fromDate);
			//bankConfiguration.getInterestCalculationBasis()
			 if (interestCalculationBasis.equalsIgnoreCase(Constants.MONTHLY)) {
					if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate), maturityDate) < 0) {

						interestDates.add(maturityDate);
						break;
					}

					interestDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.getFirstDateOfNextMonth(fromDate);

			}
			else if (interestCalculationBasis.equalsIgnoreCase(Constants.QUARTERLY)) {
				if ((month == 2 || month == 5 || month == 8 || month == 11)) {
					// fromDate = DateService.getFirstDateOfNextMonth(fromDate);
					if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate),
							maturityDate) < 0) {

						interestDates.add(maturityDate);
						break;
					}
					interestDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 3);
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			} 
			else if (interestCalculationBasis.equalsIgnoreCase(Constants.HALFYEARLY)) {
				if (month == 2 || month == 8) {
					// fromDate = DateService.getFirstDateOfNextMonth(fromDate);
					if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate),
							maturityDate) < 0) {

						interestDates.add(maturityDate);
						break;
					}
					interestDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 6);
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			} 
			else if (interestCalculationBasis.equalsIgnoreCase(Constants.ANNUALLY)) {
				if (month == 2) {
					// fromDate = DateService.getFirstDateOfNextMonth(fromDate);
					if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate),
							maturityDate) < 0) {

						interestDates.add(maturityDate);
						break;
					}
					interestDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 12);
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			} 

		}
		return interestDates;
	}
	
	public List<Date> getFutureCompoundingDates(Date fromDate, Date maturityDate, String interestCompoundingBasis) {

		List<Date> compoundingDates = new ArrayList();
		//BankConfiguration bankConfiguration = ratesDAO.findAll();

		while (true) {

			int month = DateService.getMonth(fromDate);
			//bankConfiguration.getInterestCalculationBasis()
			 if (interestCompoundingBasis.equalsIgnoreCase(Constants.MONTHLY)) {
					if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate), maturityDate) < 0) {

						compoundingDates.add(maturityDate);
						break;
					}

					compoundingDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.getFirstDateOfNextMonth(fromDate);

			}
			else if (interestCompoundingBasis.equalsIgnoreCase(Constants.QUARTERLY)) {
				if ((month == 2 || month == 5 || month == 8 || month == 11)) {
					// fromDate = DateService.getFirstDateOfNextMonth(fromDate);
					if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate),
							maturityDate) < 0) {

						compoundingDates.add(maturityDate);
						break;
					}
					compoundingDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 3);
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			} 
			else if (interestCompoundingBasis.equalsIgnoreCase(Constants.HALFYEARLY)) {
				if (month == 2 || month == 8) {
					// fromDate = DateService.getFirstDateOfNextMonth(fromDate);
					if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate),
							maturityDate) < 0) {

						compoundingDates.add(maturityDate);
						break;
					}
					compoundingDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 6);
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			} 
			else if (interestCompoundingBasis.equalsIgnoreCase(Constants.ANNUALLY)) {
				if (month == 2) {
					// fromDate = DateService.getFirstDateOfNextMonth(fromDate);
					if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate),
							maturityDate) < 0) {

						compoundingDates.add(maturityDate);
						break;
					}
					compoundingDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 12);
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			} 

		}
		return compoundingDates;
	}

	public List<Date> getFutureDepositDates(Integer deductionDay, String paymentType, Date maturityDate) {

		List<Date> depositDates = new ArrayList();
		Date fromDate = DateService.getCurrentDate();
		Date dueDate = fromDate; 
		if(deductionDay != null)
			dueDate = DateService.setDate(fromDate, deductionDay);
		
		
		if (dueDate == null || paymentType == null)
			return null;

		
		if(deductionDay == null)
			return  depositDates;
		
		if(DateService.getDaysBetweenTwoDates(dueDate, fromDate) > 0)
			depositDates.add(dueDate);
			
		dueDate=DateService.setDate(dueDate, deductionDay);
		int monthsToAdd = 0;
		if (paymentType.equalsIgnoreCase(Constants.MONTHLY)) {
			monthsToAdd = 1;
		} else if (paymentType.equalsIgnoreCase(Constants.QUARTERLY)) {
			monthsToAdd = 3;
		} else if (paymentType.equalsIgnoreCase(Constants.FULLYEARLY)
				|| paymentType.equalsIgnoreCase(Constants.ANNUALLY))
			monthsToAdd = 12;
		else if (paymentType.equalsIgnoreCase(Constants.HALFYEARLY))
			monthsToAdd = 6;

		// From next due Deposits
		while (true) {

			dueDate = DateService.addDays(DateService.generateMonthsDate(dueDate, monthsToAdd), 1);
			dueDate=DateService.setDate(dueDate, deductionDay);
			if (monthsToAdd == 0)
				break;
			if (DateService.getDaysBetweenTwoDates(dueDate, maturityDate) > 0) {
				depositDates.add(dueDate);
			} else
				break;
		}

		return depositDates;
	}
	
	


	public BankPayment insertInBankPayment(Long depositId, Double amount, String comment) {

		// To insert the deposit amount to bank payment .
		BankPayment bankPayment = new BankPayment();

		bankPayment.setDepositID(depositId);
		bankPayment.setAmount(amount);
		bankPayment.setPaymentDate(DateService.loginDate);
		bankPayment.setComment(comment);
		bankPayment = bankPaymentDAO.saveBankPayment(bankPayment);
		return bankPayment;

	}

	public BankPaymentDetails insertInBankPaymentDetails(Long bankPaymentId, Long depositId, Long depositHolderId,
			Long customerId, Double contributedAmt, Date paymentDate, Long nomineeId) {

		BankPaymentDetails bankPaymentDetails = new BankPaymentDetails();
		bankPaymentDetails.setBankPaymentId(bankPaymentId);
		bankPaymentDetails.setDepositId(depositId);
		bankPaymentDetails.setDepositHolderID(depositHolderId);
		bankPaymentDetails.setCustomerId(customerId);
		bankPaymentDetails.setAmount(contributedAmt);
		bankPaymentDetails.setAmountPaidDate(paymentDate);
		bankPaymentDetails.setNomineeId(nomineeId);
		bankPaymentDetails = bankPaymentDAO.saveBankPaymentDetails(bankPaymentDetails);
		return bankPaymentDetails;
	}
	

	public void insertInBankPayment(Deposit deposit, List<DepositHolder> depositHolderList, Double amount,
			String purpose) {
		// Create a Bank payment For the amount left in Principal
		BankPayment bankPayment = this.insertInBankPayment(deposit.getId(), amount, purpose);

		// //Insert in BankPaid Master
		// BankPaid bankPaid = this.insertInBankPaid(bankPayment.getId(),
		// deposit.getId(), amount, purpose);

		// Insert in BankPayment Details
		for (int j = 0; j < depositHolderList.size(); j++) {
			DepositHolder depositHolder = depositHolderList.get(j);
			Double contributedAmount = amount * depositHolder.getContribution() / 100;

			// Insert in BankPaymentDetails
			BankPaymentDetails bankPaymentDetails = this.insertInBankPaymentDetails(bankPayment.getId(),
					deposit.getId(), depositHolder.getId(), depositHolder.getCustomerId(), contributedAmount,
					DateService.getCurrentDateTime(), null);

			
			// Check if the product is configured for Fund Transfer, 
			// then you go for transfer the fund in linked account,
			// Otherwise wait for DD or Cheque.
			ProductConfiguration productConfiguration  =  productConfigurationDAO.findById(deposit.getProductConfigurationId());
			if(productConfiguration==null || (productConfiguration!=null && productConfiguration.getPaymentModeOnMaturity()!=null && 
					productConfiguration.getPaymentModeOnMaturity().equals("Fund Transfer")))
			{
				
				
				// Insert data in BankPaid
				BankPaid bankPaid = this.insertInBankPaid(deposit.getId(), bankPaymentDetails.getBankPaymentId(),amount, purpose);
				
				List<AccountDetails> accountDetailsList = accountDetailsDAO
						.findCurrentSavingByCustId(depositHolder.getCustomerId());
				String accountNo = "";
				if (accountDetailsList.size() > 0)
				{
					//AccountDetails accountDetails = accountDetailsList.get(0);
					accountNo = accountDetailsList.get(0).getAccountNo();
					if (accountNo != "") {
						// If the holder is not alive, then find the nominee of that
						// holder
						if (depositHolder.getDeathCertificateSubmitted() != null
								&& depositHolder.getDeathCertificateSubmitted().equalsIgnoreCase("Yes"))
							continue;
						
						// Do fund transfer while paying
						if(deposit.getIsMaturityDisbrsmntInLinkedAccount()!= null) 
						{
							if(deposit.getIsMaturityDisbrsmntInLinkedAccount()==1)

							{
								this.insertInBankPaidDetails(bankPaid.getId(), bankPayment.getId(), bankPaymentDetails.getId(), depositHolder.getId(),
										depositHolder.getCustomerId(), contributedAmount, DateService.getCurrentDateTime(),
										Constants.FUNDTRANSFER, null, null, null, null, accountNo, null, null, null, null, null, null);
							}
							else								
							{
															
								this.insertInBankPaidDetails(bankPaid.getId(),  bankPayment.getId(), bankPaymentDetails.getId(),
										depositHolder.getId(), depositHolder.getCustomerId(), contributedAmount,DateService.getCurrentDateTime(),
										Constants.FUNDTRANSFER, depositHolder.getMaturityDisbrsmntBankName(), null, 
										null, null, null, null, depositHolder.getIsMaturityDisbrsmntInSameBank(),
										depositHolder.getMaturityDisbrsmntAccHolderName(), depositHolder.getMaturityDisbrsmntAccNumber(), 
										Constants.FUNDTRANSFER, depositHolder.getMaturityDisbrsmntBankIFSCCode());
							}
							
							// Update paid status in Bank Payment Details
							bankPaymentDetails.setIsPaid(1);
							bankPaymentDAO.updateBankPaymentDetails(bankPaymentDetails);
							
							// Insert in Journal & Ledger
							// -----------------------------------------------------------
							String accountType = "";
							if(accountDetailsList.get(0).getAccountType().equalsIgnoreCase("Savings"))
								accountType = "Savings Account";
							else if(accountDetailsList.get(0).getAccountType().equalsIgnoreCase("Current"))
								accountType = "Current Account";
							if(accountDetailsList.get(0).getAccountType().equalsIgnoreCase("Deposit"))
								accountType = "Deposit Account";
							
							//ToDo:
//							ledgerService.insertJournalLedger(deposit.getId(),this.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(), deposit.getAccountNumber(),
//									"Deposit Account", accountNo, accountType, "BankPayment", contributedAmount,
//									Constants.FUNDTRANSFER, null);
							// -----------------------------------------------------------
						}				
					
					}
				}
			}
		}
	}
	
	public BankPaid insertInBankPaid(Long depositId,Long bankPaymentId, Double amount, String comment) {

		// To insert the deposit amount to bank payment .
		BankPaid bankPaid = new BankPaid();
		bankPaid.setBankPaymentId(bankPaymentId);
		bankPaid.setDepositID(depositId);
		bankPaid.setAmount(amount);
		bankPaid.setPaymentDate(DateService.loginDate);
		bankPaid.setComment(comment);
		bankPaid = bankPaymentDAO.saveBankPaid(bankPaid);
		return bankPaid;

	}
	
	public void insertInBankPaidDetails(Long bankPaidId, Long bankPaymentId, Long bankPaymentDetailsId, Long depositHolderId,
			Long customerId, Double contributedAmt, Date paymentDate, String modeOfPayment, String bank, String branch,
			String chequeDDNo, Date chequeDDdate, String savingCurrentAccountNumber, Long nomineeId, Integer isMaturityFundTransferInSameBank, 
			String maturityFundTransferAccHolderName, String maturityFundTransferAccNumber, String maturityFundTransferType,
			String maturityFundTransferBankIFSCCode) {

		BankPaidDetails bankPaidDetails = new BankPaidDetails();
		// deathCertificate =
		// (depositHolderList.get(i).getDeathCertificateSubmitted())!=null ?
		// depositHolderList.get(i).getDeathCertificateSubmitted() :
		// Constants.No;
	
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(modeOfPayment);
		
		bankPaidDetails.setBankPaymentId(bankPaymentId);
		bankPaidDetails.setBankPaidId(bankPaidId);
		bankPaidDetails.setBankPaymentDetailsId(bankPaymentDetailsId);
		bankPaidDetails.setDepositHolderID(depositHolderId);
		bankPaidDetails.setCustomerId(customerId);
		bankPaidDetails.setPaymentModeId(mop.getId());
		bankPaidDetails.setPaymentMode(mop.getPaymentMode());
		bankPaidDetails.setAmount(contributedAmt);
		bankPaidDetails.setAmountPaidDate(paymentDate);
		bankPaidDetails.setBank(bank);
		bankPaidDetails.setBranch(branch);
		bankPaidDetails.setChequeDDNumber(chequeDDNo);
		bankPaidDetails.setChequeDDdate(chequeDDdate);
		bankPaidDetails.setSavingCurrentAccountNumber(savingCurrentAccountNumber);
		bankPaidDetails.setNomineeId(nomineeId);
		bankPaidDetails.setMaturityFundTransferAccHolderName(maturityFundTransferAccHolderName);
		bankPaidDetails.setMaturityFundTransferAccNumber(maturityFundTransferAccNumber);
		bankPaidDetails.setMaturityFundTransferBankIFSCCode(maturityFundTransferBankIFSCCode);

		bankPaymentDAO.saveBankPaidDetails(bankPaidDetails);

	}
	
	public Float getDepositInterestRateForSlab(Integer days, String category, String currency, Double fromSlab, Double toSlab, 
			String depositClassification, String citizen, String nriAccountType) {
		DepositRates depositeRate = depositRatesDAO.getRateByMaturityPeriodAndCategory(days, category, currency, fromSlab, toSlab, depositClassification);
		if (depositeRate != null) {
			return depositeRate.getInterestRate();
		} else {
			return null;
		}

	}
	
	private Float calculateAge(Date birthDate)
	{
		int years = 0;
	      int months = 0;
	      int days = 0;
	 
	      //create calendar object for birth day
	      Calendar birthDay = Calendar.getInstance();
	      birthDay.setTimeInMillis(birthDate.getTime());
	 
	      //create calendar object for current day
	      long currentTime = System.currentTimeMillis();
	      Calendar now = Calendar.getInstance();
	      now.setTimeInMillis(currentTime);
	 
	      //Get difference between years
	      years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
	      int currMonth = now.get(Calendar.MONTH) + 1;
	      int birthMonth = birthDay.get(Calendar.MONTH) + 1;
	 
	      //Get difference between months
	      months = currMonth - birthMonth;
	 
	      //if month difference is in negative then reduce years by one
	      //and calculate the number of months.
	      if (months < 0)
	      {
	         years--;
	         months = 12 - birthMonth + currMonth;
	         if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
	            months--;
	      } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
	      {
	         years--;
	         months = 11;
	      }
	 
	      //Calculate the days
	      if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
	         days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
	      else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
	      {
	         int today = now.get(Calendar.DAY_OF_MONTH);
	         now.add(Calendar.MONTH, -1);
	         days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
	      }
	      else
	      {
	         days = 0;
	         if (months == 12)
	         {
	            years++;
	            months = 0;
	         }
	      }
	      
	      return new Float(years + "." + months);
	
	}
	
	public String geCustomerActualCategory(Customer customer)
	{
		String category = customer.getCategory();
		if(customer.getCitizen().equalsIgnoreCase("NRI"))
			return category;
		
		if(customer.getCategory().equalsIgnoreCase(Constants.SENIORCITIZEN) || customer.getCategory().equalsIgnoreCase("Sr Citizen"))
			return category;
		
		Float age = this.calculateAge(customer.getDateOfBirth());
		if(age < 60)
		{
			return category;
		}
		else
		{
			List<CustomerCategory> list = customerDAO.getAllActiveCustomerCategory();
			for(int i=0;i<list.size();i++)
			{
				if(list.get(i).getCustomerCategory().equalsIgnoreCase(Constants.SENIORCITIZEN) || customer.getCategory().equalsIgnoreCase("Sr Citizen"))
				{
					category = list.get(i).getCustomerCategory();
					break;
				}
				else
					continue;
			}
			
		}
		
		return category;
	}
	

	public HashMap<String, Double> calculateInterestAmount(Deposit deposit, List<DepositHolder> depositHolderList,
			String autoDeposit, Date increasedDateForMaturityOnHoliday) {

		Long depositId = deposit.getId();

		Float interestRate = (deposit.getModifiedInterestRate() == null) ? deposit.getInterestRate()
				: deposit.getModifiedInterestRate();

		Boolean isDynamicRate = false; 
		if(autoDeposit!=null && autoDeposit.contains("Auto") && deposit.getSweepInType() != null && deposit.getSweepInType().contains("Dynamic Interest"))
		{
			isDynamicRate = true;
		}
	
		Distribution lastInterestCalculated = paymentDistributionDAO.getLastInterestCalculatedOrAdjusted(depositId,
				DateService.getCurrentDate());

		Long lastInterestDistributionId = 0l;
		Date prevDistributionDate = null;
		Double prevCompoundFixedAmt = 0d, prevCompoundVariableAmt = 0d;
		Double fixedIntAmt = 0d, variableIntAmt = 0d, totalFixedInterest = 0d, totalVariableInterest = 0d;
		Integer totalDaysDiff = 0;
		//Boolean isAdjustmentPresent = false;
		// If current month Interest is not calculated

		// Get last month InterstId from the distribution table
		// if Interest is calculated earlier
		if (lastInterestCalculated != null) {
			lastInterestDistributionId = lastInterestCalculated.getId();
			prevDistributionDate = lastInterestCalculated.getDistributionDate();
			prevCompoundFixedAmt = lastInterestCalculated.getCompoundFixedAmt();
			prevCompoundVariableAmt = lastInterestCalculated.getCompoundVariableAmt();
			if (lastInterestCalculated.getAction().equalsIgnoreCase("Interest"))
				prevDistributionDate = DateService.addDays(prevDistributionDate, 1);
			
			if (lastInterestCalculated.getAction().equalsIgnoreCase("Interest Compounding"))
				prevDistributionDate = DateService.addDays(prevDistributionDate, 1);
			
			if (lastInterestCalculated.getAction().equalsIgnoreCase("Renew"))
				prevDistributionDate = DateService.addDays(prevDistributionDate, 1);
			
			if (lastInterestCalculated.getAction().equalsIgnoreCase("Interest Adjustment For Withdraw"))
				prevDistributionDate = DateService.addDays(prevDistributionDate, 1);

//			if (lastInterestCalculated.getAction().contains("Adjustment"))
//				isAdjustmentPresent = true;
		}

		// Fetch all the records starting from the last interest calculated
		// Or Fetch all records starting from beginning
		List<Distribution> lastPaymentDistForDepWithdraw = paymentDistributionDAO
				.getAllDepWithdrawFromLastInterestCalc(depositId, lastInterestDistributionId);

		// Transactions have been made
		if (lastPaymentDistForDepWithdraw != null) {

			for (int i = 0; i < lastPaymentDistForDepWithdraw.size(); i++) {
				Distribution paymentDist = lastPaymentDistForDepWithdraw.get(i);

				if (lastInterestCalculated == null && i == 0) {
					// distributionId = paymentDist.getId();
					if(!paymentDist.getAction().equalsIgnoreCase("TDS") || (paymentDist.getAction().equalsIgnoreCase("TDS") && (!(prevCompoundFixedAmt.equals(paymentDist.getCompoundFixedAmt())) ||
							(!(prevCompoundVariableAmt.equals(paymentDist.getCompoundVariableAmt()))))))
					{
						prevDistributionDate = paymentDist.getDistributionDate();
						prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
						prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();
					}
					continue;
				}

				if(!paymentDist.getAction().equalsIgnoreCase("TDS") || (paymentDist.getAction().equalsIgnoreCase("TDS") && (!(prevCompoundFixedAmt.equals(paymentDist.getCompoundFixedAmt())) ||
						(!prevCompoundVariableAmt.equals(paymentDist.getCompoundVariableAmt())))))
				{
					if (paymentDist.getAction().equalsIgnoreCase("TDS")
				
						|| paymentDist.getAction().equalsIgnoreCase("PAYOFF")
						|| paymentDist.getAction().equalsIgnoreCase("Interest Compounding")) 
					{

						if (paymentDist.getAction().equalsIgnoreCase("TDS"))
							prevDistributionDate = DateService.addDays(paymentDist.getDistributionDate(), 1);
						if (paymentDist.getAction().equalsIgnoreCase("Interest Compounding"))
							prevDistributionDate = DateService.addDays(paymentDist.getDistributionDate(), 1);
						if (paymentDist.getAction().equalsIgnoreCase("Interest Adjustment For Withdraw"))
							prevDistributionDate = DateService.addDays(paymentDist.getDistributionDate(), 1);
	
						prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
						prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();
	
						continue;
					}
				}
				
				if(!paymentDist.getAction().equalsIgnoreCase("TDS") || (paymentDist.getAction().equalsIgnoreCase("TDS") && (!(prevCompoundFixedAmt.equals(paymentDist.getCompoundFixedAmt())) ||
						(!prevCompoundVariableAmt.equals(paymentDist.getCompoundVariableAmt())))))
				{
					Integer daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
							paymentDist.getDistributionDate());
	
					if (daysDifference > 0) {
						totalDaysDiff = totalDaysDiff + daysDifference;
						
						if(isDynamicRate)
						{
							interestRate = this.getDepositInterestRate(daysDifference, 
									deposit.getPrimaryCustomerCategory(), deposit.getDepositCurrency(),  
									deposit.getDepositAmount(), deposit.getDepositClassification(), 
									deposit.getPrimaryCitizen(), deposit.getPrimaryNRIAccountType());
						}
	
						fixedIntAmt = (prevCompoundFixedAmt * interestRate / 100) / 365 * daysDifference;
						variableIntAmt = (prevCompoundVariableAmt * interestRate / 100) / 365 * daysDifference;
	
						totalFixedInterest = totalFixedInterest + fixedIntAmt;
						totalVariableInterest = totalVariableInterest + variableIntAmt;
	
					}
	
					prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
					prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();
	
					prevDistributionDate = paymentDist.getDistributionDate();
				}
			}
		}

		// Add Interest if there is any Withdraw
		// -----------------------------------------
		if(lastInterestCalculated != null && lastInterestCalculated.getAction().equalsIgnoreCase("Interest Adjustment For Withdraw"))
		{
	
			// Get the Distribution for Withdraw only
			Distribution withdrawDistribution = paymentDistributionDAO.getPreviousDistribution(depositId, lastInterestCalculated.getId());
						
			// Get the last BaseLine before interest calculation for withdrawal amount
			// because base line will be the top-up/last adjustment
			Distribution lastBaseLine = paymentDistributionDAO.getLastBaseLineForWithdraw(depositId, withdrawDistribution.getId());
		
			Integer durationFromBase = DateService.getDaysBetweenTwoDates(lastBaseLine.getDistributionDate(),
					withdrawDistribution.getDistributionDate())+1;

			
			// Get the new interest rate of the Duration from the base
//			Float newInterestRateForThePeriod = depositRateDAO.getInterestRate(deposit.getPrimaryCustomerCategory(), deposit.getDepositCurrency(), durationFromBase, deposit.getDepositClassification(), deposit.getDepositAmount());

			Float newInterestRateForThePeriod = this.getDepositInterestRate(durationFromBase, 
					deposit.getPrimaryCustomerCategory(), deposit.getDepositCurrency(),  
					deposit.getDepositAmount(), deposit.getDepositClassification(), 
					deposit.getPrimaryCitizen(), deposit.getPrimaryNRIAccountType());
			
		
			
			if(withdrawDistribution != null && withdrawDistribution.getAction().equalsIgnoreCase(Constants.WITHDRAW))
			{
				// get the Withdraw Amount
				Double interestOnWithdrawAmt = (withdrawDistribution.getVariableAmt() * newInterestRateForThePeriod / 100) / 365
						* durationFromBase;
				interestOnWithdrawAmt = interestOnWithdrawAmt *-1;
				totalVariableInterest = totalVariableInterest + interestOnWithdrawAmt;
				
			}				
		}
		// -----------------------------------------
		
		// Now all transaction is done and
		// it is the time of interest calculation on Month End
		Date dt = (increasedDateForMaturityOnHoliday == null) ? DateService.getCurrentDate() : increasedDateForMaturityOnHoliday;
		Integer daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate, dt)
				+ 1;

		if(isDynamicRate)
		{
			interestRate = this.getDepositInterestRate(daysDifference, 
					deposit.getPrimaryCustomerCategory(), deposit.getDepositCurrency(),  
					deposit.getDepositAmount(), Constants.fixedDeposit, 
					deposit.getPrimaryCitizen(), deposit.getPrimaryNRIAccountType());
		}
		
		fixedIntAmt = (prevCompoundFixedAmt * interestRate / 100) / 365 * daysDifference;
		variableIntAmt = (prevCompoundVariableAmt * interestRate / 100) / 365 * daysDifference;

		// If adjustment is done, then fetch the In hand Interest from Interest Summary
		// Table
//		if (isAdjustmentPresent) {
//			DepositSummary depositSummaryAfterAdjustment = depositSummaryDAO.getDepositSummary(depositId);
//			totalFixedInterest = totalFixedInterest
//					+ (depositSummaryAfterAdjustment.getTotalFixedInterestInHand() == null ? 0
//							: depositSummaryAfterAdjustment.getTotalFixedInterestInHand());
//			totalVariableInterest = totalVariableInterest
//					+ (depositSummaryAfterAdjustment.getTotalVariableInterestInHand() == null ? 0
//							: depositSummaryAfterAdjustment.getTotalVariableInterestInHand());
//		}

		totalFixedInterest = totalFixedInterest + fixedIntAmt;
		totalVariableInterest = totalVariableInterest + variableIntAmt;
		
		HashMap<String, Double> interestDetails = new HashMap<>();
		interestDetails.put(Constants.FIXEDINTEREST, totalFixedInterest);
		interestDetails.put(Constants.VARIABLEINTEREST,totalVariableInterest);

		return interestDetails;

	}
	

	
	public Double getCompoundInterestAmount(Deposit deposit)
	{

		Long depositId = deposit.getId();
		// Get Interest Summary of the deposit
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(depositId);

		// Compound the Interest
		if (depositSummary == null)
			return null;

		// Getting in Hand Interest which is not compounded
		Double fixedInHandInterest = depositSummary.getTotalFixedInterestInHand();
		Double variableInHandInterest = depositSummary.getTotalVariableInterestInHand();
		
		return ((fixedInHandInterest == 0? 0d :fixedInHandInterest) + (variableInHandInterest == 0? 0d :variableInHandInterest));
		
	}
	
	public Distribution insertInDistributionForPenaltyInFixedVariable(String action, Deposit deposit, List<DepositHolder> depositHolderList,
			Double fixedPenaltyAmount, Double variablePenaltyAmount)
	{
		Distribution previousPaymentDistribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());
		
		fixedPenaltyAmount = fixedPenaltyAmount == null? 0d : fixedPenaltyAmount;
		variablePenaltyAmount = variablePenaltyAmount == null? 0d : variablePenaltyAmount;
		
		// Step 1: One Row will be added in Distribution table	
		// ----------------------------------------------------------------------------------
		Distribution paymentDistribution = new Distribution();
		paymentDistribution.setDistributionDate(DateService.getCurrentDate());
		paymentDistribution.setDepositId(deposit.getId());
		paymentDistribution.setAction(action); //(Constants.MissedRecurringPaymentPenalty);
		paymentDistribution.setDepositedAmt(-(fixedPenaltyAmount + variablePenaltyAmount));

		
		paymentDistribution.setFixedAmt(-fixedPenaltyAmount);
		paymentDistribution.setVariableAmt(-variablePenaltyAmount);
		paymentDistribution.setCompoundFixedAmt(previousPaymentDistribution.getCompoundFixedAmt()-fixedPenaltyAmount);
		paymentDistribution.setCompoundVariableAmt(previousPaymentDistribution.getCompoundVariableAmt()-variablePenaltyAmount); 		
		paymentDistribution.setTotalBalance(paymentDistribution.getCompoundFixedAmt() + paymentDistribution.getCompoundVariableAmt());

		
		paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);
		// ----------------------------------------------------------------------------------
		
		// 2. Rows will be added to HolderWiseDistribution table
		// ----------------------------------------------------------------------------------
		this.insertInDepositHolderWiseDistributionForPenaltyInFixedVariable(depositHolderList, action,
				paymentDistribution.getId(), fixedPenaltyAmount, variablePenaltyAmount);
		//  ----------------------------------------------------------------------------------	
		
		return paymentDistribution;
	}

	public void insertInDepositHolderWiseDistributionForPenaltyInFixedVariable(List<DepositHolder> depositHolderList, String action,
			Long distributionId, Double fixedPenaltyAmount, Double variablePenaltyAmount)
	{
	
		for (int i = 0; i < depositHolderList.size(); i++) {
													
			DepositHolderWiseDistribution depHolderDistribution = new DepositHolderWiseDistribution();
			depHolderDistribution.setAction(action);
			depHolderDistribution.setActionId(distributionId);
			depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
			depHolderDistribution.setCustomerId(depositHolderList.get(i).getCustomerId());
			depHolderDistribution.setDepositHolderId(depositHolderList.get(i).getId());
			depHolderDistribution.setDepositId(depositHolderList.get(i).getDepositId());
			depHolderDistribution.setContribution(depositHolderList.get(i).getContribution());

			// Get the last distribution of the holder from holderwisedistribution
			DepositHolderWiseDistribution lastDistribution = depositHolderWiseDistributionDAO
					.getDepositHolderWiseLastDistribution(depositHolderList.get(i).getDepositId(),
							depositHolderList.get(i).getId());
			
			// Get the last distribution of the holder from holder wise distribution
			DepositHolderWiseDistribution lastHolderWiseDistribution = depositHolderWiseDistributionDAO.getDepositHolderWiseLastDistribution(depositHolderList.get(i).getDepositId(), depositHolderList.get(i).getId());	
		
			// Step 1: One Row will be added in Distribution table
			// ----------------------------------------------------------------------------------
			depHolderDistribution = new DepositHolderWiseDistribution();
			depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
			depHolderDistribution.setAction(action);
			depHolderDistribution.setActionId(distributionId);
			depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
			depHolderDistribution.setCustomerId(depositHolderList.get(i).getCustomerId());
			depHolderDistribution.setDepositHolderId(depositHolderList.get(i).getId());
			depHolderDistribution.setDepositId(depositHolderList.get(i).getDepositId());
			depHolderDistribution.setContribution(depositHolderList.get(i).getContribution());
				
			Double contributedFixedPenalty = fixedPenaltyAmount * depositHolderList.get(i).getContribution() / 100;
			Double contributedVariablePenalty = variablePenaltyAmount * depositHolderList.get(i).getContribution() / 100;


			depHolderDistribution.setFixedAmt(-contributedFixedPenalty);
			depHolderDistribution.setVariableAmt(-contributedVariablePenalty);
			
//			depHolderDistribution.setBalanceFixedInterest(lastHolderWiseDistribution.getBalanceFixedInterest());
//			depHolderDistribution.setBalanceVariableInterest(lastHolderWiseDistribution.getBalanceVariableInterest());
			
			depHolderDistribution.setFixedCompoundAmount(lastHolderWiseDistribution.getFixedCompoundAmount()-contributedFixedPenalty);
			depHolderDistribution
			.setVariableCompoundAmount(lastHolderWiseDistribution.getVariableCompoundAmount()-contributedVariablePenalty);
				
			depositHolderWiseDistributionDAO.saveDepositHolderWiseDistribution(depHolderDistribution);
		}
	}
	
	public DepositSummary upsertInDepositSummaryForPenaltyInFixedVariable(Deposit deposit, String action, 
			List<DepositHolder> depositHolderList, Double fixedPenaltyAmount, Double variablePenaltyAmount) {

		
		// getAllRatesByCustomerCategoryCitizenAndAccountType
		// Get last Deposit Summary
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(deposit.getId());	

		if(action.equals(Constants.topupPenalty))
		{
			depositSummary.setTotalFixedPrincipal(depositSummary.getTotalFixedPrincipal()-fixedPenaltyAmount);
			depositSummary.setTotalVariablePrincipal(depositSummary.getTotalVariablePrincipal()-variablePenaltyAmount);
			depositSummary.setTotalPrincipal(depositSummary.getTotalFixedPrincipal() + depositSummary.getTotalVariablePrincipal());

			depositSummaryDAO.update(depositSummary);
			
			//Insert or Update HolderWiseDepositSummary
			this.upsertHolderWiseDepositSummaryForPenaltyInFixedVariable(deposit, action, depositHolderList, 
					depositSummary, fixedPenaltyAmount, variablePenaltyAmount);
		}
		else
		{
			if(variablePenaltyAmount<=depositSummary.getTotalVariableInterestInHand())
			{
				depositSummary.setTotalVariableInterestInHand(depositSummary.getTotalVariableInterestInHand() - variablePenaltyAmount);
				depositSummary.setTotalVariableInterestAccumulated((depositSummary.getTotalVariableInterestAccumulated() == null ? 0
						: depositSummary.getTotalVariableInterestAccumulated()) - variablePenaltyAmount);
			}
			else
			{
				Double variableInhandInterest = depositSummary.getTotalVariableInterestInHand();
				//Double fixedInhandInterest = depositSummaryHolderWise.getTotalFixedInterestInHand();
				depositSummary.setTotalVariableInterestInHand(0d);
				depositSummary.setTotalVariableInterestAccumulated(depositSummary.getTotalVariableInterestAccumulated() - (variablePenaltyAmount-variableInhandInterest));
			}
			
	
			if(fixedPenaltyAmount<=depositSummary.getTotalFixedInterestInHand())
			{
				depositSummary.setTotalFixedInterestInHand(depositSummary.getTotalFixedInterestInHand()-fixedPenaltyAmount);
				depositSummary.setTotalFixedInterestAccumulated((depositSummary.getTotalFixedInterestAccumulated() == null ? 0
						: depositSummary.getTotalFixedInterestAccumulated()) - fixedPenaltyAmount);
			}
			else
			{
				Double fixedInhandInterest = depositSummary.getTotalFixedInterestInHand();
				//Double fixedInhandInterest = depositSummaryHolderWise.getTotalFixedInterestInHand();
				depositSummary.setTotalFixedInterestInHand(0d);
				depositSummary.setTotalFixedInterestAccumulated(depositSummary.getTotalFixedInterestAccumulated() - (fixedPenaltyAmount -fixedInhandInterest));
			}
			depositSummaryDAO.update(depositSummary);;	
				
				//Insert or Update HolderWiseDepositSummary
			this.upsertHolderWiseDepositSummaryForPenaltyInFixedVariable(deposit, action, depositHolderList, 
					depositSummary, fixedPenaltyAmount, variablePenaltyAmount);
		}
		

		// Update the currentBalance of the deposit
		if(depositSummary.getTotalPrincipal()==null)
			deposit.setCurrentBalance(0d);
		else
			deposit.setCurrentBalance(depositSummary.getTotalPrincipal());
		fixedDepositDAO.updateFD(deposit);
		
		return depositSummary;
	}

	@SuppressWarnings("unused")
	private void upsertHolderWiseDepositSummaryForPenaltyInFixedVariable(Deposit deposit, String action, List<DepositHolder> depositHolderList, 
			DepositSummary depositSummary, Double fixedPenaltyAmount, Double variablePenaltyAmount)
	{	
		
		boolean insert = false;
		
		if(action.equals(Constants.topupPenalty))
		{
			for (int i = 0; i < depositHolderList.size(); i++) 
			{
				
				insert = false; 
						
				DepositSummaryHolderWise depositSummaryHolderWise = depositSummaryDAO.getHolderWiseDepositSummary(deposit.getId(), depositHolderList.get(i).getId());
				
				if(depositSummaryHolderWise == null)
				{
					depositSummaryHolderWise = new DepositSummaryHolderWise();
					insert = true;
				}
				depositSummaryHolderWise.setDepositId(deposit.getId());
				depositSummaryHolderWise.setDepositHolderId(depositHolderList.get(i).getId());
				depositSummaryHolderWise.setCustomerId(depositHolderList.get(i).getCustomerId());
							
				Double contributedFixedPenalty = fixedPenaltyAmount * depositHolderList.get(i).getContribution() / 100;
				Double contributedVariablePenalty = variablePenaltyAmount * depositHolderList.get(i).getContribution() / 100;
			
				depositSummaryHolderWise.setTotalFixedPrincipal(depositSummaryHolderWise.getTotalFixedPrincipal()-contributedFixedPenalty);
				depositSummaryHolderWise.setTotalVariablePrincipal(depositSummaryHolderWise.getTotalVariablePrincipal()-contributedVariablePenalty);
				depositSummaryHolderWise.setTotalPrincipal(depositSummaryHolderWise.getTotalFixedPrincipal() + depositSummaryHolderWise.getTotalVariablePrincipal());
			
				if(insert)
				{
					depositSummaryHolderWise = depositSummaryDAO.insertDepositSummaryHolderWise(depositSummaryHolderWise);
				}
				else
				{
					depositSummaryHolderWise = depositSummaryDAO.updateDepositSummaryHolderWise(depositSummaryHolderWise);
				}
			}
		}
		else
		{
			for (int i = 0; i < depositHolderList.size(); i++) 
			{
				
				insert = false; 
						
				DepositSummaryHolderWise depositSummaryHolderWise = depositSummaryDAO.getHolderWiseDepositSummary(deposit.getId(), depositHolderList.get(i).getId());
				
				if(depositSummaryHolderWise == null)
				{
					depositSummaryHolderWise = new DepositSummaryHolderWise();
					insert = true;
				}
				depositSummaryHolderWise.setDepositId(deposit.getId());
				depositSummaryHolderWise.setDepositHolderId(depositHolderList.get(i).getId());
				depositSummaryHolderWise.setCustomerId(depositHolderList.get(i).getCustomerId());
							
				Double contributedFixedPenalty = fixedPenaltyAmount * depositHolderList.get(i).getContribution() / 100;
				Double contributedVariablePenalty = variablePenaltyAmount * depositHolderList.get(i).getContribution() / 100;
			
				
				if(contributedVariablePenalty<=depositSummaryHolderWise.getTotalVariableInterestInHand())
				{
					depositSummaryHolderWise.setTotalVariableInterestInHand(depositSummaryHolderWise.getTotalVariableInterestInHand() - contributedVariablePenalty);
					depositSummaryHolderWise.setTotalVariableInterestAccumulated((depositSummaryHolderWise.getTotalVariableInterestAccumulated() == null ? 0
							: depositSummaryHolderWise.getTotalVariableInterestAccumulated()) - contributedVariablePenalty);
				}
				else
				{
					Double variableInhandInterest = depositSummaryHolderWise.getTotalVariableInterestInHand();
					//Double fixedInhandInterest = depositSummaryHolderWise.getTotalFixedInterestInHand();
					depositSummaryHolderWise.setTotalVariableInterestInHand(0d);
					depositSummaryHolderWise.setTotalVariableInterestAccumulated(depositSummaryHolderWise.getTotalVariableInterestAccumulated() - (contributedVariablePenalty-variableInhandInterest));
				}
				
	
				if(contributedFixedPenalty<=depositSummaryHolderWise.getTotalFixedInterestInHand())
				{
					depositSummaryHolderWise.setTotalFixedInterestInHand(depositSummaryHolderWise.getTotalFixedInterestInHand() - contributedFixedPenalty);
					depositSummaryHolderWise.setTotalFixedInterestAccumulated((depositSummaryHolderWise.getTotalFixedInterestAccumulated() == null ? 0
							: depositSummaryHolderWise.getTotalFixedInterestAccumulated()) - contributedFixedPenalty);
				}
				else
				{
					Double fixedInhandInterest = depositSummaryHolderWise.getTotalFixedInterestInHand();
					//Double fixedInhandInterest = depositSummaryHolderWise.getTotalFixedInterestInHand();
					depositSummaryHolderWise.setTotalFixedInterestInHand(0d);
					depositSummaryHolderWise.setTotalFixedInterestAccumulated(depositSummaryHolderWise.getTotalFixedInterestAccumulated() - (contributedFixedPenalty -fixedInhandInterest));
				}
			
				if(insert)
				{
					depositSummaryHolderWise = depositSummaryDAO.insertDepositSummaryHolderWise(depositSummaryHolderWise);
				}
				else
				{
					depositSummaryHolderWise = depositSummaryDAO.updateDepositSummaryHolderWise(depositSummaryHolderWise);
				}
			}
		}	

	}
	
	public Distribution deductWithdrawPenalty(Deposit deposit, Double withdrawAount, Distribution lastDistribution, boolean isPrematureWithdraw, Double prevPenaltyDue) throws CustomException
	{
				
		// get the withdraw penalty master for the product Configuration
		WithdrawPenaltyMaster withdrawPenaltymaster = withdrawPenaltyDAO.getWithdrawPenalyMaster(deposit.getProductConfigurationId(), isPrematureWithdraw);
		
		if(withdrawPenaltymaster == null)
			return lastDistribution;

		Double penaltyAmount = 0d;
		
		
		// Get Withdraw Penalty Details
		List<WithdrawPenaltyDetails> withdrawPenaltyDetailList = withdrawPenaltyDAO.getWithdrawPenalyDetails(withdrawPenaltymaster.getId());
		
		// call the method to get the exact slab i.e. withdrawPenaltyDetail
		WithdrawPenaltyDetails withdrawPenaltyDetails = this.getWithdrawPenaltyDetailsForDeposit(deposit, withdrawPenaltyDetailList);
		
		if(withdrawPenaltyDetails != null)
		{

			if(withdrawPenaltyDetails.getPenaltyFlatAmount()!=null)
			{
				penaltyAmount = Double.parseDouble(withdrawPenaltyDetails.getPenaltyFlatAmount().toString());
			}
			else if(withdrawPenaltyDetails.getPenaltyRate()!=null)
			{
				Float penaltyRate = withdrawPenaltyDetails.getPenaltyRate();			
				penaltyAmount = penaltyRate == null? penaltyAmount :(withdrawAount * penaltyRate/100);
			}	
		}
	
		
		if(penaltyAmount == 0)
			return lastDistribution;
		
		if(lastDistribution.getCompoundVariableAmt()<penaltyAmount)
			throw new CustomException("Cant withdraw the amount, because penalty Amount is more than your withdrawable amount");
		
		penaltyAmount = penaltyAmount + (prevPenaltyDue == null?0d: prevPenaltyDue);
		penaltyAmount = this.round(penaltyAmount,2);
		
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
		
		// Insert penalty in PaymentDistribution and Holderwise PaymentDistribution and DepositSummary table
		// -----------------------------------------
		Distribution distribution1 = this.insertInDistributionForPenaltyInFixedVariable(Constants.withdrawPenalty,
				deposit, depositHolderList, 0d, penaltyAmount);
		
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(deposit.getId());
		depositSummary = this.upsertInDepositSummaryForPenaltyInFixedVariable(deposit, Constants.withdrawPenalty, depositHolderList,  0d, penaltyAmount);
		// -----------------------------------------
		
		// Insert in Journal & Ledger
		//-----------------------------------------------------------
		ModeOfPayment modeOfpayment = modeOfPaymentDAO.getModeOfPayment("Internal Transfer");
		if(modeOfpayment!=null)
		{
			ledgerService.insertJournal(deposit.getId(), this.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(), deposit.getAccountNumber(),
				null, Event.WITHDRAW_PENALTY.getValue(), penaltyAmount, modeOfpayment.getId(),
				depositSummary.getTotalPrincipal(), null);
		}	
		//-----------------------------------------------------------
		
		// WithdrawPenalty
		return distribution1;
	}

	private WithdrawPenaltyDetails getWithdrawPenaltyDetailsForDeposit(Deposit deposit, List<WithdrawPenaltyDetails> withdrawPenaltyDetailList)
	{
		Date fromDate = deposit.getCreatedDate();
				
		Distribution lastAdjustment = paymentDistributionDAO.getLastAdjustment(deposit.getId());
		if(lastAdjustment !=null)
			fromDate = lastAdjustment.getDistributionDate();
		
		Integer	tenureInDaysForDeposit =  DateService.getDaysBetweenTwoDates(fromDate, DateService.getCurrentDate());
		
		WithdrawPenaltyDetails withdrawPenaltyDetails = null;
		for(int i=0; i<withdrawPenaltyDetailList.size(); i++)
		{
			Date withdrawPenaltyEndDate = fromDate;
					
			String[] arrWithdrawPenaltyTenure = withdrawPenaltyDetailList.get(i).getTenure().split(",");
			for (int j = 0; j < arrWithdrawPenaltyTenure.length; j++) {
			
				if (arrWithdrawPenaltyTenure[j].toString().endsWith("Year")) {
					String num = arrWithdrawPenaltyTenure[j].replaceAll(" Year", "");
					withdrawPenaltyEndDate = DateService.addYear(withdrawPenaltyEndDate, Integer.parseInt(num));
				} else if (arrWithdrawPenaltyTenure[j].toString().endsWith("Month")) {
					String num = arrWithdrawPenaltyTenure[j].replaceAll(" Month", "");
					withdrawPenaltyEndDate = DateService.addMonths(withdrawPenaltyEndDate, Integer.parseInt(num));
				} else if (arrWithdrawPenaltyTenure[j].toString().endsWith("Day")) {
					String num = arrWithdrawPenaltyTenure[j].replaceAll(" Day", "");
					withdrawPenaltyEndDate = DateService.addDays(withdrawPenaltyEndDate, Integer.parseInt(num));
				}
			}
		
			Integer	tenureInDaysForWithdrawSlab =  DateService.getDaysBetweenTwoDates(fromDate, withdrawPenaltyEndDate);
			
			switch(withdrawPenaltyDetailList.get(i).getAmountSign()){    
			case "<":    
				if(deposit.getDepositAmount() < withdrawPenaltyDetailList.get(i).getAmount())
				{
					// Check the tenure is matching the criteria
					if(this.CheckTenureForWithdrawPenaltyDetails(withdrawPenaltyDetailList.get(i),tenureInDaysForDeposit,tenureInDaysForWithdrawSlab) )
						withdrawPenaltyDetails =  withdrawPenaltyDetailList.get(i);
				}			 
				break; 
			case "<=":    
				if(deposit.getDepositAmount() <= withdrawPenaltyDetailList.get(i).getAmount())
				{
					// Check the tenure is matching the criteria
					if(this.CheckTenureForWithdrawPenaltyDetails(withdrawPenaltyDetailList.get(i),tenureInDaysForDeposit,tenureInDaysForWithdrawSlab) )
						withdrawPenaltyDetails =  withdrawPenaltyDetailList.get(i);
				}	
			 break;  
			case ">":    
				if(deposit.getDepositAmount() > withdrawPenaltyDetailList.get(i).getAmount())
				{
					// Check the tenure is matching the criteria
					if(this.CheckTenureForWithdrawPenaltyDetails(withdrawPenaltyDetailList.get(i),tenureInDaysForDeposit,tenureInDaysForWithdrawSlab) )
						withdrawPenaltyDetails =  withdrawPenaltyDetailList.get(i);
				}	
				 break;      
			case ">=":    
				if(deposit.getDepositAmount() >= withdrawPenaltyDetailList.get(i).getAmount())
				{
					// Check the tenure is matching the criteria
					if(this.CheckTenureForWithdrawPenaltyDetails(withdrawPenaltyDetailList.get(i),tenureInDaysForDeposit,tenureInDaysForWithdrawSlab) )
						withdrawPenaltyDetails =  withdrawPenaltyDetailList.get(i);
				}	
				 break;   
			
			default:     			
				if(deposit.getDepositAmount() == withdrawPenaltyDetailList.get(i).getAmount())
				{
					// Check the tenure is matching the criteria
					if(this.CheckTenureForWithdrawPenaltyDetails(withdrawPenaltyDetailList.get(i),tenureInDaysForDeposit,tenureInDaysForWithdrawSlab) )
						withdrawPenaltyDetails =  withdrawPenaltyDetailList.get(i);
				}	
			}    
			
			if(withdrawPenaltyDetails!=null)
				break;
		}
		return withdrawPenaltyDetails;
	}
	
	@SuppressWarnings("unused")
	private Boolean CheckTenureForWithdrawPenaltyDetails(WithdrawPenaltyDetails withdrawPenaltyDetails, Integer tenureInDaysForDeposit,
			Integer tenureInDaysForWithdrawSlab)
	{
		Boolean isMatched = false;
		
		// Check the tenure is matching the criteria
		if(withdrawPenaltyDetails.getTenureSign().equals("<"))
		{
			if(tenureInDaysForDeposit < tenureInDaysForWithdrawSlab)
			{
				isMatched = true;
			}
		}
		else if(withdrawPenaltyDetails.getTenureSign().equals("<="))
		{
			if(tenureInDaysForDeposit <= tenureInDaysForWithdrawSlab)
			{
				isMatched = true;
			}
		}
		else if(withdrawPenaltyDetails.getTenureSign().equals(">"))
		{
			if(tenureInDaysForDeposit > tenureInDaysForWithdrawSlab)
			{
				isMatched = true;
			}
		}
		else if(withdrawPenaltyDetails.getTenureSign().equals(">="))
		{
			if(tenureInDaysForDeposit >= tenureInDaysForWithdrawSlab)
			{
				isMatched = true;
			}
		}
		else if(withdrawPenaltyDetails.getTenureSign().equals("="))
		{
			if(tenureInDaysForDeposit == tenureInDaysForWithdrawSlab)
			{
				isMatched = true;
			}
		}
		
		return isMatched;
	}
	public Long getPrimaryCustomerId(List<DepositHolder> depositHolderList) {
		Long customerId = 0l;
		for (int i = 0; i < depositHolderList.size(); i++) {
			if (depositHolderList.get(i).getDepositHolderStatus().equalsIgnoreCase("PRIMARY"))
				customerId = depositHolderList.get(i).getCustomerId();
		}

		return customerId;
	}
	
	public String generateRandomString() {

		String uuid = RandomStringUtils.randomAlphanumeric(16).toUpperCase();
		return uuid;
	}

}
