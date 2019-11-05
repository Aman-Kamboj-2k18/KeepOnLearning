package annona.dao;

import java.util.List;

import annona.domain.EndUser;
import annona.domain.Organisation;


public interface OrganisationDAO {

	public Organisation insertUniqueOrganisation(Organisation organisation);
	
	public List<Organisation> getAllList();
	
	public Organisation getOrganizationByUsername(String username);

}
