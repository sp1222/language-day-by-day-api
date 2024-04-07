package com.sp1222.language.daybyday.api.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("timestamp")
public class ApiExceptionResponse {
    /**
     * Http Status Code.
     */
    private HttpStatus status;

    /**
     * Error Message.
     */
    private String message;
}
