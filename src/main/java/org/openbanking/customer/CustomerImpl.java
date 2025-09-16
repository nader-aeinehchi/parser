package org.openbanking.customer;

public class CustomerImpl implements Customer {
    private final String customerId;
    private final String name;

    public CustomerImpl(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    @Override
    public String getCustomerId() { return customerId; }

    @Override
    public String getName() { return name; }
}