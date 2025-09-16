package org.openbanking.card;

import org.openbanking.customer.Customer;

public interface CreditCard {
    String getCardNumber();
    Customer getOwner();
    boolean isActive();
    boolean isStolen();
    void suspend();
    void reportStolen();
}