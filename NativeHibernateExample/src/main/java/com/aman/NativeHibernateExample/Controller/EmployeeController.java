package com.aman.NativeHibernateExample.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aman.NativeHibernateExample.Service.EmployeeService;
import com.aman.NativeHibernateExample.model.Employee;

@RestController
@RequestMapping("/api")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/employee")
	public List<Employee> Get() {
		return employeeService.get();
	}

	@PostMapping("/employee")
	public Employee save(@RequestBody Employee employeeobj) {
		employeeService.save(employeeobj);
		return employeeobj;
	}

	@GetMapping("/employee/{id}")
	public Employee get(@PathVariable long id) {
		return employeeService.get(id);
	}

	@DeleteMapping("/employee/{id}")
	public String delete(@PathVariable long id) {
		employeeService.delete(id);
		return "Employee deleted succefully";
	}

	@PutMapping("/employee")
	public Employee update(@RequestBody Employee employeeobj) {
		employeeService.save(employeeobj);
		return employeeobj;
	}

}
