package org.openbanking.account;

import org.openbanking.customer.Customer;
import java.math.BigDecimal;

public class AccountImpl implements Account {
    private final String accountNumber;
    private final Customer customer;
    private BigDecimal balance = BigDecimal.ZERO;
    private boolean closed = false;

    public AccountImpl(String accountNumber, Customer customer) {
        this.accountNumber = accountNumber;
        this.customer = customer;
    }

    @Override
    public String getAccountNumber() { return accountNumber; }

    @Override
    public Customer getCustomer() { return customer; }

    @Override
    public BigDecimal getBalance() { return balance; }

    @Override
    public void deposit(BigDecimal amount) {
        if (closed) throw new IllegalStateException("Account is closed");
        balance = balance.add(amount);
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (closed) throw new IllegalStateException("Account is closed");
        if (balance.compareTo(amount) < 0) throw new IllegalArgumentException("Insufficient funds");
        balance = balance.subtract(amount);
    }

    @Override
    public void close() { closed = true; }

    @Override
    public boolean isClosed() { return closed; }
}