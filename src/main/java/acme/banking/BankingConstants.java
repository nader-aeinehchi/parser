package acme.banking;

public enum BankingConstants {
	
	RED, YELLOW, GREEN; 

	public int getIndex() {
		return this.ordinal();
	}
}
 