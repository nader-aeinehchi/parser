package org.openbanking.account;

/**
 * Represents a bank account.
 *
 * It contains the account ID, owner's name, and current balance.
 *
 * @param id      is a String representing the unique identifier of the account,
 *                with two characters and four digits.
 * @param owner   is a String representing the name of the account owner. The
 *                name is a person's full name or a company's name.
 * @param balance is a float representing the current balance of the account.
 */
public record AccountRecord(String id, String owner, float balance) {
    public String getId() {
        return id;
    }

    protected String getOwner() {
        return owner; 
    }

    private String getBalanceString() {
        return String.format("%.2f", balance);
    }

    String getDefault() {
        return "Account ID: " + id + ", Owner: " + owner + ", Balance: $" + getBalanceString();
    }   
}
