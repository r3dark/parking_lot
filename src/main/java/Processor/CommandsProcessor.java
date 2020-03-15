package Processor;

import Contant.ApplicationProperties;
import model.Vehicle;
import service.ParkingService;

public class CommandsProcessor {

	ParkingService parkingService;

	public void setParkingService(ParkingService parkingService) throws Exception {
		this.parkingService = parkingService;
	}

	public void command (String command) {

		String[] splitCommand = command.split(" ");
		switch (splitCommand[0]) {
			case ApplicationProperties.CREATE_PARKING_LOT :
				try {
					parkingService.createParkingLot(Integer.valueOf(splitCommand[1]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			case ApplicationProperties.PARK_VEHICLE :
				try {
					Vehicle vehicle = new Vehicle();
					vehicle.setVehicleRegistrationNumber(splitCommand[1]);
					vehicle.setVehicleColor(splitCommand[2]);
					parkingService.parkVehicle(vehicle);
				} catch (Exception e) {
					e.printStackTrace();
				}
			case ApplicationProperties.UN_PARK_VEHICLE :
				try {
					parkingService.unParkVehicle(Integer.valueOf(splitCommand[1]));
				} catch (Exception e) {
					e.printStackTrace();
				}
			case ApplicationProperties.PARKING_LOT_STATUS :
				try {
					parkingService.getParkingLotStatus();
				} catch (Exception e) {
					e.printStackTrace();
				}
			case ApplicationProperties.REGISTRATION_NUMBER_FOR_CARS_WITH_COLOR :
				try {
					parkingService.getRegistrationNumbersByColor(splitCommand[1]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			case ApplicationProperties.SLOT_NUMBERS_FOR_CARS_WITH_COLOR :
				try {
					parkingService.getSlotNumbersByColor(splitCommand[1]);
				} catch (Exception e) {
					e.printStackTrace();
				}
			case ApplicationProperties.SLOT_NUMBER_FOR_REGISTRATION_NUMBER :
				try {
					parkingService.getSlotByRegistrationNumber(splitCommand[1]);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
}
