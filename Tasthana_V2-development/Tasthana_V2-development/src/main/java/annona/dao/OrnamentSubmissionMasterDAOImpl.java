package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.OrnamentSubmissionMaster;

@Repository
public class OrnamentSubmissionMasterDAOImpl implements OrnamentSubmissionMasterDAO {
	
	@PersistenceContext
	private EntityManager em;


	@Transactional
	public void save(OrnamentSubmissionMaster ornamentSubmissionMaster) {
		// TODO Auto-generated method stub
        em.persist(ornamentSubmissionMaster);
	}

	@Transactional
	public void update(OrnamentSubmissionMaster ornamentSubmissionMaster) {
		// TODO Auto-generated method stub
         em.merge(ornamentSubmissionMaster);
         em.flush();
	}

	@Override
	public List<OrnamentSubmissionMaster> getAllOrnamentSubmissionMasterDetails() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<OrnamentSubmissionMaster> ornamentSubmissionMaster = (List<OrnamentSubmissionMaster>) em.createQuery("from OrnamentSubmissionMaster").getResultList();

		return ornamentSubmissionMaster;
	}

	@Override
	public OrnamentSubmissionMaster getOrnamentSubmissionMastertById(Long id) {
		// TODO Auto-generated method stub
		OrnamentSubmissionMaster  ornamentSubmissionMaster =  (OrnamentSubmissionMaster) em.createQuery("from OrnamentSubmissionMaster where id=?").setParameter(1, id).getSingleResult();
		return ornamentSubmissionMaster;
	}

}
