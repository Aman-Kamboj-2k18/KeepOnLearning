package annona.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.FundTransfer;

@Repository
public class FundTransferDAOImpl implements FundTransferDAO {
	
	@PersistenceContext
	EntityManager em;

	@Transactional
	public void insert(FundTransfer fundTransfer) {

		em.persist(fundTransfer);
	}
	
	@Override
	public Double getTotalAmount(String fdId) {
		Double totalAmount = (Double) em.createQuery("SELECT sum(fdAmount) FROM FundTransfer o where o.fdId =:fdId")
				.setParameter("fdId", fdId).getSingleResult();

		return totalAmount;

	}

	@Override
	public Double getTotalInterestAmount(String fdId) {
		Double interest = (Double) em.createQuery("SELECT sum(interestAmount) FROM FundTransfer o where o.fdId =:fdId")
				.setParameter("fdId", fdId).getSingleResult();

		return interest;
	}

	
	@SuppressWarnings("unchecked")
	public Collection<FundTransfer> getAllData() {
		Query query = em.createQuery("SELECT o FROM FundTransfer o");

		return (Collection<FundTransfer>) query.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	public Collection<FundTransfer> getFDRatesData(String fdId) {
		Query query = em.createQuery("SELECT o FROM FundTransfer o where o.fdId ='"+fdId+"'");

		return (Collection<FundTransfer>) query.getResultList();
	}

}
