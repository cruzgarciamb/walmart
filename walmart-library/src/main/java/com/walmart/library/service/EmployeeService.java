package com.walmart.library.service;

import java.util.List;

import com.walmart.library.models.entity.Employee;

public interface EmployeeService {
	
	List<Employee> getAllEmployees();
	
	Employee getEmployeeById(Long id);
	
	void addEmployee(Employee employee);

}
