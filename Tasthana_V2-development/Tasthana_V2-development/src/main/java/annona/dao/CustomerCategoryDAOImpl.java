package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import annona.domain.CustomerCategory;

@Repository
public class CustomerCategoryDAOImpl implements CustomerCategoryDAO {

	@PersistenceContext
	EntityManager em;

	@Override
	public CustomerCategory save(CustomerCategory customerCategory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerCategory update(CustomerCategory customerCategory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerCategory findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerCategory> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CustomerCategory> cq = cb.createQuery(CustomerCategory.class);
		Root<CustomerCategory> from = cq.from(CustomerCategory.class);
		CriteriaQuery<CustomerCategory> all = cq.select(from);
		TypedQuery<CustomerCategory> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

}
