package de.chrcho.vendingmachine.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.chrcho.vendingmachine.Coin;
import de.chrcho.vendingmachine.CoinCompartment;
import de.chrcho.vendingmachine.Drink;
import de.chrcho.vendingmachine.DrinkAndChange;
import de.chrcho.vendingmachine.DrinkCompartment;
import de.chrcho.vendingmachine.exception.ChangeNotAvailableException;
import de.chrcho.vendingmachine.exception.DrinkSoldOutException;
import de.chrcho.vendingmachine.exception.NotEnoughMoneyException;

/**
 * Service implementation of vending machine.
 * 
 * @author Chrcho
 */
public class VendingMachineServiceImpl implements VendingMachineService {

	private static final int DRINK_COMPARTMENT_LIMIT = 10;
	private static final int COIN_COMPARTMENT_LIMIT = 100;

	private Map<Drink, DrinkCompartment> drinkCompartments = new HashMap<Drink, DrinkCompartment>();
	private Map<Coin, CoinCompartment> coinCompartments = new HashMap<Coin, CoinCompartment>();

	/**
	 * Service implementation of vending machine.
	 */
	public VendingMachineServiceImpl() {
		this.initVendingMachine();
	}

	/**
	 * Init vending machine in-memory.
	 */
	private void initVendingMachine() {
		// create drink compartments
		drinkCompartments.put(Drink.COCA_COLA, new DrinkCompartment(Drink.COCA_COLA, 180, 10));
		drinkCompartments.put(Drink.SPRITE, new DrinkCompartment(Drink.SPRITE, 120, 10));
		drinkCompartments.put(Drink.FANTA, new DrinkCompartment(Drink.FANTA, 100, 10));
		// create coin compartments
		coinCompartments.put(Coin.CENT_10, new CoinCompartment(Coin.CENT_10, 1));
		coinCompartments.put(Coin.CENT_20, new CoinCompartment(Coin.CENT_20, 1));
		coinCompartments.put(Coin.CENT_50, new CoinCompartment(Coin.CENT_50, 1));
		coinCompartments.put(Coin.EURO_1, new CoinCompartment(Coin.EURO_1, 0));
		coinCompartments.put(Coin.EURO_2, new CoinCompartment(Coin.EURO_2, 0));
	}
	
	private List<Coin> inputCoinList;
	
	public DrinkAndChange buy(Drink inputDrink, Coin... inputCoinArray)
			throws ChangeNotAvailableException, DrinkSoldOutException, NotEnoughMoneyException {

		// Store input coins
		this.setInputCoinList(inputCoinArray);
		
		// Check if drink compartment is empty
		DrinkCompartment inputDrinkCompartment = drinkCompartments.get(inputDrink);
		if (inputDrinkCompartment.getCount() == 0) {
			throw new DrinkSoldOutException();
		}
		
		// Check if enough money for the drink
		int inputCoinTotal = this.calcInputCoinTotal(this.inputCoinList);
		if (inputCoinTotal < inputDrinkCompartment.getPrice()) {
			throw new NotEnoughMoneyException();
		}

		List<Coin> changeCoinList = new ArrayList<Coin>();
		int changeCoinTotal = inputCoinTotal - inputDrinkCompartment.getPrice();
		if (changeCoinTotal > 0) {		
			// Create change coin list if available
			changeCoinTotal = this.createChangeCoinList(changeCoinTotal, changeCoinList);
			if (changeCoinTotal > 0) {
				throw new ChangeNotAvailableException();
			}		
			// Decrease count of change coins
			for (Coin changeCoin : changeCoinList) {
				coinCompartments.get(changeCoin).decreaseCountByOne();
			}
		}

		// Purchase is successful
		inputDrinkCompartment.decreaseCountByOne();
		this.inputCoinList = null;

		return new DrinkAndChange(inputDrinkCompartment.getDrink(), this.convertCoinList(
				changeCoinList));
	}
	
	/**
	 * Store input coins to list.
	 * 
	 * @param inputCoinArray Array of input coins
	 */
	private void setInputCoinList(Coin[] inputCoinArray) {
		if(this.inputCoinList == null) {
			this.inputCoinList = new ArrayList<Coin>();
		}
		if(inputCoinArray != null) {
			this.inputCoinList.addAll(Arrays.asList(inputCoinArray));
		}
	}
	
	/**
	 * Calculate total number of input coins.
	 * 
	 * @param inputCoinList List of input coins
	 * @return Total number
	 */
	private int calcInputCoinTotal(List<Coin> inputCoinList) {
		int inputCoinTotal = 0;
		for (Coin inputCoin : inputCoinList) {
			inputCoinTotal += inputCoin.getValue();
			coinCompartments.get(inputCoin).increaseCountByOne();
		}
		return inputCoinTotal;
	}

	/**
	 * Create list of change coins.
	 * 
	 * @param changeCoinTotal Total number of change
	 * @param changeCoinList Empty list to add change coins
	 * @return Rest number of change (0 = change available)
	 */
	private int createChangeCoinList(int changeCoinTotal, final List<Coin> changeCoinList) {
		for (Coin changeCoin : Coin.valuesOrderByValueDesc()) {
			if (changeCoinTotal >= changeCoin.getValue() && coinCompartments.get(changeCoin).getCount() > 0) {
				int changeCoinNeeded = changeCoinTotal / changeCoin.getValue();
				// Needed change coins are available? 
				changeCoinNeeded = (coinCompartments.get(changeCoin).getCount() >= changeCoinNeeded) ? 
						changeCoinNeeded : coinCompartments.get(changeCoin).getCount();			
				changeCoinTotal -= changeCoin.getValue() * changeCoinNeeded;
				// Add change coins to list
				for (int i = 0; i < changeCoinNeeded; i++) {
					changeCoinList.add(changeCoin);
				}
			}
		}
		return changeCoinTotal;
	}
	
	/**
	 * Convert coin array to coin list.
	 * 
	 * @param coinList List of coins
	 * @return Array of coins
	 */
	private Coin[] convertCoinList(List<Coin> coinList) {
		if(coinList != null) {
			return coinList.toArray(new Coin[coinList.size()]);
		}
		return new Coin[0];
	}

	public void restockDrink(Drink drink) {
		if (drinkCompartments.get(drink).getCount() < DRINK_COMPARTMENT_LIMIT) {
			drinkCompartments.get(drink).increaseCountByOne();
		}
	}

	public void restockCoin(Coin coin) {
		if (coinCompartments.get(coin).getCount() < COIN_COMPARTMENT_LIMIT) {
			coinCompartments.get(coin).increaseCountByOne();
		}
	}

	public void emptyDrink(Drink drink) {
		drinkCompartments.get(drink).setCount(0);
	}

	public void emptyCoin(Coin coin) {
		if (this.inputCoinList == null) {
			coinCompartments.get(coin).setCount(0);
		}
	}
	
	public Coin[] releaseInputCoinArray() {
		Coin[] inputCoinArray = this.convertCoinList(this.inputCoinList);
		this.inputCoinList = null;
		return inputCoinArray;
	}

	@Override
	public int getDrinkCount(Drink drink) {
		return this.drinkCompartments.get(drink).getCount();
	}

	@Override
	public int getCoinCount(Coin coin) {
		return this.coinCompartments.get(coin).getCount();
	}
}