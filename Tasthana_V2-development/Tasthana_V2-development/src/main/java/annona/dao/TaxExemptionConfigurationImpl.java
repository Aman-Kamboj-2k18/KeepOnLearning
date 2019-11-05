package annona.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.TaxExemptionConfiguration;
import annona.domain.WithdrawPenaltyMaster;
import annona.services.DateService;
@Repository
public class TaxExemptionConfigurationImpl implements TaxExemptionConfigurationDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public TaxExemptionConfiguration save(TaxExemptionConfiguration configuration) {
		em.persist(configuration);
		return configuration;
	}
	
	
	
	public List<TaxExemptionConfiguration> findByEffietiveDateTaxExemptionConfiguration(Date date) {
		Query query = em
				.createQuery("FROM TaxExemptionConfiguration o where o.effectiveDate = :effectiveDate");
		query.setParameter("effectiveDate", date);
		@SuppressWarnings("unchecked")
		List<TaxExemptionConfiguration> taxExemptionConfigurations = query.getResultList();

		return taxExemptionConfigurations;
	}
	
	
	public List<Date> findByEffectiveDate() {
		Query query = em
				.createQuery("SELECT distinct o.effectiveDate FROM TaxExemptionConfiguration o order by o.effectiveDate");

		@SuppressWarnings("unchecked")
		List<Date> taxExemptionConfigurations = query.getResultList();

		return taxExemptionConfigurations;
	}



	@Override
	@Transactional
	public TaxExemptionConfiguration update(TaxExemptionConfiguration configuration) {
		em.merge(configuration);
		return configuration;
	}



	@Override
	@Transactional
	public TaxExemptionConfiguration findById(Long id) {
		return em.find(TaxExemptionConfiguration.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<TaxExemptionConfiguration> getTaxExemptionConfigurationList() {

		String sql = "SELECT o FROM TaxExemptionConfiguration o where date(o.effectiveDate) <= date(:effectiveDate) order by o.effectiveDate desc";
			
		Query query = em.createQuery(sql);
		query.setParameter("effectiveDate", DateService.getCurrentDate());
		List lst =  query.getResultList();
		if(lst!=null && lst.size()>0)
			return (List<TaxExemptionConfiguration>)lst;
		else
			return null;

	}
}
