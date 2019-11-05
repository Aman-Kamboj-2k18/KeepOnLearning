package annona.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import annona.domain.DepositHolderWiseDistribution;


public interface DepositHolderWiseDistributionDAO {

	public DepositHolderWiseDistribution saveDepositHolderWiseDistribution(DepositHolderWiseDistribution depositHolderWiseDistribution);
	
	public List<DepositHolderWiseDistribution> getDepositHolderWiseDistributionList(Long depositId) ;
	
	public List<DepositHolderWiseDistribution> getCustomerWiseDepositHolderDistribution(Long depositId, Long customerId) ;
	
	public List<DepositHolderWiseDistribution> getByCustomerIdAndDepositId(Long depositId, Long customerId);
	
	public DepositHolderWiseDistribution getHolderWiseDistribution(Long depositId, Long depositHolderId, Date distributionDate);
	
	public DepositHolderWiseDistribution getDepositHolderWiseLastDistribution(Long depositId, Long depositHolderId);
	
	public List<Object[]> getDepositHolderWiseDistributionByDepositHolderId(Long depositHolderId);
	
	public List<Object[]> getDepositHolderWiseDistribution(Long depositId);
	
}
