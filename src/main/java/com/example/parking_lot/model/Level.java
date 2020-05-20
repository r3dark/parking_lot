package com.example.parking_lot.model;

import java.util.Arrays;

/**
 * @author rohitsharma
 */
public class Level {

	private Vehicle[] slots;

	/**
	 *
	 * @return slots at a level
	 */
	public Vehicle[] getSlots() {
		return slots;
	}

	/**
	 *
	 * @param slots
	 * sets slots at a level
	 */
	public void setSlots(Vehicle[] slots) {
		this.slots = slots;
	}

	@Override
	public String toString() {
		return "Level{" +
				"level=" + Arrays.toString(slots) +
				'}';
	}
}
