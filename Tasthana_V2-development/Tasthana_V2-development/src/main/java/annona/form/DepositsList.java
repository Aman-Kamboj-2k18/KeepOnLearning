package annona.form;

import java.util.List;

import org.springframework.stereotype.Component;

import annona.domain.Deposit;

@Component
public class DepositsList{
	
	List<Deposit> dList;

	public List<Deposit> getdList() {
		return dList;
	}

	public void setdList(List<Deposit> dList) {
		this.dList = dList;
	}


	
	
	
}