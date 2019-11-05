package annona.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import annona.domain.UnSuccessfulPayOff;
import annona.form.PayOfForm;

@Repository
public class UnSuccessfulPayOffDetailsDAOImpl implements UnSuccessfulPayOffDetailsDAO {


	@PersistenceContext
	private EntityManager em;

	public EntityManager entityManager() {
		EntityManager em = new UnSuccessfulPayOffDetailsDAOImpl().em;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}


	/*
	 * CreatedBy: Ravi Pratap Singh Query: To get the UnSuccessfulPayOff Details list 
	 *  for given time period.
	 * 
	 */
	@Override
	public List<UnSuccessfulPayOff> unSuccessfulPayOffDetailsList(Date fromDate, Date toDate) {
        String sql ="SELECT o From UnSuccessfulPayOff o where o.unSuccessfulPayoffDetailsDate>=:fromDate AND o.unSuccessfulPayoffDetailsDate<=:toDate";

		Query q = em.createQuery(sql);
		q.setParameter("fromDate",fromDate);
		q.setParameter("toDate",toDate);
		List<UnSuccessfulPayOff> list=	q.getResultList();
		return list;
		
	
	}

	

}
