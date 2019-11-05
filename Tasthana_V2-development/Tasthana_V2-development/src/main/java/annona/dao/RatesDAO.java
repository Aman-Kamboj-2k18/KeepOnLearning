package annona.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import annona.domain.BankConfiguration;
import annona.domain.CustomerCategory;
import annona.domain.DepositRates;
import annona.domain.ProductConfiguration;
import annona.domain.RatePeriods;
import annona.domain.Rates;

public interface RatesDAO {
	
	/**
	 * Method to get category values
	 * @param category
	 * @return
	 */
	public TypedQuery<Rates> findByCategory(String category);
	
	/**
	 * Method to create a rate
	 * @param rate
	 * @return
	 */
	public void createRate(Rates rate);
	
	/**
	 * Method to update rates
	 * @param rates
	 */
	public void update(Rates rates);	
	
	/**
	 * Method to get all rates
	 * @return
	 */
	public Collection<Rates> findAllRates();
	
	/**
	 *Method to get id details
	 *@param id
	 *@return
	 **/
	public Rates findById(long id);
	
	/**
	  * Method to create Bank Configuration
	  * @param BankConfiguration
	  * @return
	  */
	// public void createBankConfiguration(BankConfiguration bankConfiguration);
	 /**
	  *Method to get id details
	  *@param id
	  *@return
	  **/
	// public BankConfiguration findAll();
	 /**
	  * Method to update BankConfiguration
	  * @param rates
	  */
	// public void updateBankConfiguration(BankConfiguration bankConfiguration);
	 
//	 public BankConfiguration getBankConfiguration(String customerCategory);
	 
	 public Rates getRatesByCategory(String category);
	  
	 public Rates getAllRatesByCustomerCategoryAndCitizen(String customerCategory,String citizen);
	 
	 public Rates getAllRatesByCustomerCategoryCitizenAndAccountType(String customerCategory,String citizen,String accountType);
	 
	 public List<RatePeriods> getRateDurationsByDepositClaasification(String depositClassification);
	 
	//public List<DepositRates> getAllDepositRates();
	 
	 public List<DepositRates> getAllDepositRatesByRatePeriodId(Long ratePeriodsId);

	 public List<DepositRates> getAllRatePeriods();
	 
	 public List<RatePeriods> getAllRatesPeriodFromId(Long ratePeriodsId);
	 
	 public DepositRates getRateDescriptionById(Long ratePeriodsId ,String currency , String category ,Date effectiveDate ,String description ,String depositClassification,String nriAccountType);
	 
//	 public BankConfiguration getBankConfiguration(String citizen, String accountType);
	 
	 public BankConfiguration findAll();
	 	 
	 public void createCustomerCategory(CustomerCategory customerCategory);
	 
	 //public List<BankConfiguration> getBankConfigurationListByInterestCalculationBasis(String interestCalculationBasis);
	 
//	 public List<BankConfiguration> getBankConfigurationListByInterestCompoundingBasis(String interestCompoundingBasis);

	 public List<RatePeriods> getRateDurations(String depositClassification, String citizen, String nriAccountType, String category);
}
