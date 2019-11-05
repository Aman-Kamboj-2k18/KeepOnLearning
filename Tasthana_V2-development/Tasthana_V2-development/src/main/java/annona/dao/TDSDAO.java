package annona.dao;

import java.util.Date;
import java.util.List;

import annona.domain.DepositTDS;
import annona.domain.DepositWiseCustomerTDS;
import annona.domain.TDS;


public interface TDSDAO 
 {
	public TDS insert(TDS tds);
	
	public TDS update(TDS tds);
	
	public List<TDS> getByDepositId(Long depositId);
	
	public void deleteByDepositId(Long depositId);
	
	public TDS deleteTDSFromCurrentDate(Long depositId);
	
	public List<TDS> getTDSByQuarter(Long depositId, Integer quarter, Integer year);
	
	public TDS getTDSByMonth(Long customerId, Integer month, Integer year);
	
	public List<TDS> getTDSDeductedByFinancialYear(Long depositId, String financialYear);
	
	public DepositWiseCustomerTDS insertCustomerTDS(DepositWiseCustomerTDS customrTDS);
	
	public DepositWiseCustomerTDS updateCustomerTDS(DepositWiseCustomerTDS customrTDS);
	
	public List<DepositWiseCustomerTDS> getCustomerTDSReportByDepositId(Long customerId);
	
	public DepositTDS getConsolidatedTDS(Long depositId, String financialYear, Date date);
	
	public Double getTDS(Long customerId, String financialYear);
	
	public Double getCustomerTDSForDeposit(Long customerId, Long depositId, String financialYear);
	
	public Double getCustomerTDSForDeposit(Long customerId, Long depositId, Long tdsId, String financialYear);
	
	public List<Object[]> getAllCustomerTds();
	
	public DepositTDS insertDepositTDS(DepositTDS depositTDS);
	
	public TDS getCustomerTDS(Long customerId, String financialYear, Date dt);
	
	public TDS getLastTDSDeducted(Long customerId, String financialYear);
	
	public Double getTotalPartlyCalculatedTDS(Long customerId, Date fromDate, Date toDate);	
	
	public DepositTDS getConsolidatedTDSForDeposit(Long depositId, List<Long> tdsIds);
	
	public void updateDeductTDSInDepositWiseCustomerTDS(Long depositId);
	
	public DepositTDS getDepositTDS(Long depositId, Date date);
	
	public Double getTotalTDS(Long depositId, Date fromDate, Date toDate);
	
  }
