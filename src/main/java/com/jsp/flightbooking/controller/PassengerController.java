package com.jsp.flightbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.flightbooking.dto.ResponseStructure;
import com.jsp.flightbooking.entity.Gender;
import com.jsp.flightbooking.entity.Passenger;
import com.jsp.flightbooking.service.PassengerService;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

	@Autowired
	private PassengerService passengerService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Passenger>> savePassenger(@RequestBody Passenger passenger){
		
		return passengerService.savePassenger(passenger);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassengers() {

	    return passengerService.getAllPassengers();
	}
	
	@GetMapping("/{passengerId}")
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerById(
	        @PathVariable Integer passengerId) {

	    return passengerService.getPassengerById(passengerId);
	}
	
	@GetMapping("/contact/{contact}")
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerByContact(
	        @PathVariable Long contact) {

	    return passengerService.getPassengerByContact(contact);
	}
	
	@GetMapping("/gender/{gender}")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengerByGender(
	        @PathVariable Gender gender) {

	    return passengerService.getPassengerByGender(gender);
	}
	
	@PutMapping("/{passengerId}")
	public ResponseEntity<ResponseStructure<Passenger>> updatePassenger(
	        @PathVariable Integer passengerId,
	        @RequestBody Passenger passenger) {

	    return passengerService.updatePassenger(passengerId, passenger);
	}
	
	@DeleteMapping("/{bookingId}/{passengerId}")
	public ResponseEntity<ResponseStructure<Passenger>> removePassengerFromBooking(
	        @PathVariable Integer bookingId,
	        @PathVariable Integer passengerId) {

	    return passengerService.removePassengerFromBooking(bookingId, passengerId);
	}
	
	@GetMapping("/flight/{flightId}")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengerByFlight(
	        @PathVariable Integer flightId) {

	    return passengerService.getPassengerByFlight(flightId);
	}
	
}
