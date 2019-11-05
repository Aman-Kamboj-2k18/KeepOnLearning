package annona.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.CitizenConversionDetails;

@Repository
public class CitizenConversionDetailsDAOImpl implements CitizenConversionDetailsDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void save(CitizenConversionDetails citizenConversionDetails) {
		// TODO Auto-generated method stub
		em.persist(citizenConversionDetails);

	}

	@Transactional
	public void update(CitizenConversionDetails citizenConversionDetails) {
		// TODO Auto-generated method stub
		em.merge(citizenConversionDetails);
		em.flush();

	}

	@Override
	public CitizenConversionDetails getCitizenConversionDetailsById(Long id) {
		// TODO Auto-generated method stub
		CitizenConversionDetails  citizenConversionDetails =  (CitizenConversionDetails) em.createQuery("from CitizenConversionDetails where id=?").setParameter(1, id).getSingleResult();
		return citizenConversionDetails;
	}

}
