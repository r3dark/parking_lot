package io.gojek.parking_lot;

import io.gojek.parking_lot.exception.ParkingLotExceptionMessage;
import io.gojek.parking_lot.exception.handler.ParkingLotException;
import org.hamcrest.Matcher;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ParkingLotApplicationTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
	}

	@Test
	public void mainTest() throws Exception {

		String[] args = {"src/test/resources/parking_lot_file_inputs_for_tests.txt"};
		ParkingLotApplication.main(args);
		String expectedOutput = "/-------------------------------------------------\\\n" +
				"|             PARKING-LOT APPLICATION             |\n" +
				"\\-------------------------------------------------/\n" +
				"\n" +
				"Created a parking lot with 6 slots\n" +
				"Allocated slot number: 1 at level L1\n" +
				"Allocated slot number: 2 at level L1\n" +
				"Allocated slot number: 3 at level L1\n" +
				"Allocated slot number: 4 at level L1\n" +
				"Allocated slot number: 5 at level L1\n" +
				"Allocated slot number: 6 at level L1\n" +
				"Slot number 4 is free at level L1\n" +
				"Level Slot No. Registration No. Colour\n" +
				"  L1     1     KA-01-HH-1234     White\n" +
				"  L1     2     KA-01-HH-9999     White\n" +
				"  L1     3     KA-01-BB-0001     Black\n" +
				"  L1     5     KA-01-HH-2701     Blue\n" +
				"  L1     6     KA-01-HH-3141     Black\n" +
				"\n" +
				"Allocated slot number: 4 at level L1\n" +
				"Sorry, parking lot is full\n" +
				"KA-01-HH-1234, KA-01-HH-9999, KA-01-P-333\n" +
				"Slot 1 at level L1, Slot 2 at level L1, Slot 4 at level L1\n" +
				"Slot 6 at level L1\n" +
				"Not found\n";
		Assert.assertEquals(expectedOutput, outContent.toString());
	}

	@Test
	public void mainEmptyFileTest() throws Exception {

		String[] args = {"src/test/resources/empty_file_for_tests.txt"};
		expectedException.expect(ParkingLotException.class);
		expectedException.expectMessage(ParkingLotExceptionMessage.EMPTY_FILE.getExceptionMessage());
		ParkingLotApplication.main(args);
	}
}
