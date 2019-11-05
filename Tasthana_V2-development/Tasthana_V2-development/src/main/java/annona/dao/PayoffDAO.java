package annona.dao;

import java.util.List;

import annona.domain.Payoff;
import annona.domain.PayoffDetails;
import annona.domain.UnSuccessfulPayOff;


public interface PayoffDAO {

	public Payoff insertPayoff(Payoff payoff);
	
	public Payoff getPayoff(Long depositId) ;
	
	public PayoffDetails insertPayoffDetails(PayoffDetails payoffDetails);
	
	public List<PayoffDetails> getPayoffDetails(Long depositId);
	
	public UnSuccessfulPayOff insertUnSuccessfulPayOff(UnSuccessfulPayOff unSuccessfulPayOff);
	
	public List<PayoffDetails> payOffDetailsByDepositHolderId(Long depositHolderId);
	
	//public List<PayoffDetails> getPayOffDistribution(Long depositId);
	
//	public Double getTotalInterestFromLastPayOff(Long depositId, Long lastpayOffDistributionId);
//	
//	public PayoffDetails insertPayOffDistribution(PayoffDetails payOffDistribution);
//	
//	public List<PayoffDetails> payOffDistributionByDepositHolderId(Long depositHolderId);
//
//	 public Double getSumOffPayoffactualamountByDepositId(Long depositId);
//	 
//	 public List<PayoffDetails> payOffDistributionByDepositId(Long depositId);
}
