package annona.dao;

import java.util.List;

import annona.domain.ProductConfiguration;


public interface ProductConfigurationDAO 
 {

	public ProductConfiguration insertProductConfiguration(ProductConfiguration productConfiguration);
	
	public ProductConfiguration updateProductConfiguration(ProductConfiguration productConfiguration);
		
	public ProductConfiguration findById(Long id);	
	
	public ProductConfiguration findByProductCode(String productCode);
	
	public List<ProductConfiguration> findAll();
	
	public List<ProductConfiguration> getProductConfigurationList(String productType);
	
	public List<ProductConfiguration> getProductConfigurationListByProductTypeAndCitizen(String productType, String citizen);
	
	public ProductConfiguration getProductConfiguration(String productType, String citizen, String accountType);
	
	public List<ProductConfiguration> getProductConfigurationListByInterestCalculationBasis(String interestCalculationBasis);
	
	public List<ProductConfiguration> getProductConfigurationListByInterestCompoundingBasis(String interestCompoundingBasis);
	
	public List<ProductConfiguration> getProductConfigurationListByProductTypeAndCitizenAndNRIAccountType(String productType, String citizen,String nriAccountType);
  }
