package de.chrcho.vendingmachine;

/**
 * Compartment of drinks.
 * 
 * @author Chrcho
 */
public class DrinkCompartment extends Compartment {

	private Drink drink;
	private int price;
	
	public DrinkCompartment(Drink drink, int price, int count) {	
		super(count);
		this.setDrink(drink);
		this.setPrice(price);	
	}

	public Drink getDrink() {
		return drink;
	}

	public void setDrink(Drink drink) {
		this.drink = drink;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}