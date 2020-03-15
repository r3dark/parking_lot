package io.gojek.parking_lot.model;

import java.util.List;

/**
 * @author rohitsharma
 */
public class ParkingLot {

	private List<Level> parkingLot;

	/**
	 *
	 * @return parkingLot
	 */
	public List<Level> getParkingLot() {
		return parkingLot;
	}

	/**
	 *
	 * @param parkingLot
	 * set parking lot
	 */
	public void setParkingLot(List<Level> parkingLot) {
		this.parkingLot = parkingLot;
	}

	@Override
	public String toString() {
		return "ParkingLot{" +
				"parkingLot=" + parkingLot +
				'}';
	}
}
