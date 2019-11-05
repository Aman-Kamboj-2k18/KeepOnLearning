package annona.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.TypedQuery;

import annona.domain.DepositRates;

public interface DepositRatesDAO {
	
	public Collection<DepositRates> getDataByCategory();
	
	public TypedQuery<DepositRates> getRatesByCategory(String category, String currency, String depositClassification, Double amountSlabFrom, Double amountSlabTo);
	//public DepositRates getRateByMaturityPeriodAndCategory(Integer days, String category, String currency);
	public TypedQuery<DepositRates> getRatesByCategoryAndCitizen(String category, String currency, String depositClassification, Double amountSlabFrom, Double amountSlabTo,String nriAccountType) ;

	public DepositRates getRateByMaturityPeriodAndCategory(Integer days, String category, String currency, Double depositAmount, String depositClassification);
	
	public DepositRates getRateByMaturityPeriodAndCategory(Integer days, String category, String currency, Double fromSlab, Double toSlab, String depositClassification);
	
	public List<Double> getFromAmountSlabList(String category, String currency, String depositClassification);
	
	public Double getToAmountSlab(String category, String currency, String depositClassification, Double amountSlabFrom );
	
	public DepositRates getInterestRate(Integer tenure, String category, String currency,
			Double depositAmount, String depositClassification,  String citizen, String accountType);
	
	public List<Double> getFromAmountSlabListForViewRate(String category, String currency, String depositClassification, String citizen, String nriAccountType);
	
	public TypedQuery<DepositRates> getRatesByCategoryDependsOnCitizen(String category, String currency, String depositClassification, Double amountSlabFrom, Double amountSlabTo, String citizen, String nriAccountType) ;


}
