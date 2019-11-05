package annona.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.Transaction;
import annona.domain.UnSuccessfulPayOff;
import annona.domain.UnSuccessfulRecurringDeposit;

@Repository
public class UnSuccessfulRecurringDAOImpl implements UnSuccessfulRecurringDAO {

	@PersistenceContext
	private EntityManager em;

	
	public UnSuccessfulRecurringDeposit getLastUnSuccessfulRecurringDeposit(Long depositId)
	{
		 String sql ="SELECT o From UnSuccessfulRecurringDeposit o where o.depositId>=:depositId AND "
		 		+ "o.unsuccessPaymentDate=(SELECT max(unsuccessPaymentDate) FROM UnSuccessfulRecurringDeposit m where m.depositId>=:depositId)";

			Query q = em.createQuery(sql);
			q.setParameter("depositId",depositId);
			@SuppressWarnings("unchecked")
			List<UnSuccessfulRecurringDeposit> list=q.getResultList();
			if(list!=null && list.size()>0)
				return list.get(list.size()-1);
			else
				return null;
			

	}
	
	@SuppressWarnings("unchecked")
	public List<UnSuccessfulRecurringDeposit> getUnsuccessfulRecurringDepositOfCurrentMonth(Integer month, Integer year)
	{
		Query query = em.createQuery(
				"SELECT o FROM UnSuccessfulRecurringDeposit o WHERE EXTRACT(MONTH FROM o.unsuccessPaymentDate)=:month and "
						+ "EXTRACT(YEAR FROM o.unsuccessPaymentDate)=:year");
		query.setParameter("month", month);
		query.setParameter("year", year);
		return query.getResultList();
			
	}

	@Transactional
	public void insertUnSuccessfulRecurringDeposit(UnSuccessfulRecurringDeposit unSuccessfuluDeposit) {
		em.persist(unSuccessfuluDeposit);
	}

	
	@Transactional
	public void updateUnSuccessfulRecurringDeposit(UnSuccessfulRecurringDeposit unsuccessfulRecurringDeposit) {
		em.merge(unsuccessfulRecurringDeposit);
		em.flush();
	}

}
