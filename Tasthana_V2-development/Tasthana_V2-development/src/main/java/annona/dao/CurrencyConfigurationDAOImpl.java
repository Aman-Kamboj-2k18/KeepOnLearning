package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.CurrencyConfiguration;
import annona.domain.CustomerCategory;

@Repository
public class CurrencyConfigurationDAOImpl implements CurrencyConfigurationDAO {

	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public CurrencyConfiguration save(CurrencyConfiguration currencyConfiguration) {
		em.persist(currencyConfiguration);
		return currencyConfiguration;
	}

	@Override
	@Transactional
	public CurrencyConfiguration update(CurrencyConfiguration currencyConfiguration) {
		em.merge(currencyConfiguration);
		return currencyConfiguration;
	}

	@Override
	public CurrencyConfiguration findById(Long id) {
		try {
			CurrencyConfiguration configuration = em.find(CurrencyConfiguration.class, id);
			return configuration;
		} catch (Exception exception) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<CurrencyConfiguration> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CurrencyConfiguration> cq = cb.createQuery(CurrencyConfiguration.class);
		Root<CurrencyConfiguration> from = cq.from(CurrencyConfiguration.class);
		CriteriaQuery<CurrencyConfiguration> all = cq.select(from);
		TypedQuery<CurrencyConfiguration> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

	@Override
	public List<CurrencyConfiguration> findByCitizen(String citizen) {
		try {
			List<CurrencyConfiguration> configurations = em
					.createNamedQuery("from CurrencyConfiguration c where c.citizen = :citizen", CurrencyConfiguration.class)
					.setParameter("citizen", citizen).getResultList();
			return configurations;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	@Override
	public CurrencyConfiguration findByAccountType(String accountType) {
		try
		{
			return em
					.createNamedQuery("from CurrencyConfiguration c where c.accountType = :accountType", CurrencyConfiguration.class)
					.setParameter("accountType", accountType).getSingleResult();
		}
		catch(Exception e)
		{
			
		}
		return null;
	}

}
