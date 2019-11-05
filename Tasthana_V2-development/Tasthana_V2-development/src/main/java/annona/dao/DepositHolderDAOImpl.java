package annona.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.Branch;
import annona.domain.Deposit;
import annona.domain.DepositHolder;
import annona.form.DepositHolderForm;

@Repository
public class DepositHolderDAOImpl implements DepositHolderDAO {

	@PersistenceContext
	EntityManager em;

	@Transactional
	public DepositHolder saveDepositHolder(DepositHolder depositHolder) {
		em.persist(depositHolder);
		return depositHolder;
	}

    @Transactional
	public Deposit updateDeposit(Deposit deposit) {
		em.merge(deposit);
		em.flush();
		return deposit;
	}

	public List<DepositHolder> getDepositHolders(Long depositId) {

		if (depositId == null || depositId == 0)
			throw new IllegalArgumentException("Select the deposit");

		TypedQuery<DepositHolder> query = em.createQuery("SELECT o FROM DepositHolder o where o.depositId=:depositId ",
				DepositHolder.class);
		query.setParameter("depositId", depositId);

		return query.getResultList();
	}

	@Override
	public DepositHolder findByCustomerId(Long customerId) {
		Query query = em.createQuery("SELECT o from DepositHolder o where o.customerId=:customerId");
		query.setParameter("customerId", customerId);

		return (DepositHolder) query.getResultList().get(0);

	}

	// public List<Object[]> getAllDeposit(Long userId) {
	//
	// Query query = em.createQuery("select dh.depositHolderStatus,"
	// +
	// "dh.contribution,dh.depositId,d.maturityDate,d.status,d.createdDate,d.depositAmount
	// from Deposit as d "
	// + "inner join d.depositHolder as dh where dh.customerId=:userId");
	//
	// query.setParameter("userId", userId);
	//
	// return query.getResultList();
	// }
	public List<Object[]> getAllDeposit(Long userId) {

		Query query = em.createQuery("select dh.depositHolderStatus,"
				+ "dh.contribution,dh.depositId,d.maturityDate,d.status,d.createdDate,d.depositAmount,d.currentBalance,d.accountNumber,d.newMaturityDate,d.depositCategory from Deposit as d "
				+ "inner join d.depositHolder as dh where dh.customerId=:userId");

		query.setParameter("userId", userId);

		return query.getResultList();
	}

	public List<Object[]> getAllOpenDeposits(Long userId) {

		Query query = em.createQuery("select dh.depositHolderStatus,"
				+ "dh.contribution,dh.depositId,d.newMaturityDate,d.status,d.createdDate,d.depositAmount,d.currentBalance,d.accountNumber,d.newMaturityDate,d.depositCategory from Deposit as d "
				+ "inner join d.depositHolder as dh where dh.customerId=:userId and d.status='OPEN' ");

		query.setParameter("userId", userId);

		return query.getResultList();
	}

    @Transactional
	public void updateDepositHolder(DepositHolder depositHolder) {
		em.merge(depositHolder);
		em.flush();
	}

	public DepositHolder findById(Long id) {
		if (id == null)
			return null;
		return em.find(DepositHolder.class, id);
	}

	public DepositHolder getDepositHoldersByID(Long id) {
		Query query = em.createQuery("SELECT o FROM DepositHolder o where o.id=:id");
		query.setParameter("id", id);

		return (DepositHolder) query.getResultList().get(0);
	}

	public DepositHolder getDepositHolder(Long depositId, Long customerId) {
		Query query = em.createQuery(
				"SELECT o from DepositHolder o where  o.depositId=:depositId and o.customerId=:customerId");
		query.setParameter("depositId", depositId);
		query.setParameter("customerId", customerId);
		return (DepositHolder) query.getResultList().get(0);

	}

	public DepositHolder getAutoDepositHolder(Long depositId) {
		Query query = em.createQuery("SELECT o from DepositHolder o where  o.depositId=:depositId");
		query.setParameter("depositId", depositId);
		return (DepositHolder) query.getResultList().get(0);
	}

	public List<Object[]> getOpenDeposit(Long userId) {

		Query query = em.createNativeQuery("select dh.depositHolderStatus,"
				+ "dh.contribution,dh.depositId,d.maturityDate,d.status,d.createdDate,d.depositAmount,d.currentBalance,d.accountNumber,d.newMaturityDate,d.depositCategory,d.accountAccessType,dh.deathCertificateSubmitted from Deposit as d "
				+ "inner join depositHolder as dh on d.Id=dh.depositId where dh.customerId=:userId and d.status='OPEN'");

		query.setParameter("userId", userId);

		return query.getResultList();
	}

	public Double getReverseEMIAmount(Long depositId) {

		if (depositId == null || depositId == 0)
			throw new IllegalArgumentException("Select the deposit");

		Double totalAmount = (Double) em
				.createQuery("SELECT sum(interestAmt) FROM DepositHolder o where o.depositId=:depositId")
				.setParameter("depositId", depositId).getSingleResult();

		return totalAmount;
	}

	public List<DepositHolderForm> getDepositHoldersName(Long depositId) {
		Query query = em.createNativeQuery(
				"select dh.id as depositHolderid,dh.contribution, c.id as customerid ,c.customername,dh.depositholderstatus from "
						+ "depositholder dh inner join customerdetails c on c.id=dh.customerid "
						+ "where dh.depositid=:depositId order by dh.id");

		query.setParameter("depositId", depositId);

		List<Object[]> objList = query.getResultList();
		List<DepositHolderForm> holderList = new ArrayList<DepositHolderForm>();

		for (int i = 0; i < objList.size(); i++) {
			DepositHolderForm holder = new DepositHolderForm();

			BigInteger dId = (BigInteger) objList.get(i)[0];
			Long depositHolderId = dId.longValue();

			holder.setDepositHolderId(depositHolderId);
			holder.setContribution((Float) objList.get(i)[1]);
			holder.setCustomerId(((BigInteger) objList.get(i)[2]).longValue());
			holder.setCustomerName((String) objList.get(i)[3]);
			holder.setDepositHolderStatus((String) objList.get(i)[4]);
			holderList.add(holder);
			holder = null;
		}
		return holderList;
	}

	public Deposit getDepositByDepositIdAndCustId(Long depositId) {
		Query query = em.createQuery("SELECT o from Deposit o where  o.id=:depositId");
		query.setParameter("depositId", depositId);
		return (Deposit) query.getResultList().get(0);

	}

	public List<DepositHolderForm> getReverseEmiDepositByDepositId(Long depositId) {
		Query query = em.createNativeQuery(
				"SELECT d.id as depositId,d.emiAmount,d.dueDate,d.createdBy,d.createdDate,"
						+ " d.accountNumber,dh.customerId,dh.depositHolderStatus from Deposit d inner join DepositHolder dh on d.id=dh.depositId"
						+ " where d.depositCategory='REVERSE-EMI' and dh.depositId =:depositId");

		query.setParameter("depositId", depositId);

		@SuppressWarnings("unchecked")
		List<Object[]> emiList = query.getResultList();
		List<DepositHolderForm> reverseEmiList = new ArrayList<DepositHolderForm>();

		for (int i = 0; i < emiList.size(); i++) {
			DepositHolderForm holder = new DepositHolderForm();
			BigInteger dId = (BigInteger) emiList.get(i)[0];
			Long emiDepositId = dId.longValue();

			holder.setDepositId(emiDepositId);
			//holder.setDepositType((String) emiList.get(i)[1]);
			holder.setEmiAmount((Double) emiList.get(i)[1]);
			holder.setDueDate((Date) emiList.get(i)[2]);
			holder.setCreatedBy((String) emiList.get(i)[3]);
			holder.setCreatedDate((Date) emiList.get(i)[4]);
			holder.setAccountNumber((String) emiList.get(i)[5]);
			BigInteger custId = (BigInteger) emiList.get(i)[6];
			Long cId = custId.longValue();
			holder.setCustomerId(cId);
			holder.setDepositHolderStatus((String) emiList.get(i)[7]);
			reverseEmiList.add(holder);
			holder = null;
		}
		return reverseEmiList;
	}

	@Override
	public Deposit getDepositByAccountNumber(String accountNumber) {
		// TODO Auto-generated method stub
		Query query = em.createQuery("from Deposit  where accountNumber=:accountNumber");
		query.setParameter("accountNumber", accountNumber);

		return (Deposit) query.getSingleResult();
	}

	public List<DepositHolderForm> getUnpaidDepositHolders(Long depositId) {

		if (depositId == null || depositId == 0)
			throw new IllegalArgumentException("Select the deposit");

		Query query = em.createNativeQuery(
				"SELECT o.id,o.contribution,o.depositHolderStatus,o.distAmtOnMaturity,o.deathCertificateSubmitted,n.nomineeName as nomineeName,c.customerName as customerName, o.customerId FROM DepositHolder o left join depositholdernominees n on o.Id=n.depositHolderId left join customerDetails c on c.id=o.customerId where o.depositId=:depositId and isamounttransferredonmaturity is null");
		query.setParameter("depositId", depositId);

		List<Object[]> objList = query.getResultList();
		List<DepositHolderForm> holderList = new ArrayList<DepositHolderForm>();

		for (int i = 0; i < objList.size(); i++) {
			DepositHolderForm holder = new DepositHolderForm();
			Double amount = (Double) objList.get(i)[3];
			BigDecimal bd = new BigDecimal(amount);
			bd = bd.setScale(2, RoundingMode.HALF_UP);
			amount = bd.doubleValue();

			BigInteger dId = (BigInteger) objList.get(i)[0];
			Long depositHolderId = dId.longValue();
			holder.setDepositHolderId(depositHolderId);
			holder.setContribution((Float) objList.get(i)[1]);
			holder.setDepositHolderStatus((String) objList.get(i)[2]);
			holder.setDistAmtOnMaturity(amount);
			holder.setDeathCertificateSubmitted((String) objList.get(i)[4]);
			holder.setNomineeName((String) objList.get(i)[5]);
			holder.setCustomerName((String) objList.get(i)[6]);
			holder.setCustomerId(((BigInteger) objList.get(i)[7]).longValue());
			holderList.add(holder);
			holder = null;
		}
		return holderList;

	}

	@Override
	public Long getOpenDepositCount(String toDate, String fromDate, Long branchId) {
		try {
			Query query = null;
			if (branchId != null) {
				query = em.createQuery(
						"SELECT count(*) FROM Deposit d where d.status='OPEN' AND d.createdDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY') AND d.branchCode = :branchCode");
				Branch branch = em.find(Branch.class, branchId);
				query.setParameter("branchCode", branch.getBranchCode());
			} else {

				query = em.createQuery(
						"SELECT count(*) FROM Deposit d where d.status='OPEN' AND d.createdDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY')");
			}

			query.setParameter("toDate", toDate);
			query.setParameter("fromDate", fromDate);
			List<Long> deposits = query.getResultList();
			if (deposits != null && deposits.size() > 0) {
				return Long.valueOf(deposits.get(0).toString());
			} else {
				return 0l;
			}

		} catch (Exception e) {
			return 0l;
		}

	}

	@Override
	public Long getPrematuredDepositCount(String toDate, String fromDate, Long branchId) {
		try {
			Query query = null;
			if (branchId != null) {
				query = em.createQuery(
						"SELECT count(*) FROM Deposit d where d.status='CLOSED' AND d.closingDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY')  AND d.isPreMaturelyClosed = 1 AND d.branchCode = :branchCode");
				Branch branch = em.find(Branch.class, branchId);
				query.setParameter("branchCode", branch.getBranchCode());
			} else {
				query = em.createQuery(
						"SELECT count(*) FROM Deposit d where d.status='CLOSED' AND d.closingDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY')  AND d.isPreMaturelyClosed  = 1");
			}
			query.setParameter("toDate", toDate);
			query.setParameter("fromDate", fromDate);
			List<Long> deposits = query.getResultList();
			if (deposits != null && deposits.size() > 0) {
				return Long.valueOf(deposits.get(0).toString());
			} else {
				return 0l;
			}

		} catch (Exception e) {
			return 0l;
		}
	}

	@Override
	public Long getMaturedDepositCount(String toDate, String fromDate, Long branchId) {
		try {

			Query query = null;
			if (branchId != null) {
				query = em.createQuery(
						"SELECT count(*) FROM Deposit d where d.status='CLOSED' AND d.closingDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY') AND d.isPreMaturelyClosed IN(0,null) AND d.branchCode = :branchCode");
				Branch branch = em.find(Branch.class, branchId);
				query.setParameter("branchCode", branch.getBranchCode());
			} else {
				query = em.createQuery(
						"SELECT count(*) FROM Deposit d where d.status='CLOSED' AND d.closingDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY') AND d.isPreMaturelyClosed IN(0,null)");
			}
			query.setParameter("toDate", toDate);
			query.setParameter("fromDate", fromDate);
			List<Long> deposits = query.getResultList();
			if (deposits != null && deposits.size() > 0) {
				return Long.valueOf(deposits.get(0).toString());
			} else {
				return 0l;
			}

		} catch (Exception e) {
			return 0l;
		}
	}

	@Override
	public Double getTotalPaymentReceived(String toDate, String fromDate, Long branchId) {
		try {
			Query query = null;
			if (branchId != null) {
				query = em.createQuery(
						"SELECT sum(amountPaid) FROM Payment p where p.paymentDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY') AND p.branch = :branchCode");
				Branch branch = em.find(Branch.class, branchId);
				query.setParameter("branchCode", branch.getBranchCode());
			} else {
				query = em.createQuery(
						"SELECT sum(amountPaid) FROM Payment p where p.paymentDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY')");
			}
			query.setParameter("toDate", toDate);
			query.setParameter("fromDate", fromDate);
			List<Double> deposits = query.getResultList();
			if (deposits != null && deposits.size() > 0) {
				return Double.valueOf(deposits.get(0) == null ? 0.0 : deposits.get(0).doubleValue());
			} else {
				return 0d;
			}

		} catch (Exception e) {
			return 0d;
		}
	}

	@Override
	public Double getTotalWithdrawAmount(String toDate, String fromDate, Long branchId) {
		try {
			Query query = null;
			if (branchId != null) {
				query = em.createQuery(
						"SELECT sum(totalAmount) FROM WithdrawDeposit w where w.withdrawDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY') AND w.branch = :branchCode");
				Branch branch = em.find(Branch.class, branchId);
				query.setParameter("branchCode", branch.getBranchCode());
			} else {
				query = em.createQuery(
						"SELECT sum(totalAmount) FROM WithdrawDeposit w where w.withdrawDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY')");
			}
			query.setParameter("toDate", toDate);
			query.setParameter("fromDate", fromDate);
			List<Double> deposits = query.getResultList();
			if (deposits != null && deposits.size() > 0) {
				return Double.valueOf(deposits.get(0) == null ? 0.0 : deposits.get(0).doubleValue());
			} else {
				return 0d;
			}

		} catch (Exception e) {
			return 0d;
		}
	}

	@Override
	public Double getTotalIntrest(String toDate, String fromDate, Long branchId) {
		try {
			Query query = null;
			//if code not in use because branch id coming null
			if (branchId != null) {
				query = em.createQuery(
						"SELECT sum(interestAmount) FROM Interest i where i.interestDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY')");
				// Branch branch = em.find(Branch.class, branchId);
				// query.setParameter("branchCode", branch.getBranchCode());
			} else {
				query = em.createQuery(
						"SELECT sum(interestAmount) FROM Interest i where i.interestDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY') AND i.isCompounded = 1");
			}
			query.setParameter("toDate", toDate);
			query.setParameter("fromDate", fromDate);
			List<Double> deposits = query.getResultList();
			if (deposits != null && deposits.size() > 0) {
				return Double.valueOf(deposits.get(0) == null ? 0.0 : deposits.get(0).doubleValue());
			} else {
				return 0d;
			}

		} catch (Exception e) {
			return 0d;
		}
	}

	@Override
	public Double getTotalAmountPaidByBank(String toDate, String fromDate, Long branchId) {
		try {
			Query query = null;
			//if code not in use because branch id coming null
			if (branchId != null) {
				query = em.createQuery(
						"SELECT sum(amount) FROM BankPaid b where b.paymentDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY')");
				// Branch branch = em.find(Branch.class, branchId);
				// query.setParameter("branchCode", branch.getBranchCode());
			} else {
				query = em.createQuery(
						"SELECT sum(amount) FROM BankPaid b where b.paymentDate BETWEEN TO_DATE(:toDate,'DD/MM/YYYY') AND TO_DATE(:fromDate,'DD/MM/YYYY')");
			}
			query.setParameter("toDate", toDate);
			query.setParameter("fromDate", fromDate);
			List<Double> deposits = query.getResultList();
			if (deposits != null && deposits.size() > 0) {
				return Double.valueOf(deposits.get(0) == null ? 0.0 : deposits.get(0).doubleValue());
			} else {
				return 0d;
			}

		} catch (Exception e) {
			return 0d;
		}
	}

}
