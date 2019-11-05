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

import annona.domain.BankConfiguration;
import annona.domain.CustomerCategory;
import annona.domain.Deposit;
import annona.domain.DepositRates;
import annona.domain.ProductConfiguration;
import annona.domain.RatePeriods;
import annona.domain.Rates;

@Repository
public class RatesDAOImpl implements RatesDAO {

	@PersistenceContext
	private EntityManager em;

	
	@Transactional
	public void createCustomerCategory(CustomerCategory customerCategory) {
		em.persist(customerCategory);
	}

	@Transactional
	public void createRate(Rates rate) {
		em.persist(rate);
	}

	@Override
	public TypedQuery<Rates> findByCategory(String category) {
		TypedQuery<Rates> q = em.createQuery("SELECT o FROM Rates o where o.type =:category", Rates.class);
		q.setParameter("category", category);
		return q;
	}

	@Transactional
	public void update(Rates rates) {
		em.merge(rates);
		em.flush();
	}

	@SuppressWarnings("unchecked")
	public Collection<Rates> findAllRates() {

		Query query = em.createQuery("SELECT o FROM Rates o ORDER BY o.id ASC");

		return (Collection<Rates>) query.getResultList();
	}

	@Override
	public Rates findById(long id) {

		return em.find(Rates.class, id);
	}

	@Override
	public Rates getRatesByCategory(String category) {
		TypedQuery<Rates> q = em.createQuery("SELECT o FROM Rates o where o.type =:category", Rates.class);
		q.setParameter("category", category);

		List lst = q.getResultList();

		if (lst == null)
			return null;
		else if (lst.size() == 0)
			return null;
		else {
			return (Rates) lst.get(0);
		}

	}

	public BankConfiguration findAll() {
		Query query = em.createQuery("SELECT o FROM BankConfiguration o");
		List<BankConfiguration> lst = query.getResultList();

		if (lst.size() == 0)
			return null;
		else {
			return lst.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	public Rates getAllRatesByCustomerCategoryAndCitizen(String customerCategory, String citizen) {
		Query query = em.createQuery("SELECT o FROM Rates o where o.type =:customerCategory and o.citizen=:citizen");
		query.setParameter("customerCategory", customerCategory);
		query.setParameter("citizen", citizen);
		List<Rates> listRates = query.getResultList();
		if (listRates.size() > 0)
			return listRates.get(0);
		else
			return null;
	}

	public Rates getAllRatesByCustomerCategoryCitizenAndAccountType(String customerCategory, String citizen,
			String nriAccountType) {
		Query query = null;
		if (citizen.equalsIgnoreCase("NRI")) {
			query = em.createQuery(
					"SELECT o FROM Rates o where o.type =:customerCategory and o.citizen=:citizen and o.nriAccountType=:nriAccountType");
			query.setParameter("customerCategory", customerCategory);
			query.setParameter("citizen", citizen);
			query.setParameter("nriAccountType", nriAccountType);
		} else {
			query = em.createQuery("SELECT o FROM Rates o where o.type =:customerCategory and o.citizen=:citizen");
			query.setParameter("customerCategory", customerCategory);
			query.setParameter("citizen", citizen);
		}
		List<Rates> listRates = query.getResultList();
		if (listRates.size() > 0)
			return listRates.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<RatePeriods> getRateDurationsByDepositClaasification(String depositClassification) {
		Query query = em
				.createQuery("SELECT o FROM RatePeriods o where o.depositClassification=:depositClassification");
		query.setParameter("depositClassification", depositClassification);
		List<RatePeriods> listRates = query.getResultList();
		if (listRates.size() > 0)
			return listRates;
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<DepositRates> getAllRatePeriods() {
		Query query = em.createQuery("SELECT o FROM DepositRates o");

		List<DepositRates> depositRates = query.getResultList();
		if (depositRates.size() > 0)
			return depositRates;
		else
			return null;
	}

	public List<DepositRates> getAllDepositRatesByRatePeriodId(Long ratePeriodsId) {
		Query query = em.createQuery("SELECT o FROM DepositRates o where o.ratePeriodsId=:ratePeriodsId");

		query.setParameter("ratePeriodsId", ratePeriodsId);
		List<DepositRates> depositRates = query.getResultList();
		if (depositRates.size() > 0)
			return depositRates;
		else
			return null;

	}

	@SuppressWarnings("unchecked")
	public List<RatePeriods> getAllRatesPeriodFromId(Long ratePeriodsId) {
		Query query = em.createQuery("SELECT o FROM RatePeriods o where o.id=:ratePeriodsId");
		query.setParameter("ratePeriodsId", ratePeriodsId);
		List<RatePeriods> listRates = query.getResultList();
		if (listRates.size() > 0)
			return listRates;
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public DepositRates getRateDescriptionById(Long ratePeriodsId, String currency, String category, Date effectiveDate,
			String description, String depositClassification, String nriAccountType) {
		Query query = em.createQuery(
				"SELECT o FROM DepositRates o where o.ratePeriodsId=:ratePeriodsId and o.currency=:currency and o.category=:category and o.effectiveDate=:effectiveDate and o.description=:description and o.depositClassification = :depositClassification and o.nriAccountType=:nriAccountType");
		query.setParameter("ratePeriodsId", ratePeriodsId);
		query.setParameter("currency", currency);
		query.setParameter("category", category);
		query.setParameter("effectiveDate", effectiveDate);
		query.setParameter("description", description);
		query.setParameter("depositClassification", depositClassification);
		query.setParameter("nriAccountType", nriAccountType);
		List<DepositRates> listRates = query.getResultList();
		if (listRates.size() > 0)
			return listRates.get(0);
		else
			return null;

	}

	public BankConfiguration findAllDataByCitizen(String citizen) {
		Query query = em.createQuery("SELECT o FROM BankConfiguration o where o.citizen=:citizen");

		query.setParameter("citizen", citizen);

		List<BankConfiguration> lst = query.getResultList();
		if (lst.size() == 0)
			return null;
		else {
			return lst.get(0);
		}
	}

	public BankConfiguration findAllDataByCitizenAndAccountType(String citizen, String nriAccountType) {
		Query query = null;
		if(citizen.equalsIgnoreCase("RI"))
		{				
			query = em.createQuery(
					"SELECT o FROM BankConfiguration o where o.citizen=:citizen");
	
			query.setParameter("citizen", citizen);
		}
		else
		{
			query = em.createQuery(
					"SELECT o FROM BankConfiguration o where o.citizen=:citizen and o.nriAccountType = :nriAccountType");
	
			query.setParameter("citizen", citizen);
			query.setParameter("nriAccountType", nriAccountType);
		}
		
		List<BankConfiguration> lst = query.getResultList();
		if (lst.size() == 0)
			return null;
		else {
			return lst.get(0);
		}
	}


//	public List<BankConfiguration> getBankConfigurationListByInterestCompoundingBasis(String interestCompoundingBasis) {
//		Query query = em.createQuery(
//				"SELECT o FROM BankConfiguration o where upper(o.interestCompoundingBasis) =:interestCompoundingBasis");
//		query.setParameter("interestCompoundingBasis", interestCompoundingBasis.toUpperCase());
//		List<BankConfiguration> lst = query.getResultList();
//		return lst;
//	}

	@SuppressWarnings("unchecked")
	public List<RatePeriods> getRateDurations(String depositClassification, String citizen, String nriAccountType, String category) {
		Query query = null ;
		if(nriAccountType == null || nriAccountType == "") {
		 query = em
				.createQuery("SELECT o FROM RatePeriods o where o.depositClassification=:depositClassification and o.citizen=:citizen and o.category=:category");
		query.setParameter("depositClassification", depositClassification);
		query.setParameter("citizen", citizen);
		query.setParameter("category", category);
		}else {
			
			 query = em
						.createQuery("SELECT o FROM RatePeriods o where o.depositClassification=:depositClassification and o.citizen=:citizen and o.nriAccountType=:nriAccountType and o.category=:category");
				query.setParameter("depositClassification", depositClassification);
				query.setParameter("citizen", citizen);
				query.setParameter("nriAccountType", nriAccountType);
				query.setParameter("category", category);
		}
		List<RatePeriods> listRates = query.getResultList();
		if (listRates.size() > 0)
			return listRates;
		else
			return null;
	}

}
