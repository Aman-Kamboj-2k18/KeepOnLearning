package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.Branch;
import annona.domain.RoundOff;

@Repository
public class RoundOffDAOImpl implements RoundOffDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Transactional
	public void save(RoundOff roundOff) {
		// TODO Auto-generated method stub
		em.persist(roundOff);
	}

	@Override
	public void update(RoundOff roundOff) {
		// TODO Auto-generated method stub
		em.merge(roundOff);
		em.flush();
	}
	
	@Override
	public RoundOff getRoundingOff() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<RoundOff> roundOff = (List<RoundOff>) em.createQuery("from RoundOff").getResultList();
		if(roundOff != null && roundOff.size()>0)
			return roundOff.get(0);
		
		return null;
	}

	@Override
	public List<RoundOff> getAllDetailsFromRoundOff() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<RoundOff> roundOff = (List<RoundOff>) em.createQuery("from RoundOff").getResultList();

		return roundOff;
	}

	@Override
	public RoundOff getRoundOffById(Long id) {
		// TODO Auto-generated method stub
		RoundOff  roundOff =  (RoundOff) em.createQuery("from RoundOff where id=?").setParameter(1, id).getSingleResult();
		return roundOff;
	}
	
	@Override
	public Long getCountOfDecimalPlacesAndNearest(int decimalPlaces, String nearestHighestLowest) {
		// TODO Auto-generated method stub
		Long count = (Long) em
				.createQuery("SELECT COUNT(*) FROM RoundOff WHERE decimalPlaces=? and nearestHighestLowest=?")
		.setParameter(1, decimalPlaces).setParameter(2, nearestHighestLowest).getResultList().get(0);
		
		return count;
	}

}
