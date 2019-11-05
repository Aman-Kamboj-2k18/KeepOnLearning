package annona.dao;

import java.util.Collection;
import java.util.Date;

import annona.domain.FDRates;

public interface FDRatesDAO {
	
	/**
	 * Method to save FD rates
	 * @param fdRates
	 */
	public void saveFD(FDRates fdRates);
    
	/**
	 * Method to get all FDs
	 * @return
	 */
	public Collection<FDRates> findAllFDs();
	
	/**
	 * Method to Customer all FDs
	 * @param userid
	 * @return
	 */
	public Collection<FDRates> findAllCustomerFDs(String userid);
	
	/**
	 * Method to Customer all FDs
	 * @param userid
	 * @return
	 */
	public Collection<FDRates> getFDRatesData(String fdId);
	
	
	/**
	 * Method to get sum of all FDs interest
	 * @param userid
	 * @param fdId
	 * @return
	 */
	Double getTotalAmount(String userid,String fdId);
	
	/**
	 * Method to get sum of all FDs amount
	 * @param userid
	 * @param fdId
	 * @return
	 */
	float getTotalFDAmount(String userid,String fdId);
	
	/**
	 * Method to get sum of all FDs amount
	 * @param userid
	 * @param fdId
	 * @return
	 */
	Date getMaxdate(String userid,String fdId);
	
	/**
	 * Method to get sum of all FDs amount
	 * @param userid
	 * @param fdId
	 * @return
	 */
	Date getMaxTenuredate(String userid,String fdId);
	
	/**
	 * Method to get all FD
	 * @param id
	 * @return
	 */
	public FDRates getById(Long id);
	
	
	/**
	 * Method to get FD Data
	 * @param fdID
	 * @return
	 */
	 Long getFDData(String fdID);
	
	/**Method to update
	 * @param fdRates
	 */
	public void update(FDRates fdRates);

}
