package annona.dao;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.DepositSummaryHolderWise;
import annona.domain.DepositHolderWiseDistribution;
import annona.domain.Interest;
import annona.form.DepositForm;

@Repository
public class DepositHolderWiseConsolidatedInterestDAOImpl implements DepositHolderWiseConsolidatedInterestDAO {

	@PersistenceContext
	EntityManager em;

	
	 public  DepositSummaryHolderWise getTotalInterestByDepositHoldeId(Long depositHolderId){

		  // Query query = em.createQuery("SELECT o FROM DepositHolderWiseConsolidatedInterest o where o.depositHolderId=:depositHolderId");
		   Query query = em.createQuery("SELECT o FROM DepositSummaryHolderWise o where o.depositHolderId=:depositHolderId");
		   query.setParameter("depositHolderId", depositHolderId);
		   
		   List<DepositSummaryHolderWise> intList= query.getResultList();
		   if(intList.size()>0){
		    return intList.get(0);
		   }
		   else
		    return null;
		   
		  }
	 
	 
	 //To get the total interest from DepositHolderWiseConsolidatedInterest join with deposit holder and customer details.
	 @Override
	  public DepositForm getTotalInterestAndCustomerNameByDepositHoldeId(Long  depositHolderId) {

	   EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("persistenceUnit");
	   EntityManager antmgr = emfactory.createEntityManager();

	   String sql = "SELECT dh.customerId,dh.id as depositHolderId,c.customerName,dwc.totalInterest from"
	     + " DepositHolderWiseConsolidatedInterest dwc  inner join depositholder dh on dwc.depositId = dh.depositid inner join"
	     + " customerdetails c on dh.customerId = c.id where dwc.depositHolderId=:depositHolderId";

	   Query depositQuery = antmgr.createNativeQuery(sql);
	   depositQuery.setParameter("depositHolderId", depositHolderId);

	   @SuppressWarnings("unchecked")
	   List<Object[]> depositHolderWiseConsolidatedInterestList = depositQuery.getResultList();

	   DepositForm depositForm = new DepositForm();
	   for (int i = 0; i < depositHolderWiseConsolidatedInterestList.size(); i++) {
	    
	    Long cusId = Long.parseLong(depositHolderWiseConsolidatedInterestList.get(i)[0].toString());
	    Long depositHolderid = Long.parseLong(depositHolderWiseConsolidatedInterestList.get(i)[1].toString());
	    String customerName = (String) depositHolderWiseConsolidatedInterestList.get(i)[2];
	    Double totalInterest = (Double) depositHolderWiseConsolidatedInterestList.get(i)[3];
	    
	    depositForm.setCustomerId(cusId);
	    depositForm.setDepositHolderId(depositHolderid);
	    depositForm.setCustomerName(customerName);
	    depositForm.setTotalInterest(totalInterest);
	    
	    break;
	   }

	   return depositForm;

	  }
	
	 	
	 public Double getConsolidatedInterest(Long depositId, Long customerId) {
			
			HashMap<String, Double> totalInterest = new HashMap<>(); 
			
			Query query = em.createQuery("select Sum(o.interestAmount) from Interest o where o.depositId=:depositId and o.customerId=:customerId");
			query.setParameter("depositId", depositId);
			query.setParameter("customerId", customerId);
			Object interestAmount = query.getSingleResult();
			if(interestAmount != null )
			{
				return Double.parseDouble(interestAmount.toString());
			}
			else
				return 0d;
		}
}
