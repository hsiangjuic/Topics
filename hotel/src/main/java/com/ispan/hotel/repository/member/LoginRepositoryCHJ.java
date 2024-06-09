package com.ispan.hotel.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Customer;

@Repository
public interface LoginRepositoryCHJ extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsername(String username);
}
