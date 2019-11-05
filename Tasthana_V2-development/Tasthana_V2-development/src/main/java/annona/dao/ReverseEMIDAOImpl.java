package annona.dao;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.BenificiaryDetails;
import annona.domain.ReverseEMI;
import annona.domain.ReverseEMIPayoffDetails;


@Repository
public class ReverseEMIDAOImpl implements ReverseEMIDAO {
	@PersistenceContext
	EntityManager em;


	 @Override
	 public List<ReverseEMI> getReverseEMIList(Long depositId) {

		 Query query = em.createQuery("SELECT o FROM ReverseEMI o WHERE o.depositId=:depositId ORDER BY id");
		 query.setParameter("depositId", depositId);
			
		 List lst = query.getResultList();
		 if(lst == null)
			 return null;
		 else
			 return (List<ReverseEMI>)lst;

	 }

	@Transactional
	public ReverseEMI insertReverseEMI(ReverseEMI reverseEMI) {
		em.persist(reverseEMI);
		return reverseEMI;
	}

	@Transactional
	public ReverseEMIPayoffDetails insertReverseEMIPayoffDetails(ReverseEMIPayoffDetails reverseEMIPayoffDetails) {
		em.persist(reverseEMIPayoffDetails);
		return reverseEMIPayoffDetails;
	}
	
	
}
