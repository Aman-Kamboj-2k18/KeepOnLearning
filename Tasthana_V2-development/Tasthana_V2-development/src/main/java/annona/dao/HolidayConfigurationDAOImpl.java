package annona.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.HolidayConfiguration;
import annona.domain.Rates;
import annona.services.DateService;

/**
 * 
 * @author Sachin
 *
 */
@Repository
public class HolidayConfigurationDAOImpl implements HolidayConfigurationDAO{
	/**
	 * @since 2019
	 */
	//@Autowired
	//private HolidayConfigurationRepository holidayConfigurationRepository;
	
	@PersistenceContext
	EntityManager em;

	/**
	 * @param configuration
	 * @return {@link HolidayConfiguration}
	 * save HolidayConfiguration Data
	 * 
	 */
	@Override
	@Transactional
	public void save(HolidayConfiguration configuration) {
		em.persist(configuration);
		em.flush();
	//	configuration = em.find(HolidayConfiguration.class, configuration.getId());
		//return configuration;
	}

	@Override
	public HolidayConfiguration update(HolidayConfiguration configuration) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void delete(int year, String branchCode,String state) {
		@SuppressWarnings("unchecked")
		List<HolidayConfiguration> configurations = em.createQuery("from HolidayConfiguration where year = :year and branchCode =:branchCode and state=:state").setParameter("year", year).setParameter("branchCode", branchCode).setParameter("state", state).getResultList();
		for (HolidayConfiguration holidayConfiguration : configurations) {
			em.remove(holidayConfiguration);

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HolidayConfiguration> isPresent(int year, String branchCode,String state) {
		Query query = em.createQuery("from HolidayConfiguration h  where h.year = :year and h.branchCode=:branchCode and h.state=:state");
		query.setParameter("year", year);
		query.setParameter("branchCode", branchCode);
		query.setParameter("state", state);
		return query.getResultList();

	}
	
	@Override
	@Transactional
	public void delete(int year,String state) {
		@SuppressWarnings("unchecked")
		List<HolidayConfiguration> configurations = em.createQuery("from HolidayConfiguration where year = :year and state=:state and branchCode IS NULL").setParameter("year", year).setParameter("state", state).getResultList();
		for (HolidayConfiguration holidayConfiguration : configurations) {
			em.remove(holidayConfiguration);

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HolidayConfiguration> isPresent(int year, String state) {
		Query query = em.createQuery("from HolidayConfiguration h  where h.year = :year  and h.state=:state and branchCode IS NULL");
		query.setParameter("year", year);
		query.setParameter("state", state);
		return query.getResultList();

	}
	@Override
	public HolidayConfiguration get(int year) {
		Query query = em.createQuery("from HolidayConfiguration h  where h.year = :year");
		query.setParameter("year", year);
		List list = query.getResultList();
	
		if (list != null && list.size() > 0)
			return (HolidayConfiguration) list.get(0);
		else
			return null;
		

	}
	
	
	@Override
	public HolidayConfiguration getConfigurationByYearAndState(int year, String state) {
		Query query = em.createQuery("from HolidayConfiguration h  where h.year = :year and h.state= :state and branchCode IS NULL");
		query.setParameter("year", year);
		query.setParameter("state", state);
		List list = query.getResultList();
	
		if (list != null && list.size() > 0)
			{
			return (HolidayConfiguration) list.get(0);}
		else
		{	
		Query query1 = em.createQuery("from HolidayConfiguration h  where h.year = :year and state=''");
		query1.setParameter("year", year);
		
		list = query1.getResultList();
	
		if (list != null && list.size() > 0)
			{return (HolidayConfiguration) list.get(0);}
		else
		{	return null;
		}
		}
		

	}
	
	@Override
	public HolidayConfiguration getConfigurationByBranchAndState(int year, String state, String branch) {
		Query query = em.createQuery("from HolidayConfiguration h  where h.year = :year and h.state= :state and h.branchCode= :branch");
		query.setParameter("year", year);
		query.setParameter("state", state);
		query.setParameter("branch", branch);
		List list = query.getResultList();
	
		if (list != null && list.size() > 0)
			{return (HolidayConfiguration) list.get(0);}
		else
		{	
		Query query1 = em.createQuery("from HolidayConfiguration h  where h.year = :year and state= :state and branchCode IS NULL");
		query1.setParameter("year", year);
		query1.setParameter("state", state);
		
		list = query1.getResultList();
	
		if (list != null && list.size() > 0)
			{return (HolidayConfiguration) list.get(0);}
		else
		{	return null;
		}
		}
	}

	@Override
	public HolidayConfiguration getHolidayConfiguration(Date maturityDate) {
		
		//Date fromDate = maturityDate;
		//Date toDate = DateService.addDays(maturityDate, 10);
		
		// between :date and :ceilDate"
		// TypedQuery<HolidayConfiguration> q = em.createQuery("SELECT a FROM HolidayConfiguration a JOIN a.date p WHERE p=:maturityDate", HolidayConfiguration.class);
		// Query q = em.createQuery("SELECT a FROM HolidayConfiguration a JOIN a.date p WHERE p between :fromDate and :toDate");
		Query q = em.createQuery("SELECT a FROM HolidayConfiguration a JOIN a.date p WHERE p=:maturityDate");
		q.setParameter("maturityDate", maturityDate);
		//q.setParameter("toDate", toDate);
		@SuppressWarnings("unchecked")
		List holidayConfigurationList = q.getResultList();

		if(holidayConfigurationList !=null && holidayConfigurationList.size()>0)
			return (HolidayConfiguration)holidayConfigurationList.get(0);
		else
			return null;
	}
	
	@Override
	public HolidayConfiguration getHolidayConfiguration(Date maturityDate, String branchCode) {
		
		//Date fromDate = maturityDate;
		//Date toDate = DateService.addDays(maturityDate, 10);
		
		// between :date and :ceilDate"
		// TypedQuery<HolidayConfiguration> q = em.createQuery("SELECT a FROM HolidayConfiguration a JOIN a.date p WHERE p=:maturityDate", HolidayConfiguration.class);
		// Query q = em.createQuery("SELECT a FROM HolidayConfiguration a JOIN a.date p WHERE p between :fromDate and :toDate");
		Query q = em.createQuery("SELECT a FROM HolidayConfiguration a JOIN a.date p WHERE p=:maturityDate and a.branchCode=:branchCode");
		q.setParameter("maturityDate", maturityDate);
		q.setParameter("branchCode", branchCode);
		
		@SuppressWarnings("unchecked")
		List holidayConfigurationList = q.getResultList();

		if(holidayConfigurationList !=null && holidayConfigurationList.size()>0)
			return (HolidayConfiguration)holidayConfigurationList.get(0);
		else
			return null;
	}
	
	@Override
	public HolidayConfiguration getHolidayConfigurationByState(Date maturityDate, String state) {
		
		Query q = em.createQuery("SELECT a FROM HolidayConfiguration a JOIN a.date p WHERE p=:maturityDate and a.branchcode=null and a.state=:state");
		q.setParameter("maturityDate", maturityDate);
		q.setParameter("state", state);
		
		@SuppressWarnings("unchecked")
		List holidayConfigurationList = q.getResultList();

		if(holidayConfigurationList !=null && holidayConfigurationList.size()>0)
			return (HolidayConfiguration)holidayConfigurationList.get(0);
		else
			return null;
	}
	
	@Override
	public List<Object> getHolidayConfigurationListForNext10Days(Date date, String branchCode) {
		
		Date fromDate = date;
		Date toDate = DateService.addDays(date, 10);
		
		String sql = "SELECT a FROM HolidayConfiguration a JOIN a.date p WHERE p>=:fromDate and  p<=:toDate and a.branchCode=:branchCode";
		
//		String sql = "SELECT holiday_dates from Holiday_Dates where holiday_dates>=:fromDate and holiday_dates<=:toDate";

		Query query = em.createNativeQuery(sql);
		query.setParameter("fromDate", date);
		query.setParameter("toDate", toDate);
		query.setParameter("branchCode", branchCode);
		
		return (List<Object>)query.getResultList();
	}
	
	@Override
	public List<Object> getStateWiseHolidayConfigurationListForNext10Days(Date date, String state) {
		
		Date fromDate = date;
		Date toDate = DateService.addDays(date, 10);
		
		String sql = "SELECT a FROM HolidayConfiguration a JOIN a.date p WHERE p>=:fromDate and  p<=:toDate and a.branchcode=null and a.state=:state";

		Query query = em.createNativeQuery(sql);
		query.setParameter("fromDate", date);
		query.setParameter("toDate", toDate);
		query.setParameter("state", state);
		
		return (List<Object>)query.getResultList();
	}
}
