package service;

import model.Level;
import model.Vehicle;

import java.util.List;

public interface ParkingService {

	public String createParkingLot(Integer capacity) throws Exception;

	public String parkVehicle(Vehicle vehicle) throws Exception;

	public String unParkVehicle(Integer slot) throws Exception;

	public String getParkingLotStatus() throws Exception;

	public List<String> getRegistrationNumbersByColor(String color) throws Exception;

	public List<Integer> getSlotNumbersByColor(String color) throws Exception;

	public String getSlotByRegistrationNumber(String vehicleRegistrationNumber) throws Exception;
}
