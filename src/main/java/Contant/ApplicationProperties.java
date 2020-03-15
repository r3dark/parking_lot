package Contant;

public class ApplicationProperties {

	// commands
	public static final String CREATE_PARKING_LOT = "create_parking_lot";

	public static final String PARK_VEHICLE = "park";

	public static final String UN_PARK_VEHICLE = "leave";

	public static final String PARKING_LOT_STATUS = "status";

	public static final String REGISTRATION_NUMBER_FOR_CARS_WITH_COLOR = "registration_numbers_for_cars_with_colour";

	public static final String SLOT_NUMBERS_FOR_CARS_WITH_COLOR = "slot_numbers_for_cars_with_colour";

	public static final String SLOT_NUMBER_FOR_REGISTRATION_NUMBER = "slot_number_for_registration_number";

	// Responses
	public static final String NOT_FOUND = "Not found";

	public static final String PARKING_LOT_CREATED_MESSAGE = "Created a parking lot with {$} slots";

	public static final String PARKING_FULL_MESSAGE = "Sorry, parking lot is full";

	public static final String PARKING_ALLOCATED_MESSAGE = "Allocated slot number: {$}";

	public static final String PARKING_UNALLOCATED_MESSAGE = "Slot number {$} is free";

	// Miscellaneous
	public static final Integer DEFAULT_LEVEL_SIZE = 10;

	public static final String PARKING_LOT_EMPTIED = "Parking lot emptied";
}
