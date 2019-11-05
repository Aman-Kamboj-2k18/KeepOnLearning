package annona.dao;

import java.util.List;


import annona.domain.OrnamentsMaster;



public interface OrnamentsMasterDAO {

	public void save(OrnamentsMaster ornamentsMaster);

	public void update(OrnamentsMaster ornamentsMaster);

	public List<OrnamentsMaster> getAllOrnamentsMasterDetails();
	
	public OrnamentsMaster getOrnamentsMastertById(Long id);
	
	public Long getCountOfOrnament(String ornament);
	
	public Boolean getOrnamentExist(String ornament, Long id);

}
