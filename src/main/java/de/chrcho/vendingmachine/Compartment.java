package de.chrcho.vendingmachine;

/**
 * Base class of compartment with count.
 * 
 * @author Chrcho
 */
public class Compartment {

	private int count;

	public Compartment(int count) {
		this.setCount(count);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void increaseCountByOne() {
		this.count++;
	}
	
	public void decreaseCountByOne() {
		this.count--;
	}
}