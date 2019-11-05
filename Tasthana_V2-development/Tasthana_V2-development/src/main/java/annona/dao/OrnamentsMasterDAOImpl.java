package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.OrnamentsMaster;

@Repository
public class OrnamentsMasterDAOImpl implements OrnamentsMasterDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void save(OrnamentsMaster ornamentsMaster) {
		// TODO Auto-generated method stub
		em.persist(ornamentsMaster);
	}

	@Transactional
	public void update(OrnamentsMaster ornamentsMaster) {
		// TODO Auto-generated method stub
		em.merge(ornamentsMaster);
		em.flush();
	}

	@Override
	public List<OrnamentsMaster> getAllOrnamentsMasterDetails() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<OrnamentsMaster> ornamentsMaster = (List<OrnamentsMaster>) em.createQuery("from OrnamentsMaster").getResultList();

		return ornamentsMaster;
	}

	@Override
	public OrnamentsMaster getOrnamentsMastertById(Long id) {
		// TODO Auto-generated method stub
		OrnamentsMaster  ornamentsMaster =  (OrnamentsMaster) em.createQuery("from OrnamentsMaster where id=?").setParameter(1, id).getSingleResult();
		return ornamentsMaster;
	}
	
	@Override
	public Long getCountOfOrnament(String ornament) {
		// TODO Auto-generated method stub
		ornament = ornament.toLowerCase();
		Long count = (Long) em
				.createQuery("SELECT COUNT(*) FROM OrnamentsMaster WHERE lower(ornament)=?")
		.setParameter(1, ornament).getSingleResult();
		
		return count;
	}
	
	@Override
	public Boolean getOrnamentExist(String ornament, Long id) {
		// TODO Auto-generated method stub
		ornament = ornament.toLowerCase();
		Query qry = em.createQuery("Select o from OrnamentsMaster o where lower(ornament)=? and id!=?");		
		qry.setParameter(1, ornament.toLowerCase());
		qry.setParameter(2, id);
		List lst = qry.getResultList();
		if(lst!=null && lst.size() > 0)
			return true;
		else
			return false;
	}
	

}
