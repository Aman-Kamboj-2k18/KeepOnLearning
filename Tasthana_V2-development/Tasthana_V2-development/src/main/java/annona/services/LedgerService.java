package annona.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import annona.dao.EndUserDAO;
import annona.dao.LedgerDAO;
import annona.dao.LedgerEventMappingDAO;
import annona.dao.ModeOfPaymentDAO;
import annona.domain.EndUser;
import annona.domain.GLConfiguration;
import annona.domain.Journal;
import annona.domain.Ledger;
import annona.domain.LedgerEventMapping;
import annona.domain.ModeOfPayment;
import annona.exception.CustomException;
import annona.form.LedgerReportForm;


/**
 * Class to maintain Ledger
 */
@Service
public class LedgerService {

	@Autowired
	LedgerDAO ledgerDAO;
	
	@Autowired
	EndUserDAO endUserDAO;
	
	@Autowired
	ModeOfPaymentDAO modeOfPaymentDAO;
	
	@Autowired
	LedgerEventMappingDAO ledgerEventMappingDAO;
	
	

	public void insertJournal(Long depositId, Long customerId, Date entryDate, String fromAccountNo,
			String toAccountNo, String event, Double amount, Long mopId, Double depositBalance,
			String transactionId) throws CustomException
	{
		// Insert in Journal Debit and credit record 
		this.insertJournalRecord(depositId, customerId, entryDate, fromAccountNo, toAccountNo,  
				event, amount, mopId, transactionId, depositBalance);		
	}
	
//	//Temporary
//	@Transactional
//	public void insertJournal(Long depositId, Long customerId, Date entryDate, String fromAccountNo, 
//			String toAccountNo,  String event, Double amount,  String mopId, Double depositBalance,
//			String transactionId) throws CustomException
//	{
//		// Insert in Journal Debit and credit record 
//		this.insertJournalRecord(depositId, customerId, entryDate, fromAccountNo, toAccountNo,  
//				event, amount, Long.parseLong(mopId), transactionId, depositBalance);		
//	}

	private void insertJournalRecord(Long depositId,Long customerId,Date entryDate, String fromAccountNo, 
			String toAccountNo, String event, Double amount, Long mopId, String transactionId, Double depositBalance)
					throws CustomException
	{
		// Inserting debit record 
		//---------------------------------------------------------------------------------------
		Journal journal = new Journal();
		journal.setDepositId(depositId);
		journal.setJournalDate(entryDate);
		journal.setCustomerId(customerId);	
			
		//ModeOfPayment modeOfPayment = modeOfPaymentDAO.getModeOfPayment(paymentMode);
		
		LedgerEventMapping ledgerEventMapping = ledgerEventMappingDAO.getLedgerEventMapping(event, mopId);	
		if(ledgerEventMapping == null)
		{
			throw new CustomException("Ledger Event Mapping is not done for the Event: " + event + " and the Payment Mode Id: " + mopId + ".");
		
		}
		journal.setDebitGLCode(ledgerEventMapping.getDebitGLCode());
		String particulars = (fromAccountNo != null && fromAccountNo != "") ? ledgerEventMapping.getDebitGLAccount() + ": " + fromAccountNo : ledgerEventMapping.getDebitGLAccount();
		journal.setParticulars(particulars);
		journal.setFromAccountNumber(fromAccountNo);
		journal.setDebitAmount(amount);
		journal.setEvent(event);
		journal.setIsPosted(0);
		journal.setSerialNo(ledgerDAO.getMaxSerialNumberFromJournal() +1);
		journal.setDepositBalance(depositBalance);
		journal.setMopId(mopId);
		Journal debiJournal = ledgerDAO.insertJournal(journal);
		//-------------------------------------------------------------------------------------
		
		
		//Inserting credit record
		//-------------------------------------------------------------------------------------
		journal = new Journal();
		journal.setDepositId(depositId);
		journal.setJournalDate(entryDate);
		journal.setCustomerId(customerId);			
		journal.setCreditGLCode(ledgerEventMapping.getCreditGLCode());
		
		particulars = (toAccountNo != null && toAccountNo != "") ? ledgerEventMapping.getCreditGLAccount() + ": " + toAccountNo : ledgerEventMapping.getCreditGLAccount();
		journal.setParticulars(particulars);
		journal.setToAccountNumber(toAccountNo);
		journal.setCreditAmount(amount);
		journal.setEvent(event);
		journal.setIsPosted(0);
		journal.setSerialNo(debiJournal.getSerialNo());
		journal.setDepositBalance(depositBalance);
		journal.setMopId(mopId);
		ledgerDAO.insertJournal(journal);
		//-------------------------------------------------------------------------------------
		
	}
	
	@Transactional
	public void postInLedger()
	{	
		System.out.println("Startng the Ledger Posting...");
		
		List<Journal> journalList = ledgerDAO.getUnPostedJournals();
		if(journalList == null)
		{
			System.out.print("There is nothing to post in ledger");
			return;
		}
		
		for(int i=0; i<journalList.size(); i++)
		{
			Journal journal = journalList.get(i);
			
			String strParticulars = "";
			if(journal.getDebitGLCode() != null)
			{
				strParticulars = "To " + journal.getParticulars();	
				
				Ledger ledger = new Ledger(); 
				ledger.setDepositId(journal.getDepositId());
				ledger.setJournalId(journal.getId());
				ledger.setLedgerDateDebit(journal.getJournalDate());
				ledger.setParticularsDebit(strParticulars);
				ledger.setDebitAmount(journal.getDebitAmount());
				ledger.setDepositBalance(journal.getDepositBalance());
				ledger.setDebitGLCode(journal.getDebitGLCode());
				ledgerDAO.insertLedger(ledger);	
			}
			else
			{
				strParticulars = "From " + journal.getParticulars();	
			
				Ledger ledger = new Ledger(); 
				ledger.setDepositId(journal.getDepositId());
				ledger.setJournalId(journal.getId());
				ledger.setLedgerDateCredit(journal.getJournalDate());
				ledger.setParticularsCredit(strParticulars);
				ledger.setCreditAmount(journal.getCreditAmount());
				ledger.setDepositBalance(journal.getDepositBalance());
				ledger.setCreditGLCode(journal.getCreditGLCode());
				ledgerDAO.insertLedger(ledger);	
			}
			
			// Update the journal as posted the record
			journal.setIsPosted(1);
			ledgerDAO.updateJournal(journal);;
		}
		
		System.out.println("Ledger Posting Finished.");
	}

	//-------------------------------------------------------------------	
	public List<GLConfiguration> getGLConfigurations()
	{
		List<GLConfiguration> glConfigurationList = ledgerDAO.getGLConfiguration();
		List<ModeOfPayment> modeOfPaymentList = ledgerDAO.getModeOfPayments();	
		
		if(glConfigurationList == null)
		{
			glConfigurationList = new ArrayList<>();
			
			for (int i = 0; i < modeOfPaymentList.size(); i++)
			{	
				String paymentMode = modeOfPaymentList.get(i).getPaymentMode();
				GLConfiguration glConfiguration = new GLConfiguration();
				//glConfiguration.setId(((long)i)+1);
				glConfiguration.setGlAccount(paymentMode);
				glConfiguration.setGlCode("");		
				glConfiguration.setGlNumber("");
				glConfigurationList.add(glConfiguration);
			}			
		}
		
		
		return glConfigurationList;
	}
	
	@Transactional
	public Boolean insertGLCode(String glCodeList, String glNumberList, Long currentUserId)
	{
		
		Boolean isUpadated = false;
		
		List<GLConfiguration> glConfigurationList = ledgerDAO.getGLConfiguration();
		
		// Read GLCodes
		JSONArray jsonArrGLCodeList = new JSONArray(glCodeList);   
		HashMap<String, String> glCodeMap = new HashMap<>();
		
		if (jsonArrGLCodeList != null) 
		{ 
		   for (int i=0;i<jsonArrGLCodeList.length();i++)
		   { 
			   String glCodeString = jsonArrGLCodeList.getString(i).replace("glCode_[", "");  //glCode_[1]|1#
			  
			   String glAccount = glCodeString.substring(0, glCodeString.indexOf(']'));
			   String glCode = glCodeString.substring(glCodeString.indexOf('|')+1, glCodeString.length());
			   
			   glCodeMap.put(glAccount, glCode);
		   } 
		} 
	
		
//		// Read GLNumbers
//		JSONArray jsonArrGLNumberList = new JSONArray(glNumberList);   
//		HashMap<Long, String> glNumberMap = new HashMap<>();
//		
//		if (jsonArrGLNumberList != null) { 
//		   for (int i=0;i<jsonArrGLNumberList.length();i++)
//		   { 
//			   String glNumberString = jsonArrGLNumberList.getString(i).replace("glNumber_[", "");  //glNumber_[1]|111#
//			  
//			   String glId = glNumberString.substring(0, glNumberString.indexOf(']'));
//			   String glNumber = glNumberString.substring(glNumberString.indexOf('|')+1, glNumberString.length());
//			   
//			   glNumberMap.put(Long.parseLong(glId), glNumber);
//		   } 
//		} 
//		
		

		//String[] myList = {"Cash","Cheque", "DD", "Debit Card", "Credit Card", "Net Banking", "Fund Transfer","Charges", "Interest", "Deposit", "Savings", "Current", "TDS"};
				
		Date currentDateTime = DateService.getCurrentDateTime();
		for (Map.Entry<String, String> entry : glCodeMap.entrySet())
		{
		    String glAccount = entry.getKey();
		    String glCode = entry.getValue();
		    
		    GLConfiguration glConfiguration = ledgerDAO.getGLConfigurationByGLAccount(glAccount);
			    //String glNumber = glNumberMap.get(paymentMode);
		    if(glConfiguration == null)
		    {
		    	GLConfiguration glConfigurationNew = new GLConfiguration();
		    	glConfigurationNew.setGlAccount(glAccount);
		    	glConfigurationNew.setGlCode(glCode);
		    	//glConfiguration.setGlNumber(glNumber);
		    	glConfigurationNew.setCreatedDate(currentDateTime);
		    	glConfigurationNew.setCreatedBy(currentUserId);
		    	ledgerDAO.insertGLConfiguration(glConfigurationNew);
		    	isUpadated = true;
		    }
		    else
		    {
		    	//glConfiguration.setGlAccount(glAccount);
		    	glConfiguration.setGlCode(glCode);
		    	//glConfiguration.setGlNumber(glNumber);
		    	glConfiguration.setCreatedDate(currentDateTime);
		    	glConfiguration.setCreatedBy(currentUserId);
		    	ledgerDAO.updateGLConfiguration(glConfiguration);
		    	isUpadated = true;
		    }

		}
		return isUpadated;
	}
	
	private String getCurrentLoggedUserName() {

		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	private EndUser getCurrentLoggedUserDetails() {

		EndUser endUser = endUserDAO.findByUsername(getCurrentLoggedUserName()).getSingleResult();

		return endUser;

	}

	private GLConfiguration findGLConfiguration(String modeOfPayment)
	{
		// {"Cash","Cheque", "DD", "Debit Card", "Credit Card", "Net Banking", "Fund Transfer","Charges", "Interest", "Deposit", "Savings", "Current", "TDS"};
		List<GLConfiguration> glConfigurationList = ledgerDAO.getGLConfiguration();
		
		GLConfiguration glConfiguration = null;
    	for(int i=0; i<glConfigurationList.size(); i++)
    	{
    		if(glConfigurationList.get(i).getGlAccount().equalsIgnoreCase(modeOfPayment))
    		{
    			glConfiguration = glConfigurationList.get(i);
    			break;
    		}
    	}
    	return glConfiguration;
	}
	
	
	
	
	public GLConfiguration saveGLConfiguration(GLConfiguration glConfiguration)
	{
		glConfiguration = ledgerDAO.insertGLConfiguration(glConfiguration);

    	return glConfiguration;
	}
	
	public GLConfiguration findById(Long id)
	{
    	return ledgerDAO.findById(id);
	}
	
	
	public GLConfiguration update(GLConfiguration glConfiguration)
	{
    	return ledgerDAO.updateGLConfiguration(glConfiguration);
	}
	
	public Long countById()
	{
    	return ledgerDAO.countById();
	}
	
	public List<LedgerReportForm> getLedgersOfDeposit(Long id, Date fromDate, Date toDate)
	{
		return ledgerDAO.getLedgersOfDeposit(id, fromDate, toDate);
	}

	public List<LedgerReportForm> getLedgers(Date fromDate, Date toDate)
	{
		return ledgerDAO.getLedgers(fromDate, toDate);
	}
}
