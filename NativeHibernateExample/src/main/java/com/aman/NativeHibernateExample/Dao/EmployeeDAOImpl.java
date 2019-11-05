package com.aman.NativeHibernateExample.Dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aman.NativeHibernateExample.model.Employee;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<Employee> get() {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Employee> query = currentSession.createQuery("from Employee", Employee.class);
		List<Employee> list = query.getResultList();
		return list;
	}

	@Override
	public Employee get(long id) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);

		Employee tempEmployee = currentSession.get(Employee.class, 10);
//		currentSession.evict(tempEmployee);  This is used for detaching object from database
//		tempEmployee.setName("Honey Singh");
		return tempEmployee;
	}

	@Override
	public void save(Employee employee) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(employee);
		employee.setName("ABC");
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		Employee tempEmployee = currentSession.get(Employee.class, id);
		currentSession.delete(tempEmployee);
	}

}
