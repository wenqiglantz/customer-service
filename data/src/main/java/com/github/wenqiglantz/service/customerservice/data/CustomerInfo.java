package com.github.wenqiglantz.service.customerservice.data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
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
@JsonPropertyOrder({"customerId", "firstName", "lastName"})
public class CustomerInfo {

    @Schema(required = false, description = "The ID for the customer. Should be a Unique ID with max 36 Characters. If not provided, the system will assign one.")
    @Size(max = 36)
    private String customerId;

    @Schema(required = true, description = "The first name. The length cannot exceed 50.")
    @NotBlank @Size(max = 50)
    private String firstName;

    @Schema(required = true, description = "The last name. The length cannot exceed 50.")
    @NotBlank @Size(max = 50)
    private String lastName;
}
