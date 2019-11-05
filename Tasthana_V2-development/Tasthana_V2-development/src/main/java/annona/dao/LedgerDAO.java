package annona.dao;


import java.util.Date;
import java.util.List;

import annona.domain.GLConfiguration;
import annona.domain.Journal;
import annona.domain.Ledger;
import annona.domain.ModeOfPayment;
import annona.form.LedgerReportForm;

public interface LedgerDAO 
 {
	public Journal insertJournal(Journal Journal);
	
	public void updateJournal(Journal Journal);

	public void insertLedger(Ledger ledger);
	
	public List<GLConfiguration> getGLConfiguration();
	
	public GLConfiguration insertGLConfiguration(GLConfiguration glConfiguration);
	
	public GLConfiguration updateGLConfiguration(GLConfiguration glConfiguration);
	
	public GLConfiguration findById(Long id);
	
	public GLConfiguration getGLConfigurationByGLAccount(String glAccount);
	
	public Long countById();
	
	public List<ModeOfPayment> getModeOfPayments();
	
	public Long getMaxSerialNumberFromJournal();
	
	public List<Journal> getUnPostedJournals();
	
	public List<LedgerReportForm> getLedgersOfDeposit(Long id, Date fromDate, Date toDate);
	
	public List<LedgerReportForm> getLedgers(Date fromDate, Date toDate);

 }
