package annona.dao;

import java.util.List;

import annona.domain.OverdraftIssue;
import annona.domain.OverdraftPayment;
import annona.domain.OverdraftWithdrawMaster;
import annona.domain.UnSuccessfulOverdraft;
import annona.form.DepositForm;

public interface OverdraftIssueDAO {
	
	public void insertOverdraftIssue(OverdraftIssue overdraftIssue);
	
	public void insertOverdraftPayment(OverdraftPayment overdraftPayment);
	public void insertOverdraftWithdrawMaster(OverdraftWithdrawMaster overdraftWithdrawMaster);
	public void updateOverdraftIssue(OverdraftIssue overdraftIssue);
	public void updateOverdraftPayment(OverdraftPayment overdraftPayment);
	
	public void updateOverdraftWithdrawMaster(OverdraftWithdrawMaster overdraftWithdrawMaster);
	
	public OverdraftIssue getOverdraftIssueDetails(String overdraftNumber);
	public OverdraftIssue getOverdraftIssueDetailsById(Long id);
	
	public OverdraftIssue getOverdraftIssueDetails();
	
	public List<OverdraftIssue> getOverdraftIssueDetailsByDepositId(Long depositId);
	
	public List<OverdraftIssue> getAllActiveOverdrafts();
	
	public List<OverdraftIssue> getAllOverdrafts();
	
	public List<OverdraftIssue> getMyOverdrafts(List<DepositForm> depositList);
	
	public void insertInUnSuccessfulOverdraft(UnSuccessfulOverdraft unsuccessfulOverdraft); 
	
	public List<OverdraftWithdrawMaster> findByOverdraftIdInWithdrawMaster(Long overdraftId);
	public List<OverdraftPayment> findByOverdraftIdInOverdraftPayment(Long overdraftId);
	
}
