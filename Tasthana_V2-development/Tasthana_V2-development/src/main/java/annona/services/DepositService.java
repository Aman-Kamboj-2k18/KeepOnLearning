package annona.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.jmx.LoggerDynamicMBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import annona.dao.AccountDetailsDAO;
import annona.dao.BenificiaryDetailsDAO;
import annona.dao.CustomerDAO;
import annona.dao.DepositHolderDAO;
import annona.dao.DepositModificationDAO;
import annona.dao.DepositRateDAO;
import annona.dao.DepositSummaryDAO;
import annona.dao.FDRatesDAO;
import annona.dao.FixedDepositDAO;
import annona.dao.InterestDAO;
import annona.dao.ModeOfPaymentDAO;
import annona.dao.NomineeDAO;
import annona.dao.PayoffDAO;
import annona.dao.ProductConfigurationDAO;
import annona.dao.RatesDAO;
import annona.dao.UnSuccessfulRecurringDAO;
import annona.dao.PaymentDAO;
import annona.dao.PaymentDistributionDAO;
import annona.domain.AccountDetails;
import annona.domain.BankConfiguration;
import annona.domain.BenificiaryDetails;
import annona.domain.Customer;
import annona.domain.Deposit;
import annona.domain.DepositHolder;
import annona.domain.DepositHolderNominees;
import annona.domain.DepositModification;
import annona.domain.DepositModificationMaster;
import annona.domain.DepositSummary;
import annona.domain.Distribution;
import annona.domain.EndUser;
import annona.domain.Interest;
import annona.domain.ModeOfPayment;
import annona.domain.ModificationPenalty;
import annona.domain.PayoffDetails;
import annona.domain.ProductConfiguration;
import annona.domain.Rates;
import annona.domain.UnSuccessfulRecurringDeposit;
import annona.exception.CustomException;
import annona.domain.Payment;
import annona.form.DepositForm;
import annona.form.DepositHolderForm;
import annona.form.FixedDepositForm;
import annona.form.HolderForm;
import annona.scheduler.NotificationsScheduler;
import annona.utility.Constants;
import annona.utility.Event;
import annona.services.*;

@Service
public class DepositService {

	@Autowired
	MailSender mailSender;
	
	@Autowired
	DepositHolderDAO depositHolderDAO;

	@Autowired
	FixedDepositDAO fixedDepositDAO;

	@Autowired
	DepositModificationDAO depositModificationDAO;

	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	BenificiaryDetailsDAO benificiaryDetailsDAO;

	@Autowired
	FDService fdService;

	@Autowired
	AccountDetailsDAO accountDetailsDAO;

	@Autowired
	NomineeDAO nomineeDAO;

	@Autowired
	PaymentDAO paymentDAO;

	@Autowired
	FDRatesDAO fdRatesDAO;

	@Autowired
	InterestDAO interestDAO;

	@Autowired
	DepositRateDAO depositRateDAO;

	@Autowired
	FixedDepositForm fixedDepositForm;

	@Autowired
	PayoffDAO payOffDAO;

	@Autowired
	PaymentDistributionDAO paymentDistributionDAO;
	
	@Autowired
	CalculationService calculationService;
	
	@Autowired
	DepositSummaryDAO depositSummaryDAO;
	
	@Autowired
	LedgerService ledgerService;
	
	@Autowired
	RatesDAO ratesDAO;
	
	@Autowired
	UnSuccessfulRecurringDAO unSuccessfulRecurringDAO;
	
	@Autowired
	ProductConfigurationDAO productConfigurationDAO;
	
	@Autowired
	NotificationsScheduler notificationScheduler;

	@Autowired
	ModeOfPaymentDAO modeOfPaymentDAO;
	// list of deposits for a customer...from deposit,depositHolder,
	// depositModification table....

	/* customer edit list */
	public List<HolderForm> getAllDepositByCustomerId(Long customerId) {

		List<HolderForm> depositHolderList = fixedDepositDAO.getByCustomerId(customerId);

		if (depositHolderList != null && depositHolderList.size() > 0) {

			for (int i = 0; i < depositHolderList.size(); i++) {
				Deposit deposit = depositHolderList.get(i).getDeposit();
				ProductConfiguration _pc = productConfigurationDAO.findByProductCode(String.valueOf(deposit.getProductConfigurationId()));
				if(_pc==null){
					_pc=productConfigurationDAO.findById(deposit.getProductConfigurationId());
				}
				depositHolderList.get(i).setDepositId(deposit.getId());
				depositHolderList.get(i).setProductConfiguration(_pc);
				DepositModification lastModification = depositModificationDAO.getLastByDepositId(deposit.getId());

				if (lastModification != null) {

					deposit.setDepositAmount(lastModification.getDepositAmount());
					deposit.setMaturityDate(lastModification.getMaturityDate());
					deposit.setPaymentMode(lastModification.getPaymentMode());
					deposit.setPaymentType(lastModification.getPaymentType());
					deposit.setPayOffInterestType(lastModification.getPayOffType());
					deposit.setTenure(lastModification.getTenure());
					deposit.setTenureType(lastModification.getTenureType());
					deposit.setStopPayment(lastModification.getStopPayment());

					/* skipping payoff setters */

				}
			}
		}

		return depositHolderList;
	}




	/***************** for customer edit form ********************/

	public HolderForm getByDepositIdHolderIdCusId(Long depositId, Long holderId, Long customerId) {

		HolderForm depositHolder = fixedDepositDAO.getByCustomerIdAndDepositId(depositId, customerId);

		/************** last record from deposit modification... ************/
		DepositModification depositModification = depositModificationDAO.getLastByDepositId(depositId);

		if (depositModification != null) {

			Deposit deposit = depositHolder.getDeposit();
			deposit.setDepositAmount(depositModification.getDepositAmount());

			deposit.setMaturityDate(depositModification.getMaturityDate());
			deposit.setPaymentMode(depositModification.getPaymentMode());
			deposit.setPaymentType(depositModification.getPaymentType());
			deposit.setPayOffInterestType(depositModification.getPayOffType());
			deposit.setTenure(depositModification.getTenure());
			deposit.setTenureType(depositModification.getTenureType());
			deposit.setStopPayment(depositModification.getStopPayment());

			/******
			 * payoff setters for this holder from modification table
			 *******/

			DepositModification dModification = depositModificationDAO.getDepositHolderModification(holderId);
			if (dModification != null) {

				/* searching modification for a deposit holder */

				if (dModification != null) {
					DepositHolder holder = depositHolder.getDepositHolder();

					holder.setAccountNumber(dModification.getPayOffAccountNumber());
					holder.setBankName(dModification.getPayOffBankName());
					holder.setIfscCode(dModification.getPayOffBankIFSCCode());
					holder.setInterestAmt(dModification.getPayOffInterestAmt());
					holder.setInterestPercent(dModification.getPayOffInterestPercent());
					holder.setInterestType(dModification.getPayOffInterestType());
					holder.setNameOnBankAccount(dModification.getPayOffNameOnBankAccount());
					holder.setPayOffAccountType(dModification.getPayOffAccountType());
					holder.setTransferType(dModification.getPayOffTransferType());
				}

			}
		}
		return depositHolder;
	}

	/************** FOR Customer and Bank ..Edit Reverse EMI.. ************/
	public HolderForm getReverseEMIByDepositId(Long depositId) {
		// HolderForm holderForm=new HolderForm();
		HolderForm holderForm = (fixedDepositDAO.getByDepositId(depositId)).get(0);

		/************** last record from deposit modification... ************/
		DepositModification depositModification = depositModificationDAO.getLastByDepositId(depositId);

		if (depositModification != null) {
			Deposit deposit = holderForm.getDeposit();
			deposit.setDepositAmount(depositModification.getDepositAmount());

			deposit.setMaturityDate(depositModification.getMaturityDate());
			deposit.setTenure(depositModification.getTenure());
			deposit.setTenureType(depositModification.getTenureType());

		}
		/****** setting from benificiary table *******/

		List<BenificiaryDetails> benificiaryList = benificiaryDetailsDAO.getActiveBeneficiary(depositId);
		holderForm.setBenificiaryList(benificiaryList);

		return holderForm;
	}

	/***************** for bank edit form ********************/

	public List<HolderForm> getDepositByDepositId(Long depositId) {

		List<HolderForm> depositHolderObjList = fixedDepositDAO.getByDepositId(depositId);

		/************** last record from deposit modification... ************/
		DepositModification depositModification = depositModificationDAO.getLastByDepositId(depositId);

		if (depositModification != null) {
			Deposit deposit = depositHolderObjList.get(0).getDeposit();
			deposit.setDepositAmount(depositModification.getDepositAmount());
			deposit.setMaturityDate(depositHolderObjList.get(0).getDeposit().getNewMaturityDate());
			deposit.setPaymentMode(depositModification.getPaymentMode());
			deposit.setPaymentType(depositModification.getPaymentType());
			deposit.setPayOffInterestType(depositModification.getPayOffType());
			deposit.setTenure(depositModification.getTenure());
			deposit.setTenureType(depositModification.getTenureType());
			deposit.setStopPayment(depositModification.getStopPayment());
		}
		for (int i = 0; i < depositHolderObjList.size(); i++) {

			Customer cus = customerDAO.getById(depositHolderObjList.get(i).getDepositHolder().getCustomerId());
			depositHolderObjList.get(i).setCustomerName(cus.getCustomerName());

			/****** searching modification for a deposit holder *********/
			if (depositModification != null) {

				DepositModification dModification = depositModificationDAO
						.getDepositHolderModification(depositHolderObjList.get(i).getDepositHolder().getId());

				if (dModification != null) {
					DepositHolder holder = depositHolderObjList.get(i).getDepositHolder();

					holder.setAccountNumber(dModification.getPayOffAccountNumber());
					holder.setBankName(dModification.getPayOffBankName());
					holder.setIfscCode(dModification.getPayOffBankIFSCCode());
					holder.setInterestAmt(dModification.getPayOffInterestAmt());
					holder.setInterestPercent(dModification.getPayOffInterestPercent());
					holder.setInterestType(dModification.getPayOffInterestType());
					holder.setNameOnBankAccount(dModification.getPayOffNameOnBankAccount());
					holder.setPayOffAccountType(dModification.getPayOffAccountType());
					holder.setTransferType(dModification.getPayOffTransferType());

				}

			}
		}

		return depositHolderObjList;
	}

	/*****************
	 * get open deposit from deposit table a customer
	 *********************/

	public List<DepositHolderForm> getOpenDepositsByCustomerId(Long userId) {

		List<Object[]> depositHolderObjList = depositHolderDAO.getAllOpenDeposits(userId);
		List<DepositHolderForm> depositHolderList = new ArrayList<DepositHolderForm>();

		if (depositHolderObjList != null && depositHolderObjList.size() > 0) {

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
				depositHolderList.add(depositform);
			}

			return depositHolderList;
		} else {
			return depositHolderList;
		}

	}

	// Save from customer
	@Transactional
	public Deposit saveDepositFromCustomer(FixedDepositForm fixedDepositForm, Customer customerDetails,
				Integer taxSavingDeposit) throws CustomException {
			Date date = DateService.getCurrentDateTime();
			
			Long mopId = Long.parseLong(fixedDepositForm.getDepositForm().getPaymentMode());
			ModeOfPayment mop = modeOfPaymentDAO.getModeOfPaymentById(mopId);
			
			String transactionId = fdService.generateRandomString();
			Deposit deposit = new Deposit();
			int days = fixedDepositForm.getDaysValue() != null ? fixedDepositForm.getDaysValue() : 0;
			deposit.setProductConfigurationId(fixedDepositForm.getProductConfigurationId());
			deposit.setDepositArea(fixedDepositForm.getDepositArea());
			deposit.setAccountNumber(fdService.generateRandomString());
			deposit.setDepositAmount(fixedDepositForm.getFdAmount());
			deposit.setDeductionDay(fixedDepositForm.getDeductionDay());
			deposit.setDepositType(Constants.SINGLE);
			deposit.setPrimaryCitizen(fixedDepositForm.getCitizen());
		    deposit.setPrimaryCustomerCategory(fixedDepositForm.getCategory());
			if(fixedDepositForm.getCitizen().equals(Constants.NRI)){
				
				if(fixedDepositForm.getNriAccountType()!= null && fixedDepositForm.getNriAccountType()!="")
				{
					String nriAccountType = fixedDepositForm.getNriAccountType();
					if(nriAccountType.contains(","))
						nriAccountType = nriAccountType.substring(0, nriAccountType.indexOf(","));
					
					deposit.setNriAccountType(nriAccountType);
					deposit.setPrimaryNRIAccountType(nriAccountType);
				}
		
	        }
			if (!(fixedDepositForm.getStatus().equals("Reverse EMI"))) {
				deposit.setDueDate(fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));
				deposit.setMaturityAmount(fixedDepositForm.getEstimatePayOffAmount());
				deposit.setNewMaturityAmount(fixedDepositForm.getEstimatePayOffAmount());
				deposit.setPaymentType(fixedDepositForm.getPaymentType());

			} else {
				deposit.setDepositCategory(Constants.REVERSEEMI);
				deposit.setReverseEmiCategory(fixedDepositForm.getReverseEmiCategory());
				deposit.setMaturityAmount(0d);
				deposit.setNewMaturityAmount(0d);
			}

			deposit.setDueDate(fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));
			deposit.setFlexiRate(fixedDepositForm.getFlexiInterest());
			deposit.setDepositCurrency(fixedDepositForm.getCurrency());

			// deposit.setInterestRate(fixedDepositForm.getFdCreditAmount());
			deposit.setModifiedInterestRate(fixedDepositForm.getFdCreditAmount());
			deposit.setLinkedAccountNumber(fixedDepositForm.getAccountNo());
			deposit.setCurrentBalance(Double.valueOf(deposit.getDepositAmount()));
			if(fixedDepositForm.getFdAmount() > 10000000)
			{	
				deposit.setApprovalStatus(Constants.PENDING);
				deposit.setComment(null);
			}			
			else
			{
				deposit.setApprovalStatus(Constants.APPROVED);
				deposit.setComment(Constants.APPROVED);
			}
			
			deposit.setPaymentMode(mop.getPaymentMode());
			deposit.setPaymentModeId(mopId);
			deposit.setPayOffInterestType(fixedDepositForm.getPayOffInterestType());
			deposit.setStatus(Constants.OPEN);
			deposit.setPayOffInterestType(fixedDepositForm.getPayOffInterestType());
			deposit.setStatus(Constants.OPEN);
			deposit = fdService.getMaturityAndTenureInformation(deposit, fixedDepositForm);
			if (deposit.getPaymentMode().equalsIgnoreCase("DD") || deposit.getPaymentMode().equalsIgnoreCase("Cheque")) {
				deposit.setClearanceStatus(Constants.WAITINGFORCLEARANCE);
			}
			deposit.setMaturityInstruction(fixedDepositForm.getMaturityInstruction());
			deposit.setCreatedDate(date);
			deposit.setModifiedDate(date);
			deposit.setMaturityDate(fixedDepositForm.getMaturityDate());
			deposit.setNewMaturityDate(fixedDepositForm.getMaturityDate());
			/* PAY OFF DATE CALCULATION */

			if (fixedDepositForm.getPayOffInterestType() != null && !fixedDepositForm.getPayOffInterestType().equals("")) {
				deposit.setPayOffDueDate(fixedDepositForm.getPayoffDate());
			}
	     if(fixedDepositForm.getCitizen().equalsIgnoreCase(Constants.NRI)){
	    	 
		     deposit.setPrimaryNRIAccountType(fixedDepositForm.getNriAccountType());
	      }
			int daysDiff = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(),
					fixedDepositForm.getMaturityDate()) + 1;
			
			deposit.setDepositAccountType(fixedDepositForm.getDepositAccountType());
			String depositClassification = fixedDepositForm.getDepositClassification();
			if(fixedDepositForm.getStatus().equals("Reverse EMI"))
				depositClassification = Constants.annuityDeposit;
			
//			Float interestRate = depositRateDAO.getInterestRate(customerDetails.getCategory(),
//					fixedDepositForm.getCurrency(), daysDiff, depositClassification, fixedDepositForm.getFdAmount());
			
			Float interestRate = calculationService.getDepositInterestRate(daysDiff, 
					customerDetails.getCategory(), fixedDepositForm.getCurrency(),  
					fixedDepositForm.getFdAmount(), depositClassification, 
					fixedDepositForm.getCitizen(), fixedDepositForm.getNriAccountType());

			deposit.setTransactionId(transactionId);
			deposit.setCreatedBy(customerDetails.getUserName());
			deposit.setInterestRate(interestRate);
			deposit.setModifiedInterestRate(interestRate);
			deposit.setGestationPeriod(fixedDepositForm.getGestationPeriod());
			
			
		/*	if(fixedDepositForm.getNriAccountType()!=null && fixedDepositForm.getNriAccountType()!=""){
				deposit.setPrimaryNRIAccountType(fixedDepositForm.getNriAccountType());	
				
			}*/
			if (taxSavingDeposit == 1) {
				deposit.setDepositClassification(Constants.taxSavingDeposit);

			}
			if (taxSavingDeposit == 0 && fixedDepositForm.getPaymentType().equals("One-Time")) {
				deposit.setDepositClassification(Constants.fixedDeposit);

			}
			if (taxSavingDeposit == 0 && !fixedDepositForm.getPaymentType().equals("One-Time")) {
				deposit.setDepositClassification(Constants.recurringDeposit);

			}
			if(fixedDepositForm.getStatus().equals("Reverse EMI"))
				deposit.setDepositClassification(Constants.annuityDeposit);
				
			
			if (fixedDepositForm.getEmiAmount() != null)
				deposit.setEmiAmount(fdService.round((double) fixedDepositForm.getEmiAmount(), 2));
			
			deposit.setIsMaturityDisbrsmntInLinkedAccount(fixedDepositForm.getIsMaturityDisbrsmntInLinkedAccount());
			deposit.setTaxSavingDeposit(fixedDepositForm.getTaxSavingDeposit());
			Deposit depositSaves = fixedDepositDAO.saveFD(deposit);
			
			
			List<DepositHolder> depositHolderList = new ArrayList<>();
			DepositHolder depositHolder = new DepositHolder();
			depositHolder.setContribution(100f);
			depositHolder.setCustomerId(Long.valueOf(fixedDepositForm.getId()));
			depositHolder.setDepositHolderStatus(Constants.PRIMARY);
			depositHolder.setDepositHolderCategory(fixedDepositForm.getCategory());
			depositHolder.setDepositId(depositSaves.getId());
			depositHolder.setPayOffAccountType(fixedDepositForm.getFdPayOffAccount());
			depositHolder.setInterestType(fixedDepositForm.getInterstPayType());
			depositHolder.setCitizen(customerDetails.getCitizen());
			
			///// on maturity		
			depositHolder.setIsMaturityDisbrsmntInSameBank(fixedDepositForm.getIsMaturityDisbrsmntInSameBank());
			
			depositHolder.setMaturityDisbrsmntAccHolderName(fixedDepositForm.getMaturityDisbrsmntAccHolderName());
			
			depositHolder.setMaturityDisbrsmntAccNumber(fixedDepositForm.getMaturityDisbrsmntAccNumber());
			
			depositHolder.setMaturityDisbrsmntTransferType(fixedDepositForm.getMaturityDisbrsmntTransferType());
			
			depositHolder.setMaturityDisbrsmntBankName(fixedDepositForm.getMaturityDisbrsmntBankName());

			depositHolder.setMaturityDisbrsmntBankIFSCCode(fixedDepositForm.getMaturityDisbrsmntBankIFSCCode());
			////
			
			
			if(customerDetails.getCitizen().equalsIgnoreCase(Constants.NRI))
				depositHolder.setNriAccountType(customerDetails.getNriAccountType());
			if (fixedDepositForm.getInterestPayAmount() != null) {
				depositHolder.setInterestAmt(fixedDepositForm.getInterestPayAmount());
				depositHolder.setEmiAmount(fdService.round((double) fixedDepositForm.getInterestPayAmount(), 2));
			}
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
					depositHolder.setEmiAmount(fdService.round((double) fixedDepositForm.getEmiAmount(), 2));
				}

			}

			depositHolderList.add(depositHolderNew);

			Set<DepositHolder> set = new HashSet<DepositHolder>();
			set.add(depositHolder);
			depositSaves.setDepositHolder(set);
	if(!fixedDepositForm.getNomineeName().equals("")){
			DepositHolderNominees nominee = new DepositHolderNominees();

			nominee.setNomineeName(fixedDepositForm.getNomineeName());
			nominee.setNomineeAge(fixedDepositForm.getNomineeAge());
			nominee.setNomineeRelationship(fixedDepositForm.getNomineeRelationShip());
			nominee.setNomineeAddress(fixedDepositForm.getNomineeAddress());
			nominee.setDepositHolderId(Long.valueOf(fixedDepositForm.getId()));
			nominee.setNomineeAadhar(fixedDepositForm.getNomineeAadhar());
			nominee.setNomineePan((fixedDepositForm.getNomineePan() != null)
					? fixedDepositForm.getNomineePan().toUpperCase() : fixedDepositForm.getNomineePan());
			int age = Integer.parseInt(fixedDepositForm.getNomineeAge());
			if (age < 18) {
				nominee.setGaurdianName(fixedDepositForm.getGuardianName());
				// nominee.setGaurdianAge(fixedDepositForm.getGuardianAge());
				nominee.setGaurdianAddress(fixedDepositForm.getGuardianAddress());
				nominee.setGaurdianRelation(fixedDepositForm.getGuardianRelationShip());
				nominee.setGaurdianAadhar(fixedDepositForm.getGaurdianAadhar());
				nominee.setGaurdianPan(fixedDepositForm.getGaurdianPan().toUpperCase());
			}

			nomineeDAO.saveNominee(nominee);
		}
			/* / Deduction from Linked account / */

			if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {
				AccountDetails accountDetails = accountDetailsDAO
						.findByAccountNo(fixedDepositForm.getDepositForm().getLinkedAccountNo());
				accountDetails.setAccountBalance(
						accountDetails.getAccountBalance() - Float.valueOf(fixedDepositForm.getDepositForm().getFdPay()));
				accountDetailsDAO.updateUserAccountDetails(accountDetails);
			}
			/*
			 * To add the beneficiary
			 */
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

			/* .........SAVING PAYMENT INFO...... */

			String fromAccountNo="";
			
			
			
			Payment payment = new Payment();
			payment.setDepositId(depositSaves.getId());
			payment.setAmountPaid(Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()));
			

			if (mop.getPaymentMode().equalsIgnoreCase("Cash"))
			{
				fromAccountNo = "";
			}
					
			if (mop.getPaymentMode().equalsIgnoreCase("DD")
					|| mop.getPaymentMode().equalsIgnoreCase("Cheque")) {

				payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
				payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
				payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
				payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());
				
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
				
				fromAccountNo = cardNo;

			}

			if (mop.getPaymentMode().equalsIgnoreCase("Net Banking")) {

				payment.setNameOnBankAccount(fixedDepositForm.getOtherName1());
				payment.setAccountNumber(fixedDepositForm.getOtherAccount1().toString());
				payment.setDepositAmount(fixedDepositForm.getFdAmount());
				payment.setBank(fixedDepositForm.getOtherBank1());

				if (fixedDepositForm.getFdPayType().equals("DifferentBank")) {
					payment.setTransferType(fixedDepositForm.getOtherPayTransfer1());
					payment.setIfscCode(fixedDepositForm.getOtherIFSC1());
					payment.setBank(fixedDepositForm.getOtherBank1());
				}
				
				fromAccountNo = fixedDepositForm.getOtherAccount1().toString();

			}
			if (mop.getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {

				payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getAccountType());
				payment.setLinkedAccNoForFundTransfer(fixedDepositForm.getAccountNo());
				
				fromAccountNo = fixedDepositForm.getDepositForm().getLinkedAccountNo();

			}

			payment.setDepositHolderId(depositHolderNew.getId());
			payment.setPaymentMadeByHolderIds(depositHolderNew.getId().toString());
			payment.setPaymentModeId(mopId);
			payment.setPaymentMode(mop.getPaymentMode());
			payment.setPaymentDate(date);
			payment.setTransactionId(transactionId);
			payment.setCreatedBy(customerDetails.getUserName());
			payment = paymentDAO.insertPayment(payment);

			calculationService.insertInPaymentDistribution(deposit, depositHolderList, Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()),
					payment.getId(),  Constants.PAYMENT, null, null, null, null, null, null, null);

			Double amountPaid = Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay());
			
			//Insert in DepositSummary and HolderwiseDepositSummary
			DepositSummary depositSummary = calculationService.upsertInDepositSummary(depositSaves, Constants.PAYMENT, amountPaid, null, null,
					null, null, depositHolderList, null, null, null);
			
			// Insert in Journal & Ledger
			//-----------------------------------------------------------
			ledgerService.insertJournal(depositSaves.getId(), customerDetails.getId(), DateService.getCurrentDate(),
					fromAccountNo, depositSaves.getAccountNumber(), Event.INTEREST_ADJUSTMENT.getValue(),
					amountPaid, mopId, depositSummary.getTotalPrincipal(), transactionId);
					
			//-----------------------------------------------------------

			
			/*
			 * / saving Interest table /
			 */ fixedDepositForm.setDepositId(depositSaves.getId());
			Date fromDate = DateService.getCurrentDate();

			if (!(fixedDepositForm.getStatus().equals("Reverse EMI"))) {
			
			}

			else {

				if (fixedDepositForm.getReverseEmiCategory().equalsIgnoreCase(Constants.fixedTenure)) {

				} else {
					Date gestationEndDate = DateService.addYear(fromDate, fixedDepositForm.getGestationPeriod());
					double emiFrequency = Math
							.floor(fixedDepositForm.getFdAmount() / fixedDepositForm.getInterestPayAmount());
					int emiTimes = (int) emiFrequency;

					List<Date> payoffDateList = null;
					if (DateService.getDaysBetweenTwoDates(fromDate, gestationEndDate) >= 0) {
						payoffDateList = fdService.getPayoffDatesForFixedAmountEmi(gestationEndDate,
								fixedDepositForm.getPayOffInterestType(), emiTimes); // Doubt
																						// on
																						// start
																						// date
					} else {
						payoffDateList = fdService.getPayoffDatesForFixedAmountEmi(fromDate,
								fixedDepositForm.getPayOffInterestType(), emiTimes);// Doubt
					}
				
				}
			}

			return depositSaves;
		}
		@Transactional

		public DepositModificationMaster editDepositFromCustomer(HolderForm depositHolderForm, Customer customer, EndUser currentLoggedInUser) throws CustomException {


		// deposit with changed value
		Deposit deposit = depositHolderForm.getDeposit();

		Long depositId = deposit.getId();
		Date dt = DateService.getCurrentDateTime();
		

		// Existing Deposit
		Deposit savedDeposit = fixedDepositDAO.findById(depositId);
		Float depositInterestRate = savedDeposit.getModifiedInterestRate() == null ? savedDeposit.getInterestRate()
				: savedDeposit.getModifiedInterestRate();
		String category = savedDeposit.getPrimaryCustomerCategory();
	
		// Three possibilities to edit
		// 1. Change the amount
		// 2. Change the tenure
		// 3. Change Payoff details

		// 1. Change the amount
		// ------------------------------------------------------
		Double depositAmount = deposit.getDepositAmount();
		// depositHolderForm.getEditDepositForm().getStatus() is taking the
		// value add/reduce/null
		// if anybody wont change any value then it will be null only
		if (depositHolderForm.getEditDepositForm().getStatus() != null
				&& depositHolderForm.getEditDepositForm().getStatus() != "") {
			if (depositHolderForm.getEditDepositForm().getStatus().equals("add")) {
				depositAmount = deposit.getDepositAmount() + depositHolderForm.getEditDepositForm().getAddAmount();
			}
			if (depositHolderForm.getEditDepositForm().getStatus().equals("reduce")) {
				depositAmount = deposit.getDepositAmount() - depositHolderForm.getEditDepositForm().getAddAmount();
			}
		}
		// ------------------------------------------------------

		// 2. Change the tenure
		// ------------------------------------------------------
		Date maturityDate = savedDeposit.getNewMaturityDate() == null ? savedDeposit.getMaturityDate()
				: savedDeposit.getNewMaturityDate();

		/************** previous tenure and tenure type *************/
		String tenureArray[] = deposit.getTenureType().split(",");
		//String tenureType = tenureArray[0];
		Integer tenure = deposit.getTenure();

		// Integer tenure = savedDeposit.getTenure();
		// String tenureType = savedDeposit.getTenureType();
		Boolean interestChanged = false;
		Float newInterestRate = depositInterestRate; //savedDeposit.getModifiedInterestRate() == null ? savedDeposit.getInterestRate()
				//: savedDeposit.getModifiedInterestRate();

		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);

		// depositHolderForm.getEditDepositForm().getStatusTenure() is taking
		// the value add/reduce/null
		// if anybody wont change any value then it will be null only
		Distribution lastInterestDistribution = null;
		if (depositHolderForm.getEditDepositForm().getStatusTenure() != null
				&& depositHolderForm.getEditDepositForm().getStatusTenure() != "") {
			// New Tenure
			maturityDate = depositHolderForm.getNewMaturityDate();
			tenure = DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate);
			//tenureType = "Day";

			String currency = deposit.getDepositCurrency();
			if (currency == null)
				currency = fixedDepositDAO.getDeposit(depositId).getDepositCurrency();

			//Customer customer = customerDAO.getById(fixedDepositForm.getId());
			category = calculationService.geCustomerActualCategory(customer);
			
			// 1. calculate the interest till date, it should be done 
		    // before entering new rate in deposit table
			// ------------------------------------------------------------------
			// This will be considered here as last distribution
//				Distribution lastInterestDistribution = fdService.calculateInterestForDepositEdit(savedDeposit,
//						depositHolderList, depositInterestRate);
			lastInterestDistribution = calculationService.calculateInterest(savedDeposit, depositHolderList, "", null);
			// ------------------------------------------------------------------
			
			// New Interest rate
			newInterestRate = calculationService.getDepositInterestRate(tenure, category, currency, depositAmount,
					savedDeposit.getDepositClassification(), savedDeposit.getPrimaryCitizen(), savedDeposit.getPrimaryNRIAccountType());

			if (depositInterestRate != newInterestRate) {
				interestChanged = true;
				savedDeposit.setModifiedInterestRate(newInterestRate);
				savedDeposit.setPrimaryCustomerCategory(category);
			}
		}
		// ------------------------------------------------------
		
		
		// Save to the deposit
		// ------------------------------------------------------		
		Date payOffDueDate = savedDeposit.getPayOffDueDate();
		String payOffInterestType = savedDeposit.getPayOffInterestType();

		if ((savedDeposit.getPayOffInterestType() == null && deposit.getPayOffInterestType() != null)
				|| (savedDeposit.getPayOffInterestType() != null
						&& !savedDeposit.getPayOffInterestType().equalsIgnoreCase(deposit.getPayOffInterestType()))) {


			if (deposit.getPayOffInterestType() != null)
				payOffDueDate = fdService.calculateInterestPayOffDueDateForPayOff(deposit.getPayOffInterestType(),
						deposit.getNewMaturityDate(), savedDeposit.getCreatedDate());
			else
				payOffDueDate = null;
		}
		

		// now if user changed stoppayment value from 1 to 0
		// that means if he previously requested for stop payment but
		// currently wants to contnue the premium amount
		// then we have to change the due date from here. Because
		// due date is earlier date lying in the database.
		// Change the DueDate
		Date dueDate = savedDeposit.getDueDate();
		if (!(deposit.getStopPayment() != null && deposit.getStopPayment() == 1)
				&& (savedDeposit.getStopPayment() != null && savedDeposit.getStopPayment() == 1)) {
			int deductionDay = DateService.getDayOfMonth(savedDeposit.getDueDate());
			dueDate = fdService.calculateDueDateOnContinuePayment(savedDeposit.getPaymentType(), deductionDay);

		}

		// Set Stop Payment in Deposit also
		if (deposit.getStopPayment() != null && deposit.getStopPayment() == 1)
			savedDeposit.setStopPayment(1);
		else
			savedDeposit.setStopPayment(null);

		savedDeposit.setDueDate(dueDate);
		savedDeposit.setPayOffInterestType(payOffInterestType);
		savedDeposit.setPayOffDueDate(payOffDueDate);
		savedDeposit.setModifiedDate(DateService.getCurrentDateTime());
		savedDeposit.setDepositAmount(depositAmount);
		if (interestChanged == true)
			savedDeposit.setModifiedInterestRate(newInterestRate);
		savedDeposit.setNewMaturityDate(maturityDate);
		if (deposit.getPaymentMode().equalsIgnoreCase(Constants.SAVINGACCOUNTDEBIT)) {
			savedDeposit.setLinkedAccountNumber(deposit.getLinkedAccountNumber());
		}
		savedDeposit.setModifiedInterestRate(newInterestRate);
		savedDeposit.setPrimaryCustomerCategory(category);

		//List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositId);

		Set<DepositHolder> set = new HashSet<DepositHolder>();
		set.addAll(depositHolderList);
		savedDeposit.setDepositHolder(set);
		savedDeposit = fixedDepositDAO.updateFD(savedDeposit);
		
		// ------------------------------------------------------

		// 3. Change Payoff details if any
		// Enter in Deposit Modification master and Detail table
		// ------------------------------------------------------
		// Payoff details will come from the holder table
		// and it will go to depositmodification table
		String randomNumber = DateService.getRandomNumBasedOnDate();
		String transactionId = fdService.generateRandomString();

		// Save to DepositModificationMaster
		DepositModificationMaster depositModificationMaster = new DepositModificationMaster();
		depositModificationMaster.setMaturityDate(maturityDate);
		depositModificationMaster.setDepositAmount(depositAmount);
		depositModificationMaster.setDepositId(deposit.getId());
		depositModificationMaster.setInterestRate(newInterestRate);
		depositModificationMaster.setPaymentMode(deposit.getPaymentMode());
		depositModificationMaster.setPaymentType(deposit.getPaymentType());
		depositModificationMaster.setTenure(tenure);
		//depositModificationMaster.setTenureType(tenureType);
		depositModificationMaster.setModificationNo(randomNumber);
		depositModificationMaster.setModifiedDate(dt);
		depositModificationMaster.setTransactionId(transactionId);
		if(depositHolderForm.getEditDepositForm().getDepositConversion()!= null && depositHolderForm.getEditDepositForm().getDepositConversion().equalsIgnoreCase(Constants.recurringDeposit)){
			depositModificationMaster.setDeductionDay(depositHolderForm.getEditDepositForm().getDeductionDay());
			}
		depositModificationMaster.setDepositConversion(depositHolderForm.getEditDepositForm().getDepositConversion());
		
		int isConsideredForPenalty = 0;
		if(depositHolderForm.getDepositChange().equalsIgnoreCase("ConvertDeposit") || depositHolderForm.getDepositChange().equalsIgnoreCase("tenureChange") || depositHolderForm.getDepositChange().equalsIgnoreCase("amountChange"))
		{
			isConsideredForPenalty = 1;
		}
		depositModificationMaster.setIsConideredForPenalty(isConsideredForPenalty); 
		// depositModification.setMaturityAmount(maturityDate);
		depositModificationMaster = depositModificationDAO.saveDepositModificationMaster(depositModificationMaster);

		for (int i = 0; i < depositHolderForm.getDepositHolderList().size(); i++) {

			DepositHolder holder = depositHolderForm.getDepositHolderList().get(i);

			// Save in Deposit Modification
			DepositModification depositModification = new DepositModification();
			depositModification.setMaturityDate(maturityDate);
			depositModification.setDepositAmount(depositAmount);
			depositModification.setDepositHolderId(holder.getId());
			depositModification.setDepositId(deposit.getId());
			depositModification.setInterestRate(newInterestRate);
			depositModification.setPaymentMode(deposit.getPaymentMode());
			depositModification.setPaymentType(deposit.getPaymentType());
			depositModification.setTenure(tenure);
			//depositModification.setTenureType(tenureType);
			depositModification.setDepositConversion(depositHolderForm.getEditDepositForm().getDepositConversion());
			if(depositHolderForm.getEditDepositForm().getDepositConversion()!= null && depositHolderForm.getEditDepositForm().getDepositConversion().equalsIgnoreCase(Constants.recurringDeposit)){
				depositModificationMaster.setDeductionDay(depositHolderForm.getEditDepositForm().getDeductionDay());
				}
			// This is for payOf details
			if (holder.getInterestType() != null && (!holder.getInterestType().equals(""))) {

				depositModification.setPayOffInterestType(holder.getInterestType()); // part/percent
				depositModification.setPayOffAccountType(holder.getPayOffAccountType()); // saving/fd
				depositModification.setPayOffNameOnBankAccount(holder.getNameOnBankAccount());
				depositModification.setPayOffAccountNumber(holder.getAccountNumber());

				if (holder.getPayOffAccountType() != null && !holder.getPayOffAccountType().equals("Saving Account")) {
					depositModification.setPayOffBankIFSCCode(holder.getIfscCode());
					depositModification.setPayOffBankName(holder.getBankName());
					depositModification.setPayOffTransferType(holder.getTransferType());
				}
			}

			depositModification.setPayOffType(deposit.getPayOffInterestType()); // monthly/quarterly
			depositModification.setPayOffInterestPercent(holder.getInterestPercent());
			depositModification.setPayOffInterestAmt(holder.getInterestAmt());

			depositModification.setModifiedDate(dt);
			depositModification.setModificationNo(randomNumber);
			depositModification.setTransactionId(transactionId);
			depositModification.setModifiedBy(customer.getUserName());

			if (deposit.getStopPayment() != null && deposit.getStopPayment() == 1)
				depositModification.setStopPayment(1);
			else
				depositModification.setStopPayment(0);

			depositModification = depositModificationDAO.saveDepositHolder(depositModification);

		}

		// ------------------------------------------------------

		// String transactionId = fdService.generateRandomString();
		String userName = customer.getUserName(); // setCurrentLoggedUserName();
		Distribution lastBaseLine = null;
		if (depositHolderForm.getEditDepositForm().getStatusTenure() != "") {
			/******
			 * adjustment in Interest, Distribution and
			 * DepositHolderWiseDistribution table
			 ********/

			Date lastAdjustmentDate = deposit.getModifiedDate();

			// Steps
			// 1. Interest calculation till date
			// 2. Interest Adjustment for reducing tenure
			// 3. Update the new interest rate in deposit table
			// 4. Update the new interest rate in modification table??

			// 1. Interest calculation till date
			// ------------------------------------------------------------------
			// This will be considered here as last distribution
//			Distribution lastInterestDistribution = fdService.calculateInterestForDepositEdit(savedDeposit,
//					depositHolderList, depositInterestRate);
//			Distribution lastInterestDistribution = calculationService.calculateInterest(savedDeposit, depositHolderList, "");
			// ------------------------------------------------------------------

			// 2. Interest Adjustment and penalty for the Withdraw
			// ----------------------------------------------------------------------
			// Calculate the interest to adjust
			// Insert an interest adjustment for the withdraw in Distribution
			// table
			// Insert the adjustment in Interest table
			// Float interestRateForAdjustment = // Getting the duration to
			// adjust

			// Distribution lastAdjustment =
			// paymentDistributionDAO.getLastAdjustment(depositId);
			
			// The last Base line will be either last adjustment (i.e for TenereChange/Withdraw/DepositConversion
			lastBaseLine = paymentDistributionDAO.getLastBaseLineForTenureChange(depositId);

			Float interestRateForAdjustment = 0f;
			if (newInterestRate != depositInterestRate) {
				Date fromDate = lastBaseLine == null ? DateService.getDate(deposit.getCreatedDate())
						: lastBaseLine.getDistributionDate();
				Date toDate = DateService.getCurrentDate();
				int daysDiff = DateService.getDaysBetweenTwoDates(fromDate, toDate) + 1;

//				interestRateForAdjustment = depositRateDAO.getInterestRate(customer.getCategory(),
//						savedDeposit.getDepositCurrency(), daysDiff, savedDeposit.getDepositClassification(), deposit.getDepositAmount());
				
				interestRateForAdjustment = calculationService.getDepositInterestRate(daysDiff, 
						savedDeposit.getPrimaryCustomerCategory(), savedDeposit.getDepositCurrency(),  
						savedDeposit.getDepositAmount(), savedDeposit.getDepositClassification(), 
						savedDeposit.getPrimaryCitizen(), savedDeposit.getPrimaryNRIAccountType());
				
				// Adjust the Interest
				String action = "Interest Adjustment For Tenure Change";
				lastBaseLine = fdService.adjustInterestForTenureChangeOrConversion(action, savedDeposit, lastInterestDistribution,
						depositHolderList, lastBaseLine, interestRateForAdjustment);
			}
			// ----------------------------------------------------------------------

		}

		// if (tenureChange || amountChange || paymentTypeChange) {

		// delete from interest saved after the current date
		if(depositHolderForm.getDepositChange().equalsIgnoreCase("ConvertDeposit"))
		{
			//Step1
			// 1. Find the balance of source deposit
			// 2. Create a new deposit with the balance from source deposit and 
						// with all the existing deposit information like 
						// deposit holder, payoff information, nominee etc
			// 3. close the source deposit
			// 4. Transfer the change in Saving bank Account

			// 1. Close the Source Deposit, find the balance of source deposit
			//------------------------------------------------------
			HashMap<String, Double> sourceDepositBalance = closeDepositForDepositConversion(savedDeposit);
				
			//HashMap<String, Double> sourceDepositBalance = fdService.convertDeposit(savedDeposit);
			Double newDepositAmount = sourceDepositBalance.get(Constants.NewDepositAmount);
			Double transferAmtToSavingAccount = sourceDepositBalance.get(Constants.TransferAmountToSavingAccount);
			//Float interestRateForNewDeposit = Float.parseFloat(sourceDepositBalance.get(Constants.InterestRate).toString());
			//------------------------------------------------------
			
			// 2 Create a new Deposit with the balance of source
			//------------------------------------------------------
			// New depositDeductionDay and PaymentType is not coming
//			String newDepositPaymentType = depositHolderForm.getNewPaymentType();  // Monthly/Quarterly/....
//			String newDepositConversionType = depositHolderForm.getDepositConversion(); // Recurring/Regular Deposit
//			String newDepositTenureType = depositHolderForm.getNewDepositTenureType();  //Days/Month/Year		
//			Integer newDepositTenure = depositHolderForm.getNewDepositTenure();
//			Integer newDepositDayValue = depositHolderForm.getNewDepositDaysValue(); // If Year, then Day value
//			Integer newDepositDeductionDay = depositHolderForm.getNewDepositDeductionDay();
			
			//Pending: Find the maturity date
			
			Deposit newDeposit = this.convertToNewDeposit(savedDeposit, newDepositAmount, currentLoggedInUser, 
					depositHolderForm.getDepositConversion(), depositHolderForm.getNewDepositTenureType(), 
					depositHolderForm.getNewDepositTenure(), depositHolderForm.getNewDepositDaysValue(),
					depositHolderForm.getNewDepositDeductionDay(), depositHolderForm.getNewPaymentType());
			
			
			//------------------------------------------------------
	
			// 4. Transfer the change in Saving bank Account
			//------------------------------------------------------
			Long primaryHolderId = calculationService.getPrimaryHolderId(depositHolderList);
			List<AccountDetails> accountList = accountDetailsDAO
					.findCurrentSavingByCustId(primaryHolderId);
			if (accountList.size() > 0) {
				accountList.get(0).setAccountBalance(accountList.get(0).getAccountBalance() + 
						(transferAmtToSavingAccount == null? 0d : transferAmtToSavingAccount));
				//depositHolder.setIsAmountTransferredOnMaturity(1);
				accountDetailsDAO.updateUserAccountDetails(accountList.get(0));
			
				String accountType = accountList.get(0).getAccountType().contains("Saving")? "Savings Account" : 
					accountList.get(0).getAccountType().contains("Current")? "Current Account" : "Deposit Account";
				
				// Insert in Journal & Ledger
				//-----------------------------------------------------------	
//				ledgerService.insertJournalLedger(savedDeposit.getId(),customer.getId(), DateService.getCurrentDate(), 
//						savedDeposit.getAccountNumber(), "Deposit Account", accountList.get(0).getAccountNo(), 
//						accountType, "Convert Deposit-Change Update", transferAmtToSavingAccount, "Internal Transfer",
//						0d);		
				//-----------------------------------------------------------
			}
			//------------------------------------------------------
		}
		
		return depositModificationMaster;

	}

	@Transactional
	public String makePaymentFromCustomer(DepositForm depositForm, Customer customerDetails) {
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(depositForm.getDepositId());
		
		String transactionId = fdService.makePayment(depositHolderList, depositForm.getDepositAmount(),
				depositForm.getPaymentMode(), null, null, null, depositForm.getCardNo(), depositForm.getCardType(),
				depositForm.getCvv(), depositForm.getExpiryDate(), depositForm.getTopUp(),
				customerDetails.getUserName(), customerDetails.getEmail());

		// delete from interest saved after the current date
		//Interest lastInterest = interestDAO.deleteByDepositIdAndDate(depositForm.getDepositId());
		Deposit deposit = fixedDepositDAO.getDeposit(depositForm.getDepositId());

		// set required parameters for interest calculation in fixedDepositForm
		//fixedDepositForm.setDepositId(depositForm.getDepositId());
		//fixedDepositForm.setCategory(customerDetails.getCategory());
		//fixedDepositForm = fdService.setParametersForProjectedInterest(fixedDepositForm);

		// Save the projected interests
		//List<Interest> interestList = null;
		if (deposit.getDepositCategory() != null && deposit.getDepositCategory().equals(Constants.REVERSEEMI)) {
			// If the deposit in the Annuity deposit/ Reverse EMI then
			// change the emi amount as well as interest projection
			// get the deposit
			Integer gestationPeriod = deposit.getGestationPeriod();
			Date gestationEndDate = DateService.generateYearsDate(deposit.getCreatedDate(), gestationPeriod);

			Distribution lastDistribution = paymentDistributionDAO.getLastPaymentDistribution(deposit.getId());
			Double uncalculatedInterestAmount = fdService.getUncalculatedInterestAmount(deposit);

//			String interestCalculationBasis = Constants.MONTHLY;
//			BankConfiguration bankConfiguration = ratesDAO.getBankConfiguration(customerDetails.getCitizen(), 
//					customerDetails.getNriAccountType());
//			if(bankConfiguration != null)
//				interestCalculationBasis = bankConfiguration.getInterestCalculationBasis();
			ProductConfiguration productConfiguration = productConfigurationDAO.findById(deposit.getProductConfigurationId());
			String interestCalculationBasis  = productConfiguration.getInterestCalculationBasis();
			
			// Calculate the future EMI
			Double emiAmount = fdService.calculateEMIOnTopUp(lastDistribution.getTotalBalance(), gestationEndDate,
					deposit.getModifiedInterestRate(), DateService.getCurrentDate(), deposit.getNewMaturityDate(),
					deposit.getPayOffInterestType(), uncalculatedInterestAmount, interestCalculationBasis);

			
			// Set emi amount in depositholder as well as deposit table
			for (int i = 0; i < depositHolderList.size(); i++) {
				depositHolderList.get(i)
						.setEmiAmount(depositHolderList.get(i).getEmiAmount() / deposit.getEmiAmount() * emiAmount);
				depositHolderDAO.updateDepositHolder(depositHolderList.get(i));
			}

			deposit.setEmiAmount(emiAmount);
			fixedDepositDAO.updateFD(deposit);
		}
		if (deposit.getReverseEmiCategory() != null
				&& deposit.getReverseEmiCategory().equalsIgnoreCase("Fixed Amount")) {
			double totalDepositAmount = deposit.getDepositAmount() + depositForm.getDepositAmount();
			float rateOfInterest = deposit.getInterestRate();
			double emiAmount = depositHolderList.get(0).getEmiAmount();
			float tenure = fdService.calculateEmiTenure(totalDepositAmount, emiAmount, rateOfInterest);

			int daysTenure = (int) (tenure * 30);

			Date maturityDate = DateService.generateDaysDate(deposit.getCreatedDate(), daysTenure);
			deposit.setTenure(daysTenure);
			deposit.setNewMaturityDate(maturityDate);
			deposit.setDepositAmount(totalDepositAmount);
			
			fixedDepositDAO.updateFD(deposit);
		}

		
		return transactionId;
	}

	public List<HolderForm> getAllDepositByCustomerIdDepositIdAndAccountNo(Long customerId, Long depositId,
			String accountNumber) {

		List<HolderForm> depositHolderList = fixedDepositDAO.getByCustomerIdDepositIdAndAccountNumber(customerId,
				depositId, accountNumber);

		if (depositHolderList != null && depositHolderList.size() > 0) {

			for (int i = 0; i < depositHolderList.size(); i++) {

				Deposit deposit = depositHolderList.get(i).getDeposit();
				depositHolderList.get(i).setDepositId(deposit.getId());
				DepositModification lastModification = depositModificationDAO.getLastByDepositId(deposit.getId());

				if (lastModification != null) {

					deposit.setDepositAmount(lastModification.getDepositAmount());
					deposit.setMaturityDate(lastModification.getMaturityDate());
					deposit.setPaymentMode(lastModification.getPaymentMode());
					deposit.setPaymentType(lastModification.getPaymentType());
					deposit.setPayOffInterestType(lastModification.getPayOffType());
					deposit.setTenure(lastModification.getTenure());
					deposit.setTenureType(lastModification.getTenureType());
					deposit.setStopPayment(lastModification.getStopPayment());

					/* / skipping payoff setters / */

				}
			}
		}

		return depositHolderList;
	}
	
  private Deposit convertToNewDeposit(Deposit sourceDeposit, Double depositAmount, EndUser user, 
			String depositClassification, String tenureType, Integer tenure, Integer tenureInDaysForYearType,
			Integer deductionDay, String paymentType)
	{
		Deposit deposit = new Deposit();
		Date date = DateService.getCurrentDateTime();
		Integer tenureVal = tenure;
		if(tenureVal ==null)
			tenureVal = 0 ;
		Date maturityDate = null;
		
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPayment(Constants.FUNDTRANSFER);
		// Get the Maturity Information
		//---------------------------------------------------------
		if (tenureType.equalsIgnoreCase("Month")) {
			maturityDate = DateService.generateMonthsDate(DateService.getCurrentDate(), tenureVal);
			deposit.setTenure(tenureVal);
			deposit.setTenureType(tenureType);
			
		} else if (tenureType.equalsIgnoreCase("Year")) {
			maturityDate = DateService.generateYearsDate(DateService.getCurrentDate(), tenureVal);
			int day = 0;
			if (tenureInDaysForYearType != null)
			{
				maturityDate = DateService.addDays(maturityDate, day+1);
				tenureVal = DateService.getDaysBetweenTwoDates(DateService.loginDate, maturityDate);
				deposit.setTenure(tenureVal);
				deposit.setTenureType("Days");				
			}
			else
			{
				deposit.setTenure(tenureVal);
				deposit.setTenureType(tenureType);
			}
		
		} else {
			maturityDate = DateService.generateDaysDate(DateService.getCurrentDate(), tenureVal);
			deposit.setTenure(tenureVal);
			deposit.setTenureType(tenureType);
			
		}

		deposit.setMaturityDate(maturityDate);
		deposit.setNewMaturityDate(maturityDate);
		
	
		//---------------------------------------------------------
		
			
        deposit.setPrimaryNRIAccountType(sourceDeposit.getNriAccountType());
        deposit.setPrimaryCitizen(sourceDeposit.getPrimaryCitizen());
        deposit.setPrimaryCustomerCategory(sourceDeposit.getPrimaryCustomerCategory());
		deposit.setAccountNumber(fdService.generateRandomString());
		deposit.setDepositAmount(depositAmount);
		deposit.setDepositType(sourceDeposit.getDepositType());
		deposit.setDepositCurrency(sourceDeposit.getDepositCurrency());
		deposit.setFlexiRate("Yes");
//		deposit.setInterestRate(interestRate);
//		deposit.setModifiedInterestRate(interestRate);
//		
		
		deposit.setLinkedAccountNumber(sourceDeposit.getLinkedAccountNumber());
	
		// Double value = FDService.round(fixedDepositForm.getEstimatePayOffAmount(), 2);
		deposit.setCurrentBalance(depositAmount);
		deposit.setPaymentMode(sourceDeposit.getPaymentMode());
		deposit.setPaymentModeId(sourceDeposit.getPaymentModeId());
		
		if(deductionDay != null)
			deposit.setDueDate(this.getDueDateForConvertedDeposit(deductionDay, paymentType));
		//deposit.setDueDate(fdService.calculateDueDate(fixedDepositForm, fixedDepositForm.getDeductionDay()));
		
		
		deposit.setPaymentType(paymentType);
		deposit.setPayOffInterestType(sourceDeposit.getPayOffInterestType());
		deposit.setStatus(Constants.OPEN);
		
		deposit.setCreatedDate(date);
		deposit.setModifiedDate(date);
		deposit.setApprovalStatus(Constants.APPROVED);
		deposit.setDepositClassification(depositClassification);
		deposit.setTaxSavingDeposit(sourceDeposit.getTaxSavingDeposit());
		deposit.setDeductionDay(deductionDay);
		
		/*/ / PAY OFF DATE CALCULATION / /*/
		if (sourceDeposit.getPayOffInterestType() != null && !sourceDeposit.getPayOffInterestType().equals("")) {
			deposit.setPayOffDueDate(sourceDeposit.getPayOffDueDate());
		}

		deposit.setTransactionId(fdService.generateRandomString());
		deposit.setCreatedBy(user.getUserName());
		deposit.setMaturityInstruction(sourceDeposit.getMaturityInstruction());
		
		Integer daysDiff = DateService.getDaysBetweenTwoDates(date, maturityDate) + 1;
		Float interestRate = calculationService.getDepositInterestRate(daysDiff, sourceDeposit.getPrimaryCustomerCategory(),
				sourceDeposit.getDepositCurrency(),depositAmount, depositClassification,
				sourceDeposit.getPrimaryCitizen(), sourceDeposit.getNriAccountType());
		
		deposit.setInterestRate(interestRate);
		deposit.setModifiedInterestRate(interestRate);
		
		Deposit depositSaves = fixedDepositDAO.saveFD(deposit);

		
		List<DepositHolder> depositHolderListSourceDeposit = depositHolderDAO.getDepositHolders(sourceDeposit.getId());
		List<DepositHolder> depositHolderList = new ArrayList<>();
		Long primaryHolderId = 0l;
		for(int i=0; i<depositHolderListSourceDeposit.size(); i++)
		{
			// Save Deposit Holder
			//-------------------------------------------------------------
			DepositHolder depositHolder = new DepositHolder();
			depositHolder.setContribution(depositHolderListSourceDeposit.get(i).getContribution());
			depositHolder.setCustomerId(depositHolderListSourceDeposit.get(i).getCustomerId());
			depositHolder.setDepositId(depositSaves.getId());
			depositHolder.setDepositHolderStatus(depositHolderListSourceDeposit.get(i).getDepositHolderStatus());
			
			// Auto Age calculation for Conversion
			Customer customer = customerDAO.getById(depositHolder.getCustomerId());
			String category = calculationService.geCustomerActualCategory(customer);
			depositHolder.setDepositHolderCategory(category);
			
			// payOff Information
			if (sourceDeposit.getPayOffInterestType() != null) {
				depositHolder.setNameOnBankAccount(depositHolderListSourceDeposit.get(i).getNameOnBankAccount());
				depositHolder.setAccountNumber(depositHolderListSourceDeposit.get(i).getAccountNumber());
				depositHolder.setPayOffAccountType(depositHolderListSourceDeposit.get(i).getPayOffAccountType());
				depositHolder.setTransferType(depositHolderListSourceDeposit.get(i).getTransferType());
				depositHolder.setBankName(depositHolderListSourceDeposit.get(i).getBankName());
				depositHolder.setIfscCode(depositHolderListSourceDeposit.get(i).getIfscCode());

	
				depositHolder.setInterestType(depositHolderListSourceDeposit.get(i).getInterestType());
				depositHolder.setInterestPercent(depositHolderListSourceDeposit.get(i).getInterestPercent());
				depositHolder.setInterestAmt(depositHolderListSourceDeposit.get(i).getInterestAmt());	
			}
			
			
			DepositHolder depositHolderNew = depositHolderDAO.saveDepositHolder(depositHolder);
			
			if(depositHolderNew.getDepositHolderStatus().equalsIgnoreCase("Primary"))
				primaryHolderId = depositHolderNew.getId();
			
			depositHolderList.add(depositHolderNew);
			//------------------------------------------------------------------
			
			// Get the holder's nominee and save for new deposit holder
			//------------------------------------------------------------------
			DepositHolderNominees nomineeSourceDeposit = nomineeDAO.accountHoderNominee(depositHolderListSourceDeposit.get(i).getId());
			
			DepositHolderNominees nominee = new DepositHolderNominees();
			
			nominee.setNomineeName(nomineeSourceDeposit.getNomineeName());
			nominee.setNomineeAge(nomineeSourceDeposit.getNomineeAge());
			nominee.setNomineeRelationship(nomineeSourceDeposit.getNomineeRelationship());
			nominee.setNomineeAddress(nomineeSourceDeposit.getNomineeAddress());
			nominee.setDepositHolderId(Long.valueOf(depositHolderNew.getId()));
			nominee.setNomineeAadhar(nomineeSourceDeposit.getNomineeAadhar());
			nominee.setNomineePan(nomineeSourceDeposit.getNomineePan());
			nominee.setNomineeAge(nomineeSourceDeposit.getNomineeAge());
			nominee.setGaurdianName(nomineeSourceDeposit.getGaurdianName());
			nominee.setGaurdianAddress(nomineeSourceDeposit.getGaurdianAddress());
			nominee.setGaurdianRelation(nomineeSourceDeposit.getGaurdianRelation());
			nominee.setGaurdianAge(nomineeSourceDeposit.getGaurdianAge());
				// nominee.setGaurdianAadhar(fixedDepositForm.getGaurdianAadhar());
				// nominee.setGaurdianPan(fixedDepositForm.getGaurdianPan().toUpperCase());
			nomineeDAO.saveNominee(nominee);
			//------------------------------------------------------------------
			
		}
		
		Set<DepositHolder> set = new HashSet<DepositHolder>();
		set.addAll(depositHolderList);
		depositSaves.setDepositHolder(set);
		depositSaves = fixedDepositDAO.updateFD(depositSaves);

		
		// Save the payment
		//------------------------------------------------------------------
		Payment payment = new Payment();

		//Double amountPaid = FDService.round(Double.parseDouble(fixedDepositForm.getDepositForm().getFdPay()),2);
		payment.setAmountPaid(depositAmount);
		payment.setDepositId(depositSaves.getId());
		
		payment.setLinkedAccTypeForFundTransfer("Deposit");
		payment.setLinkedAccNoForFundTransfer(sourceDeposit.getAccountNumber());

		//payment.setTransferType(fixedDepositForm.getDepositForm().getTransferType());
		
		
//		if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("DD")
//				|| fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("Cheque")) {
//
//			payment.setChequeDDdate(fixedDepositForm.getDepositForm().getChequeDate());
//			payment.setChequeDDNumber(fixedDepositForm.getDepositForm().getChequeNo());
//			payment.setBank(fixedDepositForm.getDepositForm().getChequeBank());
//			payment.setBranch(fixedDepositForm.getDepositForm().getChequeBranch());
//		}
//		if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("Card Payment")) {
//
//			String[] cardNoArray = fixedDepositForm.getDepositForm().getCardNo().split("-");
//			String cardNo = "";
//			for (int i = 0; i < cardNoArray.length; i++) {
//				cardNo = cardNo + cardNoArray[i];
//			}
//			payment.setCardNo(Long.valueOf(cardNo));
//			payment.setCardType(fixedDepositForm.getDepositForm().getCardType());
//
//			payment.setCardCvv(fixedDepositForm.getDepositForm().getCvv());
//			payment.setCardExpiryDate(fixedDepositForm.getDepositForm().getExpiryDate());
//
//		}

//		if (fixedDepositForm.getDepositForm().getPaymentMode().equalsIgnoreCase("Net Banking")) {
//
//			payment.setLinkedAccTypeForFundTransfer(fixedDepositForm.getDepositForm().getAccountType());
//			payment.setLinkedAccNoForFundTransfer(fixedDepositForm.getDepositForm().getLinkedAccountNo());
//			payment.setTransferType(fixedDepositForm.getDepositForm().getTransferType());
//
//		}

		payment.setDepositHolderId(primaryHolderId);
 
		payment.setPaymentModeId(mop.getId());
		payment.setPaymentMode(mop.getPaymentMode());
		payment.setPaymentDate(date);
		payment.setTransactionId(fdService.generateRandomString());
		payment.setCreatedBy(user.getUserName());
		payment = paymentDAO.insertPayment(payment);

		// Insert in Distribution and holderWiseDistribution
		calculationService.insertInPaymentDistribution(depositSaves, depositHolderList, depositAmount, payment.getId(), Constants.PAYMENT,
				null, null, null, null, null, null, null);
		
		//Insert in DepositSummary and HolderwiseDepositSummary
		DepositSummary depositSummary = calculationService.upsertInDepositSummary(depositSaves, Constants.PAYMENT, depositAmount, null, null,
				null, null, depositHolderList, null, null, null);
		
		// Insert in Journal & Ledger
		//-----------------------------------------------------------	
		//ToDo:
//		ledgerService.insertJournalLedger(depositSaves.getId(),depositHolderListSourceDeposit != null ?depositHolderListSourceDeposit.get(0).getCustomerId():null, DateService.getCurrentDate(), 
//				sourceDeposit.getAccountNumber(), "Deposit Account", depositSaves.getAccountNumber(), "Deposit Account", 
//				"Payment", depositAmount, "Internal Transfer",
//				depositSummary.getTotalPrincipal());		
		//-----------------------------------------------------------

		return depositSaves;
	}

	
	private Date getDueDateForConvertedDeposit(Integer newDepositDeductionDay, String newDepositPaymentType) {
	

//		DepositModification modification = depositModificationDAO.getLastByDepositId(sourceDepositId);
//		if (modification == null) {
//			modification = new DepositModification();
//			modification.setPaymentType(deposit.getPaymentType());
//		}

		Date dueDate = DateService.getCurrentDate();
		
		Integer monthsToAdd = 0;
		if (newDepositPaymentType.equalsIgnoreCase(Constants.MONTHLY)) {
			monthsToAdd = 1;
		} else if (newDepositPaymentType.equalsIgnoreCase(Constants.QUARTERLY)) {
			monthsToAdd = 3;
		} else if (newDepositPaymentType.equalsIgnoreCase(Constants.FULLYEARLY)
				|| newDepositPaymentType.equalsIgnoreCase(Constants.ANNUALLY))
		{
			monthsToAdd = 12;
		}
		else if (newDepositPaymentType.equalsIgnoreCase(Constants.HALFYEARLY))
		{
			monthsToAdd = 6;
		}

		dueDate = DateService.addDays(DateService.generateMonthsDate(dueDate, monthsToAdd), 1);
		if(newDepositDeductionDay!=null)
			dueDate = DateService.setDate(dueDate, newDepositDeductionDay);
		
		return dueDate;
	}
	

	public HashMap<String, Double> closeDepositForDepositConversion(Deposit deposit) throws CustomException {

				
			Long depositId = deposit.getId();
			List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
			Customer customer = customerDAO.getById(fdService.getPrimaryHolderId(depositHolderList));
			
			Date createdDate = deposit.getCreatedDate();
			
//				Steps:
//					1. Calculate the Interest till today with existing interest rate
//	                2. Compound the Interest
//	  			    3. Deduct if any Previous Penalty Due
//					4. Find out the interest from the last base line to current date
//					5. Find out the interest amount that needs to adjust, customer will lose the amount
//				    6. Find the Balance Amount (multiple of 100) to find out the new Deposit Amount
//					7. Change will go to the Saving bank account
			
				
				
			// 1. Calculate the Interest till today with existing interest rate
			//-------------------------------------------
			// Calculate the Fixed And Variable Interest with existing interest rate
			Distribution distribution = calculationService.calculateInterest(deposit, depositHolderList, "", null);
			
			Double totalFixedInterest = distribution.getFixedInterest();
			Double totalVariableInterest = distribution.getVariableInterest();
			
			totalFixedInterest = totalFixedInterest == null? 0d: totalFixedInterest;
			totalVariableInterest = totalVariableInterest == null? 0d: totalVariableInterest;
			// Find the Total Interest Amount
			Double totalInterest = calculationService.round(totalFixedInterest + totalVariableInterest, 2);		
			//-------------------------------------------

			// 2. Compound the Interest i.e find the in-hand interest
			// add the inhand interest to the interest calculated till date
			//-------------------------------------------	
			DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(depositId);
			
			// Getting in Hand Interest which is not compounded
			Double fixedInHandInterest = depositSummary.getTotalFixedInterestInHand();
			Double variableInHandInterest = depositSummary.getTotalVariableInterestInHand();
			
			totalInterest = totalInterest + ((fixedInHandInterest == 0? 0d :fixedInHandInterest) + (variableInHandInterest == 0? 0d :variableInHandInterest));
			
			distribution = calculationService.compoundInterestDepositWise(deposit);
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
			
			if(prevPenaltyDue>0)
			{
				// Insert penalty in PaymentDistribution and Holderwise PaymentDistribution
				distribution = calculationService.insertInPaymentDistribution(deposit, depositHolderList, null, null, 
						Constants.PENALTY1, null, null, null, null, null, null, prevPenaltyDue);
				
				// Update in DepositSummary and DepositHolderWiseSummary
				calculationService.upsertInDepositSummary(deposit, Constants.PENALTY1, null, null, null, null, null, depositHolderList, null, null, prevPenaltyDue);
			}
			// -----------------------------------------
			

			// Insert in Journal & Ledger
			//-----------------------------------------------------------
//			ledgerService.insertJournalLedger(deposit.getId(),customer.getId(), DateService.getCurrentDate(), 
//					deposit.getAccountNumber(), "Deposit Account", null, "Charges Account", 
//					 Constants.PENALTY1, prevPenaltyDue, "Internal Tranasfer", 
//					depositSummary.getTotalPrincipal());		
			//-----------------------------------------------------------
			

						
			// 4. Adjust/Deduct the interest - Take out the interest given till date and
			// add the interest for current tenure
			//-------------------------------------------
			// Get the last base for deposit conversion
			Distribution lastBaseLine = paymentDistributionDAO.getLastBaseLineForTenureChange(depositId);
			
			Integer currentTenure = DateService.getDaysBetweenTwoDates(createdDate, DateService.getCurrentDate());
			
			// Get Interest rate for current tenure
			Float currentInterestRate = calculationService.getDepositInterestRate(currentTenure, deposit.getPrimaryCustomerCategory(),
					deposit.getDepositCurrency(), deposit.getDepositAmount(), deposit.getDepositClassification(),
					deposit.getPrimaryCitizen(), deposit.getNriAccountType());
		
			
		
			String action = "Interest Adjustment for Deposit Conversion";
			distribution =  fdService.adjustInterestForTenureChangeOrConversion(action, deposit, distribution, depositHolderList, lastBaseLine, currentInterestRate);
			//-------------------------------------------
	
		
			// Get is the current balance
			depositSummary = depositSummaryDAO.getDepositSummary(deposit.getId());
			Double depositCurrentBalance = depositSummary.getTotalPrincipal();
			
			//-------------------------------------------
			
			// 6. Close the deposit
			//-----------------------------------
			deposit.setMaturityAmountOnClosing(0d);
			deposit.setCurrentBalance(0d);
			//deposit.setPrematurePanaltyAmount(premeturePanality);
			deposit.setClosingDate(DateService.getCurrentDate());
			deposit.setStatus(Constants.CLOSESTATUS);
			fixedDepositDAO.updateFD(deposit);

			
			// update holder wise maturity amount in DepositHolder table		
			Long primaryHolderId = null;
			for (int i = 0; i < depositHolderList.size(); i++) {

				DepositHolder depositHolder = depositHolderList.get(i);

				depositHolder.setDistAmtOnMaturity(0d);
				depositHolderDAO.updateDepositHolder(depositHolder);
				
				if(depositHolder.getDepositHolderStatus().equalsIgnoreCase("Primary"))
					primaryHolderId = depositHolder.getId();
			}
			// Insert penalty in PaymentDistribution and Holderwise PaymentDistribution
			distribution = calculationService.insertInPaymentDistribution(deposit, depositHolderList, null, null, 
					Constants.DEPOSITCONVERTED, null, null, null, null, null, null, null);
			
			// Update in DepositSummary and DepositHolderWiseSummary
			calculationService.upsertInDepositSummary(deposit, Constants.DEPOSITCONVERTED, null, null, null, null, null, depositHolderList, null, null, null);
			//-----------------------------------
		
			// 7. Find out the new DepositAmount, and balance amount
			// that needs to update in saving account
			//-------------------------------------------			

			
			//Double quotient = balanceAfterAdjustment/100;
			// this amount will go to either Saving account or suspense account 
			// We are dividing with 100 as amount will be taken as multiple of 100
			// means if balanceAfterAdjustment = 30,322.13, then 22.13 will be 
			// the remainder and it will go to the saving account.
			Double remainder = depositCurrentBalance%100;
			
			Double newDepositAmount = (depositCurrentBalance - remainder);
			
			HashMap<String, Double> depositConversionDetails = new HashMap<>();

			depositConversionDetails.put(Constants.NewDepositAmount, newDepositAmount);
			depositConversionDetails.put(Constants.TransferAmountToSavingAccount, remainder);
			depositConversionDetails.put(Constants.InterestRate, Double.parseDouble(currentInterestRate.toString()));
			//-------------------------------------------
			
			return depositConversionDetails;

	}
	

	public String makePayment(Deposit deposit, FixedDepositForm fixedDepositForm, 
			String currentLoggedInUserName, List<Payment> payments, Double recurringAmount, UnSuccessfulRecurringDeposit unsuccessfulRecurringDeposit )
	{
		Double totalPaidAmount = 0d;
		Integer isTopUp = 1;
		String transactionId = "";
		for (int j = 0; j < payments.size(); j++) {
			totalPaidAmount = totalPaidAmount + payments.get(j).getAmountPaid();
		}

		Double amountPaid = fixedDepositForm.getFdAmount();
		if(totalPaidAmount>=recurringAmount)
		{
			isTopUp = 1;			
			transactionId = this.savePayment(deposit, fixedDepositForm, isTopUp, currentLoggedInUserName, amountPaid, unsuccessfulRecurringDeposit);
		}
		else if((totalPaidAmount+ fixedDepositForm.getFdAmount())== recurringAmount)
		{
			isTopUp = 0; // recurring payment
			transactionId = this.savePayment(deposit, fixedDepositForm, isTopUp, currentLoggedInUserName, amountPaid, unsuccessfulRecurringDeposit);
			
			// update previous payment list as recurring payment
			for (int j = 0; j < payments.size(); j++) {
				payments.get(j).setTopUp(0);
				paymentDAO.updatePayment(payments.get(j));
			}
		}
		else if((totalPaidAmount + fixedDepositForm.getFdAmount())> recurringAmount)
		{
			isTopUp = 0; // recurring payment
			Double amountForTopup = (totalPaidAmount + fixedDepositForm.getFdAmount()) - recurringAmount;
			Double amountForRecurring = fixedDepositForm.getFdAmount() - amountForTopup;
			transactionId = this.savePayment(deposit, fixedDepositForm, isTopUp, currentLoggedInUserName, amountForRecurring, unsuccessfulRecurringDeposit);
			
			// update previous payment list as recurring payment
			for (int j = 0; j < payments.size(); j++) {
				payments.get(j).setTopUp(0);
				paymentDAO.updatePayment(payments.get(j));
			}
			
			// Consider access amount as topup
			isTopUp = 1; 
			transactionId = this.savePayment(deposit, fixedDepositForm, isTopUp, currentLoggedInUserName, amountForTopup, unsuccessfulRecurringDeposit);
			
		}
		return transactionId;
	}
		
	public String savePayment(Deposit deposit, FixedDepositForm fixedDepositForm, int isTopUp, 
			String currentLoggedInUserName, Double amountPaid, UnSuccessfulRecurringDeposit unsuccessfulRecurringDeposit)
	{
		String transactionId = fixedDepositForm.getPaymentMode();
		
		// get deposit holder list from DepositHolder table
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPaymentById(Long.parseLong(fixedDepositForm.getPaymentMode()));
		
		Payment payment = new Payment();

		payment.setDepositId(fixedDepositForm.getDepositId());
		payment.setDepositHolderId(calculationService.getPrimaryHolderId(depositHolderList));
		payment.setPaymentMadeByHolderIds(fixedDepositForm.getPaymentMadeByHolderIds());
		payment.setAmountPaid(amountPaid);
		payment.setTransactionId(fdService.generateRandomString());
		payment.setCreatedBy(currentLoggedInUserName);
		payment.setPaymentModeId(mop.getId());
		payment.setPaymentMode(mop.getPaymentMode());
		payment.setPaymentDate(DateService.getCurrentDate());
		payment.setTopUp(isTopUp);
		
		if (mop.getPaymentMode().equalsIgnoreCase("Cash"))
		{

		}
		else if (!(mop.getPaymentMode().equalsIgnoreCase("Debit Card"))
				&& !(mop.getPaymentMode().equalsIgnoreCase("Credit Card"))
				&& !(mop.getPaymentMode().equalsIgnoreCase("onLinePayment"))
				&& !(mop.getPaymentMode().equalsIgnoreCase("Net Banking"))) {
			payment.setBank(fixedDepositForm.getChequeBank());
			payment.setBranch(fixedDepositForm.getChequeBranch());
			payment.setChequeDDNumber(String.valueOf(fixedDepositForm.getChequeNo()));


		}

		else if (mop.getPaymentMode().equalsIgnoreCase("Debit Card") || mop.getPaymentMode().equalsIgnoreCase("Credit Card")) {
			String[] cardNoArray = fixedDepositForm.getCardNo().split("-");
			String cardNo = "";
			for (int i = 0; i < cardNoArray.length; i++) {
				cardNo = cardNo + cardNoArray[i];
			}
			payment.setCardNo(Long.valueOf(cardNo));
			payment.setCardType(fixedDepositForm.getCardType());
			payment.setCardCvv(fixedDepositForm.getCvv());
			payment.setCardExpiryDate(fixedDepositForm.getExpiryDate());


		} else if (mop.getPaymentMode().equalsIgnoreCase("onLinePayment")) {

			payment.setNameOnBankAccount(fixedDepositForm.getDepositForm().getOtherName1());
			payment.setAccountNumber(fixedDepositForm.getDepositForm().getOtherAccount1());
			payment.setTransferType(fixedDepositForm.getDepositForm().getOtherPayTransfer1());

			if (fixedDepositForm.getDepositForm().getFdPayType().equalsIgnoreCase("DifferentBank")) {
				payment.setBankName(fixedDepositForm.getDepositForm().getBank());
				payment.setIfscCode(fixedDepositForm.getDepositForm().getOtherIFSC1());
			}

			if (fixedDepositForm.getFdPayOffAccount().contains("Same")) {
				AccountDetails accDetails = accountDetailsDAO
						.findByAccountNo(fixedDepositForm.getOtherAccount().toString());
				if (accDetails != null) {

				}

			}

		}
		else if (mop.getPaymentMode().equalsIgnoreCase("Net Banking"))
		{
//			payment.setDepositAmount(fixedDepositForm.getDepositAmount());
//			payment.setAmountPaid(fixedDepositForm.getDepositAmount());
//			payment.setPaymentDate(DateService.getCurrentDate());
//			//payment.setModeOfPayment("Net Banking");
//			payment.setModeOfPayment(fixedDepositForm.getPaymentMode());
			
//			payment.setDepositId(fixedDepositForm.getDepositId());
//			payment.setDepositHolderId(primaryDepositHolder.getId());
//			payment.setTopUp(fixedDepositForm.getDepositForm().getTopUp());
			payment.setNameOnBankAccount(fixedDepositForm.getOtherName());
			payment.setAccountNumber(fixedDepositForm.getOtherAccount().toString());

			if (fixedDepositForm.getFdPayOffAccount().equals("Other")) {

				payment.setTransferType(fixedDepositForm.getOtherPayTransfer());
				payment.setBankName(fixedDepositForm.getOtherBank());
				payment.setIfscCode(fixedDepositForm.getOtherIFSC());
			}
		}

		payment = paymentDAO.insertPayment(payment);
		transactionId = payment.getTransactionId();
		
		// If it is recurring payment then upodate the due date
		if(isTopUp == 0)
		{
			// overriding modification data
			DepositModification modification = depositModificationDAO.getLastByDepositId(deposit.getId());
			if (modification == null) {
				modification = new DepositModification();
				modification.setDepositAmount(deposit.getDepositAmount());
				modification.setPaymentMode(deposit.getPaymentMode());
				modification.setPaymentType(deposit.getPaymentType());
			}
		
			
			// If the recurring payment is done in same day, then only update due date
			// update due date
			if(DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), deposit.getDueDate())==0)
				deposit = notificationScheduler.updateDueDateFromRecurringPayment(deposit, modification);
			
			// update unsuccessfullPayment for recurring deposit
			if(unsuccessfulRecurringDeposit != null)
			{
				unsuccessfulRecurringDeposit.setIsPaid(1);
				unsuccessfulRecurringDeposit.setPaymentDate(DateService.getCurrentDateTime());
				unSuccessfulRecurringDAO.updateUnSuccessfulRecurringDeposit(unsuccessfulRecurringDeposit);
			}
		}

		try {

			/*String tex = Constants.CUSTOMERREMENDERSUBJECT;
			SimpleMailMessage emails = new SimpleMailMessage();
			emails.setTo(fixedDepositForm.getEmail());
			emails.setSubject(tex);
			emails.setText(Constants.HELLO + fixedDepositForm.getCustomerName() + Constants.PAYMENTKBODY4
					+ amountPaid + Constants.PAYMENTKBODY5 + Constants.BANKSIGNATURE);
			mailSender.send(emails);
			SimpleMailMessage emails1 = new SimpleMailMessage();
			emails1.setTo(fixedDepositForm.getEmail());*/

		} catch (Exception e) {
			System.out.println(e.getMessage() + e);

		}
		

			
		// Insert in Distribution and holderWiseDistribution
		calculationService.insertInPaymentDistribution(deposit, depositHolderList,
				amountPaid, payment.getId(), Constants.PAYMENT, null, null, null,
				null, null, null, null);

		// Insert in DepositSummary and HolderwiseDepositSummary
		DepositSummary depositSummary = calculationService.upsertInDepositSummary(deposit, Constants.PAYMENT,
				amountPaid, null, null, null, null, depositHolderList, null, null,
				null);
		
		return transactionId;
	}
	

	//@Transactional(rollbackFor = Exception.class)
	public 	String doPayment(FixedDepositForm fixedDepositForm, String loggedInUser) throws CustomException
	{
		String fromAccountNo = null;
		String transactionId = "";
		


		Long mopId = Long.parseLong(fixedDepositForm.getPaymentMode());
		ModeOfPayment mop = modeOfPaymentDAO.getModeOfPaymentById(mopId);

		String paymentMode=modeOfPaymentDAO.getModeOfPaymentById(Long.parseLong(fixedDepositForm.getPaymentMode())).getPaymentMode();
		Deposit deposit = fixedDepositDAO.getDeposit(fixedDepositForm.getDepositId());
		ProductConfiguration productConfiguration = productConfigurationDAO.findById(deposit.getProductConfigurationId());
		
		if(productConfiguration.getIsTopupAllowed() != null && productConfiguration.getIsTopupAllowed()==0)
			throw new CustomException("Sorry, Top-up is not allowed.");
		
		Double amountPaid = fixedDepositForm.getFdAmount();
		if(amountPaid<productConfiguration.getMinimumTopupAmount())
			throw new CustomException("Sorry, Minimum Top-up Amount is " + productConfiguration.getMinimumTopupAmount() + ".");
			
		if(amountPaid>productConfiguration.getMaximumTopupAmount())
			throw new CustomException("Sorry, Maximum Top-up Amount is " + productConfiguration.getMaximumTopupAmount() + ".");
	
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
		
		if(DateService.getDaysBetweenTwoDates(lockingDate, DateService.getCurrentDate()) < 0)
			throw new CustomException("Sorry, Deposit is still within the locking period for Top-up.");
		
		Date maturityDate = deposit.getNewMaturityDate() != null? deposit.getNewMaturityDate() : deposit.getMaturityDate();
		if(productConfiguration.getPreventionOfTopUpBeforeMaturity()!=null)
		{
			if(DateService.getDaysBetweenTwoDates(DateService.getCurrentDate(), maturityDate) <= productConfiguration.getPreventionOfTopUpBeforeMaturity())
				throw new CustomException("Sorry, Withdraw has prevented for last " + productConfiguration.getPreventionOfTopUpBeforeMaturity() + " days from maturity.");
		}
	
		
		// Now at this stage payment is possible
	//	Deposit depositSaves = fixedDepositDAO.saveFD(deposit);
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(fixedDepositForm.getDepositId());
		Long customerId = fdService.getPrimaryHolderId(depositHolderList);

		if (deposit.getDepositClassification() != null
				&& deposit.getDepositClassification().equalsIgnoreCase("Recurring Deposit")) {
			Date todaysDate = DateService.getCurrentDateTime();
			SimpleDateFormat smt = new SimpleDateFormat("dd/MM/yyyy");
			String todaysDateStr = smt.format(todaysDate);

			// Get the deposit due date
			Date dueDate = deposit.getDueDate();

			// Check it is is the actual due date or not. If there is some unsuccessful
			// payment in this date,
			// then actual due date is the unsuccessful payment date.
			// Then check the grace period. If payment is done within the grace period,
			// then there is no penalty charged.
			UnSuccessfulRecurringDeposit unSuccessfulRecurringDeposit = unSuccessfulRecurringDAO
					.getLastUnSuccessfulRecurringDeposit(deposit.getId());
			if (unSuccessfulRecurringDeposit != null)
				dueDate = unSuccessfulRecurringDeposit.getUnsuccessPaymentDate();

			Integer daysDifference = DateService.getDaysBetweenTwoDates(dueDate, todaysDate);

			// check if the date difference is within the grace period then
			// take the payment as recurring payment else top up

			// Get the Bank Configuration to find grace period
			ProductConfiguration _pc = null;
			
				_pc = productConfigurationDAO.findById(deposit.getProductConfigurationId());
		
			if (_pc==null) {
				_pc = productConfigurationDAO.findByProductCode(deposit.getProductConfigurationId().toString());
			}
			if (_pc != null) {
				// get the grace period
				Integer gracePeriod = _pc.getGracePeriodForRecurringPayment() == null ? 0
						: _pc.getGracePeriodForRecurringPayment();

				if (daysDifference>=0 && gracePeriod >= daysDifference) {
					// isTopUp = 0; // recurring
					// Get the modification last record
					// overriding modification data
					DepositModification modification = depositModificationDAO.getLastByDepositId(deposit.getId());
					if (modification == null) {
						modification = new DepositModification();
						modification.setDepositAmount(deposit.getDepositAmount());
						modification.setPaymentMode(deposit.getPaymentMode());
						modification.setPaymentType(deposit.getPaymentType());
					}

					// Now check the amount
					// -----------------------------------------------------------------
					Double recurringAmount = modification.getDepositAmount();
					List<Payment> payments = paymentDAO.getPaymentDetails(deposit.getId(), dueDate, todaysDate);
					this.makePayment(deposit, fixedDepositForm, loggedInUser, payments,
							recurringAmount, unSuccessfulRecurringDeposit);
				} else {
					Integer isTopUp = 1;
					transactionId = this.savePayment(deposit, fixedDepositForm, isTopUp, loggedInUser,
							fixedDepositForm.getFdAmount(), unSuccessfulRecurringDeposit);
				
					this.calculatePenaltyForTopup(deposit, productConfiguration, fixedDepositForm.getFdAmount());
				}
			}

		} else {
			transactionId = this.savePayment(deposit, fixedDepositForm, 1, loggedInUser,
					fixedDepositForm.getFdAmount(), null);
			
			this.calculatePenaltyForTopup(deposit, productConfiguration, fixedDepositForm.getFdAmount());
			
		}
		if (paymentMode.equalsIgnoreCase("Cash")) {
			// For accounting entry only
			

		} else if (!(paymentMode.equalsIgnoreCase("Credit Card"))
				&& !(paymentMode.equalsIgnoreCase("Debit Card"))
				&& !(paymentMode.equalsIgnoreCase("onLinePayment"))
				&& !(paymentMode.equalsIgnoreCase("Net Banking"))) {
			// For accounting entry only
			fromAccountNo = fixedDepositForm.getChequeNo().toString();

		}

		else if (paymentMode.equalsIgnoreCase("Debit Card") || paymentMode.equalsIgnoreCase("Credit Card")) {
			String[] cardNoArray = fixedDepositForm.getCardNo().split("-");
			String cardNo = "";
			for (int i = 0; i < cardNoArray.length; i++) {
				cardNo = cardNo + cardNoArray[i];
			}
			// For accounting entry only
			fromAccountNo = cardNo;


		} else if (paymentMode.equalsIgnoreCase("onLinePayment") || paymentMode.equalsIgnoreCase("Net Banking")) {

			if (fixedDepositForm.getFdPayOffAccount().contains("Same") || fixedDepositForm.getFdPayOffAccount().contains("Saving Account")) {
				AccountDetails accDetails = accountDetailsDAO
						.findByAccountNo(fixedDepositForm.getOtherAccount().toString());
				if (accDetails != null) {
					fromAccountNo = accDetails.getAccountNo();

				}

			}

		}
		
		
		
		// Insert in DepositSummary and HolderwiseDepositSummary
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(deposit.getId());
		
		//Customer customer = customerDAO.getById(fixedDepositForm.getId());

		// Insert in Journal & Ledger
		// -----------------------------------------------------------
		ledgerService.insertJournal(deposit.getId(), customerId, DateService.getCurrentDate(), fromAccountNo,
				deposit.getAccountNumber(), Event.TOPUP_DEPOSIT.getValue(),
				fixedDepositForm.getFdAmount(),
				mop.getId(), depositSummary.getTotalPrincipal(),
				transactionId);
		// -----------------------------------------------------------

		if (deposit.getReverseEmiCategory() != null
				&& deposit.getReverseEmiCategory().equalsIgnoreCase("Fixed Amount")) {
			double totalDepositAmount = deposit.getDepositAmount() + fixedDepositForm.getFdAmount();
			float rateOfInterest = deposit.getInterestRate();
			double emiAmount = depositHolderList.get(0).getEmiAmount();
			float tenure = fdService.calculateEmiTenure(totalDepositAmount, emiAmount, rateOfInterest);

			int daysTenure = (int) (tenure * 30);

			Date maturityDate1 = DateService.generateDaysDate(deposit.getCreatedDate(), daysTenure);
			deposit.setTenure(daysTenure);
			deposit.setNewMaturityDate(maturityDate1);
			deposit.setDepositAmount(totalDepositAmount);
		}

		deposit = fixedDepositDAO.updateFD(deposit);
		
		return transactionId;
	}
	
	public void calculatePenaltyForTopup(Deposit deposit, ProductConfiguration productConfiguration, Double topupAmount) throws CustomException
	{
		
		// Check if the penalty for topup is configured or not 
		if(productConfiguration.getPenaltyAmountForTopup() == null && productConfiguration.getPenaltyPercentForTopup() == null)
			return;
		
		if(productConfiguration.getPenaltyAmountForTopup() == null && productConfiguration.getPenaltyPercentForTopup() == 0f)
			return;	

		Rates rates = ratesDAO.getAllRatesByCustomerCategoryCitizenAndAccountType(
				deposit.getPrimaryCustomerCategory(), deposit.getPrimaryCitizen(),
				deposit.getPrimaryNRIAccountType());

	
		Float fixedPercent = rates == null? null : rates.getFdFixedPercent();
		Float variablePercent = 100-(fixedPercent == null? 0f : fixedPercent);
			
				
		// Get the DepositSummary
		DepositSummary depositSummary = depositSummaryDAO.getDepositSummary(deposit.getId());
//		Double totalFixedInterestAccumulated = depositSummary.getTotalFixedInterestAccumulated()==null?0l:depositSummary.getTotalFixedInterestAccumulated();
//		Double totalVariableInterestAccumulated = depositSummary.getTotalVariableInterestAccumulated()==null?0l:depositSummary.getTotalVariableInterestAccumulated(); 
		
		Double fixedPenaltyAmount = 0d;
		Double variablePenaltyAmount = 0d;
		
		Double fixedTopupAmount = topupAmount * fixedPercent/100;
		Double variableTopupAmount = topupAmount * variablePercent/100;
	
		if(productConfiguration.getPenaltyPercentForTopup() !=null && productConfiguration.getPenaltyPercentForTopup()>0)
		{
			fixedPenaltyAmount = calculationService.round(fixedTopupAmount * productConfiguration.getPenaltyPercentForTopup() /100, 2);
			variablePenaltyAmount = calculationService.round(variableTopupAmount * productConfiguration.getPenaltyPercentForTopup() /100,2);
		}
		else if(productConfiguration.getPenaltyAmountForTopup() != null)
		{
			
			fixedPenaltyAmount = calculationService.round(productConfiguration.getPenaltyAmountForTopup() * fixedPercent /100, 2);
			variablePenaltyAmount = calculationService.round(productConfiguration.getPenaltyAmountForTopup() * variablePercent /100, 2);
		}
	
			
		List<DepositHolder> depositHolderList = depositHolderDAO.getDepositHolders(deposit.getId());
		
		// Insert penalty in PaymentDistribution and Holderwise PaymentDistribution and DepositSummary table
		// -----------------------------------------
		Distribution distribution1 = calculationService.insertInDistributionForPenaltyInFixedVariable(Constants.topupPenalty,
				deposit, depositHolderList, fixedPenaltyAmount, variablePenaltyAmount);
		
		depositSummary = calculationService.upsertInDepositSummaryForPenaltyInFixedVariable(deposit, Constants.topupPenalty, depositHolderList,  fixedPenaltyAmount, variablePenaltyAmount);
		// -----------------------------------------
		
		// Insert in Journal & Ledger
		//-----------------------------------------------------------
		/*ledgerService.insertJournal(deposit.getId(), null, DateService.getCurrentDate(),
				deposit.getAccountNumber(), null, Event.K00107.getValue(),
				(fixedPenaltyAmount+variablePenaltyAmount),  "Internal Tranasfer", depositSummary.getTotalPrincipal(),
				null);*/
//		ledgerService.insertJournalLedger(deposit.getId(), null,DateService.getCurrentDate(), 
//				deposit.getAccountNumber(), "Deposit Account", null, "Charges Account", 
//				Constants.topupPenalty, (fixedPenaltyAmount+variablePenaltyAmount), "Internal Tranasfer", 
//				depositSummary.getTotalPrincipal());		
		//-----------------------------------------------------------

	}

}
