package com.jsp.flightbooking.exception;

public class FlightAlreadyBookedException extends RuntimeException{
	 public FlightAlreadyBookedException(String message) {
	        super(message);
	    }

}
