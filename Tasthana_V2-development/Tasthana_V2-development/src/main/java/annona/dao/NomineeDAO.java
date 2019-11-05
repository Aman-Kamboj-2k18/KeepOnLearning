package annona.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.TypedQuery;

import annona.domain.DepositHolderNominees;
import annona.domain.DepositRates;

public interface NomineeDAO {

	 public DepositHolderNominees saveNominee(DepositHolderNominees nominee);
	 public DepositHolderNominees accountHoderNominee(Long depositHolderId);
	 public List<DepositHolderNominees> getAllNominees(Long depositId);
}
