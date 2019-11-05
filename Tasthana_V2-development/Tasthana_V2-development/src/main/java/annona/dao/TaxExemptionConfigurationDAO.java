package annona.dao;

import java.util.Date;
import java.util.List;

import annona.domain.TaxExemptionConfiguration;

public interface TaxExemptionConfigurationDAO {
	
	public TaxExemptionConfiguration save(TaxExemptionConfiguration configuration);
	public List<TaxExemptionConfiguration> findByEffietiveDateTaxExemptionConfiguration(Date date);
	public List<Date> findByEffectiveDate();
	public TaxExemptionConfiguration update(TaxExemptionConfiguration configuration);
	
	public TaxExemptionConfiguration findById(Long id);
	
	public List<TaxExemptionConfiguration> getTaxExemptionConfigurationList();
}
