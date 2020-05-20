package com.example.parking_lot.service;

import com.example.parking_lot.exception.ParkingLotExceptionMessage;
import com.example.parking_lot.exception.handler.ParkingLotException;
import com.example.parking_lot.model.Vehicle;
import com.example.parking_lot.service.impl.ParkingServiceImpl;
import org.junit.*;
import org.junit.rules.ExpectedException;

/**
 * @author rohitsharma
 */
public class ParkingLotServiceTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	ParkingService parkingService = null;

	@Before
	public void init() {
		parkingService = new ParkingServiceImpl();
	}

	@After
	public void reset() {
		parkingService.reset();
	}

	@Test
	public void createParkingLotTest() throws Exception {

		String response = parkingService.createParkingLot(20);
		Assert.assertEquals("Created a parking lot with 20 slots", response);
	}

	@Test
	public void parkingAlreadyExistTest() throws Exception {

		parkingService.createParkingLot(20);
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_ALREADY_CREATED.getExceptionMessage());
		parkingService.createParkingLot(20);
	}

	@Test
	public void parkingLotZeroCapacityTest() throws Exception {

		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_PARKING_LOT_SIZE_ERROR.getExceptionMessage());
		parkingService.createParkingLot(0);
	}

	@Test
	public void parkingLotNegativeCapacityTest() throws Exception {

		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_PARKING_LOT_SIZE_ERROR.getExceptionMessage());
		parkingService.createParkingLot(-1);
	}

	@Test
	public void validateParkingLotWhileParkingTest() throws Exception {

		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleColor("white");
		vehicle.setVehicleRegistrationNumber("KA-01-BB-0001");
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		parkingService.parkVehicle(vehicle);
	}

	@Test
	public void parkVehicleTest() throws Exception {

		parkingService.createParkingLot(1);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleColor("white");
		vehicle.setVehicleRegistrationNumber("KA-01-BB-0001");
		String response = parkingService.parkVehicle(vehicle);
		Assert.assertEquals("Allocated slot number: 1 at level L1", response);
	}

	@Test
	public void vehicleAlreadyParkedTest() throws Exception {

		parkingService.createParkingLot(1);
		Vehicle vehicle1 = new Vehicle();
		vehicle1.setVehicleColor("white");
		vehicle1.setVehicleRegistrationNumber("KA-01-BB-0001");
		parkingService.parkVehicle(vehicle1);
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setVehicleColor("white");
		vehicle2.setVehicleRegistrationNumber("KA-01-BB-0001");
		String response = parkingService.parkVehicle(vehicle2);
		Assert.assertEquals("Vehicle with KA-01-BB-0001 registration number is already parked", response);
	}

	@Test
	public void parkingFullTest() throws Exception {

		parkingService.createParkingLot(1);
		Vehicle vehicle1 = new Vehicle();
		vehicle1.setVehicleColor("white");
		vehicle1.setVehicleRegistrationNumber("KA-01-BB-0001");
		parkingService.parkVehicle(vehicle1);
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setVehicleColor("white");
		vehicle2.setVehicleRegistrationNumber("KA-01-BB-0002");
		String response = parkingService.parkVehicle(vehicle2);
		Assert.assertEquals("Sorry, parking lot is full", response);
	}

	@Test
	public void validateParkingLotWhileUnParkingTest() throws Exception {

		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		parkingService.unParkVehicle(1);
	}

	@Test
	public void invalidParkingLotSlotTest() throws Exception {

		parkingService.createParkingLot(1);
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.INVALID_PARKING_LOT_SLOT_ERROR.getExceptionMessage());
		parkingService.unParkVehicle(0);
	}

	@Test
	public void unParkVehicleTest() throws Exception {

		parkingService.createParkingLot(1);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleColor("white");
		vehicle.setVehicleRegistrationNumber("KA-01-BB-0001");
		parkingService.parkVehicle(vehicle);
		String response = parkingService.unParkVehicle(1);
		Assert.assertEquals("Slot number 1 is free at level L1", response);
	}

	@Test
	public void validateParkingLotWhileGettingStatusTest() throws Exception {

		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		parkingService.getParkingLotStatus();
	}

	@Test
	public void parkingLotStatusTest() throws Exception {

		parkingService.createParkingLot(1);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleColor("white");
		vehicle.setVehicleRegistrationNumber("KA-01-BB-0001");
		parkingService.parkVehicle(vehicle);
		String response = parkingService.getParkingLotStatus();
		Assert.assertEquals("Level Slot No. Registration No. Colour\n  L1     1     KA-01-BB-0001     white\n", response);
	}

	@Test
	public void validateParkingLotWhileGettingRegistrationNumbersByColorTest() throws Exception {

		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		parkingService.getRegistrationNumbersByColor("white");
	}

	@Test
	public void getRegistrationNumbersByColorTest() throws Exception {

		parkingService.createParkingLot(2);
		Vehicle vehicle1 = new Vehicle();
		vehicle1.setVehicleColor("white");
		vehicle1.setVehicleRegistrationNumber("KA-01-BB-0001");
		parkingService.parkVehicle(vehicle1);
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setVehicleColor("white");
		vehicle2.setVehicleRegistrationNumber("KA-01-BB-0002");
		parkingService.parkVehicle(vehicle2);
		String response = parkingService.getRegistrationNumbersByColor("white");
		Assert.assertEquals("KA-01-BB-0001, KA-01-BB-0002", response);
	}

	@Test
	public void registrationNumbersByColorNotFoundTest() throws Exception {

		parkingService.createParkingLot(2);
		Vehicle vehicle1 = new Vehicle();
		vehicle1.setVehicleColor("white");
		vehicle1.setVehicleRegistrationNumber("KA-01-BB-0001");
		parkingService.parkVehicle(vehicle1);
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setVehicleColor("white");
		vehicle2.setVehicleRegistrationNumber("KA-01-BB-0002");
		parkingService.parkVehicle(vehicle2);
		String response = parkingService.getRegistrationNumbersByColor("black");
		Assert.assertEquals("Not found", response);
	}

	@Test
	public void validateParkingLotWhileGettingSlotNumbersByColorTest() throws Exception {

		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		parkingService.getSlotNumbersByColor("white");
	}

	@Test
	public void getSlotNumbersByColorTest() throws Exception {

		parkingService.createParkingLot(2);
		Vehicle vehicle1 = new Vehicle();
		vehicle1.setVehicleColor("white");
		vehicle1.setVehicleRegistrationNumber("KA-01-BB-0001");
		parkingService.parkVehicle(vehicle1);
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setVehicleColor("white");
		vehicle2.setVehicleRegistrationNumber("KA-01-BB-0002");
		parkingService.parkVehicle(vehicle2);
		String response = parkingService.getSlotNumbersByColor("white");
		Assert.assertEquals("Slot 1 at level L1, Slot 2 at level L1", response);
	}

	@Test
	public void slotNumbersByColorNotFoundTest() throws Exception {

		parkingService.createParkingLot(2);
		Vehicle vehicle1 = new Vehicle();
		vehicle1.setVehicleColor("white");
		vehicle1.setVehicleRegistrationNumber("KA-01-BB-0001");
		parkingService.parkVehicle(vehicle1);
		Vehicle vehicle2 = new Vehicle();
		vehicle2.setVehicleColor("white");
		vehicle2.setVehicleRegistrationNumber("KA-01-BB-0002");
		parkingService.parkVehicle(vehicle2);
		String response = parkingService.getSlotNumbersByColor("black");
		Assert.assertEquals("Not found", response);
	}

	@Test
	public void validateParkingLotWhileGettingSlotByRegistrationNumberTest() throws Exception {

		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.PARKING_LOT_NOT_EXIST_ERROR.getExceptionMessage());
		parkingService.getSlotByRegistrationNumber("KA-01-BB-0001");
	}

	@Test
	public void getSlotByRegistrationNumberTest() throws Exception {

		parkingService.createParkingLot(2);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleColor("white");
		vehicle.setVehicleRegistrationNumber("KA-01-BB-0001");
		parkingService.parkVehicle(vehicle);
		String response = parkingService.getSlotByRegistrationNumber("KA-01-BB-0001");
		Assert.assertEquals("Slot 1 at level L1", response);
	}

	@Test
	public void slotByRegistrationNumberNotFoundTest() throws Exception {

		parkingService.createParkingLot(2);
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleColor("white");
		vehicle.setVehicleRegistrationNumber("KA-01-BB-0001");
		parkingService.parkVehicle(vehicle);
		String response = parkingService.getSlotByRegistrationNumber("KA-01-BB-0002");
		Assert.assertEquals("Not found", response);
	}

	@Test
	public void resetTest() {

		String response = parkingService.reset();
		Assert.assertEquals("Parking lot reset successful.", response);
	}
}
