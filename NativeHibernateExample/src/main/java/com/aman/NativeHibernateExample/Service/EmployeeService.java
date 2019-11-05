package com.aman.NativeHibernateExample.Service;

import java.util.List;

import com.aman.NativeHibernateExample.model.Employee;


public interface EmployeeService {
	List<Employee> get();

	Employee get(long id);

	void save(Employee employee);

	void delete(long id);
}
