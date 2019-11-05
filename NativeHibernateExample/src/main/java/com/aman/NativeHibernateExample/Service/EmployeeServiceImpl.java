package com.aman.NativeHibernateExample.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aman.NativeHibernateExample.Dao.EmployeeDAO;
import com.aman.NativeHibernateExample.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDAO employeeDAO;

	@Transactional
	@Override
	public List<Employee> get() {
		return employeeDAO.get();
	}

	@Transactional
	@Override
	public Employee get(long id) {
		// TODO Auto-generated method stub
		return employeeDAO.get(id);
	}

	@Transactional
	@Override
	public void save(Employee employee) {
		employeeDAO.save(employee);
	}

	@Transactional
	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		employeeDAO.delete(id);
	}

}
