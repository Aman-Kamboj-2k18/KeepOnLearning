package annona.form;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import annona.domain.AccountDetails;
import annona.domain.MultipleFundTransfer;

@Component
public class MultipleFundTransferForm {

		
    private List<MultipleFundTransfer> multipleFundTransferList;

		public List<MultipleFundTransfer> getMultipleFundTransferList() {
		return multipleFundTransferList;
	}

	public void setMultipleFundTransferList(List<MultipleFundTransfer> multipleFundTransferList) {
		this.multipleFundTransferList = multipleFundTransferList;
	}
	
}
