package com.jsp.flightbooking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.flightbooking.dto.ResponseStructure;
import com.jsp.flightbooking.entity.Flight;
import com.jsp.flightbooking.service.FlightService;


@RestController
@RequestMapping("/flights")
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Flight>> saveFlight(@RequestBody Flight flight){
		return flightService.saveFlight(flight);
	}
	
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Flight>>> getAllFlights() {

		return flightService.getAllFlights();

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Flight>> getFlightById(@PathVariable Integer id) {

		return flightService.getFlightById(id);

	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseStructure<Flight>> updateFlight(@PathVariable Integer id,
			@RequestBody Flight flight) {

		return flightService.updateFlight(id, flight);

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<Flight>> deleteFlight(@PathVariable Integer id) {

		return flightService.deleteFlight(id);

	}
	
	@GetMapping("/airline/{airline}")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightByAirline(@PathVariable String airline){

		return flightService.getFlightByAirline(airline);

	}
	
	@GetMapping("/search")
	public ResponseEntity<ResponseStructure<List<Flight>>> searchFlight(
			@RequestParam String source,
			@RequestParam String destination,
			@RequestParam
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
			LocalDateTime departureDateTime) {

		return flightService.searchFlight(source, destination, departureDateTime);

	}
	
	@GetMapping("/price")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightByPriceRange(
			@RequestParam double minPrice,
			@RequestParam double maxPrice) {

		return flightService.getFlightByPriceRange(minPrice, maxPrice);

	}
	
	@GetMapping("/seats")
	public ResponseEntity<ResponseStructure<List<Flight>>> getFlightByAvailableSeats(@RequestParam int availableSeats){
		
		return flightService.getFlightByAvailableSeats(availableSeats);
	}
	
	@GetMapping("/cheapest")
	public ResponseEntity<ResponseStructure<Flight>> getCheapestFlightBetweenTwoCities(
			@RequestParam String source,
			@RequestParam String destination) {

		return flightService.getCheapestFlightBetweenTwoCities(source, destination);

	}
	
	@GetMapping("/pagination")
	public ResponseEntity<ResponseStructure<Page<Flight>>> getFlightByPagination(
			@RequestParam int pageNumber,
			@RequestParam int pageSize,
			@RequestParam String sortBy) {

		return flightService.getFlightByPagination(pageNumber, pageSize, sortBy);

	}

}
