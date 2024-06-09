package com.ispan.hotel.repository.member;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ispan.hotel.model.Customer;

@Repository
public interface CustomerRepositoryCHJ extends JpaRepository<Customer, Integer> {
    List<Customer> findByMemberRank_MrId(Integer mrId);
}
