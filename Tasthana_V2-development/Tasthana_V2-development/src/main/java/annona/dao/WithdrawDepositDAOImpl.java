package annona.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import annona.domain.WithdrawDeposit;


@Repository
public class WithdrawDepositDAOImpl implements WithdrawDepositDAO {

	@PersistenceContext
	private EntityManager em;

	
	/**
	 * Method for entity manager
	 * 
	 * @return
	 */
	public EntityManager entityManager() {
		EntityManager em = new WithdrawDepositDAOImpl().em;

		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public List<WithdrawDeposit> withdrawDepositListByDepositId(Long depositId) {

		Query query = em.createQuery(
				"SELECT o FROM WithdrawDeposit o where o.depositId =:depositId");
		query.setParameter("depositId", depositId);
		
		return query.getResultList();
	}

	
//	public Double getTotalWithdraw(Long depositId, Date fromDate, Date toDate)
//	{
//		HashMap<String, Double> totalInterestYearWise = new HashMap<>(); 
//		
//		Query query = em.createQuery("select Sum(o.amountPaid) from Payment o where o.depositId=:depositId and o.paymentDate>=:fromDate and o.paymentDate<=:toDate");
//		query.setParameter("depositId", depositId);
//		query.setParameter("fromDate", fromDate);
//		query.setParameter("toDate", toDate);
//		Object totalPayment = query.getSingleResult();
//		if(totalPayment != null )
//		{
//			return Double.parseDouble(totalPayment.toString());
//		}
//		else
//			return 0d;
//	}
	
	public Double getTotalWithdraw(Long depositId, Date fromDate, Date toDate)
	{
		HashMap<String, Double> totalInterestYearWise = new HashMap<>(); 
		
		Query query = em.createQuery("select Sum(o.withdrawAmount) from WithdrawDeposit o where o.depositId=:depositId and o.withdrawDate>=:fromDate and o.withdrawDate<=:toDate");
		query.setParameter("depositId", depositId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		Object totalPayment = query.getSingleResult();
		if(totalPayment != null )
		{
			return Double.parseDouble(totalPayment.toString());
		}
		else
			return 0d;
	}

}
