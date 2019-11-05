package annona.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.TypedQuery;

import annona.domain.Customer;
import annona.domain.Transaction;
import annona.domain.UploadedFile;

public interface UploadDAO {

	public void createUser(UploadedFile uploadFile);
	
	List<UploadedFile> getFilesByCustomerIdAndAccountNumber(Long customerId,String depositAccountNumber);
	
	public UploadedFile findId(Long id);
	
	public void deleteUploadedFile(Long id);
	
	 public List<UploadedFile> getByCustomerId(Long customerId);

}

