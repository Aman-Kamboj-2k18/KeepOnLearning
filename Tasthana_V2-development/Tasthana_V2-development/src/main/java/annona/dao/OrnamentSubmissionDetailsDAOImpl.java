package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.OrnamentSubmissionDetails;
import annona.domain.OrnamentSubmissionMaster;

@Repository
public class OrnamentSubmissionDetailsDAOImpl implements OrnamentSubmissionDetailsDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void save(OrnamentSubmissionDetails ornamentSubmissionDetails) {
		// TODO Auto-generated method stub
        em.persist(ornamentSubmissionDetails);
	}

	@Transactional
	public void update(OrnamentSubmissionDetails ornamentSubmissionDetails) {
		// TODO Auto-generated method stub
        em.merge(ornamentSubmissionDetails);
	}

	@Override
	public List<OrnamentSubmissionDetails> getAllOrnamentSubmissionrDetails() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<OrnamentSubmissionDetails> ornamentSubmissionDetails = (List<OrnamentSubmissionDetails>) em.createQuery("from OrnamentSubmissionDetails").getResultList();

		return ornamentSubmissionDetails;
	}

	@Override
	public OrnamentSubmissionDetails getOrnamentSubmissionDetailstById(Long id) {
		// TODO Auto-generated method stub
		OrnamentSubmissionDetails  ornamentSubmissionDetails =  (OrnamentSubmissionDetails) em.createQuery("from OrnamentSubmissionDetails where id=?").setParameter(1, id).getSingleResult();
		return ornamentSubmissionDetails;
	}

}
