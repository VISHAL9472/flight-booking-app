package com.jsp.flightbooking.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flightbooking.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, Integer>{
	
	List<Flight> findByAirline(String airline);
	
	List<Flight> findBySourceAndDestinationAndDepartureDateTime(String source,
			String destination, LocalDateTime departureDateTime);
	
	List<Flight> findByPriceBetween(double minPrice, double maxPrice);
	
	List<Flight> findByAvailableSeatsGreaterThanEqual(int availableSeats);
	
	Optional<Flight> findFirstBySourceAndDestinationOrderByPriceAsc(String source, String destination);

}
