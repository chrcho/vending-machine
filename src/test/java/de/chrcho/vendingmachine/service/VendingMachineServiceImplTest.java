package de.chrcho.vendingmachine.service;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.chrcho.vendingmachine.Coin;
import de.chrcho.vendingmachine.Drink;
import de.chrcho.vendingmachine.DrinkAndChange;
import de.chrcho.vendingmachine.exception.ChangeNotAvailableException;
import de.chrcho.vendingmachine.exception.DrinkSoldOutException;
import de.chrcho.vendingmachine.exception.NotEnoughMoneyException;
import de.chrcho.vendingmachine.service.VendingMachineService;
import de.chrcho.vendingmachine.service.VendingMachineServiceImpl;

public class VendingMachineServiceImplTest {

	private VendingMachineService vendingMachineService;
	
	@Before
	public void setUp() {
		vendingMachineService = new VendingMachineServiceImpl();
	}

	@Test
	public void testNotEnoughMoneyException() {
		Drink drink = Drink.COCA_COLA;
		try {
			vendingMachineService.buy(drink, Coin.CENT_50);
			Assert.fail();		
		} catch (Exception e) {
			Assert.assertThat(e, CoreMatchers.instanceOf(NotEnoughMoneyException.class));
		}	
		try {
			DrinkAndChange drinkAndChange = vendingMachineService.buy(drink, 
					Coin.EURO_1, Coin.CENT_20, Coin.CENT_10);
			Assert.assertTrue(drinkAndChange.getDrink() == drink);
			Assert.assertTrue(drinkAndChange.getChangeCoinArray().length == 0);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	@Test
	public void testDrinkSoldOutException() {
		try {
			Drink drink = Drink.FANTA;
			vendingMachineService.emptyDrink(drink);
			vendingMachineService.buy(drink, Coin.EURO_2);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertThat(e, CoreMatchers.instanceOf(DrinkSoldOutException.class));
		}
	}
	
	@Test
	public void testChangeNotAvailableException() {
		Drink drink = Drink.FANTA;
		Coin inputCoin = Coin.EURO_2;
		vendingMachineService.emptyCoin(Coin.CENT_50);
		vendingMachineService.emptyCoin(Coin.CENT_20);
		vendingMachineService.emptyCoin(Coin.CENT_10);
		try {
			vendingMachineService.buy(drink, inputCoin);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertThat(e, CoreMatchers.instanceOf(ChangeNotAvailableException.class));	
		}
		// No allowed if money of user is in machine
		vendingMachineService.emptyCoin(Coin.EURO_2);
		// Get money back if change not available
		Coin[] releasedCoinArray = vendingMachineService.releaseInputCoinArray();
		Assert.assertTrue(releasedCoinArray.length == 1);
		Assert.assertTrue(releasedCoinArray[0] == inputCoin);
	}
	
	@Test
	public void testReleaseInputCoins() {
		// Empty coin array
		Assert.assertTrue(vendingMachineService.releaseInputCoinArray().length == 0);
	}
	
	@Test
	public void testBuyExample() {
		try {
			DrinkAndChange drinkAndChange = vendingMachineService.buy(Drink.SPRITE, 
					Coin.EURO_2);
			Assert.assertTrue(drinkAndChange.getDrink() == Drink.SPRITE);		
			Coin[] changeCoinArray = drinkAndChange.getChangeCoinArray();			
			Assert.assertTrue(changeCoinArray[0] == Coin.CENT_50);
			Assert.assertTrue(changeCoinArray[1] == Coin.CENT_20);
			Assert.assertTrue(changeCoinArray[2] == Coin.CENT_10);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testBuyExample2() {
		Drink drink = Drink.SPRITE;
		// Coins in machine: 2 x 20 Cent and 5 x 10 Cent
		vendingMachineService.emptyCoin(Coin.CENT_50);
		vendingMachineService.emptyCoin(Coin.CENT_20);
		vendingMachineService.restockCoin(Coin.CENT_20);
		vendingMachineService.restockCoin(Coin.CENT_20);
		vendingMachineService.emptyCoin(Coin.CENT_10);
		vendingMachineService.restockCoin(Coin.CENT_10);
		vendingMachineService.restockCoin(Coin.CENT_10);
		vendingMachineService.restockCoin(Coin.CENT_10);
		vendingMachineService.restockCoin(Coin.CENT_10);
		vendingMachineService.restockCoin(Coin.CENT_10);
		try {
			DrinkAndChange drinkAndChange = vendingMachineService.buy(drink, Coin.EURO_2);
			Assert.assertTrue(drinkAndChange.getChangeCoinArray().length == 6);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testBuyWithoutChangeCoins() {
		Drink drink = Drink.SPRITE;
		try {
			DrinkAndChange drinkAndChange = vendingMachineService.buy(drink, 
					Coin.EURO_1, Coin.CENT_20);
			Assert.assertTrue(drinkAndChange.getChangeCoinArray().length == 0);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testRestockFunctions() {
		// Check drink restock limit (10)
		vendingMachineService.emptyDrink(Drink.FANTA);
		for (int i = 0; i < 15; i++) {
			vendingMachineService.restockDrink(Drink.FANTA);
		}
		Assert.assertTrue(vendingMachineService.getDrinkCount(Drink.FANTA) == 10);
		// Check coin restock limit (100)
		vendingMachineService.emptyCoin(Coin.CENT_10);
		for (int i = 0; i < 105; i++) {
			vendingMachineService.restockCoin(Coin.CENT_10);
		}
		Assert.assertTrue(vendingMachineService.getCoinCount(Coin.CENT_10) == 100);
	}
}