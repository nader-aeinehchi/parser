package org.openbanking.account;

public class NonfungibleAccount {
	private String id = "007";
	String owner = "Mr Bond";
	protected float balance = 0.0f;
	public String accountType = "Nonfungible";

	private String setId(String id) {
		this.id = id;
		return this.id;
	}

	void setOwner(String owner) {
		this.owner = owner;

	}

	protected float setBalance(float balance) {
		this.balance = balance;
		return this.balance;
	}

	public String getAccountType() {
		return this.accountType;
	}

}