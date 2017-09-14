package de.chrcho.vendingmachine;

/**
 * Class of drink and change coins.
 * 
 * @author Chrcho
 */
public class DrinkAndChange {

	private Drink drink;
	private Coin[] changeCoinArray;

	public DrinkAndChange(Drink drink, Coin[] changeCoinArray) {
		this.setDrink(drink);
		this.setChangeCoinArray(changeCoinArray);
	}

	public Drink getDrink() {
		return drink;
	}
	
	public void setDrink(Drink drink) {
		this.drink = drink;
	}
	
	public Coin[] getChangeCoinArray() {
		return changeCoinArray;
	}
	
	public void setChangeCoinArray(Coin[] changeCoinArray) {
		this.changeCoinArray = changeCoinArray;
	}
}