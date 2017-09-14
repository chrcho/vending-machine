package de.chrcho.vendingmachine.model;

import org.junit.Assert;
import org.junit.Test;

import de.chrcho.vendingmachine.Coin;

public class DrinkTest {

	@Test
	public void testDrink() {
		Assert.assertTrue(Coin.values().length == 5);
		Assert.assertTrue(Coin.valueOf("EURO_1") == Coin.EURO_1);
	}
}