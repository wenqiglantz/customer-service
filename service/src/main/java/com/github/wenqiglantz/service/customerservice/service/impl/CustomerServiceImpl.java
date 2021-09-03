package com.github.wenqiglantz.service.customerservice.service.impl;

import com.github.wenqiglantz.service.customerservice.data.CustomerInfo;
import com.github.wenqiglantz.service.customerservice.data.CustomerStatus;
import com.github.wenqiglantz.service.customerservice.data.event.CustomerWasCreated;
import com.github.wenqiglantz.service.customerservice.data.event.CustomerWasDeleted;
import com.github.wenqiglantz.service.customerservice.data.event.CustomerWasUpdated;
import com.github.wenqiglantz.service.customerservice.data.exception.NotFoundException;
import com.github.wenqiglantz.service.customerservice.persistence.entity.Customer;
import com.github.wenqiglantz.service.customerservice.persistence.repository.CustomerRepository;
import com.github.wenqiglantz.service.customerservice.service.CustomerService;
import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.Metadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    //Time-to-live for messages published.
    private static final String MESSAGE_TTL_IN_SECONDS = "1000";

    //The topic for order service
    private static final String TOPIC_ORDER_SERVICE = "order-service";

    //The name of the pubsub
    private static final String PUBSUB_NAME = "customer-order-integration";

    @Override
    public CustomerInfo saveCustomer(CustomerInfo customerInfo) throws Exception {
        customerInfo.setCustomerId(Strings.isBlank(customerInfo.getCustomerId()) ? UUID.randomUUID().toString() : customerInfo.getCustomerId());
        Customer customer = Customer.builder()
                .customerId(customerInfo.getCustomerId())
                .firstName(customerInfo.getFirstName())
                .lastName(customerInfo.getLastName())
                .build();
        customerRepository.save(customer);

        CustomerWasCreated customerWasCreated = CustomerWasCreated.builder()
                .customerId(customerInfo.getCustomerId())
                .firstName(customerInfo.getFirstName())
                .lastName(customerInfo.getLastName())
                .status(CustomerStatus.CREATED)
                .build();
        log.info("publish event {} ", customerWasCreated);
        //initialize dapr client in try-with-resource block to properly close the client at the end
        try (DaprClient client = new DaprClientBuilder().build()) {
            client.publishEvent(
                    PUBSUB_NAME,
                    TOPIC_ORDER_SERVICE,
                    customerWasCreated,
                    singletonMap(Metadata.TTL_IN_SECONDS, MESSAGE_TTL_IN_SECONDS)).block();
        }
        return customerInfo;
    }

    @Override
    public CustomerInfo getCustomer(String customerId) {
        Customer customer =
                customerRepository.findByCustomerId(customerId).orElseThrow(() ->
                        new NotFoundException("Could not find customer with customerId: " + customerId));

        CustomerInfo customerInfo = CustomerInfo.builder()
                .customerId(customerId)
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
        return customerInfo;
    }

    @Override
    public List<CustomerInfo> getCustomers() {
        List<Customer> customers = customerRepository.findAll();

        List<CustomerInfo> customerInfos = customers.stream()
                .map(customer -> CustomerInfo.builder()
                        .customerId(customer.getCustomerId())
                        .firstName(customer.getFirstName())
                        .lastName(customer.getLastName())
                        .build())
                .collect(toList());

        return customerInfos;
    }

    @Override
    public void updateCustomer(String customerId, CustomerInfo customerInfo) throws Exception {
        Customer customer =
                customerRepository.findByCustomerId(customerId).orElseThrow(() ->
                        new NotFoundException("Could not find customer with customerId: " + customerId));
        customer.setFirstName(customerInfo.getFirstName());
        customer.setLastName(customerInfo.getLastName());
        customerRepository.save(customer);

        CustomerWasUpdated customerWasUpdated = CustomerWasUpdated.builder()
                .customerId(customerInfo.getCustomerId())
                .firstName(customerInfo.getFirstName())
                .lastName(customerInfo.getLastName())
                .status(CustomerStatus.UPDATED)
                .build();

        log.info("publish event {} ", customerWasUpdated);
        //initialize dapr client in try-with-resource block to properly close the client at the end
        try (DaprClient client = new DaprClientBuilder().build()) {
            client.publishEvent(
                    PUBSUB_NAME,
                    TOPIC_ORDER_SERVICE,
                    customerWasUpdated,
                    singletonMap(Metadata.TTL_IN_SECONDS, MESSAGE_TTL_IN_SECONDS)).block();
        }
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {
        Customer customer =
                customerRepository.findByCustomerId(customerId).orElseThrow(() ->
                        new NotFoundException("Could not find customer with customerId: " + customerId));
        customerRepository.delete(customer);

        CustomerWasDeleted customerWasDeleted = CustomerWasDeleted.builder()
                .customerId(customerId)
                .status(CustomerStatus.DELETED)
                .build();

        log.info("publish event {} ", customerWasDeleted);
        //initialize dapr client in try-with-resource block to properly close the client at the end
        try (DaprClient client = new DaprClientBuilder().build()) {
            client.publishEvent(
                    PUBSUB_NAME,
                    TOPIC_ORDER_SERVICE,
                    customerWasDeleted,
                    singletonMap(Metadata.TTL_IN_SECONDS, MESSAGE_TTL_IN_SECONDS)).block();
        }
    }
}
