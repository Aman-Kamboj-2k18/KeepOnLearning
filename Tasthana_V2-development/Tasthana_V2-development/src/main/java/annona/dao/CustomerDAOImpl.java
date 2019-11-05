package annona.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.Customer;
import annona.domain.CustomerCategory;
import annona.domain.DepositHolder;
import annona.domain.Distribution;
import annona.form.CustomerForm;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Method for entity manager
	 * 
	 * @return
	 */


	EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");

	EntityManager antmgr = emfactory.createEntityManager();

	@Transactional
	public Customer updateUser(Customer customer) {
		em.merge(customer);
		em.flush();
		return customer;
	}

	@Transactional
	public Customer insertCustomer(Customer customer) {
		em.persist(customer);
		return customer;
	}

	@SuppressWarnings("unchecked")
	public Collection<Customer> findAllCustomers() {
		Query query = em.createQuery("SELECT o FROM Customer o order by o.id ");

		return (Collection<Customer>) query.getResultList();
	}

	@Override
	public TypedQuery<Customer> getByPending() {

		TypedQuery<Customer> q = em.createQuery(
				"SELECT o FROM Customer o where o.status = 'Pending' or o.status = 'Closed' or o.status = 'Suspended' ",
				Customer.class);
		return q;

	}

	@Override
	public Customer getById(Long id) {
		if (id == null)
			return null;
		return em.find(Customer.class, id);
	}

	@Override
	public TypedQuery<Customer> getByUserId(String userName) {
		TypedQuery<Customer> q = em.createQuery("SELECT o FROM Customer o where o.userName =:userName", Customer.class);
		q.setParameter("userName", userName);
		return q;
	}

	/*
	 * @Override public Customer getByUserName(String userName) { Query q =
	 * em.createQuery("SELECT o FROM Customer o where o.userName =:userName");
	 * q.setParameter("userName", userName); return (Customer)
	 * q.getResultList().get(0); }
	 */

	@SuppressWarnings("unchecked")
	public Customer getByUserName(String userName) {
		Query query = em.createQuery("SELECT o FROM Customer o where o.userName =:userName");
		System.out.println("query...." + query);
		try {
			query.setParameter("userName", userName);

			List detail = query.getResultList();
			if (detail.size() > 0)
				return (Customer) detail.get(0);
			else {
				query = em.createQuery("SELECT o FROM Customer o where o.customerName =:userName");
				query.setParameter("userName", userName);

				detail = query.getResultList();
				if (detail.size() > 0)
					return (Customer) detail.get(0);
				else
					return null;
			}

		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public TypedQuery<Customer> updateByUserID(String customerId, float balance) {
		TypedQuery<Customer> q = em.createQuery(
				"update Customer set accountBalance =:balance where customerId =:customerId ", Customer.class);
		q.setParameter("customerId", customerId);
		q.setParameter("balance", balance);
		return q;
	}

	@Override
	public TypedQuery<Customer> findAccountNO(String accNo) {
		TypedQuery<Customer> q = em.createQuery("SELECT o FROM Customer o where o.accountNo =:accNo", Customer.class);
		q.setParameter("accNo", accNo);
		return q;
	}

	@Override
	public Customer getAge(String cusmId) {

		Query query = em.createQuery("SELECT o FROM Customer o where o.customerID ='" + cusmId + "'");

		return (Customer) query.getSingleResult();

	}

	public List<CustomerForm> searchCustomer(String customerId, String customerName, String contactNum, String email) {
		customerId = customerId != null ? customerId.toLowerCase() : "";
		customerName = customerName != null ? customerName.toLowerCase() : "";
		email = email != null ? email.toLowerCase() : "";
		String whereString = this.buildWhereClause(customerId, customerName, contactNum, email);

		String sql = "SELECT o.id,o.customerName,o.email,o.contactNum, "
				+ "o.dateOfBirth from CustomerDetails as o inner join EndUser as e "
				+ "on o.id = e.customerId AND e.status ='Approved' where " + whereString;

		Query query = em.createNativeQuery(sql);
		if (customerName != "" || !customerName.isEmpty()) {
			query.setParameter("customerName", customerName.trim().toLowerCase() + "%");
		}

		if (customerId != "" || !customerId.isEmpty()) {
			query.setParameter("customerId", customerId.trim().toLowerCase());
		}

		if (email != "" || !email.isEmpty()) {
			query.setParameter("email", email.trim().toLowerCase());
		}
		if (contactNum != "" || !contactNum.isEmpty()) {
			query.setParameter("contactNum", contactNum.trim());
		}

		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();

		List<CustomerForm> cusFormList = new ArrayList<CustomerForm>();
		for (Object[] row : rows) {
			CustomerForm cusForm = new CustomerForm();
			cusForm.setId(Long.valueOf(row[0].toString()));
			cusForm.setCustomerName((String) row[1]);
			cusForm.setEmail((String) row[2]);
			cusForm.setContactNum((String) row[3]);
			cusForm.setDateOfBirth((Date) row[4]);
			/* cusForm.setAccountNo((String) row[5]); */

			cusFormList.add(cusForm);

		}
		return cusFormList;

	}

	public List<CustomerForm> searchSecondaryCustomer(String customerName, String contactNum, String email,
			String customerID, String accountNo) {
		customerID = customerID.toLowerCase();
		customerName = customerName.toLowerCase();
		email = email.toLowerCase();

		String whereString = this.buildWhereClause(customerID, customerName, contactNum, email);

		String sql = "SELECT o.id,o.customerName,o.email,o.contactNum, "
				+ "o.dateOfBirth from CustomerDetails as o inner join EndUser as e "
				+ "on o.transactionId = e.transactionId where " + whereString;

		Query query = em.createNativeQuery(sql);

		// Query query = em.createQuery("SELECT
		// o.id,o.customerName,o.email,o.contactNum,"
		// + "o.dateOfBirth,a.accountNo from Customer as o where " +
		// whereString);

		if (customerName != "" || !customerName.isEmpty()) {
			query.setParameter("customerName", customerName.trim().toLowerCase() + "%");
		}
		if (customerID != "" || !customerID.isEmpty()) {
			query.setParameter("customerId", customerID.trim().toLowerCase() + "%");
		}
		query.setParameter("accountNo", "");

		if (customerID != "") {
			query.setParameter("customerID", customerID);
		}
		if (email != "" || !email.isEmpty()) {
			query.setParameter("email", email);
		}
		if (contactNum != "" || !contactNum.isEmpty()) {
			query.setParameter("contactNum", contactNum);
		}

		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();

		List<CustomerForm> cusFormList = new ArrayList<CustomerForm>();
		for (Object[] row : rows) {
			CustomerForm cusForm = new CustomerForm();
			cusForm.setId(Long.valueOf(row[0].toString()));
			cusForm.setCustomerName((String) row[1]);
			cusForm.setEmail((String) row[2]);
			cusForm.setContactNum((String) row[3]);
			cusForm.setDateOfBirth((Date) row[4]);

			cusFormList.add(cusForm);

		}
		return cusFormList;

	}

	@SuppressWarnings("unchecked")
	public List<Customer> findCustomer(String customerId, String customerName, String contactNum, String email) {
		try {
			customerId = customerId != null ? customerId.toLowerCase() : customerId;
			customerName = customerName != null ? customerName.toLowerCase() : customerName;
			email = email != null ? email.toLowerCase() : email;
			String whereString = this.buildWhereClause(customerId, customerName, contactNum, email);

//		String sql = "SELECT o.id,o.customerName,o.email,o.contactNum, "
//				+ "o.dateOfBirth from CustomerDetails as o inner join EndUser as e "
//				+ "on o.transactionId = e.transactionId where " + whereString;

			// Query query = em.createNativeQuery(sql);

			String sql = "SELECT * from Customerdetails o where " + whereString;
			Query query = em.createNativeQuery(sql, Customer.class);

			if (customerName != "" || !customerName.isEmpty()) {
				query.setParameter("customerName", customerName.trim().toLowerCase() + "%");
			}
			if (customerId != "" || !customerId.isEmpty()) {
				query.setParameter("customerId", customerId.trim().toLowerCase());
			}
			if (email != "" || !email.isEmpty()) {
				query.setParameter("email", email.trim().toLowerCase());
			}
			if (contactNum != "" || !contactNum.isEmpty()) {
				query.setParameter("contactNum", contactNum.trim());
			}

			List<Customer> result = query.getResultList();
			return result;
		} catch (Exception exception) {
			exception.printStackTrace();

		}
		return null;

	}

	private String buildWhereClause(String customerId, String customerName, String contactNum, String email) {
		if (customerId == "" && customerName == "" && contactNum == "" && email == "")
			return "o.status='Approved'";

		String whereString = "";
		if (customerName != "") {
			whereString = " LOWER(o.customerName) LIKE :customerName AND ";
		}
		if (customerId != "") {
			whereString = whereString + " o.customerID=:customerId AND ";
		}
		/*
		 * if (customerID != "") { whereString = whereString +
		 * " o.customerID=:customerID AND "; }
		 */
		/*
		 * if (accountNo != "") { whereString = whereString +
		 * " a.accountNo=:accountNo AND "; }
		 */
		if (contactNum != "") {
			whereString = whereString + " o.contactNum=:contactNum AND ";
		}
		if (email != "") {
			whereString = whereString + " LOWER(o.email)=:email AND ";
		}
		whereString = whereString + " o.status='Approved'";

		return whereString;
	}

	@SuppressWarnings("unchecked")
	public List<CustomerCategory> getAllCustomerCategory() {
		Query query = em.createQuery("SELECT o FROM CustomerCategory o ");

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public CustomerCategory getCustomerCategoryById(Long id) {
		Query query = em.createQuery("SELECT o FROM CustomerCategory o where o.id =:id");

		try {
			query.setParameter("id", id);

			List detail = query.getResultList();
			if (detail.size() > 0)
				return (CustomerCategory) detail.get(0);
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}

	@Override
	public Long getCountOfCategory(String customerCategory) {

		Query query = em
				.createQuery("SELECT COUNT(*) FROM CustomerCategory o WHERE o.customerCategory=:customerCategory");
		query.setParameter("customerCategory", customerCategory);
		Long count = (Long) query.getResultList().get(0);
		return count;

	}

	@Transactional
	public void updateCustomerCategory(CustomerCategory updateCustomerCategory) {
		em.merge(updateCustomerCategory);
		em.flush();
	}

	@Transactional
	public void insertCustomerCategory(CustomerCategory addCustomerCategory) {
		em.persist(addCustomerCategory);
	}

	@SuppressWarnings("unchecked")
	public List<CustomerCategory> getAllActiveCustomerCategory() {
		Query query = em.createNativeQuery(
				"SELECT m.id, m.citizen,m.nriaccounttype,c.customercategory FROM citizenandcustomercategorymapping as m left join customercategory as c on m.customercategoryid=c.id");

		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();

		List<CustomerCategory> cusCategory = new ArrayList<CustomerCategory>();
		for (Object[] row : rows) {
			CustomerCategory cat = new CustomerCategory();
			cat.setId(Long.valueOf(row[0].toString()));
			cat.setCitizen((String) row[1]);
			cat.setNriAccountType((String) row[2]);
			cat.setCustomerCategory((String) row[3]);
			cusCategory.add(cat);

		}
		return cusCategory;

	}

	@Override
	public Long getCustomerByPanCard(String pancardNumber) {

		Query query = em.createQuery("SELECT COUNT(*) FROM Customer o WHERE o.pan=:pancardNumber");
		query.setParameter("pancardNumber", pancardNumber);

		Long count = (Long) query.getResultList().get(0);
		return count;

	}

	@Override
	public Long getCustomerByAadharCard(Long aadharcardNumber) {

		Query query = em.createQuery("SELECT COUNT(*) FROM Customer o WHERE o.aadhar=:aadharcardNumber");
		query.setParameter("aadharcardNumber", aadharcardNumber);

		Long count = (Long) query.getResultList().get(0);
		return count;

	}

	@Override
	public Long getNomineeByPanCard(String pancardNumber) {

		Query query = em.createQuery("SELECT COUNT(*) FROM DepositHolderNominees o WHERE o.nomineePan=:pancardNumber");
		query.setParameter("pancardNumber", pancardNumber);

		Long count = (Long) query.getResultList().get(0);
		return count;

	}

	@Override
	public Long getNomineeByAadharCard(Long aadharcardNumber) {

		Query query = em
				.createQuery("SELECT COUNT(*) FROM DepositHolderNominees o WHERE o.nomineeAadhar=:aadharcardNumber");
		query.setParameter("aadharcardNumber", aadharcardNumber);

		Long count = (Long) query.getResultList().get(0);
		return count;

	}

	@Override
	public Long getCustomerByPanCardForEdit(String pancardNumber, Long id) {

		Query query = em.createQuery("SELECT COUNT(*) FROM Customer o WHERE o.pan=:pancardNumber AND o.id!=:id");
		query.setParameter("pancardNumber", pancardNumber);
		query.setParameter("id", id);
		Long count = (Long) query.getResultList().get(0);
		return count;

	}

	@Override
	public Long getCustomerByAadharCardForEdit(Long aadhar, Long id) {

		Query query = em.createQuery("SELECT COUNT(*) FROM Customer o WHERE o.aadhar=:aadhar AND o.id!=:id");
		query.setParameter("aadhar", aadhar);
		query.setParameter("id", id);
		Long count = (Long) query.getResultList().get(0);
		return count;

	}

	@Override
	public Customer getCustomerByUserName(String userName) {

		Query query = em.createQuery("SELECT o FROM Customer o WHERE o.userName=:userName");
		query.setParameter("userName", userName);
		List lst = query.getResultList();

		if (lst != null && lst.size() > 0)
			return (Customer) lst.get(0);
		else {
			query = em.createQuery("SELECT o FROM Customer o where o.customerName =:userName");
			query.setParameter("userName", userName);

			lst = query.getResultList();
			if (lst.size() > 0)
				return (Customer) lst.get(0);
			else
				return null;
		}

	}

	@Override
	public Long getAadharCardForValidation(Long aadharcardNumber) {

		Query query = em
				.createQuery("SELECT COUNT(*) FROM DepositHolderNominees o WHERE o.nomineeAadhar=:aadharcardNumber");
		query.setParameter("aadharcardNumber", aadharcardNumber);

		Long count = (Long) query.getResultList().get(0);
		return count;

	}

	@Override
	public Long getPanCardForValidation(String pancardNumber) {

		Query query = em.createQuery("SELECT COUNT(*) FROM DepositHolderNominees o WHERE o.nomineePan=:pancardNumber");
		query.setParameter("pancardNumber", pancardNumber);

		Long count = (Long) query.getResultList().get(0);
		return count;

	}

	@SuppressWarnings("unchecked")
	public List<Customer> findCustomers() {
		Query query = em.createQuery("SELECT o FROM Customer o ");

		return (List<Customer>) query.getResultList();
	}

	public List<CustomerCategory> findAll() {
		try {
			Query query = em.createNativeQuery("SELECT * FROM customercategory", CustomerCategory.class);

			@SuppressWarnings("unchecked")
			List<CustomerCategory> rows = query.getResultList();

			if (rows.size() > 0) {
				return rows;
			}

			List<CustomerCategory> cusCategory = new ArrayList<CustomerCategory>();

			return cusCategory;

		} catch (Exception e) { // TODO: handle exception
		}
		return null;
	}

}