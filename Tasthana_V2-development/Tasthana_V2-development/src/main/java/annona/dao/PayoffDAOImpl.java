package annona.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.Payoff;
import annona.domain.PayoffDetails;
import annona.domain.UnSuccessfulPayOff;


@Repository
public class PayoffDAOImpl implements PayoffDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public  UnSuccessfulPayOff insertUnSuccessfulPayOff(UnSuccessfulPayOff unSuccessfulPayOff) {
		em.persist(unSuccessfulPayOff);
		return unSuccessfulPayOff;
	}
	
	@SuppressWarnings("unchecked")
	public Payoff getPayoff(Long depositId) {
		Query query = em
				.createQuery("SELECT o FROM Payoff o where o.depositId =:depositId order by id desc");

		try {
			List<Payoff> payoffDetails= query.setParameter("depositId", depositId).getResultList();
			if (payoffDetails.size() > 0)
				return (Payoff) payoffDetails.get(0);
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}
	
	@Transactional
	public Payoff insertPayoff(Payoff payoff) {
		em.persist(payoff);
		return payoff;
	}

	@SuppressWarnings("unchecked")
	public List<PayoffDetails> getPayoffDetails(Long depositId) {
		Query query = em
				.createQuery("SELECT o FROM PayoffDetails o where o.depositId =:depositId order by id desc");

		try {
			List<PayoffDetails> payoffDetails= query.setParameter("depositId", depositId).getResultList();
			if (payoffDetails.size() > 0)
				return (List<PayoffDetails>) payoffDetails.get(0);
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}
	
	@Transactional
	public PayoffDetails insertPayoffDetails(PayoffDetails payoffDetails) {
		em.persist(payoffDetails);
		return payoffDetails;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Double getTotalInterestFromLastPayOff(Long depositId, Long lastpayOffDistributionId) {
		Query query = em.createQuery(
				"SELECT SUM(COALESCE(o.variableInterest, 0)) FROM Distribution o where o.depositId =:depositId and action in ('Interest') and o.id >:lastpayOffDistributionId");

		Double totInterest = 0d;
		try {
			query.setParameter("depositId", depositId);
			query.setParameter("lastpayOffDistributionId", lastpayOffDistributionId);
			List<Double> interests = query.getResultList();

			for (int i = 0; i < interests.size(); i++) {
				Double interest = interests.get(i)== null? 0:  interests.get(i).doubleValue();
				totInterest = totInterest + interest;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			return totInterest;
		}

	}
	
	@Transactional
	public PayoffDetails insertPayOffDistribution(PayoffDetails payOffDistribution) {
		em.persist(payOffDistribution);
		return payOffDistribution;
	}

	public List<PayoffDetails> payOffDetailsByDepositHolderId(Long depositHolderId) {

		  if (depositHolderId == null || depositHolderId == 0)
		   throw new IllegalArgumentException("Select the deposit");

		  Query query = em.createQuery("SELECT o FROM PayoffDetails o where o.depositHolderId =:depositHolderId order by id desc");
		  query.setParameter("depositHolderId", depositHolderId);

		  return query.getResultList();
		 }
	
	 public Double getSumOffPayoffactualamountByDepositId(Long depositId) {

		  Query query = em.createQuery(
		    "SELECT SUM(payOffActualAmount) FROM PayOffDistribution o where o.depositId =:depositId");
		  query.setParameter("depositId", depositId);
		  Object payOffActualAmount = query.getSingleResult();
		  if(payOffActualAmount != null )
		  {
		   return Double.parseDouble(payOffActualAmount.toString());
		  }
		  else
		   return 0d;
		 }
	
	   public List<PayoffDetails> payOffDistributionByDepositId(Long depositId) {

		  if (depositId == null || depositId == 0)
		   throw new IllegalArgumentException("Select the deposit");

		  Query query = em.createQuery("SELECT o FROM PayOffDistribution o where o.depositId =:depositId order by id desc");
		  query.setParameter("depositId", depositId);

		  return query.getResultList();
		 }
		 
	
}
