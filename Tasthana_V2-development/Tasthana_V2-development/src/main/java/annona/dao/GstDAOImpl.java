package annona.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import annona.domain.GST;

@Repository
public class GstDAOImpl implements GstDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Method for entity manager
	 * 
	 * @return
	 */
	public EntityManager entityManager() {
		EntityManager em = new GstDAOImpl().em;

		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public void insertGST(GST gst) {
		em.persist(gst);
	}

	
}
