package com.n26.domain.statistic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class TransactionOldException extends Exception {
    public TransactionOldException(String message) {
        super(message);
    }
}
