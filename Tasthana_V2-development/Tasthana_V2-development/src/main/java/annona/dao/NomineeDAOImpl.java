package annona.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.DepositHolderNominees;
import annona.domain.DepositRates;

@Repository
public class NomineeDAOImpl implements NomineeDAO {
	
	@PersistenceContext
    EntityManager em;
	
	@Transactional
	public DepositHolderNominees saveNominee(DepositHolderNominees nominee){
		em.persist(nominee);
		return nominee;
	}
	
	@Override
	public DepositHolderNominees accountHoderNominee(Long depositHolderId) {

	    Query query = em.createQuery("SELECT o FROM DepositHolderNominees o where o.depositHolderId=:depositHolderId");
	    query.setParameter("depositHolderId", depositHolderId);
	    List<DepositHolderNominees> depositHolderList= query.getResultList();
	    
	    if(depositHolderList.size()> 0)
	    	return  (DepositHolderNominees) depositHolderList.get(0);
	    else
	    	return null;

	}

	@Override
	public List<DepositHolderNominees> getAllNominees(Long depositId) {

	    Query query = em.createQuery("SELECT o FROM DepositHolderNominees o where o.depositHolderId=:depositHolderId");
	    query.setParameter("depositHolderId", depositId);
	    List<DepositHolderNominees> depositHolderList = query.getResultList();
	    return depositHolderList;

	}
}
