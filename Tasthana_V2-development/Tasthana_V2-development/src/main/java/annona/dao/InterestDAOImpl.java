package annona.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.AccountDetails;
import annona.domain.Customer;
import annona.domain.DepositModification;
import annona.domain.DepositRates;
import annona.domain.Distribution;
import annona.domain.Interest;
import annona.form.CustomerForm;
import annona.services.DateService;

@Repository
public class InterestDAOImpl implements InterestDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Method for entity manager
	 * 
	 * @return
	 */
	public EntityManager entityManager() {
		EntityManager em = new InterestDAOImpl().em;

		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public void insert(Interest interest) {
		em.persist(interest);
		// flush a batch of inserts and release memory:
		em.flush();
	}

	@Transactional
	public void update(Interest interest) {
		em.merge(interest);
		em.flush();
	}

	public List<Interest> getByDepositId(Long depositId) {

		Query query = em.createQuery("SELECT o FROM Interest o WHERE o.depositId=:depositId order by id");
		query.setParameter("depositId", depositId);
		return query.getResultList();
	}
	
	public Double getTotalInterestAccumulated(Long depositId, Date fromDate, Date toDate) {

		if(fromDate != null)
		{	
			  Query query = em.createQuery("SELECT Sum(o.interestAmount) FROM Interest o WHERE o.depositId=:depositId and date(o.interestDate)>=date(:fromDate) and date(o.interestDate)<=date(:toDate)");
			  query.setParameter("depositId", depositId);
			  query.setParameter("fromDate", fromDate);
			  query.setParameter("toDate", toDate);
			  Object interestAmount = query.getSingleResult();
			  if(interestAmount != null )
			  {
			   return Double.parseDouble(interestAmount.toString());
			  }
			  else
			   return 0d;
		 }
		else
		{
			Query query = em.createQuery("SELECT Sum(o.interestAmount) FROM Interest o WHERE o.depositId=:depositId");
			  query.setParameter("depositId", depositId);
			  Object interestAmount = query.getSingleResult();
			  if(interestAmount != null )
			  {
			   return Double.parseDouble(interestAmount.toString());
			  }
			  else
			   return 0d;
			
		}
	
	}
	
	public Interest getInterestByMonth(Long depositId, Integer month, Integer year, Date maturityDate) {
		Query query = em.createQuery(
				"SELECT o FROM Interest o WHERE o.depositId=:depositId and EXTRACT(MONTH FROM o.interestDate)=:month and "
						+ "EXTRACT(YEAR FROM o.interestDate)=:year");
		query.setParameter("depositId", depositId);
		query.setParameter("month", month);
		query.setParameter("year", year);
		List lst = query.getResultList();
		Interest interest = null;
		if (lst != null && lst.size() > 0) {
			List<Interest> interestList = (List<Interest>) lst;

			for (int i = 0; i < interestList.size(); i++) {
				Date dt = interestList.get(i).getInterestDate();
				if (DateService.getDaysBetweenTwoDates(dt, DateService.getLastDateOfCurrentMonth()) == 0) {
					interest = interestList.get(i);
					break;
				}
				if (DateService.getDaysBetweenTwoDates(dt, maturityDate) == 0) {
					interest = interestList.get(i);
					break;
				}

			}
			return interest;
		} else {
			return null;
		}
	}


	@Transactional
	public Interest deleteByDepositIdAndDate(Long depositId) {
		//Date dt = DateService.getCurrentDate();
		Date currentDate = DateService.getCurrentDateTime();
		
//		String sql = "DELETE FROM Interest WHERE depositId=:depositId and interestCalcDate>=CURRENT_DATE and interestDeducted is null";
		
		String sql = "DELETE FROM Interest WHERE depositId=:depositId and interestDate>=:currentDate";
		Query query = em.createNativeQuery(sql);		
		query.setParameter("depositId", depositId);
		query.setParameter("currentDate", DateService.getCurrentDateTime());
		query.executeUpdate();
		
		
		Query query2 = em.createQuery("SELECT o FROM Interest o WHERE o.depositId=:depositId and  o.interestDate= (select max(m.interestDate) FROM Interest m WHERE m.depositId=:depositId)");
		query2.setParameter("depositId", depositId);
		List<Interest> intList= query2.getResultList();
		if(intList.size()>0){
			return intList.get(0);
		}
		else
			return null;
		
	}
	
	@Transactional
	public Interest deleteByDepositIdAndCurrentDate(Long depositId , Date currentDate) {
	
		String sql = "DELETE FROM Interest WHERE depositId=:depositId and interestDate>=:currentDate";
		Query query = em.createNativeQuery(sql);		
		query.setParameter("depositId", depositId);
		query.setParameter("currentDate", DateService.getCurrentDateTime());
		query.executeUpdate();
		
		
		Query query2 = em.createQuery("SELECT o FROM Interest o WHERE o.depositId=:depositId and  o.interestDate= (select max(m.interestDate) FROM Interest m WHERE m.depositId=:depositId)");
		query2.setParameter("depositId", depositId);
		List<Interest> intList= query2.getResultList();
		if(intList.size()>0){
			return intList.get(0);
		}
		else
			return null;
		
	}
	
	public HashMap<String, Double>  getTotInterestAddedToPrincipalByFinancialYear(Long depositId) {
		
		HashMap<String, Double> totalInterestYearWise = new HashMap<>(); 
		
		//Query query = em.createQuery("select o.financialYear, Sum(o.interestAmount) from Interest o where o.depositId=:depositId and interestDeducted=1 and o.interestCalcDate<CURRENT_DATE group by financialYear");
		Query query = em.createQuery("select o.financialYear, Sum(o.interestAmount) from Interest o where o.depositId=:depositId and o.interestDate<:currentDate group by financialYear");
		query.setParameter("depositId", depositId);
		query.setParameter("currentDate", DateService.getCurrentDateTime());
		List<Object[]> lstYearWiseInterests = query.getResultList();
		if(lstYearWiseInterests != null && lstYearWiseInterests.size()>0)
		{
			for(int i=0; i< lstYearWiseInterests.size(); i++)
			{
				Object[] yearWiseInterest = lstYearWiseInterests.get(i);
				totalInterestYearWise.put(yearWiseInterest[0].toString(),Double.parseDouble(yearWiseInterest[1].toString()));
			}
		}
		return totalInterestYearWise;
	}
	
	public HashMap<String, Double> getTotInterestOfFinancialYear(Long depositId) {
		
		HashMap<String, Double> totalInterestYearWise = new HashMap<>(); 
		
		//Query query = em.createQuery("select o.financialYear, Sum(o.interestAmount) from Interest o where o.depositId=:depositId and o.interestCalcDate<CURRENT_DATE group by financialYear");
		Query query = em.createQuery("select o.financialYear, Sum(o.interestAmount) from Interest o where o.depositId=:depositId and o.interestDate<:currentDate group by financialYear");
		query.setParameter("depositId", depositId);
		query.setParameter("currentDate", DateService.getCurrentDate());

		List<Object[]> lstYearWiseInterests = query.getResultList();
		if(lstYearWiseInterests != null && lstYearWiseInterests.size()>0)
		{
			for(int i=0; i< lstYearWiseInterests.size(); i++)
			{
				Object[] yearWiseInterest = lstYearWiseInterests.get(i);
				totalInterestYearWise.put(yearWiseInterest[0].toString(),Double.parseDouble(yearWiseInterest[1].toString()));
			}
		}
		return totalInterestYearWise;
	}
	
	public Double getTotAnnualInterestAmount(Long depositId, String financialYear) {
		
		HashMap<String, Double> totalInterestYearWise = new HashMap<>(); 
		
		Query query = em.createQuery("select Sum(o.interestAmount) from Interest o where o.depositId=:depositId and financialYear=:financialYear");
		query.setParameter("depositId", depositId);
		query.setParameter("financialYear", financialYear);
		Object interestAmount = query.getSingleResult();
		if(interestAmount != null )
		{
			return Double.parseDouble(interestAmount.toString());
		}
		else
			return 0d;
	}

	public Double getTotInterestGivenForPeriod(Long depositId, Date fromDate, Date toDate) {
		
		Query query =em.createQuery("select Sum(o.interestAmount) from Interest o where o.depositId=:depositId and o.interestDate>=:fromDate and o.interestDate<=:toDate");
		query.setParameter("depositId", depositId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		Object obj = query.getSingleResult();
		Double totInterest = (obj == null)? 0d : Double.parseDouble(obj.toString()); 
		return totInterest;
	}
	
	
	public Double getTotInterestGiven(Long depositId, String financialYear) {
		
		Query query =em.createQuery("select Sum(o.interestAmount) from Interest o where o.depositId=:depositId and financialYear=:financialYear ");
		query.setParameter("depositId", depositId);
		query.setParameter("financialYear", financialYear);
		Object interestAmount = query.getSingleResult();
		if(interestAmount != null )
		{
			return Double.parseDouble(interestAmount.toString());
		}
		else
			return 0d;
	}
	
	public Double getTotProjectedInterest(Long depositId, String financialYear) {
		
		Query query = em.createQuery("select Sum(o.interestAmount) from Interest o where o.depositId=:depositId and financialYear=:financialYear");
		query.setParameter("depositId", depositId);
		query.setParameter("financialYear", financialYear);
		Object interestAmount = query.getSingleResult();
		if(interestAmount != null )
		{
			return Double.parseDouble(interestAmount.toString());
		}
		else
			return 0d;
	}

	 @Transactional
	 public List<Interest> getByDate(Date fromDate, Date toDate, Long depositId){
	  
	  String sqlSelect="SELECT o FROM Interest o WHERE o.depositId=:depositId and o.interestDate>:fromDate and  o.interestDate<=:toDate)";
	  Query querySelect = em.createQuery(sqlSelect);
	  querySelect.setParameter("depositId",depositId);
	  querySelect.setParameter("toDate",toDate);
	  querySelect.setParameter("fromDate",fromDate);
	  
	  List<Interest> intList= querySelect.getResultList();
	  return intList;
  
	 }
	 
	 public Interest getMaxInterest(Long depositId, Date date){

	   Query query2 = em.createQuery("SELECT o FROM Interest o WHERE o.depositId=:depositId and  o.interestDate= (select max(m.interestDate) FROM Interest m WHERE m.depositId=:depositId and interestDate < :date)");
	   query2.setParameter("depositId", depositId);
	   query2.setParameter("date", date);
	   
	   List<Interest> intList= query2.getResultList();
	   if(intList.size()>0){
	    return intList.get(0);
	   }
	   else
	    return null;
	   
	  }
	 
	 public Interest getPreviousInterest(Long depositId, Date fromDate) {
		   //Date dt = DateService.getCurrentDate();
		   //Date dt = DateService.getCurrentDateTime();
		   
		   String sql = "SELECT o FROM Interest o WHERE o.depositId=:depositId and o.interestDate<:fromDate order by id desc";

		   Query query = em.createQuery(sql);
		   query.setParameter("depositId", depositId);
		   query.setParameter("fromDate", fromDate);
		      
		   List intList= query.getResultList();
		   if(intList.size()>0){
		    return (Interest)intList.get(0);
		   }
		   else
		    return null;
		   
		  }
		
	 public Interest getLastInterestCompounded(Long depositId){
		 
			Query query2 = em.createQuery(
					"SELECT o FROM Interest o WHERE o.depositId=:depositId and o.interestDate=(select max(m.interestDate) FROM Interest m WHERE m.depositId=:depositId)");
			query2.setParameter("depositId", depositId);
			List<Interest> intList = query2.getResultList();
			if (intList.size() > 0) {
				return intList.get(0);
			} else
				return null;

		}
	 
	public List<Interest> getInterestListGivenForPeriod(Long depositId, Date fromDate, Date toDate) {

//		Query query = em.createQuery(
//				"select o from Interest o where o.depositId=:depositId and interestDeducted=1 and interestAdjusted is null and o.interestCalcDate>=:fromDate and o.interestCalcDate<=:toDate");
//		
		// changed for tenure change, Total interest i need where negative part is also required
		Query query = em.createQuery(
				"select o from Interest o where o.depositId=:depositId and o.interestDate>=:fromDate and o.interestDate<=:toDate");
		query.setParameter("depositId", depositId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		List intList = query.getResultList();
		if (intList.size() > 0) {
			return (List<Interest>)intList;
		} else
			return null;

	}
		
}
