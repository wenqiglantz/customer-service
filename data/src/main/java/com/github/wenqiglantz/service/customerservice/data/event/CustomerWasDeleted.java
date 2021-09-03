package com.github.wenqiglantz.service.customerservice.data.event;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.wenqiglantz.service.customerservice.data.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"customerId", "status"})
public class CustomerWasDeleted {

    @Size(max = 36)
    private String customerId;

    private CustomerStatus status = CustomerStatus.DELETED;

}
