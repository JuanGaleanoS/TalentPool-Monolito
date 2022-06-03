package com.talentpool.monolito.custom.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessClienteException extends RuntimeException {

    @JsonProperty("message")
    private String message;
    @JsonProperty("status")
    private HttpStatus status;
    @JsonProperty("uri")
    private String uriRequested;

    public BusinessClienteException(String message, HttpStatus statusCode) {
        this.message = message;
        this.status = statusCode;
    }

}
