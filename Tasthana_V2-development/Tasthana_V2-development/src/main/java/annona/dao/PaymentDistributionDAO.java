package annona.dao;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import annona.domain.Payment;
import annona.domain.Deposit;
import annona.domain.Distribution;
import annona.domain.PayoffDetails;
import annona.domain.WithdrawDeposit;


public interface PaymentDistributionDAO {
	
	
		public Distribution insertPaymentDistribution(Distribution paymentDistribution) ;
		
		public Distribution getLastPaymentDistribution(Long depositId);
		
		//public List<Distribution> getPaymentDistributions(Long depositId);
		
		/**
		 * Method to get getLastPaymentDistribution for deposit/withdraw
		 * @param depositId
		 * @return
		 */
		public Distribution getLastInterestCalculated(Long depositId);
		

		/**
		 * Method to get getLastPaymentDistributionForInterestCalc for Interest calculation
		 * @param depositId
		 * @return
		 */
		public List<Distribution> getAllDepWithdrawFromLastInterestCalc(Long depositId, Long lastInterestDistributionId);

		/**
		 * Method to insert withdraw amount
		 * @param depositId
		 * @return
		 */
		public WithdrawDeposit insertWithDrawPayment(WithdrawDeposit withdrawDeposit) ;
		
		public WithdrawDeposit getLastWithdraw(Long depositId);
		
		public void closeDeposit(Deposit deposit);
		
		public List<Distribution> getPaymentDistributions(Long depositId, Date fromDate);
		
		public Distribution getLastDistributionOfPreviousMonth(Long depositId, Date fromDate);
		
		public Double getTotalInterest(Long depositId); 
		
		public List<Distribution> getCurrentQuarterInterestDistribution(Long depositId);
		
		public List<Distribution> getHalfYearlyInterestDistribution(Long depositId, Date depositDate);
		
		public Distribution getLastAdjustment(Long depositId);
		
		public List<Distribution> getAllDistributions(Long depositId);
		
		public List<Distribution> getDistributionListFrom(Long depositId, Long fromDistributionId);
		
		public Distribution getLastInterestCalculatedOrAdjusted(Long depositId, Date toDate);
		
		public Double getTotInterestGivenForPeriod(Long depositId, Date fromDate, Date toDate);
		
		public Distribution getLastBaseLineOf(Long depositId, Long distributionId);
		
		public Distribution getLastBaseLine(Long depositId);
		
		public List<Distribution> getDistributionsFromPreviousInterest(Long depositId, Long lastBaseLineId);
		
		public Distribution getLastAdjustmentFor7Days(Long depositId);
		
		public Double[] getTotalInterestGivenFromBaseLine(Long depositId, long lastBaseLineId);
		
		public Distribution getLastBaseLineForWithdraw(Long depositId, Long withdrawDistributionId);
		
		public Distribution getLastBaseLineForTenureChange(Long depositId);
		
		public List<Distribution> getInterestsGivenForPeriod(Long depositId, Date fromDate, Date toDate);
		
		public Distribution updatePaymentDistribution(Distribution distribution);
		
		public Distribution getLastTopup(Long depositId, Long toDistributionId);
		
		public Distribution getLastInterestCalculatedBeforeAdjustment(Long depositId, Long fromDistributionId);
		
		public Distribution getFirstAdjustmentAfterLastInterest(Long depositId);
		
		public List<Distribution> getAllTransactionsExceptInterestFromLastInterestCalc(Long depositId, Long lastInterestDistributionId);
		
		public Distribution getPreviousDistribution(Long depositId, Long lastBaseLineId);
}
