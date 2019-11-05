package binding.FormBinding.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import binding.FormBinding.Model.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

}
