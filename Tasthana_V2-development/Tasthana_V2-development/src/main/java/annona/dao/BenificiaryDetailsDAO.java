package annona.dao;

import java.util.List;

import annona.domain.BenificiaryDetails;
import annona.form.DepositHolderForm;

public interface BenificiaryDetailsDAO 
 {

	/**
	 * Method to save benificiary  account details
	 */
	public void insertAccountDetails(BenificiaryDetails benificiarydetails);
	
	public void updateBeneficiary(BenificiaryDetails benificiarydetails);
	
	public List<BenificiaryDetails> getBeneficiaryDetails(Long reverseEMIDepositId);
	
	public List<BenificiaryDetails> getActiveBeneficiary(Long depositId);
	
	public BenificiaryDetails findById(Long id);
	
	public List<DepositHolderForm> getReverseEmiBenificiaryDetailsByDepositId(Long depositId);
 }
