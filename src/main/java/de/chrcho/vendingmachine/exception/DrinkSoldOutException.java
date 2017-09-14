package de.chrcho.vendingmachine.exception;

/**
 * Exception for drink is sold out.
 * 
 * @author Chrcho
 */
public class DrinkSoldOutException extends Exception {

	private static final long serialVersionUID = -1839758633885217672L;

	public DrinkSoldOutException() {
		super("Drink is sold out.");
	}
}