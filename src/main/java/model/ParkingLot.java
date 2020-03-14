package model;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {

	private Map<Integer, Vehicle> slot;

	public ParkingLot(Integer parkingLotCapacity) {
		slot = new HashMap<>(parkingLotCapacity, 1);
	}

	public Map<Integer, Vehicle> getSlot() {
		return slot;
	}

	public void setSlot(Map<Integer, Vehicle> slot) {
		this.slot = slot;
	}

	@Override
	public String toString() {
		return "ParkingLot{" +
				"slot=" + slot +
				'}';
	}
}
