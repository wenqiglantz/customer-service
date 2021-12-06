package com.github.wenqiglantz.service.customerservice.service;


import com.github.wenqiglantz.service.customerservice.data.CustomerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    CustomerInfo saveCustomer(CustomerInfo customerInfo) throws Exception;

    Page<CustomerInfo> getCustomers(Pageable pageable);

    CustomerInfo getCustomer(String customerId);

    void updateCustomer(String customerId, CustomerInfo customerInfo) throws Exception;

    void deleteCustomer(String customerId) throws Exception;
}