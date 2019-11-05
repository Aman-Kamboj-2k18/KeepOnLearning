package annona.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import annona.domain.DepositHolder;
import annona.domain.DepositHolderNominees;
import annona.domain.DepositModification;
import annona.domain.DepositModificationMaster;
import annona.domain.DepositRates;
import annona.domain.ModificationPenalty;

public interface DepositModificationDAO {

	public DepositModification saveDepositHolder(DepositModification depositHolder);
	
	List<Object[]> getDepositModification(Long depositId);
	
	public DepositModification getDepositHolderModification(Long depositHolderId);
	
	 public List<Object[]> getByDepositMonthly(Long depositId, String yearMonth);
	 
	 public List<Object[]> getByDepositQuaterly(Long depositId,  String yearQuarter);
	 
	 public List<Object[]> getByDepositYearly(Long depositId, Integer year);
	 
	 public DepositModification findByDepositId(Long depositId);
	 
	 public DepositModification getLastByDepositId(Long depositId);
	 
	 public Long getModificationCount (Long depositId, String duration);
	 
	 public Float getPreviousDepositRate(Long depositId, Date modifiedDate);
	 
	 public List<Object[]> getByDepositModificationInCurrentFinancialYear(Long depositId);
	 
	 public ModificationPenalty saveModificationPenalty(ModificationPenalty modificationPenalty);
	 
	 public ModificationPenalty getLastModificationPenalty(Long depositId);
	 
	 public DepositModification getPreviousModification(Long depositHolderId, Long modificationId);
	 
	 public List<DepositModification> getByModificationNo(String modificationNo);
	 
	 public List<Object[]> getAllModificationList(Long depositid);
	 
	 public Double getModifiedDepositAmount(Long depositId);
	 
	 public DepositModificationMaster saveDepositModificationMaster(DepositModificationMaster depositModificationMaster);
	 
	 public DepositModificationMaster updateDepositModificationMaster(DepositModificationMaster depositModificationMaster);
}
