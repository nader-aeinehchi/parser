package org.openbanking.account;

import org.openbanking.customer.Customer;
import java.math.BigDecimal;

public interface Account {
    String getAccountNumber();
    Customer getCustomer();
    BigDecimal getBalance();
    void deposit(BigDecimal amount);
    void withdraw(BigDecimal amount);
    void close();
    boolean isClosed();
}