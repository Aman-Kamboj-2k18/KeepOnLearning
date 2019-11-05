package binding.FormBinding.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import binding.FormBinding.Model.Employee;
import binding.FormBinding.Repository.EmployeeRepo;

@Component
public class InsertEmployeeDaoImpl implements InsertEmployeeDao {

	@Autowired
	EmployeeRepo repo;

	@Override
	public void InsertEmployee(Employee emp) {
		// TODO Auto-generated method stub
		repo.save(emp);
	}

}
