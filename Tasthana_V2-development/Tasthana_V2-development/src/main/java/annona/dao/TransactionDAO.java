package annona.dao;

import java.util.Collection;

import annona.domain.Transaction;

public interface TransactionDAO {

	/**
	 * Method to insert data
	 * @param transaction
	 */
	public void insertTransaction(Transaction transaction);
	
	/**
	 * Method to get List
	 * @return
	 */
	public Collection<Transaction> getList();
	
	/**
	 * Method get fd Data
	 * @param fdId
	 * @return
	 */
	public Collection<Transaction> getFdData(String fdId);
	
	

}
