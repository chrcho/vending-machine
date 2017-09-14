package de.chrcho.vendingmachine.exception;

/**
 * Exception for not enough change available.
 * 
 * @author Chrcho
 */
public class ChangeNotAvailableException extends Exception {

	private static final long serialVersionUID = -7963077050003239869L;

	public ChangeNotAvailableException() {
		super("Not enough change available.");
	}
}