package model;

import java.util.Arrays;

public class Level {

	private Vehicle[] slots;

	public Vehicle[] getSlots() {
		return slots;
	}

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
