package annona.dao;

import java.util.Collection;

import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import annona.domain.DepositRateFileImport;
import annona.domain.DepositRates;
import annona.domain.RatePeriods;
import annona.domain.Transaction;

public interface DepositRateDAO {

	public void insertDepositRates(DepositRates map);
	
	public void createDuration(RatePeriods map);
	
	public DepositRates findId(Long id);
	
	public void deletebyCategory(String category,Date effectiveDate);
	
	//public Float getInterestRate(String category, String currency, Integer days);
	//public Float getInterestRate(String category, String currency, Integer days, String depositClassification, Double depositAmount);
	
	 public RatePeriods getRatePeriods(Integer startDay ,Integer endDay, String category, String citizen, String nriAccountType, String depositClassification);
	 
}
