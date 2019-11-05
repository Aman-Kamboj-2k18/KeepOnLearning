package annona.dao;

import java.util.List;

import annona.domain.CurrencyConfiguration;

public interface CurrencyConfigurationDAO {
	

	public CurrencyConfiguration save(CurrencyConfiguration currencyConfiguration);

	public CurrencyConfiguration update(CurrencyConfiguration currencyConfiguration);	

	public CurrencyConfiguration findById(Long id);

	public List<CurrencyConfiguration> findAll();
	public List<CurrencyConfiguration> findByCitizen(String citizen);
	
	public CurrencyConfiguration findByAccountType(String accountType);

}
