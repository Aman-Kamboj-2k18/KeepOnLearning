package annona.dao;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import annona.domain.CurrencyConfiguration;
import annona.domain.Deposit;
import annona.domain.DepositBeforeRenew;
import annona.domain.Distribution;
import annona.domain.Journal;

import annona.domain.RenewDeposit;
import annona.form.AutoDepositForm;
import annona.form.DepositForm;
import annona.form.HolderForm;
import annona.form.LedgerReportForm;
import annona.form.ReportForm;
import annona.form.WithdrawForm;

public interface FixedDepositDAO {

	/**
	 * Method to update FD
	 * 
	 * @param fixedDeposit
	 */
	public Deposit updateFD(Deposit deposit);

	/**
	 * Method to withdraw deposit
	 * 
	 * @param userid
	 * @return
	 */
	public WithdrawForm withdrawAccountNumber(String accountNumber);

	public List<Deposit> getOpenDeposits();

	public List<Deposit> getOpenReverseEMIDeposits();

	public Deposit getDeposit(Long id);

	public List<Object[]> getDepositForInterestRate(Long depositId);

	public Deposit saveFD(Deposit fixedDeposit);

	public Deposit findById(Long depositId);

	// public List<Object[]> getByDepositId(Long depositId);

	public Distribution withdrawDepositAmt(Long depositId);

	public List<Object[]> getOpenDepositsForMaturity(Long id);

	public List<Object[]> getByDepositIdAndCustomerId(Long depositId, Long customerId);

	public Deposit getAutoDeposit(Long customerId);

	public Deposit getAutoDeposit(Long customerId, Float interestRate);

	public List<AutoDepositForm> getAutoDeposits();

	public List<Deposit> getAutoDepositList(Long customerId);

	public Collection<Deposit> findAllFDs();

	public List<Deposit> getApprovedDeposits();

	public List<Deposit> getPendingDeposits();

	public void updateApprovalStatus(Deposit deposit);

	public List<Deposit> getDeposits();

	public List<Distribution> getDistributionList(Long depositId);

	public Deposit getDepositByDepositHolder(Long depositHolderId);

	public List<Deposit> getDeposits(Long customerId);
	
	public List<Deposit> getDepositsOfRIsForTDS(Long customerId);
	
	public List<Deposit> getDepositsOfNRIsForTDS(Long customerId);

	public Deposit findByDepositId(Long depositId);

	public List<Deposit> getOpenDepositsInDescOrder();

	public List<Deposit> getManualDeposits();

	List<DepositForm> getClosedTransactionsList(Date fromDate, Date toDate);

	List<DepositForm> getAllClosedTransactionsList();

	public List<Deposit> getAllDeposits();

	public List<AutoDepositForm> getAutoDepositListForCustomer(Long customerId);

	public List<ReportForm> getReportSummary(Date fromDate, Date toDate);

	public List<ReportForm> getReportSummaryForCus(Date fromDate, Date toDate, Long customerid);

	public List<DepositForm> getAllDepositsList();

	public List<DepositForm> getAllRegularAndAutoDepositsList();

	public List<DepositForm> getDepositByCustomerId(Long id);

	public List<HolderForm> getByCustomerId(Long customerId);

	public HolderForm getByCustomerIdAndDepositId(Long depositId, Long customerId);

	public List<HolderForm> getByDepositId(Long depositId);
	
	 public List<Deposit> findByMultipleId(List<Long> ids);
	 
	 public List<Deposit> findByClearanceStatus();
	 
	 public List<DepositForm> getAllPendingDepositsList();
	 
	 public Deposit getByAccountNumber(String accNumber);
	 
	 public RenewDeposit saveRenewDeposit(RenewDeposit renewDeposit);
	 
	 public List<HolderForm> getByCustomerIdDepositIdAndAccountNumber(Long customerId,Long depositId,String accountNumber);
	 
	 public List<Deposit> getAllCategoryDepositsOfCurrentYear(Long customerId);
	 
	 public List<Deposit> getAllDepositsOfNRIsOfCurrentYear(Long customerId) ;

	 public DepositBeforeRenew saveDepositBeforeRenew(DepositBeforeRenew depositBeforeRenew);
	 	 
	 public List<Deposit> getDeposits(String primaryCitizen, String primaryNRIAccountType);
	 
	 public List<Deposit> getDepositsForMaturity();
	 
	 public List<Journal> getJournalListByFdAccNumberAndFromAndToDate(Long depositId,Date fromDate, Date toDate) ;
	 
    
	 
	 public List<Deposit> getAutoDeposits(Long customerId, String sweepInType);
	 
	 public List<BigInteger> getDistinctCustomerOfAutoDeposits(String sweepInType);
	 
	 public List<Deposit> getDepositsByProductConfiguration(Long productConfigurationId);
	 
	 public List<Deposit> getSweepDeposits();
	 
	 public CurrencyConfiguration getDefaultCurrency(String citizen, String nriAccountType); 

	 public List<DepositForm> getAllRegularAndAutoDepositsByCustomerId(Long customerId);
	 
	 public Deposit getByAccountNumberAndStatusClose(String accNumber);
	 
	 public List<Journal> getJournalListByFromAndToDate(Date fromDate, Date toDate);
	
	 public List<Deposit> getAllSweepDeposits();
	
	List<LedgerReportForm> getLedgerReportFormByFromDateAndToDate(Date fromDate, Date toDate);


}
