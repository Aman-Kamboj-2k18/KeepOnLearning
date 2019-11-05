package annona.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.WithdrawPenaltyAmountBased;
import annona.domain.WithdrawPenaltyDetails;
import annona.domain.WithdrawPenaltyMaster;
import annona.domain.WithdrawPenaltyTenureBased;
import annona.form.WithdrawPenaltyForm;
import annona.services.DateService;

@Repository
public class WithdrawPenaltyDAOImpl implements WithdrawPenaltyDAO {

	@PersistenceContext
	private EntityManager em;

	public List<Date> getEffectiveDates() {
		// SELECT distinct o.effectiveDate FROM WithdrawPenaltyMaster o order by
		// o.effectiveDate

		Query query = em
				.createQuery("SELECT distinct o.effectiveDate FROM WithdrawPenaltyMaster o order by o.effectiveDate");

		@SuppressWarnings("unchecked")
		List<Date> accDetailsList = query.getResultList();

		return accDetailsList;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getSlabBasedWithdrawPenalties(Date effectiveDate, Long productConfigurationId, Integer isPremature) {

		String sql = "SELECT d.id,m.effectiveDate, d.withdrawPenaltyMasterId, d.amountSign, d.amount, d.tenureSign, d.tenure, d.penaltyRate, "
				+ "d.penaltyFlatAmount FROM WithdrawPenaltyMaster m inner join WithdrawPenaltyDetails d "
				+ "on m.id = d.withdrawPenaltyMasterId and date(m.effectiveDate) = date(:effectiveDate) and m.productConfigurationId= :productConfigurationId and m.isPrematureWithdraw= :isPremature ";

		Query query = em.createNativeQuery(sql);
		query.setParameter("effectiveDate", effectiveDate);
		query.setParameter("productConfigurationId", productConfigurationId);
		query.setParameter("isPremature", isPremature);
 
		return (List<Object[]>) query.getResultList();

	}

	@Transactional
	public WithdrawPenaltyMaster insertWithdrawPenaltyMaster(WithdrawPenaltyMaster withdrawPenaltyMaster) {
		em.persist(withdrawPenaltyMaster);
		return withdrawPenaltyMaster;
	}

	/*
	 * public void insertWithdrawPenaltyDetails(WithdrawPenaltyDetails
	 * WithdrawPenaltyDetails) { em.persist(WithdrawPenaltyDetails); }
	 */

	@Override
	public Boolean isAmountToAndAmountFromRangeExist(WithdrawPenaltyForm withdrawPenaltyForm) {
		try {
			Query query = em.createNativeQuery(
					"select w.id,m.effectiveDate, w.withdrawPenaltyMasterId, w.amountFrom, w.amountTo from WithdrawPenaltyMaster m  inner join WithdrawPenaltyDetails w on m.id = w.withdrawPenaltyMasterId where date(m.effectiveDate)= date(:effectiveDate) and ((w.amountSign = :amountSign  AND  w.amount = :amount) and  (w.tenureSign = :tenureSign  AND  w.tenure = :tenure)) and w.id!=:id");
					//"select w.id,m.effectiveDate, w.withdrawPenaltyMasterId, w.amount, w.tenure from WithdrawPenaltyMaster m  inner join WithdrawPenaltyDetails w on m.id = w.withdrawPenaltyMasterId where date(m.effectiveDate)= date(:effectiveDate) and w.amount =:amount and w.tenure=:tenure and w.id!=:id");
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date date = simpleDateFormat.parse(withdrawPenaltyForm.getEffectiveDate());
			SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
			query.setParameter("effectiveDate", newFormat.format(date));
			query.setParameter("amount", withdrawPenaltyForm.getAmount());
			query.setParameter("tenure", withdrawPenaltyForm.getTenure());
			query.setParameter("amountSign", withdrawPenaltyForm.getAmountSign());
			query.setParameter("tenureSign", withdrawPenaltyForm.getTenureSign());
			query.setParameter("id", withdrawPenaltyForm.getId());
			List<Object[]> list = query.getResultList();
			if (list.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean isAmountToAndAmountFromRangeExistNew(WithdrawPenaltyForm withdrawPenaltyForm) {
		try
		{		
			Query query = em.createNativeQuery(
					"select w.id,m.effectiveDate, m.productConfigurationId, m.withdrawType, w.withdrawPenaltyMasterId, w.amountFrom, w.amountTo from WithdrawPenaltyMaster m  "
					+ " inner join WithdrawPenaltyAmountBased w on m.id = w.withdrawPenaltyMasterId where productConfigurationId=:productConfigurationId and withdrawType=:withdrawType and "
					+ "date(m.effectiveDate)= date(:effectiveDate) and ((w.amountFrom <= :amountFrom  AND  w.amountTo >= :amountFrom) OR  (w.amountTo >= :amountTo  AND  w.amountFrom <= :amountTo))");
			
			String pattern = "dd/MM/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			Date date = simpleDateFormat.parse(withdrawPenaltyForm.getEffectiveDate());
			SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
			query.setParameter("effectiveDate", newFormat.format(date));
			query.setParameter("amountFrom", withdrawPenaltyForm.getAmountFrom());
			query.setParameter("amountTo", withdrawPenaltyForm.getAmountTo());
			query.setParameter("productConfigurationId", withdrawPenaltyForm.getProductConfigurationId());
			query.setParameter("withdrawType", withdrawPenaltyForm.getWithdrawType());

			List<Object[]> list = query.getResultList();
			if (list.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional
	public WithdrawPenaltyAmountBased saveWithdrawPenaltyAmountBased(
			WithdrawPenaltyAmountBased withdrawPenaltyAmountBased) {
		em.persist(withdrawPenaltyAmountBased);
		return withdrawPenaltyAmountBased;
	}
	
	@Override
	@Transactional
	public WithdrawPenaltyAmountBased updateWithdrawPenaltyAmountBased(
			WithdrawPenaltyAmountBased withdrawPenaltyAmountBased) {
		em.merge(withdrawPenaltyAmountBased);
		return withdrawPenaltyAmountBased;
	}
	
	@Override
	@Transactional
	public WithdrawPenaltyDetails updateWithdrawPenaltyDetails(
			WithdrawPenaltyDetails withdrawPenaltyDetails) {
		em.merge(withdrawPenaltyDetails);
		return withdrawPenaltyDetails;
	}
	
	
	@Override
	@Transactional
	public WithdrawPenaltyTenureBased saveWithdrawPenaltyTenureBased(
			WithdrawPenaltyTenureBased withdrawPenaltyTenureBased) {
		em.persist(withdrawPenaltyTenureBased);
		em.flush();
		return withdrawPenaltyTenureBased;
	}

	

	@SuppressWarnings("unchecked")
	public WithdrawPenaltyMaster getWithdrawPenalyMaster(Long productConfigurationId,Boolean isPrematureWithdraw) {

		String sql = "";
		if(isPrematureWithdraw)
			sql = "SELECT o FROM WithdrawPenaltyMaster o where o.productConfigurationId = :productConfigurationId and date(o.effectiveDate) <= date(:effectiveDate) and o.isPrematureWithdraw = 1 order by o.effectiveDate desc";
		else
			sql = "SELECT o FROM WithdrawPenaltyMaster o where o.productConfigurationId = :productConfigurationId and date(o.effectiveDate) <= date(:effectiveDate) and o.isPrematureWithdraw = 0 order by o.effectiveDate desc";
		
		Query query = em.createQuery(sql);
		query.setParameter("effectiveDate", DateService.getCurrentDate());
		query.setParameter("productConfigurationId", productConfigurationId);
		
		List lst =  query.getResultList();
		if(lst!=null && lst.size()>0)
			return (WithdrawPenaltyMaster)lst.get(0);
		else
			return null;

	}
	
	@SuppressWarnings("unchecked")
	public WithdrawPenaltyTenureBased getTenureBasedWithdrawPenalty(Long withdrawPenaltyMasterId) {

		String sql = "SELECT o FROM WithdrawPenaltyTenureBased o where o.withdrawPenaltyMasterId=:withdrawPenaltyMasterId";

		Query query = em.createQuery(sql);
		query.setParameter("withdrawPenaltyMasterId", withdrawPenaltyMasterId);
		
		List lst =  query.getResultList();
		if(lst!=null && lst.size()>0)
			return (WithdrawPenaltyTenureBased)lst.get(0);
		else
			return null;
	}
	
	public List<Long> getByDates(Date date) {
		// SELECT distinct o.effectiveDate FROM WithdrawPenaltyMaster o order by
		// o.effectiveDate

		Query query = em
				.createQuery("select distinct o.productConfigurationId  FROM WithdrawPenaltyMaster o where o.effectiveDate = :effectiveDate");
		query.setParameter("effectiveDate", date);
		@SuppressWarnings("unchecked")
		List<Long> accDetailsList = query.getResultList();

		return accDetailsList;
	}
	
	
	@SuppressWarnings("unchecked")
	public WithdrawPenaltyAmountBased getAmountBasedWithdrawPenalty(Long withdrawPenaltyMasterId) {

		String sql = "SELECT o FROM WithdrawPenaltyAmountBased o where o.withdrawPenaltyMasterId=:withdrawPenaltyMasterId";

		Query query = em.createQuery(sql);
		query.setParameter("withdrawPenaltyMasterId", withdrawPenaltyMasterId);
		
		List lst =  query.getResultList();
		if(lst!=null && lst.size()>0)
			return (WithdrawPenaltyAmountBased)lst.get(0);
		else
			return null;
	}
	
	@Override
	@Transactional
	public WithdrawPenaltyDetails saveWithdrawPenaltyDetails(WithdrawPenaltyDetails withdrawPenaltyDetails) {
		em.persist(withdrawPenaltyDetails);
		em.flush();
		return withdrawPenaltyDetails;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<WithdrawPenaltyDetails> getWithdrawPenalyDetails(Long withdrawPenaltyMasterId) {

		String sql = "SELECT o FROM WithdrawPenaltyDetails o where o.withdrawPenaltyMasterId = :withdrawPenaltyMasterId";
		
		Query query = em.createQuery(sql);
		query.setParameter("withdrawPenaltyMasterId", withdrawPenaltyMasterId);
		
		return query.getResultList();

	}
	
	
	

}
