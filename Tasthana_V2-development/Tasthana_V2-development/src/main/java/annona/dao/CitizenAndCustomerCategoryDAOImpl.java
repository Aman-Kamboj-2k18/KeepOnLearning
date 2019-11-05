package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.CitizenAndCustomerCategoryMapping;

@Repository
public class CitizenAndCustomerCategoryDAOImpl implements CitizenAndCustomerCategoryDAO {

	@PersistenceContext
	EntityManager em;

	@Override
	@Transactional
	public CitizenAndCustomerCategoryMapping save(CitizenAndCustomerCategoryMapping citizenAndCustomerCategory) {
		// TODO Auto-generated method stub
		em.persist(citizenAndCustomerCategory);
		em.flush();
		return citizenAndCustomerCategory;
	}

	@Override
	@Transactional
	public CitizenAndCustomerCategoryMapping update(CitizenAndCustomerCategoryMapping citizenAndCustomerCategory) {
		// TODO Auto-generated method stub
		em.merge(citizenAndCustomerCategory);
		return citizenAndCustomerCategory;
	}

	@Override
	public CitizenAndCustomerCategoryMapping findById(Long id) {
		// TODO Auto-generated method stub
		return em.find(CitizenAndCustomerCategoryMapping.class, id);
	}

	@Override
	public List<CitizenAndCustomerCategoryMapping> findAll() {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CitizenAndCustomerCategoryMapping> cq = cb.createQuery(CitizenAndCustomerCategoryMapping.class);
		Root<CitizenAndCustomerCategoryMapping> from = cq.from(CitizenAndCustomerCategoryMapping.class);
		CriteriaQuery<CitizenAndCustomerCategoryMapping> all = cq.select(from);
		TypedQuery<CitizenAndCustomerCategoryMapping> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

	@Override
	public CitizenAndCustomerCategoryMapping findByCustomerCategoryIdAndCitizen(Long customerCategoryId,
			String citizen) {
		try {
		return (CitizenAndCustomerCategoryMapping)em.createQuery("from CitizenAndCustomerCategoryMapping c where customercategoryid = :customercategoryId and citizen = :citizen")
		.setParameter("citizen",citizen)
		.setParameter("customercategoryId", customerCategoryId).getSingleResult();
		}catch (NoResultException e) {
			return null;
		}

	}

	@Override
	@Transactional
	public int deleteByCitizen(String citizen) {
		 Query query = em.createNativeQuery("delete from CitizenAndCustomerCategoryMapping c where c.citizen = :citizen");
				query.setParameter("citizen", citizen);
				int rowDeleted  = query.executeUpdate();
		return rowDeleted;
	}

}
