package org.openbanking.card;

public enum CreditCardTypes {
	
	Visa, Mastercard, Bitcoin, Ethereum, Sui; 

	public int getIndex() {
		return this.ordinal();
	}
}
 