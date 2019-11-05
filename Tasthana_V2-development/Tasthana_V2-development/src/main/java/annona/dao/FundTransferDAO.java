package annona.dao;

import java.util.Collection;

import annona.domain.FundTransfer;

public interface FundTransferDAO {
	
	
	/**
	 * Method to insert data
	 * @param fundTransfer
	 */
	public void insert(FundTransfer fundTransfer);
	
	/**
	 * Method to get total amount for FD
	 * @param fdId
	 * @return
	 */
	Double getTotalAmount(String fdId);
	
	/**
	 * Method to get total interest amount 
	 * @param fdId
	 * @return
	 */
	Double getTotalInterestAmount(String fdId);
	
	
	/**
	 * Method to get all data
	 * @return
	 */
	public Collection<FundTransfer> getAllData();
	
	
	/**
	 * Method to get specific FD data
	 * @param fdId
	 * @return
	 */
	public Collection<FundTransfer> getFDRatesData(String fdId);

}
