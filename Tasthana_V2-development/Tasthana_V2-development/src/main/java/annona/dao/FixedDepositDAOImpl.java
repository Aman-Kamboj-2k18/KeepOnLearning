package annona.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.CurrencyConfiguration;
import annona.domain.Deposit;
import annona.domain.DepositBeforeRenew;
import annona.domain.Distribution;
import annona.domain.Journal;

import annona.domain.ProductConfiguration;
import annona.domain.RenewDeposit;
import annona.form.AutoDepositForm;
import annona.form.DepositForm;
import annona.form.HolderForm;
import annona.form.LedgerReportForm;
import annona.form.ReportForm;
import annona.form.WithdrawForm;
import annona.services.DateService;
import annona.utility.Constants;

@Repository
public class FixedDepositDAOImpl implements FixedDepositDAO {

	@PersistenceContext
	private EntityManager em;

	public EntityManager entityManager() {
		EntityManager em = new FixedDepositDAOImpl().em;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public Deposit updateFD(Deposit deposit) {
		em.merge(deposit);
		em.flush();
		return deposit;
	}

	@Transactional
	public Deposit saveFD(Deposit fixedDeposit) {
		em.persist(fixedDeposit);
		return fixedDeposit;
	}

	@Override
	public WithdrawForm withdrawAccountNumber(String accountNumber) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager antmgr = emfactory.createEntityManager();

		String sql = "SELECT d.accountNumber,d.id as depositId, dh.customerId,c.customerName,dh.id as depositHolderId,pd.compoundVariableAmt,pd.compoundFixedAmt "
				+ "from Deposit d  inner join depositholder dh on d.id = dh.depositid inner join customerdetails c on dh.customerId = c.id "
				+ "inner join paymentdistribution pd on pd.depositid=dh.depositid "
				+ "where d.accountNumber=?1 and d.status='OPEN' ORDER BY pd.id DESC";

		Query depositQuery = antmgr.createNativeQuery(sql);
		depositQuery.setParameter(1, accountNumber);

		@SuppressWarnings("unchecked")
		List<Object[]> withdrawList = depositQuery.getResultList();

		WithdrawForm withdrawForm = new WithdrawForm();
		for (int i = 0; i < withdrawList.size(); i++) {
			String accNo = (String) withdrawList.get(i)[0];
			Long depositId = Long.parseLong(withdrawList.get(i)[1].toString());
			Long cusId = Long.parseLong(withdrawList.get(i)[2].toString());
			String cusName = (String) withdrawList.get(i)[3];
			Long depositHolderId = Long.parseLong(withdrawList.get(i)[4].toString());
			Double comVarAmt = (Double) withdrawList.get(i)[5];
			Double comFixedAmt = (Double) withdrawList.get(i)[6];

			System.out.println("comFixedAmt....." + comFixedAmt);

			withdrawForm.setAccountNumber(accNo);
			withdrawForm.setDepositId(depositId);
			withdrawForm.setDepositHolderId(depositHolderId);
			withdrawForm.setCustomerId(cusId);
			withdrawForm.setCustomerName(cusName);
			withdrawForm.setCompoundVariableAmt(comVarAmt);
			withdrawForm.setCompoundFixedAmt(comFixedAmt);
			break;
		}

		return withdrawForm;

	}

	@Override
	public List<Deposit> getOpenDeposits() {

		TypedQuery<Deposit> q = em.createQuery(
				"SELECT o FROM Deposit o where upper(o.status) = 'OPEN' and o.depositCategory is NULL or o.depositCategory='REVERSE-EMI' order by o.id desc",
				Deposit.class);

		return q.getResultList();
	}

	@Override
	public List<Deposit> getOpenReverseEMIDeposits() {

//		TypedQuery<Deposit> q = em.createQuery(
//				"SELECT o FROM Deposit o where upper(o.status) = 'OPEN' and "
//						+ "upper(o.depositCategory) = 'REVERSE-EMI' and  " + "date(o.payOffDueDate)=date(CURRENT_DATE)",
//				Deposit.class);

		TypedQuery<Deposit> q = em.createQuery(
				"SELECT o FROM Deposit o where upper(o.status) = 'OPEN' and "
						+ "upper(o.depositCategory) = 'REVERSE-EMI' and  " + "date(o.payOffDueDate)=date(:currentDate)",
				Deposit.class);
		q.setParameter("currentDate", DateService.getCurrentDate());
		return q.getResultList();
	}

	@Override
	public List<Deposit> getOpenDepositsInDescOrder() {

		TypedQuery<Deposit> q = em.createQuery(
				"SELECT o FROM Deposit o where upper(o.status) = 'OPEN' and  upper(o.approvalStatus) = 'APPROVED' and o.depositCategory is NULL order by o.id desc",
				Deposit.class);

		return q.getResultList();
	}

	@Override
	public List<Deposit> getApprovedDeposits() {

		TypedQuery<Deposit> q = em.createQuery(
				"SELECT o FROM Deposit o where upper(o.status) = 'OPEN' and upper(o.approvalStatus) = 'APPROVED' order by o.id ASC",
				Deposit.class);

		return q.getResultList();
	}

	@Override
	public List<Deposit> getPendingDeposits() {
		TypedQuery<Deposit> q = em.createQuery(
				"SELECT o FROM Deposit o where upper(o.status) = 'OPEN' and upper(o.approvalStatus) = 'PENDING' and o.clearanceStatus is null order by o.id",
				Deposit.class);

		return q.getResultList();
	}

	public List<DepositForm> getAllPendingDepositsList() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager antmgr = emfactory.createEntityManager();

		String sql = "SELECT d.id depositId,d.accountNumber,d.depositAmount,d.currentBalance,d.maturityDate,d.createdDate,"
				+ "d.status,d.approvalStatus,d.newMaturityDate,c.customerName, d.depositCategory from Deposit d inner join depositholder dh on d.id = dh.depositid "
				+ "and dh.depositHolderStatus='PRIMARY' inner join customerdetails c on dh.customerId = c.id where"
				+ " upper(d.status)='OPEN' and upper(d.approvalStatus) = 'PENDING' and d.clearanceStatus is null ORDER BY d.id DESC";

		Query depositQuery = antmgr.createNativeQuery(sql);

		@SuppressWarnings("unchecked")
		List<Object[]> depositList = depositQuery.getResultList();
		List<DepositForm> depsitFormList = new ArrayList<DepositForm>();
		for (int i = 0; i < depositList.size(); i++) {
			System.out.println(i);

			DepositForm depositForm = new DepositForm();
			Long depositId = Long.parseLong(depositList.get(i)[0].toString());
			String accNo = (String) depositList.get(i)[1];
			Double depositAmount = (Double) depositList.get(i)[2];
			Double currentBalance = (Double) depositList.get(i)[3];
			Date maturityDate = (Date) depositList.get(i)[4];
			Date createdDate = (Date) depositList.get(i)[5];
			String status = (String) depositList.get(i)[6];
			String appStatus = (String) depositList.get(i)[7];
			Date newMaturityDate = (Date) depositList.get(i)[8];
			String cusName = (String) depositList.get(i)[9];
			String depositCategory = (String) depositList.get(i)[10];

			depositForm.setDepositId(depositId);
			depositForm.setAccountNumber(accNo);
			depositForm.setDepositAmount(depositAmount);
			depositForm.setCurrentBalance(currentBalance);
			depositForm.setMaturityDate(maturityDate);
			depositForm.setCreatedDate(createdDate);
			depositForm.setStatus(status);
			depositForm.setApprovalStatus(appStatus);
			depositForm.setNewMaturityDate(newMaturityDate);
			depositForm.setCustomerName(cusName);
			depositForm.setDepositCategory(depositCategory);
			depsitFormList.add(depositForm);

		}

		return depsitFormList;

	}

	@Override
	public Deposit getDeposit(Long id) {
		if (id == null)
			return null;
		return em.find(Deposit.class, id);
	}

	public List<Object[]> getDepositForInterestRate(Long depositId) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager antmgr = emfactory.createEntityManager();

		String sql = "SELECT d.accountNumber,d.id as depositId, d.createddate as depositDate, "
				+ " d.flexiRate, d.modifiedInterestRate as interestRate, c.id as customerId, "
				+ " c.category,dh.depositholderstatus,c.customerName from Deposit d inner join depositholder dh on "
				+ " d.id = dh.depositid inner join customerdetails c on dh.customerId = c.id  "
				+ " where depositId = ?1 and dh.depositHolderStatus= 'PRIMARY'";

		Query depositQuery = antmgr.createNativeQuery(sql);
		depositQuery.setParameter(1, depositId);

		@SuppressWarnings("unchecked")
		List<Object[]> depositList = depositQuery.getResultList();

		return depositList;
	}

	@Override
	public Deposit findById(Long id) {
		if (id == null)
			return null;
		return em.find(Deposit.class, id);
	}

	public List<Object[]> getByDepositIdAndCustomerId(Long depositId, Long customerId) {

		Query query = em.createQuery(
				"select dh.depositHolderStatus,dh.contribution,dh.depositId,d.maturityDate,d.status,d.createdDate,"
						+ "d.depositAmount,d.depositType,d.paymentMode,d.tenureType,d.tenure,"
						+ "d.paymentType,d.dueDate,d.accountNumber,d.linkedAccountNumber,dh.id "
						+ "as depositHolderId, d.flexiRate,d.interestRate,dh.customerId,"
						+ "d.payOffInterestType,dh.accountNumber,dh.bankName,dh.ifscCode,"
						+ "dh.interestAmt,dh.interestPercent,dh.interestType,dh.nameOnBankAccount,"
						+ "dh.payOffAccountType,dh.transferType,d.payOffDueDate from Deposit as d "
						+ "inner join d.depositHolder as dh where dh.depositId=:depositId and "
						+ "dh.customerId=:customerId");

		query.setParameter("depositId", depositId);
		query.setParameter("customerId", customerId);

		return query.getResultList();
	}

	@Override
	@Transactional
	public Distribution withdrawDepositAmt(Long depositId) {
		Query query = null;
		try {
			query = em.createQuery("SELECT o FROM Distribution o WHERE o.depositId=:depositId ORDER BY id desc");
			query.setParameter("depositId", depositId);
		} catch (Exception e) {
			return null;
		}
		if (query.getResultList().size() > 0)
			return (Distribution) query.getResultList().get(0);
		else
			return null;

	}

	@Override
	public List<Object[]> getOpenDepositsForMaturity(Long id) {

		String sql = "select d.id as depositId, d.maturityDate as maturityDate,m.id,"
				+ " m.maturityDate as modifiedMaturityDate from Deposit d "
				+ "left outer join DepositModification m on d.id = m.depositId "
				+ " where d.id =:id and d.status = 'OPEN' order by m.id desc";

		Query query = em.createNativeQuery(sql);
		// Query query = em.createQuery(sql);
		query.setParameter("id", id);

		return query.getResultList();
	}

	@Override
	public Deposit getAutoDeposit(Long customerId) {

		Query query = em.createQuery(
				"select d from Deposit as d " + "inner join d.depositHolder as dh where dh.customerId=:customerId and "
						+ "upper(d.status) = 'OPEN' and upper(d.depositCategory) = 'AUTO'");

		query.setParameter("customerId", customerId);

		List<Deposit> depositList = query.getResultList();
		if (depositList.size() > 0) {
			return depositList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Deposit> getAutoDepositList(Long customerId) {

		Query query = em.createQuery(
				"select d from Deposit as d " + "inner join d.depositHolder as dh where dh.customerId=:customerId and "
						+ "upper(d.status) = 'OPEN' and upper(d.depositCategory) = 'AUTO' order by d.id desc");

		query.setParameter("customerId", customerId);

		List depositList = query.getResultList();
		if (depositList.size() > 0) {
			return (List<Deposit>) depositList;
		} else {
			return null;
		}

	}

	@Override
	public Deposit getAutoDeposit(Long customerId, Float interestRate) {

		Query query = em.createQuery("select d from Deposit as d "
				+ "inner join d.depositHolder as dh where dh.customerId=:customerId and d.interestRate=:interestRate and "
				+ "upper(d.status) = 'OPEN' and upper(d.depositCategory) = 'AUTO'");

		query.setParameter("customerId", customerId);
		query.setParameter("interestRate", interestRate);
		List depositList = query.getResultList();
		if (depositList.size() > 0) {
			return (Deposit) depositList.get(0);
		} else {
			return null;
		}

	}

	@Override
	public List<Deposit> getAutoDeposits(Long customerId, String sweepInType) {

		Query query = em.createQuery("select d from Deposit as d "
				+ "inner join d.depositHolder as dh where dh.customerId=:customerId and "
				+ "upper(d.status) = 'OPEN' and upper(d.depositCategory) = 'AUTO' and d.sweepInType=:sweepInType");

		query.setParameter("customerId", customerId);
		query.setParameter("sweepInType", sweepInType);
		return (List<Deposit>) query.getResultList();

	}

	@Override
	public List<BigInteger> getDistinctCustomerOfAutoDeposits(String sweepInType) {

		Query query = em.createNativeQuery(
				"SELECT distinct dh.customerId from Deposit d inner join depositholder dh on d.id = dh.depositid "
						+ " where dh.depositHolderStatus='PRIMARY' and d.depositCategory='AUTO' and d.sweepInType=:sweepInType and "
						+ " upper(d.status)='OPEN' and d.clearanceStatus is null");

		query.setParameter("sweepInType", sweepInType);
		return (List<BigInteger>) query.getResultList();

	}

	@Override
	public List<AutoDepositForm> getAutoDeposits() {

		// Query query = em.createQuery("SELECT d.accountNumber,d.id as
		// depositId, d.depositAmount, d.currentBalance, d.status,
		// d.depositCategory, dh.customerId,c.customerName "
		// + "from Deposit d inner join depositholder dh on d.id = dh.depositid
		// inner join customerdetails c on dh.customerId = c.id "
		// + "inner join paymentdistribution pd on pd.depositid=dh.depositid "
		// + "where upper(d.status) = 'OPEN' and upper(d.depositCategory) =
		// 'AUTO' order by d.id desc");
		//
		Query query = em.createNativeQuery(
				"SELECT d.accountNumber,d.id as depositId, d.depositAmount, d.currentBalance, d.status, d.depositCategory, c.customerId,c.customerName "
						+ "from Deposit d inner join DepositHolder dh on d.id = dh.depositid inner join CustomerDetails c on dh.customerId = c.id "
						+ "where upper(d.status) = 'OPEN' and upper(d.depositCategory) = 'AUTO' and "
						+ "sweepInType is not null order by d.id desc");

		List<Object[]> autoDepositList = query.getResultList();
		List<AutoDepositForm> autoDeposits = new ArrayList<AutoDepositForm>();
		if (autoDepositList != null) {
			for (int i = 0; i < autoDepositList.size(); i++) {
				AutoDepositForm autoDepositForm = new AutoDepositForm();
				autoDepositForm.setAccountNumber((String) autoDepositList.get(i)[0]);
				autoDepositForm.setId(Long.parseLong(autoDepositList.get(i)[1].toString()));
				autoDepositForm.setDepositAmount((Double) autoDepositList.get(i)[2]);
				autoDepositForm.setCurrentBalance((Double) autoDepositList.get(i)[3]);
				autoDepositForm.setStatus((String) autoDepositList.get(i)[4]);
				autoDepositForm.setDepositCategory((String) autoDepositList.get(i)[5]);
				autoDepositForm.setCustomerId(autoDepositList.get(i)[6].toString());
				autoDepositForm.setCustomerName((String) autoDepositList.get(i)[7]);

				autoDeposits.add(autoDepositForm);
			}
			return autoDeposits;
		} else {
			return null;
		}

	}

	@Override
	public List<Deposit> getAllSweepDeposits() {

		Query query = em.createQuery(
				"SELECT d from Deposit d where upper(d.status) = 'OPEN' and upper(d.depositCategory) = 'AUTO'");
		return query.getResultList();

	}

	@Override
	public List<Deposit> getManualDeposits() {

		Query query = em.createQuery("select d from Deposit as d where "
				+ "upper(d.status) = 'OPEN' and upper(d.depositCategory) is null order by d.id desc");

		List depositList = query.getResultList();
		if (depositList.size() > 0) {
			return (List<Deposit>) depositList;
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public Collection<Deposit> findAllFDs() {

		Query query = em.createQuery("SELECT o FROM Deposit o where upper(o.status) = 'OPEN'");

		return (Collection<Deposit>) query.getResultList();
	}

	@Transactional
	public void updateApprovalStatus(Deposit deposit) {
		em.merge(deposit);
		em.flush();
	}

	@Override
	public List<Deposit> getDeposits() {

		TypedQuery<Deposit> q = em.createQuery(
				"SELECT o FROM Deposit o where upper(o.status) = 'OPEN' order by o.id desc", Deposit.class);

		return q.getResultList();
	}

	@Override
	public List<Deposit> getDeposits(Long customerId) {

		TypedQuery<Deposit> q = em.createQuery("select d from Deposit as d "
				+ "inner join d.depositHolder as dh where dh.customerId=:customerId and " + "upper(d.status) = 'OPEN'",
				Deposit.class);

		q.setParameter("customerId", customerId);

		return q.getResultList();
	}

	@Override
	public List<Deposit> getDepositsOfRIsForTDS(Long customerId) {

		TypedQuery<Deposit> q = em.createQuery(
				"select d from Deposit as d " + "inner join d.depositHolder as dh where dh.customerId=:customerId and "
						+ "dh.citizen='RI'and upper(d.status) = 'OPEN'",
				Deposit.class);

		q.setParameter("customerId", customerId);

		return q.getResultList();
	}

	@Override
	public List<Deposit> getDepositsOfNRIsForTDS(Long customerId) {

		TypedQuery<Deposit> q = em.createQuery(
				"select d from Deposit as d " + "inner join d.depositHolder as dh where dh.customerId=:customerId and "
						+ "dh.citizen='NRI'and (dh.nriAccountType!='NRE' and dh.nriAccountType!='FCNR' "
						+ "and dh.nriAccountType!='PRP') and upper(d.status) = 'OPEN'",
				Deposit.class);

		q.setParameter("customerId", customerId);

		return q.getResultList();
	}

	@Override
	public List<Distribution> getDistributionList(Long depositId) {

		Query query = em.createQuery("SELECT o FROM Distribution o WHERE o.depositId=:depositId order by id");
		query.setParameter("depositId", depositId);

		List<Distribution> distributionList = query.getResultList();
		if (distributionList.size() > 0) {
			return distributionList;
		} else {
			return null;
		}
	}
	// @SuppressWarnings("unchecked")
	// public Collection<FixedDeposit> findAllFDsForBank() {
	//
	// Query query = em.createQuery("SELECT o FROM FixedDeposit o");
	//
	// return (Collection<FixedDeposit>) query.getResultList();
	// }

	public Deposit getDepositByDepositHolder(Long depositHolderId) {
		Query query = em.createQuery("select d from Deposit as d "
				+ "inner join d.depositHolder as dh where dh.depositHolderId=:depositHolderId and "
				+ "upper(d.status) = 'OPEN' and upper(d.approvalStatus='APPROVED'");

		query.setParameter("depositHolderId", depositHolderId);

		List<Deposit> depositList = query.getResultList();
		if (depositList.size() > 0) {
			return depositList.get(0);
		} else {
			return null;
		}
	}

	public Deposit findByDepositId(Long depositId) {
		Query query = em.createQuery("SELECT o FROM Deposit o WHERE o.id=:depositId");
		query.setParameter("depositId", depositId);
		List<Deposit> depositList = query.getResultList();
		if (depositList.size() > 0) {
			return depositList.get(0);
		} else {
			return null;
		}

	}

	/*
	 * CreatedBy: Ravi Pratap Singh Query: To get the closed deposit list from
	 * backend for given time period.
	 * 
	 */
	@Override
	public List<DepositForm> getClosedTransactionsList(Date fromDate, Date toDate) {

		Query q = em.createNativeQuery("SELECT d.id as depositId,d.accountNumber,d.depositAmount,d.currentBalance,"
				+ "d.maturityDate,d.createdDate,d.depositCategory,dh.depositHolderStatus,d.status,d.approvalStatus,c.customerName,d.newMaturityDate "
				+ "from deposit d inner join depositholder dh on d.id = dh.depositId  and dh.depositHolderStatus='PRIMARY' inner "
				+ "join customerdetails c on dh.customerId = c.id where d.status='CLOSE'"
				+ " and closingDate>=:fromDate AND closingDate<=:toDate");
		q.setParameter("fromDate", fromDate);
		q.setParameter("toDate", toDate);
		List<Object[]> depositList = q.getResultList();
		List<DepositForm> autoDeposits = new ArrayList<DepositForm>();
		if (depositList != null) {
			for (int i = 0; i < depositList.size(); i++) {
				DepositForm depositForm = new DepositForm();
				depositForm.setDepositId(Long.parseLong(depositList.get(i)[0].toString()));
				depositForm.setAccountNumber(depositList.get(i)[1].toString());
				depositForm.setDepositAmount(Double.parseDouble(depositList.get(i)[2].toString()));
				depositForm.setCurrentBalance(Double.parseDouble(depositList.get(i)[3].toString()));
				depositForm.setMaturityDate((Date) (depositList.get(i)[4]));
				depositForm.setCreatedDate((Date) (depositList.get(i)[5]));
				if (depositList.get(i)[6] != null) {
					depositForm.setDepositCategory(depositList.get(i)[6].toString());
				}
				depositForm.setDepositHolderStatus((depositList.get(i)[7]).toString());
				depositForm.setStatus(depositList.get(i)[8].toString());
				depositForm.setApprovalStatus(depositList.get(i)[9].toString());
				depositForm.setCustomerName(depositList.get(i)[10].toString());
				depositForm.setNewMaturityDate((Date) (depositList.get(i)[11]));
				autoDeposits.add(depositForm);
			}
			return autoDeposits;
		} else {
			return null;
		}
	}

	/*
	 * CreatedBy: Ravi Pratap Singh Query: To get the All closed deposit list.
	 * 
	 */
	@Override
	public List<DepositForm> getAllClosedTransactionsList() {

		Query q = em.createNativeQuery("SELECT d.id as depositId,d.accountNumber,d.depositAmount,d.currentBalance,"
				+ "d.maturityDate,d.createdDate,d.depositCategory,dh.depositHolderStatus,d.status,d.approvalStatus,c.customerName,d.newMaturityDate"
				+ "from deposit d inner join depositholder dh on d.id = dh.depositId  and dh.depositHolderStatus='PRIMARY' inner "
				+ "join customerdetails c on dh.customerId = c.id where d.status='CLOSE'");

		List<Object[]> depositList = q.getResultList();
		List<DepositForm> autoDeposits = new ArrayList<DepositForm>();
		if (depositList != null) {
			for (int i = 0; i < depositList.size(); i++) {
				DepositForm depositForm = new DepositForm();
				depositForm.setDepositId(Long.parseLong(depositList.get(i)[0].toString()));
				depositForm.setAccountNumber(depositList.get(i)[1].toString());
				depositForm.setDepositAmount(Double.parseDouble(depositList.get(i)[2].toString()));
				depositForm.setCurrentBalance(Double.parseDouble(depositList.get(i)[3].toString()));
				depositForm.setMaturityDate((Date) (depositList.get(i)[4]));
				depositForm.setCreatedDate((Date) (depositList.get(i)[5]));
				if (depositList.get(i)[6] != null) {
					depositForm.setDepositCategory(depositList.get(i)[6].toString());
				}
				depositForm.setDepositHolderStatus((depositList.get(i)[7]).toString());
				depositForm.setStatus(depositList.get(i)[8].toString());
				depositForm.setApprovalStatus(depositList.get(i)[9].toString());
				depositForm.setCustomerName(depositList.get(i)[10].toString());
				depositForm.setNewMaturityDate((Date) (depositList.get(i)[11]));
				autoDeposits.add(depositForm);
			}
			return autoDeposits;
		} else {
			return null;
		}
	}

	/*
	 * CreatedBy: Ravi Pratap Singh Query: To get the All closed Approved and
	 * Pending deposit list.
	 * 
	 */
	@Override
	public List<Deposit> getAllDeposits() {

		TypedQuery<Deposit> q = em.createQuery(
				"SELECT o FROM Deposit o where upper(o.status) = 'OPEN' and o.depositCategory is NULL order by o.id desc",
				Deposit.class);

		return q.getResultList();
	}

	@Override
	public List<AutoDepositForm> getAutoDepositListForCustomer(Long customerId) {

		Query query = em.createNativeQuery(
				"select d.id,d.accountnumber,d.depositamount,d.currentbalance ,dh.customerid,d.interestRate from Deposit"
						+ " as d inner join depositHolder as dh on d.id=dh.depositid where dh.customerId=:customerId and"
						+ " upper(d.status) = 'OPEN' and upper(d.depositCategory) = 'AUTO'");

		query.setParameter("customerId", customerId);

		List<AutoDepositForm> autoDeposits = new ArrayList<AutoDepositForm>();

		List<Object[]> depositList = query.getResultList();
		if (depositList.size() > 0) {
			for (int i = 0; i < depositList.size(); i++) {
				AutoDepositForm autoDepositForm = new AutoDepositForm();
				autoDepositForm.setId(Long.parseLong(depositList.get(i)[0].toString()));
				autoDepositForm.setAccountNumber((String) depositList.get(i)[1]);
				autoDepositForm.setDepositAmount((Double) depositList.get(i)[2]);
				autoDepositForm.setCurrentBalance((Double) depositList.get(i)[3]);
				autoDepositForm.setCustomerId(depositList.get(i)[4].toString());
				autoDepositForm.setInterestRate((float) depositList.get(i)[5]);
				autoDeposits.add(autoDepositForm);
			}
			return autoDeposits;

		} else {
			return null;
		}

	}

	public List<ReportForm> getReportSummary(Date fromDate, Date toDate) {

		/* for new deposit .. */
		String sqlNewDeposit = "SELECT count(DISTINCT d.id),k.dateSeries as Date,"
				+ "sum(dis.fixedamt) as fixedAmount,SUM(dis.variableamt) as variableAmount "
				+ "FROM deposit d INNER JOIN distribution dis " + "ON dis.depositid=d.id "
				+ "RIGHT JOIN (select (date(:fromDate) + i) as dateSeries "
				+ "from generate_series(0, (select date(:toDate) - date(:fromDate)))i ) as k "
				+ "ON date(d.createddate)=k.dateSeries AND dis.distributiondate=k.dateSeries "
				+ "AND upper(d.approvalStatus)='APPROVED' AND upper(d.status) = 'OPEN' "
				+ "AND upper(d.depositCategory) IS NULL " + "GROUP BY k.dateSeries " + "ORDER BY k.dateSeries ";

		Query qNewDeposit = em.createNativeQuery(sqlNewDeposit);

		qNewDeposit.setParameter("fromDate", fromDate);
		qNewDeposit.setParameter("toDate", toDate);

		List<Object[]> newDeposit = qNewDeposit.getResultList();

		/* for existing deposit .. */

		String sqlExistingDeposit = "SELECT SUM(dis.fixedamt) as fixedAmount, "
				+ "SUM(dis.variableamt) as variableAmount FROM deposit d "
				+ "RIGHT JOIN  (select (date(:fromDate) + i) as dateSeries "
				+ "from generate_series(0, (select date(:toDate) - date(:fromDate)))i ) as k "
				+ "ON date(d.createddate) <:fromDate " + "INNER JOIN distribution dis ON dis.depositid=d.id "
				+ "AND dis.distributiondate< k.dateSeries"
				+ " WHERE upper(d.approvalStatus)='APPROVED' AND upper(d.status) = 'OPEN' "
				+ "AND upper(d.depositCategory) IS NULL " + "GROUP BY k.dateSeries ORDER BY k.dateSeries";

		Query qExistingDeposit = em.createNativeQuery(sqlExistingDeposit);
		qExistingDeposit.setParameter("fromDate", fromDate);
		qExistingDeposit.setParameter("toDate", toDate);
		List<Object[]> existingDeposit = qExistingDeposit.getResultList();

		/* for withdraw .. */

		String sqlWithdraw = "SELECT SUM(dis.depositedamt) from " + "(select (date(:fromDate) + i) as dateSeries from "
				+ "generate_series(0, (select date(:toDate) - date(:fromDate)))i ) as k "
				+ "LEFT JOIN distribution dis ON dis.action='Withdraw' AND " + "dis.distributiondate=k.dateSeries "
				+ "GROUP BY k.dateSeries ORDER BY k.dateSeries";

		Query qWithdraw = em.createNativeQuery(sqlWithdraw);
		qWithdraw.setParameter("fromDate", fromDate);
		qWithdraw.setParameter("toDate", toDate);
		List<Double> withdrawList = qWithdraw.getResultList();

		/* for penality .. */
		String sqlPenality = "SELECT SUM(dis.depositedamt) from " + "(select (date(:fromDate) + i) as dateSeries from"
				+ " generate_series(0, (select date(:toDate) - date(:fromDate)))i ) as k "
				+ "LEFT JOIN distribution dis ON (dis.action=:penalty1 OR dis.action=:penalty2) "
				+ "AND dis.distributiondate=k.dateSeries " + "GROUP BY k.dateSeries ORDER BY k.dateSeries";

		Query qPenality = em.createNativeQuery(sqlPenality);
		qPenality.setParameter("fromDate", fromDate);
		qPenality.setParameter("toDate", toDate);
		qPenality.setParameter("penalty1", Constants.PENALTY);
		qPenality.setParameter("penalty2", Constants.PENALTY2);

		List<Double> penalityList = qPenality.getResultList();

		/* for GST .. */
		String sqlGst = "SELECT (SUM(g.cgst)+SUM(g.sgst)+SUM(g.igst)) as totalGst "
				+ "from (select (date(:fromDate) + i) as dateSeries from "
				+ "generate_series(0, (select date(:toDate) - date(:fromDate)))i ) as k "
				+ "LEFT JOIN gst g ON date(g.createddate)=k.dateSeries GROUP BY k.dateSeries "
				+ "ORDER BY k.dateSeries";

		Query qGst = em.createNativeQuery(sqlGst);
		qGst.setParameter("fromDate", fromDate);
		qGst.setParameter("toDate", toDate);

		List<Double> gstList = qGst.getResultList();

		List<ReportForm> reportList = new ArrayList<ReportForm>();

		int dayDiff = DateService.getDaysBetweenTwoDates(fromDate, toDate);

		for (int i = 0; i <= dayDiff; i++) {
			ReportForm reportForm = new ReportForm();

			Date thisDate = DateService.addDays(fromDate, i);

			/* for new deposit .. */
			if (newDeposit.size() > 0 && i < newDeposit.size()) {
				BigInteger countBigInt = (BigInteger) newDeposit.get(i)[0];
				reportForm.setNewCount(countBigInt.longValue());
				reportForm.setFromDate((Date) newDeposit.get(i)[1]);
				reportForm.setNewFixedAmount((Double) newDeposit.get(i)[2]);
				reportForm.setNewVariableAmount((Double) newDeposit.get(i)[3]);
			} else {
				reportForm.setNewCount(0l);
				reportForm.setFromDate(thisDate);
				reportForm.setNewFixedAmount(0d);
				reportForm.setNewVariableAmount(0d);
			}

			/* for existing deposit .. */

			if (existingDeposit.size() > 0 && i < existingDeposit.size()) {
				reportForm.setExistingFixedAmount((Double) existingDeposit.get(i)[0]);
				reportForm.setExistingVariableAmount((Double) existingDeposit.get(i)[1]);
			} else {
				reportForm.setExistingFixedAmount(0d);
				reportForm.setExistingVariableAmount(0d);
			}

			/* for withdraw .. */

			if (withdrawList.size() > 0 && i < withdrawList.size())
				reportForm.setWithdrawAmount(withdrawList.get(i));
			else
				reportForm.setWithdrawAmount(0d);

			/* for penality .. */

			if (penalityList.size() > 0 && i < penalityList.size()) {
				reportForm.setPenalityAmount(penalityList.get(i));
			} else {
				reportForm.setPenalityAmount(0d);
			}

			/* for gst .. */

			if (gstList.size() > 0 && i < gstList.size()) {
				reportForm.setGstAmount(gstList.get(i));
			} else {
				reportForm.setGstAmount(0d);
			}

			/* calculation of interest for last date of month */

			Date lastDate = DateService.getLastDate(thisDate);
			if (lastDate.compareTo(thisDate) == 0) {
				/* for Interest .. */

				String sqlInterest = "SELECT (SUM(dis.variableinterest) + "
						+ "SUM(dis.fixedinterest)) as totalInterest from distribution dis "
						+ "where upper(dis.action) = :action and dis.distributiondate=:fromDate "
						+ "group by dis.distributiondate ";

				Query qInterest = em.createNativeQuery(sqlInterest);
				qInterest.setParameter("fromDate", thisDate);
				qInterest.setParameter("action", "INTEREST");

				List<Double> interestList = qInterest.getResultList();
				if (interestList.size() > 0)
					reportForm.setInterestAmount(interestList.get(0));

				/* for tds */

				/* for Interest .. */
				String sqlTds = "SELECT sum(tdsamount) from tds t " + "where t.tdscalcdate= :fromDate ";

				Query qTds = em.createNativeQuery(sqlTds);
				qTds.setParameter("fromDate", thisDate);

				List<Double> tdsList = qTds.getResultList();
				if (tdsList.size() > 0)
					reportForm.setTdsAmount(tdsList.get(0));

			}

			reportList.add(reportForm);
			reportForm = null;
		}

		return reportList;
	}

	@Override
	public List<ReportForm> getReportSummaryForCus(Date fromDate, Date toDate, Long customerid) {

		/********** / for new deposit .. / **********/
		String sqlNewDeposit = "SELECT count(DISTINCT d.id),k.dateSeries as Date,"
				+ "sum(dis.fixedamt) as fixedAmount,SUM(dis.variableamt) as variableAmount "
				+ "FROM deposit d INNER JOIN depositHolder dh ON dh.depositid=d.id and dh.customerid=:customerid "
				+ "INNER JOIN distribution dis ON dis.depositid=d.id "
				+ "RIGHT JOIN (select (date(:fromDate) + i) as dateSeries "
				+ "from generate_series(0, (select date(:toDate) - date(:fromDate)))i ) as k "
				+ "ON date(d.createddate)=k.dateSeries AND dis.distributiondate=k.dateSeries "
				+ "AND upper(d.approvalStatus)='APPROVED' AND upper(d.status) = 'OPEN' " + "GROUP BY k.dateSeries "
				+ "ORDER BY k.dateSeries ";

		Query qNewDeposit = em.createNativeQuery(sqlNewDeposit);

		qNewDeposit.setParameter("fromDate", fromDate);
		qNewDeposit.setParameter("toDate", toDate);
		qNewDeposit.setParameter("customerid", customerid);

		List<Object[]> newDeposit = qNewDeposit.getResultList();

		/********** / for existing deposit .. / **********/

		String sqlExistingDeposit = "SELECT SUM(dis.fixedamt) as fixedAmount, "
				+ "SUM(dis.variableamt) as variableAmount FROM deposit d  "
				+ "INNER JOIN depositHolder dh ON dh.depositid=d.id and dh.customerid=:customerid "
				+ "RIGHT JOIN  (select (date(:fromDate) + i) as dateSeries "
				+ "from generate_series(0, (select date(:toDate) - date(:fromDate)))i ) as k "
				+ "ON date(d.createddate) <:fromDate "
				+ "INNER JOIN distribution dis ON dis.depositid=d.id AND dis.distributiondate< k.dateSeries"
				+ " WHERE upper(d.approvalStatus)='APPROVED' AND upper(d.status) = 'OPEN' "
				+ "GROUP BY k.dateSeries ORDER BY k.dateSeries";

		Query qExistingDeposit = em.createNativeQuery(sqlExistingDeposit);
		qExistingDeposit.setParameter("fromDate", fromDate);
		qExistingDeposit.setParameter("toDate", toDate);
		qExistingDeposit.setParameter("customerid", customerid);

		List<Object[]> existingDeposit = qExistingDeposit.getResultList();

		/********** / for withdraw .. / **********/

		String sqlWithdraw = "SELECT SUM(dis.depositedamt) from " + "(select (date(:fromDate) + i) as dateSeries from "
				+ "generate_series(0, (select date(:toDate) - date(:fromDate)))i ) as k "
				+ "LEFT JOIN distribution dis ON dis.action='Withdraw' AND dis.distributiondate=k.dateSeries "
				+ "LEFT JOIN depositHolder dh ON dh.depositid=dis.depositid and dh.customerid=:customerid "
				+ "GROUP BY k.dateSeries ORDER BY k.dateSeries";

		Query qWithdraw = em.createNativeQuery(sqlWithdraw);
		qWithdraw.setParameter("fromDate", fromDate);
		qWithdraw.setParameter("toDate", toDate);
		qWithdraw.setParameter("customerid", customerid);

		List<Double> withdrawList = qWithdraw.getResultList();

		/********** / for penality .. / **********/
		String sqlPenality = "SELECT SUM(dis.depositedamt) from " + "(select (date(:fromDate) + i) as dateSeries from"
				+ " generate_series(0, (select date(:toDate) - date(:fromDate)))i ) as k "
				+ "LEFT JOIN distribution dis ON (dis.action=:penalty1 OR dis.action=:penalty2) "
				+ "AND dis.distributiondate=k.dateSeries "
				+ "LEFT JOIN depositHolder dh ON dh.depositid=dis.depositid and dh.customerid=:customerid "
				+ "GROUP BY k.dateSeries ORDER BY k.dateSeries";

		Query qPenality = em.createNativeQuery(sqlPenality);
		qPenality.setParameter("fromDate", fromDate);
		qPenality.setParameter("toDate", toDate);
		qPenality.setParameter("penalty1", Constants.PENALTY);
		qPenality.setParameter("penalty2", Constants.PENALTY2);
		qPenality.setParameter("customerid", customerid);
		List<Double> penalityList = qPenality.getResultList();

		/********** / for GST .. / **********/
		String sqlGst = "SELECT (SUM(g.cgst)+SUM(g.sgst)+SUM(g.igst)) as totalGst "
				+ "from (select (date(:fromDate) + i) as dateSeries from "
				+ "generate_series(0, (select date(:toDate) - date(:fromDate)))i ) as k "
				+ "LEFT JOIN gst g ON date(g.createddate)=k.dateSeries "
				+ "LEFT JOIN depositHolder dh ON dh.depositid=g.depositid and dh.customerid=:customerid "
				+ " GROUP BY k.dateSeries ORDER BY k.dateSeries";

		Query qGst = em.createNativeQuery(sqlGst);
		qGst.setParameter("fromDate", fromDate);
		qGst.setParameter("toDate", toDate);
		qGst.setParameter("customerid", customerid);
		List<Double> gstList = qGst.getResultList();

		List<ReportForm> reportList = new ArrayList<ReportForm>();

		int dayDiff = DateService.getDaysBetweenTwoDates(fromDate, toDate);

		for (int i = 0; i <= dayDiff; i++) {
			ReportForm reportForm = new ReportForm();

			Date thisDate = DateService.addDays(fromDate, i);

			/********** / for new deposit .. / **********/
			if (newDeposit.size() > 0 && i < newDeposit.size()) {
				BigInteger countBigInt = (BigInteger) newDeposit.get(i)[0];
				reportForm.setNewCount(countBigInt.longValue());
				reportForm.setFromDate((Date) newDeposit.get(i)[1]);
				reportForm.setNewFixedAmount((Double) newDeposit.get(i)[2]);
				reportForm.setNewVariableAmount((Double) newDeposit.get(i)[3]);
			} else {
				reportForm.setNewCount(0l);
				reportForm.setFromDate(thisDate);
				reportForm.setNewFixedAmount(0d);
				reportForm.setNewVariableAmount(0d);
			}

			/********** / for existing deposit .. / **********/

			if (existingDeposit.size() > 0 && i < existingDeposit.size()) {
				reportForm.setExistingFixedAmount((Double) existingDeposit.get(i)[0]);
				reportForm.setExistingVariableAmount((Double) existingDeposit.get(i)[1]);
			} else {
				reportForm.setExistingFixedAmount(0d);
				reportForm.setExistingVariableAmount(0d);
			}

			/********** / for withdraw .. / **********/

			if (withdrawList.size() > 0 && i < withdrawList.size())
				reportForm.setWithdrawAmount(withdrawList.get(i));
			else
				reportForm.setWithdrawAmount(0d);

			/********** / for penality .. / **********/

			if (penalityList.size() > 0 && i < penalityList.size()) {
				reportForm.setPenalityAmount(penalityList.get(i));
			} else {
				reportForm.setPenalityAmount(0d);
			}
			/********** / for gst .. / **********/

			if (gstList.size() > 0 && i < gstList.size()) {
				reportForm.setGstAmount(gstList.get(i));
			} else {
				reportForm.setGstAmount(0d);
			}

			/**********
			 * / calculation of interest for last date of month /
			 **********/

			Date lastDate = DateService.getLastDate(thisDate);
			if (lastDate.compareTo(thisDate) == 0) {
				/********** / for Interest .. / **********/

				String sqlInterest = "SELECT (SUM(dis.variableinterest) + "
						+ "SUM(dis.fixedinterest)) as totalInterest from distribution dis "
						+ "INNER JOIN depositHolder dh ON dh.depositid=dis.depositid and dh.customerid=:customerid "
						+ "where upper(dis.action) = :action and dis.distributiondate=:fromDate "
						+ "group by dis.distributiondate ";

				Query qInterest = em.createNativeQuery(sqlInterest);
				qInterest.setParameter("fromDate", thisDate);
				qInterest.setParameter("action", "INTEREST");
				qInterest.setParameter("customerid", customerid);

				List<Double> interestList = qInterest.getResultList();
				if (interestList.size() > 0)
					reportForm.setInterestAmount(interestList.get(0));

				/******* / for tds / **********/

				/********** / for Interest .. / **********/
				String sqlTds = "SELECT sum(tdsamount) from tds t "
						+ "INNER JOIN depositHolder dh ON dh.depositid=t.id and dh.customerid=:customerid  "
						+ "where t.tdscalcdate= :fromDate ";

				Query qTds = em.createNativeQuery(sqlTds);
				qTds.setParameter("fromDate", thisDate);
				qTds.setParameter("customerid", customerid);

				List<Double> tdsList = qTds.getResultList();
				if (tdsList.size() > 0)
					reportForm.setTdsAmount(tdsList.get(0));

			}

			reportList.add(reportForm);
			reportForm = null;
		}

		return reportList;
	}

	@Override
	public List<DepositForm> getAllDepositsList() {

		String sql = "SELECT d.id depositId,d.accountNumber,d.depositAmount,d.currentBalance,d.maturityDate,d.createdDate,"
				+ "d.status,d.approvalStatus,d.newMaturityDate,c.customerName,c.category,d.depositCategory,dh.id depositHolderId,"
				+ "c.id customerId, d.depositClassification "
				+ " from Deposit d inner join depositholder dh on d.id = dh.depositid "
				+ "and dh.depositHolderStatus='PRIMARY' inner join customerdetails c on dh.customerId = c.id where"
				+ " d.status='OPEN' ORDER BY d.id DESC";
		Query depositQuery = em.createNativeQuery(sql);

		@SuppressWarnings("unchecked")
		List<Object[]> depositList = depositQuery.getResultList();
		List<DepositForm> depsitFormList = new ArrayList<DepositForm>();

		for (int i = 0; i < depositList.size(); i++) {
			System.out.println(i);

			DepositForm depositForm = new DepositForm();
			Long depositId = Long.parseLong(depositList.get(i)[0].toString());
			String accNo = (String) depositList.get(i)[1];
			Double depositAmount = (Double) depositList.get(i)[2];
			Double currentBalance = (Double) depositList.get(i)[3];
			Date maturityDate = (Date) depositList.get(i)[4];
			Date createdDate = (Date) depositList.get(i)[5];
			String status = (String) depositList.get(i)[6];
			String appStatus = (String) depositList.get(i)[7];
			Date newMaturityDate = (Date) depositList.get(i)[8];
			String cusName = (String) depositList.get(i)[9];
			String category = (String) depositList.get(i)[10];
			String depositCategory = (String) depositList.get(i)[11];
			Long depositHolderId = Long.parseLong(depositList.get(i)[12].toString());
			Long customerId = Long.parseLong(depositList.get(i)[13].toString());
			String depositClassification = (String) depositList.get(i)[14];

			depositForm.setDepositId(depositId);
			depositForm.setAccountNumber(accNo);
			depositForm.setDepositAmount(depositAmount);
			depositForm.setCurrentBalance(currentBalance);
			depositForm.setMaturityDate(maturityDate);
			depositForm.setCreatedDate(createdDate);
			depositForm.setStatus(status);
			depositForm.setApprovalStatus(appStatus);
			depositForm.setNewMaturityDate(newMaturityDate);
			depositForm.setCustomerName(cusName);
			depositForm.setCategory(category);
			depositForm.setDepositCategory(depositCategory);
			depositForm.setDepositHolderId(depositHolderId);
			depositForm.setCustomerId(customerId);
			depositForm.setDepositClassification(depositClassification);
			depsitFormList.add(depositForm);

		}

		return depsitFormList;

	}

	@Override
	public List<DepositForm> getAllRegularAndAutoDepositsList() {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager antmgr = emfactory.createEntityManager();

		String sql = "SELECT d.id depositId,d.accountNumber,d.depositAmount,d.currentBalance,d.maturityDate,d.createdDate,"
				+ "d.status,d.approvalStatus,c.customerName,d.depositCategory,d.newMaturityDate from Deposit d inner join depositholder dh on d.id = dh.depositid "
				+ "and dh.depositHolderStatus='PRIMARY' inner join customerdetails c on dh.customerId = c.id where"
				+ " d.status='OPEN' ORDER BY d.id DESC";

		Query depositQuery = antmgr.createNativeQuery(sql);

		@SuppressWarnings("unchecked")
		List<Object[]> depositList = depositQuery.getResultList();
		List<DepositForm> depsitFormList = new ArrayList<DepositForm>();
		for (int i = 0; i < depositList.size(); i++) {
			System.out.println(i);

			DepositForm depositForm = new DepositForm();
			Long depositId = Long.parseLong(depositList.get(i)[0].toString());
			String accNo = (String) depositList.get(i)[1];
			Double depositAmount = (Double) depositList.get(i)[2];
			Double currentBalance = (Double) depositList.get(i)[3];
			Date maturityDate = (Date) depositList.get(i)[4];
			Date createdDate = (Date) depositList.get(i)[5];
			String status = (String) depositList.get(i)[6];
			String appStatus = (String) depositList.get(i)[7];
			String cusName = (String) depositList.get(i)[8];
			Date newMaturityDate = (Date) depositList.get(i)[10];

			depositForm.setDepositId(depositId);
			depositForm.setAccountNumber(accNo);
			depositForm.setDepositAmount(depositAmount);
			depositForm.setCurrentBalance(currentBalance);
			depositForm.setMaturityDate(maturityDate);
			depositForm.setCreatedDate(createdDate);
			depositForm.setStatus(status);
			depositForm.setApprovalStatus(appStatus);
			depositForm.setCustomerName(cusName);
			depositForm.setDepositCategory((String) depositList.get(i)[9]);
			depositForm.setNewMaturityDate(newMaturityDate);
			depsitFormList.add(depositForm);

		}

		return depsitFormList;

	}

	@Override
	public List<DepositForm> getDepositByCustomerId(Long id) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager antmgr = emfactory.createEntityManager();

		String sql = "SELECT d.accountNumber,d.id depositId, c.id customerId,c.customerName,c.dateOfBirth,c.address,c.email,"
				+ " c.contactNum,dh.id as depositHolderId,d.approvalStatus,d.depositCategory,dh.contribution,d.createdDate,d.newMaturityDate,d.depositClassification,d.taxSavingDeposit,d.accountAccessType,dh.deathCertificateSubmitted,dh.depositHolderStatus from Deposit d inner join depositholder dh on d.id = dh.depositid inner join "
				+ " customerdetails c on dh.customerId = c.id where c.id=?1 and d.status='OPEN' and d.depositCategory is null ORDER BY d.id DESC";

		Query depositQuery = antmgr.createNativeQuery(sql);
		depositQuery.setParameter(1, id);

		@SuppressWarnings("unchecked")
		List<Object[]> depositList = depositQuery.getResultList();

		List<DepositForm> depsitFormList = new ArrayList<DepositForm>();
		for (int i = 0; i < depositList.size(); i++) {
			System.out.println(i);
			DepositForm depositForm = new DepositForm();
			String accNo = (String) depositList.get(i)[0];
			Long depositId = Long.parseLong(depositList.get(i)[1].toString());
			Long cusId = Long.parseLong(depositList.get(i)[2].toString());
			String cusName = (String) depositList.get(i)[3];
			Date cusDOB = (Date) depositList.get(i)[4];
			String cusAdr = (String) depositList.get(i)[5];
			String cusEmail = (String) depositList.get(i)[6];
			String cusContact = (String) depositList.get(i)[7];
			Long depositHolderId =  Long.parseLong(depositList.get(i)[8].toString());
			String appStatus = (String) depositList.get(i)[9];
			String depositCategory = (String) depositList.get(i)[10];
			//String depositType = (String) depositList.get(i)[11];
			Float contribution = (Float) depositList.get(i)[11];
			Date createdDate = (Date) depositList.get(i)[12];
			Date newMaturityDate = (Date) depositList.get(i)[13];
			String depositClassification = (String) depositList.get(i)[14];
			String taxSavingDeposit = (String) depositList.get(i)[15];
			
			String accountAccessType = (String) depositList.get(i)[16];
			String deathCertificateSubmitted = (String) depositList.get(i)[17];
			String depositHolderStatus = (String) depositList.get(i)[18];
			

			depositForm.setAccountNumber(accNo);
			depositForm.setDepositId(depositId);
			depositForm.setCustomerId(cusId);
			depositForm.setCustomerName(cusName);
			depositForm.setDateOfBirth(cusDOB);
			depositForm.setAddress(cusAdr);
			depositForm.setEmail(cusEmail);
			depositForm.setContactNum(cusContact);
			depositForm.setDepositHolderId(depositHolderId);
			depositForm.setApprovalStatus(appStatus);
			depositForm.setDepositCategory(depositCategory);
			//depositForm.setDepositType(depositType);
			depositForm.setContribution(contribution);
			depositForm.setCreatedDate(createdDate);
			depositForm.setNewMaturityDate(newMaturityDate);
			depositForm.setDepositClassification(depositClassification);
			depositForm.setTaxSavingDeposit(taxSavingDeposit);
			depositForm.setAccountAccessType(accountAccessType);
			depositForm.setDeathCertificateSubmitted(deathCertificateSubmitted);
			depositForm.setDepositHolderStatus(depositHolderStatus);
			depsitFormList.add(depositForm);

		}

		return depsitFormList;

	}

	public List<HolderForm> getByCustomerId(Long customerId) {

		String sql = "select new annona.form.HolderForm(d,dh) from Deposit as d inner join d.depositHolder as dh "
				+ "where dh.customerId=:customerId and d.status='OPEN' order by d.id desc";

		Query query1 = em.createQuery(sql);

		query1.setParameter("customerId", customerId);

		List<HolderForm> holder = query1.getResultList();

		return holder;

	}

	@Override
	public List<Journal> getJournalListByFromAndToDate(Date fromDate, Date toDate) {

		Query q = em.createQuery("SELECT o FROM Journal o WHERE  o.journalDate>=:fromDate and o.journalDate<=:toDate");
		q.setParameter("fromDate", fromDate);
		q.setParameter("toDate", toDate);

		List<Journal> journalList = q.getResultList();
		if (journalList.size() > 0)
			return journalList;
		else
			return null;
	}

	@Override
	public HolderForm getByCustomerIdAndDepositId(Long depositId, Long customerId) {

		String sql = "select new annona.form.HolderForm(d,dh) from Deposit as d inner join d.depositHolder as dh "
				+ "where dh.customerId=:customerId and d.id=:depositId";

		Query query1 = em.createQuery(sql);

		query1.setParameter("customerId", customerId);
		query1.setParameter("depositId", depositId);

		List<HolderForm> holder = query1.getResultList();
		if (holder.size() > 0)
			return holder.get(0);
		else
			return null;

	}

	public List<HolderForm> getByDepositId(Long depositId) {

		String sql = "select new annona.form.HolderForm(d,dh) from Deposit as d inner join d.depositHolder as dh "
				+ "where dh.depositId=:depositId ";

		Query query1 = em.createQuery(sql);

		query1.setParameter("depositId", depositId);

		List<HolderForm> holder = query1.getResultList();

		return holder;

	}

	public List<Deposit> findByMultipleId(List<Long> ids) {

		Query q = em.createQuery("SELECT o FROM Deposit o where o.id IN (:ids)");

		q.setParameter("ids", ids);

		return q.getResultList();

	}

	public List<Deposit> findByClearanceStatus() {

		Query q = em.createQuery(
				"SELECT o FROM Deposit o where o.clearanceStatus = :clearanceStatus and o.approvalStatus = :approvalStatus");

		q.setParameter("clearanceStatus", Constants.WAITINGFORCLEARANCE);
		q.setParameter("approvalStatus", Constants.PENDING);
		return q.getResultList();

	}

	@Override
	public Deposit getByAccountNumber(String accNumber) {

		Query q = em.createQuery("SELECT o FROM Deposit o where o.accountNumber=:accNumber");
		q.setParameter("accNumber", accNumber);
		List<Deposit> depositList = q.getResultList();
		if (depositList.size() > 0)
			return depositList.get(0);
		else
			return null;
	}

	@Transactional
	public RenewDeposit saveRenewDeposit(RenewDeposit renewDeposit) {
		em.persist(renewDeposit);
		return renewDeposit;
	}

	public List<HolderForm> getByCustomerIdDepositIdAndAccountNumber(Long customerId, Long depositId,
			String accountNumber) {

		String sql = "select new annona.form.HolderForm(d,dh) from Deposit as d inner join d.depositHolder as dh "
				+ "where dh.customerId=:customerId and d.status='OPEN' and d.id=:depositId and d.accountNumber=:accountNumber order by d.id desc";

		Query query1 = em.createQuery(sql);

		query1.setParameter("customerId", customerId);
		query1.setParameter("depositId", depositId);
		query1.setParameter("accountNumber", accountNumber);

		List<HolderForm> holder = query1.getResultList();

		return holder;

	}

	@Override
	public List<Deposit> getAllCategoryDepositsOfCurrentYear(Long customerId) {
		String financialYear = DateService.getFinancialYear(DateService.getCurrentDate());

		String[] years = financialYear.split("-");

		// Build from date
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(years[0]), 3, 1, 0, 0);
		Date fromDate = cal.getTime();

		cal.set(Integer.parseInt(years[1]), 2, 31, 0, 0);
		Date toDate = cal.getTime();

		TypedQuery<Deposit> q = em.createQuery(
				"select d from Deposit as d " + "inner join d.depositHolder as dh where dh.customerId=:customerId and "
						+ "d.newMaturityDate>=:fromDate " + " and ((d.closingDate is null) OR "
						+ "(d.closingDate is not null and d.closingDate>=:fromDate and d.closingDate<=:toDate)) "
						+ " and dh.citizen='RI'order by d.id",
				Deposit.class);

		q.setParameter("customerId", customerId);
		q.setParameter("fromDate", fromDate);
		q.setParameter("toDate", toDate);

		return q.getResultList();
	}

	@Override
	public List<Deposit> getAllDepositsOfNRIsOfCurrentYear(Long customerId) {
		String financialYear = DateService.getFinancialYear(DateService.getCurrentDate());

		String[] years = financialYear.split("-");

		// Build from date
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(years[0]), 3, 1, 0, 0);
		Date fromDate = cal.getTime();

		cal.set(Integer.parseInt(years[1]), 2, 31, 0, 0);
		Date toDate = cal.getTime();

		TypedQuery<Deposit> q = em.createQuery(
				"select d from Deposit as d " + "inner join d.depositHolder as dh where dh.customerId=:customerId and "
						+ "dh.citizen='NRI'and (dh.nriAccountType!='NRE' and dh.nriAccountType!='FCNR') "
						+ " and d.newMaturityDate>=:fromDate " + " and ((d.closingDate is null) OR "
						+ "(d.closingDate is not null and d.closingDate>=:fromDate and d.closingDate<=:toDate)) "
						+ "order by d.id",
				Deposit.class);

		q.setParameter("customerId", customerId);
		q.setParameter("fromDate", fromDate);
		q.setParameter("toDate", toDate);

		return q.getResultList();
	}

	@Transactional
	public DepositBeforeRenew saveDepositBeforeRenew(DepositBeforeRenew depositBeforeRenew) {
		em.persist(depositBeforeRenew);
		return depositBeforeRenew;
	}

	@Override
	public List<Deposit> getDeposits(String primaryCitizen, String primaryNRIAccountType) {

		TypedQuery<Deposit> q = null;
		if (primaryNRIAccountType != null && !primaryNRIAccountType.equals("")) {
			q = em.createQuery(
					"select d from Deposit as d where upper(d.status) = 'OPEN' and d.primaryCitizen=:primaryCitizen and d.primaryNRIAccountType=:primaryNRIAccountType",
					Deposit.class);

			q.setParameter("primaryCitizen", primaryCitizen);
			q.setParameter("primaryNRIAccountType", primaryNRIAccountType);
		} else {
			q = em.createQuery(
					"select d from Deposit as d where upper(d.status) = 'OPEN' and d.primaryCitizen=:primaryCitizen",
					Deposit.class);

			q.setParameter("primaryCitizen", primaryCitizen);
		}

		return q.getResultList();
	}

	@Override
	public List<Deposit> getDepositsForMaturity() {

		TypedQuery<Deposit> q = em.createQuery("SELECT o FROM Deposit o where upper(o.status) = 'OPEN' and "
				+ "date(o.newMaturityDate)=date(:currentDate)", Deposit.class);
		q.setParameter("currentDate", DateService.getCurrentDate());
		return (List<Deposit>) q.getResultList();
	}

	@Override
	public List<Journal> getJournalListByFdAccNumberAndFromAndToDate(Long depositId, Date fromDate, Date toDate) {
		Query q = em.createQuery(
				"SELECT o FROM Journal o WHERE o.depositId=:depositId and o.journalDate>=:fromDate and o.journalDate<=:toDate");
		q.setParameter("fromDate", fromDate);
		q.setParameter("toDate", toDate);
		q.setParameter("depositId", depositId);

		List<Journal> journalList = q.getResultList();
		if (journalList.size() > 0)
			return journalList;
		else
			return null;
	}


	@Override
	public List<Deposit> getDepositsByProductConfiguration(Long productConfigurationId) {

		TypedQuery<Deposit> q = null;
		q = em.createQuery(
				"select d from Deposit as d where upper(d.status) = 'OPEN' and d.productConfigurationId=:productConfigurationId",
				Deposit.class);

		q.setParameter("productConfigurationId", productConfigurationId);

		return q.getResultList();
	}

	public List<Deposit> getSweepDeposits() {

		TypedQuery<Deposit> q = null;
		q = em.createQuery(
				"select d from Deposit as d where upper(d.status) = 'OPEN' and upper(d.depositCategory) = 'AUTO' and d.sweepInType is not null order by d.id desc",
				Deposit.class);

		return q.getResultList();
	}

	public CurrencyConfiguration getDefaultCurrency(String citizen, String nriAccountType) {
		try {
			TypedQuery<CurrencyConfiguration> query = null;
			if (citizen.equals("RI")) {
				query = em.createQuery("select d from CurrencyConfiguration as d where d.citizen =:citizen",
						CurrencyConfiguration.class);
				query.setParameter("citizen", citizen);
			} else {
				query = em.createQuery(
						"select d from CurrencyConfiguration as d where d.citizen =:citizen and d.accountType=:nriAccountType",
						CurrencyConfiguration.class);
				query.setParameter("citizen", citizen);
				query.setParameter("nriAccountType", nriAccountType);
			}
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public List<DepositForm> getAllRegularAndAutoDepositsByCustomerId(Long customerId) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager antmgr = emfactory.createEntityManager();

		String sql = "SELECT d.id depositId,d.accountNumber,d.depositAmount,d.currentBalance,d.maturityDate,d.createdDate,"
				+ "d.status,d.approvalStatus,c.customerName,d.depositCategory,d.newMaturityDate,dh.depositholderstatus,dh.contribution,dh.id from Deposit d inner join depositholder dh on d.id = dh.depositid "
				+ "and dh.depositHolderStatus='PRIMARY' inner join customerdetails c on dh.customerId = :customerId where"
				+ " d.status='OPEN' AND c.id = :customerId  ORDER BY d.id DESC";

		Query depositQuery = antmgr.createNativeQuery(sql);
		depositQuery.setParameter("customerId", customerId);

		@SuppressWarnings("unchecked")
		List<Object[]> depositList = depositQuery.getResultList();
		List<DepositForm> depsitFormList = new ArrayList<DepositForm>();
		for (int i = 0; i < depositList.size(); i++) {
			System.out.println(i);

			DepositForm depositForm = new DepositForm();
			Long depositId = Long.parseLong(depositList.get(i)[0].toString());
			String accNo = (String) depositList.get(i)[1];
			Double depositAmount = (Double) depositList.get(i)[2];
			Double currentBalance = (Double) depositList.get(i)[3];
			Date maturityDate = (Date) depositList.get(i)[4];
			Date createdDate = (Date) depositList.get(i)[5];
			String status = (String) depositList.get(i)[6];
			String appStatus = (String) depositList.get(i)[7];
			String cusName = (String) depositList.get(i)[8];
			Date newMaturityDate = (Date) depositList.get(i)[10];
			String depositholderstatus = (String) depositList.get(i)[11];
			Float contribution = (Float) depositList.get(i)[12];
			Long depositHolderId =  Long.parseLong(depositList.get(i)[13].toString());

			depositForm.setDepositId(depositId);
			depositForm.setAccountNumber(accNo);
			depositForm.setDepositAmount(depositAmount);
			depositForm.setCurrentBalance(currentBalance);
			depositForm.setMaturityDate(maturityDate);
			depositForm.setCreatedDate(createdDate);
			depositForm.setStatus(status);
			depositForm.setApprovalStatus(appStatus);
			depositForm.setCustomerName(cusName);
			depositForm.setDepositCategory((String) depositList.get(i)[9]);
			depositForm.setNewMaturityDate(newMaturityDate);
			depositForm.setDepositHolderStatus(depositholderstatus);
			depositForm.setContribution(contribution);
			depositForm.setDepositHolderId(depositHolderId);
			depsitFormList.add(depositForm);

		}

		return depsitFormList;

	}

	public Deposit getByAccountNumberAndStatusClose(String accNumber) {
		/*
		 * Query q = em.createQuery("FROM Deposit  d" +
		 * " join ProductConfiguration pr  pr.id = d.productConfigurationId " +
		 * "where  (pr.paymentMode != 'Fund Transfer' OR pr.paymentMode = '' OR pr.paymentMode is null) "
		 * + "AND d.accountNumber=:accNumber AND d.status = 'CLOSE'");
		 */

		Query q = em.createQuery("SELECT o FROM Deposit o where o.accountNumber=:accNumber AND o.status = 'CLOSE'");
		q.setParameter("accNumber", accNumber);
		List<Deposit> depositList = q.getResultList();
		if (depositList.size() > 0) {
			Deposit d = depositList.get(0);

			return d;

		} else
			return null;
	}

	
	
	@Override
	public List<LedgerReportForm> getLedgerReportFormByFromDateAndToDate(Date fromDate, Date toDate) {

		Query q = em.createNativeQuery(
				"select d.accountnumber,ld.ledgerDateDebit,ld.particularsDebit,ld.debitAmount,ld.ledgerDateCredit,ld.particularsCredit,ld.creditAmount,d.Id"
						+ " from deposit d inner join ledgerdepositaccount ld on d.id = ld.depositid WHERE ((ld.ledgerDateDebit>=:fromDate and ld.ledgerDateDebit<=:toDate) OR (ld.ledgerDateCredit>=:fromDate and ld.ledgerDateCredit<=:toDate))");

		q.setParameter("fromDate", fromDate);
		q.setParameter("toDate", toDate);

		@SuppressWarnings("unchecked")
		List<Object[]> emiList = q.getResultList();

		List<LedgerReportForm> ledgerReportFormList = new ArrayList<LedgerReportForm>();

		for (int i = 0; i < emiList.size(); i++) {
			LedgerReportForm reportForm = new LedgerReportForm();
			reportForm.setAccountNumber((String) emiList.get(i)[0]);
			reportForm.setLedgerDateDebit((Date) emiList.get(i)[1]);
			reportForm.setParticularsDebit((String) emiList.get(i)[2]);
			reportForm.setDebitAmount((Double) emiList.get(i)[3]);
			reportForm.setLedgerDateCredit((Date) emiList.get(i)[4]);
			reportForm.setParticularsCredit((String) emiList.get(i)[5]);
			reportForm.setCreditAmount((Double) emiList.get(i)[6]);
			Long depositId = Long.parseLong(emiList.get(i)[7].toString());
			reportForm.setDepositId(depositId);
			ledgerReportFormList.add(reportForm);
		}
		return ledgerReportFormList;
	}


}
