package com.ispan.hotel.repository.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ispan.hotel.model.Customer;


@Repository
public interface CustomerRepositoryYTH extends JpaRepository<Customer, Integer>  {
	
//	@Query("from Customer where identification = :identification")
//	public Customer findByIdentification(String identification);
	
	@Query("from Customer where username = :username")
	public Customer findByUsername(String username);
}
