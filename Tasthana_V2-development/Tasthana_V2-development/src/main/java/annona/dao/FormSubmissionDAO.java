package annona.dao;

import java.util.List;

import annona.domain.AccountDetails;
import annona.domain.FormSubmission;
import annona.form.DepositForm;

public interface FormSubmissionDAO 
 {

	public void insertFormSubmittedFor15HAnd15H(FormSubmission formSubmission);
	public FormSubmission findcustomerId(Long customerId);
	public Boolean isFormSubmitted(Long customerId, String financialYear);
	public void update(FormSubmission formSubmission);
	public List<DepositForm> getDepositList(String accountNumber);
 }
