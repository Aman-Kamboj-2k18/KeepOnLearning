package annona.dao;

import java.util.List;

import annona.domain.BankPaid;
import annona.domain.BankPaidDetails;
import annona.domain.BankPayment;
import annona.domain.BankPaymentDetails;
import annona.domain.DDCheque;
import annona.domain.DepositHolderNominees;
import annona.form.BankPaymentForm;



public interface BankPaymentDAO {

	public BankPayment saveBankPayment(BankPayment bankPayment);
	
	public BankPayment saveBankPaymentForNominee(BankPayment bankPayment);
	
	public BankPaymentDetails saveBankPaymentDetails(BankPaymentDetails bankPaymentDetails);
	
	public BankPayment getBankPayment(Long depositId);
	 
	public BankPaymentDetails getBankPaymentByDepositHolderId(Long depositHolderId);
	
	public BankPaid saveBankPaid(BankPaid bankPaid);
	
	public BankPaidDetails saveBankPaidDetails(BankPaidDetails bankPaidDetails);
	
	public void updateBankPaymentDetails(BankPaymentDetails bankPaymentDetails);
	
	public BankPaymentDetails getBankPaymentDetails(Long bankPaymentDetailsId);
	
	public List<BankPaymentForm> getBankPaymentDetailsByAccountNo(String accountNumber);
	
	//public BankPaymentDetails findByDepositId(Long depositId);
	//public BankPaymentDetails findByDepositHolderId(Long depositHolderId);
	
	public BankPaid findByBankPaymentId(Long bankPaymentId);
	
	public DDCheque saveDDCheque(DDCheque cheque);
}
