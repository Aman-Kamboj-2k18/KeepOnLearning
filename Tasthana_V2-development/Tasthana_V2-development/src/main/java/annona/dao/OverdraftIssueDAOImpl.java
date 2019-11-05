package annona.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.Deposit;
import annona.domain.ModeOfPayment;
import annona.domain.OverdraftIssue;
import annona.domain.OverdraftPayment;
import annona.domain.OverdraftWithdrawMaster;
import annona.domain.UnSuccessfulOverdraft;
import annona.form.DepositForm;

@Repository
public class OverdraftIssueDAOImpl implements OverdraftIssueDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insertOverdraftIssue(OverdraftIssue overdraftIssue) {
		
		em.persist(overdraftIssue);
		
	}
	
	@Transactional
	public void insertOverdraftPayment(OverdraftPayment overdraftPayment) {
		
		em.persist(overdraftPayment);
		
	}
	
	@Transactional
	public void insertOverdraftWithdrawMaster(OverdraftWithdrawMaster overdraftWithdrawMaster) {
		
		em.persist(overdraftWithdrawMaster);
		
	}

	@Override
	public OverdraftIssue getOverdraftIssueDetails(String overdraftNumber) {
		// TODO Auto-generated method stub
		try {
		Query query = em.createQuery("from OverdraftIssue  where overdraftNumber=:overdraftNumber");
		query.setParameter("overdraftNumber", overdraftNumber);
		
		return (OverdraftIssue) query.getSingleResult();
		}catch (NoResultException e) {
			// TODO: handle exception
			return null;
		}

	}
	
	@Transactional
	public void updateOverdraftIssue(OverdraftIssue overdraftIssue) {
		// TODO Auto-generated method stub
		em.merge(overdraftIssue);
		em.flush();
	}

	@Transactional
	public void updateOverdraftPayment(OverdraftPayment overdraftPayment) {
		// TODO Auto-generated method stub
		em.merge(overdraftPayment);
		em.flush();
	}
	
	@Transactional
	public void updateOverdraftWithdrawMaster(OverdraftWithdrawMaster overdraftWithdrawMaster) {
		// TODO Auto-generated method stub
		em.merge(overdraftWithdrawMaster);
		em.flush();
	}
	
	@Override
	public OverdraftIssue getOverdraftIssueDetails() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("from OverdraftIssue  where isActive=1");
		return (OverdraftIssue) query.getSingleResult();
	}

	@Override
	public List<OverdraftIssue> getOverdraftIssueDetailsByDepositId(Long depositId) {
		// TODO Auto-generated method stub
     Query query = em.createQuery("from OverdraftIssue  where isActive=1 and depositId=:depositId ",OverdraftIssue.class);
     query.setParameter("depositId", depositId);
		return query.getResultList();
	}

	
	@Override
	public List<OverdraftIssue> getAllActiveOverdrafts() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("from OverdraftIssue where isActive=1 and EMIAmount!=null and AmountToReturn>0");
		return (List<OverdraftIssue>)query.getResultList();
	}
	
	@Override
	public List<OverdraftIssue> getAllOverdrafts() {
		// TODO Auto-generated method stub
		Query query = em.createQuery("from OverdraftIssue where isActive=1");
		return (List<OverdraftIssue>)query.getResultList();
	}
	
	@Override
	public List<OverdraftIssue> getMyOverdrafts(List<DepositForm> depositList) {
		String depositIds="";
		for (DepositForm depositForm : depositList) {
			depositIds+=depositForm.getDepositId()+",";
		}
		Integer length=depositIds.length();
		depositIds=depositIds.substring(0,length-1);
		Query query = em.createQuery("from OverdraftIssue where isActive=1 and depositId IN ("+depositIds+")");
		return (List<OverdraftIssue>)query.getResultList();
	}

	@Transactional
	public void insertInUnSuccessfulOverdraft(UnSuccessfulOverdraft unsuccessfulOverdraft ) {
		
		em.persist(unsuccessfulOverdraft);
		
	}

	@Override
	public List<OverdraftWithdrawMaster> findByOverdraftIdInWithdrawMaster(Long overdraftId) {
		try {
			Query query = em.createQuery("from OverdraftWithdrawMaster  where OverdraftId=:overdraftId",OverdraftWithdrawMaster.class);
			query.setParameter("overdraftId", overdraftId);
			return (List<OverdraftWithdrawMaster>) query.getResultList();
			}catch (NoResultException e) {
				// TODO: handle exception
				return null;
			}
	}

	@Override
	public List<OverdraftPayment> findByOverdraftIdInOverdraftPayment(Long overdraftId) {
		try {
			Query query = em.createQuery("from OverdraftPayment  where overdraftId=:overdraftId");
			query.setParameter("overdraftId", overdraftId);
			return (List<OverdraftPayment>) query.getResultList();
			}catch (NoResultException e) {
				// TODO: handle exception
				return null;
			}
	}

	@Override
	public OverdraftIssue getOverdraftIssueDetailsById(Long id) {
		// TODO Auto-generated method stub
				try {
				Query query = em.createQuery("from OverdraftIssue  where id=:id");
				query.setParameter("id", id);
				
				return (OverdraftIssue) query.getSingleResult();
				}catch (NoResultException e) {
					// TODO: handle exception
					return null;
				}
	}
	
}
