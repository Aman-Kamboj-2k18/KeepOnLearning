package annona.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.TypedQuery;

import annona.domain.Customer;
import annona.domain.CustomerCategory;
import annona.form.CustomerForm;

public interface CustomerDAO {

	/**
	 * Method to merge User details
	 */
	public Customer updateUser(Customer customer);
	
	/**
	 * Method to save User details
	 */
	public Customer insertCustomer(Customer customerHead);
	
	/**
	 * Method to get User details by status Approved
	 * @return
	 */
	public Collection<Customer> findAllCustomers();
	
	/**
	 * Method to get pending User details
	 * @return
	 */
	public TypedQuery<Customer> getByPending();
	
	/**
	 * Method to get id User details
	 * @param id
	 * @return
	 */
	public Customer getById(Long id);
	
	/**
	 * Method to get id User details
	 * @param userId
	 * @return
	 */
	public TypedQuery<Customer> getByUserId(String userId);
	
	/**
	 * Method to find account User details
	 * @param accNo
	 * @return
	 */
	public TypedQuery<Customer> findAccountNO(String accNo);
	
	/**
	 * Method to get User details
	 * @param userId
	 * @return
	 */
	public TypedQuery<Customer> updateByUserID(String customerId,float balance);
	
	/**
	 * Method to get customer age
	 * @param cusmId
	 * @return
	 */
	public Customer getAge(String cusmId);
	/**
	 * Method to get User details
	 * @return
	 */	
	public List<CustomerForm> searchCustomer(String customerId,String customerName, String contactNum, 
			String email);
	
	public List<CustomerForm> searchSecondaryCustomer(String customerName,String contactNum,
			   String email,String customerID,String accountNo);
	
	public Customer getByUserName(String userName);
	
	 public void insertCustomerCategory(CustomerCategory addCustomerCategory);
	 
	 
	 public void updateCustomerCategory(CustomerCategory customerCategory);
	 
	 
	 public List<CustomerCategory> getAllCustomerCategory();
	 
	 public Long getCountOfCategory(String customerCategory);
	 
	 public CustomerCategory getCustomerCategoryById(Long id);
	 
	 public List<CustomerCategory> getAllActiveCustomerCategory();
	 
	 public Long getCustomerByPanCard(String pancardNumber);
	 
	 public Long getCustomerByAadharCard(Long aadharcardNumber);
	 
	 public Long getNomineeByPanCard(String pancardNumber);
	 
	 public Long getNomineeByAadharCard(Long aadharcardNumber);
	 
	 public Long getCustomerByPanCardForEdit(String pancardNumber,Long id);
	 
	 public Long getCustomerByAadharCardForEdit(Long aadhar,Long id);
	 
	 public Customer getCustomerByUserName(String userName);
	 
	 public Long getAadharCardForValidation(Long aadharcardNumber);
	 
	 public Long getPanCardForValidation(String pancardNumber);
	 
	 public List<Customer> findCustomer(String customerId,String customerName, String contactNum, String email);
	 
	 public List<Customer> findCustomers();
	 public List<CustomerCategory> findAll();
}
