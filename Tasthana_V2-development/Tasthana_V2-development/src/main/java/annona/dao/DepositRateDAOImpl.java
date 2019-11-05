package annona.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.DepositRateFileImport;
import annona.domain.DepositRates;
import annona.domain.RatePeriods;
import annona.domain.Transaction;

@Repository
public class DepositRateDAOImpl implements DepositRateDAO{

	@PersistenceContext
	EntityManager em;

	public EntityManager entityManager() {
		EntityManager em = new DepositRateDAOImpl().em;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}


	public void insertDepositRates(DepositRates map) {

		em.persist(map);
		
	}
	

	public void createDuration(RatePeriods map) {

		em.persist(map);
		
	}

	@Override
	public DepositRates findId(Long id) {
		return em.find(DepositRates.class,id);
	}
	
	


	 public void deletebyCategory(String category,Date effectiveDate){
	  
	  Query q= em.createQuery("DELETE FROM DepositRates o where o.category=:category AND o.effectiveDate <=:effectiveDate");
	  q.setParameter("category", category);
	  q.setParameter("effectiveDate", effectiveDate);
	  
	  q.executeUpdate();
	  
	 }
	
	
	
//	public Float getInterestRate(String category, String currency, Integer days, String depositClassification, Double depositAmount)
//	{
//		  Query q= em.createQuery("SELECT o from DepositRates o where o.category=:category AND o.currency=:currency AND o.calMaturityPeriodFromInDays<=:days and"
//		  		+ " o.calMaturityPeriodToInDays>=:days and o.amountSlabFrom<=:depositAmount and o.amountSlabTo>=:depositAmount and "
//		  		+ "o.depositClassification=:depositClassification order by id desc");
//		  q.setParameter("category", category);
//		  q.setParameter("currency", currency);
//		  q.setParameter("days", days);
//		  q.setParameter("depositAmount", depositAmount);
//		  q.setParameter("depositClassification", depositClassification);
//		  
//		  List depositRates = q.getResultList();
//		  if(depositRates!=null && depositRates.size()>0)
//			  return ((List<DepositRates>)depositRates).get(0).getInterestRate();
//		  else
//			  return 0f;
//	}
	 
	@SuppressWarnings("unchecked")
	 public RatePeriods getRatePeriods(Integer startDay ,Integer endDay, String category, String citizen, String nriAccountType, String depositClassification){
		 
		 Query query  = null;
		 if(nriAccountType!=null && nriAccountType !="") {
		 query = em.createQuery("SELECT o FROM RatePeriods o where o.fromDay=:startDay and o.toDay=:endDay and o.category=:category and o.citizen=:citizen and o.nriAccountType=:nriAccountType and o.depositClassification=:depositClassification");
		 query.setParameter("startDay", startDay);
		 query.setParameter("endDay", endDay);
		 query.setParameter("category", category);
		 query.setParameter("citizen", citizen);
		 query.setParameter("nriAccountType", nriAccountType);
		 query.setParameter("depositClassification", depositClassification);
		 }
		 else {
			 query = em.createQuery("SELECT o FROM RatePeriods o where o.fromDay=:startDay and o.toDay=:endDay and o.category=:category and o.citizen=:citizen and o.depositClassification=:depositClassification");
			 query.setParameter("startDay", startDay);
			 query.setParameter("endDay", endDay);
			 query.setParameter("category", category);
			 query.setParameter("citizen", citizen);
			 query.setParameter("depositClassification", depositClassification);
		 }
	
		 List<RatePeriods> list = query.getResultList();
		  if(list.size()>0)
		   return list.get(0);
		  else
		   return null;
		 
	 }
	 
	
}

