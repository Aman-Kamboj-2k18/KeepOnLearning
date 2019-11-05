package annona.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.LogDetails;
import annona.domain.LoginDate;
import annona.services.DateService;

@Repository
public class LogDetailsDAOImpl implements LogDetailsDAO{

	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public void save(LogDetails depositRatesImport) {
		em.persist(depositRatesImport);
	}

	@Override
	public TypedQuery<LogDetails> getData(String userName) {
		TypedQuery<LogDetails> q = em.createQuery(
				"SELECT o FROM DepositRatesImport AS o WHERE o.personName = :userName or o.headName =:userName",
				LogDetails.class);
		q.setParameter("userName", userName);
		return q;
	}
	
	 @Transactional
	 public void saveLoginDate(LoginDate loginDate){
	  em.persist(loginDate);
	 }
	 
	 public Date findLoginDate(){
		 Object obj  = null;
		 try
		 {
		     Query query = em.createQuery("SELECT o.loginDate FROM LoginDate o");
		     obj = query.getSingleResult();
		 }
		 catch(NoResultException exp)
		 {
			 return DateService.getCurrentDate();
		 }
		 
	     return (Date)obj;
	 }
	 
	 public LoginDate getLoginDate(){
	     Query query = em.createQuery("SELECT o FROM LoginDate o");
	    List<LoginDate> list = query.getResultList();
	    if(list!=null && list.size()>0)
	     return list.get(0);
	    else 
	    	return null;
	   
	 }
	 
	  @Transactional
	  public void updateLoginDate(LoginDate loginDate){
	   em.merge(loginDate);
	      em.flush();  
	  }
}
