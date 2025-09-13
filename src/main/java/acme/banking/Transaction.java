package acme.banking;

/**
 * Represents a request to transfer money between two bank accounts.
 *
 * It contains the account IDs of the sender and receiver, and the amount to be
 * transferred.
 *
 * @param fromAccountId is a String representing the unique identifier of the
 *                      account, with two characters and four digits.
 * @param toAccountId   is a String representing the unique identifier of the
 *                      account, with two characters and four digits.
 * @param amount        is a float representing the amount of money to be
 *                      transferred.
 */
public record Transaction(String fromAccountId, String toAccountId, float amount) {
}
