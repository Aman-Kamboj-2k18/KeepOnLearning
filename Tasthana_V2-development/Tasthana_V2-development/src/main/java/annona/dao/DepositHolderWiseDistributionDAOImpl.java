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

import annona.domain.DepositHolderWiseDistribution;

@Repository
public class DepositHolderWiseDistributionDAOImpl implements DepositHolderWiseDistributionDAO {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public DepositHolderWiseDistribution saveDepositHolderWiseDistribution(
			DepositHolderWiseDistribution depositHolderWiseDistribution) {
		em.persist(depositHolderWiseDistribution);
		return depositHolderWiseDistribution;
	}

	@Override
	public List<DepositHolderWiseDistribution> getDepositHolderWiseDistributionList(Long depositId) {

		if (depositId == null || depositId == 0)
			throw new IllegalArgumentException("Select the deposit");

		TypedQuery<DepositHolderWiseDistribution> query = em.createQuery(
				"SELECT o FROM DepositHolderWiseDistribution o where o.depositId=:depositId ",
				DepositHolderWiseDistribution.class);
		query.setParameter("depositId", depositId);

		return query.getResultList();
	}

	@Override
	public DepositHolderWiseDistribution getDepositHolderWiseLastDistribution(Long depositId, Long depositHolderId) {
		
		Query query = em.createQuery(
				"SELECT o from DepositHolderWiseDistribution o where o.depositId=:depositId and o.depositHolderId=:depositHolderId order by id desc",DepositHolderWiseDistribution.class);

		query.setParameter("depositId", depositId);
		query.setParameter("depositHolderId", depositHolderId);
		List<DepositHolderWiseDistribution> depositHolderWiseDistributionList = query.getResultList();
		if (depositHolderWiseDistributionList != null && depositHolderWiseDistributionList.size() > 0) {
			return (DepositHolderWiseDistribution) depositHolderWiseDistributionList.get(0);
		} else
			return null;
	}

	@Override
	public List<DepositHolderWiseDistribution> getCustomerWiseDepositHolderDistribution(Long depositId,
			Long customerId) {
		Query query = em.createQuery(
				"SELECT o from DepositHolderWiseDistribution o where o.depositId=:depositId and o.customerId=:customerId order by id desc");

		query.setParameter("depositId", depositId);
		query.setParameter("customerId", customerId);

		return query.getResultList();

	}

	@Override
	public List<DepositHolderWiseDistribution> getByCustomerIdAndDepositId(Long depositId, Long customerId) {
		Query query = em.createQuery(
				"SELECT o from DepositHolderWiseDistribution o where o.depositId=:depositId and o.customerId=:customerId order by id");

		query.setParameter("depositId", depositId);
		query.setParameter("customerId", customerId);

		return query.getResultList();

	}

	@Override
	public DepositHolderWiseDistribution getHolderWiseDistribution(Long depositId, Long depositHolderId,
			Date distributionDate) {
		Query query = em.createQuery(
				"SELECT o from DepositHolderWiseDistribution o where o.depositId=:depositId and o.depositHolderId=:depositHolderId and o.action ='Interest' and o.distributionDate =:distributionDate order by id");
		query.setParameter("depositId", depositId);
		query.setParameter("depositHolderId", depositHolderId);
		query.setParameter("distributionDate", distributionDate);
		List lst = query.getResultList();
		if (lst != null && lst.size() > 0)
			return (DepositHolderWiseDistribution) lst.get(0);
		else
			return null;
	}

	@Override
	public List<Object[]> getDepositHolderWiseDistributionByDepositHolderId(Long depositHolderId) {

		String sqlStr = "Select depositId,customerId,contribution,distributionDate,action,CASE WHEN variableAmt IS NOT null"
				+ " THEN (fixedAmt + variableAmt) ELSE CASE WHEN variableInterest IS NOT null THEN"
				+ " fixedInterest + variableInterest ELSE CASE WHEN payoffAmount IS NOT null THEN payoffAmount"
				+ " ELSE tdsAmount END END END as actionAmount, (fixedCompoundAmount + variableCompoundAmount)"
				+ " as balanceAmount from DepositHolderWiseDistribution  where depositHolderId=:depositHolderId";

		Query query = em.createNativeQuery(sqlStr);

		query.setParameter("depositHolderId", depositHolderId);
		return query.getResultList();

	}

	public List<Object[]> getDepositHolderWiseDistribution(Long depositId) {		
		String sqlStr = "Select depositId,customerId,contribution,distributionDate,action,CASE WHEN variableAmt IS NOT null"
				+ " THEN (fixedAmt + variableAmt) ELSE CASE WHEN variableInterest IS NOT null THEN"
				+ " fixedInterest + variableInterest ELSE CASE WHEN payoffAmount IS NOT null THEN payoffAmount"
				+ " ELSE tdsAmount END END END as actionAmount, (fixedCompoundAmount + variableCompoundAmount)"
				+ " as balanceAmount from DepositHolderWiseDistribution  where depositId=:depositId";

		Query query = em.createNativeQuery(sqlStr);

		query.setParameter("depositId", depositId);
		return query.getResultList();

	}

}
