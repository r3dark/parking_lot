package com.example.parking_lot.model;

/**
 * @author rohitsharma
 */
public class Vehicle {

	private String vehicleRegistrationNumber;

	private String vehicleColor;

	/**
	 * @return registration number of vehicle
	 */
	public String getVehicleRegistrationNumber() {
		return vehicleRegistrationNumber;
	}

	/**
	 * @param vehicleRegistrationNumber
	 * set registration number of vehicle
	 */
	public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
	}

	/**
	 * @return color of vehicle
	 */
	public String getVehicleColor() {
		return vehicleColor;
	}

	/**
	 * @param vehicleColor
	 * set color of vehicle
	 */
	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	@Override
	public String toString() {
		return "Vehicle{" +
				"vehicleRegistrationNumber='" + vehicleRegistrationNumber + '\'' +
				", vehicleColor='" + vehicleColor + '\'' +
				'}';
	}
}
