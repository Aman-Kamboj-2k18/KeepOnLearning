package annona.dao;

import java.util.Calendar;
import java.util.Date;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.Deposit;
import annona.domain.Distribution;
import annona.domain.PayoffDetails;
import annona.domain.WithdrawDeposit;
import annona.services.DateService;

@Repository
public class PaymentDistributionDAOImpl implements PaymentDistributionDAO {

	@PersistenceContext
	EntityManager em;

	public EntityManager entityManager() {
		EntityManager em = new PaymentDistributionDAOImpl().em;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public Distribution insertPaymentDistribution(Distribution paymentDistribution) {
		em.persist(paymentDistribution);
		return paymentDistribution;
	}


	@Transactional
	public Distribution updatePaymentDistribution(Distribution distribution ) {
		em.merge(distribution );
		em.flush();
		return distribution ;
	}
	
	@SuppressWarnings("unchecked")
	public Distribution getLastPaymentDistribution(Long depositId) {
		Query query = em.createQuery("SELECT o FROM Distribution o where o.depositId =:depositId order by id desc");

		try {
			List<Distribution> paymentDistributions = query.setParameter("depositId", depositId).getResultList();
			if (paymentDistributions.size() > 0)
				return paymentDistributions.get(0);
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}

	/*
	 * @SuppressWarnings("unchecked") public List<Distribution>
	 * getPaymentDistributions(Long depositId) { Query query = em.createQuery(
	 * "SELECT o FROM Distribution o where o.depositId =:depositId order by id desc"
	 * );
	 * 
	 * try { List paymentDistributions = query.setParameter("depositId",
	 * depositId).getResultList(); if (paymentDistributions.size() > 0) return
	 * (List<Distribution>) paymentDistributions.get(0); else return null; }
	 * catch (Exception ex) { return null; } }
	 */

	@SuppressWarnings("unchecked")
	public List<Distribution> getPaymentDistributions(Long depositId, Date fromDate) {
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and distributionDate>=:distributionDate order by id");

		try {
			query.setParameter("depositId", depositId);
			query.setParameter("distributionDate", fromDate);
			List paymentDistributions = query.getResultList();
			if (paymentDistributions.size() > 0)
				return (List<Distribution>) paymentDistributions;
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}

	
	@SuppressWarnings("unchecked")
	public Distribution getLastDistributionOfPreviousMonth(Long depositId, Date fromDate) {
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and distributionDate<:distributionDate order by id desc");

		try {
			query.setParameter("depositId", depositId);
			query.setParameter("distributionDate", fromDate);
			List paymentDistributions = query.getResultList();
			if (paymentDistributions.size() > 0)
				return (Distribution) paymentDistributions.get(0);
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Distribution> getAllDepWithdrawFromLastInterestCalc(Long depositId, Long lastInterestDistributionId) {
		// Query query = em.createQuery(
		// "SELECT o FROM Distribution o where o.depositId =:depositId and
		// action in ('Payment', 'Withdraw') and Id
		// >=:lastInterestDistributionId order by id");
		
		// This will fetch all records other than interest or interest adjustment
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and Id >:lastInterestDistributionId  order by id");

		try {
			query.setParameter("depositId", depositId);
			query.setParameter("lastInterestDistributionId", lastInterestDistributionId);
			List<Distribution> paymentDistributions = query.getResultList();
			return paymentDistributions;
		} catch (Exception ex) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public Distribution getLastInterestCalculated(Long depositId) {
		/*
		 * Query query = em.createQuery(
		 * "SELECT o FROM Distribution o where o.depositId =:depositId and (action=:action1 or action=:action2 or action=:action3) order by id desc"
		 * );
		 */
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and action=:action1 and o.interestAdjusted is null order by id desc");

		query.setParameter("depositId", depositId);
		query.setParameter("action1", "Interest");
		/*
		 * query.setParameter("action2", "ModificationPenalty");
		 * query.setParameter("action3", "PreMaturityClosePenalty");
		 */

		List<Distribution> paymentDistributions = query.getResultList();
		if (paymentDistributions.size() > 0)
			return paymentDistributions.get(0);
		else
			return null;

	}

	@Transactional
	public WithdrawDeposit insertWithDrawPayment(WithdrawDeposit withdrawDeposit) {
		em.persist(withdrawDeposit);
		return withdrawDeposit;
	}


	@Transactional
	public void closeDeposit(Deposit deposit) {
		em.merge(deposit);
		em.flush();
	}

	@SuppressWarnings("unchecked")
	public Double getTotalInterest(Long depositId) {
		Query query = em.createQuery(
				"SELECT COALESCE(o.fixedInterest,0) + COALESCE(o.variableInterest,0) FROM Distribution o where o.depositId =:depositId and action!='Interest'");
		Double totInterest = 0d;
		try {
			query.setParameter("depositId", depositId);
			List<Double> interestList = query.getResultList();

			for (int i = 0; i < interestList.size(); i++) {
				totInterest = totInterest + interestList.get(i);
			}

			return totInterest;
		} catch (Exception ex) {
			return 0d;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Distribution> getCurrentQuarterInterestDistribution(Long depositId) {

		Calendar cal = Calendar.getInstance();
		int quarter = DateService.getQuarterNo(cal.getTime());
		int year = cal.get(Calendar.YEAR);

		Query query = em.createQuery(
				"select o FROM Distribution o where extract(YEAR from o.distributionDate) = :year  and extract(quarter from o.distributionDate) = :quarter and o.depositId=:depositId and o.action ='Interest'");
		query.setParameter("depositId", depositId);
		query.setParameter("year", year);
		query.setParameter("quarter", quarter);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Distribution> getHalfYearlyInterestDistribution(Long depositId, Date depositDate) {

		Calendar cal = Calendar.getInstance();
		Date toDate = cal.getTime();
		cal.add(Calendar.MONTH, -6);
		Date dt = DateService.getFirstDateOfMonth(cal.getTime());
		cal.setTime(dt);
		Date fromDate = cal.getTime();

		int monthDiff = DateService.getMonth(fromDate) - DateService.getMonth(depositDate);
		int yearDiff = DateService.getYear(fromDate) - DateService.getYear(depositDate);
		if (monthDiff == 1 && yearDiff == 0) {
			cal.add(Calendar.MONTH, -1);
			fromDate = cal.getTime();
		}

		Query query = em.createQuery(
				"select o FROM Distribution o where o.distributionDate>=:fromDate and o.distributionDate<=:toDate and o.depositId=:depositId and o.action ='Interest'");
		query.setParameter("depositId", depositId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public WithdrawDeposit getLastWithdraw(Long depositId) {
		Query query = em.createQuery("SELECT o FROM WithdrawDeposit o where o.depositId =:depositId order by id desc");

		try {
			query.setParameter("depositId", depositId);
			List withdraws = query.getResultList();
			if (withdraws != null && withdraws.size() > 0)
				return (WithdrawDeposit) withdraws.get(0);
			else
				return null;

		} catch (Exception ex) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public Distribution getLastAdjustment(Long depositId) {
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and o.action like 'Interest Adjustment%' order by id desc");

		
		try {
			List<Distribution> paymentDistributions = query.setParameter("depositId", depositId).getResultList();
			if (paymentDistributions.size() > 0)
				return paymentDistributions.get(0);
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Distribution getLastInterestCalculatedOrAdjusted(Long depositId, Date toDate) {
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and (o.action like 'Interest%' or o.action like 'Renew') and o.distributionDate<=:toDate order by id desc");
//		Query query = em.createQuery(
//				"SELECT o FROM Distribution o where o.depositId =:depositId and o.action in ('Interest', 'Interest Adjustment For Tenure Reduce') and o.distributionDate<=:toDate order by id desc");
		
//		Query query = em.createQuery(
//				"SELECT o FROM Distribution o where o.depositId =:depositId and ((o.action like 'Interest' and o.interestAdjusted is null) OR (o.action like 'Interest Adjustment For Tenure Reduce')  OR (o.action like 'Interest Adjustment For Tenure Change')) and o.distributionDate<=:toDate order by id desc");
		
//		Query query = em.createQuery(
//				"SELECT o FROM Distribution o where o.depositId =:depositId and o.action in ('Interest', 'Interest Adjustment%') and o.distributionDate<=:toDate order by o.distributionDate desc");

		query.setParameter("depositId", depositId);
		query.setParameter("toDate", toDate);
		
		try {
			List paymentDistributions = query.getResultList();
			if (paymentDistributions.size() > 0)
				return ((List<Distribution>)paymentDistributions).get(0);
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Distribution> getAllDistributions(Long depositId) {
		Query query = em.createQuery("SELECT o FROM Distribution o where o.depositId =:depositId order by id");

		query.setParameter("depositId", depositId);

		try {
			List paymentDistributionList = query.getResultList();
			if (paymentDistributionList.size() > 0)
				return (List<Distribution>) paymentDistributionList;
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Distribution> getDistributionListFrom(Long depositId, Long fromDistributionId) {
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and o.id>:fromDistributionId order by id");

		query.setParameter("depositId", depositId);
		query.setParameter("fromDistributionId", fromDistributionId);
		try {
			List paymentDistributionList = query.getResultList();
			if (paymentDistributionList.size() > 0)
				return (List<Distribution>) paymentDistributionList;
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public Double getTotInterestGivenForPeriod(Long depositId, Date fromDate, Date toDate)
	{
		Query query =em.createQuery("select Sum(o.fixedInterest + o.variableInterest) from Distribution o where o.depositId=:depositId and o.distributionDate>=:fromDate and o.distributionDate<=:toDate");
		query.setParameter("depositId", depositId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		Object obj = query.getSingleResult();
		Double totInterest = (obj == null)? 0d : Double.parseDouble(obj.toString()); 
		return totInterest;
	}
	
	public Distribution durationFromBase(Long depositId, Long distributionId)
	{
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and o.id<:distributionId and action in ('Payment', 'Interest Adjustment For Tenure Reduce') order by id desc");

		query.setParameter("depositId", depositId);
		query.setParameter("distributionId", distributionId);
		try {
			List lst = query.getResultList();
			if (lst != null && lst.size() > 0)
				return ((Distribution)lst.get(0));
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}
	
	// using for withdraw adjustment
	// using for withdraw adjustment
	 public Distribution getLastBaseLine(Long depositId)
	 {

//	  Query query = em.createQuery("SELECT o FROM Distribution o where (o.depositId =:depositId and action like 'Payment')  OR (o.depositId =:depositId and action like 'Auto Payment')"
//	    + " OR (o.depositId =:depositId and action like 'Interest Adjustment For Tenure%') order by id desc");

	  Query query = em.createQuery("SELECT o FROM Distribution o where (o.depositId =:depositId and action like 'Payment')  OR (o.depositId =:depositId and action like 'Auto Payment')"
			    + " OR (o.depositId =:depositId and action like 'Interest Adjustment%') order by id desc");

	  query.setParameter("depositId", depositId);
	  try {
	   List lst = query.getResultList();
	   if (lst != null && lst.size() > 0)
	    return ((Distribution)lst.get(0));
	   else
	    return null;
	  } catch (Exception ex) {
	   return null;
	  }
	 }
	
	// Get the distributions starting from the previous interest of base line
	// If the previous interest is not present then start from beginning
	public List<Distribution> getDistributionsFromPreviousInterest(Long depositId, Long lastBaseLineId)
	{		
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and o.id<=:lastBaseLineId and "
				+ "o.id>=(select max(m.id) from Distribution m where m.action ='Interest' and m.id<:lastBaseLineId)");
	
		query.setParameter("depositId", depositId);
		query.setParameter("lastBaseLineId", lastBaseLineId);
		
			List lst = query.getResultList();
			if (lst != null && lst.size() > 0)
				return (List<Distribution>)lst;
			else
				{

					Query query1 = em.createQuery(
						"SELECT o FROM Distribution o where o.depositId =:depositId and o.id<=:lastBaseLineId");
		
					query1.setParameter("depositId", depositId);
					query1.setParameter("lastBaseLineId", lastBaseLineId);
					
					List lst1 = query1.getResultList();
					if (lst1 != null && lst1.size() > 0)
						return (List<Distribution>)lst1;
					else
						return null;
				}
		
	}
	
	public Distribution getLastAdjustmentFor7Days(Long depositId){
		  
		  Query query = em.createQuery(
		    "SELECT o FROM Distribution o where o.depositId =:depositId and o.action =:action");

		  query.setParameter("depositId", depositId);
		  query.setParameter("action", "Interest Adjustment For Withdraw Within 7 days");
		  
		  
		   @SuppressWarnings("unchecked")
		   List<Distribution> paymentDistributions =(List<Distribution>)query.getResultList();
		   if (paymentDistributions.size() > 0)
		    return paymentDistributions.get(0);
		   else
		    return null;
		  
	}
	
	@SuppressWarnings("unchecked")
	public Double[] getTotalInterestGivenFromBaseLine(Long depositId, long lastBaseLineId) {
		Query query = em.createQuery(
				"SELECT Sum(o.fixedInterest),  Sum(o.variableInterest) FROM Distribution o where o.depositId =:depositId and action='Interest' and o.id:>lastBaseLineId");
		try {
			query.setParameter("depositId", depositId);
			query.setParameter("lastBaseLineId", lastBaseLineId);
			Object interests  = query.getSingleResult();

			Double[] interest =  (Double[]) interests;
			
			return interest;
		} catch (Exception ex) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public Distribution getLastBaseLineForWithdraw(Long depositId, Long withdrawDistributionId) {

		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and o.id<:withdrawDistributionId and (action like 'Payment' or action like  'Interest Adjustment%') order by id desc");

		try {
			query.setParameter("depositId", depositId);
			query.setParameter("withdrawDistributionId", withdrawDistributionId);
			List lst = query.getResultList();
			if(lst!=null && lst.size()>0)
				return (Distribution)lst.get(0);
			else 
				return null;
			
		} catch (Exception ex) {
			return null;
		}

	}
	
	@SuppressWarnings("unchecked")
	public Distribution getLastBaseLineForTenureChange(Long depositId) {
//		Query query = em.createQuery(
//				"SELECT o FROM Distribution o where o.depositId =:depositId and action like 'Interest Adjustment For Tenure%' order by id desc");

		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and action like 'Interest Adjustment%' order by id desc");

		
		try {
			query.setParameter("depositId", depositId);
			List lst = query.getResultList();
			if(lst!=null && lst.size()>0)
				return (Distribution)lst.get(0);
			else
			{
				Query query1 = em.createQuery(
						"SELECT o FROM Distribution o where o.depositId =:depositId and action ='Payment' order by id");
				query1.setParameter("depositId", depositId);
				List lst1 = query1.getResultList();
				if(lst1!=null && lst1.size()>0)
					return (Distribution)lst1.get(0);
				else
					return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}
	
	public Distribution getLastBaseLineOf(Long depositId, Long distributionId)
	 {
	  Query query = em.createQuery(
	    "SELECT o FROM Distribution o where o.depositId =:depositId and o.id<:distributionId and action in ('Payment', 'Interest Adjustment For Tenure Reduce') order by id desc");

		  query.setParameter("depositId", depositId);
		  query.setParameter("distributionId", distributionId);
		  try
		  {
			  List lst = query.getResultList();
			  if (lst != null && lst.size() > 0)
				  return ((Distribution)lst.get(0));
			  else
				  return null;
		  } catch (Exception ex)
		  {
			  return null;
		  }
	 }
	
	public List<Distribution> getInterestsGivenForPeriod(Long depositId, Date fromDate, Date toDate) {
		
		Query query =em.createQuery("select o from Distribution o where o.depositId=:depositId and (o.action like 'Interest' or o.action like 'Interest Adjustment%') and  o.distributionDate>=:fromDate and o.distributionDate<=:toDate");
		query.setParameter("depositId", depositId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		List lst = query.getResultList();
		if(lst!=null && lst.size()>0)
			return (List<Distribution>)lst;
		else 
			return null;
	}
	
	public Distribution getLastTopup(Long depositId, Long toDistributionId)
	{
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and o.action ='Payment' order by id desc");
		if(toDistributionId!=null)
		{
			query = em.createQuery(
					"SELECT o FROM Distribution o where o.depositId =:depositId and o.action ='Payment' and o.id<:toDistributionId order by id desc");
			query.setParameter("toDistributionId", toDistributionId);
		}
		query.setParameter("depositId", depositId);
		
		List lst1 = query.getResultList();
		if(lst1!=null && lst1.size()>0)
			return (Distribution)lst1.get(0);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public Distribution getLastInterestCalculatedBeforeAdjustment(Long depositId, Long fromDistributionId) {
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and action in ('Interest', 'Interest Adjustment For Tenure Reduce') and o.interestAdjusted is null and o.id<=:fromDistributionId order by id desc");

		query.setParameter("depositId", depositId);
		query.setParameter("fromDistributionId", fromDistributionId);
		
		try {
			List paymentDistributions = query.getResultList();
			if (paymentDistributions.size() > 0)
				return ((List<Distribution>)paymentDistributions).get(0);
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Distribution getFirstAdjustmentAfterLastInterest(Long depositId) {
		
		// get the last interest calculated
		Distribution lastInterest = this.getLastInterestCalculated(depositId);
		
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and o.action like 'Interest Adjustment For Withdraw%' and id>:lastInterestId order by id");

		
		try {
			query.setParameter("depositId", depositId);
			query.setParameter("lastInterestId", lastInterest == null? 0: lastInterest.getId());
			
			List<Distribution> paymentDistributions = query.setParameter("depositId", depositId).getResultList();
			if (paymentDistributions.size() > 0)
				return paymentDistributions.get(0);
			else
				return null;
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Distribution> getAllTransactionsExceptInterestFromLastInterestCalc(Long depositId, Long lastInterestDistributionId) {
		// Query query = em.createQuery(
		// "SELECT o FROM Distribution o where o.depositId =:depositId and
		// action in ('Payment', 'Withdraw') and Id
		// >=:lastInterestDistributionId order by id");
		
		// This will fetch all records other than interest or interest adjustment
		Query query = em.createQuery(
				"SELECT o FROM Distribution o where o.depositId =:depositId and action not like 'Interest'  and Id >=:lastInterestDistributionId  order by id");

		try {
			query.setParameter("depositId", depositId);
			query.setParameter("lastInterestDistributionId", lastInterestDistributionId);
			List<Distribution> paymentDistributions = query.getResultList();
			return paymentDistributions;
		} catch (Exception ex) {
			return null;
		}

	}
	
	// Get the distributions starting from the previous interest of base line
	// If the previous interest is not present then start from beginning
		public Distribution getPreviousDistribution(Long depositId, Long lastBaseLineId)
		{		
			Query query = em.createQuery(
					"SELECT o FROM Distribution o where o.depositId =:depositId and o.id<:lastBaseLineId order by id desc");
		
			query.setParameter("depositId", depositId);
			query.setParameter("lastBaseLineId", lastBaseLineId);
			
				List lst = query.getResultList();
				if (lst != null && lst.size() > 0)
					return (Distribution)lst.get(0);
				else
					return null;
			
		}
}

