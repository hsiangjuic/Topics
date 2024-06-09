package com.ispan.hotel.repository.hotel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Employee;

@Repository
public interface EmployeeRepositoryCYP extends JpaRepository<Employee, Integer> {
	
	@Query("SELECT e FROM Employee e WHERE e.id = :id")
	public Optional<Employee> selectById(@Param("id") String id);
	




}
