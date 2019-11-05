package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.Branch;
import annona.domain.HolidayConfiguration;
import annona.domain.NRIServiceBranches;

@Repository
public class NRIServiceBranchesDAOImpl implements NRIServiceBranchesDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void save(NRIServiceBranches nRIServiceBranches) {
		// TODO Auto-generated method stub
		em.persist(nRIServiceBranches);
	}

	@Transactional
	public void update(NRIServiceBranches nRIServiceBranches) {
		// TODO Auto-generated method stub
		em.merge(nRIServiceBranches);
		em.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NRIServiceBranches> getAllNRIServiceBranches() {
		// TODO Auto-generated method stub
		List<NRIServiceBranches> nRIServiceBranches = (List<NRIServiceBranches>) em
				.createQuery("from NRIServiceBranches where isSelected = TRUE").getResultList();

		return nRIServiceBranches;
	}

	@Override
	public NRIServiceBranches getNRIServiceBranchesById(Long id) {
		// TODO Auto-generated method stub
		NRIServiceBranches nRIServiceBranches = (NRIServiceBranches) em
				.createQuery("from NRIServiceBranches where id=?").setParameter(1, id).getSingleResult();
		return nRIServiceBranches;
	}

	@Override
	@Transactional
	public void delete(String branchCode) {
		@SuppressWarnings("unchecked")
		List<NRIServiceBranches> configurations = em
				.createQuery("from NRIServiceBranches where  branchCode =:branchCode ")
				.setParameter("branchCode", branchCode).getResultList();
		for (NRIServiceBranches nriConfiguration : configurations) {
			em.remove(nriConfiguration);

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public NRIServiceBranches isPresent(String branchCode) {
		try {
		Query query = em.createQuery("from NRIServiceBranches   where  branchCode=:branchCode ");
		query.setParameter("branchCode", branchCode);

		return (NRIServiceBranches) query.getSingleResult();
		
		}catch(NoResultException e) {
			return null;
		}

	}

	@Override
	public NRIServiceBranches getNRIServiceBranchesByBranchId(Long branchId) {
		// TODO Auto-generated method stub
		NRIServiceBranches nRIServiceBranches = (NRIServiceBranches) em
				.createQuery("from NRIServiceBranches where branchId=? and isSelected = true")
				.setParameter(1, branchId).getSingleResult();
		return nRIServiceBranches;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NRIServiceBranches> getNRIServiceBranchesByBranchCode(String branchCode) {
		Query query = em.createQuery("from NRIServiceBranches   where  branchCode=:branchCode ");
		query.setParameter("branchCode", branchCode);

		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NRIServiceBranches> getNRIServiceBranchesNotInByBranchCode(String[] branchCodes) {
		String Query = "from NRIServiceBranches where branchCode Not in (";
		int i=0;
		for (String branchCode : branchCodes) {
			if(i<branchCodes.length-1) {
				Query = Query + "'" + branchCode + "',";
			}else {
				Query = Query + "'" + branchCode + "'";
			}
			i++;
		}
		Query = Query + ")";
		Query query = em.createQuery(Query);
		return query.getResultList();
	}
	
	@Override
	public NRIServiceBranches getNRIServiceBranchByBranchCode(String branchCode) {
		Query query = em.createQuery("from NRIServiceBranches   where  branchCode=:branchCode and isSelected = TRUE ");
		query.setParameter("branchCode", branchCode);

		return (NRIServiceBranches) query.getSingleResult();

	}

}
