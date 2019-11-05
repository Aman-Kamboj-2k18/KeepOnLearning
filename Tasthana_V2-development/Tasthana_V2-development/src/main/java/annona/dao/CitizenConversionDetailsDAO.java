package annona.dao;

import annona.domain.CitizenConversionDetails;

public interface CitizenConversionDetailsDAO {
	
    public void save(CitizenConversionDetails citizenConversionDetails);
	
	public void update(CitizenConversionDetails citizenConversionDetails);
	
	public CitizenConversionDetails getCitizenConversionDetailsById(Long id);

}
