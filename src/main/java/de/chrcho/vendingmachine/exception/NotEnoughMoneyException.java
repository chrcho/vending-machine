package de.chrcho.vendingmachine.exception;

/**
 * Exception for not enough money to buy this drink.
 * 
 * @author Chrcho
 */
public class NotEnoughMoneyException extends Exception {

	private static final long serialVersionUID = 3776038983298121833L;

	public NotEnoughMoneyException() {
		super("Not enough money to buy this drink");
	}
}