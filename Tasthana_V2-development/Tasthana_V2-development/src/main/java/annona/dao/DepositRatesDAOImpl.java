package annona.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import annona.domain.DepositRates;
import annona.form.ReportForm;
import annona.services.FDService;

@Repository
public class DepositRatesDAOImpl implements DepositRatesDAO {

	@PersistenceContext
	EntityManager em;

	@SuppressWarnings("unchecked")
	public Collection<DepositRates> getDataByCategory() {

		Query query = em.createQuery("SELECT o FROM DepositRates o");

		return (Collection<DepositRates>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	// public TypedQuery<DepositRates> getRateByCategory(String category, String
	// currency) {
	public TypedQuery<DepositRates> getRatesByCategory(String category, String currency, String depositClassification, Double amountSlabFrom, Double amountSlabTo) {

		if (category == null || category.length() == 0)
			throw new IllegalArgumentException("The username argument is required");

//		String qry = "SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and o.depositClassification=:depositClassification and "
//				+ "o.effectiveDate=(SELECT max(effectiveDate) FROM DepositRates m where m.category=:category and m.currency=:currency and m.depositClassification=:depositClassification )";
//		
		String qry = "";
		TypedQuery<DepositRates> query = null;
		if(amountSlabTo == null)
		{
			qry = "SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and o.depositClassification=:depositClassification and "
				+ " effectiveDate=(SELECT max(effectiveDate) FROM DepositRates m where m.category=:category and m.currency=:currency and m.depositClassification=:depositClassification and m.amountSlabFrom<=:amountSlabFrom)";
		
			query = em.createQuery(qry, DepositRates.class);

			query.setParameter("category", category);
			query.setParameter("currency", currency);
			query.setParameter("depositClassification", depositClassification);
			query.setParameter("amountSlabFrom", amountSlabFrom);

		}
		else
		{
			qry = "SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and o.depositClassification=:depositClassification and "
				+ " effectiveDate=(SELECT max(effectiveDate) FROM DepositRates m where m.category=:category and m.currency=:currency and m.depositClassification=:depositClassification and m.amountSlabFrom<=:amountSlabFrom and m.amountSlabTo>=:amountSlabTo)";
		
			query = em.createQuery(qry, DepositRates.class);
	
			query.setParameter("category", category);
			query.setParameter("currency", currency);
			query.setParameter("depositClassification", depositClassification);
			query.setParameter("amountSlabFrom", amountSlabFrom);
			query.setParameter("amountSlabTo", amountSlabTo);
		}

		return query;
	}

	@SuppressWarnings("unchecked")
	// public TypedQuery<DepositRates> getRateByCategory(String category, String
	// currency) {
	public TypedQuery<DepositRates> getRatesByCategoryAndCitizen(String category, String currency, String depositClassification, Double amountSlabFrom, Double amountSlabTo,String nriAccountType) {

		if (category == null || category.length() == 0)
			throw new IllegalArgumentException("The username argument is required");

//		String qry = "SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and o.depositClassification=:depositClassification and "
//				+ "o.effectiveDate=(SELECT max(effectiveDate) FROM DepositRates m where m.category=:category and m.currency=:currency and m.depositClassification=:depositClassification )";
//		
		String qry = "";
		TypedQuery<DepositRates> query = null;
		if(amountSlabTo == null)
		{
			if(!nriAccountType.equals(""))
				qry = "SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and o.depositClassification=:depositClassification and o.amountSlabFrom>=:amountSlabFrom and o.nriAccountType=:nriAccountType and "
				+ " effectiveDate=(SELECT max(effectiveDate) FROM DepositRates m where m.category=:category and m.currency=:currency and m.depositClassification=:depositClassification and m.amountSlabFrom>=:amountSlabFrom and m.nriAccountType=:nriAccountType)";
			else
				qry = "SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and o.depositClassification=:depositClassification and o.amountSlabFrom>=:amountSlabFrom and o.nriAccountType is null and "
						+ " effectiveDate=(SELECT max(effectiveDate) FROM DepositRates m where m.category=:category and m.currency=:currency and m.depositClassification=:depositClassification and m.amountSlabFrom>=:amountSlabFrom and m.nriAccountType is null)";
		
			query = em.createQuery(qry, DepositRates.class);

			query.setParameter("category", category);
			query.setParameter("currency", currency);
			query.setParameter("depositClassification", depositClassification);
			query.setParameter("amountSlabFrom", amountSlabFrom);
			if(!nriAccountType.equals(""))
				query.setParameter("nriAccountType", nriAccountType);
		}
		else
		{
			if(!nriAccountType.equals(""))
				qry = "SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and o.depositClassification=:depositClassification "
				+ " and o.amountSlabFrom>=:amountSlabFrom and o.amountSlabTo<=:amountSlabTo and o.nriAccountType=:nriAccountType and "
				+ " effectiveDate=(SELECT max(effectiveDate) FROM DepositRates m where m.category=:category and m.currency=:currency and m.depositClassification=:depositClassification and m.amountSlabFrom>=:amountSlabFrom and m.amountSlabTo<=:amountSlabTo and m.nriAccountType=:nriAccountType)";
			else
				qry = "SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and o.depositClassification=:depositClassification and "
						+ " o.amountSlabFrom>=:amountSlabFrom and o.amountSlabTo<=:amountSlabTo and o.nriAccountType is null and " 
						+ " effectiveDate=(SELECT max(effectiveDate) FROM DepositRates m where m.category=:category and m.currency=:currency and m.depositClassification=:depositClassification and m.amountSlabFrom>=:amountSlabFrom and m.amountSlabTo<=:amountSlabTo and m.nriAccountType is null)";
		
			query = em.createQuery(qry, DepositRates.class);
	
			query.setParameter("category", category);
			query.setParameter("currency", currency);
			query.setParameter("depositClassification", depositClassification);
			

			query.setParameter("amountSlabFrom", amountSlabFrom);
			query.setParameter("amountSlabTo", amountSlabTo);
			
			if(!nriAccountType.equals(""))
				query.setParameter("nriAccountType", nriAccountType);
		}

		return query;
	}

	public DepositRates getRateByMaturityPeriodAndCategory(Integer days, String category, String currency,
			Double depositAmount, String depositClassification) {
		Query query = em
				.createQuery("SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and "
						+ "o.calMaturityPeriodFromInDays<=:days and o.calMaturityPeriodToInDays>=:days and "
						+ "o.effectiveDate<= current_date and  o.interestRate!=0 and "
						+ "o.amountSlabFrom<=:depositAmount and o.amountSlabTo>=:depositAmount and "
						+ "o.depositClassification=:depositClassification order by  o.effectiveDate desc ");
		query.setParameter("category", category);
		query.setParameter("currency", currency);
		query.setParameter("days", days);
		query.setParameter("depositClassification", depositClassification);
		query.setParameter("depositAmount", depositAmount);

		@SuppressWarnings("unchecked")
		List<DepositRates> ratesList = query.getResultList();
		if (ratesList.size() > 0) {
			return ratesList.get(0);
		} else {
			return null;
		}

	}

	public DepositRates getRateByMaturityPeriodAndCategory(Integer days, String category, String currency,
			Double fromSlab, Double toSlab, String depositClassification) {
		Query query = em
				.createQuery("SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and "
						+ "o.calMaturityPeriodFromInDays<=:days and o.calMaturityPeriodToInDays>=:days and "
						+ "o.effectiveDate<= current_date and  o.interestRate!=0 and "
						+ "o.amountSlabFrom=:fromSlab and o.amountSlabTo=:toSlab"
						+ "o.depositClassification=:depositClassification order by  o.effectiveDate desc ");
		query.setParameter("category", category);
		query.setParameter("currency", currency);
		query.setParameter("days", days);
		query.setParameter("depositClassification", depositClassification);
		query.setParameter("fromSlab", fromSlab);
		query.setParameter("toSlab", toSlab);

		@SuppressWarnings("unchecked")
		List<DepositRates> ratesList = query.getResultList();
		if (ratesList.size() > 0) {
			return ratesList.get(0);
		} else {
			return null;
		}

	}
	
	public List<Double> getFromAmountSlabList(String category, String currency, String depositClassification) {
		
		Query query = em
				.createQuery("SELECT distinct o.amountSlabFrom FROM DepositRates o where o.category=:category and o.currency=:currency and "
						+ "o.depositClassification=:depositClassification");
		
		//query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		query.setParameter("category", category);
		query.setParameter("currency", currency);
		query.setParameter("depositClassification", depositClassification);

		List lst =  query.getResultList();
		return (List<Double>)lst;
	}
	
	public Double getToAmountSlab(String category, String currency, String depositClassification, Double amountSlabFrom ) {
		
		Query query = em
				.createQuery("SELECT o.amountSlabTo FROM DepositRates o where o.category=:category and o.currency=:currency and "
						+ "o.depositClassification=:depositClassification and o.amountSlabFrom=:amountSlabFrom order by o.effectiveDate desc");
		
		query.setParameter("category", category);
		query.setParameter("currency", currency);
		query.setParameter("depositClassification", depositClassification);
		query.setParameter("amountSlabFrom", amountSlabFrom);

		List lst =  query.getResultList();
		if(lst!=null && lst.size()>0)
			return (Double)lst.get(0);
		else
			return null;
	}
	
	public DepositRates getInterestRate(Integer tenure, String category, String currency,
			Double depositAmount, String depositClassification,  String citizen, String accountType) {
		
		Query query = null;
		if(citizen.equalsIgnoreCase("NRI"))
		{
			 query = em
						.createQuery("SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and "
								+ "o.calMaturityPeriodFromInDays<=:tenure and o.calMaturityPeriodToInDays>=:tenure and "
								+ "o.interestRate!=0 and o.amountSlabFrom<=:depositAmount and o.amountSlabTo>=:depositAmount and "
								+ "o.depositClassification=:depositClassification and o.citizen=:citizen and o.nriAccountType=:accountType order by o.effectiveDate desc ");
				query.setParameter("category", category);
				query.setParameter("currency", currency);
				query.setParameter("tenure", tenure);
				query.setParameter("depositClassification", depositClassification);
				query.setParameter("depositAmount", depositAmount);
				query.setParameter("citizen", citizen);
				query.setParameter("accountType", accountType);
			 
		}
		else
		{
			query = em
					.createQuery("SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and "
							+ "o.calMaturityPeriodFromInDays<=:tenure and o.calMaturityPeriodToInDays>=:tenure and "
							+ "o.interestRate!=0 and o.amountSlabFrom<=:depositAmount and o.amountSlabTo>=:depositAmount and "
							+ "o.depositClassification=:depositClassification and o.citizen=:citizen order by o.effectiveDate desc ");
			query.setParameter("category", category);
			query.setParameter("currency", currency);
			query.setParameter("tenure", tenure);
			query.setParameter("depositClassification", depositClassification);
			query.setParameter("depositAmount", depositAmount);
			query.setParameter("citizen", citizen);
			
		}
		@SuppressWarnings("unchecked")
		List<DepositRates> ratesList = query.getResultList();
		if (ratesList.size() > 0) {
			return ratesList.get(0);
		} else {
			return null;
		}

	}
	
   public List<Double> getFromAmountSlabListForViewRate(String category, String currency, String depositClassification, String citizen, String nriAccountType) {

	   Query query = null;
		
		if(citizen.equalsIgnoreCase("RI"))
		{
			query = em
			.createQuery("SELECT distinct o.amountSlabFrom FROM DepositRates o where o.category=:category and o.currency=:currency and "
					+ "o.depositClassification=:depositClassification and o.citizen=:citizen");
			
			query.setParameter("category", category);
			query.setParameter("currency", currency);
			query.setParameter("depositClassification", depositClassification);
			query.setParameter("citizen", citizen);
		}
		
		else
		{
			query = em
			.createQuery("SELECT distinct o.amountSlabFrom FROM DepositRates o where o.category=:category and o.currency=:currency and "
					+ "o.depositClassification=:depositClassification and o.citizen=:citizen and o.nriAccountType=:nriAccountType");
		
			//query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			query.setParameter("category", category);
			query.setParameter("currency", currency);
			query.setParameter("depositClassification", depositClassification);
			query.setParameter("citizen", citizen);
			query.setParameter("nriAccountType", nriAccountType);
		
		}
		List lst =  query.getResultList();
		return (List<Double>)lst;
	}
   
   @SuppressWarnings("unchecked")

   public TypedQuery<DepositRates> getRatesByCategoryDependsOnCitizen(String category, String currency, String depositClassification, Double amountSlabFrom, Double amountSlabTo, String citizen, String nriAccountType) {

   	if (category == null || category.length() == 0)
   		throw new IllegalArgumentException("The username argument is required");

   	String qry = "";
   	TypedQuery<DepositRates> query = null;
   	if(amountSlabTo == null)
   	{
   		qry = "SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and o.depositClassification=:depositClassification and "
   			+ " effectiveDate=(SELECT max(effectiveDate) FROM DepositRates m where m.category=:category and m.currency=:currency and m.depositClassification=:depositClassification and m.amountSlabFrom<=:amountSlabFrom and m.citizen=:citizen and m.nriAccountType=:nriAccountType)";
   	
   		query = em.createQuery(qry, DepositRates.class);

   		query.setParameter("category", category);
   		query.setParameter("currency", currency);
   		query.setParameter("depositClassification", depositClassification);
   		query.setParameter("amountSlabFrom", amountSlabFrom);
   		query.setParameter("citizen", citizen);
   		query.setParameter("nriAccountType", nriAccountType);
   	}
   	else
   	{
   		qry = "SELECT o FROM DepositRates o where o.category=:category and o.currency=:currency and o.depositClassification=:depositClassification and "
   			+ " effectiveDate=(SELECT max(effectiveDate) FROM DepositRates m where m.category=:category and m.currency=:currency and m.depositClassification=:depositClassification and m.amountSlabFrom<=:amountSlabFrom and m.amountSlabTo>=:amountSlabTo and m.citizen=:citizen and m.nriAccountType=:nriAccountType)";
   	
   		query = em.createQuery(qry, DepositRates.class);

   		query.setParameter("category", category);
   		query.setParameter("currency", currency);
   		query.setParameter("depositClassification", depositClassification);
   		query.setParameter("amountSlabFrom", amountSlabFrom);
   		query.setParameter("amountSlabTo", amountSlabTo);
   		query.setParameter("citizen", citizen);
   		query.setParameter("nriAccountType", nriAccountType);
   	}

   	return query;
   }
}
