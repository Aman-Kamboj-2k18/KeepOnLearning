package annona.dao;

import java.util.List;

import annona.domain.Transaction;
import annona.domain.UnSuccessfulRecurringDeposit;

public interface UnSuccessfulRecurringDAO 
 {

	public UnSuccessfulRecurringDeposit getLastUnSuccessfulRecurringDeposit(Long depositId);
	
	public List<UnSuccessfulRecurringDeposit> getUnsuccessfulRecurringDepositOfCurrentMonth(Integer month, Integer year);
	
	public void insertUnSuccessfulRecurringDeposit(UnSuccessfulRecurringDeposit unSuccessfulDeposit);
	
	public void updateUnSuccessfulRecurringDeposit(UnSuccessfulRecurringDeposit unsuccessfulRecurringDeposit);
	
  }
