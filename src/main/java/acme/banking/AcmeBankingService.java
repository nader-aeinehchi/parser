package acme.banking;

import java.util.List;

/**
 * Acme BankingService interface provides methods for managing bank accounts and
 * transactions.
 *
 * It allows creating accounts, transactions, and transaction requests, as well
 * as retrieving account details and executing transactions.
 * 
 * The implementation of this interface should handle the business logic
 */
interface AcmeBankingService {

	public String PUBLIC_GREETING_MESSAGE = "Welcome to our application!";
    int PACKAGE_MAX_USERS = 100;
    double PI_VALUE = 3.14159;

	/**
	 * Banking system: Creates a bank account using the provided parameters.
	 *
	 *
	 * @param id      is a String representing the unique identifier of the account,
	 *                with two characters and four digits.
	 * @param owner   is a String representing the name of the account owner. The
	 *                name is a person's full name or a company's name.
	 * @param balance is a float representing the current balance of the account.
	 */

	public Account publicCreateAccount(String id, String owner, float balance);

	/**
	 * Retrieves a bank account by its unique identifier.
	 *
	 * @param id
	 * @return the bank account with the specified ID, or null if no such account
	 *         exists.
	 * @throws IllegalArgumentException if the provided ID is null or empty.
	 * @throws IllegalStateException    if the account with the specified ID does
	 *                                  not exist.
	 */
	Account getAccount(String id);

	/**
	 * Lists all bank accounts.
	 *
	 * @return a list of all bank accounts, or an empty list if no accounts exist.
	 */
	List<Account> listAccounts();

	/**
	 * Creates a transaction to transfer money between two bank accounts.
	 * 
	 * @param fromAccountId is a String representing the unique identifier of the
	 *                      account from which the money will be transferred, with
	 *                      two characters and four digits.
	 * @param toAccountId   is a String representing the unique identifier of the
	 *                      account to which the money will be transferred, with two
	 *                      characters and four digits.
	 * @param amount        is a float representing the amount of money to be
	 *                      transferred. It should be a positive value.
	 * @return a Transaction object representing the transfer, or null if the
	 *         transfer could not be created (e.g., due to insufficient funds or
	 *         invalid account IDs).
	 */
	Transaction createTransaction(String fromAccountId, String toAccountId, float amount);

	// /**
	// * Retrieves all transactions for a given account.
	// *
	// * @param accountId
	// * @return a list of transactions associated with the specified account ID, or
	// * an empty list if no transactions exist for that account.
	// * @throws IllegalArgumentException if the provided account ID is null or
	// empty.
	// * @throws IllegalStateException if the account with the specified ID does
	// * not exist.
	// */
	//
	// List<Transaction> getTransactions(String accountId);

	// /**
	// * Executes a transaction request, transferring funds between two accounts.
	// *
	// * @return true if the transaction was successfully executed, false otherwise.
	// * @throws IllegalArgumentException if the transaction request is null or
	// * contains invalid data.
	// * @throws IllegalStateException if the transaction request cannot be
	// * processed due to insufficient funds, invalid
	// * account IDs, or other issues.
	// */
	//
	// boolean executeTransaction(TransactionRequest transactionRequest);

}
