package de.chrcho.vendingmachine;

/**
 * Compartment of coins.
 * 
 * @author Chrcho
 */
public class CoinCompartment extends Compartment {

	private Coin coin;
	
	public CoinCompartment(Coin coin, int count) {
		super(count);
		this.setCoin(coin);
	}

	public Coin getCoin() {
		return coin;
	}

	public void setCoin(Coin coin) {
		this.coin = coin;
	}
}