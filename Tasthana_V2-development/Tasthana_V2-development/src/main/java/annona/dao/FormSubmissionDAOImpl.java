package annona.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.AccountDetails;
import annona.domain.FormSubmission;
import annona.form.DepositForm;
import annona.services.DateService;

@Repository
public class FormSubmissionDAOImpl implements FormSubmissionDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Method for entity manager
	 * 
	 * @return
	 */
	public EntityManager entityManager() {
		EntityManager em = new FormSubmissionDAOImpl().em;

		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public void insertFormSubmittedFor15HAnd15H(FormSubmission formSubmission) {
		em.persist(formSubmission);
	}

	@Transactional
	public void update(FormSubmission formSubmission) {
		em.merge(formSubmission);
	}

	public FormSubmission findcustomerId(Long customerId) {

		  if (customerId != null) {
		   Query query = em.createQuery("SELECT o FROM FormSubmission o where o.customerId =:customerId and o.financialYear =:financialYear");
		   query.setParameter("customerId", customerId);
		   query.setParameter("financialYear", DateService.getFinancialYear(DateService.getCurrentDate()));
		   @SuppressWarnings("unchecked")
		   List<FormSubmission> form = query.getResultList();
		   if (form.size() > 0)
		    return (FormSubmission) form.get(0);

		   else {
		    return null;
		   }

		  } else {
		   return null;
		  }
		 }
	
	public Boolean isFormSubmitted(Long customerId, String financialYear) {

		Query query = em.createQuery(
				"SELECT o FROM FormSubmission o where o.customerId =:customerId and o.financialYear =:financialYear");
		query.setParameter("customerId", customerId);
		query.setParameter("financialYear", financialYear);
		@SuppressWarnings("unchecked")
		List<FormSubmission> form = query.getResultList();
		if (form != null && form.size() > 0)
			return true;
		else {
			return false;
		}

	}
	
	public List<DepositForm> getDepositList(String accountNumber) {

		  // Query depositQuery = em.createQuery("SELECT
		  // d.accountNumber,d.id,c.id,c.customerName,c.dateOfBirth,c.address,c.email,c.contactNum
		  // from Deposit as d inner join d.depositholder dh inner join
		  // dh.customer c where d.accountNumber=:accountNumber");

		  EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
		  EntityManager antmgr = emfactory.createEntityManager();

		  String sql = "SELECT d.accountNumber,d.id depositId, c.id customerId,c.customerName,c.dateOfBirth,c.address,c.email,"
		    + " c.contactNum,dh.id as depositHolderId,d.approvalStatus,c.category from Deposit d inner join depositholder dh on d.id = dh.depositid inner join "
		    + " customerdetails c on dh.customerId = c.id where d.accountNumber=?1 and d.status='OPEN' ORDER BY d.id DESC";

		  Query depositQuery = antmgr.createNativeQuery(sql);
		  depositQuery.setParameter(1, accountNumber.trim());

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
		   String category = (String) depositList.get(i)[10];

		   /*
		    * System.out.println("d1..."+accNo+"d2..."+depositId+"d3.."+cusId+
		    * "d4.."+cusName+"d1..."+cusDOB+"d2..."+cusAdr+"d3.."+cusEmail+
		    * "d4.."+cusContact);
		    * 
		    */
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
		   depositForm.setCategory(category);
		   depsitFormList.add(depositForm);

		  }

		  return depsitFormList;

		 }

}
