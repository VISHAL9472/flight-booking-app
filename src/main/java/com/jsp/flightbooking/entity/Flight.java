package com.jsp.flightbooking.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Flight {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer flightId;
	
	private String airline;
	
	private String flightNumber;
	
	private String source;
	
	private String destination;
	
	private LocalDateTime departureDateTime;
	
	private LocalDateTime arrivalDateTime;
	
	private Double price;
	
	private Integer totalSeats;
	
	private Integer availableSeats;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn
	@JsonIgnore
	private List<Booking> bookings;

}
