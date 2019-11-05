package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.loader.custom.Return;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.CitizenAndCustomerCategoryMapping;
import annona.domain.GSTDeduction;

/**
 * 
 * @author Sachin
 *
 */
@Repository
public class GSTDeductionDAOImpl implements GSTDeductionDAO{
	/**
	 * @since 2019
	 */
	
	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public GSTDeduction save(GSTDeduction deduction) {
		// TODO Auto-generated method stub
		em.persist(deduction);
		return deduction;
	}

	@Override
	@Transactional
	public GSTDeduction update(GSTDeduction deduction) {
		// TODO Auto-generated method stub
		em.merge(deduction);
		return deduction;
	}

	@Override
	@Transactional
	public GSTDeduction findById(Long id) {
		return null;
		
	}

	
	
	//@Override
	@Transactional
	public GSTDeduction findAll() {
		try {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<GSTDeduction> cq = cb.createQuery(GSTDeduction.class);
		Root<GSTDeduction> from = cq.from(GSTDeduction.class);
		CriteriaQuery<GSTDeduction> all = cq.select(from);
		TypedQuery<GSTDeduction> allQuery = em.createQuery(all);
		return allQuery.getSingleResult();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
}
