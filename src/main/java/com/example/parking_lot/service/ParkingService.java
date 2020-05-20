package com.example.parking_lot.service;

import com.example.parking_lot.model.Vehicle;
import com.example.parking_lot.exception.handler.ParkingLotException;

/**
 * @author rohitsharma
 */
public interface ParkingService {

	public String createParkingLot(Integer capacity) throws ParkingLotException;

	public String parkVehicle(Vehicle vehicle) throws ParkingLotException;

	public String unParkVehicle(Integer slot) throws ParkingLotException;

	public String getParkingLotStatus() throws ParkingLotException;

	public String getRegistrationNumbersByColor(String color) throws ParkingLotException;

	public String getSlotNumbersByColor(String color) throws ParkingLotException;

	public String getSlotByRegistrationNumber(String vehicleRegistrationNumber) throws ParkingLotException;

	public String reset();
}
