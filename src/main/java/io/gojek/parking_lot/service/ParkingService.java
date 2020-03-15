package io.gojek.parking_lot.service;

import io.gojek.parking_lot.model.Vehicle;

/**
 * @author rohitsharma
 */
public interface ParkingService {

	public String createParkingLot(Integer capacity) throws Exception;

	public String parkVehicle(Vehicle vehicle) throws Exception;

	public String unParkVehicle(Integer slot) throws Exception;

	public String getParkingLotStatus() throws Exception;

	public String getRegistrationNumbersByColor(String color) throws Exception;

	public String getSlotNumbersByColor(String color) throws Exception;

	public String getSlotByRegistrationNumber(String vehicleRegistrationNumber) throws Exception;
}
