package service;

import model.ParkingLot;
import model.Vehicle;

import java.util.List;

public interface ParkingService {

	public void createParkingLot() throws Exception;

	public Integer parkVehicle(Vehicle vehicle) throws Exception;

	public void unParkVehicle(Integer slot) throws Exception;

	public ParkingLot getParkingLotStatus() throws Exception;

	public List<String> getRegistrationNumbersByColor(String color) throws Exception;

	public List<Integer> getSlotNumbersByColor(String color) throws Exception;

	public Integer getSlotByRegistrationNumber(String vehicleRegistrationNumber) throws Exception;

	public Integer getEmptySlotsCount() throws Exception;

	public void cleanUp();
}
