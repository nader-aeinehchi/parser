package org.openbanking.service;

import org.openbanking.account.*;
import org.openbanking.customer.*;
import org.openbanking.card.*;

import java.util.*;

public class BankServiceImpl implements BankService {
    private final Map<String, Account> accounts = new HashMap<>();
    private final Map<String, CreditCard> cards = new HashMap<>();
    private int accountCounter = 1000;
    private int cardCounter = 5000;

    @Override
    public Account openAccount(Customer customer) {
        String accNum = "ACC" + (++accountCounter);
        Account acc = new AccountImpl(accNum, customer);
        accounts.put(accNum, acc);
        return acc;
    }

    @Override
    public void closeAccount(Account account) {
        account.close();
    }

    @Override
    public CreditCard issueCreditCard(Customer customer) {
        String cardNum = "CARD" + (++cardCounter);
        CreditCard card = new CreditCardImpl(cardNum, customer);
        cards.put(cardNum, card);
        return card;
    }

    @Override
    public void suspendCreditCard(CreditCard card) {
        card.suspend();
    }

    @Override
    public void reportCreditCardStolen(CreditCard card) {
        card.reportStolen();
    }

    @Override
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    @Override
    public CreditCard getCreditCard(String cardNumber) {
        return cards.get(cardNumber);
    }
}