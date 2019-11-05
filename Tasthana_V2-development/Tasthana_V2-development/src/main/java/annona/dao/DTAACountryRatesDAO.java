package annona.dao;


import java.util.Date;
import java.util.List;

import annona.domain.DTAACountry;
import annona.domain.DTAACountryRates;

public interface DTAACountryRatesDAO 
 {

	public DTAACountry saveDTAACountry(DTAACountry dtaaCountry);
	
	public DTAACountry getDTAACountry(Long countryId);
	
	public DTAACountry getDTAACountry(String country);
	
	public List<DTAACountry> getDTAACountryList();
	
	public List<DTAACountryRates> getDTAACountryRatesList();
	
	public Float getDTAATaxRate(String country);
	
	public DTAACountryRates getDTAATaxRate(Long dtaaCountryId, Date effectiveDate);

	public DTAACountryRates saveDTAACountryRates(DTAACountryRates dtaaCountryRates);
	
	public DTAACountryRates updateDTAACountryRates(DTAACountryRates dtaaCountryRates);
 }
