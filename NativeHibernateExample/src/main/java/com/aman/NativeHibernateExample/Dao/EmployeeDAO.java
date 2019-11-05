package com.aman.NativeHibernateExample.Dao;

import java.util.List;

import com.aman.NativeHibernateExample.model.Employee;

public interface EmployeeDAO {

	List<Employee> get();

	Employee get(long id);

	void save(Employee employee);

	void delete(long id);
}
