package de.chrcho.vendingmachine.model;

import org.junit.Assert;
import org.junit.Test;

import de.chrcho.vendingmachine.Drink;

public class CoinTest {

	@Test
	public void testCoin() {
		Assert.assertTrue(Drink.values().length == 3);
		Assert.assertTrue(Drink.valueOf("SPRITE") == Drink.SPRITE);
	}
}