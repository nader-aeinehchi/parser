package org.openbanking.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface Transaction {
    String getTransactionId();
    BigDecimal getAmount();
    LocalDateTime getTimestamp();
    String getDescription();
}