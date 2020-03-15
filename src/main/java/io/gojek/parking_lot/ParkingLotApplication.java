package io.gojek.parking_lot;

import io.gojek.parking_lot.processor.CommandProcessor;
import io.gojek.parking_lot.contant.ApplicationProperties;
import io.gojek.parking_lot.exception.ParkingLotExceptionMessage;
import io.gojek.parking_lot.exception.handler.ParkingLotException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * @author rohitsharma
 */
public class ParkingLotApplication {

	public static void main(String[] args) throws Exception {

		// Print banner
		System.out.println(generateBanner());

		BufferedReader bufferedReader = null;
		String inputLine;
		int lineNumber = 0;
		CommandProcessor commandProcessor = new CommandProcessor();

		try {
			if (args.length == 1) {
				File file = new File(args[0]);
				try {
					bufferedReader = new BufferedReader(new FileReader(file));
					while ((inputLine = bufferedReader.readLine()) != null) {
						commandProcessor.command(inputLine.trim(), ++lineNumber);
					}
					if (lineNumber == 0) {
						throw new ParkingLotException(ParkingLotExceptionMessage.EMPTY_FILE.getExceptionMessage());
					}
				} catch (Exception e) {
					throw new ParkingLotException(ParkingLotExceptionMessage.REQUEST_PROCESSING_ERROR.getExceptionMessage()
					.concat("Exception is:\n").concat(String.valueOf(e)));
				}
			} else {
				System.out.println("Input 'Exit' to quit.");
				showCommands();

				while (true) {
					try {
						bufferedReader = new BufferedReader(new InputStreamReader(System.in));
						inputLine = bufferedReader.readLine().trim();
						if (inputLine.equals("exit")) {
							break;
						} else {
							commandProcessor.command(inputLine, lineNumber);
						}
					} catch (Exception e) {
						System.out.println(ParkingLotExceptionMessage.REQUEST_PROCESSING_ERROR.getExceptionMessage()
								.concat("Exception is:\n").concat(String.valueOf(e)));
						showCommands();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
	}

	private static String generateBanner() {
		return ("/-------------------------------------------------\\\n"
		.concat("|             PARKING-LOT APPLICATION             |\n")
		.concat("\\-------------------------------------------------/\n"));
	}

	private static void showCommands() {
		System.out.println(
				new StringBuilder()
				.append("Please use available commands, insert values in place of {variable}:\n")
				.append("1. ").append(ApplicationProperties.CREATE_PARKING_LOT).append(" {parking_lot_size}\n")
				.append("2. ").append(ApplicationProperties.PARK_VEHICLE).append(" {registration_number} {color}\n")
				.append("3. ").append(ApplicationProperties.UN_PARK_VEHICLE).append(" {slot_number}\n")
				.append("4. ").append(ApplicationProperties.PARKING_LOT_STATUS).append("\n")
				.append("5. ").append(ApplicationProperties.REGISTRATION_NUMBER_FOR_CARS_WITH_COLOR).append(" {color}\n")
				.append("6. ").append(ApplicationProperties.SLOT_NUMBERS_FOR_CARS_WITH_COLOR).append(" {color}\n")
				.append("7. ").append(ApplicationProperties.SLOT_NUMBER_FOR_REGISTRATION_NUMBER).append(" {registration_number}").toString()
		);
	}
}
