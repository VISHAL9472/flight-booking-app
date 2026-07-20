package com.jsp.flightbooking.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Passenger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer passengerId;
	
	private String name;
	
	private Integer age;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private Integer seatNumber;
	
	private String email;
	
	@Column(unique=true)
	private Long contact;
	
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Booking booking;

}
