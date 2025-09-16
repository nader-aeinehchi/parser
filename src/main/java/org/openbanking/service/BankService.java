package org.openbanking.service;

import org.openbanking.account.Account;
import org.openbanking.customer.Customer;
import org.openbanking.card.CreditCard;

public interface BankService {
    Account openAccount(Customer customer);
    void closeAccount(Account account);
    CreditCard issueCreditCard(Customer customer);
    void suspendCreditCard(CreditCard card);
    void reportCreditCardStolen(CreditCard card);
    Account getAccount(String accountNumber);
    CreditCard getCreditCard(String cardNumber);
}