package de.chrcho.vendingmachine.model;

import org.junit.Assert;
import org.junit.Test;

import de.chrcho.vendingmachine.Coin;
import de.chrcho.vendingmachine.CoinCompartment;

public class CoinCompartmentTest {

	@Test
	public void testCoinCompartment() {
		CoinCompartment coinCompartment = new CoinCompartment(Coin.CENT_10, 5);
		Assert.assertTrue(coinCompartment.getCoin() == Coin.CENT_10);
		Assert.assertTrue(coinCompartment.getCount() == 5);
	}
}