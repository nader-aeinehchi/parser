package org.openbanking.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionImpl implements Transaction {
    private final String transactionId;
    private final BigDecimal amount;
    private final LocalDateTime timestamp;
    private final String description;

    public TransactionImpl(String transactionId, BigDecimal amount, String description) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }

    @Override
    public String getTransactionId() { return transactionId; }

    @Override
    public BigDecimal getAmount() { return amount; }

    @Override
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String getDescription() { return description; }
}