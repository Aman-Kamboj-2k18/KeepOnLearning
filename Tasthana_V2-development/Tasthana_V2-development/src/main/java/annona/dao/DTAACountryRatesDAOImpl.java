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

import annona.domain.AccountDetails;
import annona.domain.BankPaid;
import annona.domain.Customer;
import annona.domain.DTAACountry;
import annona.domain.DTAACountryRates;
import annona.domain.DepositRates;
import annona.form.BankPaymentForm;
import annona.form.CustomerForm;

@Repository
public class DTAACountryRatesDAOImpl implements DTAACountryRatesDAO {

	@PersistenceContext
	private EntityManager em;

	public List<DTAACountry> getDTAACountryList() {

		Query query = em.createQuery(
				"SELECT o FROM DTAACountry o");
		
		return query.getResultList();
	}

	public DTAACountry getDTAACountry(String country) {
		
		// get the countryId
		Query query = em.createQuery("SELECT o FROM DTAACountry o where o.country=:country");		
		query.setParameter("country", country);
		List lstCountry = query.getResultList();
		
		if(lstCountry!=null && lstCountry.size()>0)
			return ((List<DTAACountry>)lstCountry).get(0);
		else
			return null;
	}

	public DTAACountry getDTAACountry(Long countryId) {
		
		// get the countryId
		Query query = em.createQuery("SELECT o FROM DTAACountry o where o.id=:countryId");		
		query.setParameter("countryId", countryId);
		List lstCountry = query.getResultList();
		
		if(lstCountry!=null && lstCountry.size()>0)
			return ((List<DTAACountry>)lstCountry).get(0);
		else
			return null;
	}
	
	public List<DTAACountryRates> getDTAACountryRatesList() {
	
		  EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
		  EntityManager antmgr = emfactory.createEntityManager();
		  
		  String strSql = "select dr.id, dr.country, dr.dtaaCountryId, dr.effectiveDate, dr.taxRate " +
				  	" from DTAACountryRates dr " + 
					" inner join (" + 
					" select dtaaCountryid, max(effectiveDate) as effectiveDate " + 
					" from DTAACountryRates " + 
					" group by dtaaCountryId " + 
					") dcr on dr.dtaaCountryId = dcr.dtaaCountryId and dr.effectiveDate = dcr.effectiveDate";
		Query query = antmgr.createNativeQuery(strSql);
		
		@SuppressWarnings("unchecked")
		List<Object[]> lst = query.getResultList();
		
		List<DTAACountryRates> lstRateList = new ArrayList<>();
		if(lst!=null && lst.size()>0)
		{
			for (int i = 0; i < lst.size(); i++) {
				DTAACountryRates dtaaRates = new DTAACountryRates();
				dtaaRates.setId(Long.parseLong(lst.get(i)[0].toString()));
				dtaaRates.setCountry(lst.get(i)[1].toString());
				dtaaRates.setDtaaCountryId(Long.parseLong(lst.get(i)[2].toString()));
				dtaaRates.setEffectiveDate((Date)lst.get(i)[3]);
				dtaaRates.setTaxRate(Float.parseFloat(lst.get(i)[4].toString()));
				lstRateList.add(dtaaRates);
			}

		}
		return lstRateList;
	}

	@Transactional
	public DTAACountry saveDTAACountry(DTAACountry dtaaCountry) {
		em.persist(dtaaCountry);
		return dtaaCountry;
	}
	
	public Float getDTAATaxRate(String country) {
		
		// get the countryId
		Query query = em.createQuery("SELECT o FROM DTAACountry o where o.country=:country");		
		query.setParameter("country", country);
		Long dtaaCountryId = null;	
		List lstCountry = query.getResultList();
		
		if(lstCountry!=null && lstCountry.size()>0)
			dtaaCountryId = ((List<DTAACountry>)lstCountry).get(0).getId();
		else
			return null;
		
		Query query1 = em.createQuery(
				"SELECT o FROM DTAACountryRates o where o.dtaaCountryId=:dtaaCountryId");
		
		query1.setParameter("dtaaCountryId", dtaaCountryId);	
		List lstCountryRates = query1.getResultList();

		if(lstCountryRates!=null && lstCountryRates.size()>0)
			return ((List<DTAACountryRates>)lstCountryRates).get(0).getTaxRate();
		else
			return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public DTAACountryRates getDTAATaxRate(Long dtaaCountryId, Date effectiveDate) {
		
		Query query = em.createQuery(
				"SELECT o FROM DTAACountryRates o where o.dtaaCountryId=:dtaaCountryId and date(o.effectiveDate)=date(:effectiveDate)");
			
		query.setParameter("dtaaCountryId", dtaaCountryId);
		query.setParameter("effectiveDate", effectiveDate);
		List lstCountryRates = query.getResultList();		
		if(lstCountryRates!=null && lstCountryRates.size()>0)
			return ((List<DTAACountryRates>)lstCountryRates).get(0);
		else
			return null;
	}

	public DTAACountryRates saveDTAACountryRates(DTAACountryRates dtaaCountryRates) {
		em.persist(dtaaCountryRates);
		return dtaaCountryRates;
	}
	
	public DTAACountryRates updateDTAACountryRates(DTAACountryRates dtaaCountryRates) {
		em.merge(dtaaCountryRates);
		em.flush();
		return dtaaCountryRates;
	}
	
}
