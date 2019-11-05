package annona.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import annona.domain.Transaction;

@Repository
public class TranscationDAOImpl implements TransactionDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insertTransaction(Transaction transaction) {
		em.persist(transaction);
	}

	@SuppressWarnings("unchecked")
	public Collection<Transaction> getList() {

		Query query = em.createQuery("SELECT o FROM Transaction o");

		return (Collection<Transaction>) query.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	public Collection<Transaction> getFdData(String fdId) {
		Query query = em.createQuery("SELECT o FROM Transaction o where o.transactionId ='"+fdId+"' ORDER BY o.id ASC");

		return (Collection<Transaction>) query.getResultList();
	}
}
