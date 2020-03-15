package service.impl;

import Contant.ApplicationProperties;
import model.Level;
import model.ParkingLot;
import model.Vehicle;
import service.ParkingService;

import java.util.ArrayList;
import java.util.List;

public class ParkingServiceImpl implements ParkingService {

	ParkingLot parkingLot = new ParkingLot();

	@Override
	public String createParkingLot(Integer capacity) throws Exception {

		int levelsRequired = computeLevels(capacity);
		int tempCapacity = capacity;

		List<Level> parkingLotLevelsList = new ArrayList<>(levelsRequired);

		for (int i = 0; i < levelsRequired; i++) {
			if (tempCapacity >= ApplicationProperties.DEFAULT_LEVEL_SIZE) {
				createParkingLotLevels(parkingLotLevelsList, ApplicationProperties.DEFAULT_LEVEL_SIZE);
			} else {
				createParkingLotLevels(parkingLotLevelsList, tempCapacity);
			}
			tempCapacity -= ApplicationProperties.DEFAULT_LEVEL_SIZE;
		}

		parkingLot.setParkingLot(parkingLotLevelsList);
		return ApplicationProperties.PARKING_LOT_CREATED_MESSAGE.replace("{$}", String.valueOf(capacity));
	}

	private void createParkingLotLevels(List<Level> parkingLotLevelsList, Integer capacity) {

		Vehicle[] slotList = new Vehicle[capacity];
		Level level = new Level();
		level.setSlots(slotList);
		parkingLotLevelsList.add(level);
	}

	private int computeLevels(Integer capacity) {

		if ((capacity % ApplicationProperties.DEFAULT_LEVEL_SIZE) == 0) {
			return (capacity / ApplicationProperties.DEFAULT_LEVEL_SIZE);
		} else {
			return ((capacity / ApplicationProperties.DEFAULT_LEVEL_SIZE) + 1);
		}
	}

	@Override
	public String parkVehicle(Vehicle vehicle) throws Exception {

		for (Level level : parkingLot.getParkingLot()) {
			for (int i = 0; i < level.getSlots().length; i++) {
				if (level.getSlots()[i] == null) {
					level.getSlots()[i] = vehicle;
					return ApplicationProperties.PARKING_ALLOCATED_MESSAGE.replace("{$}", String.valueOf(i));
				}
			}
		}
		return ApplicationProperties.PARKING_FULL_MESSAGE;
	}

	@Override
	public String unParkVehicle(Integer slot) throws Exception {

		int level = computeLevels(slot);
		parkingLot.getParkingLot().get(level).getSlots()[slot] = null;
		return ApplicationProperties.PARKING_UNALLOCATED_MESSAGE.replace("{$}", String.valueOf(slot));
	}

	@Override
	public String getParkingLotStatus() throws Exception {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Slot No. ").append("Registration No ").append("Colour\n");
		for (Level level : parkingLot.getParkingLot()) {
			for (int i = 0; i < level.getSlots().length; i++) {
				stringBuilder.append(i).append(" ")
						.append(level.getSlots()[i].getVehicleRegistrationNumber()).append(" ")
						.append(level.getSlots()[i].getVehicleColor()).append("\n");
			}
		}
		return stringBuilder.toString();
	}

	@Override
	public List<String> getRegistrationNumbersByColor(String color) throws Exception {

		List<String> registrationNumbers = new ArrayList<>();
		for (Level level : parkingLot.getParkingLot()) {
			for (int i = 0; i < level.getSlots().length; i++) {
				if (level.getSlots()[i].getVehicleColor().equals(color)) {
					registrationNumbers.add(level.getSlots()[i].getVehicleRegistrationNumber());
				}
			}
		}
		return registrationNumbers;
	}

	@Override
	public List<Integer> getSlotNumbersByColor(String color) throws Exception {

		List<Integer> slotNumbers = new ArrayList<>();
		for (Level level : parkingLot.getParkingLot()) {
			for (int i = 0; i < level.getSlots().length; i++) {
				if (level.getSlots()[i].getVehicleColor().equals(color)) {
					slotNumbers.add(i);
				}
			}
		}
		return slotNumbers;
	}

	@Override
	public String getSlotByRegistrationNumber(String vehicleRegistrationNumber) throws Exception {

		for (Level level : parkingLot.getParkingLot()) {
			for (int i = 0; i < level.getSlots().length; i++) {
				if (level.getSlots()[i].getVehicleRegistrationNumber().equals(vehicleRegistrationNumber)) {
					return String.valueOf(i);
				}
			}
		}
		return ApplicationProperties.NOT_FOUND;
	}
}
