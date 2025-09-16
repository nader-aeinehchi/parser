package org.openbanking.card;

import org.openbanking.customer.Customer;

public class CreditCardImpl implements CreditCard {
    private final String cardNumber;
    private final Customer owner;
    private boolean active = true;
    private boolean stolen = false;

    public CreditCardImpl(String cardNumber, Customer owner) {
        this.cardNumber = cardNumber;
        this.owner = owner;
    }

    @Override
    public String getCardNumber() { return cardNumber; }

    @Override
    public Customer getOwner() { return owner; }

    @Override
    public boolean isActive() { return active; }

    @Override
    public boolean isStolen() { return stolen; }

    @Override
    public void suspend() { active = false; }

    @Override
    public void reportStolen() {
        stolen = true;
        active = false;
    }
}