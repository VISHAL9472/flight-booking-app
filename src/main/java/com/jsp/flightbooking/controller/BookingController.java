package com.jsp.flightbooking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.flightbooking.dto.ResponseStructure;
import com.jsp.flightbooking.entity.Booking;
import com.jsp.flightbooking.entity.BookingStatus;
import com.jsp.flightbooking.entity.Passenger;
import com.jsp.flightbooking.entity.Payment;
import com.jsp.flightbooking.service.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/{flightId}")
	public ResponseEntity<ResponseStructure<Booking>> saveBooking(
            @PathVariable Integer flightId,
            @RequestBody Booking booking) {

        return bookingService.saveBooking(flightId, booking);
    }
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBookings() {

	    return bookingService.getAllBookings();
	}
	
	@GetMapping("/{bookingId}")
	public ResponseEntity<ResponseStructure<Booking>> getBookingById(
	        @PathVariable Integer bookingId) {

	    return bookingService.getBookingById(bookingId);
	}
	
	@GetMapping("/flight/{flightId}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingsByFlight(
	        @PathVariable Integer flightId) {

	    return bookingService.getBookingsByFlight(flightId);
	}
	
	@GetMapping("/date/{bookingDateTime}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingsByDate(
	        @PathVariable LocalDateTime bookingDateTime) {

	    return bookingService.getBookingsByDate(bookingDateTime);
	}
	
	@GetMapping("/{bookingId}/passenger")
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengerInBooking(
	        @PathVariable Integer bookingId) {

	    return bookingService.getPassengerInBooking(bookingId);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingsByStatus(
	        @PathVariable BookingStatus status) {

	    return bookingService.getBookingsByStatus(status);
	}
	
	@GetMapping("/{bookingId}/payment")
	public ResponseEntity<ResponseStructure<Payment>> getPaymentDetailsOfBooking(
	        @PathVariable Integer bookingId) {

	    return bookingService.getPaymentDetailsOfBooking(bookingId);
	}
	
	@PatchMapping("/{bookingId}/{status}")
	public ResponseEntity<ResponseStructure<Booking>> updateBookingStatus(
	        @PathVariable Integer bookingId,
	        @PathVariable BookingStatus status) {

	    return bookingService.updateBookingStatus(bookingId, status);
	}
	
	@DeleteMapping("/{bookingId}")
	public ResponseEntity<ResponseStructure<Booking>> deleteBooking(
	        @PathVariable Integer bookingId) {

	    return bookingService.deleteBooking(bookingId);
	}
	
	@GetMapping("/passenger/{passengerId}")
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingHistoryOfPassenger(
	        @PathVariable Integer passengerId) {

	    return bookingService.getBookingHistoryOfPassenger(passengerId);
	}
}
