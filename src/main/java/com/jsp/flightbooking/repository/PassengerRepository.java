package com.jsp.flightbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flightbooking.entity.Gender;
import com.jsp.flightbooking.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer>{
	
	boolean existsByContact(Long contact);
	
	Optional<Passenger> findByContact(Long contact);
	
	List<Passenger> findByGender(Gender gender);
	
	List<Passenger> findByBookingFlightFlightId(Integer flightId);

}
