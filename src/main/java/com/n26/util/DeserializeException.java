package com.n26.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class DeserializeException extends JsonProcessingException {
    public DeserializeException(String message) {
        super(message);
    }
}
