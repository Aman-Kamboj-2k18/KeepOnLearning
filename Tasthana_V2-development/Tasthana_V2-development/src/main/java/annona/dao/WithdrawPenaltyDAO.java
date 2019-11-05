package annona.dao;


import java.util.Date;
import java.util.List;

import annona.domain.WithdrawPenaltyAmountBased;
import annona.domain.WithdrawPenaltyDetails;
import annona.domain.WithdrawPenaltyMaster;
import annona.domain.WithdrawPenaltyTenureBased;
import annona.form.WithdrawPenaltyForm;



public interface WithdrawPenaltyDAO 
 {

	public List<Date> getEffectiveDates();
	
	public List<Object[]> getSlabBasedWithdrawPenalties(Date effectiveDate, Long productConfigurationId, Integer isPremature);
	
	//public WithdrawPenaltyAmountBased findById(Long id);
	//public WithdrawPenaltyAmountBased update(WithdrawPenaltyAmountBased details);
	public List<Long> getByDates(Date date);
	
	
	public WithdrawPenaltyMaster insertWithdrawPenaltyMaster(WithdrawPenaltyMaster withdrawPenaltyMaster);
	
	public WithdrawPenaltyAmountBased saveWithdrawPenaltyAmountBased(WithdrawPenaltyAmountBased withdrawPenaltyAmountBased );
	public WithdrawPenaltyDetails saveWithdrawPenaltyDetails(WithdrawPenaltyDetails withdrawPenaltyDetails );
	public Boolean isAmountToAndAmountFromRangeExist(WithdrawPenaltyForm withdrawPenaltyForm);
	public Boolean isAmountToAndAmountFromRangeExistNew(WithdrawPenaltyForm withdrawPenaltyForm);
	
	public WithdrawPenaltyTenureBased saveWithdrawPenaltyTenureBased(WithdrawPenaltyTenureBased withdrawPenaltyTenureBased);
	
	public WithdrawPenaltyMaster getWithdrawPenalyMaster(Long productConfigurationId, Boolean isPrematureWithdraw);
	
	public WithdrawPenaltyTenureBased getTenureBasedWithdrawPenalty(Long withdrawPenaltyMasterId);
	
	public WithdrawPenaltyAmountBased getAmountBasedWithdrawPenalty(Long withdrawPenaltyMasterId);
	public WithdrawPenaltyAmountBased updateWithdrawPenaltyAmountBased(WithdrawPenaltyAmountBased withdrawPenaltyAmountBased);
	
	public List<WithdrawPenaltyDetails> getWithdrawPenalyDetails(Long withdrawPenaltyMasterId);

	WithdrawPenaltyDetails updateWithdrawPenaltyDetails(WithdrawPenaltyDetails withdrawPenaltyDetails);
	
  }
