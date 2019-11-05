package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.Branch;

@Repository
public class BranchDAOImpl implements BranchDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public EntityManager entityManager() {
		EntityManager em = new BranchDAOImpl().em;

		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
	
	@Transactional
	public void save(Branch branch) {
		// TODO Auto-generated method stub
		em.persist(branch);
	}

	@Transactional
	public void update(Branch branch) {
		// TODO Auto-generated method stub
		em.merge(branch);
		em.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Branch> getAllBranches() {
		// TODO Auto-generated method stub
		List<Branch> branch = (List<Branch>) em.createQuery("from Branch").getResultList();

		return branch;
	}
	
	@Override
	public Long getCountOfBranchNameAndCode(String branchName, String branchCode) {

		Long count = (Long) em
				.createQuery("SELECT COUNT(*) FROM Branch WHERE branchName=? or branchCode=?")
		.setParameter(1, branchName).setParameter(2, branchCode).getResultList().get(0);
		
		return count;

	}

	@Override
	public Branch getBranchById(Long id) {
		// TODO Auto-generated method stub
		Branch  branch =  (Branch) em.createQuery("from Branch where id=?").setParameter(1, id).getSingleResult();
		return branch;
	}

	@Override
	public Long getCountOfBranchName(String branchName) {
		// TODO Auto-generated method stub
		Long count = (Long) em
				.createQuery("SELECT COUNT(*) FROM Branch WHERE branchName=?")
		.setParameter(1, branchName).getSingleResult();
		
		return count;
	}

	@Override
	public Long getCountOfBranchCode(String branchCode) {
		// TODO Auto-generated method stub
		Long count = (Long) em
				.createQuery("SELECT COUNT(*) FROM Branch WHERE branchCode=?")
		.setParameter(1, branchCode).getSingleResult();
		
		return count;
	}

	@Override
	public List<Branch> getAllBranchesByState(String state) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Branch> branch = (List<Branch>) em.createQuery("from Branch where state=?1").setParameter(1, state).getResultList();

		return branch;
	}

	@Override
	public Branch getBranchByBranchCode(String branchCode) {
		// TODO Auto-generated method stub
		Branch  branch =  (Branch) em.createQuery("from Branch where branchCode=?").setParameter(1, branchCode).getSingleResult();
		return branch;
	}

}
