package io.gojek.parking_lot.processor;

import io.gojek.parking_lot.contant.ApplicationProperties;
import io.gojek.parking_lot.exception.ParkingLotExceptionMessage;
import io.gojek.parking_lot.exception.handler.ParkingLotException;
import io.gojek.parking_lot.model.Vehicle;
import io.gojek.parking_lot.service.impl.ParkingServiceImpl;
/**
 * @author rohitsharma
 */
public class CommandProcessor {

	ParkingServiceImpl parkingService;

	public CommandProcessor() {
		parkingService = new ParkingServiceImpl();
	}

	public void command (String command, int lineNumber) throws ParkingLotException {

		String response = "";
		String[] splitCommand = command.split(" ");
		switch (splitCommand[0].toLowerCase()) {
			case ApplicationProperties.CREATE_PARKING_LOT : {
				try {
					if ((splitCommand.length == 2) && (splitCommand[1] != null)) {
						response = parkingService.createParkingLot(Integer.valueOf(splitCommand[1]));
					} else {
						throw new ParkingLotException(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
					}
				} catch (Exception e) {
					if (e.getMessage().equals(ParkingLotExceptionMessage.PARKING_LOT_ALREADY_CREATED.getExceptionMessage())) {
						System.out.println("Attempt to create parking lot again. Line number: " + lineNumber);
					}
					throw e;
				}
				break;
			}
			case ApplicationProperties.PARK_VEHICLE : {
				if ((splitCommand.length == 3) && (splitCommand[1] != null) && (splitCommand[2] != null)) {
					Vehicle vehicle = new Vehicle();
					vehicle.setVehicleRegistrationNumber(splitCommand[1]);
					vehicle.setVehicleColor(splitCommand[2]);
					response = parkingService.parkVehicle(vehicle);
				} else {
					throw new ParkingLotException(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
				}
				break;
			}
			case ApplicationProperties.UN_PARK_VEHICLE : {
				if ((splitCommand.length == 2) && (splitCommand[1] != null)) {
					response = parkingService.unParkVehicle(Integer.valueOf(splitCommand[1]));
				} else {
					throw new ParkingLotException(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
				}
				break;
			}
			case ApplicationProperties.PARKING_LOT_STATUS : {
				try {
					if (splitCommand.length == 1) {
						response = parkingService.getParkingLotStatus();
					} else {
						throw new ParkingLotException(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			case ApplicationProperties.REGISTRATION_NUMBER_FOR_CARS_WITH_COLOR : {
				if ((splitCommand.length == 2) && (splitCommand[1] != null)) {
					response = parkingService.getRegistrationNumbersByColor(splitCommand[1]);
				} else {
					throw new ParkingLotException(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
				}
				break;
			}
			case ApplicationProperties.SLOT_NUMBERS_FOR_CARS_WITH_COLOR : {
				if ((splitCommand.length == 2) && (splitCommand[1] != null)) {
					response = parkingService.getSlotNumbersByColor(splitCommand[1]);
				} else {
					throw new ParkingLotException(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
				}
				break;
			}
			case ApplicationProperties.SLOT_NUMBER_FOR_REGISTRATION_NUMBER : {
				if ((splitCommand.length == 2) && (splitCommand[1] != null)) {
					response = parkingService.getSlotByRegistrationNumber(splitCommand[1]);
				} else {
					throw new ParkingLotException(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
				}
				break;
			}
			default : {
				if (lineNumber == 0) {
					throw new ParkingLotException(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage()
							.concat(". Command: " + command));
				} else {
					throw new ParkingLotException(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage()
							.concat(" at line ").concat(String.valueOf(lineNumber))
							.concat(". Command: ").concat(command));
				}
			}
		}
		System.out.println(response);
	}
}
