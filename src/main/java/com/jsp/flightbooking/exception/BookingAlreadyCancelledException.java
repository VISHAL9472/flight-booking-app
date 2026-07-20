package com.jsp.flightbooking.exception;

public class BookingAlreadyCancelledException extends RuntimeException{
	public BookingAlreadyCancelledException(String message) {
		super(message);
	}

}
