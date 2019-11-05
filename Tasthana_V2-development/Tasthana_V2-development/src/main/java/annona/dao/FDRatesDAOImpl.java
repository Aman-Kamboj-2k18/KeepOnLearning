package annona.dao;

import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.FDRates;

@Repository
public class FDRatesDAOImpl implements FDRatesDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public EntityManager entityManager() {
		EntityManager em = new FDRatesDAOImpl().em;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public void saveFD(FDRates fdRates) {
		
		em.persist(fdRates);
	}


	@SuppressWarnings("unchecked")
	public Collection<FDRates> findAllFDs() {
		Query query = em.createQuery("SELECT o FROM FDRates o");

		return (Collection<FDRates>) query.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	public Collection<FDRates> findAllCustomerFDs(String userid) {
		
		Query query = em.createQuery("SELECT o FROM FDRates o where o.customerID ='"+userid+"'");

		return (Collection<FDRates>) query.getResultList();
	}

	
	public Double getTotalAmount(String userid, String fdId) {

		Double interest = (Double) em
				.createQuery("SELECT sum(interestAmount) FROM FDRates o where o.customerID =:userid and o.fdId =:fdId")
				.setParameter("userid", userid).setParameter("fdId", fdId).getSingleResult();

		return interest;
	}

	@Override
	public FDRates getById(Long id) {
		
		return em.find(FDRates.class, id);
	}

	public float getTotalFDAmount(String userid, String fdId) {

		float totalFD = (float) em
				.createQuery("SELECT max(fdCurrentAmount) FROM FDRates o where o.customerID =:userid and o.fdId =:fdId")
				.setParameter("userid", userid).setParameter("fdId", fdId).getSingleResult();

		return totalFD;
	}

	
	public Date getMaxdate(String userid, String fdId) {
		Date totalFD = (Date) em.createQuery("SELECT max(fdDeductDate) FROM FDRates o where o.customerID =:userid and o.fdId =:fdId").setParameter("userid", userid).setParameter("fdId", fdId).getSingleResult();
		return totalFD;
	}
	
	
	public Date getMaxTenuredate(String userid, String fdId) {
		Date totalFD = (Date) em.createQuery("SELECT max(fdDueDate) FROM FDRates o where o.customerID =:userid and o.fdId =:fdId").setParameter("userid", userid).setParameter("fdId", fdId).getSingleResult();
		return totalFD;
	}

	@SuppressWarnings("unchecked")
	public Collection<FDRates> getFDRatesData(String fdId) {
		Query query = em.createQuery("SELECT o FROM FDRates o where o.fdId ='"+fdId+"'");

		return (Collection<FDRates>) query.getResultList();
	}

	
	/**
	 * Method to get ID
	 * @param userid
	 * @param fdId
	 * @return
	 */
	public Long getFDData( String fdId) {

		Long id = (Long) em
				.createQuery("SELECT max(id) FROM FDRates o where o.fdId=:fdId")
				.setParameter("fdId", fdId).getSingleResult();

		return id;
	}
	/*public Long getFDData( String fdId) {

		Long id = (Long) em
				.createQuery("SELECT id FROM FDRates o where o.fdId=:fdId GROUP BY o.id ORDER BY o.id DESC LIMIT 1")
				.setParameter("fdId", fdId).getSingleResult();

		return id;
	}*/

	@Transactional
	public void update(FDRates fdRates) {
		em.merge(fdRates);
		em.flush();
	}


}
