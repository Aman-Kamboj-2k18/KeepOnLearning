package annona.dao;

import annona.domain.GSTDeduction;

public interface GSTDeductionDAO {

	GSTDeduction save(GSTDeduction deduction);

	GSTDeduction update(GSTDeduction deduction);

	GSTDeduction findById(Long id);

	GSTDeduction findAll();

}
