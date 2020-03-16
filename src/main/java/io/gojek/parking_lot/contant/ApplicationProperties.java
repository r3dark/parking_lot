package io.gojek.parking_lot.contant;

/**
 * @author rohitsharma
 */
public class ApplicationProperties {

	// commands
	public static final String CREATE_PARKING_LOT = "create_parking_lot";

	public static final String PARK_VEHICLE = "park";

	public static final String UN_PARK_VEHICLE = "leave";

	public static final String PARKING_LOT_STATUS = "status";

	public static final String REGISTRATION_NUMBER_FOR_CARS_WITH_COLOR = "registration_numbers_for_cars_with_colour";

	public static final String SLOT_NUMBERS_FOR_CARS_WITH_COLOR = "slot_numbers_for_cars_with_colour";

	public static final String SLOT_NUMBER_FOR_REGISTRATION_NUMBER = "slot_number_for_registration_number";

	public static final String RESET_PARKING_LOT = "reset";

	// Responses
	public static final String NOT_FOUND = "Not found";

	public static final String PARKING_LOT_CREATED_MESSAGE = "Created a parking lot with {$capacity} slots";

	public static final String PARKING_FULL_MESSAGE = "Sorry, parking lot is full";

	public static final String PARKING_ALLOCATED_MESSAGE = "Allocated slot number: {$slot} at level {$level}";

	public static final String PARKING_UNALLOCATED_MESSAGE = "Slot number {$slot} is free at level {$level}";

	public static final String VEHICLE_ALREADY_PARKED_MESSAGE = "Vehicle with {$registration_number} registration number is already parked";

	public static final String UNABLE_TO_DEALLOCATE_PARKING_MESSAGE = "Sorry, we are unable to deallocate the parking at {$slot}";

	public static final String PARKING_LOT_RESET_SUCCESSFUL_MESSAGE = "Parking lot reset successful.";

	// Miscellaneous
	public static final Integer DEFAULT_LEVEL_SIZE = 10;
}
