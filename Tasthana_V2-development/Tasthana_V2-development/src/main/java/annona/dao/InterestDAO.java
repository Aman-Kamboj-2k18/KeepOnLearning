package annona.dao;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import annona.domain.Interest;



public interface InterestDAO 
 {
		public void insert(Interest interest);
		
		public void update(Interest interest);
		
		public List<Interest> getByDepositId(Long depositId);
		
		public Interest getInterestByMonth(Long depositId, Integer month, Integer year, Date maturityDate);
		
		public Interest deleteByDepositIdAndDate(Long depositId);
		
		public HashMap<String, Double> getTotInterestAddedToPrincipalByFinancialYear(Long depositId);
		
		public Double getTotInterestGivenForPeriod(Long depositId, Date fromDate, Date toDate);
		
		public List<Interest> getByDate(Date fromDate, Date toDate, Long depositId);
		 
		public Interest getMaxInterest(Long depositId, Date date);
		
		public HashMap<String, Double> getTotInterestOfFinancialYear(Long depositId);
		
		public Double getTotAnnualInterestAmount(Long depositId, String financialYear);
	
		public Interest getPreviousInterest(Long depositId, Date fromDate);
		
		public Double getTotInterestGiven(Long depositId, String financialYear);
		
		public Double getTotProjectedInterest(Long depositId, String financialYear);
		
		public Interest getLastInterestCompounded(Long depositId);
		
		public Double getTotalInterestAccumulated(Long depositId, Date fromDate, Date toDate);
		
		public List<Interest> getInterestListGivenForPeriod(Long depositId, Date fromDate, Date toDate);
		
		public Interest deleteByDepositIdAndCurrentDate(Long depositId , Date currentDate);
  }
