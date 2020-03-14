package service.impl;

import model.ParkingLot;
import model.Vehicle;
import service.ParkingService;

import java.util.List;

public class CarParkingServiceImpl implements ParkingService {


	@Override
	public void createParkingLot() throws Exception {

	}

	@Override
	public Integer parkVehicle(Vehicle vehicle) throws Exception {
		return null;
	}

	@Override
	public void unParkVehicle(Integer slot) throws Exception {

	}

	@Override
	public ParkingLot getParkingLotStatus() throws Exception {
		return null;
	}

	@Override
	public List<String> getRegistrationNumbersByColor(String color) throws Exception {
		return null;
	}

	@Override
	public List<Integer> getSlotNumbersByColor(String color) throws Exception {
		return null;
	}

	@Override
	public Integer getSlotByRegistrationNumber(String vehicleRegistrationNumber) throws Exception {
		return null;
	}

	@Override
	public Integer getEmptySlotsCount() throws Exception {
		return null;
	}

	@Override
	public void cleanUp() {

	}
}
