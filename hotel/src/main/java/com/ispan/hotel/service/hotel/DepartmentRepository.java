package com.ispan.hotel.service.hotel;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.hotel.model.Dept;

public interface DepartmentRepository extends JpaRepository<Dept, Integer>  {

}
