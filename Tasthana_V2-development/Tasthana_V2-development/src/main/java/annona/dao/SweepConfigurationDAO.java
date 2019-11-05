package annona.dao;

import annona.domain.SweepConfiguration;
import annona.domain.SweepInFacilityForCustomer;


public interface SweepConfigurationDAO 
 {
	 /**
	 * Method to save User  account details
	 */
	 public SweepConfiguration getActiveSweepConfiguration();
	
	 public SweepConfiguration insertSweepConfiguration(SweepConfiguration sweepConfiguration);
	
	 public SweepConfiguration findById(Long id);
	 
	 public SweepInFacilityForCustomer getSweepInFacilityForCustomer(Long customerId);
	 
	 public SweepInFacilityForCustomer insertSweepInFacilityForCustomer(SweepInFacilityForCustomer sweepInFacilityForCustomer);
	 
	 public SweepConfiguration update(SweepConfiguration sweepConfiguration);
		
	 public SweepConfiguration save(SweepConfiguration sweepConfiguration);
	 
  }
