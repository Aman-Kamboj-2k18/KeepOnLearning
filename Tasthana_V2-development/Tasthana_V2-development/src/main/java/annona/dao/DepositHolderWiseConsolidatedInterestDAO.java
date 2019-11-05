package annona.dao;

import annona.domain.DepositSummaryHolderWise;
import annona.form.DepositForm;

public interface DepositHolderWiseConsolidatedInterestDAO {

	
	 public  DepositSummaryHolderWise getTotalInterestByDepositHoldeId(Long depositHolderId);

	 public DepositForm getTotalInterestAndCustomerNameByDepositHoldeId(Long  depositHolderId);
	 
	 public Double getConsolidatedInterest(Long depositId, Long customerId);
					
}
