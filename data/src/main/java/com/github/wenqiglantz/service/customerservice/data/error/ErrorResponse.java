package com.github.wenqiglantz.service.customerservice.data.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "errorCode", "errorKey", "errorMessage"})
public class ErrorResponse {

    @Schema(required = true, description = "HTTP Status Code of the error")
    @JsonProperty("error-code")
    private String errorCode;

    @Schema(required = true, description = "Error Type")
    @JsonProperty("error-key")
    private String errorKey;

    @Schema(required = true, description = "Description of the Error")
    @JsonProperty("error-message")
    private String errorMessage;
}
