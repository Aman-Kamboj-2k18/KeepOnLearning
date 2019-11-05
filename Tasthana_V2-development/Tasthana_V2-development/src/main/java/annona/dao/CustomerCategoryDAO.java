package annona.dao;

import java.util.List;

import annona.domain.CustomerCategory;

public interface CustomerCategoryDAO {
	

	public CustomerCategory save(CustomerCategory customerCategory);

	public CustomerCategory update(CustomerCategory customerCategory);

	public CustomerCategory findById(Long id);

	public List<CustomerCategory> findAll();

}
