package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.BankPaymentDetails;
import annona.domain.ModeOfPayment;

@Repository
public class ModeOfPaymentDAOImpl implements ModeOfPaymentDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public EntityManager entityManager() {
		EntityManager em = new ModeOfPaymentDAOImpl().em;

		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public void save(ModeOfPayment modeOfPayment) {
		// TODO Auto-generated method stub
		em.persist(modeOfPayment);
	}

	@Transactional
	public void update(ModeOfPayment modeOfPayment) {
		// TODO Auto-generated method stub
		em.merge(modeOfPayment);
		em.flush();
	}

	@Override
	public List<ModeOfPayment> getAllModeOfPaymentDetails() {
		// TODO Auto-generated method stub
		List<ModeOfPayment> modeOfPayment = (List<ModeOfPayment>) em.createQuery("from ModeOfPayment").getResultList();

		return modeOfPayment;
	}
	
	
	@Override
	public List<ModeOfPayment> getAllModeOfPaymentEmployee() {

		Query query = em.createQuery("SELECT o FROM ModeOfPayment o where o.isVisibleInBankSide='1'");

		List<ModeOfPayment> list = query.getResultList();
		if (list != null && list.size() > 0)
			return (List<ModeOfPayment>) list;
		else
			return null;

	}
	
	@Override
	public List<ModeOfPayment> getAllModeOfPaymentCustomer() {

		Query query = em.createQuery("SELECT o FROM ModeOfPayment o where o.isVisibleInCustomerSide='1'");

		List<ModeOfPayment> list = query.getResultList();
		if (list != null && list.size() > 0)
			return (List<ModeOfPayment>) list;
		else
			return null;

	}

	@Override
	public ModeOfPayment getModeOfPaymentById(Long id) {
		// TODO Auto-generated method stub
		ModeOfPayment  modeOfPayment =  (ModeOfPayment) em.createQuery("from ModeOfPayment where id=?").setParameter(1, id).getSingleResult();
		return modeOfPayment;
	}
	
	@Override
	public ModeOfPayment getModeOfPayment(String paymentMode) {
		// TODO Auto-generated method stub
		paymentMode = paymentMode.toLowerCase();
		Query query  =  em.createQuery("select o from ModeOfPayment o where lower(o.paymentMode)=?");
		return (ModeOfPayment)query.setParameter(1, paymentMode.toLowerCase()).getSingleResult();

	}


	@Override
	public Long getCountOfPaymentMode(String paymentMode) {
		// TODO Auto-generated method stub
		paymentMode = paymentMode.toLowerCase();
		Long count = (Long) em
				.createQuery("SELECT COUNT(*) FROM ModeOfPayment WHERE lower(paymentMode)=?")
		.setParameter(1, paymentMode).getSingleResult();
		
		return count;
	}
	
	@Override
	public Boolean getModeOfPaymentExist(String paymentMode, Long id) {
		// TODO Auto-generated method stub
		paymentMode = paymentMode.toLowerCase();
		Query qry = em.createQuery("Select o from ModeOfPayment o where lower(paymentMode)=? and id!=?");		
		qry.setParameter(1, paymentMode.toLowerCase());
		qry.setParameter(2, id);
		List lst = qry.getResultList();
		if(lst!=null && lst.size() > 0)
			return true;
		else
			return false;
	}
	

}
