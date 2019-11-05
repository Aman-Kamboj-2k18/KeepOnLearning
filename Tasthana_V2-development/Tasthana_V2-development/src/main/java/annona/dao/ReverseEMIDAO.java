package annona.dao;

import java.util.List;

import annona.domain.BenificiaryDetails;
import annona.domain.ReverseEMI;
import annona.domain.ReverseEMIPayoffDetails;


public interface ReverseEMIDAO {
	
	public List<ReverseEMI> getReverseEMIList(Long depositId);
	
	/**
	 * Method to save ReverseEMI
	 */
	public ReverseEMI insertReverseEMI(ReverseEMI reverseEMI);
	
	
	public ReverseEMIPayoffDetails insertReverseEMIPayoffDetails(ReverseEMIPayoffDetails reverseEMIPayoffDetails);
	
	
}
