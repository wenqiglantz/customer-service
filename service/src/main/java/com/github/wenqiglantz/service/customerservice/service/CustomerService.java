package com.github.wenqiglantz.service.customerservice.service;


import com.github.wenqiglantz.service.customerservice.data.CustomerInfo;

import java.util.List;

public interface CustomerService {

    CustomerInfo saveCustomer(CustomerInfo customerInfo) throws Exception;

    List<CustomerInfo> getCustomers();

    CustomerInfo getCustomer(String customerId);

    void updateCustomer(String customerId, CustomerInfo customerInfo) throws Exception;

    void deleteCustomer(String customerId) throws Exception;
}