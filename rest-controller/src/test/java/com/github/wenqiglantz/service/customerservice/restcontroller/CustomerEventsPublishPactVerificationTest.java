package com.github.wenqiglantz.service.customerservice.restcontroller;

import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wenqiglantz.service.customerservice.data.CustomerInfo;
import com.github.wenqiglantz.service.customerservice.data.CustomerStatus;
import com.github.wenqiglantz.service.customerservice.data.event.CustomerWasCreated;
import com.github.wenqiglantz.service.customerservice.data.event.CustomerWasDeleted;
import com.github.wenqiglantz.service.customerservice.data.event.CustomerWasUpdated;
import com.github.wenqiglantz.service.customerservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@PactFolder("pacts")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("customer-service")
public class CustomerEventsPublishPactVerificationTest {

    @RegisterExtension
    public Mockery context = new JUnit5Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void before(PactVerificationContext pactVerificationContext) {
        System.setProperty("pact.verifier.publishResults", "true");
        pactVerificationContext.setTarget(new MessageTestTarget());
        customerService = context.mock(CustomerService.class);
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @PactVerifyProvider("valid CustomerWasCreated from provider")
    public String publishCustomerWasCreated() throws Exception {

        CustomerInfo customerInfo = CustomerInfo.builder()
                .customerId("595eed0c-eff5-4278-90ad-b952f18dbee8")
                .firstName("test")
                .lastName("last")
                .build();
        CustomerWasCreated event = CustomerWasCreated.builder()
                .customerId("595eed0c-eff5-4278-90ad-b952f18dbee8")
                .firstName("test")
                .lastName("last")
                .status(CustomerStatus.CREATED)
                .build();
        context.checking(new Expectations() {
            {
                oneOf(customerService).saveCustomer(customerInfo);
            }
        });
        customerService.saveCustomer(customerInfo);

        String eventString = objectMapper.writeValueAsString(event);
        return eventString;
    }

    @PactVerifyProvider("valid CustomerWasUpdated from provider")
    public String publishCustomerWasUpdated() throws Exception {

        CustomerInfo customerInfo = CustomerInfo.builder()
                .customerId("595eed0c-eff5-4278-90ad-b952f18dbee8")
                .firstName("test123")
                .lastName("last")
                .build();
        CustomerWasUpdated event = CustomerWasUpdated.builder()
                .customerId("595eed0c-eff5-4278-90ad-b952f18dbee8")
                .firstName("test123")
                .lastName("last")
                .status(CustomerStatus.UPDATED)
                .build();
        context.checking(new Expectations() {
            {
                oneOf(customerService).updateCustomer(customerInfo.getCustomerId(), customerInfo);
            }
        });
        customerService.updateCustomer(customerInfo.getCustomerId(), customerInfo);

        String eventString = objectMapper.writeValueAsString(event);
        return eventString;
    }

    @PactVerifyProvider("valid CustomerWasDeleted from provider")
    public String publishCustomerWasDeleted() throws Exception {
        String customerId = "595eed0c-eff5-4278-90ad-b952f18dbee8";
        CustomerWasDeleted event = CustomerWasDeleted.builder()
                .customerId(customerId)
                .status(CustomerStatus.DELETED)
                .build();
        context.checking(new Expectations() {
            {
                oneOf(customerService).deleteCustomer(customerId);
            }
        });
        customerService.deleteCustomer(customerId);

        String eventString = objectMapper.writeValueAsString(event);
        return eventString;
    }
}
