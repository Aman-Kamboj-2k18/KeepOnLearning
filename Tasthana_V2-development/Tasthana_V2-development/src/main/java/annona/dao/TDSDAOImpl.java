package annona.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.print.attribute.standard.DateTimeAtCompleted;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.AccountDetails;
import annona.domain.Customer;
import annona.domain.DepositRates;
import annona.domain.DepositTDS;
import annona.domain.DepositWiseCustomerTDS;
import annona.domain.Interest;
import annona.domain.TDS;
import annona.form.CustomerForm;
import annona.services.DateService;

@Repository
public class TDSDAOImpl implements TDSDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Method for entity manager
	 * 
	 * @return
	 */
	public EntityManager entityManager() {
		EntityManager em = new TDSDAOImpl().em;

		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional
	public TDS insert(TDS tds) {
		em.persist(tds);
		// flush a batch of inserts and release memory:
		em.flush();		
		return tds;
	}

	@Transactional
	public TDS update(TDS tds) {
		em.merge(tds);
		em.flush();
		return tds;
	}

	public List<TDS> getByDepositId(Long depositId) {

		Query query = em.createQuery("SELECT o FROM TDS o WHERE o.depositId=:depositId order by id");
		query.setParameter("depositId", depositId);
		return query.getResultList();
	}

	@Transactional
	public void deleteByDepositId(Long depositId) {
		Query query = em.createQuery("DELETE FROM TDS WHERE depositId=:depositId");
		query.setParameter("depositId", depositId);
		query.executeUpdate();
	}

	@Transactional
	public TDS deleteTDSFromCurrentDate(Long depositId) {
		Date dt = DateService.getCurrentDateTime();

		String sql = "DELETE FROM TDS o WHERE o.depositId=:depositId and o.tdsCalcDate>=:currentDate and o.tdsDeducted is null";
		Query query = em.createNativeQuery(sql);
		query.setParameter("depositId", depositId);
		query.setParameter("currentDate", DateService.getCurrentDate());
		query.executeUpdate();

		Query query2 = em.createQuery(
				"SELECT o FROM TDS o WHERE o.depositId=:depositId and o.tdsCalcDate= (select max(m.tdsCalcDate) FROM TDS m WHERE m.depositId=:depositId)");
		query2.setParameter("depositId", depositId);
		List tdsList = query2.getResultList();
		if (tdsList.size() > 0)
			return (TDS) tdsList.get(0);
		else
			return null;

	}

	public TDS getTDSByMonth(Long customerId, Integer month, Integer year) {
		Query query = em.createQuery(
				"SELECT o FROM TDS o WHERE o.customerId=:customerId and EXTRACT(MONTH FROM o.tdsCalcDate)=:month and "
						+ "EXTRACT(YEAR FROM o.tdsCalcDate)=:year");
		query.setParameter("customerId", customerId);
		query.setParameter("month", month);
		query.setParameter("year", year);
		List lst = query.getResultList();
		if (lst != null && lst.size() > 0)
			return (TDS) lst.get(0);
		else
			return null;
	}

	public List<TDS> getTDSByQuarter(Long depositId, Integer quarter, Integer year) {
		// Query query = em.createQuery("SELECT o FROM TDS o WHERE
		// o.depositId=:depositId and extract(quarter from
		// o.tdsCalcDate))=:quarter and EXTRACT(MONTH FROM o.tdsCalcDate)=:month
		// and "
		// + "EXTRACT(YEAR FROM o.tdsCalcDate)=:year");
		Query query = em.createQuery(
				"SELECT o FROM TDS o WHERE o.depositId=:depositId and extract(QUARTER from o.tdsCalcDate)=:quarter and "
						+ "EXTRACT(YEAR FROM o.tdsCalcDate)=:year");
		query.setParameter("depositId", depositId);
		query.setParameter("quarter", quarter);
		query.setParameter("year", year);
		return query.getResultList();

	}
	
	public List<TDS> getTDSDeductedByFinancialYear(Long depositId, String financialYear) {

		String[] years = financialYear.split("-");

		// Build from date
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(years[0]), 3, 1, 0, 0);
		Date fromDate = cal.getTime();

		cal.set(Integer.parseInt(years[1]), 2, 31, 0, 0);
		Date toDate = cal.getTime();

		System.out.println("From Date); " + fromDate);

		Query query = em.createQuery("SELECT o FROM TDS o WHERE o.depositId=:depositId and o.tdsDeducted=1 "
				+ "and o.tdsCalcDate>=:fromDate and o.tdsCalcDate<=:toDate order by id");

		query.setParameter("depositId", depositId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		return query.getResultList();
	}

	@Transactional
	public DepositWiseCustomerTDS insertCustomerTDS(DepositWiseCustomerTDS customrTDS) {
		em.persist(customrTDS);
		// flush a batch of inserts and release memory:
		em.flush();
		return customrTDS;
	}

	@Transactional
	public DepositWiseCustomerTDS updateCustomerTDS(DepositWiseCustomerTDS customrTDS) {
		em.merge(customrTDS);
		em.flush();
		return customrTDS;
	}

	public List<DepositWiseCustomerTDS> getCustomerTDSReportByDepositId(Long customerId) {
		  Query query = em.createQuery("SELECT o FROM DepositWiseCustomerTDS o WHERE o.customerId=:customerId");
		  query.setParameter("customerId", customerId);
		  return query.getResultList();
	}
	
	public DepositTDS getConsolidatedTDS(Long depositId, String financialYear, Date date) {
		Query query = em.createQuery("SELECT sum(calculatedTDSAmount), sum(contributedTDSAmount) FROM DepositWiseCustomerTDS o WHERE o.depositId=:depositId and o.financialYear=:financialYear and o.isTDSDeducted=0 and o.tdsDate<=:date");
		query.setParameter("depositId", depositId);
		query.setParameter("financialYear", financialYear);
		query.setParameter("date", date);
		
		DepositTDS tds = new DepositTDS();
		List<Object[]> tdsList = query.getResultList();
		if(tds != null && tdsList.size()>0)
		{
			for(int i=0; i< tdsList.size(); i++)
			{
				Object[] obj = tdsList.get(i);
				tds.setDepositId(depositId);
				tds.setTdsDate(DateService.getCurrentDate());
				tds.setCalculatedTDSAmount(obj[0] == null ? 0 :Double.parseDouble(obj[0].toString()));
				//tds.setActualTDSAmount(Double.parseDouble(obj[0].toString()));
				tds.setFinancialYear(financialYear);
			}
		}
		return tds;
	}
	
	
	public DepositTDS getConsolidatedTDSForDeposit(Long depositId, List<Long> tdsIds) {
		
		if(tdsIds.size() == 0)
			return null;
		
		Query query = em.createQuery("SELECT sum(calculatedTDSAmount), sum(contributedTDSAmount) FROM DepositWiseCustomerTDS o WHERE o.depositId=:depositId and tdsId in (:tdsIds)");
		query.setParameter("depositId", depositId);
		query.setParameter("tdsIds", tdsIds);
		
		DepositTDS tds = new DepositTDS();
		List<Object[]> tdsList = query.getResultList();
		if(tds != null && tdsList.size()>0)
		{
			for(int i=0; i< tdsList.size(); i++)
			{
				Object[] obj = tdsList.get(i);
				tds.setDepositId(depositId);
				tds.setTdsDate(DateService.getCurrentDate());
				tds.setCalculatedTDSAmount(obj[0] == null ? 0 :Double.parseDouble(obj[0].toString()));
				//tds.setActualTDSAmount(Double.parseDouble(obj[0].toString()));
				tds.setFinancialYear(DateService.getFinancialYear(DateService.getCurrentDate()));
			}
		}
		return tds;
	}
	public Double getTDS(Long customerId, String financialYear) {
		Query query = em.createQuery("SELECT sum(calculatedTDSAmount) FROM TDS o WHERE o.customerId=:customerId and o.financialYear=:financialYear");
		query.setParameter("customerId", customerId);
		query.setParameter("financialYear", financialYear);
		
		Object tdsAmount = query.getSingleResult();
		if(tdsAmount!=null)
			return Double.parseDouble(tdsAmount.toString());
		else
			return 0d;
	}
	
	public Double getCustomerTDSForDeposit(Long customerId, Long depositId, String financialYear) {
		Query query = em.createQuery("SELECT o.calculatedTDSAmount FROM DepositWiseCustomerTDS o WHERE o.customerId=:customerId and o.depositId=:depositId and o.financialYear=:financialYear");
		query.setParameter("customerId", customerId);
		query.setParameter("depositId", depositId);
		query.setParameter("financialYear", financialYear);
		
		try
		{
			Object calculatedTDSAmount = query.getSingleResult();
			if(calculatedTDSAmount!=null)
				return Double.parseDouble(calculatedTDSAmount.toString());
			else
				return 0d;
		}
		catch(Exception ex)
		{
			return 0d;
		}

		
	}
	
	public Double getCustomerTDSForDeposit(Long customerId, Long depositId, Long tdsId, String financialYear) {
		Query query = em.createQuery("SELECT o FROM DepositWiseCustomerTDS o WHERE o.customerId=:customerId and o.depositId=:depositId and o.tdsId=:tdsId and o.financialYear=:financialYear order by id desc");
		query.setParameter("customerId", customerId);
		query.setParameter("depositId", depositId);
		query.setParameter("tdsId", tdsId);
		query.setParameter("financialYear", financialYear);
		
		List<DepositWiseCustomerTDS> lst =  query.getResultList();
//		try
//		{
//			Object calculatedTDSAmount = query.getSingleResult();
			if(lst!=null && lst.size()>0)
				return lst.get(0).getCalculatedTDSAmount();
			else
				return 0d;
//		}
//		catch(Exception ex)
//		{
//			return 0d;
//		}

		
	}
	
	public List<Object[]> getAllCustomerTds(){


		String sql = "SELECT t.tdsAmount,t.calculatedTDSAmount,t.tdsCalcDate,t.financialYear,"
				+ "t.customerId,c.customerName FROM TDS as t inner join customerdetails as c " 
				+ "on t.customerId = c.id";

		Query query = em.createNativeQuery(sql);
		
		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.getResultList();
		
		return rows;
	}
	
	@Transactional
	public DepositTDS insertDepositTDS(DepositTDS depositTDS) {
		em.persist(depositTDS);
		// flush a batch of inserts and release memory:
		em.flush();
		return depositTDS;
	}
	
	public TDS getCustomerTDS(Long customerId, String financialYear, Date date) {
		Query query = em.createQuery("SELECT o FROM TDS o WHERE o.customerId=:customerId and o.financialYear=:financialYear and o.tdsCalcDate<=:date");
		query.setParameter("customerId", customerId);
		query.setParameter("financialYear", financialYear);
		query.setParameter("date", date);
		
		List tdsList = query.getResultList();
		if(tdsList!=null && tdsList.size()> 0)
			return (TDS)tdsList.get(0);
		else
			return null;
	}
	
	public TDS getLastTDSDeducted(Long customerId, String financialYear) {
		Query query = em.createQuery("SELECT o FROM TDS o WHERE o.customerId=:customerId and o.financialYear=:financialYear and partlyCalculated=0 order by o.tdsCalcDate desc");
		query.setParameter("customerId", customerId);
		query.setParameter("financialYear", financialYear);
		
		List tdsList = query.getResultList();
		if(tdsList!=null && tdsList.size()> 0)
			return (TDS)tdsList.get(0);
		else
			return null;
	}
	
	public Double getTotalPartlyCalculatedTDS(Long customerId, Date fromDate, Date toDate) 
	{
		Query query = em.createQuery("SELECT sum(calculatedTDSAmount)  FROM TDS o WHERE "
				+ "o.customerId=:customerId and "
				+ "o.tdsCalcDate>=:fromDate and o.tdsCalcDate<=:toDate) "
				+ "and partlyCalculated=1");
		query.setParameter("customerId", customerId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
			
		Object tdsAmount = query.getSingleResult();
		if(tdsAmount!=null)
			return Double.parseDouble(tdsAmount.toString());
		else
			return 0d;
	}

	@Transactional
	public void updateDeductTDSInDepositWiseCustomerTDS(Long depositId)
	{
		  Query qry = em.createQuery("update DepositWiseCustomerTDS d set d.isTDSDeducted=1 where d.depositId=:depositId");
		  qry.setParameter("depositId",depositId);
		  int res = qry.executeUpdate();
	}
	
	public DepositTDS getDepositTDS(Long depositId, Date date) {
		Query query = em.createQuery("SELECT o FROM DepositTDS o WHERE o.depositId=:depositId and date(o.tdsDate)<=date(:date)");
		query.setParameter("depositId", depositId);
		query.setParameter("date", date);
		
		List tdsList = query.getResultList();
		if(tdsList!=null && tdsList.size()> 0)
			return (DepositTDS)tdsList.get(0);
		else
			return null;
	}
	
	public Double getTotalTDS(Long depositId, Date fromDate, Date toDate) 
	{
		Query query = em.createQuery("SELECT sum(calculatedTDSAmount)  FROM DepositTDS o WHERE o.depositId=:depositId and "
				+ "date(o.tdsDate)>=date(:fromDate) and date(o.tdsDate)<=date(:toDate)");
		query.setParameter("depositId", depositId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
			
		Object tdsAmount = query.getSingleResult();
		if(tdsAmount!=null)
			return Double.parseDouble(tdsAmount.toString());
		else
			return 0d;
	}
}
