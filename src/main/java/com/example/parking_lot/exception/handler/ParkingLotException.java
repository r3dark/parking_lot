package com.example.parking_lot.exception.handler;

/**
 * @author rohitsharma
 */
public class ParkingLotException extends Exception {

	private int errorCode;

	public ParkingLotException(int errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	public ParkingLotException(int errorCode, String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
		this.errorCode = errorCode;
	}

	public ParkingLotException(String errorMessage) {
		super(errorMessage);
	}

	public ParkingLotException(Throwable throwable) {
		super(throwable);
	}

	public ParkingLotException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
