package com.jsp.flightbooking.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.flightbooking.entity.Booking;
import com.jsp.flightbooking.entity.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Integer>{
	
	List<Booking> findByFlightFlightId(Integer flightId);
	
	List<Booking> findByBookingDateTime(LocalDateTime bookingDateTime);
	
	List<Booking> findByStatus(BookingStatus status);
	
	boolean existsByFlightFlightId(Integer flightId);
	
	List<Booking> findByPassengersPassengerId(Integer passengerId);
	

}
