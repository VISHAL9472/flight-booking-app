package com.jsp.flightbooking.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookingId;
	
	private LocalDateTime bookingDateTime;
	
	@Enumerated(EnumType.STRING)
	private BookingStatus status;
	
	@ManyToOne
	@JoinColumn
	private Flight flight;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn
	private List<Passenger> passengers;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Payment payment;
	

}
