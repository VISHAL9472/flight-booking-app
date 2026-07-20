package com.jsp.flightbooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.flightbooking.dto.ResponseStructure;
import com.jsp.flightbooking.entity.Booking;
import com.jsp.flightbooking.entity.Flight;
import com.jsp.flightbooking.entity.Gender;
import com.jsp.flightbooking.entity.Passenger;
import com.jsp.flightbooking.entity.Payment;
import com.jsp.flightbooking.exception.ContactAlreadyExistsException;
import com.jsp.flightbooking.exception.IdNotFoundException;
import com.jsp.flightbooking.exception.InvalidContactNumberException;
import com.jsp.flightbooking.exception.NoRecordAvailableException;
import com.jsp.flightbooking.repository.BookingRepository;
import com.jsp.flightbooking.repository.FlightRepository;
import com.jsp.flightbooking.repository.PassengerRepository;

@Service
public class PassengerService {
	
	@Autowired
	private PassengerRepository passengerRepository;
	
	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private FlightRepository flightRepository;
	
	public ResponseEntity<ResponseStructure<Passenger>> savePassenger(Passenger passenger) {

	    ResponseStructure<Passenger> res = new ResponseStructure<>();

	    if (String.valueOf(passenger.getContact()).length() != 10) {
	        throw new InvalidContactNumberException("Contact number must contain exactly 10 digits");
	    }

	    if (passengerRepository.existsByContact(passenger.getContact())) {
	        throw new ContactAlreadyExistsException("Contact number already exists");
	    }

	    Passenger dbPassenger = passengerRepository.save(passenger);

	    res.setStatusCode(HttpStatus.CREATED.value());
	    res.setMessage("Passenger Saved Successfully");
	    res.setData(dbPassenger);

	    return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<List<Passenger>>> getAllPassengers() {

	    ResponseStructure<List<Passenger>> res = new ResponseStructure<>();

	    List<Passenger> passengers = passengerRepository.findAll();

	    if (!passengers.isEmpty()) {

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Passengers Found Successfully");
	        res.setData(passengers);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new NoRecordAvailableException("No Passenger Record Available");
	}
	
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerById(Integer passengerId) {

	    ResponseStructure<Passenger> res = new ResponseStructure<>();

	    Optional<Passenger> opt = passengerRepository.findById(passengerId);

	    if (opt.isPresent()) {

	        Passenger passenger = opt.get();

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Passenger Found Successfully");
	        res.setData(passenger);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new IdNotFoundException("Passenger Id Not Found");
	}
	
	public ResponseEntity<ResponseStructure<Passenger>> getPassengerByContact(Long contact) {

	    ResponseStructure<Passenger> res = new ResponseStructure<>();

	    Optional<Passenger> opt = passengerRepository.findByContact(contact);

	    if (opt.isPresent()) {

	        Passenger passenger = opt.get();

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Passenger Found Successfully");
	        res.setData(passenger);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new IdNotFoundException("Passenger Contact Number Not Found");
	}
	
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengerByGender(Gender gender) {

	    ResponseStructure<List<Passenger>> res = new ResponseStructure<>();

	    List<Passenger> passengers = passengerRepository.findByGender(gender);

	    if (!passengers.isEmpty()) {

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Passengers Found Successfully");
	        res.setData(passengers);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new NoRecordAvailableException("No Passenger Found");
	}
	
	public ResponseEntity<ResponseStructure<Passenger>> updatePassenger(Integer passengerId,
	        Passenger updatedPassenger) {

	    ResponseStructure<Passenger> res = new ResponseStructure<>();

	    Optional<Passenger> opt = passengerRepository.findById(passengerId);

	    if (opt.isPresent()) {

	        Passenger passenger = opt.get();

	        passenger.setName(updatedPassenger.getName());
	        passenger.setAge(updatedPassenger.getAge());
	        passenger.setGender(updatedPassenger.getGender());
	        passenger.setEmail(updatedPassenger.getEmail());
	        passenger.setContact(updatedPassenger.getContact());

	        Passenger savedPassenger = passengerRepository.save(passenger);

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Passenger Updated Successfully");
	        res.setData(savedPassenger);

	        return new ResponseEntity<>(res, HttpStatus.OK);
	    }

	    throw new IdNotFoundException("Passenger Id Not Found");
	}
	
	public ResponseEntity<ResponseStructure<Passenger>> removePassengerFromBooking(Integer bookingId, Integer passengerId) {

	    ResponseStructure<Passenger> res = new ResponseStructure<>();

	    Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);

	    if (bookingOpt.isPresent()) {

	        Booking booking = bookingOpt.get();

	        Optional<Passenger> passengerOpt = passengerRepository.findById(passengerId);

	        if (passengerOpt.isPresent()) {

	            Passenger passenger = passengerOpt.get();

	            if (booking.getPassengers().contains(passenger)) {

	                booking.getPassengers().remove(passenger);

	                passenger.setBooking(null);

	                Flight flight = booking.getFlight();
	                flight.setAvailableSeats(flight.getAvailableSeats() + 1);

	                if (booking.getPassengers().isEmpty()) {

	                    bookingRepository.delete(booking);

	                } else {

	                    Payment payment = booking.getPayment();

	                    payment.setAmount(
	                            flight.getPrice() * booking.getPassengers().size());

	                    bookingRepository.save(booking);
	                }

	                passengerRepository.delete(passenger);

	                res.setStatusCode(HttpStatus.OK.value());
	                res.setMessage("Passenger Removed Successfully");
	                res.setData(passenger);

	                return new ResponseEntity<>(res, HttpStatus.OK);
	            }

	            throw new IdNotFoundException("Passenger does not belong to this booking");
	        }

	        throw new IdNotFoundException("Passenger Id Not Found");
	    }

	    throw new IdNotFoundException("Booking Id Not Found");
	}
	
	public ResponseEntity<ResponseStructure<List<Passenger>>> getPassengerByFlight(Integer flightId) {

	    ResponseStructure<List<Passenger>> res = new ResponseStructure<>();

	    List<Passenger> passengers = passengerRepository.findByBookingFlightFlightId(flightId);

	    if (!passengers.isEmpty()) {

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Passengers Found Successfully");
	        res.setData(passengers);

	        return new ResponseEntity<ResponseStructure<List<Passenger>>>(res, HttpStatus.OK);
	    }

	    throw new NoRecordAvailableException("No Passenger Found");
	}
}
