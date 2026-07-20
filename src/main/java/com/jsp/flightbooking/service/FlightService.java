package com.jsp.flightbooking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.flightbooking.dto.ResponseStructure;
import com.jsp.flightbooking.entity.Flight;
import com.jsp.flightbooking.exception.FlightAlreadyBookedException;
import com.jsp.flightbooking.exception.IdNotFoundException;
import com.jsp.flightbooking.exception.NoRecordAvailableException;
import com.jsp.flightbooking.repository.BookingRepository;
import com.jsp.flightbooking.repository.FlightRepository;

@Service
public class FlightService {
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	public ResponseEntity<ResponseStructure<Flight>> saveFlight(Flight flight) {

		ResponseStructure<Flight> response = new ResponseStructure<>();

		Flight savedFlight = flightRepository.save(flight);

		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("Flight Saved Successfully");
		response.setData(savedFlight);

		return new ResponseEntity<ResponseStructure<Flight>>(response, HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlights() {

		ResponseStructure<List<Flight>> res = new ResponseStructure<>();

		List<Flight> flights = flightRepository.findAll();

		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("All Flights Fetched Successfully");
		res.setData(flights);

		return new ResponseEntity<ResponseStructure<List<Flight>>>(res, HttpStatus.OK);

	}
	
	public ResponseEntity<ResponseStructure<Flight>> getFlightById(Integer id) {

		Optional<Flight> opt = flightRepository.findById(id);

		if (opt.isPresent()) {

			ResponseStructure<Flight> res = new ResponseStructure<>();

			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Flight Found Successfully");
			res.setData(opt.get());

			return new ResponseEntity<ResponseStructure<Flight>>(res, HttpStatus.OK);

		}

		throw new IdNotFoundException("Flight Id Not Found");

	}
	
	public ResponseEntity<ResponseStructure<Flight>> updateFlight(Integer id, Flight flight) {

		Optional<Flight> opt = flightRepository.findById(id);

		if (opt.isPresent()) {

			flight.setFlightId(id);

			Flight updatedFlight = flightRepository.save(flight);

			ResponseStructure<Flight> res = new ResponseStructure<>();

			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Flight Updated Successfully");
			res.setData(updatedFlight);

			return new ResponseEntity<ResponseStructure<Flight>>(res, HttpStatus.OK);

		}

		throw new IdNotFoundException("Flight Id Not Found");

	}
	
	public ResponseEntity<ResponseStructure<Flight>> deleteFlight(Integer id) {

	    Optional<Flight> opt = flightRepository.findById(id);

	    if (opt.isPresent()) {

	        if (bookingRepository.existsByFlightFlightId(id)) {
	            throw new FlightAlreadyBookedException(
	                    "Flight cannot be deleted because bookings already exist");
	        }

	        Flight flight = opt.get();

	        flightRepository.deleteById(id);

	        ResponseStructure<Flight> res = new ResponseStructure<>();

	        res.setStatusCode(HttpStatus.OK.value());
	        res.setMessage("Flight Deleted Successfully");
	        res.setData(flight);

	        return new ResponseEntity<ResponseStructure<Flight>>(res, HttpStatus.OK);

	    }

	    throw new IdNotFoundException("Flight Id Not Found");
	}
	
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightByAirline(String airline){

		List<Flight> flights = flightRepository.findByAirline(airline);

		if(!flights.isEmpty()) {

			ResponseStructure<List<Flight>> res = new ResponseStructure<>();

			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Flights Found Successfully");
			res.setData(flights);

			return new ResponseEntity<ResponseStructure<List<Flight>>>(res,HttpStatus.OK);

		}

		throw new NoRecordAvailableException("No Flight Found For Airline : " + airline);

	}
	
	public ResponseEntity<ResponseStructure<List<Flight>>> searchFlight(
			String source,
			String destination,
			LocalDateTime departureDateTime) {

		List<Flight> flights = flightRepository
				.findBySourceAndDestinationAndDepartureDateTime(source, destination, departureDateTime);

		if (!flights.isEmpty()) {

			ResponseStructure<List<Flight>> res = new ResponseStructure<>();

			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Flights Found Successfully");
			res.setData(flights);

			return new ResponseEntity<ResponseStructure<List<Flight>>>(res, HttpStatus.OK);

		}

		throw new NoRecordAvailableException("No Flight Available");

	}
	
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightByPriceRange(double minPrice,
			double maxPrice) {

		List<Flight> flights = flightRepository.findByPriceBetween(minPrice, maxPrice);

		if (!flights.isEmpty()) {

			ResponseStructure<List<Flight>> res = new ResponseStructure<>();

			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Flights Found Successfully");
			res.setData(flights);

			return new ResponseEntity<ResponseStructure<List<Flight>>>(res, HttpStatus.OK);

		}

		throw new NoRecordAvailableException("No Flight Found Between Price Range");

	}
	
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightByAvailableSeats(int availableSeats){
		List<Flight> flights = flightRepository.findByAvailableSeatsGreaterThanEqual(availableSeats);
		if(!flights.isEmpty()) {
			
			ResponseStructure<List<Flight>> res = new ResponseStructure<>();
			
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Flght found successfully");
			res.setData(flights);
			
			return new ResponseEntity<ResponseStructure<List<Flight>>>(res,HttpStatus.OK);
		}
		throw new NoRecordAvailableException("No flight available");
	}
	
	public ResponseEntity<ResponseStructure<Flight>> getCheapestFlightBetweenTwoCities(
			String source, String destination) {

		Optional<Flight> opt = flightRepository
				.findFirstBySourceAndDestinationOrderByPriceAsc(source, destination);

		if (opt.isPresent()) {

			ResponseStructure<Flight> res = new ResponseStructure<>();

			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Cheapest Flight Found Successfully");
			res.setData(opt.get());

			return new ResponseEntity<ResponseStructure<Flight>>(res, HttpStatus.OK);

		}

		throw new NoRecordAvailableException("No Flight Available");

	}
	
	public ResponseEntity<ResponseStructure<Page<Flight>>> getFlightByPagination(
			int pageNumber,
			int pageSize,
			String sortBy) {

		Page<Flight> page = flightRepository.findAll(
				PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)));

		if (!page.isEmpty()) {

			ResponseStructure<Page<Flight>> res = new ResponseStructure<>();

			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Flights Found Successfully");
			res.setData(page);

			return new ResponseEntity<ResponseStructure<Page<Flight>>>(res, HttpStatus.OK);

		}

		throw new NoRecordAvailableException("No Flight Available");

	}
	
	

}
