package annona.dao;

import java.util.List;

import annona.domain.OrnamentSubmissionDetails;

public interface OrnamentSubmissionDetailsDAO {
	
	public void save(OrnamentSubmissionDetails ornamentSubmissionDetails);

	public void update(OrnamentSubmissionDetails ornamentSubmissionDetails);

	public List<OrnamentSubmissionDetails> getAllOrnamentSubmissionrDetails();
	
	public OrnamentSubmissionDetails getOrnamentSubmissionDetailstById(Long id);

}
