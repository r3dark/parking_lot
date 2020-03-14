package model;

/**
 * @author rohitsharma
 */
public class Car extends Vehicle {

	private String carRegistrationNumber;

	private String carColor;

	public Car(String carRegistrationNumber, String carColor) {
		super(carRegistrationNumber, carColor);
	}
}
