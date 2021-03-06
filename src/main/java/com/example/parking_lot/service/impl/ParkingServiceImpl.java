package com.example.parking_lot.service.impl;

import com.example.parking_lot.model.Level;
import com.example.parking_lot.model.ParkingLot;
import com.example.parking_lot.model.Vehicle;
import com.example.parking_lot.properties.ApplicationProperties;
import com.example.parking_lot.service.ParkingService;
import com.example.parking_lot.exception.handler.ParkingLotException;
import com.example.parking_lot.exception.ParkingLotExceptionMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rohitsharma
 */
public class ParkingServiceImpl implements ParkingService {

	ParkingLot parkingLot = new ParkingLot();

	/**
	 *
	 * @param capacity
	 * capacity of parking lot
	 */
	@Override
	public String createParkingLot(Integer capacity) throws ParkingLotException {

		if (parkingLot.getParkingLot() != null) {
			throw new ParkingLotException(ParkingLotExceptionMessage.PARKING_LOT_ALREADY_CREATED.getExceptionMessage());
		}

		if (capacity < 1) {
			throw new ParkingLotException(ParkingLotExceptionMessage.INVALID_PARKING_LOT_SIZE_ERROR.getExceptionMessage());
		}

		try {
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
			return ApplicationProperties.PARKING_LOT_CREATED_MESSAGE.replace("{$capacity}", String.valueOf(capacity));
		} catch (Exception e) {
			throw new ParkingLotException(ParkingLotExceptionMessage.ERROR_WHILE_CREATING_PARKING_LOT.getExceptionMessage(), e);
		}
	}

	/**
	 *
	 * @param parkingLotLevelsList
	 * List of levels
	 * @param capacity
	 * capacity of parking lot
	 */
	private void createParkingLotLevels(List<Level> parkingLotLevelsList, Integer capacity) {

		Vehicle[] slotList = new Vehicle[capacity];
		Level level = new Level();
		level.setSlots(slotList);
		parkingLotLevelsList.add(level);
	}

	/**
	 *
	 * @param capacity
	 * capacity of parking lot
	 */
	private int computeLevels(Integer capacity) {

		if ((capacity % ApplicationProperties.DEFAULT_LEVEL_SIZE) == 0) {
			return (capacity / ApplicationProperties.DEFAULT_LEVEL_SIZE);
		} else {
			return ((capacity / ApplicationProperties.DEFAULT_LEVEL_SIZE) + 1);
		}
	}

	/**
	 *
	 * @param vehicle
	 * vehicle to be parked
	 */
	@Override
	public String parkVehicle(Vehicle vehicle) throws ParkingLotException {

		validateParkingLot();
		try {
			int levelNumber = 1;
			for (Level level : parkingLot.getParkingLot()) {
				validateLevel(level);
				for (int i = 0; i < level.getSlots().length; i++) {
					if (level.getSlots()[i] == null) {
						level.getSlots()[i] = vehicle;
						return ApplicationProperties.PARKING_ALLOCATED_MESSAGE.replace("{$slot}", String.valueOf(i + 1)).replace("{$level}", "L" .concat(String.valueOf(levelNumber)));
					} else if (level.getSlots()[i].getVehicleRegistrationNumber().equals(vehicle.getVehicleRegistrationNumber())) {
						return ApplicationProperties.VEHICLE_ALREADY_PARKED_MESSAGE.replace("{$registration_number}", vehicle.getVehicleRegistrationNumber());
					}
				}
				levelNumber++;
			}
			return ApplicationProperties.PARKING_FULL_MESSAGE;
		} catch (Exception e) {
			throw new ParkingLotException(ParkingLotExceptionMessage.ERROR_WHILE_PARKING_VEHICLE.getExceptionMessage().concat(vehicle.getVehicleRegistrationNumber()), e);
		}
	}

	/**
	 *
	 * @param slot
	 * slot to be emptied
	 */
	@Override
	public String unParkVehicle(Integer slot) throws ParkingLotException {

		validateParkingLot();
		if (slot < 1) {
			throw new ParkingLotException(ParkingLotExceptionMessage.INVALID_PARKING_LOT_SLOT_ERROR.getExceptionMessage());
		}
		try {
			int level = computeLevels(slot);
			if (slot > ApplicationProperties.DEFAULT_LEVEL_SIZE) {
				slot /= ApplicationProperties.DEFAULT_LEVEL_SIZE;
			}
			if ((parkingLot.getParkingLot().get(level - 1) != null) && (parkingLot.getParkingLot().get(level - 1).getSlots() != null)) {
				parkingLot.getParkingLot().get(level - 1).getSlots()[slot - 1] = null;
				return ApplicationProperties.PARKING_UNALLOCATED_MESSAGE.replace("{$slot}", String.valueOf(slot)).replace("{$level}", "L".concat(String.valueOf(level)));
			} else {
				return ApplicationProperties.UNABLE_TO_DEALLOCATE_PARKING_MESSAGE.replace("{$slot}", String.valueOf(slot));
			}
		} catch (Exception e) {
			throw new ParkingLotException(ParkingLotExceptionMessage.ERROR_WHILE_UN_PARKING_VEHICLE.getExceptionMessage().concat(String.valueOf(slot)), e);
		}
	}

	/**
	 *
	 * get parking lot status
	 */
	@Override
	public String getParkingLotStatus() throws ParkingLotException {

		validateParkingLot();
		try {
			StringBuilder stringBuilder = new StringBuilder();
			int levelNumber = 1;
			stringBuilder.append("Level ").append("Slot No. ").append("Registration No. ").append("Colour\n");
			for (Level level : parkingLot.getParkingLot()) {
				validateLevel(level);
				for (int i = 0; i < level.getSlots().length; i++) {
					if (level.getSlots()[i] != null) {
						stringBuilder.append("  L").append(levelNumber).append("     ")
								.append(i + 1).append("     ")
								.append(level.getSlots()[i].getVehicleRegistrationNumber()).append("     ")
								.append(level.getSlots()[i].getVehicleColor()).append("\n");
					}
				}
				levelNumber++;
			}
			return stringBuilder.toString();
		} catch (Exception e) {
			throw new ParkingLotException(ParkingLotExceptionMessage.ERROR_WHILE_GETTING_PARKING_LOT_STATUS.getExceptionMessage(), e);
		}
	}

	/**
	 *
	 * @param color
	 * color by which registration numbers need to be fetched
	 */
	@Override
	public String getRegistrationNumbersByColor(String color) throws ParkingLotException {

		validateParkingLot();
		try {
			List<String> registrationNumbers = new ArrayList<>();
			for (Level level : parkingLot.getParkingLot()) {
				validateLevel(level);
				for (int i = 0; i < level.getSlots().length; i++) {
					if ((level.getSlots()[i] != null) && level.getSlots()[i].getVehicleColor().equalsIgnoreCase(color)) {
						registrationNumbers.add(level.getSlots()[i].getVehicleRegistrationNumber());
					}
				}
			}

			if (registrationNumbers.size() != 0) {
				return registrationNumbers.toString().replace("[", "").replace("]", "");
			} else {
				return ApplicationProperties.NOT_FOUND;
			}
		} catch (Exception e) {
			throw new ParkingLotException(ParkingLotExceptionMessage.ERROR_WHILE_GETTING_REGISTRATION_NUMBERS_BY_COLOR.getExceptionMessage().replace("{$color}", color), e);

		}
	}

	/**
	 *
	 * @param color
	 * color by which slot numbers need to be fetched
	 */
	@Override
	public String getSlotNumbersByColor(String color) throws ParkingLotException {

		validateParkingLot();
		try {
			int levelNumber = 1;
			List<String> slotNumbers = new ArrayList<>();
			for (Level level : parkingLot.getParkingLot()) {
				validateLevel(level);
				for (int i = 0; i < level.getSlots().length; i++) {
					if ((level.getSlots()[i] != null) && level.getSlots()[i].getVehicleColor().equalsIgnoreCase(color)) {
						slotNumbers.add("Slot " .concat(String.valueOf((i + 1))).concat(" at level L").concat(String.valueOf(levelNumber)));
					}
				}
				levelNumber++;
			}
			if (slotNumbers.size() != 0) {
				return slotNumbers.toString().replace("[", "").replace("]", "");
			} else {
				return ApplicationProperties.NOT_FOUND;
			}
		} catch (Exception e) {
			throw new ParkingLotException(ParkingLotExceptionMessage.ERROR_WHILE_GETTING_SLOT_NUMBERS_BY_COLOR.getExceptionMessage().replace("{$color}", color), e);
		}
	}

	/**
	 *
	 * @param vehicleRegistrationNumber
	 * vehicle registration number by which slot numbers need to be fetched
	 */
	@Override
	public String getSlotByRegistrationNumber(String vehicleRegistrationNumber) throws ParkingLotException {

		validateParkingLot();
		try {
			int levelNumber = 1;
			for (Level level : parkingLot.getParkingLot()) {
				validateLevel(level);
				for (int i = 0; i < level.getSlots().length; i++) {
					if ((level.getSlots()[i] != null) && level.getSlots()[i].getVehicleRegistrationNumber().equalsIgnoreCase(vehicleRegistrationNumber)) {
						return ("Slot " .concat(String.valueOf(i + 1)).concat(" at level L").concat(String.valueOf(levelNumber)));
					}
				}
				levelNumber++;
			}
			return ApplicationProperties.NOT_FOUND;
		} catch (Exception e) {
			throw new ParkingLotException(ParkingLotExceptionMessage.ERROR_WHILE_GETTING_SLOT_BY_REGISTRATION_NUMBER.getExceptionMessage().replace("{$registration_number}", vehicleRegistrationNumber), e);
		}
	}

	/**
	 *
	 * reset parking lot
	 */
	@Override
	public String reset() {
		parkingLot = new ParkingLot();
		return ApplicationProperties.PARKING_LOT_RESET_SUCCESSFUL_MESSAGE;
	}

	/**
	 *
	 * validate parking lot
	 */
	private void validateParkingLot() throws ParkingLotException {
		if (parkingLot.getParkingLot() == null) {
			throw new ParkingLotException(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		}
	}

	/**
	 *
	 * validate parking lot level
	 */
	private void validateLevel(Level level) throws ParkingLotException {
		if (level.getSlots() == null) {
			throw new ParkingLotException(ParkingLotExceptionMessage.PARKING_LEVEL_NOT_EXIST_ERROR.getExceptionMessage());
		}
	}
}
