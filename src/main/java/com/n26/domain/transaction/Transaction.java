package com.n26.domain.transaction;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.n26.util.BigDecimalDeserializer;
import com.n26.util.InstantDeserializer;

/**
 * This is a Transaction entity
 */
public class Transaction {

    @JsonDeserialize(using = BigDecimalDeserializer.class)
    @NotNull
    private BigDecimal amount;
    @JsonDeserialize(using = InstantDeserializer.class)
    @NotNull
    private LocalDateTime timestamp;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
