package annona.dao;

import java.util.List;

import annona.domain.CitizenAndCustomerCategoryMapping;

public interface CitizenAndCustomerCategoryDAO {

	public CitizenAndCustomerCategoryMapping save(CitizenAndCustomerCategoryMapping citizenAndCustomerCategory);

	public CitizenAndCustomerCategoryMapping update(CitizenAndCustomerCategoryMapping citizenAndCustomerCategory);

	public CitizenAndCustomerCategoryMapping findById(Long id);

	public List<CitizenAndCustomerCategoryMapping> findAll();

	public CitizenAndCustomerCategoryMapping findByCustomerCategoryIdAndCitizen(Long customerCategoryId,String citizen);
	public int deleteByCitizen(String citizen);
}