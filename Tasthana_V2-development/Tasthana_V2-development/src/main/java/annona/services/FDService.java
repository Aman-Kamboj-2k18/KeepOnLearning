package annona.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import annona.dao.AccountDetailsDAO;
import annona.dao.BankPaymentDAO;
import annona.dao.BenificiaryDetailsDAO;
import annona.dao.CustomerDAO;
import annona.dao.DTAACountryRatesDAO;
import annona.dao.DepositHolderDAO;
import annona.dao.DepositHolderWiseDistributionDAO;
import annona.dao.DepositModificationDAO;
import annona.dao.DepositRateDAO;
import annona.dao.DepositRatesDAO;
import annona.dao.DepositSummaryDAO;
import annona.dao.EndUserDAO;
import annona.dao.FDRatesDAO;
import annona.dao.FixedDepositDAO;
import annona.dao.FormSubmissionDAO;
import annona.dao.FundTransferDAO;
import annona.dao.GstDAO;
import annona.dao.InterestDAO;
import annona.dao.ModeOfPaymentDAO;
import annona.dao.PaymentDAO;
import annona.dao.PaymentDistributionDAO;
import annona.dao.ProductConfigurationDAO;
import annona.dao.RatesDAO;
import annona.dao.ReverseEMIDAO;
import annona.dao.SweepConfigurationDAO;
import annona.dao.TDSDAO;
import annona.dao.TaxExemptionConfigurationDAO;
import annona.dao.TransactionDAO;
import annona.dao.WithdrawDepositDAO;
import annona.dao.WithdrawPenaltyDAO;
import annona.domain.AccountDetails;
import annona.domain.BenificiaryDetails;
import annona.domain.CurrencyConfiguration;
import annona.domain.Customer;
import annona.domain.Deposit;
import annona.domain.DepositHolder;
import annona.domain.DepositModification;
import annona.domain.DepositSummary;
import annona.domain.DepositSummaryHolderWise;
import annona.domain.DepositTDS;
import annona.domain.DepositWiseCustomerTDS;
import annona.domain.Distribution;
import annona.domain.EndUser;
import annona.domain.FDRates;
import annona.domain.Interest;
import annona.domain.ModeOfPayment;
import annona.domain.ModificationPenalty;
import annona.domain.Payment;
import annona.domain.ProductConfiguration;
import annona.domain.Rates;
import annona.domain.ReverseEMI;
import annona.domain.ReverseEMIPayoffDetails;
import annona.domain.SweepConfiguration;
import annona.domain.SweepInFacilityForCustomer;
import annona.domain.TDS;
import annona.domain.TaxExemptionConfiguration;
import annona.domain.Transaction;
import annona.domain.WithdrawDeposit;
import annona.domain.WithdrawPenaltyDetails;
import annona.exception.CustomException;
import annona.form.DepositHolderForm;
import annona.form.FixedDepositForm;
import annona.form.FundTransferForm;
import annona.form.WithdrawForm;
import annona.utility.Constants;
import annona.utility.Event;

/**
 * 
 *
 *
 */

// enum CustomerCategoty {
// General, Disabled, SeniorCitizen, NGO, CharitableOrganization
// }

@Service
public class FDService {

	@Autowired
	ApplicationContext context;

	@Autowired
	RatesDAO ratesDAO;

	@Autowired
	MailSender mailSender;

	@Autowired
	FDRatesDAO fdRatesDAO;

	@Autowired
	FundTransferDAO fundTransferDAO;
	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	TransactionDAO transactionDAO;

	@Autowired
	EndUserDAO endUserDAO;

	@Autowired
	DepositRatesDAO depositRatesDAO;

	@Autowired
	FixedDepositDAO fixedDepositDao;

	@Autowired
	DepositModificationDAO depositModificationDAO;

	@Autowired
	DepositHolderDAO depositHolderDAO;

	@Autowired
	AccountDetailsDAO accountDetailsDAO;

	@Autowired
	PaymentDAO paymentDAO;

	@Autowired
	WithdrawDepositDAO withdrawDepositDAO;

	@Autowired
	GstDAO gstDAO;

	@Autowired
	InterestDAO interestDAO;

	@Autowired
	TDSDAO tdsDAO;

	@Autowired
	FixedDepositForm fixedDepositForm;

	@Autowired
	FormSubmissionDAO formSubmissionDAO;

	@Autowired
	PaymentDistributionDAO paymentDistributionDAO;

	@Autowired
	DepositSummaryDAO interestSummaryDAO;

	@Autowired
	DepositHolderWiseDistributionDAO depositHolderWiseDistributionDAO;

	@Autowired
	ReverseEMIDAO reverseEMIDAO;

	@Autowired
	DepositRateDAO depositRateDAO;

	@Autowired
	BenificiaryDetailsDAO beneficiaryDetailsDAO;

	@Autowired
	BankPaymentDAO bankPaymentDAO;

	//@Autowired
	//DepositHolderWiseConsolidatedInterestDAO depositHolderWiseConsolidatedInterestDAO;

	@Autowired
	DepositSummaryDAO depositSummaryDAO;
	
	@Autowired
	CalculationService calculationService;
	
	@Autowired
	LedgerService ledgerService;
	
	@Autowired
	DTAACountryRatesDAO dtaaCountryRateDAO;
	
	@Autowired
	SweepConfigurationDAO sweepConfigurationDAO;	
	
	@Autowired
	ProductConfigurationDAO productConfigurationDAO;
	
	@Autowired
	WithdrawPenaltyDAO withdrawPenaltyDAO;
	
	@Autowired
	ModeOfPaymentDAO modeOfPaymentDAO;
	
	@Autowired
	TaxExemptionConfigurationDAO taxExemptionConfigurationDAO;
	
	protected static Logger logger = Logger.getLogger("service");

	public FDService() {

	}


//	public float getInterestRates(String category, Double FDAmount, Date fdDeductDate, Date fdTenureDate) {
//		Date fdDeductDate1 = DateService.getDate(fdDeductDate);
//		Date fdTenureDate1 = DateService.getDate(fdTenureDate);
//		float estimateRate = 0;
//		long days = DateService.getDaysBetweenTwoDates(fdDeductDate1, fdTenureDate1);
//
//		Rates rates = ratesDAO.findByCategory(category).getSingleResult();
//
//		if (rates != null) {
//			estimateRate = (float) ((FDAmount * rates.getRate() / 100) / 365) * days;
//		}
//
//		return estimateRate;
//
//	}

	/**
	 * Method to get TDS for FD Amount Interest
	 * 
	 * @param category
	 * @param interest
	 * @return
	 */
	public float getTDS(String category, float interest) {
		float tdsAmount = 0;
		Rates rates = ratesDAO.findByCategory(category).getSingleResult();

		if (rates != null) {
			tdsAmount = interest * rates.getTds() / 100;
		}

		return tdsAmount;

	}


	/**
	 * Method to get Monthly Amount
	 * 
	 * @param fdTenureType
	 * @param fdTenure
	 * @param fdAmount
	 * @return
	 */
	public static Double getForMonthlyAmount(String fdTenureType, int fdTenure, Double fdAmount) {
		Double totalAmount;
		Float month;
		if (fdTenureType.equals(Constants.DAY)) {

			month = (float) Math.floor(fdTenure / 30);
			totalAmount = month * fdAmount;

		} else if (fdTenureType.equals(Constants.MONTH)) {

			totalAmount = fdTenure * fdAmount;

		} else {
			totalAmount = (fdTenure * 12 * fdAmount);

		}
		return totalAmount;
	}

	/**
	 * Method to get Quarterly Amount
	 * 
	 * @param fdTenureType
	 * @param fdTenure
	 * @param fdAmount
	 * @return
	 */
	public static Double getForQuarterlyAmount(String fdTenureType, int fdTenure, Double fdAmount) {
		Double totalAmount;
		Float month;
		if (fdTenureType.equals(Constants.DAY)) {

			month = (float) Math.floor(fdTenure / 90);
			totalAmount = month * fdAmount;

		} else if (fdTenureType.equals(Constants.MONTH)) {
			month = (float) Math.floor(fdTenure / 3);
			totalAmount = month * fdAmount;

		} else {
			totalAmount = (fdTenure * 4 * fdAmount);

		}
		return totalAmount;
	}

	/**
	 * Method to get Half Yearly Amount
	 * 
	 * @param fdTenureType
	 * @param fdTenure
	 * @param fdAmount
	 * @return
	 */
	public static Double getForHalfYearlyAmount(String fdTenureType, int fdTenure, Double fdAmount) {
		Double totalAmount;
		Float month;
		if (fdTenureType.equals(Constants.DAY)) {

			month = (float) Math.floor(fdTenure / 180);
			totalAmount = month * fdAmount;

		} else if (fdTenureType.equals(Constants.MONTH)) {
			month = (float) Math.floor(fdTenure / 6);
			totalAmount = month * fdAmount;

		} else {
			totalAmount = (fdTenure * 2 * fdAmount);

		}
		return totalAmount;
	}

	/**
	 * Method to get Yearly Amount
	 * 
	 * @param fdTenureType
	 * @param fdTenure
	 * @param fdAmount
	 * @return
	 */
	public static Double getForYearlyAmount(String fdTenureType, int fdTenure, Double fdAmount) {
		Double totalAmount;
		Float month;
		if (fdTenureType.equals(Constants.DAY)) {

			month = (float) Math.floor(fdTenure / 360);
			totalAmount = month * fdAmount;

		} else if (fdTenureType.equals(Constants.MONTH)) {
			month = (float) Math.floor(fdTenure / 12);
			totalAmount = month * fdAmount;

		} else {
			totalAmount = (fdTenure * 1 * fdAmount);

		}
		return totalAmount;
	}

	/**
	 * Method for getting total amount
	 * 
	 * @param fixedDepositForm
	 * @return
	 */
	public Double getChangeFDAmount(FixedDepositForm fixedDepositForm) {
		Double totalFDAmount;

		if (fixedDepositForm.getChangePaymentType().equals(Constants.ONCE)) {
			if (fixedDepositForm.getChangeFDAmount() == 0.0) {
				totalFDAmount = fixedDepositForm.getFdAmount();
			} else {
				totalFDAmount = fixedDepositForm.getChangeFDAmount() + fixedDepositForm.getFdFixed();
			}
		} else if (fixedDepositForm.getChangePaymentType().equals(Constants.QUARTER)) {

			totalFDAmount = getQuarterTotalAmount(fixedDepositForm);

		} else if (fixedDepositForm.getChangePaymentType().equals(Constants.HALFYEAR)) {

			totalFDAmount = getHalfYearTotalAmount(fixedDepositForm);

		} else if (fixedDepositForm.getChangePaymentType().equals(Constants.MONTHLY)) {
			totalFDAmount = getMonthTotalAmount(fixedDepositForm);

		} else {
			if (fixedDepositForm.getPaymentType().equals(Constants.ONCE)) {
				if (fixedDepositForm.getChangeFDAmount() == 0.0) {
					totalFDAmount = fixedDepositForm.getFdAmount();
				} else {
					totalFDAmount = fixedDepositForm.getChangeFDAmount() + fixedDepositForm.getFdFixed();
				}
			} else if (fixedDepositForm.getPaymentType().equals(Constants.QUARTER)) {

				totalFDAmount = getQuarterTotalAmount(fixedDepositForm);

			} else if (fixedDepositForm.getPaymentType().equals(Constants.HALFYEAR)) {
				totalFDAmount = getHalfYearTotalAmount(fixedDepositForm);
			} else {
				totalFDAmount = getMonthTotalAmount(fixedDepositForm);
			}
		}

		return totalFDAmount;

	}

	/**
	 * Method for get Quarter Amount
	 * 
	 * @param fixedDepositForm
	 * @return
	 */
	public static Double getQuarterTotalAmount(FixedDepositForm fixedDepositForm) {
		Double totalFDAmount;
		if (fixedDepositForm.getChangeFDTenureType().equals(Constants.EMPTY)) {
			if (fixedDepositForm.getChangeFDAmount() == 0.0) {
				totalFDAmount = FDService.getForQuarterlyAmount(fixedDepositForm.getFdTenureType(),
						fixedDepositForm.getFdTenure(), fixedDepositForm.getFdAmount());
			} else {
				totalFDAmount = FDService.getForQuarterlyAmount(fixedDepositForm.getFdTenureType(),
						fixedDepositForm.getFdTenure(),
						fixedDepositForm.getChangeFDAmount() + fixedDepositForm.getFdFixed());

			}
		} else {
			if (fixedDepositForm.getChangeFDAmount() == 0.0) {
				totalFDAmount = FDService.getForQuarterlyAmount(fixedDepositForm.getChangeFDTenureType(),
						fixedDepositForm.getChangeFDTenure(), fixedDepositForm.getFdAmount());
			} else {
				totalFDAmount = FDService.getForQuarterlyAmount(fixedDepositForm.getChangeFDTenureType(),
						fixedDepositForm.getChangeFDTenure(),
						fixedDepositForm.getChangeFDAmount() + fixedDepositForm.getFdFixed());

			}

		}
		return totalFDAmount;

	}

	/**
	 * Method to get half year amount
	 * 
	 * @param fixedDepositForm
	 * @return
	 */
	public static Double getHalfYearTotalAmount(FixedDepositForm fixedDepositForm) {
		Double totalFDAmount;
		if (fixedDepositForm.getChangeFDTenureType().equals(Constants.EMPTY)) {
			if (fixedDepositForm.getChangeFDAmount() == 0.0) {
				totalFDAmount = FDService.getForHalfYearlyAmount(fixedDepositForm.getFdTenureType(),
						fixedDepositForm.getFdTenure(), fixedDepositForm.getFdAmount());
			} else {
				totalFDAmount = FDService.getForHalfYearlyAmount(fixedDepositForm.getFdTenureType(),
						fixedDepositForm.getFdTenure(),
						fixedDepositForm.getChangeFDAmount() + fixedDepositForm.getFdFixed());

			}
		} else {
			if (fixedDepositForm.getChangeFDAmount() == 0.0) {
				totalFDAmount = FDService.getForHalfYearlyAmount(fixedDepositForm.getChangeFDTenureType(),
						fixedDepositForm.getChangeFDTenure(), fixedDepositForm.getFdAmount());
			} else {
				totalFDAmount = FDService.getForHalfYearlyAmount(fixedDepositForm.getChangeFDTenureType(),
						fixedDepositForm.getChangeFDTenure(),
						fixedDepositForm.getChangeFDAmount() + fixedDepositForm.getFdFixed());

			}

		}
		return totalFDAmount;

	}

	/**
	 * Method to get month amount
	 * 
	 * @param fixedDepositForm
	 * @return
	 */
	public static Double getMonthTotalAmount(FixedDepositForm fixedDepositForm) {
		Double totalFDAmount;
		if (fixedDepositForm.getChangeFDTenureType().equals(Constants.EMPTY)) {
			if (fixedDepositForm.getChangeFDAmount() == 0.0) {
				totalFDAmount = FDService.getForMonthlyAmount(fixedDepositForm.getFdTenureType(),
						fixedDepositForm.getFdTenure(), fixedDepositForm.getFdAmount());
			} else {
				totalFDAmount = FDService.getForMonthlyAmount(fixedDepositForm.getFdTenureType(),
						fixedDepositForm.getFdTenure(),
						fixedDepositForm.getChangeFDAmount() + fixedDepositForm.getFdFixed());
			}
		} else {
			if (fixedDepositForm.getChangeFDAmount() == 0.0) {
				totalFDAmount = FDService.getForMonthlyAmount(fixedDepositForm.getChangeFDTenureType(),
						fixedDepositForm.getChangeFDTenure(), fixedDepositForm.getFdAmount());
			} else {
				totalFDAmount = FDService.getForMonthlyAmount(fixedDepositForm.getChangeFDTenureType(),
						fixedDepositForm.getChangeFDTenure(),
						fixedDepositForm.getChangeFDAmount() + fixedDepositForm.getFdFixed());
			}

		}
		return totalFDAmount;
	}

	/**
	 * Method to get Fd tenure date
	 * 
	 * @param fixedDepositForm
	 * @return
	 * @throws ParseException
	 */
	public Date getChangeFDTenureDate(FixedDepositForm fixedDepositForm) throws ParseException {
		Date fdTenureDate;

		if (fixedDepositForm.getChangeFDTenureType().equals(Constants.EMPTY)) {
			fdTenureDate = getFdTenureDate(fixedDepositForm);
		} else {
			fdTenureDate = getFdChangedTenureDate(fixedDepositForm);
		}

		return fdTenureDate;
	}

	/**
	 * Method to get FD tenure date
	 * 
	 * @param fixedDepositForm
	 * @return
	 */
	public static Date getFdChangedTenureDate(FixedDepositForm fixedDepositForm) {
		Date fdTenureDate;
		if (fixedDepositForm.getChangeFDTenureType().equals(Constants.DAY)) {

			fdTenureDate = DateService.generateDaysDate(fixedDepositForm.getFdDueDate(),
					fixedDepositForm.getChangeFDTenure());

		} else if (fixedDepositForm.getChangeFDTenureType().equals(Constants.MONTH)) {

			fdTenureDate = DateService.generateMonthsDate(fixedDepositForm.getFdDueDate(),
					fixedDepositForm.getChangeFDTenure());

		} else {

			fdTenureDate = DateService.generateYearsDate(fixedDepositForm.getFdDueDate(),
					fixedDepositForm.getChangeFDTenure());

		}
		return fdTenureDate;
	}

	/**
	 * Method to get fd tenure date
	 * 
	 * @param fixedDepositForm
	 * @return
	 * @throws ParseException
	 */
	public static Date getFdTenureDate(FixedDepositForm fixedDepositForm) throws ParseException {
		Date fdTenureDate = null;

		if (fixedDepositForm.getChangeFDDeductDate() != null) {
			Date tempDate = fixedDepositForm.getFdTenureDate();
			Date newDate = fixedDepositForm.getChangeFDDeductDate();
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String tempDate1 = format.format(tempDate);
			String[] parts = tempDate1.split("/");
			String tempDate2 = format.format(newDate);
			String[] parts1 = tempDate2.split("/");

			fdTenureDate = format.parse(parts1[0] + "/" + parts[1] + "/" + parts[2]);
		} else {
			fdTenureDate = fixedDepositForm.getFdTenureDate();
		}

		return fdTenureDate;

	}

	/**
	 * Method for getting Tenure Date
	 * 
	 * @param fixedDepositForm
	 * @return
	 */
	public static Date generateTenure(FixedDepositForm fixedDepositForm,Integer taxSavingDeposit) {
//		Date fdTenureDate = DateService.getCurrentDateTime();
//		if(taxSavingDeposit.equals(1)){
//			
//        	fixedDepositForm.setPaymentType("One-Time");
//        	fixedDepositForm.setFdTenure(5);
//        	fixedDepositForm.setFdTenureType(Constants.YEAR);
//        }
//		if (fixedDepositForm.getFdTenureType().equals(Constants.DAY)) {
//			fdTenureDate = DateService.generateDaysDate(fixedDepositForm.getFdDeductDate(),
//					fixedDepositForm.getFdTenure());
//		} else if (fixedDepositForm.getFdTenureType().equals(Constants.MONTH)) {
//			fdTenureDate = DateService.generateMonthsDate(fixedDepositForm.getFdDeductDate(),
//					fixedDepositForm.getFdTenure());
//
//		} else {
//			fdTenureDate = DateService.generateYearsDate(fixedDepositForm.getFdDeductDate(),
//					fixedDepositForm.getFdTenure());
//		}

		
		return DateService.addDays( DateService.getCurrentDate(), fixedDepositForm.getTotalTenureInDays());	
		
	}

//	/**
//	 * Method for getting total FD
//	 * 
//	 * @param fixedDepositForm
//	 * @return
//	 */
//	public static Double generateFDAmount(FixedDepositForm fixedDepositForm) {
//		Double totalFDAmount = 0d;
//		if (fixedDepositForm.getPaymentType().equals(Constants.ONCE)) {
//			totalFDAmount = fixedDepositForm.getFdAmount();
//		} else if (fixedDepositForm.getPaymentType().equals(Constants.QUARTER)) {
//
//			totalFDAmount = FDService.getForQuarterlyAmount(fixedDepositForm.getFdTenureType(),
//					fixedDepositForm.getFdTenure(), fixedDepositForm.getFdAmount());
//
//		} else if (fixedDepositForm.getPaymentType().equals(Constants.HALFYEAR)) {
//			totalFDAmount = FDService.getForHalfYearlyAmount(fixedDepositForm.getFdTenureType(),
//					fixedDepositForm.getFdTenure(), fixedDepositForm.getFdAmount());
//
//		} else if (fixedDepositForm.getPaymentType().equals(Constants.MONTHLY)) {
//			totalFDAmount = FDService.getForMonthlyAmount(fixedDepositForm.getFdTenureType(),
//					fixedDepositForm.getFdTenure(), fixedDepositForm.getFdAmount());
//
//		} else {
//			totalFDAmount = FDService.getForYearlyAmount(fixedDepositForm.getFdTenureType(),
//					fixedDepositForm.getFdTenure(), fixedDepositForm.getFdAmount());
//
//		}
//
//		return totalFDAmount;
//	}

	/**
	 * Method for getting calculations
	 * 
	 * @param fixedDepositForm
	 * @return
	 */
	public FixedDepositForm saveRates(FixedDepositForm fixedDepositForm ) {
		Rates rate = null;
		List<Rates> rates = ratesDAO.findByCategory(fixedDepositForm.getCategory()).getResultList();
		if (rates != null && rates.size() > 0) {
			rate = rates.get(0);
			Double fdAmount = fixedDepositForm.getFdAmount();
			Double fdFixed = fdAmount * rates.get(0).getFdFixedPercent() / 100;
			Double fdChangeable = fdAmount - fdFixed;
			fixedDepositForm.setFdFixed(fdFixed);
			fixedDepositForm.setFdChangeable(fdChangeable);
			fixedDepositForm.setFdCreditAmount(rate.getRate());
		}
		return fixedDepositForm;
	}

	/**
	 * Method for reports
	 * 
	 * @param fixedDepositForm
	 */
	public void forReports(FixedDepositForm fixedDepositForm) {
		Date date = DateService.getCurrentDate();
		Transaction transaction = new Transaction();
		Rates rate = null;
		List<Rates> rates = ratesDAO.findByCategory(fixedDepositForm.getCategory()).getResultList();
		if (rates != null && rates.size() >= 0) {
			rate = rates.get(0);
		}
		transaction.setRateOfInterest(rate.getRate());
		transaction.setTransactionId(fixedDepositForm.getFdID());
		transaction.setTransactionType(Constants.FIXEDDEPOSIT);
		transaction.setTransactionStatus(Constants.FDSTATUS);
		transaction.setCustomerID(fixedDepositForm.getCustomerID());
		transaction.setFdAmount(fixedDepositForm.getFdAmount());
		transaction.setFdFixed(fixedDepositForm.getFdFixed());
		transaction.setFdChangeable(fixedDepositForm.getFdChangeable());
		transaction.setAccountBalance(fixedDepositForm.getAccountBalance());
		transaction.setAccountNo(fixedDepositForm.getAccountNo());
		//transaction.setFdInterest(fixedDepositForm.getEstimateInterest());
		transaction.setAccountDate(date);

		transaction.setFdDeductDate(fixedDepositForm.getFdDeductDate());
		transaction.setFdTenureDate(fixedDepositForm.getFdTenureDate());
		transaction.setDepositType(fixedDepositForm.getDepositType());
		transactionDAO.insertTransaction(transaction);
	}

	/**
	 * Method for getting Current Deposit Values
	 * 
	 * @param fixedDeposit
	 * @return
	 */
	/*
	 * public FixedDepositForm gettingCurrentDepositData(FixedDeposit
	 * fixedDeposit) { FixedDepositForm fixedDepositForm = new
	 * FixedDepositForm(); float fdInterestAmount = 0; Double fdCurrentAmount =
	 * 0d;
	 * 
	 * if
	 * (fixedDeposit.getFdDeductDate().compareTo(DateService.getCurrentDate())
	 * <= 0) { fixedDepositForm.setFdID(fixedDeposit.getFdID());
	 * fixedDepositForm.setCustomerID(fixedDeposit.getCustomerID());
	 * 
	 * FixedDepositForm fixedDepositForm2 = getData(fixedDepositForm); //
	 * Getting // Data
	 * 
	 * fdCurrentAmount = (fixedDepositForm2.getFdRateAmount() +
	 * fixedDepositForm2.getFundTransferAmount() +
	 * fixedDepositForm2.getCashAmount() + fixedDepositForm2.getDdAmount() +
	 * fixedDepositForm2.getChequeAmount() +
	 * fixedDepositForm2.getCardTransferAmount()); float interestAmount =
	 * (float) (fixedDepositForm2.getFdRateInterestAmount() + fdInterestAmount +
	 * fixedDepositForm2.getDdInterestAmount() +
	 * fixedDepositForm2.getCashInterestAmount() +
	 * fixedDepositForm2.getChequeInterestAmount() +
	 * fixedDepositForm2.getCardTransferInterestAmount() -
	 * fixedDepositForm2.getPayOffWholeInterest() -
	 * fixedDepositForm2.getPayOffPARTInterest()); fdInterestAmount = (float)
	 * (Math.round(interestAmount * 100.0) / 100.0);
	 * 
	 * } fixedDepositForm.setFdInterest(fdInterestAmount);
	 * fixedDepositForm.setFdCurrentAmount(fdCurrentAmount); return
	 * fixedDepositForm;
	 * 
	 * }
	 */

	/**
	 * Method for Changed FD Data
	 * 
	 * @param fixedDepositForm
	 * @return
	 * @throws ParseException
	 */
	public FixedDepositForm gettingEditFDData(FixedDepositForm fixedDepositForm) throws ParseException {
		Date fdTenureDate;
		Double totalFDAmount, PayOffAmount;
		float Interest, TDSDeduct;

		fdTenureDate = getChangeFDTenureDate(fixedDepositForm); // for getting
																// tenure date

		totalFDAmount = getChangeFDAmount(fixedDepositForm); // for getting
																// total amount
		if (fixedDepositForm.getChangeFDPayType() != null) {
			fixedDepositForm.setFdPayType(fixedDepositForm.getChangeFDPayType());
		} else {
			fixedDepositForm.setFdPayType(fixedDepositForm.getFdPayType());
		}
		if (fixedDepositForm.getChangeFDPay() != null) {
			fixedDepositForm.setFdPay(fixedDepositForm.getChangeFDPay());
		} else {
			fixedDepositForm.setFdPay(fixedDepositForm.getFdPay());
		}
		//float estimateInterest =  getInterestRates(fixedDepositForm.getCategory(), totalFDAmount,fixedDepositForm.getFdDueDate(), fdTenureDate);

		Integer tenure = DateService.getDaysBetweenTwoDates(fixedDepositForm.getFdDueDate(), fdTenureDate);
		
		float estimateInterest =  calculationService.getDepositInterestRate(tenure, fixedDepositForm.getCategory(), fixedDepositForm.getCurrency(), totalFDAmount, fixedDepositForm.getDepositClassification(), fixedDepositForm.getCitizen(), fixedDepositForm.getNriAccountType());
		Interest = (float) (Math.round(estimateInterest * 100.0) / 100.0);

		if (!fixedDepositForm.getChangePaymentType().equals("")) {
			fixedDepositForm.setPaymentType(fixedDepositForm.getChangePaymentType());
		} else {
			fixedDepositForm.setPaymentType(fixedDepositForm.getPaymentType());

		}
		if (fixedDepositForm.getPaymentType().equals(fixedDepositForm.getChangePaymentType())) {
			Collection<FDRates> fdRates = fdRatesDAO.getFDRatesData(fixedDepositForm.getFdID());
			if (fdRates != null && fdRates.size() > 0) {
				Date dueDate = fdRatesDAO.getMaxTenuredate(fixedDepositForm.getCustomerID(),
						fixedDepositForm.getFdID());
				fixedDepositForm.setFdDueDate(dueDate);
			} else {
				fixedDepositForm.setFdDueDate(fixedDepositForm.getFdDueDate());
			}
		} else {
			fixedDepositForm.setFdDueDate(fixedDepositForm.getFdDueDate());
		}
		if (fixedDepositForm.getChangeFDAmount() != 0.0) {
			fixedDepositForm.setFdAmount(fixedDepositForm.getChangeFDAmount() + fixedDepositForm.getFdFixed());
			fixedDepositForm.setFdChangeable(fixedDepositForm.getChangeFDAmount());
		} else {
			fixedDepositForm.setFdAmount(fixedDepositForm.getFdAmount());
			fixedDepositForm.setFdChangeable(fixedDepositForm.getFdChangeable());
		}
		if (!fixedDepositForm.getChangeFDTenureType().equals("")) {
			fixedDepositForm.setFdTenureType(fixedDepositForm.getChangeFDTenureType());
			fixedDepositForm.setFdTenure(fixedDepositForm.getChangeFDTenure());
		} else {
			fixedDepositForm.setFdTenureType(fixedDepositForm.getFdTenureType());
			fixedDepositForm.setFdTenure(fixedDepositForm.getFdTenure());
		}
		TDSDeduct = getTDS(fixedDepositForm.getCategory(), Interest);

		Double estimatePayOffAmount = fixedDepositForm.getFdCurrentAmount() + fixedDepositForm.getFdInterest()
				+ totalFDAmount + Interest - TDSDeduct;// - SESDeduct -
														// ServiceTax;
		PayOffAmount = (Math.round(estimatePayOffAmount * 100.0) / 100.0);

		fixedDepositForm.setEstimateInterest(Interest);
		fixedDepositForm.setEstimateTDSDeduct(TDSDeduct);

		fixedDepositForm.setEstimatePayOffAmount(PayOffAmount);
		fixedDepositForm.setFdTenureDate(fdTenureDate);

		return fixedDepositForm;
	}

	/**
	 * Method for fund transfer reports
	 * 
	 * @param fundTransferForm
	 * @param balance
	 */
	public void fundTransferReports(FundTransferForm fundTransferForm, Double balance) {
		Date date = DateService.getCurrentDate();
		Transaction transaction = new Transaction();
		Rates rate = null;
		List<Rates> rates = ratesDAO.findByCategory(fundTransferForm.getCategory()).getResultList();
		if (rates != null && rates.size() >= 0) {
			rate = rates.get(0);
		}
		transaction.setRateOfInterest(rate.getRate());
		transaction.setTransactionId(fundTransferForm.getFdId());
		transaction.setTransactionType(Constants.TRANSFERFD);
		transaction.setTransactionStatus(Constants.CASHFDSTATUS);
		transaction.setCustomerID(fundTransferForm.getCustomerID());
		transaction.setDebited(fundTransferForm.getFdAmount());
		transaction.setAccountDate(date);
		transaction.setFdAmount(fundTransferForm.getFdAmount());
		transaction.setAccountBalance(balance);
		transaction.setAccountNo(fundTransferForm.getAccountNo());
		transaction.setFdTenureDate(fundTransferForm.getFdTenureDate());

		transactionDAO.insertTransaction(transaction);

	}

//	//public float getdepositintrate(integer days, string category, string currency) {
//	public float getdepositintrate(integer days, string category, string currency, double depositamount, string depositclassification) {
//		depositrates depositerate = depositratesdao.getratebymaturityperiodandcategory(days, category, currency, depositamount, depositclassification);
//		if (depositerate != null) {
//			return depositerate.getinterestrate();
//		} else {
//			return 0f;
//		}
//
//	}
//	
//	public Float getDepositInterestRateForSlab(Integer days, String category, String currency, Double fromSlab, Double toSlab, 
//			String depositClassification, String citizen, String nriAccountType) {
//		DepositRates depositeRate = depositRatesDAO.getRateByMaturityPeriodAndCategory(days, category, currency, fromSlab, toSlab, depositClassification);
//		if (depositeRate != null) {
//			return depositeRate.getInterestRate();
//		} else {
//			return null;
//		}
//
//	}

	public Float getDepositIntRate(Long depositId) {
		Float rate = 0f;
		if (depositId == null || depositId == 0)
			return rate;

		List<Object[]> deposits = fixedDepositDao.getDepositForInterestRate(depositId);// fixedDepositDao.getDeposit(depositId);
		if (deposits.size() > 0) {
			Object[] deposit = deposits.get(0);
			rate = Float.parseFloat(deposit[4].toString());

			// List<Object[]> depositModifications =
			// depositModificationDAO.getDepositModification(depositId);
			// if (depositModifications.size() > 0) {
			// Object[] depositModification = depositModifications.get(0);
			// if (depositModification[4] != null)
			// rate = Float.parseFloat(depositModification[4].toString());
			// }

		}
		return rate;
	}

//	public Float getTDSRate(String category, Long customerId) {
//
//		Customer customer = customerDAO.getById(customerId);
//
//		Boolean isMinor = this.isCustomerMinor(customer);
//		if ((!isMinor && customer.getPan() == null) || (isMinor && customer.getGuardianPan() == null)) {
//			BankConfiguration bankConfig = ratesDAO.getBankConfiguration(category);
//			if (bankConfig == null) {
//				return 0f;
//			} else {
//				return bankConfig.getTdsPercentForNoPAN();
//			}
//		} else {
//			List<Rates> rates = ratesDAO.findByCategory(category).getResultList();
//
//			if (rates.size() > 0) {
//				return rates.get(0).getTds();
//
//			} else {
//				return null;
//			}
//		}
//	}

	public Boolean isCustomerMinor(Customer customer) {
		int age = (DateService.getDaysBetweenTwoDates(customer.getDateOfBirth(), DateService.getCurrentDate())) / 365;
		if (age < 18)
			return true;
		else
			return false;
	}

	public int generateTenureInDays(Date depositDate, String tenureType, int tenure) {
		Date fdTenureDate = DateService.getCurrentDateTime();
		if (tenureType.equals(Constants.DAY)) {
			fdTenureDate = DateService.generateDaysDate(depositDate, tenure);
		} else if (tenureType.equals(Constants.MONTH)) {
			fdTenureDate = DateService.generateMonthsDate(depositDate, tenure);
		} else {
			fdTenureDate = DateService.generateYearsDate(depositDate, tenure);
		}
		return DateService.getDaysBetweenTwoDates(depositDate, fdTenureDate);
	}

	private DepositSummary insertInInterestSummary(Long depositId, Double interestAmount, Double fixedInterestAmount,
			Double variableInterestAmount, Double payOffAmount) {
		DepositSummary interestSummary = new DepositSummary();
//		interestSummary.setDepositId(depositId);
//		interestSummary.setInterestDate(DateService.getCurrentDate());
//		interestSummary.setInterestAmount(round(interestAmount, 2));
//		interestSummary.setFixedInterest(round(fixedInterestAmount, 2));
//		interestSummary.setVariableInterest(round(variableInterestAmount, 2));
//		interestSummary.setCurrentInterestBalance(
//				round(interestSummaryDAO.getCurrentInterestBalance(depositId) + interestAmount - payOffAmount, 2));
//		interestSummary.setPayOffAmount(round(payOffAmount, 2));
//		interestSummary = interestSummaryDAO.insert(interestSummary);
//
		return interestSummary;
	}

	private void consolidateInterest(Long depositId, List<DepositHolder> depositHolderList, Double interestAmount,
			Double fixedInterestAmount, Double variableInterestAmount) {
//		// Insert/update in ConsolidatedInterest table
//		// ------------------------------------------------
//		ConsolidatedInterest consolidatedInterest = interestSummaryDAO.getConsolidatedInterest(depositId);
//
//		Double interestBalance = 0d;
//		Double totalPayoff = 0d;
//		Double totalInterest = 0d;
//		Double totalFixedInterest = 0d;
//		Double totalVariableInterest = 0d;
//		Double balanceFixedInterest = 0d;
//		Double balanceVariableInterest = 0d;
//		interestAmount = round(interestAmount, 2);
//		fixedInterestAmount = round(fixedInterestAmount, 2);
//		variableInterestAmount = round(variableInterestAmount, 2);
//
//		if (consolidatedInterest == null) {
//			consolidatedInterest = new ConsolidatedInterest();
//
//			consolidatedInterest.setDepositId(depositId);
//			consolidatedInterest.setTotalInterest(round(totalInterest + interestAmount, 2));
//			consolidatedInterest.setTotalFixedInterest(round(totalFixedInterest + fixedInterestAmount, 2));
//			consolidatedInterest.setTotalVariableInterest(round(totalVariableInterest + variableInterestAmount, 2));
//			consolidatedInterest.setTotalInterestPayoff(round(totalPayoff, 2));
//			consolidatedInterest.setInterestBalance(round(interestBalance + interestAmount, 2));
//			consolidatedInterest.setFixedInterestBalance(round(totalFixedInterest + fixedInterestAmount, 2));
//			consolidatedInterest.setVariableInterestBalance(round(totalVariableInterest + variableInterestAmount, 2));
//
//			interestSummaryDAO.insertInConsolidatedInterest(consolidatedInterest);
//		} else {
//			interestBalance = consolidatedInterest.getInterestBalance() == null ? 0d
//					: consolidatedInterest.getInterestBalance();
//			totalPayoff = consolidatedInterest.getTotalInterestPayoff() == null ? 0d
//					: consolidatedInterest.getTotalInterestPayoff();
//			totalInterest = consolidatedInterest.getTotalInterest() == null ? 0d
//					: consolidatedInterest.getTotalInterest();
//			totalFixedInterest = consolidatedInterest.getTotalFixedInterest() == null ? 0d
//					: consolidatedInterest.getTotalFixedInterest();
//			totalVariableInterest = consolidatedInterest.getTotalVariableInterest() == null ? 0d
//					: consolidatedInterest.getTotalVariableInterest();
//			balanceFixedInterest = consolidatedInterest.getFixedInterestBalance() == null ? 0d
//					: consolidatedInterest.getFixedInterestBalance();
//			balanceVariableInterest = consolidatedInterest.getVariableInterestBalance() == null ? 0d
//					: consolidatedInterest.getVariableInterestBalance();
//
//			consolidatedInterest.setDepositId(depositId);
//			consolidatedInterest.setTotalInterest(round(totalInterest + interestAmount, 2));
//			consolidatedInterest.setTotalFixedInterest(round(totalFixedInterest + fixedInterestAmount, 2));
//			consolidatedInterest.setTotalVariableInterest(round(totalVariableInterest + variableInterestAmount, 2));
//			consolidatedInterest.setTotalInterestPayoff(round(totalPayoff, 2));
//			consolidatedInterest.setInterestBalance(round(interestBalance + interestAmount, 2));
//			consolidatedInterest.setFixedInterestBalance(round(balanceFixedInterest + fixedInterestAmount, 2));
//			consolidatedInterest.setVariableInterestBalance(round(balanceVariableInterest + variableInterestAmount, 2));
//			interestSummaryDAO.updateInConsolidatedInterest(consolidatedInterest);
//		}
//		// --------------------------------------------------
//
//		// Insert/update in DepositHolderWiseConsolidatedInterest table
//		// ---------------------------------------------------
//		for (int i = 0; i < depositHolderList.size(); i++) {
//			Float contribution = depositHolderList.get(i).getContribution();
//			Long depositHolderId = depositHolderList.get(i).getId();
//
//			// re-intitializing
//			interestBalance = 0d;
//			totalPayoff = 0d;
//			totalInterest = 0d;
//			totalFixedInterest = 0d;
//			totalVariableInterest = 0d;
//			balanceFixedInterest = 0d;
//			balanceVariableInterest = 0d;
//
//			InterestSummaryHolderWise holderTotalInterest = interestSummaryDAO
//					.getDepositHolderInterestConsolidation(depositId, depositHolderId);
//
//			if (holderTotalInterest != null) {
//				interestBalance = holderTotalInterest.getInterestBalance() == null ? 0d
//						: holderTotalInterest.getInterestBalance();
//				totalPayoff = holderTotalInterest.getTotalInterestPayoff() == null ? 0d
//						: holderTotalInterest.getTotalInterestPayoff();
//				totalInterest = holderTotalInterest.getTotalInterest() == null ? 0d
//						: holderTotalInterest.getTotalInterest();
//				totalFixedInterest = holderTotalInterest.getTotalFixedInterest() == null ? 0d
//						: holderTotalInterest.getTotalFixedInterest();
//				totalVariableInterest = holderTotalInterest.getTotalVariableInterest() == null ? 0d
//						: holderTotalInterest.getTotalVariableInterest();
//				balanceFixedInterest = holderTotalInterest.getFixedInterestBalance() == null ? 0d
//						: holderTotalInterest.getFixedInterestBalance();
//				balanceVariableInterest = holderTotalInterest.getVariableInterestBalance() == null ? 0d
//						: holderTotalInterest.getVariableInterestBalance();
//
//				Double holderInterestAmount = round(interestAmount * (contribution / 100), 2);
//				Double holderFixedInterestAmount = round(fixedInterestAmount * (contribution / 100), 2);
//				Double holderVariableInterestAmount = round(variableInterestAmount * (contribution / 100), 2);
//				holderTotalInterest.setDepositId(depositId);
//				holderTotalInterest.setDepositHolderId(depositHolderId);
//				holderTotalInterest.setCustomerId(depositHolderList.get(i).getCustomerId());
//				holderTotalInterest.setTotalInterest(round(totalInterest + holderInterestAmount, 2));
//				holderTotalInterest.setTotalFixedInterest(round(totalFixedInterest + holderFixedInterestAmount, 2));
//				holderTotalInterest
//						.setTotalVariableInterest(round(totalVariableInterest + holderVariableInterestAmount, 2));
//				holderTotalInterest.setTotalInterestPayoff(round(totalPayoff, 2));
//				holderTotalInterest.setInterestBalance(round(interestBalance + holderInterestAmount, 2));
//				holderTotalInterest.setFixedInterestBalance(balanceFixedInterest + holderFixedInterestAmount);
//				holderTotalInterest.setVariableInterestBalance(balanceVariableInterest + holderVariableInterestAmount);
//
//				interestSummaryDAO.updateInDepositHolderWiseConsolidatedInterest(holderTotalInterest);
//			} else {
//				Double holderInterestAmount = round(interestAmount * (contribution / 100), 2);
//				Double holderFixedInterestAmount = round(fixedInterestAmount * (contribution / 100), 2);
//				Double holderVariableInterestAmount = round(variableInterestAmount * (contribution / 100), 2);
//
//				holderTotalInterest = new InterestSummaryHolderWise();
//
//				holderTotalInterest.setDepositId(depositId);
//				holderTotalInterest.setDepositHolderId(depositHolderId);
//				holderTotalInterest.setCustomerId(depositHolderList.get(i).getCustomerId());
//				holderTotalInterest.setTotalInterest(round(totalInterest + holderInterestAmount, 2));
//				holderTotalInterest.setTotalFixedInterest(round(totalFixedInterest + holderFixedInterestAmount, 2));
//				holderTotalInterest
//						.setTotalVariableInterest(round(totalVariableInterest + holderVariableInterestAmount, 2));
//
//				holderTotalInterest.setTotalInterestPayoff(round(totalPayoff, 2));
//				holderTotalInterest.setInterestBalance(round(interestBalance + interestAmount, 2));
//
//				holderTotalInterest.setFixedInterestBalance(balanceFixedInterest + holderFixedInterestAmount);
//				holderTotalInterest.setVariableInterestBalance(balanceVariableInterest + holderVariableInterestAmount);
//
//				interestSummaryDAO.insertInDepositHolderWiseConsolidatedInterest(holderTotalInterest);
//
//			}
//
//		}
//		// ---------------------------------------------------
	}

	private void consolidateInterestForPayOff(Long depositId, Double payOffAmount) {
		// Insert/update in ConsolidatedInterest table
//		// ---------------------------------------------------
//		ConsolidatedInterest consolidatedInterest = interestSummaryDAO.getConsolidatedInterest(depositId);
//
//		Double interestBalance = 0d;
//		Double totalPayoff = 0d;
//		Double totalInterest = 0d;
//		Double totalFixedInterest = 0d;
//		Double totalVariableInterest = 0d;
//		Double balanceFixedInterest = 0d;
//		Double balanceVariableInterest = 0d;
//
//		if (consolidatedInterest != null) {
//			interestBalance = consolidatedInterest.getInterestBalance() == null ? 0d
//					: consolidatedInterest.getInterestBalance();
//			totalPayoff = consolidatedInterest.getTotalInterestPayoff() == null ? 0d
//					: consolidatedInterest.getTotalInterestPayoff();
//			totalInterest = consolidatedInterest.getTotalInterest() == null ? 0d
//					: consolidatedInterest.getTotalInterest();
//			totalFixedInterest = consolidatedInterest.getTotalFixedInterest() == null ? 0d
//					: consolidatedInterest.getTotalFixedInterest();
//			totalVariableInterest = consolidatedInterest.getTotalVariableInterest() == null ? 0d
//					: consolidatedInterest.getTotalVariableInterest();
//			balanceFixedInterest = consolidatedInterest.getFixedInterestBalance() == null ? 0d
//					: consolidatedInterest.getFixedInterestBalance();
//			balanceVariableInterest = consolidatedInterest.getVariableInterestBalance() == null ? 0d
//					: consolidatedInterest.getVariableInterestBalance();
//
//			consolidatedInterest.setDepositId(depositId);
//			consolidatedInterest.setTotalInterest(round(totalInterest, 2));
//			consolidatedInterest.setTotalFixedInterest(round(totalFixedInterest, 2));
//			consolidatedInterest.setTotalVariableInterest(round(totalVariableInterest, 2));
//			consolidatedInterest.setTotalInterestPayoff(round(totalPayoff + payOffAmount, 2));
//			consolidatedInterest.setInterestBalance(round(interestBalance - payOffAmount, 2));
//			consolidatedInterest.setFixedInterestBalance(round(balanceFixedInterest, 2));
//			consolidatedInterest.setVariableInterestBalance(round(balanceVariableInterest - payOffAmount, 2));
//
//			interestSummaryDAO.insertInConsolidatedInterest(consolidatedInterest);
//		}

	}

	private void consolidateInterestHolderWiseForPayOff(Long depositId, Long depositHolderId, Double payOffAmount) {

		// Get last DepositHolderWiseConsolidatedInterest for the Holder
		// and update pay off information
		// ---------------------------------------------------
//		InterestSummaryHolderWise holderTotalInterest = interestSummaryDAO
//				.getDepositHolderInterestConsolidation(depositId, depositHolderId);
//		Double interestBalance = 0d;
//		Double totalPayoff = 0d;
//		Double totalInterest = 0d;
//		Double totalFixedInterest = 0d;
//		Double totalVariableInterest = 0d;
//		Double balanceFixedInterest = 0d;
//		Double balanceVariableInterest = 0d;
//		if (holderTotalInterest != null) {
//			interestBalance = holderTotalInterest.getInterestBalance() == null ? 0d
//					: holderTotalInterest.getInterestBalance();
//			totalPayoff = holderTotalInterest.getTotalInterestPayoff() == null ? 0d
//					: holderTotalInterest.getTotalInterestPayoff();
//			totalInterest = holderTotalInterest.getTotalInterest() == null ? 0d
//					: holderTotalInterest.getTotalInterest();
//			totalFixedInterest = holderTotalInterest.getTotalFixedInterest() == null ? 0d
//					: holderTotalInterest.getTotalFixedInterest();
//			totalVariableInterest = holderTotalInterest.getTotalVariableInterest() == null ? 0d
//					: holderTotalInterest.getTotalVariableInterest();
//			balanceFixedInterest = holderTotalInterest.getFixedInterestBalance() == null ? 0d
//					: holderTotalInterest.getFixedInterestBalance();
//			balanceVariableInterest = holderTotalInterest.getVariableInterestBalance() == null ? 0d
//					: holderTotalInterest.getVariableInterestBalance();
//
//			holderTotalInterest.setDepositId(depositId);
//			holderTotalInterest.setDepositHolderId(depositHolderId);
//			// holderTotalInterest.setCustomerId(depositHolderList.get(i).getCustomerId());
//			holderTotalInterest.setTotalInterest(round(totalInterest, 2));
//			holderTotalInterest.setTotalFixedInterest(round(totalFixedInterest, 2));
//			holderTotalInterest.setTotalVariableInterest(round(totalVariableInterest, 2));
//
//			holderTotalInterest.setTotalInterestPayoff(round(totalPayoff + payOffAmount, 2));
//
//			holderTotalInterest.setInterestBalance(round(interestBalance - payOffAmount, 2));
//			holderTotalInterest.setFixedInterestBalance(round(balanceFixedInterest, 2));
//			holderTotalInterest.setVariableInterestBalance(round(balanceVariableInterest - payOffAmount, 2));
//
//			interestSummaryDAO.updateInDepositHolderWiseConsolidatedInterest(holderTotalInterest);
//		}
		// ---------------------------------------------------
	}



	public void deductTDS() throws CustomException {
		Date currentDate = DateService.getCurrentDate();
		int month = DateService.getMonth(currentDate);
		// if (!(month == 2))
		// return;
		//
		// if (!DateService.isLastDateOfFinancialYear(currentDate))
		// return;

		Integer year = DateService.getYear(currentDate);
		String financialYear = DateService.getFinancialYear(currentDate);

		System.out.println("Going to start TDS deduction on " + currentDate + "...");

		List<EndUser> userList = endUserDAO.getAllApprovedUser();
		HashMap<Long, Long> depositConversionDetails = new HashMap<>();
		for (int i = 0; i < userList.size(); i++) {

			EndUser user = userList.get(i);
			Long customerId = user.getCustomerId();

			// TODO for customer
			TDS tds = tdsDAO.getTDSByMonth(customerId, month + 1, year);
			if (tds != null && tds.getTdsDeducted() == 1)
				return;

			// Calculate TDS needs to be deducted of a customer
			TDS customerTotalTDS = this.calculateTDS(customerId, financialYear);
			// customerTDS.put(customerId, customerTotalTDS);
			if(customerTotalTDS != null)
				depositConversionDetails.put(customerId, customerTotalTDS.getId());

		}

		// Get all deposits
		List<Deposit> deposits = fixedDepositDao.getOpenDeposits();
		Double totalTDSAmountForDeposit = 0d;
		for (int i = 0; i < deposits.size(); i++) {
			
//			if((deposits.get(i).getPrimaryCitizen().equalsIgnoreCase("NRI")) &&
//					(deposits.get(i).getPrimaryNRIAccountType().equalsIgnoreCase("NRE") ||
//					deposits.get(i).getPrimaryNRIAccountType().equalsIgnoreCase("FCNR") ||
//					deposits.get(i).getPrimaryNRIAccountType().equalsIgnoreCase("PRP")))
//				continue;
			
			
			Long depositId = deposits.get(i).getId();
			
			
			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);
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
				continue;
			
			DepositTDS depositTDS = tdsDAO.getConsolidatedTDS(depositId, financialYear, DateService.getCurrentDate());
			// insert in deposit TDS
			depositTDS = tdsDAO.insertDepositTDS(depositTDS);
			totalTDSAmountForDeposit = (depositTDS != null && depositTDS.getCalculatedTDSAmount() != null)
					? depositTDS.getCalculatedTDSAmount() : 0;

			// update depositwisecustomertds as tds is deducted for the deposit
			tdsDAO.updateDeductTDSInDepositWiseCustomerTDS(depositId);

//			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);

			Double tdsAmountOfDepositHolder = 0d;
			HashMap<Long, Double> tdsToDeduct = new HashMap<>();
			for (int j = 0; j < depositHolderList.size(); j++) {
				Long customerId = depositHolderList.get(j).getCustomerId();

				Long tdsId = depositConversionDetails.get(customerId)==null?0l: depositConversionDetails.get(customerId);
				
				// check the TDS amount of this customer
				tdsAmountOfDepositHolder = tdsDAO.getCustomerTDSForDeposit(customerId, depositId, tdsId, financialYear);
				tdsToDeduct.put(depositHolderList.get(j).getId(), tdsAmountOfDepositHolder);

			}
			if (depositTDS != null && depositTDS.getCalculatedTDSAmount() != null) {
				Distribution lastDistribution = paymentDistributionDAO.getLastPaymentDistribution(depositId);
				if (lastDistribution == null)
					return;

				// Deduct from Transaction
				Distribution distribution = new Distribution();
				distribution.setAction(Constants.TDS);
				distribution.setDepositId(depositId);
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
				calculationService.insertInDepositHolderWiseTDSDistribution(depositHolderList, tdsToDeduct, totalTDSAmountForDeposit,
						distribution.getId());

				//Insert in DepositSummary and HolderwiseDepositSummary
				DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposits.get(i), Constants.TDS, totalTDSAmountForDeposit, null, null,
						null, tdsToDeduct, depositHolderList, null, null, null);

				
				// Insert in Journal & Ledger
				//-----------------------------------------------------------			
				ModeOfPayment modeOfpayment = modeOfPaymentDAO.getModeOfPayment("Internal Transfer");
				if(modeOfpayment!=null)
				{
					ledgerService.insertJournal(deposits.get(i).getId(),this.getPrimaryCustomerId(depositHolderList) , DateService.getCurrentDate(),
							deposits.get(i).getAccountNumber(), deposits.get(i).getAccountNumber(), Event.TDS.getValue(),
							totalTDSAmountForDeposit,modeOfpayment.getId(), depositSummary.getTotalPrincipal(), null);
				}
				//-----------------------------------------------------------
			
			}
		}
	}


	public void deductTDS(Long depositId) {
		// Get Deposit Holder List
		List<DepositHolder> depositHolders = depositHolderDAO.getDepositHolders(depositId);
		HashMap<Long, Deposit> depositsMap = new HashMap<>();
		String financialYear = DateService.getFinancialYear(DateService.getCurrentDate());

		for (int j = 0; j < depositHolders.size(); j++) {

			Long customerId = depositHolders.get(j).getCustomerId();
			// depositList.addAll(fixedDepositDao.getDeposits(customerId));
			this.calculateTDS(customerId, financialYear);

			// Insert the deposits in an array
			List<Deposit> depositList = fixedDepositDao.getDeposits(customerId);
			for (int i = 0; i < depositList.size(); i++) {
				if (depositsMap.containsKey(depositList.get(i).getId()))
					continue;
				// Insert into hashMap
				depositsMap.put(depositList.get(i).getId(), depositList.get(i));

			}
		}

		Double totalTDSAmountForDeposit = 0d;
		for (Map.Entry<Long, Deposit> entry : depositsMap.entrySet()) {
			Long newDepositIdForTds = entry.getKey();
			Deposit deposit = entry.getValue();
			DepositTDS depositTDS = tdsDAO.getConsolidatedTDS(newDepositIdForTds, financialYear,
					DateService.getCurrentDate());
			// insert in deposit TDS
			depositTDS = tdsDAO.insertDepositTDS(depositTDS);
			totalTDSAmountForDeposit = (depositTDS != null && depositTDS.getCalculatedTDSAmount() != null)
					? depositTDS.getCalculatedTDSAmount() : 0;
			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(newDepositIdForTds);
			//

			Double tdsAmountOfDepositHolder = 0d;
			HashMap<Long, Double> tdsToDeduct = new HashMap<>();
			for (int j = 0; j < depositHolderList.size(); j++) {
				Long customerId = depositHolderList.get(j).getCustomerId();

				// check the TDS amount of this customer
				tdsAmountOfDepositHolder = tdsDAO.getCustomerTDSForDeposit(customerId, newDepositIdForTds,
						depositTDS.getId(), financialYear);
				tdsToDeduct.put(depositHolderList.get(j).getId(), tdsAmountOfDepositHolder);

			}
			if (depositTDS != null && depositTDS.getCalculatedTDSAmount() != null) {
				Distribution lastDistribution = paymentDistributionDAO.getLastPaymentDistribution(newDepositIdForTds);
				if (lastDistribution == null)
					return;

				// Deduct from Transaction
				Distribution distribution = new Distribution();
				distribution.setAction(Constants.TDS);
				distribution.setDepositId(newDepositIdForTds);
				distribution.setDistributionDate(DateService.getCurrentDate());
				distribution.setDepositedAmt(-totalTDSAmountForDeposit);
				distribution.setBalanceFixedInterest(lastDistribution.getBalanceFixedInterest());
				distribution.setBalanceVariableInterest(lastDistribution.getBalanceVariableInterest());
				distribution.setFixedAmt(lastDistribution.getFixedAmt());
				distribution.setVariableAmt(lastDistribution.getVariableAmt());
				distribution.setCompoundFixedAmt(lastDistribution.getCompoundFixedAmt());
				distribution.setCompoundVariableAmt(
						this.round(lastDistribution.getCompoundVariableAmt() - totalTDSAmountForDeposit, 2));
				Double totalBal = lastDistribution.getCompoundFixedAmt()
						+ (lastDistribution.getCompoundVariableAmt() - totalTDSAmountForDeposit);
				distribution.setTotalBalance(this.round(totalBal, 2));
				distribution = paymentDistributionDAO.insertPaymentDistribution(distribution);

				// Deduct from depositHolder as per Holderwise ratio
				calculationService.insertInDepositHolderWiseTDSDistribution(depositHolderList, tdsToDeduct, totalTDSAmountForDeposit,
						distribution.getId());

//				// update the deposit table
//				deposit.setCurrentBalance(this.round(totalBal, 2));
//				fixedDepositDao.updateFD(deposit);
				

				//Insert in DepositSummary and HolderwiseDepositSummary
				DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.TDS, totalTDSAmountForDeposit, null, null,
						null, tdsToDeduct, depositHolderList, null, null, null);

				
				// Insert in Journal & Ledger
				//-----------------------------------------------------------


				
				//ToDo:
//				ledgerService.insertJournalLedger(deposit.getId(), this.getPrimaryCustomerId(depositHolderList),DateService.getCurrentDate(), 
//						deposit.getAccountNumber(), "Deposit Account", null, "TDS Account", 
//						 Constants.TDS, totalTDSAmountForDeposit, "Internal Tranasfer", 
//						depositSummary.getTotalPrincipal());		
				//-----------------------------------------------------------

			}
		}

	}

	

	@SuppressWarnings("unused")
	private DepositSummary updatePayoffDepositSummary(Long depositId, Double payOffAmount, 
			HashMap<Long, Double> depositHolderWisePayoff ){
		
		// Get last Deposit Summary
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(depositId);


		if (payOffAmount != null)
			payOffAmount = FDService.round(payOffAmount, 2);

		if (depositSummary != null) {
			// Insert in InterstSummary Table
			// ---------------------------------------------------------

			depositSummary.setTotalFixedInterestPaidOff(null);
			depositSummary.setTotalVariableInterestPaidOff(depositSummary.getTotalVariableInterestPaidOff() + payOffAmount);

			depositSummary.setTotalFixedInterestInHand(depositSummary.getTotalFixedInterestInHand());
			depositSummary.setTotalVariableInterestInHand(depositSummary.getTotalVariableInterestInHand()- payOffAmount);
			depositSummary = depositSummaryDAO.update(depositSummary);
			// ---------------------------------------------------------

			// Insert in InterestSummaryHolderWise
			// ---------------------------------------------------------
			for (Map.Entry<Long, Double> entry : depositHolderWisePayoff.entrySet()) {
			    	
			    Long depositHolderId = entry.getKey();
				Double holderPayoffAmount = depositHolderWisePayoff.get(depositHolderId);
	
				// Get last Holder wise Deposit Summary
				DepositSummaryHolderWise depositSummaryHolderWise = depositSummaryDAO.getHolderWiseDepositSummary(depositId, depositHolderId);

				if (holderPayoffAmount == null) {
					depositSummaryHolderWise.setTotalFixedInterestPaidOff(null);
					depositSummaryHolderWise.setTotalVariableInterestPaidOff(null);
				} else {
					depositSummaryHolderWise.setTotalFixedInterestPaidOff(null);
					depositSummaryHolderWise.setTotalVariableInterestPaidOff(holderPayoffAmount);
				}
				depositSummaryHolderWise.setTotalFixedInterestInHand(depositSummaryHolderWise.getTotalFixedInterestInHand());
				depositSummaryHolderWise.setTotalVariableInterestInHand(depositSummaryHolderWise.getTotalVariableInterestInHand() - holderPayoffAmount);
			
				depositSummaryDAO.updateDepositSummaryHolderWise(depositSummaryHolderWise);
			}
			// ---------------------------------------------------------
		}
		return depositSummary;
	}

	@Transactional
	public void deductAnnuityEMI(Deposit deposit) throws CustomException {

		Long depositId = deposit.getId();

		// Get the pay off EMI details of deposit holder
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);

		if (depositHolderList == null)
			return;

		// Get the beneficiaryDetails of the deposit
		List<BenificiaryDetails> beneficiaryDetailList = beneficiaryDetailsDAO.getBeneficiaryDetails(depositId);

		DepositHolder depositHolder = depositHolderList.get(0);
		Double emiAmt = depositHolder.getEmiAmount();

		Distribution lastDistribution = paymentDistributionDAO.getLastPaymentDistribution(depositId);
		lastDistribution = this.DeductEMI(deposit, depositHolderList, beneficiaryDetailList, emiAmt, lastDistribution);

	}

	private void updatePayOffDueDate(Deposit deposit, String payOffType) {
		if (payOffType == "")
			return;

		Date dueDate = DateService.addMonths(DateService.getCurrentDateTime(), 1);
		Date payOffDueDate = calculateInterestPayOffDueDate(payOffType, deposit.getNewMaturityDate(), dueDate);
		deposit.setPayOffDueDate(payOffDueDate);

		deposit.setPayOffDueDate(payOffDueDate);
		fixedDepositDao.updateFD(deposit);
	}

	
	

	/*
	 * Close deposit On Maturity. This method has been called from the scheduler
	 */


	public void createSweepDeposits() {

//		Date date = DateService.getCurrentDateTime();
//
//		// Get Bank configuration
//		BankConfiguration bankConfig = ratesDAO.findAll();
//
//		if (bankConfig == null) {
//
//			return;
//		}
//		if (bankConfig.getMinimunSavingBalanceForAutodeposit() == null) {
//			return;
//		}
//
//		int minBalanceInSavingAcc = (bankConfig.getMinimunSavingBalanceForAutodeposit() == null) ? 0
//				: bankConfig.getMinimunSavingBalanceForAutodeposit();
//
//		// Get Customer wise saving account details
//		List<Object[]> accountDetailsList = accountDetailsDAO.getCustomersSavingAccounts(minBalanceInSavingAcc);
//
//		if (accountDetailsList == null)
//			return;
//
//		for (int i = 0; i < accountDetailsList.size(); i++) {
//
//			Object[] accDetails = accountDetailsList.get(i);
//
//			BigInteger cid = (BigInteger) accDetails[0];
//			Long customerId = cid.longValue();
//
//			// Deduction from accountDetails table...
//			Double depositAmount = 0d;
//			depositAmount = (double) accDetails[20] - minBalanceInSavingAcc;
//
//			if (depositAmount <= 0d) {
//				logger.debug("Insufficient Balance of customerId : " + customerId);
//				continue;
//			}
//
//			Customer cus = customerDAO.getById(customerId);
//			int days = 365;
//			BankConfiguration bankConfiguration = ratesDAO.findAll();
//			String currency = bankConfiguration == null ? bankConfiguration.getCurrency() : "Rupee";
//			float interestRateForAutoDeposit = this.getDepositIntRate(days, cus.getCategory(), currency, depositAmount, Constants.autoDeposit );
//			this.createSweepDeposit(customerId, depositAmount, minBalanceInSavingAcc, interestRateForAutoDeposit);
//
//		}

	}

	@SuppressWarnings("unused")
	@Transactional
	public void createSweepDeposit(Long customerId, Double sweepAmount, SweepConfiguration sweepConfiguration, 
			SweepInFacilityForCustomer sweepFacilityForCustomer, Boolean isForceSweepByBank) throws CustomException {
		
		Date date = DateService.getCurrentDateTime();
		AccountDetails accDetail = accountDetailsDAO.findSavingByCustId(customerId);
		
	
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(Constants.FUNDTRANSFER);
		
		if(!isForceSweepByBank)
		{	
			// AccountDetails accDetail =
			// accountDetailsDAO.findByAccountNo(accDetails[19].toString());
			accDetail.setAccountBalance(accDetail.getAccountBalance()-sweepAmount);
			accountDetailsDAO.updateUserAccountDetails(accDetail);
		}
		
		// get the customer details
		Customer customer = customerDAO.getById(customerId);	
		String customerCategory = calculationService.geCustomerActualCategory(customer);
		
		// Default Currency We have to Configure In Separate Location
		//---------------------------------------------------	
		String currency = "INR";
		CurrencyConfiguration currConfig = fixedDepositDao.getDefaultCurrency(customer.getCitizen(), customer.getNriAccountType());
		currency = currConfig != null? currConfig.getCurrency() : currency;
		//---------------------------------------------------
		
		// There are 4 types of Sweep Configuration. These are
		// Fixed Interest & Fixed Tenure || Fixed Interest & Dynamic Tenure || Dynamic Interest & Fixed Tenure || Dynamic Interest & Dynamic Tenure
		// Check the previous deposit with sameSweepConfiguration
		 
		Boolean isNewDeposit = false;
		
		List<Deposit> depositList = fixedDepositDao.getAutoDeposits(customerId, sweepConfiguration.getSweepInType());
		Deposit deposit = null;
		if(depositList == null)
			isNewDeposit = true;
		else if(depositList.size()==0)
			isNewDeposit = true;
		else
		{
			for(int i=0; i<depositList.size(); i++)
			{
				deposit =  depositList.get(i);
				if(deposit.getSweepInType().contains("Fixed Tenure"))
				{
					// Get the  existing customer Specified Tenure
					Integer daysTenure = DateService.getDaysBetweenTwoDates(deposit.getCreatedDate(), deposit.getNewMaturityDate());
					
					// Get the interest rate of the tenure 
					Float interestRate = calculationService.getDepositInterestRate(daysTenure, customerCategory, currency, sweepAmount,
							Constants.fixedDeposit, customer.getCitizen(), customer.getNriAccountType());
					
					if(deposit.getModifiedInterestRate().floatValue()==interestRate.floatValue())
					{
						isNewDeposit = false;
						break;
					}
					else
					{
						isNewDeposit = true;
					}
				}
			}
		
		}
		
		System.out.println("///.." + deposit);
		DepositHolder depositHolder = null;
		String transactionId = generateRandomString();

		
		// Find if any Auto deposit exists or not
		// if not then create or else modify the amount only
		// It will not go to deposit modification
		List<DepositHolder> depositHolderList = new ArrayList<>();
		if (isNewDeposit) 
		{

			Date dt = DateService.getCurrentDate();
			Integer days = 0;
			if(sweepConfiguration.getSweepInType().contains("Fixed Tenure"))
			{
				String[] tenure = sweepFacilityForCustomer.getTenure().split(",");
				
				for(int i = 0; i<tenure.length; i++)
				{
					if(tenure[i]!=null)
					{
						if(tenure[i].toString().endsWith("Year"))
						{
							String num = tenure[i].replaceAll(" Year", "" );
							dt = DateService.addYear(dt, Integer.parseInt(num));
						}
						else if(tenure[i].toString().endsWith("Month"))
						{
							String num = tenure[i].replaceAll(" Month", "");
							dt = DateService.addMonths(dt, Integer.parseInt(num));
						}
						else if(tenure[i].toString().endsWith("Day"))
						{
							String num = tenure[i].replaceAll(" Day", "");
							dt = DateService.addDays(dt, Integer.parseInt(num));
						}
					}
				}
				days = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), dt);
			}
			else
			{
				// For dynamic tenure sweep configuration
				days = 365;
			}
			// Get the interest rate of the tenure 
			Float interestRate = calculationService.getDepositInterestRate(days, customerCategory, currency, sweepAmount,
								Constants.fixedDeposit, customer.getCitizen(), customer.getNriAccountType());

			deposit = new Deposit();

			// deposit.setAccountNumber(accDetails[19].toString());
			deposit.setAccountNumber(this.generateAccountNoForAutoDeposit());
			deposit.setDepositAmount(sweepAmount);
			deposit.setDepositType(Constants.SINGLE);
			deposit.setFlexiRate("Yes");

			// Get Minimum period interest rate
			deposit.setInterestRate(interestRate);
			deposit.setModifiedInterestRate(interestRate);
			deposit.setLinkedAccountNumber(accDetail.getAccountNo());
			deposit.setPaymentMode(Constants.SAVINGACCOUNTDEBIT);

			// Set maturity days by 1 year 1 day from current date
		
			deposit.setMaturityDate(DateService.addDays(DateService.getCurrentDate(), days));
			deposit.setNewMaturityDate(DateService.addDays(DateService.getCurrentDate(), days));
			// deposit.setPaymentType(fixedDepositForm.getPaymentType());

			// deposit.setPayOffInterestType(fixedDepositForm.getPayOffInterestType());
			deposit.setStatus(Constants.OPEN);
//			deposit.setTenure(days);
//			deposit.setTenureType(Constants.DAY);
			deposit.setTenureInDays(days);
			deposit.setTotalTenureInDays(days);
			deposit.setCreatedDate(date);
			deposit.setModifiedDate(date);
			deposit.setCreatedBy(Constants.SYSTEM);
			deposit.setDepositCategory(Constants.AUTO);
			deposit.setApprovalStatus(Constants.APPROVED);
			deposit.setComment("Auto Deposit");
			deposit.setDepositCurrency(currency);
			deposit.setMaturityInstruction("repay");
			deposit.setCurrentBalance(sweepAmount);
			deposit.setPrimaryCustomerCategory(customerCategory);
			deposit.setPrimaryCitizen(customer.getCitizen());
			deposit.setPrimaryNRIAccountType(customer.getNriAccountType());
			deposit.setTransactionId(transactionId);
			deposit.setSweepInType(sweepConfiguration.getSweepInType());
			deposit.setPaymentModeId(mop.getId());
			deposit.setPaymentMode(mop.getPaymentMode());
			
			// deposit.setInterestRate(5);
			// PAY OFF DATE CALCULATION 
			// For Auto Deposit Payoff will not be there
			deposit = fixedDepositDao.saveFD(deposit);

			depositHolder = new DepositHolder();
			depositHolder.setDepositId(deposit.getId());
			depositHolder.setContribution(100f);
			depositHolder.setCustomerId(customerId);
			depositHolder.setDepositHolderStatus(Constants.PRIMARY);
			depositHolder.setDepositHolderCategory(customerCategory);

			Set<DepositHolder> set = new HashSet<DepositHolder>();
			set.add(depositHolder);
			deposit.setDepositHolder(set);

			depositHolder = depositHolderDAO.saveDepositHolder(depositHolder);

			fixedDepositDao.updateFD(deposit);

			depositHolderList.add(depositHolder);

		} else 
		{

			deposit.setDepositAmount(sweepAmount);
	
			if(sweepConfiguration.getSweepInType().contains("Dynamic Tenure"))
			{
				deposit.setTenureInDays(deposit.getTenureInDays() + 365);
				deposit.setTotalTenureInDays(deposit.getTotalTenureInDays() + 365);
				deposit.setMaturityDate(DateService.addDays(DateService.getCurrentDate(), 365));
				deposit.setNewMaturityDate(DateService.addDays(DateService.getCurrentDate(), 365));
			}
			
			deposit.setCurrentBalance(deposit.getCurrentBalance() + sweepAmount);
			deposit.setModifiedDate(date);
			fixedDepositDao.updateFD(deposit);

			depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
			// this is an Auto Deposit. So only one deposit Holder will be
			// there
			depositHolder = depositHolderList.get(0);
		}

		// .........SAVING PAYMENT INFO...... 


		Payment payment = new Payment();
		payment.setAmountPaid(sweepAmount);

		payment.setLinkedAccTypeForFundTransfer("Saving");
		payment.setLinkedAccNoForFundTransfer(accDetail.getAccountNo());
		payment.setDepositHolderId(depositHolder.getId());
		payment.setPaymentModeId(mop.getId());
		payment.setPaymentMode(mop.getPaymentMode());
		payment.setPaymentDate(date);
		payment.setDepositId(deposit.getId());
		payment.setTransactionId(transactionId);
		payment.setCreatedBy(Constants.SYSTEM);
		payment = paymentDAO.insertPayment(payment);

		
		// Insert in Distribution and holderWiseDistribution
		calculationService.insertInPaymentDistribution(deposit, depositHolderList, sweepAmount,
				payment.getId(), Constants.PAYMENT, null, null, null, null, null, null, null);
		
		//Insert in DepositSummary and HolderwiseDepositSummary
		DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.PAYMENT, sweepAmount,
				null, null, null, null, depositHolderList, null, null, null);
		
		// Insert in Journal & Ledger
		//-----------------------------------------------------------
		AccountDetails accountDetails = accountDetailsDAO.findByAccountNo(deposit.getLinkedAccountNumber());
		String fromAccountNo = accountDetails.getAccountNo();

		ledgerService.insertJournal(deposit.getId(), this.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(), fromAccountNo,
				deposit.getAccountNumber(), Event.SWEEP_DEPOSIT.getValue(), sweepAmount, mop.getId(),
				depositSummary.getTotalPrincipal(), transactionId);
		
		//-----------------------------------------------------------
				
	}


public String makePayment(List<DepositHolder> depositHolderList, Double payAmount, String modeOfPayment,
			String chequeDDNo, String bank, String branch, String cardNo, String cardType, Integer cardCVV,
			String cardExpiryDate, Integer topUp, String customerName, String email) {

		String fromAccountNo = "";
		String fromAccountType = "";
		
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPaymentById(Long.parseLong(modeOfPayment));
		
		Payment payment = new Payment();
		Date date = DateService.getCurrentDateTime();
		String transactionId = generateRandomString();

		DepositHolder primaryDepositHolder = null;
		Deposit deposit = null;
		for (int i = 0; i < depositHolderList.size(); i++) {
			if (depositHolderList.get(i).getDepositHolderStatus().equalsIgnoreCase("PRIMARY"))
			{
				primaryDepositHolder = depositHolderList.get(i);
			}
			
			if(deposit==null)
				deposit = fixedDepositDao.getDeposit(depositHolderList.get(i).getDepositId());
		}

		payment.setDepositId(primaryDepositHolder.getDepositId());
		payment.setDepositHolderId(primaryDepositHolder.getId());
		payment.setAmountPaid(payAmount);
		payment.setBank(bank);
		payment.setBranch(branch);
		payment.setChequeDDNumber(chequeDDNo);
		payment.setPaymentModeId(mop.getId());
		payment.setPaymentMode(mop.getPaymentMode());
		payment.setPaymentDate(date);
		payment.setTopUp(topUp);

	
		String[] cardNoArray = cardNo.split("-");
		String cardNo1 = "";
		for (int i = 0; i < cardNoArray.length; i++) {
			cardNo1 = cardNo1 + cardNoArray[i];
		}
		if (cardNo1 != "")
			payment.setCardNo(Long.valueOf(cardNo1));
		else
			payment.setCardNo(Long.parseLong(cardNo));
		payment.setCardType(cardType);
		payment.setCardCvv(cardCVV);
		payment.setCardExpiryDate(cardExpiryDate);
		String userName = getCurrentLoggedUserName();
		
		payment.setTransactionId(transactionId);
		payment.setCreatedBy(userName);
		
	
		// For accounting entry only
		fromAccountNo = cardNo1;
		fromAccountType = cardType;
		
		payment = paymentDAO.insertPayment(payment);
		try {

			String tex = Constants.CUSTOMERREMENDERSUBJECT;
			SimpleMailMessage emails = new SimpleMailMessage();
			emails.setTo(email);
			emails.setSubject(tex);
			emails.setText(Constants.HELLO + customerName + Constants.PAYMENTKBODY4 + payAmount
					+ Constants.PAYMENTKBODY5 + Constants.BANKSIGNATURE);
			mailSender.send(emails);
			SimpleMailMessage emails1 = new SimpleMailMessage();
			emails1.setTo(email);

		} catch (Exception e) {
			System.out.println(e.getMessage() + e);

		}
		
		
		//Insert in Distributor and HolderWiseDistributor
		calculationService.insertInPaymentDistribution(deposit, depositHolderList, payAmount, payment.getId(),  Constants.PAYMENT, 
				null, null, null, null, null, null, null);
		
		//Insert in DepositSummary and HolderwiseDepositSummary
		DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.PAYMENT, payAmount, null, null,
						null, null, depositHolderList, null, null, null);
		
		//-----------------------------------------------------------	
		
		return payment.getTransactionId();
	}

	public void insertInPaymentDistributionForReverseEMI(List<DepositHolder> depositHolderList, Double payAmount,
			Long actionId, String action) {

		DepositHolder primaryDepositHolder = null;
		for (int i = 0; i < depositHolderList.size(); i++) {
			if (depositHolderList.get(i).getDepositHolderStatus().equalsIgnoreCase("PRIMARY"))
				primaryDepositHolder = depositHolderList.get(i);
		}

		// String action = "Payment";
		Customer cus = customerDAO.getById(primaryDepositHolder.getCustomerId());

		Float fixedRate = 0f;
		// List<Rates> rates =
		// ratesDAO.findByCategory(cus.getCategory()).getResultList();
		// if (!action.equalsIgnoreCase(Constants.AUTOPAYMENT)) {
		// if (rates.size() > 0) {
		// fixedRate = rates.get(0).getFdFixedPercent();
		// }
		// }

		Double previousTotalBalance = 0d;
		Double previousCompundFixed = 0d;
		Double previousCompoundVariable = 0d;
		Double previousFixedInterest = 0d;
		Double previousVariableInterest = 0d;
		Double previousBalanceFixedInterest = 0d;
		Double previousBalanceVariableInterest = 0d;
		Distribution previousPaymentDistribution = paymentDistributionDAO
				.getLastPaymentDistribution(primaryDepositHolder.getDepositId());
		if (previousPaymentDistribution != null) {
			previousTotalBalance = previousPaymentDistribution.getTotalBalance();
			previousCompundFixed = previousPaymentDistribution.getCompoundFixedAmt();
			previousCompoundVariable = previousPaymentDistribution.getCompoundVariableAmt();
			// previousFixedInterest =
			// previousPaymentDistribution.getFixedInterest();
			// previousVariableInterest =
			// previousPaymentDistribution.getVariableInterest();
			previousBalanceFixedInterest = previousPaymentDistribution.getBalanceFixedInterest();
			previousBalanceVariableInterest = previousPaymentDistribution.getBalanceVariableInterest();
		}

		Double fixAmt = 0d;
		Double variableAmt = round((payAmount - fixAmt), 2);
		Double compundFixed = round((fixAmt + 0 + previousCompundFixed), 2);
		Double compunndVariable = round((variableAmt + 0 + previousCompoundVariable), 2);
		Double totalBalance = round((compundFixed + compunndVariable), 2);

		Distribution paymentDistribution = new Distribution();
		paymentDistribution.setDepositId(primaryDepositHolder.getDepositId());
		paymentDistribution.setDistributionDate(DateService.getCurrentDate());
		paymentDistribution.setAction(action);
		paymentDistribution.setDepositedAmt(round(payAmount, 2));
		paymentDistribution.setFixedAmt(round(fixAmt, 2));
		paymentDistribution.setVariableAmt(round(variableAmt, 2));
		paymentDistribution.setActionId(actionId);
		// paymentDistribution.setFixedInterest(previousFixedInterest);
		// paymentDistribution.setVariableInterest(previousVariableInterest);
		paymentDistribution.setBalanceFixedInterest(round(previousBalanceFixedInterest, 2));
		paymentDistribution.setBalanceVariableInterest(round(previousBalanceVariableInterest, 2));

		paymentDistribution.setCompoundFixedAmt(round(compundFixed, 2));
		paymentDistribution.setCompoundVariableAmt(round(compunndVariable, 2));
		paymentDistribution.setTotalBalance(round(totalBalance, 2));
		paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

		// Insert in DepositHolderWiseDistribution
		//COM--this.insertInDepositHolderWiseDistribution(depositHolderList, action, actionId, payAmount);

		// update the deposit table
		Deposit deposit = fixedDepositDao.findById(primaryDepositHolder.getDepositId());
		deposit.setCurrentBalance(totalBalance);
		System.out.println("totalBalance++++" + totalBalance);
		fixedDepositDao.updateFD(deposit);

	}

	

	public void insertInPaymentDistributionForAnnuity(List<DepositHolder> depositHolderList, Double payAmount,
			Long actionId, String action) {

		DepositHolder primaryDepositHolder = null;
		for (int i = 0; i < depositHolderList.size(); i++) {
			if (depositHolderList.get(i).getDepositHolderStatus().equalsIgnoreCase("PRIMARY"))
				primaryDepositHolder = depositHolderList.get(i);
		}

		// String action = "Payment";
		Customer cus = customerDAO.getById(primaryDepositHolder.getCustomerId());

		Double previousCompundFixed = 0d;
		Double previousCompoundVariable = 0d;
		Double previousBalanceFixedInterest = 0d;
		Double previousBalanceVariableInterest = 0d;
		Distribution previousPaymentDistribution = paymentDistributionDAO
				.getLastPaymentDistribution(primaryDepositHolder.getDepositId());
		if (previousPaymentDistribution != null) {
			previousCompundFixed = previousPaymentDistribution.getCompoundFixedAmt();
			previousCompoundVariable = previousPaymentDistribution.getCompoundVariableAmt();
			// previousFixedInterest =
			// previousPaymentDistribution.getFixedInterest();
			// previousVariableInterest =
			// previousPaymentDistribution.getVariableInterest();
			previousBalanceFixedInterest = previousPaymentDistribution.getBalanceFixedInterest();
			previousBalanceVariableInterest = previousPaymentDistribution.getBalanceVariableInterest();
		}

		Double variableAmt = round((payAmount), 2);
		Double compundFixed = previousCompundFixed;
		Double compunndVariable = round((variableAmt + 0 + previousCompoundVariable), 2);
		Double totalBalance = round((compundFixed + compunndVariable), 2);

		Distribution paymentDistribution = new Distribution();
		paymentDistribution.setDepositId(primaryDepositHolder.getDepositId());
		paymentDistribution.setDistributionDate(DateService.getCurrentDate());
		paymentDistribution.setAction(action);
		paymentDistribution.setDepositedAmt(round(payAmount, 2));
		paymentDistribution.setFixedAmt(0d);
		paymentDistribution.setVariableAmt(round(variableAmt, 2));
		paymentDistribution.setActionId(actionId);
		// paymentDistribution.setFixedInterest(previousFixedInterest);
		// paymentDistribution.setVariableInterest(previousVariableInterest);
		paymentDistribution.setBalanceFixedInterest(round(previousBalanceFixedInterest, 2));
		paymentDistribution.setBalanceVariableInterest(round(previousBalanceVariableInterest, 2));

		paymentDistribution.setCompoundFixedAmt(round(compundFixed, 2));
		paymentDistribution.setCompoundVariableAmt(round(compunndVariable, 2));
		paymentDistribution.setTotalBalance(round(totalBalance, 2));
		paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

		// Insert in DepositHolderWiseDistribution
		//COM--this.insertInDepositHolderWiseDistribution(depositHolderList, action, actionId, payAmount);

		// update the deposit table
		Deposit deposit = fixedDepositDao.findById(primaryDepositHolder.getDepositId());
		deposit.setCurrentBalance(totalBalance);
		System.out.println("totalBalance++++" + totalBalance);
		fixedDepositDao.updateFD(deposit);

	}

//	public void insertInDepositHolderWiseDistribution(List<DepositHolder> depositHolderList, String action,
//			Long actionId, Double payAmount) {
//
//		if (!action.equalsIgnoreCase(Constants.PAYOFF)) {
//			for (int i = 0; i < depositHolderList.size(); i++) {
//
//				DepositHolderWiseDistribution depHolderDistribution = new DepositHolderWiseDistribution();
//				depHolderDistribution.setAction(action);
//				depHolderDistribution.setActionId(actionId);
//				depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
//				depHolderDistribution.setCustomerId(depositHolderList.get(i).getCustomerId());
//				depHolderDistribution.setDepositHolderId(depositHolderList.get(i).getId());
//				depHolderDistribution.setDepositId(depositHolderList.get(i).getDepositId());
//				depHolderDistribution.setActionAmount(round(payAmount, 2));
//				depHolderDistribution.setContribution(depositHolderList.get(i).getContribution());
//				Double distributedAmt = payAmount * (depositHolderList.get(i).getContribution() / 100);
//				
//				Double currentBalance = depositHolderWiseDistributionDAO.getLastBalanceOfDepositHolder(
//						depositHolderList.get(i).getDepositId(), depositHolderList.get(i).getId());
//				depHolderDistribution.setCurrentBalance(round(currentBalance + distributedAmt, 2));
//				depositHolderWiseDistributionDAO.saveDepositHolderWiseDistribution(depHolderDistribution);
//			}
//		}
//	}

	
	
//	// This is for payment/ withdraw or another general purpose entry.
//	// Not for interest, Adjustment, TDS and payoff
//	public void insertInDepositHolderWiseDistributionForPayOff(Deposit deposit, Long distributionId, HashMap<Long, Double> holderWisePayOffList) {
//		
//		for (Map.Entry<Long, Double> entry : holderWisePayOffList.entrySet()) {
//	    	
//		    Long depositHolderId = entry.getKey();
//			Double holderPayoffAmount = holderWisePayOffList.get(depositHolderId);
//
//			// Get the last distribution of the holder from holderwise distribution
//			DepositHolderWiseDistribution lastDistribution = depositHolderWiseDistributionDAO.getDepositHolderWiseLastDistribution(deposit.getId(), depositHolderId);	
//					
//			DepositHolderWiseDistribution depHolderDistribution = new DepositHolderWiseDistribution();
//			depHolderDistribution.setAction(Constants.PAYOFF);
//			depHolderDistribution.setActionId(distributionId);
//			depHolderDistribution.setDistributionDate(DateService.getCurrentDate());
//			depHolderDistribution.setCustomerId(lastDistribution.getCustomerId());
//			depHolderDistribution.setDepositHolderId(depositHolderId);
//			depHolderDistribution.setDepositId(deposit.getId());
//			depHolderDistribution.setContribution(lastDistribution.getContribution());
//			
//			
//			depHolderDistribution.setPayoffAmount(holderPayoffAmount);
//							
//			depHolderDistribution.setFixedCompoundAmount(lastDistribution.getFixedCompoundAmount());
//			depHolderDistribution.setVariableCompoundAmount(lastDistribution.getVariableCompoundAmount());
//			
//			depHolderDistribution.setBalanceFixedInterest(lastDistribution.getBalanceFixedInterest());
//			depHolderDistribution.setBalanceFixedInterest(lastDistribution.getBalanceFixedInterest() - holderPayoffAmount);
//		
//			depositHolderWiseDistributionDAO.saveDepositHolderWiseDistribution(depHolderDistribution);
//		}
//	}


	

	public float getFDFixedPercent(String Category) {
		Rates rate = null;
		List<Rates> rates = ratesDAO.findByCategory(Category).getResultList();
		if (rates != null && rates.size() > 0) {
			rate = rates.get(0);
			return rate.getFdFixedPercent();
		} else
			return 0;
	}

	// public Date getMaturityDateByDays(int days) {
	// Date date = DateService.getCurrentDateTime();
	// Calendar cal = Calendar.getInstance();
	// cal.setTime(date);
	// cal.add(Calendar.DATE, days);
	// return cal.getTime();
	// }
	/*
	 * public Date getMaturityDateByDays(Date date, int days) { // Date date=new
	 * Date(); Calendar cal = Calendar.getInstance(); cal.setTime(date);
	 * cal.add(Calendar.DATE, days); return cal.getTime(); }
	 */

	public double getCompoundMaturityAmount(Double principle, float rate, int n, float time) {

		/*
		 * amount = final amount,... principle = principal amount (initial
		 * investment),.. rate = annual nominal interest rate (in percentage),..
		 * n = number of times the interest is compounded per year,... time =
		 * number of years
		 */
		double amount = principle * (Math.pow((1 + rate / 100 / n), n * time));

		return amount;

	}

	public String getRecurringAmount(FixedDepositForm fixedDepositForm, Float timeInYear, Float rateOfInterest) {

		int freqOfCompunding = 1;
		float interval = 1f;

		if (fixedDepositForm.getPaymentType().equalsIgnoreCase("monthly")) {
			freqOfCompunding = (int) (timeInYear * 12);
			interval = (float) 0.08; // ..1/12
		}
		if (fixedDepositForm.getPaymentType().equalsIgnoreCase("quarterly")) {
			freqOfCompunding = (int) (timeInYear * 4);
			interval = (float) 0.25; // ..1/4
		}
		if (fixedDepositForm.getPaymentType().equalsIgnoreCase("halfYearly")) {
			freqOfCompunding = (int) (timeInYear * 2);
			interval = (float) 0.5; // ..1/2;
		}
		if (fixedDepositForm.getPaymentType().equalsIgnoreCase("yearly")) {
			freqOfCompunding = Integer.parseInt(timeInYear.toString());
			interval = 1;
		}

		double totalFDAmount = 0d;
		int n = 1;
		// int n= fixedDepositForm.getFdTenure();

		for (int i = 1; i <= freqOfCompunding; i++) {

			if (timeInYear < 0)
				break;
			double temp = this.getCompoundMaturityAmount(fixedDepositForm.getFdAmount(), rateOfInterest, 12,
					timeInYear);
			totalFDAmount = totalFDAmount + temp;

			timeInYear = timeInYear - interval;

		}
		return totalFDAmount + "," + freqOfCompunding;
		// return totalFDAmount;

	}

	public Date calculateDueDate(FixedDepositForm fixedDepositForm, Integer deductionDay) {

		Date dueDate = DateService.getCurrentDate();
		if(fixedDepositForm.getDeductionDay() != null)
			dueDate=DateService.setDate(dueDate, fixedDepositForm.getDeductionDay());
		
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
		if(fixedDepositForm.getDeductionDay() != null)
			dueDate=DateService.setDate(dueDate, fixedDepositForm.getDeductionDay());
		
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

	public Date calculateInterestPayOffDueDate(String payOffInterestType, Date maturityDate, Date dueDate) {

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

	public Date calculateDueDateOnContinuePayment(String paymentType, int deductionDay) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, deductionDay);
		Date dueDate = DateService.getCurrentDateTime();

		if (paymentType.equalsIgnoreCase("One-Time") || paymentType.equalsIgnoreCase("One Time")) {
			// dueDate will be the same date of deposit
		} else if (paymentType.equalsIgnoreCase("Monthly")) {
			dueDate = DateService.generateMonthsDate(dueDate, 1);
			dueDate = DateService.setDate(dueDate, deductionDay);

		} else if (paymentType.equalsIgnoreCase("Quarterly")) {

			dueDate = DateService.getQuaterlyDeductionDateOnContinuePayment(deductionDay);

		} else if (paymentType.equalsIgnoreCase("Half Yearly")) {

			dueDate = DateService.getHalfYearlyDeductionDateOnContinuePayment(deductionDay);

		} else if (paymentType.equalsIgnoreCase("Annually")) {
			dueDate = DateService.getAnnualDeductionDateOnContinuePayment();
		}
		return dueDate;
	}

	/*public void calculateExpectedMaturityAmount(Float premiumAmt, String premiumPaymentType, Integer tenure,
			String tenureType, Float interestRate) {
		Integer days = 0;

		Date maturityDate = null;
		Date currentDate = DateService.getCurrentDate();

		// Date premiumDate[] = new Date[5];
		ArrayList<Date> premiumDates = new ArrayList<Date>();
		ArrayList<Date> interestDates = new ArrayList<Date>();

		if (tenureType.equalsIgnoreCase("Month")) {
			maturityDate = DateService.generateMonthsDate(currentDate, tenure);
		} else if (tenureType.equalsIgnoreCase("Year")) {
			maturityDate = DateService.generateYearsDate(currentDate, tenure);
		} else if (tenureType.equalsIgnoreCase("Day")) {
			maturityDate = DateService.generateDaysDate(currentDate, tenure);
		}
		// maturityDate = DateService.generateDaysDate(maturityDate, -1);

		Date dt = currentDate;
		// Installments
		// -------------------------------------------------
		// 1st payment on deposit date
		premiumDates.add(dt);

		// Other premium installments
		if (premiumPaymentType.equalsIgnoreCase("Monthly")) {
			while (!dt.after(maturityDate)) {
				dt = DateService.generateMonthsDate(dt, 1);
				if (DateService.getDaysBetweenTwoDates(dt, maturityDate) > 0)
					premiumDates.add(dt);
			}
		} else if (premiumPaymentType.equalsIgnoreCase("Quarterly")) {
			while (!dt.after(maturityDate)) {
				dt = DateService.generateMonthsDate(dt, 3);
				if (DateService.getDaysBetweenTwoDates(dt, maturityDate) > 0) {
					premiumDates.add(dt);

				}
			}
		} else if (premiumPaymentType.equalsIgnoreCase("Annually")) {
			while (!dt.after(maturityDate)) {

				dt = DateService.generateMonthsDate(dt, 12);
				if (DateService.getDaysBetweenTwoDates(dt, maturityDate) > 0)
					premiumDates.add(dt);
			}
		} else if (premiumPaymentType.equalsIgnoreCase("Halh Yearly")
				|| premiumPaymentType.equalsIgnoreCase("Semi Annually")) {
			while (!dt.after(maturityDate)) {
				dt = DateService.generateMonthsDate(dt, 6);
				if (DateService.getDaysBetweenTwoDates(dt, maturityDate) > 0)
					premiumDates.add(dt);
			}
		}
		// -----------------------------------------------

		// Interest Dates
		// -----------------------------------------------
		// Get Bank configuration
		BankConfiguration bankConfig = ratesDAO.findAll();
		String interestCalculationBasis = bankConfig.getInterestCalculationBasis();

		dt = currentDate;
		// Other premium installments
		if (interestCalculationBasis.equalsIgnoreCase("Monthly")) {
			while (!dt.after(maturityDate)) {
				dt = DateService.getLastDateOfMonth(dt);
				interestDates.add(dt);

				dt = DateService.generateMonthsDate(dt, 1);
			}
		} else if (interestCalculationBasis.equalsIgnoreCase("Quarterly")) {
			while (!dt.after(maturityDate)) {
				dt = DateService.getLastDateOfMonth(dt);
				interestDates.add(dt);

				dt = DateService.generateMonthsDate(dt, 3);
			}
		} else if (interestCalculationBasis.equalsIgnoreCase("Annually")) {
			while (!dt.after(maturityDate)) {
				dt = DateService.getLastDateOfMonth(dt);
				interestDates.add(dt);
				dt = DateService.generateMonthsDate(dt, 12);
			}
		} else if (interestCalculationBasis.equalsIgnoreCase("Halh Yearly")
				|| premiumPaymentType.equalsIgnoreCase("Semi Annually")) {
			while (!dt.after(maturityDate)) {
				dt = DateService.getLastDateOfMonth(dt);
				interestDates.add(dt);

				dt = DateService.generateMonthsDate(dt, 6);
			}
		}
		// -----------------------------------------------
		Float compountDepositAmount = 0f;
		int intIndex = 0;
		for (int i = 0; i < premiumDates.size(); i++) {
			Date premiumDate = premiumDates.get(i);
			compountDepositAmount = compountDepositAmount + premiumAmt;

			Date nextPremiumDate = premiumDate;
			if (i < (premiumDates.size() - 1))
				nextPremiumDate = premiumDates.get(i + 1);

			intIndex = (intIndex != 0) ? intIndex + 1 : intIndex;
			for (int j = intIndex; j < interestDates.size(); j++) {
				if (!interestDates.get(j).after(nextPremiumDate)) {
					Date intrDate = DateService.generateDaysDate(interestDates.get(j), 1);

					int intrCalcDays = DateService.getDaysBetweenTwoDates(premiumDate, intrDate);
					Float IntrAmt = (compountDepositAmount * interestRate / 100) / 365 * intrCalcDays;
					compountDepositAmount = compountDepositAmount + IntrAmt;
					intIndex = j;

				}

				Date lastInterestPaidDate = interestDates.get(j - 1);
				int intrCalcDays = DateService.getDaysBetweenTwoDates(lastInterestPaidDate, nextPremiumDate);
				Float IntrAmt = (compountDepositAmount * interestRate / 100) / 365 * intrCalcDays;
				compountDepositAmount = compountDepositAmount + IntrAmt;
				intIndex = j;

				continue;

			}
		}

	}*/

	public Date getMaturityDate(String tenureType, int tenure) {
		Date maturityDate = null;
		Date currentDate = DateService.getCurrentDate();

		if (tenureType.equalsIgnoreCase("Month")) {
			maturityDate = DateService.generateMonthsDate(currentDate, tenure);
		} else if (tenureType.equalsIgnoreCase("Year")) {
			maturityDate = DateService.generateYearsDate(currentDate, tenure);
		} else {
			maturityDate = DateService.generateDaysDate(currentDate, tenure);
		}

		return maturityDate;
	}

	public Date getMaturityDate(String payoffInterestType, Date gestationEndDate, Integer emiTimes) {
		Date maturityDate = null;
		List<Date> payOffDates = this.getPayoffDatesForFixedAmountEmi(DateService.addDays(gestationEndDate, 1),
				payoffInterestType, emiTimes);
		maturityDate = payOffDates.get(payOffDates.size() - 1);
		return maturityDate;
	}

	public List<Date> getPayoffDatesForFixedAmountEmi(Date fromDate, String payoffInterestType, Integer emiTimes) {

		List<Date> payoffDates = new ArrayList();

		for (int i = 0; i <= emiTimes; i++) {

			int month = DateService.getMonth(fromDate);
			if (payoffInterestType.equalsIgnoreCase(Constants.MONTHLY)) {
				payoffDates.add(DateService.getLastDateOfMonth(fromDate));
				fromDate = DateService.getFirstDateOfNextMonth(fromDate);

			} else if (payoffInterestType.equalsIgnoreCase(Constants.QUARTERLY)) {
				if ((month == 2 || month == 5 || month == 8 || month == 11)) {

					payoffDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 3);

				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			} else if (payoffInterestType.equalsIgnoreCase(Constants.HALFYEARLY)) {
				if ((month == 2 || month == 8)) {

					payoffDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 6);
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			} else if (payoffInterestType.equalsIgnoreCase(Constants.ANNUALLY)) {
				if ((month == 2)) {
					payoffDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 12);
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			}

		}
		return payoffDates;
	}

	

	public List<Date> getDepositDates(Date fromDate, Date maturityDate, FixedDepositForm fixedDepositForm) {

		List<Date> depositDates = new ArrayList();

		Date dueDate = fromDate; // fixedDepositForm.getFdDeductDate();

		// First Deposit amount at the time of deposit creation
		depositDates.add(fromDate);

		int monthsToAdd = 0;
		if (fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.MONTHLY))
			monthsToAdd = 1;
		else if (fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.QUARTERLY))
			monthsToAdd = 3;
		else if (fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.FULLYEARLY)
				|| fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.ANNUALLY))
			monthsToAdd = 12;
		else if (fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.HALFYEARLY))
			monthsToAdd = 6;

		// From next due Deposits
		while (true) {
			dueDate = DateService.addDays(DateService.generateMonthsDate(dueDate, monthsToAdd), 1);
			if (!fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.ONETIME)) {
				dueDate = DateService.setDate(dueDate, fixedDepositForm.getDeductionDay());
			}
			if (monthsToAdd == 0)
				break;
			if (DateService.getDaysBetweenTwoDates(dueDate, maturityDate) > 0) {
				depositDates.add(dueDate);

			} else

				break;
		}
		return depositDates;
	}

	public List<Date> getDepositDates(Date fromDate, Date maturityDate, Deposit deposit) {

		List<Date> depositDates = new ArrayList();

		// Get last Due Date
		Date dueDate = deposit.getDueDate(); // fixedDepositForm.getFdDeductDate();

		// Get the next due dates for whole tenure
		int monthsToAdd = 0;
		if (deposit.getPaymentType().equalsIgnoreCase(Constants.MONTHLY))
			monthsToAdd = 1;
		else if (deposit.getPaymentType().equalsIgnoreCase(Constants.QUARTERLY))
			monthsToAdd = 3;
		else if (deposit.getPaymentType().equalsIgnoreCase(Constants.FULLYEARLY)
				|| deposit.getPaymentType().equalsIgnoreCase(Constants.ANNUALLY))
			monthsToAdd = 12;
		else if (deposit.getPaymentType().equalsIgnoreCase(Constants.HALFYEARLY))
			monthsToAdd = 6;

		// From next due Deposits
		while (true) {
			dueDate = DateService.addDays(DateService.generateMonthsDate(dueDate, monthsToAdd), 1);
//			if (!deposit.getPaymentType().equalsIgnoreCase(Constants.ONETIME)) {
//				dueDate = DateService.setDate(dueDate, fixedDepositForm.getDeductionDay());
//			}
			if (monthsToAdd == 0)
				break;
			if (DateService.getDaysBetweenTwoDates(dueDate, maturityDate) > 0) {
				depositDates.add(dueDate);

			} else

				break;
		}
		return depositDates;
	}

	public List<Date> getInterestDates(Date fromDate, Date maturityDate) {
		// Date fromDate = DateService.getCurrentDate();
		Integer daysDifference = DateService.getDaysBetweenTwoDates(fromDate, DateService.getLastDateOfMonth(fromDate));

		List<Date> interestDates = new ArrayList();
		while (true) {

			// fromDate = DateService.getLastDateOfMonth(fromDate);
			interestDates.add(DateService.getLastDateOfMonth(fromDate));

			fromDate = DateService.getFirstDateOfNextMonth(fromDate);
			daysDifference = DateService.getDaysBetweenTwoDates(fromDate, DateService.getLastDateOfMonth(fromDate));

			if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate), maturityDate) < 0) {

				interestDates.add(maturityDate);
				daysDifference = 0;
				break;
			}

		}
		return interestDates;
	}

	

	/*public List<Interest> getInterestBreakup(Date fromDate, Date maturityDate, FixedDepositForm fixedDepositForm,
			Float interestRate, int mod) {



		//List<Date> interestList = this.getInterestDatesForModification(fromDate, maturityDate,fixedDepositForm.getIntr);
		List<Date> depositList = this.getDepositDates(DateService.getCurrentDate(), maturityDate, fixedDepositForm);

		if (mod == 1)
			depositList.remove(0);
		List<Interest> interestClassList = new ArrayList<>();

		Double depositAmt = (double) fixedDepositForm.getFdAmount();
		Double compoundPrincipalAmt = 0.0;
		Double compoundInt = 0.0;
		int k = 0;
		int dateDiff = 0;
		Date lastInterestDate = null;
		Boolean isLastTransactionInterest = false;

		for (int i = 0; i < depositList.size(); i++) {
			Date depositDate = depositList.get(i);
			Date nextDepositDate = depositDate;
			if (i < depositList.size() - 1)
				nextDepositDate = depositList.get(i + 1);

			compoundPrincipalAmt = compoundPrincipalAmt + depositAmt;

			for (int j = k; j < interestList.size(); j++) {
				// if next deposit date < interest date then
				// add the deposit amount to compundAmt

				Date interestDate = interestList.get(j);

				// For Last Deposit skip below
				if (DateService.getDaysBetweenTwoDates(depositDate, nextDepositDate) > 0
						&& DateService.getDaysBetweenTwoDates(nextDepositDate, interestDate) > 0) {
					dateDiff = DateService.getDaysBetweenTwoDates(depositDate, nextDepositDate);

					if (lastInterestDate != null && isLastTransactionInterest) {
						if (DateService.getMonth(lastInterestDate) - DateService.getMonth(nextDepositDate) != 0)
							dateDiff = dateDiff - 1;
					} else if (DateService.getMonth(depositDate) - DateService.getMonth(nextDepositDate) == 0) {
						// Deducting one day is required. Otherwise from last
						// month
						// interest to
						// next Payment/Withdraw system will calculate one extra
						// day
						dateDiff = dateDiff - 1;

					}

					Double interestAmount = (compoundPrincipalAmt * interestRate / 100) / 365 * dateDiff;
					// Double interestAmount = compoundPrincipalAmt
					// (Math.pow((1+((interestRate/100)/365)),(365
					// (dateDiff/365))))-compoundPrincipalAmt;
					compoundInt = compoundInt + interestAmount;

					isLastTransactionInterest = false;
					k = j;
					break;
				} else if (i == depositList.size() - 1
						&& DateService.getDaysBetweenTwoDates(nextDepositDate, interestDate) > 0) {
					// this is for last month
					dateDiff = DateService.getDaysBetweenTwoDates(depositDate, interestDate) + 1;
					if (lastInterestDate != null && isLastTransactionInterest) {
						dateDiff = dateDiff - 1;
					}
					
					if (dateDiff > 0) {
						Double interestAmount = (compoundPrincipalAmt * interestRate / 100) / 365 * dateDiff;
						// Double interestAmount = compoundPrincipalAmt
						// (Math.pow((1+((interestRate/100)/365)),(365
						// (dateDiff/365))))-compoundPrincipalAmt;
						compoundInt = compoundInt + interestAmount;

						compoundPrincipalAmt = compoundPrincipalAmt + interestAmount;
						depositDate = interestDate;
						Interest interest = new Interest();
						interest.setInterestRate(round((double) interestRate, 2));
						interest.setInterestAmount(round(compoundInt, 2));
						interest.setInterestDate(interestDate);
						interest.setDepositId(fixedDepositForm.getDepositId());
						interest.setFinancialYear(DateService.getFinancialYear(interestDate));
						//interest.setInterestSum(round(compoundPrincipalAmt, 2));
						interestClassList.add(interest);
						compoundInt = 0.0;

						lastInterestDate = interestDate;
						isLastTransactionInterest = true;

						k++;
						continue;
					}
				}
				if (DateService.getDaysBetweenTwoDates(interestDate, maturityDate) <= 0)
					compoundInt = 0.0;

				dateDiff = DateService.getDaysBetweenTwoDates(depositDate, interestDate) + 1;

				if (lastInterestDate != null && isLastTransactionInterest) {
					// if (DateService.getMonth(lastInterestDate) -
					// DateService.getMonth(nextDepositDate) == 0)
					dateDiff = dateDiff - 1;
				}
				// if(i==0)
				// dateDiff = dateDiff +1;
				Double interestAmount = (compoundPrincipalAmt * interestRate / 100) / 365 * dateDiff;

				// Double interestAmount = compoundPrincipalAmt
				// (Math.pow((1+((interestRate/100)/365)),(365
				// (dateDiff/365))))-compoundPrincipalAmt;
				interestAmount = interestAmount + compoundInt;
				compoundPrincipalAmt = compoundPrincipalAmt + interestAmount;
				depositDate = interestDate;
				Interest interest = new Interest();
				interest.setInterestRate(round((double) interestRate, 2));
				interest.setInterestAmount(round(interestAmount, 2));
				interest.setInterestDate(interestDate);
				interest.setDepositId(fixedDepositForm.getDepositId());
				interest.setFinancialYear(DateService.getFinancialYear(interestDate));
				//interest.setInterestSum(round(compoundPrincipalAmt, 2));
				interestClassList.add(interest);
				compoundInt = 0.0;

				lastInterestDate = interestDate;
				isLastTransactionInterest = true;
				k++;
			}

		}

		return interestClassList;
	}*/

	private HashMap<String, Double> getTotalInterestAmountFinancialYearWise(List<Interest> interestList) {
		// SimpleDateFormat df = new SimpleDateFormat("yyyy");
		// String year = df.format(DateService.getCurrentDate());

		String currentYear = DateService.getFinancialYear(DateService.getCurrentDate());
		String prevYear = currentYear;

		Double totalInterest = 0.0;
		HashMap<String, Double> totalInterestYearWise = new HashMap<>();

		for (int i = 0; i < interestList.size(); i++) {
			currentYear = interestList.get(i).getFinancialYear();
			if (prevYear.equals(currentYear)) {
				totalInterest = totalInterest + interestList.get(i).getInterestAmount();
			} else {
				totalInterestYearWise.put(prevYear, totalInterest);
				totalInterest = 0.0;
				totalInterest = totalInterest + interestList.get(i).getInterestAmount();
			}

			prevYear = currentYear;
		}

		if (!totalInterestYearWise.containsKey(prevYear))
			totalInterestYearWise.put(prevYear, totalInterest);

		return totalInterestYearWise;
	}

	

	public Integer getDivident(Date tdsDate, List<Date> tdsDates) {
		String prevFinancialYear = "";
		String financialYear = "";
		Integer totQuarterNo = 0;
		boolean checked = false;
		int isEntered = 0;
		for (int i = 0; i < tdsDates.size(); i++) {
			if (!checked && DateService.getDaysBetweenTwoDates(tdsDate, tdsDates.get(i)) != 0) {
				continue;
			} else {
				checked = true;
				financialYear = DateService.getFinancialYear(tdsDates.get(i));
				if (isEntered == 0) {
					prevFinancialYear = financialYear;
					isEntered = 1;
				}

				if (prevFinancialYear.equalsIgnoreCase(financialYear))
					totQuarterNo = totQuarterNo + 1;
				else {
					break;
				}

			}

		}

		return totQuarterNo;
	}

	public static double round(double value, int places) {
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

	public Double getTotalDepositAmount(FixedDepositForm fixedDepositForm, Date maturityDate, Double depositAmount) {
		List<Date> depositDates = this.getDepositDates(DateService.getCurrentDate(), maturityDate, fixedDepositForm);
		return depositAmount * depositDates.size();
	}

	public Float getTotalInterestAmount(List<Interest> interestList) {

		Float interestAmt = 0f;

		for (int i = 0; i < interestList.size(); i++) {
			interestAmt += Float.parseFloat(interestList.get(i).getInterestAmount().toString());
		}

		return interestAmt;
	}

	public Float getLastInterestAmount(List<Interest> interestList) {

		Float interestAmt = 0f;

		if (interestList.size() > 0)
			interestAmt = Float.parseFloat(interestList.get(interestList.size() - 1).getInterestAmount().toString());

		return interestAmt;
	}

	public Float getTotalTDSAmount(Date fromDate, Date maturityDate, String customerCategory,
			FixedDepositForm fixedDepositForm, List<Interest> interestList) {

		Float tdsAmt = 0f;
		// List<TDS> tdsList =
		// this.getTDSBreakupFromConfirmationDeposit(fromDate, maturityDate,
		// customerCategory, fixedDepositForm,
		// interestList);
		// for (int i = 0; i < tdsList.size(); i++) {
		// tdsAmt += Float.parseFloat(tdsList.get(i).getTdsAmount().toString());
		// }
		return tdsAmt;
	}

	// -------------Modification Start----------------------------------
	public List<Date> getInterestDatesForModification(Date fromDate, Date maturityDate, String interestCalculationBasis) {

		List<Date> interestDates = new ArrayList();

		while (true) {

			int month = DateService.getMonth(fromDate);
			if (interestCalculationBasis.equalsIgnoreCase(Constants.QUARTERLY)) {
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
			} else if (interestCalculationBasis.equalsIgnoreCase(Constants.MONTHLY)) {
				if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate), maturityDate) < 0) {

					interestDates.add(maturityDate);
					break;
				}

				interestDates.add(DateService.getLastDateOfMonth(fromDate));
				fromDate = DateService.getFirstDateOfNextMonth(fromDate);

			}

		}
		return interestDates;
	}

	public List<Date> getDepositDatesForModification(FixedDepositForm fixedDepositForm, Date maturityDate) {

		List<Date> depositDates = new ArrayList();
		// Date fromDate = DateService.getCurrentDate();
		Date dueDate = fixedDepositForm.getFdDeductDate();
		if (dueDate == null || fixedDepositForm.getPaymentType() == null)
			return null;

		depositDates.add(dueDate);
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

		// From next due Deposits
		while (true) {

			dueDate = DateService.addDays(DateService.generateMonthsDate(dueDate, monthsToAdd), 1);
			if (monthsToAdd == 0)
				break;
			if (DateService.getDaysBetweenTwoDates(dueDate, maturityDate) > 0) {
				depositDates.add(dueDate);
			} else
				break;
		}

		return depositDates;
	}

	/*public List<Interest> getInterestBreakupForModification(Date fromDate, Date maturityDate,
			FixedDepositForm fixedDepositForm, Deposit deposit) {
		// Date maturityDate =
		// this.getMaturityDate(fixedDepositForm.getFdTenureType(),
		// fixedDepositForm.get //Date fromDate = DateService.getCurrentDate();
		List<Date> interestList = this.getInterestDatesForModification(fromDate, maturityDate);

		List<Date> depositList = this.getDepositDatesForModification(fixedDepositForm, maturityDate);
		Long depositId = fixedDepositForm.getDepositId();
		Date lastInterestDate = null;

		// Get last interest calculated/adjusted for the period from
		// Distribution table.
		// It is required records to calculated the interest till today for
		// which
		// interest is not yet calculated or interest calculated but adjusted
		// for withdraw
		Distribution lastInterestCalculated = paymentDistributionDAO.getLastInterestCalculatedOrAdjusted(depositId,
				DateService.getCurrentDate());

		Date firstDateOfMonth = null;
		if (lastInterestCalculated != null)
			firstDateOfMonth = DateService.addDays(lastInterestCalculated.getDistributionDate(), 1);
		else
			firstDateOfMonth = deposit.getCreatedDate();

		Date lastDateOfMonth = DateService.getLastDateOfCurrentMonth();

		// Last modification
		Double modifiedDepositAmount = deposit.getDepositAmount();
		if (fixedDepositForm.getFdAmount() != null && fixedDepositForm.getFdAmount() > 0)
			modifiedDepositAmount = round((double) fixedDepositForm.getFdAmount(), 2);

		Double modifiedInterestRate = (double) (deposit.getModifiedInterestRate() == null ? deposit.getInterestRate()
				: deposit.getModifiedInterestRate());
		if (fixedDepositForm.getFdInterest() > 0)
			modifiedInterestRate = round((double) fixedDepositForm.getFdInterest(), 2);

		Float preiviousInterestRate = depositModificationDAO.getPreviousDepositRate(depositId, fromDate);

		// ----------------------------
		Boolean isAdjustmentDone = false;
		// get records of InterestAdjustment from last interest calculation
		Distribution lastAdjustment = paymentDistributionDAO.getFirstAdjustmentAfterLastInterest(depositId);

		// check if this month interest is already deducted or not.
		// if deducted then dont allow to deduct anymore

		// If after that any interest adjustment has made
		// i.e. interest took out what has given
		// Then system will ignore the interest calculation

		// Case: Suppose, some withdraw has been done on 10th September, that
		// means.
		// Before withdraw Interest calculation has done.
		// So if system finds that interest calculation has done for this month,
		// it will be wrong. Because system has taken out money/adjusted the
		// interests
		// that are already given.

		if (lastAdjustment != null) {
			if (DateService.getDaysBetweenTwoDates(lastInterestCalculated.getDistributionDate(),
					lastAdjustment.getDistributionDate()) >= 0)
				isAdjustmentDone = true;
			else {
				isAdjustmentDone = false;

			}
		}

		if (isAdjustmentDone) {
			return this.getInterestBreakupForModificationAfterAdjustment(fromDate, maturityDate, deposit, interestList,
					depositList);
		}

		// ----------------------------

		// Distributions in modification month
		List<Distribution> distributionList = paymentDistributionDAO.getDistributionListFrom(depositId,
				lastInterestCalculated != null ? lastInterestCalculated.getId() : 0);

		// Last Distribution before firstDateOfMonth
		// Distribution lastDistributionOfPreviousMonth = paymentDistributionDAO
		// .getLastDistributionOfPreviousMonth(depositId, firstDateOfMonth);
		Date lastDistributionDate = lastInterestCalculated == null ? null
				: lastInterestCalculated.getDistributionDate();
		Double balAmount = lastInterestCalculated == null ? 0.0 : lastInterestCalculated.getTotalBalance();

		List<Interest> interestClassList = new ArrayList<>();
		Boolean isLastTransactionInterest = false;
		// Double compoundAmt = 0.0;
		int dateDiff = 0;
		Double intAmt = 0.0;

		int n = 0;
		int j = 0;
		Double compoundInterest = 0.0;
		for (int i = 0; i < interestList.size(); i++) {
			Date interestDate = interestList.get(i);

			// if (i > 0)
			// firstDateOfMonth = DateService.getFirstDateOfMonth(interestDate);
			lastDateOfMonth = DateService.getLastDateOfMonth(interestDate);

			// Payment/withdraw etc is done
			if (distributionList != null) {
				for (int k = j; k < distributionList.size(); k++) {
					Distribution distribution = distributionList.get(k);
					if (lastDistributionDate == null) {
						firstDateOfMonth = distribution.getDistributionDate();
					} else {

						int dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth,
								distribution.getDistributionDate());

						if ((distribution.getAction().equalsIgnoreCase("TDS")
								|| distribution.getAction().equalsIgnoreCase("PAYOFF"))) {
							balAmount = distribution.getTotalBalance();
							isLastTransactionInterest = true;

						}
						if (dayDiff > 0) {
							intAmt = (balAmount * preiviousInterestRate / 100) / 365 * dayDiff;
							compoundInterest = compoundInterest + intAmt;
							// totalInterestAmt = totalInterestAmt + intAmt;
							isLastTransactionInterest = false;
						}

					}

					System.out.println("Rate: " + preiviousInterestRate + " ---- " + "Distribution Date: "
							+ distribution.getDistributionDate());
					balAmount = distribution.getTotalBalance();
					firstDateOfMonth = distribution.getDistributionDate();
					lastDistributionDate = distribution.getDistributionDate();
					if (isLastTransactionInterest)
						firstDateOfMonth = DateService.addDays(distribution.getDistributionDate(), 1);

					if (DateService.getDaysBetweenTwoDates(distribution.getDistributionDate(), interestDate) <= 0) {
						j = k;
						break;
					}

					j++;
				}
			}

			// future payment
			if (depositList != null) {

				for (int m = n; m < depositList.size(); m++) {
					Date depositDate = depositList.get(m);

					if (DateService.getDaysBetweenTwoDates(depositDate, interestDate) < 0) {
						n = m;
						break;
					}
					// if (DateService.getDaysBetweenTwoDates(firstDateOfMonth,
					// depositDate) < 0) {
					// n = m;
					// continue;
					// }
					int dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth, depositDate);
					// if(isLastTransactionInterest)
					// dayDiff = dayDiff - 1;

					if (dayDiff > 0) {
						intAmt = (balAmount * modifiedInterestRate / 100) / 365 * dayDiff;
						balAmount = balAmount + modifiedDepositAmount;
						// balAmount = balAmount + intAmt;
						firstDateOfMonth = DateService.generateDaysDate(depositDate, 1);
						compoundInterest = compoundInterest + intAmt;
					}

					isLastTransactionInterest = false;
					n++;
				}
			}

			if (DateService.getDaysBetweenTwoDates(maturityDate, lastDateOfMonth) >= 0)
				lastDateOfMonth = maturityDate;

			// int dayDiff =
			// DateService.getDaysBetweenTwoDates(firstDateOfMonth,
			// lastDateOfMonth) + 1;
			int dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth, interestDate) + 1;

			// if (lastInterestDate != null && isLastTransactionInterest) {
			//// if (DateService.getMonth(lastInterestDate) -
			// DateService.getMonth(nextDepositDate) != 0)
			// dayDiff = dayDiff -1;
			// }
			//

			intAmt = (balAmount * modifiedInterestRate / 100) / 365 * dayDiff;

			compoundInterest = compoundInterest + intAmt;
			balAmount = compoundInterest + balAmount;

			Interest interest = new Interest();
			interest.setInterestRate((double) modifiedInterestRate);
			interest.setInterestAmount(round(compoundInterest, 2));
			interest.setInterestDate(interestDate);
			interest.setDepositId(depositId);
			interest.setFinancialYear(DateService.getFinancialYear(interestDate));
			//interest.setInterestSum(round(balAmount, 2));
			interestClassList.add(interest);

			lastInterestDate = interestDate;
			firstDateOfMonth = DateService.addDays(interestDate, 1);
			isLastTransactionInterest = true;
			intAmt = 0.0;
			compoundInterest = 0.0;

		}

		return interestClassList;
	}*/

	/*public List<Interest> getInterestBreakupForModificationForTenureChange(Date fromDate, Date maturityDate,
			FixedDepositForm fixedDepositForm, Deposit deposit, Distribution lastBaseLine) {
		// Date maturityDate =
		// this.getMaturityDate(fixedDepositForm.getFdTenureType(),
		// fixedDepositForm.get //Date fromDate = DateService.getCurrentDate();
		List<Date> interestList = this.getInterestDatesForModification(fromDate, maturityDate);

		List<Date> depositList = this.getDepositDatesForModification(fixedDepositForm, maturityDate);
		Long depositId = fixedDepositForm.getDepositId();
		Date lastInterestDate = lastBaseLine.getDistributionDate();

		// // Get last interest calculated/adjusted for the period from
		// // Distribution table.
		// // It is required records to calculated the interest till today for
		// which
		// // interest is not yet calculated or interest calculated but adjusted
		// for withdraw
		// Distribution lastInterestCalculated =
		// paymentDistributionDAO.getLastInterestCalculatedOrAdjusted(depositId,
		// DateService.getCurrentDate());
		//
		Date firstDateOfMonth = DateService.addDays(lastBaseLine.getDistributionDate(), 1);
		;
		// if (lastInterestCalculated != null)
		// firstDateOfMonth =
		// DateService.addDays(lastInterestCalculated.getDistributionDate(), 1);
		// else
		// firstDateOfMonth = deposit.getCreatedDate();

		Date lastDateOfMonth = DateService.getLastDateOfCurrentMonth();

		// Last modification
		Double modifiedDepositAmount = deposit.getDepositAmount();
		if (fixedDepositForm.getFdAmount() != null && fixedDepositForm.getFdAmount() > 0)
			modifiedDepositAmount = round((double) fixedDepositForm.getFdAmount(), 2);

		Double modifiedInterestRate = (double) (deposit.getModifiedInterestRate() == null ? deposit.getInterestRate()
				: deposit.getModifiedInterestRate());
		if (fixedDepositForm.getFdInterest() > 0)
			modifiedInterestRate = round((double) fixedDepositForm.getFdInterest(), 2);

		// Float preiviousInterestRate =
		// depositModificationDAO.getPreviousDepositRate(depositId, fromDate);

		// ----------------------------
		// Boolean isAdjustmentDone = false;
		// get records of InterestAdjustment from last interest calculation
		// Distribution lastAdjustment =
		// paymentDistributionDAO.getFirstAdjustmentAfterLastInterest(depositId);

		// check if this month interest is already deducted or not.
		// if deducted then dont allow to deduct anymore

		// If after that any interest adjustment has made
		// i.e. interest took out what has given
		// Then system will ignore the interest calculation

		// Case: Suppose, some withdraw has been done on 10th September, that
		// means.
		// Before withdraw Interest calculation has done.
		// So if system finds that interest calculation has done for this month,
		// it will be wrong. Because system has taken out money/adjusted the
		// interests
		// that are already given.

		// ----------------------------

		// // Distributions in modification month
		// List<Distribution> distributionList =
		// paymentDistributionDAO.getDistributionListFrom(depositId,
		// lastInterestCalculated != null ? lastInterestCalculated.getId() : 0);
		//
		// // Last Distribution before firstDateOfMonth
		// // Distribution lastDistributionOfPreviousMonth =
		// paymentDistributionDAO
		// // .getLastDistributionOfPreviousMonth(depositId, firstDateOfMonth);
		// Date lastDistributionDate = lastInterestCalculated == null ? null
		// : lastInterestCalculated.getDistributionDate();
		// Double balAmount = lastInterestCalculated == null ? 0.0 :
		// lastInterestCalculated.getTotalBalance();
		Double balAmount = lastBaseLine.getTotalBalance();

		List<Interest> interestClassList = new ArrayList<>();
		Boolean isLastTransactionInterest = false;
		// Double compoundAmt = 0.0;
		int dateDiff = 0;
		Double intAmt = 0.0;

		int n = 0;
		int j = 0;
		Double compoundInterest = 0.0;
		for (int i = 0; i < interestList.size(); i++) {
			Date interestDate = interestList.get(i);

			// if (i > 0)
			// firstDateOfMonth = DateService.getFirstDateOfMonth(interestDate);
			lastDateOfMonth = DateService.getLastDateOfMonth(interestDate);

			// // Payment/withdraw etc is done
			// if (distributionList != null) {
			// for (int k = j; k < distributionList.size(); k++) {
			// Distribution distribution = distributionList.get(k);
			// if (lastDistributionDate == null) {
			// firstDateOfMonth = distribution.getDistributionDate();
			// } else {
			//
			// int dayDiff =
			// DateService.getDaysBetweenTwoDates(firstDateOfMonth,
			// distribution.getDistributionDate());
			//
			// if ((distribution.getAction().equalsIgnoreCase("TDS")
			// || distribution.getAction().equalsIgnoreCase("PAYOFF"))) {
			// balAmount = distribution.getTotalBalance();
			// isLastTransactionInterest = true;
			//
			// }
			// if (dayDiff > 0) {
			// intAmt = (balAmount * preiviousInterestRate / 100) / 365 *
			// dayDiff;
			// compoundInterest = compoundInterest + intAmt;
			// // totalInterestAmt = totalInterestAmt + intAmt;
			// isLastTransactionInterest = false;
			// }
			//
			// }
			//
			// System.out.println("Rate: " + preiviousInterestRate + " ---- " +
			// "Distribution Date: "
			// + distribution.getDistributionDate());
			// balAmount = distribution.getTotalBalance();
			// firstDateOfMonth = distribution.getDistributionDate();
			// lastDistributionDate = distribution.getDistributionDate();
			// if (isLastTransactionInterest)
			// firstDateOfMonth =
			// DateService.addDays(distribution.getDistributionDate(), 1);
			//
			// if
			// (DateService.getDaysBetweenTwoDates(distribution.getDistributionDate(),
			// interestDate) <= 0) {
			// j = k;
			// break;
			// }
			//
			// j++;
			// }
			// }

			// future payment
			if (depositList != null) {

				for (int m = n; m < depositList.size(); m++) {
					Date depositDate = depositList.get(m);

					if (DateService.getDaysBetweenTwoDates(depositDate, interestDate) < 0) {
						n = m;
						break;
					}
					// if (DateService.getDaysBetweenTwoDates(firstDateOfMonth,
					// depositDate) < 0) {
					// n = m;
					// continue;
					// }
					int dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth, depositDate);
					// if(isLastTransactionInterest)
					// dayDiff = dayDiff - 1;

					if (dayDiff > 0) {
						intAmt = (balAmount * modifiedInterestRate / 100) / 365 * dayDiff;
						balAmount = balAmount + modifiedDepositAmount;
						// balAmount = balAmount + intAmt;
						firstDateOfMonth = DateService.generateDaysDate(depositDate, 1);
						compoundInterest = compoundInterest + intAmt;
					}

					isLastTransactionInterest = false;
					n++;
				}
			}

			if (DateService.getDaysBetweenTwoDates(maturityDate, lastDateOfMonth) >= 0)
				lastDateOfMonth = maturityDate;

			// int dayDiff =
			// DateService.getDaysBetweenTwoDates(firstDateOfMonth,
			// lastDateOfMonth) + 1;
			int dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth, interestDate) + 1;

			// if (lastInterestDate != null && isLastTransactionInterest) {
			//// if (DateService.getMonth(lastInterestDate) -
			// DateService.getMonth(nextDepositDate) != 0)
			// dayDiff = dayDiff -1;
			// }
			//

			intAmt = (balAmount * modifiedInterestRate / 100) / 365 * dayDiff;

			compoundInterest = compoundInterest + intAmt;
			balAmount = compoundInterest + balAmount;

			Interest interest = new Interest();
			interest.setInterestRate((double) modifiedInterestRate);
			interest.setInterestAmount(round(compoundInterest, 2));
			interest.setInterestDate(interestDate);
			interest.setDepositId(depositId);
			interest.setFinancialYear(DateService.getFinancialYear(interestDate));
			//interest.setInterestSum(round(balAmount, 2));
			interestClassList.add(interest);

			lastInterestDate = interestDate;
			firstDateOfMonth = DateService.addDays(interestDate, 1);
			isLastTransactionInterest = true;
			intAmt = 0.0;
			compoundInterest = 0.0;

		}

		return interestClassList;
	}

*/	public FixedDepositForm setParametersForProjectedInterest(FixedDepositForm fixedDepositForm) {
		Float interestRate = 0f;
		String tenureType = "";
		Integer tenure = 0;
		Double depositAmount = 0d;
		String paymentType = "";
		Date dueDate = null;
		Date maturityDate = null;

		Long depositId = fixedDepositForm.getDepositId();
		Deposit deposit = fixedDepositDao.getDeposit(depositId);

		interestRate = deposit.getInterestRate();
		depositAmount = deposit.getDepositAmount();
		paymentType = deposit.getPaymentType();
		dueDate = deposit.getDueDate();
		if (deposit.getStatus() == Constants.CLOSESTATUS) {
			maturityDate = deposit.getClosingDate();
			tenureType = null;
			tenure = null;
		} else {
			maturityDate = deposit.getMaturityDate();
			tenureType = deposit.getTenureType();
			tenure = deposit.getTenure();
		}

		// Overwrite the parameters if deposit has modified
		DepositModification depositModification = depositModificationDAO.getLastByDepositId(depositId);
		if (depositModification != null) {
			interestRate = depositModification.getInterestRate();
			tenureType = depositModification.getTenureType();
			tenure = depositModification.getTenure();
			depositAmount = depositModification.getDepositAmount();
			paymentType = depositModification.getPaymentType();
			maturityDate = depositModification.getMaturityDate();
		}
		fixedDepositForm.setDepositId(depositId);
		fixedDepositForm.setFdInterest(interestRate);

		fixedDepositForm.setFdTenure(tenure);
		fixedDepositForm.setFdTenureType(tenureType);
		fixedDepositForm.setFdAmount(depositAmount);
		fixedDepositForm.setPaymentType(paymentType);
		fixedDepositForm.setFdDeductDate(dueDate);
		fixedDepositForm.setMaturityDate(maturityDate);

		return fixedDepositForm;
	}

	// Maturity date is required to Force Close the deposit
	public void getTDSBreakupForModification(FixedDepositForm fixedDepositForm, List<Interest> interestList,
			Date maturityDate, Deposit deposit, List<DepositHolder> depositHolderList) {

		// // Delete Earlier calculated TDS upto current date
		// Long depositId = fixedDepositForm.getDepositId();
		// tdsDAO.deleteTDSFromCurrentDate(depositId);
		//
		// Double tdsAmount = 0.0;
		// Double calculatedTDSAmount = 0.0;
		// String financialYear = "";
		// Boolean isForm15GSubmitted = false;
		//
		// // Get the maturity date of the deposit
		// if (maturityDate == null)
		// maturityDate =
		// this.getMaturityDate(fixedDepositForm.getFdTenureType(),
		// fixedDepositForm.getFdTenure());
		//
		// Date fromDate = DateService.getCurrentDate();
		//
		// List<Date> tdsDates = getTDSDates(fromDate, maturityDate);
		//
		// // Find out the total Interest financial year wise
		// // --------------------------------------------------
		// // Below statement might not include some
		// // interest with is already calculated and added to principal
		// // in same financial year
		// HashMap<String, Double> totalInterestAmountYearWise = this
		// .getTotalInterestAmountFinancialYearWise(interestList);
		//
		// HashMap<String, Double> yearWiseGivenInterest = interestDAO
		// .getTotInterestAddedToPrincipalByFinancialYear(depositId);
		//
		// // Check Tax deduction document(15G/15H) is submitted for the current
		// // financial year.
		// // If submitted, then tds will not be deducted.
		//// boolean isTDSDeductionAllowed = true;
		//// FormSubmission formSubmission =
		// formSubmissionDAO.findDepositId(depositId);
		//// if (formSubmission != null)
		//// isTDSDeductionAllowed = false;
		//
		//// if (!isTDSDeductionAllowed)
		//// return;
		//
		// // Get the total interest which is already calculated for the
		// financial
		// // year
		// for (Map.Entry<String, Double> entry :
		// totalInterestAmountYearWise.entrySet()) {
		// String finYear = entry.getKey();
		// Double interest = entry.getValue();
		// Double totInterest = yearWiseGivenInterest.get(finYear) == null ? 0d
		// : yearWiseGivenInterest.get(finYear);
		//
		// // Total Interest of a financial year
		// //-------------------------------------
		// totInterest = totInterest + interest;
		// entry.setValue(totInterest);
		// //-------------------------------------
		//
		// Double interestAmountPerRemainingQtr = 0d;
		//// Double interestGiven = 0d;
		//
		// for (int i = 0; i < tdsDates.size(); i++) {
		// Date tdsDate = tdsDates.get(i);
		// financialYear = DateService.getFinancialYear(tdsDate);
		// if (!financialYear.equalsIgnoreCase(finYear))
		// break;
		//
		// // if quarterly, then divisor will be maximum 4.
		// // if monthly, then divisor will be maximum 12
		// Integer divisor = getDivident(tdsDate, tdsDates);
		// Double interestAlreadyGivenInTheFinYear =
		// yearWiseGivenInterest.get(financialYear) == null ? 0d
		// : yearWiseGivenInterest.get(financialYear);
		//
		// // Interest Given while executing the routine needs to be added
		//// interestAlreadyGivenInTheFinYear = interestAlreadyGivenInTheFinYear
		// + interestGiven;
		//
		// Double totTDSAmount = 0d;
		// Double totCalculatedTDSAmount = 0d;
		//
		// // means last qtr/ last month
		// interestAmountPerRemainingQtr = totInterest / divisor;
		//
		//
		//
		//// interestGiven = interestGiven + interestAmountPerRemainingQtr;
		//
		//
		// // Now check the tds rate of all the deposit holders.
		// // tds will cut from the interest of each deposit holders
		//
		// for (int j = 0; j < depositHolderList.size(); j++) {
		// Rates customerConfig = ratesDAO
		// .getRatesByCategory(depositHolderList.get(j).getDepositHolderCategory());
		//
		// Float tdsRate = customerConfig.getTds();
		// Long minInetestAmountForTDS =
		// customerConfig.getMinIntAmtForTDSDeduction();
		// Float contribution = depositHolderList.get(j).getContribution();
		//
		// // the above interest amount will be divided by
		// // the remaining TDS deduction dates
		// Double contributedInterestAmount = interestAmountPerRemainingQtr *
		// contribution / 100;
		//
		// calculatedTDSAmount = contributedInterestAmount * tdsRate / 100;
		// totCalculatedTDSAmount = totCalculatedTDSAmount +
		// calculatedTDSAmount;
		//
		// if (totInterest > minInetestAmountForTDS) {
		// // TODO:
		// // if 15h /15g is submitted by the customer
		// // then tds amount will be 0
		// tdsAmount = calculatedTDSAmount;
		// } else {
		// tdsAmount = 0d;
		// }
		//
		// isForm15GSubmitted =
		// formSubmissionDAO.isFormSubmitted(depositHolderList.get(j).getCustomerId(),
		// financialYear);
		// if(isForm15GSubmitted)
		// tdsAmount = 0d;
		//
		// totTDSAmount = totTDSAmount + tdsAmount;
		//
		// // Customer TDS
		// CustomerTDS customerTDS = new CustomerTDS();
		// customerTDS.setTdsRate(Double.parseDouble(tdsRate.toString()));
		//
		// if (Double.isNaN(tdsAmount))
		// totTDSAmount = 0d;
		//
		// if (Double.isNaN(totCalculatedTDSAmount))
		// totCalculatedTDSAmount = 0d;
		//
		// customerTDS.setCalculatedTDSAmount(round(calculatedTDSAmount, 2));
		// customerTDS.setContributedTDSAmount(round(tdsAmount, 2));
		// customerTDS.setTdsDate(tdsDate);
		// customerTDS.setCustomerId(depositHolderList.get(j).getCustomerId());
		// customerTDS.setDepositId(fixedDepositForm.getDepositId());
		//
		// tdsDAO.insertCustomerTDS(customerTDS);
		//
		//// if()
		//
		// }
		//
		// // Save Total TDS
		// TDS tds = new TDS();
		//
		// if (Double.isNaN(tdsAmount))
		// totTDSAmount = 0d;
		//
		// if (Double.isNaN(totCalculatedTDSAmount))
		// totCalculatedTDSAmount = 0d;
		//
		// tds.setTdsAmount(round(totTDSAmount, 2));
		// tds.setCalculatedTDSAmount(round(totCalculatedTDSAmount, 2));
		// tds.setTdsCalcDate(tdsDate);
		// tds.setDepositId(fixedDepositForm.getDepositId());
		// tdsDAO.insert(tds);
		// }
		// }

	}

	/*
	 * public Distribution calculateInterestOnReducingRate(Long depositId,
	 * Double oldRate, Double modifiedRate) {
	 * 
	 * String action = "Interest";
	 * 
	 * // getting data from the intrest table of current month Integer month =
	 * DateService.getMonth(DateService.getCurrentDate()); Integer year =
	 * DateService.getYear(DateService.getCurrentDate());
	 * 
	 * Distribution lastInterestCalculated =
	 * paymentDistributionDAO.getLastInterestCalculated(depositId);
	 * 
	 * Date prevDistributionDate = null; Long lastInterestDistributionId = null;
	 * Double prevCompoundFixedAmt = null; Double prevCompoundVariableAmt =
	 * null; Double fixedIntAmt = 0d; Double variableIntAmt = 0d; Double
	 * compoundFixedAmt = 0d; Double compoundVariableAmt = 0d; Double
	 * totalFixedInterest = 0d; Double totalVariableInterest = 0d; int
	 * totalDaysDiff = 0;
	 * 
	 * Distribution currentDistribution = null;
	 * 
	 * // Interest already calculated, not for first time if
	 * (lastInterestCalculated != null) // Means interest has been // calculated
	 * previously { lastInterestDistributionId = lastInterestCalculated.getId();
	 * prevDistributionDate = lastInterestCalculated.getDistributionDate();
	 * prevCompoundFixedAmt = lastInterestCalculated.getCompoundFixedAmt();
	 * prevCompoundVariableAmt =
	 * lastInterestCalculated.getCompoundVariableAmt(); fixedIntAmt = 0d;
	 * variableIntAmt = 0d; compoundFixedAmt = 0d; compoundVariableAmt = 0d;
	 * totalDaysDiff = 0;
	 * 
	 * // always be Zero (0) for interest calculation Double currentFixedAmt =
	 * 0d; Double currentVariableAmt = 0d; Double totalBalance = 0d;
	 * 
	 * // // If interest is already calculated in current date then // // do not
	 * allow to calculate again // if
	 * (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(),
	 * prevDistributionDate) == 0) // return null;
	 * 
	 * // Fetch all the records starting from the last interest calculated
	 * List<Distribution> lastPaymentDistForDepWithdraw = paymentDistributionDAO
	 * .getAllDepWithdrawFromLastInterestCalc(depositId,
	 * lastInterestDistributionId);
	 * 
	 * // After interest calculation Payment or Withdraw has been made if
	 * (lastPaymentDistForDepWithdraw != null &&
	 * lastPaymentDistForDepWithdraw.size() > 0) { for (int i = 0; i <
	 * lastPaymentDistForDepWithdraw.size(); i++) { Distribution paymentDist =
	 * lastPaymentDistForDepWithdraw.get(i); Integer daysDifference =
	 * DateService.getDaysBetweenTwoDates(prevDistributionDate,
	 * paymentDist.getDistributionDate());
	 * 
	 * totalDaysDiff = totalDaysDiff + daysDifference;
	 * 
	 * fixedIntAmt = (prevCompoundFixedAmt * oldRate / 100) / 365 *
	 * daysDifference; variableIntAmt = (prevCompoundVariableAmt * oldRate /
	 * 100) / 365 * daysDifference;
	 * 
	 * totalFixedInterest = totalFixedInterest + fixedIntAmt;
	 * totalVariableInterest = totalVariableInterest + variableIntAmt;
	 * 
	 * totalBalance = paymentDist.getTotalBalance();
	 * 
	 * prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
	 * prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();
	 * prevDistributionDate = paymentDist.getDistributionDate();
	 * 
	 * } }
	 * 
	 * // Now calculate the interest amount // from last Transaction to current
	 * date Integer daysDifference =
	 * DateService.getDaysBetweenTwoDates(prevDistributionDate,
	 * DateService.getCurrentDate());
	 * 
	 * fixedIntAmt = (prevCompoundFixedAmt * oldRate / 100) / 365 *
	 * daysDifference; variableIntAmt = (prevCompoundVariableAmt * oldRate /
	 * 100) / 365 * daysDifference;
	 * 
	 * totalFixedInterest = totalFixedInterest + fixedIntAmt;
	 * totalVariableInterest = totalVariableInterest + variableIntAmt;
	 * 
	 * compoundFixedAmt = prevCompoundFixedAmt + currentFixedAmt +
	 * totalFixedInterest; compoundVariableAmt = prevCompoundVariableAmt +
	 * currentVariableAmt + totalVariableInterest; totalBalance =
	 * compoundFixedAmt + compoundVariableAmt;
	 * 
	 * totalDaysDiff = totalDaysDiff + daysDifference;
	 * 
	 * // Saving Distribution paymentDistribution = new Distribution();
	 * paymentDistribution.setDistributionDate(DateService.getCurrentDate());
	 * paymentDistribution.setDepositId(depositId);
	 * paymentDistribution.setDaysToCalcInterest(totalDaysDiff);
	 * paymentDistribution.setFixedInterest(round(totalFixedInterest, 2));
	 * paymentDistribution.setVariableInterest(round(totalVariableInterest, 2));
	 * paymentDistribution.setCompoundFixedAmt(round(compoundFixedAmt, 2));
	 * paymentDistribution.setCompoundVariableAmt(round(compoundVariableAmt,
	 * 2)); paymentDistribution.setAction(action);
	 * paymentDistribution.setTotalBalance(round(totalBalance, 2));
	 * currentDistribution =
	 * paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);
	 * 
	 * // --------------------------------------------------------------
	 * 
	 * // update the deposit table
	 * 
	 * deposit.setCurrentBalance(totalBalance);
	 * fixedDepositDao.updateFD(deposit);
	 * 
	 * // update interest table as interest deducted for the month Date
	 * currentdate = DateService.getCurrentDate(); if (interest != null) {
	 * interest.setInterestDeducted(1); interestDAO.update(interest); } } //
	 * First time interest is going to be calculated else { // Long
	 * distributionId = 0L; // Date distributionDate =
	 * DateService.getCurrentDate(); Date currentDate =
	 * DateService.getCurrentDate(); compoundFixedAmt = 0d; compoundVariableAmt
	 * = 0d; Double currentFixedAmt = 0d; Double currentVariableAmt = 0d; Double
	 * totalBalance = 0d;
	 * 
	 * // first time interest is going to calculate // consider multiple deposit
	 * can be performed by these days // Fetch all the records starting from the
	 * last interest calculated List<Distribution> lastPaymentDistForDepWithdraw
	 * = paymentDistributionDAO
	 * .getAllDepWithdrawFromLastInterestCalc(depositId, 0L);
	 * 
	 * // Payment or Withdraw has been made if (lastPaymentDistForDepWithdraw !=
	 * null && lastPaymentDistForDepWithdraw.size() > 0) { prevCompoundFixedAmt
	 * = 0d; prevCompoundVariableAmt = 0d; Integer daysDifference = 0;
	 * fixedIntAmt = 0d; variableIntAmt = 0d; totalDaysDiff = 0;
	 * 
	 * for (int i = 0; i < lastPaymentDistForDepWithdraw.size(); i++) {
	 * Distribution paymentDist = lastPaymentDistForDepWithdraw.get(i);
	 * 
	 * if (i == 0) { // distributionId = paymentDist.getId();
	 * prevDistributionDate = paymentDist.getDistributionDate();
	 * prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
	 * prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();
	 * 
	 * compoundFixedAmt = prevCompoundFixedAmt; compoundVariableAmt =
	 * prevCompoundVariableAmt; continue; }
	 * 
	 * daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
	 * paymentDist.getDistributionDate());
	 * 
	 * fixedIntAmt = (prevCompoundFixedAmt * interestRate / 100) / 365 *
	 * daysDifference; variableIntAmt = (prevCompoundVariableAmt * interestRate
	 * / 100) / 365 * daysDifference;
	 * 
	 * totalFixedInterest = totalFixedInterest + fixedIntAmt;
	 * totalVariableInterest = totalVariableInterest + variableIntAmt;
	 * 
	 * totalBalance = paymentDist.getTotalBalance();
	 * 
	 * prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
	 * prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();
	 * prevDistributionDate = paymentDist.getDistributionDate(); totalDaysDiff =
	 * totalDaysDiff + daysDifference;
	 * 
	 * // // current fixed amount will be zero // compoundFixedAmt =
	 * prevCompoundFixedAmt + currentFixedAmt // + fixedIntAmt; //
	 * compoundVariableAmt = prevCompoundVariableAmt + // currentVariableAmt +
	 * variableIntAmt; // // // calculate the total interest amount for all
	 * deposits // and // // withdraws // totalFixedInterest =
	 * totalFixedInterest + fixedIntAmt; // totalVariableInterest =
	 * totalVariableInterest + // variableIntAmt; // totalBalance = totalBalance
	 * + compoundFixedAmt + // compoundVariableAmt; // totalDaysDiff =
	 * totalDaysDiff + daysDifference; // // prevCompoundFixedAmt =
	 * compoundFixedAmt; // prevCompoundVariableAmt = compoundVariableAmt; //
	 * prevDistributionDate = paymentDist.getDistributionDate(); }
	 * 
	 * // Calculate interest of last deposit from last deposit date to //
	 * current date daysDifference =
	 * DateService.getDaysBetweenTwoDates(prevDistributionDate, currentDate);
	 * 
	 * totalDaysDiff = totalDaysDiff + daysDifference; totalFixedInterest =
	 * totalFixedInterest + (compoundFixedAmt * interestRate / 100) / 365 *
	 * daysDifference; totalVariableInterest = totalVariableInterest +
	 * (compoundVariableAmt * interestRate / 100) / 365 * daysDifference;
	 * 
	 * // current fixed amount will be zero compoundFixedAmt =
	 * prevCompoundFixedAmt + currentFixedAmt + totalFixedInterest;
	 * compoundVariableAmt = prevCompoundVariableAmt + currentVariableAmt +
	 * totalVariableInterest;
	 * 
	 * totalBalance = compoundFixedAmt + compoundVariableAmt;
	 * 
	 * // saving Distribution paymentDistribution = new Distribution();
	 * paymentDistribution.setDistributionDate(DateService.getCurrentDate());
	 * paymentDistribution.setDepositId(depositId);
	 * paymentDistribution.setDaysToCalcInterest(totalDaysDiff);
	 * paymentDistribution.setFixedInterest(round(totalFixedInterest, 2));
	 * paymentDistribution.setVariableInterest(round(totalVariableInterest, 2));
	 * paymentDistribution.setCompoundFixedAmt(round(compoundFixedAmt, 2));
	 * paymentDistribution.setCompoundVariableAmt(round(compoundVariableAmt,
	 * 2)); paymentDistribution.setAction(action);
	 * paymentDistribution.setTotalBalance(round(totalBalance, 2));
	 * currentDistribution =
	 * paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);
	 * 
	 * // update the deposit table // Deposit deposit =
	 * fixedDepositDao.findById(depositId);
	 * deposit.setCurrentBalance(round(totalBalance, 2));
	 * fixedDepositDao.updateFD(deposit);
	 * 
	 * // update interest table as interest deducted for the month Date
	 * currentdate = DateService.getCurrentDate(); if (interest != null) {
	 * interest.setInterestDeducted(1); interestDAO.update(interest); } } }
	 * return currentDistribution; }
	 */
	public void reduceInterest(Long depositId, Double modifiedRate, Double InterestAmountTillDate,
			String customerCategory, Long depositHolderId) {

		Double fixedIntAmt = 0d;
		Double variableIntAmt = 0d;
		Double totalFixedInterest = 0d;
		Double totalVariableInterest = 0d;
		int totalDaysDiff = 0;
		Long primaryCustId = 0l;
		Date dt = DateService.getCurrentDateTime();

		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);

		List<Distribution> distributionList = fixedDepositDao.getDistributionList(depositId);
		Distribution distribution = distributionList.get(0);
		Double prevCompoundFixedAmt = distribution.getCompoundFixedAmt();
		Double prevCompoundVariableAmt = distribution.getCompoundVariableAmt();
		Date prevDistributionDate = distribution.getDistributionDate();

		Double totalInterestWithReducingRate = 0d;
		Double totalInterestAlreayGiven = 0d;

		// last distribution
		Distribution lastDistribution = distributionList.get(distributionList.size() - 1);
		Double balFixedInterest = lastDistribution.getBalanceFixedInterest();
		Double balVariableInterest = lastDistribution.getBalanceVariableInterest();
		totalInterestAlreayGiven = balFixedInterest + balVariableInterest;

		Distribution lastInterestCalculated = paymentDistributionDAO.getLastInterestCalculated(depositId);

		if (lastInterestCalculated == null)
			return;

		for (int i = 1; i < distributionList.size(); i++) {
			Distribution currentDist = distributionList.get(i);

			if (DateService.getDaysBetweenTwoDates(currentDist.getDistributionDate(),
					lastInterestCalculated.getDistributionDate()) < 0)
				break;

			if (!currentDist.getAction().equalsIgnoreCase("Interest")) {
				Double fixedAmt = currentDist.getFixedAmt();
				Double variableAmt = currentDist.getVariableAmt();
				if (fixedAmt == null)
					fixedAmt = 0.0;// ((Distribution)distributionList.get(i-1)).getFixedAmt();
				if (variableAmt == null)
					variableAmt = currentDist.getDepositedAmt();

				Integer daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
						currentDist.getDistributionDate());

				totalDaysDiff = totalDaysDiff + daysDifference;

				fixedIntAmt = (prevCompoundFixedAmt * modifiedRate / 100) / 365 * daysDifference;
				variableIntAmt = (prevCompoundVariableAmt * modifiedRate / 100) / 365 * daysDifference;

				//////////////////////////
				// Total Interest. It will required to add at the time of
				////////////////////////// Interest Calculation
				totalFixedInterest = totalFixedInterest + fixedIntAmt;
				totalVariableInterest = totalVariableInterest + variableIntAmt;

				prevCompoundFixedAmt = prevCompoundFixedAmt + fixedAmt;
				prevCompoundVariableAmt = prevCompoundVariableAmt + variableAmt;

				prevDistributionDate = currentDist.getDistributionDate();

			} else // This is for 'Interest' records
			{
				//
				Integer daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
						currentDist.getDistributionDate());

				fixedIntAmt = (prevCompoundFixedAmt * modifiedRate / 100) / 365 * daysDifference;
				variableIntAmt = (prevCompoundVariableAmt * modifiedRate / 100) / 365 * daysDifference;

				totalFixedInterest = totalFixedInterest + fixedIntAmt;
				totalVariableInterest = totalVariableInterest + variableIntAmt;

				// totalInterestAlreayGiven = totalInterestAlreayGiven +
				// currentDist.getFixedInterest()
				// + currentDist.getVariableInterest();

				prevCompoundFixedAmt = prevCompoundFixedAmt + totalFixedInterest;
				prevCompoundVariableAmt = prevCompoundVariableAmt + totalVariableInterest;

				totalInterestWithReducingRate = totalFixedInterest + totalVariableInterest;
				prevDistributionDate = currentDist.getDistributionDate();
				// totalFixedInterest = 0d;
				// totalVariableInterest = 0d;

			}
		}

		// totalDaysDiff = totalDaysDiff + daysDifference;
		Double difference = totalInterestAlreayGiven - totalInterestWithReducingRate;

		String action = "Interest Adjustment";
		if (difference > 0) {

			Double amtToAdjust = difference * (-1); // withdrawForm.getWithdrawAmount();

			Double compoundFixedAmt = lastDistribution.getCompoundFixedAmt();

			// Deducting from Compounding variable
			Double compoundVariableAmt = lastDistribution.getCompoundVariableAmt() + amtToAdjust;

			Distribution paymentDistribution = new Distribution();
			paymentDistribution.setDepositedAmt(round(amtToAdjust, 2));
			paymentDistribution.setFixedAmt(0.0);
			// paymentDistribution.setVariableAmt(round(amtToAdjust,2));
			paymentDistribution.setVariableAmt(0.0);
			paymentDistribution.setCompoundFixedAmt(round(compoundFixedAmt, 2));
			paymentDistribution.setCompoundVariableAmt(round(compoundVariableAmt, 2));
			paymentDistribution.setAction(action);
			paymentDistribution.setDistributionDate(DateService.getCurrentDate());

			paymentDistribution.setTotalBalance(round((compoundFixedAmt + compoundVariableAmt), 2));
			paymentDistribution.setDepositId(depositId);
			paymentDistribution.setDepositHolderId(depositHolderId);
			// paymentDistribution.setFixedInterest(previousFixedInterest1);
			// paymentDistribution.setVariableInterest(previousVariableInterest1);
			paymentDistribution.setBalanceFixedInterest(round(balFixedInterest, 2));
			// Deducting from BalanceVariableInterest
			paymentDistribution.setBalanceVariableInterest(round(balVariableInterest + amtToAdjust, 2));
			paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

			//COM--this.insertInDepositHolderWiseDistribution(depositHolderList, action, null, amtToAdjust);

			// Enter in interestSummary and
			// HolderwiseConsolidatedInterestSummary
			this.insertInInterestSummary(depositId, amtToAdjust, 0.0, amtToAdjust, 0.0);
			this.consolidateInterest(depositId, depositHolderList, amtToAdjust, 0.0, amtToAdjust);

			// update the deposit table
			Deposit deposit = fixedDepositDao.getDeposit(depositId);
			deposit.setCurrentBalance(round((compoundFixedAmt + compoundVariableAmt), 2));
			fixedDepositDao.updateFD(deposit);

			// Enter Excess payment information in Interest Table
			Interest interest = new Interest();
			interest.setInterestRate((double) modifiedRate);
			interest.setInterestAmount(round(amtToAdjust, 2));
			interest.setInterestDate(dt);
			interest.setDepositId(depositId);
			interest.setFinancialYear(DateService.getFinancialYear(dt));
			interestDAO.insert(interest);
		} else {
			// Fetch the last distribution
			Distribution lastPaymentDistribution = paymentDistributionDAO.getLastPaymentDistribution(depositId);

			Double amtToAdjust = difference;
			double compoundFixedAmt = lastPaymentDistribution.getCompoundFixedAmt();
			Double compoundVariableAmt = lastPaymentDistribution.getCompoundVariableAmt() + difference; // difference
																										// is
																										// in
																										// negative

			Distribution paymentDistribution = new Distribution();
			paymentDistribution.setDepositedAmt(round(amtToAdjust, 2));
			paymentDistribution.setAction(action);
			paymentDistribution.setDistributionDate(DateService.getCurrentDate());
			paymentDistribution.setFixedAmt(0.0);
			paymentDistribution.setVariableAmt(round(difference, 2));
			paymentDistribution.setCompoundFixedAmt(round(compoundFixedAmt, 2));
			paymentDistribution.setCompoundVariableAmt(round(compoundVariableAmt, 2));
			paymentDistribution.setTotalBalance(round((compoundFixedAmt + compoundVariableAmt), 2));
			paymentDistribution.setDepositId(depositId);
			// paymentDistribution.setDepositHolderId(withdrawForm.getDepositHolderId());
			// paymentDistribution.setActionId(withdrawDeposit.getId());
			// paymentDistribution.setFixedInterest(previousFixedInterest);
			// paymentDistribution.setVariableInterest(previousVariableInterest);
			paymentDistribution.setBalanceFixedInterest(lastPaymentDistribution.getBalanceFixedInterest());
			paymentDistribution.setBalanceVariableInterest(lastPaymentDistribution.getBalanceVariableInterest());

			paymentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

			//COM--insertInDepositHolderWiseDistribution(depositHolderList, action, paymentDistribution.getId(), amtToAdjust);

			// update the deposit table
			Deposit deposit = fixedDepositDao.getDeposit(depositId);
			deposit.setCurrentBalance(round((compoundFixedAmt + compoundVariableAmt), 2));
			fixedDepositDao.updateFD(deposit);
			//
			// // delete from interest saved after the current date
			// Interest remInterest =
			// interestDAO.deleteByDepositIdAndDate(withdrawForm.getDepositId());
			// DepositHolder depositHolder =
			// depositHolderDAO.findById(withdrawForm.getDepositHolderId());
			//
			// // set required parameters for interest calculation in
			// fixedDepositForm
			// Customer customer =
			// getCustomerDetails(depositHolder.getCustomerId());
			// fixedDepositForm.setDepositId(withdrawForm.getDepositId());
			// fixedDepositForm.setCategory(customer.getCategory());
			// fixedDepositForm =
			// fdService.setParametersForProjectedInterest(fixedDepositForm);
			////
			// // Save the projected interests
			// List<Interest> interestList =
			// fdService.getInterestBreakupForModification(DateService.getCurrentDate(),
			// fixedDepositForm.getMaturityDate(), fixedDepositForm);
			// for (int i = 0; i < interestList.size(); i++) {
			// Interest interest = new Interest();
			// interest = interestList.get(i);
			// interestDAO.insert(interest);
			// }
			//
			// List<TDS> tdsList =
			// fdService.getTDSBreakupForModification(fixedDepositForm,
			// interestList,
			// fixedDepositForm.getMaturityDate());
			// for (int i = 0; i < tdsList.size(); i++) {
			// TDS tds = new TDS();
			// tds = tdsList.get(i);
			// tdsDAO.insert(tds);
			// }
		}

	}

	public String generateRandomStringOD() {

		String uuid = "OD-" + RandomStringUtils.randomAlphanumeric(14).toUpperCase();
		return uuid;
	}

	public String generateRandomString() {

		String uuid = RandomStringUtils.randomAlphanumeric(16).toUpperCase();
		return uuid;
	}

	public String generateAccountNoForAutoDeposit() {

		String uuid = RandomStringUtils.randomAlphanumeric(14).toUpperCase();
		return uuid;
	}

	private String getCurrentLoggedUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();

	}

	public Customer getCustomerDetails(Long id) {
		Customer customer = customerDAO.getById(id);
		return customer;
	}

	public Float getDepositHolderContribution(List<DepositHolder> depositHolders, Long depositHolderId) {
		Float contribution = 0f;
		for (int i = 0; i < depositHolders.size(); i++) {
			DepositHolder depositHolder = depositHolders.get(i);
			if (depositHolder.getId().equals(depositHolderId)) {
				contribution = depositHolder.getContribution();
				break;
			}

		}
		return contribution;
	}

	public DepositHolder getDepositHolder(List<DepositHolder> depositHolders, Long depositHolderId) {
		DepositHolder depositHolder = null;
		for (int i = 0; i < depositHolders.size(); i++) {
			depositHolder = depositHolders.get(i);
			if (depositHolder.getId().equals(depositHolderId)) {
				break;
			} else {
				depositHolder = null;
			}

		}
		return depositHolder;
	}



	public double calculateEmi(double p, float r, int n) {
		r = r / 12 / 100; // monthly calculated
		double temp = Math.pow(1 + r, n);
		double amount = p * r * temp / (temp - 1);
		amount = round(amount, 2);
		return amount;
	}

	public float calculateEmiTenure(double p, double emi, float r) {
		r = r / 12 / 100; // monthly calculated

		double temp1 = Math.log(emi / (emi - p * r));
		double temp2 = Math.log(1 + r);
		System.out.println((temp1 / temp2));
		return (float) (temp1 / temp2);

	}

	public List<Date> getLastDatesList(Date fromDate, Date maturityDate) {

		List<Date> interestDates = new ArrayList<Date>();
		while (true) {

			interestDates.add(DateService.getLastDateOfMonth(fromDate));
			fromDate = DateService.getFirstDateOfNextMonth(fromDate);
			if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate), maturityDate) < 0) {
				break;
			}
		}
		return interestDates;
	}


	/*public List<Interest> getInterestListForEMI(Date fromDate, Date maturityDate, FixedDepositForm fixedDepositForm,
			Float interestRate, String customerCategory) {

		Integer gestationPeriod = fixedDepositForm.getGestationPeriod();
		Date gestationEndDate = DateService.generateYearsDate(fromDate, gestationPeriod);

		Double emiAmount = round((double) fixedDepositForm.getEmiAmount(), 2);

		// Find when Last Interest Calculated
		Interest lastIntrestCalculated = interestDAO.getLastInterestCompounded(fixedDepositForm.getDepositId());

		Distribution lastDistribution = paymentDistributionDAO
				.getLastPaymentDistribution(fixedDepositForm.getDepositId());

		List<Interest> interestClassList = new ArrayList<>();

		Double compoundAmt = (double) fixedDepositForm.getFdAmount();
		if (lastIntrestCalculated != null)
			compoundAmt = lastDistribution.getTotalBalance();

		int dateDiff = 0;

		Date startDate = DateService.getCurrentDate();
		if (lastIntrestCalculated != null
				&& DateService.getDaysBetweenTwoDates(startDate, lastIntrestCalculated.getInterestDate()) == 0)
			startDate = DateService.addDays(lastIntrestCalculated.getInterestDate(), 1);

		List<Date> interestListTillGestation = this.getInterestDatesTillGestation(startDate, gestationEndDate, Constants.MONTHLY);

		List<Date> payoffDateList = null;
		if (DateService.getDaysBetweenTwoDates(startDate, gestationEndDate) >= 0)
			payoffDateList = this.getEMIPayoffDates(gestationEndDate, maturityDate,
					fixedDepositForm.getPayOffInterestType());
		else
			payoffDateList = this.getEMIPayoffDates(startDate, maturityDate, fixedDepositForm.getPayOffInterestType());

		// List<Date> interestDateList = this.getInterestDates(gestationEndDate,
		// maturityDate);
		List<Date> interestDateList = null;
		if (DateService.getDaysBetweenTwoDates(startDate, gestationEndDate) >= 0)
			interestDateList = this.getInterestDatesForModification(gestationEndDate, maturityDate);
		else
			interestDateList = this.getInterestDatesForModification(startDate, maturityDate);

		Date lastInterestDate = null;

		if (DateService.getDaysBetweenTwoDates(fromDate, startDate) >= 0)
			fromDate = startDate;

		for (int i = 0; i < interestListTillGestation.size(); i++) {

			Date interestDate = interestListTillGestation.get(i);
			Integer daysDiff = 0;

			// For Last Deposit skip below
			daysDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate) + 1;
			fromDate = DateService.addDays(interestDate, 1);

			Double interestAmount = (compoundAmt * interestRate / 100) / 365 * daysDiff;
			// Double interestAmount = compoundPrincipalAmt
			// (Math.pow((1+((interestRate/100)/365)),(365
			// (dateDiff/365))))-compoundPrincipalAmt;
			compoundAmt = compoundAmt + interestAmount;

			Interest interest = new Interest();
			interest.setInterestRate(round((double) interestRate, 2));
			interest.setInterestAmount(round(interestAmount, 2));
			interest.setInterestDate(interestDate);
			interest.setDepositId(fixedDepositForm.getDepositId());
			interest.setFinancialYear(DateService.getFinancialYear(interestDate));
			interestClassList.add(interest);

			fromDate = DateService.addDays(interestDate, 1);
			lastInterestDate = interestDate;

		}

		Integer k = 0;
		if (interestDateList.size() > 0 && payoffDateList.size() > 0) {
			if (DateService.getDaysBetweenTwoDates(interestDateList.get(0), payoffDateList.get(0)) >= 0) {
				for (int i = 0; i < interestDateList.size(); i++) { // i=0,1,2

					Date interestDate = interestDateList.get(i); // 31 jan
					Date nextInterestDate = (i < interestDateList.size() - 1) ? interestDateList.get(i + 1)
							: interestDate;
					dateDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate) + 1; // from

					Double interesAmt = (compoundAmt * interestRate / 100) / 365 * dateDiff;

					// Adding Interest to the current Balance
					compoundAmt = compoundAmt + interesAmt;
					fromDate = DateService.addDays(interestDate, 1);

					Interest interest = new Interest();
					interest.setInterestRate(round((double) interestRate, 2));
					interest.setInterestAmount(round(interesAmt, 2));
					interest.setInterestDate(interestDate);
					interest.setDepositId(fixedDepositForm.getDepositId());
					interest.setFinancialYear(DateService.getFinancialYear(interestDate));
					interestClassList.add(interest);

					for (int j = k; j < payoffDateList.size(); j++) {

						Date payoffDate = payoffDateList.get(j);

						if (DateService.getDaysBetweenTwoDates(interestDate, payoffDate) >= 0
								&& DateService.getDaysBetweenTwoDates(payoffDate, nextInterestDate) > 0) {
							compoundAmt = compoundAmt - emiAmount;
							k = k + 1;
						} else if (i == (interestDateList.size() - 1) && j == (payoffDateList.size() - 1)) {
							compoundAmt = compoundAmt - emiAmount;
							k = k + 1;
						} else {
							break;
						}
					}

				}
			} else {
				k = 0;
				Double totalInterestAmount = 0d;
				for (int i = 0; i < payoffDateList.size(); i++) { // i=0,1,2

					Date payoffDate = payoffDateList.get(i); // 31 jan
					Date nextPayoffDate = (i < payoffDateList.size() - 1) ? payoffDateList.get(i + 1) : payoffDate;

					for (int j = k; j < interestDateList.size(); j++) {

						Date interestDate = interestDateList.get(j);

						if (DateService.getDaysBetweenTwoDates(payoffDate, interestDate) > 0
								&& DateService.getDaysBetweenTwoDates(nextPayoffDate, interestDate) >= 0) {
							dateDiff = DateService.getDaysBetweenTwoDates(fromDate, payoffDate) + 1;
							Double interestAmt = (compoundAmt * interestRate / 100) / 365 * dateDiff;
							totalInterestAmount = totalInterestAmount + interestAmt;
							compoundAmt = compoundAmt - emiAmount;
							fromDate = DateService.addDays(payoffDate, 1);
							break;
						}

						if (DateService.getDaysBetweenTwoDates(interestDate, payoffDate) == 0) {
							dateDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate) + 1;

							Double interestAmt = (compoundAmt * interestRate / 100) / 365 * dateDiff;
							totalInterestAmount = totalInterestAmount + interestAmt;

							// Add the interest amount
							compoundAmt = compoundAmt + totalInterestAmount;
							fromDate = DateService.addDays(interestDate, 1);

							Interest interest = new Interest();
							interest.setInterestRate(round((double) interestRate, 2));
							interest.setInterestAmount(round(totalInterestAmount, 2));
							interest.setInterestDate(interestDate);
							interest.setDepositId(fixedDepositForm.getDepositId());
							interest.setFinancialYear(DateService.getFinancialYear(interestDate));
							interestClassList.add(interest);

							// Now deduct the annuity amount
							compoundAmt = compoundAmt - emiAmount;
							totalInterestAmount = 0d;
							k = k + 1;
							break;
						}

						if (DateService.getDaysBetweenTwoDates(interestDate, nextPayoffDate) > 0) {
							break;
						}
					}

				}

			}
		}

		return interestClassList;
	}
*/
	public Date calculatePayOffDate(FixedDepositForm fixedDepositForm) {

		Date date = DateService.getCurrentDateTime();
		date = DateService.getLastDate(date);

		if (fixedDepositForm.getPayOffInterestType().equalsIgnoreCase("Monthly")) {
			// date = DateService.addMonths(date, 1);

		} else if (fixedDepositForm.getPayOffInterestType().equalsIgnoreCase("Quarterly")) {
			date = DateService.addMonths(date, 2);
		} else if (fixedDepositForm.getPayOffInterestType().equalsIgnoreCase("Semiannual")
				|| fixedDepositForm.getPayOffInterestType().equalsIgnoreCase("Halh Yearly")) {
			date = DateService.addMonths(date, 5);
		} else if (fixedDepositForm.getPayOffInterestType().equalsIgnoreCase("Annual")) {
			date = DateService.addMonths(date, 11);
		} else if (fixedDepositForm.getPayOffInterestType().equalsIgnoreCase(" End of Tenure")) {

		}

		date = DateService.getLastDateOfMonth(date);

		return date;
	}

	public Double calculateEstimatedInterest(Double principal, Float interestRate, Integer tenureInDays) {

		Double interest = 0d;

		interest = principal * (Math.pow((1 + ((interestRate / 100) / 365)), (365 * (tenureInDays / 365)))) - principal;
		return round(interest, 2);
	}

	public Double calculateEstimatedMaturityAmount(Double principal, Float interestRate, Integer tenureInDays) {

		Double interest = calculateEstimatedInterest(principal, interestRate, tenureInDays);
		return principal + interest;
	}

	public Date calculateInterestPayOffDueDateForPayOff(String payOffInterestType, Date maturityDate,
			Date depositDate) {

		Date dueDate = DateService.getCurrentDateTime();

		if (payOffInterestType.equalsIgnoreCase("One-Time") || payOffInterestType.equalsIgnoreCase("One Time")) {
			// dueDate will be the same date of deposit
			// dueDate = DateService.generateMonthsDate(dueDate, 1);
			// dueDate = DateService.getLastDateOfMonth(dueDate);
		} else if (payOffInterestType.equalsIgnoreCase("Monthly")) {

			int monthDiff = DateService.getMonthDiff(depositDate, dueDate);
			int yearDiff = DateService.getYearDiff(depositDate, dueDate);

			if (monthDiff == 0 && yearDiff == 0)
				dueDate = DateService.generateMonthsDate(dueDate, 1);

			dueDate = DateService.getLastDateOfMonth(dueDate);
			System.out.println("dueDate: " + dueDate);

		} else if (payOffInterestType.equalsIgnoreCase("Quarterly")) {
			dueDate = DateService.getQuaterlyInterestPayOffDate();

		} else if (payOffInterestType.equalsIgnoreCase("Half Yearly")
				|| payOffInterestType.equalsIgnoreCase("Semiannual")) {

			dueDate = DateService.generateMonthsDate(dueDate, 6);
			dueDate = DateService.getLastDateOfMonth(dueDate);
			// dueDate = DateService.getHalfYearlyInterestPayOffDate();

		} else if (payOffInterestType.equalsIgnoreCase("Annually") || payOffInterestType.equalsIgnoreCase("Annual")) {

			dueDate = DateService.generateMonthsDate(dueDate, 12);
			dueDate = DateService.getLastDateOfMonth(dueDate);
			// dueDate = DateService.getAnnualInterestPayOffDate();
		} else if (payOffInterestType.equalsIgnoreCase("End of Tenure")) {
			/*
			 * dueDate = DateService.generateMonthsDate(dueDate, 12); dueDate =
			 * DateService.setDate(dueDate, day);
			 */
			dueDate = maturityDate;
		}
		return dueDate;
	}

	public Double calculateExpectedMaturityAmount(Deposit deposit) {
		Double expMaturityAmount = deposit.getMaturityAmount();
		Double currentBalance = deposit.getDepositAmount();
		Date currentDate = DateService.getCurrentDate();

		return expMaturityAmount;
	}

	public Double adjustInterestForReduceTenure(Deposit deposit, Double depositAmount,
			List<DepositHolder> depositHolderList, Distribution lastInterestDistribution, boolean fromWithdraw) {

		Date dt = DateService.getCurrentDateTime();
		Long depositId = deposit.getId();

		// Getting last adjustment
		Distribution lastAdjustment = paymentDistributionDAO.getLastAdjustment(deposit.getId());

		// Getting the duration to adjust
		Date fromDate = lastAdjustment == null ? DateService.getDate(deposit.getCreatedDate())
				: lastAdjustment.getDistributionDate();
		Date toDate = DateService.getCurrentDate();
		int daysDiff = DateService.getDaysBetweenTwoDates(fromDate, toDate);

		// Getting the interest rate of the duration
//		List<Object[]> deposits = fixedDepositDao.getDepositForInterestRate(deposit.getId());// fixedDepositDao.getDeposit(depositId);
//		String category = "General";
//		if (deposits.size() > 0) {
//			Object[] depositsForcategory = deposits.get(0);
//			category = depositsForcategory[8].toString();
//		}
//		Float newInterestRateForThePeriod = depositRateDAO.getInterestRate(category, deposit.getDepositCategory(), daysDiff,
//				deposit.getDepositClassification(), deposit.getDepositAmount());
		
		Float newInterestRateForThePeriod = calculationService.getDepositInterestRate(daysDiff, 
				deposit.getDepositCategory(), deposit.getDepositCurrency(),  
				deposit.getDepositAmount(), deposit.getDepositClassification(), 
				deposit.getPrimaryCitizen(), deposit.getPrimaryNRIAccountType());

		// Getting total interest already given
		Double totalInterestGiven = paymentDistributionDAO.getTotInterestGivenForPeriod(deposit.getId(), fromDate,
				toDate);

		Double totalInterestWithReducingRate = 0d;

		// Getting the last withdraw amount
		// Because withdraw amount is coming as negative, we are making this as
		// possible
		// Otherwise, total amount with reducing rate will come negative

		// it will come from deposit modification
		// Double depositAmount = deposit.getDepositAmount();

		// Finding the amount to adjust
		// totalInterestWithReducingRate = depositAmount *
		// (newInterestRateForThePeriod / 100) / 365 * daysDiff;
		// Arpita
		List<Distribution> pdistributionList = paymentDistributionDAO.getAllDistributions(depositId);

		// Now finding the Amount that need to adjust
		// // This is coming as negative
		Double amtToAdjust = totalInterestWithReducingRate; // Math.abs(totalInterestWithReducingRate)
															// -
															// totalInterestGiven;

		// // Now amtToAdjust is positive
		// Double amtToAdjust =
		// totalInterest-Math.abs(totalInterestWithReducingRate);
		//
		// // Making it negative
		// amtToAdjust = amtToAdjust * -1;

		Float fixedRate = 0f;
		List<Rates> rates = ratesDAO.findByCategory(deposit.getPrimaryCustomerCategory()).getResultList();
		if (rates.size() > 0) {
			fixedRate = rates.get(0).getFdFixedPercent();
		}

		String action = "Interest Adjustment For Tenure Reduce";

		// Finding fixed and variable interest
		Double fixedInterst = round(amtToAdjust * fixedRate / 100, 2);
		Double variableInterest = round(amtToAdjust - fixedInterst, 2);

		// Calculating from Compounding amount
		Double compoundFixedAmt = lastInterestDistribution.getCompoundFixedAmt() + fixedInterst;
		Double compoundVariableAmt = lastInterestDistribution.getCompoundVariableAmt() + variableInterest;

		Distribution paymentDistribution = new Distribution();

		paymentDistribution.setCompoundFixedAmt(round(compoundFixedAmt, 2));
		paymentDistribution.setCompoundVariableAmt(round(compoundVariableAmt, 2));
		paymentDistribution.setAction(action);
		paymentDistribution.setDistributionDate(DateService.getCurrentDate());

		paymentDistribution.setTotalBalance(round((compoundFixedAmt + compoundVariableAmt), 2));
		paymentDistribution.setDepositId(depositId);
		paymentDistribution.setDepositHolderId(getPrimaryHolderId(depositHolderList));
		paymentDistribution.setFixedInterest(fixedInterst);
		paymentDistribution.setVariableInterest(variableInterest);
		paymentDistribution
				.setBalanceFixedInterest(round(lastInterestDistribution.getBalanceFixedInterest() + fixedInterst, 2));
		// Deducting from BalanceVariableInterest
		paymentDistribution.setBalanceVariableInterest(
				round(lastInterestDistribution.getBalanceVariableInterest() + variableInterest, 2));
		paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

		//COM--this.insertInDepositHolderWiseDistribution(depositHolderList, action, null, amtToAdjust);

		// Enter in interestSummary and
		// HolderwiseConsolidatedInterestSummary
		this.insertInInterestSummary(depositId, amtToAdjust, 0.0, amtToAdjust, 0.0);
		this.consolidateInterest(depositId, depositHolderList, amtToAdjust, 0.0, amtToAdjust);

		// update the deposit table
		// Deposit deposit = fixedDepositDao.getDeposit(depositId);
		deposit.setCurrentBalance(round((compoundFixedAmt + compoundVariableAmt), 2));
		fixedDepositDao.updateFD(deposit);

		// Enter Excess payment Adjustment information in Interest Table
		Interest interest = new Interest();
		interest.setInterestRate((double) newInterestRateForThePeriod);
		interest.setInterestAmount(round(amtToAdjust, 2));
		interest.setInterestDate(DateService.getCurrentDateTime());
		interest.setDepositId(depositId);
		interest.setFinancialYear(DateService.getFinancialYear(dt));
		interest.setIsCalculated(1);
		interestDAO.insert(interest);

		return amtToAdjust * -1;
	}
	

	public Long getPrimaryHolderId(List<DepositHolder> depositHolderList) {
		Long depositHolderId = 0l;
		for (int i = 0; i < depositHolderList.size(); i++) {
			if (depositHolderList.get(i).getDepositHolderStatus().equalsIgnoreCase("PRIMARY"))
				depositHolderId = depositHolderList.get(i).getId();
		}

		return depositHolderId;
	}

	public Long getPrimaryCustomerId(List<DepositHolder> depositHolderList) {
		Long customerId = 0l;
		for (int i = 0; i < depositHolderList.size(); i++) {
			if (depositHolderList.get(i).getDepositHolderStatus().equalsIgnoreCase("PRIMARY"))
				customerId = depositHolderList.get(i).getCustomerId();
		}

		return customerId;
	}

	
	public Distribution calculateInterestForDepositEdit(Deposit deposit, List<DepositHolder> depositHolderList,
			Float interestRate) {
		Long depositId = deposit.getId();
		Date prevDistributionDate = null;
		Long lastInterestDistributionId = 0l;
		Double prevCompoundFixedAmt = 0d;
		Double prevCompoundVariableAmt = 0d;
		Double fixedIntAmt = 0d;
		Double variableIntAmt = 0d;
		Double compoundFixedAmt = 0d;
		Double compoundVariableAmt = 0d;
		Double totalFixedInterest = 0d;
		Double totalVariableInterest = 0d;
		int totalDaysDiff = 0;
		// Double totUncalculatdInterestAmount = 0d;
		Double totalBalance = 0d;
		Double balFixedInterest = 0d;
		Double balVariableInterest = 0d;
		Boolean isLastTransactionWithdraw = false;
		String action = "Interest";

		Float modifiedInterestRate = interestRate; // (deposit.getModifiedInterestRate()
													// == null) ?
													// deposit.getInterestRate()
		// : deposit.getModifiedInterestRate();

		// Get last interest calculated/adjusted for the period from
		// Distribution table.
		// It is required to calculated the interest till today for which
		// interest is not yet calculated
		Distribution lastInterestCalculated = paymentDistributionDAO.getLastInterestCalculatedOrAdjusted(depositId,
				DateService.getCurrentDate());

		// get first adjustment record of InterestAdjustment from last interest
		// calculation
		Distribution lastAdjustment = paymentDistributionDAO.getFirstAdjustmentAfterLastInterest(depositId);

		// Get last top up
		Distribution lastTopupBeforeAdjustment = paymentDistributionDAO.getLastTopup(deposit.getId(),
				lastAdjustment != null ? lastAdjustment.getId() : null);

		Distribution currentDistribution = null;

		// Interest already calculated, not for first time
		if (lastInterestCalculated != null) // Means interest has been
											// calculated previously
		{
			lastInterestDistributionId = lastInterestCalculated.getId();
			prevDistributionDate = lastInterestCalculated.getDistributionDate();
			prevCompoundFixedAmt = lastInterestCalculated.getCompoundFixedAmt();
			prevCompoundVariableAmt = lastInterestCalculated.getCompoundVariableAmt();
			balFixedInterest = lastInterestCalculated.getBalanceFixedInterest();
			balVariableInterest = lastInterestCalculated.getBalanceVariableInterest();
			compoundFixedAmt = prevCompoundFixedAmt;
			compoundVariableAmt = prevCompoundVariableAmt;
			fixedIntAmt = 0d;
			variableIntAmt = 0d;
			compoundFixedAmt = 0d;
			compoundVariableAmt = 0d;
			totalDaysDiff = 0;
			prevDistributionDate = DateService.addDays(prevDistributionDate, 1);

			// If interest is already calculated in current date then
			// do not allow to calculate again
			// if
			// (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(),
			// prevDistributionDate) == 0)
			// return lastInterestCalculated;

			// Fetch all the records starting from the last interest calculated
			// List<Distribution> lastPaymentDistForDepWithdraw =
			// paymentDistributionDAO
			// .getAllDepWithdrawFromLastInterestCalc(depositId,
			// lastInterestDistributionId);

			// Fetch all the records starting from the last topup before
			// adjustment calculated
			List<Distribution> lastPaymentDistForDepWithdraw = paymentDistributionDAO
					.getAllTransactionsExceptInterestFromLastInterestCalc(deposit.getId(), lastInterestDistributionId);

			// After interest calculation Payment or Withdraw has been made
			if (lastPaymentDistForDepWithdraw != null && lastPaymentDistForDepWithdraw.size() > 0) {
				for (int i = 0; i < lastPaymentDistForDepWithdraw.size(); i++) {
					Distribution paymentDist = lastPaymentDistForDepWithdraw.get(i);

					Integer daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
							paymentDist.getDistributionDate());

					if (paymentDist.getAction().equalsIgnoreCase("Interest") && paymentDist.getInterestAdjusted() == 1)
						continue;
					if (paymentDist.getAction().equalsIgnoreCase("Interest Adjustment For Withdraw")) {
						totalBalance = paymentDist.getTotalBalance();

						prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
						prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();

						balFixedInterest = paymentDist.getBalanceFixedInterest();
						balVariableInterest = paymentDist.getBalanceVariableInterest();

						continue;
					}

					if (daysDifference == 0 && (paymentDist.getAction().equalsIgnoreCase("TDS")
							|| paymentDist.getAction().equalsIgnoreCase("PAYOFF"))) {
						// lastInterestDistributionId =
						// lastInterestCalculated.getId();
						prevDistributionDate = DateService.addDays(paymentDist.getDistributionDate(), 1);
						prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
						prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();

						balFixedInterest = paymentDist.getBalanceFixedInterest();
						balVariableInterest = paymentDist.getBalanceVariableInterest();

						compoundFixedAmt = prevCompoundFixedAmt;
						compoundVariableAmt = prevCompoundVariableAmt;

						continue;
					}

					if (daysDifference > 0) {

						if (paymentDist.getAction().equalsIgnoreCase("Withdraw")) {
							isLastTransactionWithdraw = true;

							daysDifference = DateService.getDaysBetweenTwoDates(
									lastTopupBeforeAdjustment.getDistributionDate(), paymentDist.getDistributionDate())
									+ 1;

							if (daysDifference > 0) {
								Float withdrawInterestRate = calculationService.getDepositInterestRate(daysDifference,
										deposit.getPrimaryCustomerCategory(), deposit.getDepositCurrency(), 
										deposit.getDepositAmount(), deposit.getDepositClassification(),
										deposit.getPrimaryCitizen(), deposit.getPrimaryNRIAccountType());
								
								fixedIntAmt = (paymentDist.getFixedAmt() * withdrawInterestRate / 100) / 365
										* daysDifference;
								variableIntAmt = (paymentDist.getVariableAmt() * withdrawInterestRate / 100) / 365
										* daysDifference;

								if (variableIntAmt < 0)
									variableIntAmt = variableIntAmt * -1;

								totalDaysDiff = totalDaysDiff + daysDifference;
								totalFixedInterest = totalFixedInterest + fixedIntAmt;
								totalVariableInterest = totalVariableInterest + variableIntAmt;

								// totalFixedInterestAfterWithdrawAdj =
								// totalFixedInterestAfterWithdrawAdj +
								// fixedIntAmt;
								// totalVariableInterestAfterWithdrawAdj =
								// totalVariableInterestAfterWithdrawAdj +
								// variableIntAmt;
							}
							isLastTransactionWithdraw = true;
						} else if (paymentDist.getAction().equalsIgnoreCase("Payment")) {
							if (isLastTransactionWithdraw) {
								// After interest calculation, No
								// Payment/Withdraw has been done
								daysDifference = DateService.getDaysBetweenTwoDates(
										lastTopupBeforeAdjustment.getDistributionDate(),
										paymentDist.getDistributionDate()) + 1;
							}

							totalDaysDiff = totalDaysDiff + daysDifference;

							fixedIntAmt = (prevCompoundFixedAmt * modifiedInterestRate / 100) / 365 * daysDifference;
							variableIntAmt = (prevCompoundVariableAmt * modifiedInterestRate / 100) / 365
									* daysDifference;

							totalFixedInterest = totalFixedInterest + fixedIntAmt;
							totalVariableInterest = totalVariableInterest + variableIntAmt;

						} else {
							totalDaysDiff = totalDaysDiff + daysDifference;

							fixedIntAmt = (prevCompoundFixedAmt * modifiedInterestRate / 100) / 365 * daysDifference;
							variableIntAmt = (prevCompoundVariableAmt * modifiedInterestRate / 100) / 365
									* daysDifference;

							totalFixedInterest = totalFixedInterest + fixedIntAmt;
							totalVariableInterest = totalVariableInterest + variableIntAmt;
						}
					}
					totalBalance = paymentDist.getTotalBalance();

					prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
					prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();
					if (paymentDist.getAction().startsWith("Payment")) {
						prevDistributionDate = paymentDist.getDistributionDate();
					} else if (paymentDist.getAction().equalsIgnoreCase("Withdraw")) {
						prevDistributionDate = lastTopupBeforeAdjustment.getDistributionDate();
					} else {
						prevDistributionDate = DateService.addDays(paymentDist.getDistributionDate(), 1);
					}

					balFixedInterest = paymentDist.getBalanceFixedInterest();
					balVariableInterest = paymentDist.getBalanceVariableInterest();
					// // Deducting one day is required. Otherwise from last
					// month
					// // interest to
					// // next Payment/Withdraw system will calculate one extra
					// day
					// if
					// (DateService.getMonth(paymentDist.getDistributionDate())
					// - DateService.getMonth(prevDistributionDate) != 0)
					// daysDifference = daysDifference - 1;
					//
					// if (daysDifference > 0) {
					// totalDaysDiff = totalDaysDiff + daysDifference;
					//
					// fixedIntAmt = (prevCompoundFixedAmt *
					// modifiedInterestRate / 100) / 365 * daysDifference;
					// variableIntAmt = (prevCompoundVariableAmt *
					// modifiedInterestRate / 100) / 365 * daysDifference;
					//
					// totalFixedInterest = totalFixedInterest + fixedIntAmt;
					// totalVariableInterest = totalVariableInterest +
					// variableIntAmt;
					// }
					// totalBalance = paymentDist.getTotalBalance();
					//
					// prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
					// prevCompoundVariableAmt =
					// paymentDist.getCompoundVariableAmt();
					// prevDistributionDate = paymentDist.getDistributionDate();
					//
					// compoundFixedAmt = prevCompoundFixedAmt;
					// compoundVariableAmt = prevCompoundVariableAmt;
					//
					// balFixedInterest = paymentDist.getBalanceFixedInterest();
					// balVariableInterest =
					// paymentDist.getBalanceVariableInterest();

				}
			}
			// After interest calculation, No Payment/Withdraw has been done
			Integer daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
					DateService.getCurrentDate()) + 1;

			// In a month from 1st day to 30th days difference comes 29.
			// So here in interest calculation last step we are adding 1 to get
			// full 30 days interest.

			// if (DateService.getMonth(DateService.getCurrentDate()) -
			// DateService.getMonth(prevDistributionDate) == 0)
			// daysDifference = daysDifference + 1;

			// When interest is calculating in the middle of the month for
			// withdrwal or
			// tenure modification then system should not include the current
			// day. Because
			// in the same day, next withdraw will happen
			// if
			// (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(),
			// DateService.getLastDateOfCurrentMonth()) != 0)
			// daysDifference = daysDifference - 1;

			compoundFixedAmt = prevCompoundFixedAmt;
			compoundVariableAmt = prevCompoundVariableAmt;

			fixedIntAmt = (prevCompoundFixedAmt * modifiedInterestRate / 100) / 365 * daysDifference;
			variableIntAmt = (prevCompoundVariableAmt * modifiedInterestRate / 100) / 365 * daysDifference;

			totalFixedInterest = totalFixedInterest + fixedIntAmt;
			totalVariableInterest = totalVariableInterest + variableIntAmt;

			balFixedInterest = balFixedInterest + totalFixedInterest;
			balVariableInterest = balVariableInterest + totalVariableInterest;

			// totUncalculatdInterestAmount = totalFixedInterest +
			// totalVariableInterest;
		} else {
			// First time interest is going to be calculated
			Date currentDate = DateService.getCurrentDate();
			compoundFixedAmt = 0d;
			compoundVariableAmt = 0d;
			totalBalance = 0d;
			balFixedInterest = 0d;
			balVariableInterest = 0d;

			// first time interest is going to calculate
			// consider multiple deposit can be performed by these days
			// Fetch all the records starting from the last interest calculated
			List<Distribution> lastPaymentDistForDepWithdraw = paymentDistributionDAO
					.getAllDepWithdrawFromLastInterestCalc(depositId, 0L);

			// Payment or Withdraw has been made
			if (lastPaymentDistForDepWithdraw != null && lastPaymentDistForDepWithdraw.size() > 0) {
				prevCompoundFixedAmt = 0d;
				prevCompoundVariableAmt = 0d;
				Integer daysDifference = 0;
				fixedIntAmt = 0d;
				variableIntAmt = 0d;
				totalDaysDiff = 0;

				for (int i = 0; i < lastPaymentDistForDepWithdraw.size(); i++) {
					Distribution paymentDist = lastPaymentDistForDepWithdraw.get(i);

					if (i == 0) {
						// distributionId = paymentDist.getId();
						prevDistributionDate = paymentDist.getDistributionDate();
						prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
						prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();

						compoundFixedAmt = prevCompoundFixedAmt;
						compoundVariableAmt = prevCompoundVariableAmt;

						balFixedInterest = paymentDist.getBalanceFixedInterest();
						balVariableInterest = paymentDist.getBalanceVariableInterest();
						continue;
					}

					daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
							paymentDist.getDistributionDate());
					//
					// fixedIntAmt = (prevCompoundFixedAmt *
					// modifiedInterestRate / 100) / 365 * daysDifference;
					// variableIntAmt = (prevCompoundVariableAmt *
					// modifiedInterestRate / 100) / 365 * daysDifference;
					//
					// totalFixedInterest = totalFixedInterest + fixedIntAmt;
					// totalVariableInterest = totalVariableInterest +
					// variableIntAmt;
					//
					// balFixedInterest = balFixedInterest + totalFixedInterest;
					// balVariableInterest = balVariableInterest +
					// totalVariableInterest;
					//
					// totalBalance = paymentDist.getTotalBalance();
					//
					// prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
					// prevCompoundVariableAmt =
					// paymentDist.getCompoundVariableAmt();
					// prevDistributionDate = paymentDist.getDistributionDate();
					// totalDaysDiff = totalDaysDiff + daysDifference;

					if (paymentDist.getAction().equalsIgnoreCase("Interest") && paymentDist.getInterestAdjusted() == 1)
						continue;
					if (paymentDist.getAction().equalsIgnoreCase("Interest Adjustment For Withdraw")) {
						totalBalance = paymentDist.getTotalBalance();

						prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
						prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();

						balFixedInterest = paymentDist.getBalanceFixedInterest();
						balVariableInterest = paymentDist.getBalanceVariableInterest();

						continue;
					}

					if (daysDifference == 0 && (paymentDist.getAction().equalsIgnoreCase("TDS")
							|| paymentDist.getAction().equalsIgnoreCase("PAYOFF"))) {
						// lastInterestDistributionId =
						// lastInterestCalculated.getId();
						prevDistributionDate = DateService.addDays(paymentDist.getDistributionDate(), 1);
						prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
						prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();

						balFixedInterest = paymentDist.getBalanceFixedInterest();
						balVariableInterest = paymentDist.getBalanceVariableInterest();

						compoundFixedAmt = prevCompoundFixedAmt;
						compoundVariableAmt = prevCompoundVariableAmt;

						continue;
					}

					if (daysDifference > 0) {

						if (paymentDist.getAction().equalsIgnoreCase("Withdraw")) {
							isLastTransactionWithdraw = true;

							daysDifference = DateService.getDaysBetweenTwoDates(
									lastTopupBeforeAdjustment.getDistributionDate(), paymentDist.getDistributionDate())
									+ 1;

							if (daysDifference > 0) {
								Float withdrawInterestRate = calculationService.getDepositInterestRate(daysDifference,
										this.getPrimaryHolderCategory(depositHolderList), deposit.getDepositCurrency(),
										deposit.getDepositAmount(), deposit.getDepositClassification(),
										deposit.getPrimaryCitizen(), deposit.getPrimaryNRIAccountType());
								fixedIntAmt = (paymentDist.getFixedAmt() * withdrawInterestRate / 100) / 365
										* daysDifference;
								variableIntAmt = (paymentDist.getVariableAmt() * withdrawInterestRate / 100) / 365
										* daysDifference;

								if (variableIntAmt < 0)
									variableIntAmt = variableIntAmt * -1;

								totalDaysDiff = totalDaysDiff + daysDifference;
								totalFixedInterest = totalFixedInterest + fixedIntAmt;
								totalVariableInterest = totalVariableInterest + variableIntAmt;

							}
							isLastTransactionWithdraw = true;
						} else if (paymentDist.getAction().equalsIgnoreCase("Payment")) {
							if (isLastTransactionWithdraw) {
								// After interest calculation, No
								// Payment/Withdraw has been done
								daysDifference = DateService.getDaysBetweenTwoDates(
										lastTopupBeforeAdjustment.getDistributionDate(),
										paymentDist.getDistributionDate()) + 1;
							}

							totalDaysDiff = totalDaysDiff + daysDifference;

							fixedIntAmt = (prevCompoundFixedAmt * modifiedInterestRate / 100) / 365 * daysDifference;
							variableIntAmt = (prevCompoundVariableAmt * modifiedInterestRate / 100) / 365
									* daysDifference;

							totalFixedInterest = totalFixedInterest + fixedIntAmt;
							totalVariableInterest = totalVariableInterest + variableIntAmt;
						} else {
							totalDaysDiff = totalDaysDiff + daysDifference;

							fixedIntAmt = (prevCompoundFixedAmt * modifiedInterestRate / 100) / 365 * daysDifference;
							variableIntAmt = (prevCompoundVariableAmt * modifiedInterestRate / 100) / 365
									* daysDifference;

							totalFixedInterest = totalFixedInterest + fixedIntAmt;
							totalVariableInterest = totalVariableInterest + variableIntAmt;

						}
					}

				}

				// Calculate interest from last transaction to current date
				daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate, currentDate) + 1;

				// for last interest calculation we are adding 1 day ,.ie the
				// interest calculation day
				// otherwise difference (of 1st and 30th) will come 29, that is
				// actually one day
				// shorter than the actual month days
				// if (DateService.getMonth(DateService.getCurrentDate())
				// - DateService.getMonth(prevDistributionDate) == 0)
				// daysDifference = daysDifference + 1;

				totalDaysDiff = totalDaysDiff + daysDifference;

				compoundFixedAmt = prevCompoundFixedAmt;
				compoundVariableAmt = prevCompoundVariableAmt;

				fixedIntAmt = (compoundFixedAmt * modifiedInterestRate / 100) / 365 * daysDifference;
				variableIntAmt = (compoundVariableAmt * modifiedInterestRate / 100) / 365 * daysDifference;

				totalFixedInterest = totalFixedInterest + fixedIntAmt;
				totalVariableInterest = totalVariableInterest + variableIntAmt;

				balFixedInterest = balFixedInterest + totalFixedInterest;
				balVariableInterest = balVariableInterest + totalVariableInterest;

				// totUncalculatdInterestAmount = totalFixedInterest +
				// totalVariableInterest;
			}

		}

		// current fixed amount will be zero
		compoundFixedAmt = compoundFixedAmt + totalFixedInterest;
		compoundVariableAmt = compoundVariableAmt + totalVariableInterest;

		totalBalance = compoundFixedAmt + compoundVariableAmt;

		// saving
		Distribution paymentDistribution = new Distribution();
		paymentDistribution.setDistributionDate(DateService.getCurrentDate());
		paymentDistribution.setDepositId(depositId);
		paymentDistribution.setDaysToCalcInterest(totalDaysDiff);
		paymentDistribution.setFixedInterest(round(totalFixedInterest, 2));

		paymentDistribution.setVariableInterest(round(totalVariableInterest, 2));

		paymentDistribution.setBalanceFixedInterest(round(balFixedInterest, 2));
		paymentDistribution.setBalanceVariableInterest(round(balVariableInterest, 2));
		paymentDistribution.setCompoundFixedAmt(round(compoundFixedAmt, 2));
		paymentDistribution.setCompoundVariableAmt(round(compoundVariableAmt, 2));
		paymentDistribution.setAction(action);
		paymentDistribution.setTotalBalance(round(totalBalance, 2));
		currentDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

		Double amount = totalFixedInterest + totalVariableInterest;
		DepositSummary interestSummary = this.insertInInterestSummary(depositId, amount, totalFixedInterest,
				totalVariableInterest, 0d);
		//COM--this.insertInDepositHolderWiseDistribution(depositHolderList, action, interestSummary.getId(),
		//COM--this.round(Double.valueOf(amount), 2));

		// Consolidate Interest and update holderwise consolidation
		this.consolidateInterest(depositId, depositHolderList, amount, totalFixedInterest, totalVariableInterest);

		// update the deposit table
		// Deposit deposit = fixedDepositDao.findById(depositId);
		deposit.setCurrentBalance(round(totalBalance, 2));
		fixedDepositDao.updateFD(deposit);

		// Insert in Interest Table.
		// It can be the middle of the month
		Date dt = DateService.getCurrentDateTime();
		Interest interest = new Interest();
		interest.setInterestRate(Double.parseDouble(modifiedInterestRate.toString()));
		interest.setInterestAmount(round(amount, 2));
		interest.setInterestDate(dt);
		interest.setDepositId(depositId);
		interest.setFinancialYear(DateService.getFinancialYear(dt));
		interest.setIsCalculated(1);
		interestDAO.insert(interest);

		return currentDistribution;
	}

	public String getPrimaryHolderCategory(List<DepositHolder> depositHolderList) {
		String category = "";
		for (int i = 0; i < depositHolderList.size(); i++) {
			if (depositHolderList.get(i).getDepositHolderStatus().equalsIgnoreCase("PRIMARY"))
				category = depositHolderList.get(i).getDepositHolderCategory();
		}
		return category;
	}

	public Double getAmountToLose(Deposit deposit, Double withdrawAmt) {

		// Get deposit holder List
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
	
		// Get last base line
		Distribution lastBaseLine = paymentDistributionDAO.getLastBaseLine(deposit.getId());
	
		// Calculate the Fixed And Variable Interest
		HashMap<String, Double> interestDetails = calculationService.calculateInterestAmount(deposit, depositHolderList, "", null);
		Double totalFixedInterest = calculationService.round(interestDetails.get(Constants.FIXEDINTEREST),2);
		Double totalVariableInterest = calculationService.round(interestDetails.get(Constants.VARIABLEINTEREST),2);
		
		// Get last Deposit Summary
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(deposit.getId());	
		
		Double variableInterestAccumulated = (depositSummary.getTotalVariableInterestAccumulated() == null)? 0d: 
			depositSummary.getTotalVariableInterestAccumulated() + totalVariableInterest;
		
		variableInterestAccumulated = variableInterestAccumulated + ((depositSummary.getTotalVariableInterestPaidOff() == null)? 0d: 
			depositSummary.getTotalVariableInterestPaidOff());
		
		Double totalTDS=tdsDAO.getTotalTDS(deposit.getId(), lastBaseLine.getDistributionDate(), DateService.getCurrentDate());
		
		variableInterestAccumulated = variableInterestAccumulated + totalTDS; 

		
		// Finding the Adjustment
		//-----------------------------------------
		// Getting the duration from the base
		Integer durationFromBase = DateService.getDaysBetweenTwoDates(lastBaseLine.getDistributionDate(),
				DateService.getCurrentDate()) +1;
		
		// Finding the interestRate for this much duration
		Float interestRateForWithdrawDuration = calculationService.getDepositInterestRate(durationFromBase,
				deposit.getPrimaryCustomerCategory(), deposit.getDepositCurrency(), deposit.getDepositAmount(),
				deposit.getDepositClassification(), deposit.getPrimaryCitizen(),
				deposit.getPrimaryNRIAccountType());

		Double variableInterestShouldGet = ((lastBaseLine.getCompoundVariableAmt()*interestRateForWithdrawDuration/100) * durationFromBase/365);
		Double amtToAdjust = (variableInterestAccumulated-variableInterestShouldGet) * -1;
				//-----------------------------------------
		

		return amtToAdjust;
	}

	@Transactional
	public int modificationAdjustment(Date lastModifiedDate, Float interestRatePrev, Date maturityDate,
			Date createdDate, FixedDepositForm fixedDepositForm) {
		List<Interest> interestList = new ArrayList<Interest>();
		List<Interest> interestListActual = new ArrayList<Interest>();
		List<Interest> interestListPrev = new ArrayList<Interest>();

		Long depositId = fixedDepositForm.getDepositId();
		Date currentDate = DateService.getCurrentDate();

		/*
		 * Interest calculation from last interest calculated till last
		 * modification
		 */

		int days = DateService.getDaysBetweenTwoDates(lastModifiedDate, currentDate);

		Float rateActual = calculationService.getDepositInterestRate(days, fixedDepositForm.getCategory(), fixedDepositForm.getCurrency(),
				fixedDepositForm.getDepositAmount(), fixedDepositForm.getDepositClassification(),
				fixedDepositForm.getCitizen(), fixedDepositForm.getNriAccountType());
		
		if (rateActual == null) {
			return 0;
		}
		days = DateService.getDaysBetweenTwoDates(currentDate, maturityDate);
		Float rateFutureTenure = calculationService.getDepositInterestRate(days, fixedDepositForm.getCategory(),
				fixedDepositForm.getCurrency(), fixedDepositForm.getDepositAmount(), fixedDepositForm.getDepositClassification(),
				fixedDepositForm.getCitizen(), fixedDepositForm.getNriAccountType());
		if (rateFutureTenure == null) {
			return 0;
		}

		/*** deleting interest after todays date ***/
		Interest lastInterestCalculated = interestDAO.deleteByDepositIdAndDate(depositId);

		Double interestSum = 0d;
		Double interestAmount = 0d;
		Double interestSumActual = 0d;
		Double interestSumPrev = 0d;

		if (lastInterestCalculated == null) {
			interestList = this.getInterestBetweenDates(createdDate, currentDate, fixedDepositForm,
					fixedDepositForm.getFdInterest(), createdDate);

		} else {

			fixedDepositForm.setFdInterest(Float.valueOf(interestRatePrev));
			//fixedDepositForm.setFdAmount(lastInterestCalculated.getInterestSum());
			Date fromDate = DateService.addDays(lastInterestCalculated.getInterestDate(), 1);

			interestList = this.getInterestBetweenDates(fromDate, currentDate, fixedDepositForm,
					fixedDepositForm.getFdInterest(), createdDate);
			//interestSum = lastInterestCalculated.getInterestSum();

		}

		/* Actual Interest calculation from last modification till today */
		fixedDepositForm.setFdAmount(interestSum);
		fixedDepositForm.setFdInterest(rateActual);
		Date fromDate = DateService.addDays(lastModifiedDate, 1);

		interestListActual = this.getInterestBetweenDates(fromDate, currentDate, fixedDepositForm,
				fixedDepositForm.getFdInterest(), createdDate);
		for (int i = 0; i < interestListActual.size(); i++) {
			interestSumActual = interestSumActual + interestListActual.get(i).getInterestAmount();
		}

		/** Prev Interest calculation from last modification till today **/

		fixedDepositForm.setFdInterest(interestRatePrev);
		interestListPrev = this.getInterestBetweenDates(fromDate, currentDate, fixedDepositForm,
				fixedDepositForm.getFdInterest(), createdDate);
		for (int i = 0; i < interestListPrev.size(); i++) {
			interestSumPrev = interestSumPrev + interestListPrev.get(i).getInterestAmount();
		}

		/** interest insert from last interest calculated till today **/
		for (int i = 0; i < interestList.size(); i++) {
			interestAmount = interestAmount + interestList.get(i).getInterestAmount();
//			if (i == (interestList.size() - 1))
//				interestSum = interestList.get(i).getInterestSum();
		}
		Interest interest = new Interest();
		interest.setDepositId(depositId);
		interest.setFinancialYear(DateService.getFinancialYear(currentDate));
		interest.setInterestRate(Double.valueOf(rateActual.toString()));
		interest.setInterestAmount(round(interestAmount, 2));
		interest.setInterestDate(currentDate);
		//interest.setInterestSum(interestSum);
		interestDAO.insert(interest);

		/******* Interest difference with the 2 different rates ******/

		Double difference = interestSumPrev - interestSumActual;

		String action = "Interest Adjustment For Tenure";
		Double amtToAdjust = difference * (-1);

		/** Inserting adjustment amount ***/
		interest = new Interest();
		interest.setDepositId(depositId);
		interest.setFinancialYear(DateService.getFinancialYear(currentDate));
		interest.setInterestRate(Double.valueOf(rateActual.toString()));
		interest.setInterestAmount(round(amtToAdjust, 2));
		interest.setInterestDate(currentDate);
		interestSum = interest.getInterestAmount() + interestSum;
		//interest.setInterestSum(round(interestSum, 2));
		interestDAO.insert(interest);

		/****** Reinsert from today till maturity ****/

		fixedDepositForm.setFdInterest(rateFutureTenure);
		fixedDepositForm.setFdAmount(interestSum);
		fromDate = DateService.addDays(DateService.getCurrentDate(), 1);

		interestList = this.getInterestBetweenDates(DateService.getCurrentDate(), maturityDate, fixedDepositForm,
				rateFutureTenure, createdDate);
		for (int i = 0; i < interestList.size(); i++) {
			interest = new Interest();
			interest = interestList.get(i);
			interestDAO.insert(interest);

		}

		/*** Rate update for last modified date till today ***/

		interestList = interestDAO.getByDate(lastModifiedDate, currentDate, depositId);

		for (int i = 0; i < interestList.size(); i++) {
			interest = interestList.get(i);
			interest.setInterestRate(Double.valueOf(rateActual.toString()));
			interestDAO.update(interest);

		}

		List<Distribution> distributionList = fixedDepositDao.getDistributionList(depositId);
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);

		Distribution lastDistribution = distributionList.get(distributionList.size() - 1);
		Double compoundFixedAmt = lastDistribution.getCompoundFixedAmt();

		// Deducting from Compounding variable
		Double compoundVariableAmt = lastDistribution.getCompoundVariableAmt() + amtToAdjust;

		Double balFixedInterest = lastDistribution.getBalanceFixedInterest();
		Double balVariableInterest = lastDistribution.getBalanceVariableInterest();

		Distribution paymentDistribution = new Distribution();
		paymentDistribution.setDepositedAmt(round(amtToAdjust, 2));
		paymentDistribution.setFixedAmt(0.0);
		paymentDistribution.setVariableAmt(0.0);
		paymentDistribution.setCompoundFixedAmt(round(compoundFixedAmt, 2));
		paymentDistribution.setCompoundVariableAmt(round(compoundVariableAmt, 2));
		paymentDistribution.setAction(action);
		paymentDistribution.setDistributionDate(DateService.getCurrentDate());
		paymentDistribution.setTotalBalance(round((compoundFixedAmt + compoundVariableAmt), 2));
		paymentDistribution.setDepositId(depositId);
		paymentDistribution.setDepositHolderId(fixedDepositForm.getDepositHolderId());
		paymentDistribution.setBalanceFixedInterest(round(balFixedInterest, 2));
		paymentDistribution.setBalanceVariableInterest(round(balVariableInterest + amtToAdjust, 2));

		paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

		//COM--this.insertInDepositHolderWiseDistribution(depositHolderList, action, null, amtToAdjust);

		return 1;

	}

	public List<Date> getInterestBetween2Dates(Date fromDate, Date maturityDate) {
		List<Date> interestDates = new ArrayList<Date>();
		while (true) {
			Date dateToAdd = DateService.getLastDateOfMonth(fromDate);
			if (DateService.getDaysBetweenTwoDates(dateToAdd, maturityDate) > 0) {
				interestDates.add(DateService.getLastDateOfMonth(fromDate));
				fromDate = DateService.getFirstDateOfNextMonth(fromDate);

			} else {
				interestDates.add(maturityDate);
				break;
			}

		}
		return interestDates;
	}

	public List<Date> getDueDatesBetween2Dates(FixedDepositForm fixedDepositForm, Date fromDate, Date maturityDate,
			Date createdDate) {

		List<Date> depositDates = new ArrayList();
		Date dueDate = createdDate;
		int monthsToAdd = 0;

		if (fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.MONTHLY))
			monthsToAdd = 1;
		else if (fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.QUARTERLY))
			monthsToAdd = 3;
		else if (fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.FULLYEARLY)
				|| fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.ANNUALLY))
			monthsToAdd = 12;
		else if (fixedDepositForm.getPaymentType().equalsIgnoreCase(Constants.HALFYEARLY))
			monthsToAdd = 6;

		// From next due Deposits
		while (true) {
			dueDate = DateService.addDays(DateService.generateMonthsDate(dueDate, monthsToAdd), 1);
			if (fixedDepositForm.getDeductionDay() != null)
				dueDate = DateService.setDate(dueDate, fixedDepositForm.getDeductionDay());

			if (monthsToAdd == 0)
				break;
			if (DateService.getDaysBetweenTwoDates(dueDate, maturityDate) > 0) {
				depositDates.add(dueDate);

			} else

				break;
		}

		List<Date> depositDatesBetDates = new ArrayList<Date>();
		for (int i = 0; i < depositDates.size(); i++) {
			if (depositDates.get(i).compareTo(fromDate) > 0) {
				depositDatesBetDates.add(depositDates.get(i));

			}

		}

		return depositDatesBetDates;
	}

	public List<Interest> getInterestBetweenDates(Date fromDate, Date toDate, FixedDepositForm fixedDepositForm,
			Float interestRate, Date createdDate) {

		// List<Date> interestList = this.getInterestDates(fromDate, toDate);
		List<Date> interestList = this.getInterestBetween2Dates(fromDate, toDate);
		List<Date> depositList = this.getDueDatesBetween2Dates(fixedDepositForm, fromDate, toDate, createdDate);

		List<Date> allDates = new ArrayList<Date>();
		allDates.addAll(interestList);
		allDates.addAll(depositList);
		Collections.sort(allDates);

		List<Interest> interestClassList = new ArrayList<>();

		Double compoundPrincipalAmt = fixedDepositForm.getFdAmount();
		Double compoundInt = 0.0;
		double interestTemp = 0d;
		int k = 0;
		int dateDiff = 0;
		for (int i = 0; i < allDates.size(); i++) {

			dateDiff = DateService.getDaysBetweenTwoDates(fromDate, allDates.get(i));
			interestTemp = interestTemp + compoundPrincipalAmt * interestRate * dateDiff / 365 / 100;

			if (depositList.contains(allDates.get(i))) {
				compoundPrincipalAmt = compoundPrincipalAmt + fixedDepositForm.getDepositAmount();

			}

			if (interestList.contains(allDates.get(i))) {

				compoundInt = interestTemp;
				compoundPrincipalAmt = compoundPrincipalAmt + compoundInt;
				interestTemp = 0d;

				Interest interest = new Interest();
				interest.setInterestRate(round((double) interestRate, 2));
				interest.setInterestAmount(round(compoundInt, 2));
				interest.setInterestDate(allDates.get(i));
				interest.setDepositId(fixedDepositForm.getDepositId());
				interest.setFinancialYear(DateService.getFinancialYear(allDates.get(i)));
				//interest.setInterestSum(round(compoundPrincipalAmt, 2));
				interestClassList.add(interest);
			}
			fromDate = DateService.addDays(allDates.get(i), 1);
		}

		return interestClassList;
	}

	/*public String compareModification(Date fromDate, Date maturityDate, FixedDepositForm fixedDepositForm,
			Deposit deposit) {
		Double modifiedInterestRate = round((double) fixedDepositForm.getFdInterest(), 2);
		List<Interest> interestClassList = this.getInterestBreakupForModification(fromDate, maturityDate,
				fixedDepositForm, deposit);

//		return "" + interestClassList.get(interestClassList.size() - 1).getInterestSum() + "," + modifiedInterestRate;
		return "";
	}*/

	public Double getAmountToAdjustForTenureChangeAlert(Deposit deposit, List<DepositHolder> depositHolderList,
			Distribution lastAdjustment, Float reducingInterestRate, String customerCategory,
			List<Distribution> allDistributionList) {

		Distribution lastDistribution = allDistributionList.get(allDistributionList.size() - 1);

		// Find the Total Interest
		// -------------------------------------------------
		Long depositId = deposit.getId();
		Date prevDistributionDate = null;

		Double prevCompoundFixedAmt = 0d;
		Double prevCompoundVariableAmt = 0d;
		Double fixedIntAmt = 0d;
		Double variableIntAmt = 0d;
		Double compoundFixedAmt = 0d;
		Double compoundVariableAmt = 0d;
		Double totalFixedInterest = 0d;
		Double totalVariableInterest = 0d;
		int totalDaysDiff = 0;
		// Double totUncalculatdInterestAmount = 0d;
		Double totalBalance = 0d;
		Double balFixedInterest = 0d;
		Double balVariableInterest = 0d;
		int index = 0;

		Date fromDate = null;
		Long distributionId = 0l;
		Double fixedAmount = 0d;
		Double variableAmount = 0d;

		if (lastAdjustment == null) {
			fromDate = deposit.getCreatedDate();
			index = 1;
		} else {
			lastAdjustment.getDistributionDate();
			distributionId = lastAdjustment.getId();
			fromDate = lastAdjustment.getDistributionDate();
			prevDistributionDate = lastAdjustment.getDistributionDate();
			prevCompoundFixedAmt = lastAdjustment.getCompoundFixedAmt();
			prevCompoundVariableAmt = lastAdjustment.getCompoundVariableAmt();
			fixedAmount = lastAdjustment.getFixedAmt() == null ? 0d : lastAdjustment.getFixedAmt();
			variableAmount = lastAdjustment.getVariableAmt() == null ? 0d : lastAdjustment.getVariableAmt();
			balFixedInterest = lastAdjustment.getBalanceFixedInterest();
			balVariableInterest = lastAdjustment.getBalanceVariableInterest();
			index = 0;
		}

		// If distributionId > 0 means, no adjustment has made
		// so we have to calculate from the first distribution that is
		// when deposit was created

		List<Distribution> distributionList = new ArrayList<Distribution>();
		for (int i = 0; i < allDistributionList.size(); i++) {

			if (allDistributionList.get(i).getId() > distributionId) {
				distributionList.add(allDistributionList.get(i));
			}

		}

		if (distributionId == 0) {
			// lastDistributionId = distributionList.get(0).getId();
			prevDistributionDate = lastDistribution.getDistributionDate();
			prevCompoundFixedAmt = lastDistribution.getCompoundFixedAmt();
			prevCompoundVariableAmt = lastDistribution.getCompoundVariableAmt();
			fixedAmount = lastDistribution.getFixedAmt() == null ? 0d : lastDistribution.getFixedAmt();
			variableAmount = lastDistribution.getVariableAmt() == null ? 0d : lastDistribution.getVariableAmt();
			balFixedInterest = lastDistribution.getBalanceFixedInterest();
			balVariableInterest = lastDistribution.getBalanceVariableInterest();
		}
		// Distribution distribution = null;
		Double totalInterestWithReducingRate = 0d;
		Double amtToAdjust = 0d;
		// Interest already calculated, not for first time
		if (distributionList != null) // Means interest has been
		// calculated previously
		{
			Double currentFixedAmt = 0d;
			Double currentVariableAmt = 0d;

			// distribution = lastDistribution;
			fixedIntAmt = 0d;
			variableIntAmt = 0d;

			compoundFixedAmt = 0d;
			compoundVariableAmt = 0d;
			totalDaysDiff = 0;

			// After interest calculation Payment or Withdraw has been made
			if (distributionList.size() > 0) {
				for (int i = index; i < distributionList.size(); i++) {

					Distribution paymentDist = distributionList.get(i);
					Integer daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
							paymentDist.getDistributionDate());

					if ((paymentDist.getAction().equalsIgnoreCase("Interest")))
						daysDifference = daysDifference + 1;
					else {
						if (DateService.getMonth(paymentDist.getDistributionDate())
								- DateService.getMonth(prevDistributionDate) != 0)
							daysDifference = daysDifference - 1;
					}

					// totalDaysDiff = totalDaysDiff + daysDifference;

					fixedAmount = distributionList.get(i).getFixedAmt() == null ? 0d
							: distributionList.get(i).getFixedAmt();
					variableAmount = distributionList.get(i).getVariableAmt() == null ? 0d
							: distributionList.get(i).getVariableAmt();

					fixedIntAmt = (prevCompoundFixedAmt * reducingInterestRate / 100) / 365 * daysDifference;
					variableIntAmt = (prevCompoundVariableAmt * reducingInterestRate / 100) / 365 * daysDifference;

					totalFixedInterest = totalFixedInterest + fixedIntAmt;
					totalVariableInterest = totalVariableInterest + variableIntAmt;

					if ((paymentDist.getAction().equalsIgnoreCase("Interest"))) {
						prevCompoundFixedAmt = prevCompoundFixedAmt + totalFixedInterest;
						prevCompoundVariableAmt = prevCompoundVariableAmt + totalVariableInterest;
						totalInterestWithReducingRate = totalInterestWithReducingRate + totalFixedInterest
								+ totalVariableInterest;
						totalFixedInterest = 0d;
						totalVariableInterest = 0d;
						fixedIntAmt = 0d;
						variableIntAmt = 0d;
					} else {
						prevCompoundFixedAmt = prevCompoundFixedAmt + fixedAmount;

						// For Payoff and TDS depositted amount comes in
						// negative
						// so adding deposittedamt means actually we are
						// deducting the actual amount
						if ((paymentDist.getAction().equalsIgnoreCase("PAYOFF"))
								|| (paymentDist.getAction().equalsIgnoreCase("TDS")))
							prevCompoundVariableAmt = prevCompoundVariableAmt
									+ (distributionList.get(i).getDepositedAmt() == null ? 0d
											: distributionList.get(i).getDepositedAmt());
						else
							prevCompoundVariableAmt = prevCompoundVariableAmt + variableAmount;
					}
					prevDistributionDate = paymentDist.getDistributionDate();
				}
			}

			// Now finding the Amount that need to adjust
			// This is coming as negative

			// Getting total interest already given

			Double totalInterestAlreadyGiven = 0d;

			for (int i = 0; i < allDistributionList.size(); i++) {
				Date distributionDate = allDistributionList.get(i).getDistributionDate();
				if (DateService.getDaysBetweenTwoDates(fromDate, distributionDate) >= 0 && DateService
						.getDaysBetweenTwoDates(distributionDate, DateService.getCurrentDateTime()) >= 0) {
					System.out.println("hhh.." + allDistributionList.get(i));
					if (allDistributionList.get(i).getFixedInterest() == null)
						allDistributionList.get(i).setFixedInterest(0d);

					if (allDistributionList.get(i).getVariableInterest() == null)
						allDistributionList.get(i).setVariableInterest(0d);

					totalInterestAlreadyGiven = totalInterestAlreadyGiven
							+ allDistributionList.get(i).getFixedInterest()
							+ allDistributionList.get(i).getVariableInterest();

				}
			}
			amtToAdjust = totalInterestWithReducingRate - totalInterestAlreadyGiven;
		}
		return amtToAdjust;
	}

	public Distribution calculateInterestForAlert(Deposit deposit, List<DepositHolder> depositHolderList) {
		Long depositId = deposit.getId();
		Date prevDistributionDate = null;
		Long lastInterestDistributionId = 0l;
		Double prevCompoundFixedAmt = 0d;
		Double prevCompoundVariableAmt = 0d;
		Double fixedIntAmt = 0d;
		Double variableIntAmt = 0d;
		Double compoundFixedAmt = 0d;
		Double compoundVariableAmt = 0d;
		Double totalFixedInterest = 0d;
		Double totalVariableInterest = 0d;
		int totalDaysDiff = 0;
		Double totalBalance = 0d;
		Double balFixedInterest = 0d;
		Double balVariableInterest = 0d;
		String action = "Interest";

		Float modifiedInterestRate = (deposit.getModifiedInterestRate() == null) ? deposit.getInterestRate()
				: deposit.getModifiedInterestRate();

		Distribution lastInterestCalculated = paymentDistributionDAO.getLastInterestCalculatedOrAdjusted(depositId,
				DateService.getCurrentDate());

		if (lastInterestCalculated != null) {
			lastInterestDistributionId = lastInterestCalculated.getId();
			prevDistributionDate = lastInterestCalculated.getDistributionDate();
			prevCompoundFixedAmt = lastInterestCalculated.getCompoundFixedAmt();
			prevCompoundVariableAmt = lastInterestCalculated.getCompoundVariableAmt();
			balFixedInterest = lastInterestCalculated.getBalanceFixedInterest();
			balVariableInterest = lastInterestCalculated.getBalanceVariableInterest();
			compoundFixedAmt = prevCompoundFixedAmt;
			compoundVariableAmt = prevCompoundVariableAmt;
			fixedIntAmt = 0d;
			variableIntAmt = 0d;
			compoundFixedAmt = 0d;
			compoundVariableAmt = 0d;
			totalDaysDiff = 0;

			// If interest is already calculated in current date then
			// do not allow to calculate again
			if (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), prevDistributionDate) == 0)
				return lastInterestCalculated;

			// Fetch all the records starting from the last interest calculated
			List<Distribution> lastPaymentDistForDepWithdraw = paymentDistributionDAO
					.getAllDepWithdrawFromLastInterestCalc(depositId, lastInterestDistributionId);

			// After interest calculation Payment or Withdraw has been made
			if (lastPaymentDistForDepWithdraw != null && lastPaymentDistForDepWithdraw.size() > 0) {
				for (int i = 0; i < lastPaymentDistForDepWithdraw.size(); i++) {
					Distribution paymentDist = lastPaymentDistForDepWithdraw.get(i);

					Integer daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
							paymentDist.getDistributionDate());

					if (daysDifference == 0 && (paymentDist.getAction().equalsIgnoreCase("TDS")
							|| paymentDist.getAction().equalsIgnoreCase("PAYOFF"))) {
						// lastInterestDistributionId =
						// lastInterestCalculated.getId();
						prevDistributionDate = paymentDist.getDistributionDate();
						prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
						prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();

						balFixedInterest = paymentDist.getBalanceFixedInterest();
						balVariableInterest = paymentDist.getBalanceVariableInterest();

						compoundFixedAmt = prevCompoundFixedAmt;
						compoundVariableAmt = prevCompoundVariableAmt;

						continue;
					}

					if (DateService.getDaysBetweenTwoDates(prevDistributionDate,
							lastInterestCalculated.getDistributionDate()) == 0)
						daysDifference = daysDifference - 1;
					// if (prevDistributionDate ==
					// lastInterestCalculated.getDistributionDate())
					// daysDifference = daysDifference - 1;

					totalDaysDiff = totalDaysDiff + daysDifference;

					fixedIntAmt = (prevCompoundFixedAmt * modifiedInterestRate / 100) / 365 * daysDifference;
					variableIntAmt = (prevCompoundVariableAmt * modifiedInterestRate / 100) / 365 * daysDifference;

					totalFixedInterest = totalFixedInterest + fixedIntAmt;
					totalVariableInterest = totalVariableInterest + variableIntAmt;

					totalBalance = paymentDist.getTotalBalance();

					prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
					prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();
					prevDistributionDate = paymentDist.getDistributionDate();

					compoundFixedAmt = prevCompoundFixedAmt;
					compoundVariableAmt = prevCompoundVariableAmt;

					balFixedInterest = paymentDist.getBalanceFixedInterest();
					balVariableInterest = paymentDist.getBalanceVariableInterest();

				}
			}

			Integer daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
					DateService.getCurrentDate());

			if (DateService.getMonth(DateService.getCurrentDate()) - DateService.getMonth(prevDistributionDate) == 0)
				daysDifference = daysDifference + 1;

			/*
			 * if
			 * (DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(),
			 * DateService.getLastDateOfCurrentMonth()) != 0) daysDifference =
			 * daysDifference - 1;
			 */
			compoundFixedAmt = prevCompoundFixedAmt;
			compoundVariableAmt = prevCompoundVariableAmt;

			fixedIntAmt = (prevCompoundFixedAmt * modifiedInterestRate / 100) / 365 * daysDifference;
			variableIntAmt = (prevCompoundVariableAmt * modifiedInterestRate / 100) / 365 * daysDifference;

			totalFixedInterest = totalFixedInterest + fixedIntAmt;
			totalVariableInterest = totalVariableInterest + variableIntAmt;

			balFixedInterest = balFixedInterest + totalFixedInterest;
			balVariableInterest = balVariableInterest + totalVariableInterest;

		} else {

			Date currentDate = DateService.getCurrentDate();
			compoundFixedAmt = 0d;
			compoundVariableAmt = 0d;
			totalBalance = 0d;
			balFixedInterest = 0d;
			balVariableInterest = 0d;

			List<Distribution> lastPaymentDistForDepWithdraw = paymentDistributionDAO
					.getAllDepWithdrawFromLastInterestCalc(depositId, 0L);

			if (lastPaymentDistForDepWithdraw != null && lastPaymentDistForDepWithdraw.size() > 0) {
				prevCompoundFixedAmt = 0d;
				prevCompoundVariableAmt = 0d;
				Integer daysDifference = 0;
				fixedIntAmt = 0d;
				variableIntAmt = 0d;
				totalDaysDiff = 0;

				for (int i = 0; i < lastPaymentDistForDepWithdraw.size(); i++) {
					Distribution paymentDist = lastPaymentDistForDepWithdraw.get(i);

					if (i == 0) {
						// distributionId = paymentDist.getId();
						prevDistributionDate = paymentDist.getDistributionDate();
						prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
						prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();

						compoundFixedAmt = prevCompoundFixedAmt;
						compoundVariableAmt = prevCompoundVariableAmt;

						balFixedInterest = paymentDist.getBalanceFixedInterest();
						balVariableInterest = paymentDist.getBalanceVariableInterest();
						continue;
					}

					daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
							paymentDist.getDistributionDate());

					fixedIntAmt = (prevCompoundFixedAmt * modifiedInterestRate / 100) / 365 * daysDifference;
					variableIntAmt = (prevCompoundVariableAmt * modifiedInterestRate / 100) / 365 * daysDifference;

					totalFixedInterest = totalFixedInterest + fixedIntAmt;
					totalVariableInterest = totalVariableInterest + variableIntAmt;

					balFixedInterest = balFixedInterest + totalFixedInterest;
					balVariableInterest = balVariableInterest + totalVariableInterest;

					totalBalance = paymentDist.getTotalBalance();

					prevCompoundFixedAmt = paymentDist.getCompoundFixedAmt();
					prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();
					prevDistributionDate = paymentDist.getDistributionDate();
					totalDaysDiff = totalDaysDiff + daysDifference;

				}
				daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate, currentDate);

				if (DateService.getMonth(DateService.getCurrentDate())
						- DateService.getMonth(prevDistributionDate) == 0)
					daysDifference = daysDifference + 1;

				totalDaysDiff = totalDaysDiff + daysDifference;

				compoundFixedAmt = prevCompoundFixedAmt;
				compoundVariableAmt = prevCompoundVariableAmt;

				fixedIntAmt = (compoundFixedAmt * modifiedInterestRate / 100) / 365 * daysDifference;
				variableIntAmt = (compoundVariableAmt * modifiedInterestRate / 100) / 365 * daysDifference;

				totalFixedInterest = totalFixedInterest + fixedIntAmt;
				totalVariableInterest = totalVariableInterest + variableIntAmt;

				balFixedInterest = balFixedInterest + totalFixedInterest;
				balVariableInterest = balVariableInterest + totalVariableInterest;

			}

		}

		// current fixed amount will be zero
		compoundFixedAmt = compoundFixedAmt + totalFixedInterest;
		compoundVariableAmt = compoundVariableAmt + totalVariableInterest;

		totalBalance = compoundFixedAmt + compoundVariableAmt;

		// saving
		Distribution paymentDistribution = new Distribution();
		paymentDistribution.setDistributionDate(DateService.getCurrentDate());
		paymentDistribution.setDepositId(depositId);
		paymentDistribution.setDaysToCalcInterest(totalDaysDiff);
		paymentDistribution.setFixedInterest(round(totalFixedInterest, 2));

		paymentDistribution.setVariableInterest(round(totalVariableInterest, 2));

		paymentDistribution.setBalanceFixedInterest(round(balFixedInterest, 2));
		paymentDistribution.setBalanceVariableInterest(round(balVariableInterest, 2));
		paymentDistribution.setCompoundFixedAmt(round(compoundFixedAmt, 2));
		paymentDistribution.setCompoundVariableAmt(round(compoundVariableAmt, 2));
		paymentDistribution.setAction(action);
		paymentDistribution.setTotalBalance(round(totalBalance, 2));

		return paymentDistribution;
	}

	////////////
	public Double getAmountToAdjustForTenureChangeAlert(Long depositId, Distribution lastBaseLine, Float interestRate) {

		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(depositId);

		// Get total interest given till date
		Double fixedInterestGiven = ((depositSummary.getTotalFixedInterestAccumulated() == null)? 0d: depositSummary.getTotalFixedInterestAccumulated())
				+ ((depositSummary.getTotalFixedInterestPaidOff() == null)? 0d: depositSummary.getTotalFixedInterestPaidOff()); 
		Double variableInterestGiven =((depositSummary.getTotalVariableInterestAccumulated() == null)? 0d: depositSummary.getTotalVariableInterestAccumulated())
				+ ((depositSummary.getTotalVariableInterestPaidOff() == null)? 0d: depositSummary.getTotalVariableInterestPaidOff()); 

		fixedInterestGiven = round(fixedInterestGiven,2) * -1;
		variableInterestGiven = round(variableInterestGiven,2) * -1;
				
		// Getting the duration from the base
		Integer durationFromBase = DateService.getDaysBetweenTwoDates(lastBaseLine.getDistributionDate(),
				DateService.getCurrentDate()) + 1;

		// Get the adjustment amount
		Double fixedInterest = ((lastBaseLine.getCompoundFixedAmt() * interestRate / 100 / 365) * durationFromBase);
		Double variableInterest = ((lastBaseLine.getCompoundVariableAmt() * interestRate / 100 / 365)
				* durationFromBase);
		fixedInterest =   fixedInterestGiven + fixedInterest;
		variableInterest = variableInterestGiven + variableInterest;
		Double totalInterest = fixedInterest + variableInterest;
		
		return totalInterest;

	}

	

	// public Distribution withdrawFromDeposit(Deposit deposit, WithdrawForm
	// withdrawForm,
	// Distribution lastInterestDistribution, List<DepositHolder>
	// depositHolderList,
	// Distribution lastBaseLine, String createdBy)

	public Distribution withdrawFromDeposit(Deposit deposit, WithdrawForm withdrawForm, Distribution lastDistribution,
			List<DepositHolder> depositHolderList, Distribution lastBaseLine, String createdBy) throws NumberFormatException, CustomException {

		
		// 1. Withdraw the amount as well in WithdrawDeposit Table.
		// 2. Insert in PaymentDistribution
		// 3. Update the Deposit Summary

		
		// Getting the duration from the base
		EndUser custName = endUserDAO.getByUserName(createdBy);
		String userName = custName.getDisplayName();

		/*
		 * Integer durationFromBase =
		 * DateService.getDaysBetweenTwoDates(lastBaseLine.getDistributionDate()
		 * , DateService.getCurrentDate()) + 1;
		 * 
		 * // Get the interest rate of the Duration from the base List<Object[]>
		 * deposits =
		 * fixedDepositDao.getDepositForInterestRate(deposit.getId());//
		 * fixedDepositDao.getDeposit(depositId); String category = "General";
		 * if (deposits.size() > 0) { Object[] depositsForcategory =
		 * deposits.get(0); category = depositsForcategory[8].toString(); }
		 * Float newInterestRateForThePeriod =
		 * depositRateDAO.getInterestRate(category, durationFromBase); //
		 * Calculate the Interest Double interestOnWithdrawAmt =
		 * (withdrawForm.getWithdrawAmount() * newInterestRateForThePeriod /
		 * 100) / 365 durationFromBase;
		 */
		// -------------------------------------------------------------------------------
		String toAccountNo = null;
		
		
		String transactionId = this.generateRandomString();
		if(withdrawForm.getModeOfPayment()==null || withdrawForm.getModeOfPayment()=="") {
			withdrawForm.setModeOfPayment("Fund Transfer");
		}
		
		ModeOfPayment mop=modeOfPaymentDAO.getModeOfPayment(withdrawForm.getModeOfPayment());

		// 1. Withdraw the amount
		// -------------------------------------------------------------------------------
		WithdrawDeposit withdrawDeposit = new WithdrawDeposit();
		withdrawDeposit.setDepositHolderId(withdrawForm.getDepositHolderId());
		withdrawDeposit.setPaymentMadeByHolderIds(withdrawForm.getPaymentMadeByHolderIds());
		withdrawDeposit.setDepositId(withdrawForm.getDepositId());
		withdrawDeposit.setWithdrawAmount(withdrawForm.getWithdrawAmount());
		withdrawDeposit.setPaymentMode(mop.getPaymentMode());
		withdrawDeposit.setPaymentModeId(mop.getId());
		withdrawDeposit.setTransactionId(transactionId);
		withdrawDeposit.setCreatedBy(createdBy);
		withdrawDeposit.setCustomerName(userName);
		withdrawDeposit.setWithdrawDate(DateService.getCurrentDateTime());
		// Earlier we are giving InterestAmount with the WithdrawAmount to the
		// customer
		// but from 22/8/2018 changes with discussion with Partho Sir, we are
		// changing the concept of withdraw. We will add the interest amount
		// with principal.
		// And we will not do any interest adjustment

		withdrawDeposit.setInterestAmount(withdrawForm.getWithdrawAmount());
		withdrawDeposit.setTotalAmount(withdrawForm.getWithdrawAmount());
		
		if (mop.getPaymentMode().equalsIgnoreCase("DD")
				|| mop.getPaymentMode().equalsIgnoreCase("Cheque")) {
			withdrawDeposit.setChequeDDNumber(withdrawForm.getChequeDDNumber());
			withdrawDeposit.setBranch(withdrawForm.getBranch());
			withdrawDeposit.setBank(withdrawForm.getChequeDDBank());
			withdrawDeposit.setChequeDDdate(withdrawForm.getChequeDDdate());
			
			toAccountNo = withdrawForm.getChequeDDNumber();
			

		}

		else if (mop.getIsLinkedWithCASAAccount()!= null && mop.getIsLinkedWithCASAAccount()==1) {
			Double withdrawAmt = withdrawForm.getWithdrawAmount();

			// update saving/current account
			for (int i = 0; i < depositHolderList.size(); i++) {

				DepositHolder depositHolder = depositHolderList.get(i);
				Long depositHolderId = depositHolder.getDepositId();
				float contribution = depositHolder.getContribution();
				Double holderWithdrawAmt = withdrawAmt * contribution / 100;

				depositHolder.setDistAmtOnMaturity(holderWithdrawAmt);
				depositHolderDAO.updateDepositHolder(depositHolder);

				AccountDetails account = accountDetailsDAO.findByAccountNo(withdrawForm.getAccountNumber());
				if (account != null) {
					// Credited the saving account
					account.setAccountBalance(account.getAccountBalance() + holderWithdrawAmt);
					accountDetailsDAO.updateUserAccountDetails(account);
					
					// For accounting entry only
					toAccountNo = withdrawForm.getAccountNumber();
					
							
				}
				
				
			}
		}
		withdrawDeposit = paymentDistributionDAO.insertWithDrawPayment(withdrawDeposit);
		// -----------------------------------------------------------------------------

		// 2. Insert in PaymentDistribution
		// -----------------------------------------------------------------------------
		lastDistribution = calculationService.insertInPaymentDistribution(deposit, depositHolderList, withdrawForm.getWithdrawAmount(),
				withdrawDeposit.getId(), Constants.WITHDRAW, null, null, null, null, null, null, null);
		// -----------------------------------------------------------------------------

		// 3. Update in DepositSummary
		// -----------------------------------------------------------------------------
		DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.WITHDRAW, withdrawForm.getWithdrawAmount(), null, null, 
				null, null, depositHolderList, null, null, null);
		// -----------------------------------------------------------------------------
//		// update the deposit table
//		deposit.setCurrentBalance(totalbalance);
//		fixedDepositDao.updateFD(deposit);
		// ----------------------------------------------------------------------
		
		// Insert in Journal & Ledger
		//-----------------------------------------------------------
		
		ledgerService.insertJournal(deposit.getId(), this.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(), 
				deposit.getAccountNumber(), toAccountNo,Event.WITHDRAW_DEPOSIT.getValue(),
				withdrawForm.getWithdrawAmount(),
				mop.getId(), depositSummary.getTotalPrincipal(),
				transactionId);
		
			//-----------------------------------------------------------

		return lastDistribution;
	}

@Transactional
	public void withdrawOperation(WithdrawForm withdrawForm, String username) throws CustomException {

		// Check the validations to restrict withdraw	
		Deposit deposit = fixedDepositDao.findById(withdrawForm.getDepositId());
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(deposit.getProductConfigurationId());
		if(productConfiguration==null){
			productConfiguration = productConfigurationDAO.findByProductCode(deposit.getProductConfigurationId().toString());
		}
		if(productConfiguration.getIsWithdrawAllowed() != null && productConfiguration.getIsWithdrawAllowed()==0)
			throw new CustomException("Sorry, Withdraw is not allowed.");
		
		String lockingPeriodForWithdraw = productConfiguration.getLockingPeriodForWithdraw();
		String[] arrlockingPeriodForTopup = lockingPeriodForWithdraw.split(",");
		
		
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
		
		if(DateService.getDaysBetweenTwoDates(lockingDate, DateService.getCurrentDate()) < 0)
			throw new CustomException("Sorry, Deposit is still within the locking period for Withdraw.");
		
		Date maturityDate = deposit.getNewMaturityDate() != null? deposit.getNewMaturityDate() : deposit.getMaturityDate();
		if(productConfiguration.getPreventionOfWithdrawBeforeMaturity()!=null)
		{
			if(DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate) <= productConfiguration.getPreventionOfWithdrawBeforeMaturity())
				throw new CustomException("Sorry, Withdraw has prevented for last " + productConfiguration.getPreventionOfWithdrawBeforeMaturity() + " days from maturity.");
		}
	
		
		
		Date currentDate = DateService.getCurrentDate();
		
		// Get the DepositHolder
		DepositHolder depositHolder = depositHolderDAO.findById(withdrawForm.getDepositHolderId());

		// Get the last BaseLine before interest calculation
		// because base line will be the topup/last interest or last compounding
		Distribution lastBaseLine = paymentDistributionDAO.getLastBaseLine(withdrawForm.getDepositId());
		// Distribution lastBaseLine = paymentDistributionDAO.get

		// **************************************************************************************

		// **************************************************************************************

		// Steps
		// 1. Interest calculation till date
		// 2. Withdraw the amount
		// 3. Interest Earned on Withdraw Amount
		// 4. Interest Adjustment and penalty for the Withdraw
		// i.e. Interest Adjustment will be the Amount
		// which has been already earned will be taken out from the user.
		// Hence, Amount will be (the total interest compounded till now -
		// Interest Earned on Withdraw Amount)

		
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(withdrawForm.getDepositId());
		Distribution lastDistribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());

		
		// Start Date -- 12/3/2018
		// 1. First Calculate the interest up to the withdrawalDate		
		// 2. Adjust the Interest
		// 3. Withdraw the Amount

		// 1. Calculate the interest
		// -----------------------------------------
		Distribution lastInterestCalculated = calculationService.calculateInterest(deposit, depositHolderList,"", null);
		// -----------------------------------------

		// 2. Withdraw the Amount
		// -----------------------------------------
		lastDistribution = this.withdrawFromDeposit(deposit, withdrawForm, lastInterestCalculated, depositHolderList,
				lastBaseLine, username);
		// -----------------------------------------
		
		// 3. Deduct the penalty from the withdraw amount
		// -----------------------------------------
		// Check if SlabBasedPenaltyRequiredForWithdraw is configured or not 
		// if configured, then deduct the penalty amount
		if((productConfiguration.getIsSlabBasedPenaltyRequiredForWithdraw() != null && productConfiguration.getIsSlabBasedPenaltyRequiredForWithdraw() == 1))
			lastDistribution = calculationService.deductWithdrawPenalty(deposit, withdrawForm.getWithdrawAmount(), lastDistribution, false, 0d);	
		// -----------------------------------------
				
		// 4. Give the Interest for Withdraw Amount and Adjust the extra interest given from the amount
		// -----------------------------------------
		Boolean isAdjustmentRequired = true;
		if(deposit.getDepositCategory() != null && deposit.getDepositCategory().equals("AUTO") && deposit.getSweepInType() != null && deposit.getSweepInType().contains("Dynamic Interest"))
		{
			isAdjustmentRequired = false;
		}
		if(isAdjustmentRequired)
			lastDistribution= calculationService.adjustInterestForWithdraw(deposit, lastDistribution, depositHolderList, lastBaseLine, productConfiguration);
		// -----------------------------------------		
		
		
		
		
		
//		// 4. Interest earned from withdrawal amount from last base line to
//		// withdraw date
//		// -----------------------------------------
//		Integer durationFromBase = DateService.getDaysBetweenTwoDates(lastBaseLine.getDistributionDate(),
//				DateService.getCurrentDate()) + 1;
//
//		// Get the new interest rate of the Duration from the base
//		Float newInterestRateForThePeriod = depositRateDAO.getInterestRate(deposit.getPrimaryCustomerCategory(), deposit.getDepositCurrency(), durationFromBase, deposit.getDepositClassification(), deposit.getDepositAmount());
//
//		// Calculate the Interest
//		Double interestOnWithdrawAmt = (withdrawForm.getWithdrawAmount() * newInterestRateForThePeriod / 100) / 365
//				* durationFromBase;
//		
//		
//		// Get the fixed and variable rate 
//		Rates rates = ratesDAO.getAllRatesByCustomerCategoryCitizenAndAccountType( deposit.getPrimaryCustomerCategory(), 
//				deposit.getPrimaryCitizen(), deposit.getPrimaryNRIAccountType());
//	
////		Float fixedRate = 0f;
////		if (rates != null) {
////			fixedRate = rates.getFdFixedPercent();
////			
////		Double fixedInterest = interestOnWithdrawAmt* fixedRate/100;
////		Double variableInterest = interestOnWithdrawAmt - fixedInterest;
//		calculationService.addInterest(deposit, depositHolderList, newInterestRateForThePeriod, 0d, interestOnWithdrawAmt);
		// -----------------------------------------

				
		//context.publishEvent(new WithdrawEvent(this, "Withdraw", deposit));

	}


@Transactional
public void withdrawOperationForSweep(WithdrawForm withdrawForm, String username) throws CustomException {

	// Check the validations to restrict withdraw	
	Deposit deposit = fixedDepositDao.findById(withdrawForm.getDepositId());
	
	Date currentDate = DateService.getCurrentDate();
	
	// Get the DepositHolder
	DepositHolder depositHolder = depositHolderDAO.findById(withdrawForm.getDepositHolderId());

	// Get the last BaseLine before interest calculation
	// because base line will be the topup/last interest or last compounding
	Distribution lastBaseLine = paymentDistributionDAO.getLastBaseLine(withdrawForm.getDepositId());
	// Distribution lastBaseLine = paymentDistributionDAO.get

	// **************************************************************************************

	// **************************************************************************************

	// Steps
	// 1. Interest calculation till date
	// 2. Withdraw the amount
	// 3. Interest Earned on Withdraw Amount
	// 4. Interest Adjustment and penalty for the Withdraw
	// i.e. Interest Adjustment will be the Amount
	// which has been already earned will be taken out from the user.
	// Hence, Amount will be (the total interest compounded till now -
	// Interest Earned on Withdraw Amount)

	
	List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(withdrawForm.getDepositId());
	Distribution lastDistribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());

	
	// Start Date -- 12/3/2018
	// 1. First Calculate the interest up to the withdrawalDate		
	// 2. Adjust the Interest
	// 3. Withdraw the Amount

	// 1. Calculate the interest
	// -----------------------------------------
	Distribution lastInterestCalculated = calculationService.calculateInterest(deposit, depositHolderList,"", null);
	// -----------------------------------------

	// 2. Withdraw the Amount
	// -----------------------------------------
	lastDistribution = this.withdrawFromDeposit(deposit, withdrawForm, lastInterestCalculated, depositHolderList,
			lastBaseLine, username);
	// -----------------------------------------		
	
	// 3. Give the Interest for Withdraw Amount and Adjust the extra interest given from the amount
	// -----------------------------------------
	Boolean isAdjustmentRequired = true;
	if(deposit.getDepositCategory() != null && deposit.getDepositCategory().equals("AUTO") && deposit.getSweepInType() != null && deposit.getSweepInType().contains("Dynamic Interest"))
	{
		isAdjustmentRequired = false;
	}
	if(isAdjustmentRequired)
		lastDistribution= calculationService.adjustInterestForWithdraw(deposit, lastDistribution, depositHolderList, lastBaseLine, null);
	// -----------------------------------------		

}
	
//	private Distribution deductWithdrawPenalty(Deposit deposit, Double withdrawAount, Distribution lastDistribution) throws CustomException
//	{
//		// Check if SlabBasedPenaltyRequiredForWithdraw is configured or not 
////		if((productConfiguration.getIsSlabBasedPenaltyRequiredForWithdraw() != null && productConfiguration.getIsSlabBasedPenaltyRequiredForWithdraw() == 1))
////					return lastDistribution;
//					
//		// get the withdraw penalty master for the product Configuration
//		WithdrawPenaltyMaster withdrawPenaltymaster = withdrawPenaltyDAO.getWithdrawPenalyMaster(deposit.getProductConfigurationId(), false);
//		
//		if(withdrawPenaltymaster == null)
//			return lastDistribution;
//
//		Double penaltyAmount = 0d;
//		if(withdrawPenaltymaster.getWithdrawType().equals("Tenure Based"))
//		{
//			// Get the tenureBased Penalty
//			WithdrawPenaltyTenureBased tenureBasedWithdrawPenalty = withdrawPenaltyDAO.getTenureBasedWithdrawPenalty(withdrawPenaltymaster.getId());
//			
//			Float penaltyRate = tenureBasedWithdrawPenalty.getPenaltyRate();
//			
//			
//			penaltyAmount = penaltyRate == null? tenureBasedWithdrawPenalty.getPenaltyFlatAmount() :(withdrawAount * penaltyRate/100);
//			
//		}
//		
//		if(withdrawPenaltymaster.getWithdrawType().equals("Amount Based"))
//		{
//			// Get the amountBased Penalty
//			WithdrawPenaltyAmountBased amountBasedWithdrawPenalty = withdrawPenaltyDAO.getAmountBasedWithdrawPenalty(withdrawPenaltymaster.getId());
//			// WithdrawPenalty
//			return lastDistribution;
//		}
//		
//		if(penaltyAmount == 0)
//			return lastDistribution;
//		
//		if(lastDistribution.getCompoundVariableAmt()<penaltyAmount)
//			throw new CustomException("Cant withdraw the amount, because penalty Amount is more than your withdrawable amount");
//		
//		penaltyAmount = calculationService.round(penaltyAmount,2);
//		
//		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
//		
//		// Insert penalty in PaymentDistribution and Holderwise PaymentDistribution and DepositSummary table
//		// -----------------------------------------
//		Distribution distribution1 = calculationService.insertInDistributionForPenaltyInFixedVariable(Constants.withdrawPenalty,
//				deposit, depositHolderList, 0d, penaltyAmount);
//		
//		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(deposit.getId());
//		depositSummary = calculationService.upsertInDepositSummaryForPenaltyInFixedVariable(deposit, Constants.withdrawPenalty, depositHolderList,  0d, penaltyAmount);
//		// -----------------------------------------
//		
//		// Insert in Journal & Ledger
//		//-----------------------------------------------------------
//		ledgerService.insertJournalLedger(deposit.getId(), DateService.getCurrentDate(), 
//				deposit.getAccountNumber(), "Deposit Account", null, "Charges Account", 
//				Constants.withdrawPenalty, (penaltyAmount), "Internal Tranasfer", 
//				depositSummary.getTotalPrincipal());		
//		//-----------------------------------------------------------
//		
//		// WithdrawPenalty
//		return distribution1;
//	}
	

	public Distribution adjustInterestForTenureChangeOrConversion(String action, Deposit deposit, Distribution lastDistribution,
			List<DepositHolder> depositHolderList, Distribution lastBaseLine, Float interestRate)
	{
//		// Getting the duration from the base
//		Integer durationFromBase = DateService.getDaysBetweenTwoDates(lastBaseLine.getDistributionDate(),
//				DateService.getCurrentDate());

		// Get the Interest from the Distribution that needs to be adjusted
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(deposit.getId());
		
  	    // Get total interest given till date
		Double fixedInterestGiven = ((depositSummary.getTotalFixedInterestAccumulated() == null)? 0d: depositSummary.getTotalFixedInterestAccumulated())
				+ ((depositSummary.getTotalFixedInterestPaidOff() == null)? 0d: depositSummary.getTotalFixedInterestPaidOff()); 
		Double variableInterestGiven =((depositSummary.getTotalVariableInterestAccumulated() == null)? 0d: depositSummary.getTotalVariableInterestAccumulated())
				+ ((depositSummary.getTotalVariableInterestPaidOff() == null)? 0d: depositSummary.getTotalVariableInterestPaidOff()); 				
			
		fixedInterestGiven = round(fixedInterestGiven,2) * -1;
		variableInterestGiven = round(variableInterestGiven,2) * -1;
		
		// Getting the duration from the base
		Integer durationFromBase = DateService.getDaysBetweenTwoDates(lastBaseLine.getDistributionDate(),
				DateService.getCurrentDate()) + 1;


		// Get the adjustment amount
		Double fixedInterest = ((lastBaseLine.getCompoundFixedAmt() * interestRate / 100 / 365) * durationFromBase);
		Double variableInterest = ((lastBaseLine.getCompoundVariableAmt() * interestRate / 100 / 365)
				* durationFromBase);
		fixedInterest =   fixedInterestGiven + fixedInterest;
		variableInterest = variableInterestGiven + variableInterest;
		Double totalInterest = fixedInterest + variableInterest;
		
		// Insert the interest adjustment in Interest table
		Interest interest = new Interest();
		interest.setDepositId(deposit.getId());
		interest.setFinancialYear(DateService.getFinancialYear(DateService.loginDate));
		interest.setInterestAmount(totalInterest);
		interest.setInterestDate(DateService.loginDate);
		interest.setIsCalculated(1);
		interest.setInterestRate(round((double) interestRate, 2));
		//interest.setInterestSum(round((compoundFixedAmt + compoundVariableAmt), 2));
		interestDAO.insert(interest);
		

		// Saving in Distribution and HolderWise Distribution
		Distribution paymentDistribution = calculationService.insertInPaymentDistribution(deposit, depositHolderList, null, null, action, 
				fixedInterest, variableInterest, null, null, null, null, null);


		// Update in Deposit Summary and Deposit Holder Summary Details
		calculationService.upsertInDepositSummary(deposit, action, null, null, null, null, null,
				depositHolderList, fixedInterest, variableInterest, null);
			
		
		// Insert in Journal & Ledger
		//-----------------------------------------------------------
		//ToDo:
//		ledgerService.insertJournalLedger(deposit.getId(),this.getPrimaryCustomerId(depositHolderList), DateService.getCurrentDate(), 
//				deposit.getAccountNumber(),  "Deposit Account", null,  "Interest Account", 
//				 Constants.FUNDTRANSFER, totalInterest, "Internal Tranasfer", 
//				0d);		
		//-----------------------------------------------------------
		// update the deposit table
		deposit.setCurrentBalance(lastDistribution.getCompoundFixedAmt() + lastDistribution.getCompoundVariableAmt());
		fixedDepositDao.updateFD(deposit);

		
		return lastDistribution;



	}

	public Distribution DeductEMI(Deposit deposit, List<DepositHolder> depositHolderList,
			List<BenificiaryDetails> beneficiaryDetailList, Double emiAmt, Distribution lastDistribution) throws CustomException {
		boolean insufficientAmt = false;
		Double variableCompoundAmt = lastDistribution.getCompoundVariableAmt();
		// check the emi amount available or not
		if (emiAmt > variableCompoundAmt)
			insufficientAmt = true;

		if (insufficientAmt) {
			// update next payoffDate
			this.updatePayOffDueDate(deposit, deposit.getPayOffInterestType());
			return null;
		}

		// If full emi amount is not available for next installment,
		// add the remaining amount with the last emi amount
		if ((variableCompoundAmt - emiAmt) < emiAmt)
			emiAmt = variableCompoundAmt; // i.e. emiAmt +
											// (variableCompoundAmt-emiAmt);
		// For last payoff emi will be balance left
		if (DateService.getDaysBetweenTwoDates(DateService.loginDate, deposit.getMaturityDate()) == 0)
			emiAmt = variableCompoundAmt;

		// i.e. emiAmt +
		// 1. Finding the interest for EMI Amount
		// 2. PayOff the amount as well as the interest calculated in point 1 in
		// ReverseEMI Table.
		// 3. Insert in PaymentDistribution

		// 1. Finding the interest on WithdrawAmount for the shorter duration
		// from baseline
		// -------------------------------------------------------------------------------
		// Getting the duration from the base
		// Integer durationFromBase =
		// DateService.getDaysBetweenTwoDates(lastBaseLine.getDistributionDate(),
		// DateService.getCurrentDate()) + 1;
		//
		// // Get the interest rate of the Duration from the base
		// List<Object[]> deposits =
		// fixedDepositDao.getDepositForInterestRate(deposit.getId());//
		// fixedDepositDao.getDeposit(depositId);
		// String category = "General";
		// if (deposits.size() > 0) {
		// Object[] depositsForcategory = deposits.get(0);
		// category = depositsForcategory[8].toString();
		// }
		// Float newInterestRateForThePeriod =
		// depositRateDAO.getInterestRate(category, durationFromBase);
		// // Calculate the Interest
		// Double interestOnWithdrawAmt = (emiAmt * newInterestRateForThePeriod
		// / 100) / 365
		// * durationFromBase;
		// -------------------------------------------------------------------------------

		// 2. PayOff the amount as well as the interest calculated in point 1 in
		// ReverseEMI Table
		// -------------------------------------------------------------------------------
		emiAmt = emiAmt * (-1);
		double compoundFixedAmt = lastDistribution.getCompoundFixedAmt();
		Double compoundVariableAmt = lastDistribution.getCompoundVariableAmt() + emiAmt;
		
		String action = "EMI";
		
		
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(Constants.FUNDTRANSFER);	
		String transactionId = this.generateRandomString();


		// Insert in ReverseEMI table
		ReverseEMI reverseEmi = new ReverseEMI();
		reverseEmi.setDepositHolderId(this.getPrimaryHolderId(depositHolderList));
		reverseEmi.setDepositId(deposit.getId());
		reverseEmi.setEmiAmount(-emiAmt);
		reverseEmi.setModeOfPayment(mop.getId().toString());
		reverseEmi.setCreatedBy("SYSTEM");
		reverseEmi.setTransactionId(transactionId);
		reverseEmi = reverseEMIDAO.insertReverseEMI(reverseEmi);

		for (int i = 0; i < beneficiaryDetailList.size(); i++) {
			BenificiaryDetails beneficiaryDetails = beneficiaryDetailList.get(i);
			ReverseEMIPayoffDetails reverseEMIPayOffDetails = new ReverseEMIPayoffDetails();
			reverseEMIPayOffDetails.setDepositId(deposit.getId());
			reverseEMIPayOffDetails.setDepositHolderId(beneficiaryDetails.getDepositHolderId());
			reverseEMIPayOffDetails.setBeneficiaryDetailsId(beneficiaryDetails.getId());
			reverseEMIPayOffDetails.setReverseEMIId(reverseEmi.getId());
			// reverseEMIPayOffDetails.setEmiAmount(-emiAmt);
			reverseEMIPayOffDetails.setEmiAmount(-beneficiaryDetails.getAmountToTransfer());
			reverseEMIPayOffDetails.setBeneficiaryName(beneficiaryDetails.getBenificiaryName());
			reverseEMIPayOffDetails.setBeneficiaryAccountType(beneficiaryDetails.getBankAccountType());
			reverseEMIPayOffDetails.setBeneficiaryAccountNumber(beneficiaryDetails.getBenificiaryAccountNumber());
			reverseEMIPayOffDetails.setBeneficiaryIFSCCode(beneficiaryDetails.getIfscCode());
			reverseEMIPayOffDetails.setPayoffAmount(beneficiaryDetails.getAmountToTransfer());
			// reverseEMIPayOffDetails.setPayOffBankName(beneficiaryDetails.get);
			// reverseEMIPayOffDetails.setPayOffBranch(payOffBranch);
			reverseEMIPayOffDetails = reverseEMIDAO.insertReverseEMIPayoffDetails(reverseEMIPayOffDetails);
		}
		// -------------------------------------------------------------------------------


		// update next payoffDate
		this.updatePayOffDueDate(deposit, deposit.getPayOffInterestType());
		
		// -------------------------------------------------------------------------------
		// Insert penalty in PaymentDistribution and Holderwise PaymentDistribution
		Distribution distribution = calculationService.insertInPaymentDistribution(deposit, depositHolderList, emiAmt, null, 
				Constants.EMI, null, null, null, null, null, null, null);
		
		// Update in DepositSummary and DepositHolderWiseSummary
		DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.EMI, emiAmt, null, null, null, null, depositHolderList, null, null, null);
		// -----------------------------------------
		

		// Insert in Journal & Ledger
		//-----------------------------------------------------------	
		//Beneficiary LOOP emiAmount
		for (int i = 0; i < beneficiaryDetailList.size(); i++)
		{
			ledgerService.insertJournal(deposit.getId(),beneficiaryDetailList.get(i).getCustomerId(), DateService.getCurrentDate(), 
					deposit.getAccountNumber(), beneficiaryDetailList.get(i).getBenificiaryName() + "-" + beneficiaryDetailList.get(i).getBenificiaryAccountNumber(),
					Event.ANNUITY_EMI.getValue(), beneficiaryDetailList.get(i).getAmountToTransfer(), mop.getId(),
					depositSummary.getTotalPrincipal(), transactionId);
		}
	
		//-----------------------------------------------------------


		// update the deposit table
		deposit.setCurrentBalance(round(compoundFixedAmt + compoundVariableAmt, 2));
		fixedDepositDao.updateFD(deposit);

		return distribution;

	}

	public Distribution adjustInterestForReverseEMI(Deposit deposit, Distribution lastDistribution,
			List<DepositHolder> depositHolderList, Distribution lastBaseLine)

	{
		// Getting the duration from the base
		Integer durationFromBase = DateService.getDaysBetweenTwoDates(lastBaseLine.getDistributionDate(),
				DateService.getCurrentDate()) + 1;

		String action = "Interest Adjustment For EMI";
		Float interestRate = deposit.getModifiedInterestRate() == null ? deposit.getInterestRate()
				: deposit.getModifiedInterestRate();

		Double fixedInterest = lastBaseLine.getCompoundFixedAmt(); // ((lastBaseLine.getCompoundFixedAmt()
																	// *
																	// interestRate
																	// / 100) /
																	// 365 *
																	// durationFromBase)
																	// * -1;
		Double variableInterest = ((lastBaseLine.getCompoundVariableAmt() * interestRate / 100) / 365
				* durationFromBase) * -1;

		// Insert in Distribution table
		// Insert in HolderWise Distribution table
		Distribution paymentDistribution = new Distribution();
		paymentDistribution.setAction(action);
		paymentDistribution.setDistributionDate(DateService.getCurrentDate());

		// paymentDistribution.setFixedAmt();
		// paymentDistribution.setVariableAmt();
		paymentDistribution.setFixedInterest(fixedInterest);
		paymentDistribution.setVariableInterest(variableInterest);

		Double compoundFixedAmt = FDService.round(lastDistribution.getCompoundFixedAmt() + fixedInterest, 2);
		Double compoundVariableAmt = FDService.round(lastDistribution.getCompoundVariableAmt() + variableInterest, 2);

		paymentDistribution.setCompoundFixedAmt(compoundFixedAmt);
		paymentDistribution.setCompoundVariableAmt(compoundVariableAmt);

		paymentDistribution.setTotalBalance(compoundFixedAmt + compoundVariableAmt);
		paymentDistribution.setDepositId(deposit.getId());
		paymentDistribution.setDepositHolderId(this.getPrimaryHolderId(depositHolderList));
		// paymentDistribution.setActionId(withdrawDeposit.getId());
		paymentDistribution.setBalanceFixedInterest(lastDistribution.getBalanceFixedInterest() + fixedInterest);
		paymentDistribution
				.setBalanceVariableInterest(lastDistribution.getBalanceVariableInterest() + variableInterest);

		lastDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

		//COM--this.insertInDepositHolderWiseDistribution(depositHolderList, action, null, fixedInterest + variableInterest);

		// update the deposit table
		deposit.setCurrentBalance(FDService.round((compoundFixedAmt + compoundVariableAmt), 2));
		fixedDepositDao.updateFD(deposit);

		return lastDistribution;
	}

	/*public List<Interest> getInterestBreakupForEMI(Date fromDate, Date maturityDate, FixedDepositForm fixedDepositForm,
			Deposit deposit) {
		// Date maturityDate =
		// this.getMaturityDate(fixedDepositForm.getFdTenureType(),
		// fixedDepositForm.get //Date fromDate = DateService.getCurrentDate();
		List<Date> interestList = this.getInterestDatesForModification(fromDate, maturityDate);
		List<Date> depositList = this.getDepositDatesForModification(fixedDepositForm, maturityDate);
		Long depositId = fixedDepositForm.getDepositId();

		// Get last interest calculated/adjusted for the period from
		// Distribution table.
		// It is required to calculated the interest till today for which
		// interest is not yet calculated
		Distribution lastInterestCalculated = paymentDistributionDAO.getLastInterestCalculatedOrAdjusted(depositId,
				DateService.getCurrentDate());

		Date firstDateOfMonth = null;
		if (lastInterestCalculated != null)
			firstDateOfMonth = DateService.addDays(lastInterestCalculated.getDistributionDate(), 1);
		else
			firstDateOfMonth = deposit.getCreatedDate();

		Date lastDateOfMonth = DateService.getLastDateOfCurrentMonth();

		// Last modification
		Double modifiedDepositAmount = round((double) fixedDepositForm.getFdAmount(), 2);
		Double modifiedInterestRate = round((double) fixedDepositForm.getFdInterest(), 2);
		Float preiviousInterestRate = depositModificationDAO.getPreviousDepositRate(depositId, fromDate);
		// Double

		// Distributions in modification month
		List<Distribution> distributionList = paymentDistributionDAO.getDistributionListFrom(depositId,
				lastInterestCalculated != null ? lastInterestCalculated.getId() : 0);

		// Last Distribution before firstDateOfMonth
		// Distribution lastDistributionOfPreviousMonth = paymentDistributionDAO
		// .getLastDistributionOfPreviousMonth(depositId, firstDateOfMonth);
		Date lastDistributionDate = lastInterestCalculated == null ? null
				: lastInterestCalculated.getDistributionDate();
		Double balAmount = lastInterestCalculated == null ? 0.0 : lastInterestCalculated.getTotalBalance();

		List<Interest> interestClassList = new ArrayList<>();
		// Double compoundAmt = 0.0;
		int dateDiff = 0;
		Double intAmt = 0.0;

		int n = 0;
		int j = 0;
		Double compoundInterest = 0.0;
		for (int i = 0; i < interestList.size(); i++) {
			Date interestDate = interestList.get(i);

			if (i > 0)
				firstDateOfMonth = DateService.getFirstDateOfMonth(interestDate);
			lastDateOfMonth = DateService.getLastDateOfMonth(interestDate);

			// Payment/withdraw etc is done
			if (distributionList != null) {
				for (int k = j; k < distributionList.size(); k++) {
					Distribution distribution = distributionList.get(k);
					if (lastDistributionDate == null) {
						firstDateOfMonth = distribution.getDistributionDate();
					} else {

						int dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth,
								distribution.getDistributionDate());

						if (DateService.getMonth(firstDateOfMonth) != DateService
								.getMonth(DateService.getCurrentDate()))
							dayDiff = dayDiff - 1;

						if ((distribution.getAction().equalsIgnoreCase("TDS")
								|| distribution.getAction().equalsIgnoreCase("PAYOFF"))) {
							balAmount = distribution.getTotalBalance();
						}
						if (dayDiff > 0) {
							intAmt = (balAmount * preiviousInterestRate / 100) / 365 * dayDiff;
							compoundInterest = compoundInterest + intAmt;
							// totalInterestAmt = totalInterestAmt + intAmt;
						}

					}

					System.out.println("Rate: " + preiviousInterestRate + " ---- " + "Distribution Date: "
							+ distribution.getDistributionDate());
					balAmount = distribution.getTotalBalance();
					firstDateOfMonth = distribution.getDistributionDate();
					lastDistributionDate = distribution.getDistributionDate();

					if (DateService.getDaysBetweenTwoDates(distribution.getDistributionDate(), interestDate) <= 0) {
						j = k;
						break;
					}

					j++;
				}
			}

			// future payment
			if (depositList != null) {

				for (int m = n; m < depositList.size(); m++) {
					Date depositDate = depositList.get(m);
					if (DateService.getDaysBetweenTwoDates(depositDate, interestDate) < 0) {
						n = m;
						break;
					}
					if (DateService.getDaysBetweenTwoDates(firstDateOfMonth, depositDate) < 0) {
						n = m;
						continue;
					}
					int dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth, depositDate);
					intAmt = (balAmount * modifiedInterestRate / 100) / 365 * dayDiff;
					balAmount = balAmount + modifiedDepositAmount;
					// balAmount = balAmount + intAmt;

					firstDateOfMonth = DateService.generateDaysDate(depositDate, 1);
					compoundInterest = compoundInterest + intAmt;
					n++;
				}
			}

			if (DateService.getDaysBetweenTwoDates(maturityDate, lastDateOfMonth) >= 0)
				lastDateOfMonth = maturityDate;

			int dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth, lastDateOfMonth) + 1;
			intAmt = (balAmount * modifiedInterestRate / 100) / 365 * dayDiff;

			compoundInterest = compoundInterest + intAmt;
			balAmount = compoundInterest + balAmount;

			Interest interest = new Interest();
			interest.setInterestRate((double) modifiedInterestRate);
			interest.setInterestAmount(round(compoundInterest, 2));
			interest.setInterestDate(interestDate);
			interest.setDepositId(depositId);
			interest.setFinancialYear(DateService.getFinancialYear(interestDate));
			//interest.setInterestSum(round(balAmount, 2));
			interestClassList.add(interest);

			intAmt = 0.0;
			compoundInterest = 0.0;

		}

		return interestClassList;
	}*/

	public Distribution deductAmount(Deposit deposit, Double amount, String reasonOfDeduction,
			Distribution lastDistribution, List<DepositHolder> depositHolderList) {

		// Deduct the amount in payment distribution
		// ------------------------------------------------------------------
		String action = reasonOfDeduction; // "Deduction from last Month/Qtr
											// TDS";
		Double amtDeduction = amount * -1;

		Distribution paymentDistribution = new Distribution();
		paymentDistribution.setDepositedAmt(FDService.round(amtDeduction, 2));

		paymentDistribution.setAction(action);
		paymentDistribution.setDistributionDate(DateService.getCurrentDate());
		paymentDistribution.setFixedAmt(0.0);
		paymentDistribution.setVariableAmt(FDService.round(amtDeduction, 2));
		paymentDistribution.setCompoundFixedAmt(FDService.round(lastDistribution.getCompoundFixedAmt(), 2));
		paymentDistribution
				.setCompoundVariableAmt(FDService.round(lastDistribution.getCompoundVariableAmt() + amtDeduction, 2));
		//
		Double totalbalance = FDService
				.round(paymentDistribution.getCompoundFixedAmt() + paymentDistribution.getCompoundVariableAmt(), 2);
		paymentDistribution.setTotalBalance(totalbalance);
		paymentDistribution.setDepositId(deposit.getId());
		paymentDistribution.setDepositHolderId(getPrimaryHolderId(depositHolderList));
		paymentDistribution.setActionId(null);
		paymentDistribution.setBalanceFixedInterest(lastDistribution.getBalanceFixedInterest());
		paymentDistribution.setBalanceVariableInterest(lastDistribution.getBalanceVariableInterest());

		lastDistribution = paymentDistributionDAO.insertPaymentDistribution(paymentDistribution);

		//COM--this.insertInDepositHolderWiseDistribution(depositHolderList, action, deposit.getId(), amtDeduction);

		// update the deposit table
		deposit.setCurrentBalance(totalbalance);
		fixedDepositDao.updateFD(deposit);
		// ----------------------------------------------------------------------

		return lastDistribution;

	}

	public TDS calculateTDS(Long customerId, String financialYear) {
		Double calculatedTDSAmount = 0.0;
		Boolean isEligibleForForm15GH = false;
		Boolean isForm15GSubmitted = false;
		Boolean isTDSDeductionRequired = false;
		
		// Get the customer category from customer details
		Customer customer = customerDAO.getById(customerId);
		
		// Get all open deposits(No sweep/auto deposit considered).
		List<Deposit> deposits = null;
		if(customer.getCitizen().equalsIgnoreCase("RI"))
			deposits = fixedDepositDao.getDepositsOfRIsForTDS(customerId);
		else
			deposits = fixedDepositDao.getDepositsOfNRIsForTDS(customerId);
		
		if(deposits == null)
			return null; 
		
		if(deposits.size()==0)
			return null;
		
		Distribution lastDistribution = null;


		
		// Get the Customer Actual Category
		String category = calculationService.geCustomerActualCategory(customer);
		Rates customerConfig = ratesDAO.getAllRatesByCustomerCategoryCitizenAndAccountType(category, customer.getCitizen(), customer.getNriAccountType());

		// Get the tds rate and the minimum InetestAmount For TDS deduction
		// upto the amount of minInetestAmountForTDS, TDS will not be deducted
		// If the amount exceeds, then TDS will be deducted from the customer
		Float tdsRate = customerConfig.getTds();
		if(customer.getCitizen().equalsIgnoreCase("NRI"))
		{
			tdsRate = dtaaCountryRateDAO.getDTAATaxRate(customer.getCountry());
			
		}
		Long minInterestAmountForTDS = customerConfig.getMinIntAmtForTDSDeduction();
		Double totalPaymentOfAllDeposit = 0d;
		Double totalInterestGivenToAllDeposits = 0d;
		HashMap<Long, Double> depositWiseTotalInterestGiven = new HashMap<>();

		ProductConfiguration productConfiguration = null;
		
		// Go through the all deposits of the customer
		for (int i = 0; i < deposits.size(); i++) {

			Deposit deposit = deposits.get(i);
			Long depositId = deposit.getId();

			// Get Product Configuration
			Long productConfigurationId = deposit.getProductConfigurationId();
					
			// Get Deposit Holder to find out the contribution%
			// in the deposit
			DepositHolder depositHolder = depositHolderDAO.getDepositHolder(depositId, customerId);
			Float contribution = depositHolder.getContribution();

		

			// Calculate the total interest given to customer
			// ------------------------------------------------
			// Get the last Interest compounded for the deposit
			// Interest lastInterest =
			// interestDAO.getLastInterestCompounded(depositId);
			Date fromDate = deposit.getCreatedDate();
			TDS lastTDS = tdsDAO.getLastTDSDeducted(customerId, financialYear);
			if (lastTDS != null) {
				fromDate = DateService.addDays(lastTDS.getTdsCalcDate(), 1);
			} else {
				String prevFinancialYear = DateService.getPreviousFinancialYear(financialYear);
				lastTDS = tdsDAO.getLastTDSDeducted(customerId, prevFinancialYear);
				if (lastTDS != null)
					fromDate = DateService.addDays(lastTDS.getTdsCalcDate(), 1);
			}

			// Get total Interest Given
			Double interestGivenToTheDeposit = interestDAO.getTotInterestGivenForPeriod(depositId, fromDate,
					DateService.getCurrentDate()); // interestDAO.getTotInterestGiven(depositId,
													// financialYear);
			Double contributedInterestGiven = interestGivenToTheDeposit * contribution / 100;
			depositWiseTotalInterestGiven.put(depositId, contributedInterestGiven);

			totalInterestGivenToAllDeposits = totalInterestGivenToAllDeposits + contributedInterestGiven;
			// --------------------------------------------------

		}

		// Check if the customer is eligible for Form 15G/H
		Period age = DateService.calculateAge(customer.getDateOfBirth());

		// For regular(<60) customer, 2.5 lac is the exemption limit
		// For Sr Citizen(>=60 to <80) 3 lac is the exemption limit
		// For Super Sr Citizen(>=80) 5 lac is the exemption limit
		
		// Get the exemption limit from the database
		List<TaxExemptionConfiguration> taxExemptionConfigurationList = taxExemptionConfigurationDAO.getTaxExemptionConfigurationList();	
		
		Integer limitForTax = 250000;
		
		if(taxExemptionConfigurationList==null)
		{
			if (age.getYears() >= 60 && age.getYears() < 80)
				limitForTax = 300000;
			else if (age.getYears() >= 80)
				limitForTax = 500000;
		}
		else
		{ 
			TaxExemptionConfiguration taxExemptionConfiguration = this.getActualTaxExemptionConfiguration(taxExemptionConfigurationList, age);
			limitForTax = taxExemptionConfiguration == null ? limitForTax : Integer.parseInt(taxExemptionConfiguration.getExemptionLimitAmount().toString());
		}


		//if (totalContributionOnProjectedInterestOfAllDeposit < limitForTax) {
		if (totalInterestGivenToAllDeposits < limitForTax) {
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
				if (totalInterestGivenToAllDeposits <= minInterestAmountForTDS) {
					isTDSDeductionRequired = false;
				} else {
					isTDSDeductionRequired = true;
				}
			}

		}

		// From here We have to Change
		// This TDS is for full full year
		if (isTDSDeductionRequired) {
			if (customer.getPan() != null && customer.getPan() != "") {
				if(customer.getPan().trim().equals(""))
				{
					// Get the Rate from Product Configuration
					tdsRate = productConfiguration.getTdsPercentForNoPAN(); // 20f;
				}
				else
				{
					// nothing to do
				}
			} else {
				// Get the Rate from Bank Configuration
				tdsRate = productConfiguration.getTdsPercentForNoPAN(); // 20f;
			}
			calculatedTDSAmount = round(totalInterestGivenToAllDeposits * tdsRate / 100, 2);
		}

		// // Now check how much TDS he/she has paid already
		// Double totalTDSDeducted = tdsDAO.getTDS(customerId, financialYear);
		// Double pendingTDS = calculatedTDSAmount - totalTDSDeducted;
		//
		// if(isTDSDeductionRequired && pendingTDS>0)
		// isTDSDeductionRequired = true;
		// else
		// isTDSDeductionRequired = false;

		// Now pending TDS should be divided no of occurrence of remaining TDS
		// if my interest calculation is monthly, total no of tds will be 12
		// like that...
		// if my interest calculation is quarterly, total no of tds will be 4
		// like that...

		// Find out the no of occurrence of pending TDS

		if (isTDSDeductionRequired && calculatedTDSAmount > 0) {

			// Insert in TDS table
			// Customer consolidated TDS
			// --------------------------------------------------------
			TDS tds = new TDS();

			tds.setTdsAmount(round(calculatedTDSAmount, 2));
			tds.setCalculatedTDSAmount(round(calculatedTDSAmount, 2));
			tds.setTdsCalcDate(DateService.getCurrentDate());
			tds.setCustomerId(customerId);
			tds.setFinancialYear(financialYear);
			tds.setPartlyCalculated(0);

			tds.setTdsDeducted(1);
			tds = tdsDAO.insert(tds);
			// ---------------------------------------------------------------

			// Insert in Deposit wise Customer TDS
			for (int i = 0; i < deposits.size(); i++) {

				Deposit deposit = deposits.get(i);
				Long depositId = deposit.getId();

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
			return tds;
		} else {
			// Insert in TDS table
			// Customer consolidated TDS
			// --------------------------------------------------------
			TDS tds = new TDS();

			tds.setTdsAmount(round(calculatedTDSAmount, 2));
			tds.setCalculatedTDSAmount(round(calculatedTDSAmount, 2));
			tds.setTdsCalcDate(DateService.getCurrentDate());
			tds.setCustomerId(customerId);
			tds.setFinancialYear(financialYear);
			tds.setPartlyCalculated(0);

			tds.setTdsDeducted(1);
			tds = tdsDAO.insert(tds);
			// ---------------------------------------------------------------

			return tds;

		}

	}

	private TaxExemptionConfiguration getActualTaxExemptionConfiguration(List<TaxExemptionConfiguration> taxExemptionConfigurationList, Period age)
	{
				
		TaxExemptionConfiguration taxExemptionConfiguration = null;
		for(int i=0; i<taxExemptionConfigurationList.size(); i++)
		{
			
			switch(taxExemptionConfigurationList.get(i).getExemptionComparisonSign()){    
			case "<":    
				if(age.getYears() < taxExemptionConfigurationList.get(i).getExemptionAge())
				{
					taxExemptionConfiguration = taxExemptionConfigurationList.get(i);
				}			 
				break; 
			case "<=":    
				if(age.getYears() <= taxExemptionConfigurationList.get(i).getExemptionAge())
				{
					taxExemptionConfiguration = taxExemptionConfigurationList.get(i);
				}	
			 break;  
			case ">":    
				if(age.getYears() > taxExemptionConfigurationList.get(i).getExemptionAge())
				{
					taxExemptionConfiguration = taxExemptionConfigurationList.get(i);
				}	
				 break;      
			case ">=":    
				if(age.getYears() >= taxExemptionConfigurationList.get(i).getExemptionAge())
				{
					taxExemptionConfiguration = taxExemptionConfigurationList.get(i);
				}	
				 break;   
			
			default:     			
				taxExemptionConfiguration = taxExemptionConfigurationList.get(i);
			}    

		}
		return taxExemptionConfiguration;
	}
	
	private Double getCustomerInterestProjection(Long customerId, String financialYear) {
		// Get all open/close/sweep deposits
		// TODO:
		List<Deposit> deposits = fixedDepositDao.getDeposits(customerId);
		Distribution lastDistribution = null;

		// Get the customer category from customer details
		Customer customer = customerDAO.getById(customerId);
		Rates customerConfig = ratesDAO.getRatesByCategory(customer.getCategory());

		Double totalContributionOnProjectedInterestOfAllDeposit = 0d;

		// Go through the all deposits of the customer
		for (int i = 0; i < deposits.size(); i++) {

			Deposit deposit = deposits.get(i);
			Long depositId = deposit.getId();

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
			totalContributionOnProjectedInterestOfAllDeposit = totalContributionOnProjectedInterestOfAllDeposit
					+ contributedProjectedInterestAmount;
			// --------------------------------------------------------------------
		}

		return totalContributionOnProjectedInterestOfAllDeposit;
	}

	public Double getTotalInterestEarnedFromAllDeposits(Long customerId, String financialYear) {
		// Get all open/close/sweep deposits
		// TODO:
		List<Deposit> deposits = fixedDepositDao.getDeposits(customerId);
		Double totalContributionInterestEarnedFromAllDeposits = 0d;

		// Go through the all deposits of the customer
		for (int i = 0; i < deposits.size(); i++) {

			Deposit deposit = deposits.get(i);
			Long depositId = deposit.getId();

			// Get Deposit Holder to find out the contribution%
			// in the deposit
			DepositHolder depositHolder = depositHolderDAO.getDepositHolder(depositId, customerId);
			Float contribution = depositHolder.getContribution();

			// Calculate the Total Interest / Projected Interest through out the
			// year for the customer
			// --------------------------------------------------------------------
			Interest lastInterest = interestDAO.getLastInterestCompounded(depositId);

			Date fromDate = (lastInterest == null) ? deposit.getCreatedDate()
					: DateService.addDays(lastInterest.getInterestDate(), 1);

			// Get total Interest Given
			Double interestGivenToTheDeposit = interestDAO.getTotInterestGivenForPeriod(depositId, fromDate,
					DateService.getCurrentDate()); // interestDAO.getTotInterestGiven(depositId,
													// financialYear);
			Double contributedInterestGiven = interestGivenToTheDeposit * contribution / 100;
			totalContributionInterestEarnedFromAllDeposits = totalContributionInterestEarnedFromAllDeposits
					+ contributedInterestGiven;
			// --------------------------------------------------------------------
		}

		return totalContributionInterestEarnedFromAllDeposits;
	}

	// -----------------------EMI Calculation---------------------------
	public Double calculateEMI(Date fromDate, Date maturityDate, Double depositAmount, Integer gestationPeriod,
			Float interestRate, String beneficiaryPayoffType, String interestCalculationBasis) {

		Date gestationEndDate = DateService.generateYearsDate(DateService.getCurrentDate(), gestationPeriod);
		List<Date> interestListTillGestation = this.getInterestDatesTillGestation(fromDate, gestationEndDate, interestCalculationBasis);

		// The formula for calculating compound interest is
		// A = P (1 + r/n) ^ nt
		// -----------------------------
		// P is the principal amount,
		// r is the rate of interest per annum,
		// n denotes the number of times in a year the interest gets compounded,
		// and
		// t denotes the number of years.

		Double principalAmount = depositAmount;

		principalAmount = this.getPrincipalAmountTillGestation(interestListTillGestation, principalAmount, interestRate,
				fromDate, null);

		// The formula of annuity payment is
		// P = r(PV)/(1-(1+r)^-n)
		// -------------------------------
		// PV is the present value, i.e. the Current Balance
		// r is the rate of interest per annum,
		// n denotes the number of times in a year the interest gets compounded,
		// and
		// t denotes the number of years.
		// -------------------------------------------------

		// Important: We are not getting the correct annuity emi amount with the
		// above formula
		// That is why we decided to divide the present value/present principal
		// amount by the no of installments
		// from gestation date to the maturity date
		Date startDate = DateService.addDays(gestationEndDate, 1);
		List<Date> payoffDates = this.getEMIPayoffDates(startDate, maturityDate, beneficiaryPayoffType);
		Double emiAmount = principalAmount / payoffDates.size();

		return this.round(emiAmount, 2);
	}

	public Double getPrincipalAmountTillGestation(List<Date> interestListTillGestation, Double principalAmount,
			Float interestRate, Date fromDate, Double uncalculatedInterestAmount) {

		for (int i = 0; i < interestListTillGestation.size(); i++) {

			Date interestDate = interestListTillGestation.get(i);
			Integer daysDiff = 0;

			// For Last Deposit skip below
			daysDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate) + 1;
			fromDate = DateService.addDays(interestDate, 1);

			Double interestAmount = (principalAmount * interestRate / 100) / 365 * daysDiff;

			if (uncalculatedInterestAmount == null)
				uncalculatedInterestAmount = 0d;
			if (i == 0)
				interestAmount = interestAmount + uncalculatedInterestAmount;
			// Double interestAmount = compoundPrincipalAmt
			// (Math.pow((1+((interestRate/100)/365)),(365
			// (dateDiff/365))))-compoundPrincipalAmt;
			principalAmount = principalAmount + interestAmount;
		}

		return this.round(principalAmount, 2);
	}

	public List<Date> getEMIPayoffDates(Date fromDate, Date maturityDate, String beneficiaryPayoffType) {

		List<Date> payoffDates = new ArrayList();

		while (true) {

			int month = DateService.getMonth(fromDate);
			if (beneficiaryPayoffType.equalsIgnoreCase(Constants.QUARTERLY)) {
				if ((month == 2 || month == 5 || month == 8 || month == 11)) {
					if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate),
							maturityDate) < 0) {

						// payoffDates.add(maturityDate);
						break;
					}
					payoffDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 3);
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			} else if (beneficiaryPayoffType.equalsIgnoreCase(Constants.MONTHLY)) {
				if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate), maturityDate) < 0) {

					// payoffDates.add(maturityDate);
					break;
				}

				payoffDates.add(DateService.getLastDateOfMonth(fromDate));
				fromDate = DateService.getFirstDateOfNextMonth(fromDate);

			} else if (beneficiaryPayoffType.equalsIgnoreCase(Constants.HALFYEARLY)) {
				if ((month == 2 || month == 8)) {
					// fromDate = DateService.getFirstDateOfNextMonth(fromDate);
					if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate),
							maturityDate) < 0) {

						// payoffDates.add(maturityDate);
						break;
					}
					payoffDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 6);
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			} else if (beneficiaryPayoffType.equalsIgnoreCase(Constants.ANNUALLY)) {
				if ((month == 2)) {
					// fromDate = DateService.getFirstDateOfNextMonth(fromDate);
					if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate),
							maturityDate) < 0) {

						// payoffDates.add(maturityDate);
						break;
					}
					payoffDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 12);
				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			}

		}
		return payoffDates;
	}

	public List<Date> getInterestDatesTillGestation(Date fromDate, Date gestationDate, String interestCalculationBasis) {

		List<Date> interestDates = new ArrayList();
		//BankConfiguration bankConfiguration = ratesDAO.findAll();

		while (true) {

			int month = DateService.getMonth(fromDate);
			if (interestCalculationBasis.equalsIgnoreCase(Constants.QUARTERLY)) {
				if ((month == 2 || month == 5 || month == 8 || month == 11)) {
					if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate),
							gestationDate) < 0) {
						break;
					}
					interestDates.add(DateService.getLastDateOfMonth(fromDate));
					fromDate = DateService.addMonths(fromDate, 3);
					// fromDate = DateService.getFirstDateOfNextMonth(fromDate);

				} else {
					fromDate = DateService.addMonths(fromDate, 1);
				}
			} else if (interestCalculationBasis.equalsIgnoreCase(Constants.MONTHLY)) {

				if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(fromDate), gestationDate) < 0) {
					break;
				}
				interestDates.add(DateService.getLastDateOfMonth(fromDate));
				fromDate = DateService.getFirstDateOfNextMonth(fromDate);

			}

		}
		return interestDates;
	}

	/*public List<Interest> getInterestListForEMIOnTopup(Deposit deposit, String customerCategory, Double topupAmount,
			Double uncalculatedInterestAmount, Double emiAmount, Distribution lastDistribution, Date gestationEndDate) {

		List<Date> interestListTillGestation = null;
		List<Date> interestDateList = null;
		Integer daysDiff = 0;

		Date currentDate = DateService.getCurrentDate();

		// ------------------------------
		Interest lastInterestBeforeTopup = interestDAO.getPreviousInterest(deposit.getId(), currentDate);

		Date startDate = deposit.getCreatedDate();
		if (lastInterestBeforeTopup != null)
			startDate = lastInterestBeforeTopup.getInterestDate();
		// --------------------------------

		Date payOffStartDate = gestationEndDate;
		if (DateService.getDaysBetweenTwoDates(gestationEndDate, currentDate) > 0)
			payOffStartDate = currentDate;
		List<Date> payoffDateList = this.getEMIPayoffDates(payOffStartDate, deposit.getNewMaturityDate(),
				deposit.getPayOffInterestType());

		Double compoundAmt = lastDistribution.getTotalBalance();

		// if topup is done before the gestation ends then
		// change in interest
		Date fromDate = currentDate;

		List<Interest> interestClassList = new ArrayList<>();
		int k = 0;
		Double totalInterestAmount = 0d;

		// This is the new principal amount in todays date
		// compoundAmt = compoundAmt + uncalculatedInterestAmount;

		if (DateService.getDaysBetweenTwoDates(gestationEndDate, currentDate) > 0)
			interestDateList = this.getInterestDatesForModification(currentDate, deposit.getNewMaturityDate());
		else
			interestDateList = this.getInterestDatesForModification(gestationEndDate, deposit.getNewMaturityDate());

		
		// If top up is done within GestationPeriod
		if (DateService.getDaysBetweenTwoDates(currentDate, gestationEndDate) > 0) {
			interestListTillGestation = this.getInterestDatesTillGestation(currentDate, gestationEndDate, Constants.MONTHLY);

			// Interest accumulation witin gestation period
			// No emi payout within this period
			// -----------------------------------------------
			for (int i = 0; i < interestListTillGestation.size(); i++) {

				Date interestDate = interestListTillGestation.get(i);

				// For Last Deposit skip below
				daysDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate) + 1;
				fromDate = DateService.addDays(interestDate, 1);

				Double interestAmount = (compoundAmt * deposit.getModifiedInterestRate() / 100) / 365 * daysDiff;
				// Double interestAmount = compoundPrincipalAmt
				// (Math.pow((1+((interestRate/100)/365)),(365
				// (dateDiff/365))))-compoundPrincipalAmt;

				// First time uncalculatedInterestAmount will be
				// added to interestAmount
				if (i == 0) {
					interestAmount = interestAmount + uncalculatedInterestAmount;
				}
				compoundAmt = compoundAmt + interestAmount;

				Interest interest = new Interest();
				interest.setInterestRate(Double.parseDouble(deposit.getModifiedInterestRate().toString()));
				interest.setInterestAmount(round(interestAmount, 2));
				interest.setInterestDate(interestDate);
				interest.setDepositId(deposit.getId());
				interest.setFinancialYear(DateService.getFinancialYear(interestDate));
				interestClassList.add(interest);

				fromDate = DateService.addDays(interestDate, 1);
				// lastInterestDate = interestDate;

			}
		} else {
			// After gestation topup is made
			totalInterestAmount = uncalculatedInterestAmount;
		}

		k = 0;
		for (int i = 0; i < payoffDateList.size(); i++) { // i=0,1,2

			Date payoffDate = payoffDateList.get(i); // 31 jan
			Date nextPayoffDate = (i < payoffDateList.size() - 1) ? payoffDateList.get(i + 1) : payoffDate;

			for (int j = k; j < interestDateList.size(); j++) {

				Date interestDate = interestDateList.get(j);

				if (DateService.getDaysBetweenTwoDates(payoffDate, interestDate) > 0
						&& DateService.getDaysBetweenTwoDates(nextPayoffDate, interestDate) >= 0) {
					daysDiff = DateService.getDaysBetweenTwoDates(fromDate, payoffDate) + 1;
					Double interestAmt = (compoundAmt * deposit.getModifiedInterestRate() / 100) / 365 * daysDiff;
					totalInterestAmount = totalInterestAmount + interestAmt;
					compoundAmt = compoundAmt - emiAmount;
					fromDate = DateService.addDays(payoffDate, 1);
					break;
				}

				if (DateService.getDaysBetweenTwoDates(interestDate, payoffDate) == 0) {
					daysDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate) + 1;

					Double interestAmt = (compoundAmt * deposit.getModifiedInterestRate() / 100) / 365 * daysDiff;
					totalInterestAmount = totalInterestAmount + interestAmt;

					// Add the interest amount
					compoundAmt = compoundAmt + totalInterestAmount;
					fromDate = DateService.addDays(interestDate, 1);

					Interest interest = new Interest();
					interest.setInterestRate(round((double) deposit.getModifiedInterestRate(), 2));
					interest.setInterestAmount(round(totalInterestAmount, 2));
					interest.setInterestDate(interestDate);
					interest.setDepositId(fixedDepositForm.getDepositId());
					interest.setFinancialYear(DateService.getFinancialYear(interestDate));
					interestClassList.add(interest);

					// Now deduct the annuity amount
					compoundAmt = compoundAmt - emiAmount;
					totalInterestAmount = 0d;
					k = k + 1;
					break;
				}

				if (DateService.getDaysBetweenTwoDates(interestDate, nextPayoffDate) > 0) {
					break;
				}
			}

		}

		//
		//
		//
		//
		//
		//
		//
		//
		//
		//
		//
		// //-----------------------------------------------------------
		// // Now EMI payout and interest calculation
		// k = 0;
		// if (interestDateList.size() > 0 && payoffDateList.size() > 0) {
		// if (DateService.getDaysBetweenTwoDates(interestDateList.get(0),
		// payoffDateList.get(0)) >= 0) {
		// for (int i = 0; i < interestDateList.size(); i++) { // i=0,1,2
		//
		// Date interestDate = interestDateList.get(i); // 31 jan
		// Date nextInterestDate = (i < interestDateList.size() - 1) ?
		// interestDateList.get(i + 1)
		// : interestDate;
		// daysDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate)
		// + 1; // from
		//
		// Double interesAmt = (compoundAmt * deposit.getModifiedInterestRate()
		// / 100) / 365 * daysDiff;
		//
		// // Adding Interest to the current Balance
		// compoundAmt = compoundAmt + interesAmt;
		// fromDate = DateService.addDays(interestDate, 1);
		//
		// Interest interest = new Interest();
		// interest.setInterestRate(round((double)deposit.getModifiedInterestRate(),
		// 2));
		// interest.setInterestAmount(round(interesAmt, 2));
		// interest.setInterestDate(interestDate);
		// interest.setDepositId(fixedDepositForm.getDepositId());
		// interest.setFinancialYear(DateService.getFinancialYear(interestDate));
		// interestClassList.add(interest);
		//
		// for (int j = k; j < payoffDateList.size(); j++) {
		//
		// Date payoffDate = payoffDateList.get(j);
		//
		// if (DateService.getDaysBetweenTwoDates(interestDate, payoffDate) >= 0
		// && DateService.getDaysBetweenTwoDates(payoffDate, nextInterestDate) >
		// 0) {
		// compoundAmt = compoundAmt - emiAmount;
		// k = k + 1;
		// } else if (i == (interestDateList.size() - 1) && j ==
		// (payoffDateList.size() - 1)) {
		// compoundAmt = compoundAmt - emiAmount;
		// k = k + 1;
		// } else {
		// break;
		// }
		// }
		//
		// }
		// } else {
		//
		//
		// k = 0;
		// Double totalInterestAmount = uncalculatedInterestAmount;
		// for (int i = 0; i < payoffDateList.size(); i++) { // i=0,1,2
		//
		// Date payoffDate = payoffDateList.get(i); // 31 jan
		// Date nextPayoffDate = (i < payoffDateList.size() - 1) ?
		// payoffDateList.get(i + 1) : payoffDate;
		//
		// for (int j = k; j < interestDateList.size(); j++) {
		//
		// Date interestDate = interestDateList.get(j);
		//
		// if (DateService.getDaysBetweenTwoDates(payoffDate, interestDate) > 0
		// && DateService.getDaysBetweenTwoDates(nextPayoffDate, interestDate)
		// >= 0) {
		// daysDiff = DateService.getDaysBetweenTwoDates(fromDate, payoffDate) +
		// 1;
		// Double interestAmt = (compoundAmt * deposit.getModifiedInterestRate()
		// / 100) / 365 * daysDiff;
		// totalInterestAmount = totalInterestAmount + interestAmt;
		// compoundAmt = compoundAmt - emiAmount;
		// fromDate = DateService.addDays(payoffDate, 1);
		// break;
		// }
		//
		// if (DateService.getDaysBetweenTwoDates(interestDate, payoffDate) ==
		// 0) {
		// daysDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate)
		// + 1;
		//
		// Double interestAmt = (compoundAmt * deposit.getModifiedInterestRate()
		// / 100) / 365 * daysDiff;
		// totalInterestAmount = totalInterestAmount + interestAmt;
		//
		// // Add the interest amount
		// compoundAmt = compoundAmt + totalInterestAmount;
		// fromDate = DateService.addDays(interestDate, 1);
		//
		// Interest interest = new Interest();
		// interest.setInterestRate(round((double)
		// deposit.getModifiedInterestRate(), 2));
		// interest.setInterestAmount(round(totalInterestAmount, 2));
		// interest.setInterestDate(interestDate);
		// interest.setDepositId(fixedDepositForm.getDepositId());
		// interest.setFinancialYear(DateService.getFinancialYear(interestDate));
		// interestClassList.add(interest);
		//
		// // Now deduct the annuity amount
		// compoundAmt = compoundAmt - emiAmount;
		// totalInterestAmount = 0d;
		// k = k + 1;
		// break;
		// }
		//
		// if (DateService.getDaysBetweenTwoDates(interestDate, nextPayoffDate)
		// > 0) {
		// break;
		// }
		// }
		//
		// }
		// }
		// }
		// // If customers tops up after gestation period then
		// else {
		// Float interestRate = deposit.getModifiedInterestRate();
		//
		// interestDateList = this.getInterestDatesForModification(currentDate,
		// deposit.getNewMaturityDate());
		//
		// k = 0;
		// if (interestDateList.size() > 0 && payoffDateList.size() > 0) {
		// if (DateService.getDaysBetweenTwoDates(interestDateList.get(0),
		// payoffDateList.get(0)) >= 0) {
		// for (int i = 0; i < interestDateList.size(); i++) { // i=0,1,2
		//
		// Date interestDate = interestDateList.get(i); // 31 jan
		// Date nextInterestDate = (i < interestDateList.size() - 1) ?
		// interestDateList.get(i + 1)
		// : interestDate;
		// daysDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate)
		// + 1; // from
		//
		// Double interesAmt = (compoundAmt * interestRate / 100) / 365 *
		// daysDiff;
		//
		// // Adding Interest to the current Balance
		// compoundAmt = compoundAmt + interesAmt;
		// fromDate = DateService.addDays(interestDate, 1);
		//
		// Interest interest = new Interest();
		// interest.setInterestRate(round((double)interestRate, 2));
		// interest.setInterestAmount(round(interesAmt, 2));
		// interest.setInterestDate(interestDate);
		// interest.setDepositId(fixedDepositForm.getDepositId());
		// interest.setFinancialYear(DateService.getFinancialYear(interestDate));
		// interestClassList.add(interest);
		//
		// for (int j = k; j < payoffDateList.size(); j++) {
		//
		// Date payoffDate = payoffDateList.get(j);
		//
		// if (DateService.getDaysBetweenTwoDates(interestDate, payoffDate) >= 0
		// && DateService.getDaysBetweenTwoDates(payoffDate, nextInterestDate) >
		// 0) {
		// compoundAmt = compoundAmt - emiAmount;
		// k = k + 1;
		// } else if (i == (interestDateList.size() - 1) && j ==
		// (payoffDateList.size() - 1)) {
		// compoundAmt = compoundAmt - emiAmount;
		// k = k + 1;
		// } else {
		// break;
		// }
		// }
		//
		// }
		// } else {
		//
		//
		// k = 0;
		// Double totalInterestAmount = uncalculatedInterestAmount;
		// for (int i = 0; i < payoffDateList.size(); i++) { // i=0,1,2
		//
		// Date payoffDate = payoffDateList.get(i); // 31 jan
		// Date nextPayoffDate = (i < payoffDateList.size() - 1) ?
		// payoffDateList.get(i + 1) : payoffDate;
		//
		// for (int j = k; j < interestDateList.size(); j++) {
		//
		// Date interestDate = interestDateList.get(j);
		//
		// if (DateService.getDaysBetweenTwoDates(payoffDate, interestDate) > 0
		// && DateService.getDaysBetweenTwoDates(nextPayoffDate, interestDate)
		// >= 0) {
		// daysDiff = DateService.getDaysBetweenTwoDates(fromDate, payoffDate) +
		// 1;
		// Double interestAmt = (compoundAmt * interestRate / 100) / 365 *
		// daysDiff;
		// totalInterestAmount = totalInterestAmount + interestAmt;
		// compoundAmt = compoundAmt - emiAmount;
		// fromDate = DateService.addDays(payoffDate, 1);
		// break;
		// }
		//
		// if (DateService.getDaysBetweenTwoDates(interestDate, payoffDate) ==
		// 0) {
		// daysDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate)
		// + 1;
		//
		// Double interestAmt = (compoundAmt * interestRate / 100) / 365 *
		// daysDiff;
		// totalInterestAmount = totalInterestAmount + interestAmt;
		//
		// // Add the interest amount
		// compoundAmt = compoundAmt + totalInterestAmount;
		// fromDate = DateService.addDays(interestDate, 1);
		//
		// Interest interest = new Interest();
		// interest.setInterestRate(round((double) interestRate, 2));
		// interest.setInterestAmount(round(totalInterestAmount, 2));
		// interest.setInterestDate(interestDate);
		// interest.setDepositId(fixedDepositForm.getDepositId());
		// interest.setFinancialYear(DateService.getFinancialYear(interestDate));
		// interestClassList.add(interest);
		//
		// // Now deduct the annuity amount
		// compoundAmt = compoundAmt - emiAmount;
		// totalInterestAmount = 0d;
		// k = k + 1;
		// break;
		// }
		//
		// if (DateService.getDaysBetweenTwoDates(interestDate, nextPayoffDate)
		// > 0) {
		// break;
		// }
		// }
		//
		// }
		//
		// }
		// }
		// }

		return interestClassList;
	}*/

	public Double calculateEMIOnTopUp(Double principalAmount, Date gestationEndDate, Float interestRate, Date fromDate,
			Date maturityDate, String beneficiaryPayoffType, Double uncalculatedInterestAmount, String interestCalculationBasis) {
		List<Date> interestListTillGestation = null;
		Date startDate = DateService.addDays(gestationEndDate, 1);
		// principalAmount = principalAmount + uncalculatedInterestAmount;
		if (DateService.getDaysBetweenTwoDates(fromDate, gestationEndDate) > 0) {
			interestListTillGestation = this.getInterestDatesTillGestation(fromDate, gestationEndDate, interestCalculationBasis);
			principalAmount = this.getPrincipalAmountTillGestation(interestListTillGestation, principalAmount,
					interestRate, fromDate, uncalculatedInterestAmount);
		} else {
			startDate = fromDate;
		}

		// The formula of annuity payment is
		// P = r(PV)/(1-(1+r)^-n)
		// -------------------------------
		// PV is the present value, i.e. the Current Balance
		// r is the rate of interest per annum,
		// n denotes the number of times in a year the interest gets compounded,
		// and
		// t denotes the number of years.
		// -------------------------------------------------

		// Important: We are not getting the correct annuity emi amount with the
		// above formula
		// That is why we decided to divide the present value/present principal
		// amount by the no of installments
		// from gestation date to the maturity date

		List<Date> payoffDates = this.getEMIPayoffDates(startDate, maturityDate, beneficiaryPayoffType);
		// List<Date> interestListTillMaturityFromGestation =
		// this.getInterestDatesForModification(startDate, maturityDate);
		// Double emiAmount = (interestRate/100 *
		// principalAmount)/(1-(Math.pow((1+interestRate/100),-interestListTillMaturityFromGestation.size())));
		Double emiAmount = principalAmount / payoffDates.size();

		return this.round(emiAmount, 2);

	}

	public Double getUncalculatedInterestAmount(Deposit deposit) {
		Long depositId = deposit.getId();
		Date prevDistributionDate = null;
		Long lastInterestDistributionId = 0l;
		Double prevCompoundVariableAmt = 0d;
		Double variableIntAmt = 0d;
		Double compoundVariableAmt = 0d;
		Double totalVariableInterest = 0d;
		int totalDaysDiff = 0;

		Float modifiedInterestRate = (deposit.getModifiedInterestRate() == null) ? deposit.getInterestRate()
				: deposit.getModifiedInterestRate();

		// Get last interest calculated/adjusted for the period from
		// Distribution table.
		// It is required to calculated the interest till today for which
		// interest is not yet calculated
		Distribution lastInterestCalculated = paymentDistributionDAO.getLastInterestCalculatedOrAdjusted(depositId,
				DateService.getCurrentDate());

		Distribution currentDistribution = null;

		// Interest already calculated, not for first time
		if (lastInterestCalculated != null) // Means interest has been
											// calculated previously
		{
			lastInterestDistributionId = lastInterestCalculated.getId();
			prevDistributionDate = DateService.addDays(lastInterestCalculated.getDistributionDate(), 1);

			prevCompoundVariableAmt = lastInterestCalculated.getCompoundVariableAmt();
			compoundVariableAmt = prevCompoundVariableAmt;
			variableIntAmt = 0d;

			compoundVariableAmt = 0d;
			totalDaysDiff = 0;

			// Fetch all the records starting from the last interest calculated
			List<Distribution> lastPaymentDistForDepWithdraw = paymentDistributionDAO
					.getAllDepWithdrawFromLastInterestCalc(depositId, lastInterestDistributionId);

			// After interest calculation Payment or Withdraw has been made
			if (lastPaymentDistForDepWithdraw != null && lastPaymentDistForDepWithdraw.size() > 0) {
				for (int i = 0; i < lastPaymentDistForDepWithdraw.size(); i++) {
					Distribution paymentDist = lastPaymentDistForDepWithdraw.get(i);

					Integer daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
							paymentDist.getDistributionDate()) + 1;

					if (daysDifference == 0 && (paymentDist.getAction().equalsIgnoreCase("TDS")
							|| paymentDist.getAction().equalsIgnoreCase("PAYOFF"))) {

						prevDistributionDate = DateService.addDays(paymentDist.getDistributionDate(), 1);
						prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();

						compoundVariableAmt = prevCompoundVariableAmt;

						continue;
					}

					// Deducting one day is required. Otherwise from last month
					// interest to
					// next Payment/Withdraw system will calculate one extra day
					// if
					// (DateService.getMonth(paymentDist.getDistributionDate())
					// - DateService.getMonth(prevDistributionDate) != 0)
					// daysDifference = daysDifference - 1;
					if (paymentDist.getAction().equalsIgnoreCase("Payment")
							|| paymentDist.getAction().equalsIgnoreCase("Withdraw"))
						daysDifference = daysDifference - 1;

					if (daysDifference > 0) {
						totalDaysDiff = totalDaysDiff + daysDifference;
						variableIntAmt = (prevCompoundVariableAmt * modifiedInterestRate / 100) / 365 * daysDifference;
						totalVariableInterest = totalVariableInterest + variableIntAmt;
					}

					prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();
					prevDistributionDate = DateService.addDays(paymentDist.getDistributionDate(), 1);
					compoundVariableAmt = prevCompoundVariableAmt;
				}
			}

		} else {
			// First time interest is going to be calculated
			Date currentDate = DateService.getCurrentDate();
			compoundVariableAmt = 0d;

			// first time interest is going to calculate
			// consider multiple deposit can be performed by these days
			// Fetch all the records starting from the last interest calculated
			List<Distribution> lastPaymentDistForDepWithdraw = paymentDistributionDAO
					.getAllDepWithdrawFromLastInterestCalc(depositId, 0L);

			// Payment or Withdraw has been made
			if (lastPaymentDistForDepWithdraw != null && lastPaymentDistForDepWithdraw.size() > 0) {

				prevCompoundVariableAmt = 0d;
				Integer daysDifference = 0;
				variableIntAmt = 0d;
				totalDaysDiff = 0;

				for (int i = 0; i < lastPaymentDistForDepWithdraw.size(); i++) {
					Distribution paymentDist = lastPaymentDistForDepWithdraw.get(i);

					if (i == 0) {
						prevDistributionDate = DateService.addDays(paymentDist.getDistributionDate(), 1);
						prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();

						compoundVariableAmt = prevCompoundVariableAmt;
						continue;
					}

					daysDifference = DateService.getDaysBetweenTwoDates(prevDistributionDate,
							paymentDist.getDistributionDate()) + 1;

					if (paymentDist.getAction().equalsIgnoreCase("Payment")
							|| paymentDist.getAction().equalsIgnoreCase("Withdraw"))
						daysDifference = daysDifference - 1;

					variableIntAmt = (prevCompoundVariableAmt * modifiedInterestRate / 100) / 365 * daysDifference;
					totalVariableInterest = totalVariableInterest + variableIntAmt;

					prevCompoundVariableAmt = paymentDist.getCompoundVariableAmt();
					prevDistributionDate = paymentDist.getDistributionDate();
					totalDaysDiff = totalDaysDiff + daysDifference;

				}

			}

		}
		return totalVariableInterest;

	}

	/*public Double getInterestForSweepDeposit(Double depositAmount, Deposit deposit) {
		Date fromDate = DateService.getCurrentDate();
		// Date maturityDate =
		// this.getMaturityDate(fixedDepositForm.getFdTenureType(),
		// fixedDepositForm.get //Date fromDate = DateService.getCurrentDate();
		Date maturityDate = deposit.getNewMaturityDate() == null ? deposit.getMaturityDate()
				: deposit.getNewMaturityDate();
		List<Date> interestList = this.getInterestDatesForModification(fromDate, maturityDate);
		// List<Date> depositList =
		// this.getDepositDatesForModification(fixedDepositForm, maturityDate);
		Long depositId = deposit.getId();// ixedDepositForm.getDepositId();
		Date lastInterestDate = null;
		Float modifiedInterestRate = deposit.getModifiedInterestRate() == null ? deposit.getInterestRate()
				: deposit.getModifiedInterestRate();

		// Get last interest calculated/adjusted for the period from
		// Distribution table.
		// It is required to calculated the interest till today for which
		// interest is not yet calculated
		Distribution lastInterestCalculated = paymentDistributionDAO.getLastInterestCalculatedOrAdjusted(depositId,
				DateService.getCurrentDate());

		Date firstDateOfMonth = null;
		if (lastInterestCalculated != null)
			firstDateOfMonth = DateService.addDays(lastInterestCalculated.getDistributionDate(), 1);
		else
			firstDateOfMonth = deposit.getCreatedDate();

		Date lastDateOfMonth = DateService.getLastDateOfCurrentMonth();

		// Last modification
		Double modifiedDepositAmount = deposit.getDepositAmount();
		if (depositAmount != null && depositAmount > 0)
			modifiedDepositAmount = round((double) depositAmount, 2);

		Float preiviousInterestRate = depositModificationDAO.getPreviousDepositRate(depositId, fromDate);
		// Double

		// Distributions in modification month
		List<Distribution> distributionList = paymentDistributionDAO.getDistributionListFrom(depositId,
				lastInterestCalculated != null ? lastInterestCalculated.getId() : 0);

		// Last Distribution before firstDateOfMonth
		// Distribution lastDistributionOfPreviousMonth = paymentDistributionDAO
		// .getLastDistributionOfPreviousMonth(depositId, firstDateOfMonth);
		Date lastDistributionDate = lastInterestCalculated == null ? null
				: lastInterestCalculated.getDistributionDate();
		Double balAmount = lastInterestCalculated == null ? 0.0 : lastInterestCalculated.getTotalBalance();

		List<Interest> interestClassList = new ArrayList<>();
		Boolean isLastTransactionInterest = false;
		// Double compoundAmt = 0.0;
		int dateDiff = 0;
		Double intAmt = 0.0;

		int n = 0;
		int j = 0;
		Double compoundInterest = 0.0;
		for (int i = 0; i < interestList.size(); i++) {
			Date interestDate = interestList.get(i);

			// if (i > 0)
			// firstDateOfMonth = DateService.getFirstDateOfMonth(interestDate);
			lastDateOfMonth = DateService.getLastDateOfMonth(interestDate);

			// Payment/withdraw etc is done
			if (distributionList != null) {
				for (int k = j; k < distributionList.size(); k++) {
					Distribution distribution = distributionList.get(k);
					if (lastDistributionDate == null) {
						firstDateOfMonth = distribution.getDistributionDate();
					} else {

						int dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth,
								distribution.getDistributionDate());

						//
						// if(DateService.getMonth(firstDateOfMonth)!=
						// DateService.getMonth(DateService.getCurrentDate()))
						// dayDiff = dayDiff - 1;

						if (isLastTransactionInterest)
							dayDiff = dayDiff - 1;

						if ((distribution.getAction().equalsIgnoreCase("TDS")
								|| distribution.getAction().equalsIgnoreCase("PAYOFF"))) {
							balAmount = distribution.getTotalBalance();
							isLastTransactionInterest = true;

						}
						if (dayDiff > 0) {
							intAmt = (balAmount * preiviousInterestRate / 100) / 365 * dayDiff;
							compoundInterest = compoundInterest + intAmt;
							// totalInterestAmt = totalInterestAmt + intAmt;
							isLastTransactionInterest = false;
						}

					}

					System.out.println("Rate: " + preiviousInterestRate + " ---- " + "Distribution Date: "
							+ distribution.getDistributionDate());
					balAmount = distribution.getTotalBalance();
					firstDateOfMonth = distribution.getDistributionDate();
					lastDistributionDate = distribution.getDistributionDate();

					if (DateService.getDaysBetweenTwoDates(distribution.getDistributionDate(), interestDate) <= 0) {
						j = k;
						break;
					}

					j++;
				}
			}

			if (DateService.getDaysBetweenTwoDates(maturityDate, lastDateOfMonth) >= 0)
				lastDateOfMonth = maturityDate;

			// int dayDiff =
			// DateService.getDaysBetweenTwoDates(firstDateOfMonth,
			// lastDateOfMonth) + 1;
			int dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth, interestDate) + 1;

			if (lastInterestDate != null && isLastTransactionInterest) {
				// if (DateService.getMonth(lastInterestDate) -
				// DateService.getMonth(nextDepositDate) != 0)
				dayDiff = dayDiff - 1;
			}

			intAmt = (balAmount * modifiedInterestRate / 100) / 365 * dayDiff;

			compoundInterest = compoundInterest + intAmt;
			balAmount = compoundInterest + balAmount;

			// Interest interest = new Interest();
			// interest.setInterestRate((double) modifiedInterestRate);
			// interest.setInterestAmount(round(compoundInterest, 2));
			// interest.setInterestDate(interestDate);
			// interest.setDepositId(depositId);
			// interest.setFinancialYear(DateService.getFinancialYear(interestDate));
			// interest.setInterestSum(round(balAmount, 2));
			// interestClassList.add(interest);

			lastInterestDate = interestDate;
			firstDateOfMonth = DateService.generateDaysDate(interestDate, 1);
			isLastTransactionInterest = true;
			intAmt = 0.0;
			compoundInterest = 0.0;

		}

		return balAmount;
	}*/

	@Transactional(rollbackFor = Exception.class)
	public void withdrawFromSaving(WithdrawForm withdrawForm, Customer customer) throws Exception {

		AccountDetails savingAcc = accountDetailsDAO.findSavingByCustId(withdrawForm.getCustomerId());
		Double amt = savingAcc.getAccountBalance() - withdrawForm.getWithdrawAmount();

		if (amt >= 0) {

			savingAcc.setAccountBalance(Double.valueOf(amt.toString()));
			accountDetailsDAO.updateUserAccountDetails(savingAcc);

		} else {

			amt = -amt;

			Double amountToAdjust = amt;
			savingAcc.setAccountBalance(0d);
			accountDetailsDAO.updateUserAccountDetails(savingAcc);

			// -------------------------------------------
			List<Deposit> autoDepositList = fixedDepositDao.getAutoDepositList(withdrawForm.getCustomerId());

			DepositHolder depositHolder = null;
			List<DepositHolder> depositHolderList = new ArrayList();

			if (autoDepositList != null) {
				for (int i = 0; i < autoDepositList.size(); i++) {
					Deposit deposit = autoDepositList.get(i);

					if (depositHolder == null)
						depositHolder = depositHolderDAO.getAutoDepositHolder(deposit.getId());

					depositHolderList.add(depositHolder);

					Distribution distribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());
					Double amtAdjusted = 0d;
					if (distribution != null) {
						if (distribution.getTotalBalance() >= amountToAdjust) {
							amtAdjusted = amountToAdjust;
							amountToAdjust = 0d;
						} else {
							amtAdjusted = distribution.getTotalBalance();
							amountToAdjust = amountToAdjust - amtAdjusted;
						}

						Customer customerDetails = customer;

						// withdraw from auto deposit
						withdrawForm.setDepositId(deposit.getId());
						withdrawForm.setDepositHolderId(depositHolder.getId());
						withdrawForm.setWithdrawAmount(amtAdjusted);
						withdrawForm.setModeOfPayment(Constants.WITHDRAWFROMSAVING);

						this.withdrawOperationForSweep(withdrawForm, customerDetails.getUserName());

						depositHolderList.clear();

						if (amountToAdjust == 0)
							break;
						else {
							if (i == autoDepositList.size() - 1) {
								throw new Exception("Insufficient Balance in Sweep Deposit");
							}
						}
					}
				}
			}

		}

	}

	

	public Deposit getMaturityAndTenureInformation(Deposit deposit, FixedDepositForm fixedDepositForm) {
		int days = fixedDepositForm.getDaysValue() != null ? fixedDepositForm.getDaysValue() : 0;
/*
		if (fixedDepositForm.getFdTenureType().equals("Year")) {
			int tenureinDays = 0;
			int fdYear = fixedDepositForm.getFdTenure();

			Date dt = DateService.addYear(DateService.getCurrentDateTime(), fdYear);
			dt = DateService.addDays(dt, days);
			tenureinDays = DateService.getDaysBetweenTwoDates(DateService.loginDate, dt);
			deposit.setTenure(tenureinDays);
			deposit.setTenureType("Days");
			Date maturitydate = DateService.addDays(fixedDepositForm.getMaturityDate(), days);
			deposit.setMaturityDate(maturitydate);
			deposit.setNewMaturityDate(maturitydate);

		}
		if (fixedDepositForm.getFdTenureType().equals("Month") || fixedDepositForm.getFdTenureType().equals("Day")) {*/
			deposit.setTotalTenureInDays(fixedDepositForm.getTotalTenureInDays());
			deposit.setTenureInDays(fixedDepositForm.getTenureInDays());
			deposit.setTenureInMonths(fixedDepositForm.getTenureInMonths());
			deposit.setTenureInYears(fixedDepositForm.getTenureInYears());
			deposit.setTenureType("");
			deposit.setMaturityDate(fixedDepositForm.getMaturityDate());
			deposit.setNewMaturityDate(fixedDepositForm.getMaturityDate());

		/*}*/
		return deposit;
	}

	public static double roundToHalf(double number) {
		double diff = number - (int) number;
		if (diff < 0.03)
			return (int) number;

		return round(number, 2); // Math.round(number * 2) / 2.0;
	}

	/*public List<Interest> getInterestListForFixedAmountEmi(Date fromDate, Date maturityDate, Float interestRate,
			Double emiAmount, Double depositAmount, Date gestationEndDate, Long depositId, String payOffInterestType,
			Integer emiTimes, List<Date> payoffDateList) {

		Double emiFixedAmount = round((double) emiAmount, 2);

		// Find when Last Interest Calculated
		Interest lastIntrestCalculated = interestDAO.getLastInterestCompounded(depositId);

		Distribution lastDistribution = paymentDistributionDAO.getLastPaymentDistribution(depositId);

		List<Interest> interestClassList = new ArrayList<>();

		Double compoundAmt = (double) depositAmount;
		if (lastIntrestCalculated != null)
			compoundAmt = lastDistribution.getTotalBalance();

		int dateDiff = 0;

		Date startDate = DateService.getCurrentDate();
		if (lastIntrestCalculated != null
				&& DateService.getDaysBetweenTwoDates(startDate, lastIntrestCalculated.getInterestDate()) == 0)
			startDate = DateService.addDays(lastIntrestCalculated.getInterestDate(), 1);
		List<Date> interestListTillGestation = this.getInterestDatesTillGestation(startDate, gestationEndDate, Constants.MONTHLY);

		List<Date> interestDateList = null;
		if (DateService.getDaysBetweenTwoDates(startDate, gestationEndDate) >= 0)
			interestDateList = this.getInterestDatesForModification(gestationEndDate, maturityDate);
		else
			interestDateList = this.getInterestDatesForModification(startDate, maturityDate);

		Date lastInterestDate = null;

		if (DateService.getDaysBetweenTwoDates(fromDate, startDate) >= 0)
			fromDate = startDate;

		for (int i = 0; i < interestListTillGestation.size(); i++) {

			Date interestDate = interestListTillGestation.get(i);
			Integer daysDiff = 0;

			// For Last Deposit skip below
			daysDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate) + 1;
			fromDate = DateService.addDays(interestDate, 1);

			Double interestAmount = (compoundAmt * interestRate / 100) / 365 * daysDiff;
			// Double interestAmount = compoundPrincipalAmt
			// (Math.pow((1+((interestRate/100)/365)),(365
			// (dateDiff/365))))-compoundPrincipalAmt;
			compoundAmt = compoundAmt + interestAmount;

			Interest interest = new Interest();
			interest.setInterestRate(round((double) interestRate, 2));
			interest.setInterestAmount(round(interestAmount, 2));
			interest.setInterestDate(interestDate);
			interest.setDepositId(fixedDepositForm.getDepositId());
			interest.setFinancialYear(DateService.getFinancialYear(interestDate));
			interestClassList.add(interest);

			fromDate = DateService.addDays(interestDate, 1);
			lastInterestDate = interestDate;

		}

		Integer k = 0;
		if (interestDateList.size() > 0 && payoffDateList.size() > 0) {
			if (DateService.getDaysBetweenTwoDates(interestDateList.get(0), payoffDateList.get(0)) >= 0) {
				for (int i = 0; i < interestDateList.size(); i++) { // i=0,1,2

					Date interestDate = interestDateList.get(i); // 31 jan
					Date nextInterestDate = (i < interestDateList.size() - 1) ? interestDateList.get(i + 1)
							: interestDate;
					dateDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate) + 1; // from

					Double interesAmt = (compoundAmt * interestRate / 100) / 365 * dateDiff;

					// Adding Interest to the current Balance
					compoundAmt = compoundAmt + interesAmt;
					fromDate = DateService.addDays(interestDate, 1);

					Interest interest = new Interest();
					interest.setInterestRate(round((double) interestRate, 2));
					interest.setInterestAmount(round(interesAmt, 2));
					interest.setInterestDate(interestDate);
					interest.setDepositId(fixedDepositForm.getDepositId());
					interest.setFinancialYear(DateService.getFinancialYear(interestDate));
					interestClassList.add(interest);

					for (int j = k; j < payoffDateList.size(); j++) {

						Date payoffDate = payoffDateList.get(j);

						if (DateService.getDaysBetweenTwoDates(interestDate, payoffDate) >= 0
								&& DateService.getDaysBetweenTwoDates(payoffDate, nextInterestDate) > 0) {
							compoundAmt = compoundAmt - emiAmount;
							k = k + 1;
						} else if (i == (interestDateList.size() - 1) && j == (payoffDateList.size() - 1)) {
							compoundAmt = compoundAmt - emiAmount;
							k = k + 1;
						} else {
							break;
						}
					}

				}
			} else {
				k = 0;
				Double totalInterestAmount = 0d;
				for (int i = 0; i < payoffDateList.size(); i++) { // i=0,1,2

					Date payoffDate = payoffDateList.get(i); // 31 jan
					Date nextPayoffDate = (i < payoffDateList.size() - 1) ? payoffDateList.get(i + 1) : payoffDate;

					for (int j = k; j < interestDateList.size(); j++) {

						Date interestDate = interestDateList.get(j);

						if (DateService.getDaysBetweenTwoDates(payoffDate, interestDate) > 0
								&& DateService.getDaysBetweenTwoDates(nextPayoffDate, interestDate) >= 0) {
							dateDiff = DateService.getDaysBetweenTwoDates(fromDate, payoffDate) + 1;
							Double interestAmt = (compoundAmt * interestRate / 100) / 365 * dateDiff;
							totalInterestAmount = totalInterestAmount + interestAmt;
							compoundAmt = compoundAmt - emiAmount;
							fromDate = DateService.addDays(payoffDate, 1);
							break;
						}

						if (DateService.getDaysBetweenTwoDates(interestDate, payoffDate) == 0) {
							dateDiff = DateService.getDaysBetweenTwoDates(fromDate, interestDate) + 1;

							Double interestAmt = (compoundAmt * interestRate / 100) / 365 * dateDiff;
							totalInterestAmount = totalInterestAmount + interestAmt;

							// Add the interest amount
							compoundAmt = compoundAmt + totalInterestAmount;
							fromDate = DateService.addDays(interestDate, 1);

							Interest interest = new Interest();
							interest.setInterestRate(round((double) interestRate, 2));
							interest.setInterestAmount(round(totalInterestAmount, 2));
							interest.setInterestDate(interestDate);
							interest.setDepositId(fixedDepositForm.getDepositId());
							interest.setFinancialYear(DateService.getFinancialYear(interestDate));
							interestClassList.add(interest);

							// Now deduct the annuity amount
							compoundAmt = compoundAmt - emiAmount;
							totalInterestAmount = 0d;
							k = k + 1;
							break;
						}

						if (DateService.getDaysBetweenTwoDates(interestDate, nextPayoffDate) > 0) {
							break;
						}
					}

				}

			}
		}

		return interestClassList;

	}*/

	

	public List<Interest> getInterestBreakupForModificationAfterAdjustment(Date fromDate, Date maturityDate,
			Deposit deposit, List<Date> interestList, List<Date> depositList) {

		Long depositId = fixedDepositForm.getDepositId();
		Date lastInterestDate = null;

		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
		// Get last interest calculated/adjusted for the period from
		// Distribution table.
		// It is required records to calculated the interest till today for
		// which
		// interest is not yet calculated or interest calculated but adjusted
		// for withdraw
		Distribution lastInterestCalculated = paymentDistributionDAO.getLastInterestCalculatedOrAdjusted(depositId,
				DateService.getCurrentDate());

		Date firstDateOfMonth = null;
		if (lastInterestCalculated != null)
			firstDateOfMonth = DateService.addDays(lastInterestCalculated.getDistributionDate(), 1);
		else
			firstDateOfMonth = deposit.getCreatedDate();

		Date lastDateOfMonth = DateService.getLastDateOfCurrentMonth();

		// Last modification
		Double modifiedDepositAmount = deposit.getDepositAmount();
		if (fixedDepositForm.getFdAmount() != null && fixedDepositForm.getFdAmount() > 0)
			modifiedDepositAmount = round((double) fixedDepositForm.getFdAmount(), 2);

		Double modifiedInterestRate = (double) (deposit.getModifiedInterestRate() == null ? deposit.getInterestRate()
				: deposit.getModifiedInterestRate());
		if (fixedDepositForm.getFdInterest() > 0)
			modifiedInterestRate = round((double) fixedDepositForm.getFdInterest(), 2);

		Float preiviousInterestRate = depositModificationDAO.getPreviousDepositRate(depositId, fromDate);

		// get first adjustment record of InterestAdjustment from last interest
		// calculation
		Distribution lastAdjustment = paymentDistributionDAO.getFirstAdjustmentAfterLastInterest(depositId);

		// Get last top up
		Distribution lastTopupBeforeAdjustment = paymentDistributionDAO.getLastTopup(deposit.getId(),
				lastAdjustment != null ? lastAdjustment.getId() : null);

		// Distributions in modification month
		List<Distribution> distributionList = paymentDistributionDAO.getDistributionListFrom(depositId,
				lastInterestCalculated != null ? lastInterestCalculated.getId() : 0);

		// Last Distribution before firstDateOfMonth
		// Distribution lastDistributionOfPreviousMonth = paymentDistributionDAO
		// .getLastDistributionOfPreviousMonth(depositId, firstDateOfMonth);
		Date lastDistributionDate = lastInterestCalculated == null ? null
				: lastInterestCalculated.getDistributionDate();
		Double balAmount = lastInterestCalculated == null ? 0.0 : lastInterestCalculated.getTotalBalance();

		List<Interest> interestClassList = new ArrayList<>();
		Boolean isLastTransactionInterest = false;
		// Double compoundAmt = 0.0;
		int dateDiff = 0;
		Double intAmt = 0.0;

		int n = 0;
		int j = 0;
		Double compoundInterest = 0.0;
		Boolean isLastTransactionWithdraw = false;
		for (int i = 0; i < interestList.size(); i++) {
			Date interestDate = interestList.get(i);

			// if (i > 0)
			// firstDateOfMonth = DateService.getFirstDateOfMonth(interestDate);
			lastDateOfMonth = DateService.getLastDateOfMonth(interestDate);

			// Payment/withdraw etc is done
			if (distributionList != null) {
				for (int k = j; k < distributionList.size(); k++) {
					Distribution distribution = distributionList.get(k);
					if (lastDistributionDate == null) {
						firstDateOfMonth = distribution.getDistributionDate();
					} else {

						int dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth,
								distribution.getDistributionDate());

						if (distribution.getAction().equalsIgnoreCase("Interest")
								&& distribution.getInterestAdjusted() == 1) {
							j++;
							continue;
						}
						if (distribution.getAction().equalsIgnoreCase("Interest Adjustment For Withdraw")) {
							balAmount = distribution.getTotalBalance();
							j++;
							continue;
						}
						if ((distribution.getAction().equalsIgnoreCase("TDS")
								|| distribution.getAction().equalsIgnoreCase("PAYOFF"))) {
							balAmount = distribution.getTotalBalance();
							isLastTransactionInterest = true;

						}

						if (dayDiff > 0) {

							if (distribution.getAction().equalsIgnoreCase("Withdraw")) {
								isLastTransactionWithdraw = true;

								dayDiff = DateService.getDaysBetweenTwoDates(
										lastTopupBeforeAdjustment.getDistributionDate(),
										distribution.getDistributionDate()) + 1;

								if (dayDiff > 0) {
									Float withdrawInterestRate = calculationService.getDepositInterestRate(dayDiff,
											this.getPrimaryHolderCategory(depositHolderList),
											deposit.getDepositCurrency(), deposit.getDepositAmount(),
											deposit.getDepositClassification(), 
											deposit.getPrimaryCitizen(), deposit.getPrimaryNRIAccountType());
									
									intAmt = (Math.abs(distribution.getVariableAmt()) * withdrawInterestRate / 100)
											/ 365 * dayDiff;
									compoundInterest = compoundInterest + intAmt;
									isLastTransactionWithdraw = true;
								}
							} else if (distribution.getAction().equalsIgnoreCase("Payment")) {
								if (isLastTransactionWithdraw) {
									// After interest calculation, No
									// Payment/Withdraw has been done
									dayDiff = DateService.getDaysBetweenTwoDates(
											lastTopupBeforeAdjustment.getDistributionDate(),
											distribution.getDistributionDate()) + 1;
								}

								// balAmount = (distribution.getTotalBalance() *
								// modifiedInterestRate / 100) / 365 * dayDiff;
								intAmt = (balAmount * preiviousInterestRate / 100) / 365 * dayDiff;
								compoundInterest = compoundInterest + intAmt;
								lastTopupBeforeAdjustment = distribution;
								isLastTransactionInterest = false;
							} else {
								intAmt = (balAmount * preiviousInterestRate / 100) / 365 * dayDiff;
								compoundInterest = compoundInterest + intAmt;
								// totalInterestAmt = totalInterestAmt + intAmt;
								isLastTransactionInterest = false;
							}
						}

					}

					System.out.println("Rate: " + preiviousInterestRate + " ---- " + "Distribution Date: "
							+ distribution.getDistributionDate());
					balAmount = distribution.getTotalBalance();
					firstDateOfMonth = distribution.getDistributionDate();
					lastDistributionDate = distribution.getDistributionDate();
					if (isLastTransactionInterest)
						firstDateOfMonth = DateService.addDays(distribution.getDistributionDate(), 1);

					if (DateService.getDaysBetweenTwoDates(distribution.getDistributionDate(), interestDate) <= 0) {
						j = k;
						break;
					}

					j++;
				}
			}

			// future payment
			if (depositList != null) {

				for (int m = n; m < depositList.size(); m++) {
					Date depositDate = depositList.get(m);

					if (DateService.getDaysBetweenTwoDates(depositDate, interestDate) < 0) {
						n = m;
						break;
					}
					// if (DateService.getDaysBetweenTwoDates(firstDateOfMonth,
					// depositDate) < 0) {
					// n = m;
					// continue;
					// }
					int dayDiff = 0;
					if (isLastTransactionWithdraw) {
						// After interest calculation, No Payment/Withdraw has
						// been done
						dayDiff = DateService.getDaysBetweenTwoDates(lastTopupBeforeAdjustment.getDistributionDate(),
								depositDate);
					} else {
						dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth, depositDate);
					}
					// if(isLastTransactionInterest)
					// dayDiff = dayDiff - 1;

					if (dayDiff > 0) {
						intAmt = (balAmount * modifiedInterestRate / 100) / 365 * dayDiff;
						balAmount = balAmount + modifiedDepositAmount;
						// balAmount = balAmount + intAmt;
						firstDateOfMonth = DateService.generateDaysDate(depositDate, 1);
						compoundInterest = compoundInterest + intAmt;
					}

					isLastTransactionWithdraw = false;
					isLastTransactionInterest = false;
					n++;
				}
			}

			if (DateService.getDaysBetweenTwoDates(maturityDate, lastDateOfMonth) >= 0)
				lastDateOfMonth = maturityDate;

			// int dayDiff =
			// DateService.getDaysBetweenTwoDates(firstDateOfMonth,
			// lastDateOfMonth) + 1;
			int dayDiff = DateService.getDaysBetweenTwoDates(firstDateOfMonth, interestDate) + 1;

			// if (lastInterestDate != null && isLastTransactionInterest) {
			//// if (DateService.getMonth(lastInterestDate) -
			// DateService.getMonth(nextDepositDate) != 0)
			// dayDiff = dayDiff -1;
			// }
			//

			intAmt = (balAmount * modifiedInterestRate / 100) / 365 * dayDiff;

			compoundInterest = compoundInterest + intAmt;
			balAmount = compoundInterest + balAmount;

			Interest interest = new Interest();
			interest.setInterestRate((double) modifiedInterestRate);
			interest.setInterestAmount(round(compoundInterest, 2));
			interest.setInterestDate(interestDate);
			interest.setDepositId(depositId);
			interest.setFinancialYear(DateService.getFinancialYear(interestDate));
//			interest.setInterestSum(round(balAmount, 2));
			interestClassList.add(interest);

			lastInterestDate = interestDate;
			firstDateOfMonth = DateService.addDays(interestDate, 1);
			isLastTransactionInterest = true;
			intAmt = 0.0;
			compoundInterest = 0.0;

		}

		return interestClassList;
	}
	
	
	
	public Float getDepositInterestRateForCitizens(Integer tenure, String category, String currency, Double depositAmount, String depositClassification , String citizen, String accountType) {
		
		return calculationService.getDepositInterestRate(tenure, category, currency, depositAmount, depositClassification, citizen, accountType);
	}

	
	
	
	
	public Double getExpectedMaturityAmount(Date fromDate, Date maturityDate, FixedDepositForm fixedDepositForm,
			Float interestRate) throws CustomException   {

		// Get all the configurations which have monthly interest calculation
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(fixedDepositForm.getProductConfigurationId());	
		if(productConfiguration == null)
			{productConfiguration = productConfigurationDAO.findByProductCode(fixedDepositForm.getProductConfigurationId().toString());}
		if(productConfiguration == null)	
		throw new CustomException("Please fill the data for the Citizen and Account Type first");
		
		List<Date> interestDates = calculationService.getFutureInterestDates(fromDate, maturityDate, productConfiguration.getInterestCalculationBasis());
		List<Date> compoundingDates = calculationService.getFutureCompoundingDates(fromDate, maturityDate, productConfiguration.getInterestCompoundingBasis());
		List<Date> depositDates = this.getFutureDepositDates(fixedDepositForm, maturityDate); //this.getDepositDates(DateService.getCurrentDate(), maturityDate, fixedDepositForm);
		

		DepositSummary depositSummary = new DepositSummary();

		Double depositAmt = (double) fixedDepositForm.getFdAmount();
		Double compoundPrincipalAmt = 0.0;
		Double totalUncomoundedInterestAmt = 0.0;
		int k = 0;
		int n = 0;
		int daysDiff = 0;
		Date lastInterestDate = null;
		Boolean isLastTransactionInterest = false;
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
			
			isLastTransactionInterest = false;

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
					isLastTransactionInterest = true;
					k++; 
				}
				else if (DateService.getDaysBetweenTwoDates(depositDate, nextDepositDate) == 0)
				{
					// For one time payment where depositDate and nextDepositDate will be same
					daysDiff = DateService.getDaysBetweenTwoDates(startDate, interestDate) + 1;
					Double interestAmount = (compoundPrincipalAmt * interestRate/100/365) * daysDiff;
					totalUncomoundedInterestAmt = totalUncomoundedInterestAmt + interestAmount;
					
					startDate = DateService.addDays(interestDate, 1);
					isLastTransactionInterest = true;
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
					isLastTransactionInterest = false;
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

	
	public List<Date> getFutureDepositDates(FixedDepositForm fixedDepositForm, Date maturityDate) {
		
				List<Date> depositDates = new ArrayList();
				// Date fromDate = DateService.getCurrentDate();
				Date dueDate = fixedDepositForm.getFdDeductDate();
				if (dueDate == null || fixedDepositForm.getPaymentType() == null)
					return null;
		
				depositDates.add(dueDate);
				
				// Deduction day will be null in case of one time payment
				if(fixedDepositForm.getDeductionDay() == null)
					return  depositDates;
				
				dueDate=DateService.setDate(dueDate, fixedDepositForm.getDeductionDay());
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
		
				// From next due Deposits
				while (true) {
		
					dueDate = DateService.addDays(DateService.generateMonthsDate(dueDate, monthsToAdd), 1);
					dueDate=DateService.setDate(dueDate, fixedDepositForm.getDeductionDay());
					if (monthsToAdd == 0)
						break;
					if (DateService.getDaysBetweenTwoDates(dueDate, maturityDate) > 0) {
						depositDates.add(dueDate);
					} else
						break;
				}
		
				return depositDates;
			}
	
	
	public String[] getFCNRCurrencies()
	{
		String curFCNR[]=new String[8];//declaration and instantiation  
		
		curFCNR[0]="USD";
		curFCNR[1]="GBP";  
		curFCNR[2]="EUR";  
		curFCNR[3]="JPY";  
		curFCNR[4]="CAD";  
		curFCNR[5]="AUD";  
		curFCNR[6]="HKD";  
		curFCNR[7]="SGD"; 
		
		return curFCNR;
	}

	public String[] getRFCCurrencies()
	{
		String curRFC[]=new String[6];//declaration and instantiation  
		
		curRFC[0]="USD";
		curRFC[1]="GBP";  
		curRFC[2]="EUR";  
		curRFC[3]="JPY";  
		curRFC[4]="CAD";  
		curRFC[5]="AUD";  
				
		return curRFC;
	}
	
	
	
	public HashMap<String, Double> convertDeposit(Deposit deposit)
	{
			
			Long depositId = deposit.getId();
			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
			Customer customer = customerDAO.getById(this.getPrimaryHolderId(depositHolderList));
			
			Date createdDate = deposit.getCreatedDate();
			
//			Steps:
//				1. Calculate the Interest till today with existing interest rate
//              2. Compound the Interest
//  			3. Deduct if any Previous Penalty Due
//				4. Find out the interest from the last base line to current date
//				5. Find out the interest amount that needs to adjust, customer will lose the amount
//			    6. Find the Balance Amount (multiple of 100) to find out the new Deposit Amount
//				7. Change will go to the Saving bank account
			
				
				
			// 1. Calculate the Interest till today with existing interest rate
			//-------------------------------------------
			// Calculate the Fixed And Variable Interest with existing interest rate
			HashMap<String, Double> interestDetails = calculationService.calculateInterestAmount(deposit, depositHolderList, "", null);

			Double totalFixedInterest = interestDetails.get(Constants.FIXEDINTEREST);
			Double totalVariableInterest = interestDetails.get(Constants.VARIABLEINTEREST);
			
			totalFixedInterest = totalFixedInterest == null? 0d: totalFixedInterest;
			totalVariableInterest = totalVariableInterest == null? 0d: totalVariableInterest;
			// Find the Total Interest Amount
			Double totalInterest = this.round(totalFixedInterest + totalVariableInterest, 2);		
			//-------------------------------------------

			// 2. Compound the Interest i.e find the in-hand interest
			// add the inhand interest to the interest calculated till date
			//-------------------------------------------		
			DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(depositId);

			// Compound the Interest
			if (depositSummary == null)
				return null;

			// Getting in Hand Interest which is not compounded
			Double fixedInHandInterest = depositSummary.getTotalFixedInterestInHand() != null ? depositSummary.getTotalFixedInterestInHand() : 0.0;
			Double variableInHandInterest = depositSummary.getTotalVariableInterestInHand() != null ? depositSummary.getTotalVariableInterestInHand() : 0.0;
			
			totalInterest = totalInterest + ((fixedInHandInterest == 0? 0d :fixedInHandInterest) + (variableInHandInterest == 0? 0d :variableInHandInterest));
			//-------------------------------------------
			
			
			// 3. Deduct penaltyDue from Total Amount
			// -----------------------------------------
			Double prevPenaltyDue = 0d;
			// Fetch last Penalty of the deposit
			ModificationPenalty modPenalty = depositModificationDAO.getLastModificationPenalty(deposit.getId());
			if (modPenalty != null)
				prevPenaltyDue = modPenalty.getPenaltyDue();
			
			// Deduct the penalty from total interest
			totalInterest = totalInterest - prevPenaltyDue;
			// -----------------------------------------
		

//			3. Find out the interest from the last base line to current date 
			// with the new interest rate for reduced duration
			//-------------------------------------------
			Distribution lastBaseLine = paymentDistributionDAO.getLastBaseLineForTenureChange(depositId);
			Integer currentTenure = DateService.getDaysBetweenTwoDates(createdDate, DateService.getCurrentDate());
			
			// Get Interest rate for current tenure
			Float currentInterestRate = calculationService.getDepositInterestRate(currentTenure, deposit.getPrimaryCustomerCategory(),
					deposit.getDepositCurrency(), deposit.getDepositAmount(), deposit.getDepositClassification(),
					deposit.getPrimaryCitizen(), deposit.getNriAccountType());
			

			Double fixedInterest = ((lastBaseLine.getCompoundFixedAmt() * currentInterestRate / 100 / 365) * currentTenure);
			Double variableInterest = ((lastBaseLine.getCompoundVariableAmt() * currentInterestRate / 100 / 365)
					* currentTenure);
			//-------------------------------------------
			
			
//			4. Find out the interest amount that needs to adjust 
			// Customer will loose this amount 
			//-------------------------------------------
			Double interestNeedsToAdjust = totalInterest - (fixedInterest + variableInterest);
			//-------------------------------------------

		
//			5. Find out the Balance Amount
			//-------------------------------------------			
			Double balanceAfterAdjustment = deposit.getCurrentBalance()-interestNeedsToAdjust;
			
			//Double quotient = balanceAfterAdjustment/100;
			// this amount will go to either Saving account or suspense account 
			// We are dividing with 100 as amount will be taken as multiple of 100
			// means if balanceAfterAdjustment = 30,322.13, then 22.13 will be 
			// the remainder and it will go to the saving account.
			Double remainder = balanceAfterAdjustment%100;
			
			Double newDepositAmount = (balanceAfterAdjustment - remainder);
			
			HashMap<String, Double> depositConversionDetails = new HashMap<>();
			depositConversionDetails.put(Constants.LoseAmount, interestNeedsToAdjust);
			depositConversionDetails.put(Constants.NewDepositAmount, newDepositAmount);
			depositConversionDetails.put(Constants.TransferAmountToSavingAccount, remainder);
			depositConversionDetails.put(Constants.InterestRate, Double.parseDouble(currentInterestRate.toString()));
			//-------------------------------------------
			
			return depositConversionDetails;

	}
	
	public Date getRevereseEMIPayOffDueDate(Date depositDate, Date gestationEndDate, Date maturityDate, String payOffInterestType, boolean isFromDayEndProcess )
	{
		
		Date dueDate = DateService.loginDate;
		if(DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), gestationEndDate) > 0)
			dueDate = gestationEndDate;

		int month = DateService.getMonth(dueDate);
		if (payOffInterestType.equalsIgnoreCase("One-Time") || payOffInterestType.equalsIgnoreCase("One Time")) {

			dueDate = DateService.getLastDateOfMonth(dueDate);
		} else if (payOffInterestType.equalsIgnoreCase("Monthly")) {

			if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(dueDate), maturityDate) < 0) {
				// dueDate = maturityDate;
				return dueDate;
			}
			dueDate = DateService.getLastDateOfMonth(dueDate);

		} else if (payOffInterestType.equalsIgnoreCase("Quarterly")) {

			while (true) {
				if (month == 2 || month == 5 || month == 8 || month == 11) {
					break;
				} else {
					dueDate = DateService.addMonths(dueDate, 1);
					month = DateService.getMonth(dueDate);
				}
			}
			if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(dueDate), maturityDate) < 0) {
				// dueDate = maturityDate;
				return dueDate;
			}
			dueDate = DateService.getLastDateOfMonth(dueDate);

		} else if (payOffInterestType.equalsIgnoreCase("Half Yearly")
				|| payOffInterestType.equalsIgnoreCase("Semiannual")) {

			while (true) {
				if (month == 2 || month == 8) {
					break;
				} else {
					dueDate = DateService.addMonths(dueDate, 1);
					month = DateService.getMonth(dueDate);
				}
			}
			if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(dueDate), maturityDate) < 0) {
				// dueDate = maturityDate;
				return dueDate;
			}
			dueDate = DateService.getLastDateOfMonth(dueDate);

		} else if (payOffInterestType.equalsIgnoreCase("Annually") || payOffInterestType.equalsIgnoreCase("Annual")) {

			while (true) {
				if (month == 2) {
					break;
				} else {
					dueDate = DateService.addMonths(dueDate, 1);
					month = DateService.getMonth(dueDate);
				}
			}
			if (DateService.getDaysBetweenTwoDates(DateService.getLastDateOfMonth(dueDate), maturityDate) < 0) {
				// dueDate = maturityDate;
				return dueDate;
			}
			dueDate = DateService.getLastDateOfMonth(dueDate);

		} else if (payOffInterestType.equalsIgnoreCase("End of Tenure")) {

			dueDate = maturityDate;
		}
		return dueDate;

	}
	
	@Transactional
	public void saveSweepInFacilityForCustomer(Integer isSeepBankRestrict ,Long customerId, Integer isSweepRequired, String tenure,
			Integer minimumSavingBalanceForSweepIn, Integer minimumAmountRequiredForSweepIn) {

		Customer customer = customerDAO.getById(customerId);
		AccountDetails accDetails = accountDetailsDAO.findSavingByCustId(customer.getId());
		
		// Get the active sweep configuration 
		SweepConfiguration sweepconfig = sweepConfigurationDAO.getActiveSweepConfiguration();

		SweepInFacilityForCustomer sweepInFacilityForCustomer = sweepConfigurationDAO.getSweepInFacilityForCustomer(customer.getId());
		if (sweepInFacilityForCustomer == null) {
			Integer tenureInDays = 0;
			String[] tenureDaysAndMonthsAndYears = tenure.split(",");
			Calendar cal = Calendar.getInstance();
			int currentYear = cal.get(Calendar.YEAR);
			int currentMonth = cal.get(Calendar.MONTH);
			int currentMonthTotalNumberOfDays = YearMonth.of(currentYear, currentMonth).lengthOfMonth();
			String currentDateIn[] = LocalDate.now().toString().split("-");
			String currentDateDaysPassed = currentDateIn[2];
			int currentMonthDaysLeft = currentMonthTotalNumberOfDays - Integer.valueOf(currentDateDaysPassed);

			int yearAddCount = 1;
			int monthAddCount = 1;
			int daysInMonth = 0;
			for (String inDayMonthYear : tenureDaysAndMonthsAndYears) {
				if (inDayMonthYear.contains("Month")) {
					String month[] = inDayMonthYear.split(" ");
					int numberOfMonth = Integer.valueOf(month[0]);
					YearMonth yearMonthObject = null;
					while (monthAddCount <= numberOfMonth) {
						if (monthAddCount == 1) {
							// yearMonthObject = YearMonth.of(currentYear, currentMonth);
							daysInMonth = currentMonthDaysLeft;
						} else {
							currentMonth = currentMonth + 1;
							yearMonthObject = YearMonth.of(currentYear, currentMonth);
							daysInMonth = yearMonthObject.lengthOfMonth();
						}

						// int daysInMonth = yearMonthObject.lengthOfMonth();
						tenureInDays += daysInMonth;
						monthAddCount++;
						if (currentMonth == 12) {
							currentYear += 1;
							currentMonth = 1;
						}

					}
				} else if (inDayMonthYear.contains("Year")) {
					String year[] = inDayMonthYear.split(" ");
					int numberOfYear = Integer.valueOf(year[0]);
					while (yearAddCount <= numberOfYear) {
						int daysInYear = 0;
						if (yearAddCount == 1)
							daysInYear = (Year.isLeap(currentYear)) ? 366 : 365;
						else
							daysInYear = (Year.isLeap(currentYear + yearAddCount - 1)) ? 366 : 365;
						tenureInDays += daysInYear;
						yearAddCount++;
					}
				} else if (inDayMonthYear.contains("Day")) {
					String day[] = inDayMonthYear.split(" ");
					tenureInDays += Integer.parseInt(day[0]);

				}

			}
			if (tenureInDays == 0) {
				tenureInDays = 365;
			}else {
				tenureInDays = tenureInDays + Integer.valueOf(currentDateDaysPassed);
			}


			String category = calculationService.geCustomerActualCategory(customer);
			
			// Default Currency We have to Configure In Separate Location
			//---------------------------------------------------	
			String currency = "INR";
			CurrencyConfiguration currConfig = fixedDepositDao.getDefaultCurrency(customer.getCitizen(), customer.getNriAccountType());
			currency = currConfig != null? currConfig.getCurrency() : currency;
			//---------------------------------------------------

			Integer amount = sweepconfig.getMinimumAmountRequiredForSweepIn();
			Float interestRate = calculationService.getDepositInterestRate(
					tenureInDays, category, currency,  Double.valueOf(amount),
					Constants.fixedDeposit, customer.getCitizen(), customer.getNriAccountType());

			sweepInFacilityForCustomer = new SweepInFacilityForCustomer();
			sweepInFacilityForCustomer.setAccountId(accDetails.getId());
			sweepInFacilityForCustomer.setCustomerId(customer.getId());
			sweepInFacilityForCustomer.setInitialInterestRate(interestRate);
			sweepInFacilityForCustomer.setModifiedDate(DateService.getCurrentDateTime());
			sweepInFacilityForCustomer.setModifiedBy(this.getCurrentLoggedUserName());
			sweepInFacilityForCustomer.setCreatedDate(new Date());
			sweepInFacilityForCustomer.setTenureInDays(tenureInDays);
			sweepInFacilityForCustomer.setCreatedBy(getCurrentLoggedUserName());

			sweepInFacilityForCustomer.setTenure(tenure);
			sweepInFacilityForCustomer.setIsSweepInConfigureedByCustomer(isSweepRequired);
			sweepInFacilityForCustomer.setIsSweepInRestrictedByBank(isSeepBankRestrict);
			
			sweepInFacilityForCustomer.setMinimumAmountRequiredForSweepIn(minimumSavingBalanceForSweepIn);
			sweepInFacilityForCustomer.setMinimumSavingBalanceForSweepIn(minimumAmountRequiredForSweepIn);
			sweepConfigurationDAO.insertSweepInFacilityForCustomer(sweepInFacilityForCustomer);

		} else {

			Integer tenureInDays = 0;
			String[] tenureDaysAndMonthsAndYears = tenure.split(",");
			Calendar cal = Calendar.getInstance();
			int currentYear = cal.get(Calendar.YEAR);
			int currentMonth = cal.get(Calendar.MONTH);
			int currentMonthTotalNumberOfDays = YearMonth.of(currentYear, currentMonth).lengthOfMonth();
			String currentDateIn[] = LocalDate.now().toString().split("-");
			String currentDateDaysPassed = currentDateIn[2];
			int currentMonthDaysLeft = currentMonthTotalNumberOfDays - Integer.valueOf(currentDateDaysPassed);

			int yearAddCount = 1;
			int monthAddCount = 1;
			int daysInMonth = 0;
			for (String inDayMonthYear : tenureDaysAndMonthsAndYears) {
				if (inDayMonthYear.contains("Month")) {
					String month[] = inDayMonthYear.split(" ");
					int numberOfMonth = Integer.valueOf(month[0]);
					YearMonth yearMonthObject = null;
					while (monthAddCount <= numberOfMonth) {
						if (monthAddCount == 1) {
							// yearMonthObject = YearMonth.of(currentYear, currentMonth);
							daysInMonth = currentMonthDaysLeft;
						} else {
							currentMonth = currentMonth + 1;
							yearMonthObject = YearMonth.of(currentYear, currentMonth);
							daysInMonth = yearMonthObject.lengthOfMonth();
						}

						// int daysInMonth = yearMonthObject.lengthOfMonth();
						tenureInDays += daysInMonth;
						monthAddCount++;
						if (currentMonth == 12) {
							currentYear += 1;
							currentMonth = 1;
						}

					}
				} else if (inDayMonthYear.contains("Year")) {
					String year[] = inDayMonthYear.split(" ");
					int numberOfYear = Integer.valueOf(year[0]);
					while (yearAddCount <= numberOfYear) {
						int daysInYear = 0;
						if (yearAddCount == 1)
							daysInYear = (Year.isLeap(currentYear)) ? 366 : 365;
						else
							daysInYear = (Year.isLeap(currentYear + yearAddCount - 1)) ? 366 : 365;
						tenureInDays += daysInYear;
						yearAddCount++;
					}
				} else if (inDayMonthYear.contains("Day")) {
					String day[] = inDayMonthYear.split(" ");
					tenureInDays += Integer.parseInt(day[0]);

				}

			}
			if (tenureInDays == 0) {
				tenureInDays = 365;
			}else {
				tenureInDays = tenureInDays + Integer.valueOf(currentDateDaysPassed);
			}
			
			String category = calculationService.geCustomerActualCategory(customer);
			// Default Currency We have to Configure In Separate Location
			//---------------------------------------------------	
			String currency = "INR";
			CurrencyConfiguration currConfig = fixedDepositDao.getDefaultCurrency(customer.getCitizen(), customer.getNriAccountType());
			currency = currConfig != null? currConfig.getCurrency() : currency;
			//---------------------------------------------------
			
			Double amount = Double.valueOf(sweepconfig.getMinimumAmountRequiredForSweepIn());
			Float interestRate = calculationService.getDepositInterestRate(
					tenureInDays, category, currency, amount,
					Constants.fixedDeposit, customer.getCitizen(), customer.getNriAccountType());
			sweepInFacilityForCustomer.setInitialInterestRate(interestRate);
			sweepInFacilityForCustomer.setModifiedDate(DateService.getCurrentDateTime());
			sweepInFacilityForCustomer.setModifiedBy(this.getCurrentLoggedUserName());
			sweepInFacilityForCustomer.setTenure(tenure);
			sweepInFacilityForCustomer.setIsSweepInConfigureedByCustomer(isSweepRequired);
			sweepInFacilityForCustomer.setIsSweepInRestrictedByBank(isSeepBankRestrict);
			sweepInFacilityForCustomer.setMinimumAmountRequiredForSweepIn(minimumSavingBalanceForSweepIn);
			sweepInFacilityForCustomer.setMinimumSavingBalanceForSweepIn(minimumAmountRequiredForSweepIn);
			sweepInFacilityForCustomer.setTenureInDays(tenureInDays);
			sweepConfigurationDAO.insertSweepInFacilityForCustomer(sweepInFacilityForCustomer);
		}

	}
	
	
}
