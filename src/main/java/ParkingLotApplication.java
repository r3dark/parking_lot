import Processor.CommandsProcessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author rohitsharma
 */
public class ParkingLotApplication {

	public static void main(String[] args) throws Exception {

		// Print banner
		System.out.println( " ------------------------------------------------- \n" +
							"|             PARKING-LOT APPLICATION             |\n" +
							" ------------------------------------------------- \n");

		BufferedReader bufferedReader;
		String inputLine;
		CommandsProcessor commandsProcessor = new CommandsProcessor();
		File file = new File(ParkingLotApplication.class.getClassLoader().getResource("parking_lot_file_inputs.txt").getFile());
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			while ((inputLine = bufferedReader.readLine()) != null) {
				commandsProcessor.command(inputLine.trim());
			}
		} catch (Exception e) {

		}

	}
}
