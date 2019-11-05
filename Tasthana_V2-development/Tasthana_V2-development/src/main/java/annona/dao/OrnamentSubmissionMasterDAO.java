package annona.dao;

import java.util.List;

import annona.domain.OrnamentSubmissionMaster;


public interface OrnamentSubmissionMasterDAO {
	
	public void save(OrnamentSubmissionMaster ornamentSubmissionMaster);

	public void update(OrnamentSubmissionMaster ornamentSubmissionMaster);

	public List<OrnamentSubmissionMaster> getAllOrnamentSubmissionMasterDetails();
	
	public OrnamentSubmissionMaster getOrnamentSubmissionMastertById(Long id);
	
	

}
