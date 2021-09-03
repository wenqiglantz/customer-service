package com.github.wenqiglantz.service.customerservice.data.event;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.wenqiglantz.service.customerservice.data.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"customerId", "firstName", "lastName", "status"})
public class CustomerWasCreated {

    @Size(max = 36)
    private String customerId;

    @NotBlank @Size(max = 50)
    private String firstName;

    @NotBlank @Size(max = 50)
    private String lastName;

    private CustomerStatus status = CustomerStatus.CREATED;

}
