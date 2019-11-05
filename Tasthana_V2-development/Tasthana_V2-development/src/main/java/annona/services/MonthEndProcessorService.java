package annona.services;

import java.math.BigDecimal;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.GeneratedValue;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import annona.dao.AccountDetailsDAO;
import annona.dao.BenificiaryDetailsDAO;
import annona.dao.CustomerDAO;

import annona.dao.DepositHolderDAO;
import annona.dao.DepositModificationDAO;
import annona.dao.DepositRateDAO;
import annona.dao.DepositRatesDAO;
import annona.dao.EndUserDAO;
import annona.dao.FDRatesDAO;
import annona.dao.FixedDepositDAO;
import annona.dao.FormSubmissionDAO;
import annona.dao.FundTransferDAO;
import annona.dao.GstDAO;
import annona.dao.InterestDAO;
import annona.dao.DepositSummaryDAO;
import annona.dao.PayoffDAO;
import annona.dao.PaymentDAO;
import annona.dao.PaymentDistributionDAO;
import annona.dao.RatesDAO;
import annona.dao.ReverseEMIDAO;
import annona.dao.TDSDAO;
import annona.dao.TransactionDAO;
import annona.dao.DepositHolderWiseDistributionDAO;


import annona.form.FixedDepositForm;

import annona.scheduler.NotificationsScheduler;


/**
 * 
 *
 *
 */

enum CustomerCategoty {
	General, Disabled, SeniorCitizen, NGO, CharitableOrganization
}

@Service
public class MonthEndProcessorService {

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
	FixedDepositDAO fixedDepositDAO;

	@Autowired
	DepositModificationDAO depositModificationDAO;

	@Autowired
	DepositHolderDAO depositHolderDAO;

	@Autowired
	AccountDetailsDAO accountDetailsDAO;

	@Autowired
	PaymentDAO paymentDAO;

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
	PayoffDAO payOffDAO;
	
	@Autowired
	BenificiaryDetailsDAO beneficiaryDetailsDAO;
	
	@Autowired
	NotificationsScheduler notificationsScheduler;


	protected static Logger logger = Logger.getLogger("service");

	
	public MonthEndProcessorService()
	{
		
	}

//	
//
//	notificationsScheduler.caculateInterest(); // for interest and tds deduction
//	
//	notificationsScheduler.caculatePayOff();
//	
//	notificationsScheduler.calculateModificationPenalty();
//	
//	notificationsScheduler.autoDeduction();
//	
//	notificationsScheduler.transferMoneyOnMaturity();
//	
//	notificationsScheduler.deductReverseEMI();
//	
//	notificationsScheduler.paymentRemindMail();
//	
//	notificationsScheduler.deductTDS();
//	notificationsScheduler.createAutoDeposit();
//	
//	
}
