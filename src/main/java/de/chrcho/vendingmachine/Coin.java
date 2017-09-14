package de.chrcho.vendingmachine;

/**
 * Existing coins of vending machine.
 * 
 * @author Chrcho
 */
public enum Coin {

	CENT_10(10), CENT_20(20), CENT_50(50), EURO_1(100), EURO_2(200);
	
	private int value;
	
	private Coin(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	/**
	 * Fetch all coins in descending value order.
	 * 
	 * @return Coin array in descending value order
	 */
	public static Coin[] valuesOrderByValueDesc() {
		Coin[] coinArray = Coin.values();
		Coin coinTemp;
		for (int i = 0; i < coinArray.length - 1; i++) {
			for (int j = 1; j < coinArray.length - i; j++) {
				if (coinArray[j - 1].getValue() < coinArray[j].getValue()) {
					coinTemp = coinArray[j - 1];
					coinArray[j - 1] = coinArray[j];
					coinArray[j] = coinTemp;
				}
			}
		}
		return coinArray;
	}
}