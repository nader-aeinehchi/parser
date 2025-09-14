package acme.banking;

/**
 * ZooKeeper is good
 */
public final class ZooKeeper extends Zoo implements ZooKeeperInterface {

	private String name;
	private String zooName;
	private String location;

	/**
	 * Help keep the zoo in order
	 * 
	 * @param name
	 * @param zooName
	 * @param location
	 */
	public ZooKeeper(String name, String zooName, String location) {
		this.name = name;
		this.zooName = zooName;
		this.location = location;
	}

	public String publicSetter(String color, String age) {
		return color + "-" + color + "  " + age + " age";
	}

	protected String protectedSetter(String color, String age) {
		return color + "-" + color + "  " + age + " age";
	}

	String defaultSetter(String color, String age) {
		return color + "-" + color + "  " + age + " age";
	}

	private String privateSetter(String color, String age) {
		return color + "-" + color + "  " + age + " age";
	}

	static class InnerClass extends Zoo {
		private void doSomeInternalOperation(int numberOfOperations) {

		}
	}
}
