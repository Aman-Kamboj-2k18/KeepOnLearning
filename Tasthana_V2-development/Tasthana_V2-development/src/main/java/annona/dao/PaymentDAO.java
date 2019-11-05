package annona.dao;

import java.util.Date;
import java.util.List;
import annona.domain.Payment;
import annona.form.BankPaymentForm;
import annona.form.DepositForm;


public interface PaymentDAO {
	
	public List<DepositForm> paymentAccountNumber(String accountNumber);
	
	/**
	 * Method to save Deposit
	 */
	public Payment insertPayment(Payment payment);

	/**
	 * Method to save PaymentDistribution
	 */
	public void insertPaymentDistribution(Payment payment);
	
	public List<BankPaymentForm> customerListForBankPayment(String accountNumber);	
	
	public Double getTotalPayment(Long depositId, Date fromDate, Date toDate);
	
	public Payment getLastPaymentByDepositId(Long depositId);
	
	public List<Payment> getPaymentDetails(Long depositId, Date fromDate, Date toDate);
	
	public Payment updatePayment(Payment payment);
	
	public List<DepositForm> withdrawAccountNumber(String accountNumber);
	
}
