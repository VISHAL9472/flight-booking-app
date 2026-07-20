package com.jsp.flightbooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.flightbooking.dto.ResponseStructure;
import com.jsp.flightbooking.entity.Booking;
import com.jsp.flightbooking.entity.BookingStatus;
import com.jsp.flightbooking.entity.Flight;
import com.jsp.flightbooking.entity.Passenger;
import com.jsp.flightbooking.entity.Payment;
import com.jsp.flightbooking.entity.PaymentStatus;
import com.jsp.flightbooking.exception.BookingAlreadyCancelledException;
import com.jsp.flightbooking.exception.IdNotFoundException;
import com.jsp.flightbooking.exception.NoRecordAvailableException;
import com.jsp.flightbooking.exception.NoSeatsAvailableException;
import com.jsp.flightbooking.repository.BookingRepository;
import com.jsp.flightbooking.repository.FlightRepository;
import com.jsp.flightbooking.repository.PassengerRepository;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private PassengerRepository passengerRepository;
	
	public ResponseEntity<ResponseStructure<Booking>> saveBooking(Integer flightId, Booking booking) {

	    ResponseStructure<Booking> res = new ResponseStructure<>();

	    Optional<Flight> flightOpt = flightRepository.findById(flightId);

	    if (flightOpt.isPresent()) {

	        Flight flight = flightOpt.get();

	        if (booking.getPassengers().size() > flight.getAvailableSeats()) {
	            throw new NoSeatsAvailableException("Seats Not Available");
	        }

	        booking.setFlight(flight);

	        for (Passenger passenger : booking.getPassengers()) {
	            passenger.setBooking(booking);
	        }

	        Payment payment = booking.getPayment();

	        payment.setAmount(
	                flight.getPrice() * booking.getPassengers().size());

	        booking.setPayment(payment);

	        flight.setAvailableSeats(
	                flight.getAvailableSeats()
	                        - booking.getPassengers().size());

	        Booking dbBooking = bookingRepository.save(booking);

	        flightRepository.save(flight);

	        res.setStatusCode(HttpStatus.CREATED.value());
	        res.setMessage("Booking Saved Successfully");
	        res.setData(dbBooking);

	        return new ResponseEntity<>(res, HttpStatus.CREATED);
	    }

	    throw new IdNotFoundException("Flight Id Not Found");
	}
	
	public ResponseEntity<ResponseStructure<List<Booking>>> getAllBookings() {

	    ResponseStructure<List<Booking>> res = new ResponseStructure<>();

	    List<Booking> bookings = bookingRepository.findAll();

	    if (!bookings.isEmpty()) {

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("All Bookings Found Successfully");
	        res.setData(bookings);

	        return new ResponseEntity<ResponseStructure<List<Booking>>>(res, HttpStatus.OK);

	    } else {

	        throw new NoRecordAvailableException("No Booking Records Available");
	    }
	}
	
	public ResponseEntity<ResponseStructure<Booking>> getBookingById(Integer bookingId) {

	    ResponseStructure<Booking> res = new ResponseStructure<>();

	    Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

	    if (bookingOpt.isPresent()) {

	        Booking booking = bookingOpt.get();

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Booking Found Successfully");
	        res.setData(booking);

	        return new ResponseEntity<ResponseStructure<Booking>>(res, HttpStatus.OK);

	    } else {

	        throw new IdNotFoundException("Booking Id Not Found");
	    }
	}
	
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingsByFlight(Integer flightId) {

	    ResponseStructure<List<Booking>> res = new ResponseStructure<>();

	    List<Booking> bookings = bookingRepository.findByFlightFlightId(flightId);

	    if (!bookings.isEmpty()) {

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Bookings Found Successfully");
	        res.setData(bookings);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new NoRecordAvailableException("No Booking Found For This Flight");
	}
	
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingsByDate(LocalDateTime bookingDateTime) {

	    ResponseStructure<List<Booking>> res = new ResponseStructure<>();

	    List<Booking> bookings = bookingRepository.findByBookingDateTime(bookingDateTime);

	    if (!bookings.isEmpty()) {

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Bookings Found Successfully");
	        res.setData(bookings);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new NoRecordAvailableException("No Booking Found On This Date");
	}
	
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengerInBooking(Integer bookingId) {

	    ResponseStructure<List<Passenger>> res = new ResponseStructure<>();

	    Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

	    if (bookingOpt.isPresent()) {

	        Booking booking = bookingOpt.get();

	        List<Passenger> passengers = booking.getPassengers();

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Passenger Found Successfully");
	        res.setData(passengers);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new IdNotFoundException("Booking Id Not Found");
	}
	
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingsByStatus(BookingStatus status) {

	    ResponseStructure<List<Booking>> res = new ResponseStructure<>();

	    List<Booking> bookings = bookingRepository.findByStatus(status);

	    if (!bookings.isEmpty()) {

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Bookings Found Successfully");
	        res.setData(bookings);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new NoRecordAvailableException("No Booking Found With This Status");
	}
	
	public ResponseEntity<ResponseStructure<Payment>> getPaymentDetailsOfBooking(Integer bookingId) {

	    ResponseStructure<Payment> res = new ResponseStructure<>();

	    Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

	    if (bookingOpt.isPresent()) {

	        Booking booking = bookingOpt.get();

	        Payment payment = booking.getPayment();

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Payment Details Found Successfully");
	        res.setData(payment);

	        return new ResponseEntity<ResponseStructure<Payment>>(res, HttpStatus.OK);
	    }

	    throw new IdNotFoundException("Booking Id Not Found");
	}
	
	public ResponseEntity<ResponseStructure<Booking>> updateBookingStatus(Integer bookingId,
	        BookingStatus status) {

	    ResponseStructure<Booking> res = new ResponseStructure<>();

	    Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

	    if (bookingOpt.isPresent()) {

	        Booking booking = bookingOpt.get();

	        booking.setStatus(status);

	        Booking dbBooking = bookingRepository.save(booking);

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Booking Status Updated Successfully");
	        res.setData(dbBooking);

	        return new ResponseEntity<ResponseStructure<Booking>>(res, HttpStatus.OK);
	    }

	    throw new IdNotFoundException("Booking Id Not Found");
	}
	
	public ResponseEntity<ResponseStructure<Booking>> deleteBooking(Integer bookingId) {

	    ResponseStructure<Booking> res = new ResponseStructure<>();

	    Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

	    if (bookingOpt.isPresent()) {

	        Booking booking = bookingOpt.get();

	        // Already Cancelled
	        if (booking.getStatus() == BookingStatus.CANCELLED) {
	            throw new BookingAlreadyCancelledException("Booking is already cancelled");
	        }

	        // Update Booking Status
	        booking.setStatus(BookingStatus.CANCELLED);

	        // Update Payment Status
	        Payment payment = booking.getPayment();
	        payment.setPaymentStatus(PaymentStatus.REFUNDED);

	        // Update Available Seats
	        Flight flight = booking.getFlight();
	        flight.setAvailableSeats(flight.getAvailableSeats() + booking.getPassengers().size());

	        // Save Changes
	        bookingRepository.save(booking);

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Booking Cancelled Successfully");
	        res.setData(booking);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new IdNotFoundException("Booking Id Not Found");
	}
	
	public ResponseEntity<ResponseStructure<List<Booking>>> getBookingHistoryOfPassenger(Integer passengerId) {

	    ResponseStructure<List<Booking>> res = new ResponseStructure<>();

	    List<Booking> bookings = bookingRepository.findByPassengersPassengerId(passengerId);

	    if (!bookings.isEmpty()) {

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Booking History Found Successfully");
	        res.setData(bookings);

	        return new ResponseEntity<ResponseStructure<List<Booking>>>(res, HttpStatus.OK);
	    }

	    throw new NoRecordAvailableException("No Booking History Found");
	}
	
	
	
	
}
