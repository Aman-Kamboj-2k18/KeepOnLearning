package annona.form;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class WithdrawPenaltyFormList {

	private List<WithdrawPenaltyForm> withdrawPenaltyFormList;

	public List<WithdrawPenaltyForm> getWithdrawPenaltyFormList() {
		return withdrawPenaltyFormList;
	}

	public void setWithdrawPenaltyFormList(List<WithdrawPenaltyForm> withdrawPenaltyFormList) {
		this.withdrawPenaltyFormList = withdrawPenaltyFormList;
	}

}


