package com.walmart.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walmart.library.models.entity.Employee;
import com.walmart.library.repository.IEmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired private IEmployeeRepository repository;
	
	@Override
	public List<Employee> getAllEmployees() {
		return repository.findAll();
	}

	@Override
	public Employee getEmployeeById(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public void addEmployee(Employee employee) {
		repository.save(employee);

	}

}
