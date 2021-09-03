package com.github.wenqiglantz.service.customerservice.bdd;

import com.github.wenqiglantz.service.customerservice.data.CustomerInfo;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class HttpClient {

    private final String SERVER_URL = "http://localhost";
    private final String CUSTOMER_ENDPOINT = "/customers";

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate();

    private String customerEndpoint() {
        return SERVER_URL + ":" + port + CUSTOMER_ENDPOINT;
    }

    public int post(CustomerInfo customerInfo) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        HttpEntity<CustomerInfo> request = new HttpEntity<>(customerInfo, headers);

        return restTemplate.postForEntity(customerEndpoint(), request, ResponseEntity.class).getStatusCodeValue();
    }

    public CustomerInfo getCustomer(String customerId) {
        return restTemplate.getForEntity(customerEndpoint(), CustomerInfo.class, customerId).getBody();
    }

    public void clean() {
        restTemplate.delete(customerEndpoint());
    }

}
