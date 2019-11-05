package annona.dao;

import java.util.List;

import annona.domain.Deposit;
import annona.domain.DepositHolder;
import annona.form.DepositHolderForm;

public interface DepositHolderDAO {

	public DepositHolder saveDepositHolder(DepositHolder depositHolder);

	public Deposit updateDeposit(Deposit deposit);

	public List<DepositHolder> getDepositHolders(Long depositId);

	public DepositHolder getAutoDepositHolder(Long depositId);

	public List<Object[]> getAllDeposit(Long userId);

	public List<Object[]> getOpenDeposit(Long userId);

	public List<Object[]> getAllOpenDeposits(Long userId);

	public DepositHolder findByCustomerId(Long customerId);

	public DepositHolder findById(Long id);

	public void updateDepositHolder(DepositHolder depositHolder);

	public DepositHolder getDepositHoldersByID(Long id);

	public DepositHolder getDepositHolder(Long depositId, Long customerId);

	public Double getReverseEMIAmount(Long depositId);

	public List<DepositHolderForm> getDepositHoldersName(Long depositId);

	public Deposit getDepositByDepositIdAndCustId(Long depositId);

	public List<DepositHolderForm> getReverseEmiDepositByDepositId(Long depositId);

	public Deposit getDepositByAccountNumber(String accountNumber);

	public List<DepositHolderForm> getUnpaidDepositHolders(Long depositId);

	public Long getOpenDepositCount(String toDate, String fromDate,Long branchId);

	public Long getPrematuredDepositCount(String toDate, String fromDate,Long branchId);

	public Long getMaturedDepositCount(String toDate, String fromDate,Long branchId);

	public Double getTotalPaymentReceived(String toDate, String fromDate,Long branchId);

	public Double getTotalWithdrawAmount(String toDate, String fromDate,Long branchId);
	public Double getTotalIntrest(String toDate, String fromDate, Long branchId);
	public Double getTotalAmountPaidByBank(String toDate, String fromDate, Long branchId);

}
