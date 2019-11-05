package annona.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.AccountDetails;
import annona.domain.BankPaid;
import annona.domain.BankPaidDetails;
import annona.domain.BankPayment;
import annona.domain.BankPaymentDetails;
import annona.domain.Customer;
import annona.domain.DDCheque;
import annona.domain.DepositHolderNominees;
import annona.domain.DepositRates;
import annona.domain.Interest;
import annona.form.BankPaymentForm;

@Repository
public class BankPaymentDAOImpl implements BankPaymentDAO {

	@PersistenceContext
	EntityManager em;


	public BankPayment saveBankPayment(BankPayment bankPayment) {
		em.persist(bankPayment);
		return bankPayment;
	}

	public DDCheque saveDDCheque(DDCheque cheque) {
		em.persist(cheque);
		return cheque;
	}

	@Transactional
	public BankPayment saveBankPaymentForNominee(BankPayment bankPayment) {
		em.persist(bankPayment);
		return bankPayment;
	}

	@Override
	public BankPayment getBankPayment(Long depositId) {

		Query query = em.createQuery("SELECT o FROM BankPayment o where o.depositID =" + depositId);

		List list = query.getResultList();
		if (list != null && list.size() > 0)
			return (BankPayment) list.get(0);
		else
			return null;
	}

	@Override
	public BankPaymentDetails getBankPaymentByDepositHolderId(Long depositHolderId) {

		Query query = em.createQuery("SELECT o FROM BankPaymentDetails o where o.depositHolderID =" + depositHolderId);

		List list = query.getResultList();
		if (list != null && list.size() > 0)
			return (BankPaymentDetails) list.get(0);
		else
			return null;

	}

	
	  public BankPaymentDetails saveBankPaymentDetails(BankPaymentDetails bankPaymentDetails) {
		em.persist(bankPaymentDetails);
		return bankPaymentDetails;
	 }
	
	
	public BankPaid saveBankPaid(BankPaid bankPaid) {
		em.persist(bankPaid);
		return bankPaid;
	}
	
	@Transactional
	public BankPaidDetails saveBankPaidDetails(BankPaidDetails bankPaidDetails) {
		em.persist(bankPaidDetails);
		return bankPaidDetails;
	 }

	@Transactional
	public void updateBankPaymentDetails(BankPaymentDetails bankPaymentDetails) {
		em.merge(bankPaymentDetails);
		em.flush();
	}
	
	public BankPaymentDetails getBankPaymentDetails(Long bankPaymentDetailsId){
		Query query = em.createQuery("SELECT o FROM BankPaymentDetails o where o.id =" + bankPaymentDetailsId);

		List list = query.getResultList();
		if (list != null && list.size() > 0)
			return (BankPaymentDetails) list.get(0);
		else
			return null;
	}
	
	@Override
	 public List<BankPaymentForm> getBankPaymentDetailsByAccountNo(String accountNumber) {

	  EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
	  EntityManager antmgr = emfactory.createEntityManager();

	 
	  String sql = "SELECT c.customerName,bp.ispaid,bp.amountPaidDate,bp.amount,dn.nomineeName,"
	    + " dh.id as depositHolderId,bpd.nomineeId from Deposit d inner join bankpaymentdetails bp on d.id = bp.depositid "
	    + " inner join customerdetails c on bp.customerId = c.id  inner join  depositholder dh on dh.id=bp.depositHolderID"
	    + " inner join DepositHolderNominees dn on dn.depositholderid = bp.depositHolderID inner join bankpaiddetails bpd"
	    + " on dh.id = bpd.depositHolderID where d.accountNumber=?1"
	    + " and d.status='CLOSE' and bp.ispaid=1 ORDER BY d.id DESC";

	  //  and bp.ispaid is null
	  Query depositQuery = antmgr.createNativeQuery(sql);
	  depositQuery.setParameter(1, accountNumber);

	  @SuppressWarnings("unchecked")
	  List<Object[]> depositList = depositQuery.getResultList();

	  List<BankPaymentForm> bankPaymentFormList = new ArrayList<BankPaymentForm>();
	  for (int i = 0; i < depositList.size(); i++) {
	   BankPaymentForm bankPaymentForm = new BankPaymentForm();
	   String cusName = (String) depositList.get(i)[0];
	   Integer isPaid = (depositList.get(i)[1] == null) ? null :Integer.parseInt(depositList.get(i)[1].toString());
	   Date paymentDate = (Date) depositList.get(i)[2];
	   Double amount = Double.parseDouble(depositList.get(i)[3].toString());
	   String nomineeName = (String) depositList.get(i)[4];
	   Long depositHolderId = Long.parseLong(depositList.get(i)[5].toString());
	   Long nomineeId = (depositList.get(i)[6] == null) ? null : Long.parseLong(depositList.get(i)[6].toString());
	   
	   bankPaymentForm.setCustomerName(cusName);
	   bankPaymentForm.setIsPaid(isPaid);
	   bankPaymentForm.setAmountPaidDate(paymentDate);
	   bankPaymentForm.setAmount(amount);
	   bankPaymentForm.setNomineeName(nomineeName);
	   bankPaymentForm.setDepositHolderId(depositHolderId);
	   bankPaymentForm.setNomineeId(nomineeId);
	   bankPaymentFormList.add(bankPaymentForm);

	  }

	  return bankPaymentFormList;

	 }

//	@Override
//	public BankPayment findByDepositId(Long depositId) {
//		Query query = em.createQuery("SELECT o FROM BankPayment o where o.depositId =" + depositId);
//
//		List list = query.getResultList();
//		if (list != null && list.size() > 0)
//			return (BankPayment) list.get(0);
//		else
//			return null;
//	}

	
	
	@Override
	public BankPaid findByBankPaymentId(Long bankPaymentId) {
		Query query = em.createQuery("SELECT o FROM BankPaid o where o.bankPaymentId =" + bankPaymentId);
		List list = query.getResultList();
		if (list != null && list.size() > 0)
			return (BankPaid) list.get(0);
		else
			return null;
	}
}
