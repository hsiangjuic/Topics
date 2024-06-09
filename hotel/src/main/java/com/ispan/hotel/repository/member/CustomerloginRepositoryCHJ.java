package com.ispan.hotel.repository.member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ispan.hotel.model.Customer;

public interface CustomerloginRepositoryCHJ extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsername(String username);

    Optional<Customer> findByEmail(String email); // 添加這行
}
