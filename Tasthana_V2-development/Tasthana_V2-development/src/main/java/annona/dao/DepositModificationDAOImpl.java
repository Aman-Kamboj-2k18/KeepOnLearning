package annona.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.Deposit;
import annona.domain.DepositHolder;
import annona.domain.DepositHolderNominees;
import annona.domain.DepositModification;
import annona.domain.DepositModificationMaster;
import annona.domain.DepositRates;
import annona.domain.ModificationPenalty;
import annona.services.DateService;

@Repository
public class DepositModificationDAOImpl implements DepositModificationDAO {

	@PersistenceContext
	EntityManager em;


	public DepositModification saveDepositHolder(DepositModification depositHolder) {
		em.persist(depositHolder);
		return depositHolder;
	}
	

	public DepositModificationMaster saveDepositModificationMaster(DepositModificationMaster depositModificationMaster) {
		em.persist(depositModificationMaster);
		return depositModificationMaster;
	}

	@Transactional
	public DepositModificationMaster updateDepositModificationMaster(DepositModificationMaster depositModificationMaster){
		em.merge(depositModificationMaster);
		em.flush();
		return depositModificationMaster;
	}
	
	public List<Object[]> getDepositModification(Long depositId) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager antmgr = emfactory.createEntityManager();

		String sql = "SELECT  dh.depositholderstatus, dm.id, dm.depositamount, dm.depositHolderId, "
				+ " dm.interestRate, dm.modifieddate, dm.paymentMode,dm.paymentType, "
				+ " dm.tenure,dm.tenureType from depositholder dh	right outer join "
				+ " depositmodification dm on dh.id = dm.depositHolderId "
				+ " where dh.depositId = ?1 order by modifieddate desc";

		Query depositQuery = antmgr.createNativeQuery(sql);
		depositQuery.setParameter(1, depositId);

		@SuppressWarnings("unchecked")
		List<Object[]> depositList = depositQuery.getResultList();

		return depositList;
	}

	public DepositModification getDepositHolderModification(Long depositHolderId) {

		if (depositHolderId == null || depositHolderId == 0)
			throw new IllegalArgumentException("Select the deposit");

		String hql = "SELECT o from DepositModification o where o.id in "
				+ "(select max(m.id) from DepositModification m GROUP BY m.depositHolderId "
				+ "having m.depositHolderId=:depositHolderId)";
		TypedQuery<DepositModification> query = em.createQuery(hql, DepositModification.class);
		query.setParameter("depositHolderId", depositHolderId);

		List lst = query.getResultList();
		if (lst.size() == 0)
			return null;

		return (DepositModification) lst.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getByDepositMonthly(Long depositId, String yearMonth) {

		// Query query = em.createQuery(
		// "select CONCAT(extract(YEAR from modifiedDate), '-M' ,extract(MONTH
		// from modifiedDate)) as month,"
		// + "count(*) from DepositModification WHERE depositId=:depositId group
		// by CONCAT(extract(YEAR from modifiedDate), '-M' ,"
		// + "extract(MONTH from modifiedDate)) order by "yearMonth
		// + "CONCAT(extract(YEAR from modifiedDate), '-M' ,extract(MONTH from
		// modifiedDate)) asc");

		Query query = em.createQuery(
				"select CONCAT(extract(YEAR from modifiedDate), '-M' ,extract(MONTH from modifiedDate)) as month, "
						+ "count(*) from DepositModification WHERE depositId=:depositId and CONCAT(extract(YEAR from modifiedDate), '-M' , "
						+ "extract(MONTH from modifiedDate)) = :yearMonth "
						+ "group by CONCAT(extract(YEAR from modifiedDate), '-M' , "
						+ "extract(MONTH from modifiedDate)) order by "
						+ "CONCAT(extract(YEAR from modifiedDate), '-M' ,extract(MONTH from modifiedDate)) asc");

		query.setParameter("depositId", depositId);
		query.setParameter("yearMonth", yearMonth);
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getByDepositQuaterly(Long depositId, String yearQuarter) {

		// Query query = em.createQuery("select CONCAT(extract(YEAR from
		// modifiedDate), '-Q' ,"
		// + "extract(quarter from modifiedDate)) as quarter,count(*) from"
		// + " DepositModification WHERE depositId=:depositId group by
		// CONCAT(extract(YEAR from modifiedDate),"
		// + " '-Q' ,extract(quarter from modifiedDate)) "
		// + "order by CONCAT(extract(YEAR from modifiedDate), '-Q'
		// ,extract(quarter from modifiedDate)) asc)");

		Query query = em.createQuery(
				"select CONCAT(extract(YEAR from modifiedDate), '-Q' , extract(quarter from modifiedDate)) as quarter, "
						+ "count(*)  from DepositModification WHERE depositId=:depositId  and "
						+ "CONCAT(extract(YEAR from modifiedDate), '-Q' , extract(quarter from modifiedDate)) = :yearQuarter "
						+ "group by CONCAT(extract(YEAR from modifiedDate), '-Q' ,extract(quarter from modifiedDate)) "
						+ "order by CONCAT(extract(YEAR from modifiedDate), '-Q' ,extract(quarter from modifiedDate))  asc");
		query.setParameter("depositId", depositId);
		query.setParameter("quarter", yearQuarter);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getByDepositYearly(Long depositId, Integer year) {

		Query query = em.createQuery("select extract(YEAR from modifiedDate) as year,count(*) from "
				+ "DepositModification WHERE depositId=:depositId and year=:year group by extract(YEAR from modifiedDate) "
				+ "order by extract(YEAR from modifiedDate) asc");
		query.setParameter("depositId", depositId);
		query.setParameter("year", year);

		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	 public List<Object[]> getByDepositModificationInCurrentFinancialYear(Long depositId) {
	  Date dt = DateService.getCurrentDate();
	  int month = DateService.getMonth(dt);
	  int year = DateService.getYear(dt);
	  int currentYear = DateService.getYear(dt);
	  int prevFinancial=0;
	  
	  if (month <= 2) {
	   year = currentYear - 1;
	   prevFinancial=1;
	   
	  } else {
	   year = currentYear;
	  }

	  int prevYear = currentYear - 1;
	  List<Object[]> lt = new ArrayList<Object[]>();
	  /*for modification count*/
	  String sql = "select distinct modificationNo, extract(YEAR from modifiedDate) as year, extract(MONTH from modifiedDate) as month,"
	    + "count(*) as count from DepositModification WHERE depositId=:depositId and"
	    + " extract(YEAR from modifiedDate)>=:year and (CASE WHEN :prevFinancial= 1 THEN (CASE WHEN extract(YEAR from modifiedDate) = :prevYear THEN extract(MONTH from "
	    + "modifiedDate)>= 4  ELSE extract(MONTH from modifiedDate)<=3  END) ELSE extract(MONTH from modifiedDate)>=4 END)"
	    + "group by modificationNo, extract(YEAR from modifiedDate),extract(MONTH from modifiedDate)";

	  Query query = em.createNativeQuery(sql);

	  query.setParameter("depositId", depositId);
	  query.setParameter("year", year);
	  query.setParameter("prevYear", prevYear);
	  query.setParameter("prevFinancial", prevFinancial);
	  lt= query.getResultList();
	  
	 
	  /*for withdraw count*/
	  String sqlWithdraw = "select cast(null as char),extract(YEAR from withdrawDate) as year, extract(MONTH from withdrawDate) as month,"
	    + "count(*) as count from withdrawdeposit WHERE  depositId=:depositId and"
	    + " extract(YEAR from withdrawDate)>=:year and (CASE WHEN :prevFinancial= 1 THEN (CASE WHEN extract(YEAR from withdrawDate) = :prevYear THEN "
	    + "extract(MONTH from withdrawDate)>= 4  ELSE extract(MONTH from withdrawDate)<=3  END) ELSE extract(MONTH from withdrawDate)>=4 END) "
	    + "group by extract(YEAR from withdrawDate),extract(MONTH from withdrawDate)";
	  Query queryWithdraw = em.createNativeQuery(sqlWithdraw);

	  queryWithdraw.setParameter("depositId", depositId);
	  queryWithdraw.setParameter("year", year);
	  queryWithdraw.setParameter("prevYear", prevYear);
	  queryWithdraw.setParameter("prevFinancial", prevFinancial);
	  
	  List<Object[]> lt2 =queryWithdraw.getResultList();
	  lt.addAll(lt2);
	  
	  
	  /*for payment count*/
	  String sqlPayment = "select cast(null as char), extract(YEAR from paymentDate) as year,extract(MONTH from paymentDate) as month, "
	    + "count(*) as count from payment WHERE depositId=:depositId and extract(YEAR from paymentDate)>=:year and "
	    + "(CASE WHEN :prevFinancial= 1 THEN (CASE WHEN extract(YEAR from paymentDate) = :prevYear THEN extract(MONTH from paymentDate)>= 4"
	    + "  ELSE extract(MONTH from paymentDate)<=3  END) ELSE extract(MONTH from paymentDate)>=4 END)"
	    + " and topUp=1 group by extract(YEAR from paymentDate),extract(MONTH from paymentDate)";
	  
	  
	  Query queryPayment = em.createNativeQuery(sqlPayment);
	  queryPayment.setParameter("depositId", depositId);
	  queryPayment.setParameter("year",year);
	  queryPayment.setParameter("prevYear", prevYear);
	  queryPayment.setParameter("prevFinancial", prevFinancial);
	  
	  List<Object[]> lt3 =queryPayment.getResultList();
	  lt.addAll(lt3);
	  return lt;

	 }
	@Override
	public DepositModification findByDepositId(Long depositId) {

		Query query = em.createQuery("SELECT o FROM DepositModification o WHERE o.depositId=:depositId");
		query.setParameter("depositId", depositId);

		return (DepositModification) query.getResultList().get(0);
	}

	public DepositModification getLastByDepositId(Long depositId) {

		Query query = em.createQuery(
				"SELECT o FROM DepositModification o where o.depositId =:depositId order by o.id DESC ");

		query.setParameter("depositId", depositId);

		List obj = query.getResultList();
		System.out.println("last record.." + obj.isEmpty());
		if (!obj.isEmpty())
			return (DepositModification) obj.get(0);
		else
			return null;

	}

	public Float getPreviousDepositRate(Long depositId, Date modifiedDate) {

		Query query = em.createQuery(
				"SELECT o FROM DepositModification o where o.depositId =:depositId and o.modifiedDate <:modifiedDate order by o.modifiedDate DESC ");

		query.setParameter("depositId", depositId);
		query.setParameter("modifiedDate", modifiedDate);

		List obj = query.getResultList();
		if (!obj.isEmpty())
		{
			Float prevInterestRate = ((DepositModification) obj.get(0)).getInterestRate();
			
			if(prevInterestRate != null)
			{
				return prevInterestRate;
			}
		}
		
		Query query2 = em.createQuery("SELECT o FROM Deposit o WHERE o.id=:depositId");
		query2.setParameter("depositId", depositId);
		List<Deposit> depositList = query2.getResultList();

		return depositList.get(0).getInterestRate();
	}
	
	public Double getModifiedDepositAmount(Long depositId) {

		Query query = em.createQuery(
				"SELECT o FROM DepositModification o where o.depositId =:depositId order by o.modifiedDate DESC ");

		query.setParameter("depositId", depositId);

		List obj = query.getResultList();
		if (!obj.isEmpty())
			return ((DepositModification) obj.get(0)).getDepositAmount();
		else 
			return null;
	}

	public Long getModificationCount(Long depositId, String duration) {
		String whereCondition = "";
		if (duration.equalsIgnoreCase("Full Tenure"))
			whereCondition = "";

		if (duration.equalsIgnoreCase("Yearly"))
			whereCondition = " and extract(YEAR from o.modifiedDate)= extract(YEAR from now())";

		if (duration.equalsIgnoreCase("Half Yearly"))
			whereCondition = "and CEIL( extract(MONTH FROM  o.modifiedDate) / 6)= CEIL( extract(MONTH FROM now()) / 6)";

		if (duration.equalsIgnoreCase("Quaterly"))
			whereCondition = "and extract(QUARTER from o.modifiedDate)= extract(QUARTER from now())";

		if (duration.equalsIgnoreCase("Monthly"))
			whereCondition = "and extract(MONTH from o.modifiedDate)= extract(MONTH from now())";

		Query query = em.createQuery(
				" select count(distinct o.modificationNo) from DepositModification o  where  o.depositId =:depositId "
						+ whereCondition);

		query.setParameter("depositId", depositId);

		List<Long> obj = query.getResultList();
		System.out.println(obj.isEmpty());
		if (!obj.isEmpty())
			return (Long) obj.get(0);
		else
			return null;
	}
	

	public ModificationPenalty saveModificationPenalty(ModificationPenalty modificationPenalty) {
		em.persist(modificationPenalty);
		return modificationPenalty;
	}
	
	@Override
	public ModificationPenalty getLastModificationPenalty(Long depositId) {

		Query query = em.createQuery("SELECT o FROM ModificationPenalty o WHERE o.depositId=:depositId order by o.penaltyDate DESC ");
		query.setParameter("depositId", depositId);

		List obj = query.getResultList();
		if(obj != null && obj.size()>0)
			return (ModificationPenalty) obj.get(0);
		else
			return null;
	}
	
	
	@Override
	 public List<Object[]> getAllModificationList(Long depositid) {

	  Query query = em.createNativeQuery("SELECT dm.depositid, dm.modifieddate,dm.modifiedby,"
	    + "dm.modificationno, cast('No' as char) AS penalty FROM depositmodification dm "
	    + "where dm.depositid=:depositid group by dm.modificationno, dm.depositid, "
	    + "dm.modifieddate,dm.modifiedby, penalty ORDER BY dm.modifieddate desc");
	 
	  query.setParameter("depositid",depositid);

	  List<Object[]> obj = query.getResultList();
	  if(obj.size()>0)
	   return obj;
	  
	  else
	   return null;
	 }
	 
	 @Override
	 public List<DepositModification> getByModificationNo(String modificationNo) {

	  Query query = em.createQuery(
	    "SELECT o FROM DepositModification o where o.modificationNo =:modificationNo");

	  query.setParameter("modificationNo", modificationNo);

	  List<DepositModification> obj = query.getResultList();
	 
	  if (obj.size()>0)
	   return  obj;
	  else
	   return null;

	 }
	 
	 
	 @Override
	 public DepositModification getPreviousModification(Long depositHolderId, Long modificationId) {

	  Query query = em.createQuery(
	    "SELECT o FROM DepositModification o where o.depositHolderId =:depositHolderId and o.id < :modificationId order by o.id desc");

	  query.setParameter("depositHolderId", depositHolderId);
	  query.setParameter("modificationId", modificationId);

	  List<DepositModification> obj = query.getResultList();
	 
	  if (obj.size()>0)
	   return  obj.get(0);
	  else
	   return null;

	 }
	 
}
