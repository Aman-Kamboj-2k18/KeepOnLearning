package annona.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.AccountDetails;
import annona.domain.Customer;
import annona.domain.DepositRates;
import annona.form.CustomerForm;

@Repository
public class AccountDetailsDAOImpl implements AccountDetailsDAO {

	@PersistenceContext
	private EntityManager em;


	@Transactional
	public void insertAccountDetails(AccountDetails accountDetails) {
		em.persist(accountDetails);
	}

	public CustomerForm editCustomerDetails(Long customerId) {
	     CustomerForm cust = new CustomerForm();

	     Query query = em.createQuery("SELECT id,category,customerName,gender,age,contactNum,altContactNum,"
	       + "email,altEmail,address,pincode,country,state,city,nomineeName,nomineeAge,"
	       + "nomineeAddress,nomineeRelationShip,dateOfBirth,pan,aadhar,userName,guardianName,"
	       + "guardianAge,guardianAddress,guardianRelationShip,"
	       + "guardianPan,guardianAadhar,nriAccountType,citizen FROM Customer where id=:id");

	     query.setParameter("id", customerId);
	     List<Object[]> customerDetail = query.getResultList();
	     for (int i = 0; i < customerDetail.size(); i++) {

	      Long d1 = (Long) customerDetail.get(i)[0];
	      String d2 = (String) customerDetail.get(i)[1];
	      String d3 = (String) customerDetail.get(i)[2];
	      String d4 = (String) customerDetail.get(i)[3];
	      String d5 = (String) customerDetail.get(i)[4];
	      String d6 = (String) customerDetail.get(i)[5];
	      String d7 = (String) customerDetail.get(i)[6];
	      String d8 = (String) customerDetail.get(i)[7];
	      String d9 = (String) customerDetail.get(i)[8];
	      String d10 = (String) customerDetail.get(i)[9];
	      String d11 = (String) customerDetail.get(i)[10];
	      String d12 = (String) customerDetail.get(i)[11];
	      String d13 = (String) customerDetail.get(i)[12];
	      String d14 = (String) customerDetail.get(i)[13];
	      String d15 = (String) customerDetail.get(i)[14];
	      String d16 = (String) customerDetail.get(i)[15];
	      String d17 = (String) customerDetail.get(i)[16];
	      String d18 = (String) customerDetail.get(i)[17];
	      Date d19 = (Date) customerDetail.get(i)[18];
	      String d20 = (String) customerDetail.get(i)[19];
	      Long d21 = (Long) customerDetail.get(i)[20];
	      String d22 = (String) customerDetail.get(i)[21];
	      
	      String d23 = (String) customerDetail.get(i)[22];
	      String d24 = (String) customerDetail.get(i)[23];
	      String d25 = (String) customerDetail.get(i)[24];
	      String d26 = (String) customerDetail.get(i)[25];
	      String d27 = (String) customerDetail.get(i)[26];
	      Long d28 =  (Long)customerDetail.get(i)[27];
	      String d29 = (String) customerDetail.get(i)[28];
	      String d30 = (String) customerDetail.get(i)[29];
	      
	      cust.setId(d1);
	      cust.setCategory(d2);
	      cust.setCustomerName(d3);
	      cust.setGender(d4);
	      cust.setAge(d5);
	      cust.setContactNum(d6);
	      cust.setAltContactNum(d7);
	      cust.setEmail(d8);
	      cust.setAltEmail(d9);
	      cust.setAddress(d10);
	      cust.setPincode(d11);
	      cust.setCountry(d12);
	      cust.setState(d13);
	      cust.setCity(d14);
	      cust.setNomineeName(d15);
	      cust.setNomineeAge(d16);
	      cust.setNomineeAddress(d17);
	      cust.setNomineeRelationShip(d18);
	      cust.setDateOfBirth(d19);
	      cust.setPan(d20);
	      cust.setAadhar(d21);
	      cust.setUserName(d22);
	      cust.setGuardianName(d23);
	      cust.setGuardianAge(d24);
	      cust.setGuardianAddress(d25);
	      cust.setGuardianRelationShip(d26);
	      cust.setGuardianPan(d27);
	      cust.setGuardianAadhar(d28);
	      cust.setNriAccountType(d29);
	      cust.setCitizen(d30);
	     }

		  Query queryAccount = em.createQuery(
		    "SELECT a.accountType,a.accountNo,a.accountBalance,a.id, a.minimumBalance from AccountDetails a where a.customerID=:customerId and a.isActive=:isActive");
		  queryAccount.setParameter("customerId", customerId);
		  queryAccount.setParameter("isActive", 1);

		  List<Object[]> accountDetail = queryAccount.getResultList();

		  List<AccountDetails> accDetailsList = new ArrayList<AccountDetails>();

		  for (int i = 0; i < accountDetail.size(); i++) {
		   String accType = (String) accountDetail.get(i)[0];
		   String accNo = (String) accountDetail.get(i)[1];
		   Double accBalance = (Double) accountDetail.get(i)[2];
		   Long id = (Long) accountDetail.get(i)[3];
		   Double minimumBalance = (Double) accountDetail.get(i)[4];
		   AccountDetails accDetail = new AccountDetails();
		   accDetail.setAccountBalance(accBalance);
		   accDetail.setAccountNo(accNo);
		   accDetail.setAccountType(accType);
		   accDetail.setId(id);
		   accDetail.setMinimumBalance(minimumBalance);
		   accDetailsList.add(accDetail);

		  }

		  cust.setAccountDetails(accDetailsList);

		  return cust;

		 }
	

    @Transactional
    public void updateUserAccountDetails(AccountDetails accountDetails) {
		em.merge(accountDetails);
		em.flush();
	}

	/*
	 * @Transactional public void deleteAccountById(Long accountId) { Query
	 * deleteQuery = em.createQuery(
	 * "delete From AccountDetails a where a.id=:accountId");
	 * deleteQuery.setParameter("accountId", accountId);
	 * deleteQuery.executeUpdate(); }
	 */
	public List<AccountDetails> findByCustomerId(Long customerId) {

		Query query = em.createQuery(
				"SELECT o FROM AccountDetails o where o.customerID =:customerId and o.isActive=:isActive ");
		query.setParameter("customerId", customerId);
		query.setParameter("isActive", 1);

		return query.getResultList();
	}

	public List<Object[]> getCustomersSavingAccounts(Integer minBalanceInSavingAcc) {

		String sql = "SELECT c.id,c.category,c.customerName,c.gender,c.age,c.contactNum,c.altContactNum, "
				+ "c.email,c.altEmail,c.address,c.pincode,c.country,c.state,c.city,c.nomineeName,c.nomineeAge, "
				+ "c.nomineeAddress,c.nomineeRelationShip, a.id accountdetailsId, a.accountNo, a. accountBalance, "
				+ "a.accountType FROM CustomerDetails c inner join accountdetails a "
				+ "on c.id = a.customerid and upper(a.accountType)='SAVINGS' and a.accountBalance >:minBalanceInSavingAcc and a.isActive=:isActive";

		Query query = em.createNativeQuery(sql);
		// Query query = em.createQuery(sql);
		query.setParameter("minBalanceInSavingAcc", minBalanceInSavingAcc);
		query.setParameter("isActive", 1);

		return query.getResultList();
	}

	public AccountDetails findByAccountNo(String accountNo) {

		if (accountNo != "") {
			Query query = em.createQuery("SELECT o FROM AccountDetails o where o.accountNo =:accountNo and o.isActive=:isActive");
			query.setParameter("accountNo", accountNo);
			query.setParameter("isActive", 1);

			@SuppressWarnings("unchecked")
			List<AccountDetails> accDetailsList = query.getResultList();
			if (accDetailsList.size() > 0)
				return (AccountDetails) accDetailsList.get(0);

			else {
				return null;
			}

		} else {
			return null;
		}
	}

	public List<AccountDetails> findCurrentSavingByCustId(Long customerId) {

		Query query = em.createQuery(
				"SELECT o FROM AccountDetails o where o.customerID =:customerId and (o.accountType=:savings OR o.accountType=:current) and o.isActive=:isActive order by o.accountType desc");
		query.setParameter("customerId", customerId);
		query.setParameter("savings", "Savings");
		query.setParameter("current", "Current");
		query.setParameter("isActive", 1);
		return query.getResultList();
	}

	public AccountDetails findSavingByCustId(Long customerId) {

		Query query = em.createQuery(
				"SELECT o FROM AccountDetails o where o.customerID =:customerId and o.accountType=:saving and o.isActive=:isActive");
		query.setParameter("customerId", customerId);
		query.setParameter("saving", "Savings");
		query.setParameter("isActive", 1);
		List<AccountDetails> accDetailsList = query.getResultList();
		if (accDetailsList.size() > 0) {
			return accDetailsList.get(0);
		} else {
			return null;
		}

	}

	public AccountDetails findById(Long id) {
		if (id == null)
			return null;
		return em.find(AccountDetails.class, id);

	}
	
	public List<AccountDetails> findCurrentAndSavingAndDepositByCustomerIdAndIsActive(Long customerId) {

		Query query = em.createQuery(
				"SELECT o FROM AccountDetails o where o.customerID =:customerId and (o.accountType=:savings OR o.accountType=:current OR o.accountType='Deposit') and o.isActive=:isActive order by o.accountType desc");
		query.setParameter("customerId", customerId);
		query.setParameter("savings", "Savings");
		query.setParameter("current", "Current");
		query.setParameter("isActive", 1);
		return query.getResultList();
	}
}
