package annona.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.AccountDetails;
import annona.domain.Customer;
import annona.domain.DepositRates;
import annona.domain.SweepConfiguration;
import annona.domain.SweepInFacilityForCustomer;
import annona.form.CustomerForm;

@Repository
public class SweepConfigurationDAOImpl implements SweepConfigurationDAO {

	@PersistenceContext
	private EntityManager em;
	
	public SweepConfiguration getActiveSweepConfiguration() {

		Query query = em.createQuery(
				"SELECT o FROM SweepConfiguration o where o.isActive=1");
		List SweepConfigurationList  = query.getResultList();
		
		if(SweepConfigurationList !=null && SweepConfigurationList.size()>0)
			return (SweepConfiguration)SweepConfigurationList.get(0);
		else
			return null;
	}
	
	public SweepConfiguration findById(Long id) {
		if (id == null)
			return null;
		return em.find(SweepConfiguration.class, id);

	}

	@Transactional
	public SweepConfiguration insertSweepConfiguration(SweepConfiguration sweepConfiguration) {
		em.persist(sweepConfiguration);
		return sweepConfiguration;
	}


	public SweepInFacilityForCustomer getSweepInFacilityForCustomer(Long customerId) {

		SweepInFacilityForCustomer sweepFacility = null;
		try
		{
			Query query = em.createQuery(
					"SELECT o FROM SweepInFacilityForCustomer o where o.customerId =:customerId order by id desc");
			query.setParameter("customerId", customerId);
			List<SweepInFacilityForCustomer> lst = query.getResultList();
			if(lst !=null && lst.size()>0)
				sweepFacility =  (SweepInFacilityForCustomer)lst.get(0);
		}
		catch (NoResultException e) {
			// TODO: handle exception
		}

		return sweepFacility;

	}

	@Transactional
	public SweepInFacilityForCustomer insertSweepInFacilityForCustomer(SweepInFacilityForCustomer sweepInFacilityForCustomer) {
		em.persist(sweepInFacilityForCustomer);
		return sweepInFacilityForCustomer;
	}

	
	//------------------------------------------------------------
	
	

	public SweepInFacilityForCustomer findByCustomerId(Long customerId) {
		try {
	Query query = em.createQuery("SELECT o FROM SweepInFacilityForCustomer o where o.customerId =:customerId");
	query.setParameter("customerId", customerId);
		SweepInFacilityForCustomer sweepFacility = (SweepInFacilityForCustomer) query.getSingleResult();
	return sweepFacility;
		}catch (NoResultException e) {
			// TODO: handle exception
		}

		return null;

	}


	@Transactional
	public SweepConfiguration update(SweepConfiguration sweepConfiguration) {

		return em.merge(sweepConfiguration);

	}

	@Transactional
	public SweepConfiguration save(SweepConfiguration sweepConfiguration) {
		em.persist(sweepConfiguration);
		return sweepConfiguration;

	}

}
