package annona.dao;


import java.util.Date;
import java.util.List;

import annona.domain.WithdrawDeposit;



public interface WithdrawDepositDAO 
 {

	public List<WithdrawDeposit> withdrawDepositListByDepositId(Long depositId);
	
	public Double getTotalWithdraw(Long depositId, Date fromDate, Date toDate);
	
  }
