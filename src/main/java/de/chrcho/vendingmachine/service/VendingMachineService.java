package de.chrcho.vendingmachine.service;

import de.chrcho.vendingmachine.Coin;
import de.chrcho.vendingmachine.Drink;
import de.chrcho.vendingmachine.DrinkAndChange;
import de.chrcho.vendingmachine.exception.ChangeNotAvailableException;
import de.chrcho.vendingmachine.exception.DrinkSoldOutException;
import de.chrcho.vendingmachine.exception.NotEnoughMoneyException;

/**
 * Interface of vending machine service.
 * 
 * @author Chrcho
 */
public interface VendingMachineService {

	/**
	 * Buy drink with coins.
	 * 
	 * @param inputDrink Drink as enumeration
	 * @param inputCoinArray Array of input coin enumerations
	 * @return Drink and change object
	 * @throws ChangeNotAvailableException
	 * @throws DrinkSoldOutException
	 * @throws NotEnoughMoneyException
	 */
	public DrinkAndChange buy(Drink inputDrink, Coin... inputCoinArray) throws 
		ChangeNotAvailableException, DrinkSoldOutException, NotEnoughMoneyException;
	
	/**
	 * Restock drink compartment by one until limit.
	 * 
	 * @param drink Drink as enumeration
	 */
	public void restockDrink(Drink drink);
	
	/**
	 * Restock coin compartment by one until limit.
	 * 
	 * @param coin Coin as enumeration
	 */
	public void restockCoin(Coin coin);
	
	/**
	 * Empty drink compartment.
	 * 
	 * @param drink Drink as enumeration
	 */
	public void emptyDrink(Drink drink);
	
	/**
	 * Empty coin compartment.
	 * 
	 * @param coin Coin as enumeration
	 */
	public void emptyCoin(Coin coin);
	
	/**
	 * Release input coins if exception is occured.
	 * 
	 * @return Array of input coins
	 */
	public Coin[] releaseInputCoinArray();
	
	/**
	 * Get count of drink.
	 * 
	 * @param drink Drink as enumeration
	 * @return Count of drink
	 */
	public int getDrinkCount(Drink drink);
	
	/**
	 * Get count of coin.
	 * 
	 * @param coin Coin as enumeration
	 * @return Count of coin
	 */
	public int getCoinCount(Coin coin);
}