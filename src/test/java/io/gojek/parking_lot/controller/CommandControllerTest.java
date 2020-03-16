package io.gojek.parking_lot.controller;

import io.gojek.parking_lot.exception.ParkingLotExceptionMessage;
import io.gojek.parking_lot.exception.handler.ParkingLotException;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CommandControllerTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	CommandController commandController = null;
	int lineNumber;

	@Before
	public void init() {
		commandController = new CommandController();
		lineNumber = 1;
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void reset() {
		System.setOut(originalOut);
	}

	@Test
	public void emptyCommandTest() throws Exception {

		String emptyCommand = "";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(emptyCommand, lineNumber);
	}

	@Test
	public void createParkingLotCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot 6";
		commandController.command(createParkingLotCommand, lineNumber);
		Assert.assertEquals("Created a parking lot with 6 slots\n", outContent.toString());
		commandController.parkingService.reset();
	}

	@Test
	public void createParkingLotInvalidCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(createParkingLotCommand, lineNumber);
	}

	@Test
	public void createParkingLotAgainCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot 6";
		commandController.command(createParkingLotCommand, lineNumber);
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_ALREADY_CREATED.getExceptionMessage());
		commandController.command(createParkingLotCommand, lineNumber);
	}

	@Test
	public void parkVehicleCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot 6";
		commandController.command(createParkingLotCommand, lineNumber);
		String parkCommand = "park KA-01-HH-1234 white";
		commandController.command(parkCommand, lineNumber);
		Assert.assertEquals("Created a parking lot with 6 slots\n" +
				"Allocated slot number: 1 at level L1\n", outContent.toString());
	}

	@Test
	public void parkVehicleWithoutParkingLotCreationCommandTest() throws Exception {

		String parkCommand = "park KA-01-HH-1234 white";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		commandController.command(parkCommand, lineNumber);
	}

	@Test
	public void parkVehicleInvalidCommandTest() throws Exception {

		String parkCommand = "paaark KA-01-HH-1234 white";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(parkCommand, lineNumber);
	}

	@Test
	public void parkVehicleMissingColorCommandTest() throws Exception {

		String parkCommand = "park KA-01-HH-1234";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(parkCommand, lineNumber);
	}

	@Test
	public void parkVehicleMissingRegistrationNumberCommandTest() throws Exception {

		String parkCommand = "park white";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(parkCommand, lineNumber);
	}

	@Test
	public void parkVehicleExtraParametersCommandTest() throws Exception {

		String parkCommand = "park KA-01-HH-1234 white blue red";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(parkCommand, lineNumber);
	}

	@Test
	public void unParkVehicleCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot 6";
		commandController.command(createParkingLotCommand, lineNumber);
		String unParkCommand = "leave 4";
		commandController.command(unParkCommand, lineNumber);
		Assert.assertEquals("Created a parking lot with 6 slots\n" +
				"Slot number 4 is free at level L1\n", outContent.toString());
	}

	@Test
	public void unParkVehicleWithoutParkingLotCreationCommandTest() throws Exception {

		String unParkCommand = "leave 4";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		commandController.command(unParkCommand, lineNumber);
	}

	@Test
	public void unParkVehicleMissingSlotNumberCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot 6";
		commandController.command(createParkingLotCommand, lineNumber);
		String unParkCommand = "leave";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(unParkCommand, lineNumber);
	}

	@Test
	public void unParkVehicleInvalidCommandTest() throws Exception {

		String unParkCommand = "leeeve 4";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(unParkCommand, lineNumber);
	}

	@Test
	public void unParkVehicleExtraParametersCommandTest() throws Exception {

		String unParkCommand = "leave 4 5 2";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(unParkCommand, lineNumber);
	}

	@Test
	public void parkingLotStatusCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot 6";
		commandController.command(createParkingLotCommand, lineNumber);
		String statusCommand = "status";
		commandController.command(statusCommand, lineNumber);
		Assert.assertEquals("Created a parking lot with 6 slots\n" +
				"Level Slot No. Registration No. Colour\n" +
				"\n", outContent.toString());
	}

	@Test
	public void parkingLotStatusWithoutParkingLotCreationCommandTest() throws Exception {

		String statusCommand = "status";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		commandController.command(statusCommand, lineNumber);
	}

	@Test
	public void parkingLotStatusEmptyCommandTest() throws Exception {

		String statusCommand = "";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(statusCommand, lineNumber);
	}

	@Test
	public void parkingLotStatusInvalidCommandTest() throws Exception {

		String statusCommand = "statusss";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(statusCommand, lineNumber);
	}

	@Test
	public void registrationNumberForCarsWithColorCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot 6";
		commandController.command(createParkingLotCommand, lineNumber);
		String parkCommand = "park KA-01-HH-1234 white";
		commandController.command(parkCommand, lineNumber);
		String registrationNumbersForCarsWithColorCommand = "registration_numbers_for_cars_with_colour White";
		commandController.command(registrationNumbersForCarsWithColorCommand, lineNumber);
		Assert.assertEquals("Created a parking lot with 6 slots\n" +
				"Allocated slot number: 1 at level L1\n" +
				"KA-01-HH-1234\n", outContent.toString());
	}

	@Test
	public void registrationNumberForCarsWithColorNoVehicleParkedCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot 6";
		commandController.command(createParkingLotCommand, lineNumber);
		String registrationNumbersForCarsWithColorCommand = "registration_numbers_for_cars_with_colour White";
		commandController.command(registrationNumbersForCarsWithColorCommand, lineNumber);
		Assert.assertEquals("Created a parking lot with 6 slots\n" +
				"Not found\n", outContent.toString());
	}

	@Test
	public void registrationNumberForCarsWithColorWithoutParkingLotCreationCommandTest() throws Exception {

		String registrationNumbersForCarsWithColorCommand = "registration_numbers_for_cars_with_colour White";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		commandController.command(registrationNumbersForCarsWithColorCommand, lineNumber);
	}

	@Test
	public void registrationNumberForCarsWithColorInvalidCommandTest() throws Exception {

		String registrationNumbersForCarsWithColorCommand = "registration_numberssssss_for_cars_with_colour";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(registrationNumbersForCarsWithColorCommand, lineNumber);
	}

	@Test
	public void registrationNumberForCarsWithColorMissingColorCommandTest() throws Exception {

		String registrationNumbersForCarsWithColorCommand = "registration_numbers_for_cars_with_colour";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(registrationNumbersForCarsWithColorCommand, lineNumber);
	}

	@Test
	public void slotNumbersForCarsWithColorCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot 6";
		commandController.command(createParkingLotCommand, lineNumber);
		String parkCommand = "park KA-01-HH-1234 white";
		commandController.command(parkCommand, lineNumber);
		String slotNumbersForCarsWithColorCommand = "slot_numbers_for_cars_with_colour White";
		commandController.command(slotNumbersForCarsWithColorCommand, lineNumber);
		Assert.assertEquals("Created a parking lot with 6 slots\n" +
				"Allocated slot number: 1 at level L1\n" +
				"Slot 1 at level L1\n", outContent.toString());
	}

	@Test
	public void slotNumberForCarsWithColorNoVehicleParkedCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot 6";
		commandController.command(createParkingLotCommand, lineNumber);
		String slotNumbersForCarsWithColorCommand = "slot_numbers_for_cars_with_colour White";
		commandController.command(slotNumbersForCarsWithColorCommand, lineNumber);
		Assert.assertEquals("Created a parking lot with 6 slots\n" +
				"Not found\n", outContent.toString());
	}

	@Test
	public void slotNumberForCarsWithColorWithoutParkingLotCreationCommandTest() throws Exception {

		String slotNumbersForCarsWithColorCommand = "slot_numbers_for_cars_with_colour White";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		commandController.command(slotNumbersForCarsWithColorCommand, lineNumber);
	}

	@Test
	public void slotNumberForCarsWithColorInvalidCommandTest() throws Exception {

		String slotNumbersForCarsWithColorCommand = "slot_numbersssss_for_cars_with_colour White";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(slotNumbersForCarsWithColorCommand, lineNumber);
	}

	@Test
	public void slotNumberForCarsWithColorMissingColorCommandTest() throws Exception {

		String slotNumbersForCarsWithColorCommand = "slot_numbers_for_cars_with_colour";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(slotNumbersForCarsWithColorCommand, lineNumber);
	}

	@Test
	public void slotNumberForRegistrationNumberCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot 6";
		commandController.command(createParkingLotCommand, lineNumber);
		String parkCommand = "park KA-01-HH-1234 white";
		commandController.command(parkCommand, lineNumber);
		String slotNumberForRegistrationNumberCommand = "slot_number_for_registration_number KA-01-HH-3141";
		commandController.command(slotNumberForRegistrationNumberCommand, lineNumber);
		Assert.assertEquals("Created a parking lot with 6 slots\n" +
				"Allocated slot number: 1 at level L1\n" +
				"Not found\n", outContent.toString());
	}

	@Test
	public void slotNumberForRegistrationNumberNoVehicleParkedCommandTest() throws Exception {

		String createParkingLotCommand = "create_parking_lot 6";
		commandController.command(createParkingLotCommand, lineNumber);
		String slotNumberForRegistrationNumberCommand = "slot_number_for_registration_number KA-01-HH-3141";
		commandController.command(slotNumberForRegistrationNumberCommand, lineNumber);
		Assert.assertEquals("Created a parking lot with 6 slots\n" +
				"Not found\n", outContent.toString());
	}

	@Test
	public void slotNumberForRegistrationNumberWithoutParkingLotCreationCommandTest() throws Exception {

		String slotNumberForRegistrationNumberCommand = "slot_number_for_registration_number KA-01-HH-3141";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		commandController.command(slotNumberForRegistrationNumberCommand, lineNumber);
	}

	@Test
	public void slotNumberForRegistrationNumberWithRegNoInvalidCommandTest() throws Exception {

		String slotNumberForRegistrationNumberCommand = "slot_numberrrrr_for_registration_number KA-01-HH-3141";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(slotNumberForRegistrationNumberCommand, lineNumber);
	}

	@Test
	public void slotNumberForRegistrationNumberMissingRegNoCommandTest() throws Exception {

		String slotNumberForRegistrationNumberCommand = "slot_number_for_registration_number";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(slotNumberForRegistrationNumberCommand, lineNumber);
	}

	@Test
	public void parkingLotResetCommandTest() throws Exception {

		String resetCommand = "reset";
		commandController.command(resetCommand, lineNumber);
		Assert.assertEquals("Parking lot reset successful.\n", outContent.toString());
	}

	@Test
	public void parkingLotResetInvalidCommandTest() throws Exception {

		String resetCommand = "reseet";
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_COMMAND.getExceptionMessage());
		commandController.command(resetCommand, lineNumber);
	}
}

