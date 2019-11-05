package annona.dao;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import annona.domain.Payment;
import annona.form.BankPaymentForm;
import annona.form.DepositForm;

@Repository
public class paymentDAOImpl implements PaymentDAO {
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public List<DepositForm> withdrawAccountNumber(String accountNumber) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager antmgr = emfactory.createEntityManager();

		String sql = "SELECT d.accountNumber,d.id depositId, c.id customerId,c.customerName,c.dateOfBirth,c.address,c.email,"
				+ " c.contactNum,dh.id as depositHolderId,d.approvalStatus,d.depositCategory,d.newMaturityDate,d.depositClassification,"
				+ " d.taxSavingDeposit, d.primaryCitizen, d.primaryNRIAccountType, d.createdDate,d.productConfigurationId,d.accountAccessType,dh.deathCertificateSubmitted,dh.depositHolderStatus,o.overdraftNumber,o.withdrawableAmount,d.currentBalance,o.totalAmountWithdrawn" 
				+ " from Deposit d inner join  overdraftissue o on d.id=o.depositid inner join depositholder dh on o.depositid = dh.depositid inner join "
				+ " customerdetails c on dh.customerId = c.id where d.accountNumber=?1 and d.status='OPEN' ORDER BY d.id DESC";

		Query depositQuery = antmgr.createNativeQuery(sql);
		depositQuery.setParameter(1, accountNumber);

		@SuppressWarnings("unchecked")
		List<Object[]> depositList = depositQuery.getResultList();

		List<DepositForm> depsitFormList = new ArrayList<DepositForm>();
		for (int i = 0; i < depositList.size(); i++) {
			System.out.println(i);
			DepositForm depositForm = new DepositForm();
			String accNo = (String) depositList.get(i)[0];
			Long depositId = Long.parseLong(depositList.get(i)[1].toString());
			Long cusId = Long.parseLong(depositList.get(i)[2].toString());
			String cusName = (String) depositList.get(i)[3];
			Date cusDOB = (Date) depositList.get(i)[4];
			String cusAdr = (String) depositList.get(i)[5];
			String cusEmail = (String) depositList.get(i)[6];
			String cusContact = (String) depositList.get(i)[7];
			Long depositHolderId = Long.parseLong(depositList.get(i)[8].toString());
			String appStatus = (String) depositList.get(i)[9];
			String depositCategory = (String) depositList.get(i)[10];
			Date newMaturityDate = (Date) depositList.get(i)[11];
			String depositClassification  = (String) depositList.get(i)[12];
			String taxSavingDeposit = (String) depositList.get(i)[13];
			String primaryCitizen = (String) depositList.get(i)[14];
			String primaryNRIAccountType = (String) depositList.get(i)[15];
			Date createdDate = (Date) depositList.get(i)[16];
			//Long pcId = Long.parseLong(depositList.get(i)[17].toString());
			String pcId = (String)(depositList.get(i)[17].toString());
			String accountAccessType = (String)(depositList.get(i)[18]);
			String deathCertificateSubmitted =  (String)(depositList.get(i)[19]);
			String depositHolderStatus=  (String)(depositList.get(i)[20]);
			
			String overdraftNumber=  (String)(depositList.get(i)[21]);
			Double withdrawableAmount=  (Double)(depositList.get(i)[22]);
			Double withdrawnAmount=  (Double)(depositList.get(i)[24]);
			Double currentBalance=  (Double)(depositList.get(i)[23]);
			depositForm.setOverdraftNumber(overdraftNumber);
			depositForm.setWithdrawableAmount(withdrawableAmount-withdrawnAmount);
			
			depositForm.setAccountNumber(accNo);
			depositForm.setCurrentBalance(currentBalance);
			depositForm.setDepositId(depositId);
			depositForm.setCustomerId(cusId);
			depositForm.setCustomerName(cusName);
			depositForm.setDateOfBirth(cusDOB);
			depositForm.setAddress(cusAdr);
			depositForm.setEmail(cusEmail);
			depositForm.setContactNum(cusContact);
			depositForm.setDepositHolderId(depositHolderId);
			depositForm.setApprovalStatus(appStatus);
			depositForm.setDepositCategory(depositCategory);
			depositForm.setNewMaturityDate(newMaturityDate);
			depositForm.setDepositClassification(depositClassification);
			depositForm.setTaxSavingDeposit(taxSavingDeposit);
			depositForm.setPrimaryCitizen(primaryCitizen);
			depositForm.setPrimaryNRIAccountType(primaryNRIAccountType);
			depositForm.setCreatedDate(createdDate);
			depositForm.setProductConfigurationId(pcId);
			depositForm.setDeathCertificateSubmitted(deathCertificateSubmitted);
			depositForm.setAccountAccessType(accountAccessType);
			depositForm.setDepositHolderStatus(depositHolderStatus);
			depsitFormList.add(depositForm);

		}

		return depsitFormList;

	}
	
	@Override
	@Transactional
	public List<DepositForm> paymentAccountNumber(String accountNumber) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager antmgr = emfactory.createEntityManager();

		String sql = "SELECT d.accountNumber,d.id depositId, c.id customerId,c.customerName,c.dateOfBirth,c.address,c.email,"
				+ " c.contactNum,dh.id as depositHolderId,d.approvalStatus,d.depositCategory,d.newMaturityDate,d.depositClassification,"
				+ " d.taxSavingDeposit, d.primaryCitizen, d.primaryNRIAccountType, d.createdDate,d.productConfigurationId,d.accountAccessType,dh.deathCertificateSubmitted,dh.depositHolderStatus" 
				+ " from Deposit d inner join depositholder dh on d.id = dh.depositid inner join "
				+ " customerdetails c on dh.customerId = c.id where d.accountNumber=?1 and d.status='OPEN' ORDER BY d.id DESC";

		Query depositQuery = antmgr.createNativeQuery(sql);
		depositQuery.setParameter(1, accountNumber);

		@SuppressWarnings("unchecked")
		List<Object[]> depositList = depositQuery.getResultList();

		List<DepositForm> depsitFormList = new ArrayList<DepositForm>();
		for (int i = 0; i < depositList.size(); i++) {
			System.out.println(i);
			DepositForm depositForm = new DepositForm();
			String accNo = (String) depositList.get(i)[0];
			Long depositId = Long.parseLong(depositList.get(i)[1].toString());
			Long cusId = Long.parseLong(depositList.get(i)[2].toString());
			String cusName = (String) depositList.get(i)[3];
			Date cusDOB = (Date) depositList.get(i)[4];
			String cusAdr = (String) depositList.get(i)[5];
			String cusEmail = (String) depositList.get(i)[6];
			String cusContact = (String) depositList.get(i)[7];
			Long depositHolderId = Long.parseLong(depositList.get(i)[8].toString());
			String appStatus = (String) depositList.get(i)[9];
			String depositCategory = (String) depositList.get(i)[10];
			Date newMaturityDate = (Date) depositList.get(i)[11];
			String depositClassification  = (String) depositList.get(i)[12];
			String taxSavingDeposit = (String) depositList.get(i)[13];
			String primaryCitizen = (String) depositList.get(i)[14];
			String primaryNRIAccountType = (String) depositList.get(i)[15];
			Date createdDate = (Date) depositList.get(i)[16];
			//Long pcId = Long.parseLong(depositList.get(i)[17].toString());
			String pcId = (String)(depositList.get(i)[17].toString());
			String accountAccessType = (String)(depositList.get(i)[18]);
			String deathCertificateSubmitted =  (String)(depositList.get(i)[19]);
			String depositHolderStatus=  (String)(depositList.get(i)[20]);
			
			
			depositForm.setAccountNumber(accNo);
			depositForm.setDepositId(depositId);
			depositForm.setCustomerId(cusId);
			depositForm.setCustomerName(cusName);
			depositForm.setDateOfBirth(cusDOB);
			depositForm.setAddress(cusAdr);
			depositForm.setEmail(cusEmail);
			depositForm.setContactNum(cusContact);
			depositForm.setDepositHolderId(depositHolderId);
			depositForm.setApprovalStatus(appStatus);
			depositForm.setDepositCategory(depositCategory);
			depositForm.setNewMaturityDate(newMaturityDate);
			depositForm.setDepositClassification(depositClassification);
			depositForm.setTaxSavingDeposit(taxSavingDeposit);
			depositForm.setPrimaryCitizen(primaryCitizen);
			depositForm.setPrimaryNRIAccountType(primaryNRIAccountType);
			depositForm.setCreatedDate(createdDate);
			depositForm.setProductConfigurationId(pcId);
			depositForm.setDeathCertificateSubmitted(deathCertificateSubmitted);
			depositForm.setAccountAccessType(accountAccessType);
			depositForm.setDepositHolderStatus(depositHolderStatus);
			depsitFormList.add(depositForm);

		}

		return depsitFormList;

	}


	@Transactional
	public Payment insertPayment(Payment payment) {
		em.persist(payment);
		return payment;
	}

	/*
	 * createdby:Ravi pratap singh 05 jun 2017
	 * 
	 * Method to save PaymentDistribution
	 */
	@Transactional
	public void insertPaymentDistribution(Payment payment) {
		em.persist(payment);

	}

	@Override
	public List<BankPaymentForm> customerListForBankPayment(String accountNumber) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager antmgr = emfactory.createEntityManager();

	
		String sql = "SELECT d.accountNumber,d.id depositId, c.id customerId,c.customerName,c.dateOfBirth,c.address,"
				+ " c.email,c.contactNum,bp.id as bankpaymentdetailsid, bp.ispaid, bp.depositholderid,dh.DeathCertificateSubmitted,bp.bankPaymentId,d.depositClassification,d.taxSavingDeposit "
				+ " from Deposit d inner join bankpaymentdetails bp on d.id = bp.depositid inner join"
				+ " customerdetails c on bp.customerId = c.id  inner join  depositholder dh on dh.id=bp.depositHolderID"
				+ " where d.accountNumber=?1 and d.status='CLOSE' ORDER BY d.id DESC";

		//  and bp.ispaid is null
		Query depositQuery = antmgr.createNativeQuery(sql);
		depositQuery.setParameter(1, accountNumber);

		@SuppressWarnings("unchecked")
		List<Object[]> depositList = depositQuery.getResultList();

		List<BankPaymentForm> bankPaymentFormList = new ArrayList<BankPaymentForm>();
		for (int i = 0; i < depositList.size(); i++) {

			BankPaymentForm bankPaymentForm = new BankPaymentForm();
			String accNo = (String) depositList.get(i)[0];
			Long depositId = Long.parseLong(depositList.get(i)[1].toString());
			Long cusId = Long.parseLong(depositList.get(i)[2].toString());
			String cusName = (String) depositList.get(i)[3];
			Date cusDOB = (Date) depositList.get(i)[4];
			String cusAdr = (String) depositList.get(i)[5];
			String cusEmail = (String) depositList.get(i)[6];
			String cusContact = (String) depositList.get(i)[7];
			Long bankPaymentDetailsId = Long.parseLong(depositList.get(i)[8].toString());
			Integer isPaid = (depositList.get(i)[9] == null) ? null :Integer.parseInt(depositList.get(i)[9].toString());
			Long bankPaymentDepositHolderId = Long.parseLong(depositList.get(i)[10].toString());
			String deathCertificateSubmitted = (String) depositList.get(i)[11];
			Long bankPaymentId = Long.parseLong(depositList.get(i)[12].toString());
			String depositClassification = (String) depositList.get(i)[13];
			String taxSavingDeposit = (String) depositList.get(i)[14];
			
			bankPaymentForm.setAccountNumber(accNo);
			bankPaymentForm.setDepositId(depositId);
			bankPaymentForm.setCustomerId(cusId);
			bankPaymentForm.setCustomerName(cusName);
			bankPaymentForm.setDateOfBirth(cusDOB);
			bankPaymentForm.setAddress(cusAdr);
			bankPaymentForm.setEmail(cusEmail);
			bankPaymentForm.setContactNum(cusContact);
			bankPaymentForm.setIsPaid(isPaid);
			bankPaymentForm.setBankPaymnetDetailsId(bankPaymentDetailsId);
			bankPaymentForm.setDepositHolderId(bankPaymentDepositHolderId);
			bankPaymentForm.setDeathCertificateSubmitted(deathCertificateSubmitted);
			bankPaymentForm.setBankPaymentId(bankPaymentId);
			bankPaymentForm.setDepositClassification(depositClassification);
			bankPaymentForm.setTaxSavingDeposit(taxSavingDeposit);
			bankPaymentFormList.add(bankPaymentForm);
			

		}

		return bankPaymentFormList;

	}

	public Double getTotalPayment(Long depositId, Date fromDate, Date toDate) {
		HashMap<String, Double> totalInterestYearWise = new HashMap<>();

		Query query = em.createQuery(
				"select Sum(o.amountPaid) from Payment o where o.depositId=:depositId and o.paymentDate>=:fromDate and o.paymentDate<=:toDate");
		query.setParameter("depositId", depositId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		Object totalPayment = query.getSingleResult();
		if (totalPayment != null) {
			return Double.parseDouble(totalPayment.toString());
		} else
			return 0d;
	}

	public Payment getLastPaymentByDepositId(Long depositId) {
		Query query = em.createQuery("SELECT o FROM Payment o where o.depositId =:depositId order by o.id DESC");

		query.setParameter("depositId", depositId);
		List obj = (List) query.getResultList();
		if (!obj.isEmpty())
			return (Payment) obj.get(0);
		else
			return null;
		// return (Payment) query.getResultList().get(0);

	}
	
	public List<Payment> getPaymentDetails(Long depositId, Date fromDate, Date toDate) {

		Query query = em.createQuery(
				"select o from Payment o where o.depositId=:depositId and date(o.paymentDate)>=date(:fromDate) and date(o.paymentDate)<=date(:toDate)");
		query.setParameter("depositId", depositId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		List<Payment> paymentList = query.getResultList();
		return paymentList;
	}
	
	public Payment updatePayment(Payment payment)
	{
		em.merge(payment);
		em.flush();
		return payment;
	}
}
