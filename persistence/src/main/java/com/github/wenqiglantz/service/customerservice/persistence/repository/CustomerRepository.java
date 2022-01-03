package com.github.wenqiglantz.service.customerservice.persistence.repository;

import com.github.wenqiglantz.service.customerservice.persistence.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface CustomerRepository extends JpaRepository<Customer, String>, RevisionRepository<Customer, String, Integer> {
    Optional<Customer> findByCustomerId(String customerId);
}

