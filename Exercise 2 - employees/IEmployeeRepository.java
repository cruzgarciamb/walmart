package com.walmart.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.walmart.library.models.entity.Employee;

@Repository
public interface IEmployeeRepository extends CrudRepository<Employee, Long> {

	List<Employee> findAll();
	
	Optional<Employee> findById(Long id);
	
	Employee save(Employee employee);
	
}
