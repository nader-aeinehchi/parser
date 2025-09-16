package org.openbanking.payment;

import org.openbanking.account.Account;
import java.math.BigDecimal;

public interface Payment {
    void makePayment(Account from, Account to, BigDecimal amount);
}