package com.ispan.hotel.service.member;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ispan.hotel.model.Customer;
import com.ispan.hotel.model.MemberRank;
import com.ispan.hotel.repository.member.CustomerRepositoryCHJ;
import com.ispan.hotel.repository.member.MemberRankRepositoryCHJ;

@Service
public class CustomerServiceCHJ {

    @Autowired
    private CustomerRepositoryCHJ customerRepository;

    @Autowired
    private MemberRankRepositoryCHJ memberRankRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }

    public Optional<MemberRank> findMemberRankById(Integer id) {
        return memberRankRepository.findById(id);
    }

}
