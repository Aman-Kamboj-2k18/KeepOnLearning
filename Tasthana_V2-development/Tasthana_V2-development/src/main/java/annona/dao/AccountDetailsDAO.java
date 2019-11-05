package annona.dao;

import java.util.List;

import annona.domain.AccountDetails;
import annona.form.CustomerForm;


public interface AccountDetailsDAO 
 {

	/**
	 * Method to save User  account details
	 */
	public void insertAccountDetails(AccountDetails accountDetails);
	
	public CustomerForm editCustomerDetails(Long id);
	
	public void updateUserAccountDetails(AccountDetails accountDetails);
	
	//public void deleteAccountById(Long accountId);
	
	public List<AccountDetails> findByCustomerId(Long customerId);	
	
	public List<Object[]> getCustomersSavingAccounts(Integer minBalanceInSavingAcc);
	
	public AccountDetails findByAccountNo(String accountNo);
	
	public List<AccountDetails> findCurrentSavingByCustId(Long customerId);
	
	public AccountDetails findSavingByCustId(Long customerId);
	
	 public AccountDetails findById(Long id);
	 
	 public List<AccountDetails> findCurrentAndSavingAndDepositByCustomerIdAndIsActive(Long customerId);
  }
