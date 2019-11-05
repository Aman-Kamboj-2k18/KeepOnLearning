package annona.dao;

import java.util.List;

import annona.domain.ModeOfPayment;



public interface ModeOfPaymentDAO {
	
public void save(ModeOfPayment modeOfPayment);
	
	public void update(ModeOfPayment modeOfPayment);
	
	public List<ModeOfPayment> getAllModeOfPaymentDetails();
	
	public List<ModeOfPayment> getAllModeOfPaymentEmployee();
	
	public List<ModeOfPayment> getAllModeOfPaymentCustomer();
	
	public ModeOfPayment getModeOfPaymentById(Long id);
	
	public ModeOfPayment getModeOfPayment(String paymentMode);
	
	public Long getCountOfPaymentMode(String paymentMode);
	
	public Boolean getModeOfPaymentExist(String paymentMode, Long id);

}
