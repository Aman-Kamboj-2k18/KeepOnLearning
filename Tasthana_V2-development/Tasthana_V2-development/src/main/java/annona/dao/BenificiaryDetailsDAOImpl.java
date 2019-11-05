package annona.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import annona.domain.BenificiaryDetails;
import annona.form.DepositHolderForm;

@Repository
public class BenificiaryDetailsDAOImpl implements BenificiaryDetailsDAO {

	@PersistenceContext
	private EntityManager em;

	
	/**
	 * Method for entity manager
	 * 
	 * @return
	 */
	
	 
	public EntityManager entityManager() {
		EntityManager em = new BenificiaryDetailsDAOImpl().em;

		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Override
	public BenificiaryDetails findById(Long id){
		return em.find(BenificiaryDetails.class, id);
	}
	
	public void insertAccountDetails(BenificiaryDetails benificiarydetails) {
		em.persist(benificiarydetails);
	}
	
	public void updateBeneficiary(BenificiaryDetails benificiarydetails){
		em.merge(benificiarydetails);
	}
	
	

	@Override
	 public List<BenificiaryDetails> getBeneficiaryDetails(Long reverseEMIDepositId) {

		 Query query = em.createQuery("SELECT o FROM BenificiaryDetails o WHERE o.depositId=:reverseEMIDepositId ORDER BY id");
		 query.setParameter("reverseEMIDepositId", reverseEMIDepositId);
			
		 List lst = query.getResultList();
		 if(lst == null)
			 return null;
		 else
			 return (List<BenificiaryDetails>)lst;

	 }
	
	@Override
	 public List<BenificiaryDetails> getActiveBeneficiary(Long depositId) {

		 Query query = em.createQuery("SELECT o FROM BenificiaryDetails o WHERE o.depositId=:depositId and o.isActive=1 ORDER BY id");
		 query.setParameter("depositId", depositId);
			
		 List<BenificiaryDetails> lst = query.getResultList();
		 if(lst.size()>0)
			 return lst;
		 else
			 return null;

	 }
	
	public List<DepositHolderForm> getReverseEmiBenificiaryDetailsByDepositId(Long depositId) {
		  Query query = em.createNativeQuery(
		    "SELECT d.id as depositId,b.depositHolderId,b.benificiaryName,b.benificiaryAccountNumber,"
		    + " b.amountToTransfer from Deposit d inner join BenificiaryDetails b on d.id=b.depositId"
		    + " where d.depositCategory='REVERSE-EMI' and b.depositId =:depositId");

		  query.setParameter("depositId", depositId);

		  @SuppressWarnings("unchecked")
		  List<Object[]> benificiaryList = query.getResultList();
		  List<DepositHolderForm> benificiaryDetails = new ArrayList<DepositHolderForm>();

		  for (int i = 0; i < benificiaryList.size(); i++) {
		   DepositHolderForm holderForm = new DepositHolderForm();
		   BigInteger dId = (BigInteger) benificiaryList.get(i)[0];
		   Long emiDepositId =  dId.longValue();
		   holderForm.setDepositId(emiDepositId);
		   BigInteger dhId = (BigInteger) benificiaryList.get(i)[1];
		   Long depositHolderId =  dhId.longValue();
		   holderForm.setDepositHolderId(depositHolderId);
		   holderForm.setBeneficiaryName((String) benificiaryList.get(i)[2]);
		   holderForm.setBeneficiaryAccountNumber((String) benificiaryList.get(i)[3]);
		   holderForm.setAmountToTransfer((Double) benificiaryList.get(i)[4]);
		   
		   benificiaryDetails.add(holderForm);
		   holderForm = null;
		  }
		  return benificiaryDetails;
		 }
	
}
