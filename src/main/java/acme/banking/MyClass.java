package acme.banking;

import java.io.IOException;

public final class MyClass<T extends Number> extends Zoo implements ZooKeeperInterface	 {
    
    // A constructor
    public MyClass() {
        // constructor body
    }
    
    // A method with parameters and a return type
    private static <E> E myMethod(String name, int value) throws IOException {
        // method body
        return null;
    }
    
    // An abstract method without a body
    protected void processData() {
    	
    };
    
    // A method with no parameters
    void anotherMethod() {
        // method body
    }
    
    public class InnerClass {
    	public static void firstInnerMethod(final String insider) {
    		
    	}
    	
    	private InnerClass(String name) {
    		
    	}
    }
}