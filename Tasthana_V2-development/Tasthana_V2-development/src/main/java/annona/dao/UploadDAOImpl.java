package annona.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.Customer;
import annona.domain.UploadedFile;



@Repository
public class UploadDAOImpl implements UploadDAO {

	@PersistenceContext
	private EntityManager em;

	public EntityManager entityManager() {
		EntityManager em = new UploadDAOImpl().em;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	
	
	@Transactional
	public void createUser(UploadedFile uploadFile) {
		em.persist(uploadFile);
	}

	@Override
	public List<UploadedFile> getFilesByCustomerIdAndAccountNumber(Long customerId,String depositAccountNumber) {
		TypedQuery<UploadedFile> q = em.createQuery(
				"SELECT o FROM UploadedFile AS o WHERE o.customerId= :customerId and o.depositAccountNumber= :depositAccountNumber",
				UploadedFile.class);
		q.setParameter("customerId", customerId);
		q.setParameter("depositAccountNumber", depositAccountNumber);
		return q.getResultList();
	}
	
	public UploadedFile findId(Long id) {

		return em.find(UploadedFile.class, id);

	}

	@Transactional
	public void deleteUploadedFile(Long id) {
		UploadedFile file = em.find(UploadedFile.class, id);
		em.remove(file);
	}
	
	 @SuppressWarnings("unchecked")
	  public List<UploadedFile> getByCustomerId(Long customerId){
			Query query = em.createQuery("SELECT o FROM UploadedFile o WHERE o.customerId=:customerId");
			 query.setParameter("customerId", customerId);
			return query.getResultList();
		}
}
