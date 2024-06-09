package com.ispan.hotel.repository.hotel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Dept;

@Repository
public interface DeptRepositoryCYP extends JpaRepository<Dept, Integer> {

}
