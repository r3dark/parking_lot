package model;

import java.util.List;

public class ParkingLot {

	private List<Level> parkingLot;

	public List<Level> getParkingLot() {
		return parkingLot;
	}

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
