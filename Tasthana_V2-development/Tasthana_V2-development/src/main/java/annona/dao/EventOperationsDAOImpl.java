package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import annona.domain.EventOperations;

@Repository
public class EventOperationsDAOImpl implements EventOperationsDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public EntityManager entityManager() {
		EntityManager em = new EventOperationsDAOImpl().em;

		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public void save(EventOperations eventOperations) {
		// TODO Auto-generated method stub
		em.persist(eventOperations);

	}

	@Transactional
	public void update(EventOperations eventOperations) {
		// TODO Auto-generated method stub
		em.merge(eventOperations);
		em.flush();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EventOperations> getAllEventOperationsDetails() {
		// TODO Auto-generated method stub
		List<EventOperations> eventOperations = (List<EventOperations>) em.createQuery("from EventOperations").getResultList();

		return eventOperations;
	}

	@Override
	public EventOperations getEventOperationsById(Long id) {
		// TODO Auto-generated method stub
		EventOperations  eventOperations =  (EventOperations) em.createQuery("from EventOperations where id=?").setParameter(1, id).getSingleResult();
		return eventOperations;
	}

}
