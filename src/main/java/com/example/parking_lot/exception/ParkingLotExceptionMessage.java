package com.example.parking_lot.exception;

/**
 * @author rohitsharma
 */
public enum ParkingLotExceptionMessage {

	PARKING_LOT_ALREADY_CREATED ("Parking lot already created."),
	PARKING_LOT_NOT_EXIST_ERROR ("Parking lot does't exist, please create one."),
	INVALID_COMMAND ("Invalid command entered"),
	INVALID_PARKING_LOT_SIZE_ERROR ("parking_lot_size can't be less than 1."),
	INVALID_PARKING_LOT_SLOT_ERROR ("slot_number can't be less than 1."),
	EMPTY_FILE ("Input file is empty."),
	REQUEST_PROCESSING_ERROR ("There was an error processing your request. "),
	PARKING_LEVEL_NOT_EXIST_ERROR ("Parking level does't exit."),
	ERROR_WHILE_CREATING_PARKING_LOT ("Error occurred while creating parking lot."),
	ERROR_WHILE_PARKING_VEHICLE ("Error occurred while parking vehicle. "),
	ERROR_WHILE_UN_PARKING_VEHICLE ("Error occurred while un-parking vehicle at slot: "),
	ERROR_WHILE_GETTING_PARKING_LOT_STATUS ("Error occurred while getting parking lot status."),
	ERROR_WHILE_GETTING_REGISTRATION_NUMBERS_BY_COLOR ("Error occurred while getting registration numbers by color {$color}"),
	ERROR_WHILE_GETTING_SLOT_NUMBERS_BY_COLOR ("Error occurred while getting slot numbers by color {$color}"),
	ERROR_WHILE_GETTING_SLOT_BY_REGISTRATION_NUMBER ("Error occurred while getting slot number with registration number {$registration_number}");

	private final String exceptionMessage;

	ParkingLotExceptionMessage(String message) {
		this.exceptionMessage = message;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}
}
