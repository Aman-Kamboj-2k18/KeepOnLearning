package annona.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.AccountDetails;

import annona.domain.Customer;
import annona.domain.Deposit;
import annona.domain.DepositSummaryHolderWise;
import annona.domain.DepositModification;
import annona.domain.DepositRates;
import annona.domain.Interest;
import annona.domain.DepositSummary;
import annona.form.CustomerForm;
import annona.services.DateService;

@Repository
public class DepositSummaryDAOImpl implements DepositSummaryDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Method for entity manager
	 * 
	 * @return
	 *//*
	public EntityManager entityManager() {
		EntityManager em = new DepositSummaryDAOImpl().em;

		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}
*/
	
	public DepositSummary insert(DepositSummary depositSummary) {
		em.persist(depositSummary);
		return depositSummary;
	}

	@Transactional
	public DepositSummary update(DepositSummary depositSummary) {
		em.merge(depositSummary);
		em.flush();
		return depositSummary;
	}

	@Override
	public DepositSummary getDepositSummary(Long depositId) {

		Query query = em.createQuery("SELECT o FROM DepositSummary o WHERE o.depositId=:depositId");
		query.setParameter("depositId", depositId);
		List depositSummaryList = query.getResultList();
		if (depositSummaryList!= null && depositSummaryList.size() > 0) {
			return (DepositSummary)depositSummaryList.get(0);
		} else {
			return null;
		}
	}
	
//	@Override
//	public Double getCurrentInterestBalance(Long depositId)
//	{
//		Query query = em.createQuery("SELECT o FROM DepositSummary o WHERE o.depositId=:depositId order by id desc");
//		query.setParameter("depositId", depositId);
//		List depositSummaryList = query.getResultList();
//		if (depositSummaryList!= null && depositSummaryList.size() > 0) {
//			return ((DepositSummary)depositSummaryList.get(0)).getCurrentInterestBalance();
//		} else {
//			return 0d;
//		}
//	}
//	
//	
//	@Transactional
//	public DepositSummaryHolderWise insertInDepositHolderWiseConsolidatedInterest(DepositSummaryHolderWise holderWiseInterestConsolidation) {
//		em.persist(holderWiseInterestConsolidation);
//		return holderWiseInterestConsolidation;
//	}
//
//	@Override
//	public DepositSummaryHolderWise getDepositHolderInterestConsolidation(Long depositId, Long depositHolderId)
//	{
//		Query query = em.createQuery("SELECT o FROM DepositHolderWiseConsolidatedInterest o WHERE o.depositId=:depositId and depositHolderId=:depositHolderId order by id desc");
//		query.setParameter("depositId", depositId);
//		query.setParameter("depositHolderId", depositHolderId);
//		List depositSummaryList = query.getResultList();
//		if (depositSummaryList!= null && depositSummaryList.size() > 0) {
//			return ((DepositSummaryHolderWise)depositSummaryList.get(0));
//		}
//		else
//		{
//			return null;
//		}
//	}
//	
//	@Transactional
//	public void updateInDepositHolderWiseConsolidatedInterest(DepositSummaryHolderWise holderWiseInterestConsolidation)
//	{
//		em.merge(holderWiseInterestConsolidation);
//		em.flush();
//	}
//	
//	
//	@Transactional
//	public ConsolidatedInterest insertInConsolidatedInterest(ConsolidatedInterest consolidatedInterest)
//	{
//		em.persist(consolidatedInterest);
//		return consolidatedInterest;
//	}
//	
//	@Transactional
//	public void updateInConsolidatedInterest(ConsolidatedInterest consolidatedInterest)
//	{
//		em.merge(consolidatedInterest);
//		em.flush();
//	}
//	
//	@Override
//	public ConsolidatedInterest getConsolidatedInterest(Long depositId)
//	{
//		// this tab;e will have only single record for a deposit
//		Query query = em.createQuery("SELECT o FROM ConsolidatedInterest o WHERE o.depositId=:depositId");
//		query.setParameter("depositId", depositId);
//		List depositSummaryList = query.getResultList();
//		if (depositSummaryList!= null && depositSummaryList.size() > 0) {
//			return (ConsolidatedInterest)depositSummaryList.get(0);
//		} else {
//			return null;
//		}
//	}
	
	@Transactional
	public DepositSummaryHolderWise insertDepositSummaryHolderWise(DepositSummaryHolderWise depositSummaryHolderWise) {
		em.persist(depositSummaryHolderWise);
		return depositSummaryHolderWise;
	}

	@Transactional
	public DepositSummaryHolderWise updateDepositSummaryHolderWise(DepositSummaryHolderWise depositSummaryHolderWise) {
		em.merge(depositSummaryHolderWise);
		em.flush();
		return depositSummaryHolderWise;
	}

	@Override
	public List<DepositSummaryHolderWise> getHolderWiseDepositSummary(Long depositId) {

		Query query = em.createQuery("SELECT o FROM DepositSummaryHolderWise o WHERE o.depositId=:depositId");
		query.setParameter("depositId", depositId);
		List<DepositSummaryHolderWise> holderWiseDepositSummaryList = query.getResultList();
		return holderWiseDepositSummaryList;
	}
	
	@Override
	public DepositSummaryHolderWise getHolderWiseDepositSummary(Long depositId, Long depositHolderId) {

		Query query = em.createQuery("SELECT o FROM DepositSummaryHolderWise o WHERE o.depositId=:depositId and o.depositHolderId=:depositHolderId");
		query.setParameter("depositId", depositId);
		query.setParameter("depositHolderId", depositHolderId);
		List<DepositSummaryHolderWise> holderWiseDepositSummaryList = query.getResultList();
		if(holderWiseDepositSummaryList!=null && holderWiseDepositSummaryList.size()>0)
			return holderWiseDepositSummaryList.get(0);
		else
			return null;
			
		}


	@Override
	public Double getCurrentInterestBalance(Long depositId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}	
	
